package com.example.useraccount;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.IndexTable;
import com.example.group_purchase.R;

public class RegisterActivity extends Activity {

	private EditText username, tel, password;
	private Button register;
    private ProgressDialog progressDialog;
	String uname, pass, tel_num;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		username = (EditText) findViewById(R.id.reg_username);
		tel = (EditText) findViewById(R.id.reg_tel);
		password = (EditText) findViewById(R.id.reg_password);
		
		setCheck();
		
		register = (Button) findViewById(R.id.register);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(NetWork.isNetworkAvailable(RegisterActivity.this)){
					if(checkagain()){
						new RegisterAsyncTask().execute();
					}else{
						Toast.makeText(RegisterActivity.this, "输入内容格式不正确！！！", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(RegisterActivity.this, "请检查网络连接状态", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	class RegisterAsyncTask extends AsyncTask<String, Integer, String[]>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(RegisterActivity.this);
		    progressDialog.setTitle("提示信息");
	        progressDialog.setMessage("正在更新用户信息，请稍后......");
	        //设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
	        progressDialog.setCancelable(false);
	        //设置ProgressDialog样式为圆圈的形式
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.show();
		}

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub

			uname = username.getText().toString();
			pass = password.getText().toString();
			tel_num = tel.getText().toString();
			if (uname.length() > 0 && pass.length() > 0 && tel_num.length() > 0) {
				HashMap<String, String> map = new HashMap<String, String>();

				map.put("username", uname);
				map.put("password", pass);
				map.put("tel", tel_num);
				
				HttpRequest req = new HttpRequest(map);
				req.Post("/create");
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String status = ResponedData.mapresponse.get("message").toString();
			Log.d("appTag", status);
			if(status.equals("success")){
				Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegisterActivity.this, IndexTable.class);
				Bundle bundle = new Bundle();
				bundle.putString("token", ResponedData.mapresponse.get("token").toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}else if(status.equals("nohttpconnect")){
				Toast.makeText(RegisterActivity.this, "连接超时！请重试", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
				register.setText("请重新注册");
			}
			progressDialog.dismiss();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}
	
	private void setCheck(){
		//refer
		//http://unetman.blog.51cto.com/884028/198749
		username.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					String u = username.getText().toString();
					if(u.length() == 0){
						Toast.makeText(RegisterActivity.this, "用户名不能为空，请重新填写！！！", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		
		password.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					String p = password.getText().toString();
					Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{5,17}$");
					
					Matcher m = pattern.matcher(p);
					if(!m.matches()){
						Toast.makeText(RegisterActivity.this, "密码格式不正确", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		tel.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					String t = tel.getText().toString();
					Pattern pattern = Pattern.compile("^(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
					Matcher m = pattern.matcher(t);
					if(!m.matches()){
						Toast.makeText(RegisterActivity.this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	private boolean checkagain(){
		String t = tel.getText().toString();
		String u = username.getText().toString();
		String p = password.getText().toString();
		if(u.length() == 0){
			return false;
		}
		Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9]\\w{5,17}$");
		Matcher m1 = pattern1.matcher(p);
		
		if(!m1.matches()){
			return false;
		}
		
		Pattern pattern2 = Pattern.compile("^(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m2 = pattern2.matcher(t);
		if(!m2.matches()){
			return false;
		}
		return true; 
	}
}

