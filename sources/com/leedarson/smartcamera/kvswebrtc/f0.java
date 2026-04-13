package com.leedarson.smartcamera.kvswebrtc;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesisvideo.AWSKinesisVideoClient;
import com.amazonaws.services.kinesisvideo.model.ChannelRole;
import com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem;
import com.amazonaws.services.kinesisvideosignaling.AWSKinesisVideoSignalingClient;
import com.amazonaws.services.kinesisvideosignaling.model.IceServer;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.DeviceIdUtils;
import com.leedarson.smartcamera.bean.IceCandidateEventBean;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.beans.LdsRadarData;
import com.leedarson.smartcamera.kvswebrtc.beans.LdsTrackSwitchNotifyBean;
import com.leedarson.smartcamera.kvswebrtc.beans.LivePlaySeqRequestPairBean;
import com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean;
import com.leedarson.smartcamera.kvswebrtc.focus.b;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Message;
import com.leedarson.smartcamera.kvswebrtc.utils.e;
import com.leedarson.smartcamera.reporters.FirstFrameRenderAutoReconnectHistoryData;
import com.leedarson.smartcamera.reporters.WebRtcLogStepBean;
import com.leedarson.smartcamera.reporters.WebRtcReporterV3;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tencent.bugly.Bugly;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.ByteUtil;
import com.tutk.IOTC.Packet;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.cache.CacheHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.AudioTrack;
import org.webrtc.CandidatePairChangeEvent;
import org.webrtc.DataChannel;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglRenderer;
import org.webrtc.IceCandidate;
import org.webrtc.Loggable;
import org.webrtc.Logging;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.MediaStreamTrack;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RTCStatsReport;
import org.webrtc.RtpReceiver;
import org.webrtc.RtpTransceiver;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;
import org.webrtc.audio.JavaAudioDeviceModule;
import org.webrtc.s0;
import timber.log.a;

/* compiled from: KVSWebRTCChannel */
public class f0 {
    private static HashSet<String> a = new HashSet<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    private VideoTrack A;
    /* access modifiers changed from: private */
    public boolean A0;
    private boolean A1;
    /* access modifiers changed from: private */
    public AudioTrack B;
    private long B0;
    private long B1;
    private ExecutorService C;
    private int C0;
    public com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.k C1;
    /* access modifiers changed from: private */
    public d0 D;
    public io.reactivex.processors.b<WebRtcServiceStateChangeLogBean> D0;
    public com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.j D1;
    /* access modifiers changed from: private */
    public HashMap<String, PeerConnection> E;
    public io.reactivex.processors.b<WebRtcServiceStateChangeLogBean> E0;
    public com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.l E1;
    private HashMap<String, Queue<IceCandidate>> F;
    public io.reactivex.processors.b<WebRtcServiceStateChangeLogBean> F0;
    public com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.i F1;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.signaling.d G;
    public io.reactivex.processors.b<WebRtcServiceStateChangeLogBean> G0;
    /* access modifiers changed from: private */
    public g0 G1;
    /* access modifiers changed from: private */
    public MediaStream H;
    public io.reactivex.processors.b<PeerConnection.IceConnectionState> H0;
    private long H1;
    /* access modifiers changed from: private */
    public DataChannel I;
    public io.reactivex.processors.b<kotlin.n<Integer, String>> I0;
    private long I1;
    private com.leedarson.smartcamera.listener.e J;
    public io.reactivex.processors.b<kotlin.n<Boolean, Boolean>> J0;
    FirstFrameRenderAutoReconnectHistoryData J1;
    private com.leedarson.smartcamera.listener.b K;
    public io.reactivex.processors.b<Double> K0;
    io.reactivex.disposables.b K1;
    private com.leedarson.smartcamera.listener.a L;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.utils.f L0;
    private Runnable L1;
    private byte[] M;
    private boolean M0;
    private List<Long> N;
    /* access modifiers changed from: private */
    public int N0;
    private int O;
    private q O0;
    private boolean P;
    /* access modifiers changed from: private */
    public PeerConnection.PeerConnectionState P0;
    private String Q;
    private boolean Q0;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.listener.j R;
    private boolean R0;
    /* access modifiers changed from: private */
    public s S;
    private boolean S0;
    /* access modifiers changed from: private */
    public boolean T;
    private int T0;
    private com.leedarson.smartcamera.listener.k U;
    private boolean U0;
    private com.leedarson.smartcamera.listener.c V;
    private ConcurrentHashMap<String, LivePlaySeqRequestPairBean> V0;
    private com.leedarson.smartcamera.listener.d W;
    /* access modifiers changed from: private */
    public boolean W0;
    private com.leedarson.smartcamera.listener.l X;
    /* access modifiers changed from: private */
    public String X0;
    private com.leedarson.smartcamera.listener.m Y;
    /* access modifiers changed from: private */
    public String Y0;
    private com.leedarson.smartcamera.listener.g Z;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.utils.e Z0;
    private i0 a0;
    com.leedarson.smartcamera.kvswebrtc.datarepos.a a1;
    private String b;
    Message b0;
    private long b1;
    private String c;
    private Timer c0;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.signaling.c c1;
    private String d;
    private String d0;
    private io.reactivex.disposables.b d1;
    private String e;
    /* access modifiers changed from: private */
    public int e0;
    private io.reactivex.disposables.b e1;
    /* access modifiers changed from: private */
    public String f;
    private int f0;
    /* access modifiers changed from: private */
    public long f1;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public int g0;
    /* access modifiers changed from: private */
    public long g1;
    public String h;
    /* access modifiers changed from: private */
    public int h0;
    public int h1;
    private ChannelRole i;
    private boolean i0;
    private Handler i1;
    private String j;
    private com.leedarson.smartcamera.listener.f j0;
    private Runnable j1;
    private final List<ResourceEndpointListItem> k;
    /* access modifiers changed from: private */
    public boolean k0;
    com.leedarson.smartcamera.kvswebrtc.focus.a k1;
    /* access modifiers changed from: private */
    public final List<IceServer> l;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o l0;
    EglRenderer.FrameListener l1;
    private final List<PeerConnection.IceServer> m;
    /* access modifiers changed from: private */
    public String m0;
    /* access modifiers changed from: private */
    public h0 m1;
    @Nullable
    private com.leedarson.smartcamera.kvswebrtc.record.h n;
    private String n0;
    private Bitmap n1;
    @Nullable
    private JavaAudioDeviceModule o;
    /* access modifiers changed from: private */
    public int o0;
    SurfaceViewRenderer o1;
    @Nullable
    private PeerConnectionFactory p;
    /* access modifiers changed from: private */
    public Timer p0;
    com.leedarson.smartcamera.listener.n p1;
    private AudioTrack q;
    private final int q0;
    com.leedarson.smartcamera.listener.n q1;
    private DefaultVideoDecoderFactory r;
    private Context r0;
    int r1;
    @Nullable
    private com.leedarson.smartcamera.kvswebrtc.record.k s;
    private e0 s0;
    int s1;
    @Nullable
    private com.leedarson.smartcamera.kvswebrtc.record.l t;
    /* access modifiers changed from: private */
    public Timer t0;
    private long t1;
    /* access modifiers changed from: private */
    public String u;
    private final int u0;
    WebRtcLogStepBean u1;
    public String v;
    /* access modifiers changed from: private */
    public boolean v0;
    PeerConnection.IceConnectionState v1;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n w;
    /* access modifiers changed from: private */
    public String w0;
    com.leedarson.smartcamera.kvswebrtc.signaling.b w1;
    private AWSCredentials x;
    /* access modifiers changed from: private */
    public String x0;
    int x1;
    /* access modifiers changed from: private */
    public PeerConnection y;
    private ArrayList<String> y0;
    private int y1;
    /* access modifiers changed from: private */
    public VideoTrack z;
    private int z0;
    private int z1;

    /* compiled from: KVSWebRTCChannel */
    public enum q {
        IDLE,
        LIVING,
        SD;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    /* compiled from: KVSWebRTCChannel */
    public enum r {
        LIVE,
        SDCARD,
        PRE_LINK;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void F(f0 x02, String x12) {
        Class[] clsArr = {f0.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x02, x12}, (Object) null, changeQuickRedirect, true, 9841, clsArr, Void.TYPE).isSupported) {
            x02.b(x12);
        }
    }

    static /* synthetic */ Message G(f0 x02, IceCandidate x12) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02, x12}, (Object) null, changeQuickRedirect, true, 9842, new Class[]{f0.class, IceCandidate.class}, Message.class);
        return proxy.isSupported ? (Message) proxy.result : x02.I0(x12);
    }

    static /* synthetic */ boolean H(f0 x02) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 9843, new Class[]{f0.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x02.w1();
    }

    static /* synthetic */ void K(f0 x02, MediaStream x12) {
        Class[] clsArr = {f0.class, MediaStream.class};
        if (!PatchProxy.proxy(new Object[]{x02, x12}, (Object) null, changeQuickRedirect, true, 9844, clsArr, Void.TYPE).isSupported) {
            x02.w0(x12);
        }
    }

    static /* synthetic */ void R(f0 x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 9845, new Class[]{f0.class}, Void.TYPE).isSupported) {
            x02.r3();
        }
    }

    static /* synthetic */ Timer W(f0 x02, Timer x12) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02, x12}, (Object) null, changeQuickRedirect, true, 9846, new Class[]{f0.class, Timer.class}, Timer.class);
        return proxy.isSupported ? (Timer) proxy.result : x02.L0(x12);
    }

    static /* synthetic */ void b0(f0 x02, int x12, int x2, byte[] x3) {
        Object[] objArr = {x02, new Integer(x12), new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9847, new Class[]{f0.class, cls, cls, byte[].class}, Void.TYPE).isSupported) {
            x02.z2(x12, x2, x3);
        }
    }

    static /* synthetic */ void e(f0 x02, int x12, String x2) {
        Object[] objArr = {x02, new Integer(x12), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9836, new Class[]{f0.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x02.A3(x12, x2);
        }
    }

    static /* synthetic */ void i0(f0 x02, String x12, IceCandidate x2) {
        Class[] clsArr = {f0.class, String.class, IceCandidate.class};
        if (!PatchProxy.proxy(new Object[]{x02, x12, x2}, (Object) null, changeQuickRedirect, true, 9848, clsArr, Void.TYPE).isSupported) {
            x02.D0(x12, x2);
        }
    }

    static /* synthetic */ void j(f0 x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 9838, new Class[]{f0.class}, Void.TYPE).isSupported) {
            x02.x3();
        }
    }

    static /* synthetic */ String j0(f0 x02, String x12, String x2, String x3) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02, x12, x2, x3}, (Object) null, changeQuickRedirect, true, 9849, new Class[]{f0.class, cls, cls, cls}, String.class);
        return proxy.isSupported ? (String) proxy.result : x02.v0(x12, x2, x3);
    }

    static /* synthetic */ void l0(f0 x02, String x12) {
        Class[] clsArr = {f0.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x02, x12}, (Object) null, changeQuickRedirect, true, 9850, clsArr, Void.TYPE).isSupported) {
            x02.o1(x12);
        }
    }

    static /* synthetic */ String m(f0 x02) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 9839, new Class[]{f0.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : x02.X0();
    }

    static /* synthetic */ void n(f0 x02) {
        if (!PatchProxy.proxy(new Object[]{x02}, (Object) null, changeQuickRedirect, true, 9840, new Class[]{f0.class}, Void.TYPE).isSupported) {
            x02.a3();
        }
    }

    static /* synthetic */ void s(f0 x02, String x12) {
        Class[] clsArr = {f0.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x02, x12}, (Object) null, changeQuickRedirect, true, 9837, clsArr, Void.TYPE).isSupported) {
            x02.a(x12);
        }
    }

    public boolean v1() {
        return this.N0 == 1;
    }

    public void j3(ArrayList<String> videoCodecs) {
        if (!PatchProxy.proxy(new Object[]{videoCodecs}, this, changeQuickRedirect, false, 9701, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.y0 = videoCodecs;
            a("[解码器配置] 当前共支持的解码Traack setVideoCodecs:" + videoCodecs);
        }
    }

    public void c3(int enableSdes) {
        Object[] objArr = {new Integer(enableSdes)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9702, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.z0 = enableSdes;
            a("[解码器配置] 待定解码器 setEnableSdes:" + enableSdes);
        }
    }

    public void l3(boolean flagFromBackEnd) {
        this.U0 = flagFromBackEnd;
    }

    public boolean x1() {
        return this.U0;
    }

    public void h3(int spkNSLevel) {
        this.T0 = spkNSLevel;
    }

    /* compiled from: KVSWebRTCChannel */
    public static /* synthetic */ class g {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[r.values().length];
            a = iArr;
            try {
                iArr[r.LIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.SDCARD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.PRE_LINK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private void x2(r cmd) {
        if (!PatchProxy.proxy(new Object[]{cmd}, this, changeQuickRedirect, false, 9703, new Class[]{r.class}, Void.TYPE).isSupported) {
            switch (g.a[cmd.ordinal()]) {
                case 1:
                    this.o0 = 0;
                    return;
                case 2:
                    this.o0 = 1;
                    return;
                case 3:
                    this.o0 = 2;
                    return;
                default:
                    return;
            }
        }
    }

    private f0() {
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = "";
        this.f = "";
        this.g = "";
        this.i = ChannelRole.VIEWER;
        this.k = new ArrayList();
        this.l = new ArrayList();
        this.m = new ArrayList();
        this.v = "";
        this.E = new HashMap<>();
        this.F = new HashMap<>();
        this.N = new ArrayList();
        this.P = true;
        this.Q = "";
        this.T = false;
        this.e0 = 0;
        this.f0 = 1000;
        this.g0 = 60;
        this.h0 = 0;
        this.i0 = false;
        this.q0 = 10000;
        this.u0 = 3600000;
        this.z0 = 0;
        this.A0 = false;
        this.B0 = 0;
        this.C0 = 0;
        this.D0 = io.reactivex.processors.b.Y();
        this.E0 = io.reactivex.processors.b.Y();
        this.F0 = io.reactivex.processors.b.Y();
        this.G0 = io.reactivex.processors.b.Y();
        this.H0 = io.reactivex.processors.b.Y();
        this.I0 = io.reactivex.processors.b.Y();
        this.J0 = io.reactivex.processors.b.Y();
        this.K0 = io.reactivex.processors.b.Y();
        this.L0 = new com.leedarson.smartcamera.kvswebrtc.utils.f();
        this.M0 = false;
        this.O0 = q.IDLE;
        this.Q0 = true;
        this.R0 = false;
        this.S0 = false;
        this.T0 = -1;
        this.U0 = false;
        this.V0 = new ConcurrentHashMap<>();
        this.W0 = false;
        this.X0 = "unknown";
        this.Z0 = new com.leedarson.smartcamera.kvswebrtc.utils.e();
        this.a1 = new com.leedarson.smartcamera.kvswebrtc.datarepos.a();
        this.b1 = 0;
        this.f1 = 0;
        this.g1 = 0;
        this.h1 = 0;
        this.i1 = new Handler();
        this.j1 = new k();
        this.t1 = System.currentTimeMillis();
        this.u1 = null;
        this.v1 = PeerConnection.IceConnectionState.DISCONNECTED;
        this.y1 = 0;
        this.z1 = 3;
        this.A1 = false;
        this.B1 = System.currentTimeMillis();
        this.G1 = new e();
        this.H1 = 0;
        this.I1 = 15000;
        this.J1 = new FirstFrameRenderAutoReconnectHistoryData();
        this.L1 = new j(this);
        this.S = new s(Looper.getMainLooper());
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(new com.leedarson.base.utils.r("webrtc-channel"));
        this.C = newSingleThreadExecutor;
        this.D = new d0(newSingleThreadExecutor);
        if (BaseApplication.b().n()) {
            this.Q0 = SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "use_webrtc", true);
            boolean temp = SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "use_mobile", false);
            this.R0 = temp;
            this.S0 = temp;
        }
        this.a0 = new i0();
    }

    public static void q1() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9704, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.kvswebrtc.utils.d.a().d().submit(m.c);
        }
    }

    static /* synthetic */ void R1() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9835, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("KVSWebRTCChannel").a("initPeerConnection: ", new Object[0]);
            PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(BaseApplication.b()).setInjectableLogger(new h(), Logging.Severity.LS_VERBOSE).createInitializationOptions());
            timber.log.a.g("KVSWebRTCChannel").a("initPeerConnection: end", new Object[0]);
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class h implements Loggable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onLogMessage(String s, Logging.Severity severity, String s1) {
            Class<String> cls = String.class;
            Class[] clsArr = {cls, Logging.Severity.class, cls};
            if (!PatchProxy.proxy(new Object[]{s, severity, s1}, this, changeQuickRedirect, false, 9851, clsArr, Void.TYPE).isSupported) {
                if (severity == Logging.Severity.LS_TRANSPORT_ERROR) {
                    a.b g = timber.log.a.g("KVSWebRTCChannel");
                    g.a("PeerConnectionFactory error: " + s + "  class: " + s1, new Object[0]);
                    com.leedarson.smartcamera.logreport.c b = com.leedarson.smartcamera.logreport.c.b();
                    b.e("", "", "PeerConnection error", "class=" + s1 + "  error=" + s);
                }
            }
        }
    }

    public f0(String deviceId, String userId, String supportIpv6, r cmd) {
        this();
        this.Q = userId;
        this.m0 = deviceId;
        this.n0 = supportIpv6;
        x2(cmd);
        this.k0 = true;
        this.g = ExifInterface.GPS_MEASUREMENT_2D;
        this.x0 = R0();
    }

    public f0(String awsAccessKey, String awsSecretKey, String sessionToken, String channelArn, String region, String userId, boolean isLive) {
        this();
        this.b = awsAccessKey;
        this.c = awsSecretKey;
        this.d = sessionToken;
        this.e = channelArn;
        this.f = region;
        this.Q = userId;
        this.o0 = isLive ^ true ? 1 : 0;
        this.k0 = false;
        this.x0 = R0();
    }

    public f0(String liveType, KVSParamBean kvsParam, String userId, String deviceId, String supportIpv6, r cmd) {
        this();
        this.b = kvsParam.accessKeyId;
        this.c = kvsParam.secretAccessKey;
        this.d = kvsParam.sessionToken;
        this.e = kvsParam.channelArn;
        this.f = kvsParam.region;
        this.Q = userId;
        x2(cmd);
        this.m0 = deviceId;
        this.n0 = supportIpv6;
        this.g = liveType;
        if (Constans.IPC_LIVE_TYPE_LDS.equals(liveType) || Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(liveType)) {
            this.k0 = true;
        } else {
            this.k0 = false;
        }
        this.x0 = R0();
    }

    public void b3(boolean defaultHD) {
        this.i0 = defaultHD;
    }

    public boolean s1() {
        return this.i0;
    }

    public String S0() {
        return this.b;
    }

    public String V0() {
        return this.c;
    }

    public String l1() {
        return this.d;
    }

    public String W0() {
        return this.e;
    }

    public String f1() {
        return this.f;
    }

    private void p1(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 9705, new Class[]{Context.class}, Void.TYPE).isSupported) {
            synchronized (com.leedarson.smartcamera.kvswebrtc.utils.d.a()) {
                if (System.currentTimeMillis() - this.b1 < 500) {
                    b("[风险] 短时间内执行了多次init，请检查代码流程是否异常！");
                }
                this.b1 = System.currentTimeMillis();
                this.n = new com.leedarson.smartcamera.kvswebrtc.record.h();
                if (this.o == null) {
                    this.o = JavaAudioDeviceModule.builder(context.getApplicationContext()).setUseHardwareAcousticEchoCanceler(this.R0).setUseHardwareNoiseSuppressor(this.S0).setSamplesReadyCallback(this.n).createAudioDeviceModule();
                    PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
                    options.networkIgnoreMask = 0;
                    options.disableNetworkMonitor = true;
                    if (this.p == null) {
                        DefaultVideoDecoderFactory defaultVideoDecoderFactory = new DefaultVideoDecoderFactory(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), this.a1.a());
                        this.r = defaultVideoDecoderFactory;
                        defaultVideoDecoderFactory.setOnErrorCodecErrorHandler(new v(this));
                        this.p = PeerConnectionFactory.builder().setVideoDecoderFactory(this.r).setVideoEncoderFactory(new DefaultVideoEncoderFactory(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), true, true)).setAudioDeviceModule(this.o).createPeerConnectionFactory();
                        MediaConstraints audioConstraints = new MediaConstraints();
                        X0();
                        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation", this.Q0 ? "true" : Bugly.SDK_IS_DEV));
                        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl", this.Q0 ? "true" : Bugly.SDK_IS_DEV));
                        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googHighpassFilter", this.Q0 ? "true" : Bugly.SDK_IS_DEV));
                        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", this.Q0 ? "true" : Bugly.SDK_IS_DEV));
                        int i2 = this.T0;
                        if (i2 >= 0 && i2 <= 3) {
                            audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("ldsGoogRenderNoiseSuppression", this.Q0 ? "true" : Bugly.SDK_IS_DEV));
                            audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("ldsGoogRenderNoiseSuppressionLevel", this.Q0 ? String.valueOf(this.T0) : "-1"));
                        }
                        AudioTrack createAudioTrack = this.p.createAudioTrack("KvsAudioTrack", this.p.createAudioSource(audioConstraints));
                        this.q = createAudioTrack;
                        createAudioTrack.setEnabled(false);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: N1 */
    public /* synthetic */ void O1(int code, String message) {
        if (!PatchProxy.proxy(new Object[]{new Integer(code), message}, this, changeQuickRedirect, false, 9834, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            if (code == -7001) {
                b("[解码失败]  无法支持h265的硬解码 - 软解码策略开始介入  message=" + message + "  hardCacheDecodeCompatMark=" + this.a1.a());
                this.a1.c(false);
                StringBuilder sb = new StringBuilder();
                sb.append("[解码失败] 尝试标定本机器h265的硬解为不支持 ");
                sb.append(this.a1.a());
                a(sb.toString());
                K2("H265硬解码失败强制进行重新连接", -31006003, WebRtcLogStepBean.APP_VIDEO_MEDIA_CODEC_CHECK_STEP);
            }
        }
    }

    public boolean r1() {
        return this.T;
    }

    private void Y0(e0 listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9706, new Class[]{e0.class}, Void.TYPE).isSupported) {
            this.D.b(new n(this, listener));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: L1 */
    public /* synthetic */ void M1(e0 listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9833, new Class[]{e0.class}, Void.TYPE).isSupported) {
            a("single getConnectHost run: ");
            if (this.b.isEmpty() || this.c.isEmpty() || this.d.isEmpty() || this.e.isEmpty() || this.f.isEmpty()) {
                WebRtcLogStepBean _kvsStepGetIceConfigPre = new WebRtcLogStepBean("GET_ICECONFIG", -2011);
                String tip = "缺少必要参数: awsAccessKey=" + this.b + "  awsSecretKey=" + this.c + "  sessionToken=" + this.d + "   channelArn=" + this.e + "  region=" + this.f;
                _kvsStepGetIceConfigPre.requestParams = tip;
                _kvsStepGetIceConfigPre.endTrace("KVS连接缺乏必要参数", -2011);
                WebRtcReporterV3.v(this.h, this.m0).K(_kvsStepGetIceConfigPre).L("KVS相关Key为空，连接终止");
                if (listener != null) {
                    listener.onFail(tip);
                    return;
                }
                return;
            }
            this.y1 = 0;
            b1(listener, "getIceServerList getConnectHost");
            a("single getConnectHost end ");
        }
    }

    private String K0(int length) {
        Object[] objArr = {new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9707, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < length; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    public void J2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9708, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.d1;
            if (bVar != null && !bVar.isDisposed()) {
                this.d1.dispose();
            }
            io.reactivex.disposables.b bVar2 = this.e1;
            if (bVar2 != null && !bVar2.isDisposed()) {
                this.e1.dispose();
            }
        }
    }

    public void H0(Context context, com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
        if (!PatchProxy.proxy(new Object[]{context, listener}, this, changeQuickRedirect, false, 9709, new Class[]{Context.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class}, Void.TYPE).isSupported) {
            c(context, listener, false);
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class i implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void a(double lostPercent) {
            Object[] objArr = {new Double(lostPercent)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9871, new Class[]{Double.TYPE}, Void.TYPE).isSupported) {
                f0.this.K0.onNext(Double.valueOf(lostPercent));
            }
        }
    }

    private void c(Context context, com.leedarson.smartcamera.kvswebrtc.signaling.c listener, boolean flagIsAutoReconnect) {
        if (!PatchProxy.proxy(new Object[]{context, listener, new Byte(flagIsAutoReconnect ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9710, new Class[]{Context.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            A3(0, "_innerConnect flagIsAutoConnect=" + flagIsAutoReconnect);
            if (this.k0) {
                a("[通道连接调用]  开始准备走立达信自研通道");
                this.H1 = System.currentTimeMillis();
            }
            B3();
            this.r0 = BaseApplication.b();
            this.c1 = listener;
            this.f1 = System.currentTimeMillis();
            this.Z0.p(new i());
            if (!flagIsAutoReconnect) {
                J2();
                this.J1 = new FirstFrameRenderAutoReconnectHistoryData();
            }
            a("[构建通道] 业务方调用开始连接拉流 -- connect: " + this.c1 + "==" + this.o0);
            int i2 = this.o0;
            if (i2 == 0 || i2 == 2) {
                if (!flagIsAutoReconnect) {
                    a("[构建通道] 当前非自动重新连接，重新计算拉流倒计时");
                    io.reactivex.e w2 = io.reactivex.e.w(1);
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    this.d1 = w2.g(60000, timeUnit).c(com.leedarson.base.http.observer.l.c()).I(new b0(this, listener), q.c);
                    this.e1 = io.reactivex.e.w(1).g(45000, timeUnit).c(com.leedarson.base.http.observer.l.c()).H(new a0(this, listener));
                } else {
                    a("[构建通道]  内部自在重试通道构建....，不需要重新定义倒计时");
                }
                if (this.o0 == 2) {
                    a("[通道状态变化] 预连接状态: " + this.h1 + "=" + t1() + "=" + r1());
                    if (t1() && r1()) {
                        listener.onConnected();
                    }
                    if (this.h1 > 0) {
                        a("预连接中:（上一次的预先链接还在进行) connectStatus=" + this.h1);
                        return;
                    }
                }
            }
            this.D.a();
            this.D.b(new s(this, context, listener));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: y1 */
    public /* synthetic */ void z1(com.leedarson.smartcamera.kvswebrtc.signaling.c cVar, Integer num) {
        if (!PatchProxy.proxy(new Object[]{cVar, num}, this, changeQuickRedirect, false, 9832, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.c.class, Integer.class}, Void.TYPE).isSupported) {
            Integer num2 = num;
            com.leedarson.smartcamera.kvswebrtc.signaling.c listener = cVar;
            a("[拉流超时] 开始连接拉流 -->> (60S) 还未出流 拉流超时 && 准备上报日志 && 回收资源");
            com.leedarson.smartcamera.logreport.c.b().e(this.m0, this.h, "PeerConnection", "拉流超时");
            if (!org.apache.http.util.j.c(this.h)) {
                new WebRtcLogStepBean(WebRtcLogStepBean.PULL_LIVE_TIME_OUT_CHECK_STEP, -200020).endTrace("拉流超时", -200020);
                WebRtcReporterV3 _startPlayReporter = WebRtcReporterV3.w(this.h, this.m0);
                _startPlayReporter.K(this.u1);
                _startPlayReporter.I(this.J1);
                _startPlayReporter.M(t1(), r1(), WebRtcReporterV3.v(this.h, this.m0).r(), this.u1, this.W0);
            }
            com.leedarson.smartcamera.kvswebrtc.utils.f fVar = this.L0;
            if (fVar != null) {
                if (fVar.f() != null) {
                    a("[拉流超时]  当前选中的trackId =" + this.L0.f().b);
                }
                List<VideoTrack> tracks = this.L0.h();
                for (int i2 = 0; i2 < tracks.size(); i2++) {
                    VideoTrack track = tracks.get(i2);
                    a("[拉流超时] 打印当前track的可用性 " + track.enabled() + " sinks.size=" + track.getSinks().size() + " isDataChannelConnected=" + r1() + " peerConnection=" + t1());
                }
            }
            a("[拉流超时] 终止掉自动重连任务");
            r3();
            I2(true);
            if (listener != null) {
                listener.d(-1100);
            }
            this.h1 = -1;
        }
    }

    static /* synthetic */ void A1(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: B1 */
    public /* synthetic */ void C1(com.leedarson.smartcamera.kvswebrtc.signaling.c listener, Integer num) {
        Class[] clsArr = {com.leedarson.smartcamera.kvswebrtc.signaling.c.class, Integer.class};
        if (!PatchProxy.proxy(new Object[]{listener, num}, this, changeQuickRedirect, false, 9831, clsArr, Void.TYPE).isSupported) {
            a("超时45秒，查询app和设备版本是否需要升级");
            listener.f();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D1 */
    public /* synthetic */ void E1(Context context, com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
        Class[] clsArr = {Context.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class};
        if (!PatchProxy.proxy(new Object[]{context, listener}, this, changeQuickRedirect, false, 9830, clsArr, Void.TYPE).isSupported) {
            p1(context);
            if (this.k0) {
                this.f0 = 1000;
                this.g0 = 3;
                initMQTTClient(listener);
                return;
            }
            this.f0 = 10000;
            this.g0 = 2;
            a("开始准备初始化KVS的webSocket通道");
            initWebSocketClient(listener);
        }
    }

    private void A3(int _connectStatus, String desc) {
        Object[] objArr = {new Integer(_connectStatus), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9711, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            this.h1 = _connectStatus;
            a("[更新链接状态]   connectStatus=" + this.h1 + "   desc=" + desc + " deviceId:" + this.m0);
        }
    }

    private void H2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9712, new Class[0], Void.TYPE).isSupported) {
            a("[预连接] 触发重新连接...reConnect");
            A3(0, "reconnect111");
            com.leedarson.smartcamera.logreport.c.b().e(this.m0, "", "preConnect", "开始预连接");
            this.D.b(new k(this, new j()));
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class j implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9872, new Class[0], Void.TYPE).isSupported) {
                f0.e(f0.this, 2, "reConnect onOpen  111");
                com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "preConnect", "onOpen");
                f0.this.createSdpOffer(new a());
            }
        }

        /* compiled from: KVSWebRTCChannel */
        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9878, new Class[0], Void.TYPE).isSupported) {
                    f0 f0Var = f0.this;
                    f0.s(f0Var, "[流信息添加] 收到流信息添加 - 处理扫尾工作 reConnect onAddStream: " + Thread.currentThread());
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 9879, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    com.leedarson.log.f.a("[DataChannel] 状态发生变化-- createSdpOffer onDataChannelStateChange: " + state + " deviceId:" + f0.this.m0);
                    if (state == DataChannel.State.OPEN) {
                        android.os.Message msg = android.os.Message.obtain();
                        msg.what = 16;
                        f0.this.S.sendMessage(msg);
                    }
                }
            }

            public void c(byte[] bytes) {
            }

            public void onError(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 9880, new Class[]{String.class}, Void.TYPE).isSupported) {
                    f0.s(f0.this, "reConnect onError: ");
                    f0.e(f0.this, -1, "onError44");
                }
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 9881, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                    f0 f0Var = f0.this;
                    f0.s(f0Var, "[ICE通道状态变化] reConnect onIceConnectionChange: " + iceConnectionState);
                    if (iceConnectionState == PeerConnection.IceConnectionState.FAILED || iceConnectionState == PeerConnection.IceConnectionState.CLOSED || iceConnectionState == PeerConnection.IceConnectionState.DISCONNECTED) {
                        f0.s(f0.this, "[ICE通道状态变化] reConnect PeerConnection 断开");
                        f0 f0Var2 = f0.this;
                        f0.e(f0Var2, -1, "preConnect onIceConnectionChange: " + iceConnectionState);
                        long unused = f0.this.g1 = 0;
                        if (f0.this.c1 != null) {
                            f0.this.c1.a(new Event("", "", iceConnectionState.toString()));
                        }
                        if (f0.this.R != null) {
                            f0.this.R.f();
                        }
                        f0.this.I2(false);
                    }
                }
            }
        }

        public void e(String message) {
        }

        public void c(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 9873, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.log.f.a("kvsSdpAnswer");
            }
        }

        public void g(String message) {
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9874, new Class[0], Void.TYPE).isSupported) {
                f0.s(f0.this, "reConnect onClose: ");
                f0.e(f0.this, -1, "preConnect onClose: ");
            }
        }

        public void a(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 9875, new Class[]{Event.class}, Void.TYPE).isSupported) {
                f0.s(f0.this, "preConnect onError: ");
                f0.e(f0.this, -1, "preConnect onError: ");
            }
        }

        public void onException(Exception exc) {
            if (!PatchProxy.proxy(new Object[]{exc}, this, changeQuickRedirect, false, 9876, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                f0.s(f0.this, "preConnect onException: ");
                f0.e(f0.this, -1, "preConnect onException: ");
            }
        }

        public void d(int stateCode) {
            if (!PatchProxy.proxy(new Object[]{new Integer(stateCode)}, this, changeQuickRedirect, false, 9877, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                f0 f0Var = f0.this;
                f0.s(f0Var, "[拉流-通道状态同步] preConnect onWebRtcStateChange: " + stateCode);
                if (f0.this.c1 != null) {
                    f0.this.c1.d(stateCode);
                }
                if (stateCode != 200) {
                    f0.this.I2(false);
                }
            }
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$reConnect$7 */
    public /* synthetic */ void a2(com.leedarson.smartcamera.kvswebrtc.signaling.c connectListener) {
        if (!PatchProxy.proxy(new Object[]{connectListener}, this, changeQuickRedirect, false, 9829, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.c.class}, Void.TYPE).isSupported) {
            if (this.h1 > 0) {
                a("[预连接] 连接正在处理中，不需要重复连接");
                return;
            }
            this.h1 = 1;
            A3(1, "[预连接] 开始进行预连接操作 single preConnect run  connecting ");
            BaseApplication b2 = BaseApplication.b();
            this.r0 = b2;
            p1(b2);
            this.f0 = 1000;
            this.g0 = 3;
            initMQTTClient(connectListener);
        }
    }

    public void y2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9713, new Class[0], Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append("[预连接] preConnect: ");
            sb.append(this.g1);
            sb.append(" connectStatus: ");
            sb.append(this.h1);
            sb.append(" connect: ");
            sb.append(t1() && r1());
            a(sb.toString());
            if (System.currentTimeMillis() - this.g1 < CacheHandler.delayTime || this.h1 > 0 || (t1() && r1())) {
                this.g1 = 0;
                a("[预连接] 被拦截..... isPeerConnectionConnect=" + t1() + "  isConnect()" + r1() + "  connectStatus=" + this.h1);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            this.g1 = currentTimeMillis;
            this.h1 = 0;
            this.H1 = currentTimeMillis;
            n3();
            H2();
        }
    }

    public void u3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9714, new Class[0], Void.TYPE).isSupported) {
            a("[预连接超时器] 停止(解除)预连接超时器监听");
            Handler handler = this.i1;
            if (handler != null) {
                handler.removeCallbacks(this.j1);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n3() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9715(0x25f3, float:1.3614E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.os.Handler r1 = r0.i1
            if (r1 == 0) goto L_0x002a
            java.lang.Runnable r2 = r0.j1
            r1.removeCallbacks(r2)
            android.os.Handler r1 = r0.i1
            java.lang.Runnable r2 = r0.j1
            r3 = 35000(0x88b8, double:1.72923E-319)
            r1.postDelayed(r2, r3)
        L_0x002a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.n3():void");
    }

    /* compiled from: KVSWebRTCChannel */
    public class k implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9882, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "preConnect", "预连接超时");
                f0 f0Var = f0.this;
                f0.s(f0Var, "preConnect timeout: " + f0.this.f1 + "==" + f0.this.g1 + " (已经过了近35S) 但是通道还没有建立成功（SDP未交换成功 / candidate是否有收集足够 / 一直打洞Checking中）");
                f0.this.I2(true);
                f0.this.h1 = -1;
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 9;
                f0.this.S.sendMessage(msg);
            }
        }
    }

    public void createSdpOffer(com.leedarson.smartcamera.kvswebrtc.signaling.d dataListener) {
        if (!PatchProxy.proxy(new Object[]{dataListener}, this, changeQuickRedirect, false, 9716, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.d.class}, Void.TYPE).isSupported) {
            a("[媒体协商] 开始创建Offer createSdpOffer isPeerConnection: " + t1());
            this.G = dataListener;
            MediaConstraints sdpMediaConstraints = new MediaConstraints();
            sdpMediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));
            sdpMediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
            if (this.y == null) {
                J0();
            }
            PeerConnection peerConnection = this.y;
            if (peerConnection != null) {
                peerConnection.createOffer(new l(dataListener), sdpMediaConstraints);
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class l extends g0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.smartcamera.kvswebrtc.signaling.d b;

        l(com.leedarson.smartcamera.kvswebrtc.signaling.d dVar) {
            this.b = dVar;
        }

        public void onCreateSuccess(SessionDescription sessionDescription) {
            if (!PatchProxy.proxy(new Object[]{sessionDescription}, this, changeQuickRedirect, false, 9883, new Class[]{SessionDescription.class}, Void.TYPE).isSupported) {
                super.onCreateSuccess(sessionDescription);
                try {
                    com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "PeerConnection", "createOffer");
                    f0 f0Var = f0.this;
                    f0.s(f0Var, "[媒体协商] Offer创建成功 MqttLog createSdpOffer onCreateSuccess" + sessionDescription + "， isConnect：" + f0.this.T + " isLDSWebRTC=" + f0.this.k0);
                    f0.this.y.setLocalDescription(new g0(), sessionDescription);
                    Message sdpOfferMessage = Message.createOfferMessage(sessionDescription, f0.this.h);
                    f0 f0Var2 = f0.this;
                    f0Var2.b0 = sdpOfferMessage;
                    int unused = f0Var2.e0 = f0Var2.k0 ? 0 : 10;
                    f0.j(f0.this);
                    int unused2 = f0.this.h0 = 0;
                    if (org.apache.http.util.j.c(f0.this.h)) {
                        f0.m(f0.this);
                        f0.n(f0.this);
                    }
                    String sdpMessage = sessionDescription.description;
                    if (!f0.this.k0) {
                        f0 f0Var3 = f0.this;
                        f0.s(f0Var3, "[媒体协商] 开始准备发送Offer(KVS-WebSocket) payload=" + f0.this.b0.getMessagePayload());
                        f0.this.w.r(f0.this.b0);
                        com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "kvsoffer-answer", "Sending Offer:");
                    } else if (f0.this.l0 != null) {
                        f0.s(f0.this, "[媒体协商] 开始准备发送Offer MqttLog vary sendSdpOffer ");
                        com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o o = f0.this.l0;
                        String f = f0.this.m0;
                        f0 f0Var4 = f0.this;
                        String str = f0Var4.h;
                        String p = f0Var4.x0;
                        List q = f0.this.l;
                        String r = f0.this.f;
                        String t = f0.this.g;
                        o.q(f, str, p, sdpMessage, q, r, t, "PeerConnection创建时,在重试发送sdp :sendCount=" + f0.this.h0 + "  maxTimes=" + f0.this.g0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onCreateFailure(String error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9884, new Class[]{String.class}, Void.TYPE).isSupported) {
                super.onCreateFailure(error);
                f0 f0Var = f0.this;
                f0.s(f0Var, "[媒体协商] Offer创建失败 createSdpOffer onCreateFailure error: " + error);
                com.leedarson.smartcamera.kvswebrtc.signaling.d dVar = this.b;
                if (dVar != null) {
                    dVar.onError("媒体协商创建失败: error=" + error);
                }
            }
        }
    }

    public void e3(SurfaceViewRenderer renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 9717, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            try {
                a("[渲染器设置]  设置画布 setRenderer: " + renderer);
                this.l1 = null;
                this.o1 = renderer;
                if (0 == 0) {
                    this.l1 = new e(this, renderer);
                }
                if (renderer != null) {
                    renderer.removeFrameListener(this.l1);
                    renderer.addFrameListener(this.l1, 1.0f);
                    this.L0.l(renderer);
                    return;
                }
                b("[渲染器设置]  上层业务调用的渲染器为空 renderer==null");
            } catch (Exception e2) {
                b("[渲染设置]  render设置发生异常  e=" + e2.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j2 */
    public /* synthetic */ void k2(SurfaceViewRenderer renderer, Bitmap bitmap) {
        Class[] clsArr = {SurfaceViewRenderer.class, Bitmap.class};
        if (!PatchProxy.proxy(new Object[]{renderer, bitmap}, this, changeQuickRedirect, false, 9828, clsArr, Void.TYPE).isSupported) {
            J2();
            this.n1 = bitmap;
            if (renderer != null) {
                renderer.addFrameListener(this.l1, 1.0f);
            }
        }
    }

    public void f3(SurfaceViewRenderer renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 9718, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            if (this.A != null) {
                com.leedarson.log.f.b("KVSWebRTCChannel", "remoteVideoTrackId=" + this.A.id() + " videoTrackState=" + this.A.state());
                this.A.addSink(renderer);
                this.A.setEnabled(true);
                try {
                    com.leedarson.smartcamera.kvswebrtc.record.j getVideoPTSRenderer = new com.leedarson.smartcamera.kvswebrtc.record.j(com.leedarson.smartcamera.kvswebrtc.utils.c.b());
                    getVideoPTSRenderer.setListener(this.R);
                    this.A.addSink(getVideoPTSRenderer);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            AudioTrack audioTrack = this.B;
            if (audioTrack != null) {
                audioTrack.setEnabled(true);
            }
        }
    }

    public synchronized boolean p3(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 9719, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        a("startRecording path: " + path);
        try {
            if (this.t == null) {
                this.t = new com.leedarson.smartcamera.kvswebrtc.record.l(this.o);
            }
            com.leedarson.smartcamera.kvswebrtc.record.k kVar = new com.leedarson.smartcamera.kvswebrtc.record.k(this.L0.h(), this.t);
            this.s = kVar;
            kVar.a(new File(path));
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void w3() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0028 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0028 }
            r4 = 0
            r5 = 9720(0x25f8, float:1.362E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0028 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0028 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0028 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0018
            monitor-exit(r8)
            return
        L_0x0018:
            r0 = r8
            java.lang.String r1 = "stopRecording"
            r0.a(r1)     // Catch:{ all -> 0x0028 }
            com.leedarson.smartcamera.kvswebrtc.record.k r1 = r0.s     // Catch:{ all -> 0x0028 }
            if (r1 == 0) goto L_0x0026
            r1.b()     // Catch:{ all -> 0x0028 }
        L_0x0026:
            monitor-exit(r8)
            return
        L_0x0028:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.w3():void");
    }

    public synchronized boolean m3(b.c onYUVDataListener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{onYUVDataListener}, this, changeQuickRedirect, false, 9721, new Class[]{b.c.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        a("[Focus] 开启 - startFocus: ");
        try {
            com.leedarson.smartcamera.kvswebrtc.focus.a aVar = new com.leedarson.smartcamera.kvswebrtc.focus.a(this.L0.h());
            this.k1 = aVar;
            aVar.startFocus(onYUVDataListener);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void s3() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0027 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0027 }
            r4 = 0
            r5 = 9722(0x25fa, float:1.3623E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0027 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0027 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0027 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0018
            monitor-exit(r8)
            return
        L_0x0018:
            r0 = r8
            java.lang.String r1 = "[Focus功能] 关闭 -stopFocus"
            r0.a(r1)     // Catch:{ all -> 0x0027 }
            com.leedarson.smartcamera.kvswebrtc.focus.a r1 = r0.k1     // Catch:{ all -> 0x0027 }
            if (r1 == 0) goto L_0x0025
            r1.a()     // Catch:{ all -> 0x0027 }
        L_0x0025:
            monitor-exit(r8)
            return
        L_0x0027:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.s3():void");
    }

    public void B0(String path, h0 listener) {
        if (!PatchProxy.proxy(new Object[]{path, listener}, this, changeQuickRedirect, false, 9723, new Class[]{String.class, h0.class}, Void.TYPE).isSupported) {
            this.m1 = listener;
            try {
                com.leedarson.smartcamera.kvswebrtc.utils.d.a().d().submit(new d(this, path));
            } catch (Exception e2) {
                e2.printStackTrace();
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 3;
                msg.arg1 = 0;
                this.S.sendMessage(msg);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: H1 */
    public /* synthetic */ void I1(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 9827, new Class[]{String.class}, Void.TYPE).isSupported) {
            Bitmap bitmap = this.n1;
            if (bitmap == null || bitmap.isRecycled()) {
                android.os.Message msg = android.os.Message.obtain();
                msg.what = 3;
                msg.arg1 = 0;
                this.S.sendMessage(msg);
                return;
            }
            a("[画面截图] captureIntime capture onFrame:  getScreenshotPath " + this.n1 + "   width=" + this.n1.getWidth() + "   height=" + this.n1.getHeight() + " path=" + path);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
                this.n1.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                android.os.Message msg2 = android.os.Message.obtain();
                msg2.what = 3;
                msg2.arg1 = 1;
                this.S.sendMessage(msg2);
            } catch (Exception e2) {
                android.os.Message msg3 = android.os.Message.obtain();
                msg3.what = 3;
                msg3.arg1 = 0;
                this.S.sendMessage(msg3);
            }
        }
    }

    public Bitmap c1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9724, new Class[0], Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap bitmap = this.n1;
        if (bitmap == null || bitmap.isRecycled()) {
            return this.n1;
        }
        try {
            return this.n1.copy(Bitmap.Config.ARGB_8888, true);
        } catch (Exception e2) {
            return null;
        }
    }

    public void q3(com.leedarson.smartcamera.listener.n listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9725, new Class[]{com.leedarson.smartcamera.listener.n.class}, Void.TYPE).isSupported) {
            this.p1 = listener;
            this.D.b(new y(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l2 */
    public /* synthetic */ void m2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9826, new Class[0], Void.TYPE).isSupported) {
            a("startTalk run: ");
            AudioTrack audioTrack = this.q;
            if (audioTrack != null) {
                audioTrack.setEnabled(true);
                JavaAudioDeviceModule javaAudioDeviceModule = this.o;
                if (javaAudioDeviceModule != null) {
                    javaAudioDeviceModule.startIntercom();
                }
                X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SPEAKERSTART, AVIOCTRLDEFs.SMsgAVIoctrlAVStream.parseContent(0));
                android.os.Message msg = android.os.Message.obtain();
                s sVar = this.S;
                msg.what = 7;
                sVar.sendMessage(msg);
                a("startTalk end: ");
            }
        }
    }

    public void z3(com.leedarson.smartcamera.listener.n listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9726, new Class[]{com.leedarson.smartcamera.listener.n.class}, Void.TYPE).isSupported) {
            this.q1 = listener;
            this.D.b(new h(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: p2 */
    public /* synthetic */ void q2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9825, new Class[0], Void.TYPE).isSupported) {
            a("startTalk run: ");
            if (this.q != null) {
                y3();
                android.os.Message msg = android.os.Message.obtain();
                s sVar = this.S;
                msg.what = 8;
                sVar.sendMessage(msg);
                a("startTalk end: ");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void y3() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9727(0x25ff, float:1.363E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            org.webrtc.AudioTrack r2 = r1.q
            if (r2 == 0) goto L_0x0032
            org.webrtc.PeerConnection r3 = r1.y
            if (r3 == 0) goto L_0x0022
            r2.setEnabled(r0)
        L_0x0022:
            org.webrtc.audio.JavaAudioDeviceModule r2 = r1.o
            if (r2 == 0) goto L_0x0029
            r2.stopIntercom()
        L_0x0029:
            byte[] r0 = com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlAVStream.parseContent(r0)
            r2 = 849(0x351, float:1.19E-42)
            r1.X2(r2, r0)
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.y3():void");
    }

    public void z0(boolean isEnable) {
        Object[] objArr = {new Byte(isEnable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9728, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.D.b(new l(this, isEnable));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: F1 */
    public /* synthetic */ void G1(boolean isEnable) {
        Object[] objArr = {new Byte(isEnable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9824, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.B != null) {
                try {
                    a("[音频Track] 远程音频Enable变化 " + isEnable);
                    this.B.setEnabled(isEnable);
                } catch (Exception ex) {
                    ex.toString();
                }
            }
        }
    }

    public boolean t1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9729, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean isPeerConnectionConnected = false;
        boolean icePeerConnectionConnectedFlag = false;
        boolean icePeerConnectionCompletedFlag = false;
        try {
            PeerConnection.PeerConnectionState peerConnectionState = this.P0;
            isPeerConnectionConnected = peerConnectionState != null && peerConnectionState == PeerConnection.PeerConnectionState.CONNECTED;
            PeerConnection.IceConnectionState iceConnectionState = this.v1;
            icePeerConnectionConnectedFlag = iceConnectionState != null && iceConnectionState == PeerConnection.IceConnectionState.CONNECTED;
            icePeerConnectionCompletedFlag = iceConnectionState != null && iceConnectionState == PeerConnection.IceConnectionState.COMPLETED;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (isPeerConnectionConnected || icePeerConnectionConnectedFlag || icePeerConnectionCompletedFlag) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void x3() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9730(0x2602, float:1.3635E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.c0
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.c0 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.x3():void");
    }

    public void k1(String startTimeStamp, String endTimeStamp, int type, com.leedarson.smartcamera.listener.e listener) {
        Class<String> cls = String.class;
        Object[] objArr = {startTimeStamp, endTimeStamp, new Integer(type), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9731, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.e.class}, Void.TYPE).isSupported) {
            this.J = listener;
            this.M = null;
            this.M = new byte[0];
            X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlHasListEventReq.parseConent(0, startTimeStamp, endTimeStamp, type));
        }
    }

    public void j1(long startTimeStamp, long endTimeStamp, int type, com.leedarson.smartcamera.listener.e listener) {
        Object[] objArr = {new Long(startTimeStamp), new Long(endTimeStamp), new Integer(type), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9732, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.e.class}, Void.TYPE).isSupported) {
            a("getSDTimeList");
            this.J = listener;
            this.M = null;
            this.M = new byte[0];
            byte[] hasListBytes = AVIOCTRLDEFs.SMsgAVIoctrlHasListEventReq.parseConent(0, startTimeStamp, endTimeStamp, type);
            int nextInt = new Random().nextInt();
            this.r1 = nextInt;
            W2(nextInt, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_REQ, hasListBytes);
        }
    }

    public void h1(String startTime, String endTime, int type, com.leedarson.smartcamera.listener.b listener) {
        Class<String> cls = String.class;
        int i2 = 1;
        Object[] objArr = {startTime, endTime, new Integer(type), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9733, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.b.class}, Void.TYPE).isSupported) {
            this.K = listener;
            this.N.clear();
            if (type != 0) {
                i2 = 18;
            }
            byte[] bytes = AVIOCTRLDEFs.SMsgAVIoctrlListEventReq.parseConent(0, startTime, endTime, (byte) i2, (byte) 0);
            int nextInt = new Random().nextInt();
            this.s1 = nextInt;
            W2(nextInt, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ, bytes);
        }
    }

    public void i1(long j2, long j3, int i2, com.leedarson.smartcamera.listener.b listener) {
        int i3 = 1;
        Object[] objArr = {new Long(j2), new Long(j3), new Integer(i2), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9734, new Class[]{cls, cls, Integer.TYPE, com.leedarson.smartcamera.listener.b.class}, Void.TYPE).isSupported) {
            long startTime = j2;
            long endTime = j3;
            int type = i2;
            this.K = listener;
            this.N.clear();
            if (type != 0) {
                i3 = 18;
            }
            byte[] bytes = AVIOCTRLDEFs.SMsgAVIoctrlListEventReq.parseConent(0, startTime, endTime, (byte) i3, (byte) 0);
            int nextInt = new Random().nextInt();
            this.s1 = nextInt;
            W2(nextInt, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ, bytes);
        }
    }

    public void N0(int deleteType, int i2, List<Long> recordTimestamp, com.leedarson.smartcamera.listener.a listener) {
        Object[] objArr = {new Integer(deleteType), new Integer(i2), recordTimestamp, listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9735, new Class[]{cls, cls, List.class, com.leedarson.smartcamera.listener.a.class}, Void.TYPE).isSupported) {
            int eventType = i2;
            this.L = listener;
            if (recordTimestamp != null && recordTimestamp.size() != 0) {
                X2(AVIOCTRLDEFs.OTYPE_USER_IPCAM_DELLISTEVENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDelListEventReq.parseConent(0, recordTimestamp.size(), (byte) 0, (byte) 1, (byte) recordTimestamp.size(), (byte) deleteType, recordTimestamp, eventType));
            }
        }
    }

    public void setOnWebRTCSDPlayListener(com.leedarson.smartcamera.listener.j onWebRTCSDPlayListener) {
        this.R = onWebRTCSDPlayListener;
    }

    public void S2(long j2, int i2) {
        long j3 = j2;
        Object[] objArr = {new Long(j2), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9737, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            long timestamp = j2;
            int type = i2;
            d3(1000);
            int i3 = this.O + 1;
            this.O = i3;
            if (i3 > 26) {
                this.O = 1;
            }
            a("sdRecordPlay: " + timestamp + " sdChannelId: " + this.O);
            X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 16, 0, this.O, timestamp, 0, type));
        }
    }

    public void T2(long timestamp, int type) {
        Object[] objArr = {new Long(timestamp), new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9738, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            a("sdRecordResume ");
            X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 17, 0, this.O, timestamp, 0, type));
        }
    }

    public void V2(long timestamp, int type) {
        Object[] objArr = {new Long(timestamp), new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9739, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            a("sdRecordStop: " + this.O0 + " timestamp: " + timestamp + " type: " + type);
            q qVar = this.O0;
            if ((qVar == null || qVar == q.SD) && timestamp != 0) {
                X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 1, 0, this.O, timestamp, 0, type));
            }
        }
    }

    public void R2(long timestamp, int i2) {
        Object[] objArr = {new Long(timestamp), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9740, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            int type = i2;
            a("sdRecordPause_timestamp: " + timestamp);
            q qVar = this.O0;
            if (qVar == null || qVar == q.SD) {
                X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 0, 0, this.O, timestamp, 0, type));
            }
        }
    }

    public void U2(long timestamp, int i2, int seekFlag) {
        Object[] objArr = {new Long(timestamp), new Integer(i2), new Integer(seekFlag)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Long.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9741, clsArr, Void.TYPE).isSupported) {
            int type = i2;
            d3(1000);
            a("sdRecordSeekPlay_timestamp: " + timestamp);
            X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(0, 6, seekFlag, this.O, timestamp, 0, type));
        }
    }

    public void g1(com.leedarson.smartcamera.listener.c listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9742, new Class[]{com.leedarson.smartcamera.listener.c.class}, Void.TYPE).isSupported) {
            this.V = listener;
            X2(802, AVIOCTRLDEFs.SMsgAVIoctrlGetStreamCtrlReq.parseContent(0));
        }
    }

    public void g3(int resolution, com.leedarson.smartcamera.listener.k listener) {
        if (!PatchProxy.proxy(new Object[]{new Integer(resolution), listener}, this, changeQuickRedirect, false, 9743, new Class[]{Integer.TYPE, com.leedarson.smartcamera.listener.k.class}, Void.TYPE).isSupported) {
            this.U = listener;
            X2(800, AVIOCTRLDEFs.SMsgAVIoctrlSetStreamCtrlReq.parseContent(0, (byte) resolution));
        }
    }

    public void E0() {
        com.leedarson.smartcamera.kvswebrtc.signaling.d dVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9747, new Class[0], Void.TYPE).isSupported) {
            long curTime = System.currentTimeMillis();
            if (this.B0 == 0) {
                this.B0 = curTime;
            }
            if (curTime - this.B0 > CacheHandler.delayTime) {
                this.B0 = curTime;
                int i2 = this.C0 + 1;
                this.C0 = i2;
                if (i2 < 2 && (dVar = this.G) != null) {
                    dVar.onError("SD卡回放进度响应超时 " + this.m0 + ", " + this.h);
                }
            }
        }
    }

    public void Z2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9748, new Class[0], Void.TYPE).isSupported) {
            if (!BaseApplication.d) {
                Y2(AVIOCTRLDEFs.CMD_AVIO_CTRL_HEARTHEAT_REQ, new byte[0]);
            }
        }
    }

    public void o3(com.leedarson.smartcamera.listener.l listener, com.leedarson.smartcamera.listener.g onRadarDataReportListener) {
        if (!PatchProxy.proxy(new Object[]{listener, onRadarDataReportListener}, this, changeQuickRedirect, false, 9749, new Class[]{com.leedarson.smartcamera.listener.l.class, com.leedarson.smartcamera.listener.g.class}, Void.TYPE).isSupported) {
            this.X = listener;
            this.Z = onRadarDataReportListener;
            X2(AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_START_REQ, new byte[0]);
        }
    }

    public void P2(long timestamp, com.leedarson.smartcamera.listener.g onRadarDataReportListener) {
        Object[] objArr = {new Long(timestamp), onRadarDataReportListener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9750, new Class[]{Long.TYPE, com.leedarson.smartcamera.listener.g.class}, Void.TYPE).isSupported) {
            this.Z = onRadarDataReportListener;
            byte[] data = Packet.longToByteArray_Little(timestamp);
            a("requestSDCard timestamp:" + timestamp + ",hex:" + w.b(data));
            X2(AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_SDCARD_START_REQ, data);
        }
    }

    public void v3(com.leedarson.smartcamera.listener.m listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9751, new Class[]{com.leedarson.smartcamera.listener.m.class}, Void.TYPE).isSupported) {
            this.Y = listener;
            this.Z = null;
            X2(AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_STOP_REQ, new byte[0]);
        }
    }

    private void X2(int type, byte[] data) {
        Object[] objArr = {new Integer(type), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9752, new Class[]{Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            W2(Q0(2), type, data);
        }
    }

    private void W2(int seq, int type, byte[] data) {
        Object[] objArr = {new Integer(seq), new Integer(type), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9753, new Class[]{cls, cls, byte[].class}, Void.TYPE).isSupported) {
            this.D.b(new c0(this, seq, type, data));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f2 */
    public /* synthetic */ void g2(int seq, int type, byte[] data) {
        Object[] objArr = {new Integer(seq), new Integer(type), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9823, new Class[]{cls, cls, byte[].class}, Void.TYPE).isSupported) {
            ByteBuffer buffer = ByteBuffer.allocate(28);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(seq);
            buffer.putInt(4, type);
            buffer.putLong(8, System.currentTimeMillis());
            buffer.putInt(16, data.length);
            buffer.putInt(20, 0);
            try {
                DataChannel dataChannel = this.I;
                if (dataChannel != null) {
                    dataChannel.send(new DataChannel.Buffer(ByteBuffer.wrap(A0(buffer.array(), data)), true));
                    BaseApplication.b().n();
                }
            } catch (Exception e2) {
                com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
                String str = this.m0;
                String str2 = this.h;
                b2.e(str, str2, "PeerConnection", "sendCtrl Exception:" + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    private void Y2(int type, byte[] data) {
        Object[] objArr = {new Integer(type), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9754, new Class[]{Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            this.D.b(new u(this, type, data));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h2 */
    public /* synthetic */ void i2(int type, byte[] data) {
        if (!PatchProxy.proxy(new Object[]{new Integer(type), data}, this, changeQuickRedirect, false, 9822, new Class[]{Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            int seq = new Random().nextInt();
            ByteBuffer buffer = ByteBuffer.allocate(28);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(seq);
            buffer.putInt(4, type);
            buffer.putLong(8, System.currentTimeMillis());
            buffer.putInt(16, data.length);
            buffer.putInt(20, 0);
            if (this.I != null) {
                if (BaseApplication.b().n()) {
                    a("[通道心跳] 收跳发送-->保持webrtcChannel : " + seq);
                }
                com.leedarson.smartcamera.kvswebrtc.utils.e eVar = this.Z0;
                eVar.c(" webrtc 心跳发送==>" + seq);
                this.I.customSend(new DataChannel.Buffer(ByteBuffer.wrap(A0(buffer.array(), data)), true), false);
            }
        }
    }

    public byte[] A0(byte[] byte_1, byte[] byte_2) {
        Class<byte[]> cls = byte[].class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{byte_1, byte_2}, this, changeQuickRedirect, false, 9755, new Class[]{cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] byte_3 = new byte[(byte_1.length + byte_2.length)];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public void w2(boolean videoTrackEnable, boolean audioTrackEnable) {
        Object[] objArr = {new Byte(videoTrackEnable ? (byte) 1 : 0), new Byte(audioTrackEnable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9756, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.D.b(new r(this, videoTrackEnable, audioTrackEnable));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: Y1 */
    public /* synthetic */ void Z1(boolean videoTrackEnable, boolean audioTrackEnable) {
        Object[] objArr = {new Byte(videoTrackEnable ? (byte) 1 : 0), new Byte(audioTrackEnable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9821, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            a("[暂停拉流] pauseLive run: " + this.H);
            if (this.H != null) {
                try {
                    if (this.L0 != null) {
                        a("[视频流Track]  可用性设置 ? " + videoTrackEnable);
                        this.L0.j(videoTrackEnable);
                    }
                    AudioTrack audioTrack = this.B;
                    if (audioTrack != null) {
                        audioTrack.setEnabled(audioTrackEnable);
                    }
                } catch (Exception exception) {
                    b("[暂停拉流] pauseLive exception==>" + exception.toString());
                }
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class m implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9885, new Class[0], Void.TYPE).isSupported) {
                try {
                    f0.s(f0.this, "setVideoTrackEnable run: ");
                    if (f0.this.H != null) {
                        f0.s(f0.this, "[视频流Track]  可用性设置.setVideoTrackEnable ? true");
                        f0.this.L0.j(true);
                    }
                    f0.s(f0.this, "setVideoTrackEnable end: ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void k3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9757, new Class[0], Void.TYPE).isSupported) {
            this.D.b(new m());
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class n implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;

        n(boolean z) {
            this.c = z;
        }

        public void run() {
            boolean z = false;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9886, new Class[0], Void.TYPE).isSupported) {
                try {
                    f0.s(f0.this, "[拉流模式变化] resumeLive run:(获取拉流) ");
                    if (f0.this.H != null) {
                        f0.s(f0.this, "[视频流Track]  可用性设置.resumeLive ? true");
                        f0.this.L0.j(true);
                        if (f0.this.B != null) {
                            AudioTrack y = f0.this.B;
                            if (!this.c) {
                                z = true;
                            }
                            y.setEnabled(z);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Q2(boolean isMute) {
        Object[] objArr = {new Byte(isMute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9758, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (t1()) {
                this.D.b(new n(isMute));
                return;
            }
            a("resumeLive: " + this.c1);
            H0(this.r0, this.c1);
        }
    }

    public void t3(SurfaceViewRenderer renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 9759, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            this.D.b(new w(this, renderer));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n2 */
    public /* synthetic */ void o2(SurfaceViewRenderer renderer) {
        if (!PatchProxy.proxy(new Object[]{renderer}, this, changeQuickRedirect, false, 9820, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            try {
                this.E.clear();
                this.F.clear();
                if (this.H != null) {
                    a("[视频流Track]  停止拉流 可用性设置.stopLive ? false");
                    this.L0.j(false);
                    AudioTrack audioTrack = this.B;
                    if (audioTrack != null) {
                        audioTrack.setEnabled(false);
                    }
                }
                if (renderer != null) {
                    this.L0.e();
                }
                this.H = null;
            } catch (Exception ex) {
                b("[停止拉流] 发生异常 stopLive.KVS: exception=" + ex.toString());
            }
        }
    }

    private String e1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9760, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder _tipLeaveLiveRoom = new StringBuilder();
        if (!(this.y == null || this.P0 == null)) {
            _tipLeaveLiveRoom.append(" P2PState:" + this.P0);
        }
        if (this.I != null) {
            _tipLeaveLiveRoom.append(" DataChannelState:" + this.I.state().toString());
        }
        return _tipLeaveLiveRoom.toString();
    }

    public void t2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9761, new Class[0], Void.TYPE).isSupported) {
            a("[进出直播间] 用户离开直播间...... isPeerConnectionConnect=" + t1() + " ,isDataChannelConnected()=" + r1() + " , flagIsRendered=" + this.W0);
            this.Z0.o();
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o oVar = this.l0;
            if (oVar != null) {
                oVar.s((com.leedarson.smartcamera.kvswebrtc.signaling.a) null);
            }
            if (u1()) {
                WebRtcReporterV3 _webRtcPlayReporter = WebRtcReporterV3.w(this.h, this.m0);
                if (_webRtcPlayReporter.C() > 0) {
                    if (_webRtcPlayReporter.p(WebRtcLogStepBean.CHANGE_LIVING_MODE_REQ)) {
                        WebRtcLogStepBean webRtcLogStepBean = this.u1;
                        if (webRtcLogStepBean != null) {
                            _webRtcPlayReporter.J(webRtcLogStepBean);
                        }
                        if (t1() && r1()) {
                            _webRtcPlayReporter.I(this.J1);
                            _webRtcPlayReporter.M(t1(), r1(), WebRtcReporterV3.v(this.h, this.m0).r(), this.u1, this.W0);
                        }
                    } else {
                        a("leaveLiveRoom  2222222222 \n" + new Gson().toJson((Object) _webRtcPlayReporter.r()));
                        if (!this.W0) {
                            _webRtcPlayReporter.I(this.J1);
                            _webRtcPlayReporter.M(t1(), r1(), WebRtcReporterV3.v(this.h, this.m0).r(), this.u1, this.W0);
                        }
                        _webRtcPlayReporter.o();
                    }
                } else if (!this.W0) {
                    WebRtcLogStepBean webRtcLogStepBean2 = this.u1;
                    if (webRtcLogStepBean2 != null) {
                        _webRtcPlayReporter.K(webRtcLogStepBean2);
                    }
                    _webRtcPlayReporter.I(this.J1);
                    _webRtcPlayReporter.M(t1(), r1(), WebRtcReporterV3.v(this.h, this.m0).r(), this.u1, this.W0);
                }
            }
            this.Z0.j();
            this.W0 = false;
            this.u1 = null;
        }
    }

    public void O0(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 9762, new Class[]{String.class}, Void.TYPE).isSupported) {
            a("[进出直播间] 开始进入直播间  ---》 ref=" + ref);
            WebRtcLogStepBean webRtcLogStepBean = new WebRtcLogStepBean(WebRtcLogStepBean.ENTER_LIVE_ROOM, 200);
            this.u1 = webRtcLogStepBean;
            webRtcLogStepBean.endTrace("开始进入直播间 " + ref, 200);
            a3();
            y0();
        }
    }

    private void a3() {
        com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o oVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9763, new Class[0], Void.TYPE).isSupported) {
            if (u1() && !org.apache.http.util.j.c(this.h) && (oVar = this.l0) != null) {
                oVar.u(this.m0, this.h, Q0(1));
            }
        }
    }

    private int Q0(int i2) {
        LivePlaySeqRequestPairBean _livePlaySeqBean;
        Object[] objArr = {new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9764, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (org.apache.http.util.j.c(this.h)) {
            return 100;
        }
        if (this.V0.containsKey(this.h)) {
            _livePlaySeqBean = this.V0.get(this.h);
            _livePlaySeqBean.seq++;
        } else {
            _livePlaySeqBean = new LivePlaySeqRequestPairBean();
            _livePlaySeqBean.seq = 100;
        }
        this.V0.put(this.h, _livePlaySeqBean);
        return _livePlaySeqBean.seq;
    }

    public void I2(boolean closeWebsocket) {
        if (!PatchProxy.proxy(new Object[]{new Byte(closeWebsocket ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9765, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            d(closeWebsocket, false);
        }
    }

    public void K2(String refReason, int causeCode, String stepName) {
        WebRtcLogStepBean webRtcLogStepBean;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{refReason, new Integer(causeCode), stepName}, this, changeQuickRedirect, false, 9766, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            b("[自动重连策略] 强制触发  ref=" + refReason + "  causeCode=" + causeCode + "  stepName=" + stepName);
            if (!(this.J1 == null || (webRtcLogStepBean = this.u1) == null)) {
                webRtcLogStepBean.resetStartTime();
                this.u1.endTrace(refReason, 200);
                WebRtcReporterV3 preLinksReporter = WebRtcReporterV3.v(this.h, this.m0);
                WebRtcLogStepBean networkChangeStep = new WebRtcLogStepBean(stepName, causeCode);
                networkChangeStep.endTrace("原因：" + refReason, -31006002);
                preLinksReporter.K(networkChangeStep);
                preLinksReporter.d(t1(), r1(), preLinksReporter.r(), this.u1);
                this.J1.d(preLinksReporter);
            }
            com.leedarson.smartcamera.kvswebrtc.signaling.b bVar = this.w1;
            if (bVar != null) {
                bVar.c();
            }
            d(false, true);
            this.S.removeCallbacks(this.L1);
            this.S.postDelayed(this.L1, this.I1);
            b("[自动重连策略] App网络变化 - 重新连接开始 - 开始重建通道 ");
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.i iVar = this.F1;
            if (iVar != null) {
                iVar.a();
            }
            c(BaseApplication.b(), this.c1, true);
        }
    }

    private void d(boolean z2, boolean z3) {
        Object[] objArr = {new Byte(z2 ? (byte) 1 : 0), new Byte(z3 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9767, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            boolean isAutoReconnectMode = z3;
            boolean closeWebsocket = z2;
            if (isAutoReconnectMode) {
                a("[通道资源释放] 正在收集&&分析上次通道连接失败的原因");
                if (!(this.J1 == null || this.u1 == null)) {
                    WebRtcReporterV3 preLinksReporter = WebRtcReporterV3.v(this.h, this.m0);
                    preLinksReporter.d(t1(), r1(), preLinksReporter.r(), this.u1);
                    this.J1.d(preLinksReporter);
                    if (!preLinksReporter.n()) {
                        a("[candiate检测] 发现App未发送relay candidate给设备（存在iceConfig失效）-->清除本地iceConfig缓存数据");
                        com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o oVar = this.l0;
                        if (oVar != null) {
                            oVar.f("删除iceConfig本地缓存");
                        }
                    }
                    preLinksReporter.o();
                }
            }
            a("[通道资源释放] LDS.WEBRTC.release  release() 开始准备释放资源    closeWebsocket=" + closeWebsocket);
            if (!this.M0) {
                WebRtcLogStepBean _webRtcStepOfExitLiveRoom = new WebRtcLogStepBean(WebRtcLogStepBean.LEAVE_LIVE_ROOM, -100001);
                WebRtcReporterV3 _preLinkeReporter = WebRtcReporterV3.v(this.h, this.m0);
                if (_preLinkeReporter.C() > 0) {
                    _webRtcStepOfExitLiveRoom.endTrace("离开直播间" + e1(), -100001);
                    _preLinkeReporter.K(_webRtcStepOfExitLiveRoom);
                    _preLinkeReporter.L("释放离开直播间");
                }
                WebRtcReporterV3 _webRtcPlayReporter = WebRtcReporterV3.w(this.h, this.m0);
                if (_webRtcPlayReporter.C() > 0) {
                    WebRtcLogStepBean webRtcLogStepBean = this.u1;
                    if (webRtcLogStepBean != null) {
                        _webRtcPlayReporter.J(webRtcLogStepBean);
                    }
                    _webRtcStepOfExitLiveRoom.endTrace("离开直播间" + e1(), -100001);
                    _webRtcPlayReporter.K(_webRtcStepOfExitLiveRoom);
                    _webRtcPlayReporter.L("用户主动离开直播间,释放直播间资源");
                }
            }
            this.H1 = 0;
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o oVar2 = this.l0;
            if (oVar2 != null) {
                oVar2.h = true;
                oVar2.s((com.leedarson.smartcamera.kvswebrtc.signaling.a) null);
                this.l0 = null;
            }
            Timer timer = this.p0;
            if (timer != null) {
                timer.cancel();
                this.p0 = null;
            }
            Timer timer2 = this.t0;
            if (timer2 != null) {
                timer2.cancel();
                this.t0 = null;
            }
            Timer timer3 = this.c0;
            if (timer3 != null) {
                timer3.cancel();
                this.c0 = null;
            }
            try {
                if (!org.apache.http.util.j.c(this.h)) {
                    this.V0.remove(this.h);
                }
                this.h = null;
                M2(this.B, this.y, this.I, this.p, this.o, this.w, closeWebsocket);
                this.p = null;
                this.B = null;
                this.y = null;
                this.I = null;
                this.o = null;
                this.w = null;
                this.E.clear();
                this.F.clear();
                this.t = null;
                this.T = false;
                this.v = "";
                x3();
                this.j0 = null;
                this.V = null;
                this.U = null;
                this.X = null;
                this.Y = null;
                this.Z = null;
                this.m1 = null;
                this.o1 = null;
                this.n1 = null;
                if (!isAutoReconnectMode) {
                    a("[资源释放] 释放自动连接超时记时器 -当前非自动重连接");
                    J2();
                } else {
                    a("[资源释放] 不自动释放用户进入直播间的超时记时器 - 当前属于自动重新连接Keep");
                }
                A3(-1, "release ");
                u3();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void M2(AudioTrack remoteAudioTrack, PeerConnection peerConnection, DataChannel dataChannel, PeerConnectionFactory factory, JavaAudioDeviceModule deviceModule, com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n webSocketClient, boolean closeWebsocket) {
        Object[] objArr = {remoteAudioTrack, peerConnection, dataChannel, factory, deviceModule, webSocketClient, new Byte(closeWebsocket ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9768, new Class[]{AudioTrack.class, PeerConnection.class, DataChannel.class, PeerConnectionFactory.class, JavaAudioDeviceModule.class, com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            this.D.b(new g(this, remoteAudioTrack, dataChannel, peerConnection, closeWebsocket, webSocketClient, factory, deviceModule));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b2 */
    public /* synthetic */ void c2(AudioTrack audioTrack, DataChannel dataChannel, PeerConnection peerConnection, boolean z2, com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n nVar, PeerConnectionFactory peerConnectionFactory, JavaAudioDeviceModule javaAudioDeviceModule) {
        Object[] objArr = {audioTrack, dataChannel, peerConnection, new Byte(z2 ? (byte) 1 : 0), nVar, peerConnectionFactory, javaAudioDeviceModule};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9819, new Class[]{AudioTrack.class, DataChannel.class, PeerConnection.class, Boolean.TYPE, com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n.class, PeerConnectionFactory.class, JavaAudioDeviceModule.class}, Void.TYPE).isSupported) {
            DataChannel dataChannel2 = dataChannel;
            PeerConnectionFactory factory = peerConnectionFactory;
            boolean closeWebsocket = z2;
            AudioTrack remoteAudioTrack = audioTrack;
            PeerConnection peerConnection2 = peerConnection;
            JavaAudioDeviceModule deviceModule = javaAudioDeviceModule;
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n webSocketClient = nVar;
            if (remoteAudioTrack != null) {
                try {
                    remoteAudioTrack.setEnabled(false);
                } catch (Exception e2) {
                    a("LDS.WEBRTC release产生异常：e" + e2.toString());
                    return;
                }
            }
            if (dataChannel2 != null) {
                dataChannel2.dispose();
            }
            if (peerConnection2 != null) {
                peerConnection2.dispose();
            }
            if (closeWebsocket && webSocketClient != null) {
                webSocketClient.j();
            }
            if (factory != null) {
                factory.dispose();
            }
            if (deviceModule != null) {
                deviceModule.release();
            }
        }
    }

    private void J0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9769, new Class[0], Void.TYPE).isSupported) {
            PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(this.m);
            rtcConfig.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
            rtcConfig.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN;
            rtcConfig.keyType = PeerConnection.KeyType.ECDSA;
            rtcConfig.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
            rtcConfig.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.DISABLED;
            rtcConfig.iceUnwritableTimeMs = 30000;
            rtcConfig.audioJitterBufferMaxPackets = 200;
            if (org.apache.http.util.j.c(this.n0) || "0".equals(this.n0)) {
                rtcConfig.disableIpv6 = true;
            } else {
                rtcConfig.disableIpv6 = false;
            }
            if (this.z0 == 1) {
                rtcConfig.enableDtls = false;
            }
            a("[PeerConnection初始化] createLocalPeerConnection  set RTCConfiguration enableDtls" + rtcConfig.enableDtls);
            if (this.p != null) {
                o oVar = new o(this.h);
                this.w1 = oVar;
                this.y = this.p.createPeerConnection(rtcConfig, (PeerConnection.Observer) oVar);
                this.H = this.p.createLocalMediaStream("KvsLocalMediaStream");
                x0();
                int i2 = this.o0;
                if (i2 == 0 || i2 == 2) {
                    t0();
                }
                u0();
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class o extends com.leedarson.smartcamera.kvswebrtc.signaling.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        o(String peerId) {
            super(peerId);
        }

        public void onConnectionChange(PeerConnection.PeerConnectionState newState) {
            if (!PatchProxy.proxy(new Object[]{newState}, this, changeQuickRedirect, false, 9887, new Class[]{PeerConnection.PeerConnectionState.class}, Void.TYPE).isSupported) {
                s0.b(this, newState);
                PeerConnection.PeerConnectionState unused = f0.this.P0 = newState;
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onConnectionChange 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                if (f0.this.P0 == PeerConnection.PeerConnectionState.CONNECTED) {
                    f0 f0Var = f0.this;
                    f0Var.J0.onNext(new kotlin.n(Boolean.valueOf(f0Var.t1()), Boolean.valueOf(f0.this.r1())));
                }
                f0 f0Var2 = f0.this;
                f0.s(f0Var2, "[通道连接变化] onConnectionChange: " + newState.toString());
                com.leedarson.smartcamera.logreport.c b = com.leedarson.smartcamera.logreport.c.b();
                String f = f0.this.m0;
                String str = f0.this.h;
                b.e(f, str, "PeerConnection", "onConnectionChange:" + newState);
            }
        }

        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 9888, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                super.onIceConnectionChange(iceConnectionState);
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onIceConnectionChange 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                try {
                    WebRtcLogStepBean _iceConnectionStep = new WebRtcLogStepBean(WebRtcLogStepBean.PEER_CONNECTION_CONNECT, 200);
                    _iceConnectionStep.desc = "iceConnection 状态变更" + iceConnectionState;
                    f0 f0Var = f0.this;
                    WebRtcReporterV3 _preLinkReporter = WebRtcReporterV3.v(f0Var.h, f0Var.m0);
                    _preLinkReporter.K(_iceConnectionStep);
                    if (iceConnectionState == PeerConnection.IceConnectionState.CONNECTED) {
                        _iceConnectionStep.endTrace("通道连接成功", 200);
                        _preLinkReporter.L("P2P通道连接成功");
                        f0.this.Z0.j();
                    } else if (iceConnectionState == PeerConnection.IceConnectionState.CLOSED) {
                        _iceConnectionStep.endTrace("通道关闭", -2005);
                        boolean unused = f0.this.W0 = false;
                        if (_preLinkReporter.m("GET_ICECONFIG")) {
                            _preLinkReporter.L("P2P通道在连接时-通道异常CLOSED");
                        }
                        f0.this.Z0.o();
                    } else if (iceConnectionState == PeerConnection.IceConnectionState.FAILED) {
                        _iceConnectionStep.endTrace("通道建立失败", -31007201);
                        boolean unused2 = f0.this.W0 = false;
                        if (_preLinkReporter.m("GET_ICECONFIG")) {
                            _preLinkReporter.L("P2P通道在连接时-FAILED");
                        }
                        f0.this.Z0.o();
                    }
                    f0 f0Var2 = f0.this;
                    f0.s(f0Var2, "[ICE通道状态变化] peerConnection 状态变化: " + iceConnectionState.toString() + " 当前dataListener=" + f0.this.G);
                    com.leedarson.smartcamera.logreport.c b = com.leedarson.smartcamera.logreport.c.b();
                    String f = f0.this.m0;
                    String str = f0.this.h;
                    b.e(f, str, "PeerConnection", "onIceConnectionChange:" + iceConnectionState);
                    f0 f0Var3 = f0.this;
                    f0Var3.v1 = iceConnectionState;
                    if (f0Var3.G != null) {
                        f0.this.G.onIceConnectionChange(iceConnectionState);
                    }
                    if (iceConnectionState == PeerConnection.IceConnectionState.FAILED || iceConnectionState == PeerConnection.IceConnectionState.CLOSED || iceConnectionState == PeerConnection.IceConnectionState.DISCONNECTED) {
                        f0.s(f0.this, "[ICE通道状态变化] PeerConnection 断开");
                        if (f0.this.c1 != null) {
                            f0.this.c1.a(new Event("", "", iceConnectionState.toString()));
                        }
                        if (f0.this.R != null) {
                            f0.this.R.f();
                        }
                        f0.this.I2(false);
                    }
                } catch (Exception e) {
                    f0 f0Var4 = f0.this;
                    f0.F(f0Var4, "[ICE通道状态变化] 发生异常 onIceConnectionChange " + e.toString());
                }
            }
        }

        public void onIceCandidate(IceCandidate iceCandidate) {
            if (!PatchProxy.proxy(new Object[]{iceCandidate}, this, changeQuickRedirect, false, 9889, new Class[]{IceCandidate.class}, Void.TYPE).isSupported) {
                super.onIceCandidate(iceCandidate);
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onIceCandidate 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                try {
                    Message message = f0.G(f0.this, iceCandidate);
                    f0.s(f0.this, "[候选人]  onIceCandidate本地候选人生成");
                    if (!f0.H(f0.this)) {
                        return;
                    }
                    if (f0.this.k0) {
                        String unused = f0.this.w0 = iceCandidate.sdp;
                        if (f0.this.l0 != null) {
                            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o o = f0.this.l0;
                            String f = f0.this.m0;
                            f0 f0Var = f0.this;
                            o.p(f, f0Var.h, f0Var.w0, "自研webrtc-Candidate");
                        }
                    } else if (f0.this.w != null) {
                        f0.this.w.q(message);
                        com.leedarson.smartcamera.logreport.c b = com.leedarson.smartcamera.logreport.c.b();
                        String f2 = f0.this.m0;
                        String str = f0.this.h;
                        b.e(f2, str, "PeerConnection", "kvs Sending IceCandidate:" + iceCandidate.toString());
                    }
                } catch (Exception e) {
                    f0 f0Var2 = f0.this;
                    f0.F(f0Var2, "[候选人] 发生异常 onIceCandidate " + e.toString());
                }
            }
        }

        public void onAddStream(MediaStream mediaStream) {
            if (!PatchProxy.proxy(new Object[]{mediaStream}, this, changeQuickRedirect, false, 9890, new Class[]{MediaStream.class}, Void.TYPE).isSupported) {
                super.onAddStream(mediaStream);
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onAddStream 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                try {
                    f0 f0Var = f0.this;
                    f0.s(f0Var, "[数据流添加] 开始添加数据流  onAddStream: " + mediaStream.getId());
                    com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "PeerConnection", "onAddStream");
                    f0.K(f0.this, mediaStream);
                    f0.this.S.removeMessages(2);
                    android.os.Message msg = android.os.Message.obtain();
                    msg.what = 4;
                    f0.this.S.sendMessage(msg);
                } catch (Exception e) {
                    f0 f0Var2 = f0.this;
                    f0.F(f0Var2, "[数据流添加] 发生异常 onAddStream " + e.toString());
                }
            }
        }

        public void onRemoveStream(MediaStream mediaStream) {
            if (!PatchProxy.proxy(new Object[]{mediaStream}, this, changeQuickRedirect, false, 9891, new Class[]{MediaStream.class}, Void.TYPE).isSupported) {
                super.onRemoveStream(mediaStream);
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onRemoveStream 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                f0 f0Var = f0.this;
                f0.s(f0Var, "[数据流移除] 移除远程数据流 remove remote video stream (and audio)  " + mediaStream.getId());
            }
        }

        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
            Class[] clsArr = {RtpReceiver.class, MediaStream[].class};
            if (!PatchProxy.proxy(new Object[]{rtpReceiver, mediaStreams}, this, changeQuickRedirect, false, 9892, clsArr, Void.TYPE).isSupported) {
                super.onAddTrack(rtpReceiver, mediaStreams);
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onAddTrack 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                try {
                    MediaStreamTrack track = rtpReceiver.track();
                    f0 f0Var = f0.this;
                    f0.s(f0Var, "[添加Track] 添加track onAddTrack：" + track);
                    if (track instanceof VideoTrack) {
                        VideoTrack unused = f0.this.z = (VideoTrack) track;
                    }
                } catch (Exception e) {
                    f0 f0Var2 = f0.this;
                    f0.F(f0Var2, "[添加Track] 发生异常 onAddTrack " + e.toString());
                }
            }
        }

        public void onSelectedCandidatePairChanged(CandidatePairChangeEvent event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 9893, new Class[]{CandidatePairChangeEvent.class}, Void.TYPE).isSupported) {
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onSelectedCandidatePairChanged 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                try {
                    String unused = f0.this.X0 = com.leedarson.smartcamera.kvswebrtc.utils.b.a(event.local.sdp, event.remote.sdp);
                    f0 f0Var = f0.this;
                    String unused2 = f0Var.Y0 = "localCandidate:" + event.local.sdp + ",remoteCandidate:" + event.remote.sdp;
                } catch (Exception e) {
                    f0 f0Var2 = f0.this;
                    f0.F(f0Var2, "[候选人提名] 发生异常 e=" + e.toString());
                }
                WebRtcReporterV3.R(f0.this.h + "", f0.this.Y0);
                WebRtcReporterV3.S(f0.this.h + "", f0.this.X0);
                f0 f0Var3 = f0.this;
                f0.s(f0Var3, "[候选人提名] 提名结果:" + f0.this.X0 + " iceCandidate已选定  reason=" + event.reason + ", local=" + event.local.sdp + ", remote=" + event.remote.sdp);
            }
        }

        public void onMessageReport(String logType, String key, String desc) {
            Class<String> cls = String.class;
            Class[] clsArr = {cls, cls, cls};
            if (!PatchProxy.proxy(new Object[]{logType, key, desc}, this, changeQuickRedirect, false, 9894, clsArr, Void.TYPE).isSupported) {
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onMessageReport 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                } else if (logType.equals("NackReport")) {
                    if (key.equals("NackMaxRetries")) {
                        com.leedarson.smartcamera.kvswebrtc.utils.e B = f0.this.Z0;
                        B.e(" Nack 出现视频重传达最大次数 desc=" + desc);
                        String str = f0.this.h;
                        WebRtcReporterV3.Q(str, "Nack发生重传" + desc);
                    } else if (key.equals("NackTimeout")) {
                        com.leedarson.smartcamera.kvswebrtc.utils.e B2 = f0.this.Z0;
                        B2.e(" Nack 视频重传失败(超时)" + desc);
                    } else if (key.equals("NackReceived")) {
                        com.leedarson.smartcamera.kvswebrtc.utils.e B3 = f0.this.Z0;
                        B3.e(" Nack 视频接收成功" + desc);
                    }
                } else if (logType.equals("RTTReport")) {
                    com.leedarson.smartcamera.kvswebrtc.utils.e B4 = f0.this.Z0;
                    B4.f(" RTTReport: " + desc);
                    if (f0.this.y != null) {
                        try {
                            f0.this.y.getStats(new f(this));
                        } catch (Exception e) {
                            f0 f0Var = f0.this;
                            f0.F(f0Var, "[通话质量] WebRtc通话质量收集返回出现异常： e=" + e.toString());
                        }
                    }
                } else if (logType.equals("StunPing")) {
                    com.leedarson.smartcamera.kvswebrtc.utils.e B5 = f0.this.Z0;
                    B5.g(" sturnPing: " + logType + " " + key + ":" + desc);
                } else if (logType.equals("TransPortError")) {
                    com.leedarson.smartcamera.kvswebrtc.utils.e B6 = f0.this.Z0;
                    B6.g("通道断开异常 (stun ping 异常) desc=" + desc);
                    f0 f0Var2 = f0.this;
                    com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.l lVar = f0Var2.E1;
                    if (lVar != null) {
                        lVar.a(f0Var2.m0, f0.this.h, desc);
                    }
                    f0 f0Var3 = f0.this;
                    f0.F(f0Var3, "[通道传输异常] TransPortError: " + desc);
                }
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: d */
        public /* synthetic */ void e(RTCStatsReport report) {
            if (!PatchProxy.proxy(new Object[]{report}, this, changeQuickRedirect, false, 9896, new Class[]{RTCStatsReport.class}, Void.TYPE).isSupported) {
                if (report != null) {
                    f0.this.Z0.d(" webrtc 通话质量报告", report);
                }
            }
        }

        public void onDataChannel(DataChannel dataChannel) {
            if (!PatchProxy.proxy(new Object[]{dataChannel}, this, changeQuickRedirect, false, 9895, new Class[]{DataChannel.class}, Void.TYPE).isSupported) {
                super.onDataChannel(dataChannel);
                if (b()) {
                    f0.s(f0.this, "[废弃通道处理] onDataChannel 当前通道已被释放-不用通知业务层(大概率内部在做自动重新连接)");
                    return;
                }
                try {
                    f0.s(f0.this, "[DataChannel] DataChannel 通道创建成功 Remote onDataChannel: ");
                    dataChannel.registerObserver(new a(dataChannel));
                } catch (Exception e) {
                    f0 f0Var = f0.this;
                    f0.F(f0Var, "[DataChannel] 发生异常 registerObserver e=" + e.toString());
                }
            }
        }

        /* compiled from: KVSWebRTCChannel */
        public class a implements DataChannel.Observer {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ DataChannel a;

            a(DataChannel dataChannel) {
                this.a = dataChannel;
            }

            public void onBufferedAmountChange(long l) {
                Object[] objArr = {new Long(l)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9897, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                    f0 f0Var = f0.this;
                    f0.s(f0Var, "[DataChannel] 当前DataChannel剩余数量 onBufferedAmountChange=" + l);
                }
            }

            public void onStateChange() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9898, new Class[0], Void.TYPE).isSupported) {
                    try {
                        f0 f0Var = f0.this;
                        f0.s(f0Var, "[DataChannel] DataChannel发生了变化 state: " + this.a.state());
                        if (f0.this.G != null) {
                            f0.this.G.a(this.a.state());
                        }
                        com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
                        String f = f0.this.m0;
                        String str = f0.this.h;
                        b2.e(f, str, "PeerConnection", "Remote Data Channel onStateChange:" + this.a.state());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onMessage(DataChannel.Buffer buffer) {
                byte[] bytes;
                if (!PatchProxy.proxy(new Object[]{buffer}, this, changeQuickRedirect, false, 9899, new Class[]{DataChannel.Buffer.class}, Void.TYPE).isSupported) {
                    f0.s(f0.this, "[DataChannel] 收到来自DataChannel 数据 onMessage ");
                    if (buffer.data.hasArray()) {
                        bytes = buffer.data.array();
                    } else {
                        bytes = new byte[buffer.data.remaining()];
                        buffer.data.get(bytes);
                    }
                    if (f0.this.G != null) {
                        f0.this.G.c(bytes);
                    }
                }
            }
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9770, new Class[0], Void.TYPE).isSupported) {
            this.L0.e();
            List<String> streamIds = new ArrayList<>();
            streamIds.add("KvsLocalMediaStream");
            int trackCount = 1;
            ArrayList<String> arrayList = this.y0;
            if (arrayList != null && arrayList.size() > 1) {
                trackCount = this.y0.size();
            }
            if (this.y != null) {
                for (int index = 0; index < trackCount; index++) {
                    this.y.addTrack(M0(), streamIds);
                }
                int trackIdx = 0;
                for (RtpTransceiver obj : this.y.getTransceivers()) {
                    if (obj.getMediaType() == MediaStreamTrack.MediaType.MEDIA_TYPE_VIDEO) {
                        obj.setDirection(RtpTransceiver.RtpTransceiverDirection.RECV_ONLY);
                        this.L0.d(obj.getReceiver().track(), trackIdx);
                        trackIdx++;
                    }
                }
            }
        }
    }

    private VideoTrack M0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9771, new Class[0], VideoTrack.class);
        if (proxy.isSupported) {
            return (VideoTrack) proxy.result;
        }
        VideoTrack videoTrack = this.p.createVideoTrack("KvsVideoTrack", this.p.createVideoSource(false));
        videoTrack.setEnabled(false);
        return videoTrack;
    }

    private boolean w1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9772, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.k0) {
            return true;
        }
        com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n nVar = this.w;
        if (nVar == null || !nVar.k()) {
            return false;
        }
        return true;
    }

    private AWSCredentials Z0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9773, new Class[0], AWSCredentials.class);
        if (proxy.isSupported) {
            return (AWSCredentials) proxy.result;
        }
        if (this.x == null) {
            this.x = new BasicSessionCredentials(this.b, this.c, this.d);
        }
        return this.x;
    }

    private AWSKinesisVideoClient T0(String region) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{region}, this, changeQuickRedirect, false, 9774, new Class[]{String.class}, AWSKinesisVideoClient.class);
        if (proxy.isSupported) {
            return (AWSKinesisVideoClient) proxy.result;
        }
        AWSKinesisVideoClient awsKinesisVideoClient = new AWSKinesisVideoClient(Z0());
        Region tempRegion = Region.getRegion(region);
        if (tempRegion == null) {
            a("LDS.WEBRTC  getAwsKinesisVideoClient 获取空Region=" + region + "   deviceId=" + this.m0 + "   liveType=" + this.g);
        } else {
            awsKinesisVideoClient.setRegion(tempRegion);
        }
        awsKinesisVideoClient.setSignerRegionOverride(region);
        awsKinesisVideoClient.setServiceNameIntern("kinesisvideo");
        return awsKinesisVideoClient;
    }

    private AWSKinesisVideoSignalingClient U0(String region, String endpoint) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{region, endpoint}, this, changeQuickRedirect2, false, 9775, new Class[]{cls, cls}, AWSKinesisVideoSignalingClient.class);
        if (proxy.isSupported) {
            return (AWSKinesisVideoSignalingClient) proxy.result;
        }
        AWSKinesisVideoSignalingClient client = new AWSKinesisVideoSignalingClient(Z0());
        Region tempRegion = Region.getRegion(region);
        if (tempRegion == null) {
            a("LDS.WEBRTC  AWSKinesisVideoSignalingClient 获取空Region=" + region + "   deviceId=" + this.m0 + "   liveType=" + this.g);
        } else {
            client.setRegion(tempRegion);
        }
        client.setSignerRegionOverride(region);
        client.setServiceNameIntern("kinesisvideo");
        client.setEndpoint(endpoint);
        return client;
    }

    private URI m1(String viewerEndpoint) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewerEndpoint}, this, changeQuickRedirect, false, 9776, new Class[]{String.class}, URI.class);
        if (proxy.isSupported) {
            return (URI) proxy.result;
        }
        return com.leedarson.smartcamera.kvswebrtc.utils.a.l(URI.create(viewerEndpoint), Z0().getAWSAccessKeyId(), Z0().getAWSSecretKey(), Z0() instanceof AWSSessionCredentials ? ((AWSSessionCredentials) Z0()).getSessionToken() : "", URI.create(this.j), this.f);
    }

    private Message I0(IceCandidate iceCandidate) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{iceCandidate}, this, changeQuickRedirect, false, 9777, new Class[]{IceCandidate.class}, Message.class);
        if (proxy.isSupported) {
            return (Message) proxy.result;
        }
        String sdpMid = iceCandidate.sdpMid;
        int sdpMLineIndex = iceCandidate.sdpMLineIndex;
        return new Message("ICE_CANDIDATE", this.u, this.h, new String(Base64.encode(("{\"candidate\":\"" + iceCandidate.sdp + "\",\"sdpMid\":\"" + sdpMid + "\",\"sdpMLineIndex\":" + sdpMLineIndex + "}").getBytes(), 11)));
    }

    private void D0(String senderClientId, IceCandidate iceCandidate) {
        if (!PatchProxy.proxy(new Object[]{senderClientId, iceCandidate}, this, changeQuickRedirect, false, 9778, new Class[]{String.class, IceCandidate.class}, Void.TYPE).isSupported) {
            try {
                if (this.E.containsKey(senderClientId)) {
                    boolean addIce = this.E.get(senderClientId).addIceCandidate(iceCandidate);
                    com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
                    String str = this.m0;
                    String str2 = this.h;
                    String str3 = this.k0 ? "ldsmqtt" : "kvswebsocket";
                    StringBuilder sb = new StringBuilder();
                    sb.append("Added ice candidate ");
                    sb.append(iceCandidate);
                    sb.append(" ");
                    sb.append(addIce ? "Successfully" : "Failed");
                    b2.e(str, str2, str3, sb.toString());
                } else if (this.F.containsKey(senderClientId)) {
                    Queue<IceCandidate> pendingIceCandidatesQueueByClientId = this.F.get(senderClientId);
                    pendingIceCandidatesQueueByClientId.add(iceCandidate);
                    this.F.put(senderClientId, pendingIceCandidatesQueueByClientId);
                } else {
                    Queue<IceCandidate> pendingIceCandidatesQueueByClientId2 = new LinkedList<>();
                    pendingIceCandidatesQueueByClientId2.add(iceCandidate);
                    this.F.put(senderClientId, pendingIceCandidatesQueueByClientId2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                b("[ICECheckError] 数据发生异常 e=" + e2.toString() + "  detail:" + iceCandidate.sdp);
                WebRtcReporterV3 preLinkReporter = WebRtcReporterV3.v(this.h, this.m0);
                WebRtcLogStepBean _sdpExchangeFail = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_CENDIDITE_CHECKING, -31007601);
                _sdpExchangeFail.setResponse("setIceCandidateError:" + e2.toString() + " detail:" + iceCandidate.sdp);
                _sdpExchangeFail.endTrace("[ICE交换] 收到错误的IceCandidate ", -31007601);
                preLinkReporter.K(_sdpExchangeFail);
                preLinkReporter.L("ICE交换 - 设置iceCandidate数据异常");
            }
        }
    }

    private void o1(String clientId) {
        if (!PatchProxy.proxy(new Object[]{clientId}, this, changeQuickRedirect, false, 9779, new Class[]{String.class}, Void.TYPE).isSupported) {
            Queue<IceCandidate> pendingIceCandidatesQueueByClientId = this.F.get(clientId);
            while (pendingIceCandidatesQueueByClientId != null && !pendingIceCandidatesQueueByClientId.isEmpty()) {
                boolean addIceCandidate = this.E.get(clientId).addIceCandidate(pendingIceCandidatesQueueByClientId.peek());
                pendingIceCandidatesQueueByClientId.remove();
            }
            this.F.remove(clientId);
        }
    }

    private void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9780, new Class[0], Void.TYPE).isSupported) {
            a("[DataChannel] 数据关联通道 Data channel addDataChannelToLocalPeer ");
            PeerConnection peerConnection = this.y;
            if (peerConnection != null) {
                DataChannel createDataChannel = peerConnection.createDataChannel("data-channel-of-" + this.h, new DataChannel.Init());
                this.I = createDataChannel;
                if (createDataChannel != null) {
                    createDataChannel.registerObserver(new p());
                }
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class p implements DataChannel.Observer {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public void onBufferedAmountChange(long l) {
        }

        public void onStateChange() {
            boolean z = false;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9900, new Class[0], Void.TYPE).isSupported) {
                if (f0.this.I != null) {
                    try {
                        f0 f0Var = f0.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("[DataChannel] 状态变化 Local Data Channel onStateChange: state: ");
                        sb.append(f0.this.I.state().toString());
                        sb.append("dataListener==null?");
                        sb.append(f0.this.G == null);
                        f0.s(f0Var, sb.toString());
                        f0 f0Var2 = f0.this;
                        DataChannel.State state = f0Var2.I.state();
                        DataChannel.State state2 = DataChannel.State.OPEN;
                        if (state == state2) {
                            z = true;
                        }
                        boolean unused = f0Var2.T = z;
                        if (f0.this.G != null) {
                            f0.this.G.a(f0.this.I.state());
                        }
                        if (f0.this.T) {
                            f0 f0Var3 = f0.this;
                            f0Var3.J0.onNext(new kotlin.n(Boolean.valueOf(f0Var3.t1()), Boolean.valueOf(f0.this.r1())));
                        }
                        if (f0.this.I.state() != state2) {
                            f0 f0Var4 = f0.this;
                            f0Var4.h1 = -1;
                            long unused2 = f0Var4.g1 = 0;
                            f0.s(f0.this, "[DataChannel] 状态变化 onStateChange " + f0.this.h1 + " preConnectStamp: " + f0.this.g1);
                            if (f0.this.p0 != null) {
                                f0.this.p0.cancel();
                                Timer unused3 = f0.this.p0 = null;
                            }
                            if (f0.this.t0 != null) {
                                f0.this.t0.cancel();
                                Timer unused4 = f0.this.t0 = null;
                            }
                        } else if (Constans.IPC_LIVE_TYPE_LDS.equals(f0.this.g) || Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(f0.this.g)) {
                            f0.s(f0.this, "[DataChannel] DataChannel 已经打开准备通知业务层可以拉流了");
                            f0.this.u3();
                            f0.R(f0.this);
                            f0.e(f0.this, 3, "DataChannel 已经打开   ");
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 16;
                            f0.this.S.sendMessage(msg);
                            f0 f0Var5 = f0.this;
                            Timer unused5 = f0Var5.p0 = f0.W(f0Var5, f0Var5.p0);
                            f0.this.p0.schedule(new a(), (long) f0.this.e0, 10000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /* compiled from: KVSWebRTCChannel */
        public class a extends TimerTask {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9902, new Class[0], Void.TYPE).isSupported) {
                    if (f0.this.T) {
                        f0.this.Z2();
                        if (f0.this.o0 == 1 && f0.this.A0) {
                            f0.this.E0();
                        }
                    }
                }
            }
        }

        public void onMessage(DataChannel.Buffer buffer) {
            if (!PatchProxy.proxy(new Object[]{buffer}, this, changeQuickRedirect, false, 9901, new Class[]{DataChannel.Buffer.class}, Void.TYPE).isSupported) {
                if (buffer.binary) {
                    buffer.data.order(ByteOrder.LITTLE_ENDIAN);
                    int seq = buffer.data.getInt();
                    int cmd = buffer.data.getInt();
                    long j = buffer.data.getLong();
                    int i = buffer.data.getInt();
                    int i2 = buffer.data.getInt();
                    int i3 = buffer.data.getInt();
                    byte[] dataBytes = new byte[buffer.data.remaining()];
                    buffer.data.get(dataBytes);
                    android.os.Message msg = android.os.Message.obtain();
                    msg.what = 1;
                    msg.arg1 = cmd;
                    msg.arg2 = seq;
                    msg.obj = dataBytes;
                    f0.this.S.sendMessage(msg);
                }
            }
        }
    }

    private void t0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9781, new Class[0], Void.TYPE).isSupported) {
            try {
                if (!this.H.addTrack(this.q)) {
                    a("Add audio track failed");
                }
                if (this.H.audioTracks.size() > 0) {
                    this.y.addTrack(this.H.audioTracks.get(0), Collections.singletonList(this.H.getId()));
                    a("[媒体Track] 添加本地音频Track Sending audio track ");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void w0(MediaStream stream) {
        if (!PatchProxy.proxy(new Object[]{stream}, this, changeQuickRedirect, false, 9782, new Class[]{MediaStream.class}, Void.TYPE).isSupported) {
            List<AudioTrack> list = stream.audioTracks;
            AudioTrack audioTrack = (list == null || list.size() <= 0) ? null : stream.audioTracks.get(0);
            this.B = audioTrack;
            if (audioTrack != null) {
                audioTrack.setEnabled(false);
                a("[媒体流添加] 设置远程音频禁音 remoteAudioTrack received: State=" + this.B.state().name());
            }
        }
    }

    private void z2(int i2, int i3, byte[] bArr) {
        com.leedarson.smartcamera.listener.b bVar;
        char c2 = 3;
        char c3 = 0;
        char c4 = 1;
        int i4 = 2;
        Object[] objArr = {new Integer(i2), new Integer(i3), bArr};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9783, new Class[]{cls, cls, byte[].class}, Void.TYPE).isSupported) {
            int seq = i3;
            int resp = i2;
            byte[] data = bArr;
            char c5 = 5;
            switch (resp) {
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_RESP /*793*/:
                    if (this.s1 == seq) {
                        int byte2int = ByteUtil.byte2int(new byte[]{data[4], data[5], data[6], data[7]});
                        byte listEventEndFlag = data[9];
                        int listEventCount = data[10] & 255;
                        String[] thisPacketData = new String[listEventCount];
                        int i5 = 0;
                        while (i5 < listEventCount) {
                            byte[] bs = new byte[12];
                            System.arraycopy(data, (i5 * 12) + 12, bs, 0, 12);
                            short yearShow = ByteUtil.byteToShort(new byte[]{bs[0], bs[1]});
                            byte month = bs[2];
                            byte day = bs[c2];
                            byte hour = bs[c5];
                            byte minute = bs[6];
                            byte second = bs[7];
                            byte b2 = bs[8];
                            byte b3 = bs[9];
                            byte[] bArr2 = bs;
                            int seq2 = seq;
                            String time = (yearShow + "-" + month + "-" + day) + " " + String.format(Locale.US, "%02d:%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(second)});
                            thisPacketData[i5] = time;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
                            try {
                                SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
                                try {
                                    this.N.add(Long.valueOf(simpleDateFormat.parse(time).getTime()));
                                } catch (ParseException e2) {
                                    ee = e2;
                                }
                            } catch (ParseException e3) {
                                ee = e3;
                                SimpleDateFormat simpleDateFormat3 = simpleDateFormat;
                                ee.printStackTrace();
                                i5++;
                                seq = seq2;
                                c2 = 3;
                                c5 = 5;
                            }
                            i5++;
                            seq = seq2;
                            c2 = 3;
                            c5 = 5;
                        }
                        if (listEventEndFlag == 1 && (bVar = this.K) != null) {
                            bVar.onSuccess(this.N);
                            return;
                        }
                        return;
                    }
                    return;
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP /*795*/:
                    int commandResult = ByteUtil.byte2int(new byte[]{data[0], data[1], data[2], data[3]});
                    int playResult = ByteUtil.byte2int(new byte[]{data[4], data[5], data[6], data[7]});
                    com.leedarson.log.f.b("KVSWebRTCChannel", "IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP commandResult: " + commandResult);
                    com.leedarson.log.f.b("KVSWebRTCChannel", String.format(Locale.US, "sd resp: cmd=%s resultChannel=%d channel=%d", new Object[]{Integer.toHexString(commandResult), Integer.valueOf(playResult), Integer.valueOf(this.O)}));
                    if (commandResult == 16) {
                        this.A0 = true;
                        byte time2 = data[8];
                        com.leedarson.smartcamera.listener.j jVar = this.R;
                        if (jVar != null) {
                            jVar.b(time2);
                        }
                        int i6 = seq;
                        return;
                    } else if (commandResult == 0) {
                        this.A0 = false;
                        com.leedarson.smartcamera.listener.j jVar2 = this.R;
                        if (jVar2 != null) {
                            jVar2.a();
                            int i7 = seq;
                            return;
                        }
                        int i8 = seq;
                        return;
                    } else if (commandResult == 17) {
                        this.A0 = true;
                        com.leedarson.smartcamera.listener.j jVar3 = this.R;
                        if (jVar3 != null) {
                            jVar3.d();
                            int i9 = seq;
                            return;
                        }
                        int i10 = seq;
                        return;
                    } else if (commandResult == 7) {
                        this.A0 = false;
                        com.leedarson.smartcamera.listener.j jVar4 = this.R;
                        if (jVar4 != null) {
                            jVar4.e();
                            int i11 = seq;
                            return;
                        }
                        int i12 = seq;
                        return;
                    } else {
                        int i13 = seq;
                        return;
                    }
                case 796:
                    try {
                        this.B0 = System.currentTimeMillis();
                        ByteBuffer buffer = ByteBuffer.wrap(data);
                        buffer.order(ByteOrder.LITTLE_ENDIAN);
                        long pts = buffer.getLong();
                        long timestamp = buffer.getLong();
                        byte flag = buffer.get();
                        com.leedarson.log.f.b("KVSWebRTCChannel", "IOTYPE_USER_IPCAM_RECORD_TIMESTAMP_RESP: pts=" + pts + " timestamp=" + timestamp + "==" + flag);
                        com.leedarson.smartcamera.listener.j jVar5 = this.R;
                        if (jVar5 != null) {
                            long j2 = timestamp;
                            jVar5.c(timestamp, pts, flag);
                        }
                        int i14 = seq;
                        return;
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        int i15 = seq;
                        return;
                    }
                case 801:
                    com.leedarson.smartcamera.listener.k kVar = this.U;
                    if (kVar != null) {
                        kVar.onSuccess();
                        int i16 = seq;
                        return;
                    }
                    int i17 = seq;
                    return;
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSTREAMCTRL_RESP /*803*/:
                    com.leedarson.smartcamera.listener.c cVar = this.V;
                    if (cVar != null) {
                        cVar.onSuccess(data[4]);
                        int i18 = seq;
                        return;
                    }
                    int i19 = seq;
                    return;
                case 804:
                    LdsTrackSwitchNotifyBean trackNotifyBean = new LdsTrackSwitchNotifyBean(data);
                    a("[TrackId设置] DataChannel请求设置TrackId =" + trackNotifyBean.getTrackId());
                    this.L0.k(trackNotifyBean.getTrackId());
                    com.leedarson.smartcamera.logreport.c.b().e(this.m0, this.h, "IIOTYPE_USER_TYPE_NOTIFY_TRACK_REQ", " data: " + com.leedarson.base.utils.e.a(data));
                    int i20 = seq;
                    return;
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SPEAKERSTART_WEB_RTC_RESP /*851*/:
                    if (data.length > 1) {
                        int lo = data[1] & 255;
                        if (lo == 100) {
                            this.I0.onNext(new kotlin.n(Integer.valueOf(lo), "开启对讲失败,固件麦克风被占用"));
                        }
                        int i21 = seq;
                        return;
                    }
                    int i22 = seq;
                    return;
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HAS_SDCARD_RESP /*1204*/:
                    if (this.j0 == null || data.length < 15) {
                        int i23 = seq;
                        return;
                    }
                    JSONArray outArry = new JSONArray();
                    if (data[0] == 1) {
                        outArry.put(true);
                    } else {
                        outArry.put(false);
                    }
                    byte[] totalM = {data[1], data[2], data[3], data[4]};
                    outArry.put(ByteUtil.byte2int(totalM));
                    totalM[0] = data[5];
                    totalM[1] = data[6];
                    totalM[2] = data[7];
                    totalM[3] = data[8];
                    outArry.put(ByteUtil.byte2int(new byte[4]));
                    outArry.put((int) data[9]);
                    outArry.put((int) data[10]);
                    this.j0.onSuccess(outArry.toString());
                    int i24 = seq;
                    return;
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_RESP /*1206*/:
                    if (this.r1 == seq) {
                        int totalTime = ByteUtil.byte2int(new byte[]{data[4], data[5], data[6], data[7]});
                        byte endFlag = data[9];
                        int count = data[10] & 255;
                        a("getSDTimeList_count: " + count + " totalTime: " + totalTime + " endFlag: " + endFlag);
                        if (data.length > 12) {
                            byte[] curData = new byte[count];
                            System.arraycopy(data, 12, curData, 0, count);
                            this.M = ByteUtil.byteMerger(this.M, curData);
                        }
                        if (endFlag == 1) {
                            com.leedarson.smartcamera.listener.e eVar = this.J;
                            if (eVar != null) {
                                eVar.onSuccess(this.M);
                                int i25 = seq;
                                return;
                            }
                            int i26 = seq;
                            return;
                        }
                        int i27 = seq;
                        return;
                    }
                    return;
                case AVIOCTRLDEFs.IIOTYPE_USER_IPCAM_DELLISTEVENT_RESP /*1208*/:
                    com.leedarson.smartcamera.listener.a aVar = this.L;
                    if (aVar == null) {
                        int i28 = seq;
                        return;
                    } else if (data[0] == 0) {
                        aVar.onSuccess();
                        int i29 = seq;
                        return;
                    } else {
                        aVar.a(0);
                        int i30 = seq;
                        return;
                    }
                case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_RESP /*1210*/:
                    if (this.W != null) {
                        int byte2int2 = ByteUtil.byte2int(new byte[]{data[0], data[1], data[2], data[3]});
                        long timesta = ByteUtil.byte2long(new byte[]{data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11]});
                        byte[] imgBytes = new byte[(data.length - 12)];
                        System.arraycopy(data, 12, imgBytes, 0, data.length - 12);
                        this.W.b(timesta, imgBytes);
                        int i31 = seq;
                        return;
                    }
                    int i32 = seq;
                    return;
                case AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_START_RESP /*1321*/:
                    a("[雷达信息] 收到实时雷达开启响应 data=" + com.leedarson.base.utils.e.a(data));
                    com.leedarson.smartcamera.listener.l lVar = this.X;
                    if (lVar != null) {
                        byte code = data[0];
                        if (code == 0) {
                            lVar.onSuccess();
                        } else {
                            lVar.a(code);
                        }
                        int i33 = seq;
                        return;
                    }
                    int i34 = seq;
                    return;
                case AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_STOP_RESP /*1323*/:
                    a("收到实时雷达关闭响应 data=" + com.leedarson.base.utils.e.a(data));
                    com.leedarson.smartcamera.listener.m mVar = this.Y;
                    if (mVar != null) {
                        byte code2 = data[0];
                        if (code2 == 0) {
                            mVar.onSuccess();
                        } else {
                            mVar.a(code2);
                        }
                        int i35 = seq;
                        return;
                    }
                    int i36 = seq;
                    return;
                case AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_REPORT /*1324*/:
                    if (this.Z != null) {
                        byte[] timeByte = new byte[8];
                        for (int i37 = 0; i37 < 8; i37++) {
                            timeByte[i37] = data[i37];
                        }
                        long timestamp2 = ByteUtil.byte2long(timeByte);
                        int number = ByteUtil.byte2int(new byte[]{data[8], data[9], data[10], data[11]});
                        int startPointPos = 12;
                        List<LdsRadarData> datas = new ArrayList<>();
                        int i38 = 0;
                        while (i38 < number) {
                            int startPointPos2 = startPointPos + 1;
                            byte startPointPos3 = data[startPointPos];
                            int startPointPos4 = startPointPos2 + 1;
                            byte startPointPos5 = data[startPointPos2];
                            byte[] bArr3 = new byte[i4];
                            int startPointPos6 = startPointPos4 + 1;
                            bArr3[c3] = data[startPointPos4];
                            int startPointPos7 = startPointPos6 + 1;
                            bArr3[c4] = data[startPointPos6];
                            int x2 = ByteUtil.byteToShort(bArr3);
                            byte[] bArr4 = new byte[i4];
                            int startPointPos8 = startPointPos7 + 1;
                            bArr4[c3] = data[startPointPos7];
                            int startPointPos9 = startPointPos8 + 1;
                            bArr4[c4] = data[startPointPos8];
                            int y2 = ByteUtil.byteToShort(bArr4);
                            byte[] bArr5 = new byte[i4];
                            int startPointPos10 = startPointPos9 + 1;
                            bArr5[c3] = data[startPointPos9];
                            int startPointPos11 = startPointPos10 + 1;
                            bArr5[1] = data[startPointPos10];
                            int z2 = ByteUtil.byteToShort(bArr5);
                            byte[] bArr6 = new byte[i4];
                            int startPointPos12 = startPointPos11 + 1;
                            bArr6[0] = data[startPointPos11];
                            int startPointPos13 = startPointPos12 + 1;
                            bArr6[1] = data[startPointPos12];
                            int xSpeed = ByteUtil.byteToShort(bArr6);
                            int number2 = number;
                            byte[] bArr7 = new byte[i4];
                            int startPointPos14 = startPointPos13 + 1;
                            bArr7[0] = data[startPointPos13];
                            int startPointPos15 = startPointPos14 + 1;
                            bArr7[1] = data[startPointPos14];
                            int ySpeed = ByteUtil.byteToShort(bArr7);
                            int resp2 = resp;
                            byte[] bArr8 = new byte[i4];
                            int startPointPos16 = startPointPos15 + 1;
                            bArr8[0] = data[startPointPos15];
                            bArr8[1] = data[startPointPos16];
                            com.leedarson.smartcamera.kvswebrtc.beans.a ldsRadarData = new com.leedarson.smartcamera.kvswebrtc.beans.a(timestamp2, startPointPos3, x2, y2, z2, xSpeed, ySpeed, ByteUtil.byteToShort(bArr8));
                            ldsRadarData.c = startPointPos5;
                            datas.add(ldsRadarData);
                            i38++;
                            number = number2;
                            startPointPos = startPointPos16 + 1;
                            resp = resp2;
                            c3 = 0;
                            c4 = 1;
                            i4 = 2;
                        }
                        int i39 = resp;
                        if (this.Z != null && datas.size() > 0) {
                            this.Z.a(0, datas);
                        }
                        int i40 = seq;
                        return;
                    }
                    int i41 = seq;
                    return;
                case AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_SDCARD_START_RESP /*1327*/:
                    a("收到sdcard雷达数据上报,字节数:" + data.length);
                    i0 i0Var = this.a0;
                    if (i0Var != null) {
                        i0Var.b(data, this.Z);
                        int i42 = seq;
                        int i43 = resp;
                        return;
                    }
                    int i44 = seq;
                    int i45 = resp;
                    return;
                case AVIOCTRLDEFs.E_CMD_AVIO_CTRL_SESSION_MODE_RESP /*5377*/:
                    a("[流模式切换] 收到设备响应 收到流类型的切换回执   data=" + com.leedarson.base.utils.e.a(data));
                    com.leedarson.smartcamera.logreport.c.b().e(this.m0, this.h, "E_CMD_AVIO_CTRL_SESSION_MODE_RESP", "data: " + com.leedarson.base.utils.e.a(data));
                    int i46 = seq;
                    int i47 = resp;
                    return;
                default:
                    int i48 = seq;
                    int i49 = resp;
                    return;
            }
        }
    }

    public void E2(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9784, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            A2(20, speed);
        }
    }

    public void F2(int speed) {
        if (!PatchProxy.proxy(new Object[]{new Integer(speed)}, this, changeQuickRedirect, false, 9785, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            A2(0, speed);
        }
    }

    public void G2(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9786, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            A2(1, speed);
        }
    }

    public void B2(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9787, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            A2(2, speed);
        }
    }

    public void C2(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9788, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            A2(3, speed);
        }
    }

    public void D2(int speed) {
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9789, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            A2(6, speed);
        }
    }

    public void A2(int control, int speed) {
        Object[] objArr = {new Integer(control), new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9790, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            X2(4097, AVIOCTRLDEFs.SMsgAVIoctrlPtzCmd.parseContent((byte) control, (byte) speed, (byte) 0, (byte) 0, (byte) 0, (byte) 0));
        }
    }

    public void n1(List<Long> recordTimestamps, com.leedarson.smartcamera.listener.d thumbnaiRespListener) {
        if (!PatchProxy.proxy(new Object[]{recordTimestamps, thumbnaiRespListener}, this, changeQuickRedirect, false, 9791, new Class[]{List.class, com.leedarson.smartcamera.listener.d.class}, Void.TYPE).isSupported) {
            this.W = thumbnaiRespListener;
            int i2 = this.x1 + 1;
            this.x1 = i2;
            if (i2 > 12) {
                this.x1 = 0;
            }
            byte[] getBytes = new byte[((recordTimestamps.size() * 8) + 4)];
            System.arraycopy(Packet.intToByteArray_Little(this.x1), 0, getBytes, 0, 4);
            for (int i3 = 0; i3 < recordTimestamps.size(); i3++) {
                System.arraycopy(Packet.longToByteArray_Little(recordTimestamps.get(i3).longValue()), 0, getBytes, (i3 * 8) + 4, 8);
            }
            X2(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ, getBytes);
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class s extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        public s(Looper mainLooper) {
            super(mainLooper);
        }

        public void handleMessage(android.os.Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9907, new Class[]{android.os.Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        try {
                            f0.b0(f0.this, msg.arg1, msg.arg2, (byte[]) msg.obj);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    case 2:
                        f0.s(f0.this, "handleMessage: PEERCON_TIMEOUT");
                        if (f0.this.G != null) {
                            f0.this.G.onError("媒体协商、P2P对点网络创建超时");
                            return;
                        }
                        return;
                    case 3:
                        if (f0.this.m1 == null) {
                            return;
                        }
                        if (msg.arg1 == 1) {
                            f0.this.m1.onSuccess();
                            return;
                        } else {
                            f0.this.m1.a(-1);
                            return;
                        }
                    case 4:
                        if (f0.this.G != null) {
                            f0.this.G.b();
                            return;
                        }
                        return;
                    case 6:
                    case 16:
                        try {
                            com.leedarson.log.f.a("ONCONNECTED: " + f0.this.h1 + "=" + f0.this.t1() + "=" + f0.this.r1() + "  kvsChannel=" + f0.this.toString());
                            WebRtcLogStepBean stepBean = new WebRtcLogStepBean(WebRtcReporterV3.h, 200);
                            StringBuilder eventLogs = new StringBuilder();
                            String dataChannelStatue = "";
                            if (f0.this.I != null) {
                                dataChannelStatue = f0.this.I.state().toString();
                            }
                            eventLogs.append("事件回调:" + msg.what + " ,isPeerConnection=" + f0.this.t1() + ",  isDataChannel=" + f0.this.r1() + ", dataChannelStatue=" + dataChannelStatue);
                            stepBean.responseParams = eventLogs.toString();
                            if (f0.this.t1()) {
                                if (f0.this.r1()) {
                                    if (f0.this.c1 != null) {
                                        f0.this.c1.onConnected();
                                        stepBean.endTrace("WebRTC通路(OK)", 200);
                                        return;
                                    }
                                    eventLogs.append("_mWebRtcConnection 为空");
                                    stepBean.endTrace("WebRTC通路(UnReady)", -2019);
                                    return;
                                }
                            }
                            stepBean.endTrace("WebRTC通路(UnReady)", -2019);
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    case 7:
                        try {
                            com.leedarson.smartcamera.listener.n nVar = f0.this.p1;
                            if (nVar != null) {
                                nVar.onSuccess();
                                return;
                            }
                            return;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            return;
                        }
                    case 8:
                        try {
                            com.leedarson.smartcamera.listener.n nVar2 = f0.this.q1;
                            if (nVar2 != null) {
                                nVar2.onSuccess();
                                return;
                            }
                            return;
                        } catch (Exception e4) {
                            e4.printStackTrace();
                            return;
                        }
                    case 9:
                        com.leedarson.log.f.a(" WEBRTC_RE_PEERCON reConnectIndex: ");
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void initWebSocketClient(com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9792, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.c.class}, Void.TYPE).isSupported) {
            WebRtcServiceStateChangeLogBean connectLogBean = new WebRtcServiceStateChangeLogBean();
            connectLogBean.requestTime = System.currentTimeMillis();
            JSONObject requestObj = new JSONObject();
            connectLogBean.requestObj = requestObj;
            try {
                requestObj.put("awsAccessKey", (Object) "" + this.b);
                requestObj.put("awsSecretKey", (Object) "" + this.c);
                requestObj.put("sessionToken", (Object) "" + this.d);
                requestObj.put("channelArn", (Object) "" + this.e);
                requestObj.put("region", (Object) "" + this.f);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (this.w == null) {
                this.w = new com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n();
            }
            this.w.t(this.m0);
            this.w.v(this.h);
            WebRtcLogStepBean _signalSocketCreateStep = new WebRtcLogStepBean("SIGNAL_CONNECT", 200);
            WebRtcReporterV3.v(this.h, this.m0).K(_signalSocketCreateStep);
            this.w.s(new a(_signalSocketCreateStep, connectLogBean, listener));
            com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
            String str = this.m0;
            String str2 = this.h;
            b2.e(str, str2, "kvswebsocket", "isOpen:" + this.w.k() + " host:" + this.v);
            if (this.w.k()) {
                if (listener != null) {
                    listener.b();
                    connectLogBean.rsponseTime = System.currentTimeMillis();
                    connectLogBean.endResponseObj = new JSONObject();
                    this.D0.onNext(connectLogBean);
                }
            } else if (this.v.isEmpty()) {
                b bVar = new b(listener, connectLogBean);
                this.s0 = bVar;
                Y0(bVar);
            } else {
                com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n nVar = this.w;
                if (nVar != null) {
                    nVar.u(this);
                    this.w.i(this.v);
                }
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ WebRtcLogStepBean a;
        final /* synthetic */ WebRtcServiceStateChangeLogBean b;
        final /* synthetic */ com.leedarson.smartcamera.kvswebrtc.signaling.c c;

        a(WebRtcLogStepBean webRtcLogStepBean, WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean, com.leedarson.smartcamera.kvswebrtc.signaling.c cVar) {
            this.a = webRtcLogStepBean;
            this.b = webRtcServiceStateChangeLogBean;
            this.c = cVar;
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9852, new Class[0], Void.TYPE).isSupported) {
                this.a.endTrace("KVS信令通道创建成功", 200);
                com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "kvswebsocket", "KVS.WebSocket onOpen");
                this.b.endTraceSuccess(new JSONObject());
                f0.this.D0.onNext(this.b);
                f0.s(f0.this, "webSocket onOpen: ");
                com.leedarson.smartcamera.kvswebrtc.signaling.c cVar = this.c;
                if (cVar != null) {
                    cVar.b();
                }
            }
        }

        public void e(String closeReason) {
            if (!PatchProxy.proxy(new Object[]{closeReason}, this, changeQuickRedirect, false, 9853, new Class[]{String.class}, Void.TYPE).isSupported) {
                WebRtcLogStepBean webRtcLogStepBean = this.a;
                webRtcLogStepBean.responseParams = closeReason;
                webRtcLogStepBean.endTrace("KVS信令通道被关闭", -2016);
                f0 f0Var = f0.this;
                WebRtcReporterV3.v(f0Var.h, f0Var.m0).L("KVS WebSocket断开");
                com.leedarson.smartcamera.logreport.c.b().e(f0.this.m0, f0.this.h, "kvswebsocket", "WebRTC.SOCKET onClose");
                f0 f0Var2 = f0.this;
                f0.s(f0Var2, "webSocket onClose: " + closeReason);
                JSONObject tempObj = new JSONObject();
                WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean = this.b;
                webRtcServiceStateChangeLogBean.putData(tempObj, "reaseon", "webSocket Close" + closeReason);
                this.b.endTraceExcept(BaseResp.ERR_MSG_SEND_FAIL, tempObj);
                f0.this.D0.onNext(this.b);
                com.leedarson.smartcamera.kvswebrtc.signaling.c cVar = this.c;
                if (cVar != null) {
                    cVar.onClose();
                }
                f0.j(f0.this);
            }
        }

        /* renamed from: com.leedarson.smartcamera.kvswebrtc.f0$a$a  reason: collision with other inner class name */
        /* compiled from: KVSWebRTCChannel */
        public class C0171a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ Event c;

            C0171a(Event event) {
                this.c = event;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9861, new Class[0], Void.TYPE).isSupported) {
                    try {
                        String sdp = Event.parseOfferEvent(this.c);
                        f0.this.y.setRemoteDescription(f0.this.G1, new SessionDescription(SessionDescription.Type.OFFER, sdp));
                        String unused = f0.this.u = this.c.getSenderClientId();
                        f0 f0Var = f0.this;
                        f0.s(f0Var, "onSdpOffer  Received SDP offer for client ID: " + f0.this.u + ".Creating answer    \n onSdpOffer.sdp=" + sdp);
                        com.leedarson.smartcamera.logreport.c b = com.leedarson.smartcamera.logreport.c.b();
                        String f = f0.this.m0;
                        String str = f0.this.h;
                        b.e(f, str, "kvswebsocket", "onSdpOffer  Received SDP offer for client ID: " + f0.this.u + ".Creating answer    \n onSdpOffer.sdp=" + sdp);
                        com.leedarson.smartcamera.kvswebrtc.signaling.c cVar = a.this.c;
                        if (cVar != null) {
                            cVar.e("sdpOffer--->" + sdp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void d(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 9854, new Class[]{Event.class}, Void.TYPE).isSupported) {
                f0.s(f0.this, "onSdpOffer: ");
                f0.this.D.b(new C0171a(event));
            }
        }

        public void c(Event answerEvent) {
            if (!PatchProxy.proxy(new Object[]{answerEvent}, this, changeQuickRedirect, false, 9855, new Class[]{Event.class}, Void.TYPE).isSupported) {
                f0.this.D.b(new a(this, answerEvent, this.c));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: i */
        public /* synthetic */ void j(Event answerEvent, com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
            if (!PatchProxy.proxy(new Object[]{answerEvent, listener}, this, changeQuickRedirect, false, 9860, new Class[]{Event.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class}, Void.TYPE).isSupported) {
                try {
                    String sdp = Event.parseSdpEvent(answerEvent);
                    if (answerEvent.getPskEnable()) {
                        f0 f0Var = f0.this;
                        sdp = f0.j0(f0Var, sdp, f0Var.m0, f0.this.x0);
                    }
                    f0 f0Var2 = f0.this;
                    f0.s(f0Var2, "[视频流Track]  onSdpAnswer - 视频流Track变化 --> 当前要设置的trackId=" + answerEvent.getTrackId());
                    f0.this.L0.k(answerEvent.getTrackId());
                    SessionDescription sdpAnswer = new SessionDescription(SessionDescription.Type.ANSWER, sdp);
                    f0 f0Var3 = f0.this;
                    f0.s(f0Var3, "[媒体协商] 设置远端sdp信息--》" + sdp + "  senderClientId=" + answerEvent.getSenderClientId());
                    f0.this.y.setRemoteDescription(f0.this.G1, sdpAnswer);
                    StringBuilder sb = new StringBuilder();
                    sb.append("LSD.WEBRTC Answer Client ID: ");
                    sb.append(answerEvent.getSenderClientId());
                    timber.log.a.c(sb.toString(), new Object[0]);
                    f0.this.E.put(answerEvent.getSenderClientId(), f0.this.y);
                    f0.l0(f0.this, answerEvent.getSenderClientId());
                    f0.j(f0.this);
                    com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
                    String f = f0.this.m0;
                    String str = f0.this.h;
                    b2.e(f, str, "kvswebsocket", "onSdpAnswer: sdp=" + sdp + "\n    Answer Client ID: " + answerEvent.getSenderClientId());
                    if (listener != null) {
                        listener.c("onSdpAnswer  sdp=" + sdp + "    Answer Client ID: " + answerEvent.getSenderClientId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void f(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 9856, new Class[]{Event.class}, Void.TYPE).isSupported) {
                f0.s(f0.this, "onIceCandidate: ");
                f0.this.D.b(new b(this, event, this.c));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: g */
        public /* synthetic */ void h(Event event, com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
            Class[] clsArr = {Event.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class};
            if (!PatchProxy.proxy(new Object[]{event, listener}, this, changeQuickRedirect, false, 9859, clsArr, Void.TYPE).isSupported) {
                try {
                    WebRtcLogStepBean _stepOfCandidate = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_CENDIDITE_RECEIVED, 200);
                    _stepOfCandidate.desc = "候选人交换:(设备==>App)";
                    f0 f0Var = f0.this;
                    WebRtcReporterV3.v(f0Var.h, f0Var.m0).K(_stepOfCandidate);
                    IceCandidate iceCandidate = Event.parseIceCandidate(event);
                    if (iceCandidate != null) {
                        f0.i0(f0.this, event.getSenderClientId(), iceCandidate);
                        if (listener != null) {
                            try {
                                listener.g("onIceCandidate:" + new Gson().toJson((Object) iceCandidate));
                                _stepOfCandidate.responseParams = new Gson().toJson((Object) iceCandidate);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            _stepOfCandidate.endTrace("候选人交换:(设备==>App)", 200);
                        }
                        return;
                    }
                    f0.F(f0.this, "Invalid Ice candidate");
                    if (listener != null) {
                        listener.onException(new Exception("Invalid Ice candidate 获得了一个空的候选人"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 9857, new Class[]{Event.class}, Void.TYPE).isSupported) {
                WebRtcLogStepBean webRtcLogStepBean = this.a;
                webRtcLogStepBean.responseParams = "KVS信令通道创建发生错误:" + event.getBody();
                webRtcLogStepBean.endTrace("KVS信令通道发生错误", -2017);
                f0 f0Var = f0.this;
                WebRtcReporterV3.v(f0Var.h, f0Var.m0).L("KVS信令通道发生错误");
                this.b.rsponseTime = System.currentTimeMillis();
                this.b.code = BaseResp.ERR_MSG_SEND_FAIL;
                JSONObject connectResponseObj = new JSONObject();
                try {
                    connectResponseObj.put("eventData", (Object) new Gson().toJson((Object) event));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean = this.b;
                webRtcServiceStateChangeLogBean.endResponseObj = connectResponseObj;
                f0.this.D0.onNext(webRtcServiceStateChangeLogBean);
                com.leedarson.smartcamera.kvswebrtc.signaling.c cVar = this.c;
                if (cVar != null) {
                    cVar.a(event);
                }
                f0.j(f0.this);
            }
        }

        public void onException(Exception e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 9858, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                String tipOfException = "KVS信令通道创建发生异常:" + e.toString();
                WebRtcLogStepBean webRtcLogStepBean = this.a;
                webRtcLogStepBean.responseParams = tipOfException;
                webRtcLogStepBean.endTrace("KVS信令通道发生异常", -2018);
                f0 f0Var = f0.this;
                WebRtcReporterV3.v(f0Var.h, f0Var.m0).L("KVS信令通道发生异常");
                f0.s(f0.this, tipOfException);
                this.b.rsponseTime = System.currentTimeMillis();
                this.b.code = BaseResp.ERR_MSG_SEND_FAIL;
                try {
                    new JSONObject().put("eventData", (Object) "KVS 自研websocket 信令服务器连接出现异常" + e.toString());
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                f0.this.D0.onNext(this.b);
                com.leedarson.smartcamera.kvswebrtc.signaling.c cVar = this.c;
                if (cVar != null) {
                    cVar.onException(e);
                }
                f0.this.v = "";
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class b implements e0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.smartcamera.kvswebrtc.signaling.c a;
        final /* synthetic */ WebRtcServiceStateChangeLogBean b;

        b(com.leedarson.smartcamera.kvswebrtc.signaling.c cVar, WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean) {
            this.a = cVar;
            this.b = webRtcServiceStateChangeLogBean;
        }

        public void onSuccess(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 9862, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (f0.this.w != null) {
                    f0.this.w.u(f0.this);
                    f0.this.w.i(f0.this.v);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
            r0 = r8;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFail(java.lang.String r9) {
            /*
                r8 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r2 = 0
                r1[r2] = r9
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.String> r0 = java.lang.String.class
                r6[r2] = r0
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 9863(0x2687, float:1.3821E-41)
                r2 = r8
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x001d
                return
            L_0x001d:
                r0 = r8
                com.leedarson.smartcamera.kvswebrtc.signaling.c r1 = r0.a
                if (r1 == 0) goto L_0x0046
                java.lang.Exception r2 = new java.lang.Exception
                r2.<init>(r9)
                r1.onException(r2)
                org.json.JSONObject r1 = new org.json.JSONObject
                r1.<init>()
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r2 = r0.b
                java.lang.String r3 = "desc"
                r2.putData(r1, r3, r9)
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r2 = r0.b
                r3 = 400(0x190, float:5.6E-43)
                r2.endTraceExcept(r3, r1)
                com.leedarson.smartcamera.kvswebrtc.f0 r2 = com.leedarson.smartcamera.kvswebrtc.f0.this
                io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r2 = r2.D0
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r3 = r0.b
                r2.onNext(r3)
            L_0x0046:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.b.onFail(java.lang.String):void");
        }
    }

    private void initMQTTClient(com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 9793, new Class[]{com.leedarson.smartcamera.kvswebrtc.signaling.c.class}, Void.TYPE).isSupported) {
            if (this.l0 == null) {
                com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o oVar = new com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o();
                this.l0 = oVar;
                oVar.setmWebRtcConnectListerner(listener);
                this.l0.f = this.D1;
            }
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.o oVar2 = this.l0;
            if (oVar2 != null) {
                oVar2.s(new t(this));
            }
            a("[初始化MqttClient] initMQTTClient liveType: " + this.g);
            if (Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.g)) {
                WebRtcServiceStateChangeLogBean connectLogBean = new WebRtcServiceStateChangeLogBean();
                connectLogBean.requestTime = System.currentTimeMillis();
                JSONObject requestObj = new JSONObject();
                connectLogBean.requestObj = requestObj;
                try {
                    requestObj.put("awsAccessKey", (Object) "" + this.b);
                    requestObj.put("awsSecretKey", (Object) "" + this.c);
                    requestObj.put("sessionToken", (Object) "" + this.d);
                    requestObj.put("channelArn", (Object) "" + this.e);
                    requestObj.put("region", (Object) "" + this.f);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Y0(new c(listener, connectLogBean));
            } else {
                a("[初始化自研客户端] isPeerConnectionConnect: " + t1() + " isDataChannelConnected: " + r1());
                if (!r1()) {
                    if (this.l0 != null) {
                        this.m.clear();
                        this.l0.t(this.h);
                        this.l0.h(this.m0, this.m, listener, this.g);
                    }
                } else {
                    return;
                }
            }
            this.l0.r(new d(listener));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: P1 */
    public /* synthetic */ void Q1(int code, String desc, WebRtcLogStepBean webRtcLogStepBean, String peerid) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(code), desc, webRtcLogStepBean, peerid};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9818, new Class[]{Integer.TYPE, cls, WebRtcLogStepBean.class, cls}, Void.TYPE).isSupported) {
            b("[媒体协商超时] 预连接发生了异常,导致直播无法播放...... desc=" + desc);
            if (!peerid.contains(this.h)) {
                b("[媒体协商超时]  发现与当前的clientId不一致，其过了时效性--- currentPeerId=" + this.h + "  callBackId=" + peerid);
            } else if (code == -31007502) {
                y0();
            } else {
                com.leedarson.smartcamera.kvswebrtc.signaling.c cVar = this.c1;
                if (cVar != null) {
                    cVar.d(code);
                    r3();
                }
            }
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class c implements e0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.smartcamera.kvswebrtc.signaling.c a;
        final /* synthetic */ WebRtcServiceStateChangeLogBean b;

        c(com.leedarson.smartcamera.kvswebrtc.signaling.c cVar, WebRtcServiceStateChangeLogBean webRtcServiceStateChangeLogBean) {
            this.a = cVar;
            this.b = webRtcServiceStateChangeLogBean;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
            r0 = r9;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onSuccess(java.lang.String r10) {
            /*
                r9 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r10
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.String> r0 = java.lang.String.class
                r6[r8] = r0
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 9864(0x2688, float:1.3822E-41)
                r2 = r9
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x001d
                return
            L_0x001d:
                r0 = r9
                com.leedarson.smartcamera.kvswebrtc.signaling.c r1 = r0.a
                if (r1 == 0) goto L_0x004e
                r1.b()
                com.leedarson.smartcamera.kvswebrtc.f0 r1 = com.leedarson.smartcamera.kvswebrtc.f0.this
                boolean unused = r1.v0 = r8
                org.json.JSONObject r1 = new org.json.JSONObject
                r1.<init>()
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r2 = r0.b
                long r3 = java.lang.System.currentTimeMillis()
                r2.rsponseTime = r3
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r2 = r0.b
                r2.endResponseObj = r1
                java.lang.String r2 = "host"
                r1.put((java.lang.String) r2, (java.lang.Object) r10)     // Catch:{ JSONException -> 0x0041 }
                goto L_0x0045
            L_0x0041:
                r2 = move-exception
                r2.printStackTrace()
            L_0x0045:
                com.leedarson.smartcamera.kvswebrtc.f0 r2 = com.leedarson.smartcamera.kvswebrtc.f0.this
                io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r2 = r2.D0
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r3 = r0.b
                r2.onNext(r3)
            L_0x004e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.c.onSuccess(java.lang.String):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
            r0 = r8;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFail(java.lang.String r9) {
            /*
                r8 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r2 = 0
                r1[r2] = r9
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.String> r0 = java.lang.String.class
                r6[r2] = r0
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 9865(0x2689, float:1.3824E-41)
                r2 = r8
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x001d
                return
            L_0x001d:
                r0 = r8
                com.leedarson.smartcamera.kvswebrtc.signaling.c r1 = r0.a
                if (r1 == 0) goto L_0x004b
                java.lang.Exception r2 = new java.lang.Exception
                r2.<init>(r9)
                r1.onException(r2)
                org.json.JSONObject r1 = new org.json.JSONObject
                r1.<init>()
                java.lang.String r2 = "errorMessage"
                java.lang.String r3 = "desc"
                r1.put((java.lang.String) r2, (java.lang.Object) r3)     // Catch:{ JSONException -> 0x0037 }
                goto L_0x003b
            L_0x0037:
                r2 = move-exception
                r2.printStackTrace()
            L_0x003b:
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r2 = r0.b
                r3 = 400(0x190, float:5.6E-43)
                r2.endTraceExcept(r3, r1)
                com.leedarson.smartcamera.kvswebrtc.f0 r2 = com.leedarson.smartcamera.kvswebrtc.f0.this
                io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r2 = r2.D0
                com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r3 = r0.b
                r2.onNext(r3)
            L_0x004b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.c.onFail(java.lang.String):void");
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class d implements com.leedarson.smartcamera.kvswebrtc.signaling.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.smartcamera.kvswebrtc.signaling.c a;

        d(com.leedarson.smartcamera.kvswebrtc.signaling.c cVar) {
            this.a = cVar;
        }

        public void c(Event answerEvent) {
            if (!PatchProxy.proxy(new Object[]{answerEvent}, this, changeQuickRedirect, false, 9866, new Class[]{Event.class}, Void.TYPE).isSupported) {
                f0 f0Var = f0.this;
                f0.s(f0Var, "[媒体协商] 收到回执 onSdpAnswer supportRtpExt: " + answerEvent.getSupportRtpExt());
                if (f0.this.y == null) {
                    f0 f0Var2 = f0.this;
                    f0.F(f0Var2, "[媒体协商] 收到sdpAnswer,但是localPeer处于被回收状态   localPeer=" + f0.this.y);
                    return;
                }
                f0.this.D.b(new c(this, answerEvent, this.a));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b(Event answerEvent, com.leedarson.smartcamera.kvswebrtc.signaling.c listener) {
            Class[] clsArr = {Event.class, com.leedarson.smartcamera.kvswebrtc.signaling.c.class};
            if (!PatchProxy.proxy(new Object[]{answerEvent, listener}, this, changeQuickRedirect, false, 9867, clsArr, Void.TYPE).isSupported) {
                try {
                    String senderClientId = answerEvent.getSenderClientId();
                    if (!senderClientId.equals(f0.this.h)) {
                        f0 f0Var = f0.this;
                        f0.F(f0Var, "[媒体协商]  收到来自管道的mqtt 回执但消息已不可用（因为当前client与answer不一致）answerClient=" + senderClientId + " currentPeerId=" + f0.this.h);
                        return;
                    }
                    int unused = f0.this.N0 = answerEvent.getSupportRtpExt();
                    String sdp = answerEvent.getSdp();
                    if (answerEvent.getPskEnable()) {
                        f0 f0Var2 = f0.this;
                        sdp = f0.j0(f0Var2, sdp, f0Var2.m0, f0.this.x0);
                    }
                    f0 f0Var3 = f0.this;
                    f0.s(f0Var3, "[TrackId设置] 根据Answer回执设置-LDSMqtt TrackId =" + answerEvent.getTrackId());
                    f0.this.L0.k(answerEvent.getTrackId());
                    f0.this.y.setRemoteDescription(f0.this.G1, new SessionDescription(SessionDescription.Type.ANSWER, sdp));
                    f0 f0Var4 = f0.this;
                    f0.s(f0Var4, "[媒体协商] 设置接收到的 Answer Client ID:: " + senderClientId);
                    f0.this.E.put(answerEvent.getSenderClientId(), f0.this.y);
                    f0.l0(f0.this, answerEvent.getSenderClientId());
                    f0.j(f0.this);
                    if (listener != null) {
                        listener.c("onSdpAnswer  sdp=" + sdp + "    Answer Client ID: " + answerEvent.getSenderClientId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void u2(JSONObject data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 9794, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            WebRtcLogStepBean _iceCandiditeReceiveStep = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_CENDIDITE_RECEIVED, 200);
            if (this.y == null && this.h == null) {
                a("当前localPeer&&mClientId都为空，已不需要此类消息...进行消息拦截....");
            } else {
                this.D.b(new p(this, data, _iceCandiditeReceiveStep));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: U1 */
    public /* synthetic */ void V1(JSONObject data, WebRtcLogStepBean _iceCandiditeReceiveStep) {
        if (!PatchProxy.proxy(new Object[]{data, _iceCandiditeReceiveStep}, this, changeQuickRedirect, false, 9817, new Class[]{JSONObject.class, WebRtcLogStepBean.class}, Void.TYPE).isSupported) {
            try {
                IceCandidateEventBean.ExtensionsBean.PayloadBean payLoadBean = (IceCandidateEventBean.ExtensionsBean.PayloadBean) com.leedarson.base.utils.m.a(data.toString(), IceCandidateEventBean.ExtensionsBean.PayloadBean.class);
                String str = payLoadBean.peerid;
                if (str != null && !org.apache.http.util.j.c(str)) {
                    a("[候选人交换] 收到来自于mqtt通道的远程候选人信息 onIceCandidateReceived: " + data);
                    String peerId = payLoadBean.peerid;
                    if (!org.apache.http.util.j.c(this.h) && this.h.equals(peerId)) {
                        com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
                        String str2 = this.m0;
                        String str3 = this.h;
                        b2.e(str2, str3, "lds-received-ice", "Received ICE:" + data.toString());
                        _iceCandiditeReceiveStep.endTrace("交换候选人(收到回执)", 200);
                        _iceCandiditeReceiveStep.responseParams = data.toString();
                        WebRtcReporterV3.v(this.h, this.m0).K(_iceCandiditeReceiveStep);
                        D0(this.h, new IceCandidate("0", 0, payLoadBean.candidate.candidate));
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x036a  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x036d  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x03a2  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x040b  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0417  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0433  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x04c5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b1(com.leedarson.smartcamera.kvswebrtc.e0 r23, java.lang.String r24) {
        /*
            r22 = this;
            java.lang.String r0 = "获取KVSiceServerList为空"
            java.lang.String r1 = "Endpoints "
            java.lang.String r2 = "HTTPS"
            java.lang.String r3 = "WSS"
            java.lang.String r4 = "region"
            java.lang.String r5 = "iceServerList"
            r6 = 2
            java.lang.Object[] r7 = new java.lang.Object[r6]
            r14 = 0
            r7[r14] = r23
            r15 = 1
            r7[r15] = r24
            com.meituan.robust.ChangeQuickRedirect r9 = changeQuickRedirect
            java.lang.Class[] r12 = new java.lang.Class[r6]
            java.lang.Class<com.leedarson.smartcamera.kvswebrtc.e0> r8 = com.leedarson.smartcamera.kvswebrtc.e0.class
            r12[r14] = r8
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r12[r15] = r8
            java.lang.Class r13 = java.lang.Void.TYPE
            r10 = 0
            r11 = 9795(0x2643, float:1.3726E-41)
            r8 = r22
            com.meituan.robust.PatchProxyResult r7 = com.meituan.robust.PatchProxy.proxy(r7, r8, r9, r10, r11, r12, r13)
            boolean r7 = r7.isSupported
            if (r7 == 0) goto L_0x0033
            return
        L_0x0033:
            r7 = r22
            r8 = r24
            r9 = r23
            boolean r10 = r7.A1
            if (r10 == 0) goto L_0x0044
            java.lang.String r0 = "正在获取kvs endpoint 节点数据，不需要重复调用....."
            r7.a(r0)
            return
        L_0x0044:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "正在获取KVS EndPoint ......"
            r10.append(r11)
            r10.append(r8)
            java.lang.String r10 = r10.toString()
            r7.a(r10)
            r7.A1 = r15
            com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r10 = new com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean
            r10.<init>()
            java.lang.String r11 = r7.f
            com.amazonaws.services.kinesisvideo.AWSKinesisVideoClient r11 = r7.T0(r11)
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r12 = new com.leedarson.smartcamera.reporters.WebRtcLogStepBean
            java.lang.String r13 = "GET_ICECONFIG"
            r6 = 200(0xc8, float:2.8E-43)
            r12.<init>(r13, r6)
            java.lang.String r15 = r7.h
            java.lang.String r14 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r14 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r15, r14)
            r14.K(r12)
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04ec }
            r15.<init>()     // Catch:{ Exception -> 0x04ec }
            java.lang.String r14 = "channelArn="
            r15.append(r14)     // Catch:{ Exception -> 0x04ec }
            java.lang.String r14 = r7.e     // Catch:{ Exception -> 0x04ec }
            r15.append(r14)     // Catch:{ Exception -> 0x04ec }
            java.lang.String r14 = ", role="
            r15.append(r14)     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.ChannelRole r14 = r7.i     // Catch:{ Exception -> 0x04ec }
            r15.append(r14)     // Catch:{ Exception -> 0x04ec }
            java.lang.String r14 = r15.toString()     // Catch:{ Exception -> 0x04ec }
            r12.requestParams = r14     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointRequest r14 = new com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointRequest     // Catch:{ Exception -> 0x04ec }
            r14.<init>()     // Catch:{ Exception -> 0x04ec }
            java.lang.String r15 = r7.e     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointRequest r14 = r14.withChannelARN(r15)     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.SingleMasterChannelEndpointConfiguration r15 = new com.amazonaws.services.kinesisvideo.model.SingleMasterChannelEndpointConfiguration     // Catch:{ Exception -> 0x04ec }
            r15.<init>()     // Catch:{ Exception -> 0x04ec }
            java.lang.String[] r6 = new java.lang.String[]{r3, r2}     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.SingleMasterChannelEndpointConfiguration r6 = r15.withProtocols((java.lang.String[]) r6)     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.ChannelRole r15 = r7.i     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.SingleMasterChannelEndpointConfiguration r6 = r6.withRole((com.amazonaws.services.kinesisvideo.model.ChannelRole) r15)     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointRequest r6 = r14.withSingleMasterChannelEndpointConfiguration(r6)     // Catch:{ Exception -> 0x04ec }
            com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointResult r6 = r11.getSignalingChannelEndpoint(r6)     // Catch:{ Exception -> 0x04ec }
            java.util.List<com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem> r14 = r7.k     // Catch:{ Exception -> 0x04ec }
            java.util.List r15 = r6.getResourceEndpointList()     // Catch:{ Exception -> 0x04ec }
            r14.addAll(r15)     // Catch:{ Exception -> 0x04ec }
            r14 = 200(0xc8, float:2.8E-43)
            r12.endTrace(r13, r14)     // Catch:{ Exception -> 0x04ec }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04ec }
            r14.<init>()     // Catch:{ Exception -> 0x04ec }
            r14.append(r1)     // Catch:{ Exception -> 0x04ec }
            java.lang.String r15 = r6.toString()     // Catch:{ Exception -> 0x04ec }
            r14.append(r15)     // Catch:{ Exception -> 0x04ec }
            r14.append(r1)     // Catch:{ Exception -> 0x04ec }
            java.util.List<com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem> r1 = r7.k     // Catch:{ Exception -> 0x04ec }
            int r1 = r1.size()     // Catch:{ Exception -> 0x04ec }
            r14.append(r1)     // Catch:{ Exception -> 0x04ec }
            java.lang.String r1 = r14.toString()     // Catch:{ Exception -> 0x04ec }
            r7.a(r1)     // Catch:{ Exception -> 0x04ec }
            r1 = 0
            r7.A1 = r1     // Catch:{ Exception -> 0x04ec }
            java.util.List<com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem> r1 = r7.k
            java.util.Iterator r1 = r1.iterator()
        L_0x00f9:
            boolean r6 = r1.hasNext()
            if (r6 == 0) goto L_0x0116
            java.lang.Object r6 = r1.next()
            com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem r6 = (com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem) r6
            java.lang.String r14 = r6.getProtocol()
            boolean r14 = r14.equals(r3)
            if (r14 == 0) goto L_0x0115
            java.lang.String r14 = r6.getResourceEndpoint()
            r7.j = r14
        L_0x0115:
            goto L_0x00f9
        L_0x0116:
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r1 = new com.leedarson.smartcamera.reporters.WebRtcLogStepBean
            r3 = 200(0xc8, float:2.8E-43)
            r1.<init>(r13, r3)
            java.lang.String r3 = r7.h
            java.lang.String r6 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r3 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r3, r6)
            r3.K(r1)
            java.lang.String r3 = r7.j
            boolean r3 = org.apache.http.util.j.c(r3)
            if (r3 == 0) goto L_0x0145
            r3 = -2013(0xfffffffffffff823, float:NaN)
            java.lang.String r6 = "kvs获取Endpoint失败: (mWssEndpoint为空)"
            r1.endTrace(r6, r3)
            java.lang.String r3 = r7.h
            java.lang.String r14 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r3 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r3, r14)
            r3.L(r6)
            r6 = 200(0xc8, float:2.8E-43)
            goto L_0x0150
        L_0x0145:
            java.lang.String r3 = r7.j
            r1.responseParams = r3
            java.lang.String r3 = "mWssEndpoint获取成功"
            r6 = 200(0xc8, float:2.8E-43)
            r1.endTrace(r3, r6)
        L_0x0150:
            r3 = 0
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r14 = new com.leedarson.smartcamera.reporters.WebRtcLogStepBean
            r14.<init>(r13, r6)
            r6 = r14
            java.lang.String r14 = r7.h
            java.lang.String r15 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r14 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r14, r15)
            r14.K(r6)
            java.util.List<com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem> r14 = r7.k
            java.util.Iterator r14 = r14.iterator()
        L_0x0168:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x0188
            java.lang.Object r15 = r14.next()
            com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem r15 = (com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem) r15
            r19 = r1
            java.lang.String r1 = r15.getProtocol()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0185
            java.lang.String r1 = r15.getResourceEndpoint()
            r3 = r1
        L_0x0185:
            r1 = r19
            goto L_0x0168
        L_0x0188:
            r19 = r1
            boolean r1 = org.apache.http.util.j.c(r3)
            if (r1 == 0) goto L_0x01a4
            r1 = -2014(0xfffffffffffff822, float:NaN)
            java.lang.String r2 = "从kvs中获取DataEndPoint为空"
            r6.endTrace(r2, r1)
            java.lang.String r1 = r7.h
            java.lang.String r14 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r1 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r1, r14)
            r1.L(r2)
            goto L_0x01ae
        L_0x01a4:
            r6.responseParams = r3
            java.lang.String r1 = "获取DataEndpoint成功"
            r2 = 200(0xc8, float:2.8E-43)
            r6.endTrace(r1, r2)
        L_0x01ae:
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r2 = r7.f
            com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r2 = r10.putData(r1, r4, r2)
            java.lang.String r4 = "dataEndpoint"
            r2.putData(r1, r4, r3)
            r10.begainTrace(r1)
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r2 = new com.leedarson.smartcamera.reporters.WebRtcLogStepBean
            r4 = 200(0xc8, float:2.8E-43)
            r2.<init>(r13, r4)
            java.lang.String r4 = r7.h
            java.lang.String r14 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r4 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r14)
            r4.K(r2)
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r14 = r7.l     // Catch:{ Exception -> 0x02fe }
            r14.clear()     // Catch:{ Exception -> 0x02fe }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02fe }
            r14.<init>()     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = "region="
            r14.append(r15)     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = r7.f     // Catch:{ Exception -> 0x02fe }
            r14.append(r15)     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = " , channelArn="
            r14.append(r15)     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = r7.e     // Catch:{ Exception -> 0x02fe }
            r14.append(r15)     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = " , role="
            r14.append(r15)     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideo.model.ChannelRole r15 = r7.i     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = r15.toString()     // Catch:{ Exception -> 0x02fe }
            r14.append(r15)     // Catch:{ Exception -> 0x02fe }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x02fe }
            r2.requestParams = r14     // Catch:{ Exception -> 0x02fe }
            java.lang.String r14 = r7.f     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideosignaling.AWSKinesisVideoSignalingClient r14 = r7.U0(r14, r3)     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest r15 = new com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest     // Catch:{ Exception -> 0x02fe }
            r15.<init>()     // Catch:{ Exception -> 0x02fe }
            java.lang.String r4 = r7.e     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest r4 = r15.withChannelARN(r4)     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideo.model.ChannelRole r15 = r7.i     // Catch:{ Exception -> 0x02fe }
            java.lang.String r15 = r15.name()     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest r4 = r4.withClientId(r15)     // Catch:{ Exception -> 0x02fe }
            com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigResult r4 = r14.getIceServerConfig(r4)     // Catch:{ Exception -> 0x02fe }
            java.util.List r15 = r4.getIceServerList()     // Catch:{ Exception -> 0x02fe }
            if (r15 == 0) goto L_0x0277
            java.util.List r15 = r4.getIceServerList()     // Catch:{ Exception -> 0x02fe }
            int r15 = r15.size()     // Catch:{ Exception -> 0x02fe }
            if (r15 != 0) goto L_0x0274
            int r15 = r7.y1     // Catch:{ Exception -> 0x02fe }
            r20 = r1
            int r1 = r7.z1     // Catch:{ Exception -> 0x02fc }
            if (r15 >= r1) goto L_0x0279
            r1 = 1
            int r15 = r15 + r1
            r7.y1 = r15     // Catch:{ Exception -> 0x02fc }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02fc }
            r0.<init>()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = "getIceServerList 还未达到重试阈值.... 111  getKvsServerListRetryTimes="
            r0.append(r1)     // Catch:{ Exception -> 0x02fc }
            int r1 = r7.y1     // Catch:{ Exception -> 0x02fc }
            r0.append(r1)     // Catch:{ Exception -> 0x02fc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02fc }
            r7.b1(r9, r0)     // Catch:{ Exception -> 0x02fc }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02fc }
            r0.<init>()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = "暂未获取到iceConfig,正在进行第"
            r0.append(r1)     // Catch:{ Exception -> 0x02fc }
            int r1 = r7.y1     // Catch:{ Exception -> 0x02fc }
            r0.append(r1)     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = "次尝试"
            r0.append(r1)     // Catch:{ Exception -> 0x02fc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x02fc }
            r2.responseParams = r0     // Catch:{ Exception -> 0x02fc }
            return
        L_0x0274:
            r20 = r1
            goto L_0x0279
        L_0x0277:
            r20 = r1
        L_0x0279:
            java.util.List r1 = r4.getIceServerList()     // Catch:{ Exception -> 0x02fc }
            if (r1 == 0) goto L_0x02a2
            java.util.List r1 = r4.getIceServerList()     // Catch:{ Exception -> 0x02fc }
            int r1 = r1.size()     // Catch:{ Exception -> 0x02fc }
            if (r1 != 0) goto L_0x028a
            goto L_0x02a2
        L_0x028a:
            com.google.gson.Gson r0 = new com.google.gson.Gson     // Catch:{ Exception -> 0x02fc }
            r0.<init>()     // Catch:{ Exception -> 0x02fc }
            java.util.List r1 = r4.getIceServerList()     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = r0.toJson((java.lang.Object) r1)     // Catch:{ Exception -> 0x02fc }
            r2.responseParams = r1     // Catch:{ Exception -> 0x02fc }
            java.lang.String r1 = "获取KVS.iceConfig成功"
            r15 = 200(0xc8, float:2.8E-43)
            r2.endTrace(r1, r15)     // Catch:{ Exception -> 0x02fc }
            goto L_0x02bb
        L_0x02a2:
            java.lang.String r1 = "已尝试过三次,但是还是未能获取到iceServerList,本次接流失败"
            r2.responseParams = r1     // Catch:{ Exception -> 0x02fc }
            r15 = -31007001(0xfffffffffe26dee7, float:-5.5452306E37)
            r2.endTrace(r0, r15)     // Catch:{ Exception -> 0x02fc }
            java.lang.String r15 = r7.h     // Catch:{ Exception -> 0x02fc }
            r21 = r1
            java.lang.String r1 = r7.m0     // Catch:{ Exception -> 0x02fc }
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r1 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r15, r1)     // Catch:{ Exception -> 0x02fc }
            r1.L(r0)     // Catch:{ Exception -> 0x02fc }
        L_0x02bb:
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r0 = r7.l     // Catch:{ Exception -> 0x02fc }
            java.util.List r1 = r4.getIceServerList()     // Catch:{ Exception -> 0x02fc }
            r0.addAll(r1)     // Catch:{ Exception -> 0x02fc }
            com.google.gson.Gson r0 = new com.google.gson.Gson     // Catch:{ Exception -> 0x02fc }
            r0.<init>()     // Catch:{ Exception -> 0x02fc }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x02fc }
            r1.<init>()     // Catch:{ Exception -> 0x02fc }
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r15 = r7.l     // Catch:{ Exception -> 0x02fc }
            java.lang.String r15 = r0.toJson((java.lang.Object) r15)     // Catch:{ Exception -> 0x02fc }
            r10.putData(r1, r5, r15)     // Catch:{ Exception -> 0x02fc }
            r10.endTraceSuccess(r1)     // Catch:{ Exception -> 0x02fc }
            io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r15 = r7.E0     // Catch:{ Exception -> 0x02fc }
            r15.onNext(r10)     // Catch:{ Exception -> 0x02fc }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02fc }
            r15.<init>()     // Catch:{ Exception -> 0x02fc }
            r21 = r0
            java.lang.String r0 = "LDS.WEBRTC mIceServerList: "
            r15.append(r0)     // Catch:{ Exception -> 0x02fc }
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r0 = r7.l     // Catch:{ Exception -> 0x02fc }
            int r0 = r0.size()     // Catch:{ Exception -> 0x02fc }
            r15.append(r0)     // Catch:{ Exception -> 0x02fc }
            java.lang.String r0 = r15.toString()     // Catch:{ Exception -> 0x02fc }
            r7.a(r0)     // Catch:{ Exception -> 0x02fc }
            goto L_0x034f
        L_0x02fc:
            r0 = move-exception
            goto L_0x0301
        L_0x02fe:
            r0 = move-exception
            r20 = r1
        L_0x0301:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "KVSWebRtcChannel Get Ice Server Config failed with Exception "
            r1.append(r4)
            java.lang.String r4 = r0.toString()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r7.a(r1)
            if (r9 == 0) goto L_0x04c5
            int r4 = r7.y1
            int r14 = r7.z1
            if (r4 < r14) goto L_0x04c5
            r2.responseParams = r1
            java.lang.String r4 = "KVS获取iceServerConfig异常"
            r14 = -31007001(0xfffffffffe26dee7, float:-5.5452306E37)
            r2.endTrace(r4, r14)
            java.lang.String r14 = r7.h
            java.lang.String r15 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r14 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r14, r15)
            r14.L(r4)
            r9.onFail(r1)
            r4 = 0
            r7.y1 = r4
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            r10.putData(r4, r5, r1)
            r14 = 400(0x190, float:5.6E-43)
            r10.endTraceExcept(r14, r4)
            io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r14 = r7.E0
            r14.onNext(r10)
        L_0x034f:
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r0 = new com.leedarson.smartcamera.reporters.WebRtcLogStepBean
            r1 = 200(0xc8, float:2.8E-43)
            r0.<init>(r13, r1)
            java.util.Locale r1 = java.util.Locale.US
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r14 = r7.f
            r15 = 0
            r4[r15] = r14
            java.lang.String r15 = "cn-"
            boolean r14 = r14.contains(r15)
            java.lang.String r15 = ""
            if (r14 == 0) goto L_0x036d
            java.lang.String r14 = ".cn"
            goto L_0x036e
        L_0x036d:
            r14 = r15
        L_0x036e:
            r16 = 1
            r4[r16] = r14
            java.lang.String r14 = "stun:stun.kinesisvideo.%s.amazonaws.com%s:443"
            java.lang.String r1 = java.lang.String.format(r1, r14, r4)
            r0.requestParams = r1
            java.lang.String r4 = r7.h
            java.lang.String r14 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r4 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r4, r14)
            r4.K(r0)
            org.webrtc.PeerConnection$IceServer$Builder r4 = org.webrtc.PeerConnection.IceServer.builder((java.lang.String) r1)
            org.webrtc.PeerConnection$IceServer r4 = r4.createIceServer()
            java.lang.String r14 = "sturn服务器创建成功"
            r16 = r1
            r1 = 200(0xc8, float:2.8E-43)
            r0.endTrace(r14, r1)
            java.util.List<org.webrtc.PeerConnection$IceServer> r1 = r7.m
            r1.add(r4)
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r1 = r7.l
            if (r1 == 0) goto L_0x040b
            r1 = 0
        L_0x03a3:
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r14 = r7.l
            int r14 = r14.size()
            if (r1 >= r14) goto L_0x0406
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r14 = r7.l
            java.lang.Object r14 = r14.get(r1)
            com.amazonaws.services.kinesisvideosignaling.model.IceServer r14 = (com.amazonaws.services.kinesisvideosignaling.model.IceServer) r14
            java.util.List r14 = r14.getUris()
            java.lang.String r14 = r14.toString()
            if (r14 == 0) goto L_0x03fb
            r17 = r0
            java.lang.String r0 = "["
            java.lang.String r0 = r14.replace(r0, r15)
            r18 = r2
            java.lang.String r2 = "]"
            java.lang.String r0 = r0.replace(r2, r15)
            org.webrtc.PeerConnection$IceServer$Builder r0 = org.webrtc.PeerConnection.IceServer.builder((java.lang.String) r0)
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r2 = r7.l
            java.lang.Object r2 = r2.get(r1)
            com.amazonaws.services.kinesisvideosignaling.model.IceServer r2 = (com.amazonaws.services.kinesisvideosignaling.model.IceServer) r2
            java.lang.String r2 = r2.getUsername()
            org.webrtc.PeerConnection$IceServer$Builder r0 = r0.setUsername(r2)
            java.util.List<com.amazonaws.services.kinesisvideosignaling.model.IceServer> r2 = r7.l
            java.lang.Object r2 = r2.get(r1)
            com.amazonaws.services.kinesisvideosignaling.model.IceServer r2 = (com.amazonaws.services.kinesisvideosignaling.model.IceServer) r2
            java.lang.String r2 = r2.getPassword()
            org.webrtc.PeerConnection$IceServer$Builder r0 = r0.setPassword(r2)
            org.webrtc.PeerConnection$IceServer r0 = r0.createIceServer()
            java.util.List<org.webrtc.PeerConnection$IceServer> r2 = r7.m
            r2.add(r0)
            goto L_0x03ff
        L_0x03fb:
            r17 = r0
            r18 = r2
        L_0x03ff:
            int r1 = r1 + 1
            r0 = r17
            r2 = r18
            goto L_0x03a3
        L_0x0406:
            r17 = r0
            r18 = r2
            goto L_0x040f
        L_0x040b:
            r17 = r0
            r18 = r2
        L_0x040f:
            java.lang.String r0 = r7.j
            boolean r0 = org.apache.http.util.j.c(r0)
            if (r0 == 0) goto L_0x0433
            if (r9 == 0) goto L_0x0432
            java.lang.String r0 = "KVSWebRTCChannel==> wssEndPoint为空"
            r9.onFail(r0)
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = "KVSWebRTCChannel==> wssEndPoint为空:"
            r10.putData(r0, r5, r1)
            r1 = 400(0x190, float:5.6E-43)
            r10.endTraceExcept(r1, r0)
            io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r1 = r7.E0
            r1.onNext(r10)
        L_0x0432:
            return
        L_0x0433:
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r0 = new com.leedarson.smartcamera.reporters.WebRtcLogStepBean
            r1 = 200(0xc8, float:2.8E-43)
            r0.<init>(r13, r1)
            java.lang.String r1 = r7.h
            java.lang.String r2 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r1 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r1, r2)
            r1.K(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r7.j
            r1.append(r2)
            java.lang.String r2 = "?X-Amz-ChannelARN="
            r1.append(r2)
            java.lang.String r2 = r7.e
            r1.append(r2)
            java.lang.String r2 = "&X-Amz-ClientId="
            r1.append(r2)
            java.lang.String r2 = r7.h
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.net.URI r2 = r7.m1(r1)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r13 = "viewerEndpoint:"
            r5.append(r13)
            r5.append(r1)
            java.lang.String r13 = ", signedUri="
            r5.append(r13)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r0.requestParams = r5
            java.lang.String r5 = "签名节点创建完成"
            r13 = 200(0xc8, float:2.8E-43)
            r0.endTrace(r5, r13)
            java.lang.String r5 = r2.toString()
            r7.v = r5
            com.leedarson.smartcamera.logreport.c r5 = com.leedarson.smartcamera.logreport.c.b()
            java.lang.String r13 = r7.m0
            java.lang.String r14 = r7.h
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r23 = r0
            java.lang.String r0 = "getHost:"
            r15.append(r0)
            java.lang.String r0 = r7.v
            r15.append(r0)
            java.lang.String r0 = r15.toString()
            java.lang.String r15 = "kvswebsocket"
            r5.e(r13, r14, r15, r0)
            org.webrtc.PeerConnection$IceConnectionState r0 = r7.v1
            org.webrtc.PeerConnection$IceConnectionState r5 = org.webrtc.PeerConnection.IceConnectionState.CONNECTED
            if (r0 == r5) goto L_0x04c4
            if (r9 == 0) goto L_0x04c4
            java.lang.String r0 = r7.v
            r9.onSuccess(r0)
        L_0x04c4:
            return
        L_0x04c5:
            r18 = r2
            int r2 = r7.y1
            r4 = 1
            int r2 = r2 + r4
            r7.y1 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "getIceServerList KV获取发生了异常，但是还未达预值 getKvsServerListRetryTimes="
            r2.append(r4)
            int r4 = r7.y1
            r2.append(r4)
            java.lang.String r4 = "  listerner="
            r2.append(r4)
            r2.append(r9)
            java.lang.String r2 = r2.toString()
            r7.b1(r9, r2)
            return
        L_0x04ec:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = " Get Signaling Endpoint failed with Exception "
            r1.append(r2)
            java.lang.String r2 = r0.getLocalizedMessage()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r7.a(r1)
            r1 = 0
            r7.A1 = r1
            if (r9 == 0) goto L_0x057d
            int r1 = r7.y1
            int r2 = r7.z1
            if (r1 < r2) goto L_0x057d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "KVSWebRtcChannel Get Signaling Endpoint failed with Exception "
            r1.append(r2)
            java.lang.String r2 = r0.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r12.responseParams = r1
            r2 = -2012(0xfffffffffffff824, float:NaN)
            java.lang.String r3 = "创建信令服务器异常"
            r12.endTrace(r3, r2)
            java.lang.String r2 = r7.h
            java.lang.String r6 = r7.m0
            com.leedarson.smartcamera.reporters.WebRtcReporterV3 r2 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.v(r2, r6)
            r2.L(r3)
            r2 = 0
            r7.y1 = r2
            r9.onFail(r1)
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = r7.f
            com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean r3 = r10.putData(r2, r4, r3)
            java.lang.String r4 = r7.e
            java.lang.String r6 = "channelArn"
            r3.putData(r2, r6, r4)
            r10.begainTrace(r2)
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Get Signaling Endpoint failed with Exception: "
            r4.append(r6)
            java.lang.String r6 = r0.toString()
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r10.putData(r3, r5, r4)
            r4 = 400(0x190, float:5.6E-43)
            r10.endTraceExcept(r4, r3)
            io.reactivex.processors.b<com.leedarson.smartcamera.kvswebrtc.beans.WebRtcServiceStateChangeLogBean> r4 = r7.E0
            r4.onNext(r10)
            return
        L_0x057d:
            int r1 = r7.y1
            r2 = 1
            int r1 = r1 + r2
            r7.y1 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "getIceServerList 尝试次数还未达到阈值....getKvsServerListRetryTimes="
            r1.append(r2)
            int r2 = r7.y1
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r7.b1(r9, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.f0.b1(com.leedarson.smartcamera.kvswebrtc.e0, java.lang.String):void");
    }

    private String X0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9796, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        this.W0 = false;
        this.B1 = System.currentTimeMillis();
        this.d0 = DeviceIdUtils.getDeviceId(BaseApplication.b());
        String random_id = "0" + K0(5);
        if (org.apache.http.util.j.c(this.h)) {
            int streamID = this.i0 ^ 1;
            this.h = String.format(Locale.US, "%s_%s_%d_%d_%d", new Object[]{this.d0, random_id, Integer.valueOf(this.o0), 0, Integer.valueOf((int) streamID)});
            int t_retryTimes = 0;
            while (a.contains(this.h) && t_retryTimes <= 3) {
                t_retryTimes++;
                this.h = String.format(Locale.US, "%s_%s_%d_%d_%d", new Object[]{this.d0, "0" + K0(5), Integer.valueOf(this.o0), 0, Integer.valueOf(streamID)});
            }
            com.leedarson.smartcamera.logreport.c b2 = com.leedarson.smartcamera.logreport.c.b();
            String str = this.m0;
            String str2 = this.h;
            b2.e(str, str2, "new peerid", str2);
        }
        a.add(this.h);
        this.Z0.j();
        WebRtcReporterV3.T(this.h, this.Z0);
        com.leedarson.log.f.c("开始一轮新的p2p连接任务(peerie创建完毕)new peerid:" + this.h);
        return this.h;
    }

    private String R0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9797, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Random random = new Random();
        StringBuilder pskValue = new StringBuilder();
        for (int i2 = 0; i2 < 64; i2++) {
            pskValue.append("123456789abcdef".charAt(random.nextInt("123456789abcdef".length())));
        }
        return pskValue.toString();
    }

    private String v0(String sdp, String pskId, String pskVaule) {
        int fromIndex;
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sdp, pskId, pskVaule}, this, changeQuickRedirect2, false, 9798, new Class[]{cls, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder newSdp = new StringBuilder(sdp);
        int fromIndex2 = 0;
        for (int i2 = 0; i2 < 5 && (fromIndex = newSdp.indexOf("a=ice-ufrag", fromIndex2)) != -1; i2++) {
            newSdp.insert(fromIndex, "a=psk:" + pskId + " " + pskVaule + "\r\n");
            fromIndex2 = newSdp.indexOf("a=ice-ufrag", fromIndex) + 1;
        }
        int fromIndex3 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int fromIndex4 = newSdp.indexOf("fingerprint", fromIndex3);
            if (fromIndex4 == -1) {
                break;
            }
            newSdp.insert(fromIndex4, "disable-");
            fromIndex3 = newSdp.indexOf("fingerprint", fromIndex4) + 1;
        }
        return newSdp.toString();
    }

    private Timer L0(Timer oldTimer) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{oldTimer}, this, changeQuickRedirect, false, 9800, new Class[]{Timer.class}, Timer.class);
        if (proxy.isSupported) {
            return (Timer) proxy.result;
        }
        if (oldTimer != null) {
            oldTimer.cancel();
        }
        return new Timer();
    }

    public void L2() {
    }

    public void N2() {
        this.G = null;
    }

    public String d1() {
        return this.g;
    }

    /* compiled from: KVSWebRTCChannel */
    public class e extends g0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onCreateFailure(String error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9868, new Class[]{String.class}, Void.TYPE).isSupported) {
                f0 f0Var = f0.this;
                f0.s(f0Var, "onSdpCreateFailure:" + error);
                f0 f0Var2 = f0.this;
                WebRtcReporterV3 preLinkReporter = WebRtcReporterV3.v(f0Var2.h, f0Var2.m0);
                WebRtcLogStepBean _sdpExchangeFail = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_SDP_CHECKING, -31007205);
                _sdpExchangeFail.setResponse("setFaillure:" + error);
                _sdpExchangeFail.endTrace("[媒体协商] 创建answer时失败", -31007205);
                preLinkReporter.K(_sdpExchangeFail);
                preLinkReporter.L("媒体协商 - 创建answer失败");
                f0 f0Var3 = f0.this;
                f0.F(f0Var3, "[媒体协商]  onSdpCreateFailure error=" + error);
                super.onCreateFailure(error);
            }
        }

        public void onSetFailure(String error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9869, new Class[]{String.class}, Void.TYPE).isSupported) {
                f0 f0Var = f0.this;
                f0.s(f0Var, "webrtc.sdp  onSetFailure" + error.toString());
                f0 f0Var2 = f0.this;
                WebRtcReporterV3 preLinkReporter = WebRtcReporterV3.v(f0Var2.h, f0Var2.m0);
                WebRtcLogStepBean _sdpExchangeFail = new WebRtcLogStepBean(WebRtcLogStepBean.EXCHANGE_SDP_CHECKING, -31007505);
                _sdpExchangeFail.setResponse("setFaillure:" + error);
                _sdpExchangeFail.endTrace("[媒体协商] 设置远程的answer失败", -31007505);
                preLinkReporter.K(_sdpExchangeFail);
                preLinkReporter.L("媒体协商 - 设置answer失败");
                f0 f0Var3 = f0.this;
                f0.F(f0Var3, "[媒体协商] remote sdp info set fail " + error);
                super.onSetFailure(error);
            }
        }
    }

    public void v2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9801, new Class[0], Void.TYPE).isSupported) {
            this.D.b(new z(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: W1 */
    public /* synthetic */ void X1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9816, new Class[0], Void.TYPE).isSupported) {
            try {
                a("[开启解码器] openDecoder");
                PeerConnection peerConnection = this.y;
                if (peerConnection != null) {
                    for (RtpTransceiver transceiver : peerConnection.getTransceivers1()) {
                        transceiver.openDecoder();
                    }
                    a("[开启解码器] single renderRelease end: ");
                }
            } catch (Exception e2) {
                b("[开启解码器] LDS.WEBRTC openDecoder产生异常：e" + e2.toString());
            }
        }
    }

    public void G0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9802, new Class[0], Void.TYPE).isSupported) {
            this.D.b(new x(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J1 */
    public /* synthetic */ void K1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9815, new Class[0], Void.TYPE).isSupported) {
            try {
                a("[关闭解码器] single closeDecoder run: ");
                for (RtpTransceiver transceiver : this.y.getTransceivers1()) {
                    transceiver.closeDecoder();
                }
            } catch (Exception e2) {
                b("[关闭解码器] LDS.WEBRTC closeDecoder产生异常：e" + e2.toString());
            }
        }
    }

    public void C0(q mode) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{mode}, this, changeQuickRedirect, false, 9803, new Class[]{q.class}, Void.TYPE).isSupported) {
            this.O0 = mode;
            q qVar = q.SD;
            if (mode == qVar) {
                d3(1000);
            } else {
                d3(0);
            }
            byte[] bytes = AVIOCTRLDEFs.AVIO_CTRLCmd.parseContent(0, mode.ordinal());
            StringBuilder sb = new StringBuilder();
            sb.append("[切换拉流模式] 发送指令--->currentMode=");
            sb.append(this.O0);
            sb.append(" currentMode == AVIO_CTRL_SESSION_MODE.LIVING=");
            q qVar2 = this.O0;
            q qVar3 = q.LIVING;
            if (qVar2 != qVar3) {
                z2 = false;
            }
            sb.append(z2);
            a(sb.toString());
            int seqNum = Q0(2);
            q qVar4 = this.O0;
            if (qVar4 == qVar3) {
                this.r.openDecoder();
                if (!org.apache.http.util.j.c(this.h)) {
                    this.t1 = System.currentTimeMillis();
                    int i2 = 200;
                    WebRtcLogStepBean _stepOfSendLiveMessage = new WebRtcLogStepBean(WebRtcLogStepBean.CHANGE_LIVING_MODE_REQ, 200);
                    String tipOfStatue = "发起拉流指令-P2P通道状态:" + t1() + ",seq=" + seqNum;
                    _stepOfSendLiveMessage.desc = tipOfStatue;
                    if (!t1()) {
                        i2 = -2007;
                    }
                    _stepOfSendLiveMessage.endTrace(tipOfStatue, i2);
                    WebRtcReporterV3 _playerReporter = WebRtcReporterV3.w(this.h, this.m0);
                    _playerReporter.K(_stepOfSendLiveMessage);
                    if (!t1()) {
                        WebRtcLogStepBean webRtcLogStepBean = this.u1;
                        if (webRtcLogStepBean != null) {
                            _playerReporter.J(webRtcLogStepBean);
                        }
                        _playerReporter.L("发起拉流指令时-通路已断开");
                    }
                } else {
                    return;
                }
            } else if (qVar4 == qVar) {
                v2();
            } else if (qVar4 == q.IDLE) {
                G0();
            }
            W2(seqNum, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_SESSION_MODE_REQ, bytes);
            com.leedarson.smartcamera.logreport.c.b().e(this.m0, this.h, "change mode", "变换拉流模式 mode: " + mode + "  peerConnect=" + r1());
        }
    }

    public boolean u1() {
        return this.M0;
    }

    public void i3(boolean supportPreCon) {
        this.M0 = supportPreCon;
    }

    public void O2(SurfaceViewRenderer surfaceViewRenderer) {
        if (!PatchProxy.proxy(new Object[]{surfaceViewRenderer}, this, changeQuickRedirect, false, 9804, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            this.D.b(new o(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d2 */
    public /* synthetic */ void e2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9814, new Class[0], Void.TYPE).isSupported) {
            try {
                a("[移除渲染器] single renderRelease run: ");
                com.leedarson.smartcamera.kvswebrtc.utils.f fVar = this.L0;
                if (fVar != null) {
                    fVar.i();
                }
            } catch (Exception e2) {
                b("[移除渲染器] LDS.WEBRTC renderRelease产生异常：e" + e2.toString());
            }
        }
    }

    public void F0() {
        this.J = null;
        this.K = null;
        this.L = null;
        this.V = null;
        this.U = null;
        this.j0 = null;
        this.G = null;
        this.m1 = null;
        this.c1 = null;
        this.p1 = null;
        this.q1 = null;
        this.W0 = false;
    }

    public void P0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9805, new Class[0], Void.TYPE).isSupported) {
            WebRtcReporterV3 _firstFrameReporter = WebRtcReporterV3.w(this.h, this.m0);
            a("[首帧渲染] onGetFirstFrame: 收到了首帧渲染 " + this.h + " deviceId=" + this.m0 + "  count=" + _firstFrameReporter.r().size() + " ,_startUseLeaveRoomStep=" + this.u1 + " -->firstFrameRenderReporter=" + _firstFrameReporter.toString());
            this.W0 = true;
            WebRtcLogStepBean _stepOfFirstFrame = new WebRtcLogStepBean(WebRtcLogStepBean.IGET_FIRST_FRAME, 200);
            if (!this.M0) {
                _stepOfFirstFrame._beginTraceTimeSpan = this.B1;
            } else {
                _stepOfFirstFrame._beginTraceTimeSpan = this.t1;
            }
            _stepOfFirstFrame.endTrace("收到首帧渲染", 200);
            WebRtcLogStepBean webRtcLogStepBean = this.u1;
            if (webRtcLogStepBean != null) {
                _firstFrameReporter.J(webRtcLogStepBean);
                _firstFrameReporter.I(this.J1);
            }
            if (!u1()) {
                _firstFrameReporter.K(_stepOfFirstFrame).L("收到首帧渲染 44444444444");
            } else if (_firstFrameReporter.m(WebRtcLogStepBean.CHANGE_LIVING_MODE_REQ)) {
                _firstFrameReporter.K(_stepOfFirstFrame).L("收到首帧渲染 3333333");
            } else {
                a("收到首帧渲染 5555555");
            }
            com.leedarson.smartcamera.logreport.c.b().e(this.m0, this.h, "firstFrame", "首帧渲染完成");
        }
    }

    private void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9806, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("KVSWebRTCChannel");
            g2.a(msg + " ,mClientId= " + this.h + ",perconnection= " + this.y + ", webrtcLogV2 deviceId:" + this.m0 + "  kvsChannel=" + toString(), new Object[0]);
        }
    }

    private void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9807, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("KVSWebRTCChannel");
            g2.c(msg + " ,mClientId= " + this.h + ",perconnection= " + this.y + ", webrtcLogV2 deviceId:" + this.m0 + "  kvsChannel=" + toString(), new Object[0]);
        }
    }

    /* compiled from: KVSWebRTCChannel */
    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        f(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9870, new Class[0], Void.TYPE).isSupported) {
                if (f0.this.y != null) {
                    f0.this.y.setJitterBufferDelayMs(this.c);
                }
            }
        }
    }

    public void d3(int delayMs) {
        Object[] objArr = {new Integer(delayMs)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9808, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.D.b(new f(delayMs));
        }
    }

    public q a1() {
        return this.O0;
    }

    private void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9809, new Class[0], Void.TYPE).isSupported) {
            if (this.u1 == null) {
                b("[自动重连策略] 用户不在直播间 - 不用执行重试连接 (处理在非直播间外Answer超时问题)");
            } else if (t1() && r1()) {
                b("[自动重连策略] 当前通道重处于可用状态,不需要执行此操作");
            } else if (this.h1 != -1) {
                long diffConnectTimeSpan = System.currentTimeMillis() - this.H1;
                long j2 = this.I1;
                if (diffConnectTimeSpan > j2) {
                    b("[自动重连策略] 距离上一次通道建立已过15S，选择自动重建通道");
                    com.leedarson.smartcamera.kvswebrtc.signaling.b bVar = this.w1;
                    if (bVar != null) {
                        bVar.c();
                    }
                    d(false, true);
                    c(BaseApplication.b(), this.c1, true);
                } else if (diffConnectTimeSpan >= 0 && diffConnectTimeSpan <= j2) {
                    b("[自动重连策略] 触发等待达到15S, 再等待 " + (this.I1 - diffConnectTimeSpan));
                    this.S.removeCallbacks(this.L1);
                    this.S.postDelayed(this.L1, this.I1 - diffConnectTimeSpan);
                }
            } else {
                b("[自动重连策略] 原先就处于IDLE状态/开始释放通道/准备重新连");
                com.leedarson.smartcamera.kvswebrtc.signaling.b bVar2 = this.w1;
                if (bVar2 != null) {
                    bVar2.c();
                }
                d(false, true);
                this.S.removeCallbacks(this.L1);
                this.S.postDelayed(this.L1, this.I1);
                b("[自动重连策略] 原先就处于IDLE状态-->开始释放通道-->开始重建通道");
                c(BaseApplication.b(), this.c1, true);
            }
        }
    }

    private void B3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9810, new Class[0], Void.TYPE).isSupported) {
            IpcService ipceService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (ipceService != null) {
                this.K1 = ipceService.wakeUpDeviceById(this.m0).H(new i(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: r2 */
    public /* synthetic */ void s2(kotlin.n booleanStringPair) {
        if (!PatchProxy.proxy(new Object[]{booleanStringPair}, this, changeQuickRedirect, false, 9813, new Class[]{kotlin.n.class}, Void.TYPE).isSupported) {
            a("[唤醒设备] 唤醒设备执行情况? " + booleanStringPair.getFirst());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: S1 */
    public /* synthetic */ void T1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9812, new Class[0], Void.TYPE).isSupported) {
            b("[自动重连策略] 触发等待达到15S,通道还是未通么？ connectedStatue=" + t1() + " isDataChannelOpen=" + r1());
            if (!r1()) {
                com.leedarson.smartcamera.kvswebrtc.signaling.b bVar = this.w1;
                if (bVar != null) {
                    bVar.c();
                }
                d(false, true);
                b("[自动重连策略] 触发等待达到15S/通道还是未通/正在尝试重新连接");
                c(BaseApplication.b(), this.c1, true);
                return;
            }
            b("[自动重连策略] 触发等待达到15S,通道还是未通么？ 通道已通 - Good");
        }
    }

    private void r3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9811, new Class[0], Void.TYPE).isSupported) {
            b("[自动重连策略] 触发等待达到还未到15S-->通道已通了（或已达到用户等待超时）-->解除重连策略");
            this.S.removeCallbacks(this.L1);
        }
    }
}
