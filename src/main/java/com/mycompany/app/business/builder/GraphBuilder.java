/*
 * Copyright ...
 *
 */
package com.mycompany.app.business.builder;

import java.util.List;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.exception.AppException;
import com.mycompany.app.business.graph.Graph;


/**
 * Represents interface that graph builder should implement to build and manager the graph.
 * 
 * This has capability to initialize and builds the graph based on 
 * on template path provided by user or nodes and graph style option 
 * provided by user.
 * 
 * It also provides capacity to add node, creates edges for a node, delete node and fetch underlying graph.
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface GraphBuilder {
	/**
	 * build graph with specified path for nodes and user preferences
	 * @param dataNodesPath
	 * @throws Exception
	 */
	Graph buildGraphFromPreferenceFile(String dataNodesPath) throws AppException ;
	
	/**
	 * builds graph with list of nodes and type of graph which may e JGraph or NeoGraph
	 * @param nodes
	 * @param graphType
	 * @throws Exception
	 */
	Graph buildGraphFromNodesAndType(List<Node> nodes, String graphType) throws AppException;	
	
	
	/**
	 * Delete graph
	 * @throws AppException 
	 */
	void deleteAll() throws AppException;
}
