package com.example.smsfiltering.modules;

import android.os.Bundle;
import android.os.Handler;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseActivity;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.utils.SharePreferenceUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (SharePreferenceUtil.getInfo1(BaseApplication.getContext(), SharePreferenceUtil.ALIAS)) {
//                    startNewActivity(MainActivity.class, true);
                    startNewActivity(MainActivity.class, true);
                } else {
                    startNewActivity(LoginActivity.class, true);
                }
                finish();
            }
        }, time);
    }
}
