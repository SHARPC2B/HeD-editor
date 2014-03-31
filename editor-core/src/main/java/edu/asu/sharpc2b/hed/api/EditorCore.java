package edu.asu.sharpc2b.hed.api;


import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;

public interface EditorCore extends ExpressionSource, DomainModel, TemplateStore, ArtifactStore {

    public HeDKnowledgeDocument getArtifact( String docId );

}
