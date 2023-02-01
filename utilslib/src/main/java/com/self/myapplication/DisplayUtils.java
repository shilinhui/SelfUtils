package com.self.myapplication;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Activity;
//import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public class DisplayUtils {

    public static void setLightNavigationBar (Activity activity, boolean light) {
        int vis = activity.getWindow().getDecorView().getSystemUiVisibility();
        boolean isNight = ((activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES);
        if (light && !isNight) {
            //黑色
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        } else {
            //白色
            vis &= ~ View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(vis);
    }

    public static void setNavbarColor(Activity activity, int colorId){
        if (null == activity) {
            return;
        }
        Window window = activity.getWindow();
        if (null == window) {
            return;
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(activity.getResources().getColor(colorId));
    }

    public static boolean isNightMode(@NonNull Context context){
        return ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES);
    }

    public static void setDarkStatusIcon(Activity act, boolean bDark) {
        View decorView = act.getWindow().getDecorView();
        if (decorView != null) {
            int vis = decorView.getSystemUiVisibility();
            if (bDark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //set black
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //set white
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    /**
     * 获取屏幕真实宽度
     */
    public static int getScreenRealWidth(Context context){
        WindowManager wm  = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Point outSize = new Point();
        wm.getDefaultDisplay().getRealSize(outSize);
        return outSize.x;
    }

    /**
     * 获取屏幕真实高度
     */
    public static int getScreenReaHeight(Context context){
        WindowManager wm  = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Point outSize = new Point();
        wm.getDefaultDisplay().getRealSize(outSize);
        return outSize.y;
    }

    /**
     * 获取当前窗口宽度
     */
    public static int getScreenShowWidth(Context context){
        WindowManager wm  = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    /**
     * 获取当前窗口高度
     */
    public static int getScreenShowHeight(Context context){
        WindowManager wm  = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 是否处于分屏模式，分屏和浮窗都属于activity.isInMultiWindowMode()
     */
    /*public static boolean isSplitScreenMode(Context context) {
        if (context != null) {
            return WindowConfiguration.isSplitScreenWindowingMode(context.getResources()
                    .getConfiguration().windowConfiguration.getWindowingMode());
        }
        return false;
    }**/
}
