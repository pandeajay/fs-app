package com.mycompany.app.business.query;

import java.util.List;

import com.mycompany.app.business.graph.Graph;

public interface GraphQuery {
	public void initialize(Graph graph) throws Exception;
	public double findShortestPathWeight(String from, String to );				
	public List<?> findShortestPathVertices(String from, String to );

}