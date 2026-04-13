package com.leedarson.newui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.j;
import com.leedarson.base.http.observer.l;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PropsBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.repos.n;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.h0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.view.WebrtcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import meshsdk.util.LDSModel;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import org.webrtc.RendererCommon;

public class BaseKVSCameraView extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    int A4;
    io.reactivex.disposables.b B4;
    io.reactivex.disposables.b C4;
    n D4;
    /* access modifiers changed from: private */
    public d E4;
    private boolean F4;
    private e a1;
    /* access modifiers changed from: private */
    public ImageView a2;
    private Context c;
    private WebrtcSurfaceView d;
    /* access modifiers changed from: private */
    public w f;
    public long p0;
    private String p1;
    /* access modifiers changed from: private */
    public boolean p2;
    /* access modifiers changed from: private */
    public boolean p3;
    IpcDeviceBean p4;
    private String q;
    private final int x;
    /* access modifiers changed from: private */
    public f0 y;
    public KVSParamBean z;
    io.reactivex.disposables.b z4;

    public interface d {
        void onClick();
    }

    static /* synthetic */ void d(BaseKVSCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4884, new Class[]{BaseKVSCameraView.class}, Void.TYPE).isSupported) {
            x0.T();
        }
    }

    static /* synthetic */ void e(BaseKVSCameraView x0, String x1) {
        Class[] clsArr = {BaseKVSCameraView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 4885, clsArr, Void.TYPE).isSupported) {
            x0.S(x1);
        }
    }

    public BaseKVSCameraView(@NonNull Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public BaseKVSCameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseKVSCameraView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.x = 0;
        this.p0 = 0;
        this.a1 = new e(this, (a) null);
        this.p2 = true;
        this.p3 = false;
        this.A4 = 0;
        this.D4 = new n();
        this.F4 = false;
        this.c = context;
        LayoutInflater.from(context).inflate(R$layout.base_kvs_camera_view, this, true);
        t();
    }

    public void setOnCameraStateListener(w onCameraStateListener) {
        this.f = onCameraStateListener;
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4847, new Class[0], Void.TYPE).isSupported) {
            WebrtcSurfaceView webrtcSurfaceView = (WebrtcSurfaceView) findViewById(R$id.video_surface);
            this.d = webrtcSurfaceView;
            webrtcSurfaceView.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
            this.d.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
            this.d.setKeepScreenOn(true);
            this.d.setClickable(true);
            this.a2 = (ImageView) findViewById(R$id.img_cache);
            this.d.setOnClickListener(new a());
            this.d.setOnFrameListener(new b(this));
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4886, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (BaseKVSCameraView.this.E4 != null) {
                BaseKVSCameraView.this.E4.onClick();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: A */
    public /* synthetic */ void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4883, new Class[0], Void.TYPE).isSupported) {
            Message msg = Message.obtain();
            msg.what = 1;
            this.a1.sendMessage(msg);
            f0 f0Var = this.y;
            if (f0Var != null) {
                f0Var.P0();
            }
        }
    }

    public void s(String deviceId, String modelId) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{deviceId, modelId}, this, changeQuickRedirect, false, 4848, clsArr, Void.TYPE).isSupported) {
            this.q = deviceId;
            this.p1 = modelId;
            this.d.setModelId(modelId);
        }
    }

    public void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4849, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean o = IpcServiceImpl.o(this.q);
            this.p4 = o;
            if (o == null) {
                return;
            }
            if (Constans.IPC_LIVE_TYPE_LDS.equals(o.props.liveType)) {
                if (this.p4.isLowPowerDevice()) {
                    V(this.q);
                } else {
                    v(this.q, (KVSParamBean) null, this.d, false);
                }
            } else if (Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.p4.props.liveType)) {
                if (this.p4.isLowPowerDevice()) {
                    V(this.q);
                } else {
                    q(this.q);
                }
            } else if (this.p4.isLowPowerDevice()) {
                V(this.q);
            } else {
                q(this.q);
            }
        }
    }

    public void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4850, new Class[0], Void.TYPE).isSupported) {
            if (u()) {
                j();
                return;
            }
            f0 f0Var = this.y;
            if (f0Var != null && !this.p3) {
                f0Var.I2(false);
            }
        }
    }

    public void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4851, new Class[0], Void.TYPE).isSupported) {
            f0 f0Var = this.y;
            if (f0Var != null) {
                f0Var.F0();
                this.y = null;
            }
            K("sufun.data  释放直播间资源成功.....");
        }
    }

    public void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4852, new Class[0], Void.TYPE).isSupported) {
            f0 f0Var = this.y;
            if (f0Var != null) {
                f0Var.F0();
            }
            this.f = null;
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4853, new Class[0], Void.TYPE).isSupported) {
            f0 f0Var = this.y;
            if (f0Var != null) {
                this.p3 = true;
                f0Var.I2(false);
                o();
                n();
                p();
            }
            WebrtcSurfaceView webrtcSurfaceView = this.d;
            if (webrtcSurfaceView != null) {
                webrtcSurfaceView.release();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void p() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4854(0x12f6, float:6.802E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.z4
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.z4
            r1.isDisposed()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.BaseKVSCameraView.p():void");
    }

    public void q(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4855, new Class[]{String.class}, Void.TYPE).isSupported) {
            p();
            this.z4 = this.D4.c(deviceId).c(l.c()).I(new h(this), new a(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: w */
    public /* synthetic */ void x(KVSParamBean kvsParamBean) {
        if (!PatchProxy.proxy(new Object[]{kvsParamBean}, this, changeQuickRedirect, false, 4882, new Class[]{KVSParamBean.class}, Void.TYPE).isSupported) {
            r(kvsParamBean);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: y */
    public /* synthetic */ void z(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 4881, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            getLiveParamFail();
        }
    }

    public void v(String deviceId, KVSParamBean kVSParamBean, WebrtcSurfaceView webrtcSurfaceView, boolean z2) {
        if (!PatchProxy.proxy(new Object[]{deviceId, kVSParamBean, webrtcSurfaceView, new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4856, new Class[]{String.class, KVSParamBean.class, WebrtcSurfaceView.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            KVSParamBean param = kVSParamBean;
            boolean webrtcAudioEnable = z2;
            this.p3 = false;
            w wVar = this.f;
            if (wVar != null) {
                wVar.c(t.CONNECTING);
            }
            PropsBean propsBean = this.p4.props;
            String supportIpv6 = propsBean.supportIpv6;
            String liveType = propsBean.liveType;
            String prefString = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
            boolean isSupport = u();
            f0 n = com.leedarson.manager.a.i().n(deviceId, liveType, supportIpv6, param, isSupport ? f0.r.PRE_LINK : f0.r.LIVE);
            this.y = n;
            if (n != null) {
                n.j3(this.p4.props.getVideoCodesArr());
                this.y.c3(this.p4.props.enableSdes);
                this.y.i3(isSupport);
                com.leedarson.manager.a.i().a(deviceId, this.y);
                webrtcSurfaceView.restoreFirstFrameRendered();
                this.y.H0(this.c, new b(webrtcAudioEnable, webrtcSurfaceView));
            }
        }
    }

    public class b implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;
        final /* synthetic */ WebrtcSurfaceView b;

        b(boolean z, WebrtcSurfaceView webrtcSurfaceView) {
            this.a = z;
            this.b = webrtcSurfaceView;
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4887, new Class[0], Void.TYPE).isSupported) {
                BaseKVSCameraView.this.K("onOpen: ");
                if (BaseKVSCameraView.this.f != null) {
                    BaseKVSCameraView.this.f.c(t.CONNECTED);
                }
                BaseKVSCameraView.this.y.createSdpOffer(new a());
            }
        }

        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4893, new Class[0], Void.TYPE).isSupported) {
                    BaseKVSCameraView baseKVSCameraView = BaseKVSCameraView.this;
                    baseKVSCameraView.K("onAddStream: " + Thread.currentThread());
                    if (BaseKVSCameraView.this.f != null) {
                        BaseKVSCameraView.this.f.c(t.STARTGETSTREAM);
                    }
                    BaseKVSCameraView.this.y.z0(b.this.a);
                    BaseKVSCameraView.this.y.e3(b.this.b);
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 4894, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    BaseKVSCameraView.this.K("onDataChannelStateChange: ");
                    if (state == DataChannel.State.OPEN) {
                        BaseKVSCameraView.this.k();
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 4895, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    BaseKVSCameraView baseKVSCameraView = BaseKVSCameraView.this;
                    baseKVSCameraView.K("onMessage: " + bytes.length);
                }
            }

            public void onError(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 4896, new Class[]{String.class}, Void.TYPE).isSupported) {
                    BaseKVSCameraView.d(BaseKVSCameraView.this);
                }
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 4897, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                    if (iceConnectionState == PeerConnection.IceConnectionState.FAILED || iceConnectionState == PeerConnection.IceConnectionState.CLOSED) {
                        BaseKVSCameraView.d(BaseKVSCameraView.this);
                    }
                }
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4888, new Class[0], Void.TYPE).isSupported) {
                BaseKVSCameraView.this.K("onClose: ");
            }
        }

        public void a(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 4889, new Class[]{Event.class}, Void.TYPE).isSupported) {
                BaseKVSCameraView.d(BaseKVSCameraView.this);
            }
        }

        public void onException(Exception exc) {
            if (!PatchProxy.proxy(new Object[]{exc}, this, changeQuickRedirect, false, 4890, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                BaseKVSCameraView.d(BaseKVSCameraView.this);
            }
        }

        public void e(String message) {
        }

        public void c(String message) {
        }

        public void g(String message) {
        }

        public void d(int stateCode) {
            Object[] objArr = {new Integer(stateCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4891, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (stateCode == -50002) {
                    BaseKVSCameraView.d(BaseKVSCameraView.this);
                    if (BaseKVSCameraView.this.f != null) {
                        BaseKVSCameraView.this.f.a(stateCode);
                    }
                }
            }
        }

        public void onConnected() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4892, new Class[0], Void.TYPE).isSupported) {
                try {
                    BaseKVSCameraView.this.k();
                    if (BaseKVSCameraView.this.f != null) {
                        BaseKVSCameraView.this.f.c(t.STARTGETSTREAM);
                    }
                    this.b.release();
                    this.b.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
                    if (BaseKVSCameraView.this.y != null) {
                        BaseKVSCameraView.this.y.e3(this.b);
                        BaseKVSCameraView.this.y.Q2(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void f() {
        }
    }

    private void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4857, new Class[0], Void.TYPE).isSupported) {
            Message msg = Message.obtain();
            msg.what = 2;
            this.a1.sendMessage(msg);
        }
    }

    public void r(KVSParamBean param) {
        if (!PatchProxy.proxy(new Object[]{param}, this, changeQuickRedirect, false, 4858, new Class[]{KVSParamBean.class}, Void.TYPE).isSupported) {
            this.z = param;
            this.p0 = System.currentTimeMillis();
            v(this.q, param, this.d, false);
        }
    }

    public void getLiveParamFail() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4859, new Class[0], Void.TYPE).isSupported) {
            T();
        }
    }

    public void i(boolean mute) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4860, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p2 = mute;
            f0 f0Var = this.y;
            if (f0Var != null) {
                if (mute) {
                    z2 = false;
                }
                f0Var.z0(z2);
            }
        }
    }

    public void V(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4861, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.A4 = 0;
            Q(deviceId, false);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void n() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4862(0x12fe, float:6.813E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.C4
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.C4
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.BaseKVSCameraView.n():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void o() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4863(0x12ff, float:6.815E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.B4
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.B4
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.BaseKVSCameraView.o():void");
    }

    public void Q(String deviceId, boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, LDSModel.MODEL_BRIGHTNESS_CTRL, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            o();
            n();
            if (!isSleep) {
                this.C4 = this.D4.d(deviceId).c(l.c()).I(g.c, f.c);
                this.B4 = this.D4.k(deviceId, isSleep).G(new j(2, 1500)).c(l.c()).I(new i(this, deviceId), new e(this, deviceId));
                return;
            }
            this.D4.k(this.p4.id, true).G(new j(2, 1500)).c(l.c()).I(d.c, c.c);
        }
    }

    static /* synthetic */ void C(LDSBaseBean ldsBaseBean) {
    }

    static /* synthetic */ void D(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: E */
    public /* synthetic */ void F(String deviceId, LDSBaseBean ldsBaseBean) {
        if (!PatchProxy.proxy(new Object[]{deviceId, ldsBaseBean}, this, changeQuickRedirect, false, 4880, new Class[]{String.class, LDSBaseBean.class}, Void.TYPE).isSupported) {
            if (!ldsBaseBean.checkDataValid()) {
                int i = this.A4 + 1;
                this.A4 = i;
                if (i < 3) {
                    Q(deviceId, false);
                } else {
                    T();
                }
            } else if (Constans.IPC_LIVE_TYPE_LDS.equals(this.p4.props.liveType)) {
                v(deviceId, this.z, this.d, false);
            } else {
                q(deviceId);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: G */
    public /* synthetic */ void H(String deviceId, Throwable th) {
        if (!PatchProxy.proxy(new Object[]{deviceId, th}, this, changeQuickRedirect, false, 4879, new Class[]{String.class, Throwable.class}, Void.TYPE).isSupported) {
            int i = this.A4 + 1;
            this.A4 = i;
            if (i < 3) {
                Q(deviceId, false);
            } else {
                T();
            }
        }
    }

    static /* synthetic */ void I(LDSBaseBean lDSBaseBean) {
        if (!PatchProxy.proxy(new Object[]{lDSBaseBean}, (Object) null, changeQuickRedirect, true, 4878, new Class[]{LDSBaseBean.class}, Void.TYPE).isSupported) {
            timber.log.a.i("wakeUpOrSleep 倒计时休眠指令执行成功", new Object[0]);
        }
    }

    static /* synthetic */ void J(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, (Object) null, changeQuickRedirect, true, 4877, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            timber.log.a.i("wakeUpOrSleep 倒计时休眠指令执行失败", new Object[0]);
        }
    }

    public void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4866, new Class[0], Void.TYPE).isSupported) {
            l();
            R();
        }
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, LDSModel.MODEL_TEMP_CTRL, new Class[0], Void.TYPE).isSupported) {
            WebrtcSurfaceView webrtcSurfaceView = this.d;
            if (webrtcSurfaceView != null) {
                webrtcSurfaceView.clearImage();
                this.d.restoreFirstFrameRendered();
            }
            if (this.y != null) {
                com.leedarson.utils.j.a = false;
            }
        }
    }

    private void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4868, new Class[0], Void.TYPE).isSupported) {
            if (this.y != null) {
                com.leedarson.utils.j.a = false;
                this.d.restoreFirstFrameRendered();
                String path = getContext().getFilesDir() + "/tempCache.jpg";
                Log.d("BaseKVSCameraView", "setNoFirstFrame start: " + path);
                this.y.B0(path, new c(path));
            }
        }
    }

    public class c implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        c(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4898, new Class[0], Void.TYPE).isSupported) {
                BaseKVSCameraView.e(BaseKVSCameraView.this, this.a);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4899, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("BaseKVSCameraView", "setNoFirstFrame onError: ");
            }
        }
    }

    private void S(String picPath) {
        if (!PatchProxy.proxy(new Object[]{picPath}, this, changeQuickRedirect, false, 4869, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.utils.j.f(getContext(), picPath, this.a2);
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4870, new Class[0], Void.TYPE).isSupported) {
            if (u()) {
                j();
                return;
            }
            f0 f0Var = this.y;
            if (f0Var != null) {
                f0Var.I2(false);
            }
        }
    }

    public void setWide(boolean isWide) {
        Object[] objArr = {new Byte(isWide ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4872, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.d.setWide(isWide);
        }
    }

    public class e extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private e() {
        }

        /* synthetic */ e(BaseKVSCameraView x0, a x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4900, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        Log.d("BaseKVSCameraView", "handleMessage ONFIRSTFRAME: " + BaseKVSCameraView.this.f);
                        com.leedarson.utils.j.a = true;
                        BaseKVSCameraView.this.a2.setVisibility(8);
                        boolean unused = BaseKVSCameraView.this.p3 = false;
                        if (BaseKVSCameraView.this.f != null) {
                            BaseKVSCameraView.this.f.d();
                        }
                        BaseKVSCameraView baseKVSCameraView = BaseKVSCameraView.this;
                        baseKVSCameraView.i(baseKVSCameraView.p2);
                        return;
                    case 2:
                        if (BaseKVSCameraView.this.f != null) {
                            BaseKVSCameraView.this.f.b();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void K(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4873, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("BaseKVSCameraView" + this.q).a(msg, new Object[0]);
        }
    }

    public void setOnSurfaceClickListener(d onSurfaceClickListener) {
        this.E4 = onSurfaceClickListener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0023, code lost:
        r2 = r2.props;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean u() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 4874(0x130a, float:6.83E-42)
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
            com.leedarson.bean.IpcDeviceBean r2 = r1.p4
            if (r2 == 0) goto L_0x002e
            com.leedarson.bean.PropsBean r2 = r2.props
            if (r2 == 0) goto L_0x002e
            boolean r2 = r2.isSupportPreCon()
            if (r2 == 0) goto L_0x002e
            r0 = 1
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.BaseKVSCameraView.u():boolean");
    }

    public void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4875, new Class[0], Void.TYPE).isSupported) {
            Log.i("", "changeToLive:" + u());
            if (u()) {
                f0 f0Var = this.y;
                if (f0Var != null) {
                    f0Var.C0(f0.q.LIVING);
                }
                this.F4 = true;
            }
        }
    }

    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4876, new Class[0], Void.TYPE).isSupported) {
            if (u() && this.F4) {
                f0 f0Var = this.y;
                if (f0Var != null) {
                    f0Var.C0(f0.q.IDLE);
                }
                this.F4 = false;
            }
        }
    }
}
