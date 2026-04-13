package com.leedarson.serviceimpl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import androidx.work.WorkRequest;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: MeshBridgeCompat */
public class l {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean a = true;
    private boolean b = true;
    private Handler c = new Handler(Looper.getMainLooper());
    private long d = 0;
    private Context e;
    private Runnable f = new a();

    public l(Context context) {
        this.e = context;
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6069, new Class[0], Void.TYPE).isSupported) {
            this.c.removeCallbacks(this.f);
            this.c.postDelayed(this.f, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
        }
    }

    public String e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6070, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        this.a = true;
        this.c.removeCallbacks(this.f);
        String msg = "屏幕点亮";
        if (this.d != 0) {
            msg = msg + ",息屏持续:" + (System.currentTimeMillis() - this.d);
        }
        this.d = System.currentTimeMillis();
        com.leedarson.log.elk.a.y(this).p(msg).o("info").a().b();
        return msg;
    }

    public boolean b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6071, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.a) {
            return true;
        }
        PowerManager pm = (PowerManager) this.e.getSystemService("power");
        if (this.b || pm.isScreenOn()) {
            return false;
        }
        return true;
    }

    /* compiled from: MeshBridgeCompat */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6072, new Class[0], Void.TYPE).isSupported) {
                boolean unused = l.this.a = false;
            }
        }
    }

    public void c(boolean isFront) {
        this.b = isFront;
        if (isFront) {
            this.a = true;
        }
    }
}
