package com.example.shopsandgoodsList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baidumap.MapPlan;
import com.example.get_data.DataStatus;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class ShopDetail extends Activity {
	private TextView name, tel, profile, category, address;
	private RatingBar rating;
	private ImageView avatar;
	private Button nav, collection;
	HashMap<String, String> map = null;
	private int size = 0;
	Handler handler;
	String url = DataStatus.remote_address + "/images/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);

		init_ui();
		addContent();
	}

	private void init_ui() {
		name = (TextView) findViewById(R.id.shop_detail_name);
		address = (TextView) findViewById(R.id.shop_detail_address);
		tel = (TextView) findViewById(R.id.shop_detail_tel);
		profile = (TextView) findViewById(R.id.shop_detail_profile);
		category = (TextView) findViewById(R.id.shop_detail_category);
		rating = (RatingBar) findViewById(R.id.shop_detail_rating);

		avatar = (ImageView) findViewById(R.id.shop_detail_avatar);

		nav = (Button) findViewById(R.id.nav);
		collection = (Button) findViewById(R.id.collection);
	}

	private void addContent() {
		Bundle b = getIntent().getExtras();
		map = ResponedData.list_shop.get(b.getInt("num") - 1);
		name.setText("店面: " + map.get("name"));
		tel.setText("电话: " + map.get("shop_tel"));
		profile.setText("简介： \n " + map.get("profile"));
		category.setText("类别： " + map.get("category"));
		rating.setRating(Float.parseFloat(map.get("rating")));
		address.setText("地址: " + map.get("address"));

		collection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences sp = ShopDetail.this.getSharedPreferences(
						"collection", MODE_PRIVATE);
				SharedPreferences.Editor e = sp.edit();
				size = sp.getInt("size", 0);
				if ((!DataStatus.collection_flag || size <= 0) && map != null) {
					size++;
					e.putString("name" + size, map.get("name").toString());
					e.putString("add" + size, map.get("address").toString());
					e.putString("profile" + size, map.get("profile").toString());
					e.putInt("size", size);
					e.commit();
					Toast.makeText(getApplicationContext(), "添加成功",
							Toast.LENGTH_SHORT).show();
					DataStatus.collection_flag = true;
				} else {
					e.remove("name" + size);
					e.remove("add" + size);
					e.remove("profile" + size);
					size--;
					e.putInt("size", size);
					e.commit();
					Toast.makeText(getApplicationContext(), "取消成功",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		nav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShopDetail.this, MapPlan.class);
				Bundle b = new Bundle();
				b.putDouble("lat",
						Double.parseDouble(map.get("lat")));
				b.putDouble("lon",
						Double.parseDouble(map.get("lon")));

				b.putString("to", map.get("address").toString());
				b.putString("name", map.get("name").toString());

				Log.d("appTag", "details" + map.get("address").toString());
				intent.putExtras(b);
				ShopDetail.this.startActivity(intent);
			}
		});
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Bitmap bmp=(Bitmap)msg.obj;  
				avatar.setImageBitmap(bmp);			
			}

		};

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bitmap bmp = getURLimage(url + map.get("avatar").toString());
				Message msg = new Message();
				msg.obj = bmp;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private Bitmap getURLimage(String image_url) {
		Bitmap bmp = null;
		try {
			URL myurl = new URL(image_url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
			conn.setConnectTimeout(6000);// 设置超时
			conn.setDoInput(true);
			conn.setUseCaches(true);// 缓存
			conn.connect();
			InputStream is = conn.getInputStream();// 获得图片的数据流
			bmp = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmp;
	}
}
