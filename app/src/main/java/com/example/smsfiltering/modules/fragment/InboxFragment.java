package com.example.smsfiltering.modules.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.base.BaseFragment;
import com.example.smsfiltering.greendao.SMSDao;
import com.example.smsfiltering.modules.adapter.InboxAdapter;
import com.example.smsfiltering.table.SMS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收件箱BlacklistFragment
 */

public class InboxFragment extends BaseFragment {
    @BindView(R.id.rv_home_recycler)
    RecyclerView rvHomeRecycler;
    @BindView(R.id.srl_home_swipe_refresh)
    SwipeRefreshLayout srlHomeSwipeRefresh;
    @BindView(R.id.toolbar_home)
    RelativeLayout toolbar_home;

    private boolean isShowToolbar = true;
    //--------------------
    private static final int SCROLL_DISTANCE = 50;
    private LinearLayoutManager layoutManager;
    private boolean isShow = true;
    private int totalScrollDistance;
    private int pageNum;
    private InboxAdapter mInboxAdapter;
    public static InboxFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        InboxFragment fragment = new InboxFragment();
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
                pageNum = 1;
                getData(pageNum);
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
        setLayoutManager();
        recycleScroll();
    }

    private void getData(int num) {
        SMSDao smsDao = BaseApplication.getInstance().getDaoSession().getSMSDao();
        List<SMS> smsList = smsDao.loadAll();
        if (srlHomeSwipeRefresh != null) {
            srlHomeSwipeRefresh.setRefreshing(false);
        }
        mInboxAdapter = new InboxAdapter(BaseApplication.getContext(), smsList);
        rvHomeRecycler.setAdapter(mInboxAdapter);

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

    /**
     * 监听上拉加载
     */
    private void recycleScroll() {
        rvHomeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && !isShow) {// (totalItemCount - 1) && isSlidingToLast
                        //加载更多功能的代码
                        pageNum++;
                        getData(pageNum);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisableItem == 0) {
                    return;
                }
                if ((dy > 0 && isShow) || (dy < 0 && !isShow)) {
                    totalScrollDistance += dy;
                }
                if (totalScrollDistance > SCROLL_DISTANCE && isShow) {
                    isShow = false;
                    totalScrollDistance = 0;
                } else if (totalScrollDistance < -SCROLL_DISTANCE && !isShow) {
                    isShow = true;
                    totalScrollDistance = 0;
                }
            }
        });
    }

    private void setLayoutManager() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomeRecycler.setLayoutManager(layoutManager);
        rvHomeRecycler.setItemAnimator(new DefaultItemAnimator());
        rvHomeRecycler.setHasFixedSize(true);
        rvHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
//        rvHomeRecycler.addItemDecoration(new DividerListItemDecoration(this, R.drawable.shape_divide_line));
    }
}
