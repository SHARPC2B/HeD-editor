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
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.test.XSD2OWL;
import org.w3.x2001.xmlschema.Schema;
import uk.ac.manchester.cs.owl.owlapi.OWL2DatatypeImpl;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class TestSimple {

    @Test
    public void testSimpleType() {

        try {
            XSD2OWL converter = new XSD2OWL();

            Schema x = converter.parse( "test/simple.xsd" );

            OWLOntology onto = converter.transform( x, true, true );

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            assertTrue( onto.containsAxiom( factory.getOWLDatatypeDefinitionAxiom(
                    factory.getOWLDatatype( IRI.create( "http://asu.edu/test#Decimal" ) ),
                    OWL2DatatypeImpl.getDatatype( OWL2Datatype.XSD_DOUBLE ) )
            ) );

        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }


    @Test
    @Ignore( "Hermit may have an issue with enumerated datatypes in more complex schemas..., generation has been temporarily disabled" )
    public void testSimpleEnum() {

        try {
            XSD2OWL converter = new XSD2OWL();

            Schema x = converter.parse( "test/simple.xsd" );

            OWLOntology onto = converter.transform( x, true, true );

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            assertTrue( onto.containsAxiom( factory.getOWLDatatypeDefinitionAxiom(
                    factory.getOWLDatatype( IRI.create( "http://asu.edu/test#Enumerated" ) ),
                    factory.getOWLDataOneOf( factory.getOWLLiteral( "A", OWL2Datatype.XSD_STRING ),
                                            factory.getOWLLiteral( "B", OWL2Datatype.XSD_STRING ),
                                            factory.getOWLLiteral( "C", OWL2Datatype.XSD_STRING )
                    ) )
            ) );

        } catch ( Exception e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
