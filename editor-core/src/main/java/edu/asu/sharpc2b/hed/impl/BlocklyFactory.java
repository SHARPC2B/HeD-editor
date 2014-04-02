package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.empire.annotation.RdfsClass;
import edu.asu.sharpc2b.ops.ClinicalRequestExpression;
import edu.asu.sharpc2b.ops.IntegerLiteral;
import edu.asu.sharpc2b.ops.IteratorExpression;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops.Variable;
import edu.asu.sharpc2b.ops.VariableExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

public class BlocklyFactory {

    public static byte[] fromExpression( String name, SharpExpression sharpExpression ) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            Document dox = builderFactory.newDocumentBuilder().newDocument();

            visitRoot( name, sharpExpression, dox );

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            dump( dox, baos );
            return baos.toByteArray();
        } catch ( ParserConfigurationException e ) {
            e.printStackTrace();
            return new byte[ 0 ];
        }
    }

    private static Element visitRoot( String name, SharpExpression sharpExpression, Document dox ) {
        Element root = dox.createElement( "xml" );
        Element block = dox.createElement( "block" );
        block.setAttribute( "type", "logic_root" );
        block.setAttribute( "inline", "false" );
        block.setAttribute( "deletable", "false" );
        block.setAttribute( "movable", "false" );
        block.setAttribute( "x", "0" );
        block.setAttribute( "y", "0" );

        Element eName = dox.createElement( "field" );
        eName.setAttribute( "name", "NAME" );
        eName.setTextContent( name );
        block.appendChild( eName );

        Element value = dox.createElement( "value" );
        value.setAttribute( "name", "NAME" );
        block.appendChild( value );

        visit( sharpExpression, value, dox );

        root.appendChild( block );
        dox.appendChild( root );
        return block;
    }

    private static void visit( SharpExpression sharpExpression, Element parent, Document dox ) {
        Class actualClass = mapClass( sharpExpression.getClass() );
        String type = extractType( actualClass );

        Element block = dox.createElement( "block" );
        block.setAttribute( "type", type );
        block.setAttribute( "inline", "false" );

        if ( sharpExpression instanceof IteratorExpression ) {
            IteratorExpression iter = (IteratorExpression) sharpExpression;
            Variable v = iter.getIterator().get( 0 );
        } else if ( sharpExpression instanceof IntegerLiteral ) {
            IntegerLiteral lit = (IntegerLiteral) sharpExpression;
            Element value = dox.createElement( "value" );
                value.setAttribute( "name", "ARG_0" );
                Element intg = dox.createElement( "block" );
                intg.setAttribute( "type", "xsd:int" );
                    Element fld = dox.createElement( "field" );
                    fld.setAttribute( "name", "VALUE" );
                    List values = lit.getHasAttribute().get( 0 ).getAttributeValue();
                    fld.setTextContent( values.get( 0 ).toString() );
                    intg.appendChild( fld );
                value.appendChild( intg );
            block.appendChild( value );
        }

        parent.appendChild( block );
    }

    private static String extractType( Class actualClass ) {
        RdfsClass klass = (RdfsClass) actualClass.getAnnotation( RdfsClass.class );
        return klass.value();
    }

    private static Class mapClass( Class<?> aClass ) {
        if ( IteratorExpression.class.isAssignableFrom( aClass ) ) {
            return ClinicalRequestExpression.class;
        } else {
            return aClass;
        }
    }


    protected static void dump( Document dox, OutputStream stream ) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult( stream );
            DOMSource source = new DOMSource( dox );
            transformer.transform( source, result );
        } catch ( TransformerException e ) {
            e.printStackTrace();
        }
    }
}
