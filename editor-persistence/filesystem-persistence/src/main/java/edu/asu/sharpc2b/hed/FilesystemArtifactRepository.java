package edu.asu.sharpc2b.hed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FilesystemArtifactRepository implements ArtifactRepository {

    private File rootDirectory;
    private File repoIndex;

    public FilesystemArtifactRepository() {
        rootDirectory = new File( "./HeD_Repo" );
        if ( ! rootDirectory.exists() ) {
            rootDirectory.mkdirs();
        }
        repoIndex = new File( rootDirectory.getAbsolutePath() + File.separator + "index" );
        if ( ! repoIndex.exists() ) {
            try {
                repoIndex.createNewFile();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getAvailableArtifacts() {
        List<String> names = new ArrayList<>(  );
        try {
            BufferedReader reader = new BufferedReader( new FileReader( repoIndex ) );
            for ( String line; ( line = reader.readLine() ) != null; ) {
                if ( ! "".equals( line ) ) {
                    names.add( line.substring( 0, line.indexOf( "," ) ) );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList( names );
    }

    @Override
    public String createArtifact( String uri, String title, InputStream content ) {
        PrintWriter output = null;
        FileOutputStream fos = null;
        try {
            File f = new File( getFullFileNameForTitle( title ) );

            output = new PrintWriter( new FileWriter( repoIndex.getAbsolutePath(), true ) );
            output.println( uri + "," + title  );

            fos = new FileOutputStream( f );
            byte[] data = new byte[ content.available() ];
            content.read( data );
            fos.write( data );

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                output.close();
            }
            if ( fos != null ) {
                try {
                    fos.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        return uri;
    }

    private String getFullFileNameForTitle( String title ) {
        return rootDirectory.getAbsolutePath() + File.separator + title + ".HeD";
    }


    @Override
    public String cloneArtifact( String uri, String newTitle, String newUri ) {
        try {
            createArtifact( newUri, newTitle, new FileInputStream( getArtifactByUri( uri ) ) );
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
            return null;
        }
        return newUri;
    }

    @Override
    public InputStream loadArtifact( String uri ) {
        File f = getArtifactByUri( uri );
        if ( f != null ) {
            try {
                return new FileInputStream( f );
            } catch ( FileNotFoundException e ) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String snapshotArtifact( String uri ) {
        String title = getArtifactTitleByUri( uri );
        String snap = "_" + new Date().getTime();
        String newUri = uri + snap;
        String newTitle = title + snap;
        return cloneArtifact( uri, newTitle, newUri  );
    }

    @Override
    public String saveArtifact( String uri, InputStream content ) {
        String title = getArtifactTitleByUri( uri );
        try {
            File f = new File( getFullFileNameForTitle( title ) );
            FileOutputStream fos = new FileOutputStream( f );
            byte[] data = new byte[ content.available() ];
            content.read( data );
            fos.write( data );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
        return uri;
    }

    @Override
    public String deleteArtifact( String uri ) {
        File f = getArtifactByUri( uri );
        f.delete();
        removeUriFromIndex( uri );
        return uri;
    }

    private void removeUriFromIndex( String uri ) {
        try {
            BufferedReader reader = new BufferedReader( new FileReader( repoIndex ) );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for ( String line; ( line = reader.readLine() ) != null; ) {
                if ( ! "".equals( line ) ) {
                    String lineUri = line.substring( 0, line.indexOf( "," ) );
                    if ( ! lineUri.equals( uri ) ) {
                        baos.write( ( line + "\n" ).getBytes() );
                    }
                }
            }
            reader.close();

            FileOutputStream fos = new FileOutputStream( repoIndex );
            fos.write( baos.toByteArray() );
            fos.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }


    protected File getArtifactByUri( String uri ) {
        String title = getArtifactTitleByUri( uri );
        return new File( getFullFileNameForTitle( title ) );
    }

    protected String getArtifactTitleByUri( String uri ) {
        try {
            BufferedReader reader = new BufferedReader( new FileReader( repoIndex ) );
            for ( String line; ( line = reader.readLine() ) != null; ) {
                if ( line.startsWith( uri ) ) {
                    return line.substring( line.indexOf( "," ) + 1 );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

}
