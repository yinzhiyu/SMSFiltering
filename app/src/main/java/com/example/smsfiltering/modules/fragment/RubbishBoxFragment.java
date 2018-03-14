package com.example.smsfiltering.modules.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 垃圾箱
 */

public class RubbishBoxFragment extends BaseFragment {
    @BindView(R.id.rv_home_recycler)
    RecyclerView rvHomeRecycler;
    @BindView(R.id.srl_home_swipe_refresh)
    SwipeRefreshLayout srlHomeSwipeRefresh;
    @BindView(R.id.toolbar_home)
    RelativeLayout toolbar_home;

    private int totalScrollDistance;
    private boolean isShow = true;
    private boolean isShowToolbar = true;
    private static final int SCROLL_DISTANCE = 40;

    public static RubbishBoxFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        RubbishBoxFragment fragment = new RubbishBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        srlHomeSwipeRefresh.setEnabled(true);
        srlHomeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadData();
            }
        });
        rvHomeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    totalScrollDistance += dy;
                    if (totalScrollDistance > SCROLL_DISTANCE && toolbar_home.getVisibility() == View.GONE) {
                        show();
                        isShowToolbar = false;
                    } else if (totalScrollDistance < SCROLL_DISTANCE && toolbar_home.getVisibility() == View.VISIBLE) {
                        hide();
                        isShowToolbar = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void hide() {
        // 组合动画设置-title动画消失
        AnimationSet setAnimation = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(1000);
        setAnimation.addAnimation(alphaAnimation);
        toolbar_home.startAnimation(setAnimation);
        toolbar_home.setVisibility(View.GONE);
    }

    private void show() {
        // 组合动画设置-title动画显示
        AnimationSet setAnimation = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1500);
        setAnimation.addAnimation(alphaAnimation);
        toolbar_home.startAnimation(setAnimation);
        toolbar_home.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.rv_home_recycler, R.id.srl_home_swipe_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rv_home_recycler:
                break;
            case R.id.srl_home_swipe_refresh:
                break;
        }
    }
}
