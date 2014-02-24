'use strict';

angular.module('ruleApp.filters', []).filter('prettify', ['$escape', function($escape) {
	return function(code) {
		return prettyPrintOne($escape.xml(code));
	};
}]);