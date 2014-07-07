package com.mycompany.app.codes;

public class ErrorCodes {
	private static final String errorPrefix = "ERROR: ";
	public static final String ERROR_GRAPH_INITIALIZED = errorPrefix + "Graph is already initialized.";
	public static final String ERROR_SPECIFY_FILE_PATH = errorPrefix + "Please specify file path";
	public static final String ERROR_BUILDING_GRAPH = errorPrefix + "Error in building graph. Error :";
	public static final String ERROR_NON_INITIALIZED_GRAPH = errorPrefix + "Graph is not initialized";
	public static final String ERROR_IN_CREATING_EDGE = errorPrefix + "Error in creating edges for a node ";
	public static final String ERROR_IN_DELETING_EDGE = errorPrefix +"Error in deleting node ";
	public static final String ERROR_IN_CLOSING_EDGE = errorPrefix +"Error in closing graph. Error : ";

}
