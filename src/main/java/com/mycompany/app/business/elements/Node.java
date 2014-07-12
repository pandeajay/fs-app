/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.elements;

import java.util.Map;

/**
 * Represents interface for a node.
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface Node {
	
	/**
	 * Retrieves node id
	 * @return
	 */
	public String nodeId();
	
	/**
	 * Get edges of a this node
	 * @return
	 */
	public Map<String,String> edges();

}
