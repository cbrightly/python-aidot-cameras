package com.leedarson.serviceimpl.ble.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.light.LightService;
import com.telink.bluetooth.light.e;
import com.telink.bluetooth.light.g;

public class TelinkLightService extends LightService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private static TelinkLightService f;

    public static TelinkLightService h() {
        return f;
    }

    public IBinder onBind(Intent intent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 6321, new Class[]{Intent.class}, IBinder.class);
        if (proxy.isSupported) {
            return (IBinder) proxy.result;
        }
        if (this.d == null) {
            this.d = new a();
        }
        return super.onBind(intent);
    }

    public void e(g gVar, int mode, int i, int src, byte[] params, byte[] data) {
        Class<byte[]> cls = byte[].class;
        Object[] objArr = {gVar, new Integer(mode), new Integer(i), new Integer(src), params, data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {g.class, cls2, cls2, cls2, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6322, clsArr, Void.TYPE).isSupported) {
            g light = gVar;
            int opcode = i;
            super.e(light, mode, opcode, src, params, data);
        }
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6323, new Class[0], Void.TYPE).isSupported) {
            super.onCreate();
            f = this;
            if (this.c == null) {
                this.c = new e();
            }
            this.c.U(this);
        }
    }

    public class a extends Binder {
        public static ChangeQuickRedirect changeQuickRedirect;

        public a() {
        }
    }
}
