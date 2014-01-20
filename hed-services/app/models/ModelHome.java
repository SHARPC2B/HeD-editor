package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.hed.impl.EditorCoreImpl;
import edu.mayo.cts2.framework.core.client.Cts2RestClient;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryDirectory;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryList;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import models.ex.ConvertJsonToJavaException;
import models.ex.ModelDataFileNotFoundException;
import play.libs.Json;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * User: rk Date: 8/19/13 Package: models
 */
public class ModelHome
{

    static int lastKey = 1;


    private static EditorCore core = EditorCoreImpl.getInstance();
    private static Map<String, String> domainClasses;



    public static List<String> getAvailableArtifacts() {
        return core.getAvailableArtifacts();
    }

    public static Rule getArtifact( String id ) {
        return null;
    }

    public static String importFromStream( byte[] stream ) {
        return core.importFromStream( stream );
    }

    public static Rule createArtifact() {
        String ruleId = core.createArtifact();
        Rule rule = new Rule();
        rule.ruleId = ruleId;
        return rule;
    }

    public static String cloneArtifact( String id ) {
        return core.cloneArtifact( id );
    }

    public static String openArtifact( String id ) {
        return core.openArtifact( id );
    }

    public static String snapshotArtifact( String id ) {
        return core.snapshotArtifact( id );
    }

    public static String saveArtifact( String id ) {
        return core.saveArtifact( id );
    }

    public static String exportArtifact( String id ) {
        return core.exportArtifact( id );
    }

    public static String closeArtifact() {
        return core.closeArtifact();
    }

    public static String deleteArtifact( String id ) {
        return core.deleteArtifact( id );
    }




    public static Map<String,String> getNamedExpressions() {
        return core.getNamedExpressions();
    }

    public static byte[] getNamedExpression( String expressionIri ) {
        return core.getNamedExpression( expressionIri );
    }

    public static boolean deleteNamedExpression( String expressionIri ) {
        return core.deleteNamedExpression( expressionIri );
    }

    public static String cloneNamedExpression( String expressionIri ) {
        return core.cloneNamedExpression( expressionIri );
    }

    public static void updateNamedExpression( String expressionIRI, String exprName, byte[] doxBytes ) {
        core.updateNamedExpression( expressionIRI, exprName, doxBytes );
    }



    static String createUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static PrimitiveInst createPrimitiveInst(String ruleId,
                                                    String templateId)
    {
        Rule rule = ModelHome.getArtifact( ruleId );

        PrimitiveTemplate template = ModelHome.getPrimitiveTemplate( templateId );

        PrimitiveInst inst = template.createInst();
//        PrimitiveInst inst = new PrimitiveInst();
//        inst.type = template;
//        inst.id = createUUID();

        rule.primitives.add( inst );

        return inst;
    }

    /**
     * new
     */
    public static PrimitiveInst createPrimitiveInst(String templateId) {
        //TODO pass actual values from form
        String name = "foo";
        Map<String,Map<String,Object>> parameterValues = new HashMap<String,Map<String,Object>>();

        PrimitiveTemplate templ = templateCache.get( templateId );
        for ( String pid : templ.parameterIds ) {
            Map<String,Object> details = new HashMap<String,Object>();
            ParameterType param = templ.getParameter( pid );
            for ( ElementType elem : param.elements ) {
                details.put( elem.name, "TODO" );
            }
            parameterValues.put( pid, details );
        }

        core.instantiateTemplate( templateId, name, parameterValues );

        return null;
    }

    /**
     * new
     */
    public static PrimitiveTemplate getPrimitiveTemplateHed(final String id)
    {
        TemplateList tList = createJavaInstanceFromJsonFile( TemplateList.class );

        HedTypeList pList = createJavaInstanceFromJsonFile( HedTypeList.class );

        PrimitiveTemplate selectedTemplate = null;
        for ( PrimitiveTemplate t : tList.templates )
        {
            if ( t.templateId.equals( id ) )
            {
                selectedTemplate = t;
            }
        }
        spliceInHedTypes( selectedTemplate, pList );

        return selectedTemplate;
    }

    public static PrimitiveTemplate getPrimitiveTemplate(final String key)
    {
//        TemplateList tList = createJavaInstanceFromJsonFile( TemplateList.class );
//
//        ParameterList pList = createJavaInstanceFromJsonFile( ParameterList.class );
//
//        PrimitiveTemplate selectedTemplate = null;
//        for ( PrimitiveTemplate t : tList.templates )
//        {
//            if ( t.templateId.equals( id ) )
//            {
//                selectedTemplate = t;
//            }
//        }
//        spliceInParameters( selectedTemplate, pList );

        return templateCache.get( key );
    }

    private static void spliceInParameters(final PrimitiveTemplate selectedTemplate,
                                           final ParameterList allParameters)
    {
        if ( selectedTemplate == null )
        {
            return;
        }
        for ( String paramId : selectedTemplate.parameterIds )
        {
            ParameterType p = findParameter( paramId, allParameters );
            if ( p != null )
            {
                selectedTemplate.parameters.add( p );
            }
        }
    }

    static ParameterType findParameter(final String paramId,
                                       final ParameterList allParameters)
    {
        for ( ParameterType p : allParameters.parameters )
        {
            if ( p.key.equals( paramId ) )
            {
                return p;
            }
        }
        return null;
    }

    private static void spliceInHedTypes(final PrimitiveTemplate selectedTemplate,
                                         final HedTypeList allHedTypes)
    {
        if ( selectedTemplate == null ) {
            return;
        }
        for ( ParameterType p : selectedTemplate.parameters ) {
            HedType hedType = findHedType( p.hedTypeName, allHedTypes );

            if ( hedType != null ) {
                p.hedType = hedType;
                for ( ElementType eType : hedType.elements )
                {
                    p.elements.add( eType );
                }
            }
        }
    }

    static HedType findHedType(final String typeName,
                               final HedTypeList allTypes) {
        for ( HedType p : allTypes.hedTypes ) {
//            if ( p.hedTypeName.equals( typeName ) )
            if ( p.name.equals( typeName ) ) {
                return p;
            }
        }
        return null;
    }

    //========================================================================================

    public static String jsonFileForClass(final Class<?> aClass)
    {
        return jsonFileForClass( aClass.getSimpleName() );
    }

    public static String jsonFileForClass(final String aClassName)
    {
        return "public/data/" + aClassName + ".json";
    }

    /**
     * General factory method for creating Sharp Java instance of a particular class. Loads the JSON
     * from a file with a name corresponding to the Class name (e.g., for ParameterList, would load file
     * /public/data/ParameterList.json.
     *
     * Currently only files for TemplateList and ParameterList.
     */
    public static <M> M createJavaInstanceFromJsonFile(final Class<M> aClass) throws ModelDataFileNotFoundException, ConvertJsonToJavaException {
        String jsonDataResourcePath = jsonFileForClass( aClass );
        System.out.println( "jsonDataResourcePath = " + jsonDataResourcePath );

        final URL urlParameters = Resources.getResource( jsonDataResourcePath );

        final String jsonText;
        try
        {
            jsonText = Resources.toString( urlParameters, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ModelDataFileNotFoundException( e );
        }
        try
        {
            JsonNode jsonNode = Json.parse( jsonText );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );

            Object o;
            o = Json.fromJson( jsonNode, aClass );

            return (M) o;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ConvertJsonToJavaException( ex );
        }
    }


    private static Map<String,PrimitiveTemplate> templateCache = new HashMap<String,PrimitiveTemplate>();

    public static TemplateList getTemplateList( String category ) {
        System.out.println( "ModelHome was asked a template list" );
        TemplateList templateList = new TemplateList();
        templateCache.clear();

        Set<String> templateIds = core.getTemplateIds( category );

        for ( String templateId : templateIds ) {
            Map<String,Object> templateDetails = core.getTemplateInfo( templateId );
            PrimitiveTemplate template = new PrimitiveTemplate();
            template.templateId = (String) templateDetails.get( "templateId" );
            template.key = template.templateId.substring( template.templateId.lastIndexOf( "#" ) + 1 );
            template.name = (String) templateDetails.get( "name" );
            template.category = (String) templateDetails.get( "category" );
            template.group = (String) templateDetails.get( "group" );
            template.description = (String) templateDetails.get( "description" );
            template.example = (String) templateDetails.get( "example" );
            template.parameterIds = (List<String>) templateDetails.get( "parameterIds" );
            template.parameters = rebuildParameterInfo( (Map<String,Map<String,Object>>) templateDetails.get( "parameterData" ) );

            spliceInHedTypes( template, hedTypeList );

            templateCache.put( template.key, template );
            templateList.addTemplate( template );
        }

        System.out.println( "Template list from the home " + templateList );
        return templateList;
    }



    static HedTypeList hedTypeList = initHeDTypeList();

    private static HedTypeList initHeDTypeList() {
        HedTypeList typeList = createJavaInstanceFromJsonFile( HedTypeList.class );

        for ( HedType type : typeList.hedTypes ) {
            if ( "CodeLiteral".equals( type.name ) ) {
                ElementType codeSystem = type.getElement( "codeSystem" );
                codeSystem.selectionChoices = lookupCodeSystems();
            }
        }

        return typeList;
    }

    private static List<String> lookupCodeSystems() {
        CodeSystemCatalogEntryDirectory codesSystems = new Cts2RestClient( true ).getCts2Resource( "http://localhost:8080/cts2framework/codesystems", CodeSystemCatalogEntryDirectory.class );
        List<String> codeSystemNames = new ArrayList<>( codesSystems.getEntryCount() );
        for ( CodeSystemCatalogEntrySummary entry : codesSystems.getEntry() ) {
            codeSystemNames.add( entry.getCodeSystemName() );
        }
        return codeSystemNames;
    }


    private static List<ParameterType> rebuildParameterInfo( Map<String, Map<String,Object>> parameterData ) {
        ArrayList<ParameterType> parameterTypes = new ArrayList<ParameterType>();
        for ( String key : parameterData.keySet() ) {
            Map<String,Object> pData = parameterData.get( key );
            ParameterType param = new ParameterType();

            param.key = key;
            param.name = (String) pData.get( "name" );
            param.label = (String) pData.get( "label" );
            param.description = (String) pData.get( "description" );
            param.hedTypeName = (String) pData.get( "typeName" );
            param.expressionChoices = (List<String>) pData.get( "expressionChoices" );

            parameterTypes.add( param );
        }
        return parameterTypes;
    }










    public static Map<String, String> getDomainClasses() {
        Map<String,String> dk = core.getDomainClasses();
        System.out.println( "Retrieved domain klasses" );
        return dk;
    }

    public static Map<String, String> getDomainProperties() {
        return core.getDomainProperties();
    }
    public static Map<String, String> getDomainProperties( String klassId ) {
        return core.getDomainProperties( klassId );
    }


}
