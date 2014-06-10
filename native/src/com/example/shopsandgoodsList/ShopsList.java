package com.example.shopsandgoodsList;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.baidumap.MapData;
import com.example.baidumap.MapLocation;
import com.example.dataAdapter.DropDownDataAdapter2;
import com.example.get_data.DataStatus;
import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
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
	DropDownDataAdapter2 dpAdatper = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopslist);
		init_ui();
		AddContent(true);
	}
	
	public void AddContent(boolean isrefresh){
		
		dp.setDropDownStyle(false);
		dp.setHeaderDefaultText("下拉刷新");
		dp.setOnBottomStyle(true);
		dp.setAutoLoadOnBottom(false);
		dp.setFooterDefaultText("努力加载中。。。。");
		DataStatus.current_shop = 0;

		left.setText(DataStatus.category);
		listItem = new LinkedList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		if(MapData.ddr == null || isrefresh ){
			map.put("location", "位置加载中");
			new MapLocation(getApplicationContext()).Loaction();

		}else{
			map.put("location", MapData.ddr);
		}
		listItem.add(map);

		if(!NetWork.isNetworkAvailable(this)){
			dp.setFooterDefaultText("无法链接到网络");
		}else{
			if(isrefresh){
				dp.setFooterDefaultText("努力加载中");
					new GetDataTask(true).execute();
			}
		}
		dpAdatper= new DropDownDataAdapter2(this, listItem);
		dp.setAdapter(dpAdatper);

	}
	// TODO Auto
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
		dp.setDropDownStyle(false);
		dp.setHeaderDefaultText("下拉刷新");
		dp.setOnBottomStyle(true);
		dp.setAutoLoadOnBottom(true);
		dp.setOnDropDownListener(new OnDropDownListener() {
			 
            @Override
            public void onDropDown() {
            	AddContent(true);
            }
        });
 
        // set on bottom listener
        dp.setOnBottomListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	new GetDataTask(false).execute();
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
	
	private class DPOnItemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ShopsList.this, ShopDetail.class);
			Bundle b = new Bundle();
			b.putInt("num", position);
			intent.putExtras(b);
			ShopsList.this.startActivity(intent);
		}
	}
	
	private class GetDataTask extends AsyncTask<String, String, String>{

		private boolean isDrop = true;
		public GetDataTask(boolean isDrop){
			this.isDrop = isDrop; 
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(isDrop){
				HttpRequest request = new HttpRequest(null);
				request.Get("/shoplist/" + DataStatus.category + "/" + DataStatus.current_shop);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			int status = ResponedData.flagresponse;
			//下拉或者第一次载入
			if(isDrop){
				if(status == 1){
					LinkedList<HashMap<String, String>> list = new ResponedData(ShopsList.this).list_shop;
					int len = list.size();
					for(int i=0; i<len; i++){
						HashMap<String, String> map = list.get(i);
						listItem.add(map);
					}
					listItem.removeFirst();
					if(len == 0 || len < 20){
						Toast.makeText(getApplicationContext(), "当前没有更多信息", Toast.LENGTH_SHORT).show();
						dp.setFooterDefaultText("");
		        		dpAdatper.notifyDataSetChanged();
						dp.onDropDownComplete();
					}
					HashMap<String, String> loc = new HashMap<String, String>();
					loc.put("location", MapData.ddr);
					listItem.addFirst(loc);
					dp.setAutoLoadOnBottom(false);
//					dp.setOnBottomStyle(false);
					
				}else{
					listItem.remove(0);
					Toast.makeText(getApplicationContext(), "链接超时", Toast.LENGTH_SHORT).show();
					dp.setFooterDefaultText("请重试！！");
	        		dpAdatper.notifyDataSetChanged();
					dp.onDropDownComplete();
				}

			}
			//上提
			else{
				if(status == 1){
					LinkedList<HashMap<String, String>> list = ResponedData.list_shop;
					int len = list.size();
					for(int i=0; i<len; i++){
						HashMap<String, String> map = list.get(i);
						listItem.add(map);
					}
	        		if(len == 0 || len < 20){
						Toast.makeText(getApplicationContext(), "已经到最后一页了！", Toast.LENGTH_SHORT).show();
						dp.setFooterDefaultText("没有更多了！");
						dp.onBottomComplete();

	        		}
	        		DataStatus.current_shop ++;
	        		dpAdatper.notifyDataSetChanged();

				}else{
					Toast.makeText(getApplicationContext(), "链接超时", Toast.LENGTH_SHORT).show();
				}
			}
			super.onPostExecute(result);
		}
		
		
	}
}
