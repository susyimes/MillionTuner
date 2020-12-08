package com.george.pitch_estimator.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil {

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float px2dip2(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        float dp = (pxValue / scale) + 0.5f;
//        if (dp > 0 && dp < 10)
//            return dp + 0.5f;
//        else if (dp >= 10 && dp < 20)
//            return (dp + 1.5f);
//        else if (dp >= 20 && dp < 30)
//            return (dp + 3f);
//        else if (dp >= 30)
//            return (dp + 4f);
//        else
        return dp * 1.078f;
    }

    public static float getScreenDensity(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue

     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue

     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static float px2sp2(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return statusBarHeight;
    }

    /**
     * get the width of screen **
     */
    public static int getScreenW(Context ctx) {
        int w = 0;
        if (Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(p);
            w = p.x;
        } else {
            w = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getWidth();
        }
        return w;
    }

    /**
     * get the height of screen **
     */
    public static int getScreenH(Context ctx) {
        int h = 0;
        if (Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(p);
            h = p.y;
        } else {
            h = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getHeight();
        }
        return h;
    }


}

