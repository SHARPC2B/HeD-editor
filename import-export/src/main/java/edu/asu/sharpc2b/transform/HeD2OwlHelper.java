package edu.asu.sharpc2b.transform;

import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops_set.AndExpression;
import org.drools.spi.KnowledgeHelper;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class HeD2OwlHelper {

    private OWLOntology ontology;
    private OWLOntologyManager manager;
    private OWLDataFactory factory;
    private PrefixManager prefixManager;

    public HeD2OwlHelper( OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, PrefixManager prefixManager ) {
        this.ontology = ontology;
        this.manager = manager;
        this.factory = factory;
        this.prefixManager = prefixManager;


        OWLImportsDeclaration imprt = manager.getOWLDataFactory().getOWLImportsDeclaration( IRI.create( "http://asu.edu/sharpc2b/sharp" ) );
        manager.applyChange( new AddImport( ontology, imprt ) );
    }

    public void registerDataModel( String model ) {
        IRI iri = IRI.create( model );
        ((DefaultPrefixManager) prefixManager).setPrefix( iri.getFragment() + ":", iri.toString() + "#" );
    }


    public String uniqueName( Object x ) {
        if ( x.getClass().getName().contains( "KnowledgeDocument" ) ) {
            return "tns:" + x.getClass().getSimpleName();
        } else {
            return "tns:" + x.getClass().getSimpleName() + "_" + x.hashCode();
        }
    }

    public OWLNamedIndividual asIndividual( Object x ) {
        return factory.getOWLNamedIndividual( uniqueName( x ), prefixManager );
    }

    public OWLNamedIndividual asIndividual( String x ) {
        if ( x.startsWith( "{urn:" ) ) {
            String ns = x.substring( 1, x.indexOf( "}" ) );
            String y = x.substring( x.indexOf( "}" ) + 1 );
            return factory.getOWLNamedIndividual( "vmr:" + y, prefixManager );
        } else if ( x.startsWith( "urn:" ) ) {
            String ns = x.substring( 0, x.indexOf( "#" ) + 1 );
            String y = x.substring( x.indexOf( "#" ) + 1 );
            return factory.getOWLNamedIndividual( "vmr:" + y, prefixManager );
        } else {
            return factory.getOWLNamedIndividual( x, prefixManager );
        }
    }

    public OWLNamedIndividual asExpressionIndividual( String op, Object root ) {
        return asIndividual( "tns:" + op + "_" + System.identityHashCode( root ) );
    }

    public OWLNamedIndividual urnAsIndividual( String x ) {
        return factory.getOWLNamedIndividual( IRI.create( x ) );
    }

    public OWLDeclarationAxiom assertIndividual( OWLNamedIndividual ind ) {
        return factory.getOWLDeclarationAxiom( ind );
    }

    public OWLClassAssertionAxiom assertType( OWLNamedIndividual ind, String klass ) {
        return factory.getOWLClassAssertionAxiom(
                factory.getOWLClass( klass, prefixManager ),
                ind );
    }

    public OWLObjectPropertyAssertionAxiom assertObjectProperty( String property, OWLNamedIndividual src, OWLNamedIndividual tgt ) {
        return factory.getOWLObjectPropertyAssertionAxiom(
                factory.getOWLObjectProperty( property, prefixManager ),
                src,
                tgt );
    }

    public void assertNullSafeDataProperty( KnowledgeHelper drools, String property, OWLNamedIndividual src, String tgt, String type ) {
        if ( ! "null".equals( tgt ) ) {
            if ( type.startsWith( "xsd:" ) ) {
                // hack: using the short form
                type = IRI.create( "http://www.w3.org/2001/XMLSchema#" + type.substring( 4 ) ).toString();
            } else {
                System.out.println( "Unkokwn type");
            }
            drools.insert( assertTypedDataProperty( property, src, tgt, type ) );
        }
    }

    public OWLDataPropertyAssertionAxiom assertTypedDataProperty( String property, OWLNamedIndividual src, String tgt, String type ) {
        return factory.getOWLDataPropertyAssertionAxiom(
                factory.getOWLDataProperty( property, prefixManager ),
                src,
                factory.getOWLTypedLiteral( tgt, new OWLDatatypeImpl( IRI.create( type ) ) ) );
    }

    public OWLDataPropertyAssertionAxiom assertDataProperty( String property, OWLNamedIndividual src, String tgt ) {
        return factory.getOWLDataPropertyAssertionAxiom(
                factory.getOWLDataProperty( property, prefixManager ),
                src,
                factory.getOWLLiteral( tgt ) );
    }

    public void assertCD( KnowledgeHelper kh, String property, OWLNamedIndividual src, Object code ) {
        OWLNamedIndividual cd = asIndividual( code );
        kh.insert( assertObjectProperty( property, src, cd ) );
        kh.insert( assertType( cd, "skos-ext:ConceptCode" ) );

        String codeVal = extractStringProperty( "getCode", code );
        if ( codeVal != null ) {
            kh.insert( assertDataProperty( "skos-ext:code", cd, codeVal ) );
        }
        String codeSystem = extractStringProperty( "getCodeSystem", code );
        if ( codeSystem != null ) {
            kh.insert( assertDataProperty( "skos-ext:codeSystem", cd, codeSystem ) );
        }
        String codeSystemName = extractStringProperty( "getCodeSystemName", code );
        if ( codeSystemName != null ) {
            kh.insert( assertDataProperty( "skos-ext:codeSystemName", cd, codeSystemName ) );
        }
        String text = extractStringProperty( "getOriginalText", code );
        if ( text != null ) {
            kh.insert( assertDataProperty( "skos-ext:label", cd, text ) );
        }
    }

    private String extractStringProperty( String propName, Object code ) {
        try {
            return (String) code.getClass().getMethod( propName ).invoke( code );
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            e.printStackTrace();
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public OWLNamedIndividual assertExpression( KnowledgeHelper kh, String op, Object root ) {
        OWLNamedIndividual expr = asExpressionIndividual( op, root );
        OWLNamedIndividual opCode = asIndividual( "ops:Op_" + op );
        kh.insert( assertObjectProperty( "ops:opCode", expr, opCode ) );
        kh.insert( assertDataProperty( "skos-ext:code", opCode, op ) );
            String actualOp = mapOperation( op );
            if ( actualOp != null ) {
                kh.insert( assertType( expr, determineNamespace( actualOp ) + actualOp + "Expression" ) );
            }
        return expr;
    }

    private String mapOperation( String op ) {
        return op;
    }
   

    private String determineNamespace( String op ) {
        String klass = op + "Expression";
        try {
            Class.forName( AndExpression.class.getPackage().getName() + "." + klass );
            return "a:";
        } catch ( ClassNotFoundException e ) {
            return "ops:";
        }
    }

    public OWLNamedIndividual assertPropertyChain( KnowledgeHelper kh, String path, OWLNamedIndividual srcVar, String modelNS ) {
        StringTokenizer tok = new StringTokenizer( path, "." );
        List<String> chain = new ArrayList<String>( tok.countTokens() );
        while ( tok.hasMoreTokens() ) {
            chain.add( tok.nextToken() );
        }
        Collections.reverse( chain );

        OWLNamedIndividual current = srcVar;
        for ( int j = 0; j < chain.size(); j++ ) {
            String prop = chain.get( j );
            OWLNamedIndividual propCode = asIndividual( modelNS + prop );
            kh.insert( assertObjectProperty( "ops:propCode", current, propCode ) );
            kh.insert( assertDataProperty( "skos-ext:code", propCode, modelNS + prop ) );
            kh.insert( assertType( current, "ops:DomainPropertyExpression" ) );

            if ( j != chain.size() -1 ) {
                OWLNamedIndividual expr = asIndividual( "tns:PropertyExpr_" + System.identityHashCode( prop ) );
                kh.insert( assertObjectProperty( "ops:source", current, expr ) );
                current = expr;
            }
        }
        return current;
    }


    public void buildOntology( Set set ) {
        for ( Object ax : set ) {
            manager.addAxiom( ontology, (OWLAxiom) ax );
        }
    }

}
