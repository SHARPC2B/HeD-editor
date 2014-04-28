package edu.asu.sharpc2b;

import edu.asu.sharpc2b.actions.SharpAction;
import edu.asu.sharpc2b.hed.impl.DomainHierarchyExplorer;
import edu.asu.sharpc2b.ops.DomainClassExpression;
import edu.asu.sharpc2b.ops.DomainClassExpressionImpl;
import edu.asu.sharpc2b.ops.DomainPropertyExpression;
import edu.asu.sharpc2b.ops.DomainPropertyExpressionImpl;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpression;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpressionImpl;
import edu.asu.sharpc2b.ops_set.CodeLiteralExpression;
import edu.asu.sharpc2b.ops_set.CodeLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.ListExpression;
import edu.asu.sharpc2b.ops_set.ListExpressionImpl;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpression;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.TimestampIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.TimestampIntervalLiteralExpressionImpl;
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

    private void createClinicalRequest( String name, Template source, boolean asTrigger ) {
        if ( source.getRootClass().isEmpty() ) {
            System.err.println( "WARNING : Template with no root class, impossible to check request!" );
            return;
        }
        String klass = source.getRootClass().get( 0 );
        ClinicalRequestExpression creq = new ClinicalRequestExpressionImpl();
        String exprName = name + " - Request ";

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




        this.expressions.put( exprName, creq );
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
                    CodeLiteralExpression code = new CodeLiteralExpressionImpl();
                    if ( elements.containsKey( "code" ) ) {
                        code.addCode( elements.get( "code" ) );
                    }
                    if ( elements.containsKey( "codeSystem" ) ) {
                        code.addCodeSystem( elements.get( "codeSystem" ) );
                    }
                    if ( elements.containsKey( "displayValue" ) ) {
                        code.addDisplayName( elements.get( "displayValue" ) );
                    }
                    codes.add( code );
                }
            }
        }
        return codes;
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
        prop.addHasCode( typeCode );
        return prop;
    }


    //TODO Should invoke the domain explorer...
    //FIXME Stubbed as a PoC, this information shuold be part of the model
    private String getCodeProperty( String klass ) {
        if ( klass.contains( "Observation" ) ) {
            return "observationFocus";
        }
        throw new IllegalStateException( "Define code property for  " + klass );
    }

    //TODO Should invoke the domain explorer...
    //FIXME Stubbed as a PoC, this information shuold be part of the model
    private String getDateProperty( String klass ) {
        if ( klass.contains( "Observation" ) ) {
            return "observationEventTime";
        }
        throw new IllegalStateException( "Define date range property for  " + klass );
    }




}
