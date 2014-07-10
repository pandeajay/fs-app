/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.graph;

import java.util.List;

import com.mycompany.app.business.elements.Node;

/**
 * Represents interface for a Graph implementation. 
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface Graph {

	/**
	 * Add specified node
	 * @param node
	 * @return
	 */
	long addNode(Node node);
	
	/**
	 * Deletes specified node
	 * @param nodeId
	 * @return
	 */
	long deleteNode(String nodeId);
	
	/**
	 * Add nodes
	 * @param nodes
	 */
	void addNodes(List<Node> nodes);
	
	/**
	 * Creates edges for specified nodes
	 * @param nodes
	 */
	void addEdges(List<Node> nodes);
	
	/**
	 * Creates edge for specified node
	 * @param node
	 * @return
	 */
	long addEdge(Node node);
	
	/**
	 * Deletes specified nodes
	 * @param node
	 */
	void deleteNodes(List<Node> node);
	
	/**
	 * Retrieves weight of shortest path
	 * @param from
	 * @param to
	 * @return
	 */
	double fetchShortestPathWeight(String from , String to);	
	
	/**
	 * Retrieves vertices of shortest path
	 * @param from
	 * @param to
	 * @return
	 */
	List<?> getShortestPathVetices(String from , String to);
	
	/**
	 * Delete graph
	 */
	void close();
	

	
	/**
	 * Deletes all nodes
	 * @return
	 */
	int deleteAllNodes();
	
	/**
	 * deletes all edges
	 * @return
	 */
	int deleteAllEdges();
}
