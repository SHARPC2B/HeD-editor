'use strict';

angular.module('ruleApp.controllers', [])
	.controller('HomeCtrl', [ function() {
		
	} ])
	.controller('MyCtrl2', [ function() {
	} ])
	.controller('GraphCtrl', [ '$http', '$scope', function ($http, $scope) {
		$http.get('partials/tree.json').success(function(data) {
			$scope.graph = data;
		});
	}]);