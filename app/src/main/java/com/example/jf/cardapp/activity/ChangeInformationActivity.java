package com.example.jf.cardapp.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.entity.PeopleData;
import com.example.jf.cardapp.utils.ImageLoaderUtils_circle;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeInformationActivity extends AppCompatActivity {
    private static final MediaType MEDIA_OBJECT_STREAM =MediaType.parse("application/octet-stream") ;
    private static final String TYPE ="application/octet-stream";
    private PeopleData data;
    Bitmap photo;
    private ImageView img_head;
    private ImageLoader imageLoader;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    private String AuthenticToken=null;
    private ImageView tv_back;
    private TextView tv_UserName,tv_Finish;
    private EditText et_Sex,et_Age,et_Bir,et_Xingzuo,et_Email,et_Phone,et_Name,et_PeopleID,et_Zhuzhi,et_ZhiYe,et_ZhiFuBaoId,et_WechatID,et_GameAge;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what){
                case 0:
                    data= (PeopleData) message.obj;
                    Log.e("data1",data.toString());
                    tv_UserName.setText(data.getUserName());
                    et_Sex.setText(data.getGender()+"");
                    et_Age.setText(data.getAge()+"");
                    et_Email.setText(data.getEmail());
                    et_Phone.setText(data.getPhone());
                    et_Name.setText(data.getName());
                    et_PeopleID.setText(data.getPeopleId());
                    et_Zhuzhi.setText(data.getAddress());
                    et_ZhiYe.setText(data.getCareer());
                    et_ZhiFuBaoId.setText(data.getAlipayID());
                    et_WechatID.setText(data.getWeixinID());
                    et_GameAge.setText(data.getGameAge()+"");
                    imageLoader.getInstance().displayImage("http://60.205.177.241:8001"+data.getPhoto(), img_head, ImageLoaderUtils_circle.MyDisplayImageOptions());
                    break;
                case 1:
                    int s= (int) message.obj;
                    if(s==0){

                        Toast.makeText(ChangeInformationActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        Intent data = new Intent();
                        // 设置要回传的数据
                        data.putExtra("contentInfo", "yes");
                        // 设置回传码和Intent
                        setResult(0, data);
                        finish();
                    }else{
                        Toast.makeText(ChangeInformationActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);
        initView();
        initEvent();

        Intent intent = getIntent();
        AuthenticToken=intent.getStringExtra("AuthenticToken");
        final Request.Builder builder = new Request.Builder().url("http://60.205.177.241:8001/api/User/UserInfo");
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
                        try {
                            JSONObject object=new JSONObject(string);
                            String Data=object.getString("Data");
                            JSONObject object1=new JSONObject(Data);
                            data=new PeopleData(object1.getString("UserName"),
                                    object1.getString("Name"),object1.getString("Email"),object1.getString("Phone"),
                                    object1.getInt("Gender"),object1.getInt("Age"),object1.getInt("GameAge"),
                                    object1.getString("IDNo"),object1.getString("Address"),object1.getString("Career"),
                                    object1.getString("UserPhoto"),object1.getString("AlipayID"),object1.getString("WeixinID"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Message msg=new Message();
                        msg.what=0;
                        msg.obj=data;
                        handler.sendMessage(msg);
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient httpClient=new OkHttpClient();
//                RequestBody requestBody=new FormBody.Builder()
//                        .add("UserName",sharedPreferences.getString("name", null))
//                        .add("Password",sharedPreferences.getString("password", null))
//                        .build();
//                Request request = new Request.Builder()
//
//                        .post(requestBody).url("http://60.205.177.241:8001/api/User/Login")
//                        .addHeader("AuthenticToken","233ABDAF399BB38D27D59E1B7A5F16F6CAE3379F606F737C95D0E0A9C7F831D2D611DED6F57AC50B865F14782299919E32D2861A87741714848695253B20F3879072699B56E7FBD2CAFEC767EE08ECBE")
//                        .build();
//                httpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("测试","请求失败");
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        final String string = response.body().string();
//                        Log.e("ccc",string);
//                        try {
//                            JSONObject object=new JSONObject(string);
//                            String Data=object.getString("Data");
//                            JSONObject object1=new JSONObject(Data);
//                            data=new PeopleData.DataBean(object1.getString("UserName"),
//                                    object1.getString("Name"),object1.getString("Email"),object1.getString("Phone"),
//                                    object1.getInt("Gender"),object1.getInt("Age"),object1.getInt("GameAge"),
//                                    object1.getString("IDNo"),object1.getString("Address"),object1.getString("Career"),
//                                    object1.getString("UserPhoto"),object1.getString("AlipayID"),object1.getString("WeixinID"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Message msg=new Message();
//                        msg.what=0;
//                        msg.obj=data;
//
//                    }
//                });
//            }
//        }).run();
    }
    public void show(View view){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.userphoto_dialog, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChangeInformationActivity.this,"点击了选择相册",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //打开相册
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // 设定结果返回

                startActivityForResult(intent, 2);
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChangeInformationActivity.this,"点击了拍照",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                if (Build.VERSION.SDK_INT==22){
                    Log.e("ccc", ContextCompat.checkSelfPermission(ChangeInformationActivity.this, Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(ChangeInformationActivity.this,Manifest.permission.CAMERA)==-1){
                        Toast.makeText(ChangeInformationActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(ChangeInformationActivity.this);

                    }else{
                        openCamera();
                    }
                }else if(Build.VERSION.SDK_INT>22){
                    Log.e("ccc",ContextCompat.checkSelfPermission(ChangeInformationActivity.this,Manifest.permission.CAMERA)+"--");
                    if(ContextCompat.checkSelfPermission(ChangeInformationActivity.this,Manifest.permission.CAMERA)==-1){
                        Toast.makeText(ChangeInformationActivity.this,"请添加相机权限",Toast.LENGTH_SHORT).show();
                        getAppDetailSettingIntent(ChangeInformationActivity.this);

                    }else{
                        openCamera();
                    }
                }else{
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    openCamera();
                }


            }
        });
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
    /*打开相机*/
    private void openCamera() {
        String state = Environment.getExternalStorageState();// 获取内存卡可用状态
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 内存卡状态可用
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("crop", "true"); // 开启剪裁
            intent.putExtra("aspectX", 1); // 宽高比例
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 150); // 宽高
            intent.putExtra("outputY", 150);

            startActivityForResult(intent, 1);
        } else {
            // 不可用
            Toast.makeText(ChangeInformationActivity.this, "内存不可用", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void initEvent() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                // 设置要回传的数据
                data.putExtra("contentInfo", "no");
                // 设置回传码和Intent
                setResult(0, data);
                finish();
            }
        });


        tv_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient httpClient=new OkHttpClient();
                        RequestBody requestBody=new FormBody.Builder()
                                .add("UserName", (String) tv_UserName.getText())
                                .add("Name", String.valueOf(et_Name.getText()))
                                .add("Email", String.valueOf(et_Email.getText()))
                                .add("Phone", String.valueOf(et_Phone.getText()))
                                .add("Gender", String.valueOf(et_Sex.getText()))
                                .add("Age", String.valueOf(et_Age.getText()))
                                .add("GameAge", String.valueOf(et_GameAge.getText()))
                                .add("IDNo", String.valueOf(et_PeopleID.getText()))
                                .add("Address", String.valueOf(et_Zhuzhi.getText()))
                                .add("Career", String.valueOf(et_ZhiYe.getText()))
                                .add("AlipayID", String.valueOf(et_ZhiFuBaoId.getText()))
                                .add("WeixinID", String.valueOf(et_WechatID.getText()))
                                .build();
                        Request request = new Request.Builder()
                                .post(requestBody).url("http://60.205.177.241:8001/api/User/Edit ")
                                .addHeader("AuthenticToken",AuthenticToken)
                                .build();
                        httpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("测试","请求失败");

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String string = response.body().string();
                                Log.e("ccc",string);
                                int s=1;
                                try {
                                    JSONObject object=new JSONObject(string);
                                    s=object.getInt("ErrorCode");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Message msg=new Message();
                                msg.what=1;
                                msg.obj=s;
                                handler.sendMessage(msg);



                            }
                        });
                    }
                }).run();
            }
        });
    }

    private void initView() {
        tv_back= (ImageView) findViewById(R.id.img_pd_top_back);
        img_head= (ImageView) findViewById(R.id.img_head);
        tv_UserName= (TextView) findViewById(R.id.tv_UserName);
        et_Sex= (EditText) findViewById(R.id.et_Sex);
        et_Age= (EditText) findViewById(R.id.et_Age);
        et_Bir= (EditText) findViewById(R.id.et_Bir);
        et_Xingzuo= (EditText) findViewById(R.id.et_Xingzuo);
        et_Email= (EditText) findViewById(R.id.et_Email);
        et_Phone= (EditText) findViewById(R.id.et_Phone);
        et_Name= (EditText) findViewById(R.id.et_Name);
        et_PeopleID= (EditText) findViewById(R.id.et_PeopleID);
        et_Zhuzhi= (EditText) findViewById(R.id.et_Zhuzhi);
        et_ZhiYe= (EditText) findViewById(R.id.et_ZhiYe);
        et_ZhiFuBaoId= (EditText) findViewById(R.id.et_ZhiFuBaoId);
        et_WechatID= (EditText) findViewById(R.id.et_WechatID);
        et_GameAge= (EditText) findViewById(R.id.et_GameAge);
        tv_Finish= (TextView) findViewById(R.id.tv_finish);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch (requestCode){
                case 1:
                    if(data.getData()!=null||data.getExtras()!=null){
                        Uri uri=data.getData();
                        if(uri!=null){
                            this.photo = BitmapFactory.decodeFile(uri.getPath()); // 拿到图片
                        }
                        if(photo==null){
                            Bundle bundle = data.getExtras();
                            if(bundle!=null){
                                photo = (Bitmap) bundle.get("data");
                                FileOutputStream fileOutputStream = null;
                                try {
// 获取 SD 卡根目录 生成图片并
                                    String saveDir = Environment
                                            .getExternalStorageDirectory()
                                            + "/dhj_Photos";
// 新建目录
                                    File dir = new File(saveDir);
                                    if (!dir.exists())
                                        dir.mkdir();
// 生成文件名
                                    SimpleDateFormat t = new SimpleDateFormat(
                                            "yyyyMMddssSSS");
                                    String filename = "MT" + (t.format(new Date()))
                                            + ".jpg";
// 新建文件
                                    File file = new File(saveDir, filename);
// 打开文件输出流
                                    fileOutputStream = new FileOutputStream(file);
// 生成图片文件
                                    this.photo.compress(Bitmap.CompressFormat.JPEG,
                                            100, fileOutputStream);
// 相片的完整路径
                                    Log.e("图片路径",file.getPath());
                                    OkHttpClient  client = new OkHttpClient.Builder()
                                            .connectTimeout(10, TimeUnit.SECONDS)
                                            .readTimeout(10, TimeUnit.SECONDS)
                                            .build();
                                    MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
                                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                                    requestBody.addFormDataPart("headImage", file.getName(), body);
                                    Request requestPostFile = new Request.Builder()
                                            .url("http://60.205.177.241:8001/api/User/UploadUserPhoto")
                                            .addHeader("AuthenticToken",AuthenticToken)
                                            .post(requestBody.build())
                                            .build();
                                    client.newCall(requestPostFile).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {

                                        }

                                        @Override
                                        public void onResponse(Call call, final Response response) throws IOException {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Log.e("cccc",response.body().string());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "修改成功",
                                        Toast.LENGTH_SHORT).show();
                                Intent data1 = new Intent();
                                // 设置要回传的数据
                                data1.putExtra("contentInfo", "yes");
                                // 设置回传码和Intent
                                setResult(0, data1);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "找不到图片",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                case 2:
                    //打开相册并选择照片，这个方式选择单张
// 获取返回的数据，这里是android自定义的Uri地址
                    Uri selectedImage = data.getData();
                    // 裁剪图片意图
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(selectedImage, "image/*");
                    intent.putExtra("crop", "true");
                    // 裁剪框的比例，1：1
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    // 裁剪后输出图片的尺寸大小
                    intent.putExtra("outputX", 250);
                    intent.putExtra("outputY", 250);

                    intent.putExtra("outputFormat", "JPEG");// 图片格式
                    intent.putExtra("noFaceDetection", true);// 取消人脸识别
                    intent.putExtra("return-data", true);
                    // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
                    startActivityForResult(intent, 1);
//                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//// 获取选择照片的数据视图
//                    Cursor cursor = getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//// 从数据视图中获取已选择图片的路径
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    cursor.close();
//                    OkHttpClient  client = new OkHttpClient.Builder()
//                            .connectTimeout(10, TimeUnit.SECONDS)
//                            .readTimeout(10, TimeUnit.SECONDS)
//                            .build();
//                    File file = new File(picturePath);
//                    if (!file.exists()) {
//                        Toast.makeText(ChangeInformationActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//                        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
//                        requestBody.addFormDataPart("headImage", file.getName(), body);
//                        Request requestPostFile = new Request.Builder()
//                                .url("http://60.205.177.241:8001/api/User/UploadUserPhoto")
//                                .addHeader("AuthenticToken",AuthenticToken)
//                                .post(requestBody.build())
//                                .build();
//                        client.newCall(requestPostFile).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//
//                            }
//
//                            @Override
//                            public void onResponse(Call call, final Response response) throws IOException {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            Log.e("cccc",response.body().string());
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                            }
//                        });
//                    }
                    break;
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
}
