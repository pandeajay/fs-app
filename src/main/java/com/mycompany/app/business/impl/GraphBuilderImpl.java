package com.mycompany.app.business.impl;

import java.util.List;
import java.util.Map;

import com.mycompany.app.business.builder.GraphBuilder;
import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.codes.ErrorCodes;
import com.mycompany.app.codes.InfoCodes;
import com.mycompany.app.graphs.impl.Jgraph;
import com.mycompany.app.graphs.impl.NeoGraph;
import com.mycompany.app.log.LogImpl;
import com.mycompany.app.utilities.Logger;
import com.mycompany.app.utilities.Utils;

public class GraphBuilderImpl implements GraphBuilder{

	private Graph graph = null;	
	private Logger logger = Logger.getLogger(new LogImpl());
	private Map<String,String> userInputs = null;



	@Override
	public void buildGraph(String dataNodesPath) throws Exception {
		try{
			logger.fine(InfoCodes.INFO_INITIALIZING_GRAPH);		

			if(this.graph != null){			
				logger.fine(ErrorCodes.ERROR_GRAPH_INITIALIZED);		
				throw new Exception (ErrorCodes.ERROR_GRAPH_INITIALIZED);
			}

			userInputs =  Utils.userQueries(dataNodesPath);
			
			if(userInputs == null || userInputs.isEmpty()){
				throw new Exception(ErrorCodes.ERROR_NON_INITIALIZED_GRAPH);
			}
			
			String graphStyle = userInputs.get("Graph-Style");
			if(graphStyle.equals("NeoGraph")){
				this.graph  = new NeoGraph();
			}else {
				//use this by default
				this.graph  = new Jgraph();
			}		

			logger.fine(InfoCodes.INFO_GRAPH_INITIALIZED);				
			logger.info(InfoCodes.INFO_BUILDING_GRAPH);								
			String nodesDataPath = userInputs.get("DataFile");
			
			List<Node> newNodesList = Utils.getAllNodesFromJson(nodesDataPath);				
			graph.addNodes(newNodesList);
			graph.addEdges(newNodesList);
		}catch(Exception ex){
			logger.warning(ErrorCodes.ERROR_BUILDING_GRAPH+ ex);	
		}		
	}
	@Override
	public void buildGraph(List<Node> nodes, String graphType) throws Exception {
		try{
			if(graphType == null || graphType.length() == 0){
				this.graph  = new Jgraph();
			}else if(graphType.equals("NeoGraph")){
				this.graph  = new NeoGraph();
			}else {
				//use this by default
				this.graph  = new Jgraph();
			}		
			
			logger.info(InfoCodes.INFO_BUILDING_GRAPH);	
			if(nodes != null && nodes.size() > 0){					
				graph.addNodes(nodes);
				graph.addEdges(nodes);
				return ;
			}
		}catch(Exception ex){
			logger.warning(ErrorCodes.ERROR_BUILDING_GRAPH+ ex);
			throw new Exception(ErrorCodes.ERROR_BUILDING_GRAPH+ ex);
		}	
		
	}

	@Override
	public void addNode(Node node) {
		try{
			logger.info(InfoCodes.INFO_ADDING_NODE+ node.getNodeId() + "...");	
			graph.addNode(node);
			logger.info(InfoCodes.INFO_ADDED_NODE + node.getNodeId());				
		}catch(Exception ex){
			logger.warning(ErrorCodes.ERROR_BUILDING_GRAPH+ ex);				
		}			
	}



	@Override
	public void createEdgesForNode(Node node) {
		try{
			logger.info(InfoCodes.INFO_CREATING_EDGE+ node.getNodeId() + " ...");	
			graph.addEdge(node);
			logger.info(InfoCodes.INFO_CREATED_EDGE + node.getNodeId() );	

		}catch(Exception ex){
			logger.warning(ErrorCodes.ERROR_IN_CREATING_EDGE+  node.getNodeId() + " . Error : " + ex);				
		}

	}

	@Override
	public void deleteNode(String nodeId) {
		try{
			logger.fine(InfoCodes.INFO_DELETING_EDGE+ nodeId +" ...");	
			graph.deleteNode(nodeId);
			logger.fine(InfoCodes.INFO_DELETED_EDGE+ nodeId);

		}catch(Exception ex){
			logger.warning(ErrorCodes.ERROR_IN_DELETING_EDGE + nodeId + ". Error : " + ex);				
		}		

	}

	@Override
	public void close() {
		try{
			logger.fine(InfoCodes.INFO_CLOSING_GRAPH );	
			this.graph.close();
			this.graph = null;
			logger.fine(InfoCodes.INFO_CLOSED_GRAPH);
		}catch(Exception ex){
			logger.warning(ErrorCodes.ERROR_IN_CLOSING_EDGE + ex);			
		}

	}

	@Override
	public Graph getGraph() {
		return this.graph;
	}
	
	
	

}
