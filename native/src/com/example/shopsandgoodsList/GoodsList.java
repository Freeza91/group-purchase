package com.example.shopsandgoodsList;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.dataAdapter.DropDownDataAdapter1;
import com.example.dataAdapter.DropDownDataAdapter2;
import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;
import com.example.view.DropDown;
import com.example.view.DropDown.OnDropDownListener;

public class GoodsList extends Activity {

	DropDown dp;
	private Button left, mid, right;
	int current = 0;
	private int flag = 0;
	DropDownDataAdapter1 dpAdatper;
	
	LinkedList<HashMap<String, String>> listItem ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodslist);
		
		dp = (DropDown) findViewById(R.id.goods_refresh);
		
		AddContent();
		AddPreContent(0);
		
	}
	
	private void AddPreContent(int flag){
		listItem = new LinkedList<HashMap<String, String>>();
		LinkedList<HashMap<String, String>> list;
		HashMap<String, String> mm = new HashMap<String, String>();
		mm.put("1", "GridView");
		listItem.add(mm);
		for(int i=0; i<8; i++){
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("1", "GridView");
			listItem.add(m);
		}
		
		if(!NetWork.isNetworkAvailable(this)){
			dp.setFooterDefaultText("无法链接到网络");
		}else{
			dp.setFooterDefaultText("努力加载中");
			if(flag == 0)
				new GetDataTask().execute();
        	
        	int len = ResponedData.list_good.size();
        	list = ResponedData.list_good;
        	for(int i=0; i<len; i++){
        		HashMap<String, String> m = new HashMap<String, String>();
        		m = list.get(i);
        		listItem.add(m);
        	}
		}
		dpAdatper = new DropDownDataAdapter1(this, listItem);
		dp.setAdapter(dpAdatper);
	}
	
	private void AddContent(){
		
		dp.setDropDownStyle(false);
		dp.setOnBottomStyle(true);
		dp.setAutoLoadOnBottom(true);
		dp.setHeaderDividersEnabled(false);

		dp.setOnDropDownListener(new OnDropDownListener() {
			 
            @Override
            public void onDropDown() {
            	AddPreContent(0);
            }
        });
 
        // set on bottom listener
        dp.setOnBottomListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
//            	new GetDataTask().execute();
        		for(int i=0; i<8; i++){
        			HashMap<String, String> m = new HashMap<String, String>();
        			m.put("1", "GridView");
        			listItem.add(m);
        		}
        		dpAdatper.notifyDataSetChanged();
            	
            }
        });
        
        dp.setOnItemClickListener(new DPOnItemOnClick()); 
	}
	
	private class GetDataTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(flag == 0){
				//第一次加载或者刷新
				HttpRequest request = new HttpRequest(null);
				request.Get("/shoplist");
			}else{
				//底部加载
			}
			return null;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			int status = ResponedData.flagresponse;
			if(status == 1){
				AddPreContent(1);
				dp.setSecondPositionVisible();
			}else{
				Toast.makeText(getApplicationContext(), "链接超时", Toast.LENGTH_SHORT).show();
			}
			dp.onDropDownComplete();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
	
	
	private class DPOnItemOnClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GoodsList.this, GoodDetail.class);
			Bundle b = new Bundle();
			b.putInt("num", position);
			intent.putExtras(b);
			startActivity(intent);
		}
	}
}


