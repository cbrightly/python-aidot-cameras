package com.leedarson.newui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.exifinterface.media.ExifInterface;
import com.airbnb.lottie.LottieAnimationView;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;

public class HorLiveController extends RelativeLayout implements View.OnClickListener, Observer {
    public static ChangeQuickRedirect changeQuickRedirect;
    private h A4;
    /* access modifiers changed from: private */
    public ArrayList<View> B4;
    private int C4 = 5;
    private int D4 = 0;
    private int E4 = 3;
    private int F4 = -1;
    private int G4 = -1;
    /* access modifiers changed from: private */
    public boolean H4 = true;
    /* access modifiers changed from: private */
    public boolean I4 = false;
    /* access modifiers changed from: private */
    public boolean J4 = false;
    /* access modifiers changed from: private */
    public boolean K4 = false;
    /* access modifiers changed from: private */
    public boolean L4 = false;
    /* access modifiers changed from: private */
    public boolean M4 = false;
    private boolean N4 = false;
    private boolean O4 = false;
    private boolean P4 = false;
    private boolean Q4 = false;
    /* access modifiers changed from: private */
    public int R4 = 0;
    private Handler S4 = new Handler();
    private Runnable T4 = new a();
    private View U4;
    Handler V4 = new Handler(Looper.getMainLooper());
    int W4 = 0;
    com.leedarson.base.views.d X4;
    private ImageView a1;
    private View a2;
    String c = "NewLiveFragment#HorLiveControler";
    private TrafficStatsTextView d;
    private LDSTextView f;
    private ImageView p0;
    private ImageView p1;
    private ImageView p2;
    private ImageView p3;
    private View p4;
    private LDSTextView q;
    private ImageView x;
    private ImageView y;
    private LottieAnimationView z;
    private BatteryMeterView z4;

    static /* synthetic */ void a(HorLiveController x0, String x1) {
        Class[] clsArr = {HorLiveController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 4996, clsArr, Void.TYPE).isSupported) {
            x0.z(x1);
        }
    }

    static /* synthetic */ void c(HorLiveController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4998, new Class[]{HorLiveController.class}, Void.TYPE).isSupported) {
            x0.A();
        }
    }

    static /* synthetic */ boolean d(HorLiveController x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4997, new Class[]{HorLiveController.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.u();
    }

    public HorLiveController(Context context) {
        super(context);
    }

    public HorLiveController(Context context, AttributeSet attrs) {
        super(context, attrs);
        r(context);
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4999, new Class[0], Void.TYPE).isSupported) {
                HorLiveController.a(HorLiveController.this, "hideRunnable");
                HorLiveController.this.n();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void C() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4967(0x1367, float:6.96E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.os.Handler r1 = r0.S4
            if (r1 == 0) goto L_0x0029
            java.lang.Runnable r2 = r0.T4
            r1.removeCallbacks(r2)
            android.os.Handler r1 = r0.S4
            java.lang.Runnable r2 = r0.T4
            r3 = 6000(0x1770, double:2.9644E-320)
            r1.postDelayed(r2, r3)
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.HorLiveController.C():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void D() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 4968(0x1368, float:6.962E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.os.Handler r1 = r0.S4
            if (r1 == 0) goto L_0x0020
            java.lang.Runnable r2 = r0.T4
            r1.removeCallbacks(r2)
        L_0x0020:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.HorLiveController.D():void");
    }

    private void z(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4969, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.d(this.c, msg);
        }
    }

    public void t(int style) {
        if (!PatchProxy.proxy(new Object[]{new Integer(style)}, this, changeQuickRedirect, false, 4970, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.R4 = style;
            switch (style) {
                case 1:
                    this.f.setVisibility(8);
                    this.d.setVisibility(8);
                    this.p1.setVisibility(0);
                    this.p3.setVisibility(8);
                    return;
                case 2:
                    this.f.setVisibility(8);
                    this.d.setVisibility(8);
                    this.p1.setVisibility(8);
                    this.p3.setVisibility(8);
                    this.a2.setVisibility(8);
                    this.q.setVisibility(0);
                    return;
                case 3:
                    this.f.setVisibility(8);
                    this.d.setVisibility(8);
                    this.p1.setVisibility(8);
                    this.p3.setVisibility(8);
                    this.a2.setVisibility(8);
                    this.q.setVisibility(8);
                    return;
                default:
                    this.f.setVisibility(0);
                    this.d.setVisibility(0);
                    this.p1.setVisibility(8);
                    return;
            }
        }
    }

    private void r(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 4971, new Class[]{Context.class}, Void.TYPE).isSupported) {
            setBackgroundResource(R$drawable.bg_player_bottom);
            setGravity(80);
            LayoutInflater.from(context).inflate(R$layout.include_live_ctrl_hor, this, true);
            View findViewById = findViewById(R$id.fade_layout);
            this.p4 = findViewById;
            findViewById.setOnClickListener(this);
            this.z4 = (BatteryMeterView) findViewById(R$id.battery_view);
            this.a2 = findViewById(R$id.func_extras_layout);
            this.d = (TrafficStatsTextView) findViewById(R$id.tv_traffic_speed);
            this.f = (LDSTextView) findViewById(R$id.tv_resolution);
            this.q = (LDSTextView) findViewById(R$id.tv_goto_live);
            this.x = (ImageView) findViewById(R$id.iv_fullscreen);
            this.y = (ImageView) findViewById(R$id.iv_silence);
            this.z = (LottieAnimationView) findViewById(R$id.iv_full_alarm);
            this.a1 = (ImageView) findViewById(R$id.iv_full_light);
            this.p0 = (ImageView) findViewById(R$id.iv_full_path);
            ImageView imageView = (ImageView) findViewById(R$id.iv_sleep);
            this.p2 = imageView;
            imageView.setOnClickListener(this);
            ImageView imageView2 = (ImageView) findViewById(R$id.iv_stop);
            this.p1 = imageView2;
            imageView2.setOnClickListener(this);
            ImageView imageView3 = (ImageView) findViewById(R$id.iv_widescreen);
            this.p3 = imageView3;
            imageView3.setOnClickListener(this);
            this.y.setOnClickListener(this);
            this.x.setOnClickListener(this);
            this.f.setOnClickListener(this);
            this.q.setOnClickListener(this);
            this.p0.setOnClickListener(this);
            this.a1.setOnClickListener(this);
            this.z.setOnClickListener(this);
            A();
        }
    }

    public void setOnEventCallback(h onEventCallback) {
        this.A4 = onEventCallback;
    }

    public void s(boolean light, boolean alarm, boolean hasPath) {
        this.O4 = light;
        this.P4 = alarm;
        this.N4 = hasPath;
    }

    public void setSleep(boolean sleep) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Byte(sleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4972, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.Q4 = sleep;
            ImageView imageView = this.p2;
            if (!sleep) {
                i = 8;
            }
            imageView.setVisibility(i);
        }
    }

    public void H() {
        TrafficStatsTextView trafficStatsTextView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4973, new Class[0], Void.TYPE).isSupported && (trafficStatsTextView = this.d) != null) {
            trafficStatsTextView.k();
        }
    }

    public void I() {
        TrafficStatsTextView trafficStatsTextView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4974, new Class[0], Void.TYPE).isSupported && (trafficStatsTextView = this.d) != null) {
            trafficStatsTextView.l();
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4975, new Class[0], Void.TYPE).isSupported) {
            String str = this.c;
            Log.d(str, "toggleFunLayout: " + getVisibility());
            if (getVisibility() == 8) {
                m();
            } else {
                n();
            }
        }
    }

    public void setMax_res(int max_res) {
        this.F4 = max_res;
    }

    public void setMin_res(int min_res) {
        this.G4 = min_res;
    }

    public void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4976, new Class[0], Void.TYPE).isSupported) {
            D();
            com.leedarson.utils.a.b().c(this, 200);
            if (this.U4 != null) {
                com.leedarson.utils.a.b().d(this.U4, 200);
            }
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            animator.setDuration(300).addUpdateListener(new b());
            animator.start();
        }
    }

    public class b implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5000, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                float a = ((Float) animation.getAnimatedValue()).floatValue();
                if ((HorLiveController.this.R4 == 0 || (HorLiveController.this.R4 == 1 && HorLiveController.d(HorLiveController.this))) && HorLiveController.this.B4 != null && HorLiveController.this.B4.size() > 0) {
                    Iterator it = HorLiveController.this.B4.iterator();
                    while (it.hasNext()) {
                        ((View) it.next()).setAlpha(a);
                    }
                }
                if (a != 0.0f) {
                    return;
                }
                if ((HorLiveController.this.R4 == 0 || (HorLiveController.this.R4 == 1 && HorLiveController.d(HorLiveController.this))) && HorLiveController.this.B4 != null && HorLiveController.this.B4.size() > 0) {
                    Iterator it2 = HorLiveController.this.B4.iterator();
                    while (it2.hasNext()) {
                        ((View) it2.next()).setVisibility(8);
                    }
                }
            }
        }
    }

    public void m() {
        ArrayList<View> arrayList;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4977, new Class[0], Void.TYPE).isSupported) {
            C();
            com.leedarson.utils.a.b().a(this, 200);
            if (this.U4 != null) {
                com.leedarson.utils.a.b().e(this.U4, 200);
            }
            int i = this.R4;
            if ((i == 0 || (i == 1 && u())) && (arrayList = this.B4) != null && arrayList.size() > 0) {
                Iterator<View> it = this.B4.iterator();
                while (it.hasNext()) {
                    View view = it.next();
                    view.setAlpha(0.0f);
                    view.setVisibility(0);
                }
            }
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.setDuration(300).addUpdateListener(new c());
            animator.start();
        }
    }

    public class c implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5001, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                float a = ((Float) animation.getAnimatedValue()).floatValue();
                if (HorLiveController.this.B4 != null && HorLiveController.this.B4.size() > 0) {
                    Iterator it = HorLiveController.this.B4.iterator();
                    while (it.hasNext()) {
                        ((View) it.next()).setAlpha(a);
                    }
                }
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        h hVar;
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4978, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        h hVar2 = this.A4;
        if (hVar2 != null) {
            hVar2.f();
        }
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.iv_silence) {
            boolean z2 = true ^ this.H4;
            this.H4 = z2;
            h hVar3 = this.A4;
            if (hVar3 != null) {
                hVar3.b(z2);
            }
        } else if (viewId == R$id.tv_resolution) {
            G();
        } else if (viewId == R$id.iv_fullscreen) {
            h hVar4 = this.A4;
            if (hVar4 != null) {
                hVar4.e();
            }
        } else if (viewId == R$id.iv_full_alarm) {
            this.A4.o();
        } else if (viewId == R$id.iv_full_light) {
            this.A4.s();
        } else if (viewId == R$id.iv_sleep) {
            this.A4.n();
        } else if (viewId == R$id.iv_stop) {
            this.A4.d();
        } else if (viewId == R$id.iv_widescreen) {
            this.A4.r();
        } else if (viewId == R$id.iv_full_path) {
            this.A4.p();
        } else if (viewId == R$id.tv_goto_live && (hVar = this.A4) != null) {
            hVar.a();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void setTitleView(View titleView) {
        this.U4 = titleView;
    }

    public void setLinkView(View... linkView) {
        if (!PatchProxy.proxy(new Object[]{linkView}, this, changeQuickRedirect, false, 4979, new Class[]{View[].class}, Void.TYPE).isSupported) {
            if (this.B4 == null) {
                this.B4 = new ArrayList<>();
            }
            this.B4.clear();
            if (linkView != null) {
                for (View view : linkView) {
                    if (view != null) {
                        this.B4.add(view);
                    }
                }
            }
        }
    }

    public void F() {
        int i = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4980, new Class[0], Void.TYPE).isSupported) {
            this.a1.setVisibility(this.O4 ? 0 : 8);
            this.z.setVisibility(this.P4 ? 0 : 8);
            ImageView imageView = this.p0;
            if (!this.N4) {
                i = 8;
            }
            imageView.setVisibility(i);
        }
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4981, new Class[0], Void.TYPE).isSupported) {
            this.a1.setVisibility(8);
            this.z.setVisibility(8);
            this.p0.setVisibility(8);
        }
    }

    public void E(String power) {
        if (!PatchProxy.proxy(new Object[]{power}, this, changeQuickRedirect, false, 4982, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                this.z4.setProgress(Integer.valueOf(power).intValue());
                this.z4.setVisibility(0);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4983, new Class[0], Void.TYPE).isSupported) {
            this.z4.setVisibility(8);
        }
    }

    public void setCharging(boolean isCharging) {
        BatteryMeterView batteryMeterView;
        Object[] objArr = {new Byte(isCharging ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4984, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (batteryMeterView = this.z4) != null) {
            batteryMeterView.setCharging(isCharging);
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Object c;

        d(Object obj) {
            this.c = obj;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5002, new Class[0], Void.TYPE).isSupported) {
                try {
                    JSONObject dataObj = (JSONObject) this.c;
                    if (dataObj != null) {
                        if (dataObj.has("isLightOn")) {
                            boolean unused = HorLiveController.this.I4 = dataObj.getBoolean("isLightOn");
                        }
                        if (dataObj.has(H5ActionName.ACTION_MUTE)) {
                            boolean unused2 = HorLiveController.this.H4 = dataObj.getBoolean(H5ActionName.ACTION_MUTE);
                        }
                        if (dataObj.has("isAlarm")) {
                            boolean unused3 = HorLiveController.this.K4 = dataObj.getBoolean("isAlarm");
                        }
                        if (dataObj.has("isFullScreen")) {
                            boolean unused4 = HorLiveController.this.L4 = dataObj.getBoolean("isFullScreen");
                        }
                        if (dataObj.has("isWide")) {
                            boolean unused5 = HorLiveController.this.M4 = dataObj.getBoolean("isWide");
                        }
                        if (dataObj.has("isPathOn")) {
                            boolean unused6 = HorLiveController.this.J4 = dataObj.getBoolean("isPathOn");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HorLiveController.c(HorLiveController.this);
            }
        }
    }

    public void update(Observable observable, Object arg) {
        Class[] clsArr = {Observable.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{observable, arg}, this, changeQuickRedirect, false, 4985, clsArr, Void.TYPE).isSupported) {
            post(new d(arg));
        }
    }

    private void A() {
        int i = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4986, new Class[0], Void.TYPE).isSupported) {
            ColorStateList colorStateList = null;
            this.a1.setImageTintList(this.I4 ? null : ColorStateList.valueOf(-1));
            this.a1.setImageResource(this.I4 ? R$drawable.ic_live_light : R$drawable.ic_light);
            this.p0.setImageResource(this.J4 ? R$drawable.ic_path_on : R$drawable.ic_path);
            this.p0.setImageTintList(this.J4 ? null : ColorStateList.valueOf(-1));
            LottieAnimationView lottieAnimationView = this.z;
            if (!this.K4) {
                colorStateList = ColorStateList.valueOf(-1);
            }
            lottieAnimationView.setImageTintList(colorStateList);
            if (this.K4) {
                this.z.setPadding(0, 0, 0, 0);
                this.z.setAnimation("ipcalarming.json");
                this.z.setRepeatCount(-1);
                this.z.u();
            } else {
                this.V4.post(new j(this));
            }
            this.y.setImageResource(this.H4 ? R$drawable.ic_silence : R$drawable.ic_voice);
            this.x.setImageResource(this.L4 ? R$drawable.ic_live_icon_min : R$drawable.ic_fullscreen);
            this.p3.setImageResource(this.M4 ? R$drawable.ic_live_wide_min : R$drawable.ic_live_wide);
            if (this.R4 == 0) {
                ImageView imageView = this.p3;
                if (this.L4) {
                    i = 8;
                }
                imageView.setVisibility(i);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: v */
    public /* synthetic */ void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4995, new Class[0], Void.TYPE).isSupported) {
            if (this.W4 == 0) {
                this.W4 = com.leedarson.base.utils.d.b(getContext(), 9.0f);
            }
            LottieAnimationView lottieAnimationView = this.z;
            int i = this.W4;
            lottieAnimationView.setPadding(i, i, i, i);
            this.z.setImageResource(R$drawable.ic_alarm);
        }
    }

    public void setResolution(int quality) {
        if (!PatchProxy.proxy(new Object[]{new Integer(quality)}, this, changeQuickRedirect, false, 4987, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.C4 = quality;
            switch (quality) {
                case 1:
                    this.D4 = 1;
                    int i = this.F4;
                    if (i == 4) {
                        this.f.setText(PubUtils.getString(getContext(), R$string._4k));
                        return;
                    } else if (i == 3) {
                        this.f.setText(PubUtils.getString(getContext(), R$string._3k));
                        return;
                    } else if (i == 2) {
                        this.f.setText(PubUtils.getString(getContext(), R$string._2k));
                        return;
                    } else {
                        this.f.setText(PubUtils.getString(getContext(), R$string.high_definition));
                        return;
                    }
                case 5:
                    this.D4 = 0;
                    int i2 = this.G4;
                    if (i2 == 3) {
                        this.f.setText(PubUtils.getString(getContext(), R$string._3k));
                        return;
                    } else if (i2 == 2) {
                        this.f.setText(PubUtils.getString(getContext(), R$string._2k));
                        return;
                    } else if (i2 == 1) {
                        this.f.setText(PubUtils.getString(getContext(), R$string.high_definition));
                        return;
                    } else {
                        this.f.setText(PubUtils.getString(getContext(), R$string.sd));
                        return;
                    }
                case 16:
                    this.D4 = 2;
                    this.f.setText(PubUtils.getString(BaseApplication.b(), R$string._auto_resulution));
                    return;
                default:
                    return;
            }
        }
    }

    public void setQualityTotalCount(int total) {
        if (total != 0) {
            this.E4 = total;
        }
    }

    private boolean u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4988, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4989, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setVisibility(visibility);
            if (getVisibility() == 8) {
                D();
            }
        }
    }

    private void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4990, new Class[0], Void.TYPE).isSupported) {
            String[] alerts = {o(16), o(1), o(5)};
            int defaultCheckIndex = 0;
            int i = this.E4;
            if (i == 2) {
                int i2 = this.C4;
                if (i2 == 5) {
                    defaultCheckIndex = 1;
                } else if (i2 == 1) {
                    defaultCheckIndex = 0;
                }
                alerts = new String[]{o(1), o(5)};
            } else if (i == 3) {
                int i3 = this.C4;
                if (i3 == 16) {
                    defaultCheckIndex = 0;
                } else if (i3 == 1) {
                    defaultCheckIndex = 1;
                } else if (i3 == 5) {
                    defaultCheckIndex = 2;
                }
            }
            com.leedarson.base.views.d c2 = new com.leedarson.base.views.d(getContext()).d().c(defaultCheckIndex, alerts, BaseApplication.b().getResources().getColor(R$color.primary_color), new k(this));
            this.X4 = c2;
            c2.l();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public /* synthetic */ void y(int which) {
        Object[] objArr = {new Integer(which)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4994, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int tempQuanlity = 0;
            int i = this.E4;
            if (i != 2) {
                if (i == 3) {
                    switch (which) {
                        case 0:
                            tempQuanlity = 16;
                            break;
                        case 1:
                            tempQuanlity = 1;
                            break;
                        case 2:
                            tempQuanlity = 5;
                            break;
                    }
                }
            } else {
                switch (which) {
                    case 0:
                        tempQuanlity = 1;
                        break;
                    case 1:
                        tempQuanlity = 5;
                        break;
                }
            }
            h hVar = this.A4;
            if (hVar != null && tempQuanlity != this.C4) {
                hVar.l(tempQuanlity);
            }
        }
    }

    public void l() {
        com.leedarson.base.views.d dVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4991, new Class[0], Void.TYPE).isSupported && (dVar = this.X4) != null) {
            dVar.e();
        }
    }

    private String o(int tempQuanlity) {
        Object[] objArr = {new Integer(tempQuanlity)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4992, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String tempQualityTip = "";
        switch (tempQuanlity) {
            case 1:
                int i = this.F4;
                if (i != 4) {
                    if (i != 3) {
                        if (i != 2) {
                            tempQualityTip = PubUtils.getString(getContext(), R$string.high_definition);
                            break;
                        } else {
                            tempQualityTip = PubUtils.getString(getContext(), R$string._2k);
                            break;
                        }
                    } else {
                        tempQualityTip = PubUtils.getString(getContext(), R$string._3k);
                        break;
                    }
                } else {
                    tempQualityTip = PubUtils.getString(getContext(), R$string._4k);
                    break;
                }
            case 5:
                int i2 = this.G4;
                if (i2 != 3) {
                    if (i2 != 2) {
                        if (i2 != 1) {
                            tempQualityTip = PubUtils.getString(getContext(), R$string.sd);
                            break;
                        } else {
                            tempQualityTip = PubUtils.getString(getContext(), R$string.high_definition);
                            break;
                        }
                    } else {
                        tempQualityTip = PubUtils.getString(getContext(), R$string._2k);
                        break;
                    }
                } else {
                    tempQualityTip = PubUtils.getString(getContext(), R$string._3k);
                    break;
                }
            case 16:
                tempQualityTip = PubUtils.getString(BaseApplication.b(), R$string._auto_resulution);
                break;
        }
        return tempQualityTip + JustifyTextView.TWO_CHINESE_BLANK;
    }

    public boolean B(String streamType) {
        boolean tempChanged = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{streamType}, this, changeQuickRedirect, false, 4993, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int tempQuanlity = 0;
        char c2 = 65535;
        switch (streamType.hashCode()) {
            case 48:
                if (streamType.equals("0")) {
                    c2 = 0;
                    break;
                }
                break;
            case 49:
                if (streamType.equals("1")) {
                    c2 = 1;
                    break;
                }
                break;
            case 50:
                if (streamType.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    c2 = 2;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                tempQuanlity = 1;
                break;
            case 1:
                tempQuanlity = 5;
                break;
            case 2:
                tempQuanlity = 16;
                break;
        }
        if (tempQuanlity == this.C4) {
            tempChanged = false;
        }
        timber.log.a.g("streamType").c("判定属性变化111：  targetStreamType=" + streamType + "  tempChanged=" + tempChanged + " quality=" + this.C4, new Object[0]);
        if (tempChanged) {
            setResolution(tempQuanlity);
        }
        timber.log.a.g("streamType").c("判定属性变化222：  targetStreamType=" + streamType + "  tempChanged=" + tempChanged + " quality=" + this.C4, new Object[0]);
        return tempChanged;
    }

    public int getCurrentQuality() {
        return this.C4;
    }
}
