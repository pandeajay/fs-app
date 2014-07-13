/*
 * Copyright ...
 *
 */

package com.mycompany.app.business.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents class for shortest Path in Graph and its associated weight
 *
 * @author  
 * @see
 * @since 1.0
 */

public class ShortestPathAndWeight {
	private List<String> vertices = new ArrayList<String>();
	private double weight = 0.0;
	
	public List<String> vertices() {
		return vertices;
	}

	public double weight() {
		return weight;
	}

	public ShortestPathAndWeight(List<String> vertices, double weight){
		this.vertices.addAll(vertices);
		this.weight = weight;		
	}
}
