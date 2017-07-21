package com.example.jf.cardapp.activity;


import android.content.Intent;

import android.util.Log;
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



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jf on 2017/2/23.
 */

public class SignInActivity extends BaseActivity {
    @BindView(R.id.iv_enroll_back)
    ImageView ivEnrollBack;
    @BindView(R.id.et_enroll_people)
    EditText etEnrollPeople;
    @BindView(R.id.et_enroll_code)
    EditText etEnrollCode;
    @BindView(R.id.iv_enroll_code)
    ImageView ivEnrollCode;
    @BindView(R.id.et_setpassword)
    EditText etSetpassword;
    @BindView(R.id.et_re_setpassword)
    EditText etReSetpassword;
    @BindView(R.id.bt_finish)
    Button btFinish;
    @BindView(R.id.tv_deal)
    TextView tvDeal;

    private String systemCode;
    private String name;
    private String code;
    private String password;
    private String passwordAgain;
    private String string;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_signin;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
// TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_enroll_back, R.id.iv_enroll_code, R.id.bt_finish, R.id.tv_deal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_enroll_back:
                finish();
                break;
            case R.id.iv_enroll_code:
                ivEnrollCode.setImageBitmap(Code.getInstance().createBitmap());
                systemCode = Code.getCode();

                Toast.makeText(SignInActivity.this, systemCode, Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_finish:
                name = etEnrollPeople.getText().toString();
                code = etEnrollCode.getText().toString();
                password = etSetpassword.getText().toString();
                passwordAgain = etReSetpassword.getText().toString();
                if (name.length() != 0 && code.length() != 0 && password.length() != 0 && passwordAgain.length() != 0) {
                    if (systemCode.equalsIgnoreCase(code)) {
                        if (password.equals(passwordAgain)) {
                            if (isEmail(name)) {
                                Log.e("dianji", "邮箱注册");
                                goHttp(name, password);
                            } else {
                                if (isMobileNO(name)) {
                                    Log.e("dianji", "电话注册");
                                    //给服务器发送请求
                                    goHttp(name, password);

                                } else {
                                    Toast.makeText(SignInActivity.this, "输入手机号/邮箱不正确", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, "密码不一致", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "输入验证码不正确", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "填写不完整", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_deal:
                break;
        }
    }

    private void goHttp(final String i, final String j) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpDatePost post = new UpDatePost(i, j);
                Gson gson = new Gson();
                OkHttpUtils.post("http://60.205.177.241:8001/api/User/Register").tag(this).upJson(gson.toJson(post)).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("seshi", s);
                        ReturnPost data = new ReturnPost();
                        Gson gson1 = new Gson();
                        data = gson1.fromJson(s, ReturnPost.class);
                        if (data.getErrorCode() == 0) {
                            Toast.makeText(SignInActivity.this, data.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, data.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }


                });


            }
        }).start();
    }

    //验证手机号
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //验证邮箱号
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivEnrollCode.setImageBitmap(Code.getInstance().createBitmap());
        systemCode = Code.getCode();
    }
}
