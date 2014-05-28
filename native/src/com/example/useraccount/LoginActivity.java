package com.example.useraccount;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class LoginActivity extends Activity {

	private EditText tel, password;
	private Button register, login;
    private ProgressDialog progressDialog;
    String utel, pass;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		tel = (EditText) findViewById(R.id.login_tel);
		password = (EditText) findViewById(R.id.login_password);
		
		setCheck();
		
		register = (Button) findViewById(R.id.toRegister);
		login = (Button) findViewById(R.id.login);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(NetWork.isNetworkAvailable(LoginActivity.this)){
					if(checkagain()){
						new LoginAsyncTask().execute();
					}else{
						Toast.makeText(getApplicationContext(), "填写格式不正确，请重新填写", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(LoginActivity.this, "请检查网络连接状态", Toast.LENGTH_SHORT).show();
				}
			}
		});

		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
	
	class LoginAsyncTask extends AsyncTask<String, Integer, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub

			utel = tel.getText().toString();
			pass = password.getText().toString();
			if (utel.length() > 0 && pass.length() > 3) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("tel", utel);
				map.put("password", pass);
				HttpRequest request = new HttpRequest(map);
				request.Post("/login");
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(LoginActivity.this);
		    progressDialog.setTitle("提示信息");
	        progressDialog.setMessage("正在更新用户信息，请稍后......");
	        //设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
	        progressDialog.setCancelable(false);
	        //设置ProgressDialog样式为圆圈的形式
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.show();
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String status = ResponedData.mapresponse.get("message").toString();
			if(status.equals("success")){
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, IndexTable.class);
				Bundle bundle = new Bundle();
				bundle.putString("token", ResponedData.mapresponse.get("token"));
				intent.putExtras(bundle);
				startActivity(intent);
				
			}else if(status.equals("nohttpconnect")){
				Toast.makeText(LoginActivity.this, "连接超时！请重试", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(LoginActivity.this, "密码和用户名不正确，重新登录！！", Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
		}
		
	}
	
	private void setCheck(){
		tel.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					String utel = tel.getText().toString();
					if(utel.length() == 0){
						Toast.makeText(LoginActivity.this, "用户名不能为空，请重新填写！！！", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(LoginActivity.this, "密码格式不正确", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	private boolean checkagain(){
		String u = tel.getText().toString();
		if(u.length() == 0){
			return false;
		}
		String p = password.getText().toString();
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]\\w{5,17}$");
		
		Matcher m = pattern.matcher(p);
		if(!m.matches()){
			return false;
		}
		return true;
	}

}
