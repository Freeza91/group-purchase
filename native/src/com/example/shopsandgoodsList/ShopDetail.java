package com.example.shopsandgoodsList;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baidumap.MapPlan;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class ShopDetail extends Activity{
	private TextView name, tel, profile, category, rating;
	private ImageView avatar;
	private Button nav;
	HashMap<String, String> map = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		
		init_ui();
		addContent();
	}
	
	private void init_ui(){
		name = (TextView) findViewById(R.id.shop_detail_name);
		tel = (TextView) findViewById(R.id.shop_detail_tel);
		profile = (TextView) findViewById(R.id.shop_detail_profile);
		category = (TextView) findViewById(R.id.shop_detail_category);
		rating = (TextView) findViewById(R.id.shop_detail_rating);
		
		avatar = (ImageView) findViewById(R.id.shop_detail_avatar);
		
		nav = (Button) findViewById(R.id.nav);
		
		
	}
	
	private void addContent(){
		Bundle b = getIntent().getExtras();
		map = ResponedData.list_shop.get(b.getInt("num") - 1);
		Toast.makeText(getApplicationContext(), b.getInt("num") + "", Toast.LENGTH_SHORT).show();
		name.setText(map.get("name"));
		tel.setText(map.get("tel"));
		profile.setText(map.get("profile"));
		category.setText(map.get("category"));
		rating.setText(map.get("rating"));
		
		nav.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ShopDetail.this, MapPlan.class);
						Bundle b = new Bundle();
						b.putDouble("lat", Double.parseDouble(map.get("lat").toString()));
						b.putDouble("lon", Double.parseDouble(map.get("lon").toString()));
						
						b.putString("to", map.get("address").toString());
						b.putString("name", map.get("name").toString());
						
						Log.d("appTag",map.get("lat").toString());
						intent.putExtras(b);
						ShopDetail.this.startActivity(intent);
					}
		});
	}
	
}
