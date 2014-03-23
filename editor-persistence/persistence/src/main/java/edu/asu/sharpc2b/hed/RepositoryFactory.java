package edu.asu.sharpc2b.hed;

public class RepositoryFactory {

    public static enum REPOSITORY { FILE, STANBOL; }

    public static ArtifactRepository getRepository( REPOSITORY type ) {
        switch ( type ) {
            case STANBOL:
                return new StanbolArtifactRepository();
            case FILE :
                return new FilesystemArtifactRepository();
            default:
                return new FilesystemArtifactRepository();
        }
    }

}
