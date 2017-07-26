(function() {
	app.service('supplySrv',['$resource', function($resource){
		var $supply = $resource('/supply/:id', {id:'@id'}, {
			get: { transformResponse : parser },
			save: { method: 'POST', transformResponse : parser },
			remove: { method: 'DELETE', transformResponse : parser }
		});
		
		this.query = function() {
			return $supply.query();
		};

		this.save = function(item, func) {
			return $supply.save(item, func);
		};

		this.remove = function(item, func) {
			return $supply.remove(item, func);
		};
	}]);
	app.controller('supplyCtl',['$scope', 'supplySrv', function($scope, supplySrv){
		var $supply = supplySrv;

		$scope.title = 'Supplies';
		$scope.items = [];
		$scope.current = {};
		
		$scope.modal = false;
		$scope.view = 'list';
		
		$scope.init = function() {
			$scope.items = $supply.query();
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
			$supply.save($scope.current, function(response){
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
			$supply.remove({"id":id}, function(response){
				if (index > -1) {
					$scope.items.splice(index, 1);
				}
			});
		}
	}]);
})();