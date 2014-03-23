package edu.asu.sharpc2b.hed;

import java.io.InputStream;
import java.util.List;

public interface ArtifactRepository {

    List<String> getAvailableArtifacts();

    String cloneArtifact( String uri, String newTitle, String newUri );

    InputStream loadArtifact( String uri );

    String snapshotArtifact( String uri );

    String saveArtifact( String uri, InputStream content );

    String deleteArtifact( String uri );

    public String createArtifact( String uri, String title, InputStream content );

}
