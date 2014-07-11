/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.query;

import java.util.List;

import com.mycompany.app.business.graph.Graph;

/**
 * Represents interface for implementing query runner on graph
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface GraphQuery {
	public void initialize(Graph graph) throws Exception;
	public double findShortestPathWeight(String from, String to ) throws Exception ;				
	public List<?> findShortestPath(String from, String to ) throws Exception;

}