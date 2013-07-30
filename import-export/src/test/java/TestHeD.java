import edu.asu.sharpc2b.transform.HeD2OwlDumper;
import org.junit.Ignore;
import org.junit.Test;

public class TestHeD {

    private static String targetFolder =  null;

    @Test
    @Ignore
    public void testLoadHeD() {

        try {
            HeD2OwlDumper dumper = new HeD2OwlDumper();

            dumper.compile( "org/hl7/v3/hed/newMentor_HeD_NQF0068_ECArule_example.xml",
                    "/home/davide/Projects/Git/sharp-editor/model-importer/target/generated-sources/org/hl7/v3/hed/newMentor.owl" );

        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}
