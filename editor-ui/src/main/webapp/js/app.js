'use strict';

var ruleApp = angular.module('ruleApp', ['ngRoute', 'ngSanitize', 'ruleApp.services', 'ruleApp.controllers', 'ruleApp.filters', 'ruleApp.directives', 'ngGrid', 'ui.bootstrap', 'ui.tinymce', 'ui.select2'])
	.config([ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'partials/home.html',
				controller : 'HomeCtrl'
			});
			$routeProvider.when('/standard/background', {
				templateUrl : 'partials/standard/background.html',
				controller : 'BackgroundCtrl'
			});
			$routeProvider.when('/standard/expression', {
				templateUrl : 'partials/standard/expression.html',
				controller : 'ExpressionCtrl'
			});
			$routeProvider.when('/standard/trigger', {
				templateUrl : 'partials/standard/trigger.html',
				controller : 'TriggerCtrl'
			});
			$routeProvider.when('/standard/logic', {
				templateUrl : 'partials/standard/logic.html',
				controller : 'LogicCtrl'
			});
			$routeProvider.when('/standard/action', {
				templateUrl : 'partials/standard/action.html',
				controller : 'ActionCtrl'
			});
			$routeProvider.when('/standard/save', {
				templateUrl : 'partials/standard/save.html',
				controller : 'SaveCtrl'
			});
			$routeProvider.when('/technical', {
				templateUrl : 'partials/technical.html',
				controller : 'TechnicalCtrl'
			});
			$routeProvider.otherwise({
				redirectTo : '/home'
			});
	}]);