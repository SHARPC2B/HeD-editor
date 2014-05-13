package edu.asu.sharpc2b.transform;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
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



    private static Map<OWLClassExpression,Set<String>> palette = new LinkedHashMap<OWLClassExpression, Set<String>>();

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
        Set<String> primitiveTypes = new HashSet<String>();


        Set<OWLSubClassOfAxiom> allSubs = operatorOntology.getAxioms( AxiomType.SUBCLASS_OF, true );
        for ( OWLSubClassOfAxiom sub : allSubs ) {
            try {

                if ( ! sub.getSuperClass().isAnonymous() &&
                     ( sub.getSuperClass().asOWLClass().equals( factory.getOWLClass( IriUtil.opsIRI( "OperatorExpression" ) ) )
                       || sub.getSuperClass().asOWLClass().equals( factory.getOWLClass( IriUtil.opsIRI( "PrimitiveExpression" ) ) )
                     )
                ) {
                    visitExpressionDefinition( sub.getSubClass(), operatorOntology, manager, factory, builder );
                    List<String> types = gatherPrimitiveTypes( sub.getSubClass(), operatorOntology );
                    primitiveTypes.addAll( types );
                }

                // if ( ! sub.getSuperClass().isAnonymous() && sub.getSuperClass().asOWLClass().equals( factory.getOWLClass( IriUtil.opsIRI( "PropertyExpression" ) ) ) ) {
                //    visitExpressionDefinition( sub.getSubClass(), operatorOntology, manager, factory, builder );
                //}
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }


        OWLClass variableExpr = factory.getOWLClass( IriUtil.opsIRI( "VariableExpression" ) );
        getPaletteGroup( variableExpr ).add( variableExpr.getIRI().toString() );

        OWLClass domainConceptExpr = factory.getOWLClass( IriUtil.opsIRI( "DomainClassExpression" ) );
        getPaletteGroup( domainConceptExpr ).add( domainConceptExpr.getIRI().toString() );
        OWLClass domainPropertyExpr = factory.getOWLClass( IriUtil.opsIRI( "DomainPropertyExpression" ) );
        getPaletteGroup( domainPropertyExpr ).add( domainPropertyExpr.getIRI().toString() );

        OWLClass propertyExpr = factory.getOWLClass( IriUtil.opsIRI( "PropertyExpression" ) );
        OWLClass setter = factory.getOWLClass( IriUtil.opsIRI( "PropertySetExpression" ) );
        OWLClass getter = factory.getOWLClass( IRI.create( mainId.getOntologyIRI().toString() + "#", "PropertyExpression" ) );
        getPaletteGroup( propertyExpr ).add( setter.getIRI().toString() );
        getPaletteGroup( propertyExpr ).add( getter.getIRI().toString() );





        String common = (String) TemplateRuntime.execute( registry.getNamedTemplate( "blockyCommon" ), null, Collections.emptyMap(), registry );
        builder.append( common );
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
            createPalette( paletteOputput, primitiveTypes, operatorOntology, factory );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    private List<String> gatherPrimitiveTypes( OWLClassExpression primitive, OWLOntology operatorOntology ) {
        Set<OWLClassExpression> subs = superClosure( primitive, operatorOntology );

        IRI id = primitive.asOWLClass().getIRI();

        List<String> paramTypes = new ArrayList<String>(  );

        for ( OWLClassExpression expr : subs ) {
            if ( expr instanceof OWLObjectIntersectionOf ) {
                OWLObjectIntersectionOf allValuesFrom = (OWLObjectIntersectionOf) expr;
                for ( OWLClassExpression op : allValuesFrom.asConjunctSet() ) {
                    if ( op instanceof OWLDataSomeValuesFrom ) {
                        OWLDataSomeValuesFrom some = (OWLDataSomeValuesFrom) op;
                        IRI filler = some.getFiller().asOWLDatatype().getIRI();
                        paramTypes.add( filler.toString().replace( "http://www.w3.org/2001/XMLSchema#", "xsd:" ) );
                    }
                }
            } else if ( expr instanceof OWLDataSomeValuesFrom ) {
                OWLDataSomeValuesFrom some = (OWLDataSomeValuesFrom) expr;
                IRI filler = some.getFiller().asOWLDatatype().getIRI();
                paramTypes.add( filler.toString().replace( "http://www.w3.org/2001/XMLSchema#", "xsd:" ) );
            } else if ( expr instanceof OWLDataAllValuesFrom ) {
                OWLDataAllValuesFrom all = (OWLDataAllValuesFrom) expr;
                IRI filler = all.getFiller().asOWLDatatype().getIRI();
                paramTypes.add( filler.toString().replace( "http://www.w3.org/2001/XMLSchema#", "xsd:" ) );
            }
        }

        return paramTypes;
    }




    public static class InputArgument {
        public String id;
        public String name;
        public List<String> allowedTypes;
        public List<String> allowedTypeNames;
        public boolean multiple = false;

        public String toString() {
           return "Input " + name
                  // + " (" + id + ")"
                  + ( multiple ? "* " : " " )
                  // + allowedTypes
                  + " >> " + allowedTypeNames;
        }
    }

    private void visitExpressionDefinition( OWLClassExpression expression, OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, StringBuilder builder ) throws ParserConfigurationException, TransformerException {
        if ( isHidden( expression.asOWLClass().getIRI(), ontology ) ) {
            return;
        }

        PrefixManager pManager = IriUtil.getDefaultSharpOntologyFormat();
        String baseOntologyIri = ontology.getOntologyID().getOntologyIRI().toString() + "#";

        Set<OWLClassExpression> supers = superClosure( expression, ontology ) ;

        Arity arity = getArity( expression, supers, ontology, manager, factory, pManager );
        List<InputArgument> arguments = new ArrayList<InputArgument>();
        Set<OWLClassExpression> returnTypes = new HashSet<>();


        for ( OWLClassExpression expr : supers ) {
            if ( expr instanceof OWLObjectSomeValuesFrom && ( (OWLObjectSomeValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "returns" ) ) ) {
                returnTypes.add( ( (OWLObjectSomeValuesFrom) expr ).getFiller() );
            }
        }

        String id = expression.asOWLClass().getIRI().toString();
        String code = "BUG";


        for ( OWLClassExpression expr : supers ) {
            if ( expr instanceof OWLObjectAllValuesFrom ) {
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "firstOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupAdmissibleTypes( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    InputArgument arg = createInputArg( fil, ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI(), "firstOperand" );
                    arguments.add( arg );
                } else
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "secondOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupAdmissibleTypes( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    InputArgument arg = createInputArg( fil, ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI(), "secondOperand" );
                    arguments.add( arg );
                } else
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "thirdOperand" ) ) ) {
                    Set<OWLClassExpression> fil = lookupAdmissibleTypes( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    InputArgument arg = createInputArg( fil, ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI(), "thirdOperand" );
                    arguments.add( arg );
                } else
                if (
                        ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "hasOperand" ) )
                        || ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "extraOperand" ) )
                        ) {
                    Set<OWLClassExpression> fil = lookupAdmissibleTypes( ( (OWLObjectAllValuesFrom) expr ).getFiller(), ontology );
                    OWLObjectProperty prop = ((OWLObjectAllValuesFrom) expr).getProperty().asOWLObjectProperty();
                    InputArgument arg = createInputArg( fil, prop.getIRI(), prop.getIRI().getFragment() );
                    arg.multiple = true;
                    arguments.add( arg );
                } else
                if ( ( (OWLObjectAllValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "opCode" ) ) ) {
                    OWLObjectIntersectionOf codeConcept = (OWLObjectIntersectionOf) ( (OWLObjectAllValuesFrom) expr ).getFiller();
                    for ( OWLClassExpression inner : codeConcept.getOperands() ) {
                        if ( inner instanceof OWLDataHasValue && ( (OWLDataHasValue) inner ).getProperty().asOWLDataProperty().getIRI().equals( IriUtil.skosIRI( "notation" ) ) ) {
                            code = ( (OWLDataHasValue) inner ).getValue().getLiteral();
                        }
                    }
                } else
                {
                    OWLObjectAllValuesFrom all = (OWLObjectAllValuesFrom) expr;
                    if ( all.getProperty() instanceof OWLObjectProperty ) {
                        IRI propIri = all.getProperty().asOWLObjectProperty().getIRI();
                        if ( propIri.getNamespace().equals( baseOntologyIri ) ) {
                            Set<OWLClassExpression> fil = lookupAdmissibleTypes( all.getFiller(), ontology );
                            InputArgument arg = createInputArg( fil, propIri, propIri.getFragment() );
                            arg.multiple = ! checkCardinalityRestriction( all, supers );
                            arguments.add( arg );
                        }
                    }
                }
            } else if ( expr instanceof OWLDataAllValuesFrom ) {
                OWLDataAllValuesFrom all = (OWLDataAllValuesFrom) expr;
                if ( all.getProperty() instanceof OWLDataProperty ) {
                    IRI propIri = all.getProperty().asOWLDataProperty().getIRI();
                    if ( propIri.getNamespace().equals( baseOntologyIri ) ) {
                        OWLDataRange range = all.getFiller();
                        InputArgument arg = new InputArgument();
                        arg.allowedTypes = Arrays.asList( range.asOWLDatatype().getIRI().toString() );
                        arg.allowedTypeNames = Arrays.asList( range.asOWLDatatype().getIRI().getFragment() );
                        arg.multiple = false;
                        arg.name = propIri.getFragment();
                        arg.id = propIri.toString();
                        arguments.add( 0, arg );
                    }
                }

            }
        }

        /*
        if ( arguments.isEmpty() ) {
            System.out.println( "WARNING DOUBLE CHECK BLOCK WITH NO ARGS " + id );
        }
        System.out.println( "Found block " + id + " >>> " + returnTypes );
        for ( InputArgument arg : arguments ) {
            System.out.println( "\t" + arg );
        }
        */


        Map<String,Object> context = new HashMap<String, Object>( 7 );
        context.put( "iri", id );

        context.put( "returnType", new ArrayList<OWLClassExpression>( returnTypes ) );
        context.put( "returnTypeNames", new ArrayList<String>( extractTypeNames( returnTypes ) ) );

        context.put( "arguments", arguments );

        context.put( "color", pick() );
        context.put( "name", code );
        context.put( "tooltip", "HeD expression : " + code );
        context.put( "multipleArg", null );

        for ( InputArgument arg : arguments ) {
            if ( arg.multiple ) {
                // assumption: only 1 multiple arg
                context.put( "multipleArg", arg );
            }
        }

        String js = (String) TemplateRuntime.execute( registry.getNamedTemplate( "blocky" ),
                                                      null,
                                                      context,
                                                      registry );


        builder.append( js );


        for ( OWLClassExpression superType : supers ) {
            if ( ! superType.isAnonymous() ) {
                getPaletteGroup( superType ).add( id );
            }
        }

    }

    private boolean checkCardinalityRestriction( OWLObjectAllValuesFrom expr, Set<OWLClassExpression> supers ) {
        IRI propIri = expr.getProperty().asOWLObjectProperty().getIRI();
        for ( OWLClassExpression sup : supers ) {
            if ( sup instanceof OWLObjectMaxCardinality
                 && ( (OWLObjectMaxCardinality) sup ).getProperty().asOWLObjectProperty().getIRI().equals( propIri )
                 && ( (OWLObjectMaxCardinality) sup ).getCardinality() == 1 ) {
                return true;
            }
        }
        return false;
    }

    private InputArgument createInputArg( Set<OWLClassExpression> fil, IRI propIri, String name ) {
        InputArgument arg = new InputArgument();
        arg.allowedTypes = extractTypes( fil );
        arg.allowedTypeNames = extractTypeNames( fil );
        arg.multiple = false;
        arg.name = name;
        arg.id = propIri.toString();
        return arg;
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

    private Set<OWLClassExpression> lookupAdmissibleTypes( OWLClassExpression expression, OWLOntology ontology ) {
        Set<OWLClassExpression> subs = subClosure( expression, ontology ) ;

        Set<OWLClassExpression> returnTypes = new LinkedHashSet<>();
        for ( OWLClassExpression expr : subs ) {
            if ( expr instanceof OWLObjectSomeValuesFrom && ( (OWLObjectSomeValuesFrom) expr ).getProperty().asOWLObjectProperty().getIRI().equals( IriUtil.opsIRI( "returns" ) ) ) {
                returnTypes.add( ( (OWLObjectSomeValuesFrom) expr ).getFiller() );
            }
        }
        /*
        if ( returnTypes.isEmpty() ) {
            String tgt = expression.asOWLClass().getIRI().getFragment();
            if ( ! ( "AnyExpression".equals( tgt ) || "OperatorExpression".equals( tgt ) ) ) {
                System.out.println( "Looking up compatible types with " + expression.asOWLClass().getIRI().getFragment() );
                System.out.println( "  Searching in " + subs );
                System.out.println( " Found " + returnTypes );
            }
        }
        */
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
                if ( ! parent.isAnonymous() && parent != expression ) {
                    superClosure( parent, ontology, ancestors );
                }
            }
        }

    }

    private Set<OWLClassExpression> subClosure( OWLClassExpression expression, OWLOntology ontology ) {
        Set<OWLClassExpression> descendants = new TreeSet();
        descendants.add( expression );
        subClosure( expression, ontology, descendants );
        return descendants;
    }

    private void subClosure( OWLClassExpression expression, OWLOntology ontology, Set<OWLClassExpression> descendants ) {
        Set<OWLSubClassOfAxiom> subs = ontology.getAxioms( AxiomType.SUBCLASS_OF, true );
        for ( OWLSubClassOfAxiom sub : subs ) {
            if ( sub.getSuperClass().equals( expression ) ) {
                OWLClassExpression child = sub.getSubClass();
                descendants.add( child );
                if ( ! child.isAnonymous() ) {
                    subClosure( child, ontology, descendants );
                }
            }
        }
        Set<OWLEquivalentClassesAxiom> equivs = ontology.getAxioms( AxiomType.EQUIVALENT_CLASSES, true );
        for ( OWLEquivalentClassesAxiom eq : equivs ) {
            List<OWLClassExpression> eqClasses = eq.getClassExpressionsAsList();
            if ( eqClasses.contains( expression ) ) {
                OWLClassExpression child = eqClasses.get( 1 );
                descendants.add( child );
                if ( ! child.isAnonymous() ) {
                    superClosure( child, ontology, descendants );
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


    private void createPalette( File operatorsOutput, Set<String> primitiveTypes, OWLOntology ontology, OWLDataFactory factory ) throws ParserConfigurationException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dox = builder.newDocument();

        Element root = dox.createElement( "xml" );
        root.setAttribute( "id", "toolbox" );
        root.setAttribute( "style", "display: none" );
        dox.appendChild( root );

        OWLClassExpression expressionRoot = factory.getOWLClass( IriUtil.opsIRI( "SharpExpression" ) );
        OWLOntology exprCore = ontology.getOWLOntologyManager().getOntology( IRI.create( IriUtil.opsCoreBaseIRI.replace( "#", "" ) ) );

        Map<OWLClassExpression, Element> paletteTree = new HashMap<OWLClassExpression, Element>();
        visitExpressionTypes( expressionRoot, root, exprCore, dox, factory, paletteTree );


        Element cat = dox.createElement( "category" );
        cat.setAttribute( "name", "Datatypes" );
        for ( String type : primitiveTypes ) {
            Element block = dox.createElement( "block" );
            block.setAttribute( "type", type );
            cat.appendChild( block );
        }
        Element block = dox.createElement( "block" );
        block.setAttribute( "type", "xsd:text" );
        cat.appendChild( block );
        dox.getDocumentElement().appendChild( cat );

        //prettyPrint( dox.getDocumentElement(), System.out );
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

    private void visitExpressionTypes( OWLClassExpression expressionRoot, Element root, OWLOntology ontology, Document dox, OWLDataFactory factory, Map<OWLClassExpression, Element> paletteTree ) {
        paletteTree.put( expressionRoot, root );
        Set<OWLSubClassOfAxiom> children = ontology.getSubClassAxiomsForSuperClass( expressionRoot.asOWLClass() );
        Map<String,Element> elementMap = new HashMap<String,Element>();

        for ( OWLSubClassOfAxiom sub : children ) {
            OWLClassExpression child = sub.getSubClass();
            if ( ! child.isAnonymous() && palette.containsKey( child ) && ! isHidden( child.asOWLClass().getIRI(), ontology ) ) {
                String name = child.asOWLClass().getIRI().getFragment().replace( "Expression", "" );
                Element cat = dox.createElement( "category" );
                cat.setAttribute( "name", name );
                elementMap.put( name, cat );

                //TODO Hack
                if ( "DomainClass".equals( name ) ) {
                    Element block = dox.createElement( "block" );
                    block.setAttribute( "ng-repeat", "block in domainClasses track by $index" );
                    block.setAttribute( "type", "{{block}}" );
                    block.setAttribute( "update-toolbox","" );
                    cat.appendChild( block );
                    continue;
                } else if ( "DomainProperty".equals( name ) ) {
                    Element allPropBlock = dox.createElement( "block" );
                    allPropBlock.setAttribute( "type", "http://asu.edu/sharpc2b/ops#DomainProperty" );
                    cat.appendChild( allPropBlock );

                    Element block = dox.createElement( "block" );
                    block.setAttribute( "ng-repeat", "block in domainProperties track by $index" );
                    block.setAttribute( "type", "{{block}}/properties" );
                    block.setAttribute( "update-toolbox","" );
                    cat.appendChild( block );
                    continue;
                }

                Map<String,Element> leaves = new HashMap<String,Element>();
                for ( String id : palette.get( child ) ) {
                    // check that the operator is not subsumed

                    Set<OWLSubClassOfAxiom> grandChildren = ontology.getSubClassAxiomsForSuperClass( child.asOWLClass() );
                    boolean subsumed = false;
                    for ( OWLSubClassOfAxiom gc : grandChildren ) {
                        if ( palette.containsKey( gc.getSubClass() ) && palette.get( gc.getSubClass() ).contains( id ) ) {
                            subsumed = true;
                            break;
                        }
                    }
                    if ( ! subsumed ) {
                        IRI iri = IRI.create( id );
                        if ( ! isHidden( iri, ontology ) ) {
                            Element block = dox.createElement( "block" );
                            block.setAttribute( "type", id );
                            leaves.put( iri.getFragment(), block );
                        }
                    }
                }
                addInOrder( leaves, cat );

                visitExpressionTypes( child, cat, ontology, dox, factory, paletteTree );
            } else {
                //System.err.println( "Skipping  " + child );
            }
        }

        addInOrder( elementMap, root );
    }

    private void addInOrder( Map<String, Element> elementMap, Element root ) {
        List<String> keys = new ArrayList( elementMap.keySet() );
        Collections.sort( keys );
        for ( String key : keys ) {
            root.appendChild( elementMap.get( key ) );
        }
    }

    private boolean isHidden( IRI element, OWLOntology onto ) {
        String source = element.getNamespace();
        if ( source.endsWith( "#" ) ) {
            source = source.substring( 0, source.length() - 1 );
        }
        IRI nativeIri = IRI.create( source );
        Set<OWLAnnotationAssertionAxiom> annots = onto.getOWLOntologyManager().getOntology( nativeIri ).getAnnotationAssertionAxioms( element );
        for ( OWLAnnotationAssertionAxiom annot : annots ) {
            if ( annot.getProperty().getIRI().getFragment().equals( "hidden" ) ) {
                return true;
            }
        }
        return false;
    }


    public static final void prettyPrint( Node xml, OutputStream out ) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
        tf.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        tf.setOutputProperty( OutputKeys.INDENT, "yes" );
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tf.transform( new DOMSource( xml ), new StreamResult(out));
    }

    private static Arity  getArity( OWLClassExpression expression, Set<OWLClassExpression> supers, OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, PrefixManager pManager ) {
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "NullaryExpression" ) ) ) ) {
            return Arity.NULLARY;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "NAryExpression" ) ) ) ) {
            return Arity.NARY;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "TernaryExpression" ) ) ) ) {
            return Arity.TERNARY;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "BinaryExpression" ) ) ) ) {
            return Arity.BINARY;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "UnaryExpression" ) ) ) ) {
            return Arity.UNARY;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "PrimitiveExpression" ) ) ) ) {
            return Arity.LITERAL;
        }
        if ( supers.contains( factory.getOWLClass( IriUtil.opsIRI( "MiscExpression" ) ) ) ) {
            return Arity.MIXED;
        }

        // throw new UnsupportedOperationException( "Unable to determine arity for class " + expression );
        return Arity.UNKNOWN;
    }

    private static Integer pick() {
        return 42;
    }


    private static Set<String> getPaletteGroup( OWLClassExpression grp ) {
        if ( ! palette.containsKey( grp ) ) {
            palette.put( grp, new HashSet<String>() );
        }
        return palette.get( grp );
    }

}
