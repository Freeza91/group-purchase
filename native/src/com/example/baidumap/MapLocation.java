package com.example.baidumap;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class MapLocation {
	
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener;
//	private GeoPoint bit;
	private Context c = null;
	public MapLocation(Context c){
		this.c = c;
		myListener = new MyLocationListener(this.c);
	}
	
	public boolean Loaction(){
		mLocationClient = new LocationClient(c);     //声明LocationClient类
		mLocationClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();
		// option.setLocationMode(LocationMode.Device_Sensors);
		// option.setLocationMode(LocationMode.Battery_Saving);
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setOpenGps(true);
		option.setScanSpan(5000);
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		mLocationClient.setLocOption(option);
		
		mLocationClient.start();
		
		if (mLocationClient != null){
			if(mLocationClient.isStarted()){
				mLocationClient.requestLocation();
			}else{
				 Log.d("Loc", "locClient is not started");
			}
		}else{
			 Log.d("Loc", "locClient is null");
		}
		return true;
	}
}
