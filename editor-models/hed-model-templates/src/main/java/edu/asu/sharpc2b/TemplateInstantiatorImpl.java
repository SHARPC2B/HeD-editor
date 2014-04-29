package edu.asu.sharpc2b;

import edu.asu.sharpc2b.hed.impl.DomainHierarchyExplorer;
import edu.asu.sharpc2b.ops.BinaryExpression;
import edu.asu.sharpc2b.ops.BooleanExpression;
import edu.asu.sharpc2b.ops.DomainClassExpression;
import edu.asu.sharpc2b.ops.DomainClassExpressionImpl;
import edu.asu.sharpc2b.ops.DomainPropertyExpression;
import edu.asu.sharpc2b.ops.DomainPropertyExpressionImpl;
import edu.asu.sharpc2b.ops_set.DateAddExpression;
import edu.asu.sharpc2b.ops_set.DateAddExpressionImpl;
import edu.asu.sharpc2b.ops_set.IntervalExpression;
import edu.asu.sharpc2b.ops.ListVariableExpression;
import edu.asu.sharpc2b.ops.ListVariableExpressionImpl;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops.UnaryExpression;
import edu.asu.sharpc2b.ops.Variable;
import edu.asu.sharpc2b.ops.VariableExpression;
import edu.asu.sharpc2b.ops.VariableExpressionImpl;
import edu.asu.sharpc2b.ops.VariableImpl;
import edu.asu.sharpc2b.ops_set.AndExpression;
import edu.asu.sharpc2b.ops_set.AndExpressionImpl;
import edu.asu.sharpc2b.ops_set.BooleanLiteralExpression;
import edu.asu.sharpc2b.ops_set.BooleanLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpression;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpressionImpl;
import edu.asu.sharpc2b.ops_set.CodeLiteralExpression;
import edu.asu.sharpc2b.ops_set.CodeLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.EqualExpressionImpl;
import edu.asu.sharpc2b.ops_set.FilterExpression;
import edu.asu.sharpc2b.ops_set.FilterExpressionImpl;
import edu.asu.sharpc2b.ops_set.IntegerLiteralExpression;
import edu.asu.sharpc2b.ops_set.IntegerLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.IntervalExpressionImpl;
import edu.asu.sharpc2b.ops_set.IsNotEmptyExpression;
import edu.asu.sharpc2b.ops_set.IsNotEmptyExpressionImpl;
import edu.asu.sharpc2b.ops_set.ListExpression;
import edu.asu.sharpc2b.ops_set.ListExpressionImpl;
import edu.asu.sharpc2b.ops_set.LiteralExpression;
import edu.asu.sharpc2b.ops_set.LiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.NowExpressionImpl;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpression;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.PhysicalQuantityIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.PhysicalQuantityIntervalLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.PhysicalQuantityLiteralExpression;
import edu.asu.sharpc2b.ops_set.PhysicalQuantityLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.PropertyExpression;
import edu.asu.sharpc2b.ops_set.PropertyExpressionImpl;
import edu.asu.sharpc2b.ops_set.StringLiteralExpression;
import edu.asu.sharpc2b.ops_set.StringLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.TimestampIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.TimestampIntervalLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.TimestampLiteralExpression;
import edu.asu.sharpc2b.ops_set.TimestampLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.TodayExpressionImpl;
import edu.asu.sharpc2b.ops_set.ValueSetExpression;
import edu.asu.sharpc2b.ops_set.ValueSetExpressionImpl;
import edu.asu.sharpc2b.skos_ext.ConceptCode;
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import edu.asu.sharpc2b.templates.Parameter;
import edu.asu.sharpc2b.templates.Template;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TemplateInstantiatorImpl implements TemplateInstantiator {

    //TODO Refactor to pass as arguments...
    private static final String DOMAIN_MODEL_PATH = "ontologies/domain_models/domain-vmr.ofn";
    private static final String DOMAIN_NS = "urn:hl7-org:vmr:r2#";


    private DomainHierarchyExplorer explorer = DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS );

    private LinkedHashMap<String,SharpExpression> expressions = new LinkedHashMap<String,SharpExpression>();

    public TemplateInstantiatorImpl( ) {
    }

    public LinkedHashMap<String,SharpExpression> instantiateExpression( String name, Template source ) {
        String mainCat = source.getCategory().get( 0 );

        // TODO Need to move the strings / enums in BlocklyFactory to a more semantic representation, and put them in a shared module

        if ( "CONDITION".equals( mainCat ) ) {
            processAsCondition( name, source );
        } else if ( "TRIGGER".equals( mainCat ) ) {
            processAsTrigger( name, source );
        } else if ( "ACTION".equals( mainCat ) ) {
            String subCat = source.getCategory().get( 1 );
            if ( "CONDITION".equals( source ) ) {
                processAsCondition( name, source );
            } else if ( "ACTION".equals( source ) ) {
                processAsAction( name, source );
            }
        } else {
             throw new IllegalStateException( "Unrecognized main category while trying to instantiate template " + mainCat );
        }

        return expressions;
    }


    private void processAsCondition( String name, Template source ) {
        // on condition, create:
        // - a clinical request to bring in the data
        // - main expression has filters based on non-date, non-code attributes
        // - "has" X, existential version of the main expression

        String dataExprName = name + " - Facts";

        boolean needsFilter = hasNonRequestFilterCriteria( source );
        String klass = source.getRootClass().get( 0 );

        SharpExpression mainExpression;
        ListVariableExpression var = new ListVariableExpressionImpl();
        if ( needsFilter ) {
            FilterExpression filter = new FilterExpressionImpl();
            mainExpression = filter;

            AndExpression condition = new AndExpressionImpl();
            for ( Parameter param : source.getHasParameter() ) {
                if ( ! isRequestParam( param, klass ) ) {
                    SharpExpression expr = buildConstraint( source, param );
                    condition.addHasOperand( expr );
                }
        }

            filter.addSource_List( var );
            filter.addCondition( condition.getHasOperand().size() > 1 ? condition : condition.getHasOperand().get( 0 ) );
        } else {
            mainExpression = var;
        }


        IsNotEmptyExpression exists = new IsNotEmptyExpressionImpl();
        VariableExpression mainVar = new VariableExpressionImpl();
        Variable v2 = new VariableImpl();
        v2.addName( name );
        mainVar.addReferredVariable( v2 );
        exists.addFirstOperand( mainVar );
        expressions.put( name, exists );


        expressions.put( dataExprName, mainExpression );


        String requestName = createClinicalRequest( name, source, false );
        Variable v = new VariableImpl();
        v.addName( requestName );
        var.addReferredVariable( v );
    }

    private void processAsAction( String name, Template source ) {

    }


    private void processAsTrigger( String name, Template source ) {
        //TODO make "Timed" a semantically constrained type. There should be an explicit TimedTrigger subtype (add to the ontology)
        if ( source.getGroup().contains( "Timed" ) ) {
            if ( ! source.getHasParameter().isEmpty() ) {
                Parameter param = source.getHasParameter().get( 0 );
                createPeriodLiteralTrigger( name, param );
            }
        } else {
            // typed
            createClinicalRequest( name, source, true );
        }
    }

    private void createPeriodLiteralTrigger( String name, Parameter source ) {
        PeriodLiteralExpression period = new PeriodLiteralExpressionImpl();
        Map<String,String> elems = tokenize( source.getValue().get( 0 ) );

        String type = source.getTypeName().get( 0 );
        if ( "TimestampLiteral".equals( type ) ) {
            TimestampIntervalLiteralExpression intv = new TimestampIntervalLiteralExpressionImpl();
            Date date = parseDate( elems.get( "value" ) );
            intv.addLow_DateTime( date );
            intv.addHigh_DateTime( date );
            intv.addLowClosed( true );
            intv.addHighClosed( true );
            period.addPhase( intv );
            period.addCount( 1 );
            period.addIsFlexible( false );
        } else if ( "PeriodLiteral".equals( type ) ) {
            period = (PeriodLiteralExpression) buildLiteral( "PeriodLiteral", elems );
        } else {
            throw new UnsupportedOperationException( "Unable to parse timed trigger with type " + type );
        }

        expressions.put( name, period );
    }

    private Date parseDate( String value ) {
        try {
            return new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm" ).parse( value );
        } catch ( ParseException e ) {
            e.printStackTrace();
            return new Date();
        }
    }

    private String createClinicalRequest( String name, Template source, boolean asTrigger ) {
        if ( source.getRootClass().isEmpty() ) {
            System.err.println( "WARNING : Template with no root class, impossible to check request!" );
            return "";
        }
        String klass = source.getRootClass().get( 0 );
        ClinicalRequestExpression creq = new ClinicalRequestExpressionImpl();
        String exprName = name + " - Request";

        creq.addDataType( getDomainClassExpression( klass ) );

        creq.addCardinality( asTrigger ? "single" : "multiple" );
        creq.addTriggerType( "DataElementAdded" );
        creq.addIsInitial( true );

        String codeProperty = getCodeProperty( klass );
        List<CodeLiteralExpression> codeList = gatherCodesFromParams( source, codeProperty );
        if ( ! codeList.isEmpty() ) {
            creq.addCodeProperty( getDomainPropertyExpression( codeProperty ) );

            ListExpression list = new ListExpressionImpl();
            for ( CodeLiteralExpression code : codeList ) {
                list.addElement( code );
            }

            creq.addCodes( list );
        } else {
            ValueSetExpression valSet = gatherValueSetFromParams( source, codeProperty );
            if ( valSet != null ) {
                creq.addCodeProperty( getDomainPropertyExpression( codeProperty ) );
                creq.addCodes( valSet );
            }
        }

        String dateProperty = getDateProperty( klass );
        IntervalExpression dateRange = gatherDateRangeFromParams( source, dateProperty );
        creq.addDateRange( dateRange );

        this.expressions.put( exprName, creq );
        return exprName;
    }

    private IntervalExpression gatherDateRangeFromParams( Template source, String dateProperty ) {
        IntervalExpression intval = new IntervalExpressionImpl();
        for ( Parameter param : source.getHasParameter() ) {
            if ( param.getPath().contains( dateProperty ) && param.getTypeName().contains( "PeriodLiteral" ) ) {
                String value = param.getValue().get( 0 );
                Map<String,String> elements = tokenize( value );
                PeriodLiteralExpression pl = (PeriodLiteralExpression) buildLiteral( "PeriodLiteral", elements );
                if ( ! pl.getPhase().isEmpty() ) {
                    TimestampIntervalLiteralExpression ts = (TimestampIntervalLiteralExpression) pl.getPhase().get( 0 );
                    intval.setBeginOpen( ! ts.isLowClosedBoolean() );
                    intval.setEndOpen( ! ts.isHighClosedBoolean()  );
                    if ( ts.getLow_DateTime() != null ) {
                        TimestampLiteralExpression low = new TimestampLiteralExpressionImpl();
                        low.addValue_DateTime( ts.getLow_DateTime() );
                        intval.addBegin( low );
                    }
                    if ( ts.getHigh_DateTime() != null ) {
                        TimestampLiteralExpression hig = new TimestampLiteralExpressionImpl();
                        hig.addValue_DateTime( ts.getHigh_DateTime() );
                        intval.addEnd( hig );
                    }
                }
            } else if ( param.getPath().contains( dateProperty ) && param.getTypeName().contains( "PhysicalQuantityLiteral" ) ) {
                String value = param.getValue().get( 0 );
                Map<String,String> elements = tokenize( value );
                PhysicalQuantityLiteralExpression pq = (PhysicalQuantityLiteralExpression) buildLiteral( "PhysicalQuantityLiteral", elements );
                if ( pq.getValue_DoubleDouble() != null ) {
                    Double delta = pq.getValue_DoubleDouble();
                    DateAddExpression adder = new DateAddExpressionImpl();
                    adder.addDate( new TodayExpressionImpl() );

                    IntegerLiteralExpression i = new IntegerLiteralExpressionImpl();
                    i.addValue( delta.intValue() );
                    adder.addNumberOfPeriods( i );

                    LiteralExpression lit = new LiteralExpressionImpl();
                    lit.addValueType( "DateGranularity" );
                    lit.setValue_StringString( pq.getUnit() );
                    adder.addGranularity( lit );

                    if ( delta < 0 ) {
                        intval.addBegin( adder );
                        intval.addEnd( new TodayExpressionImpl() );
                    } else {
                        intval.addBegin( new TodayExpressionImpl() );
                        intval.addEnd( adder );
                    }
                }
            }
        }
        return intval;
    }

    private ValueSetExpression gatherValueSetFromParams( Template source, String codeProperty ) {
        for ( Parameter param : source.getHasParameter() ) {
            if ( param.getPath().contains( codeProperty ) && param.getTypeName().contains( "CodeLiteral" ) ) {
                String value = param.getValue().get( 0 );
                Map<String,String> elements = tokenize( value );
                if ( elements.containsKey( "valueSet" ) && elements.get( "valueSet" ).equals( elements.get( "codeSystem" ) ) ) {
                    ValueSetExpression valset = new ValueSetExpressionImpl();
                    valset.setIdString( elements.get( "valueSet" ) );
                    return valset;
                }
            }
        }
        return null;
    }

    private List<CodeLiteralExpression> gatherCodesFromParams( Template source, String codeProperty ) {
        List<CodeLiteralExpression> codes = new ArrayList<CodeLiteralExpression>();
        for ( Parameter param : source.getHasParameter() ) {
            if ( param.getName().contains( codeProperty ) && param.getTypeName().contains( "CodeLiteral" ) ) {
                String value = param.getValue().get( 0 );
                Map<String,String> elements = tokenize( value );
                if ( ( ! elements.containsKey( "valueSet" ) ) || ( ! elements.get( "valueSet" ).equals( elements.get( "codeSystem" ) ) ) ) {
                    CodeLiteralExpression code = (CodeLiteralExpression) buildLiteral( "CodeLiteral", elements );
                    if ( code != null ) {
                        codes.add( code );
                    }
                }
            }
        }
        return codes;
    }


    private SharpExpression buildConstraint( Template source, Parameter param ) {
        Map<String,String> elems = tokenize( param.getValue().get( 0 ) );
        String op = elems.get( "operation" );
        SharpExpression opExpr = null;

        try {
            opExpr = (SharpExpression) Class.forName( EqualExpressionImpl.class.getPackage().getName() + "." + op + "ExpressionImpl" ).newInstance();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        if ( ! ( opExpr instanceof BooleanExpression ) ) {
            throw new UnsupportedOperationException( "Unable to build constraint using non-boolean expression " + op );
        }

        PropertyExpression prop = new PropertyExpressionImpl();
        prop.addPath( getDomainPropertyExpression( param.getPath().get( 0 ) ) );
        if ( opExpr instanceof BinaryExpression ) {
            // TODO support expressions
            ((BinaryExpression) opExpr).addFirstOperand( prop );
            SharpExpression literal = buildLiteral( param.getTypeName().get( 0 ), elems );
            if ( literal != null && ! param.getNativeTypeName().isEmpty() && ! ( param.getNativeTypeName().get( 0 ).equals( param.getTypeName() ) ) ) {
                literal = cast( literal, param.getTypeName().get( 0 ), param.getNativeTypeName().get( 0 ) );
            }
            if ( literal != null ) {
                ((BinaryExpression) opExpr).addSecondOperand( literal );
            }
        } else if ( opExpr instanceof UnaryExpression ) {
            ((UnaryExpression) opExpr).addFirstOperand( prop );
        } else {
            throw new UnsupportedOperationException( "Unable to build constraint using expression " + op );
        }
        return opExpr;

    }

    private SharpExpression cast( SharpExpression literal, String current, String target ) {
        return literal;
    }

    private SharpExpression buildLiteral( String type, Map<String, String> elems ) {
        if ( "CodeLiteral".equals( type ) ) {
            CodeLiteralExpression code = new CodeLiteralExpressionImpl();
            if ( elems.containsKey( "code" ) ) {
                code.addCode( elems.get( "code" ) );
            }
            if ( elems.containsKey( "codeSystem" ) ) {
                code.addCodeSystem( elems.get( "codeSystem" ) );
            }
            if ( elems.containsKey( "displayValue" ) ) {
                code.addDisplayName( elems.get( "displayValue" ) );
            }
            return code;
        } else if ( "PeriodLiteral".equals( type ) ) {
            PeriodLiteralExpression period = new PeriodLiteralExpressionImpl();
            TimestampIntervalLiteralExpression intv = new TimestampIntervalLiteralExpressionImpl();
            if ( elems.containsKey( "low" ) ) {
                Date lowDate = parseDate( elems.get( "low" ) );
                intv.addLow_DateTime( lowDate );
            }
            if ( elems.containsKey( "high" ) && ! "".equals( elems.get( "high" ) ) ) {
                Date highDate = parseDate( elems.get( "high" ) );
                intv.addHigh_DateTime( highDate );
            }
            if ( elems.containsKey( "lowClosed" ) ) {
                intv.setLowClosedBoolean( Boolean.valueOf( elems.get( "lowClosed" ) ) );
            }
            if ( elems.containsKey( "highClosed" ) ) {
                intv.setHighClosedBoolean( Boolean.valueOf( elems.get( "highClosed" ) ) );
            }
            period.addPhase( intv );

            if ( elems.containsKey( "count" ) ) {
                period.addCount( Integer.valueOf( elems.get( "count" ) ) );
            }
            if ( elems.containsKey( "isFlexible" ) ) {
                period.setIsFlexible( Boolean.valueOf( elems.get( "isFlexible" ) ) );
            }
            //TODO Fix period/frequency
            return period;
        } else if ( "IntegerLiteral".equals( type ) ) {
            if ( elems.get( "value" ) != null ) {
                IntegerLiteralExpression literal = new IntegerLiteralExpressionImpl();
                literal.addValue( Integer.valueOf( elems.get( "value" ) ) );
                return literal;
            } else {
                return null;
            }
        } else if ( "StringLiteral".equals( type ) ) {
            if ( elems.get( "value" ) != null ) {
                StringLiteralExpression literal = new StringLiteralExpressionImpl();
                literal.addValue_String( elems.get( "value" ) );
                return literal;
            } else {
                return null;
            }
        } else if ( "BooleanLiteral".equals( type ) ) {
            if ( elems.get( "value" ) != null ) {
                BooleanLiteralExpression bool = new BooleanLiteralExpressionImpl();
                bool.addValue_Boolean( Boolean.valueOf( elems.get( "value" ) ) );
                return bool;
            } else {
                return null;
            }
        } else if ( "TimestampLiteral".equals( type ) ) {
            if ( elems.get( "value" ) != null ) {
                TimestampLiteralExpression time = new TimestampLiteralExpressionImpl();
                time.addValue_DateTime( parseDate( elems.get( "value" ) ) );
                return time;
            } else {
                return null;
            }
        } else if ( "PhysicalQuantityLiteral".equals( type ) ) {
            if ( elems.get( "value" ) != null ) {
                PhysicalQuantityLiteralExpression pq = new PhysicalQuantityLiteralExpressionImpl();
                pq.addValue_Double( Double.valueOf( elems.get( "value" ) ) );
                pq.addUnit( elems.get( "unit" ) );
                return pq;
            } else {
                return null;
            }
        } else if ( "PhysicalQuantityIntervalLiteral".equals( type ) ) {
            PhysicalQuantityIntervalLiteralExpression pqi = new PhysicalQuantityIntervalLiteralExpressionImpl();
            boolean found = false;

            if ( elems.get( "low_value" ) != null ) {
                PhysicalQuantityLiteralExpression low = new PhysicalQuantityLiteralExpressionImpl();
                low.addValue_Double( Double.valueOf( elems.get( "low_value" ) ) );
                low.addUnit( elems.get( "low_unit" ) );
                pqi.addLow_PhysicalQuantity( low );
                found = true;
            }

            if ( elems.get( "high_value" ) != null ) {
                PhysicalQuantityLiteralExpression high = new PhysicalQuantityLiteralExpressionImpl();
                high.addValue_Double( Double.valueOf( elems.get( "high_value" ) ) );
                high.addUnit( elems.get( "high_unit" ) );
                pqi.addHigh_PhysicalQuantity( high );
                found = true;
            }

            return found ? pqi : null;
        }
        return null;
    }


    private Map<String, String> tokenize( String value ) {
        Map<String,String> elements = new HashMap<String,String>();
        StringTokenizer tok = new StringTokenizer( value, ";" );
        while ( tok.hasMoreTokens() ) {
            String pair = tok.nextToken().trim();
            String key = pair.substring( 0, pair.indexOf( "=" ) );
            String val = pair.substring( pair.indexOf( "=" ) + 1 );
            elements.put( key, val );
        }
        return elements;
    }

    private DomainClassExpression getDomainClassExpression( String klass ) {
        DomainClassExpression type = new DomainClassExpressionImpl();
        ConceptCode typeCode = new ConceptCodeImpl();
        typeCode.addCode( klass );
        typeCode.addCodeSystem( DOMAIN_NS );
        type.addHasCode( typeCode );
        return type;
    }

    private DomainPropertyExpression getDomainPropertyExpression( String property ) {
        DomainPropertyExpression prop = new DomainPropertyExpressionImpl();
        ConceptCode typeCode = new ConceptCodeImpl();
        typeCode.addCode( property );
        typeCode.addCodeSystem( DOMAIN_NS );
        prop.addPropCode( typeCode );
        return prop;
    }



    private boolean isRequestParam( Parameter param, String klass ) {
        String codeProperty = getCodeProperty( klass );
        String dateProperty = getDateProperty( klass );
        String paramName = param.getName().get( 0 );
        return paramName.equals( codeProperty )
               || paramName.equals( dateProperty );
    }

    private boolean hasNonRequestFilterCriteria( Template source ) {
        //TODO introduce subjectProperty
        String klass = source.getRootClass().get( 0 );
        for ( Parameter param : source.getHasParameter() ) {
            if ( ! isRequestParam( param, klass ) ) {
                return true;
            }
        }
        return false;
    }


    //TODO Should invoke the domain explorer...
    //FIXME Stubbed as a PoC, this information shuold be part of the model
    private String getCodeProperty( String klass ) {
        if ( klass.contains( "Observation" ) ) {
            return "observationFocus";
        } else if ( klass.contains( "Person" ) ) {
            return "";
        } else if ( klass.contains( "SubstanceAdministration" ) ) {
            return "substanceCode";
        } else if ( klass.contains( "Problem" ) ) {
            return "problemCode";
        } else if ( klass.contains( "Procedure" ) ) {
            return "procedureCode";
        }
        throw new IllegalStateException( "Define code property for  " + klass );
    }

    //TODO Should invoke the domain explorer...
    //FIXME Stubbed as a PoC, this information shuold be part of the model
    private String getDateProperty( String klass ) {
        if ( klass.contains( "Observation" ) ) {
            return "observationEventTime";
        } else if ( klass.contains( "Person" ) ) {
            return "";
        } else if ( klass.contains( "SubstanceAdministrationProposal" ) ) {
            return "proposedAdministrationTimeInterval";
        } else if ( klass.contains( "SubstanceAdministration" ) ) {
            return "administrationTimeInterval";
        } else if ( klass.contains( "Problem" ) ) {
            return "problemEffectiveTime";
        } else if ( klass.contains( "Procedure" ) ) {
            return "procedureTime";
        }
        throw new IllegalStateException( "Define date range property for  " + klass );
    }




}
