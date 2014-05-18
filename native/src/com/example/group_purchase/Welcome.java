package com.example.group_purchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class Welcome extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//延迟两秒后执行run方法中的页面跳转
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(Welcome.this, IndexTable.class);
				startActivity(intent);
				Welcome.this.finish();
			}
		}, 3000); 
	}
}