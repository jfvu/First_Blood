package com.example.jf.cardapp.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/7.
 */

public class UpdateThread implements Runnable {
    Handler handler;
    Context context;
    String version;
    public UpdateThread(Handler handler, Context context,String version){
        this.handler=handler;
        this.context=context;
        this.version=version;
    }

    @Override
    public void run() {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url("http://60.205.177.241:8001/api/common/CheckUpdate").build();
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
                    Log.e("版本号",version);
                    String newversion=object.getString("Data");
                    Log.e("data",newversion+"");
                    if(!newversion.equals(version)){
                        Message msg=new Message();
                        msg.what=4;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
