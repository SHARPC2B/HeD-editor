import com.clarkparsia.common.base.SystemUtil;
import edu.asu.sharpc2b.hed.impl.BlocklyFactory;
import edu.asu.sharpc2b.hed.impl.EditorCoreImpl;
import edu.asu.sharpc2b.hed.impl.ExpressionFactory;
import edu.asu.sharpc2b.hed.impl.HeDArtifactData;
import edu.asu.sharpc2b.hed.impl.HeDNamedExpression;
import edu.asu.sharpc2b.hed.impl.ModelManagerOwlAPIHermit;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.ExpressionImpl;
import edu.asu.sharpc2b.prr.ProductionRule;
import edu.asu.sharpc2b.prr.ProductionRuleImpl;
import edu.asu.sharpc2b.prr.RuleVariable;
import edu.asu.sharpc2b.prr.RuleVariableImpl;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import edu.asu.sharpc2b.transform.OOwl2HedDumper;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.drools.io.ResourceFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.ontologydesignpatterns.ont.dul.dul.SocialPerson;
import org.ontologydesignpatterns.ont.dul.dul.SocialPersonImpl;
import org.purl.dc.terms.Agent;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

public class EditorCoreTest {

    @Test
    @Ignore
    public void testImportAndConversion() throws Exception {
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().addAppender(new NullAppender());

        //org.drools.io.Resource file = ResourceFactory.newClassPathResource( "DiabetesReminderRule.xml" );
        System.out.println( "Loading resource... " );
        long now = System.currentTimeMillis();
        org.drools.io.Resource file = ResourceFactory.newClassPathResource( "pertussis.xml" );
        byte[] hedStream = new byte[ file.getInputStream().available() ];
        file.getInputStream().read( hedStream );
        System.out.println( "Resource loaded.." + (System.currentTimeMillis() - now) );

        System.out.println( "Initializing core..." );
        now = System.currentTimeMillis();
        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();
        System.out.println( "Core ready.." + (System.currentTimeMillis() - now) );


        System.out.println( "Starting import process..." );
        now = System.currentTimeMillis();
        String uri = core.importFromStream( hedStream );
        System.out.println( "Done with the whole process.." + (System.currentTimeMillis() - now) );

        /*
        System.out.println( "Starting import process...twice " );
        now = System.currentTimeMillis();
        core.importFromStream( hedStream );
        System.out.println( "Done with the whole process gain.." + (System.currentTimeMillis() - now) );
        */

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


    @Test
    @Ignore
    public void testCreateExpressionFromBlockly() throws Exception {
        org.drools.io.Resource file = ResourceFactory.newClassPathResource( "block2.xml" );

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dox = builder.parse( file.getInputStream() );

        HeDKnowledgeDocument dok = new HeDKnowledgeDocumentImpl();
        dok.addArtifactId( "foo.test" );
        ProductionRule rule = new ProductionRuleImpl();
        dok.addContains( rule );
        RuleVariable ruleVar = new RuleVariableImpl();
        ruleVar.addName( "foobar" );
        rule.addProductionRuleBoundRuleVariable( ruleVar );
        Expression expr = new ExpressionImpl();
        ruleVar.addVariableFilterExpression( expr );

        SharpExpression sharp = new ExpressionFactory().parseBlockly( dox, BlocklyFactory.ExpressionRootType.EXPRESSION );
        expr.addBodyExpression( sharp );

        Document hed = new OOwl2HedDumper().serialize( dok );
        prettyPrint( hed, System.err );

        OWLOntology onto = ModelManagerOwlAPIHermit.getInstance().objectGraphToHeDOntology( dok );
        onto.getOWLOntologyManager().saveOntology( onto, new OWLFunctionalSyntaxOntologyFormat(), System.out );

    }

    public static final void prettyPrint( Node xml, OutputStream out ) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
        tf.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        tf.setOutputProperty( OutputKeys.INDENT, "yes" );
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tf.transform( new DOMSource( xml ), new StreamResult(out));
    }


    @Test
    @Ignore
    public void testPersists() throws Exception {
        EditorCoreImpl core = (EditorCoreImpl) EditorCoreImpl.getInstance();

        HeDKnowledgeDocument dok = new HeDKnowledgeDocumentImpl();
        dok.addArtifactId( "foo.test" );
        dok.addTitle( "My test" );
        Agent agent = new SocialPersonImpl();
        agent.addAgentName( "dsotty" );
        dok.addAuthor( agent );


        OWLOntology onto = ModelManagerOwlAPIHermit.getInstance().objectGraphToHeDOntology( dok );
        onto.getOWLOntologyManager().saveOntology( onto, new OWLFunctionalSyntaxOntologyFormat(), System.out );

        dok.getTitle().clear();
        dok.addTitle( "New title" );

        onto = ModelManagerOwlAPIHermit.getInstance().objectGraphToHeDOntology( dok );
        onto.getOWLOntologyManager().saveOntology( onto, new OWLFunctionalSyntaxOntologyFormat(), System.out );


    }



}
