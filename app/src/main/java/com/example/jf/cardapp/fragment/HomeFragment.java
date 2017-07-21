package com.example.jf.cardapp.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.activity.DetailsActivity;
import com.example.jf.cardapp.activity.MatchActivity;
import com.example.jf.cardapp.activity.NewsActivity;
import com.example.jf.cardapp.adapter.RecommendedAdapter;
import com.example.jf.cardapp.entity.Banners;
import com.example.jf.cardapp.entity.Magic;
import com.example.jf.cardapp.manager.FullyGridLayoutManager;
import com.example.jf.cardapp.thread.BannerThread;
import com.example.jf.cardapp.thread.UpdateThread;
import com.example.jf.cardapp.utils.ACache;
import com.example.jf.cardapp.utils.HttpUtils;
import com.example.jf.cardapp.view.SimpleCycleViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2017/2/11.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener{
    private SimpleCycleViewPager viewpager;
    private RecyclerView list_recommend;
    private RecommendedAdapter adapter;
    private boolean isLoop=true;
    private ScrollView scrollView;
    private TextView news;
    private TextView match;
    private TextView cardgroup;
    private Magic magic;
    private List<Magic>list;
    private InformationFragment informationFragment;
    private String newsString="";//新闻json
    private String matchString="";//赛事json
    private String bannerString="";//Bannerjson
    ArrayList<byte[]> bytelist;
    List<Banners>bannersList;
    private int flage;
    private ACache aCache;
    float x1 = 0;
    float x2 = 0;
    private Intent updataService ;
    private static int REQUESTPERMISSION = 110 ;

    private Handler handler1=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:

                    String string= (String) message.obj;

                    try {
                        JSONObject jsonobject=new JSONObject(string);
                        String string1=jsonobject.getString("Data");
                        JSONObject jsonobject1=new JSONObject(string1);
                        String string2=jsonobject1.getString("RecommandCard");
                        newsString=jsonobject1.getString("NewsNotice");
                        matchString=jsonobject1.getString("NewsGame");
                        bannerString=jsonobject1.getString("Banners");
//                        Log.e("ccc",string2);
                        JSONArray array=new JSONArray(string2);
                        for(int i=0;i<array.length();i++){
                            JSONObject object=array.getJSONObject(i);
                            int id=object.getInt("ID");
                            String pic = object.getString("Pic");
                            String name=object.getString("Name");
                            magic=new Magic(id,name,"",pic,"","","","","","","","","");
                            list.add(magic);
                        }
                        //展示推荐卡牌
                        showRecommend();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    ArrayList<Banners> bannersList=new ArrayList<Banners>();
                    bannersList= (ArrayList<Banners>)message.obj;
                    break;
                case 3:
                    List<Bitmap>bitmapList=new ArrayList<>();
                    bitmapList= (ArrayList<Bitmap>) message.obj;
                    List<Drawable> drawableList=new ArrayList<>();
                    for(Bitmap temp:bitmapList){
                        drawableList.add(new BitmapDrawable(temp));
                    }
                    //设置数据源
                    viewpager.setDatasource(drawableList);
                    //设置间隔
                    viewpager.setInterval(2500);
                    //启动自动滚动
                    viewpager.resumeScroll();
                    viewpager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction()){
                                case MotionEvent.ACTION_DOWN:
                                    //当手指按下的时候
                                    x1 = motionEvent.getX();
                                    flage = 0 ;
                                    break ;

                                case  MotionEvent.ACTION_UP :
                                    x2 = motionEvent.getX();
                                    if(x1-x2>100||x1-x2<-100){

                                    }else{
                                        int item = viewpager.getCurrentItem();
                                        if (item == 0) {
                                        } else if (item == 1) {
                                            Toast.makeText(getActivity(),"点击第一个新闻",Toast.LENGTH_SHORT).show();
                                        } else if (item == 2) {
                                            Toast.makeText(getActivity(),"点击第二个新闻",Toast.LENGTH_SHORT).show();
                                        }else if (item == 3) {
                                            Toast.makeText(getActivity(),"点击第三个新闻",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if (flage == 0) {

                                    }
                                    break ;
                            }
                            return false;
                        }
                    });

                    break;

            }
            return false;
        }
    });



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);

        bytelist=new ArrayList<>();
        aCache= ACache.get(getActivity());
        list=new ArrayList<>();
        bannersList=new ArrayList<>();
        news= (TextView) view.findViewById(R.id.news);
        match= (TextView) view.findViewById(R.id.match);
        viewpager= (SimpleCycleViewPager) view.findViewById(R.id.viewpager);
        cardgroup= (TextView) view.findViewById(R.id.cardgroup);
        list_recommend= (RecyclerView) view.findViewById(R.id.list_recommend);

        /*中间按键监听*/
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("news",newsString);
                startActivity(intent);

            }
        });
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), MatchActivity.class);
                intent.putExtra("match",matchString);
                startActivity(intent);

            }
        });

        cardgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("stringList",bannersList.toString());
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
        scrollView= (ScrollView) view.findViewById(R.id.scrollView);
//update by 2017.03.08



//update by 2017.03.09
        BannerThread bannerThread=new BannerThread(handler1,getActivity());
        new Thread(bannerThread).start();

        /*获得首页信息*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str="http://60.205.177.241:8001/api/Card/FirstPage";
                URL url= null;
                try {
                    url = new URL(str);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn= null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    conn.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                conn.setConnectTimeout(5000);

                try {
                    if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream();
                        String result = HttpUtils.readMyInputStream(is);
                        Message msg=new Message();
                        msg.what=0;
                        msg.obj=result;
                        handler1.sendMessage(msg);

                    }else{
                        Log.d("测试","请求失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        return view;
    }
    /*展示推荐卡牌*/
    private void showRecommend() {
        adapter=new RecommendedAdapter(getActivity(),list);
        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(getActivity(),2);
        //设置布局管理器
        list_recommend.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        list_recommend.setAdapter( adapter);
        this.adapter.setOnItemClickListener(new RecommendedAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent=new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("ID",list.get(postion).getId());
                boolean f=true;
                intent.putExtra("language",f);
                startActivity(intent);
            }
        });
        this.adapter.setOnItemLongClickListener(new RecommendedAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int postion) {

            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }





    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        isLoop=true;
    }

    @Override
    public void onStart() {
        super.onStart();
        isLoop=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

//    public static Bitmap getBitmap(String path) throws IOException{
//
//        URL url = new URL(path);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setConnectTimeout(5000);
//        conn.setRequestMethod("GET");
//        Bitmap bitmap = null;
//        if(conn.getResponseCode() == 200){
//            InputStream inputStream = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(inputStream);
//            return bitmap;
//        }else{
//            Log.e("...","超时");
//        }
//        return bitmap;
//    }
//    public static byte[] getBytes(Bitmap bitmap){
//        //实例化字节数组输出流
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
//        return baos.toByteArray();//创建分配字节数组
//    }


}
