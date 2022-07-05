package com.self.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetWorkUtils {
    public static final String TAG = "NetworkUtil";

    /**
     * 是否有网络连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     *是否是WiFi连接
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.isConnected()) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     *是否是移动连接
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null && mMobileNetworkInfo.isConnected()) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取连接类型
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 网络是否可用
     */
    public static boolean isNetWorkAvailable(Context context) {
        boolean isAvailable = false;
        if (context == null) {
            return isAvailable;
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return isAvailable;
            }
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos == null) {
                return isAvailable;
            }
            for (int i = 0; i < infos.length && infos[i] != null; i++) {
                if (infos[i].isConnected() && infos[i].isAvailable()) {
                    isAvailable = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAvailable;
    }

    /**
     * 是否是热点
     */
    public static boolean isNetWorkHotSpot(Context context){
        boolean ret = false;
        if(isWifiConnected(context)){
            ret = isWifiHotspot(context);
        }
        return ret;
    }

    public static boolean isWifiHotspot(Context context) {
        final String HOTSPOT_IP = "192.168.43.";
        final String HOTSPOT_IP2 = "172.20.10.";
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            return false;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo == null) {
            return false;
        }
        String ip = android.text.format.Formatter.formatIpAddress(wifiInfo.getIpAddress());
        if(ip != null) {
            if(ip.startsWith(HOTSPOT_IP) || ip.startsWith(HOTSPOT_IP2)) {
                return true;
            }
        }

        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if(dhcpInfo == null) {
            return false;
        }
        String dhcp_ip = android.text.format.Formatter.formatIpAddress(dhcpInfo.ipAddress);
        if(dhcp_ip != null) {
            if(dhcp_ip.startsWith(HOTSPOT_IP) || dhcp_ip.startsWith(HOTSPOT_IP2)) {
                return true;
            }
        }

        String dhcp_gateway = android.text.format.Formatter.formatIpAddress(dhcpInfo.gateway);
        if(dhcp_gateway != null) {
            if(dhcp_gateway.startsWith(HOTSPOT_IP) || dhcp_gateway.startsWith(HOTSPOT_IP2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 需要登录的WiFi是否登录
     */
    public static boolean isPortalWiFiConnected(Context context) {
        if (context == null){
            return false;
        }
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities nc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
        }
        return nc != null && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL);
    }

    /**
     * WiFi是否验证
     */
    public static boolean isValidatedWiFiConnected(Context context) {
        if (context == null){
            return false;
        }
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities nc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
        }
        return nc != null && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

}
