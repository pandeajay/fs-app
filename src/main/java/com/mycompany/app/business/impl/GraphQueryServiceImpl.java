/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.impl;

import com.mycompany.app.business.elements.ShortestPathAndWeight;
import com.mycompany.app.business.exception.AppException;
import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.business.query.GraphQueryService;
import com.mycompany.app.codes.ErrorCodes;

/**
 * Represents class for running queries on graph.
 *
 * @author  
 * @see
 * @since 1.0
 */

public class GraphQueryServiceImpl implements GraphQueryService {
	private Graph graph;

	public GraphQueryServiceImpl(Graph graph){
		this.graph = graph;
	}
	
	/**
	 * shortest path and weight  for from-to pair
	 */
	@Override
	public ShortestPathAndWeight shortestPath(String from, String to)throws AppException {
		try{
			if(this.graph == null){
				throw new AppException (ErrorCodes.ERROR_NON_INITIALIZED_GRAPH_FOR_QUERY);
			}			
			return this.graph.shortestPathFromImplementation(from, to);
		}catch(Exception ex){
			throw new AppException (ErrorCodes.ERROR_IN_SHORTEST_PATH);
		}
	}
}