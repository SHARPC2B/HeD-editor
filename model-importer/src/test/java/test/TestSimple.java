/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.test.XSD2OWL;
import org.w3.x2001.xmlschema.Facet;
import org.w3.x2001.xmlschema.Pattern;
import org.w3.x2001.xmlschema.Schema;
import uk.ac.manchester.cs.owl.owlapi.OWL2DatatypeImpl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class TestSimple {

    @Test
    public void testSimpleType() {

        try {
            XSD2OWL converter = new XSD2OWL();

            Schema x = converter.parse( "test/simple.xsd" );
            String tns = x.getTargetNamespace() + "#";

            OWLOntology onto = converter.transform( x, true, true );

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            assertTrue( onto.containsAxiom( factory.getOWLDatatypeDefinitionAxiom(
                    factory.getOWLDatatype( IRI.create( tns, "Decimal" ) ),
                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DOUBLE ) )
            ) );

        } catch ( Exception e ) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }


    @Test
    @Ignore
    public void testSimpleEnum() {

        try {
            XSD2OWL converter = new XSD2OWL();

            Schema x = converter.parse( "test/simple.xsd" );
            String tns = x.getTargetNamespace() + "#";

            OWLOntology onto = converter.transform( x, true, true );

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            assertTrue( onto.containsAxiom( factory.getOWLDatatypeDefinitionAxiom(
                    factory.getOWLDatatype( IRI.create( tns, "Enumerated" ) ),
                    factory.getOWLDataOneOf( factory.getOWLLiteral( "A", OWL2Datatype.XSD_STRING ),
                            factory.getOWLLiteral( "B", OWL2Datatype.XSD_STRING ),
                            factory.getOWLLiteral( "C", OWL2Datatype.XSD_STRING )
                    ) )
            ) );

        } catch ( Exception e ) {
            fail( e.getMessage() );
        }
    }


    @Test
    public void testSimpleTypeWithRestriction() {

        try {
            XSD2OWL converter = new XSD2OWL();

            Schema x = converter.parse( "test/simple.xsd" );
            String tns = x.getTargetNamespace() + "#";

            OWLOntology onto = converter.transform( x, true, true );

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            OWLClass ts = factory.getOWLClass( IRI.create( tns, "TS" ) );

            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value2Type" ) ),
                            factory.getOWLDatatypeRestriction(
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_STRING ),
                                    OWLFacet.LENGTH,
                                    factory.getOWLLiteral( "2", OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_INT ) ) )
                    )
            ));
            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value2Type" ) ),
                            factory.getOWLDatatypeRestriction(
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_STRING ),
                                    OWLFacet.MAX_LENGTH,
                                    factory.getOWLLiteral( "6", OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_INT ) ) )
                    )
            ));
            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value2Type" ) ),
                            factory.getOWLDatatypeRestriction(
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_STRING ),
                                    OWLFacet.PATTERN,
                                    factory.getOWLLiteral( "[1-2][0-9]" ) )
                    )
            ));


            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value3Type" ) ),
                            factory.getOWLDatatypeRestriction(
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DOUBLE ),
                                    OWLFacet.MIN_INCLUSIVE,
                                    factory.getOWLLiteral( "11.0", OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DOUBLE ) ) )
                    )
            ));
            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value3Type" ) ),
                            factory.getOWLDatatypeRestriction(
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DOUBLE ),
                                    OWLFacet.MAX_EXCLUSIVE,
                                    factory.getOWLLiteral( "13.0", OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DOUBLE ) ) )
                    )
            ));


            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value1Type" ) ),
                            factory.getOWLDataUnionOf(
                                OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_INT ),
                                OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_STRING )
                            )
                    ) )
            );
            assertTrue( onto.containsAxiom(
                    factory.getOWLDatatypeDefinitionAxiom(
                            factory.getOWLDatatype( IRI.create( tns, "value11Type" ) ),
                            factory.getOWLDataUnionOf(
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_ANY_URI ),
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DECIMAL ),
                                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DATE_TIME )
                            )
                    ) )
            );



            assertEquals( 4, onto.getSubClassAxiomsForSubClass( ts ).size() );



        } catch ( Exception e ) {
            fail( e.getMessage() );
        }

    }
}
