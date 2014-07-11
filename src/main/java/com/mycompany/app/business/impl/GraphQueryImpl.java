/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.impl;

import java.util.List;

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
	private Graph graph = null;

	@Override
	public double findShortestPathWeight(String from, String to) throws Exception {
		if(this.graph == null){
			throw new Exception (ErrorCodes.ERROR_NON_INITIALIZED_GRAPH_FOR_QUERY);
		}
		return this.graph.fetchShortestPathWeight(from, to);
	}

	@Override
	public List<?> findShortestPath(String from, String to)throws Exception {
		if(this.graph == null){
			throw new Exception (ErrorCodes.ERROR_NON_INITIALIZED_GRAPH_FOR_QUERY);
		}
		return this.graph.getShortestPathVetices(from, to);
	}



	@Override
	public void initialize(Graph graph) throws Exception {
		if(this.graph != null){
			throw new Exception(ErrorCodes.ERROR_QUERYIMPL_ALREADY_INITIALIZED_WITH_GRAPH);
		}
		this.graph = graph;	
		
	}
}