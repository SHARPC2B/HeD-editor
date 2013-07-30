package edu.asu.sharpc2b.transform;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * User: rk Date: 4/24/13
 *
 * Transform a SKOS A-Box ICD9 codes ontology into a T-Box ontology.  See the main() method and the test
 * case SkosABoxToTBoxTest for examples of usage.  To use, create an instance with the constructor and call
 * method 'addTBoxAxioms(..)'.
 */
public class SkosABoxToTBox
{

    /**
     * The SKOS A-Box Ontology, containing a coding scheme hierarchy, such as for ICD-9 Conditions,
     * encoding using SKOS concepts such as skos:Concept, broaderThan, and notation.
     */
    private OWLOntology ontA;

    /**
     * The Ontology that the resulting T-Box axioms are added to.l
     */
    private OWLOntology ontT;

    private OWLOntologyManager oom;

    private OWLDataFactory odf;

    private PrefixOWLOntologyFormat pm;

    private OWLClass topCodeClass;

    private OWLObjectProperty refinesProp;

    private OWLObjectProperty skosBroaderTransitive;

    private OWLAnnotationProperty prefLabelProp;

    private OWLDataProperty icd9Prop;

    //==============================================================================

    public SkosABoxToTBox ()
    {
        super();
    }

    //==============================================================================

    //=================================================================================================

    /**
     * From the A-Box ontology provided as the first argument (skosABoxOntology), create the corresponding
     * T-Box axioms and add them to the ontology provided as the second argument (targetTBoxOntology).
     *
     * @param skosABoxOntology   an Ontology with a coding hierarchy, where each code is represented as an
     *                           OWL Individual that is an instance of skos:Concept, related to other
     *                           individuals via skos:broader or skos:broaderTransitive, the code values are
     *                           represented using skos:notation, and a friendly name of the concept is
     *                           indicated using skos:prefLabel.
     * @param targetTBoxOntology the T-Box Ontology that new axioms are added to.
     */
    public void addTBoxAxioms (final OWLOntology skosABoxOntology,
                               final OWLOntology targetTBoxOntology)
//            throws OWLOntologyCreationException
    {
//        initTBoxOntology( skosABoxOntology, tboxOntologyIRI );

        this.ontA = skosABoxOntology;
        this.ontT = targetTBoxOntology;

        this.oom = this.ontT.getOWLOntologyManager();
        this.odf = this.oom.getOWLDataFactory();

        initNamespaces();
        addImports();
        initObjects();
        addCommonAxioms();
        addAxiomsForCodes();

        setUpOntologyFormat();

    }

    //=================================================================================================

    private void initNamespaces ()
    {
        String aboxNamespace = this.ontA.getOntologyID().getOntologyIRI().toString() + "#";
        String tboxNamespace = this.ontT.getOntologyID().getOntologyIRI().toString() + "#";

        pm = IriUtil.getDefaultSharpOntologyFormat();
        pm.setDefaultPrefix( tboxNamespace );
        pm.setPrefix( "a:", aboxNamespace );
        pm.setPrefix( "t:", tboxNamespace );
        pm.setPrefix( "skos:", IriUtil.skosNamespace );
    }

    /**
     * Add owl:imports from the T-Box ontology to SKOS and to the A-Box ontology.
     */
    private void addImports ()
    {
        OWLImportsDeclaration importsAxiom;
        AddImport imp;

        importsAxiom = odf.getOWLImportsDeclaration( ontA.getOntologyID().getOntologyIRI() );
        imp = new AddImport( ontT, importsAxiom );
        oom.applyChange( imp );

//        importsAxiom = odf.getOWLImportsDeclaration( skos.getOntologyID().getOntologyIRI() );
        importsAxiom = odf.getOWLImportsDeclaration( IriUtil.skosIRI );
        imp = new AddImport( ontT, importsAxiom );
        oom.applyChange( imp );
    }

    /**
     * Create objects to be commonly used.
     */
    private void initObjects ()
    {
        topCodeClass = odf.getOWLClass( "a:ICD9_Concept", pm );
        refinesProp = odf.getOWLObjectProperty( "t:refines", pm );
        icd9Prop = odf.getOWLDataProperty( "skos:notation", pm );
        skosBroaderTransitive = odf.getOWLObjectProperty( "skos:broaderTransitive", pm );
        prefLabelProp = odf.getOWLAnnotationProperty( "skos:prefLabel", pm );
    }

    /**
     * Add Axioms that are not tied to a particular entity from the input ontology.
     */
    private void addCommonAxioms ()
    {
        /* Add [skos:broaderTransitive rdfs:subPropertyOf :refines] */

        Set<OWLAxiom> axioms = new TreeSet<OWLAxiom>();
        axioms.add( odf.getOWLSubObjectPropertyOfAxiom( skosBroaderTransitive, refinesProp ) );
        axioms.add( odf.getOWLTransitiveObjectPropertyAxiom( refinesProp ) );
        axioms.add( odf.getOWLReflexiveObjectPropertyAxiom( refinesProp ) );
        oom.addAxioms( ontT, axioms );
    }

    private void addAxiomsForCodes ()
    {
        Set<OWLIndividual> codeIndividuals = topCodeClass.getIndividuals( ontA );
        assert codeIndividuals.size() > 0;

        for (OWLIndividual ind : codeIndividuals)
        {
            addAxiomsForCode( (OWLNamedIndividual) ind );
        }
    }

    /**
     * The input OWL Individual is from the input ontology and corresponds to a code in a coding hierarchy.
     * Create the OWL Class and corresponding Axioms to add to the output ontology.
     */
    private void addAxiomsForCode (OWLNamedIndividual codeInd)
    {
        Set<OWLAnnotationAssertionAxiom> annos = ontA.getAnnotationAssertionAxioms( codeInd.getIRI() );

        Set<OWLAnnotationAssertionAxiom> labelAnnos = new HashSet<OWLAnnotationAssertionAxiom>();
        for (OWLAnnotationAssertionAxiom ax : annos)
        {
            if (ax.getProperty().equals( prefLabelProp ))
            {
                labelAnnos.add( ax );
            }
        }

        assert 1 == labelAnnos.size();
        OWLAnnotationValue value = labelAnnos.iterator().next().getValue();
        assert value instanceof OWLLiteral;
        String label = ((OWLLiteral) value).getLiteral();
        String name = localName( label );

        OWLClass codeClass = odf.getOWLClass( ":" + name, pm );

        addDefinitionUsingIndividual( codeInd, codeClass );
        addDefinitionUsingCodeValue( codeInd, codeClass );
    }

    /**
     *
     * @param codeInd
     * @param codeClass
     */
    private void addDefinitionUsingIndividual (OWLNamedIndividual codeInd,
                                               OWLClass codeClass)
    {
        OWLObjectHasValue hasCodeValue = odf.getOWLObjectHasValue( refinesProp, codeInd );
        OWLObjectIntersectionOf codeConceptAndValue = odf
                .getOWLObjectIntersectionOf( hasCodeValue, topCodeClass );
        OWLEquivalentClassesAxiom eqAxiom = odf
                .getOWLEquivalentClassesAxiom( codeClass, codeConceptAndValue );
        assert eqAxiom != null;
        oom.addAxiom( ontT, eqAxiom );
    }

    private void addDefinitionUsingCodeValue (OWLNamedIndividual codeInd,
                                              OWLClass codeClass)
    {
        Set<OWLLiteral> codeValues = codeInd.getDataPropertyValues( icd9Prop, ontA );
//        println codeValues.size();
        if (codeValues.isEmpty())
        {
            System.out.println( "in " + this.getClass() + ": no icd9 code: " + codeInd );
            return;
        }

        OWLLiteral litValue = codeValues.iterator().next();
        assert litValue != null;
//        println "icd9 code = " + litValue;
//        OWLLiteral litValue = odf.getOWLLiteral( codeValue );
        OWLDataHasValue hasCodeValue = odf.getOWLDataHasValue( icd9Prop, litValue );
        OWLObjectSomeValuesFrom some = odf.getOWLObjectSomeValuesFrom( refinesProp, hasCodeValue );

        OWLObjectIntersectionOf codeConceptAndValue = odf.getOWLObjectIntersectionOf( some, topCodeClass );
        OWLSubClassOfAxiom eqAxiom = odf.getOWLSubClassOfAxiom( codeConceptAndValue, codeClass );
        assert eqAxiom != null;
        oom.addAxiom( ontT, eqAxiom );
    }

    private String localName (String s)
    {
        Pattern pat = DefaultGroovyMethods.bitwiseNegate( "[^a-zA-Z0-9_]" );
        return DefaultGroovyMethods.replaceAll( s, pat, new Closure<String>( this, this )
        {
            public String doCall (Object it)
            {
                return "_";
            }

            public String doCall ()
            {
                return doCall( null );
            }

        } );
    }

    private void setUpOntologyFormat ()
    {
        PrefixOWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( pm );
        oom.setOntologyFormat( ontT, oFormat );
    }

}
