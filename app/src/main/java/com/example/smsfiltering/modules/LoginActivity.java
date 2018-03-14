package com.example.smsfiltering.modules;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseActivity;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.UserDao;
import com.example.smsfiltering.table.User;
import com.example.smsfiltering.utils.SharePreferenceUtil;
import com.example.smsfiltering.utils.SnackbarUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.iv_img_head)
    ImageView mIvImgHead;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.iv_btn_delete)
    ImageView mIvBtnDelete;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.iv_btn_delete_pas)
    ImageView mIvBtnDeletePas;
    @BindView(R.id.iv_btn_nosee)
    ImageView mIvBtnNosee;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_login_password)
    TextView mTvLoginPassword;
    @BindView(R.id.parent)
    AutoLinearLayout mParent;
    //数据库相关
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mUserDao = BaseApplication.getInstance().getDaoSession().getUserDao();
        initToolBar();
    }


    /**
     * 设置ToolBar属性
     */
    private void initToolBar() {
        mToolbarBack.setVisibility(View.GONE);
        mToolbar.setTitle("");
        mToolbarTitle.setText("登录");
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

    @OnClick({R.id.iv_btn_delete, R.id.iv_btn_delete_pas, R.id.btn_login, R.id.tv_login_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_btn_delete:
                break;
            case R.id.iv_btn_delete_pas:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_login_password:
//                SnackbarUtil.showShortSnackbar(mParent, "暂不开发....", SnackbarUtil.WHITE, SnackbarUtil.GREEN);
                startNewActivity(RegisteredActivity.class, true);
                break;
        }
    }

    private void login() {
        String phone = mEtPhone.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            SnackbarUtil.showLongSnackbar(mParent, "请输入手机号", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            SnackbarUtil.showLongSnackbar(mParent, "请输入密码", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
            return;
        }
        queryData();
    }
    //查

    private void queryData() {
        List<User> users = mUserDao.loadAll();
        String phone = mEtPhone.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String db_phone = "";
        String db_pwd = "";
        StringBuilder sb = new StringBuilder();
        if (users.size() > 1) {
            for (int i = 0; i < users.size(); i++) {
                db_phone = users.get(i).getPhone();
                db_pwd = users.get(i).getPassword();
                sb.append(db_phone);
                if (db_phone.equals(phone)) {
                    if (db_pwd.equals(password)) {
                        SharePreferenceUtil.saveInfo2(LoginActivity.this, SharePreferenceUtil.ALIAS, true);
                        startNewActivity(MainActivity.class, true);
                        break;
                    } else {
                        SnackbarUtil.showLongSnackbar(mParent, "密码不正确", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
                        break;
                    }
                } else {
                    SnackbarUtil.showLongSnackbar(mParent, "账号未注册", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
                }

            }
        } else {
            SnackbarUtil.showLongSnackbar(mParent, "账号未注册", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
        }


    }

}
