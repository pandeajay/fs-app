package com.mycompany.app.business.graph;

import java.util.List;

import com.mycompany.app.business.elements.Node;

public interface Graph {

	long addNode(Node node);
	long deleteNode(String nodeId);
	void addNodes(List<Node> nodes);
	void addEdges(List<Node> nodes);
	long addEdge(Node node);
	void deleteNodes(List<Node> node);
	double getShortestPathWeight(String from , String to);	
	List<?> getShortestPathVetices(String from , String to);
	void close();
	List<Object> getAllEdges();
	int deleteAllNodes();
	int deleteAllEdges();
}
