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

import java.lang.reflect.InvocationTargetException;
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

    public void assertNullSafeDataProperty( KnowledgeHelper drools, String property, OWLNamedIndividual src, String tgt ) {
        if ( ! "null".equals( tgt ) ) {
            drools.insert( assertDataProperty( property, src, tgt ) );
        }
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

        String codeVal = getCode( code );
        if ( codeVal != null ) {
            kh.insert( assertDataProperty( "skos-ext:code", cd, codeVal ) );
        }
        String codeSystem = getCodeSystem( code );
        if ( codeSystem != null ) {
            kh.insert( assertDataProperty( "skos-ext:codeSystem", cd, codeSystem ) );
        }
        String text = getOriginalText( code );
        if ( text != null ) {
            kh.insert( assertDataProperty( "skos-ext:label", cd, text ) );
        }
    }

    private String getCode( Object code ) {
        try {
            return (String) code.getClass().getMethod( "getCode" ).invoke( code );
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            e.printStackTrace();
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCodeSystem( Object code ) {
        try {
            return (String) code.getClass().getMethod( "getCodeSystem" ).invoke( code );
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            e.printStackTrace();
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private String getOriginalText( Object code ) {
        try {
            return (String) code.getClass().getMethod( "getLabel" ).invoke( code );
        } catch ( IllegalAccessException e ) {
            //e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            //e.printStackTrace();
        } catch ( NoSuchMethodException e ) {
            //e.printStackTrace();
        }
        return null;
    }

    public OWLNamedIndividual assertExpression( KnowledgeHelper kh, String op, Object root ) {
        OWLNamedIndividual expr = asExpressionIndividual( op, root );
        OWLNamedIndividual opCode = asIndividual( "ops:Op_" + op );
        kh.insert( assertObjectProperty( "ops:opCode", expr, opCode ) );
        kh.insert( assertDataProperty( "skos-ext:code", opCode, op ) );
        if ( ! op.endsWith( "Literal" ) ) {
            String actualOp = mapOperation( op );
            if ( actualOp != null ) {
                kh.insert( assertType( expr, determineNamespace( actualOp ) + actualOp + "Expression" ) );
            }
        } else {
            kh.insert( assertType( expr, "ops:" + op ) );
        }
        return expr;
    }

    private String mapOperation( String op ) {
        if ( "Property".equals( op ) ) {
            return "PropertyGet";
        } else if ( "ClinicalRequest".equals( op ) ) {
            // actually mapped as a specific type of IteratorExpression elsewhere
            return null;
        } else if ( "ObjectExpression".equals( op ) ) {
            return "Create";
        }
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
        OWLNamedIndividual src = srcVar;
        StringTokenizer tok = new StringTokenizer( path, "." );

        while ( tok.hasMoreTokens() ) {
            String prop = tok.nextToken();
            OWLNamedIndividual expr = asIndividual( "tns:PropertyExpr_" + System.identityHashCode( prop ) );
            OWLNamedIndividual propCode = asIndividual( modelNS + prop );
            kh.insert( assertObjectProperty( "ops:propCode", expr, propCode ) );
            kh.insert( assertDataProperty( "skos-ext:code", propCode, modelNS + prop ) );
            if ( src != null ) {
                //TODO not sure that src can be null in a valid case
                kh.insert( assertObjectProperty( "ops:source", expr, src ) );
            }
            kh.insert( assertType( expr, "ops:PropertyExpression" ) );
            src = expr;
        }
        return src;
    }


    public void buildOntology( Set set ) {
        for ( Object ax : set ) {
            manager.addAxiom( ontology, (OWLAxiom) ax );
        }
    }

}
