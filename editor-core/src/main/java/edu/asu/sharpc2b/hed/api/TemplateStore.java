package edu.asu.sharpc2b.hed.api;


import java.util.Map;
import java.util.Set;

public interface TemplateStore {

    public Set<String> getTemplateIds( String category );

    public Map<String,Object> getTemplateInfo( String templateId );

}
