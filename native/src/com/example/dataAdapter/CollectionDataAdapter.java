package com.example.dataAdapter;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.group_purchase.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CollectionDataAdapter extends BaseAdapter{

	Context context;
	LayoutInflater mInflater;
	LinkedList<HashMap<String, String>> list;
	
	public CollectionDataAdapter(Context context, LinkedList<HashMap<String, String>> list){
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			ViewHolder view = new ViewHolder();
			convertView = mInflater.inflate(R.layout.collection_content, null);
			view.name = (TextView) convertView.findViewById(R.id.collection_name);
			view.add = (TextView) convertView.findViewById(R.id.collection_add);
			view.profile = (TextView) convertView.findViewById(R.id.collection_profile);
			HashMap<String, String> map = list.get(position);
			view.name.setText(map.get("name"));
			view.add.setText(map.get("add"));
			view.profile.setText(map.get("profile"));
			convertView.setTag(view);
		}
		return convertView;
	}
	
	class ViewHolder{
		TextView name, add, profile;
	}

}
