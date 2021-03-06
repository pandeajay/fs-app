/*
 * Copyright ...
 *
 */
package com.mycompany.app.codes;

/**
 * Represents error messages in the system.
 *
 * @author  
 * @see
 * @since 1.0
 */
public class ErrorCodes {
	private static final String errorPrefix = "ERROR: ";
	public static final String ERROR_GRAPH_INITIALIZED = errorPrefix + "Graph is already initialized.";
	public static final String ERROR_SPECIFY_FILE_PATH = errorPrefix + "Please specify file path";
	public static final String ERROR_BUILDING_GRAPH = errorPrefix + "Error in building graph. Error :";
	public static final String ERROR_NON_INITIALIZED_GRAPH = errorPrefix + "Graph is not initialized";
	public static final String ERROR_IN_CREATING_EDGE = errorPrefix + "Error in creating edges for a node ";
	public static final String ERROR_IN_DELETING_EDGE = errorPrefix +"Error in deleting node ";
	public static final String ERROR_IN_CLOSING_EDGE = errorPrefix +"Error in closing graph. Error : ";
	public static final String ERROR_NON_INITIALIZED_GRAPH_FOR_QUERY = errorPrefix + "Graph is not initialized for Query";
	public static final String ERROR_QUERYIMPL_ALREADY_INITIALIZED_WITH_GRAPH = "GraphQueryServiceImpl is already initialized";
	public static final String ERROR_IN_SHORTEST_PATH = errorPrefix + "Error in shotest path identification";
	public static final String ERROR_WHILE_CREATING_NODE = errorPrefix + "Error while creating node";
	public static final String ERROR_IN_GETTING_JSON_QUERIES = errorPrefix + "Error in getting JSON queries from user";
	public static final String ERROR_IN_READING_JSON_TO_FROM = errorPrefix + "Error in reading JSON to and from list";
	public static final String ERROR_IN_CREATING_NODE = errorPrefix + "Error in creating node";
	public static final String ERROR_WHILE_DELETING_NODES = errorPrefix + "Exception in NeoGraph while deleting nodes";
}
