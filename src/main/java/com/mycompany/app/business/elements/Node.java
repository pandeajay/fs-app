package com.mycompany.app.business.elements;

import java.util.Map;

public interface Node {
	
	/**
	 * Retrievs node id
	 * @return
	 */
	public String fetchNodeId();
	
	/**
	 * Get edges of a this node
	 * @return
	 */
	public Map<String,String> fetchEdges();

}
