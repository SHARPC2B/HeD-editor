package edu.asu.sharpc2b.hed.api;


public interface EditorCorePersistence {

    public String createNew();

    public String open( String artifactId );

    public boolean save( String artifactId );

    public void delete( String artifactId );

}
