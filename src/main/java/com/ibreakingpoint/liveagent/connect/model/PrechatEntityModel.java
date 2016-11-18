package com.ibreakingpoint.liveagent.connect.model;

import java.util.List;
import java.util.Map;

public class PrechatEntityModel {
	private String entityName;       
    private String saveToTranscript;
    private String linkToEntityName;
    private String linkToEntityField;
    private List<Map<String,Object>> entityFieldsMaps;
    
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getSaveToTranscript() {
		return saveToTranscript;
	}
	public void setSaveToTranscript(String saveToTranscript) {
		this.saveToTranscript = saveToTranscript;
	}
	public String getLinkToEntityName() {
		return linkToEntityName;
	}
	public void setLinkToEntityName(String linkToEntityName) {
		this.linkToEntityName = linkToEntityName;
	}
	public String getLinkToEntityField() {
		return linkToEntityField;
	}
	public void setLinkToEntityField(String linkToEntityField) {
		this.linkToEntityField = linkToEntityField;
	}
	public List<Map<String, Object>> getEntityFieldsMaps() {
		return entityFieldsMaps;
	}
	public void setEntityFieldsMaps(List<Map<String, Object>> entityFieldsMaps) {
		this.entityFieldsMaps = entityFieldsMaps;
	}
    
    
}
