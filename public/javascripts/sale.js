(function() {
	app.service('saleSrv',['$resource', function($resource){
		var $sale = $resource('/sale/:id', {id:'@id'}, {
			get: { transformResponse : parser },
			save: { method: 'POST', transformResponse : parser }
		});
		
		this.query = function(func) {
			return $sale.query(func);
		};

		this.get = function(item, func) {
			return $sale.save(item, func);
		};
		
		this.save = function(item, func) {
			return $sale.save(item, func);
		};
	}]);
	app.controller('saleCtl',['$scope', 'saleSrv', 'productSrv', function($scope, saleSrv, productSrv){
		var $sale = saleSrv;
		var $product = productSrv;

		$scope.title = 'Sales';
		$scope.items = [];
		$scope.current = {};
		$scope.products = [];
		
		$scope.init = function() {
			$scope.items = $sale.query();
		};

		$scope.clear = function(){
			$scope.current = {};
		}
		
		$scope.add = function(){
			$scope.clear();
			$scope.products = $product.query();
		}
		
		$scope.setCurrent = function(current){
			$scope.current = angular.copy(current);
		};
		
		$scope.save = function(){
			$sale.save($scope.current, function(response){
				$scope.items.push(response);
				$scope.current = {};
			});
		};
	}]);
})();