/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.graph;

import java.util.List;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.elements.ShortestPathAndWeight;
import com.mycompany.app.business.exception.AppException;

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
	void addNode(Node node) throws AppException;
	
	/**
	 * Deletes specified node
	 * @param nodeId
	 * @return
	 */
	void deleteNode(String nodeId) throws AppException;
	
	/**
	 * Add nodes
	 * @param nodes
	 */
	void addNodes(List<Node> nodes) throws AppException;
	
	/**
	 * Creates edges for specified nodes
	 * @param nodes
	 */
	void addEdges(List<Node> nodes) throws AppException;
	
	/**
	 * Creates edge for specified node
	 * @param node
	 * @return
	 */
	void addEdge(Node node) throws AppException;
	
	/**
	 * Deletes specified nodes
	 * @param node
	 */
	void deleteNodes(List<Node> node) throws AppException;
	
	/**
	 * Retrieves weight of shortest path
	 * @param from
	 * @param to
	 * @return
	 */
	ShortestPathAndWeight shortestPathFromImplementation(String from , String to) throws AppException;	
	
	
	/**
	 * Delete graph
	 */
	void close() throws AppException;
	

	
	/**
	 * Deletes all nodes
	 * @return
	 */
	void deleteAllNodes() throws AppException;
	
	/**
	 * deletes all edges
	 * @return
	 */
	void deleteAllEdges() throws AppException;
}
