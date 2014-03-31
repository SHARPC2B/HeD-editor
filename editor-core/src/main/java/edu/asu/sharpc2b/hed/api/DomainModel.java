package edu.asu.sharpc2b.hed.api;

import java.util.Map;


public interface DomainModel {

    public Map<String, String> getDomainClasses();

    public Map<String, String> getDomainProperties();

    public Map<String, String> getDomainProperties( String klassId );

    public String getDomainClassHierarchyDescription();

}
