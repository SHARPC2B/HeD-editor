package sharpc2b.transform

import org.semanticweb.HermiT.Reasoner
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
import org.semanticweb.owlapi.reasoner.NodeSet
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat

/**
 * User: rk
 * Date: 6/8/13
 * Time: 8:19 AM
 */
class DecoupledOpsExp1Test
extends GroovyTestCase {

    static File ontFile = FileUtil.getFileInProjectDir(
            OwlUtil.sharpEditorOntologiesDirInProject + "demos/expr-core-decoupled.owl" )

    OWLOntologyManager oom;
    OWLOntology ont
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
    Set<OWLOntology> onts;
    OWLReasonerFactory reasonerFactory;
    OWLReasoner reasoner;

    void setUp () {

//        oom = OWLManager.createOWLOntologyManager()
        oom = OwlUtil.createSharpOWLOntologyManager();
        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        odf = oom.getOWLDataFactory();
        onts = new TreeSet<OWLOntology>();
        reasonerFactory = new Reasoner.ReasonerFactory();  // Hermit
        if (reasoner != null) {
            reasoner.flush()
            reasoner.dispose()
            reasoner = null
        }
    }

    void tearDown () {

        oom.clearIRIMappers();
        oom = null;
        oFormat = null
        odf = null
        onts = null
        if (reasoner != null) {
            reasoner.flush()
            reasoner.dispose()
            reasoner = null
        }
    }

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
//        OWLObjectProperty hasOperator = odf.getOWLObjectProperty( "ops:hasOperator", oFormat )
        OWLObjectProperty hasOperand = odf.getOWLObjectProperty( "ops:hasOperand", oFormat )
        OWLObjectProperty returns = odf.getOWLObjectProperty( "ops:returns", oFormat )
        OWLClass targetExprClass = odf.getOWLClass( "ops:IntSumExpression", oFormat )
        OWLClass cInteger = odf.getOWLClass( "ops:Integer", oFormat )
        OWLClass cString = odf.getOWLClass( "ops:String", oFormat )
        OWLNamedIndividual iInteger = odf.getOWLNamedIndividual( "ops:intType", oFormat )
        OWLNamedIndividual iString = odf.getOWLNamedIndividual( "ops:stringType", oFormat )

        OWLObjectProperty opCode = odf.getOWLObjectProperty( "ops:opCode", oFormat )
        OWLNamedIndividual plusCode = odf.getOWLNamedIndividual( "ops:op_plusCode", oFormat )
        OWLDataProperty skosNotation = odf.getOWLDataProperty( "skos:notation", oFormat );
        OWLDataProperty conceptCode = odf.getOWLDataProperty( "skos-ext:code", oFormat );
        OWLDataProperty representation = odf.getOWLDataProperty( "ops:representation", oFormat );

        Set<OWLClassExpression> types1
        types1 = exp1.getTypes( ont );
        assertEquals( 0, types1.size() )

        Set<OWLIndividual> opCode1
        opCode1 = exp1.getObjectPropertyValues( opCode, ont );
        Set<OWLIndividual> operands
        operands = exp1.getObjectPropertyValues( hasOperand, ont );

        assertEquals( 1, opCode1.size() )
        assertEquals( 2, operands.size() )

//        reasoner = reasonerFactory.createReasoner( ont );
        reasoner = reasonerFactory.createNonBufferingReasoner( ont );

        assertEquals( true, reasoner.isConsistent() )

        /* A.R. -- After Reasoning */

        /* Check to see that IntSumExpression is inferred type. */

        Set<OWLClass> directClasses
        directClasses = reasoner.getTypes( exp1, true ).getFlattened();
        directClasses.each { println it }
        assert 0 < directClasses.size()

        assertEquals( true, directClasses.contains( targetExprClass ) )

        NodeSet<OWLClass> allTypeNodes
        allTypeNodes = reasoner.getTypes( exp1, false );
        println "all types:"
        allTypeNodes.each { println it }

        /* check if consistent if returns Integer, fails if returns String */

        OWLAxiom ax
        ax = odf.getOWLObjectPropertyAssertionAxiom( returns, exp1, iInteger );
        oom.addAxiom( ont, ax );

        assertTrue reasoner.isConsistent()

        oom.removeAxiom( ont, ax );

        ax = odf.getOWLObjectPropertyAssertionAxiom( returns, exp1, iString );
        oom.addAxiom( ont, ax );

        assertEquals( false, reasoner.isConsistent() )

        oom.removeAxiom( ont, ax );

//        assert   inferExpressionType(x, testOntology).contains( "IntSumExpression" )

    }

}
