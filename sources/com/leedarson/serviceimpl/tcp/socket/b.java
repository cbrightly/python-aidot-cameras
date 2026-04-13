package com.leedarson.serviceimpl.tcp.socket;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;

/* compiled from: LdsSocketManager */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, a> a = new HashMap<>();

    public void b(String ip, a client) {
        Class[] clsArr = {String.class, a.class};
        if (!PatchProxy.proxy(new Object[]{ip, client}, this, changeQuickRedirect, false, 9089, clsArr, Void.TYPE).isSupported) {
            this.a.put(ip, client);
        }
    }

    public HashMap<String, a> a() {
        return this.a;
    }
}
