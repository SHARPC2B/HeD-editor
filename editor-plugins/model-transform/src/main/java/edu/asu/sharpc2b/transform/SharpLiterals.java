package edu.asu.sharpc2b.transform;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SharpLiterals {

    private static Map<String, String> primitiveTypeMap = new HashMap<>();

    {
        initPrimitiveTypeMap();
    }


    public void addSharpLiteralExpressions( OWLOntology hedOntology, OWLOntology operatorOntology ) {

        for ( OWLClass klass : hedOntology.getClassesInSignature() ) {
            if ( klass.getIRI().getFragment().contains( "Literal" ) ) {
                processLiteralExpression( klass, hedOntology, operatorOntology );
            }
        }

    }

    private void processLiteralExpression( OWLClass klass, OWLOntology hedOntology, OWLOntology operatorOntology ) {
        OWLDataFactory odf = operatorOntology.getOWLOntologyManager().getOWLDataFactory();

        OWLClass exprClass = odf.getOWLClass( IriUtil.opsIRI( klass.getIRI().getFragment() ) );
        OWLClass superClass = odf.getOWLClass( IriUtil.opsIRI( "PrimitiveExpression" ) );

        addAxiom( operatorOntology,
                  odf.getOWLDeclarationAxiom( exprClass ) );
        addAxiom( operatorOntology,
                  odf.getOWLSubClassOfAxiom( exprClass, superClass ) );


        Set<OWLClassAxiom> axioms = hedOntology.getAxioms( klass );
        for ( OWLClassAxiom axiom : axioms ) {
            if ( axiom instanceof OWLSubClassOfAxiom ) {
                OWLSubClassOfAxiom subClassOfAxiom = (OWLSubClassOfAxiom) axiom;
                OWLClassExpression supr = subClassOfAxiom.getSuperClass();
                if ( supr instanceof OWLObjectIntersectionOf ) {
                    OWLObjectIntersectionOf and = (OWLObjectIntersectionOf) supr;
                    for ( OWLClassExpression kx : and.getOperands() ) {
                        getLiteralParameterFromAxiom( kx, exprClass, operatorOntology, odf );
                    }
                }
            }
        }
    }

    private void getLiteralParameterFromAxiom( OWLClassExpression kx, OWLClass exprClass, OWLOntology operatorOntology, OWLDataFactory odf ) {
        IRI propIRI;
        IRI propType;

        if ( kx instanceof OWLDataAllValuesFrom ) {
            OWLDataAllValuesFrom dataAll = (OWLDataAllValuesFrom) kx;
            propIRI = ( (OWLDataAllValuesFrom) kx ).getProperty().asOWLDataProperty().getIRI();
            propType = dataAll.getFiller().asOWLDatatype().getIRI();
            addParameter( exprClass, propIRI, propType, operatorOntology, odf );
        } else if ( kx instanceof OWLObjectAllValuesFrom ) {
            OWLObjectAllValuesFrom objAll = (OWLObjectAllValuesFrom) kx;
            propIRI = ( (OWLObjectAllValuesFrom) kx ).getProperty().asOWLObjectProperty().getIRI();
            propType = objAll.getFiller().asOWLClass().getIRI();
            addParameter( exprClass, propIRI, propType, operatorOntology, odf );
        } else if ( kx instanceof OWLObjectIntersectionOf ) {
            for ( OWLClassExpression inner : ( (OWLObjectIntersectionOf) kx ).getOperands() ) {
                if ( inner instanceof OWLClassExpression ) {
                    getLiteralParameterFromAxiom( inner, exprClass, operatorOntology, odf );
                }
            }
        }

    }

    private void addParameter( OWLClass exprClass, IRI propIRI, IRI propType, OWLOntology operatorOntology, OWLDataFactory odf ) {
        OWLClassExpression parameter = odf.getOWLObjectIntersectionOf(
                odf.getOWLClass( IriUtil.opsIRI( "Attribute" ) ),
                odf.getOWLDataHasValue( odf.getOWLDataProperty( IriUtil.opsIRI( "attributeName" ) ),
                                        odf.getOWLLiteral( propIRI.getFragment() ) ),
                odf.getOWLDataHasValue( odf.getOWLDataProperty( IriUtil.opsIRI( "attributeType" ) ),
                                        odf.getOWLLiteral( adaptType( propType ) ) )
        );
        OWLObjectSomeValuesFrom hasParameter = odf.getOWLObjectSomeValuesFrom(
                odf.getOWLObjectProperty( IriUtil.opsIRI( "hasAttribute" ) ),
                parameter );
        addAxiom( operatorOntology, odf.getOWLSubClassOfAxiom( exprClass, hasParameter ) );

    }

    private String adaptType( IRI propType ) {

        if ( "http://www.w3.org/2001/XMLSchema#".equals( propType.getStart() ) ) {
            return "xsd:" + propType.getFragment();
        } else {
            String fragment = propType.getFragment();
            String chosenType = primitiveTypeMap.get( fragment );
            if ( chosenType != null && chosenType.startsWith( "xsd" ) ) {
                return chosenType;
            } else {
                System.out.println( "TODO Map primitive type " + propType );
                return "xsd:string";
            }
        }

    }

    private void addAxiom( OWLOntology operatorOntology, OWLAxiom axiom ) {
        operatorOntology.getOWLOntologyManager().addAxiom( operatorOntology, axiom );
    }



    private  static void initPrimitiveTypeMap ()  {
        primitiveTypeMap.put( "Uid", "xsd:string" );
        primitiveTypeMap.put( "Decimal", "xsd:double" );
        primitiveTypeMap.put( "Code", "xsd:string" );
        primitiveTypeMap.put( "TimeStamp", "xsd:dateTimeStamp" );
        primitiveTypeMap.put( "Literal", "xsd:string" );
        primitiveTypeMap.put( "CalendarCycle", "xsd:string" );
    }

}