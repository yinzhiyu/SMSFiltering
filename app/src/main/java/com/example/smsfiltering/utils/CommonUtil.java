package com.example.smsfiltering.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.smsfiltering.base.BaseApplication;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CommonUtil {
    private static Context mContext = BaseApplication.getInstance();
    private static boolean isJsonFormat = true;/// BuildConfig.IS_FORMAT_JSON_LOG;

    /**
     * 是否隐藏
     */
    public static void isHideView(boolean isHide, View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }

    /**
     * 是否隐藏
     */
    public static void firstHideView(View view1, View view2) {
        firstHideView(true, view1, view2);
    }

    /**
     * 是否隐藏
     */
    public static void firstHideView(boolean isFirstHide, View view1, View view2) {
        if (view1 == null || view2 == null) {
            return;
        }

        if (isFirstHide) {
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
        } else {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示
     */
    public static void isShowView(boolean isShow, View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(!isShow ? View.GONE : View.VISIBLE);
    }

    /**
     * 隐藏视图
     */
    public static void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 显示视图
     */
    public static void showViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void executeOutAnim(Activity activity) {
        //activity.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }

    public static void executeInAnim(Activity activity) {
        //activity.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    public static boolean isEmptyString(String str) {
        //		if (null == str || "".equals(str)) {
        //			return true;
        //		}
        //		return false;
        return null == str || "".equals(str);
    }


    /**
     * 隐藏输入法
     */
    public static void hideIME(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏输入法
     */
    public static void showIME(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 设置textview的字体颜色
     *
     * @param resourceId
     * @param views
     */
    public static void setTextColor(int resourceId, TextView... views) {
        for (TextView tv : views) {
            String str = tv.getText().toString().trim();
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            tv.setTextColor(resourceId);
        }
    }

    public static int getTextColor(int resourceId) {
        return mContext.getResources().getColor(resourceId);
    }

    /**
     * 将int转化为long类型
     */
    public static Long transIntToLong(Object obj) {
        return ((Integer) obj).longValue();
    }

    /**
     * 改变字体
     */
    public static void changeTypeface(TextView view) {
        view.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/swatch.TTF"));
    }

    public static void setDiffColorText(TextView view, String text, int color, int start, int end) {
        SpannableString span = new SpannableString(text);
        span.setSpan(new ForegroundColorSpan(color), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(span);
    }

    /**
     * 为TextView设置大小不一的字体
     */
    public static void setDiffSizeText(String text, TextView view, Map<Integer, Integer[]> data,//
                                       Integer... params) {
        Spannable span = new SpannableString(text); // "大字小字
        //		span.setSpan(new TextAppearanceSpan(family, style, size, color, linkColor), start, end, flags);
        for (Integer res : params) {
            Integer[] offset = data.get(res);
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(res); // 大小
            span.setSpan(sizeSpan, offset[0], offset[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        view.setText(span);
    }

    /**
     * 格式化货币
     */
    public static String formatCurrency(BigDecimal price) {
        return formatNum(price).toString();
    }

    public static BigDecimal multiply100(String data) {
        return formatNum(new BigDecimal(data).multiply(new BigDecimal(100)));
    }

    public static BigDecimal divide100(String data) {
        return formatNum(new BigDecimal(data).divide(new BigDecimal(100)));
    }

    public static BigDecimal multiply100(BigDecimal data) {
        return formatNum(data.multiply(new BigDecimal(100)));

    }

    public static BigDecimal divide100(BigDecimal data) {
        return formatNum(data.divide(new BigDecimal(100)));
    }

    /**
     * 格式化数量
     */
    public static BigDecimal formatNum(BigDecimal num) {
        return num.setScale(1, BigDecimal.ROUND_DOWN);
    }

    /**
     * 带星期
     */
    public static String formatFullDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    /**
     * 带星期
     */
    public static String formatShortDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

    /**
     * 年月日时分秒
     */
    public static String formatLongDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 月
     */
    public static String formatYueDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(date);
    }

    /**
     * 月日时分
     */
    public static String formatYueRiShiFenDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * 年月日
     */
    public static String formatNianYueRiDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatChineseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }

    /**
     * 月日
     */
    public static String formatYueRiDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(date);
    }

    /**
     * 获取星期的全称（例如：星期一）
     */
    public static String getFullWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 获取星期的简称（例如：一）
     */
    public static String getShortWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekDay = c.get(Calendar.DAY_OF_WEEK); // 范围 1~7
        return getShortWeekStr(weekDay + "");
    }

    public static String getShortWeekStr(String str) {
        if ("1".equals(str)) {
            str = "日";
        } else if ("2".equals(str)) {
            str = "一";
        } else if ("3".equals(str)) {
            str = "二";
        } else if ("4".equals(str)) {
            str = "三";
        } else if ("5".equals(str)) {
            str = "四";
        } else if ("6".equals(str)) {
            str = "五";
        } else if ("7".equals(str)) {
            str = "六";
        }
        return str;
    }

    public static String splitWidthSeperatorByIndex(String s, String sep, int index) {
        StringBuilder sb = new StringBuilder(s);
        int count = 0;
        for (int i = 0; i < sb.length(); i++) {
            //			if (sb.charAt(i) == '\n') {
            //				count = 0;
            //			}
            if (count == index) {
                sb.insert(i, sep);
                count = 0;
            } else {
                count++;
            }
        }
        return sb.toString();
    }

    /**
     * 将关键字高亮
     *
     * @param str
     * @param keys
     * @return
     */
    public static SpannableString setSpannable(String str, int color, String... keys) {
        SpannableString spannable = new SpannableString(str);
        try {
            if (keys.length == 0) {
                return spannable;
            }
            CharacterStyle span;
            for (String key : keys) {
                if ("".equals(key) || " ".equals(key)) {
                    continue;
                }
                String[] arrs = str.split(key);
                int length = key.length();
                int start = 0;
                for (int i = 0; i < arrs.length - 1; i++) {
                    String arr = arrs[i];
                    int size = arr.length();
                    span = new ForegroundColorSpan(getColor(color));
                    start += size;
                    spannable.setSpan(span, start, start + length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start += length;
                }
                if (str.endsWith(key)) {
                    span = new ForegroundColorSpan(getColor(color));
                    spannable.setSpan(span, str.length() - length, str.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spannable;
    }

    public static int getColor(int resid) {
        return mContext.getResources().getColor(resid);
    }


    /**
     * 改变TextColor按钮的样式
     */
    public static void changeTextColorStyle(Button button, int bgcolor) {
        try {
            XmlResourceParser parser = button.getContext().getResources().getXml(bgcolor);
            ColorStateList colors = ColorStateList.createFromXml(button.getContext().getResources(), parser);
            button.setTextColor(colors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏真是手机号 替换成 ‘*’ 号
     *
     * @param phone
     * @return
     */
    public static String hideBindPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return phone;
        }
        char[] charName = phone.toCharArray();
        try {
            for (int i = 0; i < charName.length; i++) {
                if (i > 2 && i < charName.length - 4) {
                    charName[i] = '*';
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(charName);
    }

    /**
     * 隐藏数字并替换成*
     *
     * @param number
     * @return
     */
    public static String hideNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return number;
        }
        char[] charName = number.toCharArray();
        try {
            for (int i = 0; i < charName.length; i++) {
                charName[i] = '*';
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(charName);
    }


    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断是否是中文
     */
    public static boolean isChinese(String str) {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "0";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * 比较版本号，当currVer > serverVer false
     *
     * @param currVer
     * @param serverVer
     * @return
     */
    public static boolean isUpdate(String currVer, String serverVer)
            throws Exception {
        String curV[] = currVer.split("\\.", 3);
        String serV[] = serverVer.split("\\.", 3);
        boolean isUpdate = false;
        if (Integer.valueOf(curV[0]) < Integer.valueOf(serV[0])) {
            isUpdate = true;
        } else if (Integer.valueOf(curV[0]) > Integer.valueOf(serV[0])) {
            isUpdate = false;
        } else if (Integer.valueOf(curV[0]) == Integer.valueOf(serV[0])) {
            if (Integer.valueOf(curV[1]) < Integer.valueOf(serV[1])) {
                isUpdate = true;
            } else if (Integer.valueOf(curV[1]) > Integer.valueOf(serV[1])) {
                isUpdate = false;
            } else if (Integer.valueOf(curV[1]) == Integer.valueOf(serV[1])) {
                if (Integer.valueOf(curV[2]) < Integer.valueOf(serV[2])) {
                    isUpdate = true;
                } else if (Integer.valueOf(curV[2]) > Integer.valueOf(serV[2])) {
                    isUpdate = false;
                } else if (Integer.valueOf(curV[2]) == Integer.valueOf(serV[2])) {
                    isUpdate = false;
                }
            }
        }

        return isUpdate;
    }
}
