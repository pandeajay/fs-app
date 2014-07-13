/*
 * Copyright ...
 *
 */
package com.mycompany.app.business.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mycompany.app.business.elements.Node;
import com.mycompany.app.business.exception.AppException;
import com.mycompany.app.codes.ErrorCodes;

/**
 * Represents class implementing node and its internals
 *
 * @author  
 * @see
 * @since 1.0
 */

public class NodeImpl implements Node {
	
	//User defined node id
	private String id ;
	
	//Represents meta info of edges from this node and its weight
	private Map <String, String> edgesMap ;
	
	/**
	 * Takes user defined node id and is edges with weight
	 * @param id
	 * @param toListWithWeight
	 * @throws AppException 
	 */
	public NodeImpl(String id , List<Map<String,String>> toListWithWeight) throws AppException{
		
		try{
			this.id=new String (id);
			this.edgesMap = new HashMap<String, String>();
			if(toListWithWeight != null && !toListWithWeight.isEmpty()){
				Iterator<Map<String, String>> it = toListWithWeight.iterator();
				while(it.hasNext()){
					Map<String, String> map =  it.next();	
					Iterator<Entry<String, String>> it2  = map.entrySet().iterator();
					while(it2.hasNext()){
						Entry<String, String> next = it2.next();
						this.edgesMap.put(next.getKey(), next.getValue());					
					}
				}
			}
		}catch(Exception ex){
			throw new AppException(ErrorCodes.ERROR_WHILE_CREATING_NODE);
		}
				
	}

	@Override
	public String nodeId() {
		return this.id;
	}

	@Override
	public Map<String, String> edges() {
		return this.edgesMap ;
	}
}