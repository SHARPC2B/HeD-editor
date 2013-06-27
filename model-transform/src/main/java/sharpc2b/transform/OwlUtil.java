package sharpc2b.transform;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import java.io.File;
import java.io.InputStream;

/**
 * User: rk Date: 6/3/13 Time: 8:54 PM
 */
public class OwlUtil
{

    static final String sharpEditorOntologiesDirInProject
            = "/model-transform/src/main/resources/onts/editor-models/";

    /**
     * Need a resource path (location in classpath) for a core Sharp ontology file.  This is used to find
     * other ontology files in the folder for code Sharp ontologies.
     */
    private static String knownSharpOntologyResourcePath = "onts/editor-models/sharp.owl";

    public static File getSharpEditorOntologyDir ()
    {
        File knownFile = FileUtil.getExistingResourceAsFile( knownSharpOntologyResourcePath );
        return knownFile.getParentFile();
    }

    public static File getSharpEditorOntologyFile (final String filename)
    {
        File dir = getSharpEditorOntologyDir();
        return new File( dir, filename );
    }

    public static OWLOntologyManager createSharpOWLOntologyManager ()
    {
        OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
        addSharpEditorIriMappings( oom );
        return oom;
    }

    public static final OWLOntology loadOntologyFromResource (final OWLOntologyManager oom,
                                                              final String resourcePath)
            throws OWLOntologyCreationException
    {
        InputStream inputStream = FileUtil.getResourceAsStream( resourcePath );
        return oom.loadOntologyFromOntologyDocument( inputStream );
    }

    /**
     * Add IRI mappings, to an OWLOntologyManager, for standard Sharp ontology files.  Doing so allows the
     * OWLAPI to resolve and load ontologies based on the logical/name ontology IRI, especially ontologies
     * referenced by an owl:imports.
     */
    private static void addSharpEditorIriMappings (OWLOntologyManager oom)
    {
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/prr-sharp", "prr-sharp.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/prr", "prr-core.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/dc2dul", "dc2dul.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/skos-ext", "skos-ext.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/metadata", "metadata.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/actions", "actions.owl" );
        addSharpIriMapping( oom, "http://bmi.asu.edu/sharpc2b/skoslmm", "skos_lmm.owl" );
        addSharpIriMapping( oom, "http://bmi.asu.edu/sharpc2b/sharp-master", "sharp.owl" );
        addSharpIriMapping( oom, "http://purl.org/NET/dc_owl2dl/", "dc_owl2dl.owl" );
        addSharpIriMapping( oom, "http://purl.org/NET/dc_owl2dl", "dc_owl2dl.owl" );
        addSharpIriMapping( oom, "http://www.ontologydesignpatterns.org/ont/lmm/LMM_L1.owl", "LMM_L1.owl" );
        addSharpIriMapping( oom, "http://www.ontologydesignpatterns.org/ont/dul/IOLite.owl", "IOLite.owl" );
        addSharpIriMapping( oom, "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl", "DUL.owl" );
        addSharpIriMapping( oom, "http://www.w3.org/2004/02/skos/core", "skos-core.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/ops", "expr-core.owl" );
//        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/opsb", "demos/diffnames/expr-core-both.owl" );
//        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/opsc", "demos/diffnames/expr-core-coupled.owl" );
//        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/opsd", "demos/diffnames/expr-core-decoupled.owl" );
    }

    /**
     * Add a single IRI mapping, that maps an ontology IRI to a document location IRI, relative to the File
     * folder where ontologies are stored in the "editor-models" project.
     *
     * @param oom               The OWLOntologyManager the mapping is added to.
     * @param ontologyIriString IRI has a String
     * @param filename          Relative file location, such as "demos/expr-core-coupled.owl"
     */
    static void addSharpIriMapping (OWLOntologyManager oom,
                                    String ontologyIriString,
                                    String filename)
    {
        File sharpOntDir = getSharpEditorOntologyDir();
        File ontFile;
        ontFile = FileUtil.getFileInProjectDir( sharpEditorOntologiesDirInProject + filename );
        ontFile = new File( getSharpEditorOntologyDir(), filename );
        SimpleIRIMapper m = new SimpleIRIMapper( IRI.create( ontologyIriString ), IRI.create( ontFile ) );
        oom.addIRIMapper( m );
    }

    public static Reasoner.ReasonerFactory getHermitReasonerFactory ()
    {
        return new Reasoner.ReasonerFactory();  // Hermit
    }

    public static OWLReasoner getHermitReasoner (final OWLOntology ont,
                                                 final boolean buffering)
    {
        return buffering
                ? getHermitReasonerFactory().createReasoner( ont )
                : getHermitReasonerFactory().createNonBufferingReasoner( ont );
    }

}
