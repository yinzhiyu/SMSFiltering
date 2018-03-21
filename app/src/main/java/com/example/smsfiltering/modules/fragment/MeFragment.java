package com.example.smsfiltering.modules.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.base.BaseFragment;
import com.example.smsfiltering.greendao.SMSDao;
import com.example.smsfiltering.table.SMS;
import com.example.smsfiltering.utils.DateUtils;
import com.example.smsfiltering.utils.SharePreferenceUtil;
import com.example.smsfiltering.utils.SnackbarUtil;
import com.example.smsfiltering.view.ContainsEmojiEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 其他
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.et_content)
    ContainsEmojiEditText mEtContent;
    @BindView(R.id.btn_inbox)
    Button mBtnInbox;
    @BindView(R.id.btn_rubbish)
    Button mBtnRubbish;
    @BindView(R.id.parent)
    ConstraintLayout mParent;

    public static MeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initView();
    }

    @OnClick({R.id.btn_inbox, R.id.btn_rubbish})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(mEtContent.getText().toString().trim())) {
            SnackbarUtil.showShortSnackbar(mParent, "添加测试短信不能为空...", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
            return;
        }
        switch (view.getId()) {
            case R.id.btn_inbox:
                insertSMS("测试短信", mEtContent.getText().toString().trim(), DateUtils.getCurrentTime(), 1);
                SnackbarUtil.showShortSnackbar(mParent, "添加成功！", SnackbarUtil.WHITE, SnackbarUtil.GREEN);
                break;
            case R.id.btn_rubbish:
                insertSMS("测试短信", mEtContent.getText().toString().trim(), DateUtils.getCurrentTime(), 0);
                SnackbarUtil.showShortSnackbar(mParent, "添加成功！", SnackbarUtil.WHITE, SnackbarUtil.GREEN);
                break;
        }
    }

    private void insertSMS(String sender, String content, String time, int userfulType) {
        SMSDao smsDao = BaseApplication.getInstance().getDaoSession().getSMSDao();
        SMS insertData = new SMS(null,SharePreferenceUtil.getInfoLong(getActivity(), SharePreferenceUtil.ID), sender, content, time, 0, userfulType);
        smsDao.insert(insertData);
    }

}
