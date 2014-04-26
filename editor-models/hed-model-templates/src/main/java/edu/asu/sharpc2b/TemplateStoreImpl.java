package edu.asu.sharpc2b;

import edu.asu.sharpc2b.hed.impl.DomainHierarchyExplorer;
import edu.asu.sharpc2b.ops.BooleanExpression;
import edu.asu.sharpc2b.ops.CodeExpression;
import edu.asu.sharpc2b.ops.DateExpression;
import edu.asu.sharpc2b.ops.IntExpression;
import edu.asu.sharpc2b.ops.IntervalExpression;
import edu.asu.sharpc2b.ops.NumberExpression;
import edu.asu.sharpc2b.ops.PhysicalQuantityExpression;
import edu.asu.sharpc2b.ops.RatioExpression;
import edu.asu.sharpc2b.ops.RealExpression;
import edu.asu.sharpc2b.ops.StringExpression;
import edu.asu.sharpc2b.ops_set.AddressLiteralExpression;
import edu.asu.sharpc2b.ops_set.BooleanLiteralExpression;
import edu.asu.sharpc2b.ops_set.CodeLiteralExpression;
import edu.asu.sharpc2b.ops_set.CodedOrdinalLiteralExpression;
import edu.asu.sharpc2b.ops_set.EntityNameLiteralExpression;
import edu.asu.sharpc2b.ops_set.IdentifierLiteralExpression;
import edu.asu.sharpc2b.ops_set.IntegerIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.IntegerLiteralExpression;
import edu.asu.sharpc2b.ops_set.LiteralExpression;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpression;
import edu.asu.sharpc2b.ops_set.PhysicalQuantityIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.PhysicalQuantityLiteralExpression;
import edu.asu.sharpc2b.ops_set.QuantityIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.RatioLiteralExpression;
import edu.asu.sharpc2b.ops_set.RealIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.RealLiteralExpression;
import edu.asu.sharpc2b.ops_set.SimpleCodeLiteralExpression;
import edu.asu.sharpc2b.ops_set.StringLiteralExpression;
import edu.asu.sharpc2b.ops_set.TimestampIntervalLiteralExpression;
import edu.asu.sharpc2b.ops_set.TimestampLiteralExpression;
import edu.asu.sharpc2b.ops_set.UrlLiteralExpression;
import edu.asu.sharpc2b.templates.Parameter;
import edu.asu.sharpc2b.templates.Template;
import edu.asu.sharpc2b.templates_data.IndividualFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class TemplateStoreImpl {

    private static TemplateStoreImpl instance;
    private static Map<String, String> hedTypeMap;
    private static Map<String, Class> typeForHeDMap;

    static {
        hedTypeMap = new HashMap<String,String>();
        hedTypeMap.put( "AD", AddressLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "BL", BooleanLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "CD", CodeLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "CO", CodedOrdinalLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "CS", SimpleCodeLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "EN", EntityNameLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "II", IdentifierLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "INT", IntegerLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "IVL_INT", IntegerIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "IVL_PQ", PhysicalQuantityIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "IVL_QTY", QuantityIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "IVL_REAL", RealIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "IVL_TS", TimestampIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "PQ", PhysicalQuantityLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "REAL", RealLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "RTO", RatioLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "ST", StringLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "TEL", UrlLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "TS", TimestampLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "PIVL_TS", PeriodLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "Frequency", PeriodLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "BaseFrequency", PeriodLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "ED", LiteralExpression.class.getSimpleName().replace( "Expression", "" ) );
        hedTypeMap.put( "Entity", IdentifierLiteralExpression.class.getSimpleName().replace( "Expression", "" ) );

        typeForHeDMap = new HashMap<String,Class>();
        typeForHeDMap.put( AddressLiteralExpression.class.getSimpleName().replace( "Expression", "" ), StringExpression.class );
        typeForHeDMap.put( BooleanLiteralExpression.class.getSimpleName().replace( "Expression", "" ), BooleanExpression.class );
        typeForHeDMap.put( CodeLiteralExpression.class.getSimpleName().replace( "Expression", "" ), CodeExpression.class );
        typeForHeDMap.put( CodedOrdinalLiteralExpression.class.getSimpleName().replace( "Expression", "" ), CodedOrdinalLiteralExpression.class );
        typeForHeDMap.put( SimpleCodeLiteralExpression.class.getSimpleName().replace( "Expression", "" ), CodeExpression.class );
        typeForHeDMap.put( EntityNameLiteralExpression.class.getSimpleName().replace( "Expression", "" ), StringExpression.class );
        typeForHeDMap.put( IdentifierLiteralExpression.class.getSimpleName().replace( "Expression", "" ), StringExpression.class );
        typeForHeDMap.put( IntegerLiteralExpression.class.getSimpleName().replace( "Expression", "" ), IntExpression.class );
        typeForHeDMap.put( IntegerIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ), IntervalExpression.class );
        typeForHeDMap.put( PhysicalQuantityIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ), IntervalExpression.class );
        typeForHeDMap.put( QuantityIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ), IntervalExpression.class );
        typeForHeDMap.put( RealIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ), IntervalExpression.class );
        typeForHeDMap.put( TimestampIntervalLiteralExpression.class.getSimpleName().replace( "Expression", "" ), IntervalExpression.class );
        typeForHeDMap.put( PhysicalQuantityLiteralExpression.class.getSimpleName().replace( "Expression", "" ),PhysicalQuantityExpression.class );
        typeForHeDMap.put( RealLiteralExpression.class.getSimpleName().replace( "Expression", "" ), RealExpression.class );
        typeForHeDMap.put( RatioLiteralExpression.class.getSimpleName().replace( "Expression", "" ), RealExpression.class );
        typeForHeDMap.put( StringLiteralExpression.class.getSimpleName().replace( "Expression", "" ), StringExpression.class );
        typeForHeDMap.put( UrlLiteralExpression.class.getSimpleName().replace( "Expression", "" ), StringExpression.class );
        typeForHeDMap.put( TimestampLiteralExpression.class.getSimpleName().replace( "Expression", "" ), DateExpression.class );
        typeForHeDMap.put( PeriodLiteralExpression.class.getSimpleName().replace( "Expression", "" ), PeriodLiteralExpression.class );
        typeForHeDMap.put( LiteralExpression.class.getSimpleName().replace( "Expression", "" ), StringLiteralExpression.class );
    }

    public static TemplateStoreImpl getInstance( DomainHierarchyExplorer explorer ) {
        if ( instance == null ) {
            instance = new TemplateStoreImpl( explorer );
        }
        return instance;
    }

    private Map<String,Set<String>> templateByCategory = new HashMap<String,Set<String>>();
    private SortedMap<String,Template> templateCache = new TreeMap<String,Template>();


    protected TemplateStoreImpl( DomainHierarchyExplorer explorer ) {
            cacheTemplates( explorer );
    }



    public Set<String> getTemplateIds( String category ) {
        if ( category == null  ) {
            return templateCache.keySet();
        }
        if ( ! templateByCategory.containsKey( category ) ) {
            return Collections.emptySet();
        }
        return templateByCategory.get( category );
    }

    public Template getTemplateInfo( String templateId ) {
        if ( ! templateCache.containsKey( templateId ) ) {
            return null;
        }
        return templateCache.get( templateId );
    }


    private void cacheTemplates( DomainHierarchyExplorer explorer ) {
        for ( String name : IndividualFactory.getIndividualNames() ) {
            Object ind = IndividualFactory.getNamedIndividuals().get( name );
            if ( ind instanceof Template ) {
                Template template = (Template) ind;

                assignCategories( name, template, template.getClass().getInterfaces() );

                determineTypes( template, explorer );
            }
        }
    }

    private void determineTypes( Template template, DomainHierarchyExplorer explorer ) {
        List<Parameter> undefinedParams = new ArrayList<Parameter>();
        for ( Parameter param : template.getHasParameter() ) {
            StringTokenizer tok = new StringTokenizer( param.getPath().get( 0 ), "." );
            while ( tok.hasMoreTokens() ) {
                String prop = explorer.getDomainNs() + tok.nextToken();
                if ( ! tok.hasMoreTokens() ) {
                    if ( explorer.getPropertyRanges().containsKey( prop ) ) {
                        String originalType = explorer.getPropertyRanges().get( prop );
                        originalType = originalType.substring( originalType.lastIndexOf( "#" ) + 1 );
                        if ( ! param.getTypeName().get( 0 ).equals( originalType ) ) {
                            System.out.println( "WARNING : property " + prop + " on path " + param.getPath().get( 0 ) +  " declared with different types " + originalType + " vs (xls) " + param.getTypeName().get( 0 ) );
                            originalType = param.getTypeName().get( 0 );
                        }
                        param.getTypeName().clear();
                        param.addTypeName( mapNativeToHeD( originalType ) );
                    } else {
                        String originalType = param.getTypeName().get( 0 );
                        System.out.println( "WARNING : unable to find property " + prop + " not found in domain model OR property has complex anonymous type, will use the declared type " + originalType );
                        param.getTypeName().clear();
                        param.addTypeName( mapNativeToHeD( originalType ) );
                    }
                } else {
                    if ( ! explorer.getPropertyRanges().containsKey( prop ) ) {
                        System.err.println( "WARNING : could not find range for " + prop + " on path " + param.getPath().get( 0 ) );
                        undefinedParams.add( param );
                    }
                }
            }
        }
        template.getHasParameter().removeAll( undefinedParams );
    }

    private String mapNativeToHeD( String originalType ) {
        String hedType = hedTypeMap.get( originalType );
        if ( hedType == null ) {
            System.out.println( "WARNING : NO HeD type defined for type " + originalType );
        }
        return hedType;
    }

    public Class<?> getClassForHeDType( String hedType ) {
        return typeForHeDMap.get( hedType );
    }


    private void assignCategories( String name, Template template, Class<?>[] interfaces ) {
        for ( Class<?> interfax : interfaces ) {
            if ( interfax != Template.class && Template.class.isAssignableFrom( interfax ) ) {
                String cat = interfax.getSimpleName().replace( "Template", "" );

                templateCache.put( name, template );
                addToCategory( name, cat );

                assignCategories( name, template, interfax.getInterfaces() );
            }
        }
    }

    private void addToCategory( String templateId, String cat ) {
        Set<String> category = templateByCategory.get( cat );
        if ( category == null ) {
            category = new HashSet<String>();
            templateByCategory.put( cat, category );
        }
        category.add( templateId );
    }



    public String instantiateTemplate( String tId, String name, Map<String,Map<String,Object>> parameterValues ) {
        System.out.println( "Core init " + tId + " with name " + name + " using " + parameterValues );

        /*
        OWLDataFactory odf = templates.getOWLOntologyManager().getOWLDataFactory();
        OWLNamedIndividual root = odf.getOWLNamedIndividual( templateDataIRI( tId ) );

        List<String> exprRoots = getObjectPropertyValues( root, "hasIncarnation", odf );
        for ( String exprName : exprRoots ) {
            String expression = buildExpression( exprName, odf );
            this.getCurrentArtifact().updateNamedExpression( exprName, exprName, expression.getBytes() );
        }
        */
        return null;
    }


    public static void main( String... args ) {
        //TemplateStoreImpl t = new TemplateStoreImpl( DomainHierarchyExplorer.getInstance( "ontologies/domain_models/domain-vmr.ofn", "urn:hl7-org:vmr:r2#" ) );
        //System.out.println( t.getTemplateIds( "Condition" ) );
        Template templ = (Template) IndividualFactory.getNamedIndividuals().get( "MeniscusRepairTEST" );
    }

}
