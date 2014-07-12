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

/**
 * Represents class implementing node and its internals
 *
 * @author  
 * @see
 * @since 1.0
 */

public class NodeImpl implements Node {
	
	private String id ;
	private Map <String, String> to ;
	
	public NodeImpl(String id , List<Map<String,String>> toListWithWeight){
		
		this.id=new String (id);
		this.to = new HashMap<String, String>();
		if(toListWithWeight != null && !toListWithWeight.isEmpty()){
			Iterator<Map<String, String>> it = toListWithWeight.iterator();
			while(it.hasNext()){
				Map<String, String> map =  it.next();	
				Iterator<Entry<String, String>> it2  = map.entrySet().iterator();
				while(it2.hasNext()){
					Entry<String, String> next = it2.next();
					this.to.put(next.getKey(), next.getValue());					
				}
			}
		}
				
	}

	@Override
	public String nodeId() {
		return this.id;
	}

	@Override
	public Map<String, String> edges() {
		return this.to ;
	}
}