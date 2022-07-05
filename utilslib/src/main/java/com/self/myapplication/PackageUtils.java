package com.self.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.util.List;

public class PackageUtils {
    /**
     * 判断应用是否存在
     */
    public static boolean isAppExist(Context context, String pkgName) {
        if (TextUtils.isEmpty(pkgName) || context == null) {
            return false;
        }
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            info = null;
        }
        //true为安装了，false为未安装
        return info != null;
    }

    /**
     * 判断某一个action是否支持
     */
    public static boolean isActionSupport(Context context,String action) {
        if (context == null || TextUtils.isEmpty(action)) return false;
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取应用名称
     */
    public static String getAppName(Context context,String packageName){
        if (context == null)return "";
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo != null){
            return packageManager.getApplicationLabel(applicationInfo).toString();
        }
        return "";

    }

    /**
     * 判断应用是否正在运行
     */
    public static boolean isPackageRunning(Context context, String packageName){
        boolean isAppRunning = false;
        if (context != null){
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(packageName) || info.baseActivity.getPackageName().equals(packageName)) {
                    isAppRunning = true;
                    break;
                }
            }
        }
        return isAppRunning;
    }
}
