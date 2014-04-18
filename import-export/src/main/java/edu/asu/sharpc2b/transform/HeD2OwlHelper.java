package edu.asu.sharpc2b.transform;

import edu.asu.sharpc2b.OwlHelper;
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

public class HeD2OwlHelper extends OwlHelper {

    private OWLOntology ontology;
    private OWLOntologyManager manager;
    private OWLDataFactory factory;
    private PrefixManager prefixManager;

    public HeD2OwlHelper( OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory, PrefixManager prefixManager ) {
        super( ontology, manager, factory, prefixManager );

        OWLImportsDeclaration imprt = manager.getOWLDataFactory().getOWLImportsDeclaration( IRI.create( "http://asu.edu/sharpc2b/sharp" ) );
        manager.applyChange( new AddImport( ontology, imprt ) );
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



}
