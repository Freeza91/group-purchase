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
import com.example.get_data.DataStatus;
import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;
import com.example.view.DropDown;
import com.example.view.DropDown.OnDropDownListener;

public class GoodsList extends Activity {

	DropDown dp;
	DropDownDataAdapter1 dpAdatper;
	
	LinkedList<HashMap<String, String>> listItem ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodslist);
		
		dp = (DropDown) findViewById(R.id.goods_refresh);
		
		AddDPSetting();
		AddContent();
	}
	
	private void AddContent(){
		dp.setOnBottomStyle(true);
		dp.setAutoLoadOnBottom(true);
		
		DataStatus.current_good = 0;
		listItem = new LinkedList<HashMap<String, String>>();
		LinkedList<HashMap<String, String>> list;
		HashMap<String, String> mm = new HashMap<String, String>();
		mm.put("1", "GridView");
		listItem.add(mm);
		
		if(!NetWork.isNetworkAvailable(this)){
			dp.setFooterDefaultText("无法链接到网络");
		}else{
			dp.setFooterDefaultText("努力加载中");
				new GetDataTask(true).execute();
		}
		dpAdatper = new DropDownDataAdapter1(this, listItem);
		dp.setAdapter(dpAdatper);
	}
	
	private void AddDPSetting(){
		
		dp.setDropDownStyle(false);
		dp.setHeaderDefaultText("下拉刷新");
		dp.setOnBottomStyle(true);
		dp.setAutoLoadOnBottom(true);
		dp.setHeaderDividersEnabled(true);

		dp.setOnDropDownListener(new OnDropDownListener() {
			 
            @Override
            public void onDropDown() {
            	AddContent();
            }
        });
 
        // set on bottom listener
        dp.setOnBottomListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	new GetDataTask(false).execute();
            }
        });
        
        dp.setOnItemClickListener(new DPOnItemOnClick()); 
	}
	
	private class GetDataTask extends AsyncTask<String, String, String>{

		private boolean IsDropDown = true;
		public GetDataTask(boolean isDrop){
			this.IsDropDown = isDrop;
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(IsDropDown){
				//第一次加载或者刷新
				HttpRequest request = new HttpRequest(null);
				request.Get("/goodlist/" + DataStatus.current_good);
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			int status = ResponedData.flagresponse;
			//下拉
			if(IsDropDown){
				if(status == 1){
					LinkedList<HashMap<String, String>> list = ResponedData.list_good;
					int len = list.size();
					for(int i=0; i<len; i++){
						HashMap<String, String> map = list.get(i);
						listItem.add(map);
					}
					if(len == 0 || len < 7){
						Toast.makeText(getApplicationContext(), "当前没有更多信息", Toast.LENGTH_SHORT).show();
						dp.setFooterDefaultText("没有更多了！");
					}
	        		dpAdatper.notifyDataSetChanged();
				}else{
					Toast.makeText(getApplicationContext(), "链接超时", Toast.LENGTH_SHORT).show();
					dp.setFooterDefaultText("请重试！！");
				}
				dp.onDropDownComplete();
				dp.setFooterDefaultText("");
			}
			//上提
			else{
				if(status == 1){
					LinkedList<HashMap<String, String>> list = ResponedData.list_good;
					int len = list.size();
					for(int i=0; i<len; i++){
						HashMap<String, String> map = list.get(i);
						listItem.add(map);
					}
	        		if(len == 0 || len < 7){
						Toast.makeText(getApplicationContext(), "已经到最后一页了！", Toast.LENGTH_SHORT).show();
						dp.setFooterDefaultText("没有更多了！");
	        		}
	        		DataStatus.current_good ++;
	        		dpAdatper.notifyDataSetChanged();

				}else{
					Toast.makeText(getApplicationContext(), "链接超时", Toast.LENGTH_SHORT).show();
				}
				dp.onBottomComplete();
			}
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


