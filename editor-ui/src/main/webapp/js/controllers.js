'use strict';

var serviceUrl = 'http://localhost:9000';

angular.module('ruleApp.controllers', [])

	.controller('HomeCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = '&nbsp;';
		$scope.$parent.menuItems = [
		                            {"text": "Standard Mode", "href": "#/standard/background"},
		                            {"text": "Revise My Rules", "href": "#/revise"},
		                            {"text": "Use Others' Rules", "href": "#/others"},
		                            {"text": "Technical View", "href": "#/technical"},
		                            {"text": "Help", "href": "#/help"}
		                           ];
	}])

	.controller('BackgroundCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = 'Background Information';
		$scope.$parent.menuItems = standardMenuItems(0);
		$scope.selectedTerms = [];
		$scope.selectedCategories = [];
		$http.get('partials/standard/background/iso639-1.json').success(function(data) {
			$scope.isoLangs = data;
			$scope.isoLang = $scope.isoLangs[39];
		});
		$http.get('partials/standard/background/tag-cloud.json').success(function(data) {
			$scope.keyTerms = data;
			$scope.Categories = data;
		});
		$scope.selectTerm = function(term) {
			if ($scope.selectedTerms.indexOf(term) == -1)
				$scope.selectedTerms.push(term);
		};
		$scope.deselectTerm = function(term) {
			$scope.selectedTerms.splice($scope.selectedTerms.indexOf(term), 1);
		};
		$scope.selectCategory = function(category) {
			if ($scope.selectedCategories.indexOf(category) == -1)
				$scope.selectedCategories.push(category);
		};
		$scope.deselectCategory = function(category) {
			$scope.selectedCategories.splice($scope.selectedCategories.indexOf(category), 1);
		};
		$http.get('partials/standard/background/coverage.json').success(function(data) {
			$scope.coverages = data;
		});
		$scope.gridCoverage = {
				data: 'coverages',
				enableRowSelection: false,
				columnDefs: [
				             { field: "Type"},
				             { field: "Code"},
				             { field: "CodeSet"},
				             { field: "Description"},
				             { cellTemplate: '<a href="" ng-click="removeCoverage(row)"><i class="icon-trash"></a>', width: 11}
				             ]
			};
		$scope.saveCoverage = function(coverage) {
			$scope.coverages.push(angular.copy(coverage));
		};
		$scope.removeCoverage = function(row) {
			$scope.coverages.splice(row.rowIndex, 1);
		};
		$http.get('partials/standard/background/contributor.json').success(function(data) {
			$scope.contributors = data;
			$scope.publishers = data;
		});
		$scope.gridContributor = {
				data: 'contributors',
				enableRowSelection: false,
				columnDefs: [
				             { field: "Name"},
				             { field: "Role"},
				             { field: "Type"},
				             { field: "Address"},
				             { field: "Contact"},
				             { cellTemplate: '<a href="" ng-click="removePublisher(row)"><i class="icon-trash"></a>', width: 11}
				             ]
		};
		$scope.saveContributor = function(contributor) {
			$scope.contributors.push(angular.copy(contributor));
		};
		$scope.removePublisher = function(row) {
			$scope.contributors.splice(row.rowIndex, 1);
		};
		$scope.gridPublisher = {
				data: 'publishers',
				enableRowSelection: false,
				columnDefs: [
				             { field: "Name"},
				             { field: "Role"},
				             { field: "Type"},
				             { field: "Address"},
				             { field: "Contact"},
				             { cellTemplate: '<a href="" ng-click="removePublisher(row)"><i class="icon-trash"></a>', width: 11}
				             ]
		};
		$scope.savePublisher = function(publisher) {
			$scope.publishers.push(angular.copy(publisher));
		};
		$scope.removePublisher = function(row) {
			$scope.publishers.splice(row.rowIndex, 1);
		};
		$http.get('partials/standard/background/resource.json').success(function(data) {
			$scope.resources = data;
		});
		$scope.gridResource = {
				data: 'resources',
				enableRowSelection: false,
				columnDefs: [
				             { field: "Title"},
				             { field: "Location"},
				             { field: "Citation"},
				             { field: "Description"},
				             { cellTemplate: '<a href="" ng-click="removeResource(row)"><i class="icon-trash"></a>', width: 11}
				             ]
		};
		$http.get('partials/standard/background/evidence.json').success(function(data) {
			$scope.evidences = data;
		});
		$scope.gridEvidence = {
				data: 'evidences',
				enableRowSelection: false,
				columnDefs: [
				             { field: "Title"},
				             { field: "Quality"},
				             { field: "Strength"},
				             { field: "Location"},
				             { cellTemplate: '<a href="" ng-click="removeEvidence(row)"><i class="icon-trash"></a>', width: 11}
				             ]
		};
		$scope.tinymceOptions = {
				menubar: false,
				plugins: ["image spellchecker emoticons"],
				toolbar: "bold italic underline spellchecker styleselect bullist numlist | undo redo  | image emoticons",
				statusbar: false
		};
	}])


	.controller('DocumentationEditorController', ['$scope', '$modalInstance',  '$http', function($scope, $modalInstance,$http){

		$scope.evidence={};
		$scope.resource={};
		$scope.removeResource = function(row) {
			$scope.resources.splice(row.rowIndex, 1);
		};

		$scope.removeEvidence = function(row) {
			$scope.evidences.splice(row.rowIndex, 1);
		};

		$scope.saveResource = function() {
			$scope.resources.push($scope.resource);
			$scope.resource={};
		};

		$scope.saveEvidence = function() {
			$scope.evidences.push($scope.evidence);
			$scope.evidence={};
		};


		$http.get('partials/standard/background/resource.json').success(function(data) {
			$scope.resources = data;
		});


		$scope.gridResource = {
			data: 'resources',
			enableRowSelection: false,
			columnDefs: [
				{ field: "Title"},
				{ field: "Location"},
				{ field: "Citation"},
				{ field: "Description"},
				{ cellTemplate: '<a href="" ng-click="removeResource(row)"><i class="icon-trash"></a>', width: 11}
			]
		};
		$http.get('partials/standard/background/evidence.json').success(function(data) {
			$scope.evidences = data;
		});
		$scope.gridEvidence = {
			data: 'evidences',
			enableRowSelection: false,
			columnDefs: [
				{ field: "Title"},
				{ field: "Quality"},
				{ field: "Strength"},
				{ field: "Location"},
				{ cellTemplate: '<a href="" ng-click="removeEvidence(row)"><i class="icon-trash"></a>', width: 11}
			]
		};
		$scope.submit = function(){
			$modalInstance.close('ok');
		};
		$scope.cancel = function() {
			$modalInstance.dismiss('cancel');
		};
	}])

	.controller('ExpressionCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = 'Create Expressions';
		$scope.$parent.menuItems = standardMenuItems(1);
        $http.get(serviceUrl + '/expressions/list').success(function(data) {
            $scope.expressions = data;
        });
		$scope.currentExpression = {};
		$scope.currentExpression.name = '';
		$scope.loadPalette = function() {
			Blockly.inject(document.getElementById('blocklyDiv'),
					{path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
		};
		$scope.save = function() {
			var expressionIndex = null;
			$scope.currentExpression.xml = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace));
			for (var int = 0; int < $scope.expressions.length; int++)
				if ($scope.expressions[int].name == $scope.currentExpression.name)
					expressionIndex = int;
			if (expressionIndex === null)
				$scope.expressions.push(angular.copy($scope.currentExpression));
			else
				if (confirm("Want to override the already stored expression?"))
					$scope.expressions[expressionIndex] = angular.copy($scope.currentExpression);
		};
		$scope.open = function(expression) {
			if (confirm("Want to clean current expression?")) {
				$scope.currentExpression = angular.copy(expression);
				Blockly.mainWorkspace.clear();
				Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.currentExpression.xml));
			}
		};
		$scope.clear = function() {
			if (confirm("Want to clean current expression?")) {
				$scope.currentExpression.name = '';
				Blockly.mainWorkspace.clear();
			}
		};
	}])

	.controller('TriggerCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = 'Decide how the Rule will be Triggered';
		$scope.$parent.menuItems = standardMenuItems(2);
	}])

	.controller('LogicCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
		$scope.$parent.title = 'Define Rule Logic';
		$scope.$parent.menuItems = standardMenuItems(3);
        $http.get(serviceUrl + '/expressions/list').success(function(data) {
            $scope.expressions = data;
        });
        $scope.gridOptions = {
				data: 'primitives',
				multiSelect: false,
				filterOptions: {filterText: '', useExternalFilter: false},
				columnDefs: [{ field: 'name', displayName: 'Generic Clause', width: "45%" },
				             { field: 'example', displayName: 'Example' }],
				rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-click="openClauseEditor(row.entity)">' +
								'<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>' +
								'<div ng-cell></div>' +
							 '</div>'
		};
		$scope.openClauseEditor = function(clause) {
			 var d = $modal.open({
				 templateUrl: 'partials/standard/logic/primitive-editor.html',
				 controller: 'EditPrimitiveController',
				 resolve : {
					 clause : function() {
						 return angular.copy(clause);
					 }
				 }
			 });
		};
		Blockly.Blocks.logic_compare = {
				// Group of clauses.
				init : function() {
					this.setColour(210);
					this.appendDummyInput()
						.appendTitle("               ")
						.appendTitle(new Blockly.FieldTextInput(""), "NAME");
					this.appendValueInput("CLAUSE0")
						.setCheck("Boolean")
                        .appendTitle(new Blockly.FieldDropdown([["All must be true", "ALL"], ["One+ must be true", "ONEPLUS"], ["None must be true", "NONE"], ["One must be true", "ONE"]]), "dropdown");
					this.appendValueInput('CLAUSE1').setCheck(
					'Boolean');
					this.setOutput(true, 'Boolean');
					this.setMutator(new Blockly.Mutator(
							[ 'clause_group_item' ]));
					this
					.setTooltip('Allows more than one clause to be validated.');
					this.typeCount_ = 2;
				},
				mutationToDom : function(workspace) {
					var container = document
					.createElement('mutation');
					container.setAttribute('types',
							this.typeCount_);
					return container;
				},
				domToMutation : function(container) {
					for ( var x = 0; x < this.typeCount_; x++) {
						this.removeInput('CLAUSE' + x);
					}
					this.typeCount_ = window.parseInt(container
							.getAttribute('types'), 10);
					for ( var x = 0; x < this.typeCount_; x++) {
						var input = this.appendValueInput(
								'CLAUSE' + x).setCheck('Boolean');
						if (x == 0) {
                            input.appendTitle(new Blockly.FieldDropdown([["All must be true", "ALL"], ["One+ must be true", "ONEPLUS"], ["None must be true", "NONE"], ["One must be true", "ONE"]]), "dropdown");
						}
					}
				},
				decompose : function(workspace) {
					var containerBlock = new Blockly.Block(
							workspace, 'clause_group_container');
					containerBlock.initSvg();
					var connection = containerBlock
					.getInput('STACK').connection;
					for ( var x = 0; x < this.typeCount_; x++) {
						var typeBlock = new Blockly.Block(
								workspace, 'clause_group_item');
						typeBlock.initSvg();
						connection
						.connect(typeBlock.previousConnection);
						connection = typeBlock.nextConnection;
					}
					return containerBlock;
				},
				compose : function(containerBlock) {
					// Disconnect all input blocks and remove
					// all inputs.
					for ( var x = this.typeCount_ - 1; x >= 0; x--) {
						this.removeInput('CLAUSE' + x);
					}
					this.typeCount_ = 0;
					// Rebuild the block's inputs.
					var typeBlock = containerBlock
					.getInputTargetBlock('STACK');
					while (typeBlock) {
						var input = this.appendValueInput(
								'CLAUSE' + this.typeCount_)
								.setCheck('Boolean');
						if (this.typeCount_ == 0) {
							input.appendTitle(new Blockly.FieldDropdown([["All must be true", "ALL"], ["One+ must be true", "ONEPLUS"], ["None must be true", "NONE"], ["One must be true", "ONE"]]), "dropdown");
						}
						// Reconnect any child blocks.
						if (typeBlock.valueConnection_) {
							input.connection
							.connect(typeBlock.valueConnection_);
						}
						this.typeCount_++;
						typeBlock = typeBlock.nextConnection
						&& typeBlock.nextConnection
						.targetBlock();
					}
				},
				saveConnections : function(containerBlock) {
					// Store a pointer to any connected child
					// blocks.
					var typeBlock = containerBlock
					.getInputTargetBlock('STACK');
					var x = 0;
					while (typeBlock) {
						var input = this.getInput('CLAUSE' + x);
						typeBlock.valueConnection_ = input
						&& input.connection.targetConnection;
						x++;
						typeBlock = typeBlock.nextConnection
						&& typeBlock.nextConnection
						.targetBlock();
					}
				}
		};
		Blockly.Blocks.clause_group_container = {
				// Container.
				init: function() {
					this.setColour(210);
					this.appendDummyInput()
					.appendTitle('add clauses');
					this.appendStatementInput('STACK');
					this.setTooltip('Add, or remove clauses.');
					this.contextMenu = false;
				}
		};
		Blockly.Blocks.clause_group_item = {
				// Add type.
				init: function() {
					this.setColour(210);
					this.appendDummyInput()
					.appendTitle('Clause');
					this.setPreviousStatement(true);
					this.setNextStatement(true);
					this.setTooltip('Add a new clause.');
					this.contextMenu = false;
				}
		};
	    Blockly.Blocks.logic_boolean = {
	    		helpUrl: 'http://www.example.com/',
	    		init: function() {
	    			this.setColour(160);
	    			this.appendDummyInput()
	    			.appendTitle(new Blockly.FieldDropdown( availableExpressions( $http ) ), "Clauses");
	    			this.setOutput(true, "Boolean");
	    			this.setTooltip('');
	    		}
	    };
	    Blockly.Blocks.logic_negate = {
	    		helpUrl: 'http://www.example.com/',
	    		init: function() {
	    			this.setColour(210);
	    			this.appendValueInput( 'NOT' )
                        .appendTitle( "Not" )
                        .setAlign( Blockly.ALIGN_RIGHT );
	    			this.setOutput(true, "Boolean");
	    			this.setTooltip('');
	    		}
	    };
	    Blockly.Blocks.logic_root = {
	    		helpUrl: 'http://www.example.com/',
	    		init: function() {
	    			this.setColour(10);
	    			this.appendValueInput( 'ROOT' )
                        .appendTitle( "Conditions" )
                        .setAlign( Blockly.ALIGN_RIGHT );
	    			this.setTooltip('');
	    		}
	    };
	    Blockly.HSV_SATURATION = 0.35;
		Blockly.HSV_VALUE=0.85;
	    Blockly.inject(document.getElementById('blocklyDiv'),
	            {path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
	}])

	.controller('ActionCtrl', [ '$http', '$scope', '$modal','$log', function($http, $scope, $modal,$log) {
		$scope.$parent.title = 'Choose Actions to be Executed';
		$scope.$parent.menuItems = standardMenuItems(4);
		$http.get(serviceUrl + '/template/list').success(function(data) {
			$scope.actions = data;
		});



		$scope.openClauseEditor = function(clause) {
			 var d = $modal.open({
				 templateUrl: 'partials/standard/logic/primitive-editor.html',
				 controller: 'EditPrimitiveController',
				 resolve : {
					 clause : function() {
						 return angular.copy(clause);
					 }
				 }
			 });
		};
		Blockly.Blocks.action_group = {
				  helpUrl: 'http://www.example.com/',
				  init: function() {
				    this.setColour(20);
				    this.appendDummyInput()
				        .appendTitle(new Blockly.FieldDropdown([["Perform all", "all"], ["Perform none", "none"], ["Perform any", "any"], ["Perform exactly one", "one"]]), "NAME");
                      this.appendValueInput("Documentation")
                          .setCheck("Documentation")
                          .appendTitle("Documentation");
                      this.appendValueInput("Condition")
                          .setCheck("Condition")
                          .appendTitle("Condition");
                      this.appendStatementInput( ["NestedAction"] )
                          .setCheck( ["AtomicAction", "ActionGroup" ] );
                      this.setPreviousStatement(true, "ActionGroup");
                      this.setNextStatement(true, "ActionGroup");
				    this.setTooltip('');
				  }
		};
		Blockly.Blocks.logic_ternary = {
				  helpUrl: 'http://www.example.com/',
				  init: function() {
				    this.setColour(120);
				    this.appendValueInput("Documentation")
				        .setCheck("Documentation")
				        .appendTitle("Documentation");
				    this.appendValueInput("Condition")
				        .setCheck("Boolean")
				        .appendTitle("Condition");
				    this.appendValueInput("ActionSentence")
				        .setCheck("ActionSentence")
				        .appendTitle("Action Sentence");
				    this.setPreviousStatement(true);
				    this.setNextStatement(true);
				    this.setTooltip('');
				  }
		};
		Blockly.Blocks.action_documentation = {
				init : function() {
					this.setColour(160);
					this.appendDummyInput().appendTitle(new Blockly.FieldTextInput("Documentation"), "Doc");
					this.setOutput(true, "Documentation");
					this.setTooltip('');
				},
				customContextMenu : function(options) {
					var option = {
							enabled : true,
							text : "Define Documentation..."
					};
					option.callback = function() {
						var d = $modal.open({
							templateUrl : 'partials/standard/documentation-editor.html',
							controller : 'DocumentationEditorController'
						});
						if (!$scope.$$phase)
							$scope.$apply();
					};
					options.push(option);
				}
		};
        Blockly.Blocks.action_boolean = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(160);
                this.appendDummyInput()
                    .appendTitle(new Blockly.FieldDropdown( availableExpressions( $http ) ), "Clauses");
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
		Blockly.Blocks.text = {
				init: function() {
					this.setColour(290);
				    this.appendDummyInput()
				        .appendTitle(new Blockly.FieldTextInput("Action Sentence"), "AS");
				    this.setOutput(true, "ActionSentence");
				    this.setTooltip('');
				  }
		};
		Blockly.HSV_SATURATION = 0.35;
		Blockly.HSV_VALUE=0.85;
	    Blockly.inject(document.getElementById('blocklyDiv'),
	            {path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
	}])

	.controller('SaveCtrl', [ '$scope', '$http', function($scope, $http) {
		$scope.$parent.title = 'Guided Mode - Step 5: Review, Save, and Publish your Finished Rule';
		$scope.$parent.menuItems = standardMenuItems(5);
	}])

	.controller('TechnicalCtrl', [ '$http', '$scope', function ($http, $scope) {
		$scope.color = d3.scale.category20();
		var force = d3.layout.force()
			.charge(-300)
			.linkDistance(200)
			.size([800, 800]);
		var svg = d3.select("#force");
		d3.json("newmentor.obj.json", function(json) {
			var nodes = {};
			json.vertices.forEach(function(vertice) {
				nodes[vertice._id] = vertice;
			});
			json.edges.forEach(function(d) {
				d.source = nodes[d._outV];
				d.target = nodes[d._inV];
			});
			force.nodes(d3.values(nodes))
				.links(json.edges)
				.start();
			var paths = svg.append("g").selectAll("path")
				.data(force.links())
				.enter().append("svg:path")
				.attr("stroke", "#e5e5e5")
				.attr("id", function(d) { return d._id; });
			var labels = svg.selectAll("text")
				.data(force.links())
				.enter().append("svg:text")
				.attr("font-size", 11)
				.attr("text-anchor","middle")
				.append("svg:textPath")
				.attr("xlink:href", function(d) { return "#"+d._id; })
				.attr("startOffset", "50%")
				.text(function(d){ return d._label; });
			var circles = svg.selectAll("circle")
				.data(force.nodes())
				.enter().append("circle")
				.attr("r", 9)
				.on("click", function(d) {
					$scope.$apply(function () {
						$scope.root = d;
					});
				})
				.style("fill", function(d) { return $scope.color(d.isA); })
				.call(force.drag);
			force.on("tick", function() {
				circles.attr("cx", function(d) { return d.x; })
					.attr("cy", function(d) { return d.y; });
				paths.attr("d", function(d) { return "M" + d.source.x + "," + d.source.y + "L" + d.target.x + "," + d.target.y; });
				labels.attr("xlink:href", function(d) { return "#"+d._id; });
			});
		});
	}])

	.controller('EditPrimitiveController', ['$scope', '$modalInstance', 'clause', '$http', function($scope, $modalInstance, clause, $http){
		$scope.clause = clause;
		$scope.submit = function(){
			$modalInstance.close('ok');
		};
		$http.get(serviceUrl + '/template/' + clause.id).success(function(data) {
			$scope.detail = data;
		});
		$scope.cts2search = function(matchValue) {
			return $http.jsonp("http://sharpc2b.ranker.cloudbees.net/fwd/cts2/valueset/RXNORM/resolution?callback=JSON_CALLBACK&format=json&maxtoreturn=10&matchvalue="+matchValue).then(function(response){
				return response.data.iteratableResolvedValueSet.entryList;
		    });
		};
		$scope.verify = function(data) {
			$http.put(serviceUrl + '/template/inst', data).success(function(response) {
				$scope.detail = response;
			});
		};
		$scope.cancel = function() {
			$modalInstance.dismiss('cancel');
		};
	}]);

function standardMenuItems(position) {
	var menuItems = [{"text": "Background Information", "href": "#/standard/background"},
	                 {"text": "Create Expressions", "href": "#/standard/expression"},
	                 {"text": "Select Trigger", "href": "#/standard/trigger"},
	                 {"text": "Define Logic", "href": "#/standard/logic"},
	                 {"text": "Choose Action", "href": "#/standard/action"},
	                 {"text": "Review & Export", "href": "#/standard/save"}];
	menuItems[position].status = "disabled";
	return menuItems;
};

function availableExpressions( httpContext ) {
    var map = [ ["(Choose Expression...)", "" ] ];
    httpContext.get(serviceUrl + '/expressions/list').success(function(data) {
        var expressions = data;
        for ( var j = 0; j < expressions.length; j++ ) {
            var expr = expressions[ j ];
            map[ j ] = [ expr.name , expr.expressionIRI ];
        }
    });
    return map;
}