package com.leedarson.smartcamera.sdk;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import com.leedarson.bean.H5ActionName;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVAPIs;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.ByteUtil;
import com.tutk.IOTC.IOTCAPIs;
import com.tutk.IOTC.St_AVClientStartInConfig;
import com.tutk.IOTC.St_AVClientStartOutConfig;
import com.tutk.IOTC.St_AVServStartInConfig;
import com.tutk.IOTC.St_AVServStartOutConfig;
import com.tutk.IOTC.St_SInfoEx;
import com.tutk.IOTC.TUTKGlobalAPIs;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import leedarson.platform.g711codec.G711Coder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LdsTutkChannel */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int A;
    int A0;
    /* access modifiers changed from: private */
    public boolean B;
    private Future B0;
    private boolean C;
    int C0;
    /* access modifiers changed from: private */
    public boolean D;
    /* access modifiers changed from: private */
    public int[] D0;
    /* access modifiers changed from: private */
    public List<com.leedarson.smartcamera.listener.i> E;
    byte[] E0;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.listener.h F;
    int F0;
    private com.leedarson.smartcamera.listener.e G;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.listener.b H;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.listener.a I;
    private com.leedarson.smartcamera.listener.c J;
    private com.leedarson.smartcamera.listener.k K;
    private com.leedarson.smartcamera.listener.f L;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.listener.n M;
    /* access modifiers changed from: private */
    public int N;
    /* access modifiers changed from: private */
    public int O;
    /* access modifiers changed from: private */
    public boolean P;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.listener.d Q;
    /* access modifiers changed from: private */
    public Timer R;
    /* access modifiers changed from: private */
    public String S;
    /* access modifiers changed from: private */
    public List<Long> T;
    private boolean U;
    private byte[] V;
    private List<Long> W;
    /* access modifiers changed from: private */
    public boolean X;
    /* access modifiers changed from: private */
    public int Y;
    /* access modifiers changed from: private */
    public int Z;
    /* access modifiers changed from: private */
    public boolean a;
    private Future a0;
    private boolean b;
    private Future b0;
    /* access modifiers changed from: private */
    public boolean c;
    private Future c0;
    private ExecutorService d;
    /* access modifiers changed from: private */
    public int d0;
    private ExecutorService e;
    private boolean e0;
    private ExecutorService f;
    private String f0;
    /* access modifiers changed from: private */
    public ExecutorService g;
    com.leedarson.smartcamera.utils.c g0;
    /* access modifiers changed from: private */
    public ExecutorService h;
    int h0;
    /* access modifiers changed from: private */
    public ExecutorService i;
    int i0;
    /* access modifiers changed from: private */
    public ExecutorService j;
    int j0;
    /* access modifiers changed from: private */
    public Future k;
    int k0;
    /* access modifiers changed from: private */
    public Future l;
    boolean l0;
    /* access modifiers changed from: private */
    public Future m;
    /* access modifiers changed from: private */
    public G711Coder.CoderResult m0;
    /* access modifiers changed from: private */
    public Future n;
    /* access modifiers changed from: private */
    public AudioRecord n0;
    /* access modifiers changed from: private */
    public boolean o;
    private byte[] o0;
    private Future p;
    private byte[] p0;
    private Future q;
    long q0;
    /* access modifiers changed from: private */
    public r r;
    private int r0;
    /* access modifiers changed from: private */
    public String s;
    long s0;
    /* access modifiers changed from: private */
    public String t;
    long t0;
    /* access modifiers changed from: private */
    public String u;
    int u0;
    /* access modifiers changed from: private */
    public int v;
    int v0;
    /* access modifiers changed from: private */
    public int w;
    private int w0;
    private int x;
    private boolean x0;
    /* access modifiers changed from: private */
    public int y;
    int y0;
    /* access modifiers changed from: private */
    public int z;
    int z0;

    static /* synthetic */ int C(a x02, int x1, byte[] x2) {
        Object[] objArr = {x02, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10286, new Class[]{a.class, cls, byte[].class}, cls);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x02.w1(x1, x2);
    }

    static /* synthetic */ void F(a x02, int x1) {
        if (!PatchProxy.proxy(new Object[]{x02, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 10293, new Class[]{a.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x02.R0(x1);
        }
    }

    static /* synthetic */ void J(a x02, int x1, byte[] x2) {
        Object[] objArr = {x02, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10294, new Class[]{a.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            x02.c1(x1, x2);
        }
    }

    static /* synthetic */ void P(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10295, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.z1();
        }
    }

    static /* synthetic */ void W(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10296, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.P1();
        }
    }

    static /* synthetic */ void a(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10283, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.A0();
        }
    }

    static /* synthetic */ void a0(a x02, int x1) {
        if (!PatchProxy.proxy(new Object[]{x02, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 10297, new Class[]{a.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x02.W0(x1);
        }
    }

    static /* synthetic */ void b(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10284, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.k1();
        }
    }

    static /* synthetic */ int e(a x02) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10289, new Class[]{a.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x02.v0();
    }

    static /* synthetic */ void e0(a x02, int x1, String x2, List x3) {
        Object[] objArr = {x02, new Integer(x1), x2, x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10298, new Class[]{a.class, Integer.TYPE, String.class, List.class}, Void.TYPE).isSupported) {
            x02.J0(x1, x2, x3);
        }
    }

    static /* synthetic */ void i0(a x02, int x1) {
        if (!PatchProxy.proxy(new Object[]{x02, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 10299, new Class[]{a.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x02.I0(x1);
        }
    }

    static /* synthetic */ AudioRecord k0(a x02, int x1, int x2, int x3) {
        Object[] objArr = {x02, new Integer(x1), new Integer(x2), new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10300, new Class[]{a.class, cls, cls, cls}, AudioRecord.class);
        return proxy.isSupported ? (AudioRecord) proxy.result : x02.Y0(x1, x2, x3);
    }

    static /* synthetic */ int m0(a x02, int x1, byte[] x2) {
        Object[] objArr = {x02, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10301, new Class[]{a.class, cls, byte[].class}, cls);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x02.A1(x1, x2);
    }

    static /* synthetic */ void n(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10290, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.M1();
        }
    }

    static /* synthetic */ int n0(a x02) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10302, new Class[]{a.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x02.y1();
    }

    static /* synthetic */ void o(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10291, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.V0();
        }
    }

    static /* synthetic */ void p(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10285, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.C1();
        }
    }

    static /* synthetic */ int q0(a x02, int x1, boolean x2) {
        Object[] objArr = {x02, new Integer(x1), new Byte(x2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10287, new Class[]{a.class, cls, Boolean.TYPE}, cls);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x02.N1(x1, x2);
    }

    static /* synthetic */ boolean s0(a x02) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10288, new Class[]{a.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x02.S1();
    }

    static /* synthetic */ void t(a x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 10292, new Class[]{a.class}, Void.TYPE).isSupported) {
            x02.F0();
        }
    }

    static {
        try {
            System.loadLibrary("AVAPIs");
            System.loadLibrary("IOTCAPIs");
            System.loadLibrary("TUTKGlobalAPIs");
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public static void X0() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10212, new Class[0], Void.TYPE).isSupported) {
            if (TUTKGlobalAPIs.TUTK_SDK_Set_License_Key("AQAAABmfpqk4S/+3DUHJAi+gq9pcU1dBRLeM4Ys1o0V+JtWm9CLbN6k/QQHRfsHgwHLzkvBwGWBtade2pJqeXb/YatJDIFCuwlRcJ4At6UH8ac47hpq7t8wsH3v8XME2Y38yPCeO5/opHKuPVeH8OtUsa9xThMsZE2ZcSKkYUp5X1ZxGat4y4HxDSFFQVbfxtTp2+D36mTGd92azF8TA4pghsWgb") != 0) {
                System.out.printf("TUTK_SDK_Set_License_Key exit...!!\n", new Object[0]);
                return;
            }
            IOTCAPIs.IOTC_Initialize2(0);
            IOTCAPIs.IOTC_Setup_Session_Alive_Timeout(15);
            AVAPIs.avInitialize(32);
        }
    }

    public void E1(boolean needGrop) {
        this.e0 = needGrop;
    }

    /* compiled from: LdsTutkChannel */
    public class j implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10303, new Class[0], Void.TYPE).isSupported) {
                AVAPIs.avDeInitialize();
                IOTCAPIs.IOTC_DeInitialize();
                com.leedarson.smartcamera.utils.e.b("", " unInit: ");
            }
        }
    }

    public static void V1() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10214, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.http.observer.l.l.submit(new j());
        }
    }

    private a() {
        this.a = false;
        this.b = false;
        this.c = false;
        this.o = true;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.y = -1;
        this.z = -1;
        this.A = -1;
        this.B = false;
        this.C = false;
        this.D = false;
        this.E = Collections.synchronizedList(new Vector());
        this.O = 27;
        this.P = false;
        this.U = false;
        this.W = new ArrayList();
        this.X = false;
        this.Y = 0;
        this.Z = 1;
        this.d0 = -1;
        this.e0 = true;
        this.f0 = "";
        this.g0 = new com.leedarson.smartcamera.utils.c();
        this.h0 = 8000;
        this.i0 = 16;
        this.j0 = 2;
        this.q0 = 0;
        this.v0 = 0;
        this.x0 = false;
        this.y0 = 19;
        this.z0 = 0;
        this.A0 = -1;
        this.C0 = 15;
        this.D0 = new int[1];
        this.E0 = new byte[1024];
        this.r = new r(Looper.getMainLooper());
        ThreadFactory namedThreadFactory = new com.leedarson.base.utils.r("tutk-channel");
        this.d = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.e = Executors.newFixedThreadPool(2, namedThreadFactory);
        this.f = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.g = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.h = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.i = Executors.newSingleThreadExecutor(namedThreadFactory);
        this.j = Executors.newSingleThreadExecutor(namedThreadFactory);
    }

    public a(String p2pId, String account, String password) {
        this();
        this.s = p2pId;
        this.t = account;
        this.u = password;
    }

    public void B1(String clientId) {
        this.f0 = clientId;
    }

    public void D1(int isDTLS) {
        this.Y = isDTLS;
    }

    public String G0() {
        return this.s;
    }

    public String E0() {
        return this.t;
    }

    public String H0() {
        return this.u;
    }

    public void H1(int talkMode) {
        this.Z = talkMode;
    }

    public int S0() {
        return this.Z;
    }

    public boolean Z0() {
        boolean isConnect = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10215, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.x >= 0) {
            isConnect = true;
        }
        com.leedarson.smartcamera.utils.e.c("", this.s + " isConnect: " + isConnect);
        return isConnect;
    }

    public void registerTutkListener(com.leedarson.smartcamera.listener.i onTutkListener) {
        if (!PatchProxy.proxy(new Object[]{onTutkListener}, this, changeQuickRedirect, false, 10216, new Class[]{com.leedarson.smartcamera.listener.i.class}, Void.TYPE).isSupported) {
            if (!this.E.contains(onTutkListener)) {
                this.E.add(onTutkListener);
            }
        }
    }

    public void unRegisterTutkListener(com.leedarson.smartcamera.listener.i onTutkListener) {
        if (!PatchProxy.proxy(new Object[]{onTutkListener}, this, changeQuickRedirect, false, 10217, new Class[]{com.leedarson.smartcamera.listener.i.class}, Void.TYPE).isSupported) {
            if (this.E.contains(onTutkListener)) {
                this.E.remove(onTutkListener);
            }
        }
    }

    public void setOnSDRecordPlayListener(com.leedarson.smartcamera.listener.h onSDRecordPlayListener) {
        this.F = onSDRecordPlayListener;
    }

    public void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10218, new Class[0], Void.TYPE).isSupported) {
            Message msg1 = Message.obtain();
            r rVar = this.r;
            msg1.what = 3;
            msg1.arg1 = 0;
            rVar.sendMessage(msg1);
            if (Z0()) {
                Message msg = Message.obtain();
                r rVar2 = this.r;
                msg.what = 3;
                msg.arg1 = 1;
                rVar2.sendMessage(msg);
                return;
            }
            this.d.execute(new k());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class k implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10315, new Class[0], Void.TYPE).isSupported) {
                if (!a.this.Z0()) {
                    a.a(a.this);
                }
            }
        }
    }

    private void A0() {
        int errorRet;
        int i2;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10219, new Class[0], Void.TYPE).isSupported) {
            int reTry = 0;
            com.leedarson.smartcamera.utils.e.c("connect start", "p2pid:" + this.s + " account:" + this.t + " password:" + this.u);
            do {
                this.v = IOTCAPIs.IOTC_Get_SessionID();
                com.leedarson.smartcamera.utils.e.c("connectTutk" + reTry, this.s + " IOTC_Get_SessionID:" + this.v);
                errorRet = this.v;
                int i3 = this.v;
                if (i3 >= 0) {
                    this.w = IOTCAPIs.IOTC_Connect_ByUID_Parallel(this.s, i3);
                    errorRet = this.w;
                    com.leedarson.smartcamera.utils.e.c("connectTutk" + reTry, this.s + " IOTC_Connect_ByUID_Parallel: " + this.w);
                    int i4 = this.w;
                    if (i4 >= 0) {
                        St_AVClientStartInConfig av_client_in_config = new St_AVClientStartInConfig();
                        St_AVClientStartOutConfig av_client_out_config = new St_AVClientStartOutConfig();
                        av_client_in_config.iotc_session_id = this.v;
                        av_client_in_config.iotc_channel_id = 0;
                        av_client_in_config.timeout_sec = 15;
                        av_client_in_config.account_or_identity = this.t;
                        av_client_in_config.password_or_token = this.u;
                        av_client_in_config.resend = 1;
                        av_client_in_config.security_mode = this.Y;
                        av_client_in_config.auth_type = 0;
                        this.x = AVAPIs.avClientStartEx(av_client_in_config, av_client_out_config);
                        errorRet = this.x;
                        com.leedarson.smartcamera.utils.e.c("connectTutk" + reTry, this.s + " avClientStartEx: " + this.x);
                        if (this.x == -20039) {
                            this.x = AVAPIs.avClientStart2(this.w, this.t, this.u, 30, new int[1], 0, new int[1]);
                            com.leedarson.smartcamera.utils.e.c("connectTutk" + reTry, this.s + " avClientStart2: " + this.x);
                        }
                    } else if (i4 == -48) {
                        Message msg = Message.obtain();
                        r rVar = this.r;
                        msg.what = 3;
                        msg.arg1 = -2;
                        msg.arg2 = -48;
                        rVar.sendMessage(msg);
                        reTry = 3;
                    }
                }
                com.leedarson.smartcamera.utils.e.c(" ", String.format(Locale.US, "sessiondId:%d connectId:%d channelId:%d", new Object[]{Integer.valueOf(this.v), Integer.valueOf(this.w), Integer.valueOf(this.x)}));
                if (this.x < 0) {
                    com.leedarson.smartcamera.utils.e.c("connectTutk" + reTry, "errorCode:" + errorRet);
                    k1();
                }
                reTry++;
                i2 = this.x;
                if (i2 >= 0) {
                    break;
                }
            } while (reTry < 3);
            if (i2 >= 0) {
                byte[] startBytes = new byte[16];
                try {
                    if (this.f0.length() >= 16) {
                        this.f0 = this.f0.substring(0, 16);
                    }
                    byte[] clientBytes = this.f0.getBytes();
                    System.arraycopy(clientBytes, 0, startBytes, 0, clientBytes.length);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                com.leedarson.smartcamera.utils.e.c("", "IOTYPE_USER_IPCAM_CONNECTION_CHECK_REQ:" + ByteUtil.getHexBinString(startBytes));
                com.leedarson.smartcamera.utils.e.c("", "IOTYPE_USER_IPCAM_CONNECTION_CHECK_REQ ret:" + AVAPIs.avSendIOCtrl(this.w, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CONNECTION_CHECK_REQ, startBytes, startBytes.length));
            }
            Message msg2 = Message.obtain();
            r rVar2 = this.r;
            msg2.what = 1;
            msg2.arg1 = errorRet;
            rVar2.sendMessage(msg2);
        }
    }

    private void k1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10220, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.z;
            if (i2 >= 0) {
                AVAPIs.avServStop(i2);
            }
            int i3 = this.w;
            if (i3 >= 0) {
                int i4 = this.z;
                if (i4 >= 0) {
                    AVAPIs.avServExit(i3, i4);
                }
                int i5 = this.y;
                if (i5 >= 0) {
                    IOTCAPIs.IOTC_Session_Channel_OFF(this.w, i5);
                }
            }
            this.y = -1;
            this.z = -1;
            int i6 = this.x;
            if (i6 > -1) {
                AVAPIs.avSendIOCtrlExit(i6);
                AVAPIs.avClientStop(this.x);
                AVAPIs.avClientExit(this.v, this.x);
            }
            int i7 = this.w;
            if (i7 >= 0) {
                IOTCAPIs.IOTC_Connect_Stop_BySID(i7);
            }
            int i8 = this.v;
            if (i8 >= 0) {
                IOTCAPIs.IOTC_Session_Close(i8);
            }
            com.leedarson.smartcamera.utils.e.c("", this.s + " releaseConnect");
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.o = false;
        }
    }

    /* compiled from: LdsTutkChannel */
    public class l implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10316, new Class[0], Void.TYPE).isSupported) {
                a.b(a.this);
                a.p(a.this);
            }
        }
    }

    public void C0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10221, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("", this.s + " disconnecting");
            this.d.execute(new l());
        }
    }

    private void C1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10222, new Class[0], Void.TYPE).isSupported) {
            this.a = false;
            this.c = false;
            Future future = this.k;
            if (future != null && !future.isCancelled()) {
                this.k.cancel(true);
            }
            Future future2 = this.l;
            if (future2 != null && !future2.isCancelled()) {
                this.l.cancel(true);
            }
            this.v = -1;
            this.w = -1;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.o = false;
            Message msg = Message.obtain();
            r rVar = this.r;
            msg.what = 3;
            msg.arg1 = -1;
            rVar.sendMessage(msg);
        }
    }

    /* compiled from: LdsTutkChannel */
    public class m implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ byte[] d;

        m(int i, byte[] bArr) {
            this.c = i;
            this.d = bArr;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10317, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("sendCtrl", "cmd:" + Integer.toHexString(this.c));
                int result = a.C(a.this, this.c, this.d);
                com.leedarson.smartcamera.utils.e.c("sendCtrl", "cmd:" + Integer.toHexString(this.c) + " res:" + result);
            }
        }
    }

    private void x1(int type, byte[] data) {
        Object[] objArr = {new Integer(type), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10223, new Class[]{Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            this.d.execute(new m(type, data));
        }
    }

    public void M0(com.leedarson.smartcamera.listener.c listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 10224, new Class[]{com.leedarson.smartcamera.listener.c.class}, Void.TYPE).isSupported) {
            this.J = listener;
            x1(802, AVIOCTRLDEFs.SMsgAVIoctrlGetStreamCtrlReq.parseContent(this.w));
        }
    }

    public void G1(int resolution, com.leedarson.smartcamera.listener.k listener) {
        Object[] objArr = {new Integer(resolution), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10225, new Class[]{Integer.TYPE, com.leedarson.smartcamera.listener.k.class}, Void.TYPE).isSupported) {
            this.K = listener;
            x1(800, AVIOCTRLDEFs.SMsgAVIoctrlSetStreamCtrlReq.parseContent(this.w, (byte) resolution));
        }
    }

    public void h1(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10226, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            d1(20, speed);
        }
    }

    public void i1(int speed) {
        if (!PatchProxy.proxy(new Object[]{new Integer(speed)}, this, changeQuickRedirect, false, 10227, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            d1(0, speed);
        }
    }

    public void j1(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10228, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            d1(1, speed);
        }
    }

    public void e1(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10229, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            d1(2, speed);
        }
    }

    public void f1(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10230, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            d1(3, speed);
        }
    }

    public void g1(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10231, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            d1(6, speed);
        }
    }

    public void d1(int control, int speed) {
        Object[] objArr = {new Integer(control), new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10232, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            x1(4097, AVIOCTRLDEFs.SMsgAVIoctrlPtzCmd.parseContent((byte) control, (byte) speed, (byte) 0, (byte) 0, (byte) 0, (byte) this.w));
        }
    }

    public void J1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10233, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c(this.s, " start...");
            this.a = false;
            this.c = false;
            Future future = this.k;
            if (future != null && !future.isCancelled()) {
                this.k.cancel(true);
            }
            Future future2 = this.l;
            if (future2 != null && !future2.isCancelled()) {
                this.l.cancel(true);
            }
            Future future3 = this.a0;
            if (future3 != null && !future3.isCancelled()) {
                com.leedarson.smartcamera.utils.e.c(this.s, " start... 1111");
                this.a0.cancel(true);
            }
            Future future4 = this.b0;
            if (future4 != null && !future4.isCancelled()) {
                this.b0.cancel(true);
            }
            this.a0 = this.d.submit(new n());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class n implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public Object call() {
            int ret;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10318, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive call");
            boolean unused = a.this.X = true;
            if (!a.this.Z0()) {
                com.leedarson.smartcamera.utils.e.c(a.this.s, "connectTutk 2:");
                a.a(a.this);
            }
            com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive 2:");
            if (a.this.Z0()) {
                com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive 3:");
                int reStartNum = 0;
                do {
                    a aVar = a.this;
                    ret = a.q0(aVar, aVar.w, true);
                    reStartNum++;
                    if (ret >= 0 || reStartNum >= 3) {
                        String Q = a.this.s;
                        com.leedarson.smartcamera.utils.e.c(Q, "startLive reStartNum:" + reStartNum + " result:" + ret);
                        Message msg = Message.obtain();
                        r unused2 = a.this.r;
                        msg.what = 2;
                    }
                    a aVar2 = a.this;
                    ret = a.q0(aVar2, aVar2.w, true);
                    reStartNum++;
                    break;
                } while (reStartNum >= 3);
                String Q2 = a.this.s;
                com.leedarson.smartcamera.utils.e.c(Q2, "startLive reStartNum:" + reStartNum + " result:" + ret);
                Message msg2 = Message.obtain();
                r unused3 = a.this.r;
                msg2.what = 2;
                if (ret >= 0) {
                    msg2.arg1 = 1;
                } else {
                    a.this.C0();
                    msg2.arg1 = ret;
                }
                a.this.r.sendMessage(msg2);
            }
            boolean unused4 = a.this.X = false;
            com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive call end");
            return null;
        }
    }

    public void Q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10234, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c(this.s, " start...");
            this.a = false;
            this.c = false;
            Future future = this.k;
            if (future != null && !future.isCancelled()) {
                this.k.cancel(true);
            }
            Future future2 = this.l;
            if (future2 != null && !future2.isCancelled()) {
                this.l.cancel(true);
            }
            Future future3 = this.a0;
            if (future3 != null && !future3.isCancelled()) {
                this.a0.cancel(true);
            }
            Future future4 = this.b0;
            if (future4 != null && !future4.isCancelled()) {
                this.b0.cancel(true);
            }
            this.b0 = this.d.submit(new o());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class o implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public Object call() {
            boolean isSuc;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10319, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            com.leedarson.smartcamera.utils.e.c(a.this.s, "stopLive call");
            int reTry = 0;
            do {
                isSuc = a.s0(a.this);
                reTry++;
                if (isSuc || reTry >= 3) {
                    String Q = a.this.s;
                    com.leedarson.smartcamera.utils.e.c(Q, "stopLive result:" + isSuc + " reTry:" + reTry);
                    Message msg = Message.obtain();
                    r unused = a.this.r;
                    msg.what = 12;
                }
                isSuc = a.s0(a.this);
                reTry++;
                break;
            } while (reTry >= 3);
            String Q2 = a.this.s;
            com.leedarson.smartcamera.utils.e.c(Q2, "stopLive result:" + isSuc + " reTry:" + reTry);
            Message msg2 = Message.obtain();
            r unused2 = a.this.r;
            msg2.what = 12;
            if (isSuc) {
                msg2.arg1 = 1;
            } else {
                msg2.arg1 = 0;
            }
            a.this.r.sendMessage(msg2);
            com.leedarson.smartcamera.utils.e.c(a.this.s, "stopLive call end");
            return null;
        }
    }

    public void F1(int rate) {
        this.h0 = rate;
    }

    public int K0() {
        return this.h0;
    }

    private AudioRecord Y0(int frequency, int channel, int sampbit) {
        AutomaticGainControl automaticGainControl;
        NoiseSuppressor noiseSuppressor;
        AcousticEchoCanceler acousticEchoCanceler;
        Object[] objArr = {new Integer(frequency), new Integer(channel), new Integer(sampbit)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 10235, new Class[]{cls, cls, cls}, AudioRecord.class);
        if (proxy.isSupported) {
            return (AudioRecord) proxy.result;
        }
        this.k0 = AudioRecord.getMinBufferSize(frequency, channel, sampbit);
        AudioRecord audioRecord = new AudioRecord(7, frequency, channel, sampbit, this.k0);
        int sessionId = audioRecord.getAudioSessionId();
        G711Coder.init();
        this.m0 = new G711Coder.CoderResult();
        if (AcousticEchoCanceler.isAvailable() && (acousticEchoCanceler = AcousticEchoCanceler.create(sessionId)) != null) {
            int ret = acousticEchoCanceler.setEnabled(true);
            com.leedarson.smartcamera.utils.e.c("", "initAudioRecord aec: " + ret);
        }
        if (NoiseSuppressor.isAvailable() && (noiseSuppressor = NoiseSuppressor.create(sessionId)) != null) {
            int ret2 = noiseSuppressor.setEnabled(true);
            com.leedarson.smartcamera.utils.e.c("", "initAudioRecord ns: " + ret2);
        }
        if (AutomaticGainControl.isAvailable() && (automaticGainControl = AutomaticGainControl.create(sessionId)) != null) {
            int ret3 = automaticGainControl.setEnabled(true);
            com.leedarson.smartcamera.utils.e.c("", "initAudioRecord agc: " + ret3);
        }
        return audioRecord;
    }

    private int A1(int len, byte[] dataArr) {
        Object[] objArr = {new Integer(len), dataArr};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10236, new Class[]{cls, byte[].class}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.o0 == null) {
            this.o0 = AVIOCTRLDEFs.SFrameInfo.parseContent(AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711A, (byte) 0, (byte) 0, (byte) 0, 0);
        }
        int i2 = this.z;
        byte[] bArr = this.o0;
        return AVAPIs.avSendAudioData(i2, dataArr, len, bArr, bArr.length);
    }

    private int y1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10237, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.p0 == null) {
            this.p0 = AVIOCTRLDEFs.SFrameInfo.parseLastContent(AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711A, (byte) 0, (byte) 0, (byte) 0, 0);
        }
        int i2 = this.z;
        byte[] bArr = {0};
        byte[] bArr2 = this.p0;
        return AVAPIs.avSendAudioData(i2, bArr, 1, bArr2, bArr2.length);
    }

    public void O1(com.leedarson.smartcamera.listener.n listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 10238, new Class[]{com.leedarson.smartcamera.listener.n.class}, Void.TYPE).isSupported) {
            this.M = listener;
            this.l0 = true;
            if (this.Z == 2) {
                this.h0 = 16000;
            }
            U1();
        }
    }

    private int v0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10239, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SPEAKERSTOP, AVIOCTRLDEFs.SMsgAVIoctrlAVStream.parseContent(this.z));
        int i2 = this.z;
        if (i2 < 0) {
            return 0;
        }
        AVAPIs.avServStop(i2);
        int ret3 = -1;
        int i3 = this.w;
        if (i3 >= 0) {
            int i4 = this.z;
            if (i4 >= 0) {
                AVAPIs.avServExit(i3, i4);
            }
            int i5 = this.y;
            if (i5 >= 0) {
                ret3 = IOTCAPIs.IOTC_Session_Channel_OFF(this.w, i5);
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        if (ret3 >= 0) {
            return 0;
        }
        return -1;
    }

    public void T1(com.leedarson.smartcamera.listener.n listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, Data.MAX_DATA_BYTES, new Class[]{com.leedarson.smartcamera.listener.n.class}, Void.TYPE).isSupported) {
            this.M = listener;
            this.l0 = false;
            this.d.execute(new p(listener));
        }
    }

    /* compiled from: LdsTutkChannel */
    public class p implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.smartcamera.listener.n c;

        p(com.leedarson.smartcamera.listener.n nVar) {
            this.c = nVar;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10320, new Class[0], Void.TYPE).isSupported) {
                SystemClock.sleep(500);
                try {
                    if (a.this.n0 != null) {
                        a.this.n0.stop();
                        a.this.n0.release();
                        AudioRecord unused = a.this.n0 = null;
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                int ret = a.e(a.this);
                com.leedarson.smartcamera.utils.e.c("", "stopTalk:" + ret);
                if (ret == 0) {
                    Message msg = Message.obtain();
                    r unused2 = a.this.r;
                    msg.what = 19;
                    a.this.r.sendMessage(msg);
                    return;
                }
                com.leedarson.smartcamera.listener.n nVar = this.c;
                if (nVar != null) {
                    nVar.a(ret);
                }
            }
        }
    }

    private int N1(int i2, boolean z2) {
        int i3;
        int i4;
        Object[] objArr = {new Integer(i2), new Byte(z2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10241, new Class[]{cls, Boolean.TYPE}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        boolean hasAudio = z2;
        int avIndex = i2;
        int ret = AVAPIs.avSendIOCtrl(avIndex, 255, new byte[2], 2);
        if (ret < 0) {
            if (ret == -20016 || (i4 = this.F0) == -20015 || i4 == -20000) {
                com.leedarson.smartcamera.utils.e.c("", String.format(Locale.US, "sessiondId:%d connectId:%d channelId:%d", new Object[]{Integer.valueOf(this.v), Integer.valueOf(this.w), Integer.valueOf(this.x)}));
            }
            com.leedarson.smartcamera.utils.e.c("tutk", this.s + " IOTYPE_INNER_SND_DATA_DELAY start_ipcam_stream failed " + ret);
            return ret;
        }
        byte[] startBytes = new byte[24];
        try {
            if (this.f0.length() >= 16) {
                this.f0 = this.f0.substring(0, 16);
            }
            byte[] clientBytes = this.f0.getBytes();
            System.arraycopy(clientBytes, 0, startBytes, 8, clientBytes.length);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        com.leedarson.smartcamera.utils.e.c("", "IOTYPE_USER_IPCAM_START:" + ByteUtil.getHexBinString(startBytes));
        int ret2 = AVAPIs.avSendIOCtrl(avIndex, 511, startBytes, startBytes.length);
        if (ret2 < 0) {
            if (ret2 == -20016 || (i3 = this.F0) == -20015 || i3 == -20000) {
                com.leedarson.smartcamera.utils.e.c("", String.format(Locale.US, "sessiondId:%d connectId:%d channelId:%d", new Object[]{Integer.valueOf(this.v), Integer.valueOf(this.w), Integer.valueOf(this.x)}));
            }
            com.leedarson.smartcamera.utils.e.c("tutk", this.s + " IOTYPE_USER_IPCAM_START start_ipcam_stream failed " + ret2);
            return ret2;
        } else if (!hasAudio || (ret2 = AVAPIs.avSendIOCtrl(avIndex, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART, new byte[8], 8)) >= 0) {
            com.leedarson.smartcamera.utils.e.c("", this.s + " startStream: success");
            return ret2;
        } else {
            com.leedarson.smartcamera.utils.e.c("", this.s + " 3 start_ipcam_stream failed " + ret2);
            return ret2;
        }
    }

    private boolean S1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10242, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] stopByte = AVIOCTRLDEFs.SMsgAVIoctrlAVStream.parseContent(this.x);
        int stopAudioInt = AVAPIs.avSendIOCtrl(this.x, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP, stopByte, stopByte.length);
        int ret = AVAPIs.avSendIOCtrl(this.x, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_STOP, stopByte, stopByte.length);
        if (stopAudioInt >= 0 && ret >= 0) {
            return true;
        }
        if (stopAudioInt == -20016 || this.F0 == -20015) {
            C0();
        }
        return false;
    }

    public void b1() {
        this.b = true;
    }

    public void m1() {
        this.b = false;
    }

    public void l1() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v2, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v4, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v5, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r45v4, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r45v5, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r45v6, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v14, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v17, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v19, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v6, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v21, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v7, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v20, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v23, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r42v7, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r42v8, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v24, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v25, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r42v10, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v26, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v8, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v9, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v31, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v10, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v11, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v32, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v17, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v36, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v47, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v38, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v21, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v39, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r45v14, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v40, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v19, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r45v15, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v42, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v43, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v50, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v46, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v47, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v48, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v24, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v50, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v21, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v52, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v53, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v25, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v55, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v56, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v44, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v57, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v55, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v58, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v47, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v48, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v68, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v69, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v73, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v75, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v33, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v34, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v80, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v82, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v70, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v74, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r3v46 */
    /* JADX WARNING: Code restructure failed: missing block: B:296:0x0712, code lost:
        if (r4.F0 == -20015) goto L_0x0714;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:238:0x05e2  */
    /* JADX WARNING: Removed duplicated region for block: B:281:0x0685  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x019b A[SYNTHETIC, Splitter:B:68:0x019b] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0203  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void V0() {
        /*
            r49 = this;
            java.lang.String r1 = "8a"
            java.lang.String r2 = "4e"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 10243(0x2803, float:1.4354E-41)
            r5 = r49
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001b
            return
        L_0x001b:
            r4 = r49
            java.lang.String r0 = r4.s
            java.lang.String r5 = " start"
            com.leedarson.smartcamera.utils.e.c(r0, r5)
            r0 = 5529600(0x546000, float:7.74862E-39)
            byte[] r14 = new byte[r0]
            r0 = 16
            byte[] r15 = new byte[r0]
            r13 = 1
            int[] r12 = new int[r13]
            int[] r11 = new int[r13]
            r5 = 0
            r7 = 0
            r10 = 1000(0x3e8, float:1.401E-42)
            r0 = 0
            r4.a = r13
            r4.b = r3
            r9 = 0
            r16 = 0
            int[] r3 = new int[r13]
            r19 = r11
            int[] r11 = new int[r13]
            java.lang.String r20 = ""
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r28 = 0
            r30 = r20
            r20 = r9
            r9 = r0
            r47 = r5
            r5 = r16
            r16 = r7
            r7 = r47
        L_0x0063:
            boolean r0 = r4.a
            if (r0 == 0) goto L_0x0799
            boolean r0 = r4.b
            r31 = 100
            if (r0 != 0) goto L_0x0772
            int r0 = r4.x     // Catch:{ Exception -> 0x075b }
            r33 = 5529600(0x546000, float:7.74862E-39)
            r34 = 16
            r35 = r5
            r5 = r0
            r6 = r14
            r37 = r7
            r7 = r33
            r8 = r3
            r39 = r9
            r9 = r11
            r40 = r10
            r10 = r15
            r33 = r11
            r11 = r34
            r34 = r12
            r41 = r1
            r1 = r13
            r13 = r19
            int r0 = com.tutk.IOTC.AVAPIs.avRecvFrameData2(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch:{ Exception -> 0x0740 }
            r11 = r0
            r0 = -20012(0xffffffffffffb1d4, float:NaN)
            r5 = 0
            if (r11 != r0) goto L_0x0104
            int r0 = (r28 > r5 ? 1 : (r28 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x00a4
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x00ea }
            r28 = r5
            goto L_0x00c1
        L_0x00a4:
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x00ea }
            long r5 = r5 - r28
            r7 = 15000(0x3a98, double:7.411E-320)
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 <= 0) goto L_0x00c1
            android.os.Message r0 = android.os.Message.obtain()     // Catch:{ InterruptedException -> 0x00ea }
            com.leedarson.smartcamera.sdk.a$r r5 = r4.r     // Catch:{ InterruptedException -> 0x00ea }
            r6 = 20
            r0.what = r6     // Catch:{ InterruptedException -> 0x00ea }
            r5.sendMessage(r0)     // Catch:{ InterruptedException -> 0x00ea }
            r5 = 0
            r28 = r5
        L_0x00c1:
            r5 = 30
            java.lang.Thread.sleep(r5)     // Catch:{ InterruptedException -> 0x00ea }
            r13 = r1
            r11 = r33
            r12 = r34
            r5 = r35
            r7 = r37
            r9 = r39
            r10 = r40
            r1 = r41
            goto L_0x0063
        L_0x00d6:
            r0 = move-exception
            r5 = r35
            r7 = r37
            r9 = r39
            r13 = r40
            r35 = r41
            r39 = r3
            r47 = r2
            r2 = r1
            r1 = r47
            goto L_0x076e
        L_0x00ea:
            r0 = move-exception
            java.io.PrintStream r5 = java.lang.System.out     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r6 = r0.getMessage()     // Catch:{ Exception -> 0x00d6 }
            r5.println(r6)     // Catch:{ Exception -> 0x00d6 }
            r8 = r30
            r45 = r35
            r10 = r37
            r13 = r40
            r47 = r39
            r39 = r3
            r3 = r47
            goto L_0x07a6
        L_0x0104:
            java.util.List<com.leedarson.smartcamera.listener.i> r0 = r4.E     // Catch:{ Exception -> 0x0740 }
            int r0 = r0.size()     // Catch:{ Exception -> 0x0740 }
            if (r0 <= 0) goto L_0x0718
            if (r11 < 0) goto L_0x06f1
            r28 = 0
            r7 = 0
            r0 = r3[r7]     // Catch:{ Exception -> 0x0740 }
            r0 = r33[r7]     // Catch:{ Exception -> 0x0740 }
            byte r0 = r15[r7]     // Catch:{ Exception -> 0x0740 }
            r0 = r0 & 255(0xff, float:3.57E-43)
            java.lang.String r0 = java.lang.Integer.toHexString(r0)     // Catch:{ Exception -> 0x0740 }
            r12 = r0
            r0 = 4
            byte[] r7 = new byte[r0]     // Catch:{ Exception -> 0x0740 }
            r13 = r7
            r7 = 12
            r8 = 0
            java.lang.System.arraycopy(r15, r7, r13, r8, r0)     // Catch:{ Exception -> 0x0740 }
            byte[] r0 = new byte[r11]     // Catch:{ Exception -> 0x0740 }
            r10 = r0
            java.lang.System.arraycopy(r14, r8, r10, r8, r11)     // Catch:{ Exception -> 0x0740 }
            r7 = 3
            if (r23 != 0) goto L_0x0138
            byte r0 = r15[r7]     // Catch:{ Exception -> 0x00d6 }
            if (r0 <= 0) goto L_0x0138
            r0 = 1
            r23 = r0
        L_0x0138:
            r8 = r30
            boolean r0 = r8.equals(r12)     // Catch:{ Exception -> 0x06d5 }
            if (r0 != 0) goto L_0x0146
            r20 = 0
            r0 = r12
            r30 = r0
            goto L_0x0148
        L_0x0146:
            r30 = r8
        L_0x0148:
            java.lang.String r8 = "[hyf]"
            r9 = 2
            if (r20 != 0) goto L_0x0197
            byte r0 = r15[r9]     // Catch:{ Exception -> 0x00d6 }
            if (r0 != r1) goto L_0x0197
            r20 = 1
            android.os.Message r0 = android.os.Message.obtain()     // Catch:{ Exception -> 0x00d6 }
            r31 = r0
            com.leedarson.smartcamera.sdk.a$r r0 = r4.r     // Catch:{ Exception -> 0x00d6 }
            r5 = 13
            r6 = r31
            r6.what = r5     // Catch:{ Exception -> 0x00d6 }
            r0.sendMessage(r6)     // Catch:{ Exception -> 0x00d6 }
            r5 = 0
            com.leedarson.smartcamera.utils.c r0 = r4.g0     // Catch:{ Exception -> 0x016d }
            int r0 = r0.b(r10)     // Catch:{ Exception -> 0x016d }
            r5 = r0
            goto L_0x0171
        L_0x016d:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x00d6 }
        L_0x0171:
            byte r0 = r15[r7]     // Catch:{ Exception -> 0x00d6 }
            r22 = r0
            if (r22 != 0) goto L_0x0180
            if (r5 <= 0) goto L_0x0180
            r0 = 1000000(0xf4240, float:1.401298E-39)
            int r0 = r5 / r0
            r22 = r0
        L_0x0180:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00d6 }
            r0.<init>()     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r7 = "frameRate: "
            r0.append(r7)     // Catch:{ Exception -> 0x00d6 }
            r0.append(r5)     // Catch:{ Exception -> 0x00d6 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00d6 }
            android.util.Log.d(r8, r0)     // Catch:{ Exception -> 0x00d6 }
            r6 = r22
            goto L_0x0199
        L_0x0197:
            r6 = r22
        L_0x0199:
            if (r20 != 0) goto L_0x0203
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01ec }
            r0.<init>()     // Catch:{ Exception -> 0x01ec }
            java.lang.String r5 = "[ffmpeg video] bad data type="
            r0.append(r5)     // Catch:{ Exception -> 0x01ec }
            byte r5 = r15[r9]     // Catch:{ Exception -> 0x01ec }
            r0.append(r5)     // Catch:{ Exception -> 0x01ec }
            java.lang.String r5 = " time="
            r0.append(r5)     // Catch:{ Exception -> 0x01ec }
            r44 = r2
            r1 = r35
            r0.append(r1)     // Catch:{ Exception -> 0x01d9 }
            java.lang.String r5 = " codeType="
            r0.append(r5)     // Catch:{ Exception -> 0x01d9 }
            r0.append(r12)     // Catch:{ Exception -> 0x01d9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01d9 }
            android.util.Log.d(r8, r0)     // Catch:{ Exception -> 0x01d9 }
            r22 = r6
            r11 = r33
            r12 = r34
            r7 = r37
            r9 = r39
            r10 = r40
            r13 = 1
            r5 = r1
            r1 = r41
            r2 = r44
            goto L_0x0063
        L_0x01d9:
            r0 = move-exception
            r22 = r6
            r7 = r37
            r9 = r39
            r13 = r40
            r35 = r41
            r5 = r1
            r39 = r3
            r1 = r44
            r2 = 1
            goto L_0x076e
        L_0x01ec:
            r0 = move-exception
            r44 = r2
            r1 = r35
            r22 = r6
            r7 = r37
            r9 = r39
            r13 = r40
            r35 = r41
            r5 = r1
            r39 = r3
            r1 = r44
            r2 = 1
            goto L_0x076e
        L_0x0203:
            r44 = r2
            r1 = r35
            r7 = r44
            boolean r0 = r7.equals(r12)     // Catch:{ Exception -> 0x06ba }
            r35 = 4294967295(0xffffffff, double:2.1219957905E-314)
            java.lang.String r5 = "50"
            if (r0 != 0) goto L_0x03d7
            boolean r0 = r5.equals(r12)     // Catch:{ Exception -> 0x03c1 }
            if (r0 == 0) goto L_0x022b
            r45 = r1
            r2 = r6
            r1 = r7
            r6 = r9
            r9 = r39
            r39 = r3
            r3 = r41
            r41 = r10
            goto L_0x03e4
        L_0x022b:
            r9 = r41
            boolean r0 = r9.equals(r12)     // Catch:{ Exception -> 0x03ac }
            java.lang.String r5 = "87"
            if (r0 != 0) goto L_0x025b
            boolean r0 = r5.equals(r12)     // Catch:{ Exception -> 0x0249 }
            if (r0 == 0) goto L_0x023c
            goto L_0x025b
        L_0x023c:
            r45 = r1
            r2 = r6
            r1 = r7
            r47 = r39
            r39 = r3
            r3 = r9
            r9 = r47
            goto L_0x039d
        L_0x0249:
            r0 = move-exception
            r22 = r6
            r35 = r9
            r9 = r39
            r13 = r40
            r5 = r1
            r39 = r3
            r1 = r7
            r7 = r37
            r2 = 1
            goto L_0x076e
        L_0x025b:
            r0 = 8
            byte r0 = r15[r0]     // Catch:{ Exception -> 0x03ac }
            r8 = 1
            if (r8 != r0) goto L_0x0390
            r45 = r1
            long r0 = r4.q0     // Catch:{ Exception -> 0x0379 }
            int r2 = com.tutk.IOTC.ByteUtil.byte2int(r13)     // Catch:{ Exception -> 0x0379 }
            r31 = r0
            long r0 = (long) r2     // Catch:{ Exception -> 0x0379 }
            long r0 = r0 & r35
            r4.q0 = r0     // Catch:{ Exception -> 0x0379 }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0379 }
            r2 = 0
            r8 = r19[r2]     // Catch:{ Exception -> 0x0379 }
            r41 = r9
            r9 = r39
            if (r8 == r9) goto L_0x036e
            r8 = r19[r2]     // Catch:{ Exception -> 0x035c }
            r18 = r8
            if (r20 == 0) goto L_0x0341
            boolean r8 = r4.a     // Catch:{ Exception -> 0x032d }
            if (r8 == 0) goto L_0x0322
            boolean r5 = r5.equals(r12)     // Catch:{ Exception -> 0x032d }
            if (r5 == 0) goto L_0x02e5
            r5 = 0
            r9 = r5
        L_0x0290:
            java.util.List<com.leedarson.smartcamera.listener.i> r5 = r4.E     // Catch:{ Exception -> 0x032d }
            int r5 = r5.size()     // Catch:{ Exception -> 0x032d }
            if (r9 >= r5) goto L_0x02d8
            java.util.List<com.leedarson.smartcamera.listener.i> r5 = r4.E     // Catch:{ Exception -> 0x032d }
            java.lang.Object r5 = r5.get(r9)     // Catch:{ Exception -> 0x032d }
            com.leedarson.smartcamera.listener.i r5 = (com.leedarson.smartcamera.listener.i) r5     // Catch:{ Exception -> 0x032d }
            r39 = r3
            long r2 = r4.q0     // Catch:{ Exception -> 0x02c6 }
            r22 = 1
            r35 = r0
            r8 = r6
            r1 = r7
            r6 = r2
            r2 = r8
            r8 = r10
            r0 = r9
            r3 = r41
            r9 = r11
            r41 = r10
            r10 = r22
            r5.a(r6, r8, r9, r10)     // Catch:{ Exception -> 0x0312 }
            int r9 = r0 + 1
            r7 = r1
            r6 = r2
            r0 = r35
            r10 = r41
            r2 = 0
            r41 = r3
            r3 = r39
            goto L_0x0290
        L_0x02c6:
            r0 = move-exception
            r2 = r6
            r22 = r2
            r1 = r7
            r9 = r18
            r7 = r37
            r13 = r40
            r35 = r41
            r5 = r45
            r2 = 1
            goto L_0x076e
        L_0x02d8:
            r35 = r0
            r39 = r3
            r2 = r6
            r1 = r7
            r0 = r9
            r3 = r41
            r41 = r10
            goto L_0x034b
        L_0x02e5:
            r35 = r0
            r39 = r3
            r2 = r6
            r1 = r7
            r3 = r41
            r41 = r10
            boolean r0 = r3.equals(r12)     // Catch:{ Exception -> 0x0312 }
            if (r0 == 0) goto L_0x034b
            r0 = 0
        L_0x02f6:
            java.util.List<com.leedarson.smartcamera.listener.i> r5 = r4.E     // Catch:{ Exception -> 0x0312 }
            int r5 = r5.size()     // Catch:{ Exception -> 0x0312 }
            if (r0 >= r5) goto L_0x034b
            java.util.List<com.leedarson.smartcamera.listener.i> r5 = r4.E     // Catch:{ Exception -> 0x0312 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ Exception -> 0x0312 }
            com.leedarson.smartcamera.listener.i r5 = (com.leedarson.smartcamera.listener.i) r5     // Catch:{ Exception -> 0x0312 }
            long r6 = r4.q0     // Catch:{ Exception -> 0x0312 }
            r10 = 2
            r8 = r41
            r9 = r11
            r5.a(r6, r8, r9, r10)     // Catch:{ Exception -> 0x0312 }
            int r0 = r0 + 1
            goto L_0x02f6
        L_0x0312:
            r0 = move-exception
            r22 = r2
            r35 = r3
            r9 = r18
            r7 = r37
            r13 = r40
            r5 = r45
            r2 = 1
            goto L_0x076e
        L_0x0322:
            r35 = r0
            r39 = r3
            r2 = r6
            r1 = r7
            r3 = r41
            r41 = r10
            goto L_0x034b
        L_0x032d:
            r0 = move-exception
            r39 = r3
            r2 = r6
            r22 = r2
            r1 = r7
            r9 = r18
            r7 = r37
            r13 = r40
            r35 = r41
            r5 = r45
            r2 = 1
            goto L_0x076e
        L_0x0341:
            r35 = r0
            r39 = r3
            r2 = r6
            r1 = r7
            r3 = r41
            r41 = r10
        L_0x034b:
            r31 = r2
            r35 = r3
            r9 = r18
            r7 = r37
            r13 = r40
            r5 = r45
            r2 = 1
            r18 = r11
            goto L_0x0690
        L_0x035c:
            r0 = move-exception
            r39 = r3
            r2 = r6
            r22 = r2
            r1 = r7
            r7 = r37
            r13 = r40
            r35 = r41
            r5 = r45
            r2 = 1
            goto L_0x076e
        L_0x036e:
            r35 = r0
            r39 = r3
            r2 = r6
            r1 = r7
            r3 = r41
            r41 = r10
            goto L_0x039d
        L_0x0379:
            r0 = move-exception
            r2 = r6
            r47 = r39
            r39 = r3
            r3 = r9
            r9 = r47
            r22 = r2
            r35 = r3
            r1 = r7
            r7 = r37
            r13 = r40
            r5 = r45
            r2 = 1
            goto L_0x076e
        L_0x0390:
            r45 = r1
            r2 = r6
            r1 = r7
            r41 = r10
            r47 = r39
            r39 = r3
            r3 = r9
            r9 = r47
        L_0x039d:
            r31 = r2
            r35 = r3
            r18 = r11
            r7 = r37
            r13 = r40
            r5 = r45
            r2 = 1
            goto L_0x0690
        L_0x03ac:
            r0 = move-exception
            r45 = r1
            r2 = r6
            r47 = r39
            r39 = r3
            r3 = r9
            r9 = r47
            r22 = r2
            r35 = r3
            r1 = r7
            r7 = r37
            r13 = r40
            goto L_0x03d2
        L_0x03c1:
            r0 = move-exception
            r45 = r1
            r2 = r6
            r9 = r39
            r39 = r3
            r22 = r2
            r1 = r7
            r7 = r37
            r13 = r40
            r35 = r41
        L_0x03d2:
            r5 = r45
            r2 = 1
            goto L_0x076e
        L_0x03d7:
            r45 = r1
            r2 = r6
            r1 = r7
            r6 = r9
            r9 = r39
            r39 = r3
            r3 = r41
            r41 = r10
        L_0x03e4:
            int r0 = com.tutk.IOTC.ByteUtil.byte2int(r13)     // Catch:{ Exception -> 0x06a8 }
            long r6 = (long) r0
            long r6 = r6 & r35
            if (r23 == 0) goto L_0x04bc
            r10 = 2
            byte r0 = r15[r10]     // Catch:{ Exception -> 0x04af }
            r10 = 1
            if (r0 != r10) goto L_0x0407
            r25 = 0
            r24 = 0
            r26 = 0
            r36 = r1
            r31 = r2
            r35 = r3
            r18 = r11
            r32 = r13
            r13 = r40
            goto L_0x05e0
        L_0x0407:
            r10 = 2
            byte r0 = r15[r10]     // Catch:{ Exception -> 0x04af }
            if (r0 != 0) goto L_0x049f
            if (r25 <= 0) goto L_0x0437
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04af }
            r0.<init>()     // Catch:{ Exception -> 0x04af }
            java.lang.String r5 = "[drop p]: "
            r0.append(r5)     // Catch:{ Exception -> 0x04af }
            r5 = 3
            byte r5 = r15[r5]     // Catch:{ Exception -> 0x04af }
            r0.append(r5)     // Catch:{ Exception -> 0x04af }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x04af }
            android.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x04af }
            r22 = r2
            r5 = r6
            r11 = r33
            r12 = r34
            r7 = r37
            r10 = r40
            r13 = 1
            r2 = r1
            r1 = r3
            r3 = r39
            goto L_0x0063
        L_0x0437:
            int r0 = r24 + 1
            r18 = 3
            byte r10 = r15[r18]     // Catch:{ Exception -> 0x04af }
            if (r0 != r10) goto L_0x0469
            int r0 = r24 + 1
            byte r10 = r15[r18]     // Catch:{ Exception -> 0x04af }
            if (r0 != r10) goto L_0x0454
            r35 = 0
            int r0 = (r26 > r35 ? 1 : (r26 == r35 ? 0 : -1))
            if (r0 <= 0) goto L_0x0454
            long r35 = r6 - r26
            r42 = 1000(0x3e8, double:4.94E-321)
            int r0 = (r35 > r42 ? 1 : (r35 == r42 ? 0 : -1))
            if (r0 <= 0) goto L_0x0454
            goto L_0x0469
        L_0x0454:
            r8 = 3
            byte r0 = r15[r8]     // Catch:{ Exception -> 0x04af }
            r24 = r0
            r26 = r6
            r36 = r1
            r31 = r2
            r35 = r3
            r18 = r11
            r32 = r13
            r13 = r40
            goto L_0x05e0
        L_0x0469:
            int r0 = r24 + 1
            if (r0 <= r2) goto L_0x0471
            r0 = 1
            r25 = r0
            goto L_0x0476
        L_0x0471:
            r5 = 3
            byte r0 = r15[r5]     // Catch:{ Exception -> 0x04af }
            r25 = r0
        L_0x0476:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04af }
            r0.<init>()     // Catch:{ Exception -> 0x04af }
            java.lang.String r5 = "[drop p start]: "
            r0.append(r5)     // Catch:{ Exception -> 0x04af }
            r5 = 3
            byte r5 = r15[r5]     // Catch:{ Exception -> 0x04af }
            r0.append(r5)     // Catch:{ Exception -> 0x04af }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x04af }
            android.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x04af }
            r22 = r2
            r5 = r6
            r11 = r33
            r12 = r34
            r7 = r37
            r10 = r40
            r13 = 1
            r2 = r1
            r1 = r3
            r3 = r39
            goto L_0x0063
        L_0x049f:
            r36 = r1
            r31 = r2
            r35 = r3
            r18 = r11
            r32 = r13
            r10 = r37
            r13 = r40
            goto L_0x05de
        L_0x04af:
            r0 = move-exception
            r22 = r2
            r35 = r3
            r5 = r6
            r7 = r37
            r13 = r40
            r2 = 1
            goto L_0x076e
        L_0x04bc:
            r10 = 2
            byte r0 = r15[r10]     // Catch:{ Exception -> 0x0694 }
            r10 = 1
            if (r0 != r10) goto L_0x04d6
            r31 = r6
            r21 = 1
            r36 = r1
            r35 = r3
            r18 = r11
            r37 = r31
            r31 = r2
            r32 = r13
            r13 = r40
            goto L_0x05e0
        L_0x04d6:
            r10 = 2
            byte r0 = r15[r10]     // Catch:{ Exception -> 0x0694 }
            if (r0 != 0) goto L_0x05d0
            r18 = r11
            r10 = r37
            r31 = 0
            int r0 = (r10 > r31 ? 1 : (r10 == r31 ? 0 : -1))
            if (r0 <= 0) goto L_0x05c5
            long r35 = r6 - r16
            r37 = 5000(0x1388, double:2.4703E-320)
            int r0 = (r35 > r37 ? 1 : (r35 == r37 ? 0 : -1))
            if (r0 <= 0) goto L_0x0532
            int r0 = (r16 > r31 ? 1 : (r16 == r31 ? 0 : -1))
            if (r0 <= 0) goto L_0x0532
            r16 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0524 }
            r0.<init>()     // Catch:{ Exception -> 0x0524 }
            r31 = r2
            java.lang.String r2 = "[Frame p] Network recovery diff="
            r0.append(r2)     // Catch:{ Exception -> 0x0518 }
            r35 = r3
            long r2 = r6 - r16
            r0.append(r2)     // Catch:{ Exception -> 0x050e }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x050e }
            android.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x050e }
            goto L_0x0536
        L_0x050e:
            r0 = move-exception
            r5 = r6
            r7 = r10
            r22 = r31
            r13 = r40
            r2 = 1
            goto L_0x076e
        L_0x0518:
            r0 = move-exception
            r35 = r3
            r5 = r6
            r7 = r10
            r22 = r31
            r13 = r40
            r2 = 1
            goto L_0x076e
        L_0x0524:
            r0 = move-exception
            r31 = r2
            r35 = r3
            r5 = r6
            r7 = r10
            r22 = r31
            r13 = r40
            r2 = 1
            goto L_0x076e
        L_0x0532:
            r31 = r2
            r35 = r3
        L_0x0536:
            long r2 = r6 - r10
            r32 = r13
            r13 = r40
            int r0 = r13 + 20
            r36 = r1
            long r0 = (long) r0
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x05a6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x059c }
            r0.<init>()     // Catch:{ Exception -> 0x059c }
            java.lang.String r1 = "[Frame p] drop diff="
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            long r1 = r6 - r10
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            java.lang.String r1 = " IFRMAE="
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            r0.append(r10)     // Catch:{ Exception -> 0x059c }
            java.lang.String r1 = " Pframe="
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            r0.append(r6)     // Catch:{ Exception -> 0x059c }
            java.lang.String r1 = " gopDuration="
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            r0.append(r13)     // Catch:{ Exception -> 0x059c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x059c }
            android.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x059c }
            long r0 = r6 - r10
            int r2 = r13 * 2
            long r2 = (long) r2     // Catch:{ Exception -> 0x059c }
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x058a
            r1 = 0
            int r0 = (r16 > r1 ? 1 : (r16 == r1 ? 0 : -1))
            if (r0 != 0) goto L_0x0587
            java.lang.String r0 = "[Frame p] 丢失超过2个I帧弹窗提示 "
            android.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x059c }
        L_0x0587:
            r0 = r6
            r16 = r0
        L_0x058a:
            r5 = r6
            r7 = r10
            r10 = r13
            r22 = r31
            r11 = r33
            r12 = r34
            r1 = r35
            r2 = r36
            r3 = r39
            r13 = 1
            goto L_0x0063
        L_0x059c:
            r0 = move-exception
            r5 = r6
            r7 = r10
            r22 = r31
            r1 = r36
            r2 = 1
            goto L_0x076e
        L_0x05a6:
            int r0 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r0 >= 0) goto L_0x05c0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x059c }
            r0.<init>()     // Catch:{ Exception -> 0x059c }
            java.lang.String r1 = "[Frame p] drop pts less than I frame diff="
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            long r1 = r6 - r10
            r0.append(r1)     // Catch:{ Exception -> 0x059c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x059c }
            android.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x059c }
        L_0x05c0:
            int r21 = r21 + 1
            r37 = r10
            goto L_0x05e0
        L_0x05c5:
            r36 = r1
            r31 = r2
            r35 = r3
            r32 = r13
            r13 = r40
            goto L_0x05de
        L_0x05d0:
            r36 = r1
            r31 = r2
            r35 = r3
            r18 = r11
            r32 = r13
            r10 = r37
            r13 = r40
        L_0x05de:
            r37 = r10
        L_0x05e0:
            if (r20 == 0) goto L_0x0685
            r1 = 2
            byte r0 = r15[r1]     // Catch:{ Exception -> 0x0676 }
            r2 = 1
            if (r0 == r2) goto L_0x05fe
            byte r0 = r15[r1]     // Catch:{ Exception -> 0x05f4 }
            if (r0 != 0) goto L_0x05ed
            goto L_0x05fe
        L_0x05ed:
            r42 = r6
            r3 = r9
            r1 = r36
            goto L_0x068b
        L_0x05f4:
            r0 = move-exception
            r5 = r6
            r22 = r31
            r1 = r36
            r7 = r37
            goto L_0x076e
        L_0x05fe:
            boolean r0 = r4.a     // Catch:{ Exception -> 0x066f }
            if (r0 == 0) goto L_0x0669
            r1 = r36
            boolean r0 = r1.equals(r12)     // Catch:{ Exception -> 0x0664 }
            if (r0 == 0) goto L_0x0632
            r0 = 0
        L_0x060b:
            java.util.List<com.leedarson.smartcamera.listener.i> r3 = r4.E     // Catch:{ Exception -> 0x0664 }
            int r3 = r3.size()     // Catch:{ Exception -> 0x0664 }
            if (r0 >= r3) goto L_0x062d
            java.util.List<com.leedarson.smartcamera.listener.i> r3 = r4.E     // Catch:{ Exception -> 0x0664 }
            java.lang.Object r3 = r3.get(r0)     // Catch:{ Exception -> 0x0664 }
            r5 = r3
            com.leedarson.smartcamera.listener.i r5 = (com.leedarson.smartcamera.listener.i) r5     // Catch:{ Exception -> 0x0664 }
            r10 = 1
            r42 = r6
            r8 = r41
            r3 = r9
            r9 = r18
            r5.d(r6, r8, r9, r10)     // Catch:{ Exception -> 0x065a }
            int r0 = r0 + 1
            r9 = r3
            r6 = r42
            goto L_0x060b
        L_0x062d:
            r42 = r6
            r3 = r9
            goto L_0x068b
        L_0x0632:
            r42 = r6
            r3 = r9
            boolean r0 = r5.equals(r12)     // Catch:{ Exception -> 0x065a }
            if (r0 == 0) goto L_0x068b
            r0 = 0
        L_0x063c:
            java.util.List<com.leedarson.smartcamera.listener.i> r5 = r4.E     // Catch:{ Exception -> 0x065a }
            int r5 = r5.size()     // Catch:{ Exception -> 0x065a }
            if (r0 >= r5) goto L_0x0659
            java.util.List<com.leedarson.smartcamera.listener.i> r5 = r4.E     // Catch:{ Exception -> 0x065a }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ Exception -> 0x065a }
            com.leedarson.smartcamera.listener.i r5 = (com.leedarson.smartcamera.listener.i) r5     // Catch:{ Exception -> 0x065a }
            r10 = 2
            r6 = r42
            r8 = r41
            r9 = r18
            r5.d(r6, r8, r9, r10)     // Catch:{ Exception -> 0x065a }
            int r0 = r0 + 1
            goto L_0x063c
        L_0x0659:
            goto L_0x068b
        L_0x065a:
            r0 = move-exception
            r9 = r3
            r22 = r31
            r7 = r37
            r5 = r42
            goto L_0x076e
        L_0x0664:
            r0 = move-exception
            r42 = r6
            r3 = r9
            goto L_0x067d
        L_0x0669:
            r42 = r6
            r3 = r9
            r1 = r36
            goto L_0x068b
        L_0x066f:
            r0 = move-exception
            r42 = r6
            r3 = r9
            r1 = r36
            goto L_0x067d
        L_0x0676:
            r0 = move-exception
            r42 = r6
            r3 = r9
            r1 = r36
            r2 = 1
        L_0x067d:
            r22 = r31
            r7 = r37
            r5 = r42
            goto L_0x076e
        L_0x0685:
            r42 = r6
            r3 = r9
            r1 = r36
            r2 = 1
        L_0x068b:
            r9 = r3
            r7 = r37
            r5 = r42
        L_0x0690:
            r22 = r31
            goto L_0x0771
        L_0x0694:
            r0 = move-exception
            r31 = r2
            r35 = r3
            r42 = r6
            r3 = r9
            r10 = r37
            r13 = r40
            r2 = 1
            r7 = r10
            r22 = r31
            r5 = r42
            goto L_0x076e
        L_0x06a8:
            r0 = move-exception
            r31 = r2
            r35 = r3
            r3 = r9
            r10 = r37
            r13 = r40
            r2 = 1
            r7 = r10
            r22 = r31
            r5 = r45
            goto L_0x076e
        L_0x06ba:
            r0 = move-exception
            r45 = r1
            r31 = r6
            r1 = r7
            r10 = r37
            r13 = r40
            r35 = r41
            r2 = 1
            r47 = r39
            r39 = r3
            r3 = r47
            r9 = r3
            r7 = r10
            r22 = r31
            r5 = r45
            goto L_0x076e
        L_0x06d5:
            r0 = move-exception
            r45 = r35
            r10 = r37
            r13 = r40
            r35 = r41
            r47 = r2
            r2 = r1
            r1 = r47
            r48 = r39
            r39 = r3
            r3 = r48
            r9 = r3
            r30 = r8
            r7 = r10
            r5 = r45
            goto L_0x076e
        L_0x06f1:
            r18 = r11
            r8 = r30
            r45 = r35
            r10 = r37
            r13 = r40
            r35 = r41
            r47 = r2
            r2 = r1
            r1 = r47
            r48 = r39
            r39 = r3
            r3 = r48
            r0 = -20016(0xffffffffffffb1d0, float:NaN)
            r5 = r18
            if (r5 == r0) goto L_0x0714
            int r0 = r4.F0     // Catch:{ Exception -> 0x0738 }
            r6 = -20015(0xffffffffffffb1d1, float:NaN)
            if (r0 != r6) goto L_0x0731
        L_0x0714:
            r4.C0()     // Catch:{ Exception -> 0x0738 }
            goto L_0x0731
        L_0x0718:
            r5 = r11
            r8 = r30
            r45 = r35
            r10 = r37
            r13 = r40
            r35 = r41
            r47 = r2
            r2 = r1
            r1 = r47
            r48 = r39
            r39 = r3
            r3 = r48
            android.os.SystemClock.sleep(r31)     // Catch:{ Exception -> 0x0738 }
        L_0x0731:
            r9 = r3
            r30 = r8
            r7 = r10
            r5 = r45
            goto L_0x0771
        L_0x0738:
            r0 = move-exception
            r9 = r3
            r30 = r8
            r7 = r10
            r5 = r45
            goto L_0x076e
        L_0x0740:
            r0 = move-exception
            r8 = r30
            r45 = r35
            r10 = r37
            r13 = r40
            r35 = r41
            r47 = r2
            r2 = r1
            r1 = r47
            r48 = r39
            r39 = r3
            r3 = r48
            r9 = r3
            r7 = r10
            r5 = r45
            goto L_0x076e
        L_0x075b:
            r0 = move-exception
            r35 = r1
            r1 = r2
            r39 = r3
            r45 = r5
            r3 = r9
            r33 = r11
            r34 = r12
            r2 = r13
            r13 = r10
            r10 = r7
            r8 = r30
            r7 = r10
        L_0x076e:
            r0.printStackTrace()
        L_0x0771:
            goto L_0x0787
        L_0x0772:
            r35 = r1
            r1 = r2
            r39 = r3
            r45 = r5
            r3 = r9
            r33 = r11
            r34 = r12
            r2 = r13
            r13 = r10
            r10 = r7
            r8 = r30
            android.os.SystemClock.sleep(r31)
            r7 = r10
        L_0x0787:
            r10 = 1
            android.os.SystemClock.sleep(r10)
            r10 = r13
            r11 = r33
            r12 = r34
            r3 = r39
            r13 = r2
            r2 = r1
            r1 = r35
            goto L_0x0063
        L_0x0799:
            r39 = r3
            r45 = r5
            r3 = r9
            r13 = r10
            r33 = r11
            r34 = r12
            r10 = r7
            r8 = r30
        L_0x07a6:
            java.lang.String r0 = r4.s
            java.lang.String r1 = " end"
            com.leedarson.smartcamera.utils.e.c(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.sdk.a.V0():void");
    }

    private void F0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10244, new Class[0], Void.TYPE).isSupported) {
            int[] frameNumber = new int[1];
            this.c = true;
            byte[] aframeInfo = new byte[16];
            byte[] audioBuffer = new byte[Data.MAX_DATA_BYTES];
            while (this.c) {
                if (!this.b) {
                    int ret = AVAPIs.avCheckAudioBuf(this.x);
                    if (ret >= 0) {
                        if (ret < 3) {
                            try {
                                SystemClock.sleep(30);
                            } catch (Exception e2) {
                                System.out.println(e2.getMessage());
                                return;
                            }
                        } else {
                            int ret2 = AVAPIs.avRecvAudioData(this.x, audioBuffer, Data.MAX_DATA_BYTES, aframeInfo, 16, frameNumber);
                            if (this.E.size() <= 0) {
                                SystemClock.sleep(50);
                            } else if (ret2 > 0) {
                                byte[] data = new byte[ret2];
                                System.arraycopy(audioBuffer, 0, data, 0, ret2);
                                String codeType = Integer.toHexString(aframeInfo[0] & 255);
                                byte[] tbs = new byte[4];
                                System.arraycopy(aframeInfo, 12, tbs, 0, 4);
                                this.q0 = ((long) ByteUtil.byte2int(tbs)) & 4294967295L;
                                if (this.c) {
                                    if ("87".equals(codeType)) {
                                        for (int i2 = 0; i2 < this.E.size(); i2++) {
                                            this.E.get(i2).a(this.q0, data, ret2, 1);
                                        }
                                    } else if ("8a".equals(codeType)) {
                                        for (int i3 = 0; i3 < this.E.size(); i3++) {
                                            this.E.get(i3).a(this.q0, data, ret2, 2);
                                        }
                                    }
                                }
                            } else if (ret2 == -20016 || this.F0 == -20015) {
                                C0();
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    SystemClock.sleep(50);
                }
            }
        }
    }

    private int w1(int type, byte[] data) {
        Object[] objArr = {new Integer(type), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10245, new Class[]{cls, byte[].class}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int i2 = this.w;
        if (i2 < 0 || data == null) {
            com.leedarson.smartcamera.utils.e.c("", "ret:" + this.w);
            return -1;
        }
        int ret = AVAPIs.avSendIOCtrl(i2, type, data, data.length);
        if (ret == -20016 || this.F0 == -20015) {
            C0();
        }
        return ret;
    }

    public void Q0(String startTimeStamp, String endTimeStamp, int type, com.leedarson.smartcamera.listener.e listener) {
        Class<String> cls = String.class;
        Object[] objArr = {startTimeStamp, endTimeStamp, new Integer(type), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10246, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.e.class}, Void.TYPE).isSupported) {
            this.G = listener;
            this.V = null;
            this.V = new byte[0];
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlHasListEventReq.parseConent(0, startTimeStamp, endTimeStamp, type));
        }
    }

    public void N0(String startTime, String endTime, int type, com.leedarson.smartcamera.listener.b listener) {
        Class<String> cls = String.class;
        int i2 = 1;
        Object[] objArr = {startTime, endTime, new Integer(type), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10247, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.b.class}, Void.TYPE).isSupported) {
            this.H = listener;
            this.W.clear();
            if (type != 0) {
                i2 = 18;
            }
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlListEventReq.parseConent(0, startTime, endTime, (byte) i2, (byte) 0));
        }
    }

    public void O0(long j2, long j3, int i2, com.leedarson.smartcamera.listener.b listener) {
        int i3 = 1;
        Object[] objArr = {new Long(j2), new Long(j3), new Integer(i2), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10248, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.b.class}, Void.TYPE).isSupported) {
            long startTime = j2;
            long endTime = j3;
            int type = i2;
            this.H = listener;
            this.W.clear();
            int nextInt = new Random().nextInt(9999);
            this.r0 = nextInt;
            if (type != 0) {
                i3 = 18;
            }
            byte[] bytes = AVIOCTRLDEFs.SMsgAVIoctrlListEventReq.parseConent(0, startTime, endTime, (byte) i3, (byte) 0, nextInt);
            com.leedarson.smartcamera.utils.e.c("getSDRecordList2", this.r0 + " REQ:" + Integer.toHexString(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ) + "==" + ByteUtil.getHexBinString(bytes));
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ, bytes);
        }
    }

    private void L0(long j2, long j3) {
        int i2 = 1;
        Object[] objArr = {new Long(j2), new Long(j3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10250, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            long start = j2;
            long end = j3;
            Log.d("LdsTutkChannel", "getSDRecordList3 2: " + start + "==" + end);
            if (this.u0 != 0) {
                i2 = 18;
            }
            byte[] bytes = AVIOCTRLDEFs.SMsgAVIoctrlListEventReq.parseConent(0, start, end, (byte) i2, (byte) 0, this.r0);
            com.leedarson.smartcamera.utils.e.c("getSDRecordList3", this.r0 + " REQ:" + Integer.toHexString(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ) + "==" + ByteUtil.getHexBinString(bytes));
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ, bytes);
        }
    }

    public void P0(long startTimeStamp, long endTimeStamp, int type, com.leedarson.smartcamera.listener.e listener) {
        Object[] objArr = {new Long(startTimeStamp), new Long(endTimeStamp), new Integer(type), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10251, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.e.class}, Void.TYPE).isSupported) {
            this.G = listener;
            this.V = null;
            this.V = new byte[0];
            int nextInt = new Random().nextInt(9999);
            this.w0 = nextInt;
            byte[] hasListBytes = AVIOCTRLDEFs.SMsgAVIoctrlHasListEventReq.parseConent(0, startTimeStamp, endTimeStamp, type, nextInt);
            com.leedarson.smartcamera.utils.e.c("getSDTimeList", this.w0 + " REQ:" + Integer.toHexString(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_REQ) + "==" + ByteUtil.getHexBinString(hasListBytes));
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_REQ, hasListBytes);
        }
    }

    public void B0(int deleteType, int i2, List<Long> recordTimestamp, com.leedarson.smartcamera.listener.a listener) {
        Object[] objArr = {new Integer(deleteType), new Integer(i2), recordTimestamp, listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10253, new Class[]{cls, cls, List.class, com.leedarson.smartcamera.listener.a.class}, Void.TYPE).isSupported) {
            int eventType = i2;
            this.I = listener;
            if (recordTimestamp != null && recordTimestamp.size() != 0) {
                x1(AVIOCTRLDEFs.OTYPE_USER_IPCAM_DELLISTEVENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDelListEventReq.parseConent(0, recordTimestamp.size(), (byte) 0, (byte) 1, (byte) recordTimestamp.size(), (byte) deleteType, recordTimestamp, eventType));
            }
        }
    }

    public void p1(long j2, int i2) {
        if (!PatchProxy.proxy(new Object[]{new Long(j2), new Integer(i2)}, this, changeQuickRedirect, false, 10254, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            long timestamp = j2;
            int type = i2;
            int i3 = this.N + 1;
            this.N = i3;
            if (i3 > 26) {
                this.N = 1;
            }
            com.leedarson.smartcamera.utils.e.c("sdRecordPlay", timestamp + " sdChannelId:" + this.N);
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 16, 0, this.N, timestamp, 0, type));
            int i4 = this.N;
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 1, 0, i4 == 1 ? 26 : i4 - 1, timestamp, 0, type));
        }
    }

    public void q1(long j2, long j3, int i2) {
        Object[] objArr = {new Long(j2), new Long(j3), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10255, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            long lasttime = j2;
            long timestamp = j3;
            int type = i2;
            int i3 = this.N + 1;
            this.N = i3;
            if (i3 > 26) {
                this.N = 1;
            }
            if (lasttime > 0) {
                int i4 = this.N;
                x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 1, 0, i4 == 1 ? 26 : i4 - 1, lasttime, 0, type));
            }
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 16, 0, this.N, timestamp, 0, type));
        }
    }

    public void t1(long timestamp, int type, int seekFlag) {
        long j2 = timestamp;
        Object[] objArr = {new Long(timestamp), new Integer(type), new Integer(seekFlag)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10256, new Class[]{Long.TYPE, cls, cls}, Void.TYPE).isSupported) {
            this.C = false;
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 6, seekFlag, this.N, timestamp, 0, type));
        }
    }

    public void s1(long timestamp, int type) {
        Object[] objArr = {new Long(timestamp), new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10257, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            this.C = false;
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 17, 0, this.N, timestamp, 0, type));
        }
    }

    public void u1(long timestamp, int type) {
        Object[] objArr = {new Long(timestamp), new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10258, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            this.B = false;
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 1, 0, this.N, timestamp, 0, type));
        }
    }

    public void o1(long timestamp, int type) {
        Object[] objArr = {new Long(timestamp), new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10259, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            this.C = true;
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 0, 0, this.N, timestamp, 0, type));
        }
    }

    public void r1(long timestamp, int type) {
        Object[] objArr = {new Long(timestamp), new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10260, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            if (timestamp > 0) {
                u1(timestamp, type);
            }
            Future future = this.m;
            if (future != null && !future.isCancelled()) {
                this.m.cancel(true);
            }
            this.d.submit(new q());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class q implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10322, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (a.this.A < 0) {
                return null;
            }
            AVAPIs.avClientStop(a.this.A);
            int unused = a.this.A = -1;
            return null;
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r27v5, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v17, types: [byte] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x022b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void R0(int r49) {
        /*
            r48 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r8 = r49
            r2.<init>(r8)
            r12 = 0
            r1[r12] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Integer.TYPE
            r6[r12] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10261(0x2815, float:1.4379E-41)
            r2 = r48
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0025
            return
        L_0x0025:
            r1 = r48
            r3 = r49
            r2 = 5529600(0x546000, float:7.74862E-39)
            byte[] r2 = new byte[r2]
            int[] r13 = new int[r0]
            int[] r14 = new int[r0]
            r4 = 16
            byte[] r15 = new byte[r4]
            int[] r11 = new int[r0]
            int[] r10 = new int[r0]
            r1.B = r0
            r1.C = r12
            r1.x0 = r12
            r4 = 0
            r5 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r16 = 0
            r17 = 0
            r19 = 0
            r21 = 0
            r23 = 1000(0x3e8, float:1.401E-42)
            r24 = 0
            r25 = 0
            r26 = r8
            r27 = r9
            r28 = r16
            r29 = r17
            r16 = r4
            r17 = r5
            r9 = r7
        L_0x0061:
            boolean r4 = r1.B
            if (r4 == 0) goto L_0x03a0
            boolean r4 = r1.C
            if (r4 != 0) goto L_0x0387
            r5 = 5529600(0x546000, float:7.74862E-39)
            r31 = 16
            r4 = r2
            r6 = r13
            r7 = r14
            r8 = r15
            r32 = r9
            r9 = r31
            r31 = r10
            r10 = r11
            r33 = r11
            r11 = r31
            int r4 = com.tutk.IOTC.AVAPIs.avRecvFrameData2(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r5 = 3
            if (r4 >= r5) goto L_0x0089
            r6 = 50
            android.os.SystemClock.sleep(r6)
        L_0x0089:
            java.util.List<com.leedarson.smartcamera.listener.i> r6 = r1.E
            int r6 = r6.size()
            if (r6 <= 0) goto L_0x036e
            if (r4 < 0) goto L_0x0357
            r6 = 7
            byte r6 = r15[r6]
            if (r6 != r0) goto L_0x00a9
            com.leedarson.smartcamera.listener.h r6 = r1.F
            if (r6 == 0) goto L_0x00a9
            java.lang.String r6 = r1.s
            java.lang.String r7 = "reciveStreamEnd ... countDecoderStreamInfo"
            com.leedarson.smartcamera.utils.e.c(r6, r7)
            com.leedarson.smartcamera.listener.h r6 = r1.F
            r6.e()
        L_0x00a9:
            r6 = 5
            byte r7 = r15[r6]
            if (r7 <= 0) goto L_0x00c5
            boolean r7 = r1.x0
            if (r7 != 0) goto L_0x00c5
            android.os.Message r7 = android.os.Message.obtain()
            com.leedarson.smartcamera.sdk.a$r r8 = r1.r
            r9 = 18
            r7.what = r9
            byte r9 = r15[r6]
            r7.arg1 = r9
            r8.sendMessage(r7)
            r1.x0 = r0
        L_0x00c5:
            r7 = 19
            if (r4 < r7) goto L_0x034d
            r8 = 4
            byte[] r9 = new byte[r8]
            r10 = 2
            java.lang.System.arraycopy(r2, r10, r9, r12, r10)
            int r11 = com.tutk.IOTC.ByteUtil.byte2int(r9)
            byte r6 = r2[r10]
            if (r6 != r0) goto L_0x00e5
            byte r6 = r2[r5]
            if (r6 != r0) goto L_0x00e5
            r6 = 24
            r1.y0 = r6
            byte r6 = r2[r7]
            r1.z0 = r6
            goto L_0x00e9
        L_0x00e5:
            r1.y0 = r7
            r1.z0 = r12
        L_0x00e9:
            byte r6 = r2[r8]
            r7 = 5
            byte r5 = r2[r7]
            r7 = 8
            byte[] r10 = new byte[r7]
            r0 = 6
            java.lang.System.arraycopy(r2, r0, r10, r12, r7)
            long r35 = com.tutk.IOTC.ByteUtil.byte2longBig(r10)
            r37 = 4294967295(0xffffffff, double:2.1219957905E-314)
            r7 = r13
            long r12 = r35 & r37
            r35 = 14
            byte r35 = r2[r35]
            byte[] r0 = new byte[r8]
            r37 = r3
            r3 = 15
            r38 = r7
            r7 = 0
            java.lang.System.arraycopy(r2, r3, r0, r7, r8)
            int r7 = com.tutk.IOTC.ByteUtil.byte2intBig(r0)
            int r3 = r1.y0
            int r8 = r7 + r3
            if (r4 < r8) goto L_0x033c
            if (r35 != 0) goto L_0x032c
            r8 = 5
            if (r6 != r8) goto L_0x0196
            if (r5 != 0) goto L_0x0154
            byte[] r8 = new byte[r7]
            r1.q0 = r12
            r49 = r0
            r0 = 0
            java.lang.System.arraycopy(r2, r3, r8, r0, r7)
            if (r16 == 0) goto L_0x018f
            boolean r3 = r1.B
            if (r3 == 0) goto L_0x018f
            r3 = 0
        L_0x0134:
            java.util.List<com.leedarson.smartcamera.listener.i> r0 = r1.E
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x018f
            java.util.List<com.leedarson.smartcamera.listener.i> r0 = r1.E
            java.lang.Object r0 = r0.get(r3)
            com.leedarson.smartcamera.listener.i r0 = (com.leedarson.smartcamera.listener.i) r0
            r44 = 1
            r39 = r0
            r40 = r12
            r42 = r8
            r43 = r7
            r39.a(r40, r42, r43, r44)
            int r3 = r3 + 1
            goto L_0x0134
        L_0x0154:
            r49 = r0
            r0 = 1
            if (r5 != r0) goto L_0x018f
            byte[] r0 = new byte[r7]
            r1.q0 = r12
            r8 = 0
            java.lang.System.arraycopy(r2, r3, r0, r8, r7)
            if (r16 == 0) goto L_0x0188
            boolean r3 = r1.B
            if (r3 == 0) goto L_0x0188
            r3 = 0
        L_0x0168:
            java.util.List<com.leedarson.smartcamera.listener.i> r8 = r1.E
            int r8 = r8.size()
            if (r3 >= r8) goto L_0x0188
            java.util.List<com.leedarson.smartcamera.listener.i> r8 = r1.E
            java.lang.Object r8 = r8.get(r3)
            com.leedarson.smartcamera.listener.i r8 = (com.leedarson.smartcamera.listener.i) r8
            r44 = 2
            r39 = r8
            r40 = r12
            r42 = r0
            r43 = r7
            r39.a(r40, r42, r43, r44)
            int r3 = r3 + 1
            goto L_0x0168
        L_0x0188:
            r45 = r2
            r8 = r32
            r9 = 1
            goto L_0x034b
        L_0x018f:
            r45 = r2
            r8 = r32
            r9 = 1
            goto L_0x034b
        L_0x0196:
            r49 = r0
            r0 = 2
            if (r6 == r0) goto L_0x01a9
            r0 = 3
            if (r6 == r0) goto L_0x01a9
            r0 = 4
            if (r6 != r0) goto L_0x01a2
            goto L_0x01a9
        L_0x01a2:
            r45 = r2
            r8 = r32
            r9 = 1
            goto L_0x034b
        L_0x01a9:
            if (r26 != 0) goto L_0x01b3
            r0 = 3
            byte r8 = r15[r0]
            if (r8 <= 0) goto L_0x01b3
            r0 = 1
            r26 = r0
        L_0x01b3:
            r17 = r12
            byte[] r0 = new byte[r7]
            r8 = 0
            java.lang.System.arraycopy(r2, r3, r0, r8, r7)
            r3 = 2
            if (r6 != r3) goto L_0x01ef
            if (r16 != 0) goto L_0x01ef
            java.lang.String r3 = r1.s
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r45 = r2
            java.lang.String r2 = "[hyf]MESSAGE_RECEIVE_IFRAME ..."
            r8.append(r2)
            r8.append(r12)
            java.lang.String r2 = r8.toString()
            com.leedarson.smartcamera.utils.e.c(r3, r2)
            r2 = 1
            android.os.Message r3 = android.os.Message.obtain()
            r8 = 13
            r3.what = r8
            java.lang.Long r8 = java.lang.Long.valueOf(r12)
            r3.obj = r8
            com.leedarson.smartcamera.sdk.a$r r8 = r1.r
            r8.sendMessage(r3)
            r16 = r2
            goto L_0x01f1
        L_0x01ef:
            r45 = r2
        L_0x01f1:
            r2 = 2
            if (r6 != r2) goto L_0x01fe
            r2 = 3
            byte r3 = r15[r2]
            r8 = r32
            if (r8 == r3) goto L_0x0200
            byte r3 = r15[r2]
            goto L_0x0201
        L_0x01fe:
            r8 = r32
        L_0x0200:
            r3 = r8
        L_0x0201:
            java.lang.String r2 = "[hyf]"
            if (r16 != 0) goto L_0x022b
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r32 = r5
            java.lang.String r5 = "[ffmpeg video] bad data type="
            r8.append(r5)
            r5 = 2
            byte r5 = r15[r5]
            r8.append(r5)
            java.lang.String r5 = " time="
            r8.append(r5)
            r46 = r6
            r5 = r17
            r8.append(r5)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r2, r8)
            goto L_0x0264
        L_0x022b:
            r32 = r5
            r46 = r6
            r5 = r17
            if (r26 == 0) goto L_0x02d1
            r34 = r9
            r8 = 2
            byte r9 = r15[r8]
            r8 = 1
            if (r9 != r8) goto L_0x0245
            r28 = 0
            r27 = 0
            r29 = 0
            r47 = r10
            goto L_0x02d5
        L_0x0245:
            r8 = 2
            byte r8 = r15[r8]
            if (r8 != 0) goto L_0x02ce
            if (r28 <= 0) goto L_0x0275
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "[drop p]: "
            r8.append(r9)
            r9 = 3
            byte r9 = r15[r9]
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.util.Log.e(r2, r8)
        L_0x0264:
            r9 = r3
            r17 = r5
            r10 = r31
            r11 = r33
            r3 = r37
            r13 = r38
            r2 = r45
            r0 = 1
            r12 = 0
            goto L_0x0061
        L_0x0275:
            r9 = 3
            int r8 = r27 + 1
            r47 = r10
            byte r10 = r15[r9]
            if (r8 != r10) goto L_0x0299
            int r8 = r27 + 1
            byte r10 = r15[r9]
            if (r8 != r10) goto L_0x0293
            r8 = 0
            int r8 = (r29 > r8 ? 1 : (r29 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0293
            long r17 = r5 - r29
            r8 = 1000(0x3e8, double:4.94E-321)
            int r8 = (r17 > r8 ? 1 : (r17 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0293
            goto L_0x0299
        L_0x0293:
            r2 = 3
            byte r27 = r15[r2]
            r29 = r5
            goto L_0x02d5
        L_0x0299:
            int r8 = r27 + 1
            if (r8 <= r3) goto L_0x02a2
            r8 = 1
            r28 = r8
            r8 = 3
            goto L_0x02a7
        L_0x02a2:
            r8 = 3
            byte r9 = r15[r8]
            r28 = r9
        L_0x02a7:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "[drop p start]: "
            r9.append(r10)
            byte r8 = r15[r8]
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            android.util.Log.e(r2, r8)
            r9 = r3
            r17 = r5
            r10 = r31
            r11 = r33
            r3 = r37
            r13 = r38
            r2 = r45
            r0 = 1
            r12 = 0
            goto L_0x0061
        L_0x02ce:
            r47 = r10
            goto L_0x02d5
        L_0x02d1:
            r34 = r9
            r47 = r10
        L_0x02d5:
            if (r16 == 0) goto L_0x0328
            boolean r2 = r1.B
            if (r2 == 0) goto L_0x0326
            int r2 = r1.z0
            if (r2 != 0) goto L_0x0302
            r2 = 0
        L_0x02e0:
            java.util.List<com.leedarson.smartcamera.listener.i> r8 = r1.E
            int r8 = r8.size()
            if (r2 >= r8) goto L_0x0300
            java.util.List<com.leedarson.smartcamera.listener.i> r8 = r1.E
            java.lang.Object r8 = r8.get(r2)
            com.leedarson.smartcamera.listener.i r8 = (com.leedarson.smartcamera.listener.i) r8
            r44 = 1
            r39 = r8
            r40 = r12
            r42 = r0
            r43 = r7
            r39.d(r40, r42, r43, r44)
            int r2 = r2 + 1
            goto L_0x02e0
        L_0x0300:
            r9 = 1
            goto L_0x0329
        L_0x0302:
            r9 = 1
            if (r2 != r9) goto L_0x0329
            r2 = 0
        L_0x0306:
            java.util.List<com.leedarson.smartcamera.listener.i> r8 = r1.E
            int r8 = r8.size()
            if (r2 >= r8) goto L_0x0329
            java.util.List<com.leedarson.smartcamera.listener.i> r8 = r1.E
            java.lang.Object r8 = r8.get(r2)
            com.leedarson.smartcamera.listener.i r8 = (com.leedarson.smartcamera.listener.i) r8
            r44 = 2
            r39 = r8
            r40 = r12
            r42 = r0
            r43 = r7
            r39.d(r40, r42, r43, r44)
            int r2 = r2 + 1
            goto L_0x0306
        L_0x0326:
            r9 = 1
            goto L_0x0329
        L_0x0328:
            r9 = 1
        L_0x0329:
            r17 = r5
            goto L_0x034c
        L_0x032c:
            r49 = r0
            r45 = r2
            r46 = r6
            r34 = r9
            r47 = r10
            r8 = r32
            r9 = 1
            r32 = r5
            goto L_0x034b
        L_0x033c:
            r49 = r0
            r45 = r2
            r46 = r6
            r34 = r9
            r47 = r10
            r8 = r32
            r9 = 1
            r32 = r5
        L_0x034b:
            r3 = r8
        L_0x034c:
            goto L_0x0378
        L_0x034d:
            r9 = r0
            r45 = r2
            r37 = r3
            r38 = r13
            r8 = r32
            goto L_0x0377
        L_0x0357:
            r9 = r0
            r45 = r2
            r37 = r3
            r38 = r13
            r8 = r32
            r0 = -20016(0xffffffffffffb1d0, float:NaN)
            if (r4 == r0) goto L_0x036a
            int r0 = r1.F0
            r2 = -20015(0xffffffffffffb1d1, float:NaN)
            if (r0 != r2) goto L_0x0377
        L_0x036a:
            r1.C0()
            goto L_0x0377
        L_0x036e:
            r9 = r0
            r45 = r2
            r37 = r3
            r38 = r13
            r8 = r32
        L_0x0377:
            r3 = r8
        L_0x0378:
            r0 = r9
            r10 = r31
            r11 = r33
            r13 = r38
            r2 = r45
            r12 = 0
            r9 = r3
            r3 = r37
            goto L_0x0061
        L_0x0387:
            r45 = r2
            r37 = r3
            r8 = r9
            r31 = r10
            r33 = r11
            r38 = r13
            r9 = r0
            r2 = 100
            android.os.SystemClock.sleep(r2)
            r3 = r37
            r2 = r45
            r12 = 0
            r9 = r8
            goto L_0x0061
        L_0x03a0:
            r45 = r2
            java.lang.String r0 = "TAG"
            java.lang.String r2 = "getSDVideoStream: end"
            android.util.Log.e(r0, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.sdk.a.R0(int):void");
    }

    private void I0(int i2) {
        byte[] videoBuffer;
        int[] outFrameSize;
        int[] outBufSize;
        int avIndex;
        byte b2;
        byte[] frameInfo;
        int i3;
        a aVar;
        byte b3 = 1;
        int i4 = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(i2)}, this, changeQuickRedirect, false, 10262, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a aVar2 = this;
            int avIndex2 = i2;
            byte[] videoBuffer2 = new byte[5529600];
            int[] outBufSize2 = new int[1];
            int[] outFrameSize2 = new int[1];
            byte[] frameInfo2 = new byte[16];
            int[] outFrmInfoBufSize = new int[1];
            int[] frameNumber = new int[1];
            while (aVar2.D) {
                int ret = AVAPIs.avRecvFrameData2(avIndex2, videoBuffer2, 5529600, outBufSize2, outFrameSize2, frameInfo2, 16, outFrmInfoBufSize, frameNumber);
                if (ret > 0) {
                    Log.d("TAG", "avRecvFrameData2 avindex:" + avIndex2 + " getPicStream:" + ret + " ");
                }
                if (ret < 3) {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                if (aVar2.Q == null) {
                    b2 = b3;
                    aVar = aVar2;
                    avIndex = avIndex2;
                    videoBuffer = videoBuffer2;
                    outBufSize = outBufSize2;
                    outFrameSize = outFrameSize2;
                    frameInfo = frameInfo2;
                    i3 = i4;
                } else if (ret >= 0) {
                    byte b4 = frameInfo2[7];
                    if (ret >= 19) {
                        byte[] vbs = new byte[4];
                        System.arraycopy(videoBuffer2, 2, vbs, i4, 2);
                        int byte2int = ByteUtil.byte2int(vbs);
                        if (videoBuffer2[2] == b3 && videoBuffer2[3] == b3) {
                            aVar2.y0 = 24;
                        } else {
                            aVar2.y0 = 19;
                        }
                        byte frameType = videoBuffer2[4];
                        byte eventType = videoBuffer2[5];
                        byte[] tbs = new byte[8];
                        System.arraycopy(videoBuffer2, 6, tbs, 0, 8);
                        aVar = aVar2;
                        long timeStamp1 = ByteUtil.byte2longBig(tbs);
                        byte encryptionType = videoBuffer2[14];
                        avIndex = avIndex2;
                        byte[] pbs = new byte[4];
                        outBufSize = outBufSize2;
                        outFrameSize = outFrameSize2;
                        System.arraycopy(videoBuffer2, 15, pbs, 0, 4);
                        int payloadLen = ByteUtil.byte2intBig(pbs);
                        if (encryptionType != 0) {
                            videoBuffer = videoBuffer2;
                            int i5 = payloadLen;
                            i3 = 0;
                            frameInfo = frameInfo2;
                            b2 = 1;
                        } else if (frameType == 6) {
                            byte[] abs = new byte[payloadLen];
                            byte[] bArr = pbs;
                            System.arraycopy(videoBuffer2, aVar.y0, abs, 0, payloadLen);
                            String eventT = "";
                            if (eventType == 0) {
                                eventT = NotificationCompat.CATEGORY_EVENT;
                            } else if (eventType == 1) {
                                eventT = "all";
                            }
                            String str = aVar.s;
                            videoBuffer = videoBuffer2;
                            StringBuilder sb = new StringBuilder();
                            sb.append(timeStamp1);
                            int i6 = payloadLen;
                            sb.append("");
                            boolean iss = com.leedarson.smartcamera.utils.d.b(eventT, str, sb.toString());
                            boolean z2 = iss;
                            boolean isi = com.leedarson.smartcamera.utils.d.h(eventT, aVar.s, timeStamp1 + "", abs);
                            String str2 = eventT;
                            boolean z3 = isi;
                            frameInfo = frameInfo2;
                            i3 = 0;
                            b2 = 1;
                            aVar.Q.a(timeStamp1, String.format(Locale.US, "%s-%s-%s.jpg", new Object[]{aVar.S, aVar.s, timeStamp1 + ""}));
                        } else {
                            videoBuffer = videoBuffer2;
                            int i7 = payloadLen;
                            i3 = 0;
                            frameInfo = frameInfo2;
                            b2 = 1;
                        }
                    } else {
                        b2 = b3;
                        aVar = aVar2;
                        avIndex = avIndex2;
                        videoBuffer = videoBuffer2;
                        outBufSize = outBufSize2;
                        outFrameSize = outFrameSize2;
                        frameInfo = frameInfo2;
                        i3 = i4;
                    }
                } else {
                    b2 = b3;
                    aVar = aVar2;
                    avIndex = avIndex2;
                    videoBuffer = videoBuffer2;
                    outBufSize = outBufSize2;
                    outFrameSize = outFrameSize2;
                    frameInfo = frameInfo2;
                    i3 = i4;
                }
                aVar2 = aVar;
                i4 = i3;
                frameInfo2 = frameInfo;
                b3 = b2;
                avIndex2 = avIndex;
                outBufSize2 = outBufSize;
                outFrameSize2 = outFrameSize;
                videoBuffer2 = videoBuffer;
            }
        }
    }

    public void I1(com.leedarson.smartcamera.listener.d thumbnaiRespListener) {
        this.Q = thumbnaiRespListener;
    }

    public boolean a1() {
        return this.P;
    }

    public void T0(String event, List<Long> timers, com.leedarson.smartcamera.listener.d listener) {
        Class[] clsArr = {String.class, List.class, com.leedarson.smartcamera.listener.d.class};
        if (!PatchProxy.proxy(new Object[]{event, timers, listener}, this, changeQuickRedirect, false, 10263, clsArr, Void.TYPE).isSupported) {
            this.S = event;
            this.T = timers;
            this.Q = listener;
            int i2 = this.O + 1;
            this.O = i2;
            if (i2 > 31) {
                this.O = 27;
            }
            if (this.P) {
                J0(this.O, event, timers);
            } else {
                z0(this.O);
            }
        }
    }

    public void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10264, new Class[0], Void.TYPE).isSupported) {
            if (this.P) {
                com.leedarson.smartcamera.listener.d dVar = this.Q;
                if (dVar != null) {
                    dVar.c(1);
                    return;
                }
                return;
            }
            int i2 = this.O + 1;
            this.O = i2;
            if (i2 > 31) {
                this.O = 27;
            }
            z0(this.O);
        }
    }

    private void z0(int picchannelId) {
        Object[] objArr = {new Integer(picchannelId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10265, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            JSONObject connectObj = new JSONObject();
            try {
                connectObj.put("seq", 11111);
                connectObj.put("handlerName", (Object) "AppCallFirmware");
                connectObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) H5ActionName.TUTK_THUMBNAI);
                connectObj.put("command", (Object) "connect");
                connectObj.put("code", 200);
                connectObj.put("channel", picchannelId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ, connectObj.toString().getBytes());
        }
    }

    public void D0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10266, new Class[0], Void.TYPE).isSupported) {
            Log.e("TAG", "disConnectThumbnai: " + this.P);
            this.S = null;
            this.T = null;
            if (this.P) {
                JSONObject disconnectObj = new JSONObject();
                try {
                    disconnectObj.put("seq", 44444);
                    disconnectObj.put("handlerName", (Object) "AppCallFirmware");
                    disconnectObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) H5ActionName.TUTK_THUMBNAI);
                    disconnectObj.put("command", (Object) "disconnect");
                    disconnectObj.put("code", 200);
                    disconnectObj.put("channel", this.O);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ, disconnectObj.toString().getBytes());
                P1();
                this.D = false;
                Future future = this.n;
                if (future != null && !future.isCancelled()) {
                    this.n.cancel(true);
                }
                Future future2 = this.q;
                if (future2 != null && !future2.isCancelled()) {
                    this.q.cancel(true);
                }
                R1();
            }
        }
    }

    /* renamed from: com.leedarson.smartcamera.sdk.a$a  reason: collision with other inner class name */
    /* compiled from: LdsTutkChannel */
    public class C0178a implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0178a() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10304, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            AVAPIs.avClientStop(a.this.A0);
            boolean unused = a.this.P = false;
            if (a.this.Q == null) {
                return null;
            }
            a.this.Q.c(0);
            return null;
        }
    }

    private void R1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10267, new Class[0], Void.TYPE).isSupported) {
            this.d.submit(new C0178a());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class r extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        public r(Looper mainLooper) {
            super(mainLooper);
        }

        public void handleMessage(Message msg) {
            short[] sa;
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10323, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        if (a.this.Z0()) {
                            int unused = a.this.d0 = 1;
                            for (int i = 0; i < a.this.E.size(); i++) {
                                ((com.leedarson.smartcamera.listener.i) a.this.E.get(i)).e(1);
                            }
                            boolean unused2 = a.this.o = true;
                            a.n(a.this);
                            return;
                        }
                        int unused3 = a.this.d0 = -1;
                        boolean unused4 = a.this.o = false;
                        for (int i2 = 0; i2 < a.this.E.size(); i2++) {
                            com.leedarson.smartcamera.listener.i onTutkListener = (com.leedarson.smartcamera.listener.i) a.this.E.get(i2);
                            onTutkListener.b(msg.arg1);
                            onTutkListener.e(-1);
                        }
                        return;
                    case 2:
                        Log.e("TAG", "MESSAGE_STREAM_START_RESULT: ");
                        if (msg.arg1 == 1) {
                            for (int i3 = 0; i3 < a.this.E.size(); i3++) {
                                ((com.leedarson.smartcamera.listener.i) a.this.E.get(i3)).e(2);
                            }
                            Callable callable = new C0180a();
                            a aVar = a.this;
                            Future unused5 = aVar.k = aVar.g.submit(callable);
                            Callable callable1 = new b();
                            a aVar2 = a.this;
                            Future unused6 = aVar2.l = aVar2.h.submit(callable1);
                            return;
                        }
                        for (int i4 = 0; i4 < a.this.E.size(); i4++) {
                            com.leedarson.smartcamera.listener.i onTutkListener2 = (com.leedarson.smartcamera.listener.i) a.this.E.get(i4);
                            onTutkListener2.b(msg.arg1);
                            onTutkListener2.e(3);
                        }
                        return;
                    case 3:
                        int unused7 = a.this.d0 = msg.arg1;
                        for (int i5 = 0; i5 < a.this.E.size(); i5++) {
                            com.leedarson.smartcamera.listener.i onTutkListener3 = (com.leedarson.smartcamera.listener.i) a.this.E.get(i5);
                            if (msg.arg1 == -2) {
                                onTutkListener3.b(msg.arg2);
                            }
                            onTutkListener3.e(msg.arg1);
                        }
                        return;
                    case 4:
                        return;
                    case 6:
                        List<Long> records = (List) msg.obj;
                        if (a.this.H != null && records != null) {
                            a.this.H.onSuccess(records);
                            return;
                        }
                        return;
                    case 7:
                        if (a.this.I != null) {
                            a.this.I.onSuccess();
                            return;
                        }
                        return;
                    case 8:
                        boolean unused8 = a.this.B = false;
                        boolean unused9 = a.this.a = false;
                        boolean unused10 = a.this.c = false;
                        if (msg.arg1 == 1) {
                            for (int i6 = 0; i6 < a.this.E.size(); i6++) {
                                ((com.leedarson.smartcamera.listener.i) a.this.E.get(i6)).e(2);
                            }
                            if (a.this.m != null && !a.this.m.isCancelled()) {
                                a.this.m.cancel(true);
                            }
                            Callable callable2 = new c();
                            a aVar3 = a.this;
                            Future unused11 = aVar3.m = aVar3.i.submit(callable2);
                            return;
                        }
                        for (int i7 = 0; i7 < a.this.E.size(); i7++) {
                            com.leedarson.smartcamera.listener.i onTutkListener4 = (com.leedarson.smartcamera.listener.i) a.this.E.get(i7);
                            onTutkListener4.b(msg.arg1);
                            onTutkListener4.e(3);
                        }
                        return;
                    case 11:
                        if (a.this.F != null) {
                            com.leedarson.smartcamera.utils.e.c(a.this.s, "reciveStreamEnd ...");
                            a.this.F.e();
                            return;
                        }
                        return;
                    case 12:
                        boolean unused12 = a.this.a = false;
                        boolean unused13 = a.this.c = false;
                        if (a.this.k != null && !a.this.k.isCancelled()) {
                            a.this.k.cancel(true);
                        }
                        if (a.this.l != null && !a.this.l.isCancelled()) {
                            a.this.l.cancel(true);
                            return;
                        }
                        return;
                    case 13:
                        for (int i8 = 0; i8 < a.this.E.size(); i8++) {
                            com.leedarson.smartcamera.listener.i onTutkListener5 = (com.leedarson.smartcamera.listener.i) a.this.E.get(i8);
                            Object obj = msg.obj;
                            if (obj != null) {
                                onTutkListener5.c(((Long) obj).longValue());
                            }
                            onTutkListener5.e(4);
                        }
                        return;
                    case 14:
                    case 19:
                        if (a.this.M != null) {
                            a.this.M.onSuccess();
                            return;
                        }
                        return;
                    case 15:
                        if (a.this.M != null) {
                            a.this.M.a(msg.arg1);
                            return;
                        }
                        return;
                    case 16:
                        if (a.this.M != null && (sa = (short[]) msg.obj) != null) {
                            a.this.M.b(sa, sa.length, msg.arg1);
                            return;
                        }
                        return;
                    case 17:
                        int resp = msg.arg1;
                        byte[] respData = (byte[]) msg.obj;
                        com.leedarson.smartcamera.utils.e.c("MESSAGE_CMD_RESP_DATA", "RESP:" + Integer.toHexString(resp) + "==" + ByteUtil.getHexBinString(respData));
                        try {
                            a.J(a.this, resp, respData);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    case 18:
                        if (a.this.F != null) {
                            a.this.F.b(msg.arg1);
                            return;
                        }
                        return;
                    case 20:
                        for (int i9 = 0; i9 < a.this.E.size(); i9++) {
                            ((com.leedarson.smartcamera.listener.i) a.this.E.get(i9)).e(6);
                        }
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        }

        /* renamed from: com.leedarson.smartcamera.sdk.a$r$a  reason: collision with other inner class name */
        /* compiled from: LdsTutkChannel */
        public class C0180a implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0180a() {
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10324, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                a.o(a.this);
                return null;
            }
        }

        /* compiled from: LdsTutkChannel */
        public class b implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10325, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                a.t(a.this);
                return null;
            }
        }

        /* compiled from: LdsTutkChannel */
        public class c implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;

            c() {
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10326, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                a aVar = a.this;
                a.F(aVar, aVar.A);
                return null;
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c1(int r40, byte[] r41) {
        /*
            r39 = this;
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Integer r0 = new java.lang.Integer
            r9 = r40
            r0.<init>(r9)
            r10 = 0
            r2[r10] = r0
            r11 = 1
            r2[r11] = r41
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r7[r10] = r0
            java.lang.Class<byte[]> r0 = byte[].class
            r7[r11] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 10269(0x281d, float:1.439E-41)
            r3 = r39
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002c
            return
        L_0x002c:
            r2 = r39
            r3 = r41
            r4 = r40
            java.lang.String r0 = "code"
            r7 = 11
            r8 = 9
            r9 = 6
            r12 = 5
            r13 = 10
            r14 = 7
            r15 = 12
            r6 = 4
            r5 = 3
            switch(r4) {
                case 497: goto L_0x04e2;
                case 512: goto L_0x04e2;
                case 793: goto L_0x02cc;
                case 795: goto L_0x0234;
                case 801: goto L_0x0225;
                case 803: goto L_0x0214;
                case 850: goto L_0x01d6;
                case 1204: goto L_0x016e;
                case 1206: goto L_0x00fa;
                case 1208: goto L_0x00e0;
                case 1210: goto L_0x0048;
                default: goto L_0x0044;
            }
        L_0x0044:
            r33 = r4
            goto L_0x0502
        L_0x0048:
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x00d8 }
            r5.<init>(r3)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r5 = r5.trim()     // Catch:{ Exception -> 0x00d8 }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x00d8 }
            r6.<init>((java.lang.String) r5)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r7 = "CUSTOM_RESP"
            java.lang.String r8 = r6.toString()     // Catch:{ Exception -> 0x00d8 }
            com.leedarson.smartcamera.utils.e.c(r7, r8)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r7 = "handlerName"
            java.lang.String r7 = r6.getString(r7)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r8 = "FirmwareCallApp"
            boolean r7 = r7.equals(r8)     // Catch:{ Exception -> 0x00d8 }
            if (r7 == 0) goto L_0x00d4
            java.lang.String r7 = "service"
            java.lang.String r7 = r6.getString(r7)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r8 = "thumbnailPhoto"
            boolean r7 = r7.equals(r8)     // Catch:{ Exception -> 0x00d8 }
            if (r7 != 0) goto L_0x008c
            java.lang.String r7 = "service"
            java.lang.String r7 = r6.getString(r7)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r8 = "GET"
            boolean r7 = r7.equals(r8)     // Catch:{ Exception -> 0x00d8 }
            if (r7 == 0) goto L_0x00d4
        L_0x008c:
            java.lang.String r7 = "command"
            java.lang.String r7 = r6.getString(r7)     // Catch:{ Exception -> 0x00d8 }
            r8 = -1
            int r9 = r7.hashCode()     // Catch:{ Exception -> 0x00d8 }
            switch(r9) {
                case 530405532: goto L_0x00af;
                case 951351530: goto L_0x00a5;
                case 1849900413: goto L_0x009b;
                default: goto L_0x009a;
            }     // Catch:{ Exception -> 0x00d8 }
        L_0x009a:
            goto L_0x00b9
        L_0x009b:
            java.lang.String r9 = "talkToDevice"
            boolean r7 = r7.equals(r9)     // Catch:{ Exception -> 0x00d8 }
            if (r7 == 0) goto L_0x009a
            goto L_0x00ba
        L_0x00a5:
            java.lang.String r1 = "connect"
            boolean r1 = r7.equals(r1)     // Catch:{ Exception -> 0x00d8 }
            if (r1 == 0) goto L_0x009a
            r1 = r10
            goto L_0x00ba
        L_0x00af:
            java.lang.String r1 = "disconnect"
            boolean r1 = r7.equals(r1)     // Catch:{ Exception -> 0x00d8 }
            if (r1 == 0) goto L_0x009a
            r1 = r11
            goto L_0x00ba
        L_0x00b9:
            r1 = r8
        L_0x00ba:
            switch(r1) {
                case 0: goto L_0x00cb;
                case 1: goto L_0x00ca;
                case 2: goto L_0x00be;
                default: goto L_0x00bd;
            }     // Catch:{ Exception -> 0x00d8 }
        L_0x00bd:
            goto L_0x00d4
        L_0x00be:
            int r0 = r6.getInt(r0)     // Catch:{ Exception -> 0x00d8 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 != r1) goto L_0x00d4
            r2.U1()     // Catch:{ Exception -> 0x00d8 }
            goto L_0x00d4
        L_0x00ca:
            goto L_0x00d4
        L_0x00cb:
            java.lang.String r0 = "channel"
            int r0 = r6.getInt(r0)     // Catch:{ Exception -> 0x00d8 }
            r2.L1(r0)     // Catch:{ Exception -> 0x00d8 }
        L_0x00d4:
            r33 = r4
            goto L_0x0502
        L_0x00d8:
            r0 = move-exception
            r0.printStackTrace()
            r33 = r4
            goto L_0x0502
        L_0x00e0:
            com.leedarson.smartcamera.listener.a r0 = r2.I
            if (r0 == 0) goto L_0x00f6
            byte r1 = r3[r10]
            if (r1 != 0) goto L_0x00ef
            r0.onSuccess()
            r33 = r4
            goto L_0x0502
        L_0x00ef:
            r0.a(r10)
            r33 = r4
            goto L_0x0502
        L_0x00f6:
            r33 = r4
            goto L_0x0502
        L_0x00fa:
            byte[] r0 = new byte[r6]
            byte r16 = r3[r6]
            r0[r10] = r16
            byte r12 = r3[r12]
            r0[r11] = r12
            byte r9 = r3[r9]
            r0[r1] = r9
            byte r9 = r3[r14]
            r0[r5] = r9
            int r9 = com.tutk.IOTC.ByteUtil.byte2int(r0)
            byte r8 = r3[r8]
            byte r12 = r3[r13]
            byte r7 = r3[r7]
            if (r7 != r11) goto L_0x0140
            int r7 = r3.length
            int r14 = r12 + 16
            if (r7 != r14) goto L_0x0140
            byte[] r7 = new byte[r6]
            int r14 = r3.length
            int r14 = r14 - r6
            byte r6 = r3[r14]
            r7[r10] = r6
            int r6 = r3.length
            int r6 = r6 - r5
            byte r6 = r3[r6]
            r7[r11] = r6
            int r6 = r3.length
            int r6 = r6 - r1
            byte r6 = r3[r6]
            r7[r1] = r6
            int r1 = r3.length
            int r1 = r1 - r11
            byte r1 = r3[r1]
            r7[r5] = r1
            int r1 = com.tutk.IOTC.ByteUtil.byte2int(r7)
            int r5 = r2.w0
            if (r5 == r1) goto L_0x0140
            return
        L_0x0140:
            int r1 = r3.length
            if (r1 <= r15) goto L_0x0150
            byte[] r1 = new byte[r12]
            java.lang.System.arraycopy(r3, r15, r1, r10, r12)
            byte[] r5 = r2.V
            byte[] r5 = com.tutk.IOTC.ByteUtil.byteMerger(r5, r1)
            r2.V = r5
        L_0x0150:
            byte[] r1 = new byte[r13]
            int r5 = r3.length
            int r5 = r5 - r13
            java.lang.System.arraycopy(r3, r5, r1, r10, r13)
            if (r8 != r11) goto L_0x016a
            com.leedarson.smartcamera.listener.e r5 = r2.G
            if (r5 == 0) goto L_0x0166
            byte[] r6 = r2.V
            r5.onSuccess(r6)
            r33 = r4
            goto L_0x0502
        L_0x0166:
            r33 = r4
            goto L_0x0502
        L_0x016a:
            r33 = r4
            goto L_0x0502
        L_0x016e:
            com.leedarson.smartcamera.listener.f r0 = r2.L
            if (r0 == 0) goto L_0x01d2
            int r0 = r3.length
            r7 = 15
            if (r0 < r7) goto L_0x01d2
            org.json.JSONArray r0 = new org.json.JSONArray
            r0.<init>()
            byte r7 = r3[r10]
            if (r7 != r11) goto L_0x0184
            r0.put((boolean) r11)
            goto L_0x0187
        L_0x0184:
            r0.put((boolean) r10)
        L_0x0187:
            byte[] r7 = new byte[r6]
            byte r15 = r3[r11]
            r7[r10] = r15
            byte r15 = r3[r1]
            r7[r11] = r15
            byte r15 = r3[r5]
            r7[r1] = r15
            byte r15 = r3[r6]
            r7[r5] = r15
            int r15 = com.tutk.IOTC.ByteUtil.byte2int(r7)
            r0.put((int) r15)
            byte[] r6 = new byte[r6]
            byte r12 = r3[r12]
            r7[r10] = r12
            byte r9 = r3[r9]
            r7[r11] = r9
            byte r9 = r3[r14]
            r7[r1] = r9
            r1 = 8
            byte r1 = r3[r1]
            r7[r5] = r1
            int r1 = com.tutk.IOTC.ByteUtil.byte2int(r6)
            r0.put((int) r1)
            byte r1 = r3[r8]
            r0.put((int) r1)
            byte r5 = r3[r13]
            r0.put((int) r5)
            com.leedarson.smartcamera.listener.f r8 = r2.L
            java.lang.String r9 = r0.toString()
            r8.onSuccess(r9)
            r33 = r4
            goto L_0x0502
        L_0x01d2:
            r33 = r4
            goto L_0x0502
        L_0x01d6:
            java.util.concurrent.Future r1 = r2.c0     // Catch:{ Exception -> 0x020f }
            if (r1 == 0) goto L_0x01e5
            boolean r1 = r1.isCancelled()     // Catch:{ Exception -> 0x020f }
            if (r1 != 0) goto L_0x01e5
            java.util.concurrent.Future r1 = r2.c0     // Catch:{ Exception -> 0x020f }
            r1.cancel(r11)     // Catch:{ Exception -> 0x020f }
        L_0x01e5:
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x020f }
            r1.<init>(r3)     // Catch:{ Exception -> 0x020f }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x020f }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x020f }
            r5.<init>((java.lang.String) r1)     // Catch:{ Exception -> 0x020f }
            java.lang.String r6 = "IOTYPE_USER_IPCAM_SPEAKERSTART_RESP"
            java.lang.String r7 = r1.toString()     // Catch:{ Exception -> 0x020f }
            com.leedarson.smartcamera.utils.e.c(r6, r7)     // Catch:{ Exception -> 0x020f }
            int r0 = r5.getInt(r0)     // Catch:{ Exception -> 0x020f }
            r6 = 100
            if (r0 != r6) goto L_0x020b
            com.leedarson.smartcamera.listener.n r0 = r2.M     // Catch:{ Exception -> 0x020f }
            if (r0 == 0) goto L_0x020b
            r0.a(r6)     // Catch:{ Exception -> 0x020f }
        L_0x020b:
            r33 = r4
            goto L_0x0502
        L_0x020f:
            r0 = move-exception
            r33 = r4
            goto L_0x0502
        L_0x0214:
            com.leedarson.smartcamera.listener.c r0 = r2.J
            if (r0 == 0) goto L_0x0221
            byte r1 = r3[r6]
            r0.onSuccess(r1)
            r33 = r4
            goto L_0x0502
        L_0x0221:
            r33 = r4
            goto L_0x0502
        L_0x0225:
            com.leedarson.smartcamera.listener.k r0 = r2.K
            if (r0 == 0) goto L_0x0230
            r0.onSuccess()
            r33 = r4
            goto L_0x0502
        L_0x0230:
            r33 = r4
            goto L_0x0502
        L_0x0234:
            byte[] r0 = new byte[r6]
            byte r8 = r3[r10]
            r0[r10] = r8
            byte r8 = r3[r11]
            r0[r11] = r8
            byte r8 = r3[r1]
            r0[r1] = r8
            byte r8 = r3[r5]
            r0[r5] = r8
            int r8 = com.tutk.IOTC.ByteUtil.byte2int(r0)
            byte[] r13 = new byte[r6]
            byte r6 = r3[r6]
            r13[r10] = r6
            byte r6 = r3[r12]
            r13[r11] = r6
            byte r6 = r3[r9]
            r13[r1] = r6
            byte r6 = r3[r14]
            r13[r5] = r6
            int r6 = com.tutk.IOTC.ByteUtil.byte2int(r13)
            java.util.Locale r9 = java.util.Locale.US
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r12 = java.lang.Integer.toHexString(r8)
            r5[r10] = r12
            java.lang.Integer r10 = java.lang.Integer.valueOf(r6)
            r5[r11] = r10
            int r10 = r2.N
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r5[r1] = r10
            java.lang.String r1 = "sd resp: cmd=%s resultChannel=%d channel=%d"
            java.lang.String r1 = java.lang.String.format(r9, r1, r5)
            java.lang.String r5 = "TAG"
            com.leedarson.smartcamera.utils.e.c(r5, r1)
            r1 = 16
            if (r8 != r1) goto L_0x028f
            r2.n1()
            r33 = r4
            goto L_0x0502
        L_0x028f:
            if (r8 != 0) goto L_0x02a0
            com.leedarson.smartcamera.listener.h r1 = r2.F
            if (r1 == 0) goto L_0x029c
            r1.a()
            r33 = r4
            goto L_0x0502
        L_0x029c:
            r33 = r4
            goto L_0x0502
        L_0x02a0:
            r1 = 17
            if (r8 != r1) goto L_0x02b3
            com.leedarson.smartcamera.listener.h r1 = r2.F
            if (r1 == 0) goto L_0x02af
            r1.d()
            r33 = r4
            goto L_0x0502
        L_0x02af:
            r33 = r4
            goto L_0x0502
        L_0x02b3:
            if (r8 != r14) goto L_0x02c8
            int r1 = r2.N
            if (r1 != r6) goto L_0x02c8
            android.os.Message r1 = android.os.Message.obtain()
            com.leedarson.smartcamera.sdk.a$r r5 = r2.r
            r1.what = r7
            r5.sendMessage(r1)
            r33 = r4
            goto L_0x0502
        L_0x02c8:
            r33 = r4
            goto L_0x0502
        L_0x02cc:
            byte[] r15 = new byte[r6]
            byte r0 = r3[r6]
            r15[r10] = r0
            byte r0 = r3[r12]
            r15[r11] = r0
            byte r0 = r3[r9]
            r15[r1] = r0
            byte r0 = r3[r14]
            r15[r5] = r0
            int r17 = com.tutk.IOTC.ByteUtil.byte2int(r15)
            byte r14 = r3[r8]
            byte r13 = r3[r13]
            byte r0 = r3[r7]
            if (r0 != r11) goto L_0x0337
            int r0 = r3.length
            int r7 = r13 * 12
            r19 = 16
            int r7 = r7 + 16
            if (r0 != r7) goto L_0x0337
            byte[] r0 = new byte[r6]
            int r7 = r3.length
            int r7 = r7 - r6
            byte r6 = r3[r7]
            r0[r10] = r6
            int r6 = r3.length
            int r6 = r6 - r5
            byte r6 = r3[r6]
            r0[r11] = r6
            int r6 = r3.length
            int r6 = r6 - r1
            byte r6 = r3[r6]
            r0[r1] = r6
            int r6 = r3.length
            int r6 = r6 - r11
            byte r6 = r3[r6]
            r0[r5] = r6
            int r6 = com.tutk.IOTC.ByteUtil.byte2int(r0)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "tutk获取事件列表数据回执：  回执码："
            r7.append(r8)
            int r8 = r2.r0
            r7.append(r8)
            java.lang.String r8 = " getSDRecordList==seq 请求码："
            r7.append(r8)
            r7.append(r6)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "ldsTutkChannel"
            com.leedarson.smartcamera.utils.e.c(r8, r7)
            int r7 = r2.r0
            if (r7 == r6) goto L_0x0337
            return
        L_0x0337:
            java.lang.String[] r6 = new java.lang.String[r13]
            r0 = 0
            r7 = r0
        L_0x033b:
            java.lang.String r0 = "LdsTutkChannel"
            r19 = 0
            if (r7 >= r13) goto L_0x045d
            r8 = 12
            byte[] r9 = new byte[r8]
            int r21 = r7 * 12
            int r12 = r21 + 12
            java.lang.System.arraycopy(r3, r12, r9, r10, r8)
            byte[] r12 = new byte[r1]
            byte r21 = r9[r10]
            r12[r10] = r21
            byte r21 = r9[r11]
            r12[r11] = r21
            short r8 = com.tutk.IOTC.ByteUtil.byteToShort(r12)
            byte r11 = r9[r1]
            byte r1 = r9[r5]
            r22 = 5
            byte r25 = r9[r22]
            r16 = 6
            byte r26 = r9[r16]
            r18 = 7
            byte r27 = r9[r18]
            r28 = 8
            byte r29 = r9[r28]
            r30 = 9
            byte r31 = r9[r30]
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r8)
            java.lang.String r5 = "-"
            r10.append(r5)
            r10.append(r11)
            r10.append(r5)
            r10.append(r1)
            java.lang.String r5 = r10.toString()
            java.util.Locale r10 = java.util.Locale.US
            r41 = r1
            r33 = r4
            r1 = 3
            java.lang.Object[] r4 = new java.lang.Object[r1]
            java.lang.Integer r1 = java.lang.Integer.valueOf(r25)
            r32 = 0
            r4[r32] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r26)
            r23 = 1
            r4[r23] = r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r27)
            r24 = 2
            r4[r24] = r1
            java.lang.String r1 = "%02d:%02d:%02d"
            java.lang.String r1 = java.lang.String.format(r10, r1, r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r5)
            java.lang.String r10 = " "
            r4.append(r10)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            r6[r7] = r4
            r9 = 0
            r5 = 0
            r1 = 0
            java.text.SimpleDateFormat r10 = new java.text.SimpleDateFormat
            r34 = r1
            java.lang.String r1 = "yyyy-MM-dd HH:mm:ss"
            r10.<init>(r1)
            r1 = r10
            java.lang.String r10 = "GMT+0"
            java.util.TimeZone r10 = java.util.TimeZone.getTimeZone(r10)
            r1.setTimeZone(r10)
            r10 = 0
            java.util.Date r35 = r1.parse(r4)     // Catch:{ ParseException -> 0x0442 }
            r10 = r35
            r35 = r4
            r36 = r5
            long r4 = r2.s0     // Catch:{ ParseException -> 0x043c }
            int r4 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r4 <= 0) goto L_0x0428
            long r4 = r2.t0     // Catch:{ ParseException -> 0x043c }
            int r4 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r4 <= 0) goto L_0x0428
            long r4 = r10.getTime()     // Catch:{ ParseException -> 0x043c }
            r37 = r8
            r38 = r9
            long r8 = r2.s0     // Catch:{ ParseException -> 0x043a }
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 < 0) goto L_0x040f
            long r4 = r10.getTime()     // Catch:{ ParseException -> 0x043a }
            long r8 = r2.t0     // Catch:{ ParseException -> 0x043a }
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x042c
        L_0x040f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ParseException -> 0x043a }
            r4.<init>()     // Catch:{ ParseException -> 0x043a }
            java.lang.String r5 = "getSDRecordList3 3: "
            r4.append(r5)     // Catch:{ ParseException -> 0x043a }
            long r8 = r10.getTime()     // Catch:{ ParseException -> 0x043a }
            r4.append(r8)     // Catch:{ ParseException -> 0x043a }
            java.lang.String r4 = r4.toString()     // Catch:{ ParseException -> 0x043a }
            android.util.Log.d(r0, r4)     // Catch:{ ParseException -> 0x043a }
            return
        L_0x0428:
            r37 = r8
            r38 = r9
        L_0x042c:
            java.util.List<java.lang.Long> r0 = r2.W     // Catch:{ ParseException -> 0x043a }
            long r4 = r10.getTime()     // Catch:{ ParseException -> 0x043a }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ ParseException -> 0x043a }
            r0.add(r4)     // Catch:{ ParseException -> 0x043a }
            goto L_0x044e
        L_0x043a:
            r0 = move-exception
            goto L_0x044b
        L_0x043c:
            r0 = move-exception
            r37 = r8
            r38 = r9
            goto L_0x044b
        L_0x0442:
            r0 = move-exception
            r35 = r4
            r36 = r5
            r37 = r8
            r38 = r9
        L_0x044b:
            r0.printStackTrace()
        L_0x044e:
            int r7 = r7 + 1
            r9 = r16
            r12 = r22
            r1 = r24
            r4 = r33
            r5 = 3
            r10 = 0
            r11 = 1
            goto L_0x033b
        L_0x045d:
            r33 = r4
            r1 = 1
            if (r14 != r1) goto L_0x0502
            long r4 = r2.s0
            int r1 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r1 <= 0) goto L_0x04d8
            long r7 = r2.t0
            int r1 = (r7 > r19 ? 1 : (r7 == r19 ? 0 : -1))
            if (r1 <= 0) goto L_0x04d8
            int r1 = r2.v0
            int r1 = r1 + 24
            r2.v0 = r1
            int r1 = r1 * 60
            int r1 = r1 * 60
            int r1 = r1 * 1000
            long r9 = (long) r1
            long r4 = r4 + r9
            int r1 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x04b8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "getSDRecordList3 4: "
            r1.append(r4)
            int r4 = r2.v0
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            long r0 = r2.s0
            int r4 = r2.v0
            int r5 = r4 + 24
            int r5 = r5 * 60
            int r5 = r5 * 60
            int r5 = r5 * 1000
            long r7 = (long) r5
            long r7 = r7 + r0
            int r4 = r4 * 60
            int r4 = r4 * 60
            int r4 = r4 * 1000
            long r4 = (long) r4
            long r0 = r0 + r4
            long r4 = r2.t0
            int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r9 <= 0) goto L_0x04b3
            goto L_0x04b4
        L_0x04b3:
            r4 = r7
        L_0x04b4:
            r2.L0(r0, r4)
            goto L_0x0502
        L_0x04b8:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "getSDRecordList3 5: "
            r1.append(r4)
            int r4 = r2.v0
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            com.leedarson.smartcamera.listener.b r0 = r2.H
            if (r0 == 0) goto L_0x0502
            java.util.List<java.lang.Long> r1 = r2.W
            r0.onSuccess(r1)
            goto L_0x0502
        L_0x04d8:
            com.leedarson.smartcamera.listener.b r0 = r2.H
            if (r0 == 0) goto L_0x0502
            java.util.List<java.lang.Long> r1 = r2.W
            r0.onSuccess(r1)
            goto L_0x0502
        L_0x04e2:
            r33 = r4
            int r0 = r3.length
            if (r0 <= 0) goto L_0x0502
            r1 = 0
            byte r0 = r3[r1]
            r1 = 101(0x65, float:1.42E-43)
            if (r0 != r1) goto L_0x0502
            android.os.Message r1 = android.os.Message.obtain()
            com.leedarson.smartcamera.sdk.a$r r4 = r2.r
            r5 = 3
            r1.what = r5
            r5 = -2
            r1.arg1 = r5
            r1.arg2 = r0
            r4.sendMessage(r1)
            r2.C0()
        L_0x0502:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.sdk.a.c1(int, byte[]):void");
    }

    /* compiled from: LdsTutkChannel */
    public class b implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10305, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            a aVar = a.this;
            int unused = aVar.y = IOTCAPIs.IOTC_Session_Get_Free_Channel(aVar.w);
            com.leedarson.smartcamera.utils.e.c("", "openTalkChannel free channel: " + a.this.y);
            if (a.this.y >= 0) {
                if (a.C(a.this, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SPEAKERSTART, AVIOCTRLDEFs.SMsgAVIoctrlAVStream.parseContent(a.this.y)) == 0) {
                    St_AVServStartInConfig av_serv_in_config = new St_AVServStartInConfig();
                    St_AVServStartOutConfig av_serv_out_config = new St_AVServStartOutConfig();
                    av_serv_in_config.iotc_session_id = a.this.w;
                    av_serv_in_config.iotc_channel_id = a.this.y;
                    av_serv_in_config.timeout_sec = 20;
                    av_serv_in_config.server_type = 0;
                    av_serv_in_config.resend = 0;
                    av_serv_in_config.security_mode = a.this.Y;
                    av_serv_in_config.password_auth = new s();
                    com.leedarson.smartcamera.utils.e.c("", "avServStartEx start");
                    int unused2 = a.this.z = AVAPIs.avServStartEx(av_serv_in_config, av_serv_out_config);
                    int bResend = av_serv_out_config.resend;
                    com.leedarson.smartcamera.utils.e.c("TAG", "avServStartEx(), avIndex=[" + 0 + "], sid=[" + a.this.z + "], bResend=[" + bResend + "]");
                    if (a.this.z == -20039) {
                        a aVar2 = a.this;
                        int unused3 = aVar2.z = AVAPIs.avServStart3(aVar2.w, "", "", 30, 0, a.this.y, new int[4]);
                    }
                }
                com.leedarson.smartcamera.utils.e.c("", "avServStart3:" + a.this.z);
                if (a.this.z >= 0) {
                    Message msg = Message.obtain();
                    r unused4 = a.this.r;
                    msg.what = 14;
                    a.this.r.sendMessage(msg);
                    a.P(a.this);
                    return null;
                } else if (a.this.M == null) {
                    return null;
                } else {
                    a.this.M.a(a.this.y);
                    if (a.this.Z != 2 || a.this.y < 0) {
                        return null;
                    }
                    IOTCAPIs.IOTC_Session_Channel_OFF(a.this.w, a.this.y);
                    return null;
                }
            } else if (a.this.M == null) {
                return null;
            } else {
                a.this.M.a(a.this.y);
                return null;
            }
        }
    }

    private void U1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10270, new Class[0], Void.TYPE).isSupported) {
            this.c0 = this.d.submit(new b());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class c implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public Object call() {
            int avIndex;
            int i;
            String str;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10306, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            int[] srvType = new int[1];
            int[] nSend = new int[1];
            try {
                St_AVClientStartInConfig av_client_in_config = new St_AVClientStartInConfig();
                St_AVClientStartOutConfig av_client_out_config = new St_AVClientStartOutConfig();
                av_client_in_config.iotc_session_id = a.this.v;
                av_client_in_config.iotc_channel_id = a.this.N;
                av_client_in_config.timeout_sec = 20;
                av_client_in_config.account_or_identity = a.this.t;
                av_client_in_config.password_or_token = a.this.u;
                av_client_in_config.resend = 1;
                av_client_in_config.security_mode = a.this.Y;
                av_client_in_config.auth_type = 0;
                int avIndex2 = AVAPIs.avClientStartEx(av_client_in_config, av_client_out_config);
                try {
                    com.leedarson.smartcamera.utils.e.c("", a.this.s + " sd avClientStartEx 1: " + a.this.N);
                    if (avIndex2 == -20039) {
                        i = -20039;
                        str = " avClientStart2: ";
                        avIndex = AVAPIs.avClientStart2(a.this.w, a.this.t, a.this.u, 30, srvType, 0, nSend);
                        com.leedarson.smartcamera.utils.e.c("", a.this.s + str + a.this.N);
                    } else {
                        i = -20039;
                        str = " avClientStart2: ";
                        avIndex = avIndex2;
                    }
                    com.leedarson.smartcamera.utils.e.c("", a.this.N + " sd avClientStart 2:" + avIndex);
                    if (avIndex == -20027) {
                        if (a.this.A >= 0) {
                            AVAPIs.avClientStop(a.this.A);
                        }
                        avIndex2 = AVAPIs.avClientStartEx(av_client_in_config, av_client_out_config);
                        com.leedarson.smartcamera.utils.e.c("", a.this.N + " sd avClientStart 3:" + avIndex2);
                        if (avIndex2 == i) {
                            avIndex = AVAPIs.avClientStart2(a.this.w, a.this.t, a.this.u, 30, srvType, 0, nSend);
                            com.leedarson.smartcamera.utils.e.c("", a.this.s + str + a.this.N);
                        } else {
                            avIndex = avIndex2;
                        }
                    }
                    com.leedarson.smartcamera.utils.e.c("", a.this.N + " sd avClientStart 4:" + avIndex);
                    if (avIndex < 0) {
                        return null;
                    }
                    int unused = a.this.A = avIndex;
                    int ret = a.q0(a.this, avIndex, false);
                    com.leedarson.smartcamera.utils.e.c("", "sd startStream:" + ret);
                    Message msg = Message.obtain();
                    r unused2 = a.this.r;
                    msg.what = 8;
                    if (ret >= 0) {
                        msg.arg1 = 1;
                    } else {
                        msg.arg1 = ret;
                    }
                    a.this.r.sendMessage(msg);
                    return null;
                } catch (Exception e) {
                    e = e;
                    int i2 = avIndex2;
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return null;
            }
        }
    }

    private void n1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10271, new Class[0], Void.TYPE).isSupported) {
            this.d.submit(new c());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class d implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        d(int i) {
            this.c = i;
        }

        public Object call() {
            int i;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10307, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            int[] srvType = new int[1];
            int[] nSend = new int[1];
            try {
                St_AVClientStartInConfig av_client_in_config = new St_AVClientStartInConfig();
                St_AVClientStartOutConfig av_client_out_config = new St_AVClientStartOutConfig();
                av_client_in_config.iotc_session_id = a.this.v;
                av_client_in_config.iotc_channel_id = this.c;
                av_client_in_config.timeout_sec = 20;
                av_client_in_config.account_or_identity = a.this.t;
                av_client_in_config.password_or_token = a.this.u;
                av_client_in_config.resend = 1;
                av_client_in_config.security_mode = a.this.Y;
                av_client_in_config.auth_type = 0;
                a.this.A0 = AVAPIs.avClientStartEx(av_client_in_config, av_client_out_config);
                com.leedarson.smartcamera.utils.e.b("", a.this.s + "startPicStream avClientStartEx: " + this.c);
                a aVar = a.this;
                if (aVar.A0 == -20039) {
                    i = -20039;
                    aVar.A0 = AVAPIs.avClientStart2(aVar.w, a.this.t, a.this.u, 30, srvType, this.c, nSend);
                    com.leedarson.smartcamera.utils.e.c("", a.this.s + " avClientStart2: " + this.c);
                } else {
                    i = -20039;
                }
                int i2 = a.this.A0;
                if (i2 == -20027) {
                    if (i2 >= 0) {
                        AVAPIs.avClientStop(i2);
                    }
                    a.this.A0 = AVAPIs.avClientStartEx(av_client_in_config, av_client_out_config);
                    com.leedarson.smartcamera.utils.e.c("", a.this.s + " avClientStartEx: " + this.c);
                    a aVar2 = a.this;
                    if (aVar2.A0 == i) {
                        aVar2.A0 = AVAPIs.avClientStart2(aVar2.w, a.this.t, a.this.u, 30, srvType, this.c, nSend);
                        com.leedarson.smartcamera.utils.e.c("", a.this.s + " avClientStart2: " + this.c);
                    }
                }
                com.leedarson.smartcamera.utils.e.c("", this.c + " startPicStream:" + a.this.A0);
                a aVar3 = a.this;
                if (aVar3.A0 < 0) {
                    return null;
                }
                boolean unused = aVar3.P = true;
                if (a.this.Q != null) {
                    a.this.Q.c(1);
                    a.W(a.this);
                    Timer unused2 = a.this.R = new Timer();
                    a.this.R.schedule(new C0179a(), 15000, 15000);
                }
                if (!TextUtils.isEmpty(a.this.S)) {
                    if (a.this.T != null) {
                        a aVar4 = a.this;
                        a.e0(aVar4, aVar4.O, a.this.S, a.this.T);
                    }
                }
                if (a.this.n != null && !a.this.n.isCancelled()) {
                    a.this.n.cancel(true);
                }
                Callable callable3 = new b();
                a aVar5 = a.this;
                Future unused3 = aVar5.n = aVar5.j.submit(callable3);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* renamed from: com.leedarson.smartcamera.sdk.a$d$a  reason: collision with other inner class name */
        /* compiled from: LdsTutkChannel */
        public class C0179a extends TimerTask {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0179a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10308, new Class[0], Void.TYPE).isSupported) {
                    a aVar = a.this;
                    a.a0(aVar, aVar.O);
                }
            }
        }

        /* compiled from: LdsTutkChannel */
        public class b implements Callable {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            public Object call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10309, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                boolean unused = a.this.D = true;
                a aVar = a.this;
                a.i0(aVar, aVar.A0);
                return null;
            }
        }
    }

    private void L1(int channel) {
        Object[] objArr = {new Integer(channel)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10272, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q = this.d.submit(new d(channel));
        }
    }

    private void z1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10273, new Class[0], Void.TYPE).isSupported) {
            Future future = this.B0;
            if (future != null && !future.isCancelled()) {
                this.B0.cancel(true);
            }
            this.B0 = this.e.submit(new e());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class e implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10310, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            if (a.this.n0 == null) {
                a aVar = a.this;
                AudioRecord unused = aVar.n0 = a.k0(aVar, aVar.h0, aVar.i0, aVar.j0);
            }
            if (a.this.n0 == null) {
                return null;
            }
            boolean hasInit = false;
            while (!hasInit && a.this.l0) {
                try {
                    Thread.sleep(10);
                    if (a.this.n0.getState() == 1) {
                        hasInit = true;
                        a.this.n0.startRecording();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = a.this.k0;
            byte[] audioData = new byte[i];
            short[] inputData = new short[(i / 2)];
            while (true) {
                a aVar2 = a.this;
                if (aVar2.l0) {
                    try {
                        if (aVar2.n0.getRecordingState() == 3) {
                            int size = a.this.n0.read(audioData, 0, audioData.length);
                            if (a.this.r != null) {
                                ByteBuffer.wrap(audioData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(inputData);
                                Message msg = Message.obtain();
                                r unused2 = a.this.r;
                                msg.what = 16;
                                msg.obj = inputData;
                                msg.arg1 = (int) (a.t0(audioData) * 10.0d);
                                a.this.r.sendMessage(msg);
                            }
                            if (size == audioData.length) {
                                G711Coder.g711aEncode(audioData, audioData.length, a.this.m0);
                                a aVar3 = a.this;
                                a.m0(aVar3, aVar3.m0.dataLen, a.this.m0.dataArr);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    a.n0(aVar2);
                    return null;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void P1() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10274(0x2822, float:1.4397E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.R
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.R = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.sdk.a.P1():void");
    }

    private void W0(int picchannelId) {
        Object[] objArr = {new Integer(picchannelId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10275, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            JSONObject heartObj = new JSONObject();
            try {
                heartObj.put("seq", 333333);
                heartObj.put("handlerName", (Object) "AppCallFirmware");
                heartObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) H5ActionName.TUTK_THUMBNAI);
                heartObj.put("command", (Object) "heartbeat");
                heartObj.put("code", 200);
                heartObj.put("channel", picchannelId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ, heartObj.toString().getBytes());
        }
    }

    private void J0(int picchannelId, String event, List<Long> timers) {
        if (!PatchProxy.proxy(new Object[]{new Integer(picchannelId), event, timers}, this, changeQuickRedirect, false, 10276, new Class[]{Integer.TYPE, String.class, List.class}, Void.TYPE).isSupported) {
            int repeatCount = timers.size() % this.C0 == 0 ? timers.size() / this.C0 : (timers.size() / this.C0) + 1;
            Log.e("TAG", "getPics repeatCount: " + repeatCount);
            for (int i2 = 0; i2 < repeatCount; i2++) {
                int index = this.C0 * i2;
                List<Long> list = new ArrayList<>();
                int j2 = 0;
                while (j2 < this.C0 && index < timers.size()) {
                    list.add(timers.get(index));
                    j2++;
                    index++;
                }
                JSONObject getObj = new JSONObject();
                try {
                    getObj.put("seq", 22222);
                    getObj.put("handlerName", (Object) "AppCallFirmware");
                    getObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) H5ActionName.TUTK_THUMBNAI);
                    getObj.put("command", (Object) "get");
                    getObj.put("code", 200);
                    getObj.put("channel", picchannelId);
                    getObj.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) event);
                    JSONArray timesArr = new JSONArray();
                    for (Long time : list) {
                        timesArr.put((Object) time + "");
                    }
                    getObj.put("times", (Object) timesArr);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                com.leedarson.smartcamera.utils.e.c("", "getPics: " + getObj.toString());
                x1(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ, getObj.toString().getBytes());
            }
        }
    }

    /* compiled from: LdsTutkChannel */
    public class f implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10311, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            while (a.this.o) {
                a aVar = a.this;
                int o0 = aVar.w;
                int[] p0 = a.this.D0;
                byte[] bArr = a.this.E0;
                aVar.F0 = AVAPIs.avRecvIOCtrl(o0, p0, bArr, bArr.length, 1000);
                a aVar2 = a.this;
                int i = aVar2.F0;
                if (i > 0) {
                    com.leedarson.smartcamera.utils.e.c("avRecvIOCtrl", "len:" + a.this.F0);
                    a aVar3 = a.this;
                    int i2 = aVar3.F0;
                    byte[] resultBytes = new byte[i2];
                    System.arraycopy(aVar3.E0, 0, resultBytes, 0, i2);
                    Message msg = Message.obtain();
                    r unused = a.this.r;
                    msg.what = 17;
                    msg.arg1 = a.this.D0[0];
                    msg.obj = resultBytes;
                    a.this.r.sendMessage(msg);
                } else if (i == -20016 || i == -20015) {
                    aVar2.C0();
                }
                SystemClock.sleep(20);
            }
            return null;
        }
    }

    private void M1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10277, new Class[0], Void.TYPE).isSupported) {
            this.p = this.f.submit(new f());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class s implements AVAPIs.avPasswordAuthFn {
        public static ChangeQuickRedirect changeQuickRedirect;

        public s() {
        }

        public int password_auth(String account, String[] password) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{account, password}, this, changeQuickRedirect, false, 10327, new Class[]{String.class, String[].class}, Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            if (a.this.Y != 1) {
                password[0] = "";
            } else if (account == null || !account.equals(a.this.t)) {
                return AVAPIs.AV_ER_INVALID_ARG;
            } else {
                password[0] = a.this.H0();
            }
            return 0;
        }
    }

    public void x0() {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10278, new Class[0], Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.s);
            sb.append(" connect2 status: ");
            if (this.d0 == 0) {
                z2 = true;
            }
            sb.append(z2);
            sb.append(this.d0);
            com.leedarson.smartcamera.utils.e.c("TAG", sb.toString());
            if (this.d0 != 0) {
                this.d.execute(new g());
            }
        }
    }

    /* compiled from: LdsTutkChannel */
    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10312, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("TAG", "connect2: start ");
                Message msg1 = Message.obtain();
                r unused = a.this.r;
                msg1.what = 3;
                msg1.arg1 = 0;
                a.this.r.sendMessage(msg1);
                if (a.this.Z0()) {
                    Message msg = Message.obtain();
                    r unused2 = a.this.r;
                    msg.what = 3;
                    msg.arg1 = 1;
                    a.this.r.sendMessage(msg);
                } else {
                    com.leedarson.smartcamera.utils.e.c("TAG", "connect2: connectTutk ");
                    a.a(a.this);
                }
                com.leedarson.smartcamera.utils.e.c("TAG", "connect2: end ");
            }
        }
    }

    public void K1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10279, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c(this.s, " start...");
            this.a = false;
            this.c = false;
            Future future = this.k;
            if (future != null && !future.isCancelled()) {
                this.k.cancel(true);
            }
            Future future2 = this.l;
            if (future2 != null && !future2.isCancelled()) {
                this.l.cancel(true);
            }
            Future future3 = this.a0;
            if (future3 != null && !future3.isCancelled()) {
                com.leedarson.smartcamera.utils.e.c(this.s, " start... 1111");
                this.a0.cancel(true);
            }
            Future future4 = this.b0;
            if (future4 != null && !future4.isCancelled()) {
                this.b0.cancel(true);
            }
            this.a0 = this.d.submit(new h());
        }
    }

    /* compiled from: LdsTutkChannel */
    public class h implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public Object call() {
            int ret;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10313, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive call");
            boolean unused = a.this.X = true;
            com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive 2:");
            if (a.this.Z0()) {
                com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive 3:");
                int reStartNum = 0;
                do {
                    a aVar = a.this;
                    ret = a.q0(aVar, aVar.w, true);
                    reStartNum++;
                    if (ret >= 0 || reStartNum >= 3) {
                        String Q = a.this.s;
                        com.leedarson.smartcamera.utils.e.c(Q, "startLive reStartNum:" + reStartNum + " result:" + ret);
                        Message msg = Message.obtain();
                        r unused2 = a.this.r;
                        msg.what = 2;
                    }
                    a aVar2 = a.this;
                    ret = a.q0(aVar2, aVar2.w, true);
                    reStartNum++;
                    break;
                } while (reStartNum >= 3);
                String Q2 = a.this.s;
                com.leedarson.smartcamera.utils.e.c(Q2, "startLive reStartNum:" + reStartNum + " result:" + ret);
                Message msg2 = Message.obtain();
                r unused3 = a.this.r;
                msg2.what = 2;
                if (ret >= 0) {
                    msg2.arg1 = 1;
                } else {
                    a.this.C0();
                    msg2.arg1 = ret;
                }
                a.this.r.sendMessage(msg2);
            }
            boolean unused4 = a.this.X = false;
            com.leedarson.smartcamera.utils.e.c(a.this.s, "startLive call end");
            return null;
        }
    }

    /* compiled from: LdsTutkChannel */
    public class i implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ byte[] c;
        final /* synthetic */ int d;

        i(byte[] bArr, int i) {
            this.c = bArr;
            this.d = i;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10314, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            G711Coder.g711aEncode(this.c, this.d, a.this.m0);
            a aVar = a.this;
            a.m0(aVar, aVar.m0.dataLen, a.this.m0.dataArr);
            return null;
        }
    }

    public void v1(byte[] data, int length) {
        if (!PatchProxy.proxy(new Object[]{data, new Integer(length)}, this, changeQuickRedirect, false, 10280, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            this.e.submit(new i(data, length));
        }
    }

    public St_SInfoEx U0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10281, new Class[0], St_SInfoEx.class);
        if (proxy.isSupported) {
            return (St_SInfoEx) proxy.result;
        }
        St_SInfoEx st_sInfoEx = new St_SInfoEx();
        Log.e("TAG", "IOTC_Session_Check_Ex: ");
        int re = IOTCAPIs.IOTC_Session_Check_Ex(this.v, st_sInfoEx);
        Log.e("TAG", "IOTC_Session_Check_Ex: " + re + "==" + st_sInfoEx.toString());
        return st_sInfoEx;
    }

    public static double t0(byte[] buffer) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{buffer}, (Object) null, changeQuickRedirect, true, 10282, new Class[]{byte[].class}, Double.TYPE);
        if (proxy.isSupported) {
            return ((Double) proxy.result).doubleValue();
        }
        double sumVolume = 0.0d;
        for (int i2 = 0; i2 < buffer.length; i2 += 2) {
            int temp = ((buffer[i2 + 1] & 255) << 8) + (buffer[i2] & 255);
            if (temp >= 32768) {
                temp = 65535 - temp;
            }
            sumVolume += (double) Math.abs(temp);
        }
        return Math.log10(1.0d + ((sumVolume / ((double) buffer.length)) / 2.0d)) * 10.0d;
    }

    public void u0() {
        this.G = null;
        this.H = null;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
    }
}
