package edu.asu.sharpc2b;

import org.drools.spi.KnowledgeHelper;
import org.hl7.v3.hed.CD;
import org.hl7.v3.hed.KnowledgeDocument;
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

import javax.xml.namespace.QName;
import java.util.Collection;
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
        if ( x instanceof KnowledgeDocument ) {
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

    public OWLDataPropertyAssertionAxiom assertDataProperty( String property, OWLNamedIndividual src, String tgt ) {
        return factory.getOWLDataPropertyAssertionAxiom(
                factory.getOWLDataProperty( property, prefixManager ),
                src,
                factory.getOWLLiteral( tgt ) );
    }

    public void assertCD( KnowledgeHelper kh, String property, OWLNamedIndividual src, CD code ) {
        OWLNamedIndividual cd = asIndividual( code );
        kh.insert( assertObjectProperty( property, src, cd ) );
        kh.insert( assertType( cd, "skos-ext:ConceptCode" ) );
        if ( code.getCode() != null ) {
            kh.insert( assertDataProperty( "skos-ext:code", cd, code.getCode() ) );
        }
        if ( code.getCodeSystem() != null ) {
            kh.insert( assertDataProperty( "skos-ext:codeSystem", cd, code.getCodeSystem() ) );
        }
        if ( code.getOriginalText() != null ) {
            kh.insert( assertDataProperty( "skos-ext:label", cd, code.getOriginalText() ) );
        }
    }

    public OWLNamedIndividual assertExpression( KnowledgeHelper kh, String op, Object link ) {
        OWLNamedIndividual expr = asIndividual( "tns:Expr_" + op + "_" + System.identityHashCode( link ) );
        OWLNamedIndividual opCode = asIndividual( "ops:Op_" + op );
        kh.insert( assertObjectProperty( "ops:opCode", expr, opCode ) );
        kh.insert( assertDataProperty( "skos-ext:code", opCode, op ) );
        return expr;
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
            kh.insert( assertObjectProperty( "ops:source", expr, src ) );
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
