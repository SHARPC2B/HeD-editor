'use strict';

angular.module('ruleApp.directives', [])
.directive('showData', [ '$compile',  function($compile) {
	return {
		scope : true,
		link : function(scope, elm, attrs) {
			var el;

			attrs.$observe('template', function(tpl) {
				if (angular.isDefined(tpl)) {
					// compile the provided template against the current scope
					el = $compile('<p>' + tpl + '</p>')(scope);

					// stupid way of emptying the element
					elm.html("");

					// add the template content
					elm.append(el);
				}
			});
		}
	};
}])
.directive('updateToolbox', function() {
	return function(scope, element, attrs) {
		scope.$watch('$last', function(v) {
			if (v) {
				Blockly.Toolbox.HtmlDiv.innerHTML = '';
				Blockly.languageTree = document.getElementById('toolbox');;
				Blockly.Toolbox.init();
			}
		});
	};
});