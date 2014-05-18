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
import com.example.shopsandgoodsList.ShopsList;

public class PopWindowLeft extends PopupWindow{

	Context context;
	LayoutInflater inflater;
	View view;
	ListView lv_left, lv_right;
	final int LEFT = 1;
	final int RIGHT = 2;
	
	String category [] = {"全部分类", "美食",  "酒店",  "休闲娱乐",  "生活服务", "丽人",  "旅游"};
	String caterogy_details[][] = {
			{"全部", "火锅", "自助餐", "西餐", "日韩料理", "甜点", "烧烤烤鱼", "川湘菜", "江浙菜", "粤菜", "小吃快餐"},
			{"全部", "经济型酒店", "豪华型酒店"},
			{"全部", "电影", "KTV", "洗浴", "健身", "桌游", "密室逃脱", "酒吧", "演出赛事", "真人CS", "其他"},
			{"全部", "摄影", "体检", "照片冲印", "服装服务", "配镜", "鲜花婚庆"},
			{"全部", "美发", "美甲", "美容SPA", "舞蹈"},
			{"全部", "本地/周边旅游", "景点门票"}
	};
	
	public PopWindowLeft(Context context, int width, int height) {
		// TODO Auto-generated constructor stub
		super(LayoutInflater.from(context).inflate(R.layout.popdrop_left, null),
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
		this.context = context;
		view = LayoutInflater.from(context).inflate(R.layout.popdrop_left, null);
		this.setAnimationStyle(R.style.PopupWindowAnimation);
//		this.setAnimationStyle(android.R.style.Animation_Dialog);
		
		this.setWidth(width  * 9 / 10);
		this.update();
		this.setTouchable(true);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.setBackgroundDrawable(new BitmapDrawable(this.context.getResources(), (Bitmap) null));
		this.view.setBackgroundResource(R.drawable.choosearea_bg_left);
		this.setContentView(view);
		addListContent();
	}
	
	private void addListContent(){
		
		lv_left = (ListView)view.findViewById(R.id.popdropcontent_left);
		lv_left.setPadding(25, 0, 0, 0);
		lv_right = (ListView) view.findViewById(R.id.popdropcontent_right);
		lv_right.setPadding(1, 0, 0, 0);

		SimpleAdapter adapter = new SimpleAdapter(this.context, getData(),
				R.layout.popdrop1,new String [] {"category"},new int []{R.id.popdrop1}
				);
		lv_left.setAdapter(adapter);
		lv_left.setOnItemClickListener(new MyOnItemClick(this.context));
	}
	
	private ArrayList<HashMap<String, Object>> getData(){
		ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String,Object>>();
		for(int i=0; i<category.length; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("category", category[i]);
			arraylist.add(map);
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
			if(position == 0){
				DataStatus data = new DataStatus();
				data.setCate("全部分类");
			}else{
				int mark = position - 1;
				ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String,Object>>();
				for(int i=0; i<caterogy_details[mark].length; i++){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("category_details", caterogy_details[mark][i]);
					item.add(map);
				}
				
				lv_right.setVisibility(View.VISIBLE);
				
				SimpleAdapter adapter = new SimpleAdapter(this.context, item,
						R.layout.popdrop1,new String [] {"category_details"},new int []{R.id.popdrop1}
						);
				
				lv_right.setAdapter(adapter);
				lv_right.setOnItemClickListener(new MyRightOnItemClick(this.context));
			}
		}
	}
	
	class MyRightOnItemClick implements OnItemClickListener{

		Context context;
		public MyRightOnItemClick(Context c){
			this.context = c;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			DataStatus data = new DataStatus();
			if(position == 0){ 
				data.setCate("全部");
			}else {
				String value =  parent.getAdapter().getItem(position).toString();
				data.setCate("value");
				Toast.makeText(this.context, value, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
}


