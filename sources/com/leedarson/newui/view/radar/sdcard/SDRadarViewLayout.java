package com.leedarson.newui.view.radar.sdcard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.request.target.j;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.bean.H5ActionName;
import com.leedarson.newui.view.radar.BaseRadarDot;
import com.leedarson.newui.view.radar.RadarPathView;
import com.leedarson.newui.view.radar.RadarPointView;
import com.leedarson.newui.view.radar.RadarRangeView;
import com.leedarson.newui.view.radar.lib.ekf.EKFLDS;
import com.leedarson.serviceinterface.MapService;
import com.leedarson.serviceinterface.listener.OnRequestListener;
import com.leedarson.smartcamera.kvswebrtc.beans.LdsRadarData;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.linear.q;
import org.json.JSONObject;

public class SDRadarViewLayout extends LinearLayout {
    public static int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d;
    public static int f;
    public static int q;
    private int A4;
    private int B4;
    private int C4;
    private int D4;
    private FrameLayout E4;
    private final long F4 = 500;
    private float G4;
    /* access modifiers changed from: private */
    public boolean H4;
    private boolean I4;
    private boolean J4;
    /* access modifiers changed from: private */
    public ImageView K4;
    /* access modifiers changed from: private */
    public Bitmap L4;
    /* access modifiers changed from: private */
    public String M4 = "";
    private String N4;
    private MapService O4;
    private HashMap<Integer, List<com.leedarson.smartcamera.kvswebrtc.beans.a>> P4 = new HashMap<>();
    private HashMap<Integer, List<com.leedarson.newui.view.radar.b>> Q4 = new HashMap<>();
    private float R4 = 1.9f;
    private float S4 = 0.8f;
    private g T4;
    private long U4;
    private int V4 = 2;
    private boolean W4 = false;
    private int X4;
    private int Y4;
    private Context Z4;
    public int a1 = 800;
    public float a2;
    Handler a5 = new Handler(Looper.getMainLooper());
    private Runnable b5 = new b(this);
    private int c5 = 0;
    private RadarPathView d5;
    private float e5;
    private float f5;
    private float g5;
    private float h5;
    private Runnable i5 = new e();
    private long j5;
    private HashMap<Long, Boolean> k5 = new HashMap<>();
    public float p0;
    public float p1;
    /* access modifiers changed from: private */
    public f p2;
    /* access modifiers changed from: private */
    public RadarRangeView p3;
    private int p4;
    /* access modifiers changed from: private */
    public SDCardRadarLayoutWrapper x;
    private float y = 0.0f;
    public float z = 1.7777778f;
    private int z4;

    public interface f {
        void a();

        void b();

        void c();
    }

    public interface g {
        void a(boolean z, boolean z2);
    }

    static /* synthetic */ void c(SDRadarViewLayout x0, String x1) {
        Class[] clsArr = {SDRadarViewLayout.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5571, clsArr, Void.TYPE).isSupported) {
            x0.E(x1);
        }
    }

    static /* synthetic */ void f(SDRadarViewLayout x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5572, new Class[]{SDRadarViewLayout.class}, Void.TYPE).isSupported) {
            x0.D();
        }
    }

    static /* synthetic */ void g(SDRadarViewLayout x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5573, new Class[]{SDRadarViewLayout.class}, Void.TYPE).isSupported) {
            x0.K();
        }
    }

    public SDRadarViewLayout(Context context) {
        super(context);
        t(context, (AttributeSet) null);
    }

    public SDRadarViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        t(context, attrs);
    }

    public SDRadarViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        t(context, attrs);
    }

    public void t(Context context, AttributeSet attributeSet) {
        Class[] clsArr = {Context.class, AttributeSet.class};
        if (!PatchProxy.proxy(new Object[]{context, attributeSet}, this, changeQuickRedirect, false, 5539, clsArr, Void.TYPE).isSupported) {
            View view = LayoutInflater.from(getContext()).inflate(R$layout.float_player_radar_view, (ViewGroup) null);
            this.Z4 = context;
            v(context);
            y(view);
        }
    }

    private void y(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5540, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.E4 = (FrameLayout) view.findViewById(R$id.radar_view_container);
            this.K4 = (ImageView) view.findViewById(R$id.radar_imageview);
            this.X4 = this.p4;
            this.Y4 = this.z4;
            addView(view);
            setOnClickListener(new a(this));
            this.p3 = (RadarRangeView) view.findViewById(R$id.test_range_circle_view);
            this.O4 = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: z */
    public /* synthetic */ void A(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5570, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (this.H4) {
            f fVar = this.p2;
            if (fVar != null) {
                fVar.a();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        this.H4 = true;
        I(this.x, "onClickRunnableExe");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: B */
    public /* synthetic */ void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5569, new Class[0], Void.TYPE).isSupported) {
            if (this.H4) {
                f fVar = this.p2;
                if (fVar != null) {
                    fVar.a();
                    return;
                }
                return;
            }
            this.H4 = true;
            I(this.x, "onClickRunnableExe");
        }
    }

    private void v(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5541, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.p4 = com.leedarson.newui.view.radar.c.A;
            this.z4 = com.leedarson.newui.view.radar.c.B;
            this.A4 = com.leedarson.newui.view.radar.c.C;
            this.B4 = com.leedarson.newui.view.radar.c.D;
            c = com.leedarson.view.a.a(context, 12.0f);
            d = com.leedarson.view.a.a(context, 18.0f);
            f = com.leedarson.view.a.a(context, 23.0f);
            q = com.leedarson.view.a.a(context, 81.0f);
        }
    }

    public void setOnSizeChangeListener(g onSizeChangeListener) {
        this.T4 = onSizeChangeListener;
    }

    public void G(Activity activity, String deviceId, SDCardRadarLayoutWrapper sdcardRadarLayoutWrapper) {
        Class[] clsArr = {Activity.class, String.class, SDCardRadarLayoutWrapper.class};
        if (!PatchProxy.proxy(new Object[]{activity, deviceId, sdcardRadarLayoutWrapper}, this, changeQuickRedirect, false, 5542, clsArr, Void.TYPE).isSupported) {
            this.N4 = deviceId;
            this.x = sdcardRadarLayoutWrapper;
            setVisibility(0);
            I(sdcardRadarLayoutWrapper, "SDRadarView.onStartShowRadar");
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5543, new Class[0], Void.TYPE).isSupported) {
            setVisibility(8);
            this.H4 = false;
            f fVar = this.p2;
            if (fVar != null) {
                fVar.b();
            }
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5544, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.a(H5ActionName.ACTION_DESTORY);
            HashMap<Integer, List<com.leedarson.smartcamera.kvswebrtc.beans.a>> hashMap = this.P4;
            if (hashMap != null) {
                hashMap.clear();
            }
            HashMap<Integer, List<com.leedarson.newui.view.radar.b>> hashMap2 = this.Q4;
            if (hashMap2 != null) {
                hashMap2.clear();
            }
            RadarRangeView radarRangeView = this.p3;
            if (radarRangeView != null) {
                radarRangeView.removeAllViews();
            }
            HashMap<Long, Boolean> hashMap3 = this.k5;
            if (hashMap3 != null) {
                hashMap3.clear();
            }
            this.c5 = 0;
        }
    }

    public void n(List<com.leedarson.smartcamera.kvswebrtc.beans.a> newDatas) {
        if (!PatchProxy.proxy(new Object[]{newDatas}, this, changeQuickRedirect, false, 5545, new Class[]{List.class}, Void.TYPE).isSupported) {
            u(newDatas);
            if (s()) {
                r(newDatas);
                return;
            }
            p();
            q();
        }
    }

    private void u(List<com.leedarson.smartcamera.kvswebrtc.beans.a> list) {
        HashMap<Integer, Integer> mapRadarColorGroup;
        HashMap<Integer, EKFLDS> ekfMaps;
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 5546, new Class[]{List.class}, Void.TYPE).isSupported) {
            List<com.leedarson.smartcamera.kvswebrtc.beans.a> list2 = list;
            HashMap<Integer, EKFLDS> ekfMaps2 = new HashMap<>();
            HashMap<Integer, Integer> mapRadarColorGroup2 = new HashMap<>();
            for (int i = 0; i < list2.size(); i++) {
                com.leedarson.smartcamera.kvswebrtc.beans.a data = list2.get(i);
                if (this.P4.containsKey(Integer.valueOf(data.e))) {
                    this.P4.get(Integer.valueOf(data.e)).add(data);
                } else {
                    List<LdsRadarData> datas = new ArrayList<>();
                    datas.add(data);
                    this.P4.put(Integer.valueOf(data.e), datas);
                }
                if (mapRadarColorGroup2.containsKey(Integer.valueOf(data.e))) {
                    data.t = mapRadarColorGroup2.get(Integer.valueOf(data.e)).intValue();
                } else {
                    if (this.c5 >= com.leedarson.newui.view.radar.c.h.length) {
                        this.c5 = 0;
                    }
                    int i2 = this.c5;
                    data.t = i2;
                    this.c5 = i2 + 1;
                }
                mapRadarColorGroup2.put(Integer.valueOf(data.e), Integer.valueOf(data.t));
                float radarPointRadius = getRadarPointRadius();
                data.a(this.p1 * this.R4, this.a2 * this.S4);
                data.n = radarPointRadius;
                data.r = Color.parseColor("#80FFFFFF");
                data.s = com.leedarson.newui.view.radar.c.o;
                data.p = com.leedarson.newui.view.radar.c.c(data.t);
                data.q = com.leedarson.newui.view.radar.c.d(data.t);
            }
            HashMap<Integer, List<Float>> originX = new HashMap<>();
            HashMap<Integer, List<Float>> originY = new HashMap<>();
            HashMap<Integer, List<Float>> kfX = new HashMap<>();
            HashMap<Integer, List<Float>> kfY = new HashMap<>();
            for (Integer intValue : this.P4.keySet()) {
                int id = intValue.intValue();
                if (originX.get(Integer.valueOf(id)) == null) {
                    originX.put(Integer.valueOf(id), new ArrayList());
                }
                if (originY.get(Integer.valueOf(id)) == null) {
                    originY.put(Integer.valueOf(id), new ArrayList());
                }
                if (kfX.get(Integer.valueOf(id)) == null) {
                    kfX.put(Integer.valueOf(id), new ArrayList());
                }
                if (kfY.get(Integer.valueOf(id)) == null) {
                    kfY.put(Integer.valueOf(id), new ArrayList());
                }
                com.leedarson.newui.view.radar.lib.ekf.a ekflds = (com.leedarson.newui.view.radar.lib.ekf.a) ekfMaps2.get(Integer.valueOf(id));
                if (ekflds == null) {
                    ekflds = new com.leedarson.newui.view.radar.lib.ekf.a();
                    ekfMaps2.put(Integer.valueOf(id), ekflds);
                }
                for (com.leedarson.smartcamera.kvswebrtc.beans.a data2 : this.P4.get(Integer.valueOf(id))) {
                    ekflds.c(data2);
                    q prediction = ekflds.b();
                    float defaultX = data2.f;
                    float defaultY = data2.g;
                    List<com.leedarson.smartcamera.kvswebrtc.beans.a> list3 = list2;
                    if (this.W4) {
                        ekfMaps = ekfMaps2;
                        mapRadarColorGroup = mapRadarColorGroup2;
                        float newY = (float) prediction.getEntry(1);
                        float newX = (float) prediction.getEntry(0);
                        data2.f = newX;
                        data2.g = Math.abs(newY - defaultY) > 100.0f ? defaultY : newY;
                        float f2 = newY;
                        float f3 = newX;
                        data2.a(this.p1 * this.R4, this.a2 * this.S4);
                    } else {
                        ekfMaps = ekfMaps2;
                        mapRadarColorGroup = mapRadarColorGroup2;
                    }
                    originX.get(Integer.valueOf(id)).add(Float.valueOf(defaultX));
                    originY.get(Integer.valueOf(id)).add(Float.valueOf(defaultY));
                    kfX.get(Integer.valueOf(id)).add(Float.valueOf(data2.f));
                    kfY.get(Integer.valueOf(id)).add(Float.valueOf(data2.g));
                    list2 = list3;
                    ekfMaps2 = ekfMaps;
                    mapRadarColorGroup2 = mapRadarColorGroup;
                }
                List<com.leedarson.smartcamera.kvswebrtc.beans.a> list4 = list2;
                HashMap<Integer, EKFLDS> hashMap = ekfMaps2;
                HashMap<Integer, Integer> hashMap2 = mapRadarColorGroup2;
            }
            List<com.leedarson.smartcamera.kvswebrtc.beans.a> list5 = list2;
            HashMap<Integer, EKFLDS> hashMap3 = ekfMaps2;
            HashMap<Integer, Integer> hashMap4 = mapRadarColorGroup2;
            for (Integer intValue2 : originX.keySet()) {
                intValue2.intValue();
            }
            for (Integer intValue3 : originY.keySet()) {
                intValue3.intValue();
            }
            for (Integer intValue4 : kfX.keySet()) {
                intValue4.intValue();
            }
            for (Integer intValue5 : kfY.keySet()) {
                intValue5.intValue();
            }
        }
    }

    public void setUseKalman(boolean useKalman) {
        this.W4 = useKalman;
    }

    private void r(List<com.leedarson.smartcamera.kvswebrtc.beans.a> newDatas) {
        if (!PatchProxy.proxy(new Object[]{newDatas}, this, changeQuickRedirect, false, 5547, new Class[]{List.class}, Void.TYPE).isSupported) {
            for (int i = 0; i < newDatas.size(); i++) {
                com.leedarson.smartcamera.kvswebrtc.beans.a data = newDatas.get(i);
                RadarPointView radarPointView = new RadarPointView(getContext());
                radarPointView.setData(data);
                this.p3.addView(radarPointView);
                if (this.Q4.containsKey(Integer.valueOf(data.e))) {
                    this.Q4.get(Integer.valueOf(data.e)).add(radarPointView);
                } else {
                    List<BaseRadarDot> points = new ArrayList<>();
                    points.add(radarPointView);
                    this.Q4.put(Integer.valueOf(data.e), points);
                }
            }
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5548, new Class[0], Void.TYPE).isSupported) {
            for (Integer id : this.P4.keySet()) {
                if (this.P4.get(id).size() == 1) {
                    com.leedarson.smartcamera.kvswebrtc.beans.a data = (com.leedarson.smartcamera.kvswebrtc.beans.a) this.P4.get(id).get(0);
                    RadarPointView radarPointView = new RadarPointView(getContext());
                    radarPointView.setData(data);
                    this.p3.addView(radarPointView);
                    if (this.Q4.containsKey(Integer.valueOf(data.e))) {
                        this.Q4.get(Integer.valueOf(data.e)).add(radarPointView);
                    } else {
                        List<BaseRadarDot> points = new ArrayList<>();
                        points.add(radarPointView);
                        this.Q4.put(Integer.valueOf(data.e), points);
                    }
                }
            }
        }
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5549, new Class[0], Void.TYPE).isSupported) {
            RadarPathView radarPathView = new RadarPathView(getContext());
            this.d5 = radarPathView;
            radarPathView.setDatasMap(this.P4);
            this.p3.addView(this.d5);
        }
    }

    public void L(com.leedarson.smartcamera.kvswebrtc.beans.a aVar, RadarPointView radarPointView, float originZ, boolean bringToFront) {
        if (!PatchProxy.proxy(new Object[]{aVar, radarPointView, new Float(originZ), new Byte(bringToFront ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5551, new Class[]{com.leedarson.smartcamera.kvswebrtc.beans.a.class, RadarPointView.class, Float.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            RadarPointView radarView = radarPointView;
            com.leedarson.smartcamera.kvswebrtc.beans.a data = aVar;
            float pivotX = ((float) (RadarPointView.c / 2)) + data.l;
            float pivotY = ((float) RadarPointView.d) - Math.abs(data.m);
            this.e5 = pivotX;
            this.f5 = pivotY;
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setDuration(500);
            animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animationSet.addAnimation(new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, pivotX, pivotY));
            animationSet.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            animationSet.setAnimationListener(new a(pivotX, pivotY, bringToFront, radarView, originZ));
            radarView.startAnimation(animationSet);
        }
    }

    public class a implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ float a;
        final /* synthetic */ float b;
        final /* synthetic */ boolean c;
        final /* synthetic */ RadarPointView d;
        final /* synthetic */ float e;

        a(float f2, float f3, boolean z, RadarPointView radarPointView, float f4) {
            this.a = f2;
            this.b = f3;
            this.c = z;
            this.d = radarPointView;
            this.e = f4;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5574, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                ScaleAnimation scale2 = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f, this.a, this.b);
                scale2.setInterpolator(new AccelerateDecelerateInterpolator());
                scale2.setDuration(500);
                scale2.setAnimationListener(new C0122a());
                this.d.startAnimation(scale2);
            }
        }

        /* renamed from: com.leedarson.newui.view.radar.sdcard.SDRadarViewLayout$a$a  reason: collision with other inner class name */
        public class C0122a implements Animation.AnimationListener {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0122a() {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5575, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                    a aVar = a.this;
                    if (aVar.c) {
                        aVar.d.setZ(aVar.e);
                        a.this.d.getData().d(com.leedarson.newui.view.radar.c.c(a.this.d.getData().t));
                        a.this.d.c();
                        return;
                    }
                    SDRadarViewLayout.this.p3.removeView(a.this.d);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5552, new Class[0], Void.TYPE).isSupported) {
            E("雷达切换到大屏");
            M();
            LinearLayout.LayoutParams containerParams = (LinearLayout.LayoutParams) this.E4.getLayoutParams();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            containerParams.width = screenWidth;
            int i = this.D4;
            containerParams.height = i;
            float f2 = this.z;
            if (((int) (((float) screenWidth) / f2)) > i) {
                this.Y4 = i;
                this.X4 = (int) (f2 * ((float) i));
            } else {
                this.X4 = screenWidth;
                this.Y4 = (int) (((float) screenWidth) / f2);
            }
            this.E4.setLayoutParams(containerParams);
            FrameLayout.LayoutParams radarRangeCircleViewParam = (FrameLayout.LayoutParams) this.p3.getLayoutParams();
            radarRangeCircleViewParam.width = this.X4;
            radarRangeCircleViewParam.height = this.Y4;
            this.p3.setLayoutParams(radarRangeCircleViewParam);
            R(false);
            x();
            g gVar = this.T4;
            if (gVar != null) {
                gVar.a(false, this.I4);
            }
        }
    }

    private void R(boolean isFirstRequestRadarMap) {
        Object[] objArr = {new Byte(isFirstRequestRadarMap ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5553, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.K4.getLayoutParams();
            params.width = -2;
            params.height = -2;
            if (this.H4) {
                this.K4.setImageResource(R$drawable.default_radar_large_square_full_bg);
                if (this.J4) {
                    params.width = this.X4;
                    params.height = this.Y4;
                    com.leedarson.newui.view.radar.g.c("雷达宽屏,显示方格");
                }
            } else {
                this.K4.setImageResource(R$drawable.default_radar_small_square_full_bg);
            }
            this.K4.setLayoutParams(params);
            if (this.L4 != null) {
                com.leedarson.newui.view.radar.g.a("显示内存中的image,图片width:" + this.L4.getWidth() + ",height:" + this.L4.getHeight());
                K();
                this.K4.setImageBitmap(this.L4);
            } else if (!TextUtils.isEmpty(this.M4)) {
                com.leedarson.newui.view.radar.g.a("加载显示内存的url");
                D();
            } else {
                J();
            }
            if (isFirstRequestRadarMap) {
                J();
            }
        }
    }

    private void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5554, new Class[0], Void.TYPE).isSupported) {
            FrameLayout.LayoutParams imageParams = (FrameLayout.LayoutParams) this.K4.getLayoutParams();
            if (this.I4) {
                imageParams.width = -1;
                imageParams.height = -1;
            } else {
                imageParams.width = this.X4;
                imageParams.height = this.Y4;
            }
            this.K4.setLayoutParams(imageParams);
        }
    }

    public class b implements OnRequestListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onRequestResult(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5576, new Class[]{Object.class}, Void.TYPE).isSupported) {
                try {
                    SDRadarViewLayout sDRadarViewLayout = SDRadarViewLayout.this;
                    SDRadarViewLayout.c(sDRadarViewLayout, "getRadarMap onRequestRequest:" + obj);
                    JSONObject jsonObject = new JSONObject((String) obj);
                    if (jsonObject.optInt("code") == 200) {
                        String radarMapPicUrl = jsonObject.optJSONObject("data").optString("url");
                        com.leedarson.newui.view.radar.g.a("获取雷达设置的地图成功:" + radarMapPicUrl);
                        if (!SDRadarViewLayout.this.M4.equals(radarMapPicUrl)) {
                            String unused = SDRadarViewLayout.this.M4 = radarMapPicUrl;
                            com.leedarson.newui.view.radar.g.a("url变更，需要加载雷达地图图片");
                            SDRadarViewLayout.f(SDRadarViewLayout.this);
                            return;
                        }
                        com.leedarson.newui.view.radar.g.a("远程url与内存url一致，不需要重新加载");
                    }
                } catch (Exception e) {
                }
            }
        }

        public void onRequestFail(int code, String message) {
            Object[] objArr = {new Integer(code), message};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5577, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                SDRadarViewLayout sDRadarViewLayout = SDRadarViewLayout.this;
                SDRadarViewLayout.c(sDRadarViewLayout, "getRadarMap onRequestFail:" + code + ",message:" + message);
            }
        }
    }

    private void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5555, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.a("请求远程url:deviceId=" + this.N4);
            this.O4.getRadarMap(this.N4, new b());
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5556, new Class[0], Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.M4)) {
                ((h) ((h) ((h) com.bumptech.glide.b.t(getContext()).i().M0(this.M4).d0(this.H4 ? R$drawable.default_radar_large_square_full_bg : R$drawable.default_radar_small_square_full_bg)).j(this.H4 ? R$drawable.default_radar_large_square_full_bg : R$drawable.default_radar_small_square_full_bg)).f(i.d)).J0(new d()).D0(new c(this.K4));
            }
        }
    }

    public class d implements com.bumptech.glide.request.e<Bitmap> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5580, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Bitmap) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException e, Object model, j<Bitmap> jVar, boolean isFirstResource) {
            return false;
        }

        public boolean a(Bitmap resource, Object model, j<Bitmap> jVar, com.bumptech.glide.load.a dataSource, boolean isFirstResource) {
            return false;
        }
    }

    public class c extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        c(ImageView view) {
            super(view);
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5579, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 5578, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    SDRadarViewLayout.g(SDRadarViewLayout.this);
                    SDRadarViewLayout.this.K4.setScaleType(ImageView.ScaleType.FIT_XY);
                    Bitmap unused = SDRadarViewLayout.this.L4 = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight() / 2);
                    com.leedarson.newui.view.radar.g.a("加载设置的地图显示,截图显示的图片width:" + resource.getWidth() + ",height:" + (resource.getHeight() / 2));
                    SDRadarViewLayout.this.K4.setImageBitmap(SDRadarViewLayout.this.L4);
                }
            }
        }
    }

    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5557, new Class[0], Void.TYPE).isSupported) {
            int i = R$id.outBackground;
            findViewById(i).setBackground((Drawable) null);
            findViewById(i).setPadding(0, 0, 0, 0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.rightMargin = 0;
            this.E4.setBackgroundColor(Color.parseColor("#000000"));
        }
    }

    public void S() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5558, new Class[0], Void.TYPE).isSupported) {
            this.H4 = false;
            O();
            ViewGroup.LayoutParams containerParams = this.E4.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            if (this.I4) {
                E("显示横屏小屏雷达");
                int i = this.A4;
                this.X4 = i;
                int i2 = this.B4;
                this.Y4 = i2;
                marginLayoutParams.topMargin = f;
                marginLayoutParams.rightMargin = q;
                containerParams.width = i;
                containerParams.height = i2;
            } else {
                E("显示竖屏小屏雷达");
                int i3 = this.p4;
                this.X4 = i3;
                int i4 = this.z4;
                this.Y4 = i4;
                containerParams.width = i3;
                containerParams.height = i4;
                marginLayoutParams.topMargin = c;
                marginLayoutParams.rightMargin = d;
            }
            this.E4.setLayoutParams(containerParams);
            ViewGroup.LayoutParams radarRangeCircleViewParam = this.p3.getLayoutParams();
            radarRangeCircleViewParam.width = this.X4;
            radarRangeCircleViewParam.height = this.Y4;
            this.p3.setLayoutParams(radarRangeCircleViewParam);
            R(false);
            x();
            g gVar = this.T4;
            if (gVar != null) {
                gVar.a(true, this.I4);
            }
            this.x.getDragHelper().g((Activity) getContext(), this.I4, (FrameLayout) this.x.getPlayerLayout(), this.x);
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5559, new Class[0], Void.TYPE).isSupported) {
            float radarPointRadius = getRadarPointRadius();
            int totalWidth = this.X4;
            int totalHeight = this.Y4;
            int i = this.X4;
            int i2 = this.Y4;
            if (((float) i) / ((float) i2) > 2.0f) {
                float f2 = (float) totalHeight;
                this.y = f2;
                int i3 = this.a1;
                this.a2 = f2 / ((float) i3);
                this.p1 = f2 / ((float) i3);
            } else {
                float f3 = (float) (totalWidth / 2);
                this.y = f3;
                int i4 = this.a1;
                this.p1 = f3 / ((float) i4);
                this.a2 = f3 / ((float) i4);
            }
            if (!(this.g5 == this.p1 && this.h5 == this.a2)) {
            }
            RadarRangeView.c = i;
            RadarRangeView.d = i2;
            RadarRangeView.f = this.y;
            RadarPointView.b(i, i2);
            RadarPathView.e(this.X4, this.Y4);
            if (s()) {
                for (Integer id : this.Q4.keySet()) {
                    for (com.leedarson.newui.view.radar.b pointView : this.Q4.get(id)) {
                        pointView.getData().a(this.p1 * this.R4, this.a2 * this.S4);
                        pointView.getData().n = radarPointRadius;
                        ((RadarPointView) pointView).c();
                    }
                }
            } else if (this.d5 != null) {
                for (Integer id2 : this.P4.keySet()) {
                    for (com.leedarson.smartcamera.kvswebrtc.beans.a ldsRadarData : this.P4.get(id2)) {
                        ldsRadarData.n = radarPointRadius;
                        ldsRadarData.a(this.p1 * this.R4, this.a2 * this.S4);
                    }
                }
                this.d5.invalidate();
            }
        }
    }

    private float getRadarPointRadius() {
        int circleRadius = com.leedarson.newui.view.radar.c.p;
        if (this.H4) {
            if (this.I4) {
                circleRadius = com.leedarson.newui.view.radar.c.y;
            } else if (this.J4) {
                circleRadius = com.leedarson.newui.view.radar.c.w;
            } else {
                circleRadius = com.leedarson.newui.view.radar.c.v;
            }
        } else if (this.I4) {
            circleRadius = com.leedarson.newui.view.radar.c.r;
        }
        return ((float) circleRadius) * 0.5f;
    }

    private void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5560, new Class[0], Void.TYPE).isSupported) {
            int i = R$id.outBackground;
            findViewById(i).setBackgroundResource(R$drawable.player_small_radar_window_bg);
            int dp2 = com.leedarson.view.a.a(getContext(), 2.0f);
            findViewById(i).setPadding(dp2, dp2, dp2, dp2);
            this.E4.setBackgroundResource(R$drawable.player_small_radar_window_inner_bg);
        }
    }

    public void F(boolean isPlayerLandScreen) {
        this.I4 = isPlayerLandScreen;
        if (isPlayerLandScreen) {
            this.J4 = false;
        }
    }

    public void setLargeRader(boolean largeRader) {
        this.H4 = largeRader;
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5581, new Class[0], Void.TYPE).isSupported) {
                if (SDRadarViewLayout.this.H4) {
                    com.leedarson.newui.view.radar.g.a("onWindowSizeChange 切换到雷达大屏   ref= sdcardRadarLayoutWrapper:" + SDRadarViewLayout.this.x);
                    SDRadarViewLayout.this.x.getDragHelper().b();
                    ((ViewGroup) SDRadarViewLayout.this.x.getParent()).removeView(SDRadarViewLayout.this.x);
                    SDRadarViewLayout.this.x.getPlayerLayout().addView(SDRadarViewLayout.this.x, SDRadarViewLayout.this.x.getIndexOfRadarLayoutContainer());
                    SDRadarViewLayout.this.P();
                    SDRadarViewLayout.this.p2.c();
                    return;
                }
                com.leedarson.newui.view.radar.g.a("onWindowSizeChange 当前显示雷达小屏,切换到雷达小屏  ref=");
                SDRadarViewLayout.this.S();
            }
        }
    }

    public void a(SDCardRadarLayoutWrapper sdcardRadarLayoutWrapper, String str) {
        Class[] clsArr = {SDCardRadarLayoutWrapper.class, String.class};
        if (!PatchProxy.proxy(new Object[]{sdcardRadarLayoutWrapper, str}, this, changeQuickRedirect, false, 5562, clsArr, Void.TYPE).isSupported) {
            this.x = sdcardRadarLayoutWrapper;
            this.a5.removeCallbacks(this.i5);
            this.a5.postDelayed(this.i5, 100);
        }
    }

    public void I(SDCardRadarLayoutWrapper sdcardRadarLayoutWrapper, String ref) {
        Class[] clsArr = {SDCardRadarLayoutWrapper.class, String.class};
        if (!PatchProxy.proxy(new Object[]{sdcardRadarLayoutWrapper, ref}, this, changeQuickRedirect, false, 5563, clsArr, Void.TYPE).isSupported) {
            a(sdcardRadarLayoutWrapper, ref);
        }
    }

    public void N(int playerWidth, int playerHeight) {
        Object[] objArr = {new Integer(playerWidth), new Integer(playerHeight)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5564, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.C4 = playerWidth;
            this.D4 = playerHeight;
            E("设置播放器显示尺寸,playerWidth:" + playerWidth + ",playerHeight:" + playerHeight);
        }
    }

    private void E(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5565, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.e(msg);
        }
    }

    public void setListener(f listener) {
        this.p2 = listener;
    }

    public boolean s() {
        return this.V4 == 1;
    }

    public void setType(int type) {
        this.V4 = type;
    }

    public int getType() {
        return this.V4;
    }

    public void w(int phyRadarRadius) {
        this.a1 = phyRadarRadius;
    }

    public void setDeviceRatio(float deviceRatio) {
        this.p0 = deviceRatio;
    }

    public void setPlayerAspectRatio(float playerAspectRatio) {
        this.G4 = playerAspectRatio;
    }

    public void setStartTime(long startTime) {
        Object[] objArr = {new Long(startTime)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5566, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            this.U4 = startTime;
            com.leedarson.newui.view.radar.g.b("startTime:" + startTime + "," + com.leedarson.base.utils.i.a(1000 * startTime, "yyyy-MM-dd HH:mm:ss"));
        }
    }

    public void T(long currentTime) {
        Object[] objArr = {new Long(currentTime)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5567, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            long time = this.U4 + currentTime;
            if (this.j5 > time) {
                this.k5.clear();
            }
            this.j5 = time;
            if (!this.k5.containsKey(Long.valueOf(time))) {
                this.k5.put(Long.valueOf(time), true);
                HashMap<Integer, List<com.leedarson.newui.view.radar.b>> hashMap = this.Q4;
                if (hashMap != null && hashMap.size() > 0) {
                    for (Integer intValue : this.Q4.keySet()) {
                        Iterator it = this.Q4.get(Integer.valueOf(intValue.intValue())).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            com.leedarson.newui.view.radar.b pointView = (com.leedarson.newui.view.radar.b) it.next();
                            if (pointView.getData().b == time) {
                                RadarPointView radarPointView = (RadarPointView) pointView;
                                float originZ = radarPointView.getZ();
                                radarPointView.setZ(100.0f);
                                radarPointView.getData().d(com.leedarson.newui.view.radar.c.d(pointView.getData().t));
                                radarPointView.c();
                                L(pointView.getData(), radarPointView, originZ, true);
                                break;
                            }
                        }
                    }
                }
                for (Integer intValue2 : this.P4.keySet()) {
                    Iterator it2 = this.P4.get(Integer.valueOf(intValue2.intValue())).iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        com.leedarson.smartcamera.kvswebrtc.beans.a radarData = (com.leedarson.smartcamera.kvswebrtc.beans.a) it2.next();
                        if (radarData.b == time) {
                            Q(radarData);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void Q(com.leedarson.smartcamera.kvswebrtc.beans.a data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5568, new Class[]{com.leedarson.smartcamera.kvswebrtc.beans.a.class}, Void.TYPE).isSupported) {
            float radarPointRadius = getRadarPointRadius();
            RadarPointView radarPointView = new RadarPointView(getContext());
            data.a(this.p1 * this.R4, this.a2 * this.S4);
            data.n = radarPointRadius;
            data.r = Color.parseColor("#80FFFFFF");
            data.s = com.leedarson.newui.view.radar.c.o;
            data.p = com.leedarson.newui.view.radar.c.c(data.t);
            radarPointView.setData(data);
            this.p3.addView(radarPointView);
            L(data, radarPointView, radarPointView.getZ(), false);
        }
    }
}
