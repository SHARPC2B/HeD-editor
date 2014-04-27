
//import org.coode.owlapi.turtle.TurtleOntologyFormat;
//import org.drools.shapes.xsd.Xsd2Owl;
//import org.drools.shapes.xsd.Xsd2OwlImpl;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.semanticweb.owlapi.model.OWLOntology;
//import org.w3._2001.xmlschema.Schema;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Properties;

import edu.asu.sharpc2b.ClassPathResource;
import edu.asu.sharpc2b.transform.StylesheetDumper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

import javax.xml.validation.Schema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestXSD {

    private static String targetFolder =  null;
//
//    @BeforeClass
//    public static void init() {
//        Properties prop = new Properties();
//        try {
//            prop.load( TestXSD.class.getResourceAsStream( "test.properties" ) );
//            targetFolder = prop.getProperty( "targetFolder" );
//        } catch ( IOException e ) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void testVMRSchema() {
//
//        try {
//            Xsd2Owl converter = Xsd2OwlImpl.getInstance();
//
//            URL url = converter.getSchemaURL( "xsd/vmr/vmr-clean.xsd" );
//            Schema x = converter.parse( url );
//
//            OWLOntology onto = converter.transform( x, url, true, false );
//
//            stream( onto, converter, "vmr.turtle.owl" );
//
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testDatatypeSchema() {
//
//        try {
//            Xsd2Owl converter = Xsd2OwlImpl.getInstance();
//
//            URL url = converter.getSchemaURL( "xsd/vmr/datatypes-clean.xsd" );
//            Schema x = converter.parse( url );
//
//            OWLOntology onto = converter.transform( x, url, true, false );
//
//            stream( onto, converter, "datatypes.turtle.owl" );
//
//        } catch ( Exception e ) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//    }
//
//
//    public void stream( OWLOntology onto, Xsd2Owl converter, String fileName ) throws FileNotFoundException
//    {
//        if ( ! fileName.endsWith( File.separator ) ) {
//            fileName = fileName + File.separator;
//        }
//        if ( targetFolder != null && new File( targetFolder ).exists() ) {
//            converter.stream( onto,
//                    new FileOutputStream( targetFolder + fileName ),
//                    new TurtleOntologyFormat()
//            );
//        }
//    }
//
//
//    @Test
//    public void testArdenSchema() {
//
//        try {
//            Xsd2Owl converter = Xsd2OwlImpl.getInstance();
//
//            URL url = converter.getSchemaURL( "xsd/arden/Arden.xsd" );
//            Schema x = converter.parse( url );
//
//            OWLOntology onto = converter.transform( x, url, true, false );
//
//            stream( onto, converter, "arden.turtle.owl" );
//
//        } catch ( Exception e ) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//    }
//
//
//    @Test
//    public void testHeDSchema() {
//
//        try {
//            Xsd2Owl converter = Xsd2OwlImpl.getInstance();
//
//            URL url = converter.getSchemaURL( "xsd/HeD/knowledgedocument.xsd" );
//            Schema x = converter.parse( url );
//
//            OWLOntology onto = converter.transform( x, url, true, false );
//
//            stream( onto, converter, "hed.turtle.owl" );
//
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    @Test
//    public void testCleanup() {
//
//        try {
//            String clean = Xsd2OwlImpl.getInstance().compactXMLSchema( "vmr.xsd" );
//            System.out.println( clean );
//        } catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//
//    }

    @Test
    public void testXSLT() throws IOException {
        InputStream data = new ClassPathResource( "DiabetesReminderRule.xml" ).getInputStream();
        byte[] xml = new byte[ data.available() ];
        data.read( xml );
        byte[] out = new StylesheetDumper().transform( xml );
        System.out.println( new String( out ) );
    }


}
