package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final int HTTP_307 = 307;
    private static final String TAG = "SA.NetworkUtils";
    private static SABroadcastReceiver mReceiver;
    private static SANetworkCallbackImpl networkCallback;
    private static String networkType;

    public static String networkType(Context context) {
        try {
            if (!TextUtils.isEmpty(networkType) && !"NULL".equals(networkType)) {
                return networkType;
            }
            if (!SensorsDataUtils.checkHasPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
                networkType = "NULL";
                return "NULL";
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                if (!isNetworkAvailable(connectivityManager)) {
                    networkType = "NULL";
                    return "NULL";
                } else if (isWiFiNetwork(connectivityManager)) {
                    networkType = LDNetUtil.NETWORKTYPE_WIFI;
                    return LDNetUtil.NETWORKTYPE_WIFI;
                }
            }
            String mobileNetworkType = mobileNetworkType(context, (TelephonyManager) context.getSystemService("phone"), connectivityManager);
            networkType = mobileNetworkType;
            return mobileNetworkType;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            networkType = "NULL";
            return "NULL";
        }
    }

    @SuppressLint({"WrongConstant"})
    public static boolean isNetworkAvailable(Context context) {
        if (!SensorsDataUtils.checkHasPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return false;
        }
        try {
            return isNetworkAvailable((ConnectivityManager) context.getSystemService("connectivity"));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static boolean isShouldFlush(String networkType2, int flushNetworkPolicy) {
        return (toNetworkType(networkType2) & flushNetworkPolicy) != 0;
    }

    private static int toNetworkType(String networkType2) {
        if ("NULL".equals(networkType2)) {
            return 255;
        }
        if (LDNetUtil.NETWORKTYPE_WIFI.equals(networkType2)) {
            return 8;
        }
        if ("2G".equals(networkType2)) {
            return 1;
        }
        if ("3G".equals(networkType2)) {
            return 2;
        }
        if ("4G".equals(networkType2)) {
            return 4;
        }
        if ("5G".equals(networkType2)) {
            return 16;
        }
        return 255;
    }

    @SuppressLint({"WrongConstant"})
    public static boolean isNetworkValid(NetworkCapabilities capabilities) {
        if (capabilities == null || Build.VERSION.SDK_INT < 21) {
            return false;
        }
        if (capabilities.hasTransport(1) || capabilities.hasTransport(0) || capabilities.hasTransport(3) || capabilities.hasTransport(7) || capabilities.hasTransport(4) || capabilities.hasCapability(16)) {
            return true;
        }
        return false;
    }

    public static boolean needRedirects(int responseCode) {
        return responseCode == 301 || responseCode == 302 || responseCode == 307;
    }

    public static String getLocation(HttpURLConnection connection, String path) {
        if (connection == null || TextUtils.isEmpty(path)) {
            return null;
        }
        String location = connection.getHeaderField("Location");
        if (TextUtils.isEmpty(location)) {
            location = connection.getHeaderField(FirebaseAnalytics.Param.LOCATION);
        }
        if (TextUtils.isEmpty(location)) {
            return null;
        }
        if (location.startsWith(NetworkManager.MOCK_SCHEME_HTTP) || location.startsWith(NetworkManager.MOCK_SCHEME_HTTPS)) {
            return location;
        }
        URL originUrl = new URL(path);
        return originUrl.getProtocol() + "://" + originUrl.getHost() + location;
    }

    public static void registerNetworkListener(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 21) {
                if (mReceiver == null) {
                    mReceiver = new SABroadcastReceiver();
                }
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                context.registerReceiver(mReceiver, intentFilter);
                SALog.i(TAG, "Register BroadcastReceiver");
                return;
            }
            if (networkCallback == null) {
                networkCallback = new SANetworkCallbackImpl();
            }
            NetworkRequest request = new NetworkRequest.Builder().build();
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                connectivityManager.registerNetworkCallback(request, networkCallback);
                SALog.i(TAG, "Register ConnectivityManager");
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void unregisterNetworkListener(Context context) {
        ConnectivityManager connectivityManager;
        try {
            if (Build.VERSION.SDK_INT < 21) {
                SABroadcastReceiver sABroadcastReceiver = mReceiver;
                if (sABroadcastReceiver != null) {
                    context.unregisterReceiver(sABroadcastReceiver);
                    SALog.i(TAG, "unregisterReceiver BroadcastReceiver");
                }
            } else if (networkCallback != null && (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) != null) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
                SALog.i(TAG, "unregister ConnectivityManager");
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void cleanNetworkTypeCache() {
        networkType = null;
    }

    private static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        NetworkCapabilities capabilities;
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                Network network = connectivityManager.getActiveNetwork();
                if (!(network == null || (capabilities = connectivityManager.getNetworkCapabilities(network)) == null)) {
                    return isNetworkValid(capabilities);
                }
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected()) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private static boolean isWiFiNetwork(ConnectivityManager connectivityManager) {
        NetworkCapabilities capabilities;
        if (Build.VERSION.SDK_INT >= 23) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null || (capabilities = connectivityManager.getNetworkCapabilities(network)) == null) {
                return false;
            }
            return capabilities.hasTransport(1);
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    @SuppressLint({"MissingPermission"})
    private static String mobileNetworkType(Context context, TelephonyManager telephonyManager, ConnectivityManager connectivityManager) {
        NetworkInfo networkInfo;
        int networkType2 = 0;
        if (telephonyManager != null) {
            if (Build.VERSION.SDK_INT < 30 || (!SensorsDataUtils.checkHasPermission(context, "android.permission.READ_PHONE_STATE") && !telephonyManager.hasCarrierPrivileges())) {
                try {
                    networkType2 = telephonyManager.getNetworkType();
                } catch (Exception ex) {
                    SALog.printStackTrace(ex);
                }
            } else {
                networkType2 = telephonyManager.getDataNetworkType();
            }
        }
        if (networkType2 == 0) {
            if (Build.VERSION.SDK_INT >= 30) {
                return "NULL";
            }
            if (!(connectivityManager == null || (networkInfo = connectivityManager.getActiveNetworkInfo()) == null)) {
                networkType2 = networkInfo.getSubtype();
            }
        }
        switch (networkType2) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
            case 18:
            case 19:
                return "4G";
            case 20:
                return "5G";
            default:
                return "NULL";
        }
    }

    public static class SABroadcastReceiver extends BroadcastReceiver {
        private SABroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                NetworkUtils.cleanNetworkTypeCache();
                SensorsDataAPI.sharedInstance().flush();
                SALog.i(NetworkUtils.TAG, "SABroadcastReceiver is receiving ConnectivityManager.CONNECTIVITY_ACTION broadcast");
            }
        }
    }

    @TargetApi(21)
    public static class SANetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
        private SANetworkCallbackImpl() {
        }

        public void onAvailable(Network network) {
            super.onAvailable(network);
            NetworkUtils.cleanNetworkTypeCache();
            SensorsDataAPI.sharedInstance().flush();
            SALog.i(NetworkUtils.TAG, "onAvailable is calling");
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            NetworkUtils.cleanNetworkTypeCache();
            SALog.i(NetworkUtils.TAG, "onCapabilitiesChanged is calling");
        }

        public void onLost(Network network) {
            super.onLost(network);
            NetworkUtils.cleanNetworkTypeCache();
            SALog.i(NetworkUtils.TAG, "onLost is calling");
        }
    }
}
