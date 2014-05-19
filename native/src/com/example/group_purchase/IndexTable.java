package com.example.group_purchase;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.example.shopsandgoodsList.GoodsList;
import com.example.shopsandgoodsList.ShopsList;
import com.example.useraccount.User;

@SuppressWarnings("deprecation")
public class IndexTable extends TabActivity {

	private TabHost tabHost;
	
	// 聲明TabSpac的Tag字符信息
	private String group_purchase = "group_purchase";
	private String shop = "shop";
	private String account = "account";
	private String setting = "setting";
	private Bundle bundle;
	int flag = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index_table);
		init();
		setNavigationBar();
	}

	private void init() {
		
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				tabChanged(tabId);
			}
		});
		
	}
	
	private void setNavigationBar() {
		// 實例化底部導航TabSpec對象
		TabSpec tab_group_purchase = tabHost.newTabSpec(group_purchase);
		TabSpec tab_shop = tabHost.newTabSpec(shop);
		TabSpec tab_collection= tabHost.newTabSpec(account);
		TabSpec tab_setting = tabHost.newTabSpec(setting);

		Intent intent1 = new Intent();
		Intent intent2 = new Intent();
		Intent intent3 = new Intent();
		Intent intent4 = new Intent();
		
		intent1.setClass(IndexTable.this, GoodsList.class);
		tab_group_purchase.setIndicator(
				createContent("团购", R.drawable.group_purchase_tab)
				).setContent(intent1);
		
		intent2.setClass(IndexTable.this, ShopsList.class);
		tab_shop.setIndicator(
				createContent("商家", R.drawable.shop_tab)
				).setContent(intent2);
		
		
		intent3.setClass(IndexTable.this, User.class);
		
		bundle = getIntent().getExtras();
		
		if(bundle != null){
			if(bundle.containsKey("category")){
				flag = 1;
			}else{
				intent3.putExtras(bundle);
				SaveToken(bundle.get("token").toString());
				flag = 2;
			}

		}
			
		tab_collection.setIndicator(
				createContent("帐号", R.drawable.user_tab)
				).setContent(intent3);
		
		intent4.setClass(IndexTable.this, Setting.class);
		tab_setting.setIndicator(
				createContent("设置", R.drawable.setting_tab)
				).setContent(intent4);

		tabHost.addTab(tab_group_purchase);
		tabHost.addTab(tab_shop);
		tabHost.addTab(tab_collection);
		tabHost.addTab(tab_setting);
		
		if(flag == 1){
			tabHost.setCurrentTabByTag(shop);
		}else if(flag == 2){
			tabHost.setCurrentTabByTag(account);
		}
	}
	
	
	private void tabChanged(String tabId) {
		if(tabId.equals(group_purchase)){
			tabHost.setCurrentTabByTag(group_purchase);
		}else if(tabId.equals(shop)){
			tabHost.setCurrentTabByTag(shop);
		}else if(tabId.equals(account)){
			tabHost.setCurrentTabByTag(account);
		}else if(tabId.equals(setting)){
			tabHost.setCurrentTabByTag(setting);
		}
		
	}
	
	private View createContent(String text, int resid){
		
		View view = LayoutInflater.from(this).inflate(R.layout.tabwidget, null,false);
		
		TextView tab_tv= (TextView) view.findViewById(R.id.bottom_tab_tv);
		ImageView tab_icon = (ImageView) view.findViewById(R.id.bottom_tab_img);
		tab_tv.setText(text);
		tab_icon.setBackgroundResource(resid);
		return view;
	}
	
	private void SaveToken(String token){
		SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("token", token);
		Log.d("appTag", "save chenggong");
		editor.commit();
	}
	
}
