package com.example.baidumap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {

	SharedPreferences sp;
	SharedPreferences.Editor e;
	Context c;
	
	public MyLocationListener(Context c){
		this.c = c;
		sp = c.getSharedPreferences("MapData", Context.MODE_PRIVATE);
		e = sp.edit();
	}
	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		if (location == null)
			return;
		
		MapData data = new MapData(this.c);
		data.error_code = location.getLocType();
		data.lat = location.getLatitude();
		data.lon = location.getLongitude();
		data.radius = location.getRadius();
		
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		data.ddr = location.getAddrStr();
		data.district = location.getDistrict();
		data.street = location.getStreet();
		Log.d("appTag", data.ddr);
		if(data.ddr != null && data.ddr.length() > 0){
			Log.d("appTag", "save start");
			data.save();
			
		}
		if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndistrict" + location.getDistrict());
			sb.append("\ndistree" + location.getStreet());
		}
	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
		// TODO Auto-generated method stub
		if (poiLocation == null) {
			return;
		}
		StringBuffer sb = new StringBuffer(256);
		sb.append("Poi time : ");
		sb.append(poiLocation.getTime());
		sb.append("\nerror code : ");
		sb.append(poiLocation.getLocType());
		sb.append("\nlatitude : ");
		sb.append(poiLocation.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(poiLocation.getLongitude());
		sb.append("\nradius : ");
		sb.append(poiLocation.getRadius());
		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(poiLocation.getAddrStr());
		}
		if (poiLocation.hasPoi()) {
			sb.append("\nPoi:");
			sb.append(poiLocation.getPoi());
		} else {
			sb.append("noPoi information");
		}
		Log.d("LocSDK3", sb.toString());
	}
}