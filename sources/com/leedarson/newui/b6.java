package com.leedarson.newui;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.FeedbackDoneBean;
import com.leedarson.base.beans.FeedbackDoneParamsBean;
import com.leedarson.base.beans.FeedbackRequestBean;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IPCLiveAction;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PropsBean;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.hummandetect.CopyAssetUtils;
import com.leedarson.hummandetect.DetectRoi;
import com.leedarson.hummandetect.HumanDetectSDK;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.report_repos.k;
import com.leedarson.newui.repos.beans.BindPackageInfoItemBean;
import com.leedarson.newui.repos.beans.BindPackageInfoResponseBean;
import com.leedarson.newui.repos.beans.KvsParmsResponseWrapBean;
import com.leedarson.newui.repos.beans.UpdateSubscribeBean;
import com.leedarson.newui.repos.reporters.a;
import com.leedarson.newui.repoter.IPCReconnectReporter;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.listener.OnControlRespListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.DeviceIdUtils;
import com.leedarson.smartcamera.bean.AckBean;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.codec.c;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import meshsdk.ConfigUtil;
import meshsdk.model.json.RoutineRule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import timber.log.a;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: NewLivePresenter */
public class b6 extends com.leedarson.base.presenters.a<c6, NewLiveFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A = 3;
    private HumanDetectSDK A0;
    private int B = 0;
    private DetectRoi B0;
    /* access modifiers changed from: private */
    public io.reactivex.processors.b<Boolean> C = io.reactivex.processors.b.Y();
    com.leedarson.newui.report_repos.k D;
    com.leedarson.newui.repoter.e E = new com.leedarson.newui.repoter.e();
    com.leedarson.newui.repoter.d F = new com.leedarson.newui.repoter.d();
    public com.leedarson.newui.repoter.h G = new com.leedarson.newui.repoter.h();
    private com.leedarson.newui.view.radar.d H;
    /* access modifiers changed from: private */
    public int I;
    /* access modifiers changed from: private */
    public int J = 0;
    public IpcDeviceBean K;
    public boolean L = false;
    com.leedarson.newui.repos.r M = new com.leedarson.newui.repos.r();
    /* access modifiers changed from: private */
    public ArrayList<io.reactivex.disposables.b> N = new ArrayList<>();
    public int O = 0;
    public IPCReconnectReporter.ReconnectEventInfoBean P = new IPCReconnectReporter.ReconnectEventInfoBean();
    private boolean Q = true;
    io.reactivex.disposables.b R = null;
    io.reactivex.disposables.b S = null;
    private boolean T = false;
    private boolean U = false;
    /* access modifiers changed from: private */
    public com.leedarson.newui.stratages.c V = new com.leedarson.newui.stratages.c();
    long W = 0;
    long X = 0;
    private com.leedarson.smartcamera.listener.i Y = new k();
    io.reactivex.disposables.b Z;
    com.leedarson.smartcamera.listener.f a0;
    com.leedarson.smartcamera.listener.c b0;
    /* access modifiers changed from: private */
    public long c0 = 0;
    int d0 = 0;
    private Runnable e0 = new n0();
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.sdk.a f;
    com.leedarson.smartcamera.listener.k f0;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.f0 g;
    Handler g0 = new Handler();
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c h;
    private final String h0 = "playSound";
    private SimpleDateFormat i;
    private final String i0 = "SDcardBaseInfo";
    /* access modifiers changed from: private */
    public String j;
    private final String j0 = "RebootFunc";
    /* access modifiers changed from: private */
    public String k;
    MediaScannerConnection k0;
    private int l = 0;
    /* access modifiers changed from: private */
    public boolean l0 = false;
    private Timer m;
    com.leedarson.smartcamera.listener.n m0;
    private Timer n;
    com.leedarson.smartcamera.listener.n n0;
    /* access modifiers changed from: private */
    public int o = 0;
    private Timer o0;
    /* access modifiers changed from: private */
    public int p = 15;
    /* access modifiers changed from: private */
    public boolean p0 = false;
    private boolean q = false;
    Gson q0 = new Gson();
    /* access modifiers changed from: private */
    public boolean r;
    io.reactivex.disposables.b r0;
    /* access modifiers changed from: private */
    public String s;
    io.reactivex.disposables.b s0;
    private String t;
    io.reactivex.disposables.b t0;
    private String u;
    io.reactivex.disposables.b u0;
    /* access modifiers changed from: private */
    public String v;
    com.leedarson.newui.repos.n v0 = new com.leedarson.newui.repos.n();
    private String w;
    int w0 = 0;
    /* access modifiers changed from: private */
    public boolean x = false;
    boolean x0 = true;
    /* access modifiers changed from: private */
    public long y = 0;
    private boolean y0 = false;
    private String z;
    private String z0 = "";

    static /* synthetic */ int E(b6 x02) {
        int i2 = x02.o;
        x02.o = i2 + 1;
        return i2;
    }

    static /* synthetic */ void H(b6 x02, String x1) {
        Class[] clsArr = {b6.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x02, x1}, (Object) null, changeQuickRedirect, true, 2749, clsArr, Void.TYPE).isSupported) {
            x02.u0(x1);
        }
    }

    static /* synthetic */ void J(b6 x02, IpcDeviceBean x1) {
        Class[] clsArr = {b6.class, IpcDeviceBean.class};
        if (!PatchProxy.proxy(new Object[]{x02, x1}, (Object) null, changeQuickRedirect, true, 2750, clsArr, Void.TYPE).isSupported) {
            x02.t1(x1);
        }
    }

    static /* synthetic */ int M(b6 x02) {
        int i2 = x02.p;
        x02.p = i2 - 1;
        return i2;
    }

    static /* synthetic */ void N(b6 x02, String x1) {
        Class[] clsArr = {b6.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x02, x1}, (Object) null, changeQuickRedirect, true, 2751, clsArr, Void.TYPE).isSupported) {
            x02.r(x1);
        }
    }

    static /* synthetic */ void Q(b6 x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 2747, new Class[]{b6.class}, Void.TYPE).isSupported) {
            x02.e0();
        }
    }

    static /* synthetic */ int V(b6 x02) {
        int i2 = x02.J;
        x02.J = i2 + 1;
        return i2;
    }

    static /* synthetic */ void v(b6 x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 2748, new Class[]{b6.class}, Void.TYPE).isSupported) {
            x02.V1();
        }
    }

    public b6(c6 view, NewLiveFragment fragment) {
        super(view, fragment);
        if (fragment != null) {
            try {
                this.z = fragment.getContext().getFilesDir().getPath() + "/web/static/media/";
                File dirFile = new File(this.z);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void C1(boolean webRTC) {
        this.r = webRTC;
    }

    /* compiled from: NewLivePresenter */
    public class k implements com.leedarson.smartcamera.listener.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void e(int state) {
            if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 2752, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("onStateChange:" + state);
                if (!b6.this.x) {
                    switch (state) {
                        case -2:
                            if (b6.this.m() != null) {
                                ((c6) b6.this.m()).V(-111, "TUTK超过最大连接");
                            }
                            ((c6) b6.this.m()).showToast(R$string.max_connecttion_err);
                            com.leedarson.newui.repoter.i.c().w(b6.this.v, "TUTK超过最大连接");
                            return;
                        case -1:
                            if (b6.this.m() != null) {
                                ((c6) b6.this.m()).V(-109, "TUTK连接断开: 连接超时被主动断开");
                            }
                            com.leedarson.newui.repoter.i.c().w(b6.this.v, "TUTK连接断开");
                            return;
                        case 0:
                            if (b6.this.m() != null) {
                                ((c6) b6.this.m()).i();
                            }
                            b6.this.W = System.currentTimeMillis();
                            return;
                        case 1:
                            com.leedarson.newui.repoter.i.c().x(b6.this.v, System.currentTimeMillis() - b6.this.W, "连接成功");
                            com.leedarson.manager.a.i().c(b6.this.s, b6.this.f);
                            b6.this.d2();
                            if (b6.this.m() != null) {
                                ((c6) b6.this.m()).T(1);
                            }
                            b6.Q(b6.this);
                            return;
                        case 2:
                            com.leedarson.newui.repoter.i.c().A(b6.this.v, "TUTK拉流成功");
                            if (b6.this.m() != null) {
                                ((c6) b6.this.m()).T(2);
                            }
                            b6.this.C.onNext(true);
                            return;
                        case 3:
                            if (b6.this.m() != null) {
                                ((c6) b6.this.m()).V(IMediaPlayer.MEDIA_ERROR_TIMED_OUT, "TUTK拉流失败");
                            }
                            com.leedarson.newui.repoter.i.c().z(b6.this.v, "TUTK拉流失败");
                            return;
                        case 4:
                            b6.this.X = System.currentTimeMillis();
                            b6.this.P.firstFrameReceiveTime = System.currentTimeMillis();
                            com.leedarson.newui.repoter.i.c().B(b6.this.v, "TUTK收到首个I帧");
                            return;
                        case 6:
                            com.leedarson.newui.repoter.i c = com.leedarson.newui.repoter.i.c();
                            String t = b6.this.v;
                            c.y(t, "TUTK拉不到流，分辨率=" + b6.this.I + ".自动切SD");
                            if (b6.this.I != 5) {
                                b6.this.B1(5, false);
                                return;
                            }
                            try {
                                if (b6.this.J < 10) {
                                    com.leedarson.newui.repoter.i.c().j(((NewLiveFragment) b6.this.l()).getContext(), b6.this.v, "TUTK_ERROR_NO_DATA", "收不到视频流");
                                    b6.V(b6.this);
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

        public void b(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2753, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("onConnectError:" + code);
                b6 b6Var2 = b6.this;
                b6Var2.O = code;
                if (code == -90) {
                    try {
                        if (!(b6Var2.l() == null || ((NewLiveFragment) b6.this.l()).P4 == null)) {
                            com.leedarson.newui.repoter.i.c().u(b6.this.v, code, ((NewLiveFragment) b6.this.l()).f5, ((NewLiveFragment) b6.this.l()).P4.online.booleanValue());
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                com.leedarson.newui.repoter.i.c().t(b6.this.v, code);
            }
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2754, new Class[]{Long.TYPE, byte[].class, cls, cls}, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (!b6.this.x) {
                    b6.this.C.onNext(false);
                    if (b6.this.h != null) {
                        b6.this.h.Z(timestap, data, len, codec);
                    }
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2755, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (!b6.this.x && b6.this.h != null) {
                    b6.this.h.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
        }
    }

    public void A0(String str, String str2, String str3, String str4, String str5) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, str5}, this, changeQuickRedirect, false, 2647, clsArr, Void.TYPE).isSupported) {
            String p2pId = str2;
            String password = str4;
            String id = str;
            String account = str3;
            String dtls = str5;
            timber.log.a.c("initTutkChannel: start", new Object[0]);
            this.s = p2pId;
            this.t = account;
            this.u = password;
            this.v = id;
            this.w = dtls;
            com.leedarson.manager.a.i().b(id, p2pId);
            com.leedarson.smartcamera.sdk.a tempChannel = com.leedarson.manager.a.i().l(p2pId);
            if (tempChannel == null) {
                this.f = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.f);
            } else if (!account.equals(tempChannel.E0()) || !password.equals(tempChannel.H0())) {
                this.f = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.f);
            } else {
                this.f = tempChannel;
            }
            timber.log.a.c("initTutkChannel: end-" + this.f, new Object[0]);
            try {
                if (((NewLiveFragment) l()).P4.modelId == null || !((NewLiveFragment) l()).P4.modelId.contains("IPC.A001058")) {
                    this.f.E1(true);
                } else {
                    this.f.E1(false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.f != null) {
                if (com.alibaba.android.arouter.utils.e.b(dtls) || !dtls.equals("1")) {
                    this.f.D1(0);
                } else {
                    this.f.D1(1);
                }
                this.f.registerTutkListener(this.Y);
                if (l() != null) {
                    this.f.B1(DeviceIdUtils.getDeviceId(((NewLiveFragment) l()).getContext()));
                }
            }
        }
    }

    public void b2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2648, new Class[0], Void.TYPE).isSupported) {
            this.O = 0;
            this.P.init();
            if (this.f != null && com.leedarson.base.utils.networkutil.a.a(BaseApplication.b())) {
                if (!(l() == null || ((NewLiveFragment) l()).D4 == null || this.h == null)) {
                    g1("codecReady start:");
                    this.h.H(((NewLiveFragment) l()).D4.getHolder().getSurface(), new u());
                }
                if (!(l() == null || ((NewLiveFragment) l()).getContext() == null)) {
                    com.leedarson.newui.repoter.i.c().f(((NewLiveFragment) l()).getContext(), this.v);
                }
                ((c6) m()).i();
                com.leedarson.newui.repoter.i.c().s(this.v, this.s, this.t, this.u, this.w, "开始连接");
                this.f.x0();
                ((c6) m()).T(0);
            } else if (!com.leedarson.base.utils.networkutil.a.a(BaseApplication.b())) {
                ((c6) m()).i();
                ((c6) m()).T(0);
                p1(this.Z);
                io.reactivex.disposables.b I2 = io.reactivex.e.w(1).g(2, TimeUnit.SECONDS).c(com.leedarson.base.http.observer.l.c()).I(new m4(this), t4.c);
                this.Z = I2;
                b(I2);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class u implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;

        u() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2775, new Class[0], Void.TYPE).isSupported) {
                b6.this.g1("codecReady end:");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a1 */
    public /* synthetic */ void b1(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 2746, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            this.E.k("Fail 此时网链路探测不通，已提示用户重新连接");
            com.leedarson.newui.repoter.i.c().t(this.v, -41);
            ((c6) m()).V(-102, "此时网链路探测不通，已提示用户重新连接");
        }
    }

    static /* synthetic */ void c1(Throwable throwable) {
    }

    public void z1(int rate) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(rate)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2650, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.f) != null) {
            aVar.F1(rate);
        }
    }

    public void d2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2651, new Class[0], Void.TYPE).isSupported) {
            if (this.f != null) {
                g1("tutkStartLive startLive2:" + this.f.G0());
                this.f.K1();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2652(0xa5c, float:3.716E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.sdk.a r1 = r0.f
            if (r1 == 0) goto L_0x0040
            com.tutk.IOTC.St_SInfoEx r1 = r1.U0()
            com.leedarson.newui.repoter.i r2 = com.leedarson.newui.repoter.i.c()
            java.lang.String r3 = r0.v
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "tutk连接信息："
            r4.append(r5)
            java.lang.String r5 = r1.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.v(r3, r4)
            r0.s0()
        L_0x0040:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.e0():void");
    }

    /* compiled from: NewLivePresenter */
    public class e0 implements com.leedarson.smartcamera.listener.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        e0() {
        }

        public void onSuccess(int resolution) {
            Object[] objArr = {new Integer(resolution)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2795, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                b6.this.g1("getResolution onSuccess");
                int unused = b6.this.I = resolution;
                ((c6) b6.this.m()).E(resolution);
            }
        }
    }

    public void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2653, new Class[0], Void.TYPE).isSupported) {
            if (this.f != null) {
                this.b0 = new e0();
            }
            this.f.M0(this.b0);
        }
    }

    public void g1(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 2654, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("NewLivePresenter").a(msg, new Object[0]);
        }
    }

    public void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2655, new Class[0], Void.TYPE).isSupported) {
            this.h = new com.leedarson.smartcamera.codec.c();
            E1(true);
            this.h.u(new m0());
        }
    }

    /* compiled from: NewLivePresenter */
    public class m0 implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        m0() {
        }

        public void W0(long currentTime, int decFps, int showFps) {
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2833, new Class[0], Void.TYPE).isSupported) {
                b6.this.g1("CodecCallback playStart");
                try {
                    ((c6) b6.this.m()).p();
                    ((c6) b6.this.m()).g();
                    long nowTime = System.currentTimeMillis();
                    com.leedarson.newui.repoter.i c2 = com.leedarson.newui.repoter.i.c();
                    String t = b6.this.v;
                    b6 b6Var = b6.this;
                    c2.r(t, nowTime - b6Var.X, nowTime - b6Var.W, "开始播放");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void L0() {
        }

        public void B0(byte[] data, int length) {
            if (!PatchProxy.proxy(new Object[]{data, new Integer(length)}, this, changeQuickRedirect, false, 2834, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
                for (int i = 0; i < length; i += 20) {
                    ((c6) b6.this.m()).v((short) data[i]);
                }
                b6.this.s1(data, length);
            }
        }

        public void B(byte[] data, int width, int height) {
            Object[] objArr = {data, new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2835, new Class[]{byte[].class, cls, cls}, Void.TYPE).isSupported) {
                if (System.currentTimeMillis() - b6.this.c0 > 1000) {
                    b6.this.x0(data, width, height, true);
                    long unused = b6.this.c0 = System.currentTimeMillis();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void G1(android.view.Surface r9) {
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
            r5 = 2657(0xa61, float:3.723E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r8
            com.leedarson.smartcamera.codec.c r2 = r1.h
            if (r2 == 0) goto L_0x0052
            if (r9 == 0) goto L_0x0052
            boolean r3 = r1.q
            java.lang.String r4 = "surfaceView"
            if (r3 != 0) goto L_0x004a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "SUFUN.surfaceView.Created.createCodec"
            r2.append(r3)
            int r3 = r9.hashCode()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.leedarson.base.logger.a.c(r4, r2)
            com.leedarson.smartcamera.codec.c r2 = r1.h
            r2.B(r9, r0)
            r1.q = r0
            goto L_0x0052
        L_0x004a:
            r2.O(r9)
            java.lang.String r0 = "SUFUN.surfaceView.Created.resumeCodec"
            com.leedarson.base.logger.a.c(r4, r0)
        L_0x0052:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.G1(android.view.Surface):void");
    }

    public void O1() {
        com.leedarson.smartcamera.codec.c cVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2658, new Class[0], Void.TYPE).isSupported && (cVar = this.h) != null) {
            cVar.U();
        }
    }

    public void d0(Surface surface, int width, int height) {
        Object[] objArr = {surface, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2660, new Class[]{Surface.class, cls, cls}, Void.TYPE).isSupported) {
            if (this.h != null && surface != null) {
                com.leedarson.base.logger.a.c("surfaceView", "SUFUN.surfaceView.changetSurface" + surface.hashCode());
                this.h.z(surface, width, height);
                if (this.q) {
                    this.h.J();
                }
            }
        }
    }

    public void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2661, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.sdk.a aVar = this.f;
            if (aVar != null) {
                aVar.unRegisterTutkListener(this.Y);
                this.f.Q1();
            }
            com.leedarson.smartcamera.codec.c cVar = this.h;
            if (cVar != null) {
                cVar.I();
                this.h = null;
            }
            Handler handler = this.g0;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            HumanDetectSDK humanDetectSDK = this.A0;
            if (humanDetectSDK != null) {
                humanDetectSDK.release();
            }
            com.leedarson.smartcamera.kvswebrtc.utils.d.a().g();
        }
    }

    public void l1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2662, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.repoter.i.c().i(this.v);
            com.leedarson.newui.report_repos.k kVar = this.D;
            if (kVar != null) {
                kVar.q();
            }
            io.reactivex.disposables.b bVar = this.t0;
            if (bVar != null && !bVar.isDisposed()) {
                this.t0.dispose();
            }
            io.reactivex.disposables.b bVar2 = this.u0;
            if (bVar2 != null && !bVar2.isDisposed()) {
                this.u0.dispose();
            }
            m1();
            com.leedarson.smartcamera.sdk.a aVar = this.f;
            if (aVar != null) {
                aVar.u0();
            }
            com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
            if (f0Var != null) {
                f0Var.F0();
            }
            this.f = null;
            this.Y = null;
            this.g = null;
            this.h = null;
            this.a0 = null;
            this.m0 = null;
            this.n0 = null;
            this.b0 = null;
            this.f0 = null;
            p1(this.R);
            onDestroy();
        }
    }

    /* compiled from: NewLivePresenter */
    public class n0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        n0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2856, new Class[0], Void.TYPE).isSupported) {
                b6.this.g1("hideRunnable");
                try {
                    if (b6.this.m() != null) {
                        ((c6) b6.this.m()).a();
                        ((c6) b6.this.m()).showToast(R$string.timeout);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void t1(IpcDeviceBean deviceBean) {
        if (!PatchProxy.proxy(new Object[]{deviceBean}, this, changeQuickRedirect, false, 2663, new Class[]{IpcDeviceBean.class}, Void.TYPE).isSupported) {
            g1("当前流码率类型： deviceBean.props.StreamType=" + deviceBean.props.StreamType);
            if ("0".equals(deviceBean.props.StreamType)) {
                B1(1, false);
            } else if ("1".equals(deviceBean.props.StreamType)) {
                B1(5, false);
            } else if (ExifInterface.GPS_MEASUREMENT_2D.equals(deviceBean.props.StreamType)) {
                B1(16, false);
            } else if (deviceBean.pushBean.isDefaultHD()) {
                B1(1, false);
            } else {
                B1(5, false);
            }
        }
    }

    public void B1(int resolution, boolean needLoading) {
        Object[] objArr = {new Integer(resolution), new Byte(needLoading ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2664, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (needLoading) {
                ((c6) m()).b();
                Handler handler = this.g0;
                if (handler != null) {
                    handler.removeCallbacksAndMessages((Object) null);
                    this.g0.postDelayed(this.e0, 10000);
                }
            }
            com.leedarson.newui.repoter.d dVar = this.F;
            dVar.u("resolution=" + resolution);
            o0 o0Var = new o0(resolution, needLoading);
            this.f0 = o0Var;
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var != null) {
                    f0Var.g3(resolution, o0Var);
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.sdk.a aVar = this.f;
            if (aVar != null) {
                aVar.G1(resolution, o0Var);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class o0 implements com.leedarson.smartcamera.listener.k {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;
        final /* synthetic */ boolean b;

        o0(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2857, new Class[0], Void.TYPE).isSupported) {
                int unused = b6.this.I = this.a;
                b6.this.g1("setResolution, onSuccess");
                if (this.b) {
                    ((c6) b6.this.m()).a();
                    Handler handler = b6.this.g0;
                    if (handler != null) {
                        handler.removeCallbacksAndMessages((Object) null);
                    }
                }
                ((c6) b6.this.m()).E(this.a);
                com.leedarson.newui.repoter.d dVar = b6.this.F;
                dVar.v("resolution成功---> isWebRtc=" + b6.this.r);
            }
        }
    }

    public void J1(com.leedarson.smartcamera.listener.l listener, com.leedarson.smartcamera.listener.g onRadarDataReportListener) {
        com.leedarson.smartcamera.kvswebrtc.f0 f0Var;
        Class[] clsArr = {com.leedarson.smartcamera.listener.l.class, com.leedarson.smartcamera.listener.g.class};
        if (!PatchProxy.proxy(new Object[]{listener, onRadarDataReportListener}, this, changeQuickRedirect, false, 2665, clsArr, Void.TYPE).isSupported) {
            if (this.r && (f0Var = this.g) != null) {
                f0Var.o3(listener, onRadarDataReportListener);
            }
            if (this.H == null) {
                this.H = new com.leedarson.newui.view.radar.d();
            }
        }
    }

    public void T1(String str, com.leedarson.smartcamera.listener.m listener) {
        IpcDeviceBean ipcDeviceBean;
        com.leedarson.smartcamera.kvswebrtc.f0 f0Var;
        Class[] clsArr = {String.class, com.leedarson.smartcamera.listener.m.class};
        if (!PatchProxy.proxy(new Object[]{str, listener}, this, changeQuickRedirect, false, 2666, clsArr, Void.TYPE).isSupported) {
            if (this.r && (ipcDeviceBean = this.K) != null && ipcDeviceBean.hasPath() && (f0Var = this.g) != null) {
                f0Var.v3(listener);
            }
            com.leedarson.newui.view.radar.d dVar = this.H;
            if (dVar != null) {
                dVar.a();
            }
        }
    }

    public void y1(boolean videoTrackEnable, boolean audioTrackEnable) {
        this.T = videoTrackEnable;
        this.U = audioTrackEnable;
    }

    public void j1(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 2667, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var == null) {
                    return;
                }
                if (this.L) {
                    f0Var.w2(this.T, this.U);
                    b0(ref);
                    return;
                }
                f0Var.I2(false);
                io.reactivex.disposables.b bVar = this.r0;
                if (bVar != null && !bVar.isDisposed()) {
                    this.r0.dispose();
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.sdk.a aVar = this.f;
            if (aVar != null) {
                aVar.b1();
                this.f.Q1();
            }
            com.leedarson.smartcamera.codec.c cVar = this.h;
            if (cVar != null) {
                cVar.G();
            }
        }
    }

    public void A1(IpcWebrtcSurfaceView webrtcSurfaceView, String ref) {
        com.leedarson.smartcamera.kvswebrtc.f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{webrtcSurfaceView, ref}, this, changeQuickRedirect, false, 2668, new Class[]{IpcWebrtcSurfaceView.class, String.class}, Void.TYPE).isSupported) {
            boolean isResume = false;
            try {
                isResume = ((NewLiveFragment) l()).V3();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Log.d("NewLivePresenter", "[hyf]setRePlayListener ===== " + this.L + " isResume:" + isResume);
            if (isResume && this.L && (f0Var = this.g) != null && f0Var.t1()) {
                com.leedarson.log.f.a("setRePlayListener:" + this.L + " ,ref=" + ref);
                this.g.C0(f0.q.LIVING);
                ((c6) m()).T(2);
                this.y0 = true;
                this.g.e3(webrtcSurfaceView);
                webrtcSurfaceView.restoreFirstFrameRendered();
                this.g.Q2(true);
            }
        }
    }

    public void r1(String ref, IpcDeviceBean deviceBean, IpcWebrtcSurfaceView webrtcSurfaceView) {
        if (!PatchProxy.proxy(new Object[]{ref, deviceBean, webrtcSurfaceView}, this, changeQuickRedirect, false, 2669, new Class[]{String.class, IpcDeviceBean.class, IpcWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            this.x = false;
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var != null) {
                    f0Var.O0(ref);
                }
                if (this.L) {
                    if (deviceBean.isLowPowerDevice()) {
                        f2(deviceBean.id);
                    }
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var2 = this.g;
                    if (f0Var2 == null || !f0Var2.t1()) {
                        g1("sufun.data 22222222---》");
                        D0(deviceBean, (KVSParamBean) null, webrtcSurfaceView, false, ref);
                        return;
                    }
                    g1("sufun.data 11111111---》");
                    A1(webrtcSurfaceView, "直播间resumeLive(恢复直播)");
                    return;
                }
                if (this.g != null) {
                    g1("webrtc resumeLive:" + this.g.t1());
                    this.g.I2(false);
                }
                b(io.reactivex.e.w(1).c(com.leedarson.base.http.observer.l.c()).I(new f4(this, ref), q4.c));
                return;
            }
            com.leedarson.smartcamera.sdk.a aVar = this.f;
            if (aVar != null) {
                aVar.registerTutkListener(this.Y);
                this.f.m1();
                g1("startLive2:" + this.f.G0());
                try {
                    if (((NewLiveFragment) l()).P4.props.TurnOnOff) {
                        b2();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            com.leedarson.smartcamera.codec.c cVar = this.h;
            if (cVar != null) {
                cVar.J();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: R0 */
    public /* synthetic */ void S0(String ref, Integer num) {
        Class[] clsArr = {String.class, Integer.class};
        if (!PatchProxy.proxy(new Object[]{ref, num}, this, changeQuickRedirect, false, 2745, clsArr, Void.TYPE).isSupported) {
            ((c6) m()).i();
            ((c6) m()).Y(v0(this.v));
            IpcDeviceBean ipcDeviceBean = this.K;
            p0(ipcDeviceBean, "resumeLive." + ref);
        }
    }

    static /* synthetic */ void T0(Throwable throwable) {
    }

    public void c2(String deviceId, boolean TurnOnOff) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(TurnOnOff ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2670, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("TurnOnOff", TurnOnOff);
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                j0(deviceId, _payload, new p0(TurnOnOff));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class p0 implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;

        p0(boolean z) {
            this.a = z;
        }

        public void onResult(int i, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i), str}, this, changeQuickRedirect, false, 2859, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.h("deviceControlSetDevAttrReq IPC摄像头TurnOnOff切换成功" + this.a, new Object[0]);
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2860, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlSetDevAttrReq IPC摄像头TurnOnOff切换(失败)" + this.a + ", tip" + tip, new Object[0]);
            }
        }
    }

    private void j0(String deviceId, JSONObject payloadObj, OnControlRespListener respListener) {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{deviceId, payloadObj, respListener}, this, changeQuickRedirect, false, 2671, new Class[]{String.class, JSONObject.class, OnControlRespListener.class}, Void.TYPE).isSupported) {
            try {
                ((c6) m()).b();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    String _topic = "iot/v1/c/deviceId/device/setDevAttrReq".replace("deviceId", deviceId);
                    MqttMessageConfigBean _config = new MqttMessageConfigBean();
                    IpcDeviceBean ipcDeviceBean = this.K;
                    if (!com.alibaba.android.arouter.utils.e.b(ipcDeviceBean == null ? "" : ipcDeviceBean.simpleVersion)) {
                        z2 = true;
                    }
                    _config.isSupportSimpleVersion = z2;
                    jsonObject.put(FirebaseAnalytics.Param.METHOD, (Object) "setDevAttrReq");
                    jsonObject.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
                    jsonObject.put("payload", (Object) payloadObj);
                    _mqttService.publish(_topic, _config, jsonObject, new q0(respListener));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class q0 implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ OnControlRespListener a;

        q0(OnControlRespListener onControlRespListener) {
            this.a = onControlRespListener;
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 2861, clsArr, Void.TYPE).isSupported) {
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).a();
                    this.a.onResult(200, callbackData.toString());
                }
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2862, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).a();
                    this.a.onFail(code, taskId, desc);
                }
            }
        }
    }

    private void h0(String deviceId, JSONObject actionsObj, OnControlRespListener respListener, long timeOutLimitOfMs) {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{deviceId, actionsObj, respListener, new Long(timeOutLimitOfMs)}, this, changeQuickRedirect, false, 2672, new Class[]{String.class, JSONObject.class, OnControlRespListener.class, Long.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                jsonObject.put("actions", (Object) actionsObj);
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    String _topic = "iot/v1/s/userId/device/devActionReq".replace("userId", BaseApplication.b().d());
                    MqttMessageConfigBean _config = new MqttMessageConfigBean();
                    _config.timeOutLimitOfMs = timeOutLimitOfMs;
                    IpcDeviceBean ipcDeviceBean = this.K;
                    if (!com.alibaba.android.arouter.utils.e.b(ipcDeviceBean == null ? "" : ipcDeviceBean.simpleVersion)) {
                        z2 = true;
                    }
                    _config.isSupportSimpleVersion = z2;
                    JSONObject _messageObj = new JSONObject();
                    _messageObj.put(FirebaseAnalytics.Param.METHOD, (Object) "devActionReq");
                    _messageObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
                    _messageObj.put("payload", (Object) actionsObj);
                    _mqttService.publish(_topic, _config, _messageObj, new r0(respListener));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class r0 implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ OnControlRespListener a;

        r0(OnControlRespListener onControlRespListener) {
            this.a = onControlRespListener;
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 2863, clsArr, Void.TYPE).isSupported) {
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).a();
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
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2864, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).a();
                    this.a.onFail(code, taskId, desc);
                }
            }
        }
    }

    private void i0(String deviceId, JSONObject extendsObj) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, extendsObj}, this, changeQuickRedirect, false, 2673, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                jsonObject.put("extends", (Object) extendsObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Device", H5ActionName.ACTION_DEVICE_CONTROL, jsonObject.toString()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void K1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2674, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.i == null) {
                this.i = new SimpleDateFormat("yyyyMMddHHmmss");
            }
            String str = com.leedarson.smartcamera.utils.f.b(((NewLiveFragment) l()).getContext()) + (deviceId + "_" + this.i.format(new Date()) + ".mp4");
            this.j = str;
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var == null) {
                    return;
                }
                if (f0Var.p3(str)) {
                    ((c6) m()).d();
                } else {
                    ((c6) m()).j();
                }
            } else {
                com.leedarson.smartcamera.codec.c cVar = this.h;
                if (cVar != null) {
                    cVar.S(str, new a());
                }
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class a implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2756, new Class[0], Void.TYPE).isSupported) {
                ((c6) b6.this.m()).d();
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2757, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((c6) b6.this.m()).j();
            }
        }
    }

    public void U1(IPCLiveAction iPCLiveAction) {
        if (!PatchProxy.proxy(new Object[]{iPCLiveAction}, this, changeQuickRedirect, false, 2675, new Class[]{IPCLiveAction.class}, Void.TYPE).isSupported) {
            if (m() != null) {
                ((c6) m()).c();
            }
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var != null) {
                    f0Var.w3();
                    V1();
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.h;
            if (cVar != null) {
                cVar.X(new b());
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class b implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2758, new Class[0], Void.TYPE).isSupported) {
                b6.v(b6.this);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2759, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((c6) b6.this.m()).showToast(R$string.player_videotape_error);
            }
        }
    }

    private void V1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2676, new Class[0], Void.TYPE).isSupported) {
            try {
                ((c6) m()).showToast(R$string.player_videotape_sucess);
                ((c6) m()).c();
                MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(BaseApplication.b().getApplicationContext(), new c());
                this.k0 = mediaScannerConnection;
                mediaScannerConnection.connect();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class c implements MediaScannerConnection.MediaScannerConnectionClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onMediaScannerConnected() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2760, new Class[0], Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.k0.scanFile(b6Var.j, (String) null);
            }
        }

        public void onScanCompleted(String str, Uri uri) {
            Class[] clsArr = {String.class, Uri.class};
            if (!PatchProxy.proxy(new Object[]{str, uri}, this, changeQuickRedirect, false, 2761, clsArr, Void.TYPE).isSupported) {
                b6.this.k0.disconnect();
            }
        }
    }

    public String v0(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2677, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
            if (!com.alibaba.android.arouter.utils.e.b(this.z)) {
                return this.z + deviceId + userId + "_tempPhoto.jpg";
            } else if (l() == null) {
                return "";
            } else {
                return ((NewLiveFragment) l()).getContext().getFilesDir().getPath() + "/web/static/media/" + deviceId + userId + "_tempPhoto.jpg";
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public void F1(String deviceId, boolean isThum, IpcWebrtcSurfaceView ipcWebrtcSurfaceView) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(isThum ? (byte) 1 : 0), ipcWebrtcSurfaceView}, this, changeQuickRedirect, false, 2678, new Class[]{String.class, Boolean.TYPE, IpcWebrtcSurfaceView.class}, Void.TYPE).isSupported) {
            try {
                if (this.i == null) {
                    this.i = new SimpleDateFormat("yyyyMMddHHmmss");
                }
                if (isThum) {
                    this.k = v0(deviceId);
                } else {
                    this.k = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (deviceId + "_" + this.i.format(new Date()) + ".jpg");
                }
                g1("snapShot:" + this.k);
                File dirFile = new File(this.k).getParentFile();
                if (dirFile != null && !dirFile.exists()) {
                    dirFile.mkdirs();
                }
                if (this.r) {
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                    if (f0Var != null) {
                        f0Var.B0(this.k, new d(isThum, deviceId));
                        return;
                    }
                    return;
                }
                com.leedarson.smartcamera.codec.c cVar = this.h;
                if (cVar != null) {
                    cVar.M(this.k, new e(isThum, deviceId));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class d implements com.leedarson.smartcamera.kvswebrtc.h0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;
        final /* synthetic */ String b;

        d(boolean z, String str) {
            this.a = z;
            this.b = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2762, new Class[0], Void.TYPE).isSupported) {
                b6.this.g1("snapShot onSuccess:");
                if (!this.a) {
                    ((c6) b6.this.m()).f(b6.this.k);
                    return;
                }
                b6 b6Var = b6.this;
                b6Var.e2(this.b, b6Var.k);
            }
        }

        public void a(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2763, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("snapShot onError:  code=" + code);
                if (!this.a) {
                    ((c6) b6.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class e implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;
        final /* synthetic */ String b;

        e(boolean z, String str) {
            this.a = z;
            this.b = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2764, new Class[0], Void.TYPE).isSupported) {
                b6.this.g1("snapShot onSuccess:");
                if (!this.a) {
                    ((c6) b6.this.m()).f(b6.this.k);
                    return;
                }
                b6 b6Var = b6.this;
                b6Var.e2(this.b, b6Var.k);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2765, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (!this.a) {
                    ((c6) b6.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    public void M1(String talkMode) {
        if (!PatchProxy.proxy(new Object[]{talkMode}, this, changeQuickRedirect, false, 2679, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.l0 = false;
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var != null) {
                    f0Var.q3(new f());
                }
            } else if (this.f != null && m() != null) {
                try {
                    com.leedarson.newui.repoter.i.c().h(BaseApplication.b(), this.v);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ((c6) m()).b();
                if (com.alibaba.android.arouter.utils.e.b(talkMode) || !talkMode.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    this.f.H1(1);
                } else {
                    this.f.H1(2);
                }
                com.leedarson.newui.repoter.i.c().m(this.v, this.f.S0());
                g gVar = new g(System.currentTimeMillis(), talkMode);
                this.m0 = gVar;
                this.f.O1(gVar);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class f implements com.leedarson.smartcamera.listener.n {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2766, new Class[0], Void.TYPE).isSupported) {
                ((c6) b6.this.m()).g1();
                boolean unused = b6.this.l0 = true;
            }
        }

        public void a(int code) {
        }

        public void b(short[] data, int length, int db) {
        }
    }

    /* compiled from: NewLivePresenter */
    public class g implements com.leedarson.smartcamera.listener.n {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ String b;

        g(long j, String str) {
            this.a = j;
            this.b = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2767, new Class[0], Void.TYPE).isSupported) {
                boolean unused = b6.this.l0 = true;
                com.leedarson.newui.repoter.i.c().q(b6.this.v, System.currentTimeMillis() - this.a);
                ((c6) b6.this.m()).g1();
                ((c6) b6.this.m()).a();
            }
        }

        public void a(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2768, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.i.c().l(b6.this.v, code);
                if (code == 100) {
                    ((c6) b6.this.m()).showToast(R$string.speak_error);
                } else {
                    ((c6) b6.this.m()).showToast(R$string.player_error_1);
                }
                ((c6) b6.this.m()).a();
            }
        }

        public void b(short[] sArr, int i, int db) {
            Object[] objArr = {sArr, new Integer(i), new Integer(db)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2769, new Class[]{short[].class, cls, cls}, Void.TYPE).isSupported) {
                if (com.alibaba.android.arouter.utils.e.b(this.b) || !this.b.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    ((c6) b6.this.m()).v0((float) ((((double) db) * 1.0d) / 1000.0d));
                }
            }
        }
    }

    public void X1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2680, new Class[0], Void.TYPE).isSupported) {
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var != null) {
                    f0Var.z3(new h());
                }
            } else if (this.f != null && m() != null) {
                try {
                    com.leedarson.newui.repoter.i.c().g(BaseApplication.b(), this.v);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ((c6) m()).b();
                com.leedarson.newui.repoter.i.c().n(this.v);
                i iVar = new i();
                this.n0 = iVar;
                this.f.T1(iVar);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class h implements com.leedarson.smartcamera.listener.n {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2770, new Class[0], Void.TYPE).isSupported) {
                try {
                    ((c6) b6.this.m()).F0();
                    if (b6.this.l0) {
                        ((c6) b6.this.m()).Z0();
                        boolean unused = b6.this.l0 = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int code) {
        }

        public void b(short[] data, int length, int db) {
        }
    }

    /* compiled from: NewLivePresenter */
    public class i implements com.leedarson.smartcamera.listener.n {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2771, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (b6.this.l0) {
                        ((c6) b6.this.m()).Z0();
                        boolean unused = b6.this.l0 = false;
                    }
                    com.leedarson.newui.repoter.i.c().p(b6.this.v);
                    ((c6) b6.this.m()).a();
                    ((c6) b6.this.m()).F0();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int code) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code)}, this, changeQuickRedirect, false, 2772, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                try {
                    if (b6.this.l0) {
                        ((c6) b6.this.m()).Z0();
                        boolean unused = b6.this.l0 = false;
                    }
                    com.leedarson.newui.repoter.i.c().o(b6.this.v, code);
                    ((c6) b6.this.m()).a();
                    ((c6) b6.this.m()).showToast(R$string.stop_talk_error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void b(short[] data, int length, int db) {
        }
    }

    public void e1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2681, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("LightOnOff", 0);
                this.E.f("关闭IPC 电灯");
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                j0(deviceId, _payload, new j());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class j implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2773, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("NewLivePresenter").h("deviceControlSetDevAttrReq IPC摄像头灯打关闭(成功)", new Object[0]);
                if (code == 200) {
                    ((c6) b6.this.m()).V0(false);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2774, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlSetDevAttrReq IPC摄像头灯关闭(失败)" + tip, new Object[0]);
            }
        }
    }

    public void f1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2682, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("LightOnOff", 1);
                this.E.f("打开 IPC 电灯");
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                j0(deviceId, _payload, new l());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class l implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2776, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("NewLivePresenter").m("deviceControlSetDevAttrReq IPC摄像头灯打开(成功)", new Object[0]);
                if (code == 200) {
                    ((c6) b6.this.m()).V0(true);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2777, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlSetDevAttrReq IPC摄像头灯打开(失败)" + tip, new Object[0]);
            }
        }
    }

    public void Y1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2683, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("trackingMode", 0);
                this.E.f("关闭IPC人型自动追踪");
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                j0(deviceId, _payload, new m());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class m implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2778, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("NewLivePresenter").m("deviceControlSetDevAttrReq 人型追踪关闭(成功)", new Object[0]);
                if (code == 200) {
                    ((c6) b6.this.m()).c0(false);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2779, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlSetDevAttrReq 人型追踪关闭(失败)" + tip, new Object[0]);
            }
        }
    }

    public void Z1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2684, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("trackingMode", 1);
                this.E.f("打开IPC人型自动追踪");
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                j0(deviceId, _payload, new n());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class n implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2780, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("NewLivePresenter").m("deviceControlSetDevAttrReq 人型追踪打开(成功)", new Object[0]);
                if (code == 200) {
                    ((c6) b6.this.m()).c0(true);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2781, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlSetDevAttrReq 人型追踪打开(失败)" + tip, new Object[0]);
            }
        }
    }

    public void Z(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2685, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveCancelAlarm");
                JSONObject actionsObj = new JSONObject();
                actionsObj.put("devId", (Object) deviceId);
                actionsObj.put("action", (Object) "playSound");
                JSONArray inArray = new JSONArray();
                inArray.put(0);
                inArray.put(1);
                inArray.put(30);
                actionsObj.put("in", (Object) inArray);
                h0(deviceId, actionsObj, new o(), 10000);
                ((c6) m()).b();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class o implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2782, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                if (code == 200) {
                    ((c6) b6.this.m()).A0(false);
                    timber.log.a.g("NewLivePresenter").m("deviceControlByDevActionReq  关闭报警成功", new Object[0]);
                }
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2783, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlByDevActionReq  关闭报警(失败)  tip=" + tip, new Object[0]);
            }
        }
    }

    public void a0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2686, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickAlarm");
                JSONObject actionsObj = new JSONObject();
                actionsObj.put("devId", (Object) deviceId);
                actionsObj.put("action", (Object) "playSound");
                JSONArray inArray = new JSONArray();
                inArray.put(1);
                inArray.put(1);
                inArray.put(30);
                actionsObj.put("in", (Object) inArray);
                com.leedarson.newui.repoter.e eVar = this.E;
                eVar.i("Turn On Alarm  params=" + inArray.toString());
                h0(deviceId, actionsObj, new p(), 10000);
                ((c6) m()).b();
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class p implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2784, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                if (code == 200) {
                    ((c6) b6.this.m()).A0(true);
                    timber.log.a.g("NewLivePresenter").m("deviceControlByDevActionReq  打开报警成功", new Object[0]);
                }
                b6.this.E.j("IPC警报状态设置成功");
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2785, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                com.leedarson.newui.repoter.e eVar = b6.this.E;
                eVar.h("IPC获取设置失败(Fail)" + tip);
                a.b g = timber.log.a.g("NewLivePresenter");
                g.m("deviceControlByDevActionReq  打开报警失败 tip=" + tip, new Object[0]);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void S1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2687(0xa7f, float:3.765E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.o0
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.o0 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.S1():void");
    }

    public void I1(int direction) {
        Object[] objArr = {new Integer(direction)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2688, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            S1();
            this.l = direction;
            Timer timer = new Timer();
            this.o0 = timer;
            timer.schedule(new q(direction), 50, 200);
        }
    }

    /* compiled from: NewLivePresenter */
    public class q extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        q(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2786, new Class[0], Void.TYPE).isSupported) {
                if (b6.this.r) {
                    if (b6.this.g != null) {
                        if (!b6.this.p0) {
                            boolean unused = b6.this.p0 = true;
                            b6.this.g.E2(1);
                        }
                        b6 b6Var = b6.this;
                        b6Var.g1("PtzMove:" + this.c);
                        switch (this.c) {
                            case 1:
                                b6.this.g.G2(4);
                                return;
                            case 2:
                                b6.this.g.B2(4);
                                return;
                            case 3:
                                b6.this.g.C2(4);
                                return;
                            case 6:
                                b6.this.g.D2(4);
                                return;
                            default:
                                return;
                        }
                    }
                } else if (b6.this.f != null) {
                    if (!b6.this.p0) {
                        boolean unused2 = b6.this.p0 = true;
                        b6.this.f.h1(1);
                    }
                    b6 b6Var2 = b6.this;
                    b6Var2.g1("PtzMove:" + this.c);
                    switch (this.c) {
                        case 1:
                            b6.this.f.j1(4);
                            return;
                        case 2:
                            b6.this.f.e1(4);
                            return;
                        case 3:
                            b6.this.f.f1(4);
                            return;
                        case 6:
                            b6.this.f.g1(4);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public void R1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2689, new Class[0], Void.TYPE).isSupported) {
            S1();
            if (this.p0) {
                if (this.r) {
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                    if (f0Var != null) {
                        f0Var.F2(1);
                    }
                } else {
                    com.leedarson.smartcamera.sdk.a aVar = this.f;
                    if (aVar != null) {
                        aVar.i1(1);
                    }
                }
                this.p0 = false;
            }
        }
    }

    public void E1(boolean mute) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2690, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.Q = mute;
            if (this.r) {
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                if (f0Var != null) {
                    if (mute) {
                        z2 = false;
                    }
                    f0Var.z0(z2);
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.codec.c cVar = this.h;
            if (cVar != null) {
                cVar.y(mute);
            }
        }
    }

    public void w0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2692, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject extendsObj = new JSONObject();
                extendsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "getVerListReq");
                extendsObj.put("devId", (Object) deviceId);
                extendsObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) "ota");
                JSONObject _payload = new JSONObject();
                _payload.put("devId", (Object) deviceId);
                String productId = "";
                if (IpcServiceImpl.p(deviceId) != null) {
                    productId = IpcServiceImpl.p(deviceId).productId;
                }
                _payload.put("productId", (Object) productId);
                _payload.put("homeId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "houseId", ""));
                _payload.put("devType", (Object) "IPC");
                extendsObj.put("payload", (Object) _payload);
                String _topic = "iot/v1/s/userId/ota/getVerListReq".replace("userId", BaseApplication.b().d());
                MqttMessageConfigBean _config = new MqttMessageConfigBean();
                _config.isSupportSimpleVersion = false;
                ((LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class)).publish(_topic, _config, extendsObj, new r());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class r implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 2789, new Class[]{String.class, JSONObject.class}, Void.TYPE).isSupported) {
                if (b6.this.m() != null) {
                    a.b g = timber.log.a.g("NewLivePresenter");
                    g.h("getVerList 获取固件版本(成功)  data=" + callbackData, new Object[0]);
                    ((c6) b6.this.m()).Q0(callbackData.toString());
                }
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2790, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("getVerList 获取固件版本失败  code=" + code + ", taskId=" + taskId + ", desc=" + desc, new Object[0]);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void Q1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2693(0xa85, float:3.774E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.m
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.m = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.Q1():void");
    }

    public void H1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2694, new Class[0], Void.TYPE).isSupported) {
            this.o = 0;
            Q1();
            Timer timer = new Timer();
            this.m = timer;
            timer.schedule(new s(), 1000, 1000);
        }
    }

    /* compiled from: NewLivePresenter */
    public class s extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void run() {
            int count;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2791, new Class[0], Void.TYPE).isSupported) {
                b6.E(b6.this);
                if (b6.this.o >= 10800 && (count = 10810 - b6.this.o) >= 0) {
                    try {
                        ((c6) b6.this.m()).I(count);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void e2(String deviceId, String str) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, str}, this, changeQuickRedirect, false, 2695, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                JSONObject extendsObj = new JSONObject();
                extendsObj.put("updatePicture", true);
                i0(deviceId, extendsObj);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void s1(byte[] data, int length) {
        if (!PatchProxy.proxy(new Object[]{data, new Integer(length)}, this, changeQuickRedirect, false, 2696, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                com.leedarson.smartcamera.sdk.a aVar = this.f;
                if (aVar != null) {
                    aVar.v1(data, length);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void p0(IpcDeviceBean deviceBean, String ref) {
        Class[] clsArr = {IpcDeviceBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{deviceBean, ref}, this, changeQuickRedirect, false, 2697, clsArr, Void.TYPE).isSupported) {
            this.v = deviceBean.id;
            com.leedarson.newui.repoter.d dVar = this.F;
            dVar.j("deviceId=" + this.v);
            this.G.h(((NewLiveFragment) l()).getContext(), deviceBean);
            long requestStartTime = System.currentTimeMillis();
            io.reactivex.disposables.b bVar = this.r0;
            if (bVar != null && !bVar.isDisposed()) {
                this.r0.dispose();
            }
            this.r0 = this.M.d(this.v, new t(ref, requestStartTime));
            g1("getKVSParams onStart: " + this.r0);
            this.N.add(this.r0);
        }
    }

    /* compiled from: NewLivePresenter */
    public class t extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ long d;

        t(String str, long j) {
            this.c = str;
            this.d = j;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2794, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2792, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getKVSParams onError:" + e.getMsg());
                ((c6) b6.this.m()).J();
                com.leedarson.newui.repoter.d dVar = b6.this.F;
                dVar.k("获取kvs登陆信息失败 code:" + e.getCode() + " msg:" + e.getMsg());
            }
        }

        public void onSuccess(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2793, new Class[]{String.class}, Void.TYPE).isSupported) {
                String response = str;
                b6 b6Var = b6.this;
                b6Var.g1("getKVSParams onSuccess:" + response);
                try {
                    Gson gson = new Gson();
                    KvsParmsResponseWrapBean resPonseData = (KvsParmsResponseWrapBean) gson.fromJson(response, new a().getType());
                    KVSParamBean kvsParamBean = (KVSParamBean) ((HashMap) resPonseData.data).get(b6.this.v);
                    if (!resPonseData.checkDataValid() || resPonseData.data == null || kvsParamBean == null) {
                        ((c6) b6.this.m()).J();
                        com.leedarson.newui.repoter.d dVar = b6.this.F;
                        dVar.k("获取kvs登陆信息失败---> 无效 " + response);
                    } else if (kvsParamBean.requireFiledCheck()) {
                        if (kvsParamBean.nowTime > 0) {
                            long unused = b6.this.y = Math.abs(System.currentTimeMillis() - kvsParamBean.nowTime);
                        }
                        ((c6) b6.this.m()).C(kvsParamBean, this.c);
                        long duration = System.currentTimeMillis() - this.d;
                        b6 b6Var2 = b6.this;
                        b6Var2.G.d(b6Var2.v, duration, 200, gson.toJson((Object) kvsParamBean), "获取kvs登陆信息成功");
                        com.leedarson.newui.repoter.d dVar2 = b6.this.F;
                        dVar2.l("response=" + gson.toJson((Object) kvsParamBean));
                    } else {
                        ((c6) b6.this.m()).J();
                        com.leedarson.newui.repoter.d dVar3 = b6.this.F;
                        dVar3.k("获取kvs登陆信息失败---> 参数缺失：" + response);
                    }
                } catch (Exception e) {
                    ((c6) b6.this.m()).J();
                    com.leedarson.newui.repoter.d dVar4 = b6.this.F;
                    dVar4.k("获取kvs登陆信息解析失败：" + e.getMessage());
                }
            }
        }

        /* compiled from: NewLivePresenter */
        public class a extends TypeToken<KvsParmsResponseWrapBean> {
            a() {
            }
        }
    }

    public void o0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2698, new Class[]{String.class}, Void.TYPE).isSupported) {
            String productId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String appId = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "APP_ID", "");
            String owner = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(((NewLiveFragment) l()).getContext()));
                paramsJson.put("productId", (Object) productId);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.serviceimpl.http.manager.b0.b().K(((NewLiveFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/liveStream/popUps", headerJson.toString(), paramsJson.toString(), new v());
        }
    }

    /* compiled from: NewLivePresenter */
    public class v extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2799, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2797, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("popUps error=" + e.getCode() + "——" + e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2798, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("popUps onSuccess:" + response);
                ((c6) b6.this.m()).a0(response);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0023, code lost:
        r2 = r2.props;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean B0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 2699(0xa8b, float:3.782E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x001e
            java.lang.Object r0 = r1.result
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x001e:
            r1 = r8
            com.leedarson.bean.IpcDeviceBean r2 = r1.K
            if (r2 == 0) goto L_0x002e
            com.leedarson.bean.PropsBean r2 = r2.props
            if (r2 == 0) goto L_0x002e
            boolean r2 = r2.isSupportPreCon()
            if (r2 == 0) goto L_0x002e
            r0 = 1
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.B0():boolean");
    }

    public void D0(IpcDeviceBean ipcDeviceBean, KVSParamBean kVSParamBean, IpcWebrtcSurfaceView ipcWebrtcSurfaceView, boolean z2, String str) {
        if (!PatchProxy.proxy(new Object[]{ipcDeviceBean, kVSParamBean, ipcWebrtcSurfaceView, new Byte(z2 ? (byte) 1 : 0), str}, this, changeQuickRedirect, false, 2700, new Class[]{IpcDeviceBean.class, KVSParamBean.class, IpcWebrtcSurfaceView.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            KVSParamBean param = kVSParamBean;
            boolean webrtcAudioEnable = z2;
            IpcDeviceBean deviceBean = ipcDeviceBean;
            IpcWebrtcSurfaceView webrtcSurfaceView = ipcWebrtcSurfaceView;
            String desc = str;
            this.P.init();
            ((c6) m()).i();
            this.v = deviceBean.id;
            PropsBean propsBean = deviceBean.props;
            String supportIpv6 = propsBean.supportIpv6;
            String liveType = propsBean.liveType;
            boolean isSupport = B0();
            boolean isSupport2 = isSupport;
            this.g = com.leedarson.manager.a.i().n(this.v, liveType, supportIpv6, param, isSupport ? f0.r.PRE_LINK : f0.r.LIVE);
            com.leedarson.log.f.e("kvsConnect getWebRTCChannel: " + this.g + "  ref: " + desc);
            com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
            if (f0Var != null) {
                f0Var.h3(deviceBean.getSpkNSLevel());
                this.g.j3(deviceBean.props.getVideoCodesArr());
                this.g.i3(isSupport2);
                this.g.c3(deviceBean.props.enableSdes);
                com.leedarson.log.f.b("NewLivePresenter", "isSupportPreCon: " + isSupport2);
                com.leedarson.newui.repoter.h hVar = this.G;
                String str2 = this.v;
                hVar.c(liveType, str2, param, "get Channel:" + supportIpv6 + " isSupportPreCon:" + isSupport2);
                if (webrtcSurfaceView != null) {
                    webrtcSurfaceView.clearImage();
                }
                webrtcSurfaceView.restoreFirstFrameRendered();
                com.leedarson.newui.repoter.d dVar = this.F;
                dVar.x(" deviceId=" + this.v + " liveType=" + liveType + "  modelId=" + deviceBean.modelId + "  isSupport=" + isSupport2, "IPCLivePage", desc);
                this.D = new com.leedarson.newui.report_repos.k(this.v, this.G, this.g, k.a.LIVE);
                com.leedarson.manager.a.i().a(this.v, this.g);
                try {
                    com.leedarson.log.f.a("deviceBean.modelId:" + ((NewLiveFragment) l()).P4.modelId);
                    if (deviceBean.pushBean.isDefaultHD()) {
                        this.g.b3(true);
                    } else {
                        this.g.b3(false);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                long connectStartTime = System.currentTimeMillis();
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var2 = this.g;
                f0Var2.C1 = new k4(this);
                f0Var2.D1 = new w();
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var3 = this.g;
                f0Var3.E1 = a4.a;
                f0Var3.F1 = new r4(this);
                p1(this.S);
                b(this.g.K0.c(com.leedarson.base.http.observer.l.c()).I(s4.c, o4.c));
                p1(this.s0);
                io.reactivex.disposables.b I2 = this.g.I0.c(com.leedarson.base.http.observer.l.c()).I(new p4(this), e4.c);
                this.s0 = I2;
                b(I2);
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var4 = this.g;
                Context context = ((NewLiveFragment) l()).getContext();
                String str3 = liveType;
                boolean z3 = isSupport2;
                x xVar = r1;
                String str4 = supportIpv6;
                x xVar2 = new x(webrtcAudioEnable, webrtcSurfaceView, connectStartTime, deviceBean);
                f0Var4.H0(context, xVar);
                this.g.O0("NewLivePresenter.KvsConnect");
                p1(this.R);
                io.reactivex.disposables.b I3 = this.g.J0.c(com.leedarson.base.http.observer.l.c()).I(new n4(this, webrtcAudioEnable, webrtcSurfaceView), u4.c);
                this.R = I3;
                b(I3);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class w implements com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.j {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }
    }

    static /* synthetic */ void K0(String str, String peerId, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, peerId, message}, (Object) null, changeQuickRedirect, true, 2743, clsArr, Void.TYPE).isSupported) {
            a.C0116a aVar = com.leedarson.newui.repos.reporters.a.a;
            aVar.a(peerId + "").f(peerId, message);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: L0 */
    public /* synthetic */ void M0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2742, new Class[0], Void.TYPE).isSupported) {
            ((c6) m()).V(-31006002, "网络切换触发了自动重新拉流");
            ((c6) m()).i();
        }
    }

    static /* synthetic */ void N0(Double aDouble) {
    }

    static /* synthetic */ void O0(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: P0 */
    public /* synthetic */ void Q0(kotlin.n integerStringPair) {
        if (!PatchProxy.proxy(new Object[]{integerStringPair}, this, changeQuickRedirect, false, 2741, new Class[]{kotlin.n.class}, Void.TYPE).isSupported) {
            if (((Integer) integerStringPair.getFirst()).intValue() != 200) {
                ((c6) m()).showToast(R$string.speak_error);
                X1();
            }
        }
    }

    static /* synthetic */ void G0(Throwable throwable) {
    }

    /* compiled from: NewLivePresenter */
    public class x implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;
        final /* synthetic */ IpcWebrtcSurfaceView b;
        final /* synthetic */ long c;
        final /* synthetic */ IpcDeviceBean d;

        x(boolean z, IpcWebrtcSurfaceView ipcWebrtcSurfaceView, long j, IpcDeviceBean ipcDeviceBean) {
            this.a = z;
            this.b = ipcWebrtcSurfaceView;
            this.c = j;
            this.d = ipcDeviceBean;
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2802, new Class[0], Void.TYPE).isSupported) {
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).T(0);
                }
                b6.this.F.w("KVS.Channel.startCreateSdpOffer");
                long kvsSdpOfferStartTime = System.currentTimeMillis();
                if (b6.this.g != null) {
                    b6.this.g.createSdpOffer(new a(kvsSdpOfferStartTime));
                }
            }
        }

        /* compiled from: NewLivePresenter */
        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ long a;

            a(long j) {
                this.a = j;
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2814, new Class[0], Void.TYPE).isSupported) {
                    if (b6.this.m() != null) {
                        ((c6) b6.this.m()).T(2);
                    }
                    com.leedarson.log.f.a("createSdpOffer onAddStream: " + Thread.currentThread());
                    if (((NewLiveFragment) b6.this.l()).getActivity() != null) {
                        ((NewLiveFragment) b6.this.l()).getActivity().runOnUiThread(new C0102a());
                    }
                }
            }

            /* renamed from: com.leedarson.newui.b6$x$a$a  reason: collision with other inner class name */
            /* compiled from: NewLivePresenter */
            public class C0102a implements Runnable {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0102a() {
                }

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2819, new Class[0], Void.TYPE).isSupported) {
                        if (b6.this.g != null) {
                            b6.this.g.z0(x.this.a);
                            b6.this.g.e3(x.this.b);
                        }
                    }
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 2815, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    com.leedarson.log.f.a("createSdpOffer onDataChannelStateChange: " + state);
                    if (state == DataChannel.State.OPEN) {
                        b6.H(b6.this, "DataChannel.Open");
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 2816, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    com.leedarson.log.f.a("createSdpOffer onMessage: " + bytes.length);
                }
            }

            public void onError(String desc) {
                if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 2817, new Class[]{String.class}, Void.TYPE).isSupported) {
                    com.leedarson.log.f.a("createSdpOffer onError: " + desc);
                    if (b6.this.m() != null) {
                        ((c6) b6.this.m()).V(-104, "KVS onError Desc=" + desc);
                    }
                    com.leedarson.newui.repoter.d dVar = b6.this.F;
                    dVar.f("desc=" + desc);
                    long currentTimeMillis = System.currentTimeMillis() - this.a;
                }
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 2818, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                    if ((iceConnectionState == PeerConnection.IceConnectionState.FAILED || iceConnectionState == PeerConnection.IceConnectionState.CLOSED) && b6.this.m() != null) {
                        ((c6) b6.this.m()).V(-105, "  onIceConnectionChange iceConnectionState=" + iceConnectionState);
                    }
                }
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2803, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.a("webRTCChannel.connect onClose: ");
            }
        }

        public void a(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2804, new Class[]{Event.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("webRTCChannel.connect onError: kvs.通道发生了错误" + event.getMessagePayload());
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).V(-106, "kvs.通道发生了错误" + event.getMessagePayload());
                }
                com.leedarson.newui.repoter.d dVar = b6.this.F;
                dVar.o("kvs.通道发生了错误" + event.getMessagePayload());
            }
        }

        public void onException(Exception e2) {
            if (!PatchProxy.proxy(new Object[]{e2}, this, changeQuickRedirect, false, 2805, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("webRTCChannel.connect onError: kvs.通道发生了异常" + e2.toString() + " timeOffset:" + b6.this.y);
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).V(-107, "kvs.通道发生了异常" + e2.toString() + " timeOffset:" + b6.this.y);
                }
                com.leedarson.newui.repoter.d dVar = b6.this.F;
                dVar.p("kvs.通道发生了异常" + e2.toString() + " timeOffset:" + b6.this.y);
                b6 b6Var = b6.this;
                com.leedarson.newui.repoter.h hVar = b6Var.G;
                String t = b6Var.v;
                hVar.i(t, " webrtc打通链路出现异常：" + e2.toString());
                if (((e2.toString().contains("Handshake error") && b6.this.y > 240) || e2.toString().contains("Chain validation failed")) && b6.this.m() != null) {
                    ((c6) b6.this.m()).showToast(R$string.lds_ipc_timeoffset_error);
                }
            }
        }

        public void e(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 2806, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("webRTCChannel.connect kvsSdpOffer: " + message);
                b6.this.g1("kvsSdpOffer:开始发送offer 想看详细来这边打开");
                b6.this.F.t(message);
            }
        }

        public void c(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 2807, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6.this.g1("kvsSdpAnswer:想看详细来这边打开");
                b6.this.F.s(message);
                long currentTimeMillis = System.currentTimeMillis() - this.c;
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).T(1);
                }
            }
        }

        public void g(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 2808, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("webRTCChannel.connect kvsIceCandidate: " + message);
                b6.this.F.r(message);
            }
        }

        public void d(int stateCode) {
            if (!PatchProxy.proxy(new Object[]{new Integer(stateCode)}, this, changeQuickRedirect, false, 2809, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (stateCode == -50002) {
                    if (b6.this.m() != null) {
                        ((c6) b6.this.m()).V(AckBean.WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED, "webRtc设备超过最大连接");
                        ((c6) b6.this.m()).showToast(R$string.max_connecttion_err);
                        b6.this.g.I2(false);
                    }
                } else if (stateCode == -1100 && b6.this.m() != null) {
                    ((c6) b6.this.m()).V(stateCode, "已超一分钟未出流数据，已经超时，该停下来了");
                }
            }
        }

        public void onConnected() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2810, new Class[0], Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("通道建立成功  connected ---> 准备展示画面---》准备发起拉流-----》" + this.d.pushBean.isDefaultHD() + "==" + b6.this.g.s1());
                b6.H(b6.this, "WebRtcConnected（webrtc通道打通）");
                b6.J(b6.this, this.d);
                if (b6.this.m() != null) {
                    ((c6) b6.this.m()).T(2);
                }
                b6.this.g.z0(this.a);
                b6.this.A1(this.b, "WebRtcConnected（webrtc通道打通）");
                b6.this.V.setOnHideDialogHandler(new b4(this));
                b6.this.V.setOnShowDialogHandler(new c4(this));
                b6.this.V.g();
            }
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: h */
        public /* synthetic */ void i(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2813, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ((c6) b6.this.m()).h1();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: j */
        public /* synthetic */ void k(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2812, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ((c6) b6.this.m()).f1();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2811, new Class[0], Void.TYPE).isSupported) {
                b6.this.l0();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: H0 */
    public /* synthetic */ void I0(boolean webrtcAudioEnable, IpcWebrtcSurfaceView webrtcSurfaceView, kotlin.n booleanBooleanPair) {
        if (!PatchProxy.proxy(new Object[]{new Byte(webrtcAudioEnable ? (byte) 1 : 0), webrtcSurfaceView, booleanBooleanPair}, this, changeQuickRedirect, false, 2740, new Class[]{Boolean.TYPE, IpcWebrtcSurfaceView.class, kotlin.n.class}, Void.TYPE).isSupported) {
            if (this.L && ((Boolean) booleanBooleanPair.getFirst()).booleanValue() && ((Boolean) booleanBooleanPair.getSecond()).booleanValue()) {
                g1("准备展示画面---》准备发起拉流-----》");
                u0("预连接成功 && 通道同时打通时发起直播");
                if (this.g.s1()) {
                    B1(1, false);
                }
                ((c6) m()).T(2);
                this.g.z0(webrtcAudioEnable);
                A1(webrtcSurfaceView, "预连接成功 && 通道同时打通时发起直播");
            }
        }
    }

    static /* synthetic */ void J0(Throwable throwable) {
    }

    private void u0(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 2701, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.a("getStatusAndToLive");
            com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
            if (f0Var != null) {
                f0Var.g1(new y());
                c0("getStatusAndToLive." + ref);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class y implements com.leedarson.smartcamera.listener.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        y() {
        }

        public void onSuccess(int resolution) {
            Object[] objArr = {new Integer(resolution)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2820, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("NewLivePresenter", "onSuccess: " + resolution);
                ((c6) b6.this.m()).E(resolution);
            }
        }
    }

    public void g0(IpcWebrtcSurfaceView webrtcSurfaceView, boolean z2, String reason) {
        Object[] objArr = {webrtcSurfaceView, new Byte(z2 ? (byte) 1 : 0), reason};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2703, new Class[]{IpcWebrtcSurfaceView.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            if (this.g != null && webrtcSurfaceView != null) {
                this.P.leaveReason = reason;
                com.leedarson.newui.repoter.d dVar = this.F;
                dVar.i("webrtc销毁reason:" + reason, "IPCLivePage");
                com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
                f0Var.F1 = null;
                f0Var.I2(false);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2704(0xa90, float:3.789E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.g
            if (r1 == 0) goto L_0x0023
            r1.t2()
            com.leedarson.newui.stratages.c r1 = r0.V
            r1.f()
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.d1():void");
    }

    public void n1() {
        com.leedarson.smartcamera.kvswebrtc.f0 f0Var;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2705, new Class[0], Void.TYPE).isSupported && (f0Var = this.g) != null) {
            f0Var.L2();
        }
    }

    public void w1(String deviceId, boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2706, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("NewLivePresenter", "setLiveState: " + isSleep);
            io.reactivex.disposables.b bVar = this.t0;
            if (bVar != null && !bVar.isDisposed()) {
                this.t0.dispose();
            }
            io.reactivex.disposables.b bVar2 = this.u0;
            if (bVar2 != null && !bVar2.isDisposed()) {
                this.u0.dispose();
            }
            if (!isSleep) {
                this.u0 = this.v0.d(deviceId).c(com.leedarson.base.http.observer.l.c()).I(g4.c, j4.c);
            }
            this.t0 = this.v0.k(deviceId, isSleep).G(new com.leedarson.base.http.observer.j(2, 1500)).c(com.leedarson.base.http.observer.l.c()).I(h4.c, l4.c);
            if (this.x0) {
                ((c6) m()).Z(isSleep);
            } else if (l() != null) {
                ((NewLiveFragment) l()).H3();
            }
        }
    }

    static /* synthetic */ void U0(LDSBaseBean ldsBaseBean) {
    }

    static /* synthetic */ void V0(Throwable throwable) {
    }

    static /* synthetic */ void W0(LDSBaseBean ldsBaseBean) {
    }

    static /* synthetic */ void X0(Throwable throwable) {
    }

    public void f2(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2707, new Class[]{String.class}, Void.TYPE).isSupported) {
            ((c6) m()).i();
            this.x0 = true;
            this.w0 = 0;
            if (this.K != null) {
                this.G.h(((NewLiveFragment) l()).getContext(), this.K);
            }
            w1(deviceId, false);
        }
    }

    public void g2(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2708, new Class[]{String.class}, Void.TYPE).isSupported) {
            ((c6) m()).b();
            this.x0 = false;
            this.w0 = 0;
            w1(deviceId, false);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void W1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2709(0xa95, float:3.796E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.n
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.n = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.W1():void");
    }

    public void L1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2710, new Class[0], Void.TYPE).isSupported) {
            this.p = 15;
            W1();
            Timer timer = new Timer();
            this.n = timer;
            timer.schedule(new z(), 50, 1000);
        }
    }

    /* compiled from: NewLivePresenter */
    public class z extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        z() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2822, new Class[0], Void.TYPE).isSupported) {
                b6.M(b6.this);
                if (b6.this.p < 0) {
                    b6.this.W1();
                } else if (b6.this.m() != null) {
                    ((c6) b6.this.m()).O(b6.this.p);
                }
            }
        }
    }

    public void t0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2711, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            g1("getShareAccount");
            String baseUrl = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramObj = new JSONObject();
            String appId = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "APP_ID", "");
            String owner = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(((NewLiveFragment) l()).getContext(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(((NewLiveFragment) l()).getContext()));
                paramObj.put("deviceIds", (Object) deviceId);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.serviceimpl.http.manager.b0.b().K(((NewLiveFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, String.format(Locale.US, "%s/devices/shares", new Object[]{baseUrl}), headerJson.toString(), paramObj.toString(), new a0());
        }
    }

    /* compiled from: NewLivePresenter */
    public class a0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a0() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2825, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2823, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("error=" + e.getCode() + "——" + e.getMsg());
                ((c6) b6.this.m()).y();
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2824, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getShareAccount onSuccess:" + response);
                try {
                    ((c6) b6.this.m()).t(new JSONArray(response).getJSONObject(0).getString("fromUserUuid"));
                } catch (Exception e) {
                    e.printStackTrace();
                    ((c6) b6.this.m()).y();
                }
            }
        }
    }

    public void N1(String ref) {
        com.leedarson.smartcamera.sdk.a aVar;
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 2712, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.x = true;
            if (!this.r && (aVar = this.f) != null) {
                aVar.unRegisterTutkListener(this.Y);
            }
            j1(ref);
        }
    }

    public void h1(JSONObject data) {
        com.leedarson.smartcamera.kvswebrtc.f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 2713, new Class[]{JSONObject.class}, Void.TYPE).isSupported && (f0Var = this.g) != null) {
            f0Var.u2(data);
        }
    }

    public void k1(String id) {
        if (!PatchProxy.proxy(new Object[]{id}, this, changeQuickRedirect, false, 2714, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject actionsObj = new JSONObject();
                actionsObj.put("devId", (Object) id);
                actionsObj.put("action", (Object) "RebootFunc");
                com.leedarson.newui.repoter.i.c().j(((NewLiveFragment) l()).getContext(), this.v, "reboot", "发送设备重启指令");
                h0(this.v, actionsObj, new b0(), 10000);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class b0 implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b0() {
        }

        public void onResult(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 2826, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                Log.d("NewLivePresenter", "reboot onResult: " + code);
                timber.log.a.g("NewLivePresenter").m("deviceControlByDevActionReq  重启设备-投递成功", new Object[0]);
            }
        }

        public void onFail(int i, String str, String str2) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, str2};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2827, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                timber.log.a.g("NewLivePresenter").m("deviceControlByDevActionReq  重启设备-投递失败", new Object[0]);
            }
        }
    }

    public void q0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2715, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            try {
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                JSONObject headerJson = new JSONObject();
                JSONObject paramsJson = new JSONObject();
                String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
                String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(BaseApplication.b()));
                paramsJson.put("deviceId", (Object) deviceId);
                String url = baseUrl + "/api/ipc/recordPlanController/packageExpireRemind";
                g1("getPackageExpireRemind url=" + url);
                com.leedarson.serviceimpl.http.manager.b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new c0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class c0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c0() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2830, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2828, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getPackageExpireRemind error=" + e.getCode() + "——" + e.getMsg());
                ((c6) b6.this.m()).D0(e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2829, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getPackageExpireRemind onSuccess:" + response);
                ((c6) b6.this.m()).X0(response);
            }
        }
    }

    public void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2716, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject extendsObj = new JSONObject();
                extendsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "getDevInfoReq");
                extendsObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
                JSONObject _payload = new JSONObject();
                _payload.put("devId", (Object) this.v);
                String productId = "";
                if (IpcServiceImpl.p(this.v) != null) {
                    productId = IpcServiceImpl.p(this.v).productId;
                }
                _payload.put("productId", (Object) productId);
                _payload.put("homeId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "houseId", ""));
                _payload.put("devType", (Object) "IPC");
                extendsObj.put("payload", (Object) _payload);
                String _topic = "iot/v1/s/userId/device/getDevInfoReq".replace("userId", BaseApplication.b().d());
                MqttMessageConfigBean _config = new MqttMessageConfigBean();
                _config.isSupportSimpleVersion = false;
                ((LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class)).publish(_topic, _config, extendsObj, new d0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class d0 implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        d0() {
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 2831, clsArr, Void.TYPE).isSupported) {
                try {
                    b6.N(b6.this, callbackData.getJSONObject("payload").getString(ConfigUtil.VERSION_FILE));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2832, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("getVerList 获取固件版本失败  code=" + code + ", taskId=" + taskId + ", desc=" + desc, new Object[0]);
            }
        }
    }

    private void r(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2717, new Class[]{String.class}, Void.TYPE).isSupported) {
            String firmwareVersion = str;
            try {
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                JSONObject headerJson = new JSONObject();
                JSONObject paramsJson = new JSONObject();
                String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
                String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(BaseApplication.b()));
                paramsJson.put("os", (Object) "android");
                paramsJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(BaseApplication.b()));
                paramsJson.put("productModel", (Object) this.K.modelId);
                paramsJson.put("deviceVersion", (Object) firmwareVersion);
                com.leedarson.serviceimpl.http.manager.b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/abnormalVersion/check", headerJson.toString(), paramsJson.toString(), new f0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class f0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        f0() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2838, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2836, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("LiveFailAnalyse").a(e.getMessage(), new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2837, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("LiveFailAnalyse").a(response, new Object[0]);
                ((c6) b6.this.m()).d1(response);
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class g0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        g0() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2841, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2839, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getBrands error=" + e.getCode() + "——" + e.getMsg());
                ((c6) b6.this.m()).G0(e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2840, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getBrands onSuccess:" + response);
                ((c6) b6.this.m()).k1(response);
            }
        }
    }

    public void m0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2718, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.http.manager.b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/v16/devices/" + deviceId + "/brands", "", (String) null, new g0());
        }
    }

    public void r0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2719, new Class[]{String.class}, Void.TYPE).isSupported) {
            String modelId = str;
            try {
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                JSONObject headerJson = new JSONObject();
                JSONObject paramsJson = new JSONObject();
                String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
                String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) com.leedarson.base.utils.w.E(BaseApplication.b()));
                com.leedarson.serviceimpl.http.manager.b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/v28/product-recommend/getRecommend/" + modelId, headerJson.toString(), paramsJson.toString(), new h0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class h0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        h0() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2843, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2842, new Class[]{String.class}, Void.TYPE).isSupported) {
                ((c6) b6.this.m()).e0(response);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void P1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2720(0xaa0, float:3.812E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.u0
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.u0
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.P1():void");
    }

    /* compiled from: NewLivePresenter */
    public class i0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        i0() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2847, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
            if (!PatchProxy.proxy(new Object[]{d}, this, changeQuickRedirect, false, 2844, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                b6.this.N.add(d);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2845, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                ((c6) b6.this.m()).a();
                b6 b6Var = b6.this;
                b6Var.g1("updateSubscribeStatus error=" + e.getCode() + "——" + e.getMsg());
                ((c6) b6.this.m()).showToast(R$string.failed_operation);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2846, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("updateSubscribeStatus onSuccess:" + response);
                ((c6) b6.this.m()).a();
                LDSBaseBean<UpdateSubscribeBean> resp = (LDSBaseBean) b6.this.q0.fromJson(response, new a().getType());
                try {
                    if (resp.checkDataValid()) {
                        T t = resp.data;
                        if (((UpdateSubscribeBean) t).lock == 1 && ((UpdateSubscribeBean) t).status == 1) {
                            ((c6) b6.this.m()).showToast(R$string.renewal_lock_turn_off);
                        } else if (((UpdateSubscribeBean) t).lock != 1 || ((UpdateSubscribeBean) t).status == 1) {
                            ((c6) b6.this.m()).k0();
                        } else {
                            ((c6) b6.this.m()).showToast(R$string.renewal_lock_turn_on);
                        }
                    } else {
                        ((c6) b6.this.m()).showToast(R$string.failed_operation);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* compiled from: NewLivePresenter */
        public class a extends TypeToken<LDSBaseBean<UpdateSubscribeBean>> {
            a() {
            }
        }
    }

    public void a2(String packageId) {
        if (!PatchProxy.proxy(new Object[]{packageId}, this, changeQuickRedirect, false, 2721, new Class[]{String.class}, Void.TYPE).isSupported) {
            ((c6) m()).b();
            this.M.e(packageId, 1, new i0());
        }
    }

    private void m1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2722, new Class[0], Void.TYPE).isSupported) {
            Iterator<io.reactivex.disposables.b> it = this.N.iterator();
            while (it.hasNext()) {
                io.reactivex.disposables.b disposable = it.next();
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }

    private void p1(io.reactivex.disposables.b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 2723, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    public void v1(String deviceId, int leftValue) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Integer(leftValue)}, this, changeQuickRedirect, false, 2724, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("Dimming", leftValue);
                com.leedarson.newui.repoter.e eVar = this.E;
                eVar.f("修改亮度:" + leftValue);
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                _payload.put("devId", (Object) deviceId);
                _payload.put("userId", (Object) BaseApplication.b().d());
                _payload.put("password", (Object) this.K.password);
                j0(deviceId, _payload, new j0(leftValue));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class j0 implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        j0(int i) {
            this.a = i;
        }

        public void onResult(int i, String responseData) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i), responseData}, this, changeQuickRedirect, false, 2848, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.m("deviceControlSetDevAttrReq IPC灯亮度控制(成功)" + responseData, new Object[0]);
                PropsBean propsBean = b6.this.K.props;
                propsBean.Dimming = this.a + "";
                ((c6) b6.this.m()).m0();
            }
        }

        public void onFail(int i, String str, String tip) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, tip};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2849, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("NewLivePresenter");
                g.c("deviceControlSetDevAttrReq IPC灯亮度控制(失败)" + tip, new Object[0]);
            }
        }
    }

    public void c0(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 2725, new Class[]{String.class}, Void.TYPE).isSupported) {
            boolean isResume = false;
            try {
                isResume = ((NewLiveFragment) l()).V3();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.f.a("changeToLive support:" + this.L + " isResume:" + isResume + " ,ref=" + ref);
            if (this.L && isResume) {
                this.g.Q2(this.Q);
                this.g.C0(f0.q.LIVING);
                this.y0 = true;
            }
        }
    }

    public void b0(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 2726, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.L && this.y0) {
                a.C0116a aVar = com.leedarson.newui.repos.reporters.a.a;
                aVar.a(this.g.h + "").e(200, ref);
                this.g.C0(f0.q.IDLE);
                this.y0 = false;
            }
        }
    }

    public void x1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2727, new Class[0], Void.TYPE).isSupported) {
            if (this.g != null && l() != null) {
                com.leedarson.utils.j.a = false;
                Bitmap lastBitMap = this.g.c1();
                if (lastBitMap != null && !lastBitMap.isRecycled()) {
                    ((c6) m()).w0(lastBitMap);
                }
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class k0 extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        k0(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2853, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
            if (!PatchProxy.proxy(new Object[]{d2}, this, changeQuickRedirect, false, 2850, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                b6.this.N.add(d2);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 2851, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getDeviceBindPackageInfo error=" + e.getCode() + "——" + e.getMsg());
            }
        }

        /* compiled from: NewLivePresenter */
        public class a extends TypeToken<LDSBaseBean<BindPackageInfoResponseBean>> {
            a() {
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2852, new Class[]{String.class}, Void.TYPE).isSupported) {
                b6 b6Var = b6.this;
                b6Var.g1("getDeviceBindPackageInfo onSuccess:" + response);
                LDSBaseBean<BindPackageInfoResponseBean> resp = (LDSBaseBean) b6.this.q0.fromJson(response, new a().getType());
                try {
                    if (resp.checkDataValid()) {
                        T t = resp.data;
                        if (((BindPackageInfoResponseBean) t).bindPackageIds != null && ((BindPackageInfoResponseBean) t).bindPackageIds.size() > 0) {
                            boolean isBind = false;
                            boolean hasCanBind = false;
                            Iterator<BindPackageInfoItemBean> it = ((BindPackageInfoResponseBean) resp.data).bindPackageIds.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                BindPackageInfoItemBean itemBean = it.next();
                                if (this.c.equals(itemBean.deviceId) && !com.alibaba.android.arouter.utils.e.b(itemBean.packageId)) {
                                    isBind = true;
                                    break;
                                }
                            }
                            if (((BindPackageInfoResponseBean) resp.data).hasCanBindPackage > 0) {
                                hasCanBind = true;
                            }
                            ((c6) b6.this.m()).g0(isBind, hasCanBind);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void n0(String deviceId, String houseId) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{deviceId, houseId}, this, changeQuickRedirect, false, 2728, clsArr, Void.TYPE).isSupported) {
            this.M.c(deviceId, houseId, new k0(deviceId));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void k0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2729(0xaa9, float:3.824E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.g
            if (r1 == 0) goto L_0x004d
            r1.P0()
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.g
            java.lang.String r1 = r1.h
            r0.z0 = r1
            com.leedarson.newui.repos.reporters.a$a r1 = com.leedarson.newui.repos.reporters.a.a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            com.leedarson.smartcamera.kvswebrtc.f0 r3 = r0.g
            java.lang.String r3 = r3.h
            r2.append(r3)
            java.lang.String r3 = ""
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.leedarson.newui.repos.reporters.a r1 = r1.a(r2)
            java.lang.String r2 = r0.v
            com.leedarson.newui.repos.reporters.a r1 = r1.h(r2)
            r1.d()
            com.leedarson.newui.stratages.c r1 = r0.V
            r1.e()
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.k0():void");
    }

    public void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2730, new Class[0], Void.TYPE).isSupported) {
            try {
                if (this.A0 == null) {
                    CopyAssetUtils.copyAssets(BaseApplication.b(), "person.bin");
                    CopyAssetUtils.copyAssets(BaseApplication.b(), "person.param");
                    HumanDetectSDK humanDetectSDK = new HumanDetectSDK();
                    this.A0 = humanDetectSDK;
                    humanDetectSDK.create(BaseApplication.b().getFilesDir().getAbsolutePath());
                }
            } catch (Exception e2) {
                a.b g2 = timber.log.a.g("NewLivePresenter");
                g2.c("初始化人形检测异常： e=" + e2.toString(), new Object[0]);
            }
        }
    }

    public void x0(byte[] bArr, int width, int height, boolean need2nv12) {
        Object[] objArr = {bArr, new Integer(width), new Integer(height), new Byte(need2nv12 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {byte[].class, cls, cls, Boolean.TYPE};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2731, clsArr, Void.TYPE).isSupported) {
            byte[] yuvData = bArr;
            z0();
            com.leedarson.smartcamera.kvswebrtc.utils.d.a().c().submit(new i4(this, yuvData, need2nv12, width, height));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: E0 */
    public /* synthetic */ void F0(byte[] yuvData, boolean need2nv12, int width, int height) {
        byte[] nv12s;
        Object[] objArr = {yuvData, new Byte(need2nv12 ? (byte) 1 : 0), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {byte[].class, Boolean.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2739, clsArr, Void.TYPE).isSupported) {
            try {
                if (this.A0 != null && yuvData != null && l() != null && ((NewLiveFragment) l()).p6 && !((NewLiveFragment) l()).M7 && !((NewLiveFragment) l()).t7) {
                    if (need2nv12) {
                        nv12s = com.leedarson.utils.j.a(yuvData, width, height);
                    } else {
                        nv12s = yuvData;
                    }
                    DetectRoi detect = this.A0.detect(nv12s, width, height);
                    this.B0 = detect;
                    if (detect != null && detect.result == 0 && detect.x > 0) {
                        DetectRoi detectRoi = this.B0;
                        ((c6) m()).S(width, height, detectRoi.x, detectRoi.y, detectRoi.width, detectRoi.height);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void D1(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Boolean.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 2732(0xaac, float:3.828E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.smartcamera.kvswebrtc.f0 r1 = r0.g
            if (r1 == 0) goto L_0x0037
            if (r9 == 0) goto L_0x0034
            com.leedarson.newui.d4 r2 = new com.leedarson.newui.d4
            r2.<init>(r0)
            r1.m3(r2)
            goto L_0x0037
        L_0x0034:
            r1.s3()
        L_0x0037:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.b6.D1(boolean):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: Y0 */
    public /* synthetic */ void Z0(byte[] yuvData, int width, int height) {
        Object[] objArr = {yuvData, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2738, new Class[]{byte[].class, cls, cls}, Void.TYPE).isSupported) {
            x0(yuvData, width, height, true);
        }
    }

    public void u1(boolean isFocus) {
        com.leedarson.smartcamera.codec.c cVar;
        Object[] objArr = {new Byte(isFocus ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2733, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (cVar = this.h) != null) {
            cVar.L(isFocus);
        }
    }

    public void q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2734, new Class[0], Void.TYPE).isSupported) {
            BusinessService _business = (BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class);
            if (_business != null) {
                FeedbackRequestBean _feedbackRequestData = new FeedbackRequestBean();
                FeedbackDoneParamsBean feedbackDoneParamsBean = _feedbackRequestData.done.params;
                feedbackDoneParamsBean.content = "一键反馈：IPC设备直播失败";
                feedbackDoneParamsBean.feedbackType = 7;
                feedbackDoneParamsBean.feedbackSecondType = 22;
                feedbackDoneParamsBean.occurredTime = System.currentTimeMillis();
                FeedbackDoneParamsBean feedbackDoneParamsBean2 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean2.prePage = "IPCMainActivityPage";
                feedbackDoneParamsBean2.prePageTime = System.currentTimeMillis() + "";
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                FeedbackDoneBean feedbackDoneBean = _feedbackRequestData.done;
                feedbackDoneBean.url = baseUrl + "/feedback";
                ArrayList<String> arrayList = _feedbackRequestData.done.params.deviceIds;
                arrayList.add(this.v + "");
                ((c6) m()).b();
                _business.reportIssues(_feedbackRequestData, new l0());
            }
        }
    }

    /* compiled from: NewLivePresenter */
    public class l0 implements BusinessService.UploadCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        l0() {
        }

        public void success(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 2854, new Class[]{Object.class}, Void.TYPE).isSupported) {
                ((c6) b6.this.m()).a();
                ((c6) b6.this.m()).showToast(R$string.lds_report_issue_success);
            }
        }

        public void fail(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 2855, clsArr, Void.TYPE).isSupported) {
                ((c6) b6.this.m()).a();
                ((c6) b6.this.m()).showToast(R$string.lds_report_issue_success);
            }
        }
    }

    public void o1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2735, new Class[0], Void.TYPE).isSupported) {
            if (this.g != null) {
                a.C0116a aVar = com.leedarson.newui.repos.reporters.a.a;
                aVar.a(this.z0 + "").e(200, "用户切到后台");
                this.g.F0();
                this.g = null;
            }
            g1("sufun.data  释放直播间资源成功.....");
        }
    }

    public void i1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2736, new Class[0], Void.TYPE).isSupported) {
            if (this.g != null) {
                a.C0116a aVar = com.leedarson.newui.repos.reporters.a.a;
                aVar.a(this.z0 + "").f(this.z0, "App has no Network");
            }
        }
    }

    public boolean C0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2737, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.g;
        if (f0Var == null || !this.r) {
            return false;
        }
        return f0Var.r1();
    }
}
