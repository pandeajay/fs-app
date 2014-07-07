package com.mycompany.app.business.builder;

import java.util.List;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.graph.Graph;

public interface GraphBuilder {
	void buildGraph(String dataNodesPath) throws Exception ;
	void buildGraph(List<Node> nodes, String graphType) throws Exception;	
	void createEdgesForNode(Node node );	
	void deleteNode(String nodeId );
	Graph getGraph();
	void close();
	void addNode(Node node);

}
