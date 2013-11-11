package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import models.ex.ConvertJsonToJavaException;
import models.ex.ModelDataFileNotFoundException;
import play.libs.Json;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: rk Date: 8/18/13 Package: models
 */
public class ParameterList
        extends SharpType
{

//    public String mtype = getClass().getSimpleName();

    public List<ParameterType> parameters;

    //========================================================================================

    public static ParameterList createFromFile ()
            throws ModelDataFileNotFoundException, ConvertJsonToJavaException
    {
        String parameterListJsonResourcePath = ModelHome.jsonFileForClass( ParameterList.class );
//        System.out.println( "parameterListJsonResourcePath = " + parameterListJsonResourcePath );

        final URL urlParameters = Resources.getResource( parameterListJsonResourcePath );

        final String parametersText;
        try
        {
            parametersText = Resources.toString( urlParameters, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ModelDataFileNotFoundException( e );
        }
        try
        {
            JsonNode parametersNode = Json.parse( parametersText );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );

            Object o;
            o = Json.fromJson( parametersNode, ParameterList.class );

            ParameterList pList = (ParameterList) o;

            return pList;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ConvertJsonToJavaException( ex );
        }
    }

}
