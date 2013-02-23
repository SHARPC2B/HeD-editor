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

import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.test.Xsd2Owl;
import org.test.Xsd2OwlImpl;
import org.w3.x2001.xmlschema.Schema;
import uk.ac.manchester.cs.owl.owlapi.OWL2DatatypeImpl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class TestElementRefs {

    private static OWLOntology onto;
    private static OWLOntologyManager manager;
    private static OWLDataFactory factory;
    private static String tns;

    @BeforeClass
    public static void parse() {

        Xsd2Owl converter = Xsd2OwlImpl.getInstance();

        Schema x = converter.parse("test/elementRefs.xsd");
        tns = x.getTargetNamespace() + "#";

        onto = converter.transform( x, true, true );

        manager = OWLManager.createOWLOntologyManager();
        factory = manager.getOWLDataFactory();
    }


    @Test
    public void testElementRefs() {

        try {
            String px = tns;
            OWLClass k = factory.getOWLClass( IRI.create( px, "Left" ) );
            OWLClass l = factory.getOWLClass( IRI.create( px, "Link" ) );
            OWLDatatype string = OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_STRING );

            OWLObjectProperty p1 = factory.getOWLObjectProperty( IRI.create( px, "link" ) );
            OWLDataProperty p2 = factory.getOWLDataProperty( IRI.create( px, "pointer" ) );

            assertTrue( onto.containsAxiom( factory.getOWLDeclarationAxiom( l ) ) );
            assertTrue( onto.containsAxiom( factory.getOWLDeclarationAxiom( k ) ) );
            assertTrue( onto.containsAxiom( factory.getOWLDeclarationAxiom( p1 ) ) );
            assertTrue( onto.containsAxiom( factory.getOWLDeclarationAxiom( p2 ) ) );


            assertTrue( onto.containsAxiom( factory.getOWLObjectPropertyDomainAxiom( p1, k ) ) );
            assertTrue( onto.containsAxiom( factory.getOWLObjectPropertyRangeAxiom( p1, l ) ) );
            assertTrue( onto.containsAxiom( factory.getOWLDataPropertyDomainAxiom( p2, factory.getOWLObjectUnionOf( l, k ) ) ) );
            assertTrue( onto.containsAxiom( factory.getOWLDataPropertyRangeAxiom( p2, string ) ) );

            assertTrue( onto.containsAxiom( factory.getOWLSubClassOfAxiom(
                    l,
                    factory.getOWLObjectIntersectionOf(
                            factory.getOWLObjectIntersectionOf(
                                    factory.getOWLDataAllValuesFrom(
                                            p2,
                                            string ),
                                    factory.getOWLDataMinCardinality(
                                            2,
                                            p2,
                                            string ),
                                    factory.getOWLDataMaxCardinality(
                                            4,
                                            p2,
                                            string )
                            )
                    ))
            ));

            assertTrue( onto.containsAxiom( factory.getOWLSubClassOfAxiom(                    k,
                    factory.getOWLObjectIntersectionOf(
                            factory.getOWLObjectIntersectionOf(
                                    factory.getOWLObjectAllValuesFrom(
                                            p1,
                                            l ),
                                    factory.getOWLObjectMinCardinality(
                                            0,
                                            p1,
                                            l )
                            ),
                            factory.getOWLObjectIntersectionOf(
                                    factory.getOWLDataAllValuesFrom(
                                            p2,
                                            string ),
                                    factory.getOWLDataMinCardinality(
                                            1,
                                            p2,
                                            string ),
                                    factory.getOWLDataMaxCardinality(
                                            1,
                                            p2,
                                            string )
                            )
                    ))
            ));


        } catch ( Exception e ) {
            fail( e.getMessage() );
        }

    }

}
