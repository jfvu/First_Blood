package com.example.jf.cardapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.utils.CameraHelper;
import com.example.jf.cardapp.utils.OnCaptureCallback;
import com.example.jf.cardapp.view.MaskSurfaceView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Scan2Activity extends AppCompatActivity implements OnCaptureCallback {
    private MaskSurfaceView surfaceview;
    private ImageView imageView;
    //	拍照
    private Button btn_capture;
    //	重拍
    private Button btn_recapture;
    //	取消
    private Button btn_cancel;
    //	确认
    private Button btn_ok;

    private Camera mCamera;

    //	拍照后得到的保存的文件路径
    private String filepath;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    String string= (String) message.obj;
//                    Toast.makeText(Scan2Activity.this,string,Toast.LENGTH_SHORT).show();
                    double f=0.8132;
                    String str="";
                    String str2="";
                    if(!string.equals("服务器请求失败")){
                        try {
                            JSONObject object=new JSONObject(string);
                            str=object.getString("img_id");
                            f=object.getDouble("score");
                            str=str.trim();

                            if(str != null && !"".equals(str)){
                                for(int i=0;i<str.length();i++){
                                    if(str.charAt(i)>=48 && str.charAt(i)<=57){
                                        str2+=str.charAt(i);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(f>0.8){
                            Intent intent=new Intent(Scan2Activity.this,DetailsActivity.class);
                            intent.putExtra("ID",Integer.parseInt(str2));
                            boolean f1=true;
                            intent.putExtra("language",f1);
                            startActivity(intent);
                            finish();
                        }else{
                            handler.sendEmptyMessageDelayed(1, 300);
                        }
                    }else{
                        handler.sendEmptyMessageDelayed(1, 300);
                    }
                    break;
                case 1:
                        CameraHelper.getInstance().tackPicture(Scan2Activity.this);


                    break;
            }
            return false;

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_scan2);
        this.surfaceview = (MaskSurfaceView) findViewById(R.id.surface_view);
        this.imageView = (ImageView) findViewById(R.id.image_view);
//        btn_capture = (Button) findViewById(R.id.btn_capture);
//        btn_recapture = (Button) findViewById(R.id.btn_recapture);
//        btn_ok = (Button) findViewById(R.id.btn_ok);
//        btn_cancel = (Button) findViewById(R.id.btn_cancel);

//		设置矩形区域大小
        this.surfaceview.setMaskSize(900, 600);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CameraHelper.getInstance().tackPicture(Scan2Activity.this);
            }
        }).start();
//		拍照
//        btn_capture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                btn_capture.setEnabled(false);
////                btn_ok.setEnabled(true);
////                btn_recapture.setEnabled(true);
//                CameraHelper.getInstance().tackPicture(Scan2Activity.this);
////                CameraHelper.getInstance().takePhoto(MainActivity.this);
//            }
//        });
//
////		重拍
//        btn_recapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btn_capture.setEnabled(true);
//                btn_ok.setEnabled(false);
//                btn_recapture.setEnabled(false);
//                imageView.setVisibility(View.GONE);
//                surfaceview.setVisibility(View.VISIBLE);
//                deleteFile();
//                CameraHelper.getInstance().startPreview();
//            }
//        });
//
////		确认
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//            }
//        });
//
////		取消
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                deleteFile();
//                Scan2Activity.this.finish();
//            }
//        });
    }

//    /**
//     * 删除图片文件呢
//     */
//    private void deleteFile(){
//        if(this.filepath==null || this.filepath.equals("")){
//            return;
//        }
//        File f = new File(this.filepath);
//        if(f.exists()){
//            f.delete();
//        }
//    }

    @Override
    public void onCapture(boolean success, final Bitmap bitmap) {
        this.filepath = filepath;
        String message = "拍照成功";
        if(!success){
            message = "拍照失败";
            CameraHelper.getInstance().startPreview();
//            this.imageView.setVisibility(View.GONE);
//            this.surfaceview.setVisibility(View.VISIBLE);
        }else{
//            this.imageView.setVisibility(View.VISIBLE);
//            this.surfaceview.setVisibility(View.GONE);
            final Bitmap bitmap1=zoomImg(bitmap,200,150);
//            this.imageView.setImageBitmap(bitmap1);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        postJson(bitmap1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).run();
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void postJson(Bitmap bitmap) throws JSONException, IOException {
        byte[] bt=Bitmap2Bytes(bitmap);

        String jsonString= Base64.encodeToString(bt, Base64.DEFAULT);
        final OkHttpClient httpClient=new OkHttpClient();
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
                handler.sendEmptyMessageDelayed(1, 300);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.e("ccc",string);
                Message msg=new Message();
                msg.what=0;
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
    public Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (this.checkCameraHardware(this) && (mCamera == null)) {
//            mCamera = getCamera();
//
//        }
    }
    /**
     * 检查是否具有相机功能
     *
     * @param context context
     * @return 是否具有相机功能
     */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }
    /**
     * 初始化相机
     *
     * @return camera
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
        }
        return camera;
    }
}
