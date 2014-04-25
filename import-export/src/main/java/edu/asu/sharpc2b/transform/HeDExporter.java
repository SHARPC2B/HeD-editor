package edu.asu.sharpc2b.transform;

import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import org.semanticweb.owlapi.model.OWLOntology;

public interface HeDExporter {

    public byte[] export( HeDKnowledgeDocument dok, OWLOntology dow );

}
