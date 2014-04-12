import edu.asu.sharpc2b.hed.impl.EditorCoreImpl;
import edu.asu.sharpc2b.hed.impl.HeDArtifactData;
import edu.asu.sharpc2b.hed.impl.HeDNamedExpression;
import edu.asu.sharpc2b.hed.impl.ModelManagerOwlAPIHermit;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.transform.OOwl2HedDumper;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.drools.io.ResourceFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

public class EditorCoreTest {

    @Test
    @Ignore
    public void testImportAndConversion() throws Exception {
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(new NullAppender());

        org.drools.io.Resource file = ResourceFactory.newClassPathResource( "DiabetesReminderRule.xml" );
        byte[] hedStream = new byte[ file.getInputStream().available() ];
        file.getInputStream().read( hedStream );

        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();

        String uri = core.importFromStream( hedStream );
        HeDArtifactData data = core.getCurrentArtifact();

        assertEquals( uri, data.getArtifactId() );

        HeDKnowledgeDocument dok = data.getKnowledgeDocument();

        Document dox = new OOwl2HedDumper().serialize( dok );
        OOwl2HedDumper.dump( dox, System.err );

        core.deleteArtifact( uri );

    }



    @Test
    @Ignore
    public void testSaveArtifact() throws Exception {
        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();

        String uri = core.createArtifact();
        HeDArtifactData data = core.getCurrentArtifact();

        core.saveArtifact( uri );

        core.deleteArtifact( uri );
    }

    @Test
    @Ignore
    public void testOpenArtifact() throws Exception {
        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();
        String uri = null;
        try {
            uri = core.createArtifact();
            core.closeArtifact();
            core.openArtifact( uri );
        } catch ( Throwable t ) {
            t.printStackTrace();
            if ( uri != null ) {
                core.deleteArtifact( uri );
            }
        }

    }

    @Test
    @Ignore
    public void testImportExpressions() throws Exception {
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(new NullAppender());

        org.drools.io.Resource file = ResourceFactory.newClassPathResource( "Diabetes_MetadataOnly.xml" );
        byte[] hedStream = new byte[ file.getInputStream().available() ];
        file.getInputStream().read( hedStream );

        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();

        String uri = core.importFromStream( hedStream );
        HeDArtifactData data = core.getCurrentArtifact();
        HeDNamedExpression expr = data.getNamedExpression( "foo" );
        System.out.println( new String( expr.getDoxBytes() ) );

        core.deleteArtifact( uri );

    }



    @Test
    @Ignore
    public void testBlocklyGeneration() throws Exception {
        org.drools.io.Resource file = ResourceFactory.newClassPathResource( "DiabetesReminderRule.xml" );
        byte[] hedStream = new byte[ file.getInputStream().available() ];
        file.getInputStream().read( hedStream );

        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();

        String uri = core.importFromStream( hedStream );
        HeDArtifactData data = core.getCurrentArtifact();

        assertEquals( uri, data.getArtifactId() );

        HeDKnowledgeDocument dok = data.getKnowledgeDocument();

    }


}
