package com.example.baidumap;

import android.app.Activity;
import android.util.Log;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKLine;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoutePlan;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRoutePlan;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;

public class MySearchListener implements MKSearchListener {

	private MapView map;
	private Activity a;
	
	public MySearchListener(Activity a, MapView map){
		this.map = map;
		this.a = a;
	}
	@Override
	public void onGetAddrResult(MKAddrInfo result, int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetBusDetailResult(MKBusLineResult result, int error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetDrivingRouteResult(MKDrivingRouteResult result, int error) {
		// TODO Auto-generated method stub
		 if (result == null) {  
             return;  
		 }  
		 RouteOverlay routeOverlay = new RouteOverlay(a, map);  // 此处仅展示一个方案作为示例  
		 routeOverlay.setData(result.getPlan(0).getRoute(0));  
		 map.getOverlays().add(routeOverlay);  
		 map.refresh();  
	}

	@Override
	public void onGetPoiDetailSearchResult(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetTransitRouteResult(MKTransitRouteResult result, int error) {
		// TODO Auto-generated method stub
		 if (result == null) {  
             return;  
		 }  
		 TransitOverlay transitOverlay = new TransitOverlay(a, map);  // 此处仅展示一个方案作为示例  
		 transitOverlay.setData(result.getPlan(0));  
		 map.getOverlays().add(transitOverlay); 
		 map.refresh(); 
		 setLineInfor(result.getPlan(0));
	}

	@Override
	public void onGetWalkingRouteResult(MKWalkingRouteResult result, int error) {
		// TODO Auto-generated method stub
		 if (result == null) {  
             return;  
		 }  
		 RouteOverlay routeOverlay = new RouteOverlay(a, map);  // 此处仅展示一个方案作为示例  
		 routeOverlay.setData(result.getPlan(0).getRoute(0));  
		 map.getOverlays().add(routeOverlay);  
		 map.refresh();  
	}
	
	private void setLineInfor(MKTransitRoutePlan plan){
		int total = plan.getNumLines() + plan.getNumRoute();
		String line = "";
		boolean flag = false;
		//refer 
		//http://www.cnblogs.com/jz1108/archive/2011/11/06/2238119.html
		for(int i=0; i<total; i++){
			if(i % 2 == 0){
				// i为偶数
                // 处理第一个步行描述逻辑
				if(i / 2 == 0){
                    line = "从" + "起点出发";
                }
                // 处理最后一个步行描述逻辑
                if (i / 2 == plan.getNumRoute() - 1) {
                    flag = true;
                }
                if (plan.getRoute(i / 2).getDistance() > 0) {
                	line += "步行约" + plan.getRoute(i / 2).getDistance() + "米至";
				}
			}else{
				MKLine way = plan.getLine((i - 1)/2);
				 if (plan.getRoute((i - 1) / 2).getDistance() > 0) {
					 line += way.getGetOnStop().name + "\n";
                 }
				line += "乘坐" + way.getTitle() + ",";
				line += "经过" + way.getNumViaStops() + "站,";
				line += "在" +  way.getGetOffStop().name + "下车\n";
			}
			if(flag){
				line += "终点。";
			}
		}
		MapData.line = line;
	}
}
