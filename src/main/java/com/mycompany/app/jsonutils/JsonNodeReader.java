package com.mycompany.app.jsonutils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonNodeReader {



	public static List<String> readData(String fileStr) {
		List<String> parsedStr = new ArrayList<String>();

		JSONParser parser = new JSONParser();

		try {		
			Object obj = parser.parse(new FileReader(fileStr));			
			JSONArray jsonObject = (JSONArray) obj;			
			ListIterator<?> it = jsonObject.listIterator();
			while (it.hasNext()) {
				JSONArray arr2 = (JSONArray) it.next();
				// System.out.println("arr2 === " + arr2);
				ListIterator<JSONObject> it2 = arr2.listIterator();
				while (it2.hasNext()) {					
					JSONObject arr3 = (JSONObject) it2.next();
					//System.out.println("arr3 === " + arr3);
					String id = (String) arr3.get("id");
					//System.out.println("id === " + id);					
					JSONArray arr = (JSONArray) arr3.get("to");
					ListIterator<JSONArray> it4 = arr.listIterator();
					if(it4.hasNext() == false){
						parsedStr.add(id+";"+";");
					}
					while (it4.hasNext()) {
						String str = "";
						JSONArray arr4 = (JSONArray) it4.next();
						// System.out.println("arr4 === " +arr4);
						ListIterator<?> it5 = arr4.listIterator();
						str = str + id + ";";
						while (it5.hasNext()) {						
							str = str + it5.next()+";";
							//System.out.println("str === " + str);
						}						
						parsedStr.add(str);						
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return parsedStr;

	}

}
