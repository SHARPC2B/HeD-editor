package models;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TemplateList {

    @Id
    public String listId = UUID.randomUUID().toString();

    public List<PrimitiveTemplate> templates;

    public TemplateList() {
        templates = new ArrayList<PrimitiveTemplate>();
    }

    public void addTemplate( PrimitiveTemplate template ) {
        templates.add( template );
    }

    public String getListId() {
        return listId;
    }

    public void setListId( String listId ) {
        this.listId = listId;
    }

    public List<PrimitiveTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates( List<PrimitiveTemplate> templates ) {
        this.templates = templates;
    }

}
