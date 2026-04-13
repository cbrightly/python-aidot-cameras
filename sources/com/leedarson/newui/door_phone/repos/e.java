package com.leedarson.newui.door_phone.repos;

import android.os.Handler;
import android.os.Looper;
import com.leedarson.base.http.observer.l;
import com.leedarson.newui.channelstratages.c;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.listener.n;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.concurrent.TimeUnit;
import leedarson.platform.playersdk.PlayerStateDefine;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import org.webrtc.RendererCommon;

/* compiled from: DoorPhoneKvsRepos */
public class e extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    com.leedarson.newui.channelstratages.c b;
    IpcWebrtcSurfaceView c;
    f0 d;
    com.leedarson.newui.repoter.d e = new com.leedarson.newui.repoter.d();
    public io.reactivex.processors.b<j> f = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<k> g = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<k> h = io.reactivex.processors.b.Y();
    private final int i = 30;
    private io.reactivex.disposables.b j;

    /* compiled from: DoorPhoneKvsRepos */
    public enum j {
        ON_PREPARE,
        ON_CHANNEL_SUCCESS,
        ON_ERROR,
        ON_TIME_OUT;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void c(e x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4140, new Class[]{e.class}, Void.TYPE).isSupported) {
            x0.d();
        }
    }

    public void l(JSONObject payloadObj) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{payloadObj}, this, changeQuickRedirect, false, 4126, new Class[]{JSONObject.class}, Void.TYPE).isSupported && (f0Var = this.d) != null) {
            f0Var.u2(payloadObj);
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class k {
        public int a = 0;
        public String b = "";

        public k(int code, String desc) {
            this.a = code;
            this.b = desc;
        }
    }

    private void f(String deviceId, String liveType, String supportIpv6) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{deviceId, liveType, supportIpv6}, this, changeQuickRedirect, false, 4127, clsArr, Void.TYPE).isSupported) {
            this.b = new com.leedarson.newui.channelstratages.c(deviceId, liveType, supportIpv6);
            b();
            this.a = new io.reactivex.disposables.a();
            a(this.b.h.H(a.c));
            a(this.b.j.I(new a(), new b()));
            a(this.b.i.I(new c(), new d()));
            a(this.b.k.I(new b(this), new c(this)));
            this.b.e();
            com.leedarson.newui.repoter.d dVar = this.e;
            dVar.x("deviceId=" + deviceId, "DoorBellPage", "门铃预连接");
            n();
            e();
            a(this.j);
        }
    }

    static /* synthetic */ void g(KVSParamBean param) {
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class a implements io.reactivex.functions.e<f0> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4142, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((f0) obj);
            }
        }

        public void a(f0 kvsWebRTCChannel) {
            if (!PatchProxy.proxy(new Object[]{kvsWebRTCChannel}, this, changeQuickRedirect, false, 4141, new Class[]{f0.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                eVar.d = kvsWebRTCChannel;
                e.c(eVar);
            }
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class b implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4144, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 4143, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                io.reactivex.processors.b<k> bVar = eVar.h;
                bVar.onNext(new k(-1002, "连接状态发生变化" + throwable.toString()));
            }
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class c implements io.reactivex.functions.e<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4146, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((String) obj);
            }
        }

        public void a(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 4145, new Class[]{String.class}, Void.TYPE).isSupported) {
                e.this.f.onNext(j.ON_ERROR);
                e eVar = e.this;
                eVar.h.onNext(new k(-1001, "获取参数失败"));
            }
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class d implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4148, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 4147, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                io.reactivex.processors.b<k> bVar = eVar.h;
                bVar.onNext(new k(-1001, "获取参数失败" + throwable.toString()));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(c.C0104c kvsConnectStateChange) {
        if (!PatchProxy.proxy(new Object[]{kvsConnectStateChange}, this, changeQuickRedirect, false, 4139, new Class[]{c.C0104c.class}, Void.TYPE).isSupported) {
            if (kvsConnectStateChange.a() < 0) {
                this.f.onNext(j.ON_ERROR);
                io.reactivex.processors.b<k> bVar = this.h;
                bVar.onNext(new k(-1002, "连接状态发生变化" + kvsConnectStateChange.a() + kvsConnectStateChange.b()));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 4138, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            io.reactivex.processors.b<k> bVar = this.h;
            bVar.onNext(new k(-1002, "连接状态发生变化" + throwable.toString()));
        }
    }

    public void m(IpcWebrtcSurfaceView webrtcSurfaceView, String deviceId, String liveType, String supportIpv6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{webrtcSurfaceView, deviceId, liveType, supportIpv6}, this, changeQuickRedirect, false, 4128, new Class[]{IpcWebrtcSurfaceView.class, cls, cls, cls}, Void.TYPE).isSupported) {
            this.e.d(deviceId);
            webrtcSurfaceView.release();
            webrtcSurfaceView.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
            this.c = webrtcSurfaceView;
            this.f.onNext(j.ON_PREPARE);
            f(deviceId, liveType, supportIpv6);
        }
    }

    /* renamed from: com.leedarson.newui.door_phone.repos.e$e  reason: collision with other inner class name */
    /* compiled from: DoorPhoneKvsRepos */
    public class C0111e implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0111e() {
        }

        /* renamed from: com.leedarson.newui.door_phone.repos.e$e$a */
        /* compiled from: DoorPhoneKvsRepos */
        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4152, new Class[0], Void.TYPE).isSupported) {
                    e.this.d.z0(false);
                    e eVar = e.this;
                    eVar.d.e3(eVar.c);
                    e.this.f.onNext(j.ON_CHANNEL_SUCCESS);
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4149, new Class[0], Void.TYPE).isSupported) {
                e.this.e.g("webRtcChannel 流添加成功");
                new Handler(Looper.getMainLooper()).post(new a());
            }
        }

        public void a(DataChannel.State state) {
            if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 4150, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.d dVar = e.this.e;
                dVar.y("KVS.WebRTC 状态变更state=" + state);
                com.leedarson.newui.repoter.d dVar2 = e.this.e;
                dVar2.h("KVS.WebRTC DataChannel 状态变更state=" + state);
                if (state == DataChannel.State.OPEN) {
                    e.this.d.g1(new b());
                } else if (state == DataChannel.State.CLOSING) {
                    e eVar = e.this;
                    eVar.h.onNext(new k(-1004, "DataChannel 正在关闭"));
                } else if (state == DataChannel.State.CLOSED) {
                    e eVar2 = e.this;
                    eVar2.h.onNext(new k(PlayerStateDefine.EC_MALLOC_FAILED, "DataChannel 已关闭"));
                }
            }
        }

        /* renamed from: com.leedarson.newui.door_phone.repos.e$e$b */
        /* compiled from: DoorPhoneKvsRepos */
        public class b implements com.leedarson.smartcamera.listener.c {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            public void onSuccess(int resolution) {
            }
        }

        public void c(byte[] bytes) {
        }

        public void onError(String desc) {
            if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 4151, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.d dVar = e.this.e;
                dVar.f("desc=" + desc);
                e.this.f.onNext(j.ON_ERROR);
                e eVar = e.this;
                eVar.h.onNext(new k(-1003, "连接状态发生变化"));
            }
        }

        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4129, new Class[0], Void.TYPE).isSupported) {
            p(false);
            this.d.createSdpOffer(new C0111e());
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class f implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onSuccess() {
        }

        public void a(int code) {
        }

        public void b(short[] data, int length, int db) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void r() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4130(0x1022, float:5.787E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.d
            if (r1 == 0) goto L_0x0023
            com.leedarson.newui.door_phone.repos.e$f r2 = new com.leedarson.newui.door_phone.repos.e$f
            r2.<init>()
            r1.q3(r2)
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.door_phone.repos.e.r():void");
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class g implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onSuccess() {
        }

        public void a(int code) {
        }

        public void b(short[] data, int length, int db) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void s() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4131(0x1023, float:5.789E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.d
            if (r1 == 0) goto L_0x0023
            com.leedarson.newui.door_phone.repos.e$g r2 = new com.leedarson.newui.door_phone.repos.e$g
            r2.<init>()
            r1.z3(r2)
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.door_phone.repos.e.s():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void o(com.leedarson.view.IpcWebrtcSurfaceView r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.view.IpcWebrtcSurfaceView> r0 = com.leedarson.view.IpcWebrtcSurfaceView.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4132(0x1024, float:5.79E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r9
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.d
            if (r1 == 0) goto L_0x002a
            r1.t3(r10)
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.d
            r1.I2(r8)
        L_0x002a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.door_phone.repos.e.o(com.leedarson.view.IpcWebrtcSurfaceView):void");
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4133, new Class[0], Void.TYPE).isSupported) {
            super.b();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4134(0x1026, float:5.793E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.j
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.j
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.door_phone.repos.e.n():void");
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4135, new Class[0], Void.TYPE).isSupported) {
            n();
            e();
        }
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4136, new Class[0], Void.TYPE).isSupported) {
            this.j = io.reactivex.e.R(30, TimeUnit.SECONDS).c(l.c()).I(new h(), new i());
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class h implements io.reactivex.functions.e<Long> {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4154, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Long) obj);
            }
        }

        public void a(Long l) {
            if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 4153, new Class[]{Long.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("KvsRepos", "DoorPhoneKvsRepos.TimeOut ===>");
                e eVar = e.this;
                eVar.g.onNext(new k(-1, "Pick Up Phone Timeout "));
            }
        }
    }

    /* compiled from: DoorPhoneKvsRepos */
    public class i implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4155, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
        }
    }

    public void p(boolean enable) {
        f0 f0Var;
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4137, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (f0Var = this.d) != null) {
            f0Var.z0(enable);
        }
    }
}
