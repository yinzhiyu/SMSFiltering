package com.example.smsfiltering.http;


public class LtpCloud {

    public static String split(String text) {
        HttpConnectLtp htp = new HttpConnectLtp(text, "ws");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htp.result;
    }

    public String NER(String text) {
        HttpConnectLtp htp = new HttpConnectLtp(text, "ner");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htp.result;
    }
}