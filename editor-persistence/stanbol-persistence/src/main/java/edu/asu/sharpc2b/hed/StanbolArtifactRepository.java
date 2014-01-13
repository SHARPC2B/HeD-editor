package edu.asu.sharpc2b.hed;

import com.hp.hpl.jena.vocabulary.DCTerms;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.stanbol.client.StanbolClient;
import org.apache.stanbol.client.contenthub.search.model.DocumentResult;
import org.apache.stanbol.client.contenthub.search.model.StanbolSearchResult;
import org.apache.stanbol.client.contenthub.store.model.ContentHubDocumentRequest;
import org.apache.stanbol.client.contenthub.store.model.ContentItem;
import org.apache.stanbol.client.contenthub.store.model.DefaultMetadata;
import org.apache.stanbol.client.contenthub.store.model.Metadata;
import org.apache.stanbol.client.contenthub.store.services.StanbolContenthubStoreService;
import org.apache.stanbol.client.entityhub.model.LDPathProgram;
import org.apache.stanbol.client.exception.StanbolClientException;
import org.apache.stanbol.client.impl.StanbolClientImpl;
import org.apache.stanbol.client.services.exception.StanbolServiceException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StanbolArtifactRepository implements ArtifactRepository {


    private StanbolClient client;
    private String stanbolEndpoint = "http://localhost:9090/";

    StanbolArtifactRepository() {
        try {
            client = new StanbolClientImpl( getStanbolEndpoint() );
        } catch ( StanbolClientException e ) {
            e.printStackTrace();
        }
    }

    public String getStanbolEndpoint() {
        //TODO Hardcoded!
        return stanbolEndpoint;
    }


    @Override
    public List<String> getAvailableArtifacts() {
        try {
            SolrQuery sQuery = new SolrQuery();
            sQuery.setQuery( "HeD" );
            StanbolSearchResult searchResult = client.solrSearch().search(
                    StanbolContenthubStoreService.STANBOL_DEFAULT_INDEX, sQuery );

            List<DocumentResult> docs = searchResult.getItemResults();

            List<String> ids = new ArrayList<String>( docs.size() );
            for ( DocumentResult dox : docs ) {
                ids.add( dox.getLocalId() );
            }
            return ids;
        } catch ( StanbolServiceException e ) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public String createArtifact( String uri, String title, InputStream content ) {
        try {
            ContentHubDocumentRequest request = new ContentHubDocumentRequest();
            request.setURI( uri );
            request.setTitle( title );

            List<Metadata> metadata = new ArrayList<Metadata>();
            metadata.add( new DefaultMetadata( "identifier", Arrays.asList( uri ), "http://dubCore", "ident", "author", "http://dubCore#ident" ) );
            request.setMetadata( metadata );

            request.setContentStream( content );

            String docUri = client.contenthub().add( StanbolContenthubStoreService.STANBOL_DEFAULT_INDEX, "default", request );
            return docUri;

        } catch ( StanbolServiceException e ) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public String cloneArtifact( String uri ) {
        return null;
    }

    @Override
    public InputStream loadArtifact( String uri ) {
        try {
            ContentItem ci = client.contenthub().get( StanbolContenthubStoreService.STANBOL_DEFAULT_INDEX, uri, true );
            return ci.getRawContent();
        } catch ( StanbolServiceException e ) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String snapshotArtifact( String uri ) {
        return null;
    }

    @Override
    public String saveArtifact( String uri ) {
        return null;
    }

    @Override
    public String deleteArtifact( String uri ) {
        try {
            client.contenthub().delete( StanbolContenthubStoreService.STANBOL_DEFAULT_INDEX, uri );
            return uri;
        } catch ( StanbolServiceException e ) {
            e.printStackTrace();
            return null;
        }
    }


}
