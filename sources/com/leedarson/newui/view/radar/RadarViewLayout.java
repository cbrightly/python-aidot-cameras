package com.leedarson.newui.view.radar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.request.target.j;
import com.leedarson.R$anim;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.serviceinterface.MapService;
import com.leedarson.serviceinterface.listener.OnRequestListener;
import com.leedarson.smartcamera.kvswebrtc.beans.LdsRadarData;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import org.json.JSONObject;

public class RadarViewLayout extends LinearLayout {
    public static int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d;
    private int A4;
    private int B4;
    private int C4;
    private int D4;
    private int E4;
    private FrameLayout F4;
    private FrameLayout G4;
    private final long H4 = 300;
    private float I4;
    private boolean J4;
    private boolean K4;
    private boolean L4;
    /* access modifiers changed from: private */
    public ImageView M4;
    /* access modifiers changed from: private */
    public String N4 = "";
    /* access modifiers changed from: private */
    public String O4;
    private int P4 = 1;
    private float Q4 = 1.9f;
    private float R4 = 0.8f;
    private PRIView S4;
    private PRIView T4;
    /* access modifiers changed from: private */
    public HashMap<Integer, List<com.leedarson.smartcamera.kvswebrtc.beans.a>> U4 = new HashMap<>();
    /* access modifiers changed from: private */
    public HashMap<Integer, List<b>> V4 = new HashMap<>();
    private HashMap<Integer, List<com.leedarson.smartcamera.kvswebrtc.beans.a>> W4 = new HashMap<>();
    public HashMap<Integer, Integer> X4 = new HashMap<>();
    private int Y4 = 0;
    /* access modifiers changed from: private */
    public Handler Z4;
    public float a1;
    private h a2;
    private MapService a5;
    private Timer b5;
    private SimpleDateFormat c5;
    private float d5 = 0.4f;
    private long e5 = 300;
    /* access modifiers changed from: private */
    public Bitmap f;
    private long f5 = 100;
    private e g5;
    HashMap<Integer, com.leedarson.newui.view.radar.lib.ekf.a> h5 = new HashMap<>();
    private float i5;
    private float j5;
    private int k5;
    private int l5;
    private boolean m5 = true;
    public int p0;
    public float p1;
    /* access modifiers changed from: private */
    public RadarRangeView p2;
    private int p3;
    private int p4;
    private float q = 0.0f;
    public float x = 1.7777778f;
    public float y = 0.8f;
    public float z;
    private int z4;

    public interface h {
        void a();

        void b();

        void c();
    }

    static /* synthetic */ void h(RadarViewLayout x0, b x1, String x2) {
        Class[] clsArr = {RadarViewLayout.class, b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 5480, clsArr, Void.TYPE).isSupported) {
            x0.H(x1, x2);
        }
    }

    static /* synthetic */ void k(RadarViewLayout x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5481, new Class[]{RadarViewLayout.class}, Void.TYPE).isSupported) {
            x0.B();
        }
    }

    static /* synthetic */ void m(RadarViewLayout x0, String x1) {
        Class[] clsArr = {RadarViewLayout.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5482, clsArr, Void.TYPE).isSupported) {
            x0.C(x1);
        }
    }

    static /* synthetic */ void n(RadarViewLayout x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5483, new Class[]{RadarViewLayout.class}, Void.TYPE).isSupported) {
            x0.J();
        }
    }

    public int getType() {
        return this.P4;
    }

    public void setType(int type) {
        this.P4 = type;
    }

    public e getDragHelper() {
        return this.g5;
    }

    public void setxScaleSize(float xScaleSize) {
        Object[] objArr = {new Float(xScaleSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5450, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.Q4 = xScaleSize;
            g.c("xScaleSize:" + xScaleSize);
        }
    }

    public float getxScaleSize() {
        return this.Q4;
    }

    public void setyScaleSize(float yScaleSize) {
        Object[] objArr = {new Float(yScaleSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5451, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.R4 = yScaleSize;
            g.c("yScaleSize:" + yScaleSize);
        }
    }

    public float getyScaleSize() {
        return this.R4;
    }

    public RadarViewLayout(Context context) {
        super(context);
        u(context, (AttributeSet) null);
    }

    public RadarViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        u(context, attrs);
    }

    public RadarViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        u(context, attrs);
    }

    public void u(Context context, AttributeSet attributeSet) {
        Class[] clsArr = {Context.class, AttributeSet.class};
        if (!PatchProxy.proxy(new Object[]{context, attributeSet}, this, changeQuickRedirect, false, 5452, clsArr, Void.TYPE).isSupported) {
            this.g5 = new e();
            View view = LayoutInflater.from(getContext()).inflate(R$layout.float_player_radar_view, (ViewGroup) null);
            v(context);
            y(view);
            this.c5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            this.Z4 = new a(Looper.getMainLooper());
            this.a5 = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
        }
    }

    public class a extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5484, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                try {
                    int i = msg.what;
                    if (i == 1) {
                        b pointView = (b) msg.obj;
                        if (pointView instanceof View) {
                            if (pointView != null && ((View) pointView).getParent() != null) {
                                RadarViewLayout radarViewLayout = RadarViewLayout.this;
                                radarViewLayout.t(pointView, "收到超过6s显示的消息,移除:" + pointView.getData());
                                com.leedarson.smartcamera.kvswebrtc.beans.a ldsRadarData = pointView.getData();
                                for (Integer id : RadarViewLayout.this.U4.keySet()) {
                                    Iterator<LdsRadarData> iterator = ((List) RadarViewLayout.this.U4.get(id)).iterator();
                                    while (iterator.hasNext()) {
                                        if (ldsRadarData == ((com.leedarson.smartcamera.kvswebrtc.beans.a) iterator.next())) {
                                            iterator.remove();
                                        }
                                    }
                                }
                            } else if (pointView != null && ((View) pointView).getParent() == null) {
                                g.c("超过6s显示,但是该点的parent是空的");
                            }
                        }
                    } else {
                        if (i != 2) {
                            if (i == 3) {
                            }
                        }
                        View view = (View) msg.obj;
                        if (view != null) {
                            view.clearAnimation();
                            view.setVisibility(8);
                        }
                    }
                } catch (Exception e) {
                    g.d("handle remove point...fail:" + e.getMessage());
                }
            }
        }
    }

    private void y(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5453, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.G4 = (FrameLayout) view.findViewById(R$id.radar_view_container);
            ImageView imageView = (ImageView) view.findViewById(R$id.radar_imageview);
            this.M4 = imageView;
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            this.k5 = this.p3;
            this.l5 = this.p4;
            addView(view);
            setOnClickListener(new a(this));
            this.p2 = (RadarRangeView) view.findViewById(R$id.test_range_circle_view);
            this.S4 = (PRIView) view.findViewById(R$id.leftPri);
            this.T4 = (PRIView) view.findViewById(R$id.rightPri);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: z */
    public /* synthetic */ void A(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5479, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (!this.K4) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        } else if (this.J4) {
            h hVar = this.a2;
            if (hVar != null) {
                hVar.a();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        } else {
            O();
            if (this.a2 != null) {
                g.a("显示播放器右上角");
                this.a2.c();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void v(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5454, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.p3 = c.A;
            this.p4 = c.B;
            this.z4 = c.C;
            this.A4 = c.D;
            this.D4 = com.leedarson.view.a.a(context, 12.0f);
            this.E4 = com.leedarson.view.a.a(context, 18.0f);
            c = com.leedarson.view.a.a(context, 23.0f);
            d = com.leedarson.view.a.a(context, 81.0f);
        }
    }

    public void F(Activity activity, String deviceId) {
        if (!PatchProxy.proxy(new Object[]{activity, deviceId}, this, changeQuickRedirect, false, 5455, new Class[]{Activity.class, String.class}, Void.TYPE).isSupported) {
            this.O4 = deviceId;
            setVisibility(0);
            boolean z2 = this.K4;
            if (z2) {
                this.g5.g(activity, z2, this.F4, this);
            } else {
                O();
            }
        }
    }

    public void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5456, new Class[0], Void.TYPE).isSupported) {
            setVisibility(8);
            this.g5.a();
            this.J4 = false;
            h hVar = this.a2;
            if (hVar != null) {
                hVar.b();
            }
        }
    }

    public void setTailRadiusScaleSize(float tailRadiusScaleSize) {
        this.d5 = tailRadiusScaleSize;
    }

    public float getTailRadiusScaleSize() {
        return this.d5;
    }

    public long getHeadShowAnimationDuration() {
        return this.e5;
    }

    public long getTailDismissAnimationDuration() {
        return this.f5;
    }

    public void setHeadShowAnimationDuration(long headShowAnimationDuration) {
        this.e5 = headShowAnimationDuration;
    }

    public void setTailDismissAnimationDuration(long tailDismissAnimationDuration) {
        this.f5 = tailDismissAnimationDuration;
    }

    public void setPlayerViewLayout(FrameLayout playerViewLayout) {
        this.F4 = playerViewLayout;
    }

    public void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5457, new Class[0], Void.TYPE).isSupported) {
            g.a("destory...");
            HashMap<Integer, List<com.leedarson.smartcamera.kvswebrtc.beans.a>> hashMap = this.U4;
            if (hashMap != null) {
                hashMap.clear();
            }
            HashMap<Integer, List<b>> hashMap2 = this.V4;
            if (hashMap2 != null) {
                hashMap2.clear();
            }
            HashMap<Integer, Integer> hashMap3 = this.X4;
            if (hashMap3 != null) {
                hashMap3.clear();
            }
            RadarRangeView radarRangeView = this.p2;
            if (radarRangeView != null) {
                radarRangeView.removeAllViews();
            }
            HashMap<Integer, List<com.leedarson.smartcamera.kvswebrtc.beans.a>> hashMap4 = this.W4;
            if (hashMap4 != null) {
                hashMap4.clear();
            }
            if (this.Z4 != null) {
                g.a("destory...MSG_CHECK_POINT_REMOVE...");
                this.Z4.removeMessages(1);
                this.Z4.removeMessages(2);
                this.Z4.removeMessages(3);
                this.Z4.removeCallbacksAndMessages((Object) null);
            }
            this.Y4 = 0;
            R();
        }
    }

    /* JADX WARNING: type inference failed for: r8v19, types: [com.leedarson.newui.view.radar.RadarPointView] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void o(java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a> r23) {
        /*
            r22 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r23
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.util.List> r2 = java.util.List.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5458(0x1552, float:7.648E-42)
            r2 = r22
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001e
            return
        L_0x001e:
            r1 = r22
            r2 = r23
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ Exception -> 0x059f }
            r3.<init>()     // Catch:{ Exception -> 0x059f }
            java.util.Iterator r4 = r2.iterator()     // Catch:{ Exception -> 0x059f }
        L_0x002b:
            boolean r5 = r4.hasNext()     // Catch:{ Exception -> 0x059f }
            r9 = 1157234688(0x44fa0000, float:2000.0)
            r10 = -990248960(0xffffffffc4fa0000, float:-2000.0)
            r11 = 2
            if (r5 == 0) goto L_0x01ff
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r5 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r5     // Catch:{ Exception -> 0x059f }
            float r12 = r5.f     // Catch:{ Exception -> 0x059f }
            int r10 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            r13 = 1000(0x3e8, double:4.94E-321)
            if (r10 != 0) goto L_0x0075
            android.os.Handler r6 = r1.Z4     // Catch:{ Exception -> 0x059f }
            r6.removeMessages(r11)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.PRIView r6 = r1.S4     // Catch:{ Exception -> 0x059f }
            int r6 = r6.getVisibility()     // Catch:{ Exception -> 0x059f }
            if (r6 == 0) goto L_0x0065
            com.leedarson.newui.view.radar.PRIView r6 = r1.S4     // Catch:{ Exception -> 0x059f }
            r6.setVisibility(r8)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.PRIView r6 = r1.S4     // Catch:{ Exception -> 0x059f }
            android.content.Context r7 = r1.getContext()     // Catch:{ Exception -> 0x059f }
            int r9 = com.leedarson.R$anim.priview_breath_alpha     // Catch:{ Exception -> 0x059f }
            android.view.animation.Animation r7 = android.view.animation.AnimationUtils.loadAnimation(r7, r9)     // Catch:{ Exception -> 0x059f }
            r6.startAnimation(r7)     // Catch:{ Exception -> 0x059f }
        L_0x0065:
            android.os.Message r6 = android.os.Message.obtain()     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.PRIView r7 = r1.S4     // Catch:{ Exception -> 0x059f }
            r6.obj = r7     // Catch:{ Exception -> 0x059f }
            r6.what = r11     // Catch:{ Exception -> 0x059f }
            android.os.Handler r7 = r1.Z4     // Catch:{ Exception -> 0x059f }
            r7.sendMessageDelayed(r6, r13)     // Catch:{ Exception -> 0x059f }
            goto L_0x002b
        L_0x0075:
            int r9 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r9 != 0) goto L_0x00ab
            android.os.Handler r6 = r1.Z4     // Catch:{ Exception -> 0x059f }
            r7 = 3
            r6.removeMessages(r7)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.PRIView r6 = r1.T4     // Catch:{ Exception -> 0x059f }
            int r6 = r6.getVisibility()     // Catch:{ Exception -> 0x059f }
            if (r6 == 0) goto L_0x009b
            com.leedarson.newui.view.radar.PRIView r6 = r1.T4     // Catch:{ Exception -> 0x059f }
            r6.setVisibility(r8)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.PRIView r6 = r1.T4     // Catch:{ Exception -> 0x059f }
            android.content.Context r9 = r1.getContext()     // Catch:{ Exception -> 0x059f }
            int r10 = com.leedarson.R$anim.priview_breath_alpha     // Catch:{ Exception -> 0x059f }
            android.view.animation.Animation r9 = android.view.animation.AnimationUtils.loadAnimation(r9, r10)     // Catch:{ Exception -> 0x059f }
            r6.startAnimation(r9)     // Catch:{ Exception -> 0x059f }
        L_0x009b:
            android.os.Message r6 = android.os.Message.obtain()     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.PRIView r9 = r1.T4     // Catch:{ Exception -> 0x059f }
            r6.obj = r9     // Catch:{ Exception -> 0x059f }
            r6.what = r7     // Catch:{ Exception -> 0x059f }
            android.os.Handler r7 = r1.Z4     // Catch:{ Exception -> 0x059f }
            r7.sendMessageDelayed(r6, r13)     // Catch:{ Exception -> 0x059f }
            goto L_0x002b
        L_0x00ab:
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r9 = r1.W4     // Catch:{ Exception -> 0x059f }
            int r10 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x059f }
            boolean r9 = r9.containsKey(r10)     // Catch:{ Exception -> 0x059f }
            if (r9 == 0) goto L_0x00fc
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r9 = r1.W4     // Catch:{ Exception -> 0x059f }
            int r10 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ Exception -> 0x059f }
            java.util.List r9 = (java.util.List) r9     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r10 = r1.W4     // Catch:{ Exception -> 0x059f }
            int r12 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r10 = r10.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r10 = (java.util.List) r10     // Catch:{ Exception -> 0x059f }
            int r10 = r10.size()     // Catch:{ Exception -> 0x059f }
            int r10 = r10 - r0
            java.lang.Object r9 = r9.get(r10)     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r9 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r9     // Catch:{ Exception -> 0x059f }
            boolean r10 = r9.u     // Catch:{ Exception -> 0x059f }
            if (r10 != 0) goto L_0x00e6
            r10 = r0
            goto L_0x00e7
        L_0x00e6:
            r10 = r8
        L_0x00e7:
            r5.u = r10     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r10 = r1.W4     // Catch:{ Exception -> 0x059f }
            int r12 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r10 = r10.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r10 = (java.util.List) r10     // Catch:{ Exception -> 0x059f }
            r10.add(r5)     // Catch:{ Exception -> 0x059f }
            goto L_0x0111
        L_0x00fc:
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Exception -> 0x059f }
            r9.<init>()     // Catch:{ Exception -> 0x059f }
            r5.u = r0     // Catch:{ Exception -> 0x059f }
            r9.add(r5)     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r10 = r1.W4     // Catch:{ Exception -> 0x059f }
            int r12 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x059f }
            r10.put(r12, r9)     // Catch:{ Exception -> 0x059f }
        L_0x0111:
            double r9 = r1.p(r5)     // Catch:{ Exception -> 0x059f }
            int r12 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x059f }
            boolean r12 = r3.containsKey(r12)     // Catch:{ Exception -> 0x059f }
            if (r12 == 0) goto L_0x0131
            int r12 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r3.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r12 = (java.util.List) r12     // Catch:{ Exception -> 0x059f }
            r12.add(r5)     // Catch:{ Exception -> 0x059f }
            goto L_0x0142
        L_0x0131:
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ Exception -> 0x059f }
            r12.<init>()     // Catch:{ Exception -> 0x059f }
            r12.add(r5)     // Catch:{ Exception -> 0x059f }
            int r13 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x059f }
            r3.put(r13, r12)     // Catch:{ Exception -> 0x059f }
        L_0x0142:
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r12 = r1.V4     // Catch:{ Exception -> 0x059f }
            int r13 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r12.get(r13)     // Catch:{ Exception -> 0x059f }
            java.util.List r12 = (java.util.List) r12     // Catch:{ Exception -> 0x059f }
            if (r12 == 0) goto L_0x01fb
            int r13 = r12.size()     // Catch:{ Exception -> 0x059f }
            if (r13 <= 0) goto L_0x01fb
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r13 = r1.X4     // Catch:{ Exception -> 0x059f }
            int r14 = r5.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r13 = r13.get(r14)     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r13 = (java.lang.Integer) r13     // Catch:{ Exception -> 0x059f }
            int r13 = r13.intValue()     // Catch:{ Exception -> 0x059f }
            int[] r14 = com.leedarson.newui.view.radar.c.a(r13)     // Catch:{ Exception -> 0x059f }
            int r15 = r14.length     // Catch:{ Exception -> 0x059f }
            int r15 = r15 - r11
            int r11 = r12.size()     // Catch:{ Exception -> 0x059f }
            int r11 = r11 - r0
        L_0x0175:
            if (r11 < 0) goto L_0x01fb
            java.lang.Object r16 = r12.get(r11)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.b r16 = (com.leedarson.newui.view.radar.b) r16     // Catch:{ Exception -> 0x059f }
            r23 = r16
            if (r15 > 0) goto L_0x0182
            r15 = 0
        L_0x0182:
            com.leedarson.smartcamera.kvswebrtc.beans.a r8 = r23.getData()     // Catch:{ Exception -> 0x059f }
            r0 = r14[r15]     // Catch:{ Exception -> 0x059f }
            r8.p = r0     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r0 = r23.getData()     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r8 = r23.getData()     // Catch:{ Exception -> 0x059f }
            float r8 = r8.o     // Catch:{ Exception -> 0x059f }
            float r6 = r1.d5     // Catch:{ Exception -> 0x059f }
            float r8 = r8 * r6
            r0.n = r8     // Catch:{ Exception -> 0x059f }
            int r15 = r15 + -1
            r0 = r23
            boolean r6 = r0 instanceof com.leedarson.newui.view.radar.RadarPointView     // Catch:{ Exception -> 0x059f }
            if (r6 == 0) goto L_0x01a8
            r6 = r0
            com.leedarson.newui.view.radar.RadarPointView r6 = (com.leedarson.newui.view.radar.RadarPointView) r6     // Catch:{ Exception -> 0x059f }
            r6.c()     // Catch:{ Exception -> 0x059f }
            goto L_0x01f5
        L_0x01a8:
            boolean r6 = r0 instanceof com.leedarson.newui.view.radar.RadarFootImageView     // Catch:{ Exception -> 0x059f }
            if (r6 == 0) goto L_0x01f5
            r6 = r0
            com.leedarson.newui.view.radar.RadarFootImageView r6 = (com.leedarson.newui.view.radar.RadarFootImageView) r6     // Catch:{ Exception -> 0x059f }
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()     // Catch:{ Exception -> 0x059f }
            android.widget.FrameLayout$LayoutParams r6 = (android.widget.FrameLayout.LayoutParams) r6     // Catch:{ Exception -> 0x059f }
            int r7 = r1.getRadarFootWidth()     // Catch:{ Exception -> 0x059f }
            float r7 = (float) r7     // Catch:{ Exception -> 0x059f }
            float r8 = com.leedarson.newui.view.radar.c.b(r15)     // Catch:{ Exception -> 0x059f }
            float r7 = r7 * r8
            int r7 = (int) r7     // Catch:{ Exception -> 0x059f }
            r6.width = r7     // Catch:{ Exception -> 0x059f }
            r6.height = r7     // Catch:{ Exception -> 0x059f }
            r7 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r18 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r18 == 0) goto L_0x01d9
            int r7 = r12.size()     // Catch:{ Exception -> 0x059f }
            r8 = 1
            int r7 = r7 - r8
            if (r11 != r7) goto L_0x01d9
            r7 = r0
            com.leedarson.newui.view.radar.RadarFootImageView r7 = (com.leedarson.newui.view.radar.RadarFootImageView) r7     // Catch:{ Exception -> 0x059f }
            float r8 = (float) r9     // Catch:{ Exception -> 0x059f }
            r7.setRotation(r8)     // Catch:{ Exception -> 0x059f }
        L_0x01d9:
            r7 = r0
            com.leedarson.newui.view.radar.RadarFootImageView r7 = (com.leedarson.newui.view.radar.RadarFootImageView) r7     // Catch:{ Exception -> 0x059f }
            com.devs.vectorchildfinder.VectorDrawableCompat$b r7 = r7.getFullPath()     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r8 = r0.getData()     // Catch:{ Exception -> 0x059f }
            int r8 = r8.p     // Catch:{ Exception -> 0x059f }
            r7.g(r8)     // Catch:{ Exception -> 0x059f }
            r7 = r0
            com.leedarson.newui.view.radar.RadarFootImageView r7 = (com.leedarson.newui.view.radar.RadarFootImageView) r7     // Catch:{ Exception -> 0x059f }
            r7.setLayoutParams(r6)     // Catch:{ Exception -> 0x059f }
            r7 = r0
            com.leedarson.newui.view.radar.RadarFootImageView r7 = (com.leedarson.newui.view.radar.RadarFootImageView) r7     // Catch:{ Exception -> 0x059f }
            r7.invalidate()     // Catch:{ Exception -> 0x059f }
        L_0x01f5:
            int r11 = r11 + -1
            r0 = 1
            r8 = 0
            goto L_0x0175
        L_0x01fb:
            r0 = 1
            r8 = 0
            goto L_0x002b
        L_0x01ff:
            java.util.Set r0 = r3.entrySet()     // Catch:{ Exception -> 0x059f }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x059f }
        L_0x0207:
            boolean r4 = r0.hasNext()     // Catch:{ Exception -> 0x059f }
            if (r4 == 0) goto L_0x032d
            java.lang.Object r4 = r0.next()     // Catch:{ Exception -> 0x059f }
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r5 = r4.getValue()     // Catch:{ Exception -> 0x059f }
            java.util.List r5 = (java.util.List) r5     // Catch:{ Exception -> 0x059f }
            int r5 = r5.size()     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r6 = r1.U4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r7 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            boolean r6 = r6.containsKey(r7)     // Catch:{ Exception -> 0x059f }
            r7 = 8
            if (r6 == 0) goto L_0x0277
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r6 = r1.U4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r6 = r6.get(r8)     // Catch:{ Exception -> 0x059f }
            java.util.List r6 = (java.util.List) r6     // Catch:{ Exception -> 0x059f }
            int r6 = r6.size()     // Catch:{ Exception -> 0x059f }
            int r6 = r6 + r5
            if (r6 <= r7) goto L_0x0277
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r6 = r1.U4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r6 = r6.get(r8)     // Catch:{ Exception -> 0x059f }
            java.util.List r6 = (java.util.List) r6     // Catch:{ Exception -> 0x059f }
            int r6 = r6.size()     // Catch:{ Exception -> 0x059f }
            int r6 = r6 + r5
            int r6 = r6 - r7
            r8 = 0
        L_0x0251:
            if (r8 >= r6) goto L_0x0277
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r12 = r1.U4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r13 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r12.get(r13)     // Catch:{ Exception -> 0x059f }
            java.util.List r12 = (java.util.List) r12     // Catch:{ Exception -> 0x059f }
            int r12 = r12.size()     // Catch:{ Exception -> 0x059f }
            if (r12 <= r8) goto L_0x0274
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r12 = r1.U4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r13 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r12.get(r13)     // Catch:{ Exception -> 0x059f }
            java.util.List r12 = (java.util.List) r12     // Catch:{ Exception -> 0x059f }
            r12.remove(r8)     // Catch:{ Exception -> 0x059f }
        L_0x0274:
            int r8 = r8 + 1
            goto L_0x0251
        L_0x0277:
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r6 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            boolean r6 = r6.containsKey(r8)     // Catch:{ Exception -> 0x059f }
            if (r6 == 0) goto L_0x032b
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r6 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r6 = r6.get(r8)     // Catch:{ Exception -> 0x059f }
            java.util.List r6 = (java.util.List) r6     // Catch:{ Exception -> 0x059f }
            int r6 = r6.size()     // Catch:{ Exception -> 0x059f }
            int r6 = r6 + r5
            if (r6 <= r7) goto L_0x032b
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r6 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r6 = r6.get(r8)     // Catch:{ Exception -> 0x059f }
            java.util.List r6 = (java.util.List) r6     // Catch:{ Exception -> 0x059f }
            int r6 = r6.size()     // Catch:{ Exception -> 0x059f }
            int r6 = r6 + r5
            int r6 = r6 - r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x059f }
            r7.<init>()     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = "id:"
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = ",当前已显示:"
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r8 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r8.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r8 = (java.util.List) r8     // Catch:{ Exception -> 0x059f }
            int r8 = r8.size()     // Catch:{ Exception -> 0x059f }
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = "个点,当前又来了:"
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            r7.append(r5)     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = "个点，超过8个了，移除已显示的"
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            r7.append(r6)     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = "个点"
            r7.append(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.g.a(r7)     // Catch:{ Exception -> 0x059f }
            r7 = 0
        L_0x02ee:
            if (r7 >= r6) goto L_0x032b
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r8 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r8.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r8 = (java.util.List) r8     // Catch:{ Exception -> 0x059f }
            int r8 = r8.size()     // Catch:{ Exception -> 0x059f }
            if (r8 <= r7) goto L_0x0328
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r8 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r8.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r8 = (java.util.List) r8     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.b r8 = (com.leedarson.newui.view.radar.b) r8     // Catch:{ Exception -> 0x059f }
            java.lang.String r12 = "将超过8个，移除"
            r1.H(r8, r12)     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r8 = r1.V4     // Catch:{ Exception -> 0x059f }
            java.lang.Object r12 = r4.getKey()     // Catch:{ Exception -> 0x059f }
            java.lang.Object r8 = r8.get(r12)     // Catch:{ Exception -> 0x059f }
            java.util.List r8 = (java.util.List) r8     // Catch:{ Exception -> 0x059f }
            r8.remove(r7)     // Catch:{ Exception -> 0x059f }
        L_0x0328:
            int r7 = r7 + 1
            goto L_0x02ee
        L_0x032b:
            goto L_0x0207
        L_0x032d:
            r0 = 0
        L_0x032e:
            int r4 = r2.size()     // Catch:{ Exception -> 0x059f }
            if (r0 >= r4) goto L_0x059e
            java.lang.Object r4 = r2.get(r0)     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r4 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r4     // Catch:{ Exception -> 0x059f }
            float r5 = r4.f     // Catch:{ Exception -> 0x059f }
            int r6 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r6 == 0) goto L_0x0593
            int r5 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r5 != 0) goto L_0x0348
            r13 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x0595
        L_0x0348:
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r5 = r1.U4     // Catch:{ Exception -> 0x059f }
            int r6 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x059f }
            boolean r5 = r5.containsKey(r6)     // Catch:{ Exception -> 0x059f }
            if (r5 == 0) goto L_0x037c
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r5 = r1.U4     // Catch:{ Exception -> 0x059f }
            int r6 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r5 = r5.get(r6)     // Catch:{ Exception -> 0x059f }
            java.util.List r5 = (java.util.List) r5     // Catch:{ Exception -> 0x059f }
            int r5 = r5.size()     // Catch:{ Exception -> 0x059f }
            if (r5 <= 0) goto L_0x037c
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r5 = r1.U4     // Catch:{ Exception -> 0x059f }
            int r6 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r5 = r5.get(r6)     // Catch:{ Exception -> 0x059f }
            java.util.List r5 = (java.util.List) r5     // Catch:{ Exception -> 0x059f }
            r5.add(r4)     // Catch:{ Exception -> 0x059f }
            goto L_0x038f
        L_0x037c:
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x059f }
            r5.<init>()     // Catch:{ Exception -> 0x059f }
            r5.add(r4)     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r6 = r1.U4     // Catch:{ Exception -> 0x059f }
            int r7 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x059f }
            r6.put(r7, r5)     // Catch:{ Exception -> 0x059f }
        L_0x038f:
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r5 = r1.X4     // Catch:{ Exception -> 0x059f }
            int r6 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x059f }
            boolean r5 = r5.containsKey(r6)     // Catch:{ Exception -> 0x059f }
            if (r5 == 0) goto L_0x03b3
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r5 = r1.X4     // Catch:{ Exception -> 0x059f }
            int r6 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r5 = r5.get(r6)     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch:{ Exception -> 0x059f }
            int r5 = r5.intValue()     // Catch:{ Exception -> 0x059f }
            r4.t = r5     // Catch:{ Exception -> 0x059f }
            r5 = 0
            goto L_0x03c7
        L_0x03b3:
            int r5 = r1.Y4     // Catch:{ Exception -> 0x059f }
            int[][] r6 = com.leedarson.newui.view.radar.c.h     // Catch:{ Exception -> 0x059f }
            int r6 = r6.length     // Catch:{ Exception -> 0x059f }
            if (r5 < r6) goto L_0x03be
            r5 = 0
            r1.Y4 = r5     // Catch:{ Exception -> 0x059f }
            goto L_0x03bf
        L_0x03be:
            r5 = 0
        L_0x03bf:
            int r6 = r1.Y4     // Catch:{ Exception -> 0x059f }
            r4.t = r6     // Catch:{ Exception -> 0x059f }
            int r6 = r6 + 1
            r1.Y4 = r6     // Catch:{ Exception -> 0x059f }
        L_0x03c7:
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r6 = r1.X4     // Catch:{ Exception -> 0x059f }
            int r7 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x059f }
            int r8 = r4.t     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x059f }
            r6.put(r7, r8)     // Catch:{ Exception -> 0x059f }
            r6 = 0
            int r7 = r1.getRadarPointRadius()     // Catch:{ Exception -> 0x059f }
            float r8 = r1.a1     // Catch:{ Exception -> 0x059f }
            float r12 = r1.Q4     // Catch:{ Exception -> 0x059f }
            float r8 = r8 * r12
            float r12 = r1.p1     // Catch:{ Exception -> 0x059f }
            float r13 = r1.R4     // Catch:{ Exception -> 0x059f }
            float r12 = r12 * r13
            r4.a(r8, r12)     // Catch:{ Exception -> 0x059f }
            int r8 = r1.P4     // Catch:{ Exception -> 0x059f }
            r12 = 1
            if (r8 != r12) goto L_0x0410
            com.leedarson.newui.view.radar.RadarPointView r8 = new com.leedarson.newui.view.radar.RadarPointView     // Catch:{ Exception -> 0x059f }
            android.content.Context r12 = r1.getContext()     // Catch:{ Exception -> 0x059f }
            r8.<init>(r12)     // Catch:{ Exception -> 0x059f }
            r6 = r8
            float r8 = (float) r7     // Catch:{ Exception -> 0x059f }
            r4.n = r8     // Catch:{ Exception -> 0x059f }
            float r8 = (float) r7     // Catch:{ Exception -> 0x059f }
            r4.o = r8     // Catch:{ Exception -> 0x059f }
            int r8 = r4.t     // Catch:{ Exception -> 0x059f }
            int[] r8 = com.leedarson.newui.view.radar.c.a(r8)     // Catch:{ Exception -> 0x059f }
            int r12 = r8.length     // Catch:{ Exception -> 0x059f }
            r13 = 1
            int r12 = r12 - r13
            r12 = r8[r12]     // Catch:{ Exception -> 0x059f }
            r4.p = r12     // Catch:{ Exception -> 0x059f }
            r13 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x0518
        L_0x0410:
            r13 = r12
            if (r8 != r11) goto L_0x0516
            com.leedarson.newui.view.radar.RadarFootImageView r8 = new com.leedarson.newui.view.radar.RadarFootImageView     // Catch:{ Exception -> 0x059f }
            android.content.Context r12 = r1.getContext()     // Catch:{ Exception -> 0x059f }
            r8.<init>(r12)     // Catch:{ Exception -> 0x059f }
            int r12 = r1.getRadarFootWidth()     // Catch:{ Exception -> 0x059f }
            r14 = r12
            android.widget.FrameLayout$LayoutParams r15 = new android.widget.FrameLayout$LayoutParams     // Catch:{ Exception -> 0x059f }
            r15.<init>(r12, r14)     // Catch:{ Exception -> 0x059f }
            r5 = 80
            r15.gravity = r5     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r5 = r1.s(r4)     // Catch:{ Exception -> 0x059f }
            if (r5 == 0) goto L_0x04bc
            float r9 = r5.g     // Catch:{ Exception -> 0x059f }
            float r10 = r4.g     // Catch:{ Exception -> 0x059f }
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x04bc
            float r9 = r4.f     // Catch:{ Exception -> 0x059f }
            float r10 = r5.f     // Catch:{ Exception -> 0x059f }
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 < 0) goto L_0x0493
            boolean r9 = r4.u     // Catch:{ Exception -> 0x059f }
            if (r9 == 0) goto L_0x046a
            float r9 = r1.q     // Catch:{ Exception -> 0x059f }
            float r10 = r4.l     // Catch:{ Exception -> 0x059f }
            float r9 = r9 + r10
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            int r9 = java.lang.Math.abs(r9)     // Catch:{ Exception -> 0x059f }
            r15.leftMargin = r9     // Catch:{ Exception -> 0x059f }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x059f }
            r9.<init>()     // Catch:{ Exception -> 0x059f }
            java.lang.String r10 = "往回走,显示左脚,left:"
            r9.append(r10)     // Catch:{ Exception -> 0x059f }
            int r10 = r15.leftMargin     // Catch:{ Exception -> 0x059f }
            r9.append(r10)     // Catch:{ Exception -> 0x059f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.g.a(r9)     // Catch:{ Exception -> 0x059f }
            r19 = r14
            goto L_0x04e1
        L_0x046a:
            float r9 = r1.q     // Catch:{ Exception -> 0x059f }
            float r10 = r4.l     // Catch:{ Exception -> 0x059f }
            float r9 = r9 + r10
            int r10 = r12 / 2
            float r10 = (float) r10     // Catch:{ Exception -> 0x059f }
            float r9 = r9 - r10
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            int r9 = java.lang.Math.abs(r9)     // Catch:{ Exception -> 0x059f }
            r15.leftMargin = r9     // Catch:{ Exception -> 0x059f }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x059f }
            r9.<init>()     // Catch:{ Exception -> 0x059f }
            java.lang.String r10 = "往回走,显示右脚,left:"
            r9.append(r10)     // Catch:{ Exception -> 0x059f }
            int r10 = r15.leftMargin     // Catch:{ Exception -> 0x059f }
            r9.append(r10)     // Catch:{ Exception -> 0x059f }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.g.a(r9)     // Catch:{ Exception -> 0x059f }
            r19 = r14
            goto L_0x04e1
        L_0x0493:
            boolean r9 = r4.u     // Catch:{ Exception -> 0x059f }
            if (r9 == 0) goto L_0x04a6
            float r9 = r1.q     // Catch:{ Exception -> 0x059f }
            float r10 = r4.l     // Catch:{ Exception -> 0x059f }
            float r9 = r9 + r10
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            int r9 = java.lang.Math.abs(r9)     // Catch:{ Exception -> 0x059f }
            r15.leftMargin = r9     // Catch:{ Exception -> 0x059f }
            r19 = r14
            goto L_0x04e1
        L_0x04a6:
            float r9 = r1.q     // Catch:{ Exception -> 0x059f }
            float r10 = r4.l     // Catch:{ Exception -> 0x059f }
            float r9 = r9 + r10
            double r9 = (double) r9     // Catch:{ Exception -> 0x059f }
            r19 = r14
            double r13 = (double) r12     // Catch:{ Exception -> 0x059f }
            r20 = 4609434218613702656(0x3ff8000000000000, double:1.5)
            double r13 = r13 * r20
            double r9 = r9 - r13
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            int r9 = java.lang.Math.abs(r9)     // Catch:{ Exception -> 0x059f }
            r15.leftMargin = r9     // Catch:{ Exception -> 0x059f }
            goto L_0x04e1
        L_0x04bc:
            r19 = r14
            boolean r9 = r4.u     // Catch:{ Exception -> 0x059f }
            if (r9 == 0) goto L_0x04d1
            float r9 = r1.q     // Catch:{ Exception -> 0x059f }
            float r10 = r4.l     // Catch:{ Exception -> 0x059f }
            float r9 = r9 + r10
            float r10 = (float) r12     // Catch:{ Exception -> 0x059f }
            float r9 = r9 - r10
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            int r9 = java.lang.Math.abs(r9)     // Catch:{ Exception -> 0x059f }
            r15.leftMargin = r9     // Catch:{ Exception -> 0x059f }
            goto L_0x04e1
        L_0x04d1:
            float r9 = r1.q     // Catch:{ Exception -> 0x059f }
            float r10 = r4.l     // Catch:{ Exception -> 0x059f }
            float r9 = r9 + r10
            int r10 = r12 / 2
            float r10 = (float) r10     // Catch:{ Exception -> 0x059f }
            float r9 = r9 - r10
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            int r9 = java.lang.Math.abs(r9)     // Catch:{ Exception -> 0x059f }
            r15.leftMargin = r9     // Catch:{ Exception -> 0x059f }
        L_0x04e1:
            double r9 = r4.v     // Catch:{ Exception -> 0x059f }
            r13 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r17 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r17 == 0) goto L_0x04ed
            float r9 = (float) r9     // Catch:{ Exception -> 0x059f }
            r8.setRotation(r9)     // Catch:{ Exception -> 0x059f }
        L_0x04ed:
            float r9 = r4.m     // Catch:{ Exception -> 0x059f }
            float r9 = -r9
            int r9 = (int) r9     // Catch:{ Exception -> 0x059f }
            r15.bottomMargin = r9     // Catch:{ Exception -> 0x059f }
            r8.setLayoutParams(r15)     // Catch:{ Exception -> 0x059f }
            com.devs.vectorchildfinder.d r9 = new com.devs.vectorchildfinder.d     // Catch:{ Exception -> 0x059f }
            android.content.Context r10 = r1.getContext()     // Catch:{ Exception -> 0x059f }
            boolean r11 = r4.u     // Catch:{ Exception -> 0x059f }
            if (r11 == 0) goto L_0x0503
            int r11 = com.leedarson.R$drawable.footprint_human_left     // Catch:{ Exception -> 0x059f }
            goto L_0x0505
        L_0x0503:
            int r11 = com.leedarson.R$drawable.footprint_human_right     // Catch:{ Exception -> 0x059f }
        L_0x0505:
            r9.<init>(r10, r11, r8)     // Catch:{ Exception -> 0x059f }
            java.lang.String r10 = "foot1"
            com.devs.vectorchildfinder.VectorDrawableCompat$b r10 = r9.a(r10)     // Catch:{ Exception -> 0x059f }
            r8.setFullPath(r10)     // Catch:{ Exception -> 0x059f }
            r8.invalidate()     // Catch:{ Exception -> 0x059f }
            r6 = r8
            goto L_0x0518
        L_0x0516:
            r13 = -4616189618054758400(0xbff0000000000000, double:-1.0)
        L_0x0518:
            r6.setData(r4)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.RadarRangeView r5 = r1.p2     // Catch:{ Exception -> 0x059f }
            r5.addView(r6)     // Catch:{ Exception -> 0x059f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x059f }
            r5.<init>()     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = "容器 共:"
            r5.append(r8)     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.RadarRangeView r8 = r1.p2     // Catch:{ Exception -> 0x059f }
            int r8 = r8.getChildCount()     // Catch:{ Exception -> 0x059f }
            r5.append(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.String r8 = "个点,显示点:"
            r5.append(r8)     // Catch:{ Exception -> 0x059f }
            com.leedarson.smartcamera.kvswebrtc.beans.a r8 = r6.getData()     // Catch:{ Exception -> 0x059f }
            r5.append(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.g.a(r5)     // Catch:{ Exception -> 0x059f }
            boolean r5 = r6 instanceof com.leedarson.newui.view.radar.RadarFootImageView     // Catch:{ Exception -> 0x059f }
            if (r5 != 0) goto L_0x054f
            boolean r5 = r6 instanceof com.leedarson.newui.view.radar.RadarPointView     // Catch:{ Exception -> 0x059f }
            if (r5 == 0) goto L_0x0552
        L_0x054f:
            r1.N(r4, r6)     // Catch:{ Exception -> 0x059f }
        L_0x0552:
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r5 = r1.V4     // Catch:{ Exception -> 0x059f }
            int r8 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x059f }
            boolean r5 = r5.containsKey(r8)     // Catch:{ Exception -> 0x059f }
            if (r5 == 0) goto L_0x0572
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r5 = r1.V4     // Catch:{ Exception -> 0x059f }
            int r8 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x059f }
            java.lang.Object r5 = r5.get(r8)     // Catch:{ Exception -> 0x059f }
            java.util.List r5 = (java.util.List) r5     // Catch:{ Exception -> 0x059f }
            r5.add(r6)     // Catch:{ Exception -> 0x059f }
            goto L_0x0585
        L_0x0572:
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x059f }
            r5.<init>()     // Catch:{ Exception -> 0x059f }
            r5.add(r6)     // Catch:{ Exception -> 0x059f }
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.newui.view.radar.b>> r8 = r1.V4     // Catch:{ Exception -> 0x059f }
            int r9 = r4.e     // Catch:{ Exception -> 0x059f }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x059f }
            r8.put(r9, r5)     // Catch:{ Exception -> 0x059f }
        L_0x0585:
            r5 = r6
            android.os.Handler r8 = r1.Z4     // Catch:{ Exception -> 0x059f }
            com.leedarson.newui.view.radar.RadarViewLayout$b r9 = new com.leedarson.newui.view.radar.RadarViewLayout$b     // Catch:{ Exception -> 0x059f }
            r9.<init>(r5)     // Catch:{ Exception -> 0x059f }
            r10 = 6000(0x1770, double:2.9644E-320)
            r8.postDelayed(r9, r10)     // Catch:{ Exception -> 0x059f }
            goto L_0x0595
        L_0x0593:
            r13 = -4616189618054758400(0xbff0000000000000, double:-1.0)
        L_0x0595:
            int r0 = r0 + 1
            r9 = 1157234688(0x44fa0000, float:2000.0)
            r10 = -990248960(0xffffffffc4fa0000, float:-2000.0)
            r11 = 2
            goto L_0x032e
        L_0x059e:
            goto L_0x05b8
        L_0x059f:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "addDot exception:"
            r3.append(r4)
            java.lang.String r4 = r0.getMessage()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.leedarson.newui.view.radar.g.a(r3)
        L_0x05b8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.radar.RadarViewLayout.o(java.util.List):void");
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b c;

        b(b bVar) {
            this.c = bVar;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5485, new Class[0], Void.TYPE).isSupported) {
                if (RadarViewLayout.this.p2.indexOfChild((View) this.c) == -1) {
                    g.a("页面容器不包含点:" + this.c.getData());
                    return;
                }
                g.a("6s到了,发送移除:" + this.c.getData());
                Message message = Message.obtain();
                message.obj = this.c;
                message.what = 1;
                RadarViewLayout.this.Z4.sendMessage(message);
                for (Integer id : RadarViewLayout.this.V4.keySet()) {
                    Iterator<BaseRadarDot> iterator = ((List) RadarViewLayout.this.V4.get(id)).iterator();
                    while (true) {
                        if (iterator.hasNext()) {
                            if (this.c == ((b) iterator.next())) {
                                iterator.remove();
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: com.leedarson.smartcamera.kvswebrtc.beans.a} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.leedarson.smartcamera.kvswebrtc.beans.a s(com.leedarson.smartcamera.kvswebrtc.beans.a r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.smartcamera.kvswebrtc.beans.a> r0 = com.leedarson.smartcamera.kvswebrtc.beans.a.class
            r6[r2] = r0
            java.lang.Class<com.leedarson.smartcamera.kvswebrtc.beans.a> r7 = com.leedarson.smartcamera.kvswebrtc.beans.a.class
            r4 = 0
            r5 = 5459(0x1553, float:7.65E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0021
            java.lang.Object r9 = r0.result
            com.leedarson.smartcamera.kvswebrtc.beans.a r9 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r9
            return r9
        L_0x0021:
            r0 = r8
            r1 = 0
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r2 = r0.W4
            int r3 = r9.e
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            boolean r2 = r2.containsKey(r3)
            if (r2 == 0) goto L_0x005e
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r2 = r0.W4
            int r3 = r9.e
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r2 = r2.get(r3)
            java.util.List r2 = (java.util.List) r2
            if (r2 == 0) goto L_0x005e
            r3 = 0
        L_0x0042:
            int r4 = r2.size()
            if (r3 >= r4) goto L_0x005e
            java.lang.Object r4 = r2.get(r3)
            if (r4 != r9) goto L_0x005b
            int r4 = r3 + -1
            if (r4 < 0) goto L_0x005b
            int r4 = r3 + -1
            java.lang.Object r4 = r2.get(r4)
            r1 = r4
            com.leedarson.smartcamera.kvswebrtc.beans.a r1 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r1
        L_0x005b:
            int r3 = r3 + 1
            goto L_0x0042
        L_0x005e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.radar.RadarViewLayout.s(com.leedarson.smartcamera.kvswebrtc.beans.a):com.leedarson.smartcamera.kvswebrtc.beans.a");
    }

    public double p(com.leedarson.smartcamera.kvswebrtc.beans.a currentData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{currentData}, this, changeQuickRedirect, false, 5460, new Class[]{com.leedarson.smartcamera.kvswebrtc.beans.a.class}, Double.TYPE);
        if (proxy.isSupported) {
            return ((Double) proxy.result).doubleValue();
        }
        com.leedarson.smartcamera.kvswebrtc.beans.a preData = s(currentData);
        if (preData == null || currentData == null) {
            return -1.0d;
        }
        double degree = Math.toDegrees(Math.atan2((double) (currentData.f - preData.f), (double) (currentData.g - preData.g)));
        preData.w = preData.v;
        preData.v = degree;
        currentData.v = degree;
        return degree;
    }

    public void t(b radarPointView, String bz) {
        Class[] clsArr = {b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{radarPointView, bz}, this, changeQuickRedirect, false, 5461, clsArr, Void.TYPE).isSupported) {
            g.a("来了:" + radarPointView);
            Animation animation = AnimationUtils.loadAnimation(getContext(), R$anim.radar_point_animation_out);
            animation.setDuration(this.f5);
            if (radarPointView instanceof View) {
                g.a("startAnimation");
                ((View) radarPointView).startAnimation(animation);
                long now = SystemClock.uptimeMillis();
                long j = this.f5;
                long next = (j - (now % j)) + now;
                g.a("now:" + now + ",now%tall:" + (now % this.f5) + ",delay:" + (next - now));
                this.Z4.postAtTime(new c(radarPointView, bz), next);
                return;
            }
            g.a("startAnimation 错了");
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b c;
        final /* synthetic */ String d;

        c(b bVar, String str) {
            this.c = bVar;
            this.d = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5486, new Class[0], Void.TYPE).isSupported) {
                g.a("startAnimation animation copmplete 这里走了，真的是奇葩");
                RadarViewLayout.h(RadarViewLayout.this, this.c, this.d);
            }
        }
    }

    private void H(b radarPointView, String str) {
        Class[] clsArr = {b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{radarPointView, str}, this, changeQuickRedirect, false, 5462, clsArr, Void.TYPE).isSupported) {
            try {
                if (radarPointView instanceof View) {
                    ((FrameLayout) ((View) radarPointView).getParent()).removeView((View) radarPointView);
                    for (int i = 0; i < this.p2.getChildCount(); i++) {
                        boolean z2 = this.p2.getChildAt(i) instanceof RadarPointView;
                    }
                }
            } catch (Exception e2) {
                g.d("remove point:" + radarPointView.getData() + ",fail:" + e2.getMessage());
            }
        }
    }

    public void N(com.leedarson.smartcamera.kvswebrtc.beans.a aVar, View view) {
        if (!PatchProxy.proxy(new Object[]{aVar, view}, this, changeQuickRedirect, false, 5463, new Class[]{com.leedarson.smartcamera.kvswebrtc.beans.a.class, View.class}, Void.TYPE).isSupported) {
            View radarView = view;
            com.leedarson.smartcamera.kvswebrtc.beans.a data = aVar;
            float pivotX = ((float) (RadarPointView.c / 2)) + data.l;
            float pivotY = ((float) RadarPointView.d) - Math.abs(data.m);
            this.i5 = pivotX;
            this.j5 = pivotY;
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setDuration(this.e5);
            animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
            if (radarView instanceof RadarPointView) {
                animationSet.addAnimation(new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, pivotX, pivotY));
            }
            animationSet.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            animationSet.setAnimationListener(new d());
            radarView.startAnimation(animationSet);
        }
    }

    public class d implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5464, new Class[0], Void.TYPE).isSupported) {
            this.J4 = true;
            K();
            LinearLayout.LayoutParams containerParams = (LinearLayout.LayoutParams) this.G4.getLayoutParams();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            containerParams.width = screenWidth;
            containerParams.height = this.C4;
            if (this.K4) {
                ViewGroup.LayoutParams priLeftParams = this.S4.getLayoutParams();
                int i = c.m;
                priLeftParams.width = i;
                this.T4.getLayoutParams().width = i;
            } else {
                ViewGroup.LayoutParams priLeftParams2 = this.S4.getLayoutParams();
                int i2 = c.l;
                priLeftParams2.width = i2;
                this.T4.getLayoutParams().width = i2;
            }
            float f2 = this.x;
            int checkHeight = (int) (((float) screenWidth) / f2);
            int i3 = this.C4;
            if (checkHeight > i3) {
                this.l5 = i3;
                this.k5 = (int) (f2 * ((float) i3));
            } else {
                this.k5 = screenWidth;
                this.l5 = (int) (((float) screenWidth) / f2);
            }
            this.G4.setLayoutParams(containerParams);
            P(false);
            FrameLayout.LayoutParams radarRangeCircleViewParam = (FrameLayout.LayoutParams) this.p2.getLayoutParams();
            radarRangeCircleViewParam.width = this.k5;
            radarRangeCircleViewParam.height = this.l5;
            this.p2.setLayoutParams(radarRangeCircleViewParam);
            x();
        }
    }

    private void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5465, new Class[0], Void.TYPE).isSupported) {
            int i = R$id.outBackground;
            findViewById(i).setBackground((Drawable) null);
            findViewById(i).setPadding(0, 0, 0, 0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.rightMargin = 0;
            this.G4.setBackgroundColor(Color.parseColor("#000000"));
        }
    }

    public void Q(boolean isFirstRequestRadarMap) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isFirstRequestRadarMap ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5466, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.J4 = false;
            M();
            ViewGroup.LayoutParams containerParams = this.G4.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            if (this.K4) {
                int i = this.z4;
                this.k5 = i;
                int i2 = this.A4;
                this.l5 = i2;
                marginLayoutParams.topMargin = 0;
                marginLayoutParams.rightMargin = 0;
                containerParams.width = i;
                containerParams.height = i2;
            } else {
                int i3 = this.p3;
                this.k5 = i3;
                int i4 = this.p4;
                this.l5 = i4;
                containerParams.width = i3;
                containerParams.height = i4;
                marginLayoutParams.topMargin = this.D4;
                marginLayoutParams.rightMargin = this.E4;
            }
            ViewGroup.LayoutParams priLeftParams = this.S4.getLayoutParams();
            int i6 = c.k;
            priLeftParams.width = i6;
            this.T4.getLayoutParams().width = i6;
            this.G4.setLayoutParams(containerParams);
            P(isFirstRequestRadarMap);
            ViewGroup.LayoutParams radarRangeCircleViewParam = this.p2.getLayoutParams();
            radarRangeCircleViewParam.width = this.k5;
            radarRangeCircleViewParam.height = this.l5;
            this.p2.setLayoutParams(radarRangeCircleViewParam);
            x();
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5467, new Class[0], Void.TYPE).isSupported) {
            int totalWidth = this.k5;
            int totalHeight = this.l5;
            int i = this.k5;
            int i2 = this.l5;
            if (((float) i) / ((float) i2) > 2.0f) {
                float f2 = (float) totalHeight;
                this.q = f2;
                int i3 = this.p0;
                this.p1 = f2 / ((float) i3);
                this.a1 = f2 / ((float) i3);
            } else {
                float f3 = (float) (totalWidth / 2);
                this.q = f3;
                int i4 = this.p0;
                this.a1 = f3 / ((float) i4);
                this.p1 = f3 / ((float) i4);
            }
            RadarRangeView.c = i;
            RadarRangeView.d = i2;
            RadarRangeView.f = this.q;
            RadarPointView.b(i, i2);
            RadarArcView.c = RadarRangeView.c;
            RadarArcView.d = RadarRangeView.d;
            RadarArcView.f = (int) RadarRangeView.f;
            S();
        }
    }

    private void S() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5468, new Class[0], Void.TYPE).isSupported) {
            g.a("initRadarParam updatePointView");
            Set<Integer> ids = this.V4.keySet();
            if (ids != null) {
                for (Integer intValue : ids) {
                    List<BaseRadarDot> pointViews = this.V4.get(Integer.valueOf(intValue.intValue()));
                    if (pointViews != null && pointViews.size() > 0) {
                        Iterator<BaseRadarDot> it = pointViews.iterator();
                        while (it.hasNext()) {
                            b dot = (b) it.next();
                            if (dot instanceof RadarPointView) {
                                int currentRadarRadius = getRadarPointRadius();
                                dot.getData().n *= ((float) currentRadarRadius) / dot.getData().o;
                                dot.getData().o = (float) currentRadarRadius;
                                dot.getData().a(this.a1 * this.Q4, this.p1 * this.R4);
                                ((RadarPointView) dot).c();
                            }
                        }
                    }
                }
            }
        }
    }

    private int getRadarPointRadius() {
        int circleRadius = c.p;
        if (this.J4) {
            if (this.K4) {
                return c.y;
            }
            if (this.L4) {
                return c.w;
            }
            return c.t;
        } else if (this.K4) {
            return c.r;
        } else {
            return circleRadius;
        }
    }

    private int getRadarFootWidth() {
        int footW = c.q;
        if (this.J4) {
            if (this.K4) {
                footW = c.z;
            } else if (this.L4) {
                footW = c.x;
            } else {
                footW = c.u;
            }
        } else if (this.K4) {
            footW = c.s;
        }
        return (int) (((float) footW) * 0.8f);
    }

    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5469, new Class[0], Void.TYPE).isSupported) {
            int i = R$id.outBackground;
            findViewById(i).setBackgroundResource(R$drawable.player_small_radar_window_bg);
            int dp2 = com.leedarson.view.a.a(getContext(), 2.0f);
            findViewById(i).setPadding(dp2, dp2, dp2, dp2);
            this.G4.setBackgroundResource(R$drawable.player_small_radar_window_inner_bg);
        }
    }

    private void P(boolean isFirstRequestRadarMap) {
        Object[] objArr = {new Byte(isFirstRequestRadarMap ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5470, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.M4.getLayoutParams();
            params.width = -2;
            params.height = -2;
            ViewGroup.LayoutParams priLeftParams = this.S4.getLayoutParams();
            priLeftParams.height = -1;
            ViewGroup.LayoutParams priRightParams = this.T4.getLayoutParams();
            priRightParams.height = -1;
            if (this.J4) {
                this.M4.setImageResource(R$drawable.default_radar_large_square_full_bg);
                if (this.L4) {
                    params.width = this.k5;
                    int i = this.l5;
                    params.height = i;
                    priLeftParams.height = i;
                    priRightParams.height = i;
                }
            } else {
                this.M4.setImageResource(R$drawable.default_radar_small_square_full_bg);
            }
            this.M4.setLayoutParams(params);
            this.S4.setLayoutParams(priLeftParams);
            this.T4.setLayoutParams(priRightParams);
            if (this.f != null) {
                J();
                this.M4.setImageBitmap(this.f);
            } else if (!TextUtils.isEmpty(this.N4)) {
                B();
            } else {
                I();
            }
            if (isFirstRequestRadarMap) {
                I();
            }
        }
    }

    private void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5471, new Class[0], Void.TYPE).isSupported) {
            FrameLayout.LayoutParams imageParams = (FrameLayout.LayoutParams) this.M4.getLayoutParams();
            if (this.K4) {
                imageParams.width = -1;
                imageParams.height = -1;
            } else {
                imageParams.width = this.k5;
                imageParams.height = this.l5;
            }
            this.M4.setLayoutParams(imageParams);
        }
    }

    private void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5472, new Class[0], Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.O4)) {
                g.a("请求获取雷达地图url:" + this.O4);
                this.a5.getRadarMap(this.O4, new e());
            }
        }
    }

    public class e implements OnRequestListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onRequestResult(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5487, new Class[]{Object.class}, Void.TYPE).isSupported) {
                try {
                    JSONObject jsonObject = new JSONObject((String) obj);
                    if (jsonObject.optInt("code") == 200) {
                        String radarMapPicUrl = jsonObject.optJSONObject("data").optString("url");
                        if (!RadarViewLayout.this.N4.equals(radarMapPicUrl)) {
                            String unused = RadarViewLayout.this.N4 = radarMapPicUrl;
                            g.a("请求雷达地图图片url成功:" + radarMapPicUrl);
                            RadarViewLayout.k(RadarViewLayout.this);
                        }
                    }
                } catch (Exception e) {
                    g.a("getRadarMap response data exception:" + e.getMessage() + ",data:" + obj);
                }
            }
        }

        public void onRequestFail(int code, String message) {
            Object[] objArr = {new Integer(code), message};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5488, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                RadarViewLayout radarViewLayout = RadarViewLayout.this;
                RadarViewLayout.m(radarViewLayout, "getRadarMap onRequestFail:" + code + ",message:" + message + ",deviceId:" + RadarViewLayout.this.O4);
            }
        }
    }

    private void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5473, new Class[0], Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.N4)) {
                ((com.bumptech.glide.h) ((com.bumptech.glide.h) ((com.bumptech.glide.h) com.bumptech.glide.b.t(getContext()).i().M0(this.N4).d0(this.J4 ? R$drawable.default_radar_large_square_full_bg : R$drawable.default_radar_small_square_full_bg)).j(this.J4 ? R$drawable.default_radar_large_square_full_bg : R$drawable.default_radar_small_square_full_bg)).f(i.d)).J0(new g()).D0(new f(this.M4));
            }
        }
    }

    public class g implements com.bumptech.glide.request.e<Bitmap> {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5492, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Bitmap) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException e, Object model, j<Bitmap> jVar, boolean isFirstResource) {
            return false;
        }

        public boolean a(Bitmap resource, Object obj, j<Bitmap> jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Object[] objArr = {resource, obj, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5491, new Class[]{Bitmap.class, Object.class, j.class, com.bumptech.glide.load.a.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            g.a("onResourceReady,图片width:" + resource.getWidth() + ",height:" + resource.getHeight());
            return false;
        }
    }

    public class f extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        f(ImageView view) {
            super(view);
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5490, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 5489, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    RadarViewLayout.n(RadarViewLayout.this);
                    RadarViewLayout.this.M4.setScaleType(ImageView.ScaleType.FIT_XY);
                    Bitmap unused = RadarViewLayout.this.f = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight() / 2);
                    g.a("显示雷达地图图片成功,控件width:" + RadarViewLayout.this.M4.getWidth() + ",height:" + RadarViewLayout.this.M4.getHeight() + ",图片width:" + RadarViewLayout.this.f.getWidth() + ",height:" + RadarViewLayout.this.f.getHeight());
                    RadarViewLayout.this.M4.setImageBitmap(RadarViewLayout.this.f);
                }
            }
        }
    }

    public void E(boolean isWideScreen) {
        this.L4 = isWideScreen;
        if (isWideScreen) {
            this.K4 = false;
        }
    }

    public void D(boolean isPlayerLandScreen) {
        this.K4 = isPlayerLandScreen;
        if (isPlayerLandScreen) {
            this.L4 = false;
        }
    }

    public void L(int playerWidth, int playerHeight) {
        Object[] objArr = {new Integer(playerWidth), new Integer(playerHeight)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5475, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.B4 = playerWidth;
            this.C4 = playerHeight;
            C("设置播放器显示尺寸,playerWidth:" + playerWidth + ",playerHeight:" + playerHeight);
        }
    }

    private void C(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5476, new Class[]{String.class}, Void.TYPE).isSupported) {
            g.a(msg);
        }
    }

    public void setListener(h listener) {
        this.a2 = listener;
    }

    public void w(int phyRadarRadius) {
        Object[] objArr = {new Integer(phyRadarRadius)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5477, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.p0 = phyRadarRadius;
            C("雷达物理半径:" + phyRadarRadius);
        }
    }

    public void setDeviceRatio(float deviceRatio) {
        this.z = deviceRatio;
    }

    public void setPlayerAspectRatio(float playerAspectRatio) {
        this.I4 = playerAspectRatio;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void R() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5478(0x1566, float:7.676E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.b5
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.b5 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.radar.RadarViewLayout.R():void");
    }

    public void q() {
        this.f = null;
        this.N4 = "";
    }
}
