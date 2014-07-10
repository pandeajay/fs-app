/*
 * Copyright ...
 *
 */

package com.mycompany.app.graphs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.business.graph.RelationTypes;


/**
 * Represents Neo4j based implementation for Graph
 *
 * @author  
 * @see
 * @since 1.0
 */
public class NeoGraph implements Graph{

	
	GraphDatabaseService graphDb = null;
	static Map<String, String > nodeIAndNeoId = new HashMap<String, String>();
	static List<String> edgeList = new ArrayList<String>(); 
	
	
	/**
	 * Constructor
	 */
	public NeoGraph(){
		try{
			if(graphDb == null){
				String flightSystemsHome  = System.getenv("FlightSystems_Home");
				graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( flightSystemsHome+ "\\EmbeddedDB" );
			}
			if(graphDb == null){
				throw new Exception("can not initialize Neo graph ");
			}
			
		}catch(Exception e){
			System.out.println("Exception "+ e ) ;
		}
	}




	/**
	 * Creates Neo Node from Node
	 * input: node
	 * output: new node id
	 */

	@Override
	public long addNode(Node node) {
		if(!validateNode(node)){
			return 0;
		}
		
		org.neo4j.graphdb.Node myNode = null;
		
		try{
			Transaction tx = graphDb.beginTx();
			 myNode = graphDb.createNode();
			 myNode.setProperty( "id", node.fetchNodeId());
			 myNode.setProperty( "to", node.fetchEdges().toString());	
			 NeoGraph.nodeIAndNeoId.put(node.fetchNodeId(), ""+myNode.getId());
			 tx.success();
			
		}catch(Exception ex){
			System.out.println("Exception in NeoGraph createNode " + ex);
			return 0;
		}	
			
		return myNode.getId();
		
	}

	/**
	 * Creates Edge for passed node
	 * input : node
	 * output : edgeId 
	 * 
	 */
	@Override
	public long addEdge(Node node) {
		if(!validateNode(node)){
			return 0;
		}
		try{
			Transaction tx = graphDb.beginTx();

			long fromId = 0 ;
			try{
				fromId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(node.fetchNodeId()));
			}catch(Exception ex){
				System.out.println("Exception in NeoGraph createEdges. From node does not exist" );
				return 0;
			}
			org.neo4j.graphdb.Node fromNode = (org.neo4j.graphdb.Node) graphDb.getNodeById(fromId);			
			Iterator<Entry<String, String>> it = node.fetchEdges().entrySet().iterator();
			while(it.hasNext()){
				Entry<String, String> entry = it.next();
				long toId = 0 ;
				try{
					toId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(entry.getKey()));
				}catch(Exception ex){
					System.out.println("Exception in NeoGraph createEdges. To node does not exist" );
					return 0;
				}
				org.neo4j.graphdb.Node toNode = graphDb.getNodeById(toId);
				Relationship edge = fromNode.createRelationshipTo(toNode, RelationTypes.TravellingTo);
				edge.setProperty("weight", entry.getValue());
				edgeList.add(""+edge.getId());						
			}	
			 tx.success();
			 
			 return 1;
		
			
		}catch(Exception ex){
			System.out.println("Exception in NeoGraph createEdges " + ex);	
		}	
			
		
		return 0;
		
	}


	/**
	 * For a from and to pair return shortest path weight
	 * input : from and to id
	 * output : weight of shortest path between from and to
	 */

	@Override
	public double fetchShortestPathWeight(String from, String to) {		
		double minWeight = 0.0 ;
		
		if(from == null || from.length() == 0 || to == null || to.length() == 0){
			System.out.println("Exception in getShortestPathWeight. Either from or to is not specified  " );
			return 0.0;			
		}
		
		try {
			Transaction tx = graphDb.beginTx();
			long fromId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(from));
			long toId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(to));
			org.neo4j.graphdb.Node fromNode = null;
			org.neo4j.graphdb.Node toNode = null ;
			
			try{
				fromNode = graphDb.getNodeById(fromId);			
				toNode = graphDb.getNodeById(toId);
			}catch(Exception ex){
				System.out.println("Exception in getShortestPathWeight. From or To node does not exist " + ex);
				return 0.0;
			}

			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
				    PathExpanders.forTypeAndDirection( RelationTypes.TravellingTo, Direction.OUTGOING ), "weight" );
						
			Iterable<WeightedPath> paths = finder.findAllPaths(fromNode, toNode);
			Iterator<WeightedPath> it = paths.iterator();
			int i = 0;
			while(it.hasNext()){
				WeightedPath  path = it.next();
				if(i == 0){
					minWeight = path.weight();
					i++;
				}
				if(minWeight > path.weight()){
					minWeight = path.weight();
				}
			}
			tx.success();
		}catch(Exception e){
			System.out.println("Exception in getShortestPathWeight  " + e);
			return 0.0;
		}
		return minWeight;
	}


	/**
	 * Gets node form Neo node id
	 * @param neoId
	 * @return
	 */
	String getNodeIdFromNeoNodeId(long neoId){
		Iterator<Entry<String, String>> it = NeoGraph.nodeIAndNeoId.entrySet().iterator();
		
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			if(neoId == Long.parseLong(entry.getValue())){
				return entry.getKey();
			}
		}		
		
		return null;
		
	}

	/**
	 * For a from and to pair return shortest path vertices
	 * input : from and to id
	 * output : Vertices id of shortest path between from and to
	 */
	@Override
	public List getShortestPathVetices(String from, String to) {
		List<String> list = new ArrayList<String>();
		
		if(from == null || from.length() == 0 || to == null || to.length() == 0){
			System.out.println("Exception in getShortestPathVetices. Either from or to is not specified  " );
			return list ;			
		}
		
		try {
			Transaction tx = graphDb.beginTx();
			long fromId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(from));
			long toId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(to));
			
			org.neo4j.graphdb.Node fromNode = null;
			org.neo4j.graphdb.Node toNode = null ;
			
			try{
				fromNode = graphDb.getNodeById(fromId);			
				toNode = graphDb.getNodeById(toId);
			}catch(Exception ex){
				System.out.println("Exception in getShortestPathVetices. From or To node does not exist " + ex);
				return list;
			}
			
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
				    PathExpanders.forTypeAndDirection( RelationTypes.TravellingTo, Direction.OUTGOING ), "weight" );			
			
			Iterable<WeightedPath> paths = finder.findAllPaths(fromNode, toNode);
			Iterator<WeightedPath> it = paths.iterator();
			while(it.hasNext()){
				Path path = it.next();				
				Iterable<org.neo4j.graphdb.Node> nodes = path.nodes();
				Iterator<org.neo4j.graphdb.Node> it2 = nodes.iterator();
				while(it2.hasNext()){
					org.neo4j.graphdb.Node tempNode = it2.next();					
					list.add(getNodeIdFromNeoNodeId(tempNode.getId()));
				}
			}			
			tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph getShortestPathVetices " + e);
		}
		return list;

	}
	


	@Override
	public void close() {
			graphDb.shutdown();
	}

	/**
	 * Creates neo nodes from Json nodes
	 */
	@Override
	public void addNodes(List<Node> nodes) {
		if(nodes == null || nodes.size() == 0){
			System.out.println("NeoGraph createNodes passed empty list of nodes");
			return;
		}
		for(Node node : nodes){
			addNode(node);
		}	
	}

	/**
	 * Creates edges from passed JSon nodes
	 */
	@Override
	public void addEdges(List<Node> nodes) {
		if(nodes == null || nodes.size() == 0){
			System.out.println("NeoGraph createEdges passed empty list of nodes");
			return;
		}
		for(Node node : nodes){
			addEdge(node);
		}	
		
	}

	/**
	 * Deletes a nodes based on nodeId
	 */
	@Override
	public long deleteNode(String nodeId) {
		if(nodeId == null || nodeId.length() == 0 ){
			System.out.println("Error in NeoGraph deleteNode.Pass valid nodeId " );
			return 0;
		}
		try {
			Transaction tx = graphDb.beginTx();
			org.neo4j.graphdb.Node tempNode = graphDb.getNodeById(Long
					.parseLong(NeoGraph.nodeIAndNeoId.get(nodeId)));
			tempNode.delete();
			NeoGraph.nodeIAndNeoId.remove(nodeId);
			tx.success();
			return 1;
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph deleteNode " + e);
		}
		return 0;
	}
	
	/**
	 * Deletes nodes from passed node ids
	 */

	@Override
	public void deleteNodes(List<Node> nodes) {
		if(nodes == null || nodes.size() == 0){
			System.out.println("NeoGraph deleteNodes passed empty list of nodes");
			return;
		}
		try {
			Transaction tx = graphDb.beginTx();
			for (Node node : nodes) {
				deleteNode(node.fetchNodeId());
			}
			tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph getShortestPathVetices "
					+ e);
		}
	}
	

	/**
	 * gets all nodes in a graph
	 * @return
	 */
	public List<Object> getAllNodes() {
		List<Object> list = new ArrayList<Object>();
		Iterator<org.neo4j.graphdb.Node> it = graphDb.getAllNodes().iterator();
		while(it.hasNext()){
			list.add(it.next());
		}
		return list;
		
	}


	
	/**
	 * deletes all nodes in a graph
	 */
	@Override
	public int deleteAllNodes() {
		try {
			Transaction tx = graphDb.beginTx();
			List<String> nodes = new ArrayList<String>();
			Iterator<Entry<String, String>> it = NeoGraph.nodeIAndNeoId.entrySet().iterator();
			while (it.hasNext()) {
				nodes.add(it.next().getKey());
			}	
			for(String nodeId : nodes){
				deleteNode(nodeId);				
			}
			tx.close();
			//tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph deleteAllNodes " 	+ e);
			return 0;
		}
		return 1;
	}
	
	/**
	 * deletes all edges from a graph
	 */
	@Override
	public int deleteAllEdges() {
		try {	
			Transaction tx = graphDb.beginTx();
			for(String edgeId : edgeList){
				Relationship edge = graphDb.getRelationshipById(Long.parseLong(edgeId));
				edge.delete();
			}			
			tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph deleteAllEdges " 	+ e);
			return 0;
		}
		return 1;
	}
	
	/**
	 * Verifies passed node so that Neo node can be created
	 * @param node
	 * @return
	 */
	boolean validateNode(Node node){
		if(node == null){
			System.out.println("NeoGraph createEdge : node is null ");
			return false;
		}
		if(node.fetchNodeId() == null || node.fetchNodeId().length() == 0){
			System.out.println("NeoGraph createEdge : node id is null ");
			return false;
		}
		return true;
	}

}
