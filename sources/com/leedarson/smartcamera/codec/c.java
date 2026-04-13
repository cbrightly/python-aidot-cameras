package com.leedarson.smartcamera.codec;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import leedarson.platform.playersdk.PlayerSDK;

/* compiled from: LdsCodec */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public PlayerSDK a;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, e> b = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public a c;
    /* access modifiers changed from: private */
    public boolean d = true;
    private ExecutorService e;
    /* access modifiers changed from: private */
    public ExecutorService f;
    private ExecutorService g;
    private ExecutorService h;
    /* access modifiers changed from: private */
    public r i = new r(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public s j;
    /* access modifiers changed from: private */
    public d k;
    private Future l;
    /* access modifiers changed from: private */
    public int m;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public PlayerSDK.CallBack o = new j();

    /* compiled from: LdsCodec */
    public interface s {
        void a();
    }

    static /* synthetic */ byte[] d(c x0, byte[] x1, int x2, int x3) {
        Object[] objArr = {x0, x1, new Integer(x2), new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9674, new Class[]{c.class, byte[].class, cls, cls}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : x0.Y(x1, x2, x3);
    }

    static /* synthetic */ void f(c x0, String x1) {
        Class[] clsArr = {c.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9677, clsArr, Void.TYPE).isSupported) {
            x0.F(x1);
        }
    }

    static /* synthetic */ int k(c x0, byte[] x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9675, new Class[]{c.class, byte[].class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.w(x1);
    }

    static /* synthetic */ long q(c x0, byte[] x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9676, new Class[]{c.class, byte[].class}, Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : x0.x(x1);
    }

    public c() {
        ThreadFactory namedThreadFactory = new com.leedarson.base.utils.r("ldscodec-pool");
        this.e = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.f = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.g = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.h = Executors.newSingleThreadExecutor(namedThreadFactory);
    }

    public static int E() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9641, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Log.e("LdsCodec", "init: ");
        int pret = PlayerSDK.initPlayerSDK(20, "", 0);
        Log.e("LdsCodec", "init: end");
        return pret;
    }

    public void u(a callback) {
        this.c = callback;
    }

    /* compiled from: LdsCodec */
    public class j implements PlayerSDK.CallBack {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public int playerCallBack(int type, byte[] data, int dateLen) {
            Object[] objArr = {new Integer(type), data, new Integer(dateLen)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9688, new Class[]{cls, byte[].class, cls}, cls);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            switch (type) {
                case 0:
                    c cVar = c.this;
                    int unused = cVar.m = c.k(cVar, c.d(cVar, data, 20, 4));
                    c cVar2 = c.this;
                    int unused2 = cVar2.n = c.k(cVar2, c.d(cVar2, data, 24, 4));
                    Message msg = Message.obtain();
                    r unused3 = c.this.i;
                    msg.what = 2;
                    c.this.i.sendMessage(msg);
                    break;
                case 1:
                    Log.e("LdsCodec", "playerCallBack: PLAY FAILED");
                    break;
                case 4:
                    String pathKey = new String(data);
                    e listener = (e) c.this.b.get(pathKey);
                    if (listener != null) {
                        listener.onSuccess();
                        c.this.b.remove(pathKey);
                        break;
                    }
                    break;
                case 5:
                    String pathKey2 = new String(data);
                    e listener2 = (e) c.this.b.get(pathKey2);
                    if (listener2 != null) {
                        listener2.a(0);
                        c.this.b.remove(pathKey2);
                        break;
                    }
                    break;
                case 6:
                    c cVar3 = c.this;
                    int unused4 = cVar3.m = c.k(cVar3, c.d(cVar3, data, 20, 4));
                    c cVar4 = c.this;
                    int unused5 = cVar4.n = c.k(cVar4, c.d(cVar4, data, 24, 4));
                    break;
                case 9:
                    if (dateLen >= 44 && c.this.c != null) {
                        c cVar5 = c.this;
                        int decFps = c.k(cVar5, c.d(cVar5, data, 8, 4));
                        c cVar6 = c.this;
                        int showFps = c.k(cVar6, c.d(cVar6, data, 12, 4));
                        c cVar7 = c.this;
                        long curVideoTime = c.q(cVar7, c.d(cVar7, data, 40, 8));
                        if (c.this.c != null) {
                            c.this.c.W0(curVideoTime, decFps, showFps);
                            break;
                        }
                    }
                    break;
                case 25:
                    Log.e("LdsCodec", "playvideo  CBC_RECODER_ERROR_FINISH");
                    if (c.this.c != null) {
                        c.this.c.L0();
                        break;
                    }
                    break;
                case 30:
                    if (c.this.c != null) {
                        c.this.c.B0(data, dateLen);
                        break;
                    }
                    break;
                case 31:
                    Message msg2 = Message.obtain();
                    r unused6 = c.this.i;
                    msg2.what = 7;
                    msg2.obj = data;
                    msg2.arg1 = c.this.m;
                    msg2.arg2 = c.this.n;
                    c.this.i.sendMessage(msg2);
                    break;
            }
            return 0;
        }

        public void snapYUVCallback(String path, byte[] data, int width, int height) {
            Object[] objArr = {path, data, new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {String.class, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9689, clsArr, Void.TYPE).isSupported) {
                Log.e("LdsCodec", "snapYUVCallback: " + data.length + "==" + path + "=" + width + "=" + height);
                Message msg2 = Message.obtain();
                r unused = c.this.i;
                msg2.what = 7;
                msg2.obj = data;
                msg2.arg1 = width;
                msg2.arg2 = height;
                c.this.i.sendMessage(msg2);
                c.this.f.submit(new a(data, width, height, path));
            }
        }

        /* compiled from: LdsCodec */
        public class a implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ byte[] c;
            final /* synthetic */ int d;
            final /* synthetic */ int f;
            final /* synthetic */ String q;

            a(byte[] bArr, int i, int i2, String str) {
                this.c = bArr;
                this.d = i;
                this.f = i2;
                this.q = str;
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9690, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                try {
                    c.this.K(this.q, c.this.a(this.c, this.d, this.f), this.d, this.f);
                    Message msg = Message.obtain();
                    r unused = c.this.i;
                    msg.what = 5;
                    msg.obj = this.q;
                    c.this.i.sendMessage(msg);
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg2 = Message.obtain();
                    r unused2 = c.this.i;
                    msg2.what = 6;
                    msg2.obj = this.q;
                    c.this.i.sendMessage(msg2);
                    return null;
                }
            }
        }
    }

    /* compiled from: LdsCodec */
    public class k implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Surface c;

        k(Surface surface) {
            this.c = surface;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9691, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            int ret = -1;
            if (c.this.a == null) {
                PlayerSDK unused = c.this.a = new PlayerSDK();
                ret = c.this.a.createPlayerSDK(4, c.this.o);
                c.this.a.setAVSyncMode(0);
            }
            c cVar = c.this;
            c.f(cVar, "LdsCodec new: " + ret);
            c.f(c.this, "createCodec: ");
            if (c.this.a == null) {
                return null;
            }
            c.this.a.setMinBuffTime(1000);
            int dRet = c.this.a.startDecoderPlay(this.c, 1, 0);
            c.this.a.clearBuffer();
            c cVar2 = c.this;
            c.f(cVar2, "createCodec: end " + dRet);
            return null;
        }
    }

    public void B(Surface surface, boolean z) {
        if (!PatchProxy.proxy(new Object[]{surface, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9644, new Class[]{Surface.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            F("LdsCodec new: ");
            this.e.submit(new k(surface));
        }
    }

    /* compiled from: LdsCodec */
    public class l implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b c;

        l(b bVar) {
            this.c = bVar;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9692, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            int ret = -1;
            if (c.this.a == null) {
                PlayerSDK unused = c.this.a = new PlayerSDK();
                ret = c.this.a.createPlayerSDK(4, c.this.o);
                c.this.a.setAVSyncMode(0);
            }
            c cVar = c.this;
            c.f(cVar, "LdsCodec new: " + ret);
            c.f(c.this, "createCodec: ");
            if (c.this.a != null) {
                c.this.a.setMinBuffTime(1000);
                c.f(c.this, "createCodec: end ");
            }
            b bVar = this.c;
            if (bVar == null) {
                return null;
            }
            if (ret == 0) {
                bVar.onSuccess();
                return null;
            }
            bVar.a(ret);
            return null;
        }
    }

    public void C(b listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9645, new Class[]{b.class}, Void.TYPE).isSupported) {
            F("LdsCodec new 2: ");
            this.e.submit(new l(listener));
        }
    }

    /* compiled from: LdsCodec */
    public class m implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b c;

        m(b bVar) {
            this.c = bVar;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9693, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            int ret = -1;
            if (c.this.a == null) {
                PlayerSDK unused = c.this.a = new PlayerSDK();
                ret = c.this.a.createPlayerSDK(4, c.this.o);
                c.this.a.setAVSyncMode(0);
            }
            c cVar = c.this;
            c.f(cVar, "LdsCodec new: " + ret);
            c.f(c.this, "createCodec: ");
            if (c.this.a != null) {
                c.this.a.setMinBuffTime(3000);
                c.f(c.this, "createCodec: end ");
            }
            b bVar = this.c;
            if (bVar == null) {
                return null;
            }
            if (ret == 0) {
                bVar.onSuccess();
                return null;
            }
            bVar.a(ret);
            return null;
        }
    }

    public void D(b listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9646, new Class[]{b.class}, Void.TYPE).isSupported) {
            F("LdsCodec new 2: ");
            this.e.submit(new m(listener));
        }
    }

    /* compiled from: LdsCodec */
    public class n implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ byte[] c;
        final /* synthetic */ int d;
        final /* synthetic */ long f;
        final /* synthetic */ int q;

        n(byte[] bArr, int i, long j, int i2) {
            this.c = bArr;
            this.d = i;
            this.f = j;
            this.q = i2;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9694, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (c.this.a == null) {
                return null;
            }
            int bak = c.this.a.drawVideo(this.c, this.d, this.f, 100, 100, this.q);
            return null;
        }
    }

    public void Z(long timestap, byte[] data, int len, int codec) {
        Object[] objArr = {new Long(timestap), data, new Integer(len), new Integer(codec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9647, clsArr, Void.TYPE).isSupported) {
            this.g.submit(new n(data, len, timestap, codec));
        }
    }

    /* compiled from: LdsCodec */
    public class o implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ byte[] c;
        final /* synthetic */ int d;
        final /* synthetic */ long f;
        final /* synthetic */ int q;

        o(byte[] bArr, int i, long j, int i2) {
            this.c = bArr;
            this.d = i;
            this.f = j;
            this.q = i2;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9695, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (c.this.a == null) {
                return null;
            }
            int bak = c.this.a.soundAudio(this.c, this.d, this.f, this.q);
            return null;
        }
    }

    public void v(long timestap, byte[] data, int len, int codec) {
        Object[] objArr = {new Long(timestap), data, new Integer(len), new Integer(codec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9648, clsArr, Void.TYPE).isSupported) {
            this.h.submit(new o(data, len, timestap, codec));
        }
    }

    /* compiled from: LdsCodec */
    public class p implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Surface c;

        p(Surface surface) {
            this.c = surface;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9696, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            c cVar = c.this;
            c.f(cVar, "startCodec: " + c.this.a);
            if (c.this.a == null) {
                return null;
            }
            int ret = c.this.a.startDecoderPlay(this.c, 1, 0);
            c cVar2 = c.this;
            c.f(cVar2, "startCodec: end " + ret);
            return null;
        }
    }

    public void O(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 9649, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            F("startCodec: ");
            this.e.submit(new p(surface));
        }
    }

    /* compiled from: LdsCodec */
    public class q implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Surface c;
        final /* synthetic */ b d;

        q(Surface surface, b bVar) {
            this.c = surface;
            this.d = bVar;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9697, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            c cVar = c.this;
            c.f(cVar, "startCodec: " + c.this.a);
            int ret = -1;
            if (c.this.a != null) {
                ret = c.this.a.startDecoderPlay(this.c, 1, 0);
                c cVar2 = c.this;
                c.f(cVar2, "startCodec: end " + ret);
            }
            b bVar = this.d;
            if (bVar == null) {
                return null;
            }
            if (ret == 0) {
                bVar.onSuccess();
                return null;
            }
            bVar.a(ret);
            return null;
        }
    }

    public void P(Surface surface, b listener) {
        Class[] clsArr = {Surface.class, b.class};
        if (!PatchProxy.proxy(new Object[]{surface, listener}, this, changeQuickRedirect, false, 9650, clsArr, Void.TYPE).isSupported) {
            F("startCodec: ");
            this.e.submit(new q(surface, listener));
        }
    }

    /* compiled from: LdsCodec */
    public class a implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9679, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            c cVar = c.this;
            c.f(cVar, "stopCodec: " + c.this.a);
            if (c.this.a == null) {
                return null;
            }
            c.this.a.stopDecoderPlay();
            c.f(c.this, "stopCodec: end");
            return null;
        }
    }

    public void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9651, new Class[0], Void.TYPE).isSupported) {
            F("stopCodec: ");
            this.e.submit(new a());
        }
    }

    public void H(Surface surface, s listener) {
        if (!PatchProxy.proxy(new Object[]{surface, listener}, this, changeQuickRedirect, false, 9652, new Class[]{Surface.class, s.class}, Void.TYPE).isSupported) {
            this.j = listener;
            F("reStartCodec: ");
            Future future = this.l;
            if (future != null && !future.isCancelled()) {
                F("reStartCodec 1: ");
                this.l.cancel(true);
            }
            this.l = this.e.submit(new b(surface));
        }
    }

    /* compiled from: LdsCodec */
    public class b implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Surface c;

        b(Surface surface) {
            this.c = surface;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9680, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            c cVar = c.this;
            c.f(cVar, "reStartCodec start: " + c.this.a);
            if (c.this.a == null) {
                PlayerSDK unused = c.this.a = new PlayerSDK();
                int createPlayerSDK = c.this.a.createPlayerSDK(4, c.this.o);
                c.this.a.setAVSyncMode(0);
            }
            if (c.this.a == null) {
                return null;
            }
            c.this.a.stopDecoderPlay();
            int res = c.this.a.startDecoderPlay(this.c, 1, 0);
            c cVar2 = c.this;
            c.f(cVar2, "reStartCodec: end :" + res);
            Message msg = Message.obtain();
            r unused2 = c.this.i;
            msg.what = 1;
            c.this.i.sendMessage(msg);
            return null;
        }
    }

    public void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9653, new Class[0], Void.TYPE).isSupported) {
            F("releaseCodec: ");
            this.c = null;
            this.e.submit(new C0170c());
            this.b.clear();
        }
    }

    /* renamed from: com.leedarson.smartcamera.codec.c$c  reason: collision with other inner class name */
    /* compiled from: LdsCodec */
    public class C0170c implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0170c() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9681, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Log.e("LdsCodec", "releaseCodec: start");
            if (c.this.a != null) {
                c.this.a.stopDecoderPlay();
                c.this.a.destoryPlayerSDK();
                PlayerSDK unused = c.this.a = null;
                Log.e("LdsCodec", "releaseCodec....");
            }
            Log.e("LdsCodec", "releaseCodec: end");
            return null;
        }
    }

    /* compiled from: LdsCodec */
    public class d implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9682, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (c.this.a == null) {
                return null;
            }
            c.this.a.pause();
            c.f(c.this, "pauseCodec: end");
            return null;
        }
    }

    public void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9654, new Class[0], Void.TYPE).isSupported) {
            F("pauseCodec: ");
            this.e.submit(new d());
        }
    }

    /* compiled from: LdsCodec */
    public class e implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9683, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (c.this.a == null) {
                return null;
            }
            c.this.a.resume();
            c.f(c.this, "resumeCodec: end");
            return null;
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9655, new Class[0], Void.TYPE).isSupported) {
            F("resumeCodec: ");
            this.e.submit(new e());
        }
    }

    /* compiled from: LdsCodec */
    public class f implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Surface c;
        final /* synthetic */ int d;
        final /* synthetic */ int f;

        f(Surface surface, int i, int i2) {
            this.c = surface;
            this.d = i;
            this.f = i2;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9684, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (c.this.a == null) {
                return null;
            }
            c.this.a.setSurface(this.c);
            c.this.a.surfaceChanged(this.d, this.f);
            c.f(c.this, "changeSurface: end");
            return null;
        }
    }

    public void z(Surface surface, int width, int height) {
        Object[] objArr = {surface, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9656, new Class[]{Surface.class, cls, cls}, Void.TYPE).isSupported) {
            F("changeSurface: ");
            this.e.submit(new f(surface, width, height));
        }
    }

    public void M(String path, e listener) {
        Class[] clsArr = {String.class, e.class};
        if (!PatchProxy.proxy(new Object[]{path, listener}, this, changeQuickRedirect, false, 9657, clsArr, Void.TYPE).isSupported) {
            if (this.a != null) {
                this.b.put(path, listener);
                int ret = this.a.snapshot2(path);
                if (ret != 0 && listener != null) {
                    listener.a(ret);
                    this.b.remove(path);
                }
            }
        }
    }

    public int R(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 9658, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        PlayerSDK playerSDK = this.a;
        if (playerSDK != null) {
            return playerSDK.startRecoder(path, 0, 14400000);
        }
        return -1;
    }

    public int W() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9659, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        PlayerSDK playerSDK = this.a;
        if (playerSDK != null) {
            return playerSDK.stopRecoder("");
        }
        return -1;
    }

    public void S(String path, d listener) {
        Class[] clsArr = {String.class, d.class};
        if (!PatchProxy.proxy(new Object[]{path, listener}, this, changeQuickRedirect, false, 9660, clsArr, Void.TYPE).isSupported) {
            if (listener != null) {
                this.k = listener;
                this.e.submit(new g(path));
            }
        }
    }

    /* compiled from: LdsCodec */
    public class g implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        g(String str) {
            this.c = str;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9685, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Message msg = Message.obtain();
            r unused = c.this.i;
            msg.what = 3;
            if (c.this.a != null) {
                msg.arg1 = c.this.a.startRecoder(this.c, 0, 14400000);
            } else {
                msg.arg1 = -1;
            }
            c.this.i.sendMessage(msg);
            return null;
        }
    }

    public void X(d listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9661, new Class[]{d.class}, Void.TYPE).isSupported) {
            if (listener != null) {
                this.k = listener;
                this.e.submit(new h());
            }
        }
    }

    /* compiled from: LdsCodec */
    public class h implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9686, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Message msg = Message.obtain();
            r unused = c.this.i;
            msg.what = 4;
            if (c.this.a != null) {
                msg.arg1 = c.this.a.stopRecoder("");
            } else {
                msg.arg1 = -1;
            }
            c.this.i.sendMessage(msg);
            return null;
        }
    }

    /* compiled from: LdsCodec */
    public class i implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;

        i(boolean z) {
            this.c = z;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9687, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            boolean unused = c.this.d = this.c;
            if (c.this.a == null) {
                return null;
            }
            c.this.a.changeMuteState(this.c);
            c cVar = c.this;
            c.f(cVar, "codec changeMute: " + this.c);
            return null;
        }
    }

    public void y(boolean mute) {
        Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9662, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.e.submit(new i(mute));
        }
    }

    public void A() {
        PlayerSDK playerSDK;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9663, new Class[0], Void.TYPE).isSupported && (playerSDK = this.a) != null) {
            playerSDK.clearBuffer();
        }
    }

    public void Q(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 9664, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.a == null) {
                this.a = new PlayerSDK();
            }
            PlayerSDK playerSDK = this.a;
            if (playerSDK != null) {
                int ret = playerSDK.startMuxer(path);
                Log.e("LdsCodec", "startMuxer: " + path + "==ret:" + ret);
            }
        }
    }

    public void V() {
        PlayerSDK playerSDK;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9665, new Class[0], Void.TYPE).isSupported && (playerSDK = this.a) != null) {
            playerSDK.stopMuxer();
        }
    }

    public int b0(long j2, byte[] bArr, int i2, int i3) {
        Object[] objArr = {new Long(j2), bArr, new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9666, new Class[]{Long.TYPE, byte[].class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int len = i2;
        long timestap = j2;
        byte[] data = bArr;
        int codec = i3;
        PlayerSDK playerSDK = this.a;
        if (playerSDK == null) {
            return -1;
        }
        int bak = playerSDK.writeVideoData(data, len, timestap, 100, 100, codec);
        SystemClock.sleep(10);
        return bak;
    }

    public int a0(long j2, byte[] bArr, int i2, int i3) {
        Object[] objArr = {new Long(j2), bArr, new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9667, new Class[]{Long.TYPE, byte[].class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int len = i2;
        long timestap = j2;
        byte[] data = bArr;
        int codec = i3;
        PlayerSDK playerSDK = this.a;
        if (playerSDK == null) {
            return -1;
        }
        int bak = playerSDK.writeAudioData(data, len, timestap, codec);
        SystemClock.sleep(10);
        return bak;
    }

    public int N(int sampleRate, int channel, int bits) {
        Object[] objArr = {new Integer(sampleRate), new Integer(channel), new Integer(bits)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9668, new Class[]{cls, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int bak = -1;
        PlayerSDK playerSDK = this.a;
        if (playerSDK == null) {
            return -1;
        }
        try {
            bak = playerSDK.startAudioCapture(sampleRate, channel, bits);
            SystemClock.sleep(10);
            return bak;
        } catch (Exception ex) {
            ex.toString();
            return bak;
        }
    }

    public int T() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9669, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        PlayerSDK playerSDK = this.a;
        if (playerSDK == null) {
            return -1;
        }
        try {
            return playerSDK.stopAudioCapture();
        } catch (Exception ex) {
            ex.toString();
            return -1;
        }
    }

    public int L(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9670, new Class[]{Boolean.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        PlayerSDK playerSDK = this.a;
        if (playerSDK == null) {
            return -1;
        }
        try {
            return playerSDK.setYUVCbEnable(enable);
        } catch (Exception ex) {
            ex.toString();
            return -1;
        }
    }

    private int w(byte[] res) {
        return (res[0] & 255) | ((res[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((res[2] << 24) >>> 8) | (res[3] << 24);
    }

    private byte[] Y(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i2 = begin; i2 < begin + count; i2++) {
            bs[i2 - begin] = src[i2];
        }
        return bs;
    }

    private long x(byte[] res) {
        long num = 0;
        for (int ix = 7; ix >= 0; ix--) {
            num = (num << 8) | ((long) (res[ix] & 255));
        }
        return num;
    }

    private void F(String msg) {
    }

    /* compiled from: LdsCodec */
    public class r extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        public r(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9698, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        if (c.this.j != null) {
                            c.this.j.a();
                            return;
                        }
                        return;
                    case 2:
                        if (c.this.c != null) {
                            c.this.c.g();
                        }
                        c cVar = c.this;
                        cVar.y(cVar.d);
                        return;
                    case 3:
                    case 4:
                        if (c.this.k == null) {
                            return;
                        }
                        if (msg.arg1 == 0) {
                            c.this.k.onSuccess();
                            return;
                        } else {
                            c.this.k.a(msg.arg1);
                            return;
                        }
                    case 5:
                        try {
                            String path = (String) msg.obj;
                            e listener = (e) c.this.b.get(path);
                            if (listener != null) {
                                listener.onSuccess();
                                c.this.b.remove(path);
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    case 6:
                        try {
                            String path2 = (String) msg.obj;
                            e listener2 = (e) c.this.b.get(path2);
                            if (listener2 != null) {
                                listener2.a(-1);
                                c.this.b.remove(path2);
                                return;
                            }
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    case 7:
                        try {
                            byte[] data = (byte[]) msg.obj;
                            int width = msg.arg1;
                            int height = msg.arg2;
                            if (c.this.c != null) {
                                c.this.c.B(data, width, height);
                            }
                            return;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            return;
                        }
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        }
    }

    public byte[] a(byte[] i420bytes, int width, int height) {
        Object[] objArr = {i420bytes, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9671, new Class[]{byte[].class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] nv21bytes = new byte[i420bytes.length];
        int total = width * height;
        int nLen = total / 4;
        System.arraycopy(i420bytes, 0, nv21bytes, 0, total);
        for (int i2 = 0; i2 < nLen; i2++) {
            byte u = i420bytes[total + i2];
            nv21bytes[(i2 * 2) + total] = i420bytes[total + nLen + i2];
            nv21bytes[(i2 * 2) + total + 1] = u;
        }
        return nv21bytes;
    }

    public void K(String savePicPath, byte[] bArr, int width, int i2) {
        Object[] objArr = {savePicPath, bArr, new Integer(width), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9672, new Class[]{String.class, byte[].class, cls, cls}, Void.TYPE).isSupported) {
            byte[] data = bArr;
            int height = i2;
            FileOutputStream outStream = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                new YuvImage(data, 17, width, height, (int[]) null).compressToJpeg(new Rect(0, 0, width, height), 90, baos);
                Bitmap bmp = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
                outStream = new FileOutputStream(savePicPath);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.write(baos.toByteArray());
                outStream.close();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            } catch (Throwable th) {
                outStream.close();
                throw th;
            }
            outStream.close();
        }
    }
}
