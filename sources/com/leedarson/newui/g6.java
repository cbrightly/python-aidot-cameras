package com.leedarson.newui;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import androidx.core.app.NotificationCompat;
import com.github.druk.dnssd.DNSSD;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ldf.calendar.view.Calendar;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.bean.CalendarData;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.repos.beans.KvsParmsResponseWrapBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.event.ShowToastEvent;
import com.leedarson.serviceinterface.listener.OnControlRespListener;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import meshsdk.model.json.RoutineRule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import org.webrtc.SurfaceViewRenderer;
import timber.log.a;

/* compiled from: SDEventsPresenter */
public class g6 extends com.leedarson.base.presenters.a<h6, SDEventsFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean A = false;
    /* access modifiers changed from: private */
    public ExecutorService B = Executors.newFixedThreadPool(3, new com.leedarson.base.utils.r("sd-p-pool"));
    /* access modifiers changed from: private */
    public y C = new y(this, (k) null);
    public boolean D = false;
    /* access modifiers changed from: private */
    public boolean E = false;
    /* access modifiers changed from: private */
    public int F;
    /* access modifiers changed from: private */
    public boolean G = false;
    private boolean H;
    private com.leedarson.smartcamera.listener.i I = new k();
    private String J = " 00:00:00";
    private String K = " 23:59:59";
    private com.leedarson.smartcamera.listener.d L = new d();
    com.leedarson.newui.sdthumbnai.b M = new com.leedarson.newui.sdthumbnai.b();
    private int N = 0;
    int O = 0;
    io.reactivex.disposables.b P;
    com.leedarson.newui.repos.n Q = new com.leedarson.newui.repos.n();
    io.reactivex.disposables.b R;
    com.leedarson.newui.repoter.e S = new com.leedarson.newui.repoter.e();
    private final String T = "SDcardBaseInfo";
    private final String U = "SDcardFormatFunc";
    io.reactivex.disposables.b V;
    com.leedarson.newui.repos.r W = new com.leedarson.newui.repos.r();
    /* access modifiers changed from: private */
    public long X = 0;
    private ArrayList<io.reactivex.disposables.b> Y = new ArrayList<>();
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
    public f0 x;
    /* access modifiers changed from: private */
    public long y = 0;
    /* access modifiers changed from: private */
    public long z = 0;

    static /* synthetic */ void B(g6 x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 3390, new Class[]{g6.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.S0(x1);
        }
    }

    static /* synthetic */ void I(g6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3391, new Class[]{g6.class}, Void.TYPE).isSupported) {
            x0.p1();
        }
    }

    static /* synthetic */ void K(g6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3392, new Class[]{g6.class}, Void.TYPE).isSupported) {
            x0.y0();
        }
    }

    static /* synthetic */ void L(g6 x0, IpcSDWebrtcSurfaceView x1) {
        Class[] clsArr = {g6.class, IpcSDWebrtcSurfaceView.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 3393, clsArr, Void.TYPE).isSupported) {
            x0.Z0(x1);
        }
    }

    static /* synthetic */ void O(g6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3386, new Class[]{g6.class}, Void.TYPE).isSupported) {
            x0.c1();
        }
    }

    static /* synthetic */ void Y(g6 x0, long x1, String x2) {
        Object[] objArr = {x0, new Long(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 3387, new Class[]{g6.class, Long.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.n1(x1, x2);
        }
    }

    static /* synthetic */ void u(g6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3388, new Class[]{g6.class}, Void.TYPE).isSupported) {
            x0.h1();
        }
    }

    static /* synthetic */ int w(g6 x0) {
        int i2 = x0.r;
        x0.r = i2 + 1;
        return i2;
    }

    static /* synthetic */ void x(g6 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3389, new Class[]{g6.class}, Void.TYPE).isSupported) {
            x0.k1();
        }
    }

    public void a1(boolean supportPreCon) {
        this.H = supportPreCon;
    }

    /* compiled from: SDEventsPresenter */
    public class k implements com.leedarson.smartcamera.listener.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void e(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3394, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("onStateChange:" + state);
                switch (state) {
                    case -2:
                        com.leedarson.newui.repoter.j.b().g(g6.this.n, "tutk达到最大连接数", g6.this.w);
                        return;
                    case -1:
                    case 3:
                        com.leedarson.newui.repoter.j.b().g(g6.this.n, "tutk连接断开OR获取流失败", g6.this.w);
                        g6.O(g6.this);
                        return;
                    case 0:
                        if (g6.this.m() != null) {
                            ((h6) g6.this.m()).i();
                            com.leedarson.newui.repoter.j.b().g(g6.this.n, "tutk连接中", g6.this.w);
                            return;
                        }
                        return;
                    case 1:
                        com.leedarson.newui.repoter.j.b().g(g6.this.n, "tutk连接成功", g6.this.w);
                        ((h6) g6.this.m()).Y0();
                        return;
                    case 4:
                        com.leedarson.newui.repoter.j.b().f(g6.this.n);
                        return;
                    default:
                        return;
                }
            }
        }

        public void b(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3395, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("onConnectError:" + code);
                com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                String r = g6.this.n;
                b.g(r, "Tutk连接发生错误--> code=" + code, g6.this.w);
            }
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3396, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (g6.this.f != null) {
                    g6.this.f.Z(timestap, data, len, codec);
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3397, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (g6.this.f != null) {
                    g6.this.f.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
            Object[] objArr = {new Long(timestamp)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3398, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                long unused = g6.this.k = timestamp;
            }
        }
    }

    public g6(h6 view, SDEventsFragment activity) {
        super(view, activity);
    }

    public void V0(String deviceId) {
        this.n = deviceId;
    }

    public void w0(String id, String str, String str2, String str3, String str4) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{id, str, str2, str3, str4}, this, changeQuickRedirect, false, 3328, clsArr, Void.TYPE).isSupported) {
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
                this.s.setOnSDRecordPlayListener(new q());
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class q implements com.leedarson.smartcamera.listener.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        public void b(int time) {
            Object[] objArr = {new Integer(time)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3425, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).o(time * 1000);
                }
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3426, new Class[0], Void.TYPE).isSupported) {
                g6.this.J0("reciveStreamEnd");
                boolean unused = g6.this.t = true;
                com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                String r = g6.this.n;
                b.g(r, "回放获取流结束:" + g6.this.j, g6.this.w);
            }
        }

        public void a() {
        }

        public void d() {
        }
    }

    public void J0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3329, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("SDEventsPresenter").a(msg, new Object[0]);
        }
    }

    public void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3330, new Class[0], Void.TYPE).isSupported) {
            this.f = new com.leedarson.smartcamera.codec.c();
            com.leedarson.manager.c.u().A(this.f);
            this.f.u(new r());
        }
    }

    /* compiled from: SDEventsPresenter */
    public class r implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void W0(long currentTime, int decFps, int showFps) {
            Object[] objArr = {new Long(currentTime), new Integer(decFps), new Integer(showFps)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3445, new Class[]{Long.TYPE, cls, cls}, Void.TYPE).isSupported) {
                try {
                    SDEventsFragment fragment = (SDEventsFragment) g6.this.l();
                    if (fragment != null && fragment.X3()) {
                        long curPlaySec = currentTime - g6.this.k;
                        if (fragment.V5) {
                            int i = fragment.U5;
                            int i2 = fragment.T5;
                            if (i - i2 > 0 && curPlaySec >= ((long) i)) {
                                fragment.V5 = false;
                                fragment.T5 = 0;
                                fragment.U5 = 0;
                            } else if (i - i2 < 0 && curPlaySec >= ((long) i) && curPlaySec < ((long) i2)) {
                                fragment.V5 = false;
                                fragment.T5 = 0;
                                fragment.U5 = 0;
                            } else {
                                return;
                            }
                        }
                        if (g6.this.m() != null && !fragment.S5) {
                            ((h6) g6.this.m()).w((int) curPlaySec);
                            if (g6.this.t && decFps == 0 && showFps == 0 && !g6.this.u) {
                                ((h6) g6.this.m()).u();
                                boolean unused = g6.this.u = true;
                                com.leedarson.newui.repoter.j b = com.leedarson.newui.repoter.j.b();
                                String r = g6.this.n;
                                b.g(r, "播放结束：" + g6.this.j, g6.this.w);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3446, new Class[0], Void.TYPE).isSupported) {
                boolean unused = g6.this.u = false;
                g6.Y(g6.this, 0, "播放器启播");
                long unused2 = g6.this.z = 0;
                if (g6.this.m() != null) {
                    com.leedarson.newui.repoter.j.b().e(g6.this.n);
                    ((h6) g6.this.m()).g();
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
    public void f1(android.view.Surface r9) {
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
            r5 = 3331(0xd03, float:4.668E-42)
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.g6.f1(android.view.Surface):void");
    }

    public void i1() {
        com.leedarson.smartcamera.codec.c cVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3332, new Class[0], Void.TYPE).isSupported && (cVar = this.f) != null) {
            cVar.U();
        }
    }

    public void b0(Surface surface, int width, int height) {
        com.leedarson.smartcamera.codec.c cVar;
        Object[] objArr = {surface, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3333, new Class[]{Surface.class, cls, cls}, Void.TYPE).isSupported && (cVar = this.f) != null && surface != null) {
            cVar.z(surface, width, height);
        }
    }

    public void g0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3334, new Class[0], Void.TYPE).isSupported) {
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.I2(false);
                }
                io.reactivex.disposables.b bVar = this.P;
                if (bVar != null && !bVar.isDisposed()) {
                    this.P.dispose();
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.I();
            }
            R0();
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
    public void h0(com.leedarson.view.IpcWebrtcSurfaceView r10, boolean r11) {
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
            r5 = 3335(0xd07, float:4.673E-42)
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
            r0.J0(r1)
            goto L_0x003b
        L_0x0038:
            r1.I2(r8)
        L_0x003b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.g6.h0(com.leedarson.view.IpcWebrtcSurfaceView, boolean):void");
    }

    public void a0(SurfaceViewRenderer renderer) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 3336, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            if (this.H && (f0Var = this.x) != null) {
                if (renderer != null) {
                    f0Var.O2(renderer);
                }
                this.x.C0(f0.q.IDLE);
            }
        }
    }

    public void R0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3338, new Class[0], Void.TYPE).isSupported) {
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

    public void d1(boolean mute) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3339, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
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

    public void t0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3340, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.s = com.leedarson.manager.a.i().m(deviceId);
            this.n = deviceId;
            J0("initChannel:" + this.s);
        }
    }

    public void O0(WeekCalendar weekCalendar) {
        if (!PatchProxy.proxy(new Object[]{weekCalendar}, this, changeQuickRedirect, false, 3341, new Class[]{WeekCalendar.class}, Void.TYPE).isSupported) {
            if (m() != null) {
                ((h6) m()).D();
                ((h6) m()).r();
            }
            R0();
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
    public void l0(com.leedarson.view.WeekCalendar r40, int r41) {
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
            r5 = 3342(0xd0e, float:4.683E-42)
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
            com.leedarson.newui.g6$s r26 = new com.leedarson.newui.g6$s
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
            com.leedarson.newui.h6 r0 = (com.leedarson.newui.h6) r0
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.g6.l0(com.leedarson.view.WeekCalendar, int):void");
    }

    /* compiled from: SDEventsPresenter */
    public class s implements com.leedarson.smartcamera.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ List a;
        final /* synthetic */ long b;
        final /* synthetic */ long c;

        s(List list, long j, long j2) {
            this.a = list;
            this.b = j;
            this.c = j2;
        }

        public void onSuccess(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3447, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDEventsPresenter", "getHasVideoDays onSuccess:" + data.length + " hex:" + ByteUtil.getHexBinString(data));
                List<String> hasDateList = new ArrayList<>();
                int i = 0;
                while (i < data.length) {
                    if (data[i] == 1) {
                        int d2 = i / 24;
                        Log.d("SDEventsPresenter", "getHasVideoDays ===: " + ((CalendarData) this.a.get(d2)).toString());
                        hasDateList.add(com.leedarson.utils.e.e((CalendarData) this.a.get(d2)));
                        i = ((d2 + 1) * 24) + -1;
                    }
                    i++;
                }
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).m(hasDateList);
                }
                com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                String r = g6.this.n;
                b2.d(r, "获取是否有回放成功：" + ByteUtil.getHexBinString(data));
            }
        }
    }

    public void m0(String str, Calendar calendar) {
        if (!PatchProxy.proxy(new Object[]{str, calendar}, this, changeQuickRedirect, false, 3343, new Class[]{String.class, Calendar.class}, Void.TYPE).isSupported) {
            Calendar calendar2 = calendar;
            String deviceId = str;
            if (calendar2 != null) {
                this.p = calendar2;
                com.ldf.calendar.model.a seedDate = calendar2.getSeedDate();
                Locale locale = Locale.US;
                String startDay = String.format(locale, "%s-%s-%s", new Object[]{Integer.valueOf(seedDate.year), Integer.valueOf(seedDate.month), "1"});
                String endDay = String.format(locale, "%s-%s-%s", new Object[]{Integer.valueOf(seedDate.year), Integer.valueOf(seedDate.month), "31"});
                J0("getHasVideoDaysForMonth:" + seedDate.year + "=" + seedDate.month + "=" + seedDate.day);
                StringBuilder sb = new StringBuilder();
                sb.append(startDay);
                sb.append(" 00:00:00");
                long start = com.leedarson.utils.e.b(sb.toString(), "yyyy-MM-dd HH:mm:ss");
                long end = com.leedarson.utils.e.b(endDay + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                t tVar = new t(seedDate, deviceId, start, end);
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
                        f0Var.j1(start, end, this.l, tVar);
                    } else if (m() != null) {
                        ((h6) m()).L();
                    }
                } else {
                    com.leedarson.smartcamera.sdk.a aVar = this.s;
                    if (aVar != null) {
                        aVar.P0(start, end, this.l, tVar);
                    }
                }
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class t implements com.leedarson.smartcamera.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.ldf.calendar.model.a a;
        final /* synthetic */ String b;
        final /* synthetic */ long c;
        final /* synthetic */ long d;

        t(com.ldf.calendar.model.a aVar, String str, long j, long j2) {
            this.a = aVar;
            this.b = str;
            this.c = j;
            this.d = j2;
        }

        public void onSuccess(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3449, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                g6.this.J0("getHasVideoDaysForMonth onSuccess:" + data.length + " hex:" + ByteUtil.getHexBinString(data));
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
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).k(markData);
                }
                com.leedarson.newui.repoter.j.b().d(this.b, "getHasVideoDaysForMonth 获取是否有回放成功：" + ByteUtil.getHexBinString(data));
            }
        }
    }

    public void U0(String curStartTime, String curEndTime) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{curStartTime, curEndTime}, this, changeQuickRedirect, false, 3344, clsArr, Void.TYPE).isSupported) {
            this.J = " " + curStartTime;
            this.K = " " + curEndTime;
        }
    }

    public void p0(String str, int i2, boolean z2) {
        if (!PatchProxy.proxy(new Object[]{str, new Integer(i2), new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3345, new Class[]{String.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            int eventType = i2;
            String theDayOfSelected = str;
            boolean needListLoading = z2;
            com.leedarson.log.f.b("SDEventsPresenter", "getSDVideos_needListLoading: " + needListLoading);
            if (m() != null) {
                if (needListLoading) {
                    ((h6) m()).D();
                }
                ((h6) m()).r();
            }
            R0();
            this.u = true;
            long startTime = com.leedarson.utils.e.b(theDayOfSelected + this.J, "yyyy-MM-dd HH:mm:ss");
            long endTime = com.leedarson.utils.e.b(theDayOfSelected + this.K, "yyyy-MM-dd HH:mm:ss");
            com.leedarson.log.f.a("getSDVideos mCurStartTime:" + this.J + " mCurEndTime:" + this.K);
            com.leedarson.smartcamera.listener.b getRecordListRespListener = new u(theDayOfSelected);
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
                    ((h6) m()).L();
                }
            } else {
                com.leedarson.smartcamera.sdk.a aVar = this.s;
                if (aVar != null) {
                    aVar.O0(startTime, endTime, eventType, getRecordListRespListener);
                }
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class u implements com.leedarson.smartcamera.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        u(String str) {
            this.a = str;
        }

        public void onSuccess(List<Long> recordTimestamps) {
            if (!PatchProxy.proxy(new Object[]{recordTimestamps}, this, changeQuickRedirect, false, 3451, new Class[]{List.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("getSDRecordList onSuccess:" + recordTimestamps.size());
                com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                String r = g6.this.n;
                b2.c(r, "获取当天数据 day=" + this.a + "   个数=" + recordTimestamps.size());
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).h();
                    Collections.reverse(recordTimestamps);
                    ((h6) g6.this.m()).K(recordTimestamps);
                }
            }
        }
    }

    public void P0(long timeStamp, com.leedarson.smartcamera.listener.g listener) {
        f0 f0Var;
        Object[] objArr = {new Long(timeStamp), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3346, new Class[]{Long.TYPE, com.leedarson.smartcamera.listener.g.class}, Void.TYPE).isSupported) {
            if (this.w && (f0Var = this.x) != null) {
                f0Var.P2(timeStamp, listener);
            }
        }
    }

    public void N0(int eventType, long timeStamp, Surface surface) {
        com.leedarson.smartcamera.codec.c cVar;
        Object[] objArr = {new Integer(eventType), new Long(timeStamp), surface};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3347, new Class[]{Integer.TYPE, Long.TYPE, Surface.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.a("playSDRecord_eventType: " + eventType + " timeStamp: " + timeStamp);
            this.k = 0;
            com.leedarson.utils.j.a = false;
            if (m() != null) {
                ((h6) m()).z();
            }
            try {
                com.leedarson.newui.repoter.j.b().k(BaseApplication.b(), this.n, timeStamp, eventType, this.w);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.f.a("playSDRecord_isWebRTC: " + this.w);
            if (!this.w) {
                R0();
            }
            this.t = false;
            this.l = eventType;
            if (this.w) {
                this.j = timeStamp;
                n1(0, "播放器起播：playSDRecord");
                this.z = 0;
                this.u = false;
                if (l() != null) {
                    ((SDEventsFragment) l()).V5 = false;
                    ((SDEventsFragment) l()).T5 = 0;
                    ((SDEventsFragment) l()).U5 = 0;
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
                cVar.H(surface, new v(timeStamp, eventType));
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class v implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ int b;

        v(long j, int i) {
            this.a = j;
            this.b = i;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3453, new Class[0], Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).q();
                }
                g6.this.f.J();
                g6.this.s.q1(g6.this.j, this.a, this.b);
                long unused = g6.this.j = this.a;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void l1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3348(0xd14, float:4.692E-42)
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.g6.l1():void");
    }

    public void g1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3349, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsPresenter", "startRecord deviceId: " + deviceId);
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
                    h1();
                } else if (m() != null) {
                    ((h6) m()).j();
                }
            } else {
                com.leedarson.smartcamera.codec.c cVar = this.f;
                if (cVar != null && cVar != null) {
                    cVar.S(str, new w());
                }
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class w implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3454, new Class[0], Void.TYPE).isSupported) {
                g6.u(g6.this);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3455, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).j();
                }
            }
        }
    }

    private void h1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3350, new Class[0], Void.TYPE).isSupported) {
            l1();
            Timer timer = new Timer();
            this.q = timer;
            this.r = 0;
            timer.schedule(new x(), 10, 1000);
            if (m() != null) {
                ((h6) m()).d();
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class x extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        x() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3456, new Class[0], Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    g6.w(g6.this);
                    ((h6) g6.this.m()).e(g6.this.r);
                    com.leedarson.smartcamera.utils.e.e("", "startRecord:" + g6.this.r);
                }
            }
        }
    }

    public void j1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3351, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsPresenter", "Record stopRecord: " + this.w);
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.w3();
                    k1();
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

    /* compiled from: SDEventsPresenter */
    public class a implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3399, new Class[0], Void.TYPE).isSupported) {
                g6.x(g6.this);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3400, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).showToast(R$string.player_videotape_error);
                }
            }
        }
    }

    private void k1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3352, new Class[0], Void.TYPE).isSupported) {
            try {
                if (m() != null) {
                    ((h6) m()).showToast(R$string.player_videotape_sucess);
                    ((h6) m()).c();
                } else {
                    org.greenrobot.eventbus.c.c().l(new ShowToastEvent(R$string.player_videotape_sucess));
                }
                l1();
                BaseApplication.b().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.h))));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void e1(String deviceId, IpcWebrtcSurfaceView ipcWebrtcSurfaceView) {
        if (!PatchProxy.proxy(new Object[]{deviceId, ipcWebrtcSurfaceView}, this, changeQuickRedirect, false, 3353, new Class[]{String.class, IpcWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
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

    /* compiled from: SDEventsPresenter */
    public class b implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3401, new Class[0], Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).f(g6.this.i);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3402, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class c implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3403, new Class[0], Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).f(g6.this.i);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3404, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    public void Q0(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 3354, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            J0("resumeRecord: " + this.u);
            if (this.u) {
                N0(this.l, this.j, surface);
                if (m() != null) {
                    ((h6) m()).F(this.j);
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

    public void L0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3355, new Class[0], Void.TYPE).isSupported) {
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.R2(this.j, this.l);
                }
                m1(false, false);
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

    public void m1(boolean videoTrackEnable, boolean audioTrackEnable) {
        f0 f0Var;
        Object[] objArr = {new Byte(videoTrackEnable ? (byte) 1 : 0), new Byte(audioTrackEnable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3356, new Class[]{cls, cls}, Void.TYPE).isSupported && (f0Var = this.x) != null) {
            f0Var.w2(videoTrackEnable, audioTrackEnable);
        }
    }

    /* compiled from: SDEventsPresenter */
    public class d implements com.leedarson.smartcamera.listener.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void c(int status) {
            Object[] objArr = {new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3405, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("getThumbnai---》 tutk connectStatusChange:" + status);
            }
        }

        public void a(long time, String path) {
            Object[] objArr = {new Long(time), path};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3406, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("getThumbnai---》 tutk time=" + time + "   path=" + path);
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).n(time);
                }
            }
        }

        /* compiled from: SDEventsPresenter */
        public class a implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ long c;
            final /* synthetic */ byte[] d;

            a(long j, byte[] bArr) {
                this.c = j;
                this.d = bArr;
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3409, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                if (g6.this.l() != null) {
                    String str = ((SDEventsFragment) g6.this.l()).B5;
                    String str2 = ((SDEventsFragment) g6.this.l()).u5;
                    boolean b = com.leedarson.smartcamera.utils.d.b(str, str2, this.c + "");
                    String str3 = ((SDEventsFragment) g6.this.l()).B5;
                    String str4 = ((SDEventsFragment) g6.this.l()).u5;
                    com.leedarson.smartcamera.utils.d.h(str3, str4, this.c + "", this.d);
                }
                g6.this.M.f(this.c);
                Message msg = Message.obtain();
                y unused = g6.this.C;
                msg.what = 1;
                msg.obj = Long.valueOf(this.c);
                g6.this.C.sendMessage(msg);
                return null;
            }
        }

        public void b(long time, byte[] imgBytes) {
            Object[] objArr = {new Long(time), imgBytes};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3407, new Class[]{Long.TYPE, byte[].class}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("getThumbnai---》   onSuccess   time=" + time + "   imgBytes.size=" + imgBytes.length + "  ThumbnaiTask");
                g6.this.B.submit(new a(time, imgBytes));
            }
        }
    }

    public void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3357, new Class[0], Void.TYPE).isSupported) {
            File cacheFile = new File(com.leedarson.smartcamera.utils.d.g(BaseApplication.b()));
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
        }
    }

    public void e0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3358, new Class[0], Void.TYPE).isSupported) {
            s0();
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

    public void r0(String eventStr, List<Long> recordTimestamps) {
        Class[] clsArr = {String.class, List.class};
        if (!PatchProxy.proxy(new Object[]{eventStr, recordTimestamps}, this, changeQuickRedirect, false, 3359, clsArr, Void.TYPE).isSupported) {
            try {
                List<Long> noCacheTimes = new ArrayList<>();
                J0(" getThumbnai---》开始获取缩略图： eventStr=" + eventStr + "   recordTimestamps.size=" + recordTimestamps.size());
                for (int i2 = 0; i2 < recordTimestamps.size(); i2++) {
                    long time = recordTimestamps.get(i2).longValue() / 1000;
                    if (this.w) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("getThumbnai---》 target=");
                        sb.append(time);
                        sb.append("      imageCacheExits=");
                        String str = this.n;
                        sb.append(com.leedarson.smartcamera.utils.d.e(eventStr, str, time + ""));
                        J0(sb.toString());
                        String str2 = this.n;
                        if (!com.leedarson.smartcamera.utils.d.e(eventStr, str2, time + "")) {
                            noCacheTimes.add(Long.valueOf(time));
                            J0("getThumbnai---》发现没有缓存 time=" + time);
                        }
                    } else {
                        String G0 = this.s.G0();
                        if (!com.leedarson.smartcamera.utils.d.e(eventStr, G0, time + "")) {
                            noCacheTimes.add(Long.valueOf(time));
                        }
                    }
                }
                if (this.w == 0) {
                    J0("getThumbnais:" + this.s.a1() + " getPicSize:" + recordTimestamps.size());
                    if (this.s != null && noCacheTimes.size() > 0) {
                        this.s.T0(eventStr, noCacheTimes, this.L);
                    }
                } else if (this.x != null) {
                    this.M.i(new t5(this));
                    this.M.d(noCacheTimes);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: z0 */
    public /* synthetic */ void A0(List recordTimestamps1) {
        if (!PatchProxy.proxy(new Object[]{recordTimestamps1}, this, changeQuickRedirect, false, 3385, new Class[]{List.class}, Void.TYPE).isSupported) {
            J0("ThumbnaiTask  --> sdwebrtcChannel 开始下载  " + recordTimestamps1.size());
            this.x.n1(recordTimestamps1, this.L);
        }
    }

    public void f0(int position, int eventType, long timeStamp) {
        Object[] objArr = {new Integer(position), new Integer(eventType), new Long(timeStamp)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3360, new Class[]{cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            List<Long> delRecords = new ArrayList<>();
            delRecords.add(Long.valueOf(timeStamp));
            if (m() != null) {
                ((h6) m()).b();
            }
            com.leedarson.smartcamera.listener.a deleteRecordRespListener = new e(position);
            if (this.w) {
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.N0(0, eventType, delRecords, deleteRecordRespListener);
                }
            } else if (this.s != null) {
                R0();
                this.s.B0(0, eventType, delRecords, deleteRecordRespListener);
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class e implements com.leedarson.smartcamera.listener.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        e(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3410, new Class[0], Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).a();
                    ((h6) g6.this.m()).G(this.a);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3411, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).a();
                    ((h6) g6.this.m()).showToast(R$string.delete_failed);
                }
            }
        }
    }

    public void v0(int eventType, long timeStamp, Surface surface) {
        this.l = eventType;
        this.j = timeStamp;
        this.m = surface;
    }

    public void T0(Surface _surface, int progress) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{_surface, new Integer(progress)}, this, changeQuickRedirect, false, 3361, new Class[]{Surface.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.w) {
                boolean needSleep = false;
                if (!((SDEventsFragment) l()).X3() && (f0Var = this.x) != null) {
                    f0Var.S2(this.j, this.l);
                    needSleep = true;
                }
                int i2 = this.N;
                if (i2 >= 255 || i2 < 0) {
                    this.N = 0;
                }
                int i3 = this.N + 1;
                this.N = i3;
                f0 f0Var2 = this.x;
                if (f0Var2 == null) {
                    return;
                }
                if (this.A) {
                    if (needSleep) {
                        SystemClock.sleep(300);
                    }
                    this.x.U2(this.y + ((long) progress), this.l, this.N);
                    return;
                }
                f0Var2.U2(this.j + ((long) progress), this.l, i3);
                return;
            }
            this.t = false;
            if (this.f == null) {
                return;
            }
            if (((SDEventsFragment) l()).X3()) {
                S0(progress);
            } else {
                this.f.H(_surface, new f(progress));
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class f implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        f(int i) {
            this.a = i;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3412, new Class[0], Void.TYPE).isSupported) {
                g6.this.f.J();
                g6.B(g6.this, this.a);
            }
        }
    }

    private void S0(int progress) {
        if (!PatchProxy.proxy(new Object[]{new Integer(progress)}, this, changeQuickRedirect, false, 3362, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int i2 = this.N;
            if (i2 >= 255 || i2 < 0) {
                this.N = 0;
            }
            int i3 = this.N + 1;
            this.N = i3;
            com.leedarson.smartcamera.sdk.a aVar = this.s;
            if (aVar != null) {
                aVar.t1(this.j + ((long) progress), this.l, i3);
            }
            this.f.J();
        }
    }

    public void b1(boolean isWebRTC) {
        this.w = isWebRTC;
    }

    public void X0(String liveType) {
        this.o = liveType;
    }

    private void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3363, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsPresenter", "isPlayStart: " + this.E + " firstFrameRendered: " + this.D);
            if (this.E && this.D) {
                this.u = false;
                if (m() != null) {
                    ((h6) m()).g();
                }
                f0 f0Var = this.x;
                if (f0Var != null) {
                    f0Var.P0();
                }
                com.leedarson.newui.repoter.j.b().e(this.n);
            }
        }
    }

    public void x0(String str, String str2, KVSParamBean kVSParamBean, WeekCalendar weekCalendar, int i2, IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, kVSParamBean, weekCalendar, new Integer(i2), ipcSDWebrtcSurfaceView}, this, changeQuickRedirect, false, 3364, new Class[]{cls, cls, KVSParamBean.class, WeekCalendar.class, Integer.TYPE, IpcSDWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            String supportIpv6 = str2;
            IpcSDWebrtcSurfaceView renderer = ipcSDWebrtcSurfaceView;
            WeekCalendar weekCalendar2 = weekCalendar;
            String deviceId = str;
            KVSParamBean param = kVSParamBean;
            int i3 = i2;
            this.D = false;
            this.E = false;
            com.leedarson.log.f.b("SDEventsPresenter", "initWebRTCChannel isSupportPreCon: " + this.H);
            if (this.H) {
                f0 n2 = com.leedarson.manager.a.i().n(deviceId, this.o, supportIpv6, param, f0.r.PRE_LINK);
                this.x = n2;
                n2.e3(renderer);
                Z0(renderer);
                this.x.z0(false);
                String str3 = supportIpv6;
            } else {
                String deviceIdTAG = deviceId + "-SD";
                this.x = com.leedarson.manager.a.i().j(deviceIdTAG);
                String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                if (Constans.IPC_LIVE_TYPE_LDS.equals(this.o)) {
                    this.x = new f0(deviceId, userId, supportIpv6, f0.r.SDCARD);
                    String str4 = supportIpv6;
                    String str5 = userId;
                } else if (!Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.o)) {
                    if (param != null) {
                        f0 f0Var = this.x;
                        if (f0Var == null) {
                            this.x = new f0(param.accessKeyId, param.secretAccessKey, param.sessionToken, param.channelArn, param.region, userId, false);
                        } else {
                            String userId2 = userId;
                            if (!param.accessKeyId.equals(f0Var.S0())) {
                                this.x = new f0(param.accessKeyId, param.secretAccessKey, param.sessionToken, param.channelArn, param.region, userId2, false);
                            }
                        }
                    } else {
                        return;
                    }
                } else if (param != null) {
                    String str6 = supportIpv6;
                    String str7 = supportIpv6;
                    f0 f0Var2 = r1;
                    f0 f0Var3 = new f0(this.o, param, userId, deviceId, str6, f0.r.SDCARD);
                    this.x = f0Var2;
                    String str8 = userId;
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
        }
    }

    /* compiled from: SDEventsPresenter */
    public class g implements com.leedarson.smartcamera.listener.j {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void c(long timeStamp, long startTime, int flag) {
            long curPlaySec;
            Object[] objArr = {new Long(timeStamp), new Long(startTime), new Integer(flag)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Long.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3413, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("updateTime:" + timeStamp + "--" + startTime + "--" + flag + "--" + g6.this.j);
                SDEventsFragment fragment = (SDEventsFragment) g6.this.l();
                if (!g6.this.x.v1()) {
                    if (flag == 1) {
                        boolean unused = g6.this.A = true;
                        if ((startTime + "").length() == 10) {
                            startTime *= 1000;
                        }
                        if (startTime != g6.this.j) {
                            return;
                        }
                        if (g6.this.y == 0) {
                            g6.Y(g6.this, timeStamp, "更新起播时间updateTime");
                            if (fragment != null) {
                                fragment.V5 = false;
                                fragment.T5 = 0;
                                fragment.U5 = 0;
                            }
                        }
                    } else {
                        boolean unused2 = g6.this.A = false;
                    }
                    if (g6.this.A) {
                        curPlaySec = timeStamp - g6.this.y;
                    } else {
                        curPlaySec = timeStamp - g6.this.j;
                    }
                    if (((SDEventsFragment) g6.this.l()).V5) {
                        int i = fragment.U5;
                        int i2 = fragment.T5;
                        if (i - i2 > 0 && curPlaySec >= ((long) i)) {
                            fragment.V5 = false;
                            fragment.T5 = 0;
                            fragment.U5 = 0;
                        } else if (i - i2 < 0 && curPlaySec >= ((long) i) && curPlaySec < ((long) i2)) {
                            fragment.V5 = false;
                            fragment.T5 = 0;
                            fragment.U5 = 0;
                        } else {
                            return;
                        }
                    }
                    if (g6.this.m() != null && !fragment.S5 && !g6.this.u) {
                        ((h6) g6.this.m()).w((int) curPlaySec);
                        if (g6.this.t && g6.this.F - fragment.W5.getProgress() < 3000) {
                            g6.I(g6.this);
                        }
                    }
                }
            }
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3414, new Class[0], Void.TYPE).isSupported) {
                g6.this.M.e();
            }
        }

        public void b(int time) {
            Object[] objArr = {new Integer(time)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3415, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("GetSDTotalTime:" + time);
                boolean unused = g6.this.E = true;
                int unused2 = g6.this.F = time * 1000;
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).o(g6.this.F);
                }
                g6.K(g6.this);
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3416, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.a("reciveStreamEnd");
                g6.I(g6.this);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3417, new Class[0], Void.TYPE).isSupported) {
                g6.this.J0("changeToPause");
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3418, new Class[0], Void.TYPE).isSupported) {
                g6.this.J0("changeToResume");
                com.leedarson.newui.view.radar.g.e("收到播放/暂停响应");
            }
        }
    }

    private void p1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3365, new Class[0], Void.TYPE).isSupported) {
            try {
                SDEventsFragment fragment = (SDEventsFragment) l();
                this.t = true;
                if (this.F - fragment.W5.getProgress() <= 3000) {
                    this.u = true;
                    if (m() != null) {
                        ((h6) m()).u();
                        com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                        String str = this.n;
                        b2.g(str, "播放结束：" + this.j, this.w);
                    }
                    fragment.V5 = false;
                    fragment.T5 = 0;
                    fragment.U5 = 0;
                    R0();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void M0() {
        this.u = true;
    }

    public void o1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3366, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.O = 0;
            W0(deviceId, false);
            io.reactivex.disposables.b bVar = this.R;
            if (bVar != null && !bVar.isDisposed()) {
                this.R.dispose();
            }
            this.R = this.Q.d(deviceId).c(com.leedarson.base.http.observer.l.c()).I(u5.c, s5.c);
        }
    }

    static /* synthetic */ void H0(LDSBaseBean ldsBaseBean) {
    }

    static /* synthetic */ void I0(Throwable throwable) {
    }

    public void W0(String deviceId, boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3367, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.P;
            if (bVar != null && !bVar.isDisposed()) {
                this.P.dispose();
            }
            this.P = this.Q.k(deviceId, isSleep).G(new com.leedarson.base.http.observer.j(2, 1500)).c(com.leedarson.base.http.observer.l.c()).I(new v5(this, isSleep, deviceId), new w5(this, isSleep, deviceId));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B0 */
    public /* synthetic */ void C0(boolean isSleep, String deviceId, LDSBaseBean ldsBaseBean) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSleep ? (byte) 1 : 0), deviceId, ldsBaseBean}, this, changeQuickRedirect, false, 3384, new Class[]{Boolean.TYPE, String.class, LDSBaseBean.class}, Void.TYPE).isSupported) {
            if (!ldsBaseBean.checkDataValid() && !isSleep) {
                int i2 = this.O + 1;
                this.O = i2;
                if (i2 < 3) {
                    W0(deviceId, false);
                    return;
                }
                J0("kvs.唤醒发生了异常：" + ldsBaseBean.getErrorDetalInfo());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D0 */
    public /* synthetic */ void E0(boolean isSleep, String deviceId, Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSleep ? (byte) 1 : 0), deviceId, throwable}, this, changeQuickRedirect, false, 3383, new Class[]{Boolean.TYPE, String.class, Throwable.class}, Void.TYPE).isSupported) {
            if (!isSleep) {
                int i2 = this.O + 1;
                this.O = i2;
                if (i2 < 3) {
                    W0(deviceId, false);
                    return;
                }
                J0("kvs.唤醒发生了异常：" + throwable);
            }
        }
    }

    public void K0(JSONObject data) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3368, new Class[]{JSONObject.class}, Void.TYPE).isSupported && (f0Var = this.x) != null) {
            f0Var.u2(data);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3369, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            io.reactivex.disposables.b bVar = this.R;
            if (bVar != null && !bVar.isDisposed()) {
                this.R.dispose();
            }
            ExecutorService executorService = this.B;
            if (executorService != null) {
                executorService.shutdown();
            }
            ArrayList<io.reactivex.disposables.b> arrayList = this.Y;
            if (arrayList != null) {
                Iterator<io.reactivex.disposables.b> it = arrayList.iterator();
                while (it.hasNext()) {
                    io.reactivex.disposables.b disposable = it.next();
                    if (disposable != null && !disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }
            com.leedarson.newui.sdthumbnai.b bVar2 = this.M;
            if (bVar2 != null) {
                bVar2.h();
            }
        }
    }

    public void q0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 3370, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            J0("getShareAccount");
            String baseUrl = SharePreferenceUtils.getPrefString(((SDEventsFragment) l()).getContext(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramObj = new JSONObject();
            String appId = SharePreferenceUtils.getPrefString(((SDEventsFragment) l()).getContext(), "APP_ID", "");
            String owner = SharePreferenceUtils.getPrefString(((SDEventsFragment) l()).getContext(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(((SDEventsFragment) l()).getContext(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(((SDEventsFragment) l()).getContext()));
                paramObj.put("deviceIds", (Object) deviceId);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            b0.b().K(((SDEventsFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, String.format(Locale.US, "%s/devices/shares", new Object[]{baseUrl}), headerJson.toString(), paramObj.toString(), new h());
        }
    }

    /* compiled from: SDEventsPresenter */
    public class h extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3421, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3419, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("error=" + e.getCode() + "——" + e.getMsg());
                ((h6) g6.this.m()).y();
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3420, new Class[]{String.class}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("getShareAccount onSuccess:" + response);
                try {
                    ((h6) g6.this.m()).t(new JSONArray(response).getJSONObject(0).getString("fromUserUuid"));
                } catch (Exception e) {
                    e.printStackTrace();
                    ((h6) g6.this.m()).y();
                }
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class y extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private y() {
        }

        /* synthetic */ y(g6 x0, k x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3457, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        try {
                            if (g6.this.m() != null) {
                                ((h6) g6.this.m()).n(((Long) msg.obj).longValue());
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

    private void Z0(IpcSDWebrtcSurfaceView renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 3371, new Class[]{IpcSDWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            if (this.x.v1()) {
                renderer.setOnGetFrameInfoListener(new r5(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: F0 */
    public /* synthetic */ void G0(String str, String str2, String str3) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, str2, str3}, this, changeQuickRedirect, false, 3382, clsArr, Void.TYPE).isSupported) {
            String channelId = str2;
            String str4 = str;
            String frameTimestamp = str3;
            try {
                SDEventsFragment fragment = (SDEventsFragment) l();
                long channelT = (this.j / 1000) & 65535;
                if (com.alibaba.android.arouter.utils.e.b(channelId)) {
                    return;
                }
                if (!com.alibaba.android.arouter.utils.e.b(frameTimestamp)) {
                    if (((long) Integer.parseInt(channelId)) == channelT) {
                        long timeStamp = Long.parseLong(frameTimestamp);
                        if (this.y == 0 && timeStamp - this.j < ((long) this.F)) {
                            n1(timeStamp, "目前rtp扩展带的时间戳，重播同个回放会带上次播放最后几帧的时间戳，需过滤");
                            fragment.V5 = false;
                            fragment.T5 = 0;
                            fragment.U5 = 0;
                        }
                        this.z = timeStamp;
                        long curPlaySec = timeStamp - this.y;
                        if (fragment.V5) {
                            int i2 = fragment.U5;
                            int i3 = fragment.T5;
                            if (i2 - i3 > 0) {
                                long j2 = timeStamp;
                                if (curPlaySec >= ((long) i2)) {
                                    fragment.V5 = false;
                                    fragment.T5 = 0;
                                    fragment.U5 = 0;
                                }
                            }
                            if (i2 - i3 < 0 && curPlaySec >= ((long) i2) && curPlaySec < ((long) i3)) {
                                fragment.V5 = false;
                                fragment.T5 = 0;
                                fragment.U5 = 0;
                            } else {
                                return;
                            }
                        }
                        if (m() != null && !fragment.S5 && !this.u) {
                            ((h6) m()).w((int) curPlaySec);
                            if (this.t && this.F - fragment.W5.getProgress() < 3000) {
                                p1();
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void n1(long newStartPtsTime, String ref) {
        this.y = newStartPtsTime;
    }

    public void Y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3372, new Class[0], Void.TYPE).isSupported) {
            if (this.x != null && l() != null) {
                com.leedarson.utils.j.a = false;
                this.D = false;
                String path = BaseApplication.b().getFilesDir() + "/tempCache.jpg";
                com.leedarson.log.f.b("SDEventsPresenter", "setNoFirstFrame start: " + path);
                this.x.B0(path, new i(path));
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class i implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        i(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3422, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDEventsPresenter", "setNoFirstFrame onSuccess: " + g6.this.D);
                g6 g6Var = g6.this;
                if (!g6Var.D && g6Var.m() != null) {
                    ((h6) g6.this.m()).l(this.a);
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3423, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.d("SDEventsPresenter", "setNoFirstFrame onError: ");
            }
        }
    }

    public void Z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3373, new Class[0], Void.TYPE).isSupported) {
            if (this.f != null && l() != null) {
                com.leedarson.utils.j.a = false;
                String path = BaseApplication.b().getFilesDir() + "/tempCache.jpg";
                com.leedarson.log.f.b("SDEventsPresenter", "captureCacheImage start: " + path);
                this.f.M(path, new j(path));
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class j implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        j(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3424, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDEventsPresenter", "captureCacheImage onSuccess: " + g6.this.D);
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).l(this.a);
                }
            }
        }

        public void a(int code) {
        }
    }

    private void c1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3374, new Class[0], Void.TYPE).isSupported) {
            try {
                if (m() != null) {
                    ((h6) m()).s();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void k0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3375, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.utils.j.a = true;
            this.D = true;
            y0();
        }
    }

    public void o0(String deviceId, String password) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, password}, this, changeQuickRedirect, false, 3376, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                timber.log.a.g("SDEventsPresenter").a("deviceControlByDevActionReq", new Object[0]);
                JSONObject actionsObj = new JSONObject();
                actionsObj.put("action", (Object) "SDcardBaseInfo");
                actionsObj.put("devId", (Object) deviceId);
                actionsObj.put("userId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                actionsObj.put("password", (Object) password);
                actionsObj.put("in", (Object) new JSONArray());
                com.leedarson.newui.repoter.e eVar = this.S;
                eVar.e("开始请求SD卡状态信息 params=action=SDcardBaseInfo   deviceId=" + deviceId + "   userId=" + SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                i0(DNSSD.DNSSD_DEFAULT_TIMEOUT, deviceId, actionsObj, new l());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class l implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void onResult(int code, String responseData) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), responseData}, this, changeQuickRedirect, false, 3427, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                try {
                    JSONObject payload = new JSONObject(responseData).getJSONObject("payload");
                    ((h6) g6.this.m()).l0(payload.has("out") ? payload.getString("out") : "");
                    a.b g = timber.log.a.g("SDEventsPresenter");
                    g.m("deviceControlByDevActionReq SD卡信息获取成功 onResult: " + code + " responseData=" + responseData, new Object[0]);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    a.b g2 = timber.log.a.g("SDEventsPresenter");
                    g2.c("deviceControlByDevActionReq SD卡信息获取(失败) exception: " + exception.toString() + " responseData=" + responseData, new Object[0]);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3428, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("SDEventsPresenter");
                g.c("deviceControlByDevActionReq SD卡信息获取(失败) tip: " + tip, new Object[0]);
                com.leedarson.newui.repoter.e eVar = g6.this.S;
                eVar.g("SD卡信息获取超时：response=>" + tip);
                ((h6) g6.this.m()).I0();
            }
        }
    }

    public void j0(String deviceId, String password) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{deviceId, password}, this, changeQuickRedirect, false, 3377, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject actionsObj = new JSONObject();
                actionsObj.put("action", (Object) "SDcardFormatFunc");
                actionsObj.put("devId", (Object) deviceId);
                actionsObj.put("userId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                actionsObj.put("password", (Object) password);
                actionsObj.put("in", (Object) new JSONArray());
                i0(180000, deviceId, actionsObj, new m());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class m implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void onResult(int code, String responseData) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), responseData}, this, changeQuickRedirect, false, 3429, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                try {
                    a.b g = timber.log.a.g("SDEventsPresenter");
                    g.m("格式化SD卡 onResult: " + code + " responseData=" + responseData, new Object[0]);
                    if (code == 200) {
                        ((h6) g6.this.m()).R();
                    } else {
                        ((h6) g6.this.m()).X();
                    }
                } catch (Exception exception) {
                    ((h6) g6.this.m()).X();
                    exception.printStackTrace();
                    a.b g2 = timber.log.a.g("SDEventsPresenter");
                    g2.c("格式化SD卡(失败) exception: " + exception.toString() + " responseData=" + responseData, new Object[0]);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3430, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("SDEventsPresenter");
                g.c("格式化SD卡(超时) tip: " + tip, new Object[0]);
                ((h6) g6.this.m()).X();
            }
        }
    }

    private void i0(int timeout, String deviceId, JSONObject actionsObj, OnControlRespListener respListener) {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{new Integer(timeout), deviceId, actionsObj, respListener}, this, changeQuickRedirect, false, 3378, new Class[]{Integer.TYPE, String.class, JSONObject.class, OnControlRespListener.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                jsonObject.put("actions", (Object) actionsObj);
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    String _topic = "iot/v1/s/userId/device/devActionReq".replace("userId", BaseApplication.b().d());
                    MqttMessageConfigBean _config = new MqttMessageConfigBean();
                    _config.timeOutLimitOfMs = (long) timeout;
                    if (!com.alibaba.android.arouter.utils.e.b(((SDEventsFragment) l()).p3 == null ? "" : ((SDEventsFragment) l()).p3.simpleVersion)) {
                        z2 = true;
                    }
                    _config.isSupportSimpleVersion = z2;
                    JSONObject _messageObj = new JSONObject();
                    _messageObj.put(FirebaseAnalytics.Param.METHOD, (Object) "devActionReq");
                    _messageObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
                    _messageObj.put("payload", (Object) actionsObj);
                    _mqttService.publish(_topic, _config, _messageObj, new n(respListener));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: SDEventsPresenter */
    public class n implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ OnControlRespListener a;

        n(OnControlRespListener onControlRespListener) {
            this.a = onControlRespListener;
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 3431, clsArr, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).a();
                    try {
                        callbackData.put("code", 200);
                        this.a.onResult(200, callbackData.toString());
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3432, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                if (g6.this.m() != null) {
                    ((h6) g6.this.m()).a();
                    this.a.onFail(code, taskId, desc);
                }
            }
        }
    }

    public void d0(String id, String p2pId, String account, String password, String dtls) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{id, p2pId, account, password, dtls}, this, changeQuickRedirect, false, 3379, new Class[]{cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            w0(id, p2pId, account, password, dtls);
            com.leedarson.smartcamera.sdk.a aVar = this.s;
            if (aVar != null) {
                aVar.x0();
            }
        }
    }

    public void c0(String str, String supportIpv6, KVSParamBean param, WeekCalendar weekCalendar, int eventType, IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView) {
        f0 f0Var;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, supportIpv6, param, weekCalendar, new Integer(eventType), ipcSDWebrtcSurfaceView}, this, changeQuickRedirect, false, 3380, new Class[]{cls, cls, KVSParamBean.class, WeekCalendar.class, Integer.TYPE, IpcSDWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            IpcSDWebrtcSurfaceView webrtcSurfaceView = ipcSDWebrtcSurfaceView;
            String deviceId = str;
            x0(deviceId, supportIpv6, param, weekCalendar, eventType, webrtcSurfaceView);
            f0 f0Var2 = this.x;
            if (f0Var2 == null) {
                return;
            }
            if (f0Var2.r1()) {
                if (this.H && (f0Var = this.x) != null) {
                    f0Var.C0(f0.q.SD);
                }
                ((h6) m()).Y0();
                return;
            }
            this.x.H0(l() != null ? ((SDEventsFragment) l()).getContext() : null, new o(deviceId, webrtcSurfaceView));
        }
    }

    /* compiled from: SDEventsPresenter */
    public class o implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ IpcSDWebrtcSurfaceView b;

        o(String str, IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView) {
            this.a = str;
            this.b = ipcSDWebrtcSurfaceView;
        }

        /* compiled from: SDEventsPresenter */
        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3436, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                    o oVar = o.this;
                    b2.g(oVar.a, "onAddStream", g6.this.w);
                    com.leedarson.log.f.a("onAddStream");
                    try {
                        if (g6.this.l() != null) {
                            ((SDEventsFragment) g6.this.l()).getActivity().runOnUiThread(new C0113a());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.newui.g6$o$a$a  reason: collision with other inner class name */
            /* compiled from: SDEventsPresenter */
            public class C0113a implements Runnable {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0113a() {
                }

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3440, new Class[0], Void.TYPE).isSupported) {
                        g6.this.x.z0(false);
                        o oVar = o.this;
                        g6.this.x.e3(oVar.b);
                        o oVar2 = o.this;
                        g6.L(g6.this, oVar2.b);
                    }
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 3437, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                    String str = o.this.a;
                    b2.g(str, "onDataChannelStateChange:" + state, g6.this.w);
                    if (state == DataChannel.State.OPEN) {
                        f0 f0Var = g6.this.x;
                        if (f0Var != null) {
                            f0Var.C0(f0.q.SD);
                        }
                        if (g6.this.C != null) {
                            g6.this.C.post(new b());
                        }
                    }
                }
            }

            /* compiled from: SDEventsPresenter */
            public class b implements Runnable {
                public static ChangeQuickRedirect changeQuickRedirect;

                b() {
                }

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3441, new Class[0], Void.TYPE).isSupported) {
                        if (g6.this.m() != null) {
                            ((h6) g6.this.m()).Y0();
                        }
                    }
                }
            }

            public void c(byte[] bytes) {
            }

            public void onError(String desc) {
                if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 3438, new Class[]{String.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                    String str = o.this.a;
                    b2.g(str, "onError:" + desc, g6.this.w);
                }
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 3439, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.j b2 = com.leedarson.newui.repoter.j.b();
                    String str = o.this.a;
                    b2.g(str, "onIceConnectionChange:" + iceConnectionState, g6.this.w);
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3433, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.j.b().g(this.a, "websocket open", g6.this.w);
                g6.this.x.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3434, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.j.b().g(this.a, "onClose", g6.this.w);
            }
        }

        public void a(Event event) {
        }

        public void onException(Exception e) {
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
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3435, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (stateCode == -50015) {
                    boolean unused = g6.this.G = true;
                    if (g6.this.m() != null) {
                        ((h6) g6.this.m()).L();
                    }
                }
            }
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    public void n0(IpcDeviceBean deviceBean, String ref) {
        Class[] clsArr = {IpcDeviceBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{deviceBean, ref}, this, changeQuickRedirect, false, 3381, clsArr, Void.TYPE).isSupported) {
            this.n = deviceBean.id;
            long requestStartTime = System.currentTimeMillis();
            io.reactivex.disposables.b bVar = this.V;
            if (bVar != null && !bVar.isDisposed()) {
                this.V.dispose();
            }
            J0("[hyf]getKVSParams start:");
            this.V = this.W.d(this.n, new p(ref, requestStartTime));
            J0("getKVSParams onStart: " + this.V);
            this.Y.add(this.V);
        }
    }

    /* compiled from: SDEventsPresenter */
    public class p extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ long d;

        p(String str, long j) {
            this.c = str;
            this.d = j;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3444, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3442, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("[hyf]getKVSParams onError:" + e.getMsg());
                ((h6) g6.this.m()).J();
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3443, new Class[]{String.class}, Void.TYPE).isSupported) {
                g6 g6Var = g6.this;
                g6Var.J0("[hyf]getKVSParams onSuccess:" + response);
                try {
                    KvsParmsResponseWrapBean resPonseData = (KvsParmsResponseWrapBean) new Gson().fromJson(response, new a().getType());
                    KVSParamBean kvsParamBean = (KVSParamBean) ((HashMap) resPonseData.data).get(g6.this.n);
                    if (!resPonseData.checkDataValid() || resPonseData.data == null || kvsParamBean == null) {
                        ((h6) g6.this.m()).J();
                    } else if (kvsParamBean.requireFiledCheck()) {
                        if (kvsParamBean.nowTime > 0) {
                            long unused = g6.this.X = Math.abs(System.currentTimeMillis() - kvsParamBean.nowTime);
                        }
                        ((h6) g6.this.m()).C(kvsParamBean, this.c);
                        System.currentTimeMillis();
                    } else {
                        ((h6) g6.this.m()).J();
                    }
                } catch (Exception e) {
                    ((h6) g6.this.m()).J();
                }
            }
        }

        /* compiled from: SDEventsPresenter */
        public class a extends TypeToken<KvsParmsResponseWrapBean> {
            a() {
            }
        }
    }
}
