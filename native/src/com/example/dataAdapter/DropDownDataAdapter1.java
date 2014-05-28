package com.example.dataAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dataAdapter.DropDownDataAdapter2.ViewHolder2;
import com.example.get_data.DataStatus;
import com.example.group_purchase.IndexTable;
import com.example.group_purchase.R;
import com.example.shopsandgoodsList.GoodDetail;
import com.example.shopsandgoodsList.GoodsList;
import com.example.shopsandgoodsList.ShopsList;
import com.example.view.MyGridView;

public class DropDownDataAdapter1 extends BaseAdapter{

	Context context;
	LayoutInflater mInflater;
	LinkedList<HashMap<String, String>> listdata;
	ViewHolder2 view2;
	HashMap<String, String> map;
	private String url = DataStatus.remote_address + "/images/";
	Handler handler;
	int type;
	
	public DropDownDataAdapter1(Context context, LinkedList<HashMap<String, String>> listdata){
		
		this.context = context;
		this.listdata = listdata;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder1 view1 = null;
		type = position;

		if (type == 0){
			convertView = mInflater.inflate(R.layout.dropdown_pre1, null);
			view1 = new ViewHolder1();
			view1.gridView = (MyGridView)convertView.findViewById(R.id.grid);
			view1.tv = (TextView) convertView.findViewById(R.id.guess);
			convertView.setTag(view1);
			view1.gridView.setSelector(R.drawable.grid_content);
			setGridView(view1.gridView);
			view1.tv.setText("猜你喜欢");
		}else{
			convertView = mInflater.inflate(R.layout.dropdown_content1, null);
			view2 = new ViewHolder2();
			view2.name = (TextView) convertView.findViewById(R.id.good_name);
			view2.profile= (TextView) convertView.findViewById(R.id.good_profile);
			view2.price = (TextView) convertView.findViewById(R.id.good_price);
			view2.avatar = (ImageView) convertView.findViewById(R.id.good_avatar);
			map = listdata.get(type);
			view2.avatar.setContentDescription("url:" + map.get("url"));
			Log.d("appTag", map.get("avatar").toString());

			view2.name.setText("名字:" + map.get("name"));
			view2.profile.setText("简介：" + map.get("profile"));
			view2.price.setText("价格：" + map.get("price"));
			convertView.setTag(view2);
			
			handler= new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					Bitmap bmp=(Bitmap)msg.obj;  
					view2.avatar.setImageBitmap(bmp);;
				}
				
			};

		
		  new Thread(new Runnable() {  
			  
              @Override  
              public void run() {  
                  // TODO Auto-generated method stub  
                  Bitmap bmp = getURLimage(url + map.get("avatar").toString());  
                  Message msg = new Message();  
                  msg.what = type;  
                  msg.obj = bmp;  
                  handler.sendMessage(msg);  
              }  
          }).start();  
		}
		return convertView;
	}
	
	class ViewHolder1 {
		MyGridView gridView;
		TextView tv;
	}

	class ViewHolder2 {
		ImageView avatar;
		TextView name, profile, price;
	}
	
    private Bitmap getURLimage(String image_url) {  
        Bitmap bmp = null;  
        try {  
            URL myurl = new URL(image_url);  
            // 获得连接  
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();  
            conn.setConnectTimeout(6000);//设置超时  
            conn.setDoInput(true);  
            conn.setUseCaches(true);//缓存  
            conn.connect();  
            InputStream is = conn.getInputStream();//获得图片的数据流  
            bmp = BitmapFactory.decodeStream(is);  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bmp;  
    } 
	
	private void setGridView(GridView grid){
		ArrayList<HashMap<String, Object>> gridItem = new ArrayList<HashMap<String,Object>>();
		
		String category [] = {"美食", "电影", "酒店", "ktv", "丽人", "休闲娱乐"};
		int image [] = {R.drawable.ic_category_0, R.drawable.ic_category_1, R.drawable.ic_category_2,
						R.drawable.ic_category_3, R.drawable.ic_category_4, R.drawable.ic_category_5};
		
		for(int i=0; i<image.length; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("image", image[i]);
			map.put("category", category[i]);
			gridItem.add(map);
		}
		
		SimpleAdapter gridAdapter= new SimpleAdapter(this.context,  
                gridItem,//数据来源   
                R.layout.gridview_content,
                  
                //动态数组与ImageItem对应的子项          
                new String[] {"image","category"},   
                  
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                new int[] {R.id.gridItemImage, R.id.gridItemText});  
		//添加并且显示  
		
		grid.setAdapter(gridAdapter);  
		//添加消息处理  
		grid.setOnItemClickListener(new ItemClickListener(this.context)); 
	}
}

class ItemClickListener  implements OnItemClickListener  {

	private Context c;
	
	public ItemClickListener(Context c){
		this.c = c;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> item=(HashMap<String, Object>) parent.getItemAtPosition(position);
		DataStatus.category =  item.get("category").toString();
		Bundle b = new Bundle();
		b.putBoolean("category", true);
		Intent intent = new Intent(c, IndexTable.class);
		intent.putExtras(b);
		this.c.startActivity(intent);

	}
}
