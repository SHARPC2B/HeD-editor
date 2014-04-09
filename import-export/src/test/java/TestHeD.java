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

            dumper.compile( "DiabetesReminderRule.xml",
                    "/home/davide/Projects/Git/HeD-editor/sharp-editor/import-export/src/test/resources/diabetes.owl" );

        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}
