package com.example.shopsandgoodsList;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class GoodDetail extends Activity{
	private TextView name, price, integration, profile, note, service;
	private ImageView avatar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_detail);
		
		init_ui();
		addContent();
	}
	
	private void init_ui(){
		name = (TextView) findViewById(R.id.good_detail_name);
		price = (TextView) findViewById(R.id.good_detail_price);
		integration= (TextView) findViewById(R.id.good_detail_integration);
		profile = (TextView) findViewById(R.id.good_detail_profile);
		note= (TextView) findViewById(R.id.good_detail_note);
		service = (TextView) findViewById(R.id.good_detail_service);
		
		avatar = (ImageView) findViewById(R.id.good_detail_avatar);
	}
	
	private void addContent(){
		Bundle b = getIntent().getExtras();
		HashMap<String, String> map = ResponedData.list_good.get(b.getInt("num") - 1);
		Toast.makeText(getApplicationContext(), b.getInt("num") + "", Toast.LENGTH_SHORT).show();
		name.setText(map.get("name"));
		price.setText(map.get("price"));
		integration.setText(map.get("integration"));
		profile.setText(map.get("profile"));
		note.setText(map.get("note"));
		service.setText(map.get("service"));
	}
}
