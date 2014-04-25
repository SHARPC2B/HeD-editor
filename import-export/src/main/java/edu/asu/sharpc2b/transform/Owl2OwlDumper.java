package edu.asu.sharpc2b.transform;

import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.io.ByteArrayOutputStream;

public class Owl2OwlDumper implements HeDExporter {

    public byte[] export( HeDKnowledgeDocument dok, OWLOntology dow ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            dow.getOWLOntologyManager().saveOntology( dow, new ManchesterOWLSyntaxOntologyFormat(), baos );
        } catch ( OWLOntologyStorageException e ) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

}
