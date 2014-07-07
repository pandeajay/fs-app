package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mycompany.app.business.builder.GraphBuilder;
import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.graph.Graph;
import com.mycompany.app.business.impl.GraphBuilderImpl;
import com.mycompany.app.business.impl.GraphQueryImpl;
import com.mycompany.app.business.impl.NodeImpl;
import com.mycompany.app.graphs.impl.NeoGraph;

import junit.framework.TestCase;

public class TDDTest extends TestCase {


	static Graph graph = null;
	static String pathForInputFile;	
	String nodesDataPath;
	static List<Node> nodes = createNodes();
	
	static List<Node> createNodes(){		
			List<Node> nodes = new ArrayList<Node>();			
			List<Map<String,String>> nodeInfo = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("2", "2");
			map.put("3", "3");
			map.put("4", "100");			
			nodeInfo.add(map);
			Node node1 = new NodeImpl("1", nodeInfo);
			nodes.add(node1);
			
			List<Map<String,String>> nodeInfo2 = new ArrayList<Map<String, String>>();
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("3", "6");
			map2.put("4", "8");		
			nodeInfo2.add(map2);
			NodeImpl node2 = new NodeImpl("2", nodeInfo2);
			nodes.add(node2);
			
			List<Map<String,String>> nodeInfo3 = new ArrayList<Map<String, String>>();
			Map<String, String> map3 = new HashMap<String, String>();			
			map3.put("4", "1");						
			nodeInfo3.add(map3);
			NodeImpl node3 = new NodeImpl("3", nodeInfo3);
			nodes.add(node3);
			
			
			List<Map<String,String>> nodeInfo4 = new ArrayList<Map<String, String>>();
			Map<String, String> map4 = new HashMap<String, String>();	
			nodeInfo4.add(map4);
			NodeImpl node4 = new NodeImpl("4", nodeInfo4);
			nodes.add(node4);
			
			return nodes;	
	}


	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass for test");	
		graph = new NeoGraph();
	}

	public static void tearDownAfterClass() throws Exception {
		graph.close();
	}


	public void testShortestPath() {
		
		GraphBuilder gBuilder = new GraphBuilderImpl();
		try {
			gBuilder.buildGraph(nodes,null);
		} catch (Exception e) {
			fail("testShortestPath failed "  + e);
		}	
		
		GraphQueryImpl query =  new GraphQueryImpl();
		
		
		try {
			query.initialize(gBuilder.getGraph());
		} catch (Exception e) {
			fail("testShortestPath failed "  + e);
		}
		
		List<?> edgeList = query.findShortestPathVertices("1", "4");
		
		Iterator<?> it = edgeList.iterator();
		String shortestPath = "";
		while (it.hasNext()) {
			Object edge = it.next();
			shortestPath += edge.toString() + '-';
		}
		shortestPath = shortestPath.substring(0,shortestPath.length() -1);

		assertTrue(shortestPath.length() > 0);	
	}

	
	public void testShortestPathWeight() {}

	
	public void testShortestPathVertices() {}

}
