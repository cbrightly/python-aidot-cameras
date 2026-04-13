package com.leedarson.serviceimpl.ble.manager;

import android.os.HandlerThread;
import com.leedarson.bean.IRhyDevice;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;

/* compiled from: MsgPoolManager */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, e> a = new HashMap<>();
    private HandlerThread b;

    public f() {
        HandlerThread handlerThread = new HandlerThread(IRhyDevice.TYPE_BLE);
        this.b = handlerThread;
        handlerThread.start();
    }

    public e a(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 6308, new Class[]{String.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        e pool = this.a.get(mac);
        if (pool == null) {
            synchronized (f.class) {
                if (pool == null) {
                    pool = new e(mac, this.b);
                    this.a.put(mac, pool);
                }
            }
        }
        return pool;
    }
}
