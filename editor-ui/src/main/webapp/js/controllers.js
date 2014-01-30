'use strict';

var serviceUrl = 'http://localhost:9000';


angular.module('ruleApp.controllers', [])

    .controller('HomeCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
        updateTitle( $scope );

        $scope.$parent.menuItems = [
            {"text": "Standard Mode", "href": "#/standard/background"},
            {"text": "Technical View", "href": "#/technical"}
        ];

        $scope.openModal = function(partial) {
            $modal.open({
                templateUrl: 'partials/home/' + partial.toLowerCase() +'.html',
                controller: 'Home' + partial + 'Ctrl',
                scope : $scope,
                resolve: {
                    items: function() {
                        return $scope.$parent.title;
                    }
                }
            });
        };
        $scope.createNew = function() {
            if ( ! $scope.hasOwnProperty( 'currentRuleId' ) || confirm( "Create New Artifact and Discard existing one?" ) ) {
                $http({
                    method : 'POST',
                    url : serviceUrl + '/store',
                    data : [],
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {
                        $scope.currentRuleId = data.ruleId;
                        updateTitle( $scope );
                    });
            }
        };
        $scope.snapshot = function() {
            if ( $scope.hasOwnProperty( 'currentRuleId' ) ) {
                $http({
                    method : 'POST',
                    url : serviceUrl + '/store/snapshots/' + $scope.currentRuleId,
                    data : [],
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {
                        alert( 'Took snapshot of ' + $scope.currentRuleId );
                    });
            }
        };
        $scope.save = function() {
            if ( $scope.hasOwnProperty( 'currentRuleId' ) ) {
                $http({
                    method : 'PUT',
                    url : serviceUrl + '/store/' + $scope.currentRuleId,
                    data : [],
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {
                        alert( 'Saved new version of ' + $scope.currentRuleId );
                    });
            }
        };
        $scope.close = function() {
            if ( $scope.hasOwnProperty( 'currentRuleId' ) ) {
                $http({
                    method : 'DELETE',
                    url : serviceUrl + '/store',
                    data : [],
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {
                        alert( 'Closed ' + $scope.currentRuleId );
                        delete $scope.currentRuleId;
                        updateTitle( $scope );
                    });
            }
        };
        $scope.delete = function() {
            if ( $scope.hasOwnProperty( 'currentRuleId' ) && confirm( "Delete Current Artifact : Are you sure?" ) ) {
                $http({
                    method : 'DELETE',
                    url : serviceUrl + '/store/' + $scope.currentRuleId,
                    data : [],
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {
                        alert( 'Deleted ' + $scope.currentRuleId );
                        delete $scope.currentRuleId;
                        updateTitle( $scope );
                    });
            }
        };
    }])

    .controller('BackgroundCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
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
                { cellTemplate: '<a href="" ng-click="removeCoverage(row)"><i class="icon-trash"></i></a><a href="" ng-click="openRowDetail(row)"><i class="icon-zoom-in"></i></a>', width: 24}
            ]
        };
        $scope.addCoverage = function(coverage) {
            $scope.coverages.push(angular.copy(coverage));
        };
        $scope.removeCoverage = function(row) {
            $scope.coverages.splice(row.rowIndex, 1);
        };
        $http.get('partials/standard/background/contributor.json').success(function(data) {
            $scope.contributors = data;
            $scope.publishers = angular.copy(data);
        });
        $scope.gridContributor = {
            data: 'contributors',
            enableRowSelection: false,
            columnDefs: [
                { field: "Name"},
                { field: "Role"},
                { field: "Type"},
                { cellTemplate: '<a href="" ng-click="removeContributor(row)"><i class="icon-trash"></i></a><a href="" ng-click="openRowDetail(row)"><i class="icon-zoom-in"></i></a>', width: 24}
            ]
        };
        $scope.addContributor = function(contributor) {
            $scope.contributors.push(angular.copy(contributor));
        };
        $scope.removeContributor = function(row) {
            $scope.contributors.splice(row.rowIndex, 1);
        };
        $scope.gridPublisher = {
            data: 'publishers',
            enableRowSelection: false,
            columnDefs: [
                { field: "Name"},
                { field: "Role"},
                { field: "Type"},
                { cellTemplate: '<a href="" ng-click="removePublisher(row)"><i class="icon-trash"></a>', width: 11}
            ]
        };
        $scope.addPublisher = function(publisher) {
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
        $scope.rights = [{"Name":"Mayo Clinic",
            "Type":"Company",
            "Permissions":"Author"
        }];
        $scope.gridRight = {
            data: 'rights',
            enableRowSelection: false,
            columnDefs: [
                { field: "Name"},
                { field: "Type"},
                { field: "Permissions"},
                { cellTemplate: '<a href="" ng-click="removePublisher(row)"><i class="icon-trash"></a>', width: 11}
            ]
        };
        $scope.openRowDetail = function(row) {
            $modal.open({
                templateUrl: 'partials/standard/background/table-detail.html',
                controller: 'RowDetailController',
                resolve : {
                    data : function() {
                        return row.entity;
                    }
                }
            });
        };
    }])
    .controller('RowDetailController', ['$scope', '$http', '$modalInstance', 'data', function($scope, $http, $modalInstance, data) {
        $scope.data = data;
        $scope.close = function() {
            $modalInstance.dismiss();
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

    .controller('ExpressionCtrl', [ '$http', '$scope', '$sce', function($http, $scope, $sce) {
        $scope.$parent.title = 'Create Expressions';
        $scope.$parent.menuItems = standardMenuItems(1);
        $http.get(serviceUrl + '/rule/expressions/list').success(function(data) {
            $scope.expressions = data;
        });
        $scope.expressions = [];
        $scope.currentExpression = {};

        $scope.loadPalette = function() {
            Blockly.inject(document.getElementById('blocklyDiv'),
                {path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
            var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'logic_root');
            rootBlock.initSvg();
            rootBlock.render();
            rootBlock.setMovable(false);
            rootBlock.setDeletable(false);
        };
        $scope.save = function() {
            var expressionIndex = null;
            $scope.currentExpression.xml = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace));
            var  xmlDoc=new DOMParser().parseFromString($scope.currentExpression.xml,"text/xml");

            var blocks=xmlDoc.getElementsByTagName('block');
            angular.forEach(blocks, function(block) {
                if (block.getAttribute('type') == 'logic_root')
                    $scope.currentExpression.name = block.getElementsByTagName("field")[0].childNodes[0].nodeValue;
            });

            for (var int = 0; int < $scope.expressions.length; int++) {
                if ($scope.expressions[int].name == $scope.currentExpression.name) {
                    expressionIndex = int;
                }
            }
            if (expressionIndex === null) {
                $scope.currentExpression.expressionIRI = 'TODO' + $scope.currentExpression.name;
                //$scope.expressions.push(angular.copy($scope.currentExpression));
            } else {
                if (confirm("Want to override the already stored expression?")) {
                    $scope.expressions[expressionIndex] = angular.copy($scope.currentExpression);
                }
            }
            if (Blockly.mainWorkspace.getTopBlocks().length > 1)
                alert('This expression has more than one top level');

            $http({
                method : 'POST',
                url : serviceUrl + '/rule/expressions/' + $scope.currentExpression.expressionIRI + '/' + $scope.currentExpression.name,
                data : $scope.currentExpression.xml,
                headers : { 'Content-Type':'application/html' }
            }).success( function( data ) {
                    console.log(data);
                    if (expressionIndex === null) {
                        $scope.expressions.push(angular.copy($scope.currentExpression));
                    } else {
                        if (confirm("Want to override the already stored expression?")) {
                            $scope.expressions[expressionIndex] = angular.copy($scope.currentExpression);
                        }
                    }
                });
        };

        $scope.open = function(expression) {
            if (confirm("Clean current expression?")) {
                $http({
                    method : 'GET',
                    url : serviceUrl + '/rule/expressions/' + expression.expressionIRI,
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {
                        $scope.currentExpression.expressionIRI = expression.expressionIRI;
                        $scope.currentExpression.expressionName = expression.expressionName;

                        $scope.currentExpression.xml = data;
                        Blockly.mainWorkspace.clear();
                        Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.currentExpression.xml));
                    });

            }
        };
        $scope.delete = function(expression) {
            if (confirm("Delete current expression?")) {
                $http({
                    method : 'DELETE',
                    url : serviceUrl + '/rule/expressions/' + expression.expressionIRI,
                    headers : { 'Content-Type':'application/html' }
                }).success( function( data ) {

                        $scope.expressions = data;

                        Blockly.mainWorkspace.clear();
                        var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'logic_root');
                        rootBlock.initSvg();
                        rootBlock.render();
                        rootBlock.setMovable(false);
                        rootBlock.setDeletable(false);
                    });

            }
        };
        $scope.clone = function(expression) {
            $http({
                method : 'POST',
                url : serviceUrl + '/rule/expressions/' + expression.expressionIRI,
                headers : { 'Content-Type':'application/html' }
            }).success( function( data ) {
                    $scope.currentExpression = angular.copy($scope.currentExpression);
                    $scope.currentExpression.expressionIRI = data;
                    $scope.expressions.push( $scope.currentExpression );
                    Blockly.mainWorkspace.clear();
                    Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.currentExpression.xml));
                });
        };


        $scope.clear = function() {
            if (confirm("Want to clean current expression?")) {
                $scope.currentExpression.name = '';
                Blockly.mainWorkspace.clear();
                var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'logic_root');
                rootBlock.initSvg();
                rootBlock.render();
                rootBlock.setMovable(false);
                rootBlock.setDeletable(false);
            }
        };
        $scope.domainClasses = [];
        $scope.addDomainClass = function(domainClass) {
            $scope.domainClasses.push(domainClass);
            Blockly.Blocks[domainClass] = {
                init: function() {
                    this.setColour(40);
                    this.appendDummyInput().appendField(domainClass);
                    this.setOutput(true, ['http://asu.edu/sharpc2b/ops#DomainClass']);
                }
            };
        };

        $scope.select2DomainClasses = {
            data: [],
            multiple: true,
            simple_tags: true
        };
        $scope.select2Options = {
            data: { results: [], text: 'name' },
            id: function(object) {
                return object.name;
            },
            formatSelection: function(item) { return item.name; },
            formatResult: function(item) { return item.name; }
        };

        Blockly.Blocks['http://asu.edu/sharpc2b/ops#VariableExpression'] = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(160);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Choose Expression...)" ) ), "Variables");
                this.setOutput(true, null);
                this.setTooltip('');
            }
        };
        Blockly.Blocks['http://asu.edu/sharpc2b/ops#DomainPropertyExpression'] = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(40);
                this.appendValueInput()
                    .appendField(new Blockly.FieldDropdown( availableProperties( $http, $scope ) ), "DomainProperty")
                    .setCheck('http://asu.edu/sharpc2b/ops#DomainProperty');
                this.setOutput(true, ['http://asu.edu/sharpc2b/ops#DomainProperty']);
                this.setTooltip('');
            }
        };
        Blockly.Blocks['logic_root'] = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setHelpUrl('http://www.example.com/');
                this.appendValueInput("NAME")
                    .appendField(new Blockly.FieldTextInput("Name"), "NAME");
                this.setColour(46);
                this.setTooltip('');
            }
        };


        var radius = 566,
            x = d3.scale.linear().range([0, radius]),
            y = d3.scale.linear().range([0, radius]),
            node,
            root;
        var pack = d3.layout.pack()
            .size([radius, radius])
            .value(function(d) { return d.size; });
        var vis = d3.select("#packLayout").insert("svg:svg")
            .attr("width", radius)
            .attr("height", radius)
            .append("svg:g");
        d3.json("VMR.json", function(data) {
            node = root = data;

            var nodes = pack.nodes(root);
            angular.extend($scope.select2Options.data.results, nodes);



            vis.selectAll("circle")
                .data(nodes)
                .enter().append("svg:circle")
                .attr("class", function(d) { return d.children ? "parent" : "child"; })
                .attr("cx", function(d) { return d.x; })
                .attr("cy", function(d) { return d.y; })
                .attr("r", function(d) { return d.r; })
                .on("click", function(d) { return $scope.zoom(node == d ? root : d, d3.event); });

            vis.selectAll("text")
                .data(nodes)
                .enter().append("svg:text")
                .attr("class", function(d) { return d.children ? "parent" : "child"; })
                .attr("x", function(d) { return d.x; })
                .attr("y", function(d) { return d.y; })
                .attr("dy", ".35em")
                .attr("text-anchor", "middle")
                .style("opacity", function(d) { return d.r > 20 ? 1 : 0; })
                .text(function(d) {
                    if (d.children) {
                        if (d.children.length > 1)
                            return d.name;
                    } else
                        return d.name;
                })
                .on("click", function(d) { d.children ? $scope.zoom(d) : $scope.$apply($scope.addDomainClass(d.name)); });
        });

        $scope.zoom = function(d) {
            if (d) {
                if (typeof d === 'object') {
                    var k = radius / d.r / 2;
                    x.domain([d.x - d.r, d.x + d.r]);
                    y.domain([d.y - d.r, d.y + d.r]);

                    var t = vis.transition().duration(750);


                    t.selectAll("circle")
                        .attr("cx", function(d) { return x(d.x); })
                        .attr("cy", function(d) { return y(d.y); })
                        .attr("r", function(d) { return k * d.r; });

                    t.selectAll("text")
                        .attr("x", function(d) { return x(d.x); })
                        .attr("y", function(d) { return y(d.y); })
                        .style("opacity", function(d) { return k * d.r > 20 ? 1 : 0; });

                    node = d;
                }
            }
        };
    }])

    .controller('TriggerCtrl', [ '$http', '$scope', function($http, $scope) {
        $scope.$parent.title = 'Decide how the Rule will be Triggered';
        $scope.$parent.menuItems = standardMenuItems(2);
        Blockly.inject(document.getElementById('blocklyDiv'), {path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
    }])

    .controller('LogicCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
        $scope.$parent.title = 'Define Rule Logic';
        $scope.$parent.menuItems = standardMenuItems(3);
        $http.get(serviceUrl + '/template/list/Condition').success(function(data) {
            $scope.primitives = data;
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
                    .appendField("               ")
                    .appendField(new Blockly.FieldTextInput(""), "NAME");
                this.appendValueInput("CLAUSE0")
                    .setCheck("Boolean")
                    .appendField(new Blockly.FieldDropdown([["All must be true", "ALL"], ["One+ must be true", "ONEPLUS"], ["None must be true", "NONE"], ["One must be true", "ONE"]]), "dropdown");
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
                        input.appendField(new Blockly.FieldDropdown([["All must be true", "ALL"], ["One+ must be true", "ONEPLUS"], ["None must be true", "NONE"], ["One must be true", "ONE"]]), "dropdown");
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
                        input.appendField(new Blockly.FieldDropdown([["All must be true", "ALL"], ["One+ must be true", "ONEPLUS"], ["None must be true", "NONE"], ["One must be true", "ONE"]]), "dropdown");
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
                    .appendField('add clauses');
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
                    .appendField('Clause');
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
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Condition Expression...)" ) ), "Clauses");
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_negate = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(210);
                this.appendValueInput( 'NOT' )
                    .appendField( "Not" )
                    .setAlign( Blockly.ALIGN_RIGHT );
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_exists = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(210);
                this.appendValueInput( 'EXISTS' )
                    .appendField( "Exists" )
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
                    .appendField( "Conditions" )
                    .setAlign( Blockly.ALIGN_RIGHT );
                this.setTooltip('');
            }
        };
        Blockly.HSV_SATURATION = 0.66;
        Blockly.HSV_VALUE = 0.71;
        Blockly.inject(document.getElementById('blocklyDiv'), {
        	path: './lib/blockly/',
        	toolbox: document.getElementById('toolbox')
        });
        var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'logic_root');
        rootBlock.initSvg();
        rootBlock.render();
        rootBlock.setMovable(false);
        rootBlock.setDeletable(false);

    }])

    .controller('ActionCtrl', [ '$http', '$scope', '$modal','$log', function($http, $scope, $modal,$log) {
        $scope.$parent.title = 'Choose Actions to be Executed';
        $scope.$parent.menuItems = standardMenuItems(4);
        $http.get(serviceUrl + '/template/list/Action').success(function(data) {
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
                    .appendField(new Blockly.FieldDropdown([["Perform all", "all"], ["Perform none", "none"], ["Perform any", "any"], ["Perform exactly one", "one"]]), "NAME");
                this.appendValueInput("Documentation")
                    .setCheck("Documentation")
                    .appendField("Documentation");
                this.appendValueInput("Condition")
                    .setCheck("Boolean")
                    .appendField("Condition");
                this.appendStatementInput( ["NestedAction"] )
                    .setCheck( ["AtomicAction", "ActionGroup" ] );
                this.setOutput(true, "ActionGroup");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.atomic_action = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(99);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown([["Create", "CreateAction"], ["Update", "UpdateAction"], ["Remove", "RemoveAction"], ["Fire Event", "FireEventAction"], ["Declare", "DeclareResponseAction"], ["Collect", "CollectInformationAction"]]), "NAME");
                this.appendValueInput("Documentation")
                    .setCheck("Documentation")
                    .appendField("Documentation");
                this.appendValueInput("Condition")
                    .setCheck("Boolean")
                    .appendField("Condition");
                this.appendValueInput("ActionSentence")
                    .setCheck("ActionSentence")
                    .appendField("Action Sentence");
                this.setPreviousStatement(true, ["ActionGroup","AtomicAction"]);
                this.setNextStatement(true, ["ActionGroup","AtomicAction"]);
                this.setTooltip('');
            }
        };

        Blockly.Blocks.action_documentation = {
            init : function() {
                this.setColour(160);
                this.appendDummyInput().appendField(new Blockly.FieldTextInput("Documentation"), "Doc");
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
        Blockly.Blocks.action_condition = {
            helpUrl: 'http://www.example.com/',
            init: function() {
                this.setColour(160);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Condition Expression...)" ) ), "Clauses");
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.action_sentence = {
            init: function() {
                this.setColour(290);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Sentence Expression...)" ) ), "AS");
                this.setOutput(true, "ActionSentence");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_root = {
                init: function() {
                    this.setColour(10);
                    this.appendValueInput("ROOT")
                    	.setCheck("ActionGroup")
                    	.appendField("Action");
                    this.setTooltip('');
                }
        };
        Blockly.HSV_SATURATION = 0.35;
        Blockly.HSV_VALUE=0.85;
        Blockly.inject(document.getElementById('blocklyDiv'),
            {path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
        var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'logic_root');
        rootBlock.initSvg();
        rootBlock.render();
        rootBlock.setMovable(false);
        rootBlock.setDeletable(false);
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

    .controller('EditPrimitiveController', ['$scope', '$modalInstance', 'clause', '$http', '$modal', function($scope, $modalInstance, clause, $http, $modal){
        $scope.clause = clause;
        $scope.submit = function(data) {

            $http({
                method : 'POST',
                url : serviceUrl + '/primitive/create/' + data.key,
                data : $scope.expressionName

            }).success( function( answer ) {
                    $modalInstance.dismiss('ok');
                });

        };
        $http.get(serviceUrl + '/template/' + clause.key).success(function(data) {
            $scope.detail = data;
            $scope.template = clause.name;
            angular.forEach(data.parameters, function(parameter, key) {
                $scope.template = $scope.template.replace(parameter.name, '<a ng-click="openOther(detail.parameters[' + key + '])">' + parameter.name + '</a>');
            });
        });
        $scope.cts2search = function(matchValue) {
            return $http.jsonp( serviceUrl + "/fwd/cts2/codesystem/RXNORM/version/RXNORM-LATEST/entities?callback=JSON_CALLBACK&format=json&maxtoreturn=10&matchvalue="+matchValue).then(function(response) {
                return response.data.entityDirectory.entryList;
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
        $scope.openOther = function(parameter) {
            var d = $modal.open({
                templateUrl: 'partials/standard/logic/primitive-form.html',
                controller: 'ParameterController',
                resolve : {
                    parameter : function() {
                        return angular.copy(parameter);
                    }
                }
            });
        };

    }])

    .controller('ParameterController', ['$scope', '$modalInstance', 'parameter', '$http', '$modal', function($scope, $modalInstance, parameter, $http, $modal){
        $scope.parameter = parameter;
        $scope.cts2search = function(matchValue) {
            return $http.jsonp(serviceUrl + "/fwd/cts2/codesystem/RXNORM/version/RXNORM-LATEST/entities?callback=JSON_CALLBACK&format=json&maxtoreturn=10&matchvalue="+matchValue).then(function(response){
                return response.data.entityDirectory.entryList;
            });
        };
        $scope.save = function(detail) {
            alert(detail);
            $modalInstance.dismiss('cancel');
        };
        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

    }])

    .controller('HomeImportCtrl', ['$scope', '$http', '$modalInstance', function($scope, $http, $modalInstance){
        $scope.import = function() {
            $http({
                method : 'POST',
                url : serviceUrl + '/store/import'
            }).success( function( data ) {
                    $scope.currentRuleId = data;
                    updateTitle( $scope );

                    $modalInstance.dismiss('ok');
                });
        };
        $scope.close = function() {
            $modalInstance.dismiss();
        };
    }])

    .controller('HomeExportCtrl', ['$scope', '$http', '$modalInstance', function($scope, $http, $modalInstance) {
        $http.get('partials/home/rules.json').success(function(data) {
            $scope.rules = data;
        });
        $scope.export = function() {
            $scope.status = 'Creating file ...';
        };
        $scope.close = function() {
            $modalInstance.dismiss();
        };
        $scope.select = function(template) {
            $scope.selectedRule = template.name;
        };
    }])
    .controller('HomeOpenCtrl', ['$scope', '$http', '$modalInstance', function($scope, $http, $modalInstance) {
        $http.get( serviceUrl + '/store/list' ).success(function(data) {
            $scope.rules = data;
        });

        $scope.open = function(ruleId) {
            $http({
                method : 'GET',
                url : serviceUrl + '/store/' + ruleId
            }).success( function( data ) {
                    alert( $scope.currentRuleId );
                    $scope.currentRuleId = data;
                    updateTitle( $scope );

                    $modalInstance.dismiss('ok');
                });
        };
        $scope.cancel = function() {
            $modalInstance.dismiss();
        };
        $scope.select = function(template) {
            $scope.selectedRule = template.name;
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

function availableExpressions( httpContext, initMessage ) {
    var map = [ [initMessage, "" ] ];
    httpContext.get(serviceUrl + '/rule/expressions/list').success(function(data) {
        var expressions = data;
        for ( var j = 0; j < expressions.length; j++ ) {
            var expr = expressions[ j ];
            map[ j ] = [ expr.name , expr.expressionIRI ];
        }
    });
    return map;
}

function availableProperties( httpContext, $scope ) {
    var pmap = [ ["(Choose Property...)", "" ] ];

    httpContext({
        method : 'GET',
        url : serviceUrl + '/domain/properties',
        data : $scope.domainClasses,
        headers : { 'Content-Type':'application/html' }
    }).success(function(data) {
        var properties = data;
        for ( var j = 0; j < properties.length; j++ ) {
            var prop = properties[ j ];
            pmap[ j ] = [ prop.name , prop.id ];
        }
    });
    return pmap;
}

function updateTitle( $scope ) {
    if ( $scope.hasOwnProperty( 'currentRuleId' ) ) {
        $scope.$parent.title = 'Artifact ' + $scope.currentRuleId;
    } else {
        $scope.$parent.title = 'Artifact Management';
    }
}