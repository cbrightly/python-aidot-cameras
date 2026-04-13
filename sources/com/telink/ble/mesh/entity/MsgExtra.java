package com.telink.ble.mesh.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class MsgExtra {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a = false;
    public String b = "";
    public String c = "";
    public String d;
    public long e;
    public int f;
    public String g;
    public long h;

    public MsgExtra(String desc, String traceId) {
        this.b = desc;
        this.c = traceId;
    }

    public MsgExtra(String desc, String traceId, String mac, long startTime) {
        this.b = desc;
        this.c = traceId;
        this.d = mac;
        this.e = startTime;
    }

    public void b(int groupId) {
        this.f = groupId;
    }

    public String c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13029, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new Gson().toJson((Object) this);
    }

    public static MsgExtra a(String json) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{json}, (Object) null, changeQuickRedirect, true, 13030, new Class[]{String.class}, MsgExtra.class);
        return proxy.isSupported ? (MsgExtra) proxy.result : (MsgExtra) new Gson().fromJson(json, new TypeToken<MsgExtra>() {
        }.getType());
    }
}
