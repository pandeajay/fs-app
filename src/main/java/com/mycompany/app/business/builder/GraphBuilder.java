/*
 * Copyright ...
 *
 */
package com.mycompany.app.business.builder;

import java.util.List;
import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.graph.Graph;


/**
 * Represents interface that any graph builder should implement
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface GraphBuilder {
	/**
	 * build graph with specified path for nodes
	 * @param dataNodesPath
	 * @throws Exception
	 */
	void buildGraph(String dataNodesPath) throws Exception ;
	
	/**
	 * builds graph with list of nodes and type of graph which may e JGraph or NeoGraph
	 * @param nodes
	 * @param graphType
	 * @throws Exception
	 */
	void buildGraph(List<Node> nodes, String graphType) throws Exception;	
	
	/**
	 * Creates edges of passed node
	 * @param node
	 */
	void createEdgesForNode(Node node );
	
	/**
	 * Add passed node
	 * @param node
	 */
	void addNode(Node node);
	
	
	/**
	 * Delete specified node
	 * @param nodeId
	 */
	void deleteNode(String nodeId );
	
	/**
	 * Get reference to underline graph
	 * @return
	 */
	Graph getGraph();
	
	/**
	 * Delete graph
	 */
	void close();
}
