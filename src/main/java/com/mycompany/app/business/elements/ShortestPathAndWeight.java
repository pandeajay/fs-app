package com.mycompany.app.business.elements;

import java.util.ArrayList;
import java.util.List;

public class ShortestPathAndWeight {
	public List<String> vertices = new ArrayList<String>();
	public double weight = 0.0;
	
	private ShortestPathAndWeight(){
		
	}
	
	public ShortestPathAndWeight(List<String> vertices, double weight){
		this.vertices.addAll(vertices);
		this.weight = weight;		
	}
}
