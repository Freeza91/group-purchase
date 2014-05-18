package com.example.baidumap;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		if (location == null)
			return;
		
		MapData.error_code = location.getLocType();
		MapData.lat = location.getLatitude();
		MapData.lon = location.getLongitude();
		MapData.radius = location.getRadius();
		
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
		Log.d("getLocType()", location.getLocType()+" ");
		MapData.ddr = location.getAddrStr();
		MapData.district = location.getDistrict();
		MapData.street = location.getStreet();
		if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndistrict" + location.getDistrict());
			sb.append("\ndistree" + location.getStreet());
		}
		Log.d("LocSDK3", sb.toString());

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