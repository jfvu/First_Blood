package com.example.jf.cardapp.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.entity.PeopleData;
import com.example.jf.cardapp.entity.UpDatePost;
import com.example.jf.cardapp.utils.ImageLoaderUtils_circle;
import com.example.jf.cardapp.view.PullScrollView;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jf on 2017/2/22.
 */

public class PersonalDataActivity extends BaseActivity implements PullScrollView.OnTurnListener,View.OnClickListener{
    @BindView(R.id.img_pd_top_back)
    ImageView imgPdTopBack;
    private ImageView imgPdTopChange;
    private TextView tv_nickname,tv_sex,tv_age,tv_birthday,
            tv_constellation,tv_mail,tv_phone,tv_realname,tv_id,
            tv_address,tv_profession,tv_alipay,tv_WechatID,tv_playage;
    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private ImageView imgPdCenterhead;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    PeopleData data;
    private String AuthenticToken=null;
//private String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487840294576&di=290a03ec82a35b0a6a3aa7316a194159&imgtype=0&src=http%3A%2F%2Fup.qqya.com%2Fallimg%2F201710-t%2F17-101803_106599.jpg";


    @BindView(R.id.tv_finish)
    TextView tvFinish;
    private ImageLoader imageLoader;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_personaldata;
    }

    @Override
    protected void initData() {

    }
    public void show(View view){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.userphoto_dialog, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
    @Override
    protected void initView() {
        imgPdTopChange= (ImageView) findViewById(R.id.img_pd_top_change);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        imgPdTopChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT==22){
                    Log.e("ccc", ContextCompat.checkSelfPermission(PersonalDataActivity.this, Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(PersonalDataActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==-1){
                        Toast.makeText(PersonalDataActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(PersonalDataActivity.this);

                    }else{
                        Intent intent = new Intent(PersonalDataActivity.this,ChangeInformationActivity.class);
                        intent.putExtra("AuthenticToken",AuthenticToken);
                        startActivityForResult(intent,0);
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
                    Log.e("ccc",ContextCompat.checkSelfPermission(PersonalDataActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)+"--");
                    if(ContextCompat.checkSelfPermission(PersonalDataActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==-1){
                        Toast.makeText(PersonalDataActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(PersonalDataActivity.this);

                    }else{
                        Intent intent = new Intent(PersonalDataActivity.this,ChangeInformationActivity.class);
                        intent.putExtra("AuthenticToken",AuthenticToken);
                        startActivityForResult(intent,0);
                    }
                }else{
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    Intent intent = new Intent(PersonalDataActivity.this,ChangeInformationActivity.class);
                    intent.putExtra("AuthenticToken",AuthenticToken);
                    startActivityForResult(intent,0);
                }


            }
        });
        tv_nickname= (TextView) findViewById(R.id.tv_nickname);
        tv_sex= (TextView) findViewById(R.id.tv_sex);
        tv_age= (TextView) findViewById(R.id.tv_age);
        tv_mail= (TextView) findViewById(R.id.tv_mail);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        tv_realname= (TextView) findViewById(R.id.tv_realname);
        tv_id= (TextView) findViewById(R.id.tv_id);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_profession= (TextView) findViewById(R.id.tv_profession);
        tv_alipay= (TextView) findViewById(R.id.tv_alipay);
        tv_WechatID= (TextView) findViewById(R.id.tv_WechatID);
        tv_playage= (TextView) findViewById(R.id.tv_playage);
        imgPdCenterhead= (ImageView) findViewById(R.id.img_pd_centerhead);
//        imgPdCenterhead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("ccc","csadsd");
//                Intent intent =new Intent(PersonalDataActivity.this,DialogActivity.class);
//                startActivity(intent);
//            }
//        });/
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
        if (sharedPreferences.getString("id", null) != null) {

            goHttp(sharedPreferences.getString("id", null), sharedPreferences.getString("password", null));
        }else{
            imgPdTopChange.setVisibility(View.GONE);
        }
    }
    @OnClick({R.id.img_pd_top_back,  R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_pd_top_back:
                finish();
                break;
            case R.id.tv_finish:
                tvFinish.setVisibility(View.GONE);
                imgPdTopChange.setVisibility(View.VISIBLE);
                break;
            case R.id.takePhoto:
                Toast.makeText(this,"点击了拍照",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
            case R.id.choosePhoto:
                Toast.makeText(this,"点击了从相册选择",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
        }
    }

    private void goHttp(final String i, final String j) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpDatePost post = new UpDatePost(i, j);
                Gson gson = new Gson();

                OkHttpUtils.post("http://60.205.177.241:8001/api/User/Login").tag(this).upJson(gson.toJson(post)).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("seshi", s);

                        try {
                            JSONObject object=new JSONObject(s);
                            String string = object.getString("Data");
                            JSONObject object1=new JSONObject(string);
                                AuthenticToken=object1.getString("AuthenticToken");
                                data = new PeopleData(object1.getString("UserName"),
                                        object1.getString("Name"),
                                        object1.getString("Email"),
                                        object1.getString("Phone"),
                                        object1.getInt("Gender"),
                                        object1.getInt("Age"),
                                        object1.getInt("GameAge"),
                                        object1.getString("IDNo"),
                                        object1.getString("Address"),
                                        object1.getString("Career"),
                                        object1.getString("UserPhoto"),
                                        object1.getString("AlipayID"),
                                        object1.getString("WeixinID"));
                                tv_nickname.setText(data.getUserName());
                                tv_sex.setText(data.getGender()+"");
                                tv_age.setText(data.getAge()+"");
                                tv_mail.setText(data.getEmail());
                                tv_phone.setText(data.getPhone());
                                tv_realname.setText(data.getName());
                                tv_id.setText(data.getPeopleId());
                                tv_address.setText(data.getAddress());
                                tv_profession.setText(data.getCareer());
                                tv_alipay.setText(data.getAlipayID());
                                tv_WechatID.setText(data.getWeixinID());
                                tv_playage.setText(data.getGameAge() + "");
                                imageLoader.getInstance().displayImage("http://60.205.177.241:8001"+data.getPhoto(), imgPdCenterhead, ImageLoaderUtils_circle.MyDisplayImageOptions());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        imageLoader = ImageLoader.getInstance();
//                        imageLoader.init(ImageLoaderConfiguration.createDefault(PersonalDataActivity.this));
//                        imageLoader.displayImage(data.getData().getUserPhoto(), imgPdCenterhead);
                    }


                });


            }
        }).start();
    }


    @Override
    public void onTurn() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String contentInfo = data.getStringExtra("contentInfo");
        // 根据返回码的不同，设置参数
        if (requestCode == 0) {
            if(contentInfo.equals("no")){

            }else{
                finish();
            }
        }
    }
}
