package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.empire.annotation.RdfProperty;
import edu.asu.sharpc2b.actions.AtomicAction;
import edu.asu.sharpc2b.actions.AtomicActionImpl;
import edu.asu.sharpc2b.actions.CancelActionImpl;
import edu.asu.sharpc2b.actions.CollectInformationActionImpl;
import edu.asu.sharpc2b.actions.CompositeAction;
import edu.asu.sharpc2b.actions.CompositeActionImpl;
import edu.asu.sharpc2b.actions.CreateActionImpl;
import edu.asu.sharpc2b.actions.FireEventActionImpl;
import edu.asu.sharpc2b.actions.ModifyAction;
import edu.asu.sharpc2b.actions.ModifyActionImpl;
import edu.asu.sharpc2b.actions.SharpAction;
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
import edu.asu.sharpc2b.ops_set.OrExpression;
import edu.asu.sharpc2b.ops_set.OrExpressionImpl;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.ExpressionImpl;
import edu.asu.sharpc2b.prr.RuleCondition;
import edu.asu.sharpc2b.prr.RuleConditionImpl;
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

public class ExpressionFactory<T> {

    private static final String exprNS = "http://asu.edu/sharpc2b/ops-set#";


    public T parseBlockly( byte[] data, BlocklyFactory.ExpressionRootType type ) {
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

    public T parseBlockly( Document dox, BlocklyFactory.ExpressionRootType type ) {
        switch ( type ) {
            case ACTION :
                return (T) parseAction( dox );
            case TRIGGER :
                return (T) parseTrigger( dox );
            case CONDITION:
                return (T) parseCondition( dox );
            case EXPRESSION:
            default :
                return (T) parseGenericExpression( dox );
        }
    }

    private SharpAction parseAction( Document dox ) {
        CompositeAction parent = new CompositeActionImpl();

        Element xml = dox.getDocumentElement();
        Element root = getChildrenByTagName( xml, "block" ).iterator().next();

        List<Element> statements = getChildrenByTagName( root, "statement" );

        if ( statements != null && ! statements.isEmpty() ) {
            Element stat = statements.iterator().next();
            visitAction( stat, parent );
        }

        return parent;
    }


    private void visitAction( Element root, CompositeAction actionGroup ) {
        List<Element> blocks = getChildrenByTagName( root, "block" );
        if ( blocks.isEmpty() ) {
            return;
        }

        Element block = blocks.get( 0 );
        SharpAction action = null;
        String type = block.getAttribute( "type" );
        String title = "";
        String mode = null;

        List<Element> meta = getChildrenByTagName( block, "field" );
        for ( Element elem : meta ) {
            String name = elem.getAttribute( "name" );
            if ( "TITLE".equals( name ) ) {
                title = elem.getTextContent();
            } else if ( "NAME".equals( name ) ) {
                mode = elem.getTextContent();
            }
        }

        if ( "action_group".equals( type ) ) {
            action = new CompositeActionImpl();
        } else {
            if ( "CreateAction".equals( mode ) ) {
                action = new CreateActionImpl();
            } else if ( "RemoveAction".equals( mode ) ) {
                action = new CancelActionImpl();
            } else if ( "UpdateAction".equals( mode ) ) {
                action = new ModifyActionImpl();
            } else if ( "RemoveAction".equals( mode ) ) {
                action = new CancelActionImpl();
            } else if ( "Fire Event".equals( mode ) )  {
                action = new FireEventActionImpl();
            } else if ( "Declare".equals( mode ) ) {
                // TODO ! Fix hierarchy
                action = new AtomicActionImpl();
            } else if ( mode.startsWith( "Collect" ) ) {
                action = new CollectInformationActionImpl();
            }
        }

        action.addTitle( title );


        List<Element> values = getChildrenByTagName( block, "value" );
        for ( Element val : values ) {
            if ( "Condition".equals( val.getAttribute( "name" ) ) ) {
                Element sub = getChildrenByTagName( val, "block" ).get( 0 );
                SharpExpression expr = visitActionConditionBlock( sub );

                RuleCondition condition = new RuleConditionImpl();
                Expression prrExpr = new ExpressionImpl();
                prrExpr.addBodyExpression( expr );
                condition.addConditionRepresentation( prrExpr );
                action.addLocalCondition( condition );

            }
        }

        if ( "action_group".equals( type ) ) {

            List<Element> fields = getChildrenByTagName( block, "field" );
            for ( Element e : fields ) {
                if ( e.hasAttribute( "name" ) && "NAME".equals( e.getAttribute( "name" ) ) ) {
                    ((CompositeAction) action).addGroupSelection( e.getTextContent() );
                }
            }

            List<Element> stats = getChildrenByTagName( block, "statement" );
            if ( ! stats.isEmpty() ) {
                Element statement = stats.get( 0 );
                visitAction( statement, (CompositeAction) action );
            }
        } else if ( "atomic_action".equals( type ) ) {
            for ( Element val : values ) {
                if ( "ActionSentence".equals( val.getAttribute( "name" ) ) ) {
                    Element sub = getChildrenByTagName( val, "block" ).get( 0 );
                    Element field = getChildrenByTagName( sub, "field" ).get( 0 );
                    String varName = field.getTextContent();
                    String id = HeDArtifactData.idFromName( varName );

                    VariableExpression varexp = new VariableExpressionImpl();
                    Variable var = new VariableImpl();
                    var.addName( varName );
                    varexp.addReferredVariable( var );

                    Expression prrExpr = new ExpressionImpl();
                    prrExpr.addBodyExpression( varexp );
                    (( AtomicAction) action).addActionExpression( prrExpr );
                }
            }

        }

        actionGroup.addMemberAction( action );

        List<Element> nexts = getChildrenByTagName( block, "next" );
        if ( ! nexts.isEmpty() ) {
            Element next = nexts.get( 0 );
            visitAction( next, actionGroup );
        }
    }

    private SharpExpression visitActionConditionBlock( Element sub ) {
        String conditionType = sub.getAttribute( "type" );
        if ( "action_condition".equals( conditionType ) ) {
            Element field = getChildrenByTagName( sub, "field" ).get( 0 );
            String varName = field.getTextContent();
            String id = HeDArtifactData.idFromName( varName );

            VariableExpression varexp = new VariableExpressionImpl();
            Variable var = new VariableImpl();
            var.addName( varName );
            varexp.addReferredVariable( var );

            return varexp;
        } else if ( "action_logic_negate".equals( conditionType ) ) {
            Element value = getChildrenByTagName( sub, "value" ).get( 0 );
            List<Element> inner = getChildrenByTagName( value, "block" );
            if ( ! inner.isEmpty() ) {
                Element nested = inner.get( 0 );
                NotExpression not = new NotExpressionImpl();

                not.addFirstOperand( visitActionConditionBlock( nested ) );
                return not;
            }
            return new NotExpressionImpl();
        } else if ( "action_logic_compare".equals( conditionType ) ) {
            SharpExpression sharp = new AndExpressionImpl();
            SharpExpression root = null;
            List<Element> fields = getChildrenByTagName( sub, "field" );
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
            List<Element> arity = getChildrenByTagName( sub, "mutation" );
            if ( ! arity.isEmpty() ) {
                numargs = Integer.parseInt( arity.iterator().next().getAttribute( "types" ) );
            }

            List<Element> values = getChildrenByTagName( sub, "value" );
            SharpExpression[] args = new SharpExpression[ numargs ];
            for ( Element value : values ) {
                String clauseName = value.getAttribute( "name" );
                int index = Integer.parseInt( clauseName.replace( "CLAUSE", "" ) );

                List<Element> elements = getChildrenByTagName( value, "block" );
                if ( ! elements.isEmpty() ) {
                    Element child = elements.iterator().next();
                    args[ index ] = visitActionConditionBlock( child );
                }
            }
            for ( SharpExpression expr : args ) {
                (( NAryExpression ) sharp ).addHasOperand( expr );
            }
            return root;
        } else {
            throw new UnsupportedOperationException( "Unrecognized block type in action logic " + conditionType );
        }
    }


    private SharpExpression parseTrigger( Document dox ) {
        OrExpression or = new OrExpressionImpl();

        Element xml = dox.getDocumentElement();
        Element root = getChildrenByTagName( xml, "block" ).iterator().next();
        List<Element> statements = getChildrenByTagName( root, "statement" );

        if ( ! statements.isEmpty() ) {
            Element stat = statements.iterator().next();
            visitTrigger( stat, or );
        }

        return or;
    }

    private void visitTrigger( Element expr, OrExpression or ) {
        List<Element> blocks = getChildrenByTagName( expr, "block" );
        if ( blocks.isEmpty() ) {
            return;
        }

        Element block = blocks.get( 0 );
        String type = block.getAttribute( "type" );

        if ( "http://asu.edu/sharpc2b/ops-set#TemporalTrigger".equals( type ) ) {
            List<Element> values = getChildrenByTagName( block, "value" );
            if ( ! values.isEmpty() ) {
                Element value = values.get( 0 );
                List<Element> elems = getChildrenByTagName( value, "block" );
                if ( ! elems.isEmpty() ) {
                    Element period = elems.get( 0 );
                    or.addHasOperand( visitBlock( period ) );
                }
            }
        } else if ( "http://asu.edu/sharpc2b/ops-set#TypedTrigger".equals( type ) ) {
            List<Element> values = getChildrenByTagName( block, "value" );
            if ( ! values.isEmpty() ) {
                Element value = values.get( 0 );
                List<Element> elems = getChildrenByTagName( value, "block" );
                if ( ! elems.isEmpty() ) {
                    Element elem = elems.get( 0 );
                    List<Element> fields = getChildrenByTagName( elem, "field" );
                    if ( ! fields.isEmpty() ) {
                        Element field = fields.get( 0 );
                        String varName = field.getTextContent();

                        VariableExpression varexp = new VariableExpressionImpl();
                        Variable var = new VariableImpl();
                        var.addName( varName );
                        varexp.addReferredVariable( var );

                        or.addHasOperand( varexp );
                    }
                }
            }
        }

        List<Element> nexts = getChildrenByTagName( block, "next" );
        if ( ! nexts.isEmpty() ) {
            Element next = nexts.get( 0 );
            visitTrigger( next, or );
        }
    }


    private SharpExpression parseCondition( Document dox ) {
        Element xml = dox.getDocumentElement();
        Element root = getChildrenByTagName( xml, "block" ).iterator().next();
        List<Element> values = getChildrenByTagName( root, "value" );
        if ( ! values.isEmpty() ) {
            Element value = values.iterator().next();
            Element expr = getChildrenByTagName( value, "block" ).iterator().next();
            return visitConditionBlock( expr );
        }
        return null;
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
               || predicate.startsWith( exprNS + "caseeItem_" )
               || predicate.startsWith( exprNS.replace( "ops-set", "ops" ) + "property_" );
    }

}
