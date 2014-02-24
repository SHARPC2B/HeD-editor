'use strict';

angular.module('ruleApp.services', []).factory('$escape', function() {
	return {
		xml: function(xml) {
			if (xml)
				return xml.replace(/&/gi, "&amp;").replace(/"/gi, "&quot;").replace(/</gi, "&lt;").replace(/>/gi, "&gt;").replace(/'/gi, "&apos;");
		}
    };
});