package com.example.jf.cardapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.entity.FeedbackBean;
import com.example.jf.cardapp.entity.PeopleData;
import com.example.jf.cardapp.entity.ReturnPost;
import com.example.jf.cardapp.entity.UpDatePost;
import com.example.jf.cardapp.utils.DataCleanManager;
import com.example.jf.cardapp.utils.NotifyMannger;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jf on 2017/2/12.
 */

public class SetActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_set_title)
    TextView tvSetTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.ll_setapp)
    LinearLayout llSetapp;
    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.img_set_push)
    ImageView imgSetPush;
    @BindView(R.id.ll_push)
    RelativeLayout llPush;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    @BindView(R.id.ll_logout)
    RelativeLayout llLogout;
    @BindView(R.id.img_clearcache)
    ImageView imgClearcache;
    @BindView(R.id.ll_clear_cache)
    RelativeLayout llClearCache;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.img_problem)
    ImageView imgProblem;
    @BindView(R.id.ll_problem)
    RelativeLayout llProblem;
    @BindView(R.id.img_coupleback)
    ImageView imgCoupleback;
    @BindView(R.id.ll_coupleback)
    RelativeLayout llCoupleback;
    @BindView(R.id.img_score)
    ImageView imgScore;
    @BindView(R.id.ll_score)
    RelativeLayout llScore;
    @BindView(R.id.img_set_about)
    ImageView imgSetAbout;
    @BindView(R.id.ll_set_about)
    RelativeLayout llSetAbout;
    @BindView(R.id.img_set_top_back)
    ImageView imgSetTopBack;

    private int tag = 1;
    private LayoutInflater inflater;
    private View layout;
    private Dialog dialog;
    private TextView title;
    private TextView english;
    private TextView chinese;

    private EditText fdTitle;
    private EditText fdContent;
    private Button fdConfirm;
    private Button fdCancel;
    private String AuthenticToken;
    private String alter = "no";
//    private SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);、

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void setListaner() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        Intent intent = getIntent();
        AuthenticToken=intent.getStringExtra("AuthenticToken");
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.ll_push, R.id.ll_logout, R.id.ll_clear_cache, R.id.ll_problem, R.id.ll_coupleback, R.id.ll_score, R.id.ll_set_about})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_push:
                inflater = getLayoutInflater();
                layout = inflater.inflate(R.layout.dialog_select_lanuage,null);
                title = (TextView) layout.findViewById(R.id.clear_cache_title);
                english = (TextView) layout.findViewById(R.id.select_english);
                chinese = (TextView) layout.findViewById(R.id.select_chinese);
                    title.setText("通知");
                    english.setText("打开");
                    chinese.setText("关闭");
                dialog = new Dialog(SetActivity.this,R.style.Custom_Dialog_Theme);
                    dialog.setCanceledOnTouchOutside(false);
                    english.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            NotifyMannger.showNofify(SetActivity.this);


                        }
                    });
                    chinese.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            NotifyMannger.closeNotify(SetActivity.this);
                        }
                    });
                    dialog.setContentView(layout);
                    NotifyMannger.saveNotifytatic(SetActivity.this,true);

                dialog.show();
                break;
            case R.id.ll_logout:
                inflater = getLayoutInflater();
                layout = inflater.inflate(R.layout.dialog_select_lanuage,null);
                title = (TextView) layout.findViewById(R.id.clear_cache_title);
                english = (TextView) layout.findViewById(R.id.select_english);
                chinese = (TextView) layout.findViewById(R.id.select_chinese);
                    title.setText("注销");
                    english.setText("确定");
                    chinese.setText("取消");
                dialog = new Dialog(SetActivity.this,R.style.Custom_Dialog_Theme);
                    dialog.setCanceledOnTouchOutside(false);
                    english.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("AuthenticToken","noall");
                            editor.putString("id","noall");
                            editor.putString("password","noall");
                            editor.commit();
                            logout();


                        }
                    });
                    chinese.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(layout);

                dialog.show();
                break;
            case R.id.ll_clear_cache:
                inflater = getLayoutInflater();
                layout = inflater.inflate(R.layout.dialog_select_lanuage,null);
                title = (TextView) layout.findViewById(R.id.clear_cache_title);
                english = (TextView) layout.findViewById(R.id.select_english);
                chinese = (TextView) layout.findViewById(R.id.select_chinese);
                    title.setText("缓存");
                    english.setText("清除");
                    chinese.setText("取消");
                    final TextView formatSize = (TextView) layout.findViewById(R.id.formatSize);
                    formatSize.setVisibility(View.VISIBLE);
                    try {
                        formatSize.setText(DataCleanManager.getTotalCacheSize(SetActivity.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog = new Dialog(SetActivity.this,R.style.Custom_Dialog_Theme);
                    dialog.setCanceledOnTouchOutside(false);
                    english.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            formatSize.setVisibility(View.GONE);
                            DataCleanManager.clearAllCache(SetActivity.this);
                            Toast.makeText(SetActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                    chinese.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            formatSize.setVisibility(View.GONE);
                        }
                    });
                    dialog.setContentView(layout);

                dialog.show();
                break;
            case R.id.ll_problem:
                break;
            case R.id.ll_coupleback:
                inflater = getLayoutInflater();
                layout = inflater.inflate(R.layout.dialog_opinion,null);
                fdTitle = (EditText) layout.findViewById(R.id.et_title);
                fdContent = (EditText) layout.findViewById(R.id.et_content);
                fdConfirm = (Button) layout.findViewById(R.id.but_confirm);
                fdCancel = (Button) layout.findViewById(R.id.but_cancel);
                dialog = new Dialog(SetActivity.this,R.style.Custom_Dialog_Theme);
                dialog.setCanceledOnTouchOutside(false);


                fdConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(fdTitle.getText().toString())&&!TextUtils.isEmpty(fdContent.getText().toString())){
                            final FeedbackBean bean = new FeedbackBean(fdTitle.getText().toString(),fdContent.getText().toString(),0);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpClient httpClient=new OkHttpClient();
                                    RequestBody requestBody=new FormBody.Builder()
                                            .add("Title", fdTitle.getText().toString())
                                            .add("Content", fdContent.getText().toString())
                                            .add("Type", String.valueOf(0))
                                            .build();
                                    Request request = new Request.Builder()
                                            .post(requestBody).url("http://60.205.177.241:8001/api/Common/Fadeback ")
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
                                            dialog.dismiss();

                                        }
                                    });
                                }
                            }).run();
//                            final Gson gson = new Gson();
//                            OkHttpUtils.post("http://60.205.177.241:8001/api/Common/Fadeback").tag(this).upJson(gson.toJson(bean)).execute(new StringCallback() {
//                                @Override
//                                public void onSuccess(String s, Call call, Response response) {
//                                    Log.e("test","http://60.205.177.241:8001/api/Common/Fadeback"+gson.toJson(bean));
//                                    Log.e("test2",s);
//                                    Toast.makeText(SetActivity.this,s,Toast.LENGTH_LONG).show();
//                                    dialog.dismiss();
//
//                                }
//                            });

                        }
                        else{
                            Toast.makeText(SetActivity.this,"标题或内容不能为空",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                fdCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(layout);

                dialog.show();
                break;
            case R.id.ll_score:
                break;
            case R.id.ll_set_about:
                break;
        }
    }

    @OnClick(R.id.img_set_top_back)
    public void onClick() {
        Intent intent =new Intent(SetActivity.this,HomeActivity.class);
        Bundle b=new Bundle();
        b.putString("data",alter);
        intent.putExtras(b);
        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            Intent intent =new Intent(SetActivity.this,HomeActivity.class);
            Bundle b=new Bundle();
            b.putString("data",alter);
            intent.putExtras(b);
            setResult(RESULT_OK,intent);
            finish();}
        return false;


    }
    private void logout() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpUtils.post("http://60.205.177.241:8001/api/User/Logout").tag(this).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("seshi", s);


                        ReturnPost data = new ReturnPost();
                        Gson gson1 = new Gson();
                        data = gson1.fromJson(s, ReturnPost.class);
                        if (data.getErrorCode() == 0) {
                            Toast.makeText(SetActivity.this, "成功退出", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SetActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SetActivity.this, data.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });


            }
        }).start();

    }

}
