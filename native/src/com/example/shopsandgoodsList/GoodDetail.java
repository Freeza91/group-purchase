package com.example.shopsandgoodsList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.get_data.DataStatus;
import com.example.get_data.HttpRequest;
import com.example.get_data.NetWork;
import com.example.get_data.ResponedData;
import com.example.group_purchase.R;

public class GoodDetail extends Activity{
	private TextView name, price, integration, profile, note, service;
	private ImageView avatar;
	private Button buy;
	HashMap<String, String> map = null;
	private boolean flag = false;
    private ProgressDialog progressDialog;
    Handler handler;
    String url = DataStatus.remote_address + "/images/";

    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_detail);
		
		init_ui();
		addContent();
	}
	
	private void init_ui(){
		name = (TextView) findViewById(R.id.good_detail_name);
		price = (TextView) findViewById(R.id.good_detail_price);
		integration= (TextView) findViewById(R.id.good_detail_integration);
		profile = (TextView) findViewById(R.id.good_detail_profile);
		note= (TextView) findViewById(R.id.good_detail_note);
		service = (TextView) findViewById(R.id.good_detail_service);
		
		avatar = (ImageView) findViewById(R.id.good_detail_avatar);
		buy = (Button) findViewById(R.id.buy);
		
	}
	
	private void addContent(){
		Bundle b = getIntent().getExtras();
		map = ResponedData.list_good.get(b.getInt("num") - 1);
		name.setText("商品名称: " + map.get("name").toString());
		price.setText("商品价格： " + map.get("price").toString());
		integration.setText("积分： " + map.get("integration").toString());
		profile.setText("商品简介： \n" + map.get("profile").toString());
		note.setText("备注： \n" + map.get("note").toString());
		service.setText("服务： \n" + map.get("service").toString());

		Toast.makeText(getApplicationContext(), map.get("name").toString(), Toast.LENGTH_SHORT).show();

		
		buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!flag){
					if(NetWork.isNetworkAvailable(GoodDetail.this)){
						new BuyAsyncTask().execute();
					}else{
						Toast.makeText(GoodDetail.this, "无法链接网络", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(GoodDetail.this, "你已经购买过了！！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		handler = new Handler(){
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
            	 Log.d("appTag", map.get("avatar").toString());
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
	
	class BuyAsyncTask extends AsyncTask<String, Integer, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			SharedPreferences sp = GoodDetail.this.getSharedPreferences("token", MODE_PRIVATE);
			String token = sp.getString("token", null);
			HashMap<String, String> mm = new HashMap<String, String>();
			mm.put("integration", map.get("integration").toString());
			mm.put("token", token);
			HttpRequest request = new HttpRequest(mm);
			request.Post("/buy");
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(GoodDetail.this);
		    progressDialog.setTitle("提示信息");
	        progressDialog.setMessage("正在交易中，请稍后......");
	        //设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
	        progressDialog.setCancelable(false);
	        //设置ProgressDialog样式为圆圈的形式
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressDialog.show();
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			String status = ResponedData.mapresponse.get("message").toString();
			if(status.equals("nohttpconnect")){
				Toast.makeText(GoodDetail.this, "连接超时！！", Toast.LENGTH_SHORT).show();
			}else if(status.equals("success")){
				Toast.makeText(GoodDetail.this, "购买成功", Toast.LENGTH_SHORT).show();
				flag = true;
			}else if(status.equals("failed")){
				Toast.makeText(GoodDetail.this, "没有登录！！！请登录后再试！！",Toast.LENGTH_SHORT).show();
			}
			progressDialog.dismiss();
		}
	}
}
