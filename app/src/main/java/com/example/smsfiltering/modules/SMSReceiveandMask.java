package com.example.smsfiltering.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.smsfiltering.http.LtpCloud;
import com.example.smsfiltering.utils.DateUtils;


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
                String xxxx = LtpCloud.split(content);
                String sdsdsd = DateUtils.timedate(String.valueOf(msgDate));
                String smsToast = "New SMS received from : "
                        + sender + "\n'"
                        + content + "'" + xxxx + sdsdsd;
                Toast.makeText(context, smsToast, Toast.LENGTH_LONG)
                        .show();
                Log.d(TAG, "message from: " + sender + ", message body: " + content
                        + ", message date: " + msgDate);
                //自己的逻辑
            }
        }
        Log.v(TAG, ">>>>>>>onReceive end");
    }
}