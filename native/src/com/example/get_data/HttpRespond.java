package com.example.get_data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import android.util.Log;

public class HttpRespond {
	
	HttpResponse response = null;
	private String http;
	
	public HttpRespond(HttpResponse response, String http) {
		// TODO Auto-generated constructor stub
		this.response = response;
		this.http = http;
		showResponseResult();
	}
	
	public void showResponseResult(){

		if (response == null){
			return;
		}

		 HttpEntity httpEntity = response.getEntity();
		 InputStream inputStream = null;
		 BufferedReader reader = null;
		 try {
			 inputStream = httpEntity.getContent();
	         reader = new BufferedReader(new InputStreamReader(
	                    inputStream));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			String Json = "";
			String line = "";
			String Result = "";

			try {
				while(null != (line = reader.readLine())){
					Json += line;
				}
				try {
					new ResponedData().JsonParse(Json.toString(), this.http);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
