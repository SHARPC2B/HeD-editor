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

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.test.Jaxplorer;
import org.test.XSD2OWL;
import org.w3.x2001.xmlschema.Schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TestXSD {

    @Test
    public void testVMRSchema() {

        try {
            XSD2OWL converter = new XSD2OWL();

            Schema x = converter.parse( "/home/davide/Projects/xsd/src/test/resources/vmr.xsd" );

            OWLOntology onto = converter.transform( x, true, true );

            File folder = new File( "/home/davide/Projects/xsd/target/generated-sources/owl/" );
            FileOutputStream fos = new FileOutputStream( folder.getPath() + "/output.owl");
            if ( ! folder.exists() ) {
                folder.mkdirs();
            }
            OWLManager.createOWLOntologyManager().saveOntology(
                    onto,
                    new ManchesterOWLSyntaxOntologyFormat(),
                    fos);
//            OWLManager.createOWLOntologyManager().saveOntology(
//                    onto,
//                    new ManchesterOWLSyntaxOntologyFormat(),
//                    System.out );


        }

        catch (OWLOntologyStorageException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }



    @Test
    public void testExplorer() {

        XSD2OWL converter = new XSD2OWL();

        Schema x = converter.parse( "/home/davide/Projects/xsd/src/test/resources/vmr.xsd" );


    }


}
