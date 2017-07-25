var $notices = $('#notices').notify({
    template: {
        info: {icon: 'glyphicon glyphicon-info-sign', 'class': 'alert-info'},
        success: {icon: 'glyphicon glyphicon-ok-sign', 'class': 'alert-success'},
        warning: {icon: 'glyphicon glyphicon-exclamation-sign', 'class': 'alert-warning'},
        error: {icon: 'glyphicon glyphicon-remove-sign', 'class': 'alert-danger'}
    }
}).data('notify');

var parser = function(data, header, status) {
	var dataJson = angular.fromJson(data);
	if (!!dataJson.message) {
		$notices.msg(dataJson.message);
	}
	return dataJson.data || {};
};

var app = angular.module('fullPlay', ['ngResource']);

app.controller('mainCtl', ['$scope', '$resource', function($scope, $resource){
	$scope.title = 'Sample';
	$scope.products = [];
	$scope.suplies = [];
	
	var $product = $resource('/product/:id', {id:'@id'}, {
		get: { transformResponse : parser },
		save: { method: 'POST', transformResponse : parser },
		remove: { method: 'DELETE', transformResponse : parser }
	});
	var $supply = $resource('/supply/:id', {id:'@id'}, {
		get: { transformResponse : parser },
		save: { method: 'POST', transformResponse : parser },
		remove: { method: 'DELETE', transformResponse : parser }
	});
	
	$scope.init = () => {
		$scope.products = $product.query();
		$scope.supplies = $supply.query();
	};
	
//	$notices.msg({text:'Romero'});

	$product.remove({"id": 41}); // Delete
//	$product.save({"id": 42, "supplies": [{"id":2},{"id":1}]}); // Update
//	$product.save({"name": "Other", "description":"This is my description", "supplies": [1]}); // Create
//	$supply.save({"name":"wood", "stock": 10});
//	$supply.save({"name":"iron", "stock": 12});
}]);
