package com.example.smsfiltering.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectLtp extends Thread{

    public  String  api_key;
    public  String  pattern;
    public  String  format;
    public  String  text = "";
    public  String  result="";

    public HttpConnectLtp(String txt,String pat){
        api_key = "T1J7O2W0H96nBI9XEWuKMm3gyjXZKdjw9QtYyDiO";//api_key,申请账号后生成，免费申请。
        pattern = pat;//ws表示只分词，除此还有pos词性标注、ner命名实体识别、dp依存句法分词、srl语义角色标注、all全部
        format = "plain";//指定结果格式类型，plain表示简洁文本格式
        text = txt;
    }

    @Override
    public void run(){
        URL url;
        try {
            url = new URL("https://api.ltp-cloud.com/analysis/?"
                    + "api_key=" + api_key + "&"
                    + "text=" + text + "&"
                    + "format=" + format + "&"
                    + "pattern=" + pattern);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(true);  //you still need to handle redirect manully.
            HttpURLConnection.setFollowRedirects(true);
            conn.connect();

            BufferedReader innet = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder sb=new StringBuilder();
            String line=null;
            while((line=innet.readLine())!=null){
                sb.append(line);
            }
            result = sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}