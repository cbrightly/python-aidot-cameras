package com.leedarson.skiprope.datamgr;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;

/* compiled from: DataParserMgr */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, d> a = new HashMap<>();
    private com.leedarson.skiprope.ctrl.c b;

    public c(com.leedarson.skiprope.ctrl.c playerController) {
        this.b = playerController;
    }

    public d a(String key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 9540, new Class[]{String.class}, d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        if (this.a.containsKey(key)) {
            return this.a.get(key);
        }
        d parser = new d(this.b, key);
        this.a.put(key, parser);
        return parser;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9541, new Class[0], Void.TYPE).isSupported) {
            for (d parser : this.a.values()) {
                if (parser.h()) {
                    parser.t();
                }
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9542, new Class[0], Void.TYPE).isSupported) {
            for (d parser : this.a.values()) {
                if (parser.h()) {
                    parser.v();
                }
            }
        }
    }
}
