package com.telink.bluetooth.light;

import android.os.Handler;
import android.util.Log;
import com.didichuxing.doraemonkit.BuildConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: AdvanceStrategy */
public abstract class a {
    public static final byte[] a = {-48, -46, -30};
    private static final a b = new c();
    private static a c;
    public static ChangeQuickRedirect changeQuickRedirect;
    protected b d;
    protected int e = BuildConfig.VERSION_CODE;

    /* compiled from: AdvanceStrategy */
    public interface b {
        boolean d(byte b, int i, byte[] bArr, Object obj, int i2);
    }

    public abstract void b();

    public abstract void c();

    public static a a() {
        synchronized (a.class) {
            a aVar = c;
            if (aVar != null) {
                return aVar;
            }
            return b;
        }
    }

    public void d(b mCallback) {
        this.d = mCallback;
    }

    /* compiled from: AdvanceStrategy */
    public static class c extends a {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public long f;
        private Handler g = new Handler();
        /* access modifiers changed from: private */
        public long h;
        private C0218a i = new C0218a();

        /* renamed from: com.telink.bluetooth.light.a$c$a  reason: collision with other inner class name */
        /* compiled from: AdvanceStrategy */
        public class C0218a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            private byte c;
            private int d;
            private byte[] f;
            private int q;
            private Object x;

            private C0218a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13509, new Class[0], Void.TYPE).isSupported) {
                    Log.d("AdvanceStrategy", "Delay run Opcode : " + Integer.toHexString(this.c));
                    long unused = c.this.f = System.currentTimeMillis();
                    long unused2 = c.this.h = System.currentTimeMillis();
                    c.this.d.d(this.c, this.d, this.f, this.x, this.q);
                }
            }
        }

        public void b() {
            this.f = 0;
        }

        public void c() {
        }
    }
}
