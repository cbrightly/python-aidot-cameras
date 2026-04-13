package com.leedarson.base.utils.networkutil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: NetUtils */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 631, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (context != null) {
            try {
                NetworkInfo mNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    Log.d("NetUtils", "isConnected  当前是否有网络" + mNetworkInfo.isAvailable());
                    return mNetworkInfo.isAvailable();
                }
            } catch (Exception e) {
                Log.d("NetUtils", "检测网络发生异常   e=" + e.toString());
            }
        }
        return false;
    }
}
