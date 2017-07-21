package com.example.jf.cardapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.adapter.MyAdapter;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.entity.CollectCard;
import com.example.jf.cardapp.entity.PeopleData;
import com.example.jf.cardapp.utils.OnItemClickListener;
import com.example.jf.cardapp.view.ItemRemoveRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jf on 2017/2/22.
 */

public class MycollectActivity extends BaseActivity {
    private ItemRemoveRecyclerView recyclerView;
    private String AuthenticToken=null;
    private ArrayList<CollectCard>mList;
    boolean f=false;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    mList.clear();
                    String string = (String) message.obj;
                    CollectCard card;
                    try {
                        JSONObject object=new JSONObject(string);
                        JSONArray array=new JSONArray(object.getString("Data"));
                        for(int i=0;i<array.length();i++){
                            JSONObject object1=array.getJSONObject(i);
                            int id=object1.getInt("ID");
                            String pic=object1.getString("Pic");
                            String name=object1.getString("Name");
                            card=new CollectCard(id,pic,name);
                            Log.e("card",card.toString());
                            mList.add(card);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final MyAdapter adapter = new MyAdapter(MycollectActivity.this, mList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MycollectActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            String txt=mList.get(position).getName();
                            Pattern p = Pattern.compile("^[\u4e00-\u9fff]+$");
                           Matcher m = p.matcher(txt);


                            if (m.matches()) {
                                f = true;
                            }
                            Intent intent=new Intent(MycollectActivity.this,DetailsActivity.class);
                            intent.putExtra("ID",mList.get(position).getId());
                            Log.e("idid", String.valueOf(mList.get(position).getId()));
                            intent.putExtra("language",f);
                            startActivity(intent);
//                            finish();
//                            Toast.makeText(MainActivity.this, "** " + mList.get(position) + " **", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            final Request.Builder builder = new Request.Builder().url("http://60.205.177.241:8001/api/Card/CardAttention?ID="+mList.get(position).getId()+"&type="+0);
                            builder.addHeader("AuthenticToken",AuthenticToken);  //将请求头以键值对形式添加，可添加多个请求头
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
                                        } else {

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            adapter.removeItem(position);
                            Toast.makeText(MycollectActivity.this,"以取消收藏",Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
            }
            return false;

        }
    });
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycollect;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mList=new ArrayList<>();
        recyclerView = (ItemRemoveRecyclerView) findViewById(R.id.id_item_remove_recyclerview);
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        AuthenticToken=sharedPreferences.getString("AuthenticToken","noall");
        if(!AuthenticToken.equals("noall")){
            CollectJson();
        }else{
            Toast.makeText(MycollectActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }


    }

    private void CollectJson() {
        final Request.Builder builder = new Request.Builder().url("http://60.205.177.241:8001/api/Card/MyCardAttention");
        builder.addHeader("AuthenticToken",AuthenticToken);  //将请求头以键值对形式添加，可添加多个请求头
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
//                        Log.d("dddd",string);
                        Message msg=new Message();
                        msg.what=0;
                        msg.obj=string;
                        handler.sendMessage(msg);
                    } else {
                        Toast.makeText(MycollectActivity.this,"异常",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
