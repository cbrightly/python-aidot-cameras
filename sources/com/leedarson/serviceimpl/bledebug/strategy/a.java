package com.leedarson.serviceimpl.bledebug.strategy;

import android.content.Context;
import com.leedarson.serviceimpl.bledebug.bean.ConnectBean;
import com.leedarson.serviceimpl.bledebug.ble.BleDebugController;
import com.leedarson.serviceimpl.bledebug.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ConnectStrategy */
public abstract class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected ConcurrentHashMap<String, BleDebugController> a = new ConcurrentHashMap<>();
    protected Context b;
    protected c c;
    protected com.leedarson.serviceimpl.bledebug.bean.a d;
    public Set<String> e;
    public Set<String> f;

    public abstract void a(List<ConnectBean> list);

    public a(Context context, c presenter, com.leedarson.serviceimpl.bledebug.bean.a config) {
        this.b = context;
        this.c = presenter;
        this.d = config;
        this.e = new HashSet();
        this.f = new HashSet();
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6833, new Class[0], Void.TYPE).isSupported) {
            if (this.a.size() > 0) {
                for (BleDebugController next : this.a.values()) {
                    h(next);
                }
            }
            this.a.clear();
            this.e.clear();
            this.f.clear();
        }
    }

    public void h(BleDebugController controller) {
        if (!PatchProxy.proxy(new Object[]{controller}, this, changeQuickRedirect, false, 6834, new Class[]{BleDebugController.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.bledebug.a.a(controller.x + " 释放连接");
            this.a.remove(controller.x);
            controller.q();
            controller.Q();
        }
    }

    public void e(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6835, new Class[]{String.class}, Void.TYPE).isSupported) {
            f(key, "");
        }
    }

    public void c(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 6836, new Class[]{String.class}, Void.TYPE).isSupported) {
            d(key, "");
        }
    }

    public void f(String key, String str) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{key, str}, this, changeQuickRedirect, false, 6837, clsArr, Void.TYPE).isSupported) {
            this.e.add(key);
        }
    }

    public void d(String key, String str) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{key, str}, this, changeQuickRedirect, false, 6838, clsArr, Void.TYPE).isSupported) {
            this.f.add(key);
        }
    }

    public int[] b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6839, new Class[0], int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int total = this.e.size() + this.f.size();
        com.leedarson.serviceimpl.bledebug.a.b("总数:" + this.e.size() + ",失败:" + this.f.size());
        return new int[]{total, this.f.size()};
    }
}
