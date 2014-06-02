package com.example.dataAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class LoadImage {

	ImageView image;
	String url;
	public LoadImage(ImageView image, String url){
		this.image = image;
		this.url = url;
	}
	
	public void load(){
		AsyncTaskImageLoad async = new AsyncTaskImageLoad(image, url);
		//执行异步加载，并把图片的路径传送过去
		async.execute();
		Log.d("appTag", "loadimage puvlic");
	}
}

class AsyncTaskImageLoad extends AsyncTask{
	private ImageView image;
	private Bitmap bmp = null; 
	private String path;
	
	public AsyncTaskImageLoad(ImageView image, String path){
		this.image = image;
		this.path = path;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
        try {  
            URL myurl = new URL(path);  
            // 获得连接  
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();  
            conn.setConnectTimeout(20000);//设置超时  
            conn.setDoInput(true);  
            conn.setUseCaches(true);//缓存  
            conn.connect();  
            InputStream is = conn.getInputStream();//获得图片的数据流  
            bmp = BitmapFactory.decodeStream(is);  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		if(bmp != null && image != null){
			Log.d("appTag", "tag" + image.getTag().toString());
			Log.d("appTag", "path" + path);
			if(image.getTag().toString().equals(path))
				image.setImageBitmap(bmp);
		}
		super.onPostExecute(result);
	}
	
	
}
