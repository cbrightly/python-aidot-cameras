package com.leedarson.serviceimpl.ipcservice;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashSet;
import org.json.JSONObject;

/* compiled from: PartialUpdateOnDeviceDeleteStrategy */
public class i {
    private static HashSet<String> a = new HashSet<>();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(JSONObject payloadObj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{payloadObj}, (Object) null, changeQuickRedirect, true, 8082, new Class[]{JSONObject.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (payloadObj.has("extensions")) {
            try {
                JSONObject extenObj = payloadObj.getJSONObject("extensions");
                if (extenObj.has(FirebaseAnalytics.Param.METHOD) && "devUnbindNotif".equals(extenObj.getString(FirebaseAnalytics.Param.METHOD)) && payloadObj.has("id")) {
                    HashSet<String> hashSet = a;
                    hashSet.add(payloadObj.getString("id") + "");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean a(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 8083, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a.contains(deviceId);
    }

    public static void c(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 8084, new Class[]{String.class}, Void.TYPE).isSupported && a.contains(deviceId)) {
            a.remove(deviceId);
        }
    }
}
