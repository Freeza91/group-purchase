package com.example.baidumap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MapData {
	public static String locaiton;
	public static int error_code;
	public static double lon;
	public static double lat;
	public static float radius;
	public static String ddr = null;
	public static String district;
	public static String street;
	public static String line;
	private Context c;
	private SharedPreferences sp;
	private SharedPreferences.Editor e;
	
	public MapData(Context c){
		this.c = c;
	}
	public MapData(){
		
	}
	public void save(){
		sp = this.c.getSharedPreferences("MapData", Context.MODE_PRIVATE);
		e = sp.edit();
		//之前写过数据
		if(sp.getBoolean("iswrite", false) == true){
			e = sp.edit();
			e.clear();
			e.commit();
			Log.d("appTag", "clear");
		}
		e.putString("lat", String.valueOf(lat));
		e.putString("lon", String.valueOf(lon));
		e.putString("ddr", ddr);
		e.putBoolean("iswrite", true);
		Log.d("appTag", "write");
		e.commit();
		
	}
	public double read_lat(){
		sp = this.c.getSharedPreferences("MapData", Context.MODE_PRIVATE);
		return Double.parseDouble(sp.getString("lat", "0"));
	}
	public double read_lon(){
		sp = this.c.getSharedPreferences("MapData", Context.MODE_PRIVATE);
		return Double.parseDouble(sp.getString("lon", "0"));
	}
	public String read_ddr(){
		sp = this.c.getSharedPreferences("MapData", Context.MODE_PRIVATE);
		return sp.getString("ddr", null);
	}
}
