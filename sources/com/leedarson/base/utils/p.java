package com.leedarson.base.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.microshow.rxffmpeg.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: JSBridgeResp */
public class p {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static JSONObject a(int code, String desc) {
        Object[] objArr = {new Integer(code), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, BuildConfig.VERSION_CODE, new Class[]{Integer.TYPE, String.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        if (desc == null) {
            desc = "";
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("desc", (Object) desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b(code, obj);
    }

    public static JSONObject c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 491, new Class[0], JSONObject.class);
        return proxy.isSupported ? (JSONObject) proxy.result : b(200, (Object) null);
    }

    public static JSONObject d(Object data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, (Object) null, changeQuickRedirect, true, 492, new Class[]{Object.class}, JSONObject.class);
        return proxy.isSupported ? (JSONObject) proxy.result : b(200, data);
    }

    public static JSONObject b(int code, Object data) {
        Object[] objArr = {new Integer(code), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 493, new Class[]{Integer.TYPE, Object.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("code", code);
            if (data != null) {
                obj.put("data", data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
