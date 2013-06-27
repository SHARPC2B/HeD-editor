package sharpc2b.transform

import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import sharpc2b.transform.test.SharpGroovyTestCase

/**
 * User: rk
 * Date: 6/19/13
 * Time: 3:45 PM
 */
class ShopsTest extends SharpGroovyTestCase {

    static IRI shopsOntIRI = IriUtil.sharpEditorIRI( "shops" );
    static File shopsOntFile = FileUtil.getFileInProjectDir(
            "/model-transform/src/main/resources/onts/editor-models/shops.ofn" );

    static String opsCoreBaseIRI = IriUtil.sharpEditorIRI( "ops" ).toString() + "#";
    static String operatorsBaseIRI = shopsOntIRI.toString() + "#";

    void setUp () {
        super.setUp()

        reasonerFactory = new Reasoner.ReasonerFactory();  // Hermit
        if (reasoner != null) {
            reasoner.flush()
            reasoner.dispose()
            reasoner = null
        }

    }

    void testIntPlus () {


        ont = oom.loadOntologyFromOntologyDocument( IRI.create( shopsOntFile ) );

        reasoner = OwlUtil.getHermitReasoner( ont, false );

        assert reasoner.isConsistent();

        /* Create Expression with 'Add' operator and two integer operands. */

        OWLIndividual addInts = odf.getOWLNamedIndividual( exampleIRI( "addInts" ) );
        OWLIndividual intArg1 = odf.getOWLNamedIndividual( exampleIRI( "intArg1" ) );
        OWLIndividual intArg2 = odf.getOWLNamedIndividual( exampleIRI( "intArg2" ) );
        OWLIndividual realArg1 = odf.getOWLNamedIndividual( exampleIRI( "realArg1" ) );
        OWLIndividual addOperator = odf.getOWLNamedIndividual( exampleIRI( "addOperator" ) );


        OWLClass topExpression = odf.getOWLClass( opsIRI( "SharpExpression" ) )
        OWLClass integerExpr = odf.getOWLClass( opsIRI( "IntegerExpression" ) )
        OWLClass realExpr = odf.getOWLClass( opsIRI( "RealExpression" ) )

        OWLClass operator = odf.getOWLClass( opsIRI( "Operator" ) );
        OWLObjectProperty hasOperand = odf.getOWLObjectProperty( opsIRI( "hasOperand" ) );
        OWLObjectProperty hasOperand1 = odf.getOWLObjectProperty( opsIRI( "firstOperand" ) );
        OWLObjectProperty hasOperand2 = odf.getOWLObjectProperty( opsIRI( "secondOperand" ) );
        OWLObjectProperty hasOperand3 = odf.getOWLObjectProperty( opsIRI( "thirdOperand" ) );
        OWLObjectProperty hasOperator = odf.getOWLObjectProperty( opsIRI( "hasOperator" ) )

        OWLDataProperty skosNotation = odf.getOWLDataProperty( IriUtil.skosIRI( "notation" ) )
//        OWLDataProperty conceptCode = odf.getOWLDataProperty( IriUtil.sharpEditorIRI( "skos-ext#code" ) )
//        OWLObjectProperty opCode = odf.getOWLObjectProperty( exampleIRI( "opCode" ) )
//        OWLDataProperty representation = odf.getOWLDataProperty( exampleIRI( "representation" ) );

        //================================

        addAxiom( odf.getOWLClassAssertionAxiom( topExpression, addInts ) )
        addAxiom( odf.getOWLClassAssertionAxiom( integerExpr, intArg1 ) )
        addAxiom( odf.getOWLClassAssertionAxiom( integerExpr, intArg2 ) )
        addAxiom( odf.getOWLClassAssertionAxiom( realExpr, realArg1 ) )
        addAxiom( odf.getOWLClassAssertionAxiom( operator, addOperator ) )

        addAxiom( odf.getOWLObjectPropertyAssertionAxiom( hasOperator, addInts, addOperator ) )
        addAxiom( odf.getOWLDataPropertyAssertionAxiom( skosNotation, addOperator,
                odf.getOWLLiteral( "Add" ) ) );
        addAxiom( odf.getOWLObjectPropertyAssertionAxiom( hasOperand1, addInts, intArg1 ) )
        addAxiom( odf.getOWLObjectPropertyAssertionAxiom( hasOperand2, addInts, intArg2 ) )
//        addAxiom(odf.getOWLObjectPropertyAssertionAxiom(hasOperand2,addInts,realArg1))

        OWLClass targetExprClass = odf.getOWLClass( shopsIRI( "AddIntegerExpression" ) )

//        reasoner = OwlUtil.getHermitReasoner( ont, false );

//        NodeSet<OWLClass> nodeTypes
//        nodeTypes = reasoner.getTypes(addInts);
        def directTypes = reasoner.getTypes( addInts, true ).getFlattened()
//        def allTypes = reasoner.getTypes(addInts,false).getFlattened()

        println "direct types with int arg = " + directTypes;
//        println "all types with int arg = "+allTypes;

        assertTrue reasoner.isEntailed( odf.getOWLClassAssertionAxiom( targetExprClass, addInts ) )
        assertTrue reasoner.isEntailed( odf.getOWLClassAssertionAxiom( integerExpr, addInts ) )

//        OWLClass cInteger = odf.getOWLClass( exampleIRI( "Integer" ) )
//        OWLClass cString = odf.getOWLClass( exampleIRI( "String" ) )
//        OWLNamedIndividual iInteger = odf.getOWLNamedIndividual( exampleIRI( "intType" ) )
//        OWLNamedIndividual iString = odf.getOWLNamedIndividual( exampleIRI( "stringType" ) )

        OWLNamedIndividual plusCode = odf.getOWLNamedIndividual( exampleIRI( "op_plusCode" ) )

    }

    IRI exampleIRI (String localName) {
        IRI.create( "http://asu.edu/sharpc2b/ex1" + "#" + localName );
    }

    IRI shopsIRI (final String name) {
        return IRI.create( operatorsBaseIRI + name );
    }

    IRI opsIRI (final String name) {
        return IRI.create( opsCoreBaseIRI + name );
    }

    void addAxiom (final OWLAxiom axiom) {
        oom.addAxiom( ont, axiom );
//        newAxioms.add( axiom );
    }

}
