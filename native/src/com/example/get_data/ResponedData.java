package com.example.get_data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ResponedData {
	public static HashMap<String, String> mapresponse = new HashMap<String, String>();
	public static LinkedList<HashMap<String, String>> list_good = new LinkedList<HashMap<String,String>>();
	public static LinkedList<HashMap<String, String>> list_shop= new LinkedList<HashMap<String,String>>();

	public static int flagresponse = 1;
	
	public boolean JsonParse(String data, String http) throws JSONException{
		JSONObject jsonObject = null;
		if(http.equals("GET")){
			flagresponse = 1;
		}else{
			try{
				jsonObject = new JSONObject(data);
				
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Iterator it = jsonObject.keys();
	        while (it.hasNext()) {  
	            String key = (String) it.next();  
	            String value = jsonObject.getString(key);  
				mapresponse.put(key, value);
			}
		}
		return true;
	}
	
}
