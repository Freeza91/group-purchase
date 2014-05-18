package com.example.baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MKOfflineMapListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.group_purchase.R;

public class MapPlan extends Activity {
	
	private BMapManager mMapManager = null;  
    private MKSearch mMKSearch = null;  
    
    private Button bus, walk, drive;
    private TextView from, to, plan;
    private MapView map;
    private MKPlanNode start, end;
    private Handler handler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMapManager = new BMapManager(getApplication());
		mMapManager.init(null);
		
		setContentView(R.layout.activity_map_plan);
		
		init_UI();
		init_map();
		addListener();
		
		mMKSearch = new MKSearch();  
		mMKSearch.init(mMapManager, new MySearchListener(MapPlan.this,map));//注意，MKSearchListener只支持一个，以最后一次设置为准  

		setHandler();
	}
	
	private void init_UI(){
		bus = (Button) findViewById(R.id.bus);
		walk = (Button) findViewById(R.id.walk);
		drive = (Button) findViewById(R.id.drive);
		
		from = (TextView) findViewById(R.id.from);
		to = (TextView) findViewById(R.id.to);
		plan = (TextView) findViewById(R.id.plan);
		
		map = (MapView) findViewById(R.id.bmap);
	}
	
	private void init_map(){
		
		map.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = map.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放\
		GeoPoint bit = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));  
//		GeoPoint bit = new GeoPoint((int) (39.964692 * 1E6), (int) (116.323857 * 1E6));
		GeoPoint point = bit;

		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(16);// 设置地图zoom级别
		
		MKOfflineMap mOffline = null;
		mOffline = new MKOfflineMap();  
		//offline 实始化方法用更改。  
		mOffline.init(mMapController, new MKOfflineMapListener() {  
		    @Override  
		    public void onGetOfflineMapState(int type, int state) {  
		        switch (type) {  
		        case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:  
		            {  
//		                MKOLUpdateElement update = mOffline.getUpdateInfo(state);  
		                //mText.setText(String.format("%s : %d%%", update.cityName, update.ratio));  
		            }  
		            break;  
		        case MKOfflineMap.TYPE_NEW_OFFLINE:  
		            Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));  
		            break;  
		        case MKOfflineMap.TYPE_VER_UPDATE:  
		            Log.d("OfflineDemo", String.format("new offlinemap ver"));  
		            break;  
		        }      
		          }  
		});  
		
		start = new MKPlanNode();
		end = new MKPlanNode();
	}
	
	private void addListener(){
		
		bus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start.pt = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
				end.pt = new GeoPoint(40057031, 116307852);// 设置驾车路线搜索策略，时间优先、费用最少或距离最短  
				mMKSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);  
				mMKSearch.transitSearch("北京", start, end);  
				update_plan("bus");

			}
		});
		
		walk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start.pt = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
				end.pt = new GeoPoint(40057031, 116307852);// 设置驾车路线搜索策略，时间优先、费用最少或距离最短  
				mMKSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);  
				mMKSearch.walkingSearch(null, start, null, end); 
				update_plan("walk");

			}
		});
		
		drive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start.pt = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
				end.pt = new GeoPoint(40057031, 116307852);// 设置驾车路线搜索策略，时间优先、费用最少或距离最短  
				mMKSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);  
				mMKSearch.drivingSearch(null, start, null, end);
				update_plan("drive");

			}
		});
	}
	
	
	private void setHandler(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				plan.setText(MapData.line);
				super.handleMessage(msg);
			}
			
		};
		
	}
	private void update_plan(String way){

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					if(MapData.line != null && MapData.line.length() > 0){
						Message msg = new Message();
						handler.sendMessage(msg);
						break;
					}
				}
			}
		}).start();
	}
	
	@Override
	protected void onDestroy() {
		map.destroy();
		if (mMapManager != null) {
			mMapManager.destroy();
			mMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		map.onPause();
		if (mMapManager != null) {
			mMapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		map.onResume();
		if (mMapManager != null) {
			mMapManager.start();
		}
		super.onResume();
	}
}
