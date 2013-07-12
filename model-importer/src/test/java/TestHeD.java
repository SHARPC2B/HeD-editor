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

//import org.coode.owlapi.turtle.TurtleOntologyFormat;
//import org.drools.shapes.xsd.Xsd2Owl;
//import org.drools.shapes.xsd.Xsd2OwlImpl;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.semanticweb.owlapi.model.OWLOntology;
//import org.w3._2001.xmlschema.Schema;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Properties;

import edu.asu.sharpc2b.HeD2OwlDumper;
import org.junit.Test;

public class TestHeD {

    private static String targetFolder =  null;

    @Test
    public void testLoadHeD() {

        try {
            HeD2OwlDumper dumper = new HeD2OwlDumper();

            dumper.compile( "org/hl7/v3/hed/newMentor_HeD_NQF0068_ECArule_example.xml",
                    "/home/davide/Projects/Git/sharp-editor/model-importer/target/generated-sources/org/hl7/v3/hed/newMentor.owl" );

        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}
