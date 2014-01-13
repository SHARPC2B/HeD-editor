package edu.asu.sharpc2b.hed;

public class RepositoryFactory {

    public static enum REPOSITORY { FILE, STANBOL; }

    public static ArtifactRepository getRepository( REPOSITORY type ) {
        switch ( type ) {
            case FILE :
            case STANBOL:
            default:
                try {
                    return new StanbolArtifactRepository();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
        }
        return null;
    }

}
