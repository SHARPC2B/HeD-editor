import edu.asu.sharpc2b.hed.ArtifactRepository;
import edu.asu.sharpc2b.hed.RepositoryFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@RunWith(Parameterized.class)
public class RepoTest {

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    private RepositoryFactory.REPOSITORY mode;

    public RepoTest( RepositoryFactory.REPOSITORY mode ) {
        this.mode = mode;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                { RepositoryFactory.REPOSITORY.FILE },
 //               { RepositoryFactory.REPOSITORY.STANBOL }
        };
        return Arrays.asList( data );
    }

    @Test
    public void testArtifacts() throws Exception {

        ArtifactRepository knowledgeRepo = RepositoryFactory.getRepository( mode );

        String uuid = UUID.randomUUID().toString();
        String uri = "http://asu.bmi.edu/Rule_" + System.identityHashCode( uuid );
        String title =  "HeD Artifact " + System.identityHashCode( uuid );
        InputStream stream = new ByteArrayInputStream( "test".getBytes() );

        String uuid2 = UUID.randomUUID().toString();
        String uri2 = "http://asu.bmi.edu/Rule_" + System.identityHashCode( uuid2 );
        String title2 =  "HeD Artifact " + System.identityHashCode( uuid2 );
        InputStream stream2 = new ByteArrayInputStream( "test2".getBytes() );

        knowledgeRepo.createArtifact( uri, title, stream );
        knowledgeRepo.createArtifact( uri2, title2, stream2 );

        List<String> artifacts = knowledgeRepo.getAvailableArtifacts();
        assertEquals( 2, artifacts.size() );
        assertTrue( artifacts.containsAll( Arrays.asList( uri, uri2 ) ) );

        knowledgeRepo.deleteArtifact( uri );

        artifacts = knowledgeRepo.getAvailableArtifacts();
        assertEquals( 1, artifacts.size() );
        assertTrue( artifacts.containsAll( Arrays.asList( uri2 ) ) );


        String uri3 = "http://asu.bmi.edu/Rule_" + System.identityHashCode( uuid ) + "_clone";
        knowledgeRepo.cloneArtifact( uri2, title2 + "_clone", uri3 );

        artifacts = knowledgeRepo.getAvailableArtifacts();
        assertEquals( 2, artifacts.size() );
        assertTrue( artifacts.containsAll( Arrays.asList( uri2, uri3 ) ) );


        knowledgeRepo.saveArtifact( uri2, new ByteArrayInputStream( "test2aaa".getBytes() ) );
        String uri4 = knowledgeRepo.snapshotArtifact( uri2 );

        artifacts = knowledgeRepo.getAvailableArtifacts();
        assertEquals( 3, artifacts.size() );

        knowledgeRepo.deleteArtifact( uri2 );
        knowledgeRepo.deleteArtifact( uri3 );
        knowledgeRepo.deleteArtifact( uri4 );

        artifacts = knowledgeRepo.getAvailableArtifacts();
        assertEquals( 0, artifacts.size() );

    }



}
