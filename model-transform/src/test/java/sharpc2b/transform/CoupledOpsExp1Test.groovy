package sharpc2b.transform

import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException
import org.semanticweb.owlapi.reasoner.Node as OaNode
import org.semanticweb.owlapi.reasoner.NodeSet
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat

/**
 * User: rk
 * Date: 6/3/13
 * Time: 2:44 PM
 */
class CoupledOpsExp1Test
extends GroovyTestCase {

    static File ontFile = FileUtil.getFileInProjectDir(
            OwlapiUtil.ontologiesDirInProject + "demos/expr-core-coupled.owl" )

    OWLOntologyManager oom;
    OWLOntology ont
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
    Set<OWLOntology> onts;
    OWLReasonerFactory reasonerFactory;
    OWLReasoner reasoner;

    void setUp () {

//        oom = OWLManager.createOWLOntologyManager()
        oom = OwlapiUtil.createSharpOWLOntologyManager();
        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        odf = oom.getOWLDataFactory();
        onts = new TreeSet<OWLOntology>();
        reasonerFactory = new Reasoner.ReasonerFactory();  // Hermit
    }

    void tearDown () {

        oom.clearIRIMappers();
        oom = null;
        oFormat = null
        odf = null
        onts = null
    }

//    void testIfOperationOperandsOK () {
//    }

    void testMockExp1 () {

        // imports skos-ext and skos-core
        assertTrue ontFile.exists()
        ont = oom.loadOntologyFromOntologyDocument( ontFile )

        onts.add( ont );
        assertNotNull ont
        Set<OWLOntology> imports
        imports = ont.getImports()
        assertEquals( 2, imports.size() );

        onts.addAll( imports );
        assertEquals( 3, onts.size() )


        OWLIndividual exp1 = odf.getOWLNamedIndividual( "ops:mockExp1", oFormat )
        OWLObjectProperty hasOperator = odf.getOWLObjectProperty( "ops:hasOperator", oFormat )
        OWLObjectProperty hasOperand = odf.getOWLObjectProperty( "ops:hasOperand", oFormat )
        OWLObjectProperty returns = odf.getOWLObjectProperty( "ops:returns", oFormat )
//        OWLClass targetExprClass = odf.getOWLClass( "ops:IntSumExpression", oFormat )
//        OWLClass cInteger = odf.getOWLClass( "ops:Integer", oFormat )
//        OWLClass cString = odf.getOWLClass( "ops:String", oFormat )
        OWLNamedIndividual iInteger = odf.getOWLNamedIndividual( "ops:intType", oFormat )
        OWLNamedIndividual iString = odf.getOWLNamedIndividual( "ops:stringType", oFormat )

        Set<OWLClassExpression> types1
        types1 = exp1.getTypes( ont );
        assertEquals( 0, types1.size() )

        Set<OWLIndividual> operators
        Set<OWLIndividual> operands
        operators = exp1.getObjectPropertyValues( hasOperator, ont );
        operands = exp1.getObjectPropertyValues( hasOperand, ont );

        assertEquals( 1, operators.size() )
        assertEquals( 2, operands.size() )

        reasoner = reasonerFactory.createNonBufferingReasoner( ont );

        assertEquals( true, reasoner.isConsistent() )

        /* A.R. -- After Reasoning */

        Set<OWLClass> directClasses
        directClasses = reasoner.getTypes( exp1, true ).getFlattened();
        assertEquals( 1, directClasses.size() );
        NodeSet<OWLClass> rTypesNodeSet = reasoner.getTypes( exp1, true );
        Set<OWLClass> flat
        flat = rTypesNodeSet.getFlattened();
        assertEquals( 1, flat.size() )

        assertEquals( false, rTypesNodeSet.isEmpty() )
        println "rTypesNodeSet = $rTypesNodeSet"

        OaNode nd;
        Set<OaNode<OWLClass>> rTypeNodes
        rTypeNodes = rTypesNodeSet.getNodes();
        assertEquals( 1, rTypeNodes.size() )
        Set<OWLClass> flat1
        flat1 = rTypesNodeSet.getFlattened()
        assertEquals( 1, flat1.size() );

//        int n = rTypesNodeSet.iterator().size()
//        println "n = $n"

        assertEquals( 1, rTypesNodeSet.getNodes().size() );
        assertEquals( 1, rTypesNodeSet.getFlattened().size() );

//        types1 = exp1.getTypes( ont );
//        assertEquals( 1, types1.size() )

        OWLClassExpression inferredExpType = flat1.iterator().next();
        assert (inferredExpType instanceof OWLClass)
        OWLClass expClass = (OWLClass) inferredExpType;
        IRI expClassIRI = expClass.getIRI();
        IRI expectedIRI = oFormat.getIRI( "ops:IntSumExpression" );

        assertEquals( expectedIRI, expClassIRI );

        NodeSet<OWLClass> allTypeNodes
        allTypeNodes = reasoner.getTypes( exp1, false );
        allTypeNodes.each { println it }

        /* check if consistent if returns Integer, fails if returns String */

        OWLAxiom ax
        ax = odf.getOWLObjectPropertyAssertionAxiom( returns, exp1, iInteger );
        oom.addAxiom( ont, ax );

        assertTrue reasoner.isConsistent()

        oom.removeAxiom( ont, ax );

        ax = odf.getOWLObjectPropertyAssertionAxiom( returns, exp1, iString );
        oom.addAxiom( ont, ax );

//        reasoner.flush()

        assertEquals( false, reasoner.isConsistent() )

        oom.removeAxiom( ont, ax );

//        assert   inferExpressionType(x, testOntology).contains( "IntSumExpression" )
    }

    void testOperationPartsOK () {

        ont = oom.loadOntologyFromOntologyDocument( ontFile )

        onts.add( ont );
        assertNotNull ont
        Set<OWLOntology> imports
        imports = ont.getImports()
        assertEquals( 2, imports.size() );

        onts.addAll( imports );

        OWLClass cIntLiteral = odf.getOWLClass( "ops:IntLiteral", oFormat );

        boolean result = operationOK( "+", [cIntLiteral, cIntLiteral], cIntLiteral );

        assertEquals( true, result );
    }

    boolean operationOK (String operatorNotation,
                         List<OWLClassExpression> proposedOperandTypes,
                         OWLClass returnType) {

        Set<OWLAxiom> testAxioms = new HashSet<OWLAxiom>();

        OWLIndividual testExpr = odf.getOWLAnonymousIndividual();
        OWLIndividual operatorInst = odf.getOWLAnonymousIndividual();

        OWLDataProperty skosNotation = odf.getOWLDataProperty( "skos:notation", oFormat );
        OWLAxiom ax = odf.getOWLDataPropertyAssertionAxiom( skosNotation, operatorInst,
                odf.getOWLLiteral( operatorNotation ) );
        testAxioms.add( ax );

        OWLObjectProperty hasOperator
        OWLObjectProperty hasOperand
        hasOperator = odf.getOWLObjectProperty( "ops:hasOperator", oFormat );
        hasOperand = odf.getOWLObjectProperty( "ops:hasOperand", oFormat );

        testAxioms.add( odf.getOWLObjectPropertyAssertionAxiom( hasOperator, testExpr, operatorInst ) );

        for (OWLClassExpression operandType : proposedOperandTypes) {
            OWLIndividual operand = odf.getOWLAnonymousIndividual();
            testAxioms.add( odf.getOWLClassAssertionAxiom( operandType, operand ) );
            testAxioms.add( odf.getOWLObjectPropertyAssertionAxiom( hasOperand, testExpr, operand ) );
        }

        reasoner = reasonerFactory.createNonBufferingReasoner( ont );

        if (reasoner.isConsistent()) {

            oom.addAxioms( ont, testAxioms );

            boolean ok = reasoner.isConsistent();

            oom.removeAxioms( ont, testAxioms );

            return ok;
        } else {
            throw new InconsistentOntologyException();
        }
        return false;
    }

    List<OWLClass> inferExpressionTypes (OWLIndividual expressionInstance,
                                         OWLOntology inOntology) {
        Reasoner reasoner = new Reasoner( inOntology );

        NodeSet<OWLClass> rTypesNodeSet = reasoner.getTypes( expressionInstance, true );
        Set<OWLClass> exprTypes = rTypesNodeSet.getFlattened();

        return exprTypes
    }

}
