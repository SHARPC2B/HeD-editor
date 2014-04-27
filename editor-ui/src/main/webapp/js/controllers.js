'use strict';

var serviceUrl = 'http://localhost:9000';
//var serviceUrl = 'http://192.168.0.4:9000';

angular.module('ruleApp.controllers', [])

    .controller('HomeCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal ) {
        $http({
            method: 'GET',
            url: serviceUrl + '/rule/current'
        }).error(function(data) {
                updateTitle($scope);
            })
            .success(function(data) {
                if ( data != null ) {
                    $scope.currentRuleId = data.ruleId;
                    $scope.currentRuleTitle = data.Name;
                } else {
                    delete $scope.currentRuleId;
                    delete $scope.currentRuleTitle;
                }

                updateTitle( $scope );

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
                            url : serviceUrl + '/store/artifacts/' + $scope.currentRuleId,
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
                            url : serviceUrl + '/store/artifacts/' + $scope.currentRuleId,
                            data : [],
                            headers : { 'Content-Type':'application/html' }
                        }).success( function( data ) {
                                alert( 'Deleted ' + $scope.currentRuleId );
                                delete $scope.currentRuleId;
                                updateTitle( $scope );
                            });
                    }
                };
        });

        $scope.$parent.menuItems = [
            {"text": "Go To Authoring...", "href": "#/standard/background"}
            //, {"text": "Technical View", "href": "#/technical"}
        ];


        $scope.openModal = function(partial) {
            var modal = $modal.open({
                templateUrl: 'partials/home/' + partial.toLowerCase() +'.html',
                controller: 'Home' + partial + 'Ctrl',
                scope : $scope,
                resolve: {
                    items: function() {
                        return $scope.$parent.title;
                    }
                }
            });
            modal.result.then(function() {
                window.location.href = "#/standard/background";
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

                        window.location.href = "#/standard/background";
                    });
            }
        };

    }])

    .controller('BackgroundCtrl', [ '$http', '$scope', '$modal', '$rootScope', function($http, $scope, $modal, $rootScope) {
        $http({
            method: 'GET',
            url: serviceUrl + '/rule/current'
        }).success(function(data) {
                if ( data != null ) {
                    $scope.background = data;
                    $scope.currentRuleId = data.ruleId;
                    $scope.currentRuleTitle = data.Name;
                    $scope.$parent.title = 'Metadata ' + data.Name;
                } else {
                    delete $scope.currentRuleId;
                    delete $scope.currentRuleTitle;
                    $scope.$parent.title = 'Metadata (no rule active)';
                }


                $scope.$parent.menuItems = standardMenuItems(0);
                $scope.selectedTerms = [];
                $scope.selectedCategories = [];
                $http.get('partials/standard/background/iso639-1.json').success(function(data) {
                    $scope.isoLangs = data;
                });
                $scope.saveBackground = function(background) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/info/' + $scope.currentRuleId,
                        data: background
                    }).success(function(data) {
                            $http({
                                method: 'GET',
                                url: serviceUrl + '/rule/current'
                            }).success(function(data) {
                                        $scope.background = data;
                                        $scope.currentRuleId = data.ruleId;
                                        $scope.currentRuleTitle = data.Name;
                                        $scope.$parent.title = 'Metadata ' + data.Name;
                                    }
                            )

                        });
                };

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


                $http.get( serviceUrl + '/rule/metadata/contributors/' + $scope.currentRuleId ).success(function(data) {
                    $scope.contributors = data;
                });
                $http.get( serviceUrl + '/rule/metadata/publishers/' + $scope.currentRuleId ).success(function(data) {
                    $scope.publishers = data;
                });
                $http.get( serviceUrl + '/rule/metadata/coverage/' + $scope.currentRuleId ).success(function(data) {
                    $scope.coverages = data;
                });
                $http.get(serviceUrl + '/rule/metadata/resources/' + $scope.currentRuleId ).success(function(data) {
                    $scope.resources = data;
                });
                $http.get(serviceUrl + '/rule/metadata/rights/' + $scope.currentRuleId).success(function(data) {
                    $scope.rights = data;
                });
                $http.get(serviceUrl + '/rule/metadata/evidence/' + $scope.currentRuleId).success(function(data) {
                    $scope.evidences = data;
                });

                $scope.addCoverage = function(coverage) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/metadata/coverage/' + $scope.currentRuleId,
                        data: coverage
                    }).success(function(data) {
                            $scope.coverages.push(data);
                        });
                };
                $scope.addContributor = function(contributor) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/metadata/contributors/' + $scope.currentRuleId,
                        data: contributor
                    }).success(function(data) {
                            $scope.contributors.push(data);
                        });
                };
                $scope.addPublisher = function(publisher) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/metadata/publishers/' + $scope.currentRuleId,
                        data: publisher
                    }).success(function(data) {
                            $scope.publishers.push(data);
                        });

                };
                $scope.addResource = function(resource) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/metadata/resources/' + $scope.currentRuleId,
                        data: resource
                    }).success(function(data) {
                            $scope.resources.push(data);
                        });

                };
                $scope.addEvidence = function(evidence) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/metadata/evidence/' + $scope.currentRuleId,
                        data: evidence
                    }).success(function(data) {
                            $scope.evidences.push(data);
                        });
                };
                $scope.addRight = function(right) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/metadata/rights/' + $scope.currentRuleId,
                        data: right
                    }).success(function(data) {
                            $scope.rights.push(data);
                        });

                };


                $scope.removeContributor = function(contri) {
                    $http({
                        method: 'DELETE',
                        url: serviceUrl + '/rule/metadata/contributors/' + $scope.currentRuleId,
                        data : contri
                    }).success(function(contri) {
                            $scope.contributors.splice(contri.rowIndex, 1);
                        });
                };
                $scope.removePublisher = function(pub) {
                    $http({
                        method: 'DELETE',
                        url: serviceUrl + '/rule/metadata/publishers/' + $scope.currentRuleId,
                        data : pub
                    }).success(function(pub) {
                            $scope.publishers.splice(contri.rowIndex, 1);
                        });
                };
                $scope.removeCoverage = function(cov) {
                    $http({
                        method: 'DELETE',
                        url: serviceUrl + '/rule/metadata/coverage/' + $scope.currentRuleId,
                        data : cov
                    }).success(function(cov) {
                            $scope.coverages.splice(cov.rowIndex, 1);
                        });
                };
                $scope.removeResource = function(res) {
                    $http({
                        method: 'DELETE',
                        url: serviceUrl + '/rule/metadata/resources/' + $scope.currentRuleId,
                        data : res
                    }).success(function(res) {
                            $scope.resources.splice(res.rowIndex, 1);
                        });
                };
                $scope.removeEvidence = function(ev) {
                    $http({
                        method: 'DELETE',
                        url: serviceUrl + '/rule/metadata/evidence/' + $scope.currentRuleId,
                        data : ev
                    }).success(function(ev) {
                            $scope.evidences.splice(ev.rowIndex, 1);
                        });
                };
                $scope.removeRight = function(rig) {
                    $http({
                        method: 'DELETE',
                        url: serviceUrl + '/rule/metadata/rights/' + $scope.currentRuleId,
                        data : rig
                    }).success(function(rig) {
                            $scope.rights.splice(rig.rowIndex, 1);
                        });
                };




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
                $scope.gridPublisher = {
                    data: 'publishers',
                    enableRowSelection: false,
                    columnDefs: [
                        { field: "Name"},
                        { field: "Role"},
                        { field: "Type"},
                        { cellTemplate: '<a href="" ng-click="removePublisher(row)"><i class="icon-trash"></i></a><a href="" ng-click="openRowDetail(row)"><i class="icon-zoom-in"></i></a>', width: 24}
                    ]
                };
                $scope.gridResource = {
                    data: 'resources',
                    enableRowSelection: false,
                    columnDefs: [
                        { field: "Title"},
                        { field: "Location"},
                        { field: "Citation"},
                        { field: "Description"},
                        { cellTemplate: '<a href="" ng-click="removeResource(row)"><i class="icon-trash"></i></a><a href="" ng-click="openRowDetail(row)"><i class="icon-zoom-in"></i></a>', width: 24}
                    ]
                };
                $scope.gridEvidence = {
                    data: 'evidences',
                    enableRowSelection: false,
                    columnDefs: [
                        { field: "Title"},
                        { field: "Quality"},
                        { field: "Strength"},
                        { field: "Location"},
                        { cellTemplate: '<a href="" ng-click="removeEvidence(row)"><i class="icon-trash"></i></a><a href="" ng-click="openRowDetail(row)"><i class="icon-zoom-in"></i></a>', width: 24}
                    ]
                };
                $scope.saveDocumentation = function(documentation) {
                    console.log(documentation);
                };
                $scope.gridRight = {
                    data: 'rights',
                    enableRowSelection: false,
                    columnDefs: [
                        { field: "Name"},
                        { field: "Type"},
                        { field: "Permissions"},
                        { cellTemplate: '<a href="" ng-click="removePublisher(row)"><i class="icon-trash"></i></a><a href="" ng-click="openRowDetail(row)"><i class="icon-zoom-in"></i></a>', width: 24}
                    ]
                };

                $scope.tinymceOptions = {
                    menubar: false,
                    plugins: ["image spellchecker emoticons"],
                    toolbar: "bold italic underline spellchecker styleselect bullist numlist | undo redo  | image emoticons",
                    statusbar: false
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

            });
    }])
    .controller('RowDetailController', ['$scope', '$http', '$modalInstance', 'data', function($scope, $http, $modalInstance, data) {
        $scope.data = data;
        $scope.close = function() {
            $modalInstance.dismiss();
        };
    }])

    .controller('InformationInputCtrl', ['$scope', '$modalInstance', 'data', function($scope, $modalInstance, data) {
    	$scope.information=data;

        $scope.save = function(information) {
    		$modalInstance.close(information);
    	};

    	$scope.cancel = function() {
    		$modalInstance.dismiss('cancel');
    	};
    }])

    .controller('ResourceInputCtrl', ['$scope', '$modalInstance', 'data', function($scope, $modalInstance, data) {
    	$scope.resource={};

        $scope.removeResource = function(row) {
            $scope.resources.splice(row.rowIndex, 1);
        };

        $scope.saveResource = function() {
            $scope.resources.push($scope.resource);
            $scope.resource={};
        };

        $scope.resources = data;

        $scope.gridResource = {
            data: 'resources',
            enableRowSelection: false,
            columnDefs: [
                { field: "Title"},
                { field: "Citation"},
                { field: "Location"},
                { field: "Description"},
                { cellTemplate: '<a href="" ng-click="removeResource(row)"><i class="icon-trash"></a>', width: 11}
            ]
        };

        $scope.save = function(evidences) {
    		$modalInstance.close(evidences);
    	};

    	$scope.cancel = function() {
    		$modalInstance.dismiss('cancel');
    	};
    }])

    .controller('EvidenceInputCtrl', ['$scope', '$modalInstance', 'data', function($scope, $modalInstance, data) {
    	$scope.evidence={};

    	$scope.removeEvidence = function(row) {
    		$scope.evidences.splice(row.rowIndex, 1);
        };

        $scope.saveEvidence = function() {
            $scope.evidences.push($scope.evidence);
            $scope.evidence={};
        };

        $scope.evidences = data;

        $scope.gridEvidence = {
            data: 'evidences',
            enableRowSelection: false,
            columnDefs: [
                { field: "Title"},
                { field: "Citation"},
                { field: "Location"},
                { field: "Description"},
                { cellTemplate: '<a href="" ng-click="removeEvidence(row)"><i class="icon-trash"></a>', width: 11}
            ]
        };

        $scope.save = function(evidences) {
    		$modalInstance.close(evidences);
    	};

    	$scope.cancel = function() {
    		$modalInstance.dismiss('cancel');
    	};
    }])

    .controller('ExpressionCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
                $http({
                    method: 'GET',
                    url: serviceUrl + '/rule/current'
                }).success(function(data) {
                        if ( data != null ) {
                            $scope.currentRuleId = data.ruleId;
                            $scope.currentRuleTitle = data.Name;
                            $scope.$parent.title = 'Expressions ' + data.Name;
                        } else {
                            delete $scope.currentRuleId;
                            delete $scope.currentRuleTitle;
                            $scope.$parent.title = 'Expressions (no rule active)';
                        }
                        $http({
                            method : 'GET',
                            url : serviceUrl + '/rule/classes/' + $scope.currentRuleId
                        }).success(function(data) {
                                var klasses = data;
                                for ( var j = 0; j < klasses.length; j++ ) {
                                    var klass = klasses[ j ];
                                    $scope.addDomainClass(klass.name);
                                }
                            });
                });

        $scope.$parent.menuItems = standardMenuItems(1);
        $scope.domainClasses = [];
        $scope.domainProperties = [];
        $scope.expressions = [];
        $scope.currentExpression = {};

        $http.get(serviceUrl + '/rule/expressions/list/any').success(function(data) {
            $scope.expressions = data;
        });

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

            if (expressionIndex === null) {
                //alert( "On save, trying to resolve expr index for name " + $scope.currentExpression.name );
                for (var int = 0; int < $scope.expressions.length; int++) {
                    if ($scope.expressions[int].name == $scope.currentExpression.name) {
                        expressionIndex = int;
                        //alert("On save, expression Index was detected as " + expressionIndex);
                        break;
                    }
                }

            }

            var blocks=xmlDoc.getElementsByTagName('block');
            angular.forEach(blocks, function(block) {
                if (block.getAttribute('type') == 'logic_root') {
                    $scope.currentExpression.name = block.getElementsByTagName("field")[0].childNodes[0].nodeValue;
                    //alert( "On save, the current name is " + $scope.currentExpression.name );
                }
            });


            if ( expressionIndex == null ) {
                $scope.currentExpression.expressionIRI = $scope.currentExpression.name;
                //alert( "On save, no index was detected, so this is a new expression. Iri set to " + $scope.currentExpression.name );
            }

            if (Blockly.mainWorkspace.getTopBlocks().length > 1) {
                alert('This expression has more than one top level');
            }

            $http({
                method : 'POST',
                url : serviceUrl + '/rule/expressions/' + $scope.currentExpression.expressionIRI + '/' + $scope.currentExpression.name,
                data : $scope.currentExpression.xml,
                headers : { 'Content-Type':'application/html' }
            }).success( function( data ) {
                    $scope.currentExpression.expressionIRI = data;
                    if (expressionIndex === null) {
                        $scope.expressions.push(angular.copy($scope.currentExpression));
                    } else {
                        $scope.expressions[expressionIndex] = angular.copy($scope.currentExpression);
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
                        $scope.currentExpression.name = expression.name;
                        //alert( "On open, set expr Name to " + $scope.currentExpression.name );

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

        $scope.addDomainClass = function(domainClass) {
            if ( $scope.domainClasses.indexOf( domainClass ) < 0 ) {
                $scope.domainClasses.push(domainClass);
                $http({
                    method : 'POST',
                    url : serviceUrl + '/rule/classes/' + domainClass
                });
                $scope.domainProperties.push(domainClass);
                Blockly.Blocks[domainClass] = {
                    init: function() {
                        this.setColour(40);
                        this.appendDummyInput().appendField(domainClass);
                        this.setOutput(true, ['http://asu.edu/sharpc2b/ops#DomainClass']);
                    }
                };
                Blockly.Blocks[domainClass + '/properties' ] = {

                    init: function() {
                        this.setColour(40);
                        this.appendDummyInput().appendField(domainClass);
                        this.appendValueInput('DOT')
                            .appendField(new Blockly.FieldDropdown( availableProperties( $http, $scope, domainClass ) ), "DomainProperty")
                            .setCheck('http://asu.edu/sharpc2b/ops#DomainProperty');
                        this.setOutput(true, ['http://asu.edu/sharpc2b/ops#DomainProperty']);
                        this.setTooltip('');
                    }
                };
            }
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

            init: function() {
                this.setColour(160);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Choose Expression...)", "any" ) ), "Variables");
                this.setOutput(true, null);
                this.setTooltip('');
            }
        };
        Blockly.Blocks[ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] = {

            init: function() {
                this.setColour(40);
                this.appendValueInput('DOT')
                    .appendField(new Blockly.FieldDropdown( allAvailableProperties( $http, $scope ) ), "DomainProperty")
                    .setCheck('http://asu.edu/sharpc2b/ops#DomainProperty');
                this.setOutput(true, ['http://asu.edu/sharpc2b/ops#DomainProperty']);
                this.setTooltip('');
            }
        };

        Blockly.Blocks['xsd:text'] = {
            init : function() {
                // Assign 'this' to a variable for use in the tooltip closure.
                var thisBlock = this;
                this.setColour(152);
                this.appendDummyInput().appendField( '[text]' ).appendField(new Blockly.FieldExternalInput("Click to edit the text", openExternalInput), "TEXT");
                this.setOutput(true, 'http://www.w3.org/2001/XMLSchema#string');
                this.setTooltip(function() {
                    return thisBlock.getFieldValue('TEXT');
                });
            }
        };

        Blockly.Blocks['logic_root'] = {
            init: function() {
                this.setHelpUrl('http://www.example.com/');
                this.appendValueInput("NAME")
                    .appendField(new Blockly.FieldTextInput("Name"), "NAME");
                this.setColour(46);
                this.setTooltip('');
            }
        };
        function openExternalInput(text, callback) {
        	var modalInstance = $modal.open({
        		templateUrl: 'partials/standard/expression/text-input.html',
        		controller: 'TextInputCtrl',
        		resolve: {
        			text: function() {
        				return text;
        			}
        		}
        	});
        	modalInstance.result.then(function(text) {
        		callback(text);
        	});
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
        d3.json(serviceUrl + "/domain/hierarchy", function(data) {
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






    .controller('LogicCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
        $scope.$parent.menuItems = standardMenuItems(3);
        $scope.logic = {};

        $http({
            method: 'GET',
            url: serviceUrl + '/rule/current'
        }).success(function(data) {
                if ( data != null ) {
                    $scope.currentRuleId = data.ruleId;
                    $scope.currentRuleTitle = data.Name;
                    $scope.$parent.title = 'Logic : ' + data.Name;
                } else {
                    delete $scope.currentRuleId;
                    delete $scope.currentRuleTitle;
                    $scope.$parent.title = 'Logic (no rule active)';
                }
                $http({
                    method : 'GET',
                    url : serviceUrl + '/rule/current/logic'
                }).success(function(logic) {
                        $scope.logic.xml = logic;
                        Blockly.mainWorkspace.clear();
                        Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.logic.xml));
                    });

            });

        $http.get(serviceUrl + '/rule/expressions/list/any').success(function(data) {
            $scope.expressions = data;
        });


        $scope.save = function() {
            $scope.logic.xml = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace));

            $http({
                method : 'POST',
                url : serviceUrl + '/rule/logic',
                data : $scope.logic.xml,
                headers : { 'Content-Type':'application/html' }
            }).success( function( data ) {
                    alert( 'Updated rule logic' );
                });
        };

        $scope.clear = function() {
            if (confirm("Clear logic?")) {
                Blockly.mainWorkspace.clear();
                var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'logic_root');
                rootBlock.initSvg();
                rootBlock.render();
                rootBlock.setMovable(false);
                rootBlock.setDeletable(false);
            }
        };

        $scope.codeSystems = {};
        $scope.cts2search = function(matchValue) {
            var codeSys = $scope.codeSystems.chosenCodeSystem;
            return $http.jsonp(serviceUrl + '/fwd/cts2/entities?callback=JSON_CALLBACK&matchalgorithm=startsWith&format=json&maxtoreturn=20&matchvalue='+matchValue).then(function(response){
                var entities = new Array();
                var list = response.data.entityDirectory.entryList;
                console.log( list );
                for ( var j = 0; j < list.length; j++ ) {
                    var entity = list[j];
                    var descriptor = {};
                    descriptor.namespace = entity.name.namespace.toUpperCase();
                    descriptor.name = entity.name.name;
                    descriptor.designation =
                            // descriptor.namespace + ':' + descriptor.name + '-' +
                            entity.knownEntityDescriptionList[0].designation;
                    entities[ j ] = descriptor;
                }
                return entities;
            });
        };
        $scope.onSelect = function ($item, $model, $label) {
            $scope.codeSystems.chosenCode.code = $item.name;
            $scope.codeSystems.chosenCode.codeSystem = $item.namespace;
            $scope.codeSystems.chosenCode.label = $label;
        };
        $scope.execute = function() {
            $http.get( serviceUrl + '/template/list/Condition/' + $scope.codeSystems.chosenCode.codeSystem + '/' + $scope.codeSystems.chosenCode.code ).success(function( data ) {
                $scope.primitives = data;
            });
        }
        $scope.clear = function() {
            $scope.codeSystems = {};
            $http.get(serviceUrl + '/template/list/Condition').success(function(data) {
                $scope.primitives = data;
            });
        }


        $http.get(serviceUrl + '/template/list/Condition').success(function(data) {
            $scope.primitives = data;
        });

        $scope.openClauseEditor = function(clause) {

            var d = $modal.open({
                templateUrl: 'partials/standard/logic/primitive-editor.html',
                controller: 'EditPrimitiveController',
                resolve : {
                    clause : function() {
                        var inst = angular.copy(clause);
                        inst.category[0] = "CONDITION";
                        return inst;
                    }
                }
            })
            d.result.then( function( logic ) {
                console.log( logic );
                    $scope.logic.xml = logic;
                    Blockly.mainWorkspace.clear();
                    Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.logic.xml));

                    $http.get(serviceUrl + '/rule/expressions/list/any').success(function(data) {
                        $scope.expressions = data;
                    });
            });
        };

        $scope.preview = function(expression) {
            var d = $modal.open({
                templateUrl: 'partials/standard/logic/canvas.html',
                controller: 'CanvasCtrl',
                resolve : {
                    expression : function() {
                        return expression.expressionIRI;
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
            init: function() {
                this.setColour(160);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Condition Expression...)", "logic" ) ), "Clauses");
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_any = {
            init: function() {
                this.setColour(60);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Expression...)", "any" ) ), "Clauses");
                this.setOutput(true, "Any");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_negate = {
            init: function() {
                this.setColour(210);
                this.appendValueInput( 'NOT' )
                    .appendField( "Not" )
                    .setCheck( "Boolean")
                    .setAlign( Blockly.ALIGN_RIGHT );
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_exists = {
            init: function() {
                this.setColour(210);
                this.appendValueInput( 'EXISTS' )
                    .appendField( "Exists" )
                    .setCheck( "Any" )
                    .setAlign( Blockly.ALIGN_RIGHT );
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.logic_root = {
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









    .controller('CanvasCtrl', ['$scope', '$http', '$modalInstance', 'expression', function($scope, $http, $modalInstance, expression) {
        Blockly.Blocks['logic_root'] = {
            init: function() {
                this.appendValueInput("NAME")
                    .appendField(new Blockly.FieldTextInput("Name"), "NAME");
                this.setColour(46);
            }
        };

        $http({
            method : 'GET',
            url : serviceUrl + '/rule/expressions/' + expression,
            headers : { 'Content-Type':'application/html' }
        }).success( function( data ) {
                $scope.expression = expression;
                Blockly.inject(document.getElementById('blocklyPreview'), {
                    path: './lib/blockly/',
                    toolbox: document.getElementById('toolbox')
                });

                Blockly.mainWorkspace.clear();
                Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom(data));
            });

        $scope.cancel = function() {
            Blockly.inject(document.getElementById('blocklyDiv'), {
                path: './lib/blockly/',
                toolbox: document.getElementById('toolbox')
            });
            $modalInstance.dismiss();
        }


    }])

    .controller('TextInputCtrl', ['$scope', '$http', '$modalInstance', 'text', function($scope, $http, $modalInstance, text) {
        $scope.text = text;
        $scope.triggers = {};

        $scope.save = function(text) {
            $modalInstance.close(text);
        };

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

        tinymce.PluginManager.add('variables', function(editor) {
            var listVariables = ['#[var1]', '#[var2]', '#[var3]', '#[varX]'];
            var menuItems = [];
            tinymce.each(listVariables, function(variable) {
                menuItems.push({
                    text: variable,
                    onclick: function() {
                        editor.insertContent(variable);
                    }
                });
            });
            editor.addButton('variables', {
                type: 'menubutton',
                text: 'Variables',
                menu: menuItems
            });
        });

        $scope.tinymceOptions = {
            menubar: false,
            plugins: ["image spellchecker emoticons variables"],
            toolbar: "bold italic underline spellchecker styleselect bullist numlist | undo redo  | image emoticons | variables",
            statusbar: false
        };
    }])



    .controller('TriggerCtrl', [ '$http', '$scope', '$modal', function($http, $scope, $modal) {
        $scope.$parent.menuItems = standardMenuItems(2);
        $scope.triggers = {};

        $http({
            method: 'GET',
            url: serviceUrl + '/rule/current'
        }).success(function(data) {
                if ( data != null ) {
                    $scope.currentRuleId = data.ruleId;
                    $scope.currentRuleTitle = data.Name;
                    $scope.$parent.title = 'Triggers : ' + data.Name;
                } else {
                    delete $scope.currentRuleId;
                    delete $scope.currentRuleTitle;
                    $scope.$parent.title = 'Triggers (no rule active)';
                }
                $http({
                    method : 'GET',
                    url : serviceUrl + '/rule/current/triggers'
                }).success(function(trigs) {
                        $scope.triggers.xml = trigs;
                        Blockly.mainWorkspace.clear();
                        Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.triggers.xml));
                    });

            });


        $http.get(serviceUrl + '/template/list/Trigger').success(function(data) {
            $scope.triggerTemplates = data;
        });

        $scope.openClauseEditor = function(clause) {
            var d = $modal.open({
                templateUrl: 'partials/standard/logic/primitive-editor.html',
                controller: 'EditPrimitiveController',
                resolve : {
                    clause : function() {
                        var inst = angular.copy(clause);
                        inst.category[0] = "TRIGGER";
                        return inst;
                    }
                }
            });
            d.result.then( function( triggers ) {
                $scope.triggers.xml = triggers;
                Blockly.mainWorkspace.clear();
                Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.triggers.xml));
                /*
                $http.get(serviceUrl + '/rule/expressions/list/any').success(function(data) {
                    $scope.expressions = data;
                });
                */
            });

        };


        $scope.save = function() {
            $scope.triggers.xml = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace));

            $http({
                method : 'POST',
                url : serviceUrl + '/rule/triggers',
                data : $scope.triggers.xml,
                headers : { 'Content-Type':'application/html' }
            }).success( function( data ) {
                    alert( 'Updated triggers' );
                });
        };

        $scope.clear = function() {
            if (confirm("Clear Triggers?")) {
                Blockly.mainWorkspace.clear();
                var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'http://asu.edu/sharpc2b/ops-set#TriggerRoot');
                rootBlock.initSvg();
                rootBlock.render();
                rootBlock.setMovable(false);
                rootBlock.setDeletable(false);
            }
        };

        Blockly.Blocks['TypedExpression'] = {
            init: function() {
                this.setColour(30);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Typed Expression...)", "Request" ) ), "TypedExpression");
                this.setOutput( "Request" );
            }
        };
        Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TypedTrigger'] = {
            init: function() {
                this.setColour(20);
                this.appendDummyInput()
                    .appendField("Typed");
                this.appendValueInput("Trigger")
                    .setCheck("Request")
                this.setPreviousStatement(true, "trigger");
                this.setNextStatement(true, "trigger");
            }
        };
        Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TemporalTrigger'] = {
            init: function() {
                this.setColour(20);
                this.appendDummyInput()
                    .appendField("Temporal");
                this.appendValueInput("Trigger")
                    .setCheck('http://asu.edu/sharpc2b/ops#IntervalType')
                this.setPreviousStatement(true, "trigger");
                this.setNextStatement(true, "trigger");
            }
        };

        Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TriggerRoot'] = {
            init: function() {
                this.setColour(10);
                this.appendStatementInput("ROOT")
                    .setCheck("trigger")
                    .appendField("Triggers");
            }
        };
        Blockly.HSV_SATURATION = 0.66;
        Blockly.HSV_VALUE = 0.71;
        Blockly.inject(document.getElementById('blocklyDiv'), {
            path: './lib/blockly/',
            toolbox: document.getElementById('toolbox')
        });

        var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'http://asu.edu/sharpc2b/ops-set#TriggerRoot');
        rootBlock.initSvg();
        rootBlock.render();
        rootBlock.setMovable(false);
        rootBlock.setDeletable(false);

    }])




    .controller('ActionCtrl', [ '$http', '$scope', '$modal','$log', function($http, $scope, $modal,$log) {
        $scope.$parent.menuItems = standardMenuItems(4);
        $scope.actions = {};
        $scope.expressions = {};
        $scope.primitives = {};

        $http({
            method: 'GET',
            url: serviceUrl + '/rule/current'
        }).success(function(data) {
                if ( data != null ) {
                    $scope.currentRuleId = data.ruleId;
                    $scope.currentRuleTitle = data.Name;
                    $scope.$parent.title = 'Actions : ' + data.Name;
                } else {
                    delete $scope.currentRuleId;
                    delete $scope.currentRuleTitle;
                    $scope.$parent.title = 'Actions (no rule active)';
                }
                $http({
                    method : 'GET',
                    url : serviceUrl + '/rule/current/actions'
                }).success(function(acts) {
                        $scope.actions.xml = acts;
                        Blockly.mainWorkspace.clear();
                        Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.actions.xml));
                    });

            });


        $http.get(serviceUrl + '/template/list/Condition').success(function(data) {
            $scope.primitives = data;
        });

        $http.get(serviceUrl + '/rule/expressions/list/any').success(function(data) {
            $scope.expressions = data;
        });

        $scope.preview = function(expression) {
            var d = $modal.open({
                templateUrl: 'partials/standard/logic/canvas.html',
                controller: 'CanvasCtrl',
                resolve : {
                    expression : function() {
                        return expression.expressionIRI;
                    }
                }
            });
        };

        $scope.save = function() {
            $scope.actions.xml = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace));

            $http({
                method : 'POST',
                url : serviceUrl + '/rule/actions',
                data : $scope.actions.xml,
                headers : { 'Content-Type':'application/html' }
            }).success( function( data ) {
                    alert( 'Updated actions' );
                });
        };

        $scope.clear = function() {
            if (confirm("Clear Actions?")) {
                Blockly.mainWorkspace.clear();
                var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'action_root');
                rootBlock.initSvg();
                rootBlock.render();
                rootBlock.setMovable(false);
                rootBlock.setDeletable(false);
            }
        };


        $http.get(serviceUrl + '/template/list/Action').success(function(data) {
            $scope.actionsTemplates = data;
        });

        $scope.openClauseEditor = function(clause, subMode) {
            var d = $modal.open({
                templateUrl: 'partials/standard/logic/primitive-editor.html',
                controller: 'EditPrimitiveController',
                resolve : {
                    clause : function() {
                        var inst = angular.copy(clause);
                        inst.category[0] = "ACTION";
                        inst.category[1] = subMode;
                        return inst;
                    }
                }
            });
            d.result.then( function( actions ) {
                $scope.actions.xml = actions;
                Blockly.mainWorkspace.clear();
                Blockly.Xml.domToWorkspace(Blockly.mainWorkspace, Blockly.Xml.textToDom($scope.actions.xml));

                $http.get(serviceUrl + '/rule/expressions/list/any').success(function(data) {
                    $scope.expressions = data;
                });

            });
        };

        Blockly.Blocks.action_group = {
            init: function() {
                this.setColour(20);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown([["Perform all", "all"], ["Perform none", "none"], ["Perform any", "any"], ["Perform exactly one", "one"]]), "NAME");
                this.appendDummyInput()
                	.appendField("Tittle")
                	.appendField(new Blockly.FieldTextInput("title"), "TITLE");
                /*
                this.appendValueInput("Documentation")
                    .setCheck("Documentation")
                    .appendField("Documentation");
                */
                this.appendValueInput("Condition")
                    .setCheck("Boolean")
                    .appendField("Condition");
                this.appendStatementInput( ["NestedAction"] )
                    .setCheck( ["AtomicAction", "ActionGroup" ] );
                this.setPreviousStatement(true, "ActionGroup");
                this.setNextStatement(true, "ActionGroup");
            }
        };
        Blockly.Blocks.atomic_action = {
            init: function() {
                this.setColour(99);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown([["Create", "CreateAction"], ["Update", "UpdateAction"], ["Remove", "RemoveAction"], ["Fire Event", "FireEventAction"], ["Declare", "DeclareResponseAction"], ["Collect", "CollectInformationAction"]]), "NAME");
                this.appendDummyInput()
                	.appendField("Tittle")
                	.appendField(new Blockly.FieldTextInput("title"), "TITLE");
                /*
                this.appendValueInput("Documentation")
                    .setCheck("Documentation")
                    .appendField("Documentation");
                */
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

        function openExternalInformation(information, callback) {
        	var modalInstance = $modal.open({
        		templateUrl: 'partials/standard/action/information-input.html',
        		controller: 'InformationInputCtrl',
        		resolve: {
        			data: function() {
        				return JSON.parse(information);
        			}
        		}
        	});
        	modalInstance.result.then(function(information) {
        		callback(JSON.stringify(information));
        	});
        };
        function openExternalResource(resources, callback) {
        	var modalInstance = $modal.open({
        		templateUrl: 'partials/standard/action/resource-input.html',
        		controller: 'ResourceInputCtrl',
        		resolve: {
        			data: function() {
        				return JSON.parse(resources);
        			}
        		}
        	});
        	modalInstance.result.then(function(resources) {
        		callback(JSON.stringify(resources));
        	});
        };
        function openExternalEvidence(evidences, callback) {
        	var modalInstance = $modal.open({
        		templateUrl: 'partials/standard/action/evidence-input.html',
        		controller: 'EvidenceInputCtrl',
        		resolve: {
        			data: function() {
        				return JSON.parse(evidences);
        			}
        		}
        	});
        	modalInstance.result.then(function(evidences) {
        		callback(JSON.stringify(evidences));
        	});
        };

        Blockly.Blocks['action_documentation'] = {
        	init: function() {
        		this.setColour(160);
        		this.appendDummyInput().appendField('Basic Information').appendField(new Blockly.FieldExternalInput('{}', openExternalInformation), "INFORMATION");
        		this.appendDummyInput().appendField('Related Resources').appendField(new Blockly.FieldExternalInput('[]', openExternalResource), "RESOURCE");
        		this.appendDummyInput().appendField('Supporting Evidence').appendField(new Blockly.FieldExternalInput('[]', openExternalEvidence), "EVIDENCE");
        		this.setOutput(true, "Documentation");
        	}
        };

        Blockly.Blocks.action_condition = {
            init: function() {
                this.setColour(160);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Condition Expression...)", "logic") ), "Clauses");
                this.setOutput(true, "Boolean");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.action_sentence = {
            init: function() {
                this.setColour(290);
                this.appendDummyInput()
                    .appendField(new Blockly.FieldDropdown( availableExpressions( $http, "(Sentence Expression...)", "action" ) ), "AS");
                this.setOutput(true, "ActionSentence");
                this.setTooltip('');
            }
        };
        Blockly.Blocks.action_root = {
                init: function() {
                    this.setColour(10);
                    this.appendStatementInput("ROOT")
                    	.setCheck("ActionGroup")
                    	.appendField("Action");
                }
        };
        Blockly.HSV_SATURATION = 0.35;
        Blockly.HSV_VALUE=0.85;
        Blockly.inject(document.getElementById('blocklyDiv'),
            {path: './lib/blockly/', toolbox: document.getElementById('toolbox')});
        var rootBlock = new Blockly.Block(Blockly.mainWorkspace, 'action_root');
        rootBlock.initSvg();
        rootBlock.render();
        rootBlock.setMovable(false);
        rootBlock.setDeletable(false);

    }])





    .controller('SaveCtrl', [ '$scope', '$http', '$sce', function($scope, $http, $sce) {
        $scope.outputFormat1 = 'HED_HTML';
        $scope.outputFormat2 = 'HED_XML';

        $scope.refresh1 = function() {
            $http.get(serviceUrl + '/rule/export/' + $scope.currentRuleId + '/' + $scope.outputFormat1 ).success(function(data) {
                if ( $scope.outputFormat1 == 'HED_HTML' ) {
                    $scope.xml1 = $sce.trustAsHtml( data );
                } else {
                    $scope.xml1 = data;
                }
            });
        }
        $scope.refresh2 = function() {
            $http.get(serviceUrl + '/rule/export/' + $scope.currentRuleId + '/' + $scope.outputFormat2 ).success(function(data) {
                if ( $scope.outputFormat2 == 'HED_HTML' ) {
                    $scope.xml2 = $sce.trustAsHtml( data );
                } else {
                    $scope.xml2 = data;
                }
            });
        }

        $http({
            method: 'GET',
            url: serviceUrl + '/rule/current'
        }).success(function(data) {
                if ( data != null ) {
                    $scope.background = data;
                    $scope.currentRuleId = data.ruleId;
                    $scope.currentRuleTitle = data.Name;
                    $scope.$parent.title = 'Review and Save ' + data.Name;

                    $scope.$parent.menuItems = standardMenuItems(5);
                    $scope.refresh1();
                    $scope.refresh2();
                } else {
                    delete $scope.currentRuleId;
                    delete $scope.currentRuleTitle;
                    $scope.$parent.title = 'Review and Save (no rule active)';
                }

                $scope.saveBackground = function(background) {
                    $http({
                        method: 'POST',
                        url: serviceUrl + '/rule/info/' + $scope.currentRuleId,
                        data: background
                    }).success(function(data) {
                    });
                };
        });
    }])






    .controller('TechnicalCtrl', [ '$http', '$scope', function ($http, $scope) {
        $scope.color = d3.scale.category20();
        var force = d3.layout.force()
            .charge(-300)
            .linkDistance(200)
            .size([800, 800]);
        var svg = d3.select("#force");
        d3.json(serviceUrl + "/newmentor.obj.json", function(json) {
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

    .controller('EditPrimitiveController', ['$scope', '$modalInstance', 'clause', '$http', '$modal', function($scope, $modalInstance, clause, $http, $modal) {
    	$scope.clause = clause;

        $scope.formatTemplate = function( $scope ) {
            $scope.template = clause.description;
            angular.forEach($scope.detail.parameters, function(parameter, key) {
                format( parameter );
            });
        }

        // Code-filtered templates are few, and pre-populated
        // Otherwise, templates may be MANY, so details are fetched on demand.
        if ( $scope.clause.parameters == null ) {
            $http.get(serviceUrl + '/template/detail/' + clause.templateId).success(function(data) {
                $scope.detail = data;
                $scope.detail.category = clause.category;
                $scope.formatTemplate( $scope );

            });
        } else {
            $scope.detail = $scope.clause;
            $scope.detail.category = clause.category;
            $scope.formatTemplate( $scope );
        }

        $scope.submit = function(data) {
            console.log( data );
            $http({
                method : 'POST',
                url : serviceUrl + '/template/create',
                data : data
            }).success( function( answer ) {
                    $modalInstance.close( answer );
                });

        };

        $scope.verify = function(data) {
            $http({
                method : 'POST',
                url : serviceUrl + '/template/verify',
                data : $scope.detail
            }).success( function( answer ) {
                    if ( answer.length == 0 ) {
                        alert( "OK!" );
                    } else {
                        alert( answer );
                    }
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
                        return parameter;
                    }
                }
            }).result.then(function() { $scope.formatTemplate($scope) });
        };



        $scope.cts2search = function(matchValue, parameter) {
            var resolveCodes = ( parameter.selectedOperation == "Equal" );
            var codeSys = parameter.elements[2].value;
            if ( resolveCodes ) {
                var call = isEmpty( codeSys )
                    ? serviceUrl + '/fwd/cts2/entities?callback=JSON_CALLBACK&format=json&maxtoreturn=20&matchvalue='+matchValue
                    : serviceUrl + '/fwd/cts2/codesystem/' + codeSys + '/version/' + codeSys + '-LATEST/entities?callback=JSON_CALLBACK&format=json&maxtoreturn=20&matchvalue='+matchValue;
                return $http.jsonp( call ).then(function(response){
                    var entities = new Array();
                    var list = response.data.entityDirectory.entryList;
                    for ( var j = 0; j < list.length; j++ ) {
                        entities[j] = $scope.normalize( list[j], true );
                    }
                    return entities;
                });
            } else {
                return $http.jsonp(serviceUrl + '/fwd/cts2/valuesets?callback=JSON_CALLBACK&format=json&maxtoreturn=30&matchvalue='+matchValue).then(function(response){
                    var entities = new Array();
                    var list = response.data.valueSetCatalogEntryDirectory.entryList;
                    for ( var j = 0; j < list.length; j++ ) {
                        entities[j] = $scope.normalize( list[j], false );
                    }
                    return entities;
                });
            }
        };
        $scope.onSelect = function ($item, $model, $label, parameter) {
            parameter.elements[ 2 ].value = $item.namespace;
            parameter.elements[ 1 ].value = $item.name;
            parameter.elements[ 0 ].value = $label;
            format(parameter)
        };
        $scope.normalize = function(entity, resolveCodes) {
            if ( resolveCodes ) {
                var descriptor = {};
                descriptor.namespace = entity.name.namespace.toUpperCase();
                descriptor.name = entity.name.name;
                descriptor.designation =
                    //descriptor.namespace + ':' + descriptor.name + ' - ' +
                    entity.knownEntityDescriptionList[0].designation;
                return descriptor;
            } else {
                var descriptor = {};
                descriptor.designation = entity.valueSetName;
                descriptor.namespace = entity.valueSetName;
                descriptor.name = entity.valueSetName;
                return descriptor;
            }
        }

        $scope.duplicateParam = function( param ) {
            $scope.detail.parameters.splice( $scope.detail.parameters.indexOf( param ), angular.copy( param ) );
        }
    }])

    .controller('ParameterController', ['$scope', '$modalInstance', 'parameter', '$http', function($scope, $modalInstance, parameter, $http) {
        $scope.parameter = parameter;
        $scope.resolveCodes = true;

        $scope.cts2search = function(matchValue) {
            if ( $scope.resolveCodes ) {
                var codeSys = $scope.parameter.elements[2].value;
                var call = isEmpty( codeSys )
                    ? serviceUrl + '/fwd/cts2/entities?callback=JSON_CALLBACK&format=json&maxtoreturn=20&matchvalue='+matchValue
                    : serviceUrl + '/fwd/cts2/codesystem/' + codeSys + '/version/' + codeSys + '-LATEST/entities?callback=JSON_CALLBACK&format=json&maxtoreturn=20&matchvalue='+matchValue;
                return $http.jsonp( call ).then(function(response){
                    var entities = new Array();
                    var list = response.data.entityDirectory.entryList;
                    for ( var j = 0; j < list.length; j++ ) {
                        entities[j] = $scope.normalize( list[j] );
                    }
                    return entities;
                });
            } else {
                return $http.jsonp(serviceUrl + '/fwd/cts2/valuesets?callback=JSON_CALLBACK&format=json&maxtoreturn=30&matchvalue='+matchValue).then(function(response){
                    var entities = new Array();
                    var list = response.data.valueSetCatalogEntryDirectory.entryList;
                    for ( var j = 0; j < list.length; j++ ) {
                        entities[j] = $scope.normalize( list[j] );
                    }
                    return entities;
                });
            }
        };
        $scope.ok = function() {
            format( $scope.parameter );
            $modalInstance.close();
        };
        $scope.onSelect = function ($item, $model, $label) {
            $scope.parameter.elements[ 2 ].value = $item.namespace;
            $scope.parameter.elements[ 1 ].value = $item.name;
            $scope.parameter.elements[ 0 ].value = $label;
        };
        $scope.onOpChange = function(op) {
            $scope.resolveCodes = "Equal" == op;
        }
        $scope.normalize = function(entity) {
            if ( $scope.resolveCodes ) {
                var descriptor = {};
                descriptor.namespace = entity.name.namespace.toUpperCase();
                descriptor.name = entity.name.name;
                descriptor.designation =
                        //descriptor.namespace + ':' + descriptor.name + ' - ' +
                        entity.knownEntityDescriptionList[0].designation;
                return descriptor;
            } else {
                var descriptor = {};
                descriptor.designation = entity.valueSetName;
                descriptor.namespace = entity.valueSetName;
                descriptor.name = entity.valueSetName;
                return descriptor;
            }
        }
    }])


    .controller('HomeImportCtrl', ['$scope', '$http', '$modalInstance', function($scope, $http, $modalInstance){
        $scope.import = function() {
            var f = document.getElementById('inputFile').files[0];
            var r = new FileReader();
            r.onloadend = function(e){
                var byteData = e.target.result;
                delete $http.defaults.headers.common['X-Requested-With'];
                $http({
                    method : 'POST',
                    url : serviceUrl + '/store/import/' + f.name,
                    data : { 'bytes' : byteData },
                    headers : { 'Content-Type' : 'appplication/xml' }
                }).success( function( data ) {
                        $http({
                            method: 'GET',
                            url: serviceUrl + '/rule/current'
                        }).success(function(data) {
                            $scope.$parent.currentRuleId = data.ruleId;
                            $scope.$parent.currentRuleTitle = data.Name;
                            updateTitle( $scope.$parent );
                            $modalInstance.dismiss('ok');
                        });
                    });
            }
            r.readAsBinaryString(f);
        };
        $scope.close = function() {
            $modalInstance.dismiss();
        };
    }])

    .controller('HomeExportCtrl', ['$scope', '$http', '$modalInstance', function($scope, $http, $modalInstance) {
        $http.get( serviceUrl + '/store/list' ).success(function(data) {
            $scope.rules = data;
        });
        $scope.export = function(ruleId) {
            $scope.status = 'Creating file ...';
            $http({
                method : 'POST',
                url : serviceUrl + '/store/export/' + ruleId + '/HeD'
            }).success( function( data ) {
                    $modalInstance.dismiss('ok');
                });
        };
        $scope.close = function() {
            $modalInstance.dismiss();
        };
        $scope.select = function(template) {
            $scope.selectedRule = template.name;
        };
    }])
    .controller('HomeOpenCtrl', ['$scope', '$http', '$modalInstance', '$location', function($scope, $http, $modalInstance, $location) {
        $http.get( serviceUrl + '/store/list' ).success(function(data) {
            $scope.rules = data;
        });

        $scope.open = function(ruleId) {
            $http({
                method : 'GET',
                url : serviceUrl + '/store/' + ruleId
            }).success( function( openId ) {
                        $http({
                            method: 'GET',
                            url: serviceUrl + '/rule/current'
                        }).success(function(data) {
                                $scope.$parent.currentRuleId = data.ruleId;
                                $scope.$parent.currentRuleTitle = data.Name;
                                updateTitle( $scope.$parent );
                                $modalInstance.close('ok');
                            });
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
    var menuItems = [{"text": "Metadata", "href": "#/standard/background"},
        {"text": "Create Expressions", "href": "#/standard/expression"},
        {"text": "Select Trigger", "href": "#/standard/trigger"},
        {"text": "Define Logic", "href": "#/standard/logic"},
        {"text": "Choose Action", "href": "#/standard/action"},
        {"text": "Review & Export", "href": "#/standard/save"}];
    menuItems[position].status = "disabled";
    return menuItems;
};

function availableExpressions( httpContext, initMessage, type ) {
    var map = [ [initMessage, "" ] ];
    httpContext.get(serviceUrl + '/rule/expressions/list/' +type ).success(function(data) {
        var expressions = data;
        for ( var j = 0; j < expressions.length; j++ ) {
            var expr = expressions[ j ];
            map[ j ] = [ expr.name , expr.expressionIRI ];
        }
    });
    return map;
}


function allAvailableProperties( httpContext, $scope ) {
    var pmap = [ ["(Choose Property...)", "" ] ];

    httpContext({
        method : 'GET',
        url : serviceUrl + '/domain/properties'
    }).success(function(data) {
            var properties = data;
            for ( var j = 0; j < properties.length; j++ ) {
                var prop = properties[ j ];
                pmap[ j ] = [ prop.name , prop.id ];
            }
        });
    return pmap;
}


function availableProperties( httpContext, $scope, domainClass ) {
    var pmap = [ ["(Choose Property...)", "" ] ];

    httpContext({
        method : 'GET',
        url : serviceUrl + '/domain/properties/' + domainClass
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
        $scope.$parent.title = 'Artifact ' + $scope.currentRuleTitle;
    } else {
        $scope.$parent.title = 'Artifact Management';
    }
}

function format( parameter ) {
    var display = "";
    parameter.elements.forEach( function(element) {
        if ( ! isEmpty( element.value ) && element !== parameter.elements[0] ) {
            //display = display + '[' + element.name + "=" + element.value + '] ';
            display = display
                        + ' '
                        + ( isEmpty( element.pre ) ? '' : element.pre )
                        + element.value
                        + ( isEmpty( element.post ) ? '' : element.post )
                        + ' ';
        }
    });
    parameter.displayValue = display;
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}