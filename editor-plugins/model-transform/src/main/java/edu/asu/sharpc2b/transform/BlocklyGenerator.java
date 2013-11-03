package edu.asu.sharpc2b.transform;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import uk.ac.manchester.cs.owl.owlapi.OWLIndividualImpl;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BlocklyGenerator {

    private static String template;
    private static TemplateRegistry registry;



    private static Map<String,Set<String>> palette = new LinkedHashMap<String, Set<String>>();

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

    public BlocklyGenerator() {

    }


    public void processOperators( File outputBlocklyDir, OWLOntology operatorOntology ) {

        OWLOntologyID mainId = operatorOntology.getOntologyID();
        OWLOntologyManager manager = operatorOntology.getOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();


        if ( ! outputBlocklyDir.exists() ) {
            outputBlocklyDir.mkdirs();
        }

        StringBuilder builder = new StringBuilder( );
        Set<OWLSubClassOfAxiom> subs = operatorOntology.getSubClassAxiomsForSuperClass( factory.getOWLClass( IriUtil.opsIRI( "OperatorExpression" ) ) );
        for ( OWLSubClassOfAxiom sub : subs ) {
            try {
                visitExpressionDefinition( sub.getSubClass(), operatorOntology, manager, factory, builder );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        Set<OWLSubClassOfAxiom> subs2 = operatorOntology.getSubClassAxiomsForSuperClass( factory.getOWLClass( IriUtil.opsIRI( "PrimitiveExpression" ) ) );
        Set<String> primitiveTypes = new HashSet<String>();
        for ( OWLSubClassOfAxiom sub : subs2 ) {
            try {
                List<String> types = visitLiteralDefinition( sub.getSubClass(), operatorOntology, manager, factory, builder );
                primitiveTypes.addAll( types );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        Set<OWLSubClassOfAxiom> allSubs = operatorOntology.getAxioms( AxiomType.SUBCLASS_OF, true );

        for ( OWLSubClassOfAxiom sub : allSubs ) {
            try {
                if ( ! sub.getSuperClass().isAnonymous() && sub.getSuperClass().asOWLClass().equals( factory.getOWLClass( IriUtil.opsIRI( "RequestExpression" ) ) ) ) {
                    visitLiteralDefinition( sub.getSubClass(), operatorOntology, manager, factory, builder );
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        String basicBlocks = (String) TemplateRuntime.execute( registry.getNamedTemplate( "basicBlocks" ), null, Collections.emptyMap(), registry );
        builder.append( basicBlocks );


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
            createPalette( paletteOputput, primitiveTypes );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    private List<String> visitLiteralDefinition( OWLClassExpression primitive, OWLOntology operatorOntology, OWLOntologyManager manager, OWLDataFactory factory, StringBuilder builder ) {
        Set<OWLClassExpression> subs = superClosure( primitive, operatorOntology );

        IRI id = primitive.asOWLClass().getIRI();
        String name = id.getFragment().replace( "Literal", "" );

        List<String> paramNames = new ArrayList<String>(  );
        List<String> paramTypes = new ArrayList<String>(  );
        List<String> paramTypeNames = new ArrayList<String>(  );
        OWLClassExpression retType = null;


        for ( OWLClassExpression expr : subs ) {
            if ( expr instanceof OWLObjectSomeValuesFrom && ( (OWLObjectSomeValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "hasAttribute" ) ) ) {
                OWLClassExpression target = ( (OWLObjectSomeValuesFrom) expr ).getFiller();
                if ( target instanceof OWLObjectIntersectionOf ) {
                    for ( OWLClassExpression arg : ( (OWLObjectIntersectionOf) target ).getOperands() ) {
                        if ( arg instanceof OWLDataHasValue && ( (OWLDataHasValue) arg ).getProperty().asOWLDataProperty().getIRI().equals( IriUtil.opsIRI( "attributeName" ) ) ) {
                            paramNames.add( ( (OWLDataHasValue) arg ).getValue().getLiteral() );
                        }
                        if ( arg instanceof OWLDataHasValue && ( (OWLDataHasValue) arg ).getProperty().asOWLDataProperty().getIRI().equals( IriUtil.opsIRI( "attributeType" ) ) ) {
                            paramTypes.add( ( (OWLDataHasValue) arg ).getValue().getLiteral() );
                            String paramName =  ( (OWLDataHasValue) arg ).getValue().getLiteral();
                            paramName = ( paramName.substring( paramName.lastIndexOf( ":" ) + 1 ) );
                            paramTypeNames.add( paramName );
                        }
                    }
                }
            } else if ( expr instanceof OWLObjectSomeValuesFrom && ( (OWLObjectSomeValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "returns" ) ) ) {
                retType = ( (OWLObjectSomeValuesFrom) expr ).getFiller();
            }
        }

        if ( ! name.isEmpty() ) {
            Map<String,Object> context = new HashMap<String,Object>();
            context.put( "iri", id.toString() );
            context.put( "name", name );
            context.put( "paramNames", paramNames );
            context.put( "paramTypes", paramTypes );
            context.put( "paramTypeNames", paramTypeNames );
            context.put( "returnType", retType != null ? retType.asOWLClass().getIRI() : null );
            context.put( "color", pick() );
            context.put( "tooltip", "HeD literal : " + name );

            String js = (String) TemplateRuntime.execute( registry.getNamedTemplate( "blockyPrimitive" ),
                                                          null,
                                                          context,
                                                          registry );

            builder.append( js );

            getPaletteGroup( "Literals" ).add( id.toString() );
            if ( retType != null ) {
                String iri = retType.asOWLClass().getIRI().toString();
                getPaletteGroup( getGroupName( iri.substring( iri.indexOf( "#" ) + 1 ) ) ).add( id.toString() );
            }

        }

        return paramTypes;
    }



    private void visitExpressionDefinition( OWLClassExpression expression, OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, StringBuilder builder ) throws ParserConfigurationException, TransformerException {
        PrefixManager pManager = IriUtil.getDefaultSharpOntologyFormat();

        Set<OWLClassExpression> supers = superClosure( expression, ontology ) ;

        int arity = getArity( expression, supers, ontology, manager, factory, pManager );

        List<String>[] argTypes = new List[3];
        List<String>[] argTypeNames = new List[3];
        for ( int j = 0; j < 3; j++ ) {
            argTypes[ j ] = new ArrayList<String>();
            argTypeNames[ j ] = new ArrayList<String>();
        }

        Set<String> returnTypes = new HashSet<>();
        for ( OWLClassExpression expr : supers ) {
            if ( expr instanceof OWLObjectSomeValuesFrom && ( (OWLObjectSomeValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "returns" ) ) ) {
                returnTypes.add( ( (OWLObjectSomeValuesFrom) expr ).getFiller().asOWLClass().getIRI().toString() );
            }
        }

        String id = expression.asOWLClass().getIRI().toString();
        String code = "BUG";

        boolean isUnary = arity == 1;
        boolean isBinary = arity == 2;
        boolean isTernary = arity == 3;
        boolean isNary = arity == -1;


        for ( OWLClassExpression expr : supers ) {
            if ( expr instanceof OWLObjectAllValuesFrom ) {
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "firstOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupReturnType( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    argTypes[0] = extractTypes( fil );
                    argTypeNames[0] = extractTypeNames( fil );
                }
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "secondOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupReturnType( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    argTypes[1] = extractTypes( fil );
                    argTypeNames[1] = extractTypeNames( fil );
                }
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "thirdOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupReturnType( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    argTypes[2] = extractTypes( fil );
                    argTypeNames[2] = extractTypeNames( fil );
                }
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "hasOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupReturnType( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    argTypes[0] = extractTypes( fil );
                    argTypeNames[0] = extractTypeNames( fil );
                }

                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "opCode" ) ) ) {
                    OWLObjectIntersectionOf codeConcept = (OWLObjectIntersectionOf) ( (OWLObjectAllValuesFrom) expr ).getFiller();
                    for ( OWLClassExpression inner : codeConcept.getOperands() ) {
                        if ( inner instanceof OWLDataHasValue && ( (OWLDataHasValue) inner ).getProperty().asOWLDataProperty().getIRI().equals( IriUtil.skosIRI( "notation" ) ) ) {
                            code = ( (OWLDataHasValue) inner ).getValue().getLiteral();
                        }
                    }
                }
            }
        }



        Map<String,Object> context = new HashMap<String, Object>( 7 );
        context.put( "iri", id );

        context.put( "returnType", new ArrayList<String>( returnTypes ) );
        context.put( "argTypes", argTypes );
        context.put( "argTypeNames", argTypeNames );

        context.put( "color", pick() );
        context.put( "name", code );
        context.put( "tooltip", "HeD expression : " + code );

        context.put( "isUnary", isUnary );
        context.put( "isBinary", isBinary );
        context.put( "isTernary", isTernary );
        context.put( "isNary", isNary );



        String js = (String) TemplateRuntime.execute( registry.getNamedTemplate( "blocky" ),
                                                      null,
                                                      context,
                                                      registry );


        builder.append( js );

        for ( String retType : returnTypes ) {
            getPaletteGroup( getGroupName( retType.substring( retType.indexOf( "#" ) + 1 ) ) ).add( id );
        }

    }

    private List<String> extractTypes( Set<OWLClassExpression> fil ) {
        if ( fil == null || fil.isEmpty() ) {
            return new ArrayList();
        } else {
            List<String> types = new ArrayList<String>( fil.size() );
            for ( OWLClassExpression expr : fil ) {
                types.add( expr.asOWLClass().getIRI().toString() );
            }
            return types;
        }
    }

    private List<String> extractTypeNames( Set<OWLClassExpression> fil ) {
        if ( fil == null || fil.isEmpty() ) {
            return Arrays.asList( "any" );
        } else {
            List<String> types = new ArrayList<String>( fil.size() );
            for ( OWLClassExpression expr : fil ) {
                String fullName = expr.asOWLClass().getIRI().getFragment();
                fullName.replace( "Type", "" );
                fullName = fullName.substring( 0, Math.min( 3, fullName.length() ) );
                types.add( fullName );
            }
            return types;
        }
    }

    private Set<OWLClassExpression> lookupReturnType( OWLClassExpression expression, OWLOntology ontology ) {
        Set<OWLClassExpression> supers = superClosure( expression, ontology ) ;

        Set<OWLClassExpression> returnTypes = new LinkedHashSet<>();
        for ( OWLClassExpression expr : supers ) {
            if ( expr instanceof OWLObjectSomeValuesFrom && ( (OWLObjectSomeValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "returns" ) ) ) {
                returnTypes.add( ( (OWLObjectSomeValuesFrom) expr ).getFiller() );
            }
        }
        return returnTypes.isEmpty() ? null : returnTypes;
    }

    private Set<OWLClassExpression> superClosure( OWLClassExpression expression, OWLOntology ontology ) {
        Set<OWLClassExpression> ancestors = new TreeSet();
        superClosure( expression, ontology, ancestors );
        return ancestors;
    }

    private void superClosure( OWLClassExpression expression, OWLOntology ontology, Set<OWLClassExpression> ancestors ) {
        Set<OWLSubClassOfAxiom> supers = ontology.getAxioms( AxiomType.SUBCLASS_OF, true );
        for ( OWLSubClassOfAxiom sup : supers ) {
            if ( sup.getSubClass().equals( expression ) ) {
                OWLClassExpression parent = sup.getSuperClass();
                ancestors.add( parent );
                if ( ! parent.isAnonymous() ) {
                    superClosure( parent, ontology, ancestors );
                }
            }
        }
        Set<OWLEquivalentClassesAxiom> equivs = ontology.getAxioms( AxiomType.EQUIVALENT_CLASSES, true );
        for ( OWLEquivalentClassesAxiom eq : equivs ) {
            List<OWLClassExpression> eqClasses = eq.getClassExpressionsAsList();
            if ( eqClasses.contains( expression ) ) {
                OWLClassExpression parent = eqClasses.get( 1 );
                ancestors.add( parent );
                if ( ! parent.isAnonymous() ) {
                    superClosure( parent, ontology, ancestors );
                }
            }
        }

    }

    private String getGroupName( String expr ) {
        String type = expr;
        type = type.replace( "Expression", "" );
        type = type.replace( "Type", "" );
        type = type.substring( 0, 1 ).toUpperCase() + type.substring( 1 );
        return type;
    }

    private void createPalette( File operatorsOutput, Set<String> primitiveTypes ) throws ParserConfigurationException, TransformerException {
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

        Element cat = dox.createElement( "category" );
        cat.setAttribute( "name", "Datatypes" );
        for ( String type : primitiveTypes ) {
            Element block = dox.createElement( "block" );
            block.setAttribute( "type", type );
            cat.appendChild( block );
        }
        dox.getDocumentElement().appendChild( cat );

        Element varCat = dox.createElement( "category" );
        varCat.setAttribute( "name", "Variables" );
        Element block = dox.createElement( "block" );
        block.setAttribute( "type", IriUtil.opsIRI( "VariableExpression" ).toString() );
        varCat.appendChild( block );
        dox.getDocumentElement().appendChild( varCat );


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

    private static int getArity( OWLClassExpression expression, Set<OWLClassExpression> supers, OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, PrefixManager pManager ) {
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "NAryExpression" ) ) ) ) {
            return -1;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "TernaryExpression" ) ) ) ) {
            return 3;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "BinaryExpression" ) ) ) ) {
            return 2;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "UnaryExpression" ) ) ) ) {
            return 1;
        }
        return 0;
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
