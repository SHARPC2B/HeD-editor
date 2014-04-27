<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hed="urn:hl7-org:knowledgeartifact:r1"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"
		encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
		doctype-public="-//W3C//DTD HTML 4.01//EN" />

	<xsl:template name="TriggerTempl">
		<xsl:param name="trigger" />
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>when </xsl:text>
				<xsl:value-of select="$trigger/hed:eventType/text()" />
				<xsl:text> </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$trigger/hed:expression" />
		</xsl:call-template>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' occurs'" />
		</xsl:call-template>
	</xsl:template>


	<xsl:template name="ExternalDataTempl">
		<xsl:param name="def" />
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<xsl:value-of select="$def/@name" />
					<small>
						<!-- <xsl:text> [</xsl:text> <xsl:value-of select="$def/hed:expression/@dataType" 
							/> <xsl:text>] </xsl:text> -->
						<xsl:text> </xsl:text>
						<xsl:if test="$def/hed:expression/hed:description">
							<xsl:value-of select="$def/hed:expression/hed:description" />
						</xsl:if>
					</small>
				</h3>
			</div>
			<dl class="dl-horizontal">
				<dt>maps to</dt>
				<dd>
					<xsl:value-of select="$def/hed:expression/@dataType" />
				</dd>
				<xsl:if test="$def/hed:expression/hed:codes">
					<dt>with codes</dt>
					<dd>
						<xsl:call-template name="ExpressionStyler">
							<xsl:with-param name="expression"
								select="$def/hed:expression/hed:codes" />
						</xsl:call-template>
					</dd>
				</xsl:if>
				<xsl:if test="$def/hed:expression/hed:dateRange">
					<dt>in date range</dt>
					<dd>
						<xsl:call-template name="ExpressionStyler">
							<xsl:with-param name="expression"
								select="$def/hed:expression/hed:dateRange" />
						</xsl:call-template>
					</dd>
				</xsl:if>

			</dl>
		</div>
	</xsl:template>


	<xsl:template name="ExpressionRefTempl">
		<xsl:param name="def" />
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<xsl:value-of select="$def/@name" />
					<xsl:text> </xsl:text>
					<small>
						<xsl:if test="$def/hed:expression/hed:description">
							<xsl:value-of select="$def/hed:expression/hed:description" />
						</xsl:if>
					</small>
				</h3>
			</div>
			<div class="panel-body">
				<xsl:call-template name="ExpressionStyler">
					<xsl:with-param name="expression" select="$def/hed:expression" />
				</xsl:call-template>
			</div>
		</div>

	</xsl:template>

	<xsl:template name="ExpressionStyler">
		<xsl:param name="expression" />
		<xsl:choose>
			<xsl:when test="$expression/@xsi:type='After'">
				<xsl:call-template name="BinaryExpression">
					<xsl:with-param name="operator" select="' occurs after '"></xsl:with-param>
					<xsl:with-param name="operand1" select="$expression/hed:operand[1]" />
					<xsl:with-param name="operand2" select="$expression/hed:operand[2]" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='And'">
				<xsl:call-template name="NaryExpression">
					<xsl:with-param name="operator" select="'and'" />
					<xsl:with-param name="operands" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='As'">
				<xsl:call-template name="UnaryExpression">
					<!-- do not show the casting -->
					<xsl:with-param name="operator" select="''"></xsl:with-param>
					<xsl:with-param name="operand" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Before'">
				<xsl:call-template name="BinaryExpression">
					<xsl:with-param name="operator" select="' occurs before '"></xsl:with-param>
					<xsl:with-param name="operand1" select="$expression/hed:operand[1]" />
					<xsl:with-param name="operand2" select="$expression/hed:operand[2]" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='BooleanLiteral'">
				<xsl:call-template name="PrimitiveLiteral">
					<xsl:with-param name="value" select="$expression/@value" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Combine'">
				<xsl:call-template name="Combine">
					<xsl:with-param name="source" select="$expression/hed:source" />
					<xsl:with-param name="separator" select="$expression/hed:separator" />
				</xsl:call-template>
			</xsl:when>
			
			<xsl:when test="$expression/@xsi:type='CodeLiteral'">
				<xsl:call-template name="CodeLiteral">
					<xsl:with-param name="code" select="$expression/@code" />
					<xsl:with-param name="codeSystem" select="$expression/@codeSystem" />
					<xsl:with-param name="codeSystemName" select="$expression/@codeSystemName" />
					<xsl:with-param name="displayName" select="$expression/@displayName" />

				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='ComplexLiteral'">
				<xsl:call-template name="ComplexLiteral">
					<xsl:with-param name="expression" select="$expression" />
				</xsl:call-template>
			</xsl:when>


			<xsl:when test="$expression/@xsi:type='Concat'">
				<xsl:call-template name="NaryExpression">
					<xsl:with-param name="operator" select="' + '" />
					<xsl:with-param name="operands" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Conditional'">
				<xsl:call-template name="Conditional">
					<xsl:with-param name="condition" select="$expression/hed:condition" />
					<xsl:with-param name="then" select="$expression/hed:then" />
					<xsl:with-param name="else" select="$expression/hed:else" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Count'">
				<xsl:call-template name="AggregateExpression">
					<xsl:with-param name="operator" select="'count'" />
					<xsl:with-param name="operand" select="$expression/hed:source" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Current'">
				<xsl:call-template name="Current" />
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='DateAdd'">
				<xsl:call-template name="DateAdd">
					<xsl:with-param name="date" select="$expression/hed:date" />
					<xsl:with-param name="numberOfPeriods"
						select="$expression/hed:numberOfPeriods" />
					<xsl:with-param name="granularity" select="$expression/hed:granularity" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='DateDiff'">
				<xsl:call-template name="DateDiff">
					<xsl:with-param name="startDate" select="$expression/hed:startDate" />
					<xsl:with-param name="endDate" select="$expression/hed:endDate" />
					<xsl:with-param name="granularity" select="$expression/hed:granularity" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Equal'">
				<xsl:call-template name="BinaryExpression">
					<xsl:with-param name="operator" select="' = '"></xsl:with-param>
					<xsl:with-param name="operand1" select="$expression/hed:operand[1]" />
					<xsl:with-param name="operand2" select="$expression/hed:operand[2]" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='ExpressionRef'">
				<xsl:call-template name="ExpressionRef">
					<xsl:with-param name="name" select="$expression/@name" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Filter'">
				<xsl:call-template name="Filter">
					<xsl:with-param name="source" select="$expression/hed:source" />
					<xsl:with-param name="condition" select="$expression/hed:condition" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='First'">
				<xsl:call-template name="First">
					<xsl:with-param name="source" select="$expression/hed:source" />
					<xsl:with-param name="orderBy" select="$expression/@orderBy" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='ForEach'">
				<xsl:call-template name="ForEach">
					<xsl:with-param name="source" select="$expression/hed:source" />
					<xsl:with-param name="element" select="$expression/hed:element" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Greater'">
				<xsl:call-template name="BinaryExpression">
					<xsl:with-param name="operator" select="' > '"></xsl:with-param>
					<xsl:with-param name="operand1" select="$expression/hed:operand[1]" />
					<xsl:with-param name="operand2" select="$expression/hed:operand[2]" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='GreaterOrEqual'">
				<xsl:call-template name="BinaryExpression">
					<xsl:with-param name="operator" select="' >= '"></xsl:with-param>
					<xsl:with-param name="operand1" select="$expression/hed:operand[1]" />
					<xsl:with-param name="operand2" select="$expression/hed:operand[2]" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='IntegerIntervalLiteral'">
				<xsl:call-template name="PrimitiveIntervalLiteral">
					<xsl:with-param name="low" select="$expression/hed:low" />
					<xsl:with-param name="high" select="$expression/hed:high" />
					<xsl:with-param name="lowIsInclusive" select="$expression/@lowIsInclusive" />
					<xsl:with-param name="highIsInclusive" select="$expression/@highIsInclusive" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Interval'">
				<xsl:call-template name="Interval">
					<xsl:with-param name="begin" select="$expression/hed:begin" />
					<xsl:with-param name="end" select="$expression/hed:end" />
					<xsl:with-param name="beginOpen" select="$expression/@beginOpen" />
					<xsl:with-param name="endOpen" select="$expression/@endOpen" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='IntegerLiteral'">
				<xsl:call-template name="PrimitiveLiteral">
					<xsl:with-param name="value" select="$expression/@value" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Is'">
				<xsl:call-template name="Is">
					<xsl:with-param name="operand" select="$expression/hed:operand" />
					<xsl:with-param name="type" select="$expression/@isType " />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='IsEmpty'">
				<xsl:call-template name="UnaryExpression">
					<xsl:with-param name="operator" select="' has no items'" />
					<xsl:with-param name="operand" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='IsNotEmpty'">
				<xsl:call-template name="UnaryExpression">
					<xsl:with-param name="operator" select="' has one or more items'" />
					<xsl:with-param name="operand" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='IsNull'">
				<xsl:call-template name="UnaryExpression">
					<xsl:with-param name="operator" select="' has no value'" />
					<xsl:with-param name="operand" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Last'">
				<xsl:call-template name="Last">
					<xsl:with-param name="source" select="$expression/hed:source" />
					<xsl:with-param name="orderBy" select="$expression/@orderBy" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='List'">
				<xsl:call-template name="List">
					<xsl:with-param name="elements" select="$expression/hed:element" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Literal'">
				<xsl:call-template name="PrimitiveLiteral">
					<xsl:with-param name="value" select="$expression/@value" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Max'">
				<xsl:call-template name="AggregateExpression">
					<xsl:with-param name="operator" select="'maximum'" />
					<xsl:with-param name="operand" select="$expression/hed:source" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Min'">
				<xsl:call-template name="AggregateExpression">
					<xsl:with-param name="operator" select="'minimum'" />
					<xsl:with-param name="operand" select="$expression/hed:source" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Not'">
				<xsl:call-template name="UnaryExpression">
					<xsl:with-param name="operator" select="' is not true'" />
					<xsl:with-param name="operand" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Null'">
				<xsl:call-template name="Null" />
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='ObjectExpression'">
				<xsl:call-template name="ObjectExpression">
					<xsl:with-param name="expression" select="$expression" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Or'">
				<xsl:call-template name="NaryExpression">
					<xsl:with-param name="operator" select="'or'" />
					<xsl:with-param name="operands" select="$expression/hed:operand" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Property'">
				<xsl:call-template name="Property">
					<xsl:with-param name="path" select="$expression/@path" />
					<xsl:with-param name="source" select="$expression/hed:source" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='RealIntervalLiteral'">
				<xsl:call-template name="PrimitiveIntervalLiteral">
					<xsl:with-param name="low" select="$expression/hed:low" />
					<xsl:with-param name="high" select="$expression/hed:high" />
					<xsl:with-param name="lowIsInclusive" select="$expression/@lowIsInclusive" />
					<xsl:with-param name="highIsInclusive" select="$expression/@highIsInclusive" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='RealLiteral'">
				<xsl:call-template name="PrimitiveLiteral">
					<xsl:with-param name="value" select="$expression/@value" />
				</xsl:call-template>
			</xsl:when>


			<xsl:when test="$expression/@xsi:type='StringLiteral'">
				<xsl:call-template name="StringLiteral">
					<xsl:with-param name="value" select="$expression/@value" />
				</xsl:call-template>
			</xsl:when>


			<xsl:when test="$expression/@xsi:type='Sum'">
				<xsl:call-template name="Sum">
					<xsl:with-param name="source" select="$expression/hed:source" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='Today'">
				<xsl:call-template name="Today" />
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='ParameterRef'">
				<xsl:call-template name="ParameterRef">
					<xsl:with-param name="name" select="$expression/@name" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type='ValueSet'">
				<xsl:call-template name="ValueSet">
					<xsl:with-param name="id" select="$expression/@id" />
					<xsl:with-param name="version" select="$expression/@version" />
					<xsl:with-param name="authority" select="$expression/@authority" />
				</xsl:call-template>
			</xsl:when>

			<xsl:when test="$expression/@xsi:type">
				<xsl:call-template name="TokenStyler">
					<xsl:with-param name="token" select="'unspecified'" />
				</xsl:call-template>
			</xsl:when>

			<xsl:otherwise>
				<xsl:text>(Not yet implemented styler for </xsl:text>
				<xsl:value-of select="$expression/@xsi:type" />
				<xsl:text>)</xsl:text>
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>


	<xsl:template name="BinaryExpression">
		<xsl:param name="operator" />
		<xsl:param name="operand1" />
		<xsl:param name="operand2" />

		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$operand1" />
		</xsl:call-template>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="$operator" />
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$operand2" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
	</xsl:template>

	<xsl:template name="CodeLiteral">
		<xsl:param name="codeSystem" />
		<xsl:param name="code" />
		<xsl:param name="codeSystemName" />
		<xsl:param name="displayName" />

		<xsl:text>(code </xsl:text>
		<xsl:if test="$code">
			<xsl:value-of select="$code" />
			<xsl:text> </xsl:text>
		</xsl:if>
		<xsl:if test="$displayName">
			<xsl:text>for "</xsl:text>
			<xsl:value-of select="$displayName" />
			<xsl:text>" </xsl:text>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="$codeSystemName">
				<xsl:text>from </xsl:text>
				<xsl:value-of select="$codeSystemName" />
			</xsl:when>
			<xsl:when test="$codeSystem">
				<xsl:text>from </xsl:text>
				<xsl:value-of select="$codeSystem" />
			</xsl:when>
		</xsl:choose>
		<xsl:text>)</xsl:text>

	</xsl:template>


	<xsl:template name="Combine">
		<xsl:param name="source" />
		<xsl:param name="separator" />

		<xsl:text>(</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>join elements from </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$source" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text> into a text string, each separated by </xsl:text>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$separator" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:text>)</xsl:text>
	</xsl:template>

	<xsl:template name="ComplexLiteral">
		<xsl:param name="expression" />
		<xsl:call-template name="DataValueStyler">
			<xsl:with-param name="dataValue" select="$expression/hed:value" />
		</xsl:call-template>

	</xsl:template>

	<xsl:template name="Conditional">
		<xsl:param name="condition" />
		<xsl:param name="then" />
		<xsl:param name="else" />

		<xsl:text>(</xsl:text>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="'if '" />
		</xsl:call-template>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$condition" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' then '" />
		</xsl:call-template>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$then" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' else '" />
		</xsl:call-template>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$else" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
		<xsl:text>)</xsl:text>

	</xsl:template>

	<xsl:template name="Current">
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal" select="'the item'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="DateAdd">
		<xsl:param name="date" />
		<xsl:param name="numberOfPeriods" />
		<xsl:param name="granularity" />
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$date" />
		</xsl:call-template>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' + '" />
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$numberOfPeriods" />
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$granularity" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
	</xsl:template>


	<xsl:template name="DateDiff">
		<xsl:param name="startDate" />
		<xsl:param name="endDate" />
		<xsl:param name="granularity" />
		<xsl:text>(</xsl:text>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$endDate" />
		</xsl:call-template>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' - '" />
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$startDate" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' in '" />
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$granularity" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
	</xsl:template>


	<xsl:template name="ExpressionRef">
		<xsl:param name="name" />
		<xsl:call-template name="TokenStyler">
			<xsl:with-param name="token" select="$name" />
		</xsl:call-template>
	</xsl:template>



	<xsl:template name="Filter">
		<xsl:param name="source" />
		<xsl:param name="condition" />
		<xsl:text>(</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>select items from </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$source" />
		</xsl:call-template>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text> where </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$condition" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

	</xsl:template>


	<xsl:template name="First">
		<xsl:param name="source" />
		<xsl:param name="orderBy" />

		<xsl:text>(</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>first element from </xsl:text>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$source" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:if test="$orderBy">

			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text>  ordered by </xsl:text>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="TokenStyler">
				<xsl:with-param name="token" select="$orderBy" />
			</xsl:call-template>
		</xsl:if>
		<xsl:text>)</xsl:text>

	</xsl:template>

	<xsl:template name="ForEach">
		<xsl:param name="source" />
		<xsl:param name="element" />

		<xsl:text>(</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>new list from </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$source" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text> by applying to each of its items this operation: </xsl:text>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$element" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:text>)</xsl:text>
	</xsl:template>

	<xsl:template name="Interval">
		<xsl:param name="begin" />
		<xsl:param name="end" />
		<xsl:param name="beginOpen" select="false" />
		<xsl:param name="endOpen" select="false" />

		<xsl:text>(</xsl:text>
		<xsl:if test="$begin">
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text>begins </xsl:text>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:if test="not ($beginOpen != true)">
				<xsl:call-template name="OperatorStyler">
					<xsl:with-param name="operator">
						<xsl:text>and includes </xsl:text>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:call-template name="ExpressionStyler">
				<xsl:with-param name="expression" select="$begin" />
			</xsl:call-template>
		</xsl:if>

		<xsl:if test="$begin and $end">
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text> and </xsl:text>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>

		<xsl:if test="$end">
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text> ends </xsl:text>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:if test="not($endOpen = true)">
				<xsl:call-template name="OperatorStyler">
					<xsl:with-param name="operator">
						<xsl:text> and includes </xsl:text>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<xsl:call-template name="ExpressionStyler">
				<xsl:with-param name="expression" select="$end" />
			</xsl:call-template>
		</xsl:if>
		<xsl:text>)</xsl:text>
	</xsl:template>


	<xsl:template name="Is">
		<xsl:param name="operand" />
		<xsl:param name="type" />

		<xsl:text>(</xsl:text>

		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$operand" />
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="' is of type '" />
		</xsl:call-template>

		
		<xsl:call-template name="TokenStyler">
			<xsl:with-param name="token" select="$type"/>
		</xsl:call-template>
		<xsl:text>)</xsl:text>

	</xsl:template>

	<xsl:template name="Last">
		<xsl:param name="source" />
		<xsl:param name="orderBy" />

		<xsl:text>(</xsl:text>

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>first element from </xsl:text>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$source" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>

		<xsl:if test="$orderBy">

			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text>  ordered by </xsl:text>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:call-template name="TokenStyler">
				<xsl:with-param name="token" select="$orderBy" />
			</xsl:call-template>
		</xsl:if>
		<xsl:text>)</xsl:text>

	</xsl:template>

	<xsl:template name="List">
		<xsl:param name="elements" />
		<xsl:for-each select="$elements">
			<xsl:call-template name="ExpressionStyler">
				<xsl:with-param name="expression" select="." />
			</xsl:call-template>
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text>, </xsl:text>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>


	<xsl:template name="ParameterRef">
		<xsl:param name="name" />
		<xsl:value-of select="$name" />
	</xsl:template>

	<xsl:template name="NaryExpression">
		<xsl:param name="operator" />
		<xsl:param name="operands" />

		<xsl:text>(</xsl:text>
		<xsl:for-each select="$operands">
			<xsl:if test="position() > 1">
				<xsl:text> </xsl:text>
				<xsl:call-template name="OperatorStyler">
					<xsl:with-param name="operator">
						<xsl:value-of select="$operator" />
					</xsl:with-param>
				</xsl:call-template>
				<xsl:text> </xsl:text>
			</xsl:if>
			<xsl:call-template name="ExpressionStyler">
				<xsl:with-param name="expression" select="." />
			</xsl:call-template>
		</xsl:for-each>
		<xsl:text>)</xsl:text>
	</xsl:template>


	<xsl:template name="Null">
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal" select="'nothing'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="ObjectExpression">
		<xsl:param name="expression" />
		<xsl:value-of select="$expression/@objectType" />
		<xsl:text> with these properties: </xsl:text>
		<xsl:for-each select="$expression/hed:property">
			<xsl:text>(</xsl:text>
			<xsl:call-template name="TypeStyler">
				<xsl:with-param name="type">
					<xsl:value-of select="@name" />
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator" select="' = '" />
			</xsl:call-template>
			<xsl:call-template name="ExpressionStyler">
				<xsl:with-param name="expression" select="hed:value" />
			</xsl:call-template>
			<xsl:text>)</xsl:text>
		</xsl:for-each>
	</xsl:template>


	<xsl:template name="PrimitiveIntervalLiteral">
		<xsl:param name="low" />
		<xsl:param name="high" />
		<xsl:param name="lowIsInclusive" select="false" />
		<xsl:param name="highIsInclusive" select="false" />

		<xsl:text>(</xsl:text>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>from </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:if test="$lowIsInclusive= true">
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text>and including </xsl:text>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:value-of select="$low" />
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text> to </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:if test="$highIsInclusive= true">
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator">
					<xsl:text>and including </xsl:text>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
		<xsl:value-of select="$high" />
	</xsl:template>

	<xsl:template name="PrimitiveLiteral">
		<xsl:param name="value" />
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal" select="$value" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="Property">
		<xsl:param name="path" />
		<xsl:param name="source" />

		<xsl:if test="$source">
			<xsl:call-template name="ExpressionStyler">
				<xsl:with-param name="expression" select="$source" />
			</xsl:call-template>
			<xsl:text>.</xsl:text>
		</xsl:if>
		<xsl:call-template name="TokenStyler">
			<xsl:with-param name="token" select="$path" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="StringLiteral">
		<xsl:param name="value" />

		<xsl:text>"</xsl:text>
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal" select="$value" />
		</xsl:call-template>
		<xsl:text>"</xsl:text>
	</xsl:template>

	<xsl:template name="Sum">
		<xsl:param name="source" />

		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:text>Sum of </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$source" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
	</xsl:template>

	<xsl:template name="Today">
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal" select="'now'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="AggregateExpression">
		<xsl:param name="operator" />
		<xsl:param name="operand" />
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator">
				<xsl:value-of select="$operator" />
				<xsl:text> of elements in </xsl:text>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$operand" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="UnaryExpression">
		<xsl:param name="operator" />
		<xsl:param name="operand" />

		<xsl:text>(</xsl:text>
		<xsl:call-template name="ExpressionStyler">
			<xsl:with-param name="expression" select="$operand" />
		</xsl:call-template>
		<xsl:text> </xsl:text>
		<xsl:call-template name="OperatorStyler">
			<xsl:with-param name="operator" select="$operator" />
		</xsl:call-template>
		<xsl:text>)</xsl:text>
	</xsl:template>

	<xsl:template name="ValueSet">
		<xsl:param name="id" />
		<xsl:param name="version" />
		<xsl:param name="authority" />
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal">
				<xsl:text>Value set: </xsl:text>
				<xsl:value-of select="$id" />
				<xsl:if test="$version">
					<xsl:text>, v </xsl:text>
					<xsl:value-of select="$version" />
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:if test="$authority">
			<xsl:call-template name="OperatorStyler">
				<xsl:with-param name="operator" select="' from '" />
			</xsl:call-template>
			<xsl:call-template name="TokenStyler">
				<xsl:with-param name="token" select="$authority" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template name="OperatorStyler">
		<xsl:param name="operator" />
		<span style="color:red">
			<xsl:value-of select="$operator" />
		</span>
	</xsl:template>

	<xsl:template name="TokenStyler">
		<xsl:param name="token" />
		<span style="color:blue">
			<xsl:value-of select="$token" />
		</span>
	</xsl:template>

	<xsl:template name="LiteralStyler">
		<xsl:param name="literal" />
		<span style="color:green">
			<xsl:value-of select="$literal" />
		</span>
	</xsl:template>

	<xsl:template name="TypeStyler">
		<xsl:param name="type" />
		<span style="color:maroon">
			<xsl:value-of select="$type" />
		</span>
	</xsl:template>


	<xsl:template name="DataValueStyler">
		<xsl:param name="dataValue" />
		<xsl:variable name="type" select="$dataValue/@xsi:type" />
		<xsl:choose>
			<xsl:when test="$type='dt:ED' ">
				<xsl:call-template name="dtEDStyler">
					<xsl:with-param name="dataValue" select="$dataValue" />
				</xsl:call-template>
			</xsl:when>

		</xsl:choose>
	</xsl:template>


	<xsl:template name="dtEDStyler">
		<xsl:param name="dataValue" />
		<xsl:call-template name="LiteralStyler">
			<xsl:with-param name="literal" select="$dataValue/@value" />
		</xsl:call-template>
	</xsl:template>

</xsl:stylesheet>