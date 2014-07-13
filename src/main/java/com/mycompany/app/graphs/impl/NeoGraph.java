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
import com.mycompany.app.business.elements.ShortestPathAndWeight;
import com.mycompany.app.business.exception.AppException;
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
	 * @throws Exception 
	 */

	@Override
	public void addNode(Node node) throws AppException {
		try {
			validateNode(node);
			org.neo4j.graphdb.Node myNode = null;
			Transaction tx = graphDb.beginTx();
			myNode = graphDb.createNode();
			myNode.setProperty("id", node.nodeId());
			myNode.setProperty("to", node.edges().toString());
			NeoGraph.nodeIAndNeoId.put(node.nodeId(), "" + myNode.getId());
			tx.success();
		} catch (Exception ex) {
			System.out.println("Exception in NeoGraph createNode " + ex);
			throw new AppException("Exception in NeoGraph createNode ");
		}
	}

	/**
	 * Creates Edge for passed node
	 * input : node
	 * output : edgeId 
	 * @throws Exception 
	 * 
	 */
	@Override
	public void addEdge(Node node) throws AppException {
		validateNode(node);

		try {
			Transaction tx = graphDb.beginTx();

			long fromId = 0;
			fromId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(node.nodeId()));

			org.neo4j.graphdb.Node fromNode = (org.neo4j.graphdb.Node) graphDb
					.getNodeById(fromId);
			Iterator<Entry<String, String>> it = node.edges().entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				long toId = 0;
				toId = Long
						.parseLong(NeoGraph.nodeIAndNeoId.get(entry.getKey()));

				org.neo4j.graphdb.Node toNode = graphDb.getNodeById(toId);
				Relationship edge = fromNode.createRelationshipTo(toNode,
						RelationTypes.TravellingTo);
				edge.setProperty("weight", entry.getValue());
				edgeList.add("" + edge.getId());
			}
			tx.success();

		} catch (Exception ex) {
			System.out.println("Exception in NeoGraph createEdges " + ex);
			throw new AppException("Exception in NeoGraph createEdges ");
		}

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
	 * @throws AppException 
	 */
	@Override
	public ShortestPathAndWeight shortestPathFromImplementation(String from, String to) throws AppException {
		List<String> list = new ArrayList<String>();
		double minWeight = 0.0 ;

		try {
			if (from == null || from.length() == 0 || to == null
					|| to.length() == 0) {
				System.out
						.println("Exception in getShortestPathVetices. Either from or to is not specified  ");
				throw new AppException("To or From not valide for Neo4j Graph");
			}

			Transaction tx = graphDb.beginTx();
			long fromId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(from));
			long toId = Long.parseLong(NeoGraph.nodeIAndNeoId.get(to));

			org.neo4j.graphdb.Node fromNode = null;
			org.neo4j.graphdb.Node toNode = null;

				fromNode = graphDb.getNodeById(fromId);
				toNode = graphDb.getNodeById(toId);
			

			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
					PathExpanders.forTypeAndDirection(
							RelationTypes.TravellingTo, Direction.OUTGOING),
					"weight");

			Iterable<WeightedPath> paths = finder
					.findAllPaths(fromNode, toNode);
			Iterator<WeightedPath> it = paths.iterator();
			int count = 0;

			while (it.hasNext()) {
				Path path = it.next();
				WeightedPath weightedPath = (WeightedPath) path;
				if (count == 0) {
					minWeight = weightedPath.weight();
					count++;
				}
				if (minWeight > weightedPath.weight()) {
					minWeight = weightedPath.weight();
				}

				Iterable<org.neo4j.graphdb.Node> nodes = path.nodes();
				Iterator<org.neo4j.graphdb.Node> it2 = nodes.iterator();
				while (it2.hasNext()) {
					org.neo4j.graphdb.Node tempNode = it2.next();
					list.add(getNodeIdFromNeoNodeId(tempNode.getId()));
				}
			}
			tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph getShortestPathVetices " + e);
			throw new AppException("Exception in NeoGraph getShortestPathVetices");
		}
		
		ShortestPathAndWeight shortPath = new ShortestPathAndWeight(list,minWeight);
		return shortPath;

	}
	


	@Override
	public void close() {
			graphDb.shutdown();
	}

	/**
	 * Creates neo nodes from Json nodes
	 * @throws Exception 
	 */
	@Override
	public void addNodes(List<Node> nodes) throws AppException {
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
	 * @throws Exception 
	 */
	@Override
	public void addEdges(List<Node> nodes) throws AppException {
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
	 * @throws Exception 
	 */
	@Override
	public void deleteNode(String nodeId) throws AppException {

		try {
			if (nodeId == null || nodeId.length() == 0) {
				System.out
						.println("Error in NeoGraph deleteNode.Pass valid nodeId ");
			}

			Transaction tx = graphDb.beginTx();
			org.neo4j.graphdb.Node tempNode = graphDb.getNodeById(Long
					.parseLong(NeoGraph.nodeIAndNeoId.get(nodeId)));
			tempNode.delete();
			NeoGraph.nodeIAndNeoId.remove(nodeId);
			tx.success();

		} catch (Exception e) {
			System.out.println("Exception in NeoGraph deleteNode " + e);
			throw new AppException("Exception in NeoGraph deleteNode");
		}

	}

	/**
	 * Deletes nodes from passed node ids
	 */

	@Override
	public void deleteNodes(List<Node> nodes) {
		if (nodes == null || nodes.size() == 0) {
			System.out
					.println("NeoGraph deleteNodes passed empty list of nodes");
			return;
		}
		try {
			Transaction tx = graphDb.beginTx();
			for (Node node : nodes) {
				deleteNode(node.nodeId());
			}
			tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph getShortestPathVetices "
					+ e);
		}
	}

	/**
	 * gets all nodes in a graph
	 * 
	 * @return
	 */
	public List<Object> getAllNodes() {
		List<Object> list = new ArrayList<Object>();
		Iterator<org.neo4j.graphdb.Node> it = graphDb.getAllNodes().iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;

	}

	/**
	 * deletes all nodes in a graph
	 * 
	 * @throws Exception
	 */
	@Override
	public void deleteAllNodes() throws AppException {
		try {
			Transaction tx = graphDb.beginTx();
			List<String> nodes = new ArrayList<String>();
			Iterator<Entry<String, String>> it = NeoGraph.nodeIAndNeoId
					.entrySet().iterator();
			while (it.hasNext()) {
				nodes.add(it.next().getKey());
			}
			for (String nodeId : nodes) {
				deleteNode(nodeId);
			}
			tx.close();
			// tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph deleteAllNodes " + e);
			throw new AppException("Exception in NeoGraph deleteAllNodes ");
		}

	}

	/**
	 * deletes all edges from a graph
	 */
	@Override
	public void deleteAllEdges() throws AppException {
		try {
			Transaction tx = graphDb.beginTx();
			for (String edgeId : edgeList) {
				Relationship edge = graphDb.getRelationshipById(Long
						.parseLong(edgeId));
				edge.delete();
			}
			tx.success();
		} catch (Exception e) {
			System.out.println("Exception in NeoGraph deleteAllEdges " + e);
			throw new AppException("Exception in NeoGraph deleteAllEdges ");
		}

	}

	/**
	 * Verifies passed node so that Neo node can be created
	 * 
	 * @param node
	 * @return
	 */
	void validateNode(Node node) throws AppException {
		if (node == null) {
			System.out.println("NeoGraph createEdge : node is null ");
			throw new AppException("NeoGraph createEdge : node is null ");
		}
		if (node.nodeId() == null || node.nodeId().length() == 0) {
			System.out.println("NeoGraph createEdge : node id is null ");
			throw new AppException("NeoGraph createEdge : node is null ");
		}
	}
}
