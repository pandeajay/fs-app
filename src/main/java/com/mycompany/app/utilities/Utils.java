/*
 * Copyright ...
 *
 */
package com.mycompany.app.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.exception.AppException;
import com.mycompany.app.business.impl.NodeImpl;
import com.mycompany.app.codes.ErrorCodes;
import com.mycompany.app.jsonutils.JsonNodeReader;
import com.mycompany.app.log.LogImpl;


/**
 * Represents class for utility methods e.g. creating list of nodes , forming user defined queries etc.
 *
 * @author  
 * @see
 * @since 1.0
 */
public class Utils {
	
	private static Logger logger = Logger.getLogger(new LogImpl());

	
	public static List<Node> getAllNodesFromJson(String josnFile) throws AppException {
		try {
			List<String> nodesDataList = JsonNodeReader.readData(josnFile);
			Iterator<String> newIt = nodesDataList.iterator();
			Map<String, Node> nodesMap = new HashMap<String, Node>();
			while (newIt.hasNext()) {
				String str = newIt.next();
				String[] strs = str.split(";");
				Node newNode = Utils.createNode(strs);
				if (nodesMap.containsKey(newNode.nodeId())) {
					Node tempnode = nodesMap.get(newNode.nodeId());
					Map<String, String> map = tempnode.edges();
					Map<String, String> tempMap = newNode.edges();
					map.putAll(tempMap);
				} else {
					nodesMap.put(newNode.nodeId(), newNode);
				}

			}

			Iterator<Entry<String, Node>> mapIt = nodesMap.entrySet()
					.iterator();

			List<Node> newNodesList = new ArrayList<Node>();

			while (mapIt.hasNext()) {
				Entry<String, Node> entry = mapIt.next();
				newNodesList.add(entry.getValue());
			}
			return newNodesList;
		}catch(Exception ex){
			Utils.logger.error(ErrorCodes.ERROR_WHILE_CREATING_NODE);
			throw new AppException(ErrorCodes.ERROR_WHILE_CREATING_NODE);			
		}
	}

	static Node createNode(String[] strs) throws AppException {
		try {
			List<Map<String, String>> toListWithWeight = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			if (strs.length > 1) {
				map.put(strs[1], strs[2]);
			}
			toListWithWeight.add(map);
			Node newNode;

			newNode = new NodeImpl(strs[0], toListWithWeight);
			return newNode;
		} catch (AppException e) {			
			Utils.logger.error(ErrorCodes.ERROR_WHILE_CREATING_NODE);
			throw new AppException(ErrorCodes.ERROR_WHILE_CREATING_NODE);
		}

	}
	
	public static Map<String, String> userQueries(String dataNodesPath) throws AppException{
		BufferedReader br = null;
		try {
			Map<String, String> userFeedMap = new HashMap<String, String>();
			userFeedMap.put("queriesList", ";");
			String sCurrentLine;
			String flightSystemsHome = System.getenv("FlightSystems_Home");
			if (dataNodesPath != null && dataNodesPath.length() > 0) {
				br = new BufferedReader(new FileReader(dataNodesPath));
			} else {
				br = new BufferedReader(new FileReader(flightSystemsHome
						+ "\\FlightSystem\\user-files\\user-inputs.txt"));
			}
			while ((sCurrentLine = br.readLine()) != null) {

				if (sCurrentLine.contains("DataFile=")) {
					userFeedMap
							.put("DataFile",
									sCurrentLine
											.substring("DataFile=".length())
											.trim());
				} else if (sCurrentLine.contains("FromNode---ToNode=")) {
					String str = sCurrentLine.substring("FromNode---ToNode="
							.length());
					String[] strs = str.split("---");
					if (strs.length == 2) {
						String list = userFeedMap.get("queriesList");
						list = list + strs[0].trim() + "-" + strs[1].trim()
								+ ";";
						userFeedMap.put("queriesList", list);
					}
				} else if (sCurrentLine.contains("Graph-Style=")) {
					userFeedMap.put("Graph-Style",
							sCurrentLine.substring("Graph-Style=".length())
									.trim());

				}
			}
			return userFeedMap;
		} catch (IOException e) {
			throw new AppException(ErrorCodes.ERROR_IN_GETTING_JSON_QUERIES);
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	public static List<String> getFromAndToList(Map<String,String> map) throws AppException{
		try {
			List<String> frmToList = new ArrayList<String>();
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				if (!entry.getKey().contains("DataFile")) {
					String str = entry.getValue();
					String[] strs = str.split(";");
					for (String temp : strs) {
						if (temp != null && temp.length() > 0)
							frmToList.add(temp);
					}
				}
			}
			return frmToList;
		}catch(Exception ex){
			throw new AppException(ErrorCodes.ERROR_IN_READING_JSON_TO_FROM);
		}
		
	}

}
