package com.example.jf.cardapp.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.entity.PeopleData;
import com.example.jf.cardapp.entity.ReturnPost;
import com.example.jf.cardapp.entity.UpDatePost;
import com.example.jf.cardapp.utils.Code;
import com.example.jf.cardapp.utils.RandomCode;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jf on 2017/2/12.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.img_login_top_back)
    ImageView imgLoginTopBack;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.bt_in)
    Button btIn;
    @BindView(R.id.forgetpsd)
    TextView forgetpsd;
    @BindView(R.id.enroll)
    TextView enroll;
    @BindView(R.id.weichat)
    ImageView weichat;
    @BindView(R.id.pay)
    ImageView pay;

    private String name;
    private String password;
    private String code;
    private String systemCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setListaner() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
// TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //验证码
        ivCode = (ImageView) findViewById(R.id.iv_code);
        ivCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成验证码并显示在图片上
                ivCode.setImageBitmap(Code.getInstance().createBitmap());
                systemCode = Code.getCode();

                Toast.makeText(LoginActivity.this,systemCode,Toast.LENGTH_LONG).show();
            }
        });
    }



    @OnClick({R.id.img_login_top_back, R.id.iv_code, R.id.bt_in, R.id.forgetpsd, R.id.enroll, R.id.weichat, R.id.pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_login_top_back:
                finish();
                break;
            case R.id.iv_code:
                break;
            case R.id.bt_in:

                name = etPeople.getText().toString();
                password = etPassword.getText().toString();
                code = etCode.getText().toString();


                    if(systemCode.equalsIgnoreCase(code)){

                        if (isEmail(name)){
                                goHttp(name,password);
                            Log.e("登录","邮箱登陆");
                        }else{
                            if(isMobileNO(name)){
                                goHttp(name,password);
                                Log.e("登录","手机登陆");
                            }
                            else{ Toast.makeText(LoginActivity.this,"输入手机号/邮箱不正确",Toast.LENGTH_LONG).show();}
                        }
                    }else{ Toast.makeText(LoginActivity.this,"输入验证码不正确",Toast.LENGTH_LONG).show();}




                break;
            case R.id.forgetpsd:
                break;
            case R.id.enroll:
                Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.weichat:
                break;
            case R.id.pay:
                break;
        }
    }
//验证手机号
        public static boolean isMobileNO(String mobiles) {
            Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
    //验证邮箱号
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /*//验证密码是否含有数字和字母
    public static final boolean isRightPwd(String pwd) {
        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }*/
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
                        JSONObject object = null;
                        String AuthenticToken="noall";
                        int errorCode = 1;
                        try {
                            object=new JSONObject(s);
                            errorCode= object.getInt("ErrorCode");
                            JSONObject object1=new JSONObject(object.getString("Date"));
                            AuthenticToken=object1.getString("AuthenticToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PeopleData data = new PeopleData();
                        Gson gson1 = new Gson();
                        data = gson1.fromJson(s, PeopleData.class);
                        if (errorCode == 0) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            //存用户名密码
                            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id",i);
                            editor.putString("password",j);
                            editor.putString("AuthenticToken",AuthenticToken);
                            editor.commit();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "未登录", Toast.LENGTH_LONG).show();
                        }

                    }


                });


            }
        }).start();
    }

    @Override
    protected void onResume() {
        ivCode.setImageBitmap(Code.getInstance().createBitmap());
        systemCode = Code.getCode();
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
//            // 设定结果返回
//
//            startActivityForResult(intent, 2);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
