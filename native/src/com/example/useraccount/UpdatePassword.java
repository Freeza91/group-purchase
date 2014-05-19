package com.example.useraccount;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class UpdatePassword extends Activity {

	private Button finish;
	private EditText change;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_password);
		
		finish = (Button) findViewById(R.id.change_password_btn);
		change = (EditText) findViewById(R.id.change_password);
		
		finish.setOnClickListener(new updateOnClick());

	}
	
	private class updateOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String result = change.getText().toString();
			if(!checkPassword(result)){
				Toast.makeText(UpdatePassword.this,"密码格式不正确！" , Toast.LENGTH_SHORT).show();
				change.setFocusable(true);
			}else if(!NetWork.isNetworkAvailable(UpdatePassword.this)){
				Toast.makeText(UpdatePassword.this,"网络无法连接" , Toast.LENGTH_SHORT).show();
			}else{
				new updatePasswordAsyncTask().execute();
			}
		}
	}
	
	class updatePasswordAsyncTask extends AsyncTask<String, integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("password", change.getText().toString());
			SharedPreferences sp = UpdatePassword.this.getSharedPreferences("token", MODE_PRIVATE);
			String token = sp.getString("token", "NULL");
			map.put("token", token);
			if(!token.equals("NULL")){
				HttpRequest req = new HttpRequest(map);
				req.Put("/password");
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(UpdatePassword.this);
		    progressDialog.setTitle("提示信息");
	        progressDialog.setMessage("正在更新用户信息，请稍后......");
	        //设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
	        progressDialog.setCancelable(false);
	        //设置ProgressDialog样式为圆圈的形式
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String status = ResponedData.mapresponse.get("message").toString();
			if(status.equals("nohttpconnect")){
				Toast.makeText(getApplicationContext(), "连接超时！！", Toast.LENGTH_SHORT).show();
				finish.setText("请重试");
			}else if(status.equals("success")){
				String password = change.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("password", password);
				intent.putExtra("token", ResponedData.mapresponse.get("token").toString());
				setResult(UserInfor.UPDATE_pass, intent);
				UpdatePassword.this.finish();
				
			}else if(status.equals("failed")){
				Toast.makeText(getApplicationContext(), "更新失败！！！",Toast.LENGTH_SHORT).show();
				finish.setText("请重试");
			}
	        progressDialog.dismiss();
		}
	}
	
	private boolean checkPassword(String result){
		Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{5,17}$");
		Matcher m = pattern.matcher(result);
		if(!m.matches()){
			return false;
		}
		return true;
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		setResult(UserInfor.UPDATE_nothing, intent);
		UpdatePassword.this.finish();
		super.onBackPressed();

	}
}
