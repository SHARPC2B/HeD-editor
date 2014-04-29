package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.empire.annotation.RdfProperty;
import edu.asu.sharpc2b.actions.AtomicAction;
import edu.asu.sharpc2b.actions.CompositeAction;
import edu.asu.sharpc2b.actions.CreateAction;
import edu.asu.sharpc2b.actions.SharpAction;
import edu.asu.sharpc2b.ops.DomainClassExpression;
import edu.asu.sharpc2b.ops.DomainPropertyExpression;
import edu.asu.sharpc2b.ops.OperatorExpression;
import edu.asu.sharpc2b.ops.PropertySetExpression;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops.VariableExpression;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.NamedElement;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class ReferenceHunter {

    private SharpExpression expr;
    private SharpAction action;
    private boolean found = false;

    public ReferenceHunter( SharpExpression expression ) {
        this.expr = expression;
    }

    public ReferenceHunter( SharpAction action ) {
        this.action = action;
    }

    public boolean replace( String oldName, String newName ) {
        if ( expr != null ) {
            visit( expr, oldName, newName );
        } else {
            visit( action, oldName, newName );
        }
        return found;
    }

    private void visit( SharpAction action, String oldName, String newName ) {
        if ( ! action.getLocalCondition().isEmpty() ) {
            Expression cnd = action.getLocalCondition().get( 0 ).getConditionRepresentation().get( 0 );
            if ( ! cnd.getBodyExpression().isEmpty() ) {
                visit( cnd.getBodyExpression().get( 0 ), oldName, newName );
            }
        }
        if ( action instanceof CompositeAction ) {
            for ( SharpAction sub : ( (CompositeAction) action ).getMemberAction() ) {
                visit( sub, oldName, newName );
            }
        } else {
            if ( ! action.getActionExpression().isEmpty() ) {
                SharpExpression body = action.getActionExpression().get( 0 ).getBodyExpression().get( 0 );
                visit( body, oldName, newName );
            }
        }
    }


    public void visit( SharpExpression expr, String oldName, String newName ) {
        if ( expr instanceof VariableExpression ) {
            List vars = ((VariableExpression) expr ).getReferredVariable();
            if ( vars.isEmpty() ) {
                return;
            }
            NamedElement var = (NamedElement) vars.get( 0 );
            String varName = var.getName().get( 0 );
            if ( oldName.equals( varName )  ) {
                found = true;
                var.getName().clear();
                var.addName( newName );
                return;
            }
        } else if ( expr instanceof DomainClassExpression ) {
            return;
        } else if ( expr instanceof DomainPropertyExpression ) {
            return;
        } else if ( expr instanceof PropertySetExpression ) {
            PropertySetExpression setter = (PropertySetExpression) expr;
            visit( setter.getValue().get( 0 ), oldName, newName );
            visit( setter.getProperty().get( 0 ), oldName, newName );
            return;
        }

        if ( expr instanceof OperatorExpression ) {
            try {
                visitProperties( expr, oldName, newName );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }



    private void visitProperties( SharpExpression expr, String oldName, String newName ) {
        try {
            for ( Method m : expr.getClass().getMethods() ) {
                if ( m.getAnnotation( RdfProperty.class ) != null ) {
                    String propIri = m.getAnnotation( RdfProperty.class ).value();
                    if ( "http://asu.edu/sharpc2b/ops#opCode".equals( propIri ) ) {
                        continue;
                    }

                    if ( Collection.class.isAssignableFrom( m.getReturnType() ) ) {
                        Collection coll = (Collection) m.invoke( expr );
                        int j = 0;
                        for ( Object o : coll ) {
                            if ( o instanceof SharpExpression ) {
                                visit( (SharpExpression) o, oldName, newName );
                            }
                        }
                    } else {
                        Object o = m.invoke( expr );
                        if ( o instanceof SharpExpression ) {
                            visit( (SharpExpression) o, oldName, newName );
                        }
                    }
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }


}
