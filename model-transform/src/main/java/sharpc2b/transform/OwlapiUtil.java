package sharpc2b.transform;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import java.io.File;

/**
 * User: rk Date: 6/3/13 Time: 8:54 PM
 */
public class OwlapiUtil
{

    public static OWLOntologyManager createSharpOWLOntologyManager ()
    {
        OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
        addSharpIriMappings( oom );
        return oom;
    }

    public static void addSharpIriMappings (OWLOntologyManager oom)
    {
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/prr-sharp", "prr-sharp.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/prr", "prr-core.owl" );
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/ops", "expr-core.owl" );
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
        addSharpIriMapping( oom, "http://asu.edu/sharpc2b/ops", "demos/expr-core-coupled.owl" );
    }

    public static void addSharpIriMapping (OWLOntologyManager oom,
                                           String ontologyIriString,
                                           String relativeDocIriString)
    {
        final String ontDirRelative = "/editor-models/src/main/resources/ontologies/";
        File ontFile = FileUtil.getFileInProjectDir( ontDirRelative + relativeDocIriString );
        SimpleIRIMapper m = new SimpleIRIMapper( IRI.create( ontologyIriString ), IRI.create( ontFile ) );
        oom.addIRIMapper( m );
    }

}
