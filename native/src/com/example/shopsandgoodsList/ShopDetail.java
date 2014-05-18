package com.example.shopsandgoodsList;

import com.example.group_purchase.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopDetail extends Activity{
	private TextView name, tel, profile, category, rating;
	private ImageView avatar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);
		
		init_ui();
		
	}
	
	private void init_ui(){
		name = (TextView) findViewById(R.id.shop_detail_name);
		tel = (TextView) findViewById(R.id.shop_detail_tel);
		profile = (TextView) findViewById(R.id.shop_detail_profile);
		category = (TextView) findViewById(R.id.shop_detail_category);
		rating = (TextView) findViewById(R.id.shop_detail_rating);
		
		avatar = (ImageView) findViewById(R.id.shop_detail_avatar);

	}
	
}
