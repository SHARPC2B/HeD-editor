import org.junit.Test;

public class ModelManagerTest {

    @Test
    public void testOntologyLoad() throws Exception {

        ModelManagerOwlAPIHermit mm = new ModelManagerOwlAPIHermit();

        mm.loadModel();

    }


    @Test
    public void testExampleLoad() throws Exception {

        ModelManagerOwlAPIHermit mm = new ModelManagerOwlAPIHermit();

        mm.processExample();

    }



}
