package com.example.jf.cardapp.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.jf.cardapp.entity.Banners;
import com.example.jf.cardapp.utils.ACache;
import com.example.jf.cardapp.view.SimpleCycleViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/6.
 */

public class BannerThread implements Runnable{
    Banners banners;
    List<Banners> list=new ArrayList<>();
    Handler handler;
    Context context;
    ACache aCache;
    ArrayList<byte[]> bytelist;
    public BannerThread(Handler handler,Context context){
        this.handler=handler;
        this.context=context;
    }
    @Override
    public void run() {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url("http://60.205.177.241:8001/api/Card/FirstPage").build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String htmlStr =  response.body().string();
                try {
                    JSONObject object=new JSONObject(htmlStr);
                    JSONObject object2=new JSONObject(object.getString("Data"));
                    JSONArray object1=new JSONArray(object2.getString("Banners"));
                    for(int i=0;i<object1.length();i++){
                        JSONObject object3=object1.getJSONObject(i);
                        Log.e("ccc",object3.getString("Pic"));
                        banners=new Banners(object3.getInt("Type"),"http://60.205.177.241"+object3.getString("Pic"),object3.getString("Title"),object3.getString("Url"));
                        list.add(banners);
                    }
                    Log.e("List11",list.size()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                            List<Bitmap>bitmapList=new ArrayList<Bitmap>();
//                            ArrayList<byte[]>list2=new ArrayList<byte[]>();
                            for(Banners pic:list){
                                byte[] b1= new byte[0];
                                Bitmap bitmap=getBitmap(pic.getPic());
                                bitmapList.add(bitmap);
//                                    b1 = getBytes(getBitmap(pic.getPic()));


                            }
                            Message msg=new Message();
                            msg.what=2;
                            msg.obj=list;
                            handler.sendMessage(msg);
                            Message message=new Message();
                            message.what=3;
                            message.obj=bitmapList;
                            handler.sendMessage(message);



            }
        });


    }
    public static Bitmap getBitmap(String path) throws IOException{

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        Bitmap bitmap = null;
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }else{
            Log.e("...","超时");
        }
        return bitmap;
    }
    public static byte[] getBytes(Bitmap bitmap){
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }

}
