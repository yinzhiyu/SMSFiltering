package com.example.smsfiltering.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.smsfiltering.modules.LoginActivity;
import com.example.smsfiltering.utils.ActivityCollector;
import com.example.smsfiltering.utils.CommonUtil;
import com.example.smsfiltering.utils.SharePreferenceUtil;

/**
 * Created by yinzhiyu on 2018-3-13.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        ActivityCollector.addActivity(this);
        mContext = this;

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (shouleHideIM(v, ev)) {
                    CommonUtil.hideIME(mContext, v);
                }
//                if (AppUtils.isSHowKeyboard(this, v)) {
//                    regType = true;
//                } else {
//                    regType = false;
//                }
            }
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    protected boolean shouleHideIM(View v, MotionEvent ev) {

        if (null != v && v instanceof EditText) {

            // 获取当前edittext在屏幕中的位置
            int[] location = {0, 0};
            v.getLocationInWindow(location);// x:location[0] y:location[1]
            int left = location[0], top = location[1];
            int bottom = top + v.getHeight(), right = left + v.getWidth();

            // 判断点击时间是否在输入框区域，true就不隐藏，false代表发生在区域外，需隐藏软键盘
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 退出登录
     *
     * @param context
     */
    public static void logout(Context context) {
        Intent it = new Intent();
        SharePreferenceUtil.saveInfo2(BaseApplication.getContext(), SharePreferenceUtil.ALIAS, false);
        ActivityCollector.finsihAll();
        it.setClass(context, LoginActivity.class);
        context.startActivity(it);
    }
}
