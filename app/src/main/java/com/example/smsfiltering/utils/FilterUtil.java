package com.example.smsfiltering.utils;

import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.BlackWordDao;
import com.example.smsfiltering.greendao.WhiteWordDao;
import com.example.smsfiltering.table.BlackWord;
import com.example.smsfiltering.table.WhiteWord;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FilterUtil {
    /**
     * 去除字符串中的标点
     * @param s
     * @return
     */
    public static String format(String s) {
        String str = s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 黑名单：type为0
     * 白名单：type为1
     *
     * @param type
     */
    public static void getBlackList(int type) {
//        HashSet<String> hashSetBlackList = null;
        try {
            String fileName;
            //读取文件，我的文件存放在assets目录下
            if (type == 0) {
                fileName = "BlackList";
            } else {
                fileName = "WhiteList";
            }
            InputStream is = BaseApplication.getInstance().getAssets().open(fileName);
            if (is != null) {
                InputStreamReader inputreader = new InputStreamReader(is);
                BufferedReader buffreader = new BufferedReader(inputreader);
//                if (hashSetBlackList != null) {
//                    hashSetBlackList.clear();
//                } else {
//                    hashSetBlackList = new HashSet<String>();
//                }

                String line;
                int recordNum = 0;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    recordNum++;
                    //添加一条记录
//                    hashSetBlackList.add(line);
                    int index = line.indexOf(',');
                    String keyword = line.substring(0, index);
                    int number = Integer.parseInt(line.substring(index + 1));
                    if (type == 0) {
                        insertBData(keyword, number);
                    } else {
                        insertWData(keyword, number);
                    }

//                    if (recordNum % 10000 == 1) {//注意，过多的打印信息，会严重拖慢运行速度
//                    }
                }
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //增

    private static void insertWData(String keyWord, int number) {
        WhiteWordDao smsDao = BaseApplication.getInstance().getDaoSession().getWhiteWordDao();
        WhiteWord insertData = new WhiteWord(SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID), keyWord, number);
        smsDao.insert(insertData);
    }

    private static void insertBData(String keyWord, int number) {
        BlackWordDao blackWordDao = BaseApplication.getInstance().getDaoSession().getBlackWordDao();
        BlackWord insertData = new BlackWord(SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID), keyWord, number);
        blackWordDao.insert(insertData);
    }

    /**
     * 黑名单：type为0
     * 白名单：type为1
     *
     * @param type
     */
    public static void getBlackList1(int type) {
//        HashSet<String> hashSetBlackList = null;
        try {
            String fileName;
            //读取文件，我的文件存放在assets目录下
            if (type == 0) {
                fileName = "BlackList.txt";
            } else {
                fileName = "WhiteList.txt";
            }
            InputStream is = BaseApplication.getContext().getAssets().open(fileName);
            if (is != null) {
                InputStreamReader inputreader = new InputStreamReader(is);
                BufferedReader buffreader = new BufferedReader(inputreader);
//                if (hashSetBlackList != null) {
//                    hashSetBlackList.clear();
//                } else {
//                    hashSetBlackList = new HashSet<String>();
//                }

                String line;
                int recordNum = 0;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    recordNum++;
                    //添加一条记录
//                    hashSetBlackList.add(line);

//                    if (recordNum % 10000 == 1) {//注意，过多的打印信息，会严重拖慢运行速度
//                    }
                }
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断是否属于黑名单，（哈希表，超快，几乎不耗时）
    public static boolean isInBlackList(String findRecord) {
        HashSet<String> hashSetBlackList = null;
        boolean isBL = false;
        if (findRecord == null) {
            return isBL;
        }
        if (hashSetBlackList.contains(findRecord)) {
            isBL = true;
        } else {
            isBL = false;
        }
        return isBL;
    }
}
