package edu.asu.sharpc2b.hed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtifactRepositoryFactory {

    static Logger logger = LoggerFactory.getLogger( ArtifactRepositoryFactory.class );

    public enum REPOSITORY { FILE, STANBOL; }

    public static ArtifactRepository getRepository( String type ) {
        REPOSITORY repoType = REPOSITORY.valueOf( type );
        return getRepository( repoType );
    }

    public static ArtifactRepository getRepository( REPOSITORY repoType ) {
        ArtifactRepository repo;
        switch ( repoType ) {
            case STANBOL:
                repo = new StanbolArtifactRepository();
                break;
            case FILE :
                repo = new FilesystemArtifactRepository();
                break;
            default:
                repo = new FilesystemArtifactRepository();
                break;
        }
        if ( logger.isDebugEnabled() ) {
            logger.debug( "Requested repository for type " + repoType + " >> instantiated a " + repo.getClass().getName() );
        }
        return repo;
    }

}
