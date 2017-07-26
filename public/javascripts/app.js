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
		var keys = Object.keys(dataJson.data || {});
		console.log(keys);
		if (keys.length) {
			var errors = "<br/>";
			keys.forEach(function(key){
				errors += "<b>" + key + "</b>: " + dataJson.data[key].join(", ") + "<br/>";
			});
			dataJson.message.text += errors;
		}
		$notices.msg(dataJson.message);
	}
	return dataJson.data || {};
};

var app = angular.module('fullPlay', ['ngResource']);

app.controller('mainCtl', ['$scope', function($scope){
	$scope.tabs = ['product', 'supply', 'sale'];
	$scope.titles = ['Products', 'Supplies', 'Sales'];
	$scope.tab = 'product';
	$scope.isActive = function(tab) {
		return tab == $scope.tab ? "active" : "";
	}
	$scope.setActive = function(tab) {
		$scope.tab = tab;
	}
	$scope.init = function() {
//		return $scope.controller;
	}
//	$notices.msg({text:'Romero'});

//	$product.remove({"id": 41}); // Delete
//	$product.save({"id": 42, "supplies": [{"id":2},{"id":1}]}); // Update
//	$product.save({"name": "Other", "description":"This is my description", "supplies": [1]}); // Create
//	$supply.save({"name":"wood", "stock": 10});
//	$supply.save({"name":"iron", "stock": 12});
}]);
