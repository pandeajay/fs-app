package com.mycompany.app.business.elements;

public interface Edge {	
	public String getId();
	public String getStartVetex(String edgeId);
	public String getEndVetex(String edgeId);
	public String getWeight(String edgeId);

}