<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hed="urn:hl7-org:knowledgeartifact:r1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"
		encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
		doctype-public="-//W3C//DTD HTML 4.01//EN" />
	<!-- Main -->
	<xsl:include href="stylesheet/expressions-to-narrative.xsl" />
	<xsl:template match="/">
		<xsl:apply-templates select="hed:knowledgeDocument" />
	</xsl:template>

	<xsl:template match="hed:knowledgeDocument">
		<html>
			<head>
				<title>
					<xsl:value-of select="hed:metadata/hed:title/@value" />
				</title>
			</head>
			<body>
				<h1>Metadata</h1>
				<xsl:apply-templates select="hed:metadata" />
				<h1>
					<xsl:value-of select="hed:metadata/hed:artifactType/@value" />
				</h1>

				<h2>Conditions</h2>
				<xsl:for-each select="hed:conditions/hed:condition">
					<xsl:call-template name="ConditionTempl">
						<xsl:with-param name="condition" select="." />
					</xsl:call-template>
				</xsl:for-each>

				<h2>Actions</h2>
				<xsl:call-template name="ActionGroupTempl">
					<xsl:with-param name="value">
						<xsl:value-of select="." />
					</xsl:with-param>
					<xsl:with-param name="editable">
						<xsl:value-of select="." />
					</xsl:with-param>
				</xsl:call-template>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="hed:metadata">
		<table>
			<!-- identifiers -->
			<tr>
				<td>Identifiers</td>
				<td>
					<ul>
						<xsl:for-each select="hed:identifiers/hed:identifier">
							<li>
								<xsl:value-of select="@root" />
								<xsl:if test="@extension">
									<xsl:text>, extension </xsl:text>
									<xsl:value-of select="@version" />
								</xsl:if>
								<xsl:if test="@version">
									<xsl:text>, version </xsl:text>
									<xsl:value-of select="@version" />
								</xsl:if>
							</li>
						</xsl:for-each>
					</ul>
				</td>
			</tr>
			<!-- title -->
			<tr>
				<td>Title</td>
				<td>
					<xsl:value-of select="hed:title/@value" />
				</td>
			</tr>

			<!-- Related resources -->
			<tr>
				<td>Related resources</td>
				<td>
					<xsl:for-each select="hed:relatedResources/hed:relatedResource">
						<xsl:value-of select="hed:relationship/@value" />
						<ol>
							<li>
								<xsl:for-each select="hed:resources/hed:resource">
									<xsl:choose>
										<xsl:when test="hed:location">
											<xsl:element name="a">
												<xsl:attribute name="href">
        											<xsl:value-of select="hed:location/@value" />
    											</xsl:attribute>
												<xsl:value-of select="hed:title/@value" />
											</xsl:element>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="hed:title/@value" />
										</xsl:otherwise>
									</xsl:choose>
									<xsl:if test="hed:citation">
										<xsl:text> (</xsl:text>
										<xsl:value-of select="hed:citation/@value" />
										<xsl:text>)</xsl:text>
									</xsl:if>
								</xsl:for-each>
							</li>
						</ol>
					</xsl:for-each>
				</td>
			</tr>
			<!-- Applicability -->
			<tr>
				<td>Applicable in</td>
				<td>
					<xsl:for-each select="hed:applicability/hed:coverage">
						<xsl:value-of select="hed:focus/@value"/>
						<xsl:text>: </xsl:text>
						<xsl:call-template name="CodeTempl">
							<xsl:with-param name="code" select="hed:value"/>
						</xsl:call-template>
					</xsl:for-each>
				</td>
			</tr>
			<!-- Status -->
			<tr>
				<td>Current status</td>
				<td>
					<xsl:value-of select="hed:status/@value"/>				
				</td>
			</tr>
			<!-- Life cycle -->
			<tr>
				<td><xsl:value-of select="hed:artifactType/@value"/><xsl:text> history</xsl:text></td>
				<td> 
					<xsl:for-each select="hed:eventHistory/hed:artifactLifeCycleEvent">
						<xsl:value-of select="hed:eventType/@value"/><xsl:text>: </xsl:text><xsl:value-of select="hed:eventDateTime/@value"/><br/>
					</xsl:for-each>
				</td>
			</tr>
			
			<!-- Contributions -->
			<tr>
				<td>Contributions</td>
				<td>
					<xsl:for-each select="hed:contributions/hed:contribution">
						<xsl:value-of select="hed:role/@value"/>
						<xsl:text>: </xsl:text>
						<xsl:call-template name="OrganizationTempl">
							<xsl:with-param name="organization" select="hed:contributor">
							</xsl:with-param>
						</xsl:call-template>
						<br/>
					</xsl:for-each>
				
				</td>
			</tr>
			
			<!--  Usage terms -->
			<tr>
				<td>Usage terms</td>
				<td>
					<xsl:for-each select="hed:usageTerms/hed:rightsDeclaration">
						<table>
							<tr>
								<td>Assertion: </td>
								<td>
									<xsl:value-of select="hed:assertedRights/@value" />
								</td>
							</tr>
							<tr>
								<td>Rights holder: </td>
								<td>
									<xsl:call-template name="OrganizationTempl">
										<xsl:with-param name="organization" select="hed:rightsHolder" />
									</xsl:call-template>
								</td>
							</tr>
							<tr>
								<td>Permissions: </td>
								<td>
									<xsl:for-each select="hed:permissions/hed:permissions">
										<xsl:value-of select="@value" />
										<br />
									</xsl:for-each>
								</td>
							</tr>
						</table>

					</xsl:for-each>
				</td>

			</tr>
		</table>
	</xsl:template>

	<xsl:template name="ConditionTempl">
		<xsl:param name="condition" />
		<xsl:text>Condition: </xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$condition/hed:logic" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="OrganizationTempl">
		<xsl:param name="organization" />
		<xsl:value-of select="$organization/hed:name/@value" />
	</xsl:template>

	<xsl:template name="CodeTempl">
		<xsl:param name="code" />
		<xsl:value-of select="$code/@displayName"/>
		<xsl:text> (</xsl:text>
		<xsl:value-of select="$code/@codeSystemName"/>
		<xsl:text> code </xsl:text>
		<xsl:value-of select="$code/@code" />
		<xsl:text>)</xsl:text>
	</xsl:template>


	<xsl:template name="ActionGroupTempl">
		<xsl:param name="editable" />
		<xsl:param name="value" />
		<table>
			<tr>
				<td>
					<b>
						<xsl:value-of select="hed:actionGroups/hed:title/@value" />
					</b>
				</td>
			</tr>
			<tr>
				<td>
					<i>
						<xsl:value-of select="hed:actionGroups/hed:description/@value" />
					</i>
				</td>
			</tr>

			<xsl:for-each select="hed:actionGroups/hed:subElements/hed:simpleAction">
				<tr>
					<xsl:if test="@xsi:type='CollectInformationAction'">
						<td>
							<xsl:value-of select="hed:documentationConcept/hed:displayText/@value" />
							<xsl:if test="hed:documentationConcept/hed:itemCodes">
								<br />
								<xsl:text>Concept codes are: </xsl:text>
								<ul>
									<xsl:for-each
										select="hed:documentationConcept/hed:itemCodes/hed:itemCode">
										<li>
											<xsl:call-template name="CodeLiteral">
												<xsl:with-param name="code" select="./@code" />
												<xsl:with-param name="codeSystem" select="./@codeSystem" />
												<xsl:with-param name="codeSystemName" select="./@codeSystemName" />
												<xsl:with-param name="displayName" select="./@displayName" />
											</xsl:call-template>
										</li>
									</xsl:for-each>
								</ul>
							</xsl:if>
							<xsl:if test="hed:initialValue">
								<br />
								<xsl:text>Initial value is </xsl:text>
								<xsl:call-template name="ExpressionStyler">
									<xsl:with-param name="expression" select="hed:initialValue" />
								</xsl:call-template>
							</xsl:if>
						</td>
						<td>
							<xsl:if
								test="hed:documentationConcept/hed:responseRange/hed:constraintType = 'List'">
								<xsl:if
									test="hed:documentationConcept/hed:responseCardinality='Single'">
									<ul>
										<xsl:for-each
											select="hed:documentationConcept/hed:responseRange/hed:item">
											<!-- insert radio buttons here -->
											<li>
												<xsl:choose>
													<xsl:when test="hed:displayText">
														<xsl:value-of select="hed:displayText/@value" />
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="hed:value/@value" />
													</xsl:otherwise>
												</xsl:choose>
											</li>
										</xsl:for-each>
									</ul>
								</xsl:if>

								<xsl:if
									test="hed:documentationConcept/hed:responseCardinality='Multiple'">
									<ul>
										<xsl:for-each
											select="hed:documentationConcept/hed:responseRange/hed:item">
											<!-- insert check boxes here -->
											<li>
												<xsl:choose>
													<xsl:when test="hed:displayText">
														<xsl:value-of select="hed:displayText/@value" />
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="hed:value/@value" />
													</xsl:otherwise>
												</xsl:choose>
											</li>
										</xsl:for-each>
									</ul>

								</xsl:if>
							</xsl:if>
							<xsl:if test="hed:responseBinding">
								<br />
								<xsl:text>Response is bound to </xsl:text>
								<xsl:choose>
									<xsl:when test="hed:responseBinding/hed:source">
										<xsl:call-template name="ExpressionStyler">
											<xsl:with-param name="expression"
												select="hed:responseBinding/hed:source" />
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>Responses</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
								<xsl:text>.</xsl:text>
								<xsl:value-of select="hed:responseBinding/@property" />
							</xsl:if>
						</td>
					</xsl:if>
					<xsl:if test="@xsi:type != 'CollectInformationAction'">
						<td>
							<xsl:text>unknown type of action encountered!</xsl:text>
						</td>
					</xsl:if>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
</xsl:stylesheet>