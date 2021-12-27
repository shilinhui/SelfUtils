package com.self.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    private final static String TAG = "PermissionUtils";
    private final static int REQUEST_CODE = 100;
    private static IPermissionsResult mPermissionsResult;

    public interface IPermissionsResult {
        void onResult(boolean isPass);
    }


    public static void startApplicationInformation(Context context){
        if (context == null){
            Log.d(TAG,"startApplicationInformation context is null");
            return;
        }
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void startLocationSettings(Context context){
        if (context == null){
            Log.d(TAG,"startLocationSettings context is null");
            return;
        }
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void chekPermissions(Activity context, String[] permissions, @NonNull IPermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;

        if (Build.VERSION.SDK_INT < 23) {
            //6.0才用动态权限
            permissionsResult.onResult(true);
            return;
        }

        //创建一个permissionList，逐个判断哪些权限未授予，未授予的权限存储到permissionList中
        List<String> permissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                //添加还未授予的权限
                permissionList.add(permissions[i]);
            }
        }

        //申请权限
        if (permissionList.size() > 0) {
            //有权限没有通过，需要申请
            ActivityCompat.requestPermissions(context, permissions, REQUEST_CODE);
        } else {
            //权限都已经通过
            permissionsResult.onResult(true);
        }
    }

    public static void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (REQUEST_CODE == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                //有权限未通过
                if (mPermissionsResult != null){
                    mPermissionsResult.onResult(false);
                }
            } else {
                //全部权限通过
                if (mPermissionsResult != null){
                    mPermissionsResult.onResult(true);
                }
            }
        }

    }

}
