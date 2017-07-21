package com.example.jf.cardapp.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.jf.cardapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/15.
 */

public class GroupCardThread implements Runnable {
    private Handler handler;
    private Context context;
    public GroupCardThread(Handler handler,Context context){
        this.handler=handler;
        this.context=context;
    }
    @Override
    public void run() {
        final Request.Builder builder = new Request.Builder().url("http://60.205.177.241:8001/api/Card/GroupCardList?pagesize=100&pageindex=1");
        final Request request = builder.build();
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build(); //设置各种超时时间
        final Call call = client.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if (response != null) {
                        String string = response.body().string();
                        Message message=new Message();
                        message.what=0;
                        message.obj=string;
                        handler.sendMessage(message);
                        Log.e("sss",string);
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
