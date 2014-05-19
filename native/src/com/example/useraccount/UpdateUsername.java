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
import android.widget.EditText;
import android.widget.Toast;

import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class UpdateUsername extends Activity {

	private Button finish;
	private EditText change;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_username);
		
		finish = (Button) findViewById(R.id.change_username_btn);
		change = (EditText) findViewById(R.id.change_username);
		
		finish.setOnClickListener(new updateOnClick());

	}
	
	private class updateOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String result = change.getText().toString();
			if(result.length() == 0){
				Toast.makeText(UpdateUsername.this,"用户名不能为空！" , Toast.LENGTH_SHORT).show();
				change.setFocusable(true);
			}else if(!NetWork.isNetworkAvailable(UpdateUsername.this)){
				Toast.makeText(UpdateUsername.this,"网络无法连接" , Toast.LENGTH_SHORT).show();
			}else{
				new updateUsernameAsyncTask().execute();
			}
		}
	}
	
	class updateUsernameAsyncTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("username", change.getText().toString());
			SharedPreferences sp = UpdateUsername.this.getSharedPreferences("token", MODE_PRIVATE);
			String token = sp.getString("token", "NULL");
			map.put("token", token);
			if(!token.equals("NULL")){
				HttpRequest req = new HttpRequest(map);
				req.Put("/username");
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(UpdateUsername.this);
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
				String username = change.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("username", username);
				intent.putExtra("token", ResponedData.mapresponse.get("token").toString());
				setResult(UserInfor.UPDATE_uname, intent);
				UpdateUsername.this.finish();
				
			}else if(status.equals("failed")){
				Toast.makeText(getApplicationContext(), "更新失败！！！",Toast.LENGTH_SHORT).show();
				finish.setText("请重试");
			}
	        progressDialog.dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		setResult(UserInfor.UPDATE_nothing, intent);
		UpdateUsername.this.finish();
		super.onBackPressed();
	}
	
	
}
