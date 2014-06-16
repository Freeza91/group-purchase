package com.example.dataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dataAdapter.CollectionDataAdapter.ViewHolder;
import com.example.get_data.DataStatus;
import com.example.group_purchase.IndexTable;
import com.example.group_purchase.R;
import com.example.view.MyGridView;

public class DropDownDataAdapter1 extends BaseAdapter{

	Context context;
	LayoutInflater mInflater;
	LinkedList<HashMap<String, String>> listdata;
	ViewHolder2 view2;
	HashMap<String, String> map;
	private String url = DataStatus.remote_address + "/images/";
	Handler handler;
	int type;
	
	public DropDownDataAdapter1(Context context, LinkedList<HashMap<String, String>> listdata){
		
		this.context = context;
		this.listdata = listdata;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if(position > 0){
			return 1;
		}
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder1 view1 = null;
		type = position;

		if (type == 0){
			if(convertView == null || !(convertView.getTag() instanceof ViewHolder1)){
				convertView = mInflater.inflate(R.layout.dropdown_pre1, null);
				view1 = new ViewHolder1();
				view1.gridView = (MyGridView)convertView.findViewById(R.id.grid);
				view1.tv = (TextView) convertView.findViewById(R.id.guess);
				convertView.setTag(view1);
			}else{
				view1 = (ViewHolder1) convertView.getTag();
			}
			view1.gridView.setSelector(R.drawable.grid_content);
			setGridView(view1.gridView);
			view1.tv.setText("猜你喜欢");
		}else{
			if(convertView == null || !(convertView.getTag() instanceof ViewHolder2 )){
				convertView = mInflater.inflate(R.layout.dropdown_content1, null);
				view2 = new ViewHolder2();
				view2.name = (TextView) convertView.findViewById(R.id.good_name);
				view2.profile= (TextView) convertView.findViewById(R.id.good_profile);
				view2.price = (TextView) convertView.findViewById(R.id.good_price);
				view2.avatar = (ImageView) convertView.findViewById(R.id.good_avatar);
				convertView.setTag(view2);
				view2.avatar.setImageResource(R.drawable.ic_lanch);

			}else{
				view2 = (ViewHolder2) convertView.getTag();
				view2.avatar.setImageResource(R.drawable.ic_lanch);

			}
			map = listdata.get(type);

			view2.name.setText("名字:" + map.get("name"));
			view2.profile.setText("简介：" + map.get("profile"));
			view2.price.setText("价格：" + map.get("price"));
			view2.avatar.setTag(url + map.get("avatar").toString());
			new LoadImage(view2.avatar, url + map.get("avatar").toString()).load();;
		}
		return convertView;
	}
	
	class ViewHolder1 {
		MyGridView gridView;
		TextView tv;
	}

	class ViewHolder2 {
		ImageView avatar;
		TextView name, profile, price;
	}
	
	private void setGridView(GridView grid){
		ArrayList<HashMap<String, Object>> gridItem = new ArrayList<HashMap<String,Object>>();
		
		String category [] = {"美食", "电影", "酒店", "ktv", "丽人", "休闲娱乐"};
		int image [] = {R.drawable.ic_category_0, R.drawable.ic_category_1, R.drawable.ic_category_2,
						R.drawable.ic_category_3, R.drawable.ic_category_4, R.drawable.ic_category_5};
		
		for(int i=0; i<image.length; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("image", image[i]);
			map.put("category", category[i]);
			gridItem.add(map);
		}
		
		SimpleAdapter gridAdapter= new SimpleAdapter(this.context,  
                gridItem,//数据来源   
                R.layout.gridview_content,
                  
                //动态数组与ImageItem对应的子项          
                new String[] {"image","category"},   
                  
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                new int[] {R.id.gridItemImage, R.id.gridItemText});  
		//添加并且显示  
		
		grid.setAdapter(gridAdapter);  
		//添加消息处理  
		grid.setOnItemClickListener(new ItemClickListener(this.context)); 
	}
}

class ItemClickListener  implements OnItemClickListener  {

	private Context c;
	
	public ItemClickListener(Context c){
		this.c = c;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> item=(HashMap<String, Object>) parent.getItemAtPosition(position);
		DataStatus.category =  item.get("category").toString();
		Bundle b = new Bundle();
		b.putBoolean("category", true);
		Intent intent = new Intent(c, IndexTable.class);
		intent.putExtras(b);
		this.c.startActivity(intent);

	}
}
