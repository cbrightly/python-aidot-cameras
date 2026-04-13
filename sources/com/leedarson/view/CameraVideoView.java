package com.leedarson.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.bumptech.glide.h;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.listener.i;
import com.leedarson.smartcamera.listener.k;
import com.leedarson.smartcamera.listener.n;
import com.leedarson.view.IpcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import org.apache.http.l;
import org.json.JSONObject;
import timber.log.a;

public class CameraVideoView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean A4;
    /* access modifiers changed from: private */
    public boolean B4;
    /* access modifiers changed from: private */
    public boolean C4;
    private Toast D4;
    /* access modifiers changed from: private */
    public String E4;
    private int F4;
    private i G4;
    /* access modifiers changed from: private */
    public boolean a1;
    private boolean a2;
    private IpcSurfaceView c;
    /* access modifiers changed from: private */
    public ImageView d;
    /* access modifiers changed from: private */
    public Button f;
    /* access modifiers changed from: private */
    public GradientDrawable p0;
    /* access modifiers changed from: private */
    public boolean p1;
    private boolean p2;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c p3;
    /* access modifiers changed from: private */
    public String p4;
    /* access modifiers changed from: private */
    public LinearLayout q;
    private String x;
    /* access modifiers changed from: private */
    public g y;
    /* access modifiers changed from: private */
    public f z;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.sdk.a z4;

    public interface f {
        void d();

        void f();
    }

    public interface g {
        boolean a(float f, MotionEvent motionEvent);
    }

    static /* synthetic */ void k(CameraVideoView x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11606, new Class[]{CameraVideoView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.H(x1);
        }
    }

    public CameraVideoView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public CameraVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.p0 = new GradientDrawable();
        this.a1 = false;
        this.p1 = false;
        this.a2 = false;
        this.p2 = true;
        this.A4 = true;
        this.B4 = false;
        this.C4 = false;
        this.E4 = "";
        this.F4 = 1;
        this.G4 = new a();
        RelativeLayout.inflate(context, R$layout.camera_video_view, this);
        this.p3 = new com.leedarson.smartcamera.codec.c();
        w();
    }

    public class a implements i {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void e(int state) {
            if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 11607, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("deviceId", CameraVideoView.this.p4 + " onStateChange:" + state);
                if (state == 1) {
                    boolean unused = CameraVideoView.this.p1 = false;
                    CameraVideoView.k(CameraVideoView.this, state);
                    if (CameraVideoView.this.z4 != null) {
                        CameraVideoView.this.z4.G1(5, (k) null);
                        CameraVideoView.this.z4.J1();
                    }
                } else if (state == -1 || state == 3) {
                    CameraVideoView.k(CameraVideoView.this, state);
                    boolean unused2 = CameraVideoView.this.p1 = true;
                    CameraVideoView.this.f.setVisibility(0);
                    CameraVideoView.this.q.setVisibility(8);
                    String themeColor = SharePreferenceUtils.getPrefString(CameraVideoView.this.getContext(), "themeColor", "");
                    if (!themeColor.isEmpty()) {
                        CameraVideoView.this.p0.setColor(Color.parseColor(themeColor));
                    }
                    CameraVideoView.this.p0.setCornerRadius(50.0f);
                    CameraVideoView.this.f.setBackground(CameraVideoView.this.p0);
                    CameraVideoView.this.f.setText(PubUtils.getString(CameraVideoView.this.getContext(), R$string.video_reconnect));
                } else if (state == -2) {
                    boolean unused3 = CameraVideoView.this.p1 = true;
                    CameraVideoView.this.f.setVisibility(0);
                    CameraVideoView.this.q.setVisibility(8);
                    CameraVideoView.this.p0.setColor(Color.parseColor("#FFFF5858"));
                    CameraVideoView.this.p0.setCornerRadius(50.0f);
                    CameraVideoView.this.f.setBackground(CameraVideoView.this.p0);
                    CameraVideoView.this.f.setText(PubUtils.getString(CameraVideoView.this.getContext(), R$string.video_offline));
                    CameraVideoView cameraVideoView = CameraVideoView.this;
                    cameraVideoView.I(CameraVideoView.this.E4 + " " + PubUtils.getString(CameraVideoView.this.getContext(), R$string.max_connecttion_err));
                } else if (state != 2 && state == 4) {
                    f();
                }
            }
        }

        public void b(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11608, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                CameraVideoView.k(CameraVideoView.this, code);
            }
        }

        private void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11609, new Class[0], Void.TYPE).isSupported) {
                CameraVideoView.this.d.setVisibility(8);
                CameraVideoView.this.q.setVisibility(8);
            }
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11610, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (CameraVideoView.this.p3 != null && !CameraVideoView.this.C4) {
                    CameraVideoView.this.p3.Z(timestap, data, len, codec);
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11611, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (CameraVideoView.this.p3 != null && !CameraVideoView.this.A4 && !CameraVideoView.this.C4) {
                    CameraVideoView.this.p3.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
        }
    }

    private void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11579, new Class[0], Void.TYPE).isSupported) {
            this.c = (IpcSurfaceView) findViewById(R$id.video_surface);
            this.d = (ImageView) findViewById(R$id.preview_img);
            this.f = (Button) findViewById(R$id.state_btn);
            this.q = (LinearLayout) findViewById(R$id.layout_loading);
            this.f.setOnClickListener(new b());
            this.c.getHolder().addCallback(new c());
            IpcSurfaceView ipcSurfaceView = this.c;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setTouchListener(new d());
            }
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11612, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (CameraVideoView.this.z != null) {
                if (CameraVideoView.this.p1) {
                    CameraVideoView.this.z.f();
                    CameraVideoView.this.f.setVisibility(8);
                    CameraVideoView.this.q.setVisibility(0);
                    CameraVideoView.this.z();
                } else if (CameraVideoView.this.a1) {
                    CameraVideoView.this.z.d();
                }
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class c implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 11613, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("surface", "surfaceCreated:");
                if (CameraVideoView.this.p3 == null) {
                    return;
                }
                if (!CameraVideoView.this.B4) {
                    CameraVideoView.this.p3.B(surfaceHolder.getSurface(), true);
                    boolean unused = CameraVideoView.this.B4 = true;
                    return;
                }
                CameraVideoView.this.p3.O(surfaceHolder.getSurface());
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int width, int height) {
            Object[] objArr = {surfaceHolder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11614, clsArr, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("surface", "surfaceChanged" + width + "==" + height);
                if (CameraVideoView.this.p3 != null) {
                    CameraVideoView.this.p3.z(surfaceHolder.getSurface(), width, height);
                    if (CameraVideoView.this.B4) {
                        CameraVideoView.this.p3.J();
                    }
                }
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 11615, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("surface", "surfaceDestroyed");
                if (CameraVideoView.this.p3 != null) {
                    CameraVideoView.this.p3.U();
                }
            }
        }
    }

    public class d implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11616, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (CameraVideoView.this.y != null) {
                CameraVideoView.this.y.a(scale, event);
            }
            return false;
        }
    }

    public void v(String deviceId, String p2pId, String account, String password, int isDTLS) {
        Class<String> cls = String.class;
        Object[] objArr = {deviceId, p2pId, account, password, new Integer(isDTLS)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11580, new Class[]{cls, cls, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            this.p4 = deviceId;
            this.x = p2pId;
            com.leedarson.manager.a.i().b(deviceId, p2pId);
            com.leedarson.smartcamera.sdk.a tempChannel = com.leedarson.manager.a.i().l(p2pId);
            if (tempChannel == null) {
                this.z4 = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.z4);
            } else if (!account.equals(tempChannel.E0()) || !password.equals(tempChannel.H0())) {
                this.z4 = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.z4);
            } else {
                this.z4 = tempChannel;
            }
            this.z4.D1(isDTLS);
            this.z4.registerTutkListener(this.G4);
        }
    }

    public void setP2pId(String p2pId) {
        this.x = p2pId;
    }

    public void setPreviewImg(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 11581, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!url.startsWith(l.DEFAULT_SCHEME_NAME)) {
                url = getContext().getFilesDir().getPath() + "/web/" + url.replace("build", "") + ".jpg";
            }
            ImageView imageView = this.d;
            if (imageView != null) {
                imageView.setVisibility(0);
                ((h) ((h) com.bumptech.glide.b.t(getContext()).i().M0(url).m0(true)).f(com.bumptech.glide.load.engine.i.b)).D0(new e(this.d));
            }
        }
    }

    public class e extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        e(ImageView view) {
            super(view);
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11618, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 11617, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    if (resource.getHeight() > resource.getWidth()) {
                        CameraVideoView.this.d.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        CameraVideoView.this.d.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    CameraVideoView.this.d.setImageBitmap(resource);
                    return;
                }
                CameraVideoView.this.d.setBackgroundColor(CameraVideoView.this.getResources().getColor(R$color.color_gray_mutiscreen));
            }
        }
    }

    public void t() {
        IpcSurfaceView ipcSurfaceView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11582, new Class[0], Void.TYPE).isSupported && (ipcSurfaceView = this.c) != null) {
            ipcSurfaceView.setHasScale(false);
        }
    }

    public void u() {
        IpcSurfaceView ipcSurfaceView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11583, new Class[0], Void.TYPE).isSupported && (ipcSurfaceView = this.c) != null) {
            ipcSurfaceView.setHasScale(true);
        }
    }

    public void setTalkMode(int talkMode) {
        this.F4 = talkMode;
    }

    public int getTalkMode() {
        return this.F4;
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11584, new Class[0], Void.TYPE).isSupported) {
            H(0);
            com.leedarson.smartcamera.sdk.a aVar = this.z4;
            if (aVar != null) {
                aVar.w0();
            }
        }
    }

    public void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11585, new Class[0], Void.TYPE).isSupported) {
            if (this.p3 != null) {
                com.leedarson.smartcamera.utils.e.c(this.x, "createCodec: releaseCodec");
                this.p3.I();
                this.B4 = false;
            }
        }
    }

    public void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11586, new Class[0], Void.TYPE).isSupported) {
            this.C4 = false;
            com.leedarson.smartcamera.codec.c cVar = this.p3;
            if (cVar != null) {
                cVar.J();
            }
            com.leedarson.smartcamera.sdk.a aVar = this.z4;
            if (aVar != null) {
                aVar.J1();
            }
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11587, new Class[0], Void.TYPE).isSupported) {
            if (this.z4 != null) {
                com.leedarson.smartcamera.utils.e.c(this.x, "stopLive flag");
                this.z4.Q1();
            }
            this.C4 = true;
            com.leedarson.smartcamera.codec.c cVar = this.p3;
            if (cVar != null) {
                cVar.G();
            }
        }
    }

    public void s(String path, com.leedarson.smartcamera.codec.e snapListener) {
        Class[] clsArr = {String.class, com.leedarson.smartcamera.codec.e.class};
        if (!PatchProxy.proxy(new Object[]{path, snapListener}, this, changeQuickRedirect, false, 11588, clsArr, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("capture", "");
            com.leedarson.smartcamera.codec.c cVar = this.p3;
            if (cVar != null) {
                cVar.M(path, snapListener);
            }
        }
    }

    public int L(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 11589, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        com.leedarson.smartcamera.codec.c cVar = this.p3;
        if (cVar != null) {
            return cVar.R(path);
        }
        return -1;
    }

    public int O() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11590, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        com.leedarson.smartcamera.codec.c cVar = this.p3;
        if (cVar != null) {
            return cVar.W();
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void M(com.leedarson.smartcamera.listener.n r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.smartcamera.listener.n> r0 = com.leedarson.smartcamera.listener.n.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11591(0x2d47, float:1.6242E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            com.leedarson.smartcamera.sdk.a r1 = r0.z4
            if (r1 == 0) goto L_0x002c
            int r2 = r0.F4
            r1.H1(r2)
            com.leedarson.smartcamera.sdk.a r1 = r0.z4
            r1.O1(r9)
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.view.CameraVideoView.M(com.leedarson.smartcamera.listener.n):void");
    }

    public void P(n listener) {
        com.leedarson.smartcamera.sdk.a aVar;
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 11592, new Class[]{n.class}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.T1(listener);
        }
    }

    public void setMute(boolean isMute) {
        this.A4 = isMute;
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11593, new Class[0], Void.TYPE).isSupported) {
            this.a1 = true;
            if (!this.p1) {
                this.f.setVisibility(0);
                this.p0.setColor(Color.parseColor("#FFFDBA14"));
                this.p0.setCornerRadius(50.0f);
                this.f.setBackground(this.p0);
                this.f.setText(PubUtils.getString(getContext(), R$string.video_standby));
            }
        }
    }

    public void setStandby(boolean standby) {
        this.a1 = standby;
    }

    public void D(int speed) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11594, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.h1(speed);
        }
    }

    public void E(int speed) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11595, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.i1(speed);
        }
    }

    public void F(int speed) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11596, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.j1(speed);
        }
    }

    public void A(int speed) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11597, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.e1(speed);
        }
    }

    public void B(int speed) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11598, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.f1(speed);
        }
    }

    public void C(int speed) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(speed)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11599, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.g1(speed);
        }
    }

    public void setMaxScale(int digitZoom) {
        IpcSurfaceView ipcSurfaceView;
        Object[] objArr = {new Integer(digitZoom)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11600, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (ipcSurfaceView = this.c) != null) {
            ipcSurfaceView.setMaxScale((float) digitZoom);
        }
    }

    public boolean x() {
        return this.p1;
    }

    public void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11601, new Class[0], Void.TYPE).isSupported) {
            if (this.a1) {
                this.a1 = false;
                this.f.setVisibility(8);
                this.q.setVisibility(0);
                z();
            }
        }
    }

    public void setAudioRate(int rate) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(rate)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11603, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.z4) != null) {
            aVar.F1(rate);
        }
    }

    private void H(int state) {
        int code;
        if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 11604, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("CameraVideoView");
            g2.h("sendTutkState:" + state, new Object[0]);
            try {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("deviceId", (Object) this.p4);
                jsonObject2.put("desc", (Object) "");
                switch (state) {
                    case -1:
                        code = 3;
                        break;
                    case 0:
                        code = 1;
                        break;
                    case 1:
                        code = 2;
                        break;
                    default:
                        code = state;
                        break;
                }
                jsonObject2.put("connectStatus", code);
                jsonObject2.put("messageCode", 1001);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("TUTK", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setCameraName(String cameraName) {
        this.E4 = cameraName;
    }

    public void I(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 11605, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                View toastRoot = LayoutInflater.from(getContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                LDSTextView tv2 = (LDSTextView) toastRoot.findViewById(R$id.toast_notice);
                Toast toast = this.D4;
                if (toast != null) {
                    toast.cancel();
                }
                Toast toast2 = new Toast(getContext());
                this.D4 = toast2;
                toast2.setDuration(0);
                this.D4.setGravity(17, 0, 0);
                this.D4.setView(toastRoot);
                tv2.setText(str);
                this.D4.show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setOnTouchListener(g touchListener) {
        this.y = touchListener;
    }

    public void setOnCenterBtnClickListener(f listener) {
        this.z = listener;
    }
}
