package sharpc2b.transform;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * User: rk Date: 4/24/13
 *
 * Transform a SKOS A-Box ICD9 codes ontology into a T-Box ontology.
 */
public class TransformIcd9ABoxToTBox
{

    private static IRI skosIRI = IRI.create( "http://www.w3.org/2004/02/skos/core" );

    private static String skosNamespace = skosIRI.toString() + "#";

    private OWLOntologyManager oom;

    private OWLDataFactory odf;

    private PrefixManager pm;

    private OWLOntology skos;

    private OWLOntology onta;

    private OWLOntology ontt;

    private Set<OWLOntology> onts;

    private OWLClass topCodeClass;

    private OWLObjectProperty refinesProp;

    private OWLObjectProperty skosBroaderTransitive;

    private OWLAnnotationProperty prefLabelProp;

    private OWLDataProperty icd9Prop;

    //==============================================================================

    public TransformIcd9ABoxToTBox ()
    {
        super();
    }

    //==============================================================================

    /**
     * This main() is an example / test case of usage.
     *
     * @param args
     * @throws OWLOntologyCreationException
     * @throws OWLOntologyStorageException
     */
    public static void main (String[] args)
            throws OWLOntologyCreationException, OWLOntologyStorageException
    {

        String commonCodesOntsRelPath = "/asu.edu/sharpc2b/codes/03/";
        String aOntRelPath = commonCodesOntsRelPath + "icd9-pub";
        String tOntRelPath = commonCodesOntsRelPath + "icd9-classes5";

//        static String aUriPath = "http:/" + aOntRelPath;
//        static String aNamespace = aUriPath + "#";
//        static IRI aIRI = IRI.create( aUriPath );
        String ontologiesHttpFileRoot = "/Users/rk/asu/prj" +
                "/sharp-editor/model-transform/src/test/resources/http";
        String ontologiesDocUriRoot = "file:" + ontologiesHttpFileRoot;
        IRI aDocIRI = IRI.create( ontologiesDocUriRoot + aOntRelPath + ".ofn" );
        IRI tDocIRI = IRI.create( ontologiesDocUriRoot + tOntRelPath + ".ofn" );

        String tUriPath = "http:/" + tOntRelPath;
        IRI tIRI = IRI.create( tUriPath );
        PrefixManager pm = new DefaultPrefixManager( tUriPath + "#" );
        ((DefaultPrefixManager) pm).setPrefix( "a:", "http:/" + aOntRelPath + "#" );
        ((DefaultPrefixManager) pm).setPrefix( "t:", "http:/" + tOntRelPath + "#" );
        ((DefaultPrefixManager) pm).setPrefix( "skos:", skosNamespace );

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        ((OWLFunctionalSyntaxOntologyFormat) oFormat).copyPrefixesFrom( pm );

        /*
         * Everything up to here was creating paths, IRIs, prefixes, etc.
         */
        OWLOntologyManager om = OWLManager.createOWLOntologyManager();

        OWLOntology ont1 = om.loadOntologyFromOntologyDocument( aDocIRI );

        TransformIcd9ABoxToTBox inst = new TransformIcd9ABoxToTBox();

        OWLOntology ont2 = inst.createTBoxOntology( ont1, tIRI );

        om.setOntologyFormat( ont2, oFormat );
        om.saveOntology( ont2, tDocIRI );
    }

    //==============================================================================

    public OWLOntology createTBoxOntology (OWLOntology publishedSkosIcd9ABoxOntology,
                                           IRI tboxOntologyIRI)
            throws OWLOntologyCreationException
    {

        initTBoxOntology( publishedSkosIcd9ABoxOntology, tboxOntologyIRI );

        initNamespaces();
        addImports();
        initObjects();
        addCommonAxioms();
        addAxiomsForCodes();

//        setUpOntologyFormat();
//        serialize();

        return this.ontt;
    }

    private void initTBoxOntology (OWLOntology onta,
                                   IRI tboxIRI)
            throws OWLOntologyCreationException
    {

        this.onta = onta;

        oom = onta.getOWLOntologyManager();
        odf = oom.getOWLDataFactory();

        this.ontt = createNewOntology( tboxIRI );

        onts = new HashSet<OWLOntology>();
//        onts.add( skos );
        ((HashSet<OWLOntology>) onts).add( ontt );
        ((HashSet<OWLOntology>) onts).add( onta );
//        onts = [skos, aboxModel];
    }

    private OWLOntology createNewOntology (IRI tboxIRI)
            throws OWLOntologyCreationException
    {

        return oom.createOntology( tboxIRI );
    }

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

        Set<OWLAxiom> axioms = new TreeSet();
        ((TreeSet) axioms).add( odf.getOWLSubObjectPropertyOfAxiom( skosBroaderTransitive, refinesProp ) );
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

        assert 1 == ((HashSet<OWLAnnotationAssertionAxiom>) labelAnnos).size();
        OWLAnnotationValue value = ((HashSet<OWLAnnotationAssertionAxiom>) labelAnnos).iterator().next()
                .getValue();
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
            DefaultGroovyMethods.println( this, "no icd9 code: " + codeInd );
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

//    private void setUpOntologyFormat ()
//    {
//
//        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
//        ((OWLFunctionalSyntaxOntologyFormat) oFormat).copyPrefixesFrom( pm );
//        oom.setOntologyFormat( tboxModel, oFormat );
//    }
//
//    private void serialize ()
//    {
//
//        oom.saveOntology( tboxModel, shar );
//        DefaultGroovyMethods
//                .invokeMethod( oom, "saveOntology", new Object[]{tboxModel, getProperty( "sharpCodesDocIRI" )} );
//    }

}
