package com.example.jf.cardapp.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/27.
 */

public class IdentifyThread implements Runnable {
    Handler handler;
    Context context;
    Bitmap bitmap;
    public IdentifyThread(Handler handler,Context context,Bitmap bitmap){
        this.handler=handler;
        this.context=context;
        this.bitmap=bitmap;

    }
    @Override
    public void run() {
        try {
            postJson(bitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void postJson(Bitmap bitmap) throws JSONException, IOException {
        byte[] bt=Bitmap2Bytes(bitmap);
        String jsonString= Base64.encodeToString(bt, Base64.DEFAULT);
        OkHttpClient httpClient=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("imgdata",jsonString)
                .build();
        Request request = new Request.Builder()

                .post(requestBody).url("http://ec2-35-165-96-23.us-west-2.compute.amazonaws.com:80/search")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("测试","请求失败");
                Message msg=new Message();
                msg.what=2;
                msg.obj="服务器请求失败";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.e("ccc",string);
                Message msg=new Message();
                msg.what=2;
                msg.obj=string;
                handler.sendMessage(msg);
            }
        });
    }
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
