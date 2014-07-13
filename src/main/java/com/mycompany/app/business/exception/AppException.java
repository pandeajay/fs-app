/*
 * Copyright ...
 *
 */
package com.mycompany.app.business.exception;

/**
 * Represents class for Application Exceptions
 *
 * @author  
 * @see
 * @since 1.0
 */
public class AppException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;
 
	/**
	 * 
	 */
    public AppException() {
        super();
    }
 
    /**
     * 
     * @param message
     */
    public AppException(String message) {
        super(message);
        this.message = message;
    }
 
    /**
     * 
     * @param cause
     */
    public AppException(Throwable cause) {
        super(cause);
    }
 
    /**
     * 
     */
    @Override
    public String toString() {
        return message;
    }
 /**
  * 
  */
    @Override
    public String getMessage() {
        return message;
    }
}