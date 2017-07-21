package com.example.jf.cardapp.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.dbhelper.DBHelper;

import com.example.jf.cardapp.entity.UpDatePost;
import com.example.jf.cardapp.fragment.GroupFragment;
import com.example.jf.cardapp.fragment.HomeFragment;
import com.example.jf.cardapp.fragment.InformationFragment;
import com.example.jf.cardapp.fragment.MassageFragment;
import com.example.jf.cardapp.fragment.SquareFragment;
import com.example.jf.cardapp.service.UpdateService;
import com.example.jf.cardapp.thread.UpdateThread;
import com.example.jf.cardapp.utils.HttpUtils;
import com.example.jf.cardapp.utils.ImageLoaderUtils_circle;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 首页
 * Created by jf on 2017/2/11.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    //菜单键
    private ImageView menuView;
    //摄像头
    private ImageView img_scan;
    //侧滑
    private SlidingMenu menu;
    //搜索
    private Button button_search;
    //文本搜索
    private TextView leftTextSeach;
    //图片搜索
    private TextView leftScan;
    //点击登陆
    private TextView login;
    //我的收藏
    private TextView myCollect;
    //个人资料(头像)
    private ImageView head;

    //个人资料(头像)
    private ImageView headView;
    //设置按钮
    private TextView setView;
    //搜索控件
    private TextView autoCompleteTextView;
    private DBHelper myHelper;
    private SQLiteDatabase db;
    private List<String> list;//提示卡牌集合
    private RadioButton deckView, homeView, messageView, newsView, squareView;//
    private GroupFragment groupFragment;
    private HomeFragment homeFragment;
    private InformationFragment informationFragment;
    private MassageFragment massageFragment;
    private SquareFragment squareFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private RelativeLayout rel_search;
    private TextView search;
    private TextView cancel;
    private boolean isShow = false;
    private EditText edit_info;
    private Button bt_search;
    private SharedPreferences userPreferences;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    private long exitTime = 0;
    private List<CardList> cardLists;
    private static final int LOGINBACK = 2;
    private static final int LOGINBACKTWO = 3;
    private String AuthenticToken = null;
    private Intent updataService ;
    private static int REQUESTPERMISSION = 110 ;
    SharedPreferences sharedPreferences;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    cardLists.clear();
                    CardList c;
                    String string = (String) message.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        String data = jsonObject.getString("Data");
                        JSONObject jsonObject1 = new JSONObject(data);
                        String cardlist = jsonObject1.getString("CardList");
                        int TotalCount = jsonObject1.getInt("TotalCount");
                        JSONArray jsonArray = new JSONArray(cardlist);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            int id = jsonObject2.getInt("ID");
                            String name = jsonObject2.getString("Name");
                            String Setsname = jsonObject2.getString("SetsName");
                            c = new CardList(id, name, Setsname);
                            cardLists.add(c);

                        }
                        if (TotalCount == 0) {

                        } else {
                            Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                            intent.putExtra("ID", cardLists.get(0).getId());
                            edit_info.setText("");
                            startActivity(intent);
                            rel_search.setVisibility(View.GONE);
                            isShow = false;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    Log.e("检测结果","需要更新");
                    showNoticeDialog();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    private void showHeader() {
        userPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (!userPreferences.getString("id", "noall") .equals("noall")) {
            goHttp(userPreferences.getString("id", null), userPreferences.getString("password", null));
            login.setVisibility(View.GONE);
            head.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.VISIBLE);
            head.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{
//                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, 1);
//
//        }
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

 /*版本检测*/
        PackageInfo pkg = null;
        try {
            pkg = this.getPackageManager().getPackageInfo(this.getApplication().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName = pkg.applicationInfo.loadLabel(this.getPackageManager()).toString();
        String versionName = pkg.versionName;
        //检查是否弹出更新内容
        showUpdatecContent("1.解决已知bug" ,versionName);
        UpdateThread updateThread=new UpdateThread(handler,this,versionName);
        updateThread.run();
        initSlidingMeun();
        cardLists = new ArrayList<>();
        edit_info = (EditText) findViewById(R.id.edit_info);
        bt_search = (Button) findViewById(R.id.search_01);
        bt_search.setOnClickListener(this);

//左侧menu按键和监听
        headView = (ImageView) menu.findViewById(R.id.img_leftmenu_head);
        setView = (TextView) menu.findViewById(R.id.tv_leftmenu_set);
        leftTextSeach = (TextView) menu.findViewById(R.id.tv_leftmenu_textseach);
        leftScan = (TextView) menu.findViewById(R.id.tv_leftmenu_scan);
        login = (TextView) menu.findViewById(R.id.tv_left_login);
        myCollect = (TextView) menu.findViewById(R.id.tv_leftmenu_mycollect);
        head = (ImageView) menu.findViewById(R.id.img_leftmenu_head);
        headView.setOnClickListener(this);
        setView.setOnClickListener(this);
        leftTextSeach.setOnClickListener(this);
        leftScan.setOnClickListener(this);
        login.setOnClickListener(this);
        myCollect.setOnClickListener(this);
        head.setOnClickListener(this);


        deckView = (RadioButton) findViewById(R.id.rb_group);
        homeView = (RadioButton) findViewById(R.id.rb_home);
        newsView = (RadioButton) findViewById(R.id.rb_information);
        messageView = (RadioButton) findViewById(R.id.rb_message);
        squareView = (RadioButton) findViewById(R.id.rb_square);
        initEvent();
        fragmentManager = getFragmentManager();
        setTabSelection(2);

         /*
        * 遍历卡牌数据库
        * */
        list = new ArrayList<String>();
        myHelper = new DBHelper(this, "test.db", null, 1);
        db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from cards ", null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(1));
        }
        autoCompleteTextView = (TextView) findViewById(R.id.edit_info);//获取提示框控件
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.auto_item,R.id.tv_show, list);
        autoCompleteTextView.setAdapter(adapter);*/

        rel_search = (RelativeLayout) findViewById(R.id.rel_search);
        search = (TextView) findViewById(R.id.search);
        cancel = (TextView) findViewById(R.id.cancel);
        rel_search.setOnClickListener(this);
        search.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        //查看user数据，如果存在，注册登录按键消失，如果不存在，登录注册按键显示
        showHeader();
    }



    private void initSlidingMeun() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影宽度
        menu.setShadowDrawable(R.color.colorAccent);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);

        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.left_menu);
        //为菜单键设置监听
        menuView = (ImageView) findViewById(R.id.img_menu);
        menuView.setOnClickListener(this);
        img_scan = (ImageView) findViewById(R.id.img_scan);
        img_scan.setOnClickListener(this);
        button_search = (Button) findViewById(R.id.button_search);
        button_search.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击菜单键侧滑
            case R.id.img_menu:
                menu.toggle();
                break;

            case R.id.tv_leftmenu_set:

                Intent intent1 = new Intent(HomeActivity.this, SetActivity.class);
                intent1.putExtra("AuthenticToken", AuthenticToken);
                startActivityForResult(intent1, 1);

                break;
            case R.id.img_scan:
                Log.e("cccc", String.valueOf(ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED));
                if (Build.VERSION.SDK_INT==22){
                    Log.e("ccc",ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==-1){
                        Toast.makeText(HomeActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(this);

                    }else{
                        Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
                        startActivity(intent2);
                    }
//                    if (ContextCompat.checkSelfPermission(HomeActivity.this,
//                            android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                        //先判断有没有权限 ，没有就在这里进行权限的申请
//                        ActivityCompat.requestPermissions(HomeActivity.this,
//                                new String[]{android.Manifest.permission.CAMERA},1);
//
//                    }else {
//                        Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
//                        startActivity(intent2);
//                        //说明已经获取到摄像头权限了 想干嘛干嘛
//                    }
                }else if(Build.VERSION.SDK_INT>22){
                    Log.e("ccc",ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==-1){
                        Toast.makeText(HomeActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(this);

                    }else{
                        Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
                        startActivity(intent2);
                    }
                }else{
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
                    startActivity(intent2);
                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{
//                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    }, 1);
//                    Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
//                    startActivity(intent2);
//                }else{
//                    Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
//                    startActivity(intent2);
//                }



                break;
            case R.id.button_search:

                break;
            case R.id.tv_leftmenu_textseach:
                Intent intent = new Intent(HomeActivity.this, TextSeachActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_leftmenu_scan:
                if (Build.VERSION.SDK_INT==22){
                    Log.e("ccc",ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==-1){
                        Toast.makeText(HomeActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(this);

                    }else{
                        Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
                        startActivity(intent2);
                    }
//                    if (ContextCompat.checkSelfPermission(HomeActivity.this,
//                            android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                        //先判断有没有权限 ，没有就在这里进行权限的申请
//                        ActivityCompat.requestPermissions(HomeActivity.this,
//                                new String[]{android.Manifest.permission.CAMERA},1);
//
//                    }else {
//                        Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
//                        startActivity(intent2);
//                        //说明已经获取到摄像头权限了 想干嘛干嘛
//                    }
                }else if(Build.VERSION.SDK_INT>22){
                    Log.e("ccc",ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==-1){
                        Toast.makeText(HomeActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(this);

                    }else{
                        Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
                        startActivity(intent2);
                    }
                }else{
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.tv_left_login:
                Intent intent4 = new Intent(HomeActivity.this, LoginActivity.class);
//                finish();
                startActivity(intent4);
                break;
            case R.id.tv_leftmenu_mycollect:
                Intent intent2 = new Intent(HomeActivity.this, MycollectActivity.class);
                startActivity(intent2);
                break;
            case R.id.img_leftmenu_head:
                Intent intent5 = new Intent(HomeActivity.this, PersonalDataActivity.class);

                startActivity(intent5);
                break;
            case R.id.search:
                /*rel_search.setVisibility(View.VISIBLE);
                isShow=true;*/
                Intent intent3 = new Intent(HomeActivity.this, TextSeachActivity.class);
                startActivity(intent3);
                break;
            case R.id.cancel:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0); //强制隐藏键盘
                rel_search.setVisibility(View.GONE);
                isShow = false;
                break;
            case R.id.search_01:
                final String[] name = {edit_info.getText().toString()};

                if (!name[0].equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                name[0] = URLEncoder.encode(edit_info.getText().toString(), "UTF8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            String str = "http://60.205.177.241:8001/api/Card/CardSearchByName?name=" + name[0];
                            try {
                                URL url = new URL(str);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5000);

                                if (conn.getResponseCode() == 200) {
                                    InputStream is = conn.getInputStream();
                                    String result = HttpUtils.readMyInputStream(is);
                                    Log.e("ccc", result);
                                    Message msg = new Message();
                                    msg.what = 0;
                                    msg.obj = result;
                                    handler.sendMessage(msg);

                                } else {
                                    Log.d("测试", "请求失败");
                                }

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
        }
    }


    private void initEvent() {

        deckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(1);
            }
        });
        squareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(0);
            }
        });
        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(2);
            }
        });
        messageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(3);
            }
        });
        newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(4);
            }
        });
    }

    public void setTabSelection(int index) {
        //清理之前的所有状态
        clearSelection();
        //开启一个Fragment事务

        transaction = fragmentManager.beginTransaction();
        //隐藏所有的fragment，防止有多个界面显示在界面上
        hideFragments(transaction);
        switch (index) {
            case 0:
                //如果messageFragment为空，则创建一个添加到界面上

                if (squareFragment == null) {
                    squareFragment = new SquareFragment();
                    transaction.add(R.id.fragment_home, squareFragment);
                } else {
                    //如果messageFragment不为空，则直接将它显示出来
                    transaction.show(squareFragment);
                }
                break;
            case 1:
                //如果messageFragment为空，则创建一个添加到界面上
                if (groupFragment == null) {
                    groupFragment = new GroupFragment();
                    transaction.add(R.id.fragment_home, groupFragment);
                } else {
                    //如果messageFragment不为空，则直接将它显示出来
                    transaction.show(groupFragment);
                }
                break;
            case 2:
                //如果messageFragment为空，则创建一个添加到界面上
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_home, homeFragment);
                } else {
                    //如果messageFragment不为空，则直接将它显示出来
                    transaction.show(homeFragment);
                }
                break;
            case 3:
                //如果messageFragment为空，则创建一个添加到界面上
                if (massageFragment == null) {
                    massageFragment = new MassageFragment();
                    transaction.add(R.id.fragment_home, massageFragment);
                } else {
                    //如果messageFragment不为空，则直接将它显示出来
                    transaction.show(massageFragment);
                }
                break;
            case 4:
                //如果messageFragment为空，则创建一个添加到界面上
                if (informationFragment == null) {
                    informationFragment = new InformationFragment();
                    transaction.add(R.id.fragment_home, informationFragment);
                } else {
                    //如果messageFragment不为空，则直接将它显示出来
                    transaction.show(informationFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void clearSelection() {

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (massageFragment != null) {
            transaction.hide(massageFragment);
        }
        if (groupFragment != null) {
            transaction.hide(groupFragment);
        }
        if (informationFragment != null) {
            transaction.hide(informationFragment);
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (squareFragment != null) {
            transaction.hide(squareFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN&&!menu.isMenuShowing()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }else if(menu.isMenuShowing()&&keyCode == KeyEvent.KEYCODE_BACK)
        {
            menu.showContent();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class CardList {
        private int id;
        private String name;
        private String setsname;

        public CardList(int id, String name, String setsname) {
            this.id = id;
            this.name = name;
            this.setsname = setsname;
        }

        public int getId() {

            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSetsname() {
            return setsname;
        }

        public void setSetsname(String setsname) {
            this.setsname = setsname;
        }

        @Override
        public String toString() {
            return "CardList{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", setsname='" + setsname + '\'' +
                    '}';
        }
    }

    //设置头像
    private void goHttp(final String i, final String j) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpDatePost post = new UpDatePost(i, j);
                if (post == null) {
                    head.setVisibility(View.VISIBLE);
                } else {
                    Gson gson = new Gson();
                    OkHttpUtils.post("http://60.205.177.241:8001/api/User/Login").tag(this).upJson(gson.toJson(post)).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e("seshi", s);
                            try {
                                JSONObject object = new JSONObject(s);
                                String string = object.getString("Data");
                                JSONObject object1 = new JSONObject(string);
                                AuthenticToken = object1.getString("AuthenticToken");
                                Log.e("img",AuthenticToken);
                                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("AuthenticToken", AuthenticToken);
                                editor.commit();
                                String imgpath = object1.getString("UserPhoto");
                                Log.e("img",imgpath);
                                imageLoader.init(ImageLoaderConfiguration.createDefault(HomeActivity.this));
                                imageLoader.getInstance().displayImage("http://60.205.177.241:8001" + imgpath, head, ImageLoaderUtils_circle.MyDisplayImageOptions());


//                            imageLoader = ImageLoader.getInstance();
//                            imageLoader.init(ImageLoaderConfiguration.createDefault(HomeActivity.this));
//                            imageLoader.displayImage("http://60.205.177.241:8001"+imgpath, head);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    });
                }


            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Log.d("测试", "onAr");
                Bundle b = data.getExtras();
                Log.d("是否变换语言", b.getString("data"));
                if (b.getString("data").equals("yes")) {
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case LOGINBACK:
                break;
            case LOGINBACKTWO:
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog()
    {
// 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("更新");
        builder.setMessage("请更新最新版本");
// 更新
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                //启动服务
                upData();

            }


        });
// 稍后更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
    public void upData(){
        updataService = new Intent(this,  UpdateService.class);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);

        }else{
            this.startService(updataService);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        doNext(requestCode, grantResults);
        if(requestCode==1){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
//                Intent intent2 = new Intent(HomeActivity.this,Scan2Activity.class);
//                startActivity(intent2);
            }else {
                //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                Toast.makeText(HomeActivity.this,"请手动打开相机权限",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }
    private void showUpdatecContent(String dddd, String versionName) {
        String s=sharedPreferences.getString("versionName","");
        if(!s.equals(versionName)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("versionName",versionName);
            editor.commit();
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.showupdate,null);
            Dialog alertDialog = new AlertDialog.Builder(this).
                    setTitle("更新功能").
                    setMessage(dddd).
                    setIcon(R.drawable.ap).
                    create();
            alertDialog.show();
        }
    }

}

