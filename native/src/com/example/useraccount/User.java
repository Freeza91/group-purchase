package com.example.useraccount;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.collectionList.CollectionList;
import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class User extends Activity{
	private Button Tologin;
	private Bundle bundle;
	private String token;
    private ProgressDialog progressDialog;
    private boolean flag = true;
    private Button collection;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		collection = (Button) findViewById(R.id.collection);
		init_collection();
		
		Tologin = (Button) findViewById(R.id.Tologin);
		Tologin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag){
					Intent i = new Intent(User.this, LoginActivity.class);
					startActivity(i);
				}else{
					Intent intent = new Intent(User.this, UserInfor.class);
					Bundle b = new Bundle();
					b.putString("username", Tologin.getText().toString());
					intent.putExtras(b);
					startActivity(intent);
				}
			}
		});
		initLoadData();
	}
	
	private void init_collection(){
		SharedPreferences sp = getSharedPreferences("collection", MODE_APPEND);
		int size = sp.getInt("size", 0);
		if(size <= 0){
			collection.setEnabled(false);
			collection.setText("收藏夹---" + "数量为：" + 0);

		}else{
			collection.setEnabled(true);
			collection.setText("收藏夹---" + "数量为：" + size);
			collection.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i =  new Intent(User.this, CollectionList.class);
					startActivity(i);
				}
			});
		}
	}
	private void initLoadData(){
		SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
		if(sp == null || sp.getString("token", "123").equals("123")){
			Tologin.setText("请登录");
		}else{
			token = sp.getString("token", "123");
			if(NetWork.isNetworkAvailable(this)){
				//refer 
				//http://www.cnblogs.com/xiaoluo501395377/p/3430542.html
				new LoadUserDataAsyncTask().execute();
			}else{
				Toast.makeText(getApplicationContext(), "无网络链接", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	class LoadUserDataAsyncTask extends AsyncTask<String, Integer, HashMap<String, String>>{

		@Override
		protected HashMap<String, String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String>map = new HashMap<String, String>();
			map.put("token", token);
			
			HttpRequest req = new HttpRequest(map);
			req.Post("");
			HashMap<String, String> Map = ResponedData.mapresponse;
			return Map;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			 progressDialog = new ProgressDialog(User.this);
			 progressDialog.setTitle("提示信息");
			 progressDialog.setMessage("正在更新用户信息，请稍后......");
			 //设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
			 progressDialog.setCancelable(false);
			 //设置ProgressDialog样式为圆圈的形式
			 progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			 progressDialog.show();
		}

		@Override
		protected void onPostExecute(HashMap<String, String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
				String status = result.get("message").toString();
				if(status.equals("success")){
					Tologin.setText(result.get("username").toString() + "");
					flag = false;
				}else if(status.equals("nohttpconnect")){
					Tologin.setText("重试");
					Tologin.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							initLoadData();
						}
					});
				}else{
					flag = true;
					Tologin.setText("token 已经过期，请重新登录");
				}
			progressDialog.dismiss();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			progressDialog.setProgress(values[0]);
		}
		
	}
}
