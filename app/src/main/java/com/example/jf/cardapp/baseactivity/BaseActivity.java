package com.example.jf.cardapp.baseactivity;

import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.jf.cardapp.utils.PreferenceUtil;

import java.util.Locale;

/**
 * 所有activity基类
 * Created by jf on 2017/2/9.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        setListaner();
        //初始化PreferenceUtil
        PreferenceUtil.init(this);
        //根据上次的语言设置，重新设置语言
        switchLanguage(PreferenceUtil.getString("language", "zh"));
    }

    protected abstract int getLayoutId();

    /** 设置事件监听*/
    protected  void setListaner(){};



    /** 初始化数据*/
    protected abstract void initData() ;


    /** 初始化Activity的显示视图*/
    protected abstract void initView();
    protected void switchLanguage(String language) {
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
        } else {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);

        //保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }

}
