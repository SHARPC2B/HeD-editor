package sharpc2b.transform

import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.Test
import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat

/**
 * User: rk
 * Date: 6/15/13
 * Time: 1:57 PM
 */
class ExcelTest
extends GroovyTestCase {

    static File excelFile = FileUtil.getFileInResourceDir( "SharpOperators.xlsx" );
    static File inputOntFile = TestFileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT.ofn" );

    static IRI outputOntIRI = TestUtil.testIRI( "SharpOperators" );
    static File outputOntFile = TestFileUtil.getFileInTestResourceDir( "onts/out/SharpOperators.ofn" );

    static String opsBaseIRI = "http://asu.edu/sharpc2b/operators#";

    /**
     * Location in the classpath to find properties file containing entity IRIs to use in the output
     * A-Box ontology.
     */
    static String tToAConfigResourcePath = "/OWL-to-Sharp-ABox-Concepts.properties";

    OWLOntologyManager oom;
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
//    Set<OWLOntology> onts;
    OWLReasonerFactory reasonerFactory;
    OWLReasoner reasoner;

    OWLOntology ont;

    Workbook workbook

    void setUp () {

        oom = OwlapiUtil.createSharpOWLOntologyManager();
        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        odf = oom.getOWLDataFactory();
//        onts = new TreeSet<OWLOntology>();
        reasonerFactory = new Reasoner.ReasonerFactory();  // Hermit

        initTypeNameMap();
//        ontT = oom.loadOntologyFromOntologyDocument( inputOntFile );
//
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
        typeNameMap = null;
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


        OWLObjectProperty resultTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsBaseIRI + "evaluatesAs" ) );
        OWLObjectProperty operandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsBaseIRI + "hasOperandType" ) );
        OWLObjectProperty firstOperandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsBaseIRI + "hasFirstOperandType" ) );
        OWLObjectProperty secondOperandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsBaseIRI + "hasSecondOperandType" ) );
        OWLObjectProperty thirdOperandTypeRelationship = odf.getOWLObjectProperty( IRI.create
                ( opsBaseIRI + "hasThirdOperandType" ) );

        OWLClass topOperatorClass = odf.getOWLClass( IRI.create( opsBaseIRI + "Operator" ) );

        OWLClass unaryOperator = odf.getOWLClass( IRI.create( opsBaseIRI + "UnaryOperator" ) );
        OWLClass binaryOperator = odf.getOWLClass( IRI.create( opsBaseIRI + "BinaryOperator" ) );
        OWLClass ternaryOperator = odf.getOWLClass( IRI.create( opsBaseIRI + "TernaryOperator" ) );
        OWLClass naryOperator = odf.getOWLClass( IRI.create( opsBaseIRI + "NAryOperator" ) );
        OWLClass aggregateOperator = odf.getOWLClass( IRI.create( opsBaseIRI + "AggregateOperator" ) );

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

        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow( rowNum );
            String opName = row.getCell( colOperatorName ).getStringCellValue();
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

            // ToDo: modify name if reused.
            IRI operatorIRI = IRI.create( opsBaseIRI + opName )
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

            lastOperatorName = opName;
        }

        oom.saveOntology( ont, oFormat, IRI.create( outputOntFile ) );
    }

    Set<OWLAxiom> newAxioms = new HashSet<OWLAxiom>();

    void addAxiom (final OWLAxiom axiom) {
        oom.addAxiom( ont, axiom );
//        newAxioms.add( axiom );
    }

    static String typesBaseIRI = "http://asu.edu/sharpc2b/ops#";

    Map<String, IRI> typeNameMap = new HashMap<String, IRI>();

    void initTypeNameMap () {
        typeNameMap.put( "Any", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameMap.put( "T", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameMap.put( "C", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameMap.put( "Object", IRI.create( typesBaseIRI + "objectType" ) );
        typeNameMap.put( "Object<T>", IRI.create( typesBaseIRI + "objectType" ) );
        typeNameMap.put( "Number", IRI.create( typesBaseIRI + "numberType" ) );
        typeNameMap.put( "Real", IRI.create( typesBaseIRI + "realType" ) );
        typeNameMap.put( "Boolean", IRI.create( typesBaseIRI + "booleanType" ) );
        typeNameMap.put( "String", IRI.create( typesBaseIRI + "stringType" ) );
        typeNameMap.put( "Time/Duration", IRI.create( typesBaseIRI + "timeDurationType" ) );
        typeNameMap.put( "Timestamp", IRI.create( typesBaseIRI + "timestampType" ) );
        typeNameMap.put( "DateGranularity", IRI.create( typesBaseIRI + "dateGranularityType" ) );
        typeNameMap.put( "Integer", IRI.create( typesBaseIRI + "integerType" ) );
        typeNameMap.put( "Interval<T>", IRI.create( typesBaseIRI + "intervalType" ) );
        typeNameMap.put( "Collection<T>", IRI.create( typesBaseIRI + "collectionType" ) );
        typeNameMap.put( "List", IRI.create( typesBaseIRI + "listType" ) );
        typeNameMap.put( "List<T>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameMap.put( "List<S>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameMap.put( "List<String>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameMap.put( "List<Boolean>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameMap.put( "Ordered", IRI.create( typesBaseIRI + "orderedType" ) );
        typeNameMap.put( "Ordered Type", IRI.create( typesBaseIRI + "orderedType" ) );
        typeNameMap.put( "Scalar", IRI.create( typesBaseIRI + "scalarType" ) );
        typeNameMap.put( "Expression<T:S>", IRI.create( typesBaseIRI + "expressionType" ) );
    }

    OWLNamedIndividual getOpsTypeIndividual (final String typeNameInSpreadsheet) {

        IRI typeIRI = typeNameMap.get( typeNameInSpreadsheet );
        OWLNamedIndividual typeInd = typeIRI == null ? null : odf.getOWLNamedIndividual( typeIRI );
        return typeInd;
    }
}
