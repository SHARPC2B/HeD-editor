package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.empire.annotation.RdfProperty;
import edu.asu.sharpc2b.ops.DomainClassExpression;
import edu.asu.sharpc2b.ops.DomainClassExpressionImpl;
import edu.asu.sharpc2b.ops.DomainPropertyExpression;
import edu.asu.sharpc2b.ops.DomainPropertyExpressionImpl;
import edu.asu.sharpc2b.ops.NAryExpression;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops.SharpExpressionImpl;
import edu.asu.sharpc2b.ops.Variable;
import edu.asu.sharpc2b.ops.VariableExpression;
import edu.asu.sharpc2b.ops.VariableExpressionImpl;
import edu.asu.sharpc2b.ops.VariableImpl;
import edu.asu.sharpc2b.ops_set.AndExpressionImpl;
import edu.asu.sharpc2b.ops_set.IsNotEmptyExpression;
import edu.asu.sharpc2b.ops_set.IsNotEmptyExpressionImpl;
import edu.asu.sharpc2b.ops_set.NotExpression;
import edu.asu.sharpc2b.ops_set.NotExpressionImpl;
import edu.asu.sharpc2b.ops_set.OrExpressionImpl;
import edu.asu.sharpc2b.skos_ext.ConceptCode;
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import org.drools.semantics.utils.NameUtils;
import org.semanticweb.owlapi.model.IRI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpressionFactory {

    private static final String exprNS = "http://asu.edu/sharpc2b/ops-set#";


    public SharpExpression parseBlockly( byte[] data, BlocklyFactory.ExpressionRootType type ) {
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document dox = builder.parse( new ByteArrayInputStream( data ) );
            return parseBlockly( dox, type );
        } catch ( ParserConfigurationException e ) {
            e.printStackTrace();
        } catch ( SAXException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public SharpExpression parseBlockly( Document dox, BlocklyFactory.ExpressionRootType type ) {
        switch ( type ) {
            case CONDITION:
                return (SharpExpression) parseCondition( dox );
            case EXPRESSION:
            default :
                return (SharpExpression) parseGenericExpression( dox );
        }
    }

    private SharpExpression parseCondition( Document dox ) {
        Element xml = dox.getDocumentElement();
        Element root = getChildrenByTagName( xml, "block" ).iterator().next();
        Element value = getChildrenByTagName( root, "value" ).iterator().next();
        Element expr = getChildrenByTagName( value, "block" ).iterator().next();

        return visitConditionBlock( expr );
    }

    private SharpExpression visitConditionBlock( Element block ) {
        String blockType = block.getAttribute( "type" );
        SharpExpression sharp = new AndExpressionImpl();
        SharpExpression root = null;
        if ( "logic_compare".equals( blockType ) ) {
            List<Element> fields = getChildrenByTagName( block, "field" );
            for ( Element e : fields ) {
                String name = e.getAttribute( "name" );
                if ( "dropdown".equals( name ) ) {
                    String mode = e.getTextContent();
                    if ( "ALL".equals( mode ) ) {
                        sharp = new AndExpressionImpl();
                        root = sharp;
                    } else if ( "ONE".equals( mode ) ) {
                        sharp = new OrExpressionImpl();
                        root = sharp;
                    } else if ( "NONE".equals( mode ) ) {
                        root = new NotExpressionImpl();
                        sharp = new OrExpressionImpl();
                        (( NotExpression) root).addFirstOperand( sharp );
                    } else if ( "ONEPLUS".equals( mode ) ) {
                        sharp = new OrExpressionImpl();
                        root = sharp;
                    } else {
                        throw new IllegalStateException( "Invalid logic type value " + name );
                    }
                }
            }

            int numargs = 0;
            List<Element> arity = getChildrenByTagName( block, "mutation" );
            if ( ! arity.isEmpty() ) {
                numargs = Integer.parseInt( arity.iterator().next().getAttribute( "types" ) );
            }

            List<Element> values = getChildrenByTagName( block, "value" );
            SharpExpression[] args = new SharpExpression[ numargs ];
            for ( Element value : values ) {
                String clauseName = value.getAttribute( "name" );
                int index = Integer.parseInt( clauseName.replace( "CLAUSE", "" ) );

                List<Element> elements = getChildrenByTagName( value, "block" );
                if ( ! elements.isEmpty() ) {
                    Element child = elements.iterator().next();
                    args[ index ] = visitConditionBlock( child );
                }
            }
            for ( SharpExpression expr : args ) {
                (( NAryExpression ) sharp ).addHasOperand( expr );
            }
        } else if ( "logic_negate".equals( blockType ) ) {
            List<Element> values = getChildrenByTagName( block, "value" );
            root = new NotExpressionImpl();
            for ( Element value : values ) {
                List<Element> elements = getChildrenByTagName( value, "block" );
                if ( ! elements.isEmpty() ) {
                    Element child = elements.iterator().next();
                    sharp = visitConditionBlock( child );
                }
            }
            ((NotExpression) root).addFirstOperand( sharp );
        } else if ( "logic_boolean".equals( blockType  ) || "logic_any".equals( blockType ) ) {
            List<Element> fields = getChildrenByTagName( block, "field" );
            if ( ! fields.isEmpty() ) {
                String varName = fields.iterator().next().getTextContent();
                VariableExpression varexp = new VariableExpressionImpl();
                Variable var = new VariableImpl();
                var.addName( varName );
                varexp.addReferredVariable( var );
                root = varexp;
            }
        } else if ( "logic_exists".equals( blockType ) ) {
            List<Element> values = getChildrenByTagName( block, "value" );
            root = new IsNotEmptyExpressionImpl();
            for ( Element value : values ) {
                List<Element> elements = getChildrenByTagName( value, "block" );
                if ( ! elements.isEmpty() ) {
                    Element child = elements.iterator().next();
                    sharp = visitConditionBlock( child );
                }
            }
            ((IsNotEmptyExpression) root).addFirstOperand( sharp );
        } else {
            throw new UnsupportedOperationException( "Unable to determine logic block type, not expected " + blockType );
        }
        return root;
    }

    private Object parseGenericExpression( Document dox ) {
        Element xml = dox.getDocumentElement();
        Element root = getChildrenByTagName( xml, "block" ).iterator().next();
        Element value = getChildrenByTagName( root, "value" ).iterator().next();
        Element expr = getChildrenByTagName( value, "block" ).iterator().next();

        return visitBlock( expr );
    }

    private Object visitBlock( Element element ) {
        String typeIri = element.getAttribute( "type" );
        Class<?> concreteType = determineConcreteClass( typeIri );
        SharpExpression expr = null;
        if ( concreteType != null ) {
            try {
                if ( ! SharpExpression.class.isAssignableFrom( concreteType ) ) {
                    // then it is a primitive datatype
                    List<Element> children = getChildrenByTagName( element, "field" );
                    if ( ! children.isEmpty() ) {
                        String literal = children.iterator().next().getTextContent();
                        if ( String.class.equals( concreteType ) ) {
                            return literal;
                        } else if ( Integer.class.equals( concreteType ) ) {
                            return Integer.valueOf( literal );
                        } else if ( Date.class.equals( concreteType ) ) {
                            return new SimpleDateFormat( ).parse( literal );
                        } else if ( Boolean.class.equals( concreteType ) ) {
                            return Boolean.valueOf( literal.toLowerCase() );
                        } else if ( URI.class.equals( concreteType ) ) {
                            return URI.create( literal );
                        } else if ( Double.class.equals( concreteType ) ) {
                            return Double.valueOf( literal );
                        } else {
                            throw new UnsupportedOperationException( "Unable to support primitive datatype" + concreteType );
                        }
                    }
                } else if ( DomainClassExpression.class.isAssignableFrom( concreteType ) ) {
                    DomainClassExpression dke = new DomainClassExpressionImpl();
                    ConceptCode code = new ConceptCodeImpl();
                    code.addCode( typeIri );
                    dke.addHasCode( code );
                    return dke;
                } else if ( DomainPropertyExpression.class.isAssignableFrom( concreteType ) ) {
                    return traversePropChain( element );
                } else if ( VariableExpression.class.isAssignableFrom( concreteType ) ) {
                    VariableExpression varexp = new VariableExpressionImpl();
                    Element varName = getChildrenByTagName( element, "field" ).iterator().next();
                    Variable var = new VariableImpl();
                    var.addName( varName.getTextContent() );
                    varexp.addReferredVariable( var );
                    expr = varexp;
                } else {
                    expr = (SharpExpression) concreteType.newInstance();
                    fillPropertyValues( expr, getChildrenByTagName( element, "value" ) );
                    fillPropertyValues( expr, getChildrenByTagName( element, "field" ) );
                }
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            } catch ( ParseException e ) {
                e.printStackTrace();
            }

            expr = postProcess( expr );
        }
        return expr;
    }

    private SharpExpression traversePropChain( Element element ) {
        List<Element> fields = getChildrenByTagName( element, "field" );
        DomainPropertyExpression dom  = new DomainPropertyExpressionImpl();
        for ( Element field : fields ) {
            if ( "DomainProperty".equals( field.getAttribute( "name" ) ) ) {
                ConceptCode code = new ConceptCodeImpl();
                code.addCode( field.getTextContent() );
                dom.addPropCode( code );
            }
        }
        List<Element> values = getChildrenByTagName( element, "value" );
        for ( Element value : values ) {
            List<Element> sources = getChildrenByTagName( value, "block" );
            for ( Element src : sources ) {
                DomainPropertyExpression dpe = (DomainPropertyExpression) visitBlock( src );
                dpe.addSource( dom );
                dom = dpe;
            }
        }
        return dom;
    }


    private void fillPropertyValues( SharpExpression expr, List<Element> values ) {
        for ( Element property : values ) {
            String propIri = property.getAttribute( "name" );
            if ( isMultiple( propIri ) ) {
                propIri = propIri.substring( 0, propIri.lastIndexOf( "_" ) );
            }

            List<Element> blocks = getChildrenByTagName( property, "block" );
            Object value = visitBlock( blocks.iterator().next() );
            if ( value == null ) {
                continue;
            }

            //System.out.println( "Trying to fit " + value + " into " + propIri );
            for ( Method m : expr.getClass().getMethods() ) {
                RdfProperty ann = m.getAnnotation( RdfProperty.class );
                if ( ann != null && propIri.equals( ann.value() ) ) {
                    try {
                        if ( List.class.isAssignableFrom( m.getReturnType() ) ) {
                            List list = (List) m.invoke( expr );
                            list.add( value );
                        } else {
                            // primitives
                            String getter = m.getName();
                            String add = getter.startsWith( "is" ) ? getter.replace( "is", "add" ) : getter.replace( "get", "add" );
                            Method adder = expr.getClass().getMethod( add,
                                                                      value.getClass() );
                            adder.invoke( expr, value );
                        }
                        break;
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private List<Element> getChildrenByTagName( Element element, String name ) {
        List<Element> elements = new ArrayList<Element>();
        NodeList children = element.getChildNodes();
        for ( int j = 0; j < children.getLength(); j++  ) {
            Node child = children.item( j );
            if ( child instanceof Element && name.equals( child.getNodeName() ) ) {
                elements.add( (Element) child );
            }
        }
        return elements;
    }

    private SharpExpression postProcess( SharpExpression expr ) {
        if ( expr == null ) {
            return new SharpExpressionImpl();
        }
        return expr;
    }

    private Class<?> determineConcreteClass( String typeIri ) {
        boolean isOpeExpr = typeIri.indexOf( "#" ) > 0;
        boolean isPrimitive = typeIri.startsWith( "xsd:" ) || typeIri.startsWith( "http://www.w3.org/2001/XMLSchema" );
        Class klass = null;
        try {
            if ( isPrimitive ) {
                String simpleName = IRI.create( typeIri ).getFragment();
                if ( "int".equals( simpleName ) ) {
                    return Integer.class;
                } else if ( "double".equals( simpleName ) ) {
                    return Double.class;
                } else if ( "string".equals( simpleName ) || "text".equals( simpleName ) ) {
                    return String.class;
                } else if ( "dateTime".equals( simpleName ) ) {
                    return Date.class;
                } else if ( "anyURI".equals( simpleName ) ) {
                    return URI.class;
                } else if ( "boolean".equals( simpleName ) ) {
                    return Boolean.class;
                }
            } else if ( typeIri.endsWith( "/properties" )  || "http://asu.edu/sharpc2b/ops#DomainProperty".equals( typeIri ) ) {
                klass = DomainPropertyExpressionImpl.class;
            } else if ( isOpeExpr ) {
                String name = typeIri.substring( typeIri.indexOf( "#" ) + 1 ) + "Impl";
                String packageName = NameUtils.nameSpaceURIToPackage( URI.create( typeIri.substring( 0, typeIri.indexOf( "#" ) ) ) );
                klass = Class.forName( packageName + "." + name );
            } else {
                // it is a simple name -> domain clas
                klass = DomainClassExpressionImpl.class;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        //System.out.println( "       Resolve as " + klass );
        return klass;
    }

    private boolean isMultiple( String predicate ) {
        //TODO
        return predicate.startsWith( exprNS + "property_" )
               || predicate.startsWith( exprNS + "element_" )
               || predicate.startsWith( exprNS.replace( "ops-set", "ops" ) + "property_" );
    }

}
