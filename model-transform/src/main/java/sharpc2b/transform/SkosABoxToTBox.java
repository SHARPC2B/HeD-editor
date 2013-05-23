package sharpc2b.transform;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * User: rk Date: 4/24/13
 *
 * Transform a SKOS A-Box ICD9 codes ontology into a T-Box ontology.  See the main() method and the test
 * case SkosABoxToTBoxTest for examples of usage.  Basically, create an instance with the constructor and
 * call method 'createTBoxOntology(..)'.
 */
public class SkosABoxToTBox
{

    private static IRI skosIRI = IRI.create( "http://www.w3.org/2004/02/skos/core" );

    private static String skosNamespace = skosIRI.toString() + "#";

    private OWLOntologyManager oom;

    private OWLDataFactory odf;

    private PrefixManager pm;

//    private OWLOntology skos;

    private OWLOntology onta;

    private OWLOntology ontt;

//    private Set<OWLOntology> onts;

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

    /**
     * This main() is an example / test case of usage.
     */
    public static void main (String[] args)
            throws OWLOntologyCreationException, OWLOntologyStorageException
    {
        IRI aDocIRI;
        IRI tIRI;
        IRI tDocIRI;
        OWLOntologyFormat oFormat;
        {
            String sharpCodesOntsVersion = "03";
            String commonCodesOntsRelPath = "asu.edu/sharpc2b/codes/" + sharpCodesOntsVersion + "/";

            aDocIRI = IRI.create( FileUtil.getFileInTestResourceDir( "/onts/in/icd9-pub.ofn" ).toURI() );
            tDocIRI = IRI
                    .create( FileUtil.getFileInTestResourceDir( "/onts/out/icd9-classes.ofn" ).toURI() );

//            String tUriPath = "http://" + tOntRelPath;
            IRI aIRI;
            aIRI = IRI.create( "http://" + commonCodesOntsRelPath + "icd9-pub" );
            tIRI = IRI.create( "http://" + commonCodesOntsRelPath + "icd9-classes5" );
            PrefixManager pm;
            {
                pm = new DefaultPrefixManager( tIRI.toString() + "#" );
//                String aOntRelPath = commonCodesOntsRelPath + "icd9-pub";

                ((DefaultPrefixManager) pm).setPrefix( "a:", aIRI.toString() + "#" );
                ((DefaultPrefixManager) pm).setPrefix( "t:", tIRI.toString() + "#" );
                ((DefaultPrefixManager) pm).setPrefix( "skos:", IriUtil.skos + "#" );
            }
            oFormat = new OWLFunctionalSyntaxOntologyFormat();
            ((OWLFunctionalSyntaxOntologyFormat) oFormat).copyPrefixesFrom( pm );
        }
        /*
         * Everything up to here was creating paths, IRIs, prefixes, etc.
         */

        OWLOntologyManager om = OWLManager.createOWLOntologyManager();

        OWLOntology ont1 = om.loadOntologyFromOntologyDocument( aDocIRI );

        SkosABoxToTBox inst = new SkosABoxToTBox();

        OWLOntology ont2 = om.createOntology( tIRI );
        inst.createTBoxOntology( ont1, ont2 );

        om.setOntologyFormat( ont2, oFormat );
        om.saveOntology( ont2, tDocIRI );
    }

    //=================================================================================================

    /**
     * Create a T-Box Ontology equivalent to the input A-Box ontology.
     *
     * @param skosIcd9ABoxOntology an Ontology with a coding hierarchy, where each code is represented as an
     *                             OWL Individual that is an instance of skos:Concept, related to other
     *                             individuals via skos:broader or skos:broaderTransitive, the code values
     *                             are represented using skos:notation, and a friendly name of the concept
     *                             is indicated using skos:prefLabel.
     * @param tboxOntology         the T-Box Ontology that new axioms are added to.
     */
    public OWLOntology createTBoxOntology (final OWLOntology skosIcd9ABoxOntology,
                                           final OWLOntology tboxOntology)
            throws OWLOntologyCreationException
    {
//        initTBoxOntology( skosIcd9ABoxOntology, tboxOntologyIRI );

        this.onta = skosIcd9ABoxOntology;
        this.ontt = tboxOntology;

        this.oom = this.ontt.getOWLOntologyManager();
        this.odf = this.oom.getOWLDataFactory();

        initNamespaces();
        addImports();
        initObjects();
        addCommonAxioms();
        addAxiomsForCodes();

        setUpOntologyFormat();
//        serialize();

        return this.ontt;
    }

    //=================================================================================================

//    private void initTBoxOntology (OWLOntology onta,
//                                   IRI tboxIRI)
//            throws OWLOntologyCreationException
//    {
//        this.onta = onta;
//
//        oom = onta.getOWLOntologyManager();
//        odf = oom.getOWLDataFactory();
//
//        this.ontt = createNewOntology( tboxIRI );
//
////        onts = new HashSet<OWLOntology>();
////        onts.add( skos );
////        onts.add( ontt );
////        onts.add( onta );
////        onts = [skos, aboxModel];
//    }
//
//    private OWLOntology createNewOntology (IRI tboxIRI)
//            throws OWLOntologyCreationException
//    {
//        return oom.createOntology( tboxIRI );
//    }

    private void initNamespaces ()
    {
//        icd9ClassesIriString = sharpUriRoot + "/" + "icd9Classes"
//        icd9ClassesNamespace = icd9ClassesIriString + "#"
//        icd9ClassesIRI = IRI.create( icd9ClassesIriString );
//        icd9ClassesDocIRI = IRI.create( ontRootPath + "/" + "icd9Classes" + ".ofn" );

        String aboxNamespace = this.onta.getOntologyID().getOntologyIRI().toString() + "#";
        String tboxNamespace = this.ontt.getOntologyID().getOntologyIRI().toString() + "#";

        pm = new DefaultPrefixManager( tboxNamespace );
        ((DefaultPrefixManager) pm).setPrefix( "a:", aboxNamespace );
        ((DefaultPrefixManager) pm).setPrefix( "t:", tboxNamespace );
        ((DefaultPrefixManager) pm).setPrefix( "skos:", skosNamespace );
    }

    private void addImports ()
    {
        OWLImportsDeclaration importsAxiom;
        AddImport imp;

        importsAxiom = odf.getOWLImportsDeclaration( onta.getOntologyID().getOntologyIRI() );
        imp = new AddImport( ontt, importsAxiom );
        oom.applyChange( imp );

//        importsAxiom = odf.getOWLImportsDeclaration( skos.getOntologyID().getOntologyIRI() );
        importsAxiom = odf.getOWLImportsDeclaration( skosIRI );
        imp = new AddImport( ontt, importsAxiom );
        oom.applyChange( imp );
    }

    private void initObjects ()
    {
        topCodeClass = odf.getOWLClass( "a:ICD9_Concept", pm );
        refinesProp = odf.getOWLObjectProperty( "t:refines", pm );
        icd9Prop = odf.getOWLDataProperty( "skos:notation", pm );
        skosBroaderTransitive = odf.getOWLObjectProperty( "skos:broaderTransitive", pm );
        prefLabelProp = odf.getOWLAnnotationProperty( "skos:prefLabel", pm );
    }

    private void addCommonAxioms ()
    {
        /* Add [skos:broaderTransitive rdfs:subPropertyOf :refines] */

        Set<OWLAxiom> axioms = new TreeSet<OWLAxiom>();
        axioms.add( odf.getOWLSubObjectPropertyOfAxiom( skosBroaderTransitive, refinesProp ) );
        oom.addAxioms( ontt, axioms );
    }

    private void addAxiomsForCodes ()
    {
        Set<OWLIndividual> codeIndividuals = topCodeClass.getIndividuals( onta );
        assert codeIndividuals.size() > 0;

        for (OWLIndividual ind : codeIndividuals)
        {
            addAxiomsForCode( (OWLNamedIndividual) ind );
        }
    }

    private void addAxiomsForCode (OWLNamedIndividual codeInd)
    {
        Set<OWLAnnotationAssertionAxiom> annos = onta.getAnnotationAssertionAxioms( codeInd.getIRI() );

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

    private void addDefinitionUsingIndividual (OWLNamedIndividual codeInd,
                                               OWLClass codeClass)
    {
        OWLObjectHasValue hasCodeValue = odf.getOWLObjectHasValue( refinesProp, codeInd );
        OWLObjectIntersectionOf codeConceptAndValue = odf
                .getOWLObjectIntersectionOf( hasCodeValue, topCodeClass );
        OWLEquivalentClassesAxiom eqAxiom = odf
                .getOWLEquivalentClassesAxiom( codeClass, codeConceptAndValue );
        assert eqAxiom != null;
        oom.addAxiom( ontt, eqAxiom );
    }

    private void addDefinitionUsingCodeValue (OWLNamedIndividual codeInd,
                                              OWLClass codeClass)
    {
        Set<OWLLiteral> codeValues = codeInd.getDataPropertyValues( icd9Prop, onta );
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
        oom.addAxiom( ontt, eqAxiom );
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
        oom.setOntologyFormat( ontt, oFormat );
    }

    private void serialize (File outputFile)
            throws OWLOntologyStorageException
    {
        oom.saveOntology( ontt, IRI.create( outputFile ) );
    }

}
