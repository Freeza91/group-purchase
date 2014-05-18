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

import com.example.group_purchase.IndexTable;
import com.example.group_purchase.R;

public class UserInfor extends Activity {

	private Button update_uname,update_pass;
	private TextView user_infor;
	final static int UPDATE_uname = 1, UPDATE_pass = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_infor);
		
		update_uname = (Button) findViewById(R.id.update_uname);
		update_pass =  (Button) findViewById(R.id.update_pass);
		
		user_infor = (TextView) findViewById(R.id.user_infor);
		
		addlistener();
	}
	
	private void addlistener(){
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
		}
	}
	
	private void saveToken(String token){
		SharedPreferences sp = UserInfor.this.getSharedPreferences("token", MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("token", token);
		e.commit();
	}

}
