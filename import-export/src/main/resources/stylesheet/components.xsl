<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hed="urn:hl7-org:v3:knowledgeartifact:r1"
	xmlns:dt="urn:hl7-org:v3:cdsdt:r2" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"
		encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
		doctype-public="-//W3C//DTD HTML 4.01//EN" />

	<xsl:template name="IdentifierAsTextTempl">
		<xsl:param name="identifier" />
		<xsl:value-of select="$identifier/@root" />
		<xsl:if test="$identifier/@extension">
			<xsl:text>, extension </xsl:text>
			<xsl:value-of select="$identifier/@version" />
		</xsl:if>
		<xsl:if test="$identifier/@version">
			<xsl:text>, version </xsl:text>
			<xsl:value-of select="$identifier/@version" />
		</xsl:if>
	</xsl:template>

	<xsl:template name="CodeAsHoverTempl">
		<xsl:param name="code" />
		<xsl:element name="abbr">
			<xsl:attribute name="title">
			<xsl:value-of select="$code/@codeSystemName" />
			<xsl:text> code </xsl:text>
			<xsl:value-of select="$code/@code" />
			</xsl:attribute>
			<xsl:value-of select="$code/@displayName" />
		</xsl:element>
	</xsl:template>

	<xsl:template name="BehaviorsTempl">
		<xsl:param name="behaviors" />
		<table class="table">
			<xsl:for-each select="$behaviors/hed:behavior">
				<tr>
					<td>
						<xsl:value-of select="@xsi:type"/>
					</td>
					<td>
						<xsl:value-of select="@value"/>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="ResourcesTempl">
		<xsl:param name="resources" />
		<table class="table">
			<xsl:for-each select="$resources/hed:resource">
				<!-- <a href="#" class="list-group-item"> -->
				<tr><td>
					<xsl:value-of select="hed:title/@value" />
					<br />
					<small>
						<xsl:if test="hed:citation">
							<xsl:value-of select="hed:citation/@value" />
							<br />
						</xsl:if>
						<xsl:if test="hed:location">
							<xsl:value-of select="hed:location/@value" />
						</xsl:if>
					</small>
				</td></tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template name="OrganizationTempl">
		<xsl:param name="organization" />
		<xsl:element name="abbr">
			<xsl:attribute name="title">
				<xsl:for-each select="$organization/hed:addresses/hed:address">
				<xsl:call-template name="AddressTempl">
					<xsl:with-param name="address" select="." />
				</xsl:call-template>
				<br />
				</xsl:for-each>
			</xsl:attribute>
			<xsl:value-of select="$organization/hed:name/@value" />
		</xsl:element>
	</xsl:template>

	<xsl:template name="AddressTempl">
		<xsl:param name="address" />
		<xsl:value-of select="$address/dt:part[@type='SAL']/@value"/><xsl:text>, </xsl:text>
		<xsl:value-of select="$address/dt:part[@type='CTY']/@value"/><xsl:text>, </xsl:text>
		<xsl:value-of select="$address/dt:part[@type='STA']/@value"/><xsl:text>, </xsl:text>
		<xsl:value-of select="$address/dt:part[@type='ZIP']/@value"/>
	</xsl:template>

</xsl:stylesheet>