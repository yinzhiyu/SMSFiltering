package com.example.smsfiltering.modules;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseActivity;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.UserDao;
import com.example.smsfiltering.table.User;
import com.example.smsfiltering.utils.DBUtil;
import com.example.smsfiltering.utils.SharePreferenceUtil;
import com.example.smsfiltering.utils.SnackbarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.iv_btn_delete)
    ImageView mIvBtnDelete;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password_sure)
    EditText mEtPasswordSure;
    @BindView(R.id.btn_registered)
    Button mBtnRegistered;
    @BindView(R.id.tv_to_login)
    TextView mTvToLogin;
    @BindView(R.id.parent)
    FrameLayout mParent;

    //数据库相关
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
        mUserDao = BaseApplication.getInstance().getDaoSession().getUserDao();
        initToolBar();
    }

    @OnClick({R.id.btn_registered, R.id.tv_to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_registered:
                registered();
                break;
            case R.id.tv_to_login:
                startNewActivity(LoginActivity.class, true);
                break;
        }
    }

    /**
     * 注冊
     */
    private void registered() {


        String password = mEtPassword.getText().toString().trim();
        String passwordSure = mEtPasswordSure.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            SnackbarUtil.showLongSnackbar(mParent, "手机号不能为空。", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
            return;
        }
        if (!password.equals(passwordSure)) {
            SnackbarUtil.showLongSnackbar(mParent, "两次密码输入不一致...", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
            return;
        }
        String phone = mEtPhone.getText().toString().trim();
        insertData(phone, password);
        Long id = DBUtil.queryData();
        SharePreferenceUtil.saveInfo2(BaseApplication.getContext(), SharePreferenceUtil.ALIAS, true);
        SharePreferenceUtil.saveInfo(BaseApplication.getContext(), SharePreferenceUtil.PHONE, phone);
        SharePreferenceUtil.saveInfo(BaseApplication.getContext(), SharePreferenceUtil.PWD, password);
        SharePreferenceUtil.saveInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID, id);
        startNewActivity(MainActivity.class, true);
    }

    /**
     * 插入一条数据
     *
     * @param phone
     * @param pwd
     */
    private void insertData(String phone, String pwd) {
        //往数据库添加数据
        User insertData = new User(null, phone, pwd);
        mUserDao.insert(insertData);
    }

    /**
     * 设置ToolBar属性
     */
    private void initToolBar() {
        mToolbarBack.setVisibility(View.GONE);
        mToolbar.setTitle("");
        mToolbarTitle.setText("注册");
        mToolbarTitle.setTextColor(getResources().getColor(R.color.color_92a0a9));
//        toolbarTitle.getPaint().setFakeBoldText(true);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
