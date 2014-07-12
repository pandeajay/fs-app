/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.mycompany.app.graphs.impl.Jgraph;

import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.business.query.GraphQuery;
import com.mycompany.app.codes.ErrorCodes;

/**
 * Represents class for running queries on graph.
 *
 * @author  
 * @see
 * @since 1.0
 */

public class GraphQueryImpl implements GraphQuery {
	private Graph graph;

	public GraphQueryImpl(Graph graph){
		this.graph = graph;
	}
	
	
	@Override
	public double shortestPathWeight(String from, String to) throws Exception {
		if(this.graph == null){
			throw new Exception (ErrorCodes.ERROR_NON_INITIALIZED_GRAPH_FOR_QUERY);
		}
		return this.graph.fetchShortestPathWeight(from, to);
	}

	@Override
	public List<?> shortestPath(String from, String to)throws Exception {
		if(this.graph == null){
			throw new Exception (ErrorCodes.ERROR_NON_INITIALIZED_GRAPH_FOR_QUERY);
		}	
		
		return this.graph.getShortestPathVetices(from, to);
	}


}