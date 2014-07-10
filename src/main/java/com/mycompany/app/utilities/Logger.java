/*
 * Copyright ...
 *
 */
package com.mycompany.app.utilities;

import com.mycompany.app.log.Log;

/**
 * Represents class to be used for logging in the system.
 *
 * @author  
 * @see
 * @since 1.0
 */
public class Logger {


	private static Logger logger = null;
	private static Log logWriter = null;

	private Logger(){

	}

	public static Logger getLogger(Log log){
		if(logger == null){
			logger = new Logger();
			logWriter = log;
		}
		return logger;	
	}	

	public void fine(String msg){		
		logWriter.fine(msg);
	}	

	public void info(String msg){
		logWriter.info(msg);
	}
	public void config(String msg){
		logWriter.config(msg);
	}
	public void finer(String msg){
		logWriter.finer(msg);
	}
	public void finest(String msg){
		logWriter.finest(msg);
	}
	public void warning(String msg){
		logWriter.warning(msg);
	}

}
