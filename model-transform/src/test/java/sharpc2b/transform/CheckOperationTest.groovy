package sharpc2b.transform

import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat

/**
 * User: rk
 * Date: 6/3/13
 * Time: 2:44 PM
 */
class CheckOperationTest
extends GroovyTestCase {


    OWLOntologyManager oom;
    OWLOntology ont
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
    Set<OWLOntology> onts;
//    OWLOntology tboxModel

    void setUp () {

//        oom = OWLManager.createOWLOntologyManager()
        oom = OwlapiUtil.createSharpOWLOntologyManager();
        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        odf = oom.getOWLDataFactory();
        onts = new TreeSet<OWLOntology>();
    }

    void tearDown () {

    }

//    void testIfOperationOperandsOK () {
//    }

    void testMockExp1 () {

        File ontFile = new File( "/Users/rk/asu/prj/sharp-editor/editor-models/src/main/resources" +
                "/ontologies/demos/expr-core-coupled.owl" )

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

        OWLIndividual exp1 = odf.getOWLNamedIndividual( "opsc:mockExp1", oFormat );
        Set<OWLClassExpression> types1
        types1 = exp1.getTypes( ont );
        assertEquals( 0, types1.size() )

        OWLObjectProperty hasOperator
        OWLObjectProperty hasOperand
        hasOperator = odf.getOWLObjectProperty( "opsc:hasOperator", oFormat );
        hasOperand = odf.getOWLObjectProperty( "opsc:hasOperand", oFormat );


        Set<OWLIndividual> operators
        Set<OWLIndividual> operands
        operators = exp1.getObjectPropertyValues( hasOperator, ont );
        operands = exp1.getObjectPropertyValues( hasOperand, ont );

        assertEquals( 1, operators.size() )
        assertEquals( 2, operands.size() )

        Reasoner reasoner
        reasoner = new Reasoner( ont );

        assertEquals( true, reasoner.isConsistent() )

        /* A.R. -- After Reasoning */

        assertEquals( 1, types1.size() )

        OWLClassExpression inferredExpType = types1.iterator().next();
        assertEquals( OWLClass.class, inferredExpType.getClass() );
        OWLClass expClass = (OWLClass) inferredExpType;
        IRI expClassIRI = expClass.getIRI();
        IRI expectedIRI = oFormat.getIRI( "opsc:IntSumExpression" );

        assertEquals( expectedIRI, expClassIRI );

//        OWLIndividual x =
//        assert   inferExpressionType(x, testOntology).contains( "IntSumExpression" )
    }

//    boolean operationOK (OWLClass operationClass,
//                         OWLIndividual operatorInst,
//                         String operatorNotaion,
//                         List proposedOperands) {
//
//    }

    List<OWLClassExpression> inferExpressionType (OWLIndividual expressionInstance,
                                                  OWLOntology inOntology) {
        Set<OWLClassExpression> exprTypes
        exprTypes = expressionInstance.getTypes( inOntology )
        return exprTypes
    }

}
