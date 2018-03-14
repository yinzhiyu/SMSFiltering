package com.example.smsfiltering.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.smsfiltering.greendao.DaoMaster;
import com.example.smsfiltering.greendao.DaoSession;

/**
 * Created by si on 2018-3-13.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    //全局Context
    private static Context context;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        instance = this;
        super.onCreate();
        setDatabase();
    }

    /**
     * 设置greenDao
     */

    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "sms-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
    /*
   设置全局Context,便于不是Activity的类调用
    */
    public static Context getContext() {
        return context;
    }
}
