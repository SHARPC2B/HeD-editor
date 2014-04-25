<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hed="urn:hl7-org:v3:knowledgeartifact:r1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"
		encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
		doctype-public="-//W3C//DTD HTML 4.01//EN" />
	<xsl:include href="components.xsl" />
	<xsl:template match="hed:metadata">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Related Resources</h3>
			</div>
			<xsl:call-template name="RelatedResourcesTempl">
				<xsl:with-param name="resources" select="hed:relatedResources" />
			</xsl:call-template>
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
			<div class="list-group">
				<xsl:for-each select="hed:contributions/hed:contribution">
					<a href="#" class="list-group-item">
						<xsl:call-template name="OrganizationTempl">
							<xsl:with-param name="organization" select="hed:contributor" />
						</xsl:call-template>
						<xsl:text>  </xsl:text>
						<small>
							<xsl:value-of select="hed:role/@value" />
						</small>
					</a>
				</xsl:for-each>
			</div>
		</div>

		<!-- Usage -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Usage terms</h3>
			</div>
			<div class="list-group">
				<xsl:for-each select="hed:usageTerms/hed:rightsDeclaration">
					<a href="#" class="list-group-item">
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
					</a>
				</xsl:for-each>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>
