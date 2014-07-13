/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.query;

import java.util.List;

import com.mycompany.app.business.elements.ShortestPathAndWeight;
import com.mycompany.app.business.exception.AppException;
import com.mycompany.app.business.graph.Graph;

/**
 * Represents interface for implementing query runner on graph
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface GraphQueryService {				
	public ShortestPathAndWeight shortestPath(String from, String to ) throws AppException;

}