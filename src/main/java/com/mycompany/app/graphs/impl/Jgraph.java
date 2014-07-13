/*
 * Copyright ...
 *
 */

package com.mycompany.app.graphs.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.elements.ShortestPathAndWeight;
import com.mycompany.app.business.exception.AppException;
import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.codes.ErrorCodes;
import com.mycompany.app.log.LogImpl;
import com.mycompany.app.utilities.Logger;

/**
 * Represents JGraphT based implementation for Graph
 *
 * @author  
 * @see
 * @since 1.0
 */

public class Jgraph implements Graph {
	
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> jGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	private Logger logger = Logger.getLogger(new LogImpl());
		
	/**
	 * For a from and to pair return shortest path weight
	 * input : from and to id
	 * output : weight of shortest path between from and to
	 */
	public ShortestPathAndWeight shortestPathFromImplementation(String from , String to){
		DijkstraShortestPath<String, DefaultWeightedEdge> shortpath = new DijkstraShortestPath<String, DefaultWeightedEdge>(
				this.jGraph, from, to);
		ShortestPathAndWeight shortestPath = new ShortestPathAndWeight(
				getShortestPathVetices(shortpath.getPathEdgeList()),
				shortpath.getPathLength());
		return shortestPath;
	}
	
	private List<String> getShortestPathVetices(List<DefaultWeightedEdge> edgeList){
		List<String> list = new ArrayList<String>();
		Iterator<DefaultWeightedEdge> it = edgeList.iterator();
		while(it.hasNext()){
			DefaultWeightedEdge edge =  it.next();
			if(!list.contains(jGraph.getEdgeSource(edge) )){
				list.add(jGraph.getEdgeSource(edge));
			}
			if(!list.contains(jGraph.getEdgeTarget(edge) )){
				list.add(jGraph.getEdgeTarget(edge));	
			}
		}
		return list;
		
	}

	@Override
	public void close() {
		this.jGraph = null;
		
	}
	/**
	 * Deletes specified node
	 * @throws AppException 
	 */
	@Override
	public void deleteNode(String nodeId) throws AppException {
		try{
			jGraph.removeVertex(nodeId);
		}catch(Exception ex){
			logger.error(ErrorCodes.ERROR_IN_CREATING_EDGE + ex) ;
			throw new AppException(ErrorCodes.ERROR_IN_CREATING_EDGE );
		}
	}



	/**
	 * Deletes all nodes
	 */

	@Override
	public void deleteAllNodes() throws AppException {
		try{
			Set<String> vertexSet = jGraph.vertexSet();
			List<String> vertexList = new ArrayList<String>();
			for( String vertex : vertexSet){
				vertexList.add(vertex);
			}
			jGraph.removeAllVertices(vertexList);
			
		}catch(Exception e){
			logger.error("Exception in deleteAllNodes " + e);			
			throw new AppException("Exception in deleteAllNodes ");
		}		
	}


	/**
	 * Deletes all edges
	 * @throws Exception 
	 */
	@Override
	public void deleteAllEdges() throws AppException {
		try{
			Set<DefaultWeightedEdge> edgeSet = jGraph.edgeSet();
			List<DefaultWeightedEdge> edgeList = new ArrayList<DefaultWeightedEdge>();
			for( DefaultWeightedEdge edge : edgeSet){
				edgeList.add(edge);
			}
			jGraph.removeAllEdges(edgeList);
			
		}catch(Exception e){		
			logger.error("Exception in deleteAllEdges " + e);
			throw new AppException("Exception in deleteAllEdges");
		}
	
	}

	@Override
	public void addNode(Node node)throws AppException {
		try{
			jGraph.addVertex(node.nodeId());
		}catch(Exception ex){
			logger.error(ErrorCodes.ERROR_IN_CREATING_NODE + ex);
			throw new AppException(ErrorCodes.ERROR_IN_CREATING_NODE);
		}
	}

	
	@Override
	public void addEdges(List<Node> nodes) throws AppException {
		for(Node node : nodes){
			addEdge(node);
		}		
	}
	
	/**
	 * Creates JGraph nodes from Json nodes
	 * @throws AppException 
	 */

	@Override
	public void addNodes(List<Node> nodes) throws AppException {
		for(Node node : nodes){
			addNode(node);
		}		
	}
	
	/**
	 * Deletes specified nodes
	 * @throws AppException 
	 */
	@Override
	public void deleteNodes(List<Node> nodes) throws AppException {
		for(Node node : nodes){
			deleteNode(node.nodeId());
		}
		
	}


	/**
	 * Creates edges of a specified Node
	 * @throws AppException 
	 */
	@Override
	public void addEdge(Node node) throws AppException {
		try{
		Iterator<Entry<String, String>> it = node.edges().entrySet().iterator();
		while(it.hasNext()){			
			Entry<String, String> entry = it.next();
			DefaultWeightedEdge  edge = jGraph.addEdge(node.nodeId(), entry.getKey() );
			if(edge!= null && entry.getValue() != null && entry.getValue().length() > 0){
				jGraph.setEdgeWeight(edge, Double.parseDouble(entry.getValue()));
			}
		}
		}catch(Exception ex){
			logger.error(ErrorCodes.ERROR_IN_CREATING_EDGE + ex);
			throw new AppException(ErrorCodes.ERROR_IN_CREATING_EDGE);
		}
	}
}