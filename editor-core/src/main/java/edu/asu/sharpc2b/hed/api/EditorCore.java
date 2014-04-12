package edu.asu.sharpc2b.hed.api;


import edu.asu.sharpc2b.hed.impl.HeDArtifactData;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;

public interface EditorCore extends ExpressionSource, DomainModel, TemplateStore, ArtifactStore {

    public HeDKnowledgeDocument getArtifact( String docId );

    public HeDArtifactData getArtifactData( String docId );

    public HeDArtifactData getCurrentArtifactData();

    void updateBasicInfo( String ruleId, String name, String description, String status, String type );
}
