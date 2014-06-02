package com.example.collectionList;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.dataAdapter.CollectionDataAdapter;
import com.example.group_purchase.R;

public class CollectionList extends Activity {

	private ListView lv;
	private LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String,String>>();
	private CollectionDataAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		lv = (ListView) findViewById(R.id.collectionlist);
		
		loadData();
		adapter = new CollectionDataAdapter(this, list);
		lv.setAdapter(adapter);
//		deleteData();
		
	}
	
	private void loadData(){
		SharedPreferences sp = getSharedPreferences("collection", MODE_PRIVATE);
		int size = sp.getInt("size", 0);
		for(int i=1; i<=size; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", sp.getString("name" + i, null));
			map.put("add", sp.getString("add" + i, null));
			map.put("profile", sp.getString("profile" + i, null));
			list.add(map);
		}
	}
	
	private void deleteData(){
		SharedPreferences sp = getSharedPreferences("collection", MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.clear();
		e.putInt("size", 0);
		e.commit();
	}
}
