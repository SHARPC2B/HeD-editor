'use strict';

var ruleApp = angular.module('ruleApp', ['ruleApp.controllers', 'ngGrid'])
	.config([ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'partials/home.html',
				controller : 'HomeCtrl'
			});
			$routeProvider.when('/standard/background', {
				templateUrl : 'partials/standard/background.html',
				controller : 'BackgroundCtrl'
			});
			$routeProvider.when('/standard/trigger', {
				templateUrl : 'partials/standard/trigger.html',
				controller : 'TriggerCtrl'
			});
			$routeProvider.when('/graph', {
				templateUrl : 'partials/graph.html',
				controller : 'GraphCtrl'
			});
			$routeProvider.otherwise({
				redirectTo : '/home'
			});
	}]);