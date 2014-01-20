import edu.asu.sharpc2b.hed.ArtifactRepository;
import edu.asu.sharpc2b.hed.RepositoryFactory;
import edu.asu.sharpc2b.hed.StanbolArtifactRepository;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class RepoTest {

    @Test
    public void testPesx() throws Exception {

        ArtifactRepository knowledgeRepo = RepositoryFactory.getRepository( RepositoryFactory.REPOSITORY.STANBOL );

        String uuid = UUID.randomUUID().toString();
        String uri = "http://asu.bmi.edu/Rule_" + System.identityHashCode( uuid );
        String title =  "HeD Artifact " + System.identityHashCode( uuid );
        InputStream stream = new ByteArrayInputStream( "test".getBytes() );

        knowledgeRepo.createArtifact( uri, title, stream );

        System.out.println( knowledgeRepo.getAvailableArtifacts() );


    }


    @Test
    public void testAvailableArtifacts() throws Exception {

        ArtifactRepository knowledgeRepo = RepositoryFactory.getRepository( RepositoryFactory.REPOSITORY.STANBOL );

        System.out.println( knowledgeRepo.getAvailableArtifacts() );

    }


}
