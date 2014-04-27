<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hed="urn:hl7-org:knowledgeartifact:r1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"
		encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
		doctype-public="-//W3C//DTD HTML 4.01//EN" />
	<xsl:include href="stylesheet/expressions-to-narrative.xsl" />
	<xsl:include href="stylesheet/components.xsl" />
	<xsl:template name="ActionGroupTempl">
		<xsl:param name="group" />
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">
					<xsl:value-of select="$group/hed:title/@value" />
					<xsl:text> </xsl:text>
					<span class="badge">
						<xsl:value-of select="count($group/hed:subElements/*)" />
					</span>
				</h3>
			</div>
			<!-- <div class="panel panel-body"> -->
			<table class="table">
				<xsl:if test="group/hed:supportingResources">
					<tr>
						<td width="20%">Resources</td>
						<td>
							<xsl:call-template name="ResourcesTempl">
								<xsl:with-param name="resources"
									select="$group/hed:supportingResources" />
							</xsl:call-template>
						</td>
					</tr>
				</xsl:if>
				<xsl:if test="$group/hed:behaviors">
					<tr>
						<td width="20%">Behaviors</td>
						<td>
							<xsl:call-template name="BehaviorsTempl">
								<xsl:with-param name="behaviors" select="$group/hed:behaviors" />
							</xsl:call-template>
						</td>
					</tr>
				</xsl:if>
				<xsl:if test="$group/hed:conditions">
					<tr>
						<td width="20%">Applies when</td>
						<td>
							<xsl:call-template name="ExpressionStyler">
								<xsl:with-param name="expression"
									select="$group/hed:conditions/hed:condition/hed:logic" />
							</xsl:call-template>
						</td>
					</tr>
				</xsl:if>

				<xsl:for-each select="$group/hed:subElements/*">
					<tr>
						<td colspan="2">
							<div>
								<xsl:choose>
									<xsl:when test="name(.) = 'actionGroup'">
										<xsl:call-template name="ActionGroupTempl">
											<xsl:with-param name="group" select="." />
										</xsl:call-template>
									</xsl:when>

									<xsl:when test="name(.) = 'simpleAction'">
										<xsl:call-template name="SimpleActionTempl">
											<xsl:with-param name="action" select="." />
										</xsl:call-template>
									</xsl:when>
								</xsl:choose>
							</div>
						</td>
					</tr>
				</xsl:for-each>
			</table>
			<!-- </div> -->
		</div>
	</xsl:template>


	<xsl:template name="SimpleActionTempl">
		<xsl:param name="action" />
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<xsl:choose>
						<xsl:when test="$action/hed:textEquivalent">
							<xsl:value-of select="$action/hed:textEquivalent/@value" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>Untitled action</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</h4>
			</div>
			<table class="table">
				<xsl:if test="$action/hed:supportingResources">
					<tr>
						<td width="20%">Resources</td>
						<td>
							<xsl:call-template name="ResourcesTempl">
								<xsl:with-param name="resources"
									select="$action/hed:supportingResources" />
							</xsl:call-template>
						</td>
					</tr>
				</xsl:if>
				<xsl:if test="$action/hed:behaviors">
					<tr>
						<td width="20%">Behaviors</td>
						<td>
							<xsl:call-template name="BehaviorsTempl">
								<xsl:with-param name="behaviors" select="$action/hed:behaviors" />
							</xsl:call-template>
						</td>
					</tr>
				</xsl:if>
				<xsl:if test="$action/hed:conditions">
					<tr>
						<td width="20%">Applies when</td>
						<td>
							<xsl:call-template name="ExpressionStyler">
								<xsl:with-param name="expression"
									select="$action/hed:conditions/hed:condition/hed:logic" />
							</xsl:call-template>
						</td>
					</tr>
				</xsl:if>
				<tr>
					<td colspan="2">
						<xsl:choose>
							<xsl:when test="$action/@xsi:type = 'CreateAction'">
								<xsl:call-template name="CreateActionTempl">
									<xsl:with-param name="actionSentence"
										select="$action/hed:actionSentence" />
								</xsl:call-template>
							</xsl:when>
							<xsl:when test="$action/@xsi:type = 'CollectInformationAction'">
								<xsl:call-template name="CollectInformationActionTempl">
									<xsl:with-param name="documentationConcept"
										select="$action/hed:documentationConcept" />
								</xsl:call-template>
							</xsl:when>
						</xsl:choose>
					</td>
				</tr>
			</table>
		</div>
	</xsl:template>

	<xsl:template name="CreateActionTempl">
		<xsl:param name="actionSentence" />
		<xsl:text>New </xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$actionSentence" />
		</xsl:call-template>
	</xsl:template>


	<xsl:template name="CollectInformationActionTempl">
		<xsl:param name="documentationConcept" />
		<xsl:text>CDS recipient must provide the following information </xsl:text>
		<xsl:call-template name="DocumentationConceptTempl">
			<xsl:with-param name="concept" select="$documentationConcept" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="DocumentationConceptTempl">
		<xsl:param name="concept" />
		<dl class="dl-horizontal">
			<div>
				<dt>Question text</dt>
				<dd>
					<xsl:value-of select="$concept/hed:displayText/@value" />
				</dd>
			</div>
		</dl>
		<dl class="dl-horizontal">
			<div>
				<dt>Number of responses</dt>
				<dd>
					<xsl:value-of select="$concept/hed:responseCardinality/@value" />
				</dd>
			</div>
		</dl>
		<dl class="dl-horizontal">
			<div>
				<dt>Response is of type</dt>
				<dd>
					<xsl:value-of select="$concept/hed:responseDataType/@value" />
				</dd>
			</div>
		</dl>
		<xsl:if test="$concept/hed:responseRange">
			<dl class="dl-horizontal">
				<div>
					<dt>Response values</dt>
					<dd>
						<xsl:choose>
							<xsl:when
								test="$concept/hed:responseRange/@xsi:type = 'ListConstraint'">
								<xsl:call-template name="ListConstraintTempl">
									<xsl:with-param name="constraint"
										select="$concept/hed:responseRange" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
							</xsl:otherwise>
						</xsl:choose>
					</dd>
				</div>
			</dl>
		</xsl:if>
	</xsl:template>

	<xsl:template name="ListConstraintTempl">
		<xsl:param name="constraint" />
		<xsl:choose>
			<xsl:when test="$constraint/@strictSelection = 'true'">
				<xsl:text>Must be one of</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>May be one of</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<ul>
			<xsl:for-each select="$constraint/hed:item">
				<li>
					<xsl:value-of select="hed:displayText/@value" />
					<small>
						<xsl:text> [</xsl:text>
							<xsl:call-template name="ExpressionStyler">
								<xsl:with-param name="expression" select="hed:value"/>
							</xsl:call-template>
						<xsl:text>]</xsl:text> 
					</small>
				</li>
			</xsl:for-each>
		</ul>
	</xsl:template>
</xsl:stylesheet>
