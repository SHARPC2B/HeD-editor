'use strict';

angular.module('ruleApp', ['ruleApp.controllers'])
	.config([ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'partials/home.html',
				controller : 'HomeCtrl'
			});
			$routeProvider.when('/standard', {
				templateUrl : 'partials/standard.html',
				controller : 'StandardCtrl'
			});
			$routeProvider.when('/graph', {
				templateUrl : 'partials/graph.html',
				controller : 'GraphCtrl'
			});
			$routeProvider.otherwise({
				redirectTo : '/home'
			});
	}]);