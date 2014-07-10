package com.mycompany.app.business.impl;

import java.util.List;

import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.business.query.GraphQuery;


public class GraphQueryImpl implements GraphQuery {
	private Graph graph = null;
	


	@Override
	public double findShortestPathWeight(String from, String to) {
		return this.graph.fetchShortestPathWeight(from, to);
	}

	@Override
	public List<?> findShortestPathVertices(String from, String to) {
		return this.graph.getShortestPathVetices(from, to);
	}



	@Override
	public void initialize(Graph graph) throws Exception {
		if(this.graph != null){
			throw new Exception("GraphQueryImpl is already initialized");
		}
		this.graph = graph;	
		
	}





}