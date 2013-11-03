package edu.asu.sharpc2b.transform;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlocklyGenerator_backup {

    private static String template;
    private static TemplateRegistry registry;

    private static Map<String,Set<String>> palette = new HashMap<String, Set<String>>();

    {
        try {
            InputStream fis = FileUtil.getResourceAsStream( "/BlocklyTemplate.mvel" );
            byte[] data = new byte[ fis.available() ];
            fis.read( data );
            template = new String( data );
            fis.close();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        registry = new SimpleTemplateRegistry();
        CompiledTemplate compiled = TemplateCompiler.compileTemplate( template,
                                                                      (Map<String, Class<? extends org.mvel2.templates.res.Node >>) null );
        TemplateRuntime.execute( compiled,
                                 null,
                                 registry );

    }

    public BlocklyGenerator_backup() {

    }


    public void processOperators( File outputBlocklyDir, OWLOntology operatorOntology ) {

        OWLOntologyID mainId = operatorOntology.getOntologyID();
        OWLOntologyManager manager = operatorOntology.getOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();


        if ( ! outputBlocklyDir.exists() ) {
            outputBlocklyDir.mkdirs();
        }

        StringBuilder builder = new StringBuilder( );
        for ( OWLNamedIndividual individual : operatorOntology.getIndividualsInSignature( false ) ) {
            try {
                visitExpressionDefinition( individual, operatorOntology, manager, factory, builder );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        File operatorsOputput = new File( outputBlocklyDir.getPath() + File.separator + "operators.js" );
        try {
            if ( ! operatorsOputput.exists() ) {
                operatorsOputput.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream( operatorsOputput );
            fos.write( builder.toString().getBytes() );
            fos.flush();
            fos.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        File paletteOputput = new File( outputBlocklyDir.getPath() + File.separator + "palette.xml" );
        try {
            createPalette( paletteOputput );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }




    private void visitExpressionDefinition( OWLNamedIndividual individual, OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, StringBuilder builder ) throws ParserConfigurationException, TransformerException {
        PrefixManager pManager = IriUtil.getDefaultSharpOntologyFormat();
        boolean isUnary = false;
        boolean isBinary = false;
        boolean isTernary = false;
        boolean isNary = false;

        int arity = getArity( individual, ontology, manager, factory, pManager );

        String returnType = null;
        String[] argTypes = new String[3];

        Set<OWLIndividual> returnTypes = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:evaluatesAs", pManager ), ontology );
        if ( ! returnTypes.isEmpty() ) {
            if ( returnTypes.size() != 1 ) {
//                System.err.println( "WARNING - more than 1 return type detected for ops " + individual.getIRI() + " >>  "  + returnTypes );
            }
            returnType = returnTypes.iterator().next().toStringID();
        }

        Set<OWLIndividual> types;
        Set<OWLIndividual> types2;
        Set<OWLIndividual> types3;
        switch ( arity ) {

            case 1 :
                isUnary = true;
                types = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasFirstOperandType", pManager ), ontology );
                argTypes[0] = types.iterator().next().toStringID();
                break;

            case 2 :
                isBinary = true;
                types = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasFirstOperandType", pManager ), ontology );
                argTypes[0] = types.iterator().next().toStringID();
                types2 = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasSecondOperandType", pManager ), ontology );
                argTypes[1] = types2.iterator().next().toStringID();
                break;

            case 3 :
                isTernary = true;
                types = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasFirstOperandType", pManager ), ontology );
                argTypes[0] = types.iterator().next().toStringID();
                types2 = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasSecondOperandType", pManager ), ontology );
                argTypes[1] = types2.iterator().next().toStringID();
                types3 = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasThirdOperandType", pManager ), ontology );
                argTypes[2] = types3.iterator().next().toStringID();
                break;

            case Integer.MAX_VALUE :
                isNary = true;
                Object x = individual.getObjectPropertyValues( ontology );
                types = individual.getObjectPropertyValues( factory.getOWLObjectProperty( "ops:hasOperandType", pManager ), ontology );
                if ( types.size() != 1 ) {
//                    System.err.println( "WARNING - more than 1 arg type detected for NARY op " + individual.getIRI() + " >>  "  + types );
                }
                argTypes[0] = types.iterator().next().toStringID();
                break;

            default :
//                System.err.println( "WARNING - skipping non-op " + individual.getIRI() );
                // Individual is not an operator
                return;
        }


        String code = individual.getDataPropertyValues( factory.getOWLDataProperty( "skos-ext:code", pManager ), ontology ).iterator().next().getLiteral();
        String notation = individual.getDataPropertyValues( factory.getOWLDataProperty( "skos:notation", pManager ), ontology ).iterator().next().getLiteral();

        Map<String,Object> context = new HashMap<String, Object>( 7 );
        context.put( "iri", individual.getIRI().toString() );

        context.put( "returnType", returnType );
        context.put( "argTypes", argTypes );

        context.put( "color", pick() );
        context.put( "name", code );
        context.put( "tooltip", "HeD expression : " + notation );

        context.put( "isUnary", isUnary );
        context.put( "isBinary", isBinary );
        context.put( "isTernary", isTernary );
        context.put( "isNary", isNary );



        String js = (String) TemplateRuntime.execute( registry.getNamedTemplate( "blocky" ),
                                                      null,
                                                      context,
                                                      registry );


        builder.append( js );

        getPaletteGroup( ( getGroupName( ((OWLNamedIndividual) returnTypes.iterator().next() ) ) ) ).add( individual.getIRI().toString() );

    }

    private String getGroupName( OWLNamedIndividual iterator ) {

        String type = iterator.getIRI().getFragment();
        type = type.replace( "Type", "" );
        type = type.substring( 0, 1 ).toUpperCase() + type.substring( 1 );
        return type;
    }

    private void createPalette( File operatorsOutput ) throws ParserConfigurationException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dox = builder.newDocument();

        Element root = dox.createElement( "xml" );
        root.setAttribute( "id", "toolbox" );
        root.setAttribute( "style", "display: none" );
        dox.appendChild( root );


        for ( String grp : palette.keySet() ) {
            Element cat = dox.createElement( "category" );
            cat.setAttribute( "name", grp );
            for ( String type : palette.get( grp ) ) {
                Element block = dox.createElement( "block" );
                block.setAttribute( "type", type );
                cat.appendChild( block );
            }
            dox.getDocumentElement().appendChild( cat );
        }

        prettyPrint( dox.getDocumentElement(), System.out );

        try {
            if ( ! operatorsOutput.exists() ) {
                operatorsOutput.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream( operatorsOutput );
            prettyPrint( dox.getDocumentElement(), fos );
            fos.flush();
            fos.close();
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }


    }


    public static final void prettyPrint( Node xml, OutputStream out ) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
        tf.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        tf.setOutputProperty( OutputKeys.INDENT, "yes" );
        tf.transform( new DOMSource( xml ), new StreamResult(out));
    }

    private static int getArity( OWLNamedIndividual individual, OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, PrefixManager pManager ) {
        Set<OWLClassAssertionAxiom> klasses = ontology.getClassAssertionAxioms( individual );
        for ( OWLClassAssertionAxiom klass : klasses ) {
            if ( klass.getClassExpression().asOWLClass().equals( factory.getOWLClass( "ops:UnaryOperator", pManager ) ) ) {
                return 1;
            } else if ( klass.getClassExpression().asOWLClass().equals( factory.getOWLClass( "ops:BinaryOperator", pManager ) ) ) {
                return 2;
            } else if ( klass.getClassExpression().asOWLClass().equals( factory.getOWLClass( "ops:TernaryOperator", pManager ) ) ) {
                return 3;
            } else if ( klass.getClassExpression().asOWLClass().equals( factory.getOWLClass( "ops:NAryOperator", pManager ) ) ) {
                return Integer.MAX_VALUE;
            } else if ( klass.getClassExpression().asOWLClass().equals( factory.getOWLClass( "ops:AggregateOperator", pManager ) ) ) {
                return Integer.MAX_VALUE;
            }
        }
        return -1;
    }

    private static Integer pick() {
        return 42;
    }


    private static Set<String> getPaletteGroup( String grp ) {
        if ( ! palette.containsKey( grp ) ) {
            palette.put( grp, new HashSet<String>() );
        }
        return palette.get( grp );
    }

}
