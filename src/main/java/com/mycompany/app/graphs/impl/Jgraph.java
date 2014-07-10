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
	public double fetchShortestPathWeight(String from , String to){
		DijkstraShortestPath<String, DefaultWeightedEdge> shortpath = new DijkstraShortestPath<String, DefaultWeightedEdge>(this.jGraph, from, to);
		return shortpath.getPathLength();
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
	

	public List getShortestPathVetices(List<DefaultWeightedEdge> edgeList){
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

	/**
	 * For a from and to pair return shortest path vertices
	 * input : from and to id
	 * output : Vertices id of shortest path between from and to
	 */
	@Override
	public List getShortestPathVetices(String from, String to) {
		return getShortestPathVetices(getShortestPathEdgeList(from, to));
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Deletes specified node
	 */
	@Override
	public long deleteNode(String nodeId) {
		if(jGraph.removeVertex(nodeId)){
			return 1;
		}else{
			return 0;		
		}
		
	}



	/**
	 * Deletes all nodes
	 */

	@Override
	public int deleteAllNodes() {
		try{
			Set<String> vertexSet = jGraph.vertexSet();
			List<String> vertexList = new ArrayList<String>();
			for( String vertex : vertexSet){
				vertexList.add(vertex);
			}
			jGraph.removeAllVertices(vertexList);
			return 1;
		}catch(Exception e){
			System.out.print("Exception in deleteAllNodes " + e);
		}
		return 0;
	}


	/**
	 * Deletes all edges
	 */
	@Override
	public int deleteAllEdges() {
		try{
			Set<DefaultWeightedEdge> edgeSet = jGraph.edgeSet();
			List<DefaultWeightedEdge> edgeList = new ArrayList<DefaultWeightedEdge>();
			for( DefaultWeightedEdge edge : edgeSet){
				edgeList.add(edge);
			}
			jGraph.removeAllEdges(edgeList);
			return 1;
		}catch(Exception e){		
			System.out.print("Exception in deleteAllEdges " + e);
		}
		return 0;
		
	}
	
	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getJGraph(){
		return jGraph;		
	}







	@Override
	public long addNode(Node node) {
		try{
			jGraph.addVertex(node.fetchNodeId());
		}catch(Exception e){
			System.out.println("Exception in Graph2 add " + e);
		}
		return 1;
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
			deleteNode(node.fetchNodeId());
		}
		
	}


	/**
	 * Creates edges of a specified Node
	 */
	@Override
	public long addEdge(Node node) {
		Iterator<Entry<String, String>> it = node.fetchEdges().entrySet().iterator();
		while(it.hasNext()){			
			Entry<String, String> entry = it.next();
			DefaultWeightedEdge  edge = jGraph.addEdge(node.fetchNodeId(), entry.getKey() );
			if(edge!= null && entry.getValue() != null && entry.getValue().length() > 0){
				jGraph.setEdgeWeight(edge, Double.parseDouble(entry.getValue()));
			}
		}
		
		return 1;
	}



}