package com.example.smsfiltering.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by si on 2018-3-13.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    //全局Context
    private static Context context;
//    public static BaseApplication getInstance() {
//        return instance;
//    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }
    /*
   设置全局Context,便于不是Activity的类调用
    */
    public static Context getContext() {
        return context;
    }
}
