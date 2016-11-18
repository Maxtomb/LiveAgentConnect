package com.ibreakingpoint.liveagent.connect.model;

import java.util.List;
import java.util.Map;

public class PrechatDetailModel{
	private String label;	
	private String value;
	private String [] transcriptFields;
	private Boolean displayToAgent;
	private List<Map<String,Object>> entityMaps;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String[] getTranscriptFields() {
		return transcriptFields;
	}
	public void setTranscriptFields(String[] transcriptFields) {
		this.transcriptFields = transcriptFields;
	}
	public Boolean getDisplayToAgent() {
		return displayToAgent;
	}
	public void setDisplayToAgent(Boolean displayToAgent) {
		this.displayToAgent = displayToAgent;
	}
	public List<Map<String, Object>> getEntityMaps() {
		return entityMaps;
	}
	public void setEntityMaps(List<Map<String, Object>> entityMaps) {
		this.entityMaps = entityMaps;
	}
	
	
	
}