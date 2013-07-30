package sharpc2b.transform

import edu.asu.sharpc2b.transform.FileUtil
import edu.asu.sharpc2b.transform.IriUtil
import edu.asu.sharpc2b.transform.OwlUtil
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.Test
import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.model.AddImport
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLImportsDeclaration
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import sharpc2b.transform.test.TestFileUtil

/**
 * User: rk
 * Date: 6/15/13
 * Time: 1:57 PM
 */
class ExcelOperatorsTest
extends GroovyTestCase {

    static File excelFile = FileUtil.getExistingResourceAsFile(
            "/ontologies/import-operators/SharpOperators.xlsx");

    static IRI outputOntIRI = IriUtil.sharpEditorIRI( "shops" );
    static File outputOntFile = TestFileUtil.getFileForTestOutput( "/ontologies/out/shops-step-by-step.ofn" );


    static String opsCoreBaseIRI = IriUtil.sharpEditorIRI( "ops" ).toString() + "#";
    static final String skosExtBaseIRI = IriUtil.sharpEditorIRI( "skos-ext" ).toString() + "#";
    static String operatorsBaseIRI = outputOntIRI.toString() + "#";

    IRI outputIRI (final String name) {
        return IRI.create( operatorsBaseIRI + name );
    }

    static IRI opsIRI (final String name) {
        return IRI.create( opsCoreBaseIRI + name );
    }

    static IRI skosExtIRI (final String name) {
        return IRI.create( skosExtBaseIRI + name );
    }

    OWLOntologyManager oom;
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
//    Set<OWLOntology>ontologies;
    OWLReasonerFactory reasonerFactory;
    OWLReasoner reasoner;

    OWLOntology ont;

    Workbook workbook

    void setUp () {

        oom = OwlUtil.createSharpOWLOntologyManager();
        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        odf = oom.getOWLDataFactory();
//        ontologies = new TreeSet<OWLOntology>();
        reasonerFactory = new Reasoner.ReasonerFactory();  // Hermit

        initTypeNameMap();
        initTypeNameIriMap();
        initTypeExpressionNameMap();

//        ontT = oom.loadOntologyFromOntologyDocument( inputOntFile );

        ont = oom.createOntology( outputOntIRI );
//        oFormat.setPrefix( "a:", ontA.getOntologyID().getOntologyIRI().toString() + "#" );
//        oFormat.setPrefix( "t:", ontT.getOntologyID().getOntologyIRI().toString() + "#" );
    }

    void tearDown () {
        oom.clearIRIMappers()
        oom = null;
        ont = null;
        oFormat = null;
        odf = null;
        typeNameIriMap = null;
        typeExpressionNameMap = null;
    }

    void openWorkbook (File file) throws FileNotFoundException,
            IOException, InvalidFormatException {
        FileInputStream fis = null;
        try {
            System.out.println( "Opening workbook [" + file.getName() + "]" );

            fis = new FileInputStream( file );

            // Open the workbook and then create the FormulaEvaluator and
            // DataFormatter instances that will be needed to, respectively,
            // force evaluation of forumlae found in cells and create a
            // formatted String encapsulating the cells contents.
            workbook = WorkbookFactory.create( fis );
//            this.evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
//            this.formatter = new DataFormatter(true);
        }
        finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    @Test
    void testExcel () {

        openWorkbook( excelFile )
        assert workbook

        Sheet sheet
        sheet = workbook.getSheet( "Operators" )

        Row row2 = sheet.getRow( 2 );
        assert row2;
        Cell cell22 = row2.getCell( 2 );
        println cell22;
        assertEquals( "Real", cell22.getStringCellValue() );

//        println "top = " + sheet.getTopRow()
        println "first = " + sheet.getFirstRowNum()
        println "last = " + sheet.getLastRowNum()
        int firstRowNum = sheet.getFirstRowNum()
        int nRows = sheet.getLastRowNum()
        int lastRowNum = sheet.getLastRowNum()

//        Row firstRow = sheet.getRow( firstRowNum )
//        println "c[1,1] = " + firstRow.getCell( 1 ).getStringCellValue()
//        println "c[1,0] = " + firstRow.getCell( 0 ).getStringCellValue()
        Row row0 = sheet.getRow( 0 );
        println "c[0,0] = " + row0.getCell( 0 ).getStringCellValue()
        println "c[0,1] = " + row0.getCell( 1 ).getStringCellValue()
        println "c[0,2] = " + row0.getCell( 2 ).getStringCellValue()
        println "c[0,5] = " + row0.getCell( 5 ).getStringCellValue()

        Row rowLast = sheet.getRow( lastRowNum );
        println "c[n,5] = " + rowLast.getCell( 0 ).getStringCellValue()
    }

    @Test
    void testCreateOperatorOntology () {

        addImports();

        OWLObjectProperty hasOperator = odf.getOWLObjectProperty( opsIRI( "hasOperator" ) );
        OWLObjectProperty hasOperand = odf.getOWLObjectProperty( opsIRI( "hasOperand" ) );
        OWLObjectProperty hasOperand1 = odf.getOWLObjectProperty( opsIRI( "firstOperand" ) );
        OWLObjectProperty hasOperand2 = odf.getOWLObjectProperty( opsIRI( "secondOperand" ) );
        OWLObjectProperty hasOperand3 = odf.getOWLObjectProperty( opsIRI( "thirdOperand" ) );

        addAxiom( odf.getOWLSubObjectPropertyOfAxiom( hasOperand1, hasOperand ) );
        addAxiom( odf.getOWLSubObjectPropertyOfAxiom( hasOperand2, hasOperand ) );
        addAxiom( odf.getOWLSubObjectPropertyOfAxiom( hasOperand3, hasOperand ) );

        OWLObjectProperty resultTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsCoreBaseIRI + "evaluatesAs" ) );
        OWLObjectProperty operandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsCoreBaseIRI + "hasOperandType" ) );
        OWLObjectProperty firstOperandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsCoreBaseIRI + "hasFirstOperandType" ) );
        OWLObjectProperty secondOperandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsCoreBaseIRI + "hasSecondOperandType" ) );
        OWLObjectProperty thirdOperandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsCoreBaseIRI + "hasThirdOperandType" ) );

        addAxiom( odf.getOWLSubObjectPropertyOfAxiom( firstOperandTypeRelationship, operandTypeRelationship ) );
        addAxiom( odf.getOWLSubObjectPropertyOfAxiom( secondOperandTypeRelationship, operandTypeRelationship ) );
        addAxiom( odf.getOWLSubObjectPropertyOfAxiom( thirdOperandTypeRelationship, operandTypeRelationship ) );

        OWLClass topOperatorClass = odf.getOWLClass( IRI.create( opsCoreBaseIRI + "Operator" ) );

        OWLClass unaryOperator = odf.getOWLClass( IRI.create( opsCoreBaseIRI + "UnaryOperator" ) );
        OWLClass binaryOperator = odf.getOWLClass( IRI.create( opsCoreBaseIRI + "BinaryOperator" ) );
        OWLClass ternaryOperator = odf.getOWLClass( IRI.create( opsCoreBaseIRI + "TernaryOperator" ) );
        OWLClass naryOperator = odf.getOWLClass( IRI.create( opsCoreBaseIRI + "NAryOperator" ) );
        OWLClass aggregateOperator = odf.getOWLClass( IRI.create( opsCoreBaseIRI + "AggregateOperator" ) );

        addAxiom( odf.getOWLSubClassOfAxiom( unaryOperator, topOperatorClass ) );
        addAxiom( odf.getOWLSubClassOfAxiom( binaryOperator, topOperatorClass ) );
        addAxiom( odf.getOWLSubClassOfAxiom( ternaryOperator, topOperatorClass ) );
        addAxiom( odf.getOWLSubClassOfAxiom( naryOperator, topOperatorClass ) );
        addAxiom( odf.getOWLSubClassOfAxiom( aggregateOperator, topOperatorClass ) );

        int colIndex = 0;
        int colOperatorName = colIndex++;
        int colNumArgs = colIndex++;
        int colResultType = colIndex++;
        int colOperand1Type = colIndex++;
        int colOperand2Type = colIndex++;
        int colOperand3Type = colIndex++;

        String lastOperatorName = "NONE";

        openWorkbook( excelFile )
        assert workbook

        Sheet sheet
        sheet = workbook.getSheet( "Operators" )

//        int firstRowNum = sheet.getFirstRowNum()
//        int nRows = sheet.getLastRowNum()
        int lastRowNum = sheet.getLastRowNum()

        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow( rowNum );
            String nextOperatorName;
            if (rowNum < lastRowNum) {
                nextOperatorName = sheet.getRow( rowNum + 1 ).getCell( colOperatorName ).getStringCellValue();
            } else {
                nextOperatorName = "NONE";
            }
            String opNameFromConfig = row.getCell( colOperatorName ).getStringCellValue();
//            String opName = row.getCell( colOperatorName ).getStringCellValue();
//            String numArgs = row.getCell( colNumArgs ).getStringCellValue();
            String numArgs = row.getCell( colNumArgs ).toString();
            String resultTypeName = row.getCell( colResultType ).getStringCellValue();
            String op1TypeName = row.getCell( colOperand1Type ).getStringCellValue();
//            String op2Type = row.getCell(colOperand2Type).getStringCellValue();
//            String op3Type = row.getCell(colOperand3Type).getStringCellValue();
            boolean isNary = numArgs == "n";
            boolean isListArg = numArgs == "a";
            boolean isAry = !(isNary || isListArg)
            int arity;
            if (numArgs == "n") {
                arity = -1;
            } else if (numArgs == "a") {
                arity = 0;
            } else if (numArgs == "1.0") {
                arity = 1;
            } else if (numArgs == "2.0") {
                arity = 2;
            } else if (numArgs == "3.0") {
                arity = 3;
            } else {
                throw new RuntimeException( "Encountered bad value for number of operands: " + numArgs );
            }

            String op2TypeName = 2 <= arity ? row.getCell( colOperand2Type ).getStringCellValue() : null;
            String op3TypeName = 3 <= arity ? row.getCell( colOperand3Type ).getStringCellValue() : null;

            /* check if operator name used in more than one row. */
            boolean overloadedName = opNameFromConfig == lastOperatorName || opNameFromConfig ==
                    nextOperatorName;
            String opName = overloadedName ? (opNameFromConfig + typeNameMap.get( op1TypeName )) :
                opNameFromConfig;

            IRI operatorIRI = outputIRI( opName );
            OWLNamedIndividual operator = odf.getOWLNamedIndividual( operatorIRI )

            /* At this point have operator name and arity. Now need to get result and operand types. */

            OWLNamedIndividual resultType = getOpsTypeIndividual( resultTypeName );
            assertNotNull( "no type found for resultTypeName = " + resultTypeName, resultType );
            OWLNamedIndividual operand1Type = getOpsTypeIndividual( op1TypeName );
            assertNotNull( "no type found for operandTypeName = " + op1TypeName, operand1Type );
            OWLNamedIndividual operand2Type = 2 <= arity ? getOpsTypeIndividual( op2TypeName ) : null;
            OWLNamedIndividual operand3Type = 3 <= arity ? getOpsTypeIndividual( op3TypeName ) : null;
            if (2 <= arity) {
                assertNotNull( "no type found for operandTypeName = " + op2TypeName, operand2Type );
            }
            if (3 <= arity) {
                assertNotNull( "no type found for operandTypeName = " + op3TypeName, operand3Type );
            }

            OWLClass operatorClass;

            if (arity == -1) {
                addAxiom( odf.getOWLClassAssertionAxiom( naryOperator, operator ) );
            } else if (arity == 0) {
                addAxiom( odf.getOWLClassAssertionAxiom( aggregateOperator, operator ) );
            } else if (arity == 1) {
                addAxiom( odf.getOWLClassAssertionAxiom( unaryOperator, operator ) );
            } else if (arity == 2) {
                addAxiom( odf.getOWLClassAssertionAxiom( binaryOperator, operator ) );
            } else if (arity == 3) {
                addAxiom( odf.getOWLClassAssertionAxiom( ternaryOperator, operator ) );
            }

            assertNotNull( resultTypeRelationship );
            assertNotNull( operator );
            assertNotNull( operandTypeRelationship );
            assertNotNull( secondOperandTypeRelationship );
            assertNotNull( thirdOperandTypeRelationship );
            assertNotNull( operand1Type );

            /* assert skos:notation */
            OWLDataProperty skosNotation = odf.getOWLDataProperty(
                    IRI.create( "http://www.w3.org/2004/02/skos/core#notation" ) );

            // skos:notation
            addAxiom( odf.getOWLDataPropertyAssertionAxiom( skosNotation, operator, opNameFromConfig ) );

            /* assert ops:code */
            OWLDataProperty conceptCode = odf.getOWLDataProperty( skosExtIRI( "code" ) );

            addAxiom( odf.getOWLDataPropertyAssertionAxiom( conceptCode, operator, opNameFromConfig ) );

            OWLAxiom ax;
            ax = odf.getOWLObjectPropertyAssertionAxiom( resultTypeRelationship, operator, resultType );
            addAxiom( ax );

            /* first operand type */
            OWLObjectProperty opRelationship = 0 < arity ? firstOperandTypeRelationship :
                operandTypeRelationship;
            assertNotNull( opRelationship );
            ax = odf.getOWLObjectPropertyAssertionAxiom( opRelationship, operator, operand1Type );
            addAxiom( ax );

            /* second operand type */
            if (2 <= arity) {
                assertNotNull( operand2Type );
                addAxiom( odf.getOWLObjectPropertyAssertionAxiom( secondOperandTypeRelationship, operator,
                        operand2Type ) );
            }

            /* third operand type */
            if (3 <= arity) {
                assertNotNull( operand3Type );
                addAxiom( odf.getOWLObjectPropertyAssertionAxiom( thirdOperandTypeRelationship, operator,
                        operand3Type ) );
            }

            /*###################################################*/
            /* Create Expression Class */

//            OWLClass exprClass = odf.getOWLClass( outputIRI( opName ) );
            OWLClass exprClass = odf.getOWLClass( outputIRI( opName + "Expression" ) );
            OWLClass superClass = getExpressionTypeClass( resultTypeName );

            assertFalse( "has subClassOf = Expression, class = " + exprClass + "," +
                    "resultTypeName = " + resultTypeName,
                    superClass.getIRI().equals(
                            opsIRI
                                    ( "Expression" ) ) )

            addAxiom( odf.getOWLSubClassOfAxiom( exprClass, superClass ) );

            /* Define exprClass as hasOperator operatorInd AND hasOperand type Expr */

            Set<? extends OWLClassExpression> requirements = new HashSet<? extends OWLClassExpression>();

            requirements.add( odf.getOWLClass( opsIRI( "SharpExpression" ) ) );
//            requirements.add( odf.getOWLObjectHasValue( hasOperator, operator ) );

            requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperator,
                    odf.getOWLDataHasValue( skosNotation, odf.getOWLLiteral( opNameFromConfig ) ) ) );

            if (isNary) {
                requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand, getExpressionTypeClass( op1TypeName ) ) );
                requirements.add( odf.getOWLObjectAllValuesFrom( hasOperand, getExpressionTypeClass( op1TypeName ) ) );
            }

            OWLClass listExpr = getExpressionTypeClass( "List" );
            if (isListArg) {
                requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand, listExpr ) );
                requirements.add( odf.getOWLObjectExactCardinality( 1, hasOperand ) );
            }
            if (isAry) {
                requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand1,
                        getExpressionTypeClass( op1TypeName ) ) );
            }
            if (2 <= arity) {
                requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand2,
                        getExpressionTypeClass( op2TypeName ) ) );
            }
            if (3 <= arity) {
                requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand3,
                        getExpressionTypeClass( op3TypeName ) ) );
            }

            OWLObjectIntersectionOf andExpression = odf.getOWLObjectIntersectionOf( requirements );

            addAxiom( odf.getOWLEquivalentClassesAxiom( exprClass, andExpression ) );

            /* setup for next iteration */
            lastOperatorName = opNameFromConfig;
        }

        oom.saveOntology( ont, oFormat, IRI.create( outputOntFile ) );
    }

    void addImports () {
        OWLImportsDeclaration importOps = odf.getOWLImportsDeclaration( IriUtil.sharpEditorIRI( "ops" ) );

        System.out.println( "add owl:imports: " + importOps );

        oom.applyChange( new AddImport( ont, importOps ) );
    }

    Set<OWLAxiom> newAxioms = new HashSet<OWLAxiom>();

    void addAxiom (final OWLAxiom axiom) {
        oom.addAxiom( ont, axiom );
//        newAxioms.add( axiom );
    }

    static String typesBaseIRI = "http://asu.edu/sharpc2b/ops#";

    Map<String, IRI> typeNameIriMap = new HashMap<String, IRI>();

    void initTypeNameIriMap () {
        typeNameIriMap.put( "Any", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "T", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "C", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "Object", IRI.create( typesBaseIRI + "objectType" ) );
        typeNameIriMap.put( "Object<T>", IRI.create( typesBaseIRI + "objectType" ) );
        typeNameIriMap.put( "Number", IRI.create( typesBaseIRI + "numberType" ) );
        typeNameIriMap.put( "Real", IRI.create( typesBaseIRI + "realType" ) );
        typeNameIriMap.put( "Boolean", IRI.create( typesBaseIRI + "booleanType" ) );
        typeNameIriMap.put( "String", IRI.create( typesBaseIRI + "stringType" ) );
        typeNameIriMap.put( "Time/Duration", IRI.create( typesBaseIRI + "timeDurationType" ) );
        typeNameIriMap.put( "Timestamp", IRI.create( typesBaseIRI + "timestampType" ) );
        typeNameIriMap.put( "DateGranularity", IRI.create( typesBaseIRI + "dateGranularityType" ) );
        typeNameIriMap.put( "Integer", IRI.create( typesBaseIRI + "integerType" ) );
        typeNameIriMap.put( "Interval<T>", IRI.create( typesBaseIRI + "intervalType" ) );
        typeNameIriMap.put( "Collection<T>", IRI.create( typesBaseIRI + "collectionType" ) );
        typeNameIriMap.put( "List", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<T>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<S>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<String>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<Boolean>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "Ordered", IRI.create( typesBaseIRI + "orderedType" ) );
        typeNameIriMap.put( "Ordered Type", IRI.create( typesBaseIRI + "orderedType" ) );
        typeNameIriMap.put( "Scalar", IRI.create( typesBaseIRI + "scalarType" ) );
        typeNameIriMap.put( "Expression<T:S>", IRI.create( typesBaseIRI + "expressionType" ) );
    }

    Map<String, String> typeNameMap = new HashMap<String, String>();

    void initTypeNameMap () {
        typeNameMap.put( "Any", "Any" );
        typeNameMap.put( "T", "Any" );
        typeNameMap.put( "C", "Any" );
        typeNameMap.put( "Object", "Object" );
        typeNameMap.put( "Object<T>", "Object" );
        typeNameMap.put( "Number", "Number" );
        typeNameMap.put( "Real", "Real" );
        typeNameMap.put( "Boolean", "Boolean" );
        typeNameMap.put( "String", "String" );
        typeNameMap.put( "Time/Duration", "TimeDuration" );
        typeNameMap.put( "Timestamp", "Timestamp" );
        typeNameMap.put( "DateGranularity", "DateGranularity" );
        typeNameMap.put( "Integer", "Integer" );
        typeNameMap.put( "Interval<T>", "Interval" );
        typeNameMap.put( "Collection<T>", "Collection" );
        typeNameMap.put( "List", "List" );
        typeNameMap.put( "List<T>", "List" );
        typeNameMap.put( "List<S>", "List" );
        typeNameMap.put( "List<String>", "List" );
        typeNameMap.put( "List<Boolean>", "List" );
        typeNameMap.put( "Ordered", "Ordered" );
        typeNameMap.put( "Ordered Type", "Ordered" );
        typeNameMap.put( "Scalar", "Scalar" );
        typeNameMap.put( "Expression<T:S>", "Expression" );
    }

    OWLNamedIndividual getOpsTypeIndividual (final String typeNameInSpreadsheet) {

        IRI typeIRI = typeNameIriMap.get( typeNameInSpreadsheet );
        OWLNamedIndividual typeInd = typeIRI == null ? null : odf.getOWLNamedIndividual( typeIRI );
        return typeInd;
    }


    Map<String, IRI> typeExpressionNameMap = new HashMap<String, IRI>();

    void initTypeExpressionNameMap () {
        typeExpressionNameMap.put( "Any", IRI.create( typesBaseIRI + "Sharp" ) );
        typeExpressionNameMap.put( "T", IRI.create( typesBaseIRI + "Sharp" ) );
        typeExpressionNameMap.put( "C", IRI.create( typesBaseIRI + "Sharp" ) );
        typeExpressionNameMap.put( "Object", IRI.create( typesBaseIRI + "Object" ) );
        typeExpressionNameMap.put( "Object<T>", IRI.create( typesBaseIRI + "Object" ) );
        typeExpressionNameMap.put( "Number", IRI.create( typesBaseIRI + "Number" ) );
        typeExpressionNameMap.put( "Real", IRI.create( typesBaseIRI + "Real" ) );
        typeExpressionNameMap.put( "Boolean", IRI.create( typesBaseIRI + "Boolean" ) );
        typeExpressionNameMap.put( "String", IRI.create( typesBaseIRI + "String" ) );
        typeExpressionNameMap.put( "Time/Duration", IRI.create( typesBaseIRI + "TimeDuration" ) );
        typeExpressionNameMap.put( "Timestamp", IRI.create( typesBaseIRI + "Timestamp" ) );
        typeExpressionNameMap.put( "DateGranularity", IRI.create( typesBaseIRI + "DateGranularity" ) );
        typeExpressionNameMap.put( "Integer", IRI.create( typesBaseIRI + "Integer" ) );
        typeExpressionNameMap.put( "Interval<T>", IRI.create( typesBaseIRI + "Interval" ) );
        typeExpressionNameMap.put( "Collection<T>", IRI.create( typesBaseIRI + "Collection" ) );
        typeExpressionNameMap.put( "List", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<T>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<S>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<String>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<Boolean>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "Ordered", IRI.create( typesBaseIRI + "Ordered" ) );
        typeExpressionNameMap.put( "Ordered Type", IRI.create( typesBaseIRI + "Ordered" ) );
        typeExpressionNameMap.put( "Scalar", IRI.create( typesBaseIRI + "Scalar" ) );
        typeExpressionNameMap.put( "Expression<T:S>", IRI.create( typesBaseIRI + "Expression" ) );
    }

    OWLClass getExpressionTypeClass (final String typeNameInSpreadsheet) {

        IRI typeIRI = typeExpressionNameMap.get( typeNameInSpreadsheet );
        OWLClass type = typeIRI == null ? null : odf.getOWLClass(
                IRI.create( typeIRI.toString() + "Expression" ) );
        return type;
    }

}
