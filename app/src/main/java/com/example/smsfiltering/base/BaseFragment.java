package com.example.smsfiltering.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    protected boolean isInit = false;
    protected Activity activity;
    protected Context mContext;
    protected final int mPageSize = 10;
    protected final int FIRST_PAGE_NO = 1;
    protected int mPageNo = FIRST_PAGE_NO;
    protected int mTotalNo = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        mContext = getActivity();
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
        intent.setClass(getActivity(), cls);
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param cls
     */
    protected void startNewActivityForResult(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent, 0);
    }

    protected void initViews() {
    }


    protected void showLoading() {
        showLoading(true);
    }

    /**
     * 显示Loading
     *
     * @param allowClose 是否允许关闭
     */
    protected void showLoading(boolean allowClose) {
        //TODO 暂未实现Loading

    }

    protected void closeLoading() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        onFragmentResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //相当于Fragment的onResume
//            onFragmentResume();
//        } else {
//            //相当于Fragment的onPause
//            onFragmentPause();
//        }
    }

    protected void onFragmentResume() {
        if (isInit) {
            return;
        }
        initDats();
    }

    protected void onFragmentPause() {
    }

    protected void initDats() {
        isInit = true;
    }

}
