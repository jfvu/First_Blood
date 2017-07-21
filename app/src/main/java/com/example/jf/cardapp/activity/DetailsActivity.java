package com.example.jf.cardapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.entity.Curve;
import com.example.jf.cardapp.entity.Magic;
import com.example.jf.cardapp.entity.PeopleData;
import com.example.jf.cardapp.utils.HttpUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 详情页
 * Created by jf on 2017/2/11.
 *
 */
public class DetailsActivity extends AppCompatActivity {
    //价格图表
    private LineChartView lineChart;
    private LineChartView lineChart1;
    String[] date = {"日","一","二","三","四","五","六"};//X轴的标注
    int[] score= {50,42,90,33,44,74,32};//图表的数据点
    int[] score1= {0,0,0,0,0,0,0};//图表的数据点
    private List<String>dateList=new ArrayList<>();
    private List<Float>scoreList=new ArrayList<>();
    private List<Float>scoreList1=new ArrayList<>();
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<PointValue> mPointValues1 = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    String AuthenticToken=null;
    private List<String>dateList_1=new ArrayList<>();
    private List<Float>scoreList_1=new ArrayList<>();
    private List<Float>scoreList_11=new ArrayList<>();
    private List<PointValue> mPointValues_1 = new ArrayList<PointValue>();
    private List<PointValue> mPointValues_11 = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues_1 = new ArrayList<AxisValue>();
    private RelativeLayout rel_collect;
    private ImageView back;
    private TextView tv_language;
    private TextView name;//名字
    private TextView category;//角色
    private TextView ap;//魔法值
    private TextView describe;//描述
    private ImageView img,ap_1,ap_2,ap_3;
    private ImageView huoqu;
    private int id=1;
    private String string;
    private LinearLayout ln_ap;
    private RelativeLayout background;
    private String languag="en";
    private RelativeLayout rel_04;//闪牌价格
    boolean b = false;//判断是否是中文卡牌
    boolean ishowonline=true;
    boolean ishowoffline=true;
    boolean ishowonline1=true;
    boolean ishowoffline1=true;
    private Magic magic1;
    private Button bt_onlinemoney,bt_offlinemoney,bt_onlinemoney_1,bt_offlinemoney_1;
    private Handler handler=new Handler(new Handler.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:

                    String result= (String) message.obj;
                    Log.d("测试",result);
                    try {
                        JSONObject object=new JSONObject(result);
                        String string=object.getString("Data");
                        JSONObject object1=new JSONObject(string);
                        int id=object1.getInt("ID");
                        String name=object1.getString("Name");
                        String nameen=object1.getString("NameEn");
                        String pic=object1.getString("Pic");
                        String describe=object1.getString("Describe");
                        String purpose=object1.getString("Purpose");
                        String category=object1.getString("Category");
                        String magic=object1.getString("Magic");
                        String rarity=object1.getString("Rarity");
                        String version=object1.getString("Version");
                        String edition=object1.getString("Edition");
                        String illus=object1.getString("Illus");
                        String language=object1.getString("Language");
                        magic1=new Magic(id,name,nameen,pic,describe,purpose,category,magic,rarity,version,edition,illus,language);
//                        Log.e("Data",magic1.toString());
                        update(magic1);
                        showMagic(magic1.getMagic());
                        showCardBackground(magic1.getMagic());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;
                case 1:
                    Bitmap bitmap= (Bitmap) message.obj;
                    System.out.print(bitmap);
                    img.setImageBitmap(bitmap);
                    break;
                case 2:
                    String str= (String) message.obj;
                    try {
                        isShowFlash(str);//显示闪牌价格与是否有闪牌
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    boolean f= (boolean) message.obj;
                    if(f==true){
                        Toast.makeText(DetailsActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DetailsActivity.this,"收藏失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;

            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();

        b = intent.getBooleanExtra("language",false);
        id=intent.getIntExtra("ID",1);
            string = "http://60.205.177.241:8001/api/Card/CardInfo?cardID="+id;
        initView();
        initEvent();

    }

    private void initEvent() {
        rel_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                String AuthenticToken=sharedPreferences.getString("AuthenticToken","noall");
                Log.i("AuthenticToken",AuthenticToken);
                if(AuthenticToken.equals("noall")){
                    Toast.makeText(DetailsActivity.this,"先登录",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("AuthenticToken",AuthenticToken);
                    final Request.Builder builder = new Request.Builder().url("http://60.205.177.241:8001/api/Card/CardAttention?ID="+id+"&type="+1);
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
//                                    Log.e("测试",string);
                                    JSONObject object=new JSONObject(string);
                                    Message message=new Message();
                                    message.what=3;
                                    message.obj=object.getBoolean("Data");
                                    handler.sendMessage(message);
                                } else {

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

        bt_onlinemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ishowonline=true;
                ishowoffline=false;
                initLineChart();//初始化
//                Log.e("aaaa",String.valueOf(ishowonline)+String.valueOf(ishowoffline));
            }
        });
        bt_offlinemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ishowonline=false;
                ishowoffline=true;
                initLineChart();//初始化

            }
        });
        bt_onlinemoney_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ishowonline1=true;
                ishowoffline1=false;
                initLineChart1();
//                Log.e("aaaa",String.valueOf(ishowonline)+String.valueOf(ishowoffline));
            }
        });
        bt_offlinemoney_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ishowonline1=false;
                ishowoffline1=true;
                initLineChart1();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShow();
            }
        });
//updata by 17.03.06
        Log.d("url",string);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url=new URL(string);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);

                    if(conn.getResponseCode()==200){
                        InputStream is = conn.getInputStream();
                        String result = HttpUtils.readMyInputStream(is);
                        Message msg=new Message();
                        msg.what=0;
                        msg.obj=result;
                        handler.sendMessage(msg);

                    }else{
                        Log.d("测试","请求失败");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //by 2017.02.15
        //获取卡牌价格
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url("http://60.205.177.241:8001/api/Card/CardPrice?cardID="+id).build();
        Call call = client.newCall(request);
//请求加入调度
        call.enqueue(new Callback()
        {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String htmlStr =  response.body().string();
                Message msg=new Message();
                msg.what=2;
                msg.obj=htmlStr;
                handler.sendMessage(msg);
            }
        });


    }

    private void initView() {
        rel_collect= (RelativeLayout) findViewById(R.id.rel_collect);
        bt_onlinemoney= (Button) findViewById(R.id.bt_onlinemoney);
        bt_offlinemoney= (Button) findViewById(R.id.bt_offlinemoney);
        bt_onlinemoney_1= (Button) findViewById(R.id.bt_onlinemoney1);
        bt_offlinemoney_1= (Button) findViewById(R.id.bt_offlinemoney1);
        rel_04= (RelativeLayout) findViewById(R.id.rel_04);
        back= (ImageView) findViewById(R.id.img_back);
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        lineChart1= (LineChartView) findViewById(R.id.line_chart_1);
        tv_language= (TextView) findViewById(R.id.language);
//        getAxisXLables();//获取x轴的标注
//        getAxisPoints();//获取坐标点
//        initLineChart();//初始化
        name= (TextView) findViewById(R.id.name);
        category= (TextView) findViewById(R.id.category);
        ap= (TextView) findViewById(R.id.ap);
        describe= (TextView) findViewById(R.id.describe);
        huoqu= (ImageView) findViewById(R.id.huoqu);
        img= (ImageView) findViewById(R.id.pic);
        ap_1= (ImageView) findViewById(R.id.ap_1);
        ap_2= (ImageView) findViewById(R.id.ap_2);
        ap_3= (ImageView) findViewById(R.id.ap_3);
        ln_ap= (LinearLayout) findViewById(R.id.ln_ap);
        background= (RelativeLayout) findViewById(R.id.background);

    }
    private void initLineChart() {
        //网络价格
        List<Line> lines = new ArrayList<Line>();
        if(ishowonline){
//            Log.e("cccc", String.valueOf(mPointValues.size()));
            Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
            line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
            line.setCubic(true);//曲线是否平滑，即是曲线还是折线
            line.setFilled(false);//是否填充曲线的面积
            line.setHasLabels(false);//曲线的数据坐标是否加上备注
            line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
            line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            lines.add(line);
        }
        if(ishowoffline){
            //实体价格
            Line line1 = new Line(mPointValues1).setColor(Color.parseColor("#ffcc0000"));  //折线的颜色（橙色）
            line1.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
            line1.setCubic(false);//曲线是否平滑，即是曲线还是折线
            line1.setFilled(false);//是否填充曲线的面积
            line1.setHasLabels(false);//曲线的数据坐标是否加上备注
            line1.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line1.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
            line1.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            lines.add(line1);
        }
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        axisX.setName(" ");//设置X轴显示名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(6); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName(" ");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
//        data.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
//        data.setValueLabelBackgroundColor(Color.BLUE);// 设置数据背景颜色
//        data.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
//        data.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
        data.setValueLabelTextSize(15);// 设置数据文字大小
        data.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 5);//最大方法比例
        lineChart.setZoomEnabled(false);//设置是否支持缩放
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
/**注：下面的7，10只是代表一个数字去类比而已
 *   * 当时是为了解决X轴固定数据个数。见（http://forum.xda-
 */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 10;
//        v.bottom= 1;
//        v.top= 30;
        lineChart.setCurrentViewport(v);
    }

    private void initLineChart1() {
        //网络价格
        List<Line> lines = new ArrayList<Line>();
        if(ishowonline1){
            Line line = new Line(mPointValues_1).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
            line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
            line.setCubic(true);//曲线是否平滑，即是曲线还是折线
            line.setFilled(false);//是否填充曲线的面积
            line.setHasLabels(false);//曲线的数据坐标是否加上备注
            line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
            line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            lines.add(line);
        }
        if(ishowoffline1){
            //实体价格
            Line line1 = new Line(mPointValues_11).setColor(Color.parseColor("#ffcc0000"));  //折线的颜色（橙色）
            line1.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
            line1.setCubic(false);//曲线是否平滑，即是曲线还是折线
            line1.setFilled(false);//是否填充曲线的面积
            line1.setHasLabels(false);//曲线的数据坐标是否加上备注
            line1.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line1.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
            line1.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            lines.add(line1);
        }
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        axisX.setName(" ");//设置X轴显示名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(6); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues_1);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName(" ");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
//        data.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
//        data.setValueLabelBackgroundColor(Color.BLUE);// 设置数据背景颜色
//        data.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
//        data.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
        data.setValueLabelTextSize(15);// 设置数据文字大小
        data.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        lineChart1.setInteractive(true);
        lineChart1.setZoomType(ZoomType.HORIZONTAL);
        lineChart1.setMaxZoom((float) 5);//最大方法比例
        lineChart1.setZoomEnabled(false);//设置是否支持缩放
        lineChart1.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart1.setLineChartData(data);
        lineChart1.setVisibility(View.VISIBLE);
/**注：下面的7，10只是代表一个数字去类比而已
 *   * 当时是为了解决X轴固定数据个数。见（http://forum.xda-
 */
        Viewport v = new Viewport(lineChart1.getMaximumViewport());
        v.left = 0;
        v.right= 10;
//        v.bottom= 1;
//        v.top= 30;
        lineChart1.setCurrentViewport(v);
    }

    public void getAxisXLables() {
        for(int i=0;i<dateList.size();i++){
            mAxisXValues.add(new AxisValue(i).setLabel(dateList.get(i)));
        }
        for(int i=0;i<dateList_1.size();i++){
            mAxisXValues_1.add(new AxisValue(i).setLabel(dateList_1.get(i)));
        }
    }

    public void getAxisPoints() {
        for (int i = 0; i < scoreList.size(); i++) {
            mPointValues.add(new PointValue(i, scoreList.get(i)));
        }
        for (int i = 0; i < scoreList1.size(); i++) {
            mPointValues1.add(new PointValue(i, scoreList1.get(i)));
        }
        for (int i = 0; i < scoreList_1.size(); i++) {
            mPointValues_1.add(new PointValue(i, scoreList_1.get(i)));
        }
        for (int i = 0; i < scoreList_11.size(); i++) {
            mPointValues_11.add(new PointValue(i, scoreList_11.get(i)));
        }

    }
    private void dialogShow() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View view = inflater.inflate(R.layout.language_dialog, null);

        final AlertDialog alertDialog=builder.create();
        alertDialog.setView(view, 0, 0, 0, 0);
        alertDialog.show();
        TextView cn= (TextView) view.findViewById(R.id.cn);
        TextView en= (TextView) view.findViewById(R.id.en);
        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this,"中文切换成功",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                if(magic1.getLanguage().equals("en")){
                    showLanguage("cn");
                    String ls="http://60.205.177.241:8001/api/Card/OtherLanguageCard?cardID="+magic1.getId()+"&otherLanguage=cn";
                    translate(ls);
                }

//                string = "http://60.205.177.241:8001/api/Card/CardInfo?cardID="+id+"&&language=cn";
//                initEvent();

            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this,"英文切换成功",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                if(magic1.getLanguage().equals("cn")){
                    showLanguage("en");
                    String ls="http://60.205.177.241:8001/api/Card/OtherLanguageCard?cardID="+magic1.getId()+"&otherLanguage=en";
                    translate(ls);
                }
//                string = "http://60.205.177.241:8001/api/Card/CardInfo?cardID="+id+"&&language=en";
//                initEvent();

            }
        });

    }
    private void showLanguage(String language){
        if(language.equals("en")){
            tv_language.setText("English");
            Drawable drawable_n = getResources().getDrawable(R.drawable.en);
            drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(),drawable_n.getMinimumHeight());  //此为必须写的
            tv_language.setCompoundDrawables(drawable_n, null, null, null);

        }else{
            tv_language.setText("中文");
            Drawable drawable_n = getResources().getDrawable(R.drawable.cn);
            drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(),drawable_n.getMinimumHeight());  //此为必须写的
            tv_language.setCompoundDrawables(drawable_n, null, null, null);
        }
    }
    /*更新详情页面*/
    private void update(final Magic magic){
        name.setText(magic.getName());
        if(magic.getLanguage().equals("cn")){
            //显示中文
            languag="cn";
            showLanguage("cn");
        }else{
            //显示英文
            languag="en";
            showLanguage("en");
        }
        category.setText(Html.fromHtml(magic.getCategory()));
        ap.setText(magic.getMagic());
        describe.setText(Html.fromHtml(magic.getDescribe()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=null;
                String s="http://60.205.177.241/MagicCardData"+magic.getPic();
//                Log.d("测试",s);
                try {
                    bitmap=getBitmap(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.what=1;
                msg.obj=bitmap;
                handler.sendMessage(msg);


            }
        }).start();


    }
    /*展示卡牌图片*/
    public static Bitmap getBitmap(String path) throws IOException{

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        Bitmap bitmap = null;
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }else{

        }
        return bitmap;
    }
    /*切换卡牌背景色*/
    private void showCardBackground(String string){
        for(int i=0;i<string.length();i++){
            String s=String.valueOf(string.charAt(i));
            if(s.equals("1")||s.equals("2")||s.equals("3")||s.equals("X")||s.equals("4")){

            }else if(s.equals("W")){
                background.setBackgroundResource(R.drawable.background_white);
                break;
            }else if(s.equals("U")){
                background.setBackgroundResource(R.drawable.background_blue);
                break;
            }else if(s.equals("B")){
                background.setBackgroundResource(R.drawable.background_black);
                break;
            }else if(s.equals("R")){
                background.setBackgroundResource(R.drawable.background_red);
                break;
            }else if(s.equals("G")){
                background.setBackgroundResource(R.drawable.background);
                break;
            }else{


            }
        }
    }


    /*展示魔法值*/
    private void showMagic(String string){
        ln_ap.removeAllViews();
        for(int i=0;i<string.length();i++){
//            Log.d("测试", String.valueOf(string.charAt(i)));
            String s=String.valueOf(string.charAt(i));
            if(s.equals("1")||s.equals("2")||s.equals("3")||s.equals("X")||s.equals("4")){
                TextView textView=new TextView(this);
                textView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                textView.setText(s);
                textView.setPadding(4,4,4,4);
                ln_ap.addView(textView);
            }else if(s.equals("W")){
                ImageView view=new ImageView(this);
                view.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                view.setImageResource(R.drawable.w);
                view.setPadding(4,4,4,4);
                ln_ap.addView(view);
            }else if(s.equals("U")){
                ImageView view=new ImageView(this);
                view.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                view.setImageResource(R.drawable.u);
                view.setPadding(4,4,4,4);
                ln_ap.addView(view);
            }else if(s.equals("B")){
                ImageView view=new ImageView(this);
                view.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                view.setImageResource(R.drawable.b);
                view.setPadding(4,4,4,4);
                ln_ap.addView(view);
            }else if(s.equals("R")){
                ImageView view=new ImageView(this);
                view.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                view.setImageResource(R.drawable.r);
                view.setPadding(4,4,4,4);
                ln_ap.addView(view);
            }else if(s.equals("G")){
                ImageView view=new ImageView(this);
                view.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                view.setImageResource(R.drawable.g);
                view.setPadding(4,4,4,4);
                ln_ap.addView(view);
            }else{


            }
        }
    }
    private void isShowFlash(String htmlStr) throws JSONException {
        JSONObject object=new JSONObject(htmlStr);
        String date=object.getString("Data");
        JSONObject object1=new JSONObject(date);
        boolean f=  object1.getBoolean("HasFoil");
        Log.e("fff", String.valueOf(f));
        if(f){
            rel_04.setVisibility(View.VISIBLE);
        }else {
            rel_04.setVisibility(View.GONE);
        }
        String priceList=object1.getString("PriceList");
        JSONArray array=new JSONArray(priceList);
        for(int i=array.length()-1;i>array.length()-30;i--){
            JSONObject object2=array.getJSONObject(i);
            float price= (float) object2.getDouble("PaperPrice");
            float price1= (float) object2.getDouble("OnlinePrice");
            String createDate=object2.getString("CreateDate");
            float price_1= (float) object2.getDouble("FoilPaperPrice");
            float price_11= (float) object2.getDouble("FoilOnlinePrice");
            String createDate_1=object2.getString("CreateDate");
            dateList.add(createDate);
            scoreList1.add(price);
            scoreList.add(price1);
            dateList_1.add(createDate_1);
            scoreList_11.add(price_1);
            scoreList_1.add(price_11);
        }
//        Log.e("",scoreList.toString());
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
        initLineChart1();

    }
    //翻译卡牌
    private void translate(String url){
        final Request.Builder builder = new Request.Builder().url(url);
        //将请求头以键值对形式添加，可添加多个请求头
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
                        Message msg=new Message();
                        msg.what=0;
                        msg.obj=string;
                        handler.sendMessage(msg);
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

