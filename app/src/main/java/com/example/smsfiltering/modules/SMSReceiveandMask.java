package com.example.smsfiltering.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.BlackWordDao;
import com.example.smsfiltering.greendao.KeyWordDao;
import com.example.smsfiltering.greendao.PhoneDao;
import com.example.smsfiltering.greendao.SMSDao;
import com.example.smsfiltering.greendao.UserDao;
import com.example.smsfiltering.greendao.WhiteWordDao;
import com.example.smsfiltering.http.LtpCloud;
import com.example.smsfiltering.table.BlackWord;
import com.example.smsfiltering.table.KeyWord;
import com.example.smsfiltering.table.Phone;
import com.example.smsfiltering.table.SMS;
import com.example.smsfiltering.table.User;
import com.example.smsfiltering.table.WhiteWord;
import com.example.smsfiltering.utils.DateUtils;
import com.example.smsfiltering.utils.FilterUtil;
import com.example.smsfiltering.utils.SharePreferenceUtil;

import java.util.List;


public class SMSReceiveandMask extends BroadcastReceiver {
    private String TAG = "smsreceiveandmask";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, ">>>>>>>onReceive start");
        // 第一步、获取短信的内容和发件人
        StringBuilder body = new StringBuilder();// 短信内容
        StringBuilder number = new StringBuilder();// 短信发件人
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] _pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] message = new SmsMessage[_pdus.length];
            for (int i = 0; i < _pdus.length; i++) {
                message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);
            }

//            for (SmsMessage currentMessage : message) {
//                body.append(currentMessage.getDisplayMessageBody());
//                number.append(currentMessage.getDisplayOriginatingAddress());
//            }
//            String smsBody = body.toString();//短信内容
//            String smsNumber = number.toString();//短信发件人

//            if (smsNumber.contains("+86")) {
//                smsNumber = smsNumber.substring(3);
//            }
//            // 第二步:确认该短信内容是否满足过滤条件
//            boolean flags_filter = false;
//            if (smsNumber.equals("10086")) {// 屏蔽10086发来的短信
//                flags_filter = true;
//                Log.v(TAG, "sms_number.equals(10086)");
//            }
//            // 第三步:取消
//            if (flags_filter) {
//                this.abortBroadcast();
//            }
            if (message.length > 0) {
                String content = message[0].getMessageBody();
                String sender = message[0].getOriginatingAddress();
                long msgDate = message[0].getTimestampMillis();
                String timedate = DateUtils.timedate(String.valueOf(msgDate));
                int type = 1;
                for (int i = 0; i < queryDataP().size(); i++) {
                    if (content.contains(queryDataP().get(i).getPhone())) {
                        insertData(sender, content, timedate, 0);
                        type = 0;
                        break;
                    }
                }
                if (type == 1) {
                    for (int i = 0; i < queryDataL().size(); i++) {
                        if (content.contains(queryDataL().get(i).getKeyword())) {
                            insertData(sender, content, timedate, 0);
                            type = 0;
                            break;
                        }
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
            }
        }
    }

//查

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

//查（黑名单关键字）

    private List<KeyWord> queryDataL() {
        KeyWordDao keyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        Long id = SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID);
        List<KeyWord> users = keyWordDao.
                queryBuilder()
                .where(SMSDao.Properties.Id.eq(String.valueOf(id))).build().list();
        return users;
    }

    private List<Phone> queryDataP() {
        PhoneDao phoneDao = BaseApplication.getInstance().getDaoSession().getPhoneDao();
        Long id = SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID);
        List<Phone> phones = phoneDao.
                queryBuilder()
                .where(PhoneDao.Properties.Id.eq(String.valueOf(id))).build().list();
        return phones;
    }
    //增

    private void insertData(String sender, String content, String time, int userfulType) {
        SMSDao smsDao = BaseApplication.getInstance().getDaoSession().getSMSDao();
        SMS insertData = new SMS(null, queryData(), sender, content, time, 0, userfulType);
        smsDao.insert(insertData);
    }


}