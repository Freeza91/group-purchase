package com.example.get_data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ResponedData {
	public static HashMap<String, String> mapresponse; //用户登录使用
	public static LinkedList<HashMap<String, String>> list_good;
	public static LinkedList<HashMap<String, String>> list_shop;

	public static int flagresponse = 1;
	
	public boolean JsonParse(String data, String http) throws JSONException{
		JSONObject jsonObject = null;
		JSONObject o1 = null, o2 = null;
		String message = "";
		if(http.equals("GET")){
			flagresponse = 1;
    		list_good = new LinkedList<HashMap<String,String>>();
    		list_shop= new LinkedList<HashMap<String,String>>();
			try{
				o1 = new JSONObject(data);
			}catch(JSONException e){
				flagresponse = 1;
			}
			Iterator it1 = o1.keys();
	        while (it1.hasNext()) {  
	            String key1 = (String) it1.next();
	            if(key1.equals("message")){
	            	message = o1.getString("message");
	            }else{
		            try{
		            	o2 = o1.getJSONObject(key1);
		            }catch(JSONException e){
						flagresponse = 1;
		            }
		            HashMap<String, String> map = new HashMap<String, String>();
		            Iterator it2 = o2.keys();
			        while (it2.hasNext()) { 
			            String key2 = (String) it2.next();  
			            String value = o2.getString(key2);  
						map.put(key2, value);
					}
			        if(map != null){
			        	if(message.equals("shop")){
			        		list_shop.add(map);
			        	}else if(message.equals("good")){
			        		list_good.add(map);
			        	}
			        }
	            }
			}
			
		}else{
			try{
				jsonObject = new JSONObject(data);
				
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mapresponse = new HashMap<String, String>(); //用户登录使用
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
