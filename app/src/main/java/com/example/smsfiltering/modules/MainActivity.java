package com.example.smsfiltering.modules;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseActivity;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.BlackWordDao;
import com.example.smsfiltering.greendao.KeyWordDao;
import com.example.smsfiltering.greendao.SMSDao;
import com.example.smsfiltering.greendao.UserDao;
import com.example.smsfiltering.greendao.WhiteWordDao;
import com.example.smsfiltering.http.LtpCloud;
import com.example.smsfiltering.modules.adapter.OnListener;
import com.example.smsfiltering.modules.fragment.InboxFragment;
import com.example.smsfiltering.modules.fragment.MeFragment;
import com.example.smsfiltering.modules.fragment.PhoneFragment;
import com.example.smsfiltering.modules.fragment.RubbishBoxFragment;
import com.example.smsfiltering.modules.tag.TagMangerActivity;
import com.example.smsfiltering.table.BlackWord;
import com.example.smsfiltering.table.KeyWord;
import com.example.smsfiltering.table.SMS;
import com.example.smsfiltering.table.User;
import com.example.smsfiltering.table.WhiteWord;
import com.example.smsfiltering.utils.DBUtil;
import com.example.smsfiltering.utils.FilterUtil;
import com.example.smsfiltering.utils.SharePreferenceUtil;
import com.example.smsfiltering.utils.SnackbarUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationBar.OnTabSelectedListener{

    @BindView(R.id.tb)
    FrameLayout mTb;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.parent)
    AutoLinearLayout mParent;

    private FragmentManager mFragmentManager;
    private PhoneFragment phoneFragment;
    //      private BadgeItem mHomeNumberBadgeItem, mMusicNumberBadgeItem;
    private InboxFragment mInboxFragment;
    private RubbishBoxFragment mRubbishBoxFragment;
    private MeFragment mMeFragment;
    private int lastSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        initView();
        setDefaultFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new MyTask().execute();
    }

    private void initView() {
        /**
         *添加标签的消息数量
         */
//        mHomeNumberBadgeItem = new BadgeItem()
//                .setBorderWidth(2)
//                .setBackgroundColor(Color.RED)
//                .setText("4")
//                .setHideOnSelect(false); // TODO 控制便签被点击时 消失 | 不消失
        /**
         *添加标签的消息数量
         */
//        mMusicNumberBadgeItem = new BadgeItem()
//                .setBorderWidth(2)
//                .setBackgroundColor(Color.RED)
//                .setText("99+")
//                .setHideOnSelect(true);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //  设置模式
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setInActiveColor(R.color.color_555555);
        //  设置背景色样式
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home_s, "收件箱").setInactiveIcon(getResources().getDrawable(R.drawable.home_n)))
                .addItem(new BottomNavigationItem(R.drawable.touzi_s, "垃圾箱").setInactiveIcon(getResources().getDrawable(R.drawable.touzi_n)))
                .addItem(new BottomNavigationItem(R.drawable.wode_s, "黑名单").setInactiveIcon(getResources().getDrawable(R.drawable.wode_n)))
                .addItem(new BottomNavigationItem(R.drawable.gengduo_s, "其他").setInactiveIcon(getResources().getDrawable(R.drawable.gengduo_n)))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        // TODO 设置 BadgeItem 默认隐藏 注意 这句代码在添加 BottomNavigationItem 之后
//        mHomeNumberBadgeItem.hide();
    }

    private void setDefaultFragment() {

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        mInboxFragment = InvestFragment.newInstance(LoanType.home);
        mInboxFragment = InboxFragment.newInstance("");
        transaction.add(R.id.tb, mInboxFragment);
        transaction.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;

        //开启事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);

        /**
         * fragment 用 add + show + hide 方式
         * 只有第一次切换会创建fragment，再次切换不创建
         *
         * fragment 用 replace 方式
         * 每次切换都会重新创建
         *
         */
        switch (position) {
            case 0:
                if (mInboxFragment == null) {
                    mInboxFragment = InboxFragment.newInstance("");
                    transaction.add(R.id.tb, mInboxFragment);
                } else {
                    mInboxFragment.onResume();
                    transaction.show(mInboxFragment);
                }
//                mHomeNumberBadgeItem.hide();
                break;
            case 1:
                if (mRubbishBoxFragment == null) {
                    mRubbishBoxFragment = RubbishBoxFragment.newInstance("");
                    transaction.add(R.id.tb, mRubbishBoxFragment);
                } else {
                    mRubbishBoxFragment.onResume();
                    transaction.show(mRubbishBoxFragment);
                }
                break;
            case 2:
                if (phoneFragment == null) {
                    phoneFragment = PhoneFragment.newInstance("phoneFragment");
                    transaction.add(R.id.tb, phoneFragment);
                } else {
                    transaction.show(phoneFragment);
                }
                break;
            case 3:
                if (mMeFragment == null) {
                    mMeFragment = MeFragment.newInstance("mMeFragment");
                    transaction.add(R.id.tb, mMeFragment);
                } else {
                    transaction.show(mMeFragment);
                }
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    /**
     * 隐藏当前fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mInboxFragment != null) {
            transaction.hide(mInboxFragment);
        }
        if (mRubbishBoxFragment != null) {
            transaction.hide(mRubbishBoxFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
        if (phoneFragment != null) {
            transaction.hide(phoneFragment);
        }
    }


    /**
     * fragment防止重叠
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mInboxFragment == null && fragment instanceof InboxFragment) {
            mInboxFragment = (InboxFragment) fragment;
        } else if (mRubbishBoxFragment == null && fragment instanceof RubbishBoxFragment) {
            mRubbishBoxFragment = (RubbishBoxFragment) fragment;
        } else if (phoneFragment == null && fragment instanceof PhoneFragment) {
            phoneFragment = (PhoneFragment) fragment;
        } else if (mMeFragment == null && fragment instanceof MeFragment) {
            mMeFragment = (MeFragment) fragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void onExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            SnackbarUtil.showLongSnackbar(mParent, "再按一次退出程序", SnackbarUtil.WHITE, SnackbarUtil.GREEN);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startNewActivity(TagMangerActivity.class, false);
        } else if (id == R.id.nav_gallery) {
            String content = "论文积分借款遛狗别墅特殊";
            String sender = "狗蛋er";
            String timedate = "我叫时间";
            int type = 1;
            for (int i = 0; i < queryDataL().size(); i++) {
                if (content.contains(queryDataL().get(i).getKeyword())) {
                    insertData(sender, content, timedate, 0);
                    type = 0;
                    break;
                }
            }
            if (type == 1) {
                String content2 = FilterUtil.format(content);//去掉标点符号
                String ltp = LtpCloud.split(content2);//分词
                String[] s = ltp.split(" ");//截取根据" "分的词语

                double bayes1 = 1;
                double bayes2 = 1;
                for (int i = 0; i < s.length; i++) {//循环，计算每个词语出现的次数，计算概率，加入到数据库
                    //条件概率
                    WhiteWordDao whiteWordDao = BaseApplication.getInstance().getDaoSession().getWhiteWordDao();
                    BlackWordDao blackWordDao = BaseApplication.getInstance().getDaoSession().getBlackWordDao();
                    List<WhiteWord> whiteWordsList = whiteWordDao.
                            queryBuilder()
                            .where(WhiteWordDao.Properties.Keyword.eq(s[i])).build().list();
                    List<BlackWord> blackWordsList = blackWordDao.
                            queryBuilder()
                            .where(BlackWordDao.Properties.Keyword.eq(s[i])).list();

                    double white, black;
                    if (whiteWordsList.size() > 0) {
                        white = (double) whiteWordsList.get(0).getNumber() / 200;
                    } else {
                        white = 1;
                    }
                    if (blackWordsList.size() > 0) {
                        black = (double) blackWordsList.get(0).getNumber() / 200;
                    } else {
                        black = 1;
                    }
                    String xxx = s[i];
                    double p = white / (white + black);//出现这个词时，该短信为垃圾短信的概率

                    //全概率
                    bayes1 = bayes1 * p;
                    bayes2 = bayes2 * (1 - p);
                }
                double p = bayes1 / (bayes1 + bayes2);//复合概率
                if (p > 0.8) {
                    insertData(sender, content, timedate, 0);
                } else {
                    insertData(sender, content, timedate, 1);
                }
//                        .show();
//                String timedate = DateUtils.timedate(String.valueOf(msgDate));
//                insertData(sender, content, timedate);


//                String smsToast = "New SMS received from : "
//                        + sender + "\n'"
//                        + content + "'" + xxxx + sdsdsd;
//                Toast.makeText(context, smsToast, Toast.LENGTH_LONG)
//                        .show();
//                Log.d(TAG, "message from: " + sender + ", message body: " + content
//                        + ", message date: " + msgDate);
                //自己的逻辑
            }
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
//            String xxxx = LtpCloud.split("乐视网公告称，孙宏斌因工作安排调整原因向公司申请辞去乐视网董事长职务，退出董事会，并不再在乐视网担任任何职务。公司董事会充分尊重孙宏斌的个人意愿，接受其辞职申请。孙宏斌原定任期至2018年10月13日");
//            Toast.makeText(this, xxxx, Toast.LENGTH_SHORT).show();
//            SnackbarUtil.showShortSnackbar(mParent, xxxx, SnackbarUtil.WHITE, SnackbarUtil.GREEN);
            logout(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            Long id = DBUtil.queryData();
            SharePreferenceUtil.saveInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID, id);
            SharePreferenceUtil.saveInfo(MainActivity.this, SharePreferenceUtil.BLACKNUM, "200");//黑名单
            SharePreferenceUtil.saveInfo(MainActivity.this, SharePreferenceUtil.WHITENUM, "200");//白名单
            //查
            WhiteWordDao smsDao = BaseApplication.getInstance().getDaoSession().getWhiteWordDao();
            List<WhiteWord> users = smsDao.loadAll();
            if (users.size() <= 0) {
                FilterUtil.getBlackList(0);
                FilterUtil.getBlackList(1);
            }
            return "fuck";
        }
    }

    private Long queryData() {
        UserDao mUserDao = BaseApplication.getInstance().getDaoSession().getUserDao();
        List<User> users = mUserDao.loadAll();
        Long id = null;
        String phone = SharePreferenceUtil.getInfo(BaseApplication.getContext(), SharePreferenceUtil.PHONE);
        for (int i = 0; i < users.size(); i++) {
            if (phone.equals(users.get(i).getPhone())) {
                id = users.get(i).getId();
                break;
            }
        }
        return id;
    }

//查

    private List<KeyWord> queryDataL() {
        KeyWordDao keyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        Long id = SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID);
        List<KeyWord> users = keyWordDao.
                queryBuilder()
                .where(KeyWordDao.Properties.Id.eq(String.valueOf(id))).list();
        return users;
    }
    //增

    private void insertData(String sender, String content, String time, int userfulType) {
        SMSDao smsDao = BaseApplication.getInstance().getDaoSession().getSMSDao();
        SMS insertData = new SMS(null,queryData(), sender, content, time, 0, userfulType);
        smsDao.insert(insertData);
    }
}
