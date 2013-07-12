package org.ihc.hwcir.hierarchy;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
	
	private String codeSystem;
	
	private String name;
	
	private String versionId;
	
	private String artifactId;
	
	private String title;
	
	private String status;
	
	private String description;
	
	private String contributor;
	
	private String prePublished;
	
	private String adaptedFrom;
	
	private String documentation;
	
	private String coverageType;
	
	private String supportingResource;
	
	private String realizesInformation;
	
	private String isA;
	
	private String label;
	
	private String code;
	
	private String rightsHolder;
	
	private String variableFilterExpression;
	
	private String productionRuleBoundRuleVariable;
	
	private String associatedResource;
	
	private String publisher;
	
	private String conditionRepresentation;
	
	private String memberAction;
	
	private String identifier;
	
	private String _id;
	
	private String _type;
	
	private List<Vertice> children;

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getPrePublished() {
        return prePublished;
    }

    public void setPrePublished(String prePublished) {
        this.prePublished = prePublished;
    }

    public String getAdaptedFrom() {
        return adaptedFrom;
    }

    public void setAdaptedFrom(String adaptedFrom) {
        this.adaptedFrom = adaptedFrom;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }

    public String getSupportingResource() {
        return supportingResource;
    }

    public void setSupportingResource(String supportingResource) {
        this.supportingResource = supportingResource;
    }

    public String getRealizesInformation() {
        return realizesInformation;
    }

    public void setRealizesInformation(String realizesInformation) {
        this.realizesInformation = realizesInformation;
    }

    public String getIsA() {
        return isA;
    }

    public void setIsA(String isA) {
        this.isA = isA;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRightsHolder() {
        return rightsHolder;
    }

    public void setRightsHolder(String rightsHolder) {
        this.rightsHolder = rightsHolder;
    }

    public String getVariableFilterExpression() {
        return variableFilterExpression;
    }

    public void setVariableFilterExpression(String variableFilterExpression) {
        this.variableFilterExpression = variableFilterExpression;
    }

    public String getProductionRuleBoundRuleVariable() {
        return productionRuleBoundRuleVariable;
    }

    public void setProductionRuleBoundRuleVariable(String productionRuleBoundRuleVariable) {
        this.productionRuleBoundRuleVariable = productionRuleBoundRuleVariable;
    }

    public String getAssociatedResource() {
        return associatedResource;
    }

    public void setAssociatedResource(String associatedResource) {
        this.associatedResource = associatedResource;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getConditionRepresentation() {
        return conditionRepresentation;
    }

    public void setConditionRepresentation(String conditionRepresentation) {
        this.conditionRepresentation = conditionRepresentation;
    }

    public String getMemberAction() {
        return memberAction;
    }

    public void setMemberAction(String memberAction) {
        this.memberAction = memberAction;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public List<Vertice> getChildren() {
        return children;
    }

    public void addChildren(Vertice vertice) {
        if (children == null)
            children = new ArrayList<>();
       this.children.add(vertice);
    }

    @Override
	public String toString() {
		return this.name;
	}

}
