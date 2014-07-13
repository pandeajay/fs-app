/*
 * Copyright ...
 *
 */
package com.mycompany.app.log;;

/**
 * Represents interface for logging messegs in the system
 *
 * @author  
 * @see
 * @since 1.0
 */
public interface Log {
	void fine(String msg);
	void info(String msg);
	void config(String msg);
	void finer(String msg);
	void finest(String msg);
	void warning(String msg);
	void error(String msg);
}
