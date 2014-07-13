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
	private static Log log = null;

	private Logger(){

	}

	/**
	 * Singleton logger
	 * @param log
	 * @return
	 */
	public static Logger setAndGetLogger(Log userLog) {
		if(logger == null){
			logger = new Logger();
			log = userLog;
		}
		return logger;	
	}
	
	
	public static Logger getLogger(){
		if(logger == null){
			return null;
		}
		return logger;	
	}	

	/**
	 * Logs fine level messeges
	 * @param msg
	 */
	public void fine(String msg){		
		log.fine(msg);
	}	

	/**
	 * Logs info level messeges
	 * @param msg
	 */
	public void info(String msg){
		log.info(msg);
	}
	
	/**
	 * Logs config level messeges
	 * @param msg
	 */
	public void config(String msg){
		log.config(msg);
	}
	
	/**
	 * Logs finer level messeges
	 * @param msg
	 */
	public void finer(String msg){
		log.finer(msg);
	}
	/**
	 * Logs finest level messeges
	 * @param msg
	 */
	public void finest(String msg){
		log.finest(msg);
	}
	
	/**
	 * Logs warning level messeges
	 * @param msg
	 */
	public void warning(String msg){
		log.warning(msg);
	}
	
	/**
	 * Logs warning level messeges
	 * @param msg
	 */
	public void error(String msg){
		log.error(msg);
	}

}
