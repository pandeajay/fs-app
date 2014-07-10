/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.query;

import java.util.List;

/**
 * Represents interface for implementing query runner on graph
 *
 * @author  
 * @see
 * @since 1.0
 */

import com.mycompany.app.business.graph.Graph;

public interface GraphQuery {
	public void initialize(Graph graph) throws Exception;
	public double findShortestPathWeight(String from, String to );				
	public List<?> findShortestPathVertices(String from, String to );

}