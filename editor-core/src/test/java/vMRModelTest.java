import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import edu.asu.sharpc2b.hed.impl.DomainHierarchyExplorer;
import org.junit.Test;

public class vMRModelTest {

    private static final String DOMAIN_MODEL_PATH = "ontologies/domain_models/domain-vmr.ofn";
    private static final String DOMAIN_NS = "urn:hl7-org:vmr:r2#";


    @Test
    public void checkClassInheritance() {
        DomainHierarchyExplorer explorer = DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS );

        System.out.println( explorer.getDomainHierarchy() );


        for ( String key : explorer.getSuperKlasses().keySet() ) {
            if ( key.equals( key.toUpperCase() ) || key.contains( "." ) ) {
                continue;
            }
            System.out.println( key + " -> " + explorer.getSuperKlasses().get( key ) );
        }
    }
}
