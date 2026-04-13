package com.leedarson.serviceimpl.blec075;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: GsonUtils */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static LinkedTreeMap a(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 6396, new Class[]{String.class}, LinkedTreeMap.class);
        if (proxy.isSupported) {
            return (LinkedTreeMap) proxy.result;
        }
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return (LinkedTreeMap) new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonData, LinkedTreeMap.class);
    }
}
