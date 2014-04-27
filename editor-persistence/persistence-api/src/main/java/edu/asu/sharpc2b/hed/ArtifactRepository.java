package edu.asu.sharpc2b.hed;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ArtifactRepository {

    public Map<String,String> getAvailableArtifacts();

    public String cloneArtifact( String uri, String newTitle, String newUri );

    public InputStream loadArtifact( String uri );

    public String snapshotArtifact( String uri );

    public String saveArtifact( String uri, InputStream content );

    public String deleteArtifact( String uri );

    public String createArtifact( String uri, String title, InputStream content );

}
