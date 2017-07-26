(function() {
	app.service('productSrv',['$resource', function($resource){
		var $product = $resource('/product/:id', {id:'@id'}, {
			get: { transformResponse : parser },
			save: { method: 'POST', transformResponse : parser },
			remove: { method: 'DELETE', transformResponse : parser }
		});
		
		this.query = function(func) {
			return $product.query(func);
		};

		this.save = function(item, func) {
			return $product.save(item, func);
		};

		this.remove = function(item, func) {
			return $product.remove(item, func);
		};
	}]);
	app.controller('productCtl',['$scope', 'productSrv', 'supplySrv', function($scope, productSrv, supplySrv){
		var $product = productSrv;
		var $supply = supplySrv;

		$scope.title = 'Products';
		$scope.items = [];
		$scope.current = {};
		$scope.supplies = [];
		
		$scope.init = function() {
			$scope.items = $product.query();
		};

		$scope.clear = function(){
			$scope.current = {};
		}
		
		$scope.add = function(){
			$scope.clear();
			$scope.supplies = $supply.query();
		}

		$scope.setUpdate = function(current){
			$scope.clear();
			$scope.supplies = $supply.query(function(){
				$scope.current = angular.copy(current);
			});
		};

		$scope.setCurrent = function(current){
			$scope.current = angular.copy(current);
		};
		
		$scope.save = function(){
			$product.save($scope.current, function(response){
				if (!$scope.current.id) {
					$scope.items.push(response);
				} else {
					$scope.items.some(function(item, index){
						if (item.id == response.id) {
							$scope.items[index] = response;
							return true;
						}
						return false;
					});
				}
				$scope.current = {};
			});
		};
		
		$scope.remove = function(id){
			var index = -1;
        	$scope.items.findIndex(function(item, i){
	        	if (item.id == id) {
	        		index = i;
	        		return true;
	        	}
	        	return false;
	        });
			$product.remove({"id":id}, function(response){
				if (index > -1) {
					$scope.items.splice(index, 1);
				}
			});
		}
	}]);
})();