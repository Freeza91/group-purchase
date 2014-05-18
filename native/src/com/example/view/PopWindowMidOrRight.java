package com.example.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.get_data.DataStatus;
import com.example.group_purchase.R;

public class PopWindowMidOrRight extends PopupWindow{

	Context context;
	LayoutInflater inflater;
	View view;
	ListView lv;
	int flag;
	final int MID = 1;
	final int RIGHT = 2;
	
	String distance [] = {"1千米", "2千米",  "3千米",  "4千米",  "5千米"};
	String rating []   = {"智能排序", "好评优先", "离我最近", "优惠价最低"};
	
	public PopWindowMidOrRight(Context context, int flag, int width, int height) {
		// TODO Auto-generated constructor stub
		super(LayoutInflater.from(context).inflate(R.layout.popdrop_midorright, null),
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
		this.context = context;
		this.flag = flag;
		view = LayoutInflater.from(context).inflate(R.layout.popdrop_midorright, null);
		this.setAnimationStyle(R.style.PopupWindowAnimation);
//		this.setAnimationStyle(android.R.style.Animation_Dialog);
		
		this.setWidth(width / 2);
		this.update();
		this.setTouchable(true);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.setBackgroundDrawable(new BitmapDrawable(this.context.getResources(), (Bitmap) null));
		
		if(flag == MID)
			view.setBackgroundResource(R.drawable.choosearea_bg_mid);
		else if(flag == RIGHT)
			view.setBackgroundResource(R.drawable.choosearea_bg_right);
		
		this.setContentView(view);
		
		addListContent();
	}
	
	private void addListContent(){
		
		lv = (ListView) view.findViewById(R.id.popdropcontent_midorright);
		lv.setPadding(10, 0, 0, 5);

		switch (flag){
			case MID:
				//
				SimpleAdapter mid_adapter = new SimpleAdapter(this.context, getData(),
						R.layout.popdrop1,new String [] {"distance"},new int []{R.id.popdrop1}
						);
				lv.setAdapter(mid_adapter);
				break;
			case RIGHT:
				//
				SimpleAdapter right_adapter = new SimpleAdapter(this.context, getData(),
						R.layout.popdrop1,new String [] {"rating"},new int []{R.id.popdrop1}
						);
				lv.setAdapter(right_adapter);
				break;
		}
		lv.setOnItemClickListener(new MyOnItemClick(this.context));
	}
	
	private ArrayList<HashMap<String, Object>> getData(){
		ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String,Object>>();
		if(flag == MID){
			for(int i=0; i<distance.length; i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("distance", distance[i]);
				arraylist.add(map);
			}
		}else if(flag == RIGHT){
			for(int i=0; i<rating.length; i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("rating", rating[i]);
				arraylist.add(map);
			}
		}
		return arraylist;
	}
	
	class MyOnItemClick implements OnItemClickListener{

		Context context;
		public MyOnItemClick(Context c){
			this.context = c;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			DataStatus data = new DataStatus();
			Toast.makeText(this.context, parent.getAdapter().getItem(position).toString(), 
					Toast.LENGTH_SHORT).show();
			if(flag == MID){
				data.setDis(parent.getAdapter().getItem(position).toString());
			}else if(flag == RIGHT){
				data.setRating(parent.getAdapter().getItem(position).toString());
			}
		}
	}
}


