package com.example.useraccount;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.get_data.ResponedData;
import com.example.group_purchase.IndexTable;
import com.example.group_purchase.R;

public class UserInfor extends Activity {

	private Button update_uname,update_pass,logout, back;
	private TextView user_infor;
	final static int UPDATE_uname = 1, UPDATE_pass = 2, UPDATE_nothing = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_infor);
		
		update_uname = (Button) findViewById(R.id.update_uname);
		update_pass =  (Button) findViewById(R.id.update_pass);
		back = (Button)findViewById(R.id.back_user);
		logout = (Button)findViewById(R.id.logout);

		user_infor = (TextView) findViewById(R.id.user_infor);
		
		Bundle b = getIntent().getExtras();
		if(b != null && b.getString("username").toString() != null){
			user_infor.setText(b.getString("username").toString());
		}
		addlistener();
	}
	
	private void addlistener(){
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserInfor.this, IndexTable.class);
				Bundle bundle = new Bundle();
				bundle.putString("token", ReadToken());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		update_uname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_update_uname = new Intent(UserInfor.this, UpdateUsername.class);
				startActivityForResult(intent_update_uname, UPDATE_uname);
			}
		});
		
		update_pass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_update_pass = new Intent(UserInfor.this, UpdatePassword.class);
				startActivityForResult(intent_update_pass, UPDATE_pass);
			}
		});
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteToken();
				Intent intent = new Intent(UserInfor.this, IndexTable.class);
				UserInfor.this.startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bundle b = data.getExtras();
		switch(requestCode){
			case UPDATE_uname:
				Toast.makeText(UserInfor.this, "更新用户名成功", Toast.LENGTH_SHORT).show();
				user_infor.setText(b.getString("username").toString());
				saveToken(b.getString("token").toString());
				break;
			case UPDATE_pass:
				Toast.makeText(UserInfor.this, "更新密码成功", Toast.LENGTH_SHORT).show();
				saveToken(b.getString("token").toString());
			case UPDATE_nothing:
				Toast.makeText(UserInfor.this, "更新失败！", Toast.LENGTH_SHORT).show();

		}
	}
	
	private void saveToken(String token){
		SharedPreferences sp = UserInfor.this.getSharedPreferences("token", MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("token", token);
		e.commit();
	}
	
	private String ReadToken(){
		SharedPreferences sp = UserInfor.this.getSharedPreferences("token", MODE_PRIVATE);
		String ans = sp.getString("token", "token");
		return ans;
	}
	
	private void deleteToken(){
		SharedPreferences sp = UserInfor.this.getSharedPreferences("token", MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.clear();
		e.commit();
	}

}
