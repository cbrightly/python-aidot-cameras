package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.serviceinterface.utils.DeviceIdUtils;
import com.leedarson.smartcamera.codec.c;
import com.leedarson.smartcamera.listener.i;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class BaseTUTKCameraView extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean a1;
    /* access modifiers changed from: private */
    public boolean a2;
    private Context c;
    private SurfaceView d;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c f;
    /* access modifiers changed from: private */
    public boolean p0;
    /* access modifiers changed from: private */
    public w p1;
    private boolean p2;
    /* access modifiers changed from: private */
    public boolean p3;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.sdk.a q;
    private String x;
    /* access modifiers changed from: private */
    public boolean y;
    private SurfaceHolder.Callback z;

    static /* synthetic */ void g(BaseTUTKCameraView x0, String x1) {
        Class[] clsArr = {BaseTUTKCameraView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 4920, clsArr, Void.TYPE).isSupported) {
            x0.x(x1);
        }
    }

    static /* synthetic */ void h(BaseTUTKCameraView x0, Surface x1) {
        Class[] clsArr = {BaseTUTKCameraView.class, Surface.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 4921, clsArr, Void.TYPE).isSupported) {
            x0.D(x1);
        }
    }

    static /* synthetic */ void i(BaseTUTKCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4922, new Class[]{BaseTUTKCameraView.class}, Void.TYPE).isSupported) {
            x0.E();
        }
    }

    static /* synthetic */ void k(BaseTUTKCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4923, new Class[]{BaseTUTKCameraView.class}, Void.TYPE).isSupported) {
            x0.r();
        }
    }

    static /* synthetic */ void m(BaseTUTKCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4924, new Class[]{BaseTUTKCameraView.class}, Void.TYPE).isSupported) {
            x0.y();
        }
    }

    public BaseTUTKCameraView(@NonNull Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public BaseTUTKCameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTUTKCameraView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.y = false;
        this.z = null;
        this.p0 = false;
        this.a1 = false;
        this.a2 = false;
        this.p2 = false;
        this.p3 = false;
        this.c = context;
        LayoutInflater.from(context).inflate(R$layout.base_tutk_camera_view, this, true);
        v();
        t();
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4901, new Class[0], Void.TYPE).isSupported) {
            this.f = new com.leedarson.smartcamera.codec.c();
            s();
            this.f.u(new a());
        }
    }

    public class a implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void W0(long currentTime, int decFps, int showFps) {
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4925, new Class[0], Void.TYPE).isSupported) {
                boolean unused = BaseTUTKCameraView.this.a2 = true;
                if (BaseTUTKCameraView.this.p1 != null) {
                    BaseTUTKCameraView.this.p1.d();
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

    private void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4902, new Class[0], Void.TYPE).isSupported) {
            this.d = (SurfaceView) findViewById(R$id.video_surface);
            this.z = new f();
            this.d.getHolder().addCallback(this.z);
        }
    }

    public void o(boolean mute) {
        com.leedarson.smartcamera.codec.c cVar;
        Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4903, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (cVar = this.f) != null) {
            cVar.y(mute);
        }
    }

    public class f implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        public f() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 4934, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "surfaceCreated");
                BaseTUTKCameraView.h(BaseTUTKCameraView.this, holder.getSurface());
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int width, int height) {
            Object[] objArr = {holder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4935, clsArr, Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "surfaceChanged");
                BaseTUTKCameraView.this.p(holder.getSurface(), width, height);
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 4936, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "surfaceDestroyed");
                BaseTUTKCameraView.i(BaseTUTKCameraView.this);
            }
        }
    }

    public void u(String id, String str, String str2, String str3, String str4) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{id, str, str2, str3, str4}, this, changeQuickRedirect, false, 4904, clsArr, Void.TYPE).isSupported) {
            String p2pId = str;
            String password = str3;
            String account = str2;
            String dtls = str4;
            this.x = p2pId;
            com.leedarson.manager.a.i().b(id, p2pId);
            com.leedarson.smartcamera.sdk.a tempChannel = com.leedarson.manager.a.i().l(p2pId);
            if (tempChannel == null) {
                this.q = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.q);
            } else if (!account.equals(tempChannel.E0()) || !password.equals(tempChannel.H0())) {
                this.q = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                com.leedarson.manager.a.i().c(p2pId, this.q);
            } else {
                this.q = tempChannel;
            }
            if (this.q != null) {
                if (com.alibaba.android.arouter.utils.e.b(dtls) || !dtls.equals("1")) {
                    this.q.D1(0);
                } else {
                    this.q.D1(1);
                }
                this.q.registerTutkListener(new b(p2pId));
                Context context = this.c;
                if (context != null) {
                    this.q.B1(DeviceIdUtils.getDeviceId(context));
                }
            }
        }
    }

    public class b implements i {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        public void e(int state) {
            if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 4926, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (!BaseTUTKCameraView.this.y) {
                    switch (state) {
                        case -2:
                            BaseTUTKCameraView.m(BaseTUTKCameraView.this);
                            if (BaseTUTKCameraView.this.p1 != null) {
                                BaseTUTKCameraView.this.p1.b();
                                BaseTUTKCameraView.this.p1.a(-2);
                                return;
                            }
                            return;
                        case -1:
                        case 3:
                            BaseTUTKCameraView.m(BaseTUTKCameraView.this);
                            if (BaseTUTKCameraView.this.p1 != null) {
                                BaseTUTKCameraView.this.p1.b();
                                return;
                            }
                            return;
                        case 0:
                            if (BaseTUTKCameraView.this.p1 != null) {
                                BaseTUTKCameraView.this.p1.c(t.CONNECTING);
                                return;
                            }
                            return;
                        case 1:
                            if (BaseTUTKCameraView.this.p1 != null) {
                                BaseTUTKCameraView.this.p1.c(t.CONNECTED);
                            }
                            BaseTUTKCameraView.k(BaseTUTKCameraView.this);
                            com.leedarson.manager.a.i().c(this.a, BaseTUTKCameraView.this.q);
                            BaseTUTKCameraView.this.G();
                            return;
                        case 2:
                            if (BaseTUTKCameraView.this.p1 != null) {
                                BaseTUTKCameraView.this.p1.c(t.STARTGETSTREAM);
                                return;
                            }
                            return;
                        case 5:
                            boolean unused = BaseTUTKCameraView.this.a2 = false;
                            return;
                        default:
                            return;
                    }
                }
            }
        }

        public void b(int code) {
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4927, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (!BaseTUTKCameraView.this.y && BaseTUTKCameraView.this.f != null && BaseTUTKCameraView.this.a1 && !BaseTUTKCameraView.this.p3) {
                    BaseTUTKCameraView.this.f.Z(timestap, data, len, codec);
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4928, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (!BaseTUTKCameraView.this.y && BaseTUTKCameraView.this.f != null && BaseTUTKCameraView.this.a1 && !BaseTUTKCameraView.this.p3) {
                    BaseTUTKCameraView.this.f.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
        }
    }

    public void setOnCameraStateListener(w onCameraStateListener) {
        this.p1 = onCameraStateListener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void s() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4905(0x1329, float:6.873E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.smartcamera.codec.c r1 = r0.f
            if (r1 == 0) goto L_0x0027
            boolean r2 = r0.p0
            if (r2 != 0) goto L_0x0027
            com.leedarson.newui.view.BaseTUTKCameraView$c r2 = new com.leedarson.newui.view.BaseTUTKCameraView$c
            r2.<init>()
            r1.C(r2)
        L_0x0027:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.BaseTUTKCameraView.s():void");
    }

    public class c implements com.leedarson.smartcamera.codec.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4929, new Class[0], Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "createCodec onSuccess: ");
                boolean unused = BaseTUTKCameraView.this.p0 = true;
            }
        }

        public void a(int ret) {
            Object[] objArr = {new Integer(ret)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4930, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                BaseTUTKCameraView baseTUTKCameraView = BaseTUTKCameraView.this;
                BaseTUTKCameraView.g(baseTUTKCameraView, "createCodec onError: " + ret);
            }
        }
    }

    private void A(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 4906, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            x("reStartCodec: " + this.f + " p2pid:" + this.x + " hasCreate:" + this.p0 + " connect:" + w());
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null && surface != null && this.p0) {
                cVar.H(surface, new d());
            }
        }
    }

    public class d implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4931, new Class[0], Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "codecReady onSuccess: ");
                boolean unused = BaseTUTKCameraView.this.a1 = true;
            }
        }
    }

    private void D(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 4907, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            x("startCodec: " + this.f + " p2pid:" + this.x + " hasCreate:" + this.p0);
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null && surface != null && this.p0) {
                if (this.a1) {
                    cVar.J();
                } else {
                    cVar.P(surface, new e());
                }
            }
        }
    }

    public class e implements com.leedarson.smartcamera.codec.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4932, new Class[0], Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "startCodec onSuccess");
                boolean unused = BaseTUTKCameraView.this.a1 = true;
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4933, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                BaseTUTKCameraView.g(BaseTUTKCameraView.this, "startCodec onError");
            }
        }
    }

    private void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4908, new Class[0], Void.TYPE).isSupported) {
            x("stopCodec");
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null && this.p0) {
                if (this.a1) {
                    cVar.G();
                    return;
                }
                cVar.U();
                this.a1 = false;
            }
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4909, new Class[0], Void.TYPE).isSupported) {
            this.p3 = false;
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null && this.p0) {
                cVar.J();
            }
        }
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4910, new Class[0], Void.TYPE).isSupported) {
            this.p3 = true;
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null && this.p0) {
                cVar.G();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0033, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void p(android.view.Surface r9, int r10, int r11) {
        /*
            r8 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            java.lang.Integer r3 = new java.lang.Integer
            r3.<init>(r10)
            r4 = 1
            r1[r4] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r3.<init>(r11)
            r5 = 2
            r1[r5] = r3
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.view.Surface> r0 = android.view.Surface.class
            r6[r2] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r4] = r0
            r6[r5] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4911(0x132f, float:6.882E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0033
            return
        L_0x0033:
            r0 = r8
            com.leedarson.smartcamera.codec.c r1 = r0.f
            if (r1 == 0) goto L_0x0046
            if (r9 == 0) goto L_0x0046
            boolean r2 = r0.p0
            if (r2 == 0) goto L_0x0046
            r1.z(r9, r10, r11)
            com.leedarson.smartcamera.codec.c r1 = r0.f
            r1.J()
        L_0x0046:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.BaseTUTKCameraView.p(android.view.Surface, int, int):void");
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4912, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.sdk.a aVar = this.q;
            if (aVar != null) {
                aVar.x0();
            }
            A(this.d.getHolder().getSurface());
        }
    }

    public void G() {
        com.leedarson.smartcamera.sdk.a aVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4913, new Class[0], Void.TYPE).isSupported && (aVar = this.q) != null) {
            aVar.K1();
        }
    }

    public void F() {
        com.leedarson.smartcamera.sdk.a aVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4914, new Class[0], Void.TYPE).isSupported && (aVar = this.q) != null) {
            aVar.Q1();
        }
    }

    public void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4915, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.sdk.a aVar = this.q;
            if (aVar != null) {
                aVar.Q1();
            }
            com.leedarson.smartcamera.codec.c cVar = this.f;
            if (cVar != null) {
                cVar.I();
            }
        }
    }

    public void setRate(int rate) {
        com.leedarson.smartcamera.sdk.a aVar;
        Object[] objArr = {new Integer(rate)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4917, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (aVar = this.q) != null) {
            aVar.F1(rate);
        }
    }

    public boolean w() {
        return this.p2;
    }

    private void r() {
        this.p2 = true;
    }

    private void y() {
        this.p2 = false;
    }

    private void x(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4919, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.d("BaseTUTKCameraView" + this.x, msg);
        }
    }
}
