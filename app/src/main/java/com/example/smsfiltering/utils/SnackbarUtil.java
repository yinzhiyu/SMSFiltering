package com.example.smsfiltering.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smsfiltering.R;

import java.lang.ref.WeakReference;

/**
 * Created by yinzhiyu on 2018-3-14.
 */

public class SnackbarUtil {

    public static final int AMBER       = 0xFFFFC107;
    public static final int BLACK       = 0xFF000000;
    public static final int BLUE        = 0xFF5677FC;
    public static final int BLUE_GERY   = 0xFF607D8D;
    public static final int BROWN       = 0xFF795548;
    public static final int CYAN        = 0xFF00BCD4;
    public static final int DEEP_ORANGE = 0xFFFF5722;
    public static final int DEEP_PURPLE = 0xFF673AB7;
    public static final int GRAY        = 0xFF9E9E9E;
    public static final int GREEN       = 0xFF259B24;
    public static final int INDIGO      = 0xFF3F51B5;
    public static final int LIGHT_GREEN = 0xFF8BC34A;
    public static final int LIME        = 0xFFCDDC39;
    public static final int LIGHT_BLUE  = 0xFF03A9F4;
    public static final int ORANGE      = 0xFFFF9800;
    public static final int PINK        = 0xFFE91E63;
    public static final int PURPLE      = 0xFF9C27B0;
    public static final int RED         = 0xFFE51C32;
    public static final int TEAL        = 0xFF009688;
    public static final int YELLOW      = 0xFFFFEB3B;
    public static final int WHITE       = 0xFFFFFFFF;

    private SnackbarUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static WeakReference<Snackbar> snackbarWeakReference;

    /**
     * 显示短时snackbar
     *
     * @param parent    父视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showShortSnackbar(View parent, CharSequence text, int textColor, int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示短时snackbar
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showShortSnackbar(View parent, CharSequence text, int textColor, int bgColor,
                                         CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_SHORT, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent    视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showLongSnackbar(View parent, CharSequence text, int textColor, int bgColor) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示长时snackbar
     *
     * @param parent          视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showLongSnackbar(View parent, CharSequence text, int textColor, int bgColor,
                                        CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, Snackbar.LENGTH_LONG, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent    父视图(CoordinatorLayout或者DecorView)
     * @param text      文本
     * @param duration  自定义时长
     * @param textColor 文本颜色
     * @param bgColor   背景色
     */
    public static void showIndefiniteSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor) {
        showSnackbar(parent, text, duration, textColor, bgColor, null, -1, null);
    }

    /**
     * 显示自定义时长snackbar
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param duration        自定义时长
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    public static void showIndefiniteSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor,
                                              CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        showSnackbar(parent, text, duration, textColor, bgColor,
                actionText, actionTextColor, listener);
    }

    /**
     * 设置snackbar文字和背景颜色
     *
     * @param parent          父视图(CoordinatorLayout或者DecorView)
     * @param text            文本
     * @param duration        显示时长
     * @param textColor       文本颜色
     * @param bgColor         背景色
     * @param actionText      事件文本
     * @param actionTextColor 事件文本颜色
     * @param listener        监听器
     */
    private static void showSnackbar(View parent, CharSequence text, int duration, int textColor, int bgColor,
                                     CharSequence actionText, int actionTextColor, View.OnClickListener listener) {
        switch (duration) {
            default:
            case Snackbar.LENGTH_SHORT:
            case Snackbar.LENGTH_LONG:
                snackbarWeakReference = new WeakReference<>(Snackbar.make(parent, text, duration));
                break;
            case Snackbar.LENGTH_INDEFINITE:
                snackbarWeakReference = new WeakReference<>(Snackbar.make(parent, text, Snackbar.LENGTH_INDEFINITE).setDuration(duration));
        }
        View view = snackbarWeakReference.get().getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(textColor);
        view.setBackgroundColor(bgColor);
        if (actionText != null && actionText.length() > 0 && listener != null) {
            snackbarWeakReference.get().setActionTextColor(actionTextColor);
            snackbarWeakReference.get().setAction(actionText, listener);
        }
        snackbarWeakReference.get().show();
    }

    /**
     * 为snackbar添加布局
     * <p>在show...Snackbar之后调用</p>
     *
     * @param layoutId 布局文件
     * @param index    位置(the position at which to add the child or -1 to add last)
     */
    public static void addView(int layoutId, int index) {
        Snackbar snackbar = snackbarWeakReference.get();
        if (snackbar != null) {
            View view = snackbar.getView();
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layoutId, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            layout.addView(child, index, params);
        }
    }

    /**
     * 取消snackbar显示
     */
    public static void dismissSnackbar() {
        if (snackbarWeakReference != null && snackbarWeakReference.get() != null) {
            snackbarWeakReference.get().dismiss();
            snackbarWeakReference = null;
        }
    }
}
