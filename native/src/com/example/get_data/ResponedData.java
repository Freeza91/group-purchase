package com.example.get_data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class ResponedData {
	public static HashMap<String, String> mapresponse = new HashMap<String, String>(); //用户登录使用; //用户登录使用
	public static LinkedList<HashMap<String, String>> list_good;
	public static LinkedList<HashMap<String, String>> list_shop;

	public static int flagresponse = 1;
	private Context context;
	public ResponedData(Context c){
		this.context = c;
		saveListShop();
		saveListGood();
	}
	public ResponedData(){
		
	}
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
			
			
			Iterator it = jsonObject.keys();
	        while (it.hasNext()) {  
	            String key = (String) it.next();  
	            String value = jsonObject.getString(key);  
				mapresponse.put(key, value);
			}
		}
		return true;
	}
	
	private void saveListShop(){
		SharedPreferences sp = this.context.getSharedPreferences("listshop", Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		//之前已经写过一些数据
		if(sp.getBoolean("iswrite", false) == true){
			e.clear();
			e.commit();
		}
		int len = list_shop.size();
		for(int i=1; i<=len; i++){
			HashMap<String, String> map = list_shop.get(i - 1);
			e.putString("name" + i, map.get("name").toString());
			e.putString("address" + i, map.get("address").toString());
			e.putString("lat" + i, map.get("lat").toString());
			e.putString("lon" + i, map.get("lon").toString());
			e.putString("avatar" + i, map.get("avatar").toString());
			e.putString("profile" + i, map.get("profile").toString());
			e.putString("shop_tel" + i, map.get("shop_tel").toString());
			e.putString("rating" + i, map.get("rating").toString());
			e.putString("category" + i, map.get("category").toString());
		}
		e.putBoolean("iswrite", true);
		e.commit();
	}
	private void saveListGood(){
		SharedPreferences sp = this.context.getSharedPreferences("listgood", Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		//之前已经写过一些数据
		if(sp.getBoolean("iswrite", false) == true){
			e.clear();
			e.commit();
		}
		int len = list_good.size();
		for(int i=1; i<=len; i++){
			HashMap<String, String> map = list_good.get(i - 1);
			e.putString("name" + i, map.get("name").toString());
			e.putString("profile" + i, map.get("profile").toString());
			e.putString("price" + i, map.get("price").toString());
			e.putString("avatar" + i, map.get("avatar").toString());
			e.putString("integration" + i, map.get("integration").toString());
			e.putString("service" + i, map.get("service").toString());
			e.putString("note" + i, map.get("note").toString());
		}
		e.putBoolean("iswrite", true);
		e.commit();
	}
	
}
