(function() {
	app.service('productSrv',['$resource', function($resource){
		var $product = $resource('/product/:id', {id:'@id'}, {
			get: { transformResponse : parser },
			save: { method: 'POST', transformResponse : parser },
			remove: { method: 'DELETE', transformResponse : parser }
		});
		
		this.query = function() {
			return $product.query();
		};

		this.save = function(item, func) {
			return $product.save(item, func);
		};

		this.remove = function(item, func) {
			return $product.remove(item, func);
		};
	}]);
	app.controller('productCtl',['$scope', 'productSrv', function($scope, productSrv){
		var $product = productSrv;

		$scope.title = 'Products';
		$scope.items = [];
		$scope.current = {};
		
		$scope.modal = false;
		$scope.view = 'list';
		
		$scope.init = function() {
			$scope.items = $product.query();
		};

		$scope.clear = function(){
			$scope.current = {};
		}
		
		$scope.add = function(){
			$scope.clear();

		}
		
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
	        var index = $scope.items.findIndex(function(item, index){
	        	if (item.id == id) {
	        		return index;
	        	}
	        });
			$product.remove({"id":id}, function(response){
				$scope.items.splice(index, 1);
			});
		}
	}]);
})();