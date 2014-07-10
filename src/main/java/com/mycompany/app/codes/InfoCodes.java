/*
 * Copyright ...
 *
 */
package com.mycompany.app.codes;

/**
 * Represents informative messeges in the system. 
 *
 * @author  
 * @see
 * @since 1.0
 */

public class InfoCodes {
	private static final String infoPrefix = "INFO: ";
	public static final String INFO_GRAPH_INITIALIZED = infoPrefix + "Graph already initialized";
	public static final String INFO_INITIALIZING_GRAPH = infoPrefix  + "Intializing GraphBuilderImpl ...";
	public static final String INFO_BUILDING_GRAPH = infoPrefix  + "Buidling Graph...";
	public static final String INFO_ADDING_NODE = infoPrefix  + "Adding node ";
	public static final String INFO_ADDED_NODE = infoPrefix  + "Added node ";
	public static final String INFO_CREATING_EDGE = infoPrefix  + "Creating edges for a node ";
	public static final String INFO_CREATED_EDGE = infoPrefix  + "Created edges for a node ";
	public static final String INFO_DELETING_EDGE = infoPrefix  + "Deleting a node ";
	public static final String INFO_DELETED_EDGE = infoPrefix  + "Deleted a node ";
	public static final String INFO_CLOSING_GRAPH = infoPrefix  + "Closing graph ...";
	public static final String INFO_CLOSED_GRAPH = infoPrefix  + "Closed graph.";

}
