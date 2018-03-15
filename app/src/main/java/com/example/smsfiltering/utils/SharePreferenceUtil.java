package com.example.smsfiltering.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smsfiltering.base.BaseApplication;

/**
 * Created by zcyz on 2016/12/9.
 */

public class SharePreferenceUtil {
    //用户alias
    public static final String ALIAS = "alias";
    public static final String PHONE = "phone";
    public static final String PWD = "pwd";
    private static Context mContext = BaseApplication.getInstance();

    /**
     * 存储信息
     *
     * @param context
     * @param key     键
     * @param value   值
     */
    public static void saveInfo(Context context, String key, String value) {
        SharedPreferences preferences;
        preferences = mContext.getSharedPreferences("optimization", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 存储信息2
     *
     * @param context
     * @param key     键
     * @param value   值
     */
    public static void saveInfo2(Context context, String key, Boolean value) {
        SharedPreferences preferences;
        preferences = mContext.getSharedPreferences("optimization", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取信息
     *
     * @param context
     * @param key     键
     */
    public static String getInfo(Context context, String key) {
        String userInfo;
        SharedPreferences preferences;
        preferences = mContext.getSharedPreferences("optimization", context.MODE_PRIVATE);
        userInfo = preferences.getString(key, "");
        return userInfo;
    }

    /**
     * 获取信息
     *
     * @param context
     * @param key     键
     */
    public static Boolean getInfo1(Context context, String key) {
        Boolean translation;
        SharedPreferences preferences;
        preferences = mContext.getSharedPreferences("optimization", context.MODE_PRIVATE);
        translation = preferences.getBoolean(key, true);
        return translation;
    }


    /**
     * 清除用户信息
     *
     * @param context
     */
    public static void deleteAll(Context context) {
        SharedPreferences preferences;
        preferences = mContext.getSharedPreferences("optimization", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
