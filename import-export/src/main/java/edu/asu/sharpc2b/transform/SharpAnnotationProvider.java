package edu.asu.sharpc2b.transform;

import com.clarkparsia.empire.annotation.RdfsClass;
import com.clarkparsia.empire.util.EmpireAnnotationProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class SharpAnnotationProvider implements EmpireAnnotationProvider {

    private Enumeration<URL> sources;

    @Override
    public Collection<Class<?>> getClassesWithAnnotation( Class<? extends Annotation> theAnnotation ) {
        if ( theAnnotation.equals( RdfsClass.class ) ) {
            long now = System.currentTimeMillis();
            try {
                Set<Class<?>> classes = new HashSet<Class<?>>();
                Enumeration<URL> sources = getSources();
                while ( sources.hasMoreElements() ) {
                    URL url = sources.nextElement();
                    addClasses( classes, url );
                }
                return classes;
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        } else {
            return Collections.emptyList();
        }
    }

    private void addClasses( Set<Class<?>> classes, URL url ) {
        try {
            InputStream inputStream = url.openStream();
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = "";
            while ( ( line = reader.readLine() ) != null ) {
                if ( line.contains( RdfsClass.class.getName() ) ) {
                    String classList = line.substring( line.indexOf( '=' ) + 1 );
                    StringTokenizer tok = new StringTokenizer( classList, "," );
                    while ( tok.hasMoreTokens() ) {
                        try {
                            Class<?> klass = loadClass( tok.nextToken().trim() );
                            classes.add( klass );
                        } catch ( ClassNotFoundException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    private Class<?> loadClass( String klassName ) throws ClassNotFoundException {
        try {
            return Class.forName(klassName);
        }
        catch (ClassNotFoundException e) {
            try {
                return Thread.currentThread().getContextClassLoader().loadClass(klassName);
            }
            catch (ClassNotFoundException ex) {
                return ClassLoader.getSystemClassLoader().loadClass(klassName);
            }
        }
    }

    public Enumeration<URL> getSources() throws IOException {
        String res = "empire.annotation.index";
        Enumeration<URL> sources = ClassLoader.getSystemResources( res );
        if ( ! sources.hasMoreElements() ) {
            sources = Thread.currentThread().getContextClassLoader().getResources( res );
            if ( ! sources.hasMoreElements() ) {
                sources = SharpAnnotationProvider.class.getClassLoader().getResources( res );
            }
        }
        return sources;
    }
}
