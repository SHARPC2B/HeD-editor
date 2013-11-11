package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import models.ex.ModelDataFileNotFoundException;
import play.libs.Json;


import javax.persistence.Id;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: rk Date: 8/18/13 Package: models
 */
public class TemplateList
        extends SharpType
{

//    public String mtype = getClass().getSimpleName();

    @Id
    public String listId = UUID.randomUUID().toString();

    public List<PrimitiveTemplate> templates;

    public TemplateList() {
        templates = new ArrayList<PrimitiveTemplate>();
    }

    public void addTemplate( PrimitiveTemplate template ) {
        templates.add( template );
    }

    //========================================================================================

    public static TemplateList createFromFile ()
    {
//        final String templateListJsonResourcePath = "public/data/TemplateList.json";
        final String templateListJsonResourcePath = ModelHome.jsonFileForClass( TemplateList.class );
//        System.out.println( "templateListJsonResourcePath = " + templateListJsonResourcePath );
        final URL urlTemplates = Resources.getResource( templateListJsonResourcePath );
        final String templatesText;
        try
        {
            templatesText = Resources.toString( urlTemplates, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ModelDataFileNotFoundException( e );
        }


        JsonNode templatesNode = Json.parse( templatesText );
        Object o = Json.fromJson( templatesNode, TemplateList.class );

        TemplateList tList = (TemplateList) o;
        return tList;
    }

}
