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

/**
 * Represents JGraphT based implementation for Graph
 *
 * @author  
 * @see
 * @since 1.0
 */

public class Jgraph implements Graph {
	
	private final SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> jGraph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
		
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
	
	/**
	 * For a from and to pair return shortest path Edges
	 * input : from and to id
	 * output : Edges of shortest path between from and to
	 */
	public List<DefaultWeightedEdge> getShortestPathEdgeList(String from , String to){
		DijkstraShortestPath<String, DefaultWeightedEdge> shortpath = new DijkstraShortestPath<String, DefaultWeightedEdge>(this.jGraph, from, to);
		return shortpath.getPathEdgeList();
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
		// TODO Auto-generated method stub
		
	}
	/**
	 * Deletes specified node
	 */
	@Override
	public void deleteNode(String nodeId) {
		jGraph.removeVertex(nodeId);		
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
			System.out.print("Exception in deleteAllNodes " + e);
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
			System.out.print("Exception in deleteAllEdges " + e);
			throw new AppException("Exception in deleteAllEdges");
		}
	
	}
	








	@Override
	public void addNode(Node node) {
			jGraph.addVertex(node.nodeId());		
	}



	
	@Override
	public void addEdges(List<Node> nodes) {
		for(Node node : nodes){
			addEdge(node);
		}
		
		
	}
	
	/**
	 * Creates JGraph nodes from Json nodes
	 */

	@Override
	public void addNodes(List<Node> nodes) {
		for(Node node : nodes){
			addNode(node);
		}
		
		
	}
	
	/**
	 * Deletes specified nodes
	 */
	@Override
	public void deleteNodes(List<Node> nodes) {
		for(Node node : nodes){
			deleteNode(node.nodeId());
		}
		
	}


	/**
	 * Creates edges of a specified Node
	 */
	@Override
	public void addEdge(Node node) {
		Iterator<Entry<String, String>> it = node.edges().entrySet().iterator();
		while(it.hasNext()){			
			Entry<String, String> entry = it.next();
			DefaultWeightedEdge  edge = jGraph.addEdge(node.nodeId(), entry.getKey() );
			if(edge!= null && entry.getValue() != null && entry.getValue().length() > 0){
				jGraph.setEdgeWeight(edge, Double.parseDouble(entry.getValue()));
			}
		}
	}
}