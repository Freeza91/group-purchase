package com.example.dataAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.get_data.DataStatus;
import com.example.group_purchase.R;

public class DropDownDataAdapter2 extends BaseAdapter{

	Context context;
	LayoutInflater mInflater;
	ViewHolder1 view1 = null;
	LinkedList<HashMap<String, String>> listdata;
	ViewHolder2 view2;
	HashMap<String, String> map;
	private String url = DataStatus.remote_address + "/images/";
	Handler handler;
	int type;
	ImageView image;

	
	public DropDownDataAdapter2(Context context, LinkedList<HashMap<String, String>> listdata){
		
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		type = position;

		if (type == 0){
			if(convertView == null || !(convertView.getTag() instanceof ViewHolder1)){
				convertView = mInflater.inflate(R.layout.dropdown_pre2, null);
				view1 = new ViewHolder1();
				view1.location = (TextView) convertView.findViewById(R.id.location);
				convertView.setTag(view1);
			}else{
				view1 = (ViewHolder1) convertView.getTag();
			}
			HashMap<String, String> map = listdata.get(0);
			if(map.containsKey("location") && map.get("location") != null)
				view1.location.setText(map.get("location").toString());
			else
				view1.location.setText("位置加载中。。。。");
		}else{
			if(convertView == null || !(convertView.getTag() instanceof ViewHolder2)){
				convertView = mInflater.inflate(R.layout.dropdown_content2, null);
				view2 = new ViewHolder2();
				view2.name = (TextView) convertView.findViewById(R.id.shop_name);
				view2.profile= (TextView) convertView.findViewById(R.id.shop_profile);
				view2.address = (TextView) convertView.findViewById(R.id.shop_address);
				view2.avatar = (ImageView) convertView.findViewById(R.id.shop_avatar);
				convertView.setTag(view2);
				view2.avatar.setImageResource(R.drawable.ic_lanch);
			}else{
				view2 = (ViewHolder2) convertView.getTag();
				view2.avatar.setImageResource(R.drawable.ic_lanch);
			}
			map = listdata.get(type);
			view2.name.setText("店名:" + map.get("name"));
			view2.profile.setText("简介:" + map.get("profile"));
			view2.address.setText("地址：" + map.get("address"));
			view2.avatar.setTag(url + map.get("avatar").toString());
			new LoadImage(view2.avatar, url + map.get("avatar").toString()).load();;
		}
		return convertView;
	}
	
	class ViewHolder1 {
		TextView location;
	}

	class ViewHolder2 {
		ImageView avatar;
		TextView name, profile, address;
	}
}


