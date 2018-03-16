package com.example.smsfiltering.utils;

import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.UserDao;
import com.example.smsfiltering.table.User;

import java.util.List;


public class DBUtil {
    /**
     * 根据用户手机号，
     * @return
     */
    public static Long queryData() {
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
}
