package com.leedarson.newui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.ldf.calendar.view.Calendar;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.CalendarData;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.ShowToastEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.codec.c;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.h0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.view.IpcSDWebrtcSurfaceView;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.leedarson.view.WeekCalendar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.ByteUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import org.webrtc.SurfaceViewRenderer;

/* compiled from: SDCardPlayPresenter */
public class e6 extends com.leedarson.base.presenters.a<f6, SDCardPlayActivity> {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean A = false;
    /* access modifiers changed from: private */
    public ExecutorService B = Executors.newFixedThreadPool(3, new com.leedarson.base.utils.r("sd-p-pool"));
    /* access modifiers changed from: private */
    public t C = new t(this, (k) null);
    public boolean D = false;
    /* access modifiers changed from: private */
    public boolean E = false;
    /* access modifiers changed from: private */
    public int F;
    /* access modifiers changed from: private */
    public boolean G = false;
    /* access modifiers changed from: private */
    public boolean H;
    private com.leedarson.smartcamera.listener.i I = new k();
    private String J = " 00:00:00";
    private String K = " 23:59:59";
    private com.leedarson.smartcamera.listener.d L = new d();
    private int M = 0;
    int N = 0;
    io.reactivex.disposables.b O;
    com.leedarson.newui.repos.n P = new com.leedarson.newui.repos.n();
    io.reactivex.disposables.b Q;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c f;
    private SimpleDateFormat g;
    private String h;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public long j;
    /* access modifiers changed from: private */
    public long k;
    private int l;
    private Surface m;
    /* access modifiers changed from: private */
    public String n;
    private String o;
    private Calendar p;
    private Timer q;
    /* access modifiers changed from: private */
    public int r;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.sdk.a s;
    /* access modifiers changed from: private */
    public boolean t = false;
    /* access modifiers changed from: private */
    public boolean u = true;
    private boolean v = false;
    /* access modifiers changed from: private */
    public boolean w;
    /* access modifiers changed from: private */
    public f0 x;
    /* access modifiers changed from: private */
    public long y = 0;
    /* access modifiers changed from: private */
    public long z = 0;

    static /* synthetic */ void B(e6 x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 3078, new Class[]{e6.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.J0(x1);
        }
    }

    static /* synthetic */ void I(e6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3079, new Class[]{e6.class}, Void.TYPE).isSupported) {
            x0.e1();
        }
    }

    static /* synthetic */ void L(e6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3080, new Class[]{e6.class}, Void.TYPE).isSupported) {
            x0.s0();
        }
    }

    static /* synthetic */ void M(e6 x0, IpcSDWebrtcSurfaceView x1) {
        Class[] clsArr = {e6.class, IpcSDWebrtcSurfaceView.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 3081, clsArr, Void.TYPE).isSupported) {
            x0.Q0(x1);
        }
    }

    static /* synthetic */ void O(e6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3075, new Class[]{e6.class}, Void.TYPE).isSupported) {
            x0.T0();
        }
    }

    static /* synthetic */ void u(e6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3076, new Class[]{e6.class}, Void.TYPE).isSupported) {
            x0.Y0();
        }
    }

    static /* synthetic */ int w(e6 x0) {
        int i2 = x0.r;
        x0.r = i2 + 1;
        return i2;
    }

    static /* synthetic */ void x(e6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3077, new Class[]{e6.class}, Void.TYPE).isSupported) {
            x0.b1();
        }
    }

    public void R0(boolean supportPreCon) {
        this.H = supportPreCon;
    }

    /* compiled from: SDCardPlayPresenter */
    public class k implements com.leedarson.smartcamera.listener.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void e(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3082, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                e6 e6Var = e6.this;
                e6Var.B0("onStateChange:" + state);
                switch (state) {
                    case -2:
                        com.leedarson.newui.repoter.j.b().g(e6.this.n, "tutk达到最大连接数", e6.this.w);
                        return;
                    case -1:
                    case 3:
                        com.leedarson.newui.repoter.j.b().g(e6.this.n, "tutk连接断开OR获取流失败", e6.this.w);
                        e6.O(e6.this);
                        return;
                    case 0:
                        if (e6.this.m() != null) {
                            ((f6) e6.this.m()).i();
                            com.leedarson.newui.repoter.j.b().g(e6.this.n, "tutk连接中", e6.this.w);
                            return;
                        }
                        return;
                    case 1:
                        com.leedarson.newui.repoter.j.b().g(e6.this.n, "tutk连接成功", e6.this.w);
                        return;
                    case 4:
                        com.leedarson.newui.repoter.j.b().f(e6.this.n);
                        return;
                    default:
                        return;
                }
            }
        }

        public void b(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3083, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                e6 e6Var = e6.this;
                e6Var.B0("onConnectError:" + code);
                com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                String r = e6.this.n;
                b.g(r, "Tutk连接发生错误--> code=" + code, e6.this.w);
            }
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3084, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (e6.this.f != null) {
                    e6.this.f.Z(timestap, data, len, codec);
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3085, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (e6.this.f != null) {
                    e6.this.f.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
            Object[] objArr = {new Long(timestamp)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3086, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                long unused = e6.this.k = timestamp;
            }
        }
    }

    public e6(f6 view, SDCardPlayActivity activity) {
        super(view, activity);
    }

    public void M0(String deviceId) {
        this.n = deviceId;
    }

    public void q0(String id, String str, String str2, String str3, String str4) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{id, str, str2, str3, str4}, this, changeQuickRedirect, false, 3026, clsArr, Void.TYPE).isSupported) {
            String p2pId = str;
            String password = str3;
            String account = str2;
            String dtls = str4;
            timber.log.a.c("initTutkChannel: start", new Object[0]);
            com.leedarson.manager.a.i().b(id, p2pId);
            com.leedarson.smartcamera.sdk.a tempChannel = com.leedarson.manager.a.i().l(p2pId);
            if (tempChannel == null) {
                this.s = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.s);
            } else if (!account.equals(tempChannel.E0()) || !password.equals(tempChannel.H0())) {
                this.s = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.s);
            } else {
                this.s = tempChannel;
            }
            timber.log.a.c("initTutkChannel: end-" + this.s, new Object[0]);
            if (this.s != null) {
                if (com.alibaba.android.arouter.utils.e.b(dtls) || !dtls.equals("1")) {
                    this.s.D1(0);
                } else {
                    this.s.D1(1);
                }
                this.s.registerTutkListener(this.I);
                this.s.setOnSDRecordPlayListener(new l());
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class l implements com.leedarson.smartcamera.listener.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void b(int time) {
            Object[] objArr = {new Integer(time)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3119, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).o(time * 1000);
                }
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3120, new Class[0], Void.TYPE).isSupported) {
                e6.this.B0("reciveStreamEnd");
                boolean unused = e6.this.t = true;
                com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                String r = e6.this.n;
                b.g(r, "回放获取流结束:" + e6.this.j, e6.this.w);
            }
        }

        public void a() {
        }

        public void d() {
        }
    }

    public void B0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3027, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("SDCardPlayPresenter").a(msg, new Object[0]);
        }
    }

    public void o0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3028, new Class[0], Void.TYPE).isSupported) {
            this.f = new com.leedarson.smartcamera.codec.c();
            com.leedarson.manager.c.u().A(this.f);
            this.f.u(new m());
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class m implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void W0(long j, int i, int i2) {
            Object[] objArr = {new Long(j), new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3121, clsArr, Void.TYPE).isSupported) {
                int showFps = i2;
                long currentTime = j;
                int decFps = i;
                try {
                    SDCardPlayActivity activity = (SDCardPlayActivity) e6.this.l();
                    if (activity != null && activity.h2()) {
                        activity.runOnUiThread(new a(currentTime, activity, decFps, showFps));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* compiled from: SDCardPlayPresenter */
        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ long c;
            final /* synthetic */ SDCardPlayActivity d;
            final /* synthetic */ int f;
            final /* synthetic */ int q;

            a(long j, SDCardPlayActivity sDCardPlayActivity, int i, int i2) {
                this.c = j;
                this.d = sDCardPlayActivity;
                this.f = i;
                this.q = i2;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3123, new Class[0], Void.TYPE).isSupported) {
                    long curPlaySec = this.c - e6.this.k;
                    SDCardPlayActivity sDCardPlayActivity = this.d;
                    if (sDCardPlayActivity.Z5) {
                        int i = sDCardPlayActivity.Y5;
                        int i2 = sDCardPlayActivity.X5;
                        if (i - i2 > 0 && curPlaySec >= ((long) i)) {
                            sDCardPlayActivity.Z5 = false;
                            sDCardPlayActivity.X5 = 0;
                            sDCardPlayActivity.Y5 = 0;
                        } else if (i - i2 < 0 && curPlaySec >= ((long) i) && curPlaySec < ((long) i2)) {
                            sDCardPlayActivity.Z5 = false;
                            sDCardPlayActivity.X5 = 0;
                            sDCardPlayActivity.Y5 = 0;
                        } else {
                            return;
                        }
                    }
                    if (e6.this.m() != null && !this.d.W5) {
                        ((f6) e6.this.m()).w((int) curPlaySec);
                        if (e6.this.t && this.f == 0 && this.q == 0 && !e6.this.u) {
                            ((f6) e6.this.m()).u();
                            boolean unused = e6.this.u = true;
                            com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                            String r = e6.this.n;
                            b.g(r, "播放结束：" + e6.this.j, e6.this.w);
                        }
                    }
                }
            }
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3122, new Class[0], Void.TYPE).isSupported) {
                boolean unused = e6.this.u = false;
                long unused2 = e6.this.y = 0;
                long unused3 = e6.this.z = 0;
                if (e6.this.m() != null) {
                    com.leedarson.newui.repoter.j.b().e(e6.this.n);
                    ((f6) e6.this.m()).g();
                }
            }
        }

        public void L0() {
        }

        public void B0(byte[] data, int length) {
        }

        public void B(byte[] data, int width, int height) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void W0(android.view.Surface r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.view.Surface> r4 = android.view.Surface.class
            r6[r2] = r4
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3029(0xbd5, float:4.245E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r8
            com.leedarson.smartcamera.codec.c r2 = r1.f
            if (r2 == 0) goto L_0x0032
            if (r9 == 0) goto L_0x0032
            boolean r3 = r1.v
            if (r3 != 0) goto L_0x002f
            r3 = 0
            r2.D(r3)
            r1.v = r0
            goto L_0x0032
        L_0x002f:
            r2.O(r9)
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.e6.W0(android.view.Surface):void");
    }

    public void Z0() {
        com.leedarson.smartcamera.codec.c cVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3030, new Class[0], Void.TYPE).isSupported && (cVar = this.f) != null) {
            cVar.U();
        }
    }

    public void c0(Surface surface, int width, int height) {
        com.leedarson.smartcamera.codec.c cVar;
        Object[] objArr = {surface, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3031, new Class[]{Surface.class, cls, cls}, Void.TYPE).isSupported && (cVar = this.f) != null && surface != null) {
            cVar.z(surface, width, height);
        }
    }

    public void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3032, new Class[0], Void.TYPE).isSupported) {
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.I2(false);
                }
                io.reactivex.disposables.b bVar = this.O;
                if (bVar != null && !bVar.isDisposed()) {
                    this.O.dispose();
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.I();
            }
            I0();
            com.leedarson.smartcamera.sdk.a aVar = this.s;
            if (aVar != null) {
                aVar.D0();
                this.s.unRegisterTutkListener(this.I);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0029, code lost:
        r0 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void g0(com.leedarson.view.IpcWebrtcSurfaceView r10, boolean r11) {
        /*
            r9 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r10
            java.lang.Byte r3 = new java.lang.Byte
            r3.<init>(r11)
            r8 = 1
            r1[r8] = r3
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.view.IpcWebrtcSurfaceView> r0 = com.leedarson.view.IpcWebrtcSurfaceView.class
            r6[r2] = r0
            java.lang.Class r0 = java.lang.Boolean.TYPE
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3033(0xbd9, float:4.25E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0029
            return
        L_0x0029:
            r0 = r9
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.x
            if (r1 == 0) goto L_0x003b
            boolean r2 = r0.H
            if (r2 == 0) goto L_0x0038
            java.lang.String r1 = "支持预连接，对管道不做任何处理..... preConnect destroy: "
            r0.B0(r1)
            goto L_0x003b
        L_0x0038:
            r1.I2(r8)
        L_0x003b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.e6.g0(com.leedarson.view.IpcWebrtcSurfaceView, boolean):void");
    }

    public void b0(SurfaceViewRenderer renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 3034, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            if (this.H) {
                if (renderer != null) {
                    this.x.O2(renderer);
                }
                this.x.C0(f0.q.IDLE);
            }
        }
    }

    public void I0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3036, new Class[0], Void.TYPE).isSupported) {
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.V2(this.j, this.l);
                    return;
                }
                return;
            }
            try {
                com.leedarson.smartcamera.codec.c cVar = this.f;
                if (cVar != null) {
                    cVar.U();
                }
                com.leedarson.smartcamera.sdk.a aVar = this.s;
                if (aVar != null) {
                    aVar.r1(this.j, this.l);
                }
            } catch (Exception e2) {
            }
        }
    }

    public void U0(boolean mute) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3037, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    if (mute) {
                        z2 = false;
                    }
                    f0Var.z0(z2);
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.y(mute);
            }
        }
    }

    public void n0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3038, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.s = com.leedarson.manager.a.i().m(deviceId);
            this.n = deviceId;
            B0("initChannel:" + this.s);
        }
    }

    public void F0(WeekCalendar weekCalendar) {
        if (!PatchProxy.proxy(new Object[]{weekCalendar}, this, changeQuickRedirect, false, 3039, new Class[]{WeekCalendar.class}, Void.TYPE).isSupported) {
            if (m() != null) {
                ((f6) m()).D();
                ((f6) m()).r();
            }
            I0();
            this.u = true;
            if (weekCalendar != null && weekCalendar.getMarkDates() != null) {
                weekCalendar.getMarkDates().clear();
                weekCalendar.y();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0150  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void i0(com.leedarson.view.WeekCalendar r40, int r41) {
        /*
            r39 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r40
            java.lang.Integer r2 = new java.lang.Integer
            r9 = r41
            r2.<init>(r9)
            r10 = 1
            r1[r10] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.view.WeekCalendar> r0 = com.leedarson.view.WeekCalendar.class
            r6[r8] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r10] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3040(0xbe0, float:4.26E-42)
            r2 = r39
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002c
            return
        L_0x002c:
            r14 = r39
            r16 = r41
            r18 = r40
            if (r18 != 0) goto L_0x0035
            return
        L_0x0035:
            java.util.List r15 = r18.getCurrentWeekDatas()
            java.lang.Object r0 = r15.get(r8)
            r8 = r0
            com.leedarson.bean.CalendarData r8 = (com.leedarson.bean.CalendarData) r8
            int r0 = r15.size()
            int r0 = r0 - r10
            java.lang.Object r0 = r15.get(r0)
            r19 = r0
            com.leedarson.bean.CalendarData r19 = (com.leedarson.bean.CalendarData) r19
            java.lang.String r12 = com.leedarson.utils.e.e(r8)
            java.lang.String r0 = com.leedarson.utils.e.e(r19)
            java.lang.String r13 = com.leedarson.utils.e.e(r19)
            com.leedarson.bean.CalendarData r1 = r18.getToday()
            java.lang.String r11 = com.leedarson.utils.e.e(r1)
            int r9 = com.leedarson.utils.e.a(r13, r11)
            if (r9 != r10) goto L_0x006a
            r0 = r11
            r10 = r0
            goto L_0x006b
        L_0x006a:
            r10 = r0
        L_0x006b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r12)
            java.lang.String r1 = " 00:00:00"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "yyyy-MM-dd HH:mm:ss"
            long r6 = com.leedarson.utils.e.b(r0, r1)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r10)
            java.lang.String r2 = " 23:59:59"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            long r4 = com.leedarson.utils.e.b(r0, r1)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "startDay = "
            r0.append(r1)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "getHasVideoDays"
            com.leedarson.log.f.b(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "endDay = "
            r0.append(r2)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            com.leedarson.log.f.b(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "start = "
            r0.append(r2)
            r0.append(r12)
            java.lang.String r0 = r0.toString()
            com.leedarson.log.f.b(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "end = "
            r0.append(r2)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            com.leedarson.log.f.b(r1, r0)
            com.leedarson.newui.e6$n r26 = new com.leedarson.newui.e6$n
            r1 = r26
            r2 = r14
            r3 = r15
            r34 = r4
            r4 = r6
            r36 = r6
            r6 = r34
            r1.<init>(r3, r4, r6)
            com.leedarson.newui.repoter.j r0 = com.leedarson.newui.repoter.j.b()     // Catch:{ Exception -> 0x011c }
            com.leedarson.base.application.BaseApplication r1 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x011c }
            java.lang.String r2 = r14.n     // Catch:{ Exception -> 0x011c }
            boolean r3 = r14.w     // Catch:{ Exception -> 0x011c }
            r4 = r9
            r9 = r0
            r5 = r10
            r10 = r1
            r1 = r11
            r11 = r2
            r2 = r12
            r6 = r13
            r12 = r36
            r7 = r14
            r38 = r15
            r14 = r34
            r17 = r3
            r9.j(r10, r11, r12, r14, r16, r17)     // Catch:{ Exception -> 0x011a }
            goto L_0x0128
        L_0x011a:
            r0 = move-exception
            goto L_0x0125
        L_0x011c:
            r0 = move-exception
            r4 = r9
            r5 = r10
            r1 = r11
            r2 = r12
            r6 = r13
            r7 = r14
            r38 = r15
        L_0x0125:
            r0.printStackTrace()
        L_0x0128:
            boolean r0 = r7.w
            if (r0 == 0) goto L_0x0150
            com.leedarson.smartcamera.kvswebrtc.f0 r0 = r7.x
            if (r0 == 0) goto L_0x0161
            boolean r3 = r7.G
            if (r3 == 0) goto L_0x0144
            java.lang.Object r0 = r7.m()
            if (r0 == 0) goto L_0x0143
            java.lang.Object r0 = r7.m()
            com.leedarson.newui.f6 r0 = (com.leedarson.newui.f6) r0
            r0.L()
        L_0x0143:
            return
        L_0x0144:
            r20 = r0
            r21 = r36
            r23 = r34
            r25 = r16
            r20.j1(r21, r23, r25, r26)
            goto L_0x0161
        L_0x0150:
            com.leedarson.smartcamera.sdk.a r0 = r7.s
            if (r0 == 0) goto L_0x0161
            r27 = r0
            r28 = r36
            r30 = r34
            r32 = r16
            r33 = r26
            r27.P0(r28, r30, r32, r33)
        L_0x0161:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.e6.i0(com.leedarson.view.WeekCalendar, int):void");
    }

    /* compiled from: SDCardPlayPresenter */
    public class n implements com.leedarson.smartcamera.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ List a;
        final /* synthetic */ long b;
        final /* synthetic */ long c;

        n(List list, long j, long j2) {
            this.a = list;
            this.b = j;
            this.c = j2;
        }

        public void onSuccess(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3124, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDCardPlayPresenter", "getHasVideoDays onSuccess:" + data.length + " hex:" + ByteUtil.getHexBinString(data));
                List<String> hasDateList = new ArrayList<>();
                int i = 0;
                while (i < data.length) {
                    if (data[i] == 1) {
                        int d2 = i / 24;
                        Log.d("SDCardPlayPresenter", "getHasVideoDays ===: " + ((CalendarData) this.a.get(d2)).toString());
                        hasDateList.add(com.leedarson.utils.e.e((CalendarData) this.a.get(d2)));
                        i = ((d2 + 1) * 24) + -1;
                    }
                    i++;
                }
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).m(hasDateList);
                }
                com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                String r = e6.this.n;
                b2.d(r, "获取是否有回放成功：" + ByteUtil.getHexBinString(data));
            }
        }
    }

    public void j0(String str, Calendar calendar) {
        if (!PatchProxy.proxy(new Object[]{str, calendar}, this, changeQuickRedirect, false, 3041, new Class[]{String.class, Calendar.class}, Void.TYPE).isSupported) {
            Calendar calendar2 = calendar;
            String deviceId = str;
            if (calendar2 != null) {
                this.p = calendar2;
                com.ldf.calendar.model.a seedDate = calendar2.getSeedDate();
                Locale locale = Locale.US;
                String startDay = String.format(locale, "%s-%s-%s", new Object[]{Integer.valueOf(seedDate.year), Integer.valueOf(seedDate.month), "1"});
                String endDay = String.format(locale, "%s-%s-%s", new Object[]{Integer.valueOf(seedDate.year), Integer.valueOf(seedDate.month), "31"});
                B0("getHasVideoDaysForMonth:" + seedDate.year + "=" + seedDate.month + "=" + seedDate.day);
                StringBuilder sb = new StringBuilder();
                sb.append(startDay);
                sb.append(" 00:00:00");
                long start = com.leedarson.utils.e.b(sb.toString(), "yyyy-MM-dd HH:mm:ss");
                long end = com.leedarson.utils.e.b(endDay + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                o oVar = new o(seedDate, deviceId, start, end);
                try {
                    com.leedarson.newui.repoter.j.b().j(BaseApplication.b(), deviceId, start, end, this.l, this.w);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (this.w) {
                    f0 f0Var = this.x;
                    if (f0Var == null) {
                        return;
                    }
                    if (!this.G) {
                        f0Var.j1(start, end, this.l, oVar);
                    } else if (m() != null) {
                        ((f6) m()).L();
                    }
                } else {
                    com.leedarson.smartcamera.sdk.a aVar = this.s;
                    if (aVar != null) {
                        aVar.P0(start, end, this.l, oVar);
                    }
                }
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class o implements com.leedarson.smartcamera.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.ldf.calendar.model.a a;
        final /* synthetic */ String b;
        final /* synthetic */ long c;
        final /* synthetic */ long d;

        o(com.ldf.calendar.model.a aVar, String str, long j, long j2) {
            this.a = aVar;
            this.b = str;
            this.c = j;
            this.d = j2;
        }

        public void onSuccess(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3126, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                e6.this.B0("getHasVideoDaysForMonth onSuccess:" + data.length + " hex:" + ByteUtil.getHexBinString(data));
                HashMap<String, String> markData = new HashMap<>();
                int i = 0;
                while (i < data.length) {
                    if (data[i] == 1) {
                        int d2 = i / 24;
                        markData.put(String.format(Locale.US, "%s-%s-%s", new Object[]{Integer.valueOf(this.a.year), Integer.valueOf(this.a.month), Integer.valueOf(d2 + 1)}), "0");
                        i = ((d2 + 1) * 24) - 1;
                    }
                    i++;
                }
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).k(markData);
                }
                com.leedarson.newui.repoter.j.b().d(this.b, "getHasVideoDaysForMonth 获取是否有回放成功：" + ByteUtil.getHexBinString(data));
            }
        }
    }

    public void L0(String curStartTime, String curEndTime) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{curStartTime, curEndTime}, this, changeQuickRedirect, false, 3042, clsArr, Void.TYPE).isSupported) {
            this.J = " " + curStartTime;
            this.K = " " + curEndTime;
        }
    }

    public void k0(String str, int i2, boolean z2) {
        if (!PatchProxy.proxy(new Object[]{str, new Integer(i2), new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3043, new Class[]{String.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            int eventType = i2;
            String theDayOfSelected = str;
            boolean needListLoading = z2;
            com.leedarson.log.f.b("SDCardPlayPresenter", "getSDVideos_needListLoading: " + needListLoading);
            if (m() != null) {
                if (needListLoading) {
                    ((f6) m()).D();
                }
                ((f6) m()).r();
            }
            I0();
            this.u = true;
            long startTime = com.leedarson.utils.e.b(theDayOfSelected + this.J, "yyyy-MM-dd HH:mm:ss");
            long endTime = com.leedarson.utils.e.b(theDayOfSelected + this.K, "yyyy-MM-dd HH:mm:ss");
            com.leedarson.log.f.a("getSDVideos mCurStartTime:" + this.J + " mCurEndTime:" + this.K);
            com.leedarson.smartcamera.listener.b getRecordListRespListener = new p(theDayOfSelected);
            if (BaseApplication.b() != null) {
                com.leedarson.newui.repoter.j.b().i(BaseApplication.b(), this.n, startTime, endTime, eventType, this.w);
            }
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var == null) {
                    return;
                }
                if (!this.G) {
                    f0Var.i1(startTime, endTime, eventType, getRecordListRespListener);
                } else if (m() != null) {
                    ((f6) m()).L();
                }
            } else {
                com.leedarson.smartcamera.sdk.a aVar = this.s;
                if (aVar != null) {
                    aVar.O0(startTime, endTime, eventType, getRecordListRespListener);
                }
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class p implements com.leedarson.smartcamera.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        p(String str) {
            this.a = str;
        }

        public void onSuccess(List<Long> recordTimestamps) {
            if (!PatchProxy.proxy(new Object[]{recordTimestamps}, this, changeQuickRedirect, false, 3128, new Class[]{List.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("getSDRecordList onSuccess:" + recordTimestamps.size());
                com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                String r = e6.this.n;
                b2.c(r, "获取当天数据 day=" + this.a + "   个数=" + recordTimestamps.size());
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).h();
                    Collections.reverse(recordTimestamps);
                    ((f6) e6.this.m()).K(recordTimestamps);
                }
            }
        }
    }

    public void G0(long timeStamp, com.leedarson.smartcamera.listener.g listener) {
        f0 f0Var;
        Object[] objArr = {new Long(timeStamp), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3044, new Class[]{Long.TYPE, com.leedarson.smartcamera.listener.g.class}, Void.TYPE).isSupported) {
            if (this.w && (f0Var = this.x) != null) {
                f0Var.P2(timeStamp, listener);
            }
        }
    }

    public void E0(int eventType, long timeStamp, Surface surface) {
        com.leedarson.smartcamera.codec.c cVar;
        Object[] objArr = {new Integer(eventType), new Long(timeStamp), surface};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3045, new Class[]{Integer.TYPE, Long.TYPE, Surface.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.a("playSDRecord_eventType: " + eventType + " timeStamp: " + timeStamp);
            this.k = 0;
            com.leedarson.utils.j.a = false;
            if (m() != null) {
                ((f6) m()).z();
            }
            try {
                com.leedarson.newui.repoter.j.b().k(BaseApplication.b(), this.n, timeStamp, eventType, this.w);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.f.a("playSDRecord_isWebRTC: " + this.w);
            if (!this.w) {
                I0();
            }
            this.t = false;
            this.l = eventType;
            if (this.w) {
                this.j = timeStamp;
                this.y = 0;
                this.z = 0;
                this.u = false;
                if (l() != null) {
                    ((SDCardPlayActivity) l()).Z5 = false;
                    ((SDCardPlayActivity) l()).X5 = 0;
                    ((SDCardPlayActivity) l()).Y5 = 0;
                }
                this.D = false;
                this.E = false;
                this.F = 0;
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.k3();
                    this.x.S2(timeStamp, eventType);
                }
            } else if (this.s != null && (cVar = this.f) != null) {
                cVar.H(surface, new q(timeStamp, eventType));
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class q implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ int b;

        q(long j, int i) {
            this.a = j;
            this.b = i;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3130, new Class[0], Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).q();
                }
                e6.this.f.J();
                e6.this.s.q1(e6.this.j, this.a, this.b);
                long unused = e6.this.j = this.a;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3046(0xbe6, float:4.268E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.q
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.q = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.e6.c1():void");
    }

    public void X0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3047, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayPresenter", "startRecord deviceId: " + deviceId);
            if (this.g == null) {
                this.g = new SimpleDateFormat("yyyyMMddHHmmss");
            }
            String str = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (deviceId + "_" + this.g.format(new Date()) + ".mp4");
            this.h = str;
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var == null) {
                    return;
                }
                if (f0Var.p3(str)) {
                    Y0();
                } else if (m() != null) {
                    ((f6) m()).j();
                }
            } else {
                com.leedarson.smartcamera.codec.c cVar = this.f;
                if (cVar != null && cVar != null) {
                    cVar.S(str, new r());
                }
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class r implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3131, new Class[0], Void.TYPE).isSupported) {
                e6.u(e6.this);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3132, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).j();
                }
            }
        }
    }

    private void Y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3048, new Class[0], Void.TYPE).isSupported) {
            c1();
            Timer timer = new Timer();
            this.q = timer;
            this.r = 0;
            timer.schedule(new s(), 10, 1000);
            if (m() != null) {
                ((f6) m()).d();
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class s extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3133, new Class[0], Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    e6.w(e6.this);
                    ((f6) e6.this.m()).e(e6.this.r);
                    com.leedarson.smartcamera.utils.e.e("", "startRecord:" + e6.this.r);
                }
            }
        }
    }

    public void a1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3049, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayPresenter", "Record stopRecord: " + this.w);
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.w3();
                    b1();
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.X(new a());
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class a implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3087, new Class[0], Void.TYPE).isSupported) {
                e6.x(e6.this);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3088, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).showToast(R$string.player_videotape_error);
                }
            }
        }
    }

    private void b1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3050, new Class[0], Void.TYPE).isSupported) {
            try {
                if (m() != null) {
                    ((f6) m()).showToast(R$string.player_videotape_sucess);
                    ((f6) m()).c();
                } else {
                    org.greenrobot.eventbus.c.c().l(new ShowToastEvent(R$string.player_videotape_sucess));
                }
                c1();
                BaseApplication.b().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.h))));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void V0(String deviceId, IpcWebrtcSurfaceView ipcWebrtcSurfaceView) {
        if (!PatchProxy.proxy(new Object[]{deviceId, ipcWebrtcSurfaceView}, this, changeQuickRedirect, false, 3051, new Class[]{String.class, IpcWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            if (this.g == null) {
                this.g = new SimpleDateFormat("yyyyMMddHHmmss");
            }
            String str = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (deviceId + "_" + this.g.format(new Date()) + ".jpg");
            this.i = str;
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.B0(str, new b());
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.M(str, new c());
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class b implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3089, new Class[0], Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).f(e6.this.i);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3090, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class c implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3091, new Class[0], Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).f(e6.this.i);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3092, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    public void H0(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 3052, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            B0("resumeRecord: " + this.u);
            if (this.u) {
                E0(this.l, this.j, surface);
                if (m() != null) {
                    ((f6) m()).F(this.j);
                }
            } else if (!this.w) {
                com.leedarson.smartcamera.codec.c cVar = this.f;
                if (cVar != null) {
                    cVar.J();
                }
                com.leedarson.smartcamera.sdk.a aVar = this.s;
                if (aVar != null) {
                    aVar.s1(this.j, this.l);
                }
            } else if (this.x != null) {
                com.leedarson.newui.view.radar.g.e("sdRecordResume");
                this.x.T2(this.j, this.l);
            }
        }
    }

    public void D0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3053, new Class[0], Void.TYPE).isSupported) {
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.R2(this.j, this.l);
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.sdk.a aVar = this.s;
            if (aVar != null) {
                aVar.o1(this.j, this.l);
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.G();
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class d implements com.leedarson.smartcamera.listener.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void c(int status) {
            Object[] objArr = {new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3093, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                e6 e6Var = e6.this;
                e6Var.B0("getThumbnai---》 connectStatusChange:" + status);
            }
        }

        public void a(long time, String path) {
            Object[] objArr = {new Long(time), path};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3094, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
                e6 e6Var = e6.this;
                e6Var.B0("getThumbnai---》  time=" + time + "   path=" + path);
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).n(time);
                }
            }
        }

        /* compiled from: SDCardPlayPresenter */
        public class a implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ long c;
            final /* synthetic */ byte[] d;

            a(long j, byte[] bArr) {
                this.c = j;
                this.d = bArr;
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3097, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                if (e6.this.l() != null) {
                    String str = ((SDCardPlayActivity) e6.this.l()).F5;
                    String str2 = ((SDCardPlayActivity) e6.this.l()).y5;
                    boolean b = com.leedarson.smartcamera.utils.d.b(str, str2, this.c + "");
                    String str3 = ((SDCardPlayActivity) e6.this.l()).F5;
                    String str4 = ((SDCardPlayActivity) e6.this.l()).y5;
                    com.leedarson.smartcamera.utils.d.h(str3, str4, this.c + "", this.d);
                }
                Message msg = Message.obtain();
                t unused = e6.this.C;
                msg.what = 1;
                msg.obj = Long.valueOf(this.c);
                e6.this.C.sendMessage(msg);
                return null;
            }
        }

        public void b(long time, byte[] imgBytes) {
            Object[] objArr = {new Long(time), imgBytes};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3095, new Class[]{Long.TYPE, byte[].class}, Void.TYPE).isSupported) {
                e6 e6Var = e6.this;
                e6Var.B0("getThumbnai---》   onSuccess   time=" + time + "   imgBytes.size=" + imgBytes.length);
                e6.this.B.submit(new a(time, imgBytes));
            }
        }
    }

    public void m0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3054, new Class[0], Void.TYPE).isSupported) {
            File cacheFile = new File(com.leedarson.smartcamera.utils.d.g(BaseApplication.b()));
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
        }
    }

    public void d0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3055, new Class[0], Void.TYPE).isSupported) {
            m0();
            File cacheFile = new File(com.leedarson.smartcamera.utils.d.g(BaseApplication.b()));
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            com.leedarson.smartcamera.sdk.a aVar = this.s;
            if (aVar != null) {
                aVar.I1(this.L);
                this.s.y0();
            }
        }
    }

    public void l0(String eventStr, List<Long> recordTimestamps) {
        Class[] clsArr = {String.class, List.class};
        if (!PatchProxy.proxy(new Object[]{eventStr, recordTimestamps}, this, changeQuickRedirect, false, 3056, clsArr, Void.TYPE).isSupported) {
            try {
                List<Long> noCacheTimes = new ArrayList<>();
                B0(" getThumbnai---》开始获取缩略图： eventStr=" + eventStr + "   recordTimestamps.size=" + recordTimestamps.size());
                for (int i2 = 0; i2 < recordTimestamps.size(); i2++) {
                    long time = recordTimestamps.get(i2).longValue() / 1000;
                    if (this.w) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("getThumbnai---》 target=");
                        sb.append(time);
                        sb.append("      imageCacheExits=");
                        String str = this.n;
                        sb.append(com.leedarson.smartcamera.utils.d.e(eventStr, str, time + ""));
                        B0(sb.toString());
                        String str2 = this.n;
                        if (!com.leedarson.smartcamera.utils.d.e(eventStr, str2, time + "")) {
                            noCacheTimes.add(Long.valueOf(time));
                            B0("getThumbnai---》发现没有缓存 time=" + time);
                        }
                    } else {
                        String G0 = this.s.G0();
                        if (!com.leedarson.smartcamera.utils.d.e(eventStr, G0, time + "")) {
                            noCacheTimes.add(Long.valueOf(time));
                        }
                    }
                }
                if (this.w != 0) {
                    f0 f0Var = this.x;
                    if (f0Var != null) {
                        f0Var.n1(noCacheTimes, this.L);
                        return;
                    }
                    return;
                }
                B0("getThumbnais:" + this.s.a1() + " getPicSize:" + recordTimestamps.size());
                if (this.s != null && noCacheTimes.size() > 0) {
                    this.s.T0(eventStr, noCacheTimes, this.L);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void e0(int position, int eventType, long timeStamp) {
        Object[] objArr = {new Integer(position), new Integer(eventType), new Long(timeStamp)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3057, new Class[]{cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            List<Long> delRecords = new ArrayList<>();
            delRecords.add(Long.valueOf(timeStamp));
            if (m() != null) {
                ((f6) m()).b();
            }
            com.leedarson.smartcamera.listener.a deleteRecordRespListener = new e(position);
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.N0(0, eventType, delRecords, deleteRecordRespListener);
                }
            } else if (this.s != null) {
                I0();
                this.s.B0(0, eventType, delRecords, deleteRecordRespListener);
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class e implements com.leedarson.smartcamera.listener.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        e(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3098, new Class[0], Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).a();
                    ((f6) e6.this.m()).G(this.a);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3099, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).a();
                    ((f6) e6.this.m()).showToast(R$string.delete_failed);
                }
            }
        }
    }

    public void p0(int eventType, long timeStamp, Surface surface) {
        this.l = eventType;
        this.j = timeStamp;
        this.m = surface;
    }

    public void K0(Surface _surface, int progress) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{_surface, new Integer(progress)}, this, changeQuickRedirect, false, 3058, new Class[]{Surface.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.w) {
                boolean needSleep = false;
                if (!((SDCardPlayActivity) l()).h2() && (f0Var = this.x) != null) {
                    f0Var.S2(this.j, this.l);
                    needSleep = true;
                }
                int i2 = this.M;
                if (i2 >= 255 || i2 < 0) {
                    this.M = 0;
                }
                int i3 = this.M + 1;
                this.M = i3;
                f0 f0Var2 = this.x;
                if (f0Var2 == null) {
                    return;
                }
                if (this.A) {
                    if (needSleep) {
                        SystemClock.sleep(300);
                    }
                    this.x.U2(this.y + ((long) progress), this.l, this.M);
                    return;
                }
                f0Var2.U2(this.j + ((long) progress), this.l, i3);
                return;
            }
            this.t = false;
            if (this.f == null) {
                return;
            }
            if (((SDCardPlayActivity) l()).h2()) {
                J0(progress);
            } else {
                this.f.H(_surface, new f(progress));
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class f implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        f(int i) {
            this.a = i;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3100, new Class[0], Void.TYPE).isSupported) {
                e6.this.f.J();
                e6.B(e6.this, this.a);
            }
        }
    }

    private void J0(int progress) {
        if (!PatchProxy.proxy(new Object[]{new Integer(progress)}, this, changeQuickRedirect, false, 3059, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int i2 = this.M;
            if (i2 >= 255 || i2 < 0) {
                this.M = 0;
            }
            int i3 = this.M + 1;
            this.M = i3;
            com.leedarson.smartcamera.sdk.a aVar = this.s;
            if (aVar != null) {
                aVar.t1(this.j + ((long) progress), this.l, i3);
            }
            this.f.J();
        }
    }

    public void S0(boolean isWebRTC) {
        this.w = isWebRTC;
    }

    public void O0(String liveType) {
        this.o = liveType;
    }

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3060, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayPresenter", "isPlayStart: " + this.E + " firstFrameRendered: " + this.D);
            if (this.E && this.D) {
                this.u = false;
                if (m() != null) {
                    ((f6) m()).g();
                }
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.P0();
                }
                com.leedarson.newui.repoter.j.b().e(this.n);
            }
        }
    }

    public void r0(String str, String str2, KVSParamBean kVSParamBean, WeekCalendar weekCalendar, int i2, IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, kVSParamBean, weekCalendar, new Integer(i2), ipcSDWebrtcSurfaceView}, this, changeQuickRedirect, false, 3061, new Class[]{cls, cls, KVSParamBean.class, WeekCalendar.class, Integer.TYPE, IpcSDWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            String supportIpv6 = str2;
            IpcSDWebrtcSurfaceView renderer = ipcSDWebrtcSurfaceView;
            WeekCalendar weekCalendar2 = weekCalendar;
            String deviceId = str;
            KVSParamBean param = kVSParamBean;
            int eventType = i2;
            this.D = false;
            this.E = false;
            com.leedarson.log.f.b("SDCardPlayPresenter", "initWebRTCChannel isSupportPreCon: " + this.H);
            if (this.H) {
                f0 n2 = com.leedarson.manager.a.i().n(deviceId, this.o, supportIpv6, param, f0.r.PRE_LINK);
                this.x = n2;
                n2.e3(renderer);
                Q0(renderer);
                this.x.z0(false);
                if (this.H) {
                    f0 f0Var = this.x;
                    if (f0Var != null) {
                        f0Var.C0(f0.q.SD);
                        String str3 = supportIpv6;
                    } else {
                        String str4 = supportIpv6;
                    }
                } else {
                    String str5 = supportIpv6;
                }
            } else {
                String deviceIdTAG = deviceId + "-SD";
                this.x = com.leedarson.manager.a.i().j(deviceIdTAG);
                String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                if (Constans.IPC_LIVE_TYPE_LDS.equals(this.o)) {
                    this.x = new f0(deviceId, userId, supportIpv6, f0.r.SDCARD);
                    String str6 = supportIpv6;
                    String str7 = userId;
                } else if (!Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.o)) {
                    if (param != null) {
                        f0 f0Var2 = this.x;
                        if (f0Var2 == null) {
                            this.x = new f0(param.accessKeyId, param.secretAccessKey, param.sessionToken, param.channelArn, param.region, userId, false);
                        } else {
                            String userId2 = userId;
                            if (!param.accessKeyId.equals(f0Var2.S0())) {
                                this.x = new f0(param.accessKeyId, param.secretAccessKey, param.sessionToken, param.channelArn, param.region, userId2, false);
                            }
                        }
                    } else {
                        return;
                    }
                } else if (param != null) {
                    String str8 = supportIpv6;
                    String str9 = supportIpv6;
                    f0 f0Var3 = r1;
                    f0 f0Var4 = new f0(this.o, param, userId, deviceId, str8, f0.r.SDCARD);
                    this.x = f0Var3;
                    String str10 = userId;
                } else {
                    return;
                }
                if (!(IpcServiceImpl.p(deviceId) == null || IpcServiceImpl.p(deviceId).props == null)) {
                    this.x.j3(IpcServiceImpl.p(deviceId).props.getVideoCodesArr());
                    this.x.c3(IpcServiceImpl.p(deviceId).props.enableSdes);
                }
                com.leedarson.manager.a.i().a(deviceIdTAG, this.x);
            }
            this.x.setOnWebRTCSDPlayListener(new g());
            if (this.x.r1()) {
                i0(weekCalendar2, eventType);
            } else {
                this.x.H0((Context) l(), new h(deviceId, renderer, weekCalendar2, eventType));
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class g implements com.leedarson.smartcamera.listener.j {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void c(long timeStamp, long startTime, int flag) {
            Object[] objArr = {new Long(timeStamp), new Long(startTime), new Integer(flag)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Long.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3101, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("updateTime:" + timeStamp + "--" + startTime + "--" + flag + "--" + e6.this.j);
                SDCardPlayActivity activity = (SDCardPlayActivity) e6.this.l();
                if (!e6.this.x.v1()) {
                    if (flag == 1) {
                        boolean unused = e6.this.A = true;
                        if ((startTime + "").length() == 10) {
                            startTime *= 1000;
                        }
                        if (startTime != e6.this.j) {
                            return;
                        }
                        if (e6.this.y == 0) {
                            long unused2 = e6.this.y = timeStamp;
                            if (activity != null) {
                                activity.Z5 = false;
                                activity.X5 = 0;
                                activity.Y5 = 0;
                            }
                        }
                    } else {
                        boolean unused3 = e6.this.A = false;
                    }
                    if (activity != null) {
                        activity.runOnUiThread(new a(timeStamp, activity));
                    }
                }
            }
        }

        /* compiled from: SDCardPlayPresenter */
        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ long c;
            final /* synthetic */ SDCardPlayActivity d;

            a(long j, SDCardPlayActivity sDCardPlayActivity) {
                this.c = j;
                this.d = sDCardPlayActivity;
            }

            public void run() {
                long curPlaySec;
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3107, new Class[0], Void.TYPE).isSupported) {
                    if (e6.this.A) {
                        curPlaySec = this.c - e6.this.y;
                    } else {
                        curPlaySec = this.c - e6.this.j;
                    }
                    if (((SDCardPlayActivity) e6.this.l()).Z5) {
                        SDCardPlayActivity sDCardPlayActivity = this.d;
                        int i = sDCardPlayActivity.Y5;
                        int i2 = sDCardPlayActivity.X5;
                        if (i - i2 > 0 && curPlaySec >= ((long) i)) {
                            sDCardPlayActivity.Z5 = false;
                            sDCardPlayActivity.X5 = 0;
                            sDCardPlayActivity.Y5 = 0;
                        } else if (i - i2 < 0 && curPlaySec >= ((long) i) && curPlaySec < ((long) i2)) {
                            sDCardPlayActivity.Z5 = false;
                            sDCardPlayActivity.X5 = 0;
                            sDCardPlayActivity.Y5 = 0;
                        } else {
                            return;
                        }
                    }
                    if (e6.this.m() != null && !this.d.W5 && !e6.this.u) {
                        ((f6) e6.this.m()).w((int) curPlaySec);
                        if (e6.this.t && e6.this.F - this.d.a6.getProgress() < 3000) {
                            e6.I(e6.this);
                        }
                    }
                }
            }
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3102, new Class[0], Void.TYPE).isSupported) {
                if (e6.this.H) {
                    e6.O(e6.this);
                }
            }
        }

        public void b(int time) {
            Object[] objArr = {new Integer(time)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3103, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("GetSDTotalTime:" + time);
                boolean unused = e6.this.E = true;
                int unused2 = e6.this.F = time * 1000;
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).o(e6.this.F);
                }
                com.leedarson.newui.view.radar.g.e("GetSDTotalTime");
                e6.L(e6.this);
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3104, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.a("reciveStreamEnd");
                e6.I(e6.this);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3105, new Class[0], Void.TYPE).isSupported) {
                e6.this.B0("changeToPause");
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3106, new Class[0], Void.TYPE).isSupported) {
                e6.this.B0("changeToResume");
                com.leedarson.newui.view.radar.g.e("收到播放/暂停响应");
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class h implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ IpcSDWebrtcSurfaceView b;
        final /* synthetic */ WeekCalendar c;
        final /* synthetic */ int d;

        h(String str, IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView, WeekCalendar weekCalendar, int i) {
            this.a = str;
            this.b = ipcSDWebrtcSurfaceView;
            this.c = weekCalendar;
            this.d = i;
        }

        /* compiled from: SDCardPlayPresenter */
        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3111, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                    h hVar = h.this;
                    b.g(hVar.a, "onAddStream", e6.this.w);
                    com.leedarson.log.f.a("onAddStream");
                    if (e6.this.l() != null) {
                        ((SDCardPlayActivity) e6.this.l()).runOnUiThread(new C0112a());
                    }
                }
            }

            /* renamed from: com.leedarson.newui.e6$h$a$a  reason: collision with other inner class name */
            /* compiled from: SDCardPlayPresenter */
            public class C0112a implements Runnable {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0112a() {
                }

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3115, new Class[0], Void.TYPE).isSupported) {
                        e6.this.x.z0(false);
                        e6.this.x.e3(h.this.b);
                        h hVar = h.this;
                        e6.M(e6.this, hVar.b);
                    }
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 3112, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                    String str = h.this.a;
                    b.g(str, "onDataChannelStateChange:" + state, e6.this.w);
                    if (state == DataChannel.State.OPEN) {
                        h hVar = h.this;
                        e6.this.i0(hVar.c, hVar.d);
                    }
                }
            }

            public void c(byte[] bytes) {
            }

            public void onError(String desc) {
                if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 3113, new Class[]{String.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                    String str = h.this.a;
                    b.g(str, "onError:" + desc, e6.this.w);
                }
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 3114, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                    String str = h.this.a;
                    b.g(str, "onIceConnectionChange:" + iceConnectionState, e6.this.w);
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3108, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.j.b().g(this.a, "websocket open", e6.this.w);
                e6.this.x.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3109, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.j.b().g(this.a, "onClose", e6.this.w);
            }
        }

        public void a(Event event) {
        }

        public void onException(Exception e2) {
        }

        public void g(String message) {
        }

        public void c(String message) {
        }

        public void e(String message) {
        }

        public void d(int stateCode) {
            Object[] objArr = {new Integer(stateCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3110, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (stateCode == -50015) {
                    boolean unused = e6.this.G = true;
                    if (e6.this.m() != null) {
                        ((f6) e6.this.m()).L();
                    }
                }
            }
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    private void e1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3062, new Class[0], Void.TYPE).isSupported) {
            try {
                SDCardPlayActivity activity = (SDCardPlayActivity) l();
                this.t = true;
                if (this.F - activity.a6.getProgress() <= 3000) {
                    this.u = true;
                    if (m() != null) {
                        ((f6) m()).u();
                        com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                        String str = this.n;
                        b2.g(str, "播放结束：" + this.j, this.w);
                    }
                    activity.Z5 = false;
                    activity.X5 = 0;
                    activity.Y5 = 0;
                    I0();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void d1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3063, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.N = 0;
            N0(deviceId, false);
            io.reactivex.disposables.b bVar = this.Q;
            if (bVar != null && !bVar.isDisposed()) {
                this.Q.dispose();
            }
            this.Q = this.P.d(deviceId).c(com.leedarson.base.http.observer.l.c()).I(b5.c, a5.c);
        }
    }

    static /* synthetic */ void z0(LDSBaseBean ldsBaseBean) {
    }

    static /* synthetic */ void A0(Throwable throwable) {
    }

    public void N0(String deviceId, boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3064, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.O;
            if (bVar != null && !bVar.isDisposed()) {
                this.O.dispose();
            }
            this.O = this.P.k(deviceId, isSleep).G(new com.leedarson.base.http.observer.j(2, 1500)).c(com.leedarson.base.http.observer.l.c()).I(new e5(this, isSleep, deviceId), new c5(this, isSleep, deviceId));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t0 */
    public /* synthetic */ void u0(boolean isSleep, String deviceId, LDSBaseBean ldsBaseBean) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSleep ? (byte) 1 : 0), deviceId, ldsBaseBean}, this, changeQuickRedirect, false, 3074, new Class[]{Boolean.TYPE, String.class, LDSBaseBean.class}, Void.TYPE).isSupported) {
            if (!ldsBaseBean.checkDataValid() && !isSleep) {
                int i2 = this.N + 1;
                this.N = i2;
                if (i2 < 3) {
                    N0(deviceId, false);
                    return;
                }
                B0("kvs.唤醒发生了异常：" + ldsBaseBean.getErrorDetalInfo());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: v0 */
    public /* synthetic */ void w0(boolean isSleep, String deviceId, Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSleep ? (byte) 1 : 0), deviceId, throwable}, this, changeQuickRedirect, false, 3073, new Class[]{Boolean.TYPE, String.class, Throwable.class}, Void.TYPE).isSupported) {
            if (!isSleep) {
                int i2 = this.N + 1;
                this.N = i2;
                if (i2 < 3) {
                    N0(deviceId, false);
                    return;
                }
                B0("kvs.唤醒发生了异常：" + throwable);
            }
        }
    }

    public void C0(JSONObject data) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3065, new Class[]{JSONObject.class}, Void.TYPE).isSupported && (f0Var = this.x) != null) {
            f0Var.u2(data);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3066, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            io.reactivex.disposables.b bVar = this.Q;
            if (bVar != null && !bVar.isDisposed()) {
                this.Q.dispose();
            }
            ExecutorService executorService = this.B;
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class t extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private t() {
        }

        /* synthetic */ t(e6 x0, k x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3134, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        try {
                            if (e6.this.m() != null) {
                                ((f6) e6.this.m()).n(((Long) msg.obj).longValue());
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    private void Q0(IpcSDWebrtcSurfaceView renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 3067, new Class[]{IpcSDWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            if (this.x.v1()) {
                renderer.setOnGetFrameInfoListener(new d5(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x0 */
    public /* synthetic */ void y0(String str, String str2, String str3) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, str2, str3}, this, changeQuickRedirect, false, 3072, clsArr, Void.TYPE).isSupported) {
            String channelId = str2;
            String str4 = str;
            String frameTimestamp = str3;
            try {
                SDCardPlayActivity activity = (SDCardPlayActivity) l();
                long channelT = (this.j / 1000) & 65535;
                com.leedarson.log.f.a("[hyf]channelId:" + channelId + " frameTimestamp:" + frameTimestamp);
                if (com.alibaba.android.arouter.utils.e.b(channelId)) {
                    return;
                }
                if (!com.alibaba.android.arouter.utils.e.b(frameTimestamp)) {
                    if (((long) Integer.parseInt(channelId)) == channelT) {
                        long timeStamp = Long.parseLong(frameTimestamp);
                        if (this.y == 0 && timeStamp - this.j < ((long) this.F)) {
                            this.y = timeStamp;
                            activity.Z5 = false;
                            activity.X5 = 0;
                            activity.Y5 = 0;
                        }
                        if (!activity.Z5 && this.z > timeStamp) {
                            this.y = timeStamp;
                            activity.Z5 = false;
                            activity.X5 = 0;
                            activity.Y5 = 0;
                        }
                        this.z = timeStamp;
                        long curPlaySec = timeStamp - this.y;
                        if (activity.Z5) {
                            int i2 = activity.Y5;
                            int i3 = activity.X5;
                            if (i2 - i3 > 0) {
                                long j2 = timeStamp;
                                if (curPlaySec >= ((long) i2)) {
                                    activity.Z5 = false;
                                    activity.X5 = 0;
                                    activity.Y5 = 0;
                                }
                            }
                            if (i2 - i3 < 0 && curPlaySec >= ((long) i2) && curPlaySec < ((long) i3)) {
                                activity.Z5 = false;
                                activity.X5 = 0;
                                activity.Y5 = 0;
                            } else {
                                return;
                            }
                        }
                        if (m() != null && !activity.W5 && !this.u) {
                            ((f6) m()).w((int) curPlaySec);
                            if (this.t && this.F - activity.a6.getProgress() < 3000) {
                                e1();
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void P0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3068, new Class[0], Void.TYPE).isSupported) {
            if (this.x != null && l() != null) {
                com.leedarson.utils.j.a = false;
                this.D = false;
                String path = ((SDCardPlayActivity) l()).getFilesDir() + "/tempCache.jpg";
                com.leedarson.log.f.b("SDCardPlayPresenter", "setNoFirstFrame start: " + path);
                this.x.B0(path, new i(path));
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class i implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        i(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3116, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDCardPlayPresenter", "setNoFirstFrame onSuccess: " + e6.this.D);
                e6 e6Var = e6.this;
                if (!e6Var.D && e6Var.m() != null) {
                    ((f6) e6.this.m()).l(this.a);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3117, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.d("SDCardPlayPresenter", "setNoFirstFrame onError: ");
            }
        }
    }

    public void a0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3069, new Class[0], Void.TYPE).isSupported) {
            if (this.f != null && l() != null) {
                com.leedarson.utils.j.a = false;
                String path = ((SDCardPlayActivity) l()).getFilesDir() + "/tempCache.jpg";
                com.leedarson.log.f.b("SDCardPlayPresenter", "captureCacheImage start: " + path);
                this.f.M(path, new j(path));
            }
        }
    }

    /* compiled from: SDCardPlayPresenter */
    public class j implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        j(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3118, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDCardPlayPresenter", "captureCacheImage onSuccess: " + e6.this.D);
                if (e6.this.m() != null) {
                    ((f6) e6.this.m()).l(this.a);
                }
            }
        }

        public void a(int code) {
        }
    }

    private void T0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3070, new Class[0], Void.TYPE).isSupported) {
            try {
                if (m() != null) {
                    ((f6) m()).s();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void h0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3071, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.utils.j.a = true;
            this.D = true;
            com.leedarson.newui.view.radar.g.e("getFristFrame");
            s0();
        }
    }
}
