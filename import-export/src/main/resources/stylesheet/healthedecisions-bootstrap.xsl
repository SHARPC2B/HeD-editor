<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hed="urn:hl7-org:v3:knowledgeartifact:r1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"
		encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
		doctype-public="-//W3C//DTD HTML 4.01//EN" />
	<!-- Main -->
	<xsl:include href="stylesheet/action.xsl" />

	<xsl:template match="/">
		<xsl:apply-templates select="hed:knowledgeDocument" />
	</xsl:template>

	<xsl:template match="hed:knowledgeDocument">
		<html>
			<head>
				<title>
					<xsl:value-of select="hed:metadata/hed:title/@value" />
				</title>

				<meta name="viewport" content="width=device-width, initial-scale=1.0" />
				<!-- Bootstrap -->
				<!-- Latest compiled and minified CSS -->
				<link rel="stylesheet"
					href="../bootstrap/3.0.0/css/bootstrap.min.css" />

				<!-- Optional theme -->
				<link rel="stylesheet"
					href="../bootstrap/3.0.0/css/bootstrap-theme.min.css" />

				<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media 
					queries -->
				<!--[if lt IE 9]> <script src="../../assets/js/html5shiv.js"></script> 
					<script src="../../assets/js/respond.min.js"></script> <![endif] -->
			</head>
			<body>
				<div class="container">
					<div class="page-header">
						<xsl:element name="h1">
							<xsl:value-of select="hed:metadata/hed:title/@value" />
							<xsl:text> </xsl:text>
							<xsl:element name="small">
								<xsl:value-of select="hed:metadata/hed:artifactType/@value" />
								<xsl:text> with id </xsl:text>
								<xsl:call-template name="IdentifierAsTextTempl">
									<xsl:with-param name="identifier"
										select="hed:metadata/hed:identifiers/hed:identifier" />
								</xsl:call-template>
							</xsl:element>
						</xsl:element>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h2 class="panel-title">Metadata</h2>
						</div>
						<div class="panel-body">
							<xsl:apply-templates select="hed:metadata" />
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h2 class="panel-title">Triggers</h2>
						</div>
						<div class="list-group">
							<xsl:for-each select="hed:triggers/hed:trigger">
								<a href="#" class="list-group-item">
									<xsl:call-template name="TriggerTempl">
										<xsl:with-param name="trigger" select="." />
									</xsl:call-template>
								</a>
							</xsl:for-each>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h2 class="panel-title">
								<xsl:text>Condition </xsl:text>
								<small>
									<xsl:value-of
										select="hed:conditions/hed:condition/hed:logic/hed:description" />
								</small>
							</h2>
						</div>
						<div class="panel-body">
							<xsl:call-template name="ExpressionStyler">
								<xsl:with-param name="expression"
									select="hed:conditions/hed:condition/hed:logic" />
							</xsl:call-template>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h2 class="panel-title">Recommended actions</h2>
						</div>
						<div class="panel-body">
							<xsl:call-template name="ActionGroupTempl">
								<xsl:with-param name="group" select="hed:actionGroup" />
							</xsl:call-template>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h2 class="panel-title">Data definitions</h2>
						</div>
						<div class="panel-body">
							<xsl:for-each select="hed:externalData/hed:def">
								<xsl:call-template name="ExternalDataTempl">
									<xsl:with-param name="def" select="." />
								</xsl:call-template>
							</xsl:for-each>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h2 class="panel-title">Term definitions</h2>
						</div>
						<div class="panel-body">
							<xsl:for-each select="hed:expressions/hed:def">
								<xsl:call-template name="ExpressionRefTempl">
									<xsl:with-param name="def" select="." />
								</xsl:call-template>
							</xsl:for-each>
						</div>
					</div>
				</div>
				<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
				<script src="//code.jquery.com/jquery.js"></script>
				<!-- Include all compiled plugins (below), or include individual files 
					as needed -->
				<script
					src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="hed:metadata">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Related Resources</h3>
			</div>
			<xsl:for-each select="hed:relatedResources/hed:relatedResource">
				<xsl:call-template name="ResourcesTempl">
					<xsl:with-param name="resources" select="hed:resources" />
				</xsl:call-template>
			</xsl:for-each>
		</div>


		<!-- Applicability -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Applicability</h3>
			</div>
			<dl class="dl-horizontal">
				<xsl:for-each select="hed:applicability/hed:coverage">
					<dt>
						<xsl:value-of select="hed:focus/@value" />
					</dt>
					<dd>
						<xsl:call-template name="CodeAsHoverTempl">
							<xsl:with-param name="code" select="hed:value" />
						</xsl:call-template>
					</dd>
				</xsl:for-each>
			</dl>
		</div>

		<!-- Status and lifecycle -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<xsl:text>Current status: </xsl:text>
					<xsl:value-of select="hed:status/@value" />
				</h3>
			</div>
			<dl class="dl-horizontal">
				<xsl:for-each select="hed:eventHistory/hed:artifactLifeCycleEvent">
					<dt>
						<xsl:value-of select="hed:eventType/@value" />
					</dt>
					<dd>
						<xsl:value-of select="hed:eventDateTime/@value" />
					</dd>
				</xsl:for-each>
			</dl>
		</div>


		<!-- Contributions -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Contributions</h3>
			</div>
			<table class="table">
				<xsl:for-each select="hed:contributions/hed:contribution">
					<tr>
						<td>
							<xsl:call-template name="OrganizationTempl">
								<xsl:with-param name="organization" select="hed:contributor" />
							</xsl:call-template>
							<xsl:text>  </xsl:text>
							<small>
								<xsl:value-of select="hed:role/@value" />
							</small>
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</div>

		<!-- Usage -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Usage terms</h3>
			</div>
			<table class="table">
				<xsl:for-each select="hed:usageTerms/hed:rightsDeclaration">
					<tr>
						<td>
							<xsl:value-of select="hed:assertedRights/@value" />
							<xsl:text> by </xsl:text>
							<xsl:call-template name="OrganizationTempl">
								<xsl:with-param name="organization" select="hed:rightsHolder" />
							</xsl:call-template>
							<br />
							<small>
								<xsl:for-each select="hed:permissions/hed:permissions">
									<xsl:value-of select="@value" />
									<br />
								</xsl:for-each>
							</small>
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</div>
	</xsl:template>

</xsl:stylesheet>
