package com.example.smsfiltering.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.smsfiltering.table.SMS;
import com.example.smsfiltering.table.User;

import com.example.smsfiltering.greendao.SMSDao;
import com.example.smsfiltering.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sMSDaoConfig;
    private final DaoConfig userDaoConfig;

    private final SMSDao sMSDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sMSDaoConfig = daoConfigMap.get(SMSDao.class).clone();
        sMSDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        sMSDao = new SMSDao(sMSDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(SMS.class, sMSDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        sMSDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public SMSDao getSMSDao() {
        return sMSDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
