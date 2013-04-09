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

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.drools.shapes.xsd.Xsd2Owl;
import org.drools.shapes.xsd.Xsd2OwlImpl;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.w3._2001.xmlschema.Schema;

import java.io.FileOutputStream;
import java.net.URL;

public class TestXSD {

    @Test
    public void testVMRSchema() {

        try {
            Xsd2Owl converter = Xsd2OwlImpl.getInstance();

            URL url = converter.getSchemaURL( "xsd/vmr/vmr-clean.xsd" );
            Schema x = converter.parse( url );

            OWLOntology onto = converter.transform( x, url, true, false );

            converter.stream( onto,
                    new FileOutputStream( "/home/davide/Desktop/vmr.turtle.owl" ) ,
                    new TurtleOntologyFormat()
            );

        } catch ( Exception e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Test
    public void testDatatypeSchema() {

        try {
            Xsd2Owl converter = Xsd2OwlImpl.getInstance();

            URL url = converter.getSchemaURL( "xsd/vmr/datatypes-clean.xsd" );
            Schema x = converter.parse( url );

            OWLOntology onto = converter.transform( x, url, true, false );

            converter.stream( onto,
                    new FileOutputStream( "/home/davide/Desktop/datatypes.turtle.owl" ) ,
                    new TurtleOntologyFormat()
            );

        } catch ( Exception e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    @Test
    public void testArdenSchema() {

        try {
            Xsd2Owl converter = Xsd2OwlImpl.getInstance();

            URL url = converter.getSchemaURL( "xsd/arden/Arden.xsd" );
            Schema x = converter.parse( url );

            OWLOntology onto = converter.transform( x, url, true, false );

            converter.stream( onto,
                    new FileOutputStream( "/home/davide/Desktop/arden.turtle.owl" ) ,
                    new TurtleOntologyFormat()
            );

        } catch ( Exception e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    @Test
    public void testHeDSchema() {

        try {
            Xsd2Owl converter = Xsd2OwlImpl.getInstance();

            URL url = converter.getSchemaURL( "xsd/HeD/knowledgedocument.xsd" );
            Schema x = converter.parse( url );

            OWLOntology onto = converter.transform( x, url, true, false );

            converter.stream( onto,
                    new FileOutputStream( "/home/davide/Desktop/hed.turtle.owl" ) ,
                    new TurtleOntologyFormat()
            );

        } catch ( Exception e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }



    @Test
    public void testCleanup() {

        try {
            String clean = Xsd2OwlImpl.getInstance().compactXMLSchema( "vmr.xsd" );
            System.out.println( clean );
        } catch ( Exception e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


}
