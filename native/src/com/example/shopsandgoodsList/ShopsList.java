package com.example.shopsandgoodsList;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.baidumap.MapData;
import com.example.baidumap.MapLocation;
import com.example.dataAdapter.DropDownDataAdapter2;
import com.example.group_purchase.R;
import com.example.view.DropDown;
import com.example.view.DropDown.OnDropDownListener;
import com.example.view.PopWindowLeft;
import com.example.view.PopWindowMidOrRight;

public class ShopsList extends Activity {

	DropDown dp;
	LinkedList<HashMap<String, String>> listItem ;
	Button left, right, mid;
	PopWindowLeft leftPopWindow;
	PopWindowMidOrRight midPopWindow, rightPopWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopslist);
		init_ui();
		AddContent();
	}
	
	private void AddContent(){
		listItem = new LinkedList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pre", "GridView");
		listItem.add(map);
		for(int i=0; i<10; i++){
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("pre", "GridView");
			listItem.add(m);
		}
		
		DropDownDataAdapter2 dpAdatper = new DropDownDataAdapter2(this, listItem);
		dp.setAdapter(dpAdatper);
		
		//判断网络情况
		//if(NetWork.isNetworkAvailable(this)){
			//dp.setFooterDefaultText("努力加载中");
		//}else{
			//dp.setFooterDefaultText("网络无法链接");
		//}
		//第一次加载数据

	}
	private void init_ui(){
		left = (Button) findViewById(R.id.leftSpinner);
		left.setBackgroundResource(android.R.drawable.btn_dropdown);
		mid = (Button) findViewById(R.id.midSpinner);
		mid.setBackgroundResource(android.R.drawable.btn_dropdown);
		right = (Button) findViewById(R.id.rightSpinner);
		right.setBackgroundResource(android.R.drawable.btn_dropdown);
		dp = (DropDown) findViewById(R.id.shops_refresh);
		SetPoPWindow();
		AddDpListener();
	}
	
	private void AddDpListener(){		
		dp.setOnDropDownListener(new OnDropDownListener() {
			 
            @Override
            public void onDropDown() {
//            	AddPreContent();
            }
        });
 
        // set on bottom listener
        dp.setOnBottomListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	//判断网络情况
            	//判断是否到底
            }
        });
        
        dp.setOnItemClickListener(new DPOnItemClick());
 
	}
	
	private void SetPoPWindow(){
		
		final DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		final int height = displaymetrics.heightPixels;
		final int width = displaymetrics.widthPixels;
		
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leftPopWindow = new PopWindowLeft(ShopsList.this, width, height);
				
				if(!leftPopWindow.isShowing()){
					leftPopWindow.showAsDropDown(left);

				}else {
					leftPopWindow.dismiss();
				}
			}
		});
		
		mid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				midPopWindow = new PopWindowMidOrRight(ShopsList.this,1, width, height);
				if(!midPopWindow.isShowing()){
					midPopWindow.showAsDropDown(mid, -1 * width / 6, 0);
				}else{
					midPopWindow.dismiss();
				}
			}
		});
		
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rightPopWindow = new PopWindowMidOrRight(ShopsList.this, 2, width, height);
				if(!rightPopWindow.isShowing()){
					rightPopWindow.showAsDropDown(right);
				}else{
					rightPopWindow.dismiss();
				}
			}
		});
	}
	
	
	private void AddLocation(){
		new MapLocation(getApplicationContext()).Loaction();

		 final Handler handler = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
				}
	        	
	        };
	        
			new MapLocation(getApplicationContext()).Loaction();
	        new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						if(MapData.ddr != null && MapData.ddr.length() > 0){
							Message message = new Message();
							handler.sendMessage(message);
							break;
					}
					}
				}
			}).start();
	}
	
	private class DPOnItemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ShopsList.this, ShopDetail.class);
			Bundle b = new Bundle();
			b.putInt("num", position);
			intent.putExtras(b);
			startActivity(intent);
		}
	}
}
