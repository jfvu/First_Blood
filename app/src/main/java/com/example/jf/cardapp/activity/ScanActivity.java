package com.example.jf.cardapp.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.thread.IdentifyThread;
import com.example.jf.cardapp.utils.DisplayUtil;
import com.example.jf.cardapp.view.MaskView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by jf on 2017/2/14.
 */

public class ScanActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback{
    private static final String TAG = "结果";
    private ImageView back;
    Bitmap bitmap=null;
    private IdentifyThread identifyThread;
    private Bitmap getBitmap=null;
    private SurfaceView mCameraPreview;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private boolean isBackCameraOn = true;
    private ImageView img;
    private boolean isGetBuffer=true;
    Camera.Parameters parameters;
    byte[] bt;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    if(isGetBuffer&&mCamera!=null){
                        Bitmap bitmap1=getPreViewImage();

                        if(centerSquareScaleBitmap(bitmap1,100)!=null){
                            Bitmap bitmap2=zoomImg(centerSquareScaleBitmap(bitmap1,100),100,100);
                            img.setImageBitmap(bitmap2);
                            identifyThread=new IdentifyThread(handler,ScanActivity.this,bitmap2);
                            identifyThread.run();
                        }else{
                            handler.sendEmptyMessageDelayed(1, 300);
                        }




                    }else{
                        if(mCamera!=null){
                            mCamera.setPreviewCallback(null);
                        }

                    }
                    break;
                case 1:
                    if(mCamera!=null){
                        mCamera.setPreviewCallback(null);
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }

                    break ;
                case 2:
                    String string= (String) message.obj;
                    Toast.makeText(ScanActivity.this,string,Toast.LENGTH_SHORT).show();
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

                        if(f>0.7){
                            Intent intent=new Intent(ScanActivity.this,DetailsActivity.class);
                            intent.putExtra("ID",Integer.parseInt(str2));
                            boolean f1=false;
                            intent.putExtra("language",f1);
                            startActivity(intent);
                            finish();
                        }else{
                            handler.sendEmptyMessageDelayed(1, 300);
                        }
                    }else{
                        handler.sendEmptyMessageDelayed(1, 300);
                    }

//                    if(string.equals("服务器请求失败")){

//                    }
                    break;

            }
            return false;
        }
    });

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.img_scan_back);
        back.setOnClickListener(this);
        img= (ImageView) findViewById(R.id.img);
        mCameraPreview = (SurfaceView) findViewById(R.id.sv_camera);
        mSurfaceHolder = mCameraPreview.getHolder();
        mSurfaceHolder.addCallback(this);
//        maskView = (MaskView)findViewById(R.id.view_mask);
//        Rect screenCenterRect = createCenterScreenRect(DisplayUtil.dip2px(this, DST_CENTER_RECT_WIDTH)
//                ,DisplayUtil.dip2px(this, DST_CENTER_RECT_HEIGHT));
//        maskView.setCenterRect(screenCenterRect);
        mCameraPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        getBitmap=getPreViewImage();
//        Log.e("bitmap", String.valueOf(getBitmap));
//        identifyThread=new IdentifyThread(handler,ScanActivity.this,getBitmap);
//        identifyThread.run();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap2=null;
                        bitmap2=getPreViewImage();
                Log.e("bitmap", String.valueOf(bitmap2));
                    try {
                        if(bitmap2!=null){
                            postJson(bitmap2);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Message msg=new Message();
                    msg.what=0;
                    msg.obj=bitmap2;
                    handler.sendMessage(msg);
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                   postJson();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_scan_back:
                isGetBuffer=false;
                finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.checkCameraHardware(this) && (mCamera == null)) {
            mCamera = getCamera();

            if (mSurfaceHolder != null) {

                setStartPreview(mCamera, mSurfaceHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseCamera();

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera,mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStartPreview(mCamera, mSurfaceHolder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        releaseCamera();
    }
    /**
     * 切换前后摄像头
     *
     * @param view view
     */
    public void switchCamera(View view) {
        int cameraCount;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        // 遍历可用摄像头
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (isBackCameraOn) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    releaseCamera();
                    mCamera = Camera.open(i);
                    setStartPreview(mCamera, mSurfaceHolder);
                    isBackCameraOn = false;
                    break;
                }
            } else {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    releaseCamera();
                    mCamera = Camera.open(i);
                    setStartPreview(mCamera, mSurfaceHolder);
                    isBackCameraOn = true;
                    break;
                }
            }
        }
    }
    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
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
     * 在SurfaceView中预览相机内容
     *
     * @param camera camera
     * @param holder SurfaceHolder
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);

            parameters=mCamera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            //parameters.setPictureSize(surfaceView.getWidth(), surfaceView.getHeight());  // 部分定制手机，无法正常识别该方法。
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            mCamera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public Bitmap rotateMyBitmap(Bitmap bmp){
        //*****旋转一下
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0,0, bmp.getWidth(),  bmp.getHeight(), matrix, true);

        return nbmp2;

    }



    private Bitmap getPreViewImage() {
        if(mCamera!=null){
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    Camera.Size size=mCamera.getParameters().getPreviewSize();
                    YuvImage image=new YuvImage(bytes, ImageFormat.NV21,size.width,size.height,null);


                    try {
                        if(image!=null){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);

                            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//                            rectPictureSize = createCenterPictureRect(DisplayUtil.dip2px(ScanActivity.this, DST_CENTER_RECT_WIDTH)
//                                    ,DisplayUtil.dip2px(ScanActivity.this, DST_CENTER_RECT_HEIGHT));
                            //**********************
                            //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
                            bitmap=rotateMyBitmap(bmp);
                            //**********************************
                            bitmap=centerSquareScaleBitmap(bitmap,250);
                            stream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return bitmap;
    }

    private void postJson(Bitmap bitmap) throws JSONException, IOException {
        bt=Bitmap2Bytes(bitmap);

        String jsonString=Base64.encodeToString(bt, Base64.DEFAULT);
        OkHttpClient  httpClient=new OkHttpClient();
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
                Log.e("长度", String.valueOf(bt.length));
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
    /**
     * 生成屏幕中间的矩形
     * @param w 目标矩形的宽度,单位px
     * @param h	目标矩形的高度,单位px
     * @return
     */
    private Rect createCenterScreenRect(int w, int h){
        int x1 = DisplayUtil.getScreenMetrics(this).x / 2 - w / 2;
        int y1 = DisplayUtil.getScreenMetrics(this).y / 2 - h / 2;
        int x2 = x1 + w;
        int y2 = y1 + h;
        return new Rect(x1, y1, x2, y2);
    }
    /**生成拍照后图片的中间矩形的宽度和高度
     * @param w 屏幕上的矩形宽度，单位px
     * @param h 屏幕上的矩形高度，单位px
     * @return
     */
    private Point createCenterPictureRect(int w, int h){

        int wScreen = DisplayUtil.getScreenMetrics(this).x;
        int hScreen = DisplayUtil.getScreenMetrics(this).y;
        Camera.Size s = mCamera.getParameters().getPictureSize();
        Point point=new Point(s.width,s.height);
        int wSavePicture = point.y; //因为图片旋转了，所以此处宽高换位
        int hSavePicture = point.x; //因为图片旋转了，所以此处宽高换位
        float wRate = (float)(wSavePicture) / (float)(wScreen);
        float hRate = (float)(hSavePicture) / (float)(hScreen);
        float rate = (wRate <= hRate) ? wRate : hRate;//也可以按照最小比率计算

        int wRectPicture = (int)( w * wRate);
        int hRectPicture = (int)( h * hRate);
        return new Point(wRectPicture, hRectPicture);

    }
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if(widthOrg > edgeLength && heightOrg > edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try{
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }

        return result;
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



}
