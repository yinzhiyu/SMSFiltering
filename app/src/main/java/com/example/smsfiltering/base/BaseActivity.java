package com.example.smsfiltering.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.smsfiltering.utils.ActivityCollector;

/**
 * Created by yinzhiyu on 2018-3-13.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 跳转到指定的Activity 默认不结束本界面
     *
     * @param cls
     */
    protected void startNewActivity(Class<?> cls) {
        startNewActivity(cls, false);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param cls
     */
    protected void startNewActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

    /**
     * 退出登录
     *
     * @param context
     */
    public static void logout(Context context) {
        Intent it = new Intent();
        ActivityCollector.finsihAll();
//        it.setClass(context, LoginActivityImpl.class);
        context.startActivity(it);
    }
}
