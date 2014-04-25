package edu.asu.sharpc2b.transform;

import edu.asu.sharpc2b.ClassPathResource;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StylesheetDumper implements HeDExporter {

    public byte[] export( HeDKnowledgeDocument dok, OWLOntology dow ) {
        byte[] xml = new OOwl2HedDumper().export( dok, dow );

        return transform( xml );
    }

    public byte[] transform( byte[] xml ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ClassPathResource res = new ClassPathResource( "stylesheet/healthedecisions-generic.xsl" );
            StreamSource stylesource = new StreamSource( res.getInputStream() );
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setURIResolver( new URIResolver() {
                public Source resolve( String href, String base ) throws TransformerException {
                    try {
                        return new StreamSource( new ClassPathResource( href ).getInputStream() );
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            Transformer transformer = transformerFactory.newTransformer( stylesource );

            transformer.transform( new StreamSource( new ByteArrayInputStream( xml ) ), new StreamResult( baos ) );

            return baos.toByteArray();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return new byte[ 0 ];
    }
}
