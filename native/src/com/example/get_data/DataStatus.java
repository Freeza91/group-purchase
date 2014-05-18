package com.example.get_data;

public class DataStatus {
	public static String distance;
	public static String category;
	public static String rating;
	
	public static void setDis(String dis){
		distance = dis;
	}
	
	public static void setCate(String cate){
		category = cate;
	}
	
	public static void setRating(String rating){
		rating = rating;
	}
	
	public static String getDis(){
		return distance;
	}
	
	public static String getCate(){
		return category;
	}
	
	public String getRating(){
		return rating;
	}
}
