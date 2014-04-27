package edu.asu.sharpc2b.hed.api;


import java.util.List;
import java.util.Map;

public interface ArtifactStore {

    public Map<String,String> getAvailableArtifacts();

    String importFromStream( byte[] stream );

    String cloneArtifact( String id );

    String openArtifact( String id );

    String snapshotArtifact( String id );

    String saveArtifact( String id );

    byte[] exportArtifact( String id, String format );

    String closeArtifact();

    String deleteArtifact( String id );

    String createArtifact();
}
