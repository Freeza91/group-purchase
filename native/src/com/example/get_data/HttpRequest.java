package com.example.get_data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpRequest {
	
	final String baseurl = DataStatus.remote_address;
	
	private HashMap<String, String>map = new HashMap<String, String>();
	private List<NameValuePair> pairList = new ArrayList<NameValuePair>();
	private Iterator iter;
	private String url = null;
	private HttpParams params = null;

	public HttpRequest(HashMap<String, String> map ){
		this.map = map;
		if(map != null){
			iter = map.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    String key = entry.getKey().toString(); 
			    String val = entry.getValue().toString(); 
				NameValuePair content = new BasicNameValuePair(key, val);
				pairList.add(content);
			} 
		}
		
		params = new BasicHttpParams();
		/* 超时设置 */
        /* 从连接池中取连接的超时时间 */
        ConnManagerParams.setTimeout(params, 20000);
        /* 连接超时 */
        HttpConnectionParams.setConnectionTimeout(params, 20000);
        /* 请求超时 */
        HttpConnectionParams.setSoTimeout(params, 20000);
	}

	public boolean Get(String Params) {
		// TODO Auto-generated method stub
		String url = baseurl + Params;
		
		// 生成请求对象
		HttpClient httpClient = new DefaultHttpClient(params);
		HttpGet get = new HttpGet(url);
		
		// 请求的格式， 在rails中必须设置这一个
		get.setHeader("Accept", "text/html");
//
//		// 回应的格式， 在rails中不需要设置？？？
		get.setHeader("Content-type","text/html");
		HttpResponse response = null;
		boolean flag = true;
		try {
			response = httpClient.execute(get);
			// 显示响应
		}catch(ConnectTimeoutException e){
			flag = false;
		}catch(NoHttpResponseException e){
			flag = false;
		}catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if(flag)
				new HttpRespond(response, "GET");// 一个私有方法，将响应结果显示出来
			else{
				ResponedData.flagresponse = 0;
			}
		}
		return true;
	}

	public boolean Post(String Params) {
		
		//create users
		url = baseurl + "/user" + Params;
		// TODO Auto-generated method stub
		HttpResponse response = null;
		boolean flag = true;
		try {
			HttpClient httpClient = new DefaultHttpClient(params);
			HttpPost httpPost = new HttpPost(url);
			HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList);
			httpPost.setEntity(requestHttpEntity);
			response = httpClient.execute(httpPost);
		}catch(NoHttpResponseException e){
			flag = false;
			ResponedData.mapresponse.put("message", "nohttpconnect");
		}catch(ConnectTimeoutException e){
			ResponedData.mapresponse.put("message", "nohttpconnect");
			Log.d("appTag", "ConnectTimeoutException");
			flag = false;
		}catch (Exception e) {
			ResponedData.mapresponse.put("message", "nohttpconnect");
			Log.d("appTag", "Exception");
			flag = false;
			e.printStackTrace();
			return false;
		} finally {
			if(flag)
				new HttpRespond(response, "POST");// 一个私有方法，将响应结果显示出来

		}
		return false;
	}

	public boolean Put(String Params) {
		// TODO Auto-generated method stub
		url = baseurl + "/user/update" + Params;
		HttpResponse response = null;
		boolean flag = true;

		try {
			HttpClient httpClient = new DefaultHttpClient(params);
			HttpPut put= new HttpPut(url);
			HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList);
			put.setEntity(requestHttpEntity);
			response = httpClient.execute(put);
			
		}catch(NoHttpResponseException e){
			flag = false;
			ResponedData.mapresponse.put("message", "nohttpconnect");
		}catch(ConnectTimeoutException e){
			ResponedData.mapresponse.put("message", "nohttpconnect");
			flag = false;
		}catch (Exception e) {
			ResponedData.mapresponse.put("message", "nohttpconnect");
			flag = false;
			e.printStackTrace();
			return false;
		} finally {
			if(flag)
				new HttpRespond(response, "PUT");// 一个私有方法，将响应结果显示出来
		}
		return false;
	}

	public boolean Delete(String token) {
		// TODO Auto-generated method stub
		url = baseurl + "/" + token;
		try {
			HttpClient httpClient = new DefaultHttpClient(params);
			HttpDelete delete= new HttpDelete(baseurl);
			HttpResponse response = httpClient.execute(delete);
			new HttpRespond(response, "DELETE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}