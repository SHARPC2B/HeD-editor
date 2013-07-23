'use strict';

angular.module('ruleApp.controllers', [])
	.controller('HomeCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = '&nbsp;';
		$scope.$parent.menuItems = [
		                            {"text": "Standard Mode", "href": "#/standard/background"},
		                            {"text": "Advanced Mode", "href": "#/advanced"},
		                            {"text": "Revise My Rules", "href": "#/revise"},
		                            {"text": "Use Others' Rules", "href": "#/others"},
		                            {"text": "Technical View", "href": "#/technical"},
		                            {"text": "Help", "href": "#/help"}
		                           ];
	}])
	.controller('BackgroundCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = 'Background Information';
		$scope.$parent.menuItems = standardMenuItems(0);
		$scope.reference = [{Name: "Guide to clinical Preventive Services 2012", Reference: "http://www.ahrq.gov/professionals/clinicians-providers/guidelines-", Type: "Web"},
		                    {Name: "Diagnosis of functional kidney failure of cirrhosis with Dopple sonography:", Reference: "X&Y Journal; p. 234-246", Type: "Journal"}];
	    $scope.gridOptions = { data: 'reference' };
	}])
	.controller('TriggerCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = 'Decide how the Rule will be Triggered';
		$scope.$parent.menuItems = standardMenuItems(1);
	}])
	.controller('LogicCtrl', [ '$http', '$scope', function($http, $scope) {
		$scope.$parent.title = 'Define Rule Logic';
		$scope.$parent.menuItems = standardMenuItems(2);
		$scope.clause = [{"Clause": "Lab LABTEST OP VALUE", "Example": "Hemoglobin A1c > 8%"},
		                 {"Clause": "LABTEST within M days OP VALUE", "Example": "Hemoglobin A1c within 60 days > 7%"},
		                 {"Clause": "Last LABTEST OP TIME-UNITS", "Example": "Last Hemoglobin A1c More Than 6 Months Ago"},
		                 {"Clause": "Patient on MEDICATION", "Example": "Patien on Metformin"},
		                 {"Clause": "Patient has PROBLEM on problem-list", "Example": "Patient has Type 1 Diabetes on problem-list"}];
	    $scope.gridOptions = { data: 'clause' };
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
	}]);

function standardMenuItems(position) {
	var menuItems = [{"text": "Background Information", "href": "#/standard/background"},
                 {"text": "Select Trigger", "href": "#/standard/trigger"},
                 {"text": "Define Logic", "href": "#/standard/logic"},
                 {"text": "Choose Action", "href": "#/standard/action"},
                 {"text": "Review & Save", "href": "#/standard/save"}];
	menuItems[position].status = "disabled";
	return menuItems;
};