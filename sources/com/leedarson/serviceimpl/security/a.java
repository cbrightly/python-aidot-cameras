package com.leedarson.serviceimpl.security;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.security.bean.ShowAlarmBean;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.ScreenConfigurationChangeEvent;
import com.leedarson.serviceinterface.event.SwitchToLastMainWebTabEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.petterp.floatingx.assist.FxGravity;
import com.petterp.floatingx.assist.helper.a;
import com.petterp.floatingx.view.FxManagerView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import meshsdk.BaseResp;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;

/* compiled from: AlarmWindowHelper */
public class a implements View.OnClickListener {
    private static a c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private Dialog a1;
    private LoggerService a2;
    int d = com.leedarson.base.utils.d.b(BaseApplication.b(), 18.0f);
    int f = com.leedarson.base.utils.d.b(BaseApplication.b(), 44.0f);
    private com.leedarson.base.views.common.dialogs.a p0;
    /* access modifiers changed from: private */
    public Handler p1;
    private View.OnClickListener p2 = new e();
    private com.leedarson.serviceimpl.security.http.b q;
    private ShowAlarmBean x;
    /* access modifiers changed from: private */
    public Activity y;
    private boolean z;

    static /* synthetic */ void b(a x0, String x1) {
        Class[] clsArr = {a.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8652, clsArr, Void.TYPE).isSupported) {
            x0.m(x1);
        }
    }

    static /* synthetic */ void c(a x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8653, new Class[]{a.class}, Void.TYPE).isSupported) {
            x0.k();
        }
    }

    static /* synthetic */ void d(a x0, String x1) {
        Class[] clsArr = {a.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8654, clsArr, Void.TYPE).isSupported) {
            x0.n(x1);
        }
    }

    static /* synthetic */ void e(a x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8655, new Class[]{a.class}, Void.TYPE).isSupported) {
            x0.j();
        }
    }

    public static a h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 8635, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (c == null) {
            c = new a();
        }
        return c;
    }

    private a() {
        if (!org.greenrobot.eventbus.c.c().j(this)) {
            org.greenrobot.eventbus.c.c().p(this);
        }
        this.p1 = new Handler(Looper.getMainLooper());
        this.a2 = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
    }

    public void q(Activity activity, ShowAlarmBean showAlarmBean) {
        if (!PatchProxy.proxy(new Object[]{activity, showAlarmBean}, this, changeQuickRedirect, false, 8636, new Class[]{Activity.class, ShowAlarmBean.class}, Void.TYPE).isSupported) {
            try {
                this.x = showAlarmBean;
                if (l()) {
                    n("收到web调用显示悬浮窗,悬浮窗正在显示，直接显示更新悬浮窗");
                    t();
                } else if (this.z) {
                    n("收到web调用显示悬浮窗,悬浮窗已初始化，直接显示更新悬浮窗");
                    t();
                    com.petterp.floatingx.a.c().d(this.y);
                } else {
                    this.z = true;
                    this.y = activity;
                    p();
                    View view = LayoutInflater.from(BaseApplication.b().getApplicationContext()).inflate(R$layout.layout_float_view1, (ViewGroup) null);
                    ((TextView) view.findViewById(R$id.title)).setText(showAlarmBean.title);
                    ((TextView) view.findViewById(R$id.content)).setText(showAlarmBean.subTitle);
                    LottieAnimationView lottieView = (LottieAnimationView) view.findViewById(R$id.lottieView);
                    lottieView.setAnimation("security_alarming.json");
                    lottieView.setRepeatCount(Integer.MAX_VALUE);
                    lottieView.u();
                    int i = this.d;
                    com.petterp.floatingx.a.i(((a.C0198a) ((a.C0198a) ((a.C0198a) ((a.C0198a) ((a.C0198a) ((a.C0198a) com.petterp.floatingx.assist.helper.a.c().t(BaseApplication.b()).l(view)).h((float) this.f, 0.0f, 0.0f, (float) this.d)).e(0.0f, (float) i, (float) i, (float) i)).k(FxGravity.RIGHT_OR_TOP)).n(this)).d(new C0159a(view))).s().q());
                    if (!"none".equals(this.x.vibrate)) {
                        u();
                    }
                    n("收到web调用显示悬浮窗, 初始化并且显示悬浮窗成功");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                n("收到web调用显示悬浮窗, 初始化并且显示悬浮窗异常:" + e2.getMessage());
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.security.a$a  reason: collision with other inner class name */
    /* compiled from: AlarmWindowHelper */
    public class C0159a extends com.petterp.floatingx.assist.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View c;

        C0159a(View view) {
            this.c = view;
        }

        @NonNull
        public Animator c(@Nullable FrameLayout frameLayout) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{frameLayout}, this, changeQuickRedirect, false, 8656, new Class[]{FrameLayout.class}, Animator.class);
            if (proxy.isSupported) {
                return (Animator) proxy.result;
            }
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this.c, "translationX", new float[]{3000.0f, 0.0f});
            objectAnimator.setDuration(500);
            return objectAnimator;
        }

        @NonNull
        public Animator g(@Nullable FrameLayout frameLayout) {
            return null;
        }
    }

    public void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8637, new Class[0], Void.TYPE).isSupported) {
            try {
                FxManagerView managerView = com.petterp.floatingx.a.c().c();
                if (managerView != null) {
                    TextView tvContent = (TextView) managerView.findViewById(R$id.content);
                    TextView tvTitle = (TextView) managerView.findViewById(R$id.title);
                    if (tvTitle != null) {
                        tvTitle.setText(this.x.title);
                    }
                    if (tvContent != null) {
                        tvContent.setText(this.x.subTitle);
                    }
                    n("悬浮窗更新内容成功");
                }
            } catch (Exception e2) {
                n("悬浮窗更新内容异常：" + e2.getMessage());
            }
        }
    }

    /* compiled from: AlarmWindowHelper */
    public class b implements Application.ActivityLifecycleCallbacks {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        public void onActivityStarted(@NonNull Activity activity) {
        }

        public void onActivityResumed(@NonNull Activity activity) {
            if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8657, new Class[]{Activity.class}, Void.TYPE).isSupported) {
                Activity unused = a.this.y = activity;
                a aVar = a.this;
                a.b(aVar, activity + " onResume");
            }
        }

        public void onActivityPaused(@NonNull Activity activity) {
        }

        public void onActivityStopped(@NonNull Activity activity) {
        }

        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }

        public void onActivityDestroyed(@NonNull Activity activity) {
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
            r5 = 8638(0x21be, float:1.2104E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.app.Activity r1 = r0.y
            if (r1 == 0) goto L_0x0027
            android.app.Application r1 = r1.getApplication()
            com.leedarson.serviceimpl.security.a$b r2 = new com.leedarson.serviceimpl.security.a$b
            r2.<init>()
            r1.registerActivityLifecycleCallbacks(r2)
        L_0x0027:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.security.a.p():void");
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8639, new Class[0], Void.TYPE).isSupported) {
            try {
                if (l()) {
                    k();
                    j();
                    com.petterp.floatingx.a.c().hide();
                    n("收到web调用隐藏悬浮窗,隐藏成功");
                    return;
                }
                n("收到web调用隐藏悬浮窗,悬浮窗没显示，不需要隐藏");
            } catch (Exception e2) {
                n("收到web调用隐藏悬浮窗,隐藏异常:" + e2.getMessage());
            }
        }
    }

    public boolean l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8640, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.z) {
            return com.petterp.floatingx.a.c().f();
        }
        m("悬浮窗未初始化");
        return false;
    }

    private void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8641, new Class[0], Void.TYPE).isSupported) {
            try {
                ((Vibrator) this.y.getSystemService("vibrator")).vibrate(200);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8642, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (this.x == null) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        m("onclick 悬浮窗");
        if (this.x.haveDetailPage) {
            r();
        } else {
            s();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8643, new Class[0], Void.TYPE).isSupported) {
            try {
                com.leedarson.base.views.common.dialogs.a aVar = this.p0;
                if (aVar == null || !aVar.isShowing()) {
                    com.leedarson.base.views.common.dialogs.a aVar2 = new com.leedarson.base.views.common.dialogs.a(this.y);
                    this.p0 = aVar2;
                    aVar2.i(this.x.dialogTitle);
                    this.p0.h(this.x.dialogContent);
                    this.p0.f(PubUtils.getString(BaseApplication.b(), R$string.lsd_security_skip_once));
                    this.p0.d(PubUtils.getString(BaseApplication.b(), R$string.lsd_security_Ignore));
                    this.p0.c(new c());
                    this.p0.show();
                }
            } catch (Exception e2) {
                m("显示2个按钮弹窗 --异常:" + e2.getMessage());
            }
        }
    }

    /* compiled from: AlarmWindowHelper */
    public class c implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8658, new Class[0], Void.TYPE).isSupported) {
                a.c(a.this);
                a.this.o(1);
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8659, new Class[0], Void.TYPE).isSupported) {
                a.c(a.this);
                a.this.o(2);
            }
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8644, new Class[0], Void.TYPE).isSupported) {
            try {
                Dialog dialog = this.a1;
                if (dialog == null || !dialog.isShowing()) {
                    Dialog dialog2 = new Dialog(this.y, R$style.Theme_dialog);
                    this.a1 = dialog2;
                    dialog2.setContentView(R$layout.security_threebtn_dialog_layout);
                    this.a1.setCanceledOnTouchOutside(false);
                    LDSTextView titleText = (LDSTextView) this.a1.findViewById(R$id.tip_title_tv);
                    if (TextUtils.isEmpty(this.x.dialogTitle)) {
                        titleText.setVisibility(8);
                    } else {
                        titleText.setText(this.x.dialogTitle);
                        titleText.setVisibility(0);
                    }
                    ((LDSTextView) this.a1.findViewById(R$id.tip_dialog_tv)).setText(this.x.dialogContent);
                    LDSTextView btnTv0 = (LDSTextView) this.a1.findViewById(R$id.btn_tv0);
                    btnTv0.setText(PubUtils.getString(BaseApplication.b(), R$string.lsd_security_skip_once));
                    btnTv0.setOnClickListener(this.p2);
                    LDSTextView btnTv1 = (LDSTextView) this.a1.findViewById(R$id.btn_tv1);
                    btnTv1.setText(PubUtils.getString(BaseApplication.b(), R$string.lsd_security_more_detail));
                    btnTv1.setOnClickListener(this.p2);
                    LDSTextView btnTv2 = (LDSTextView) this.a1.findViewById(R$id.btn_tv2);
                    btnTv2.setText(PubUtils.getString(BaseApplication.b(), R$string.lsd_security_Ignore));
                    btnTv2.setOnClickListener(this.p2);
                    this.a1.show();
                }
            } catch (Exception e2) {
                m("显示3个按钮弹窗 --异常:" + e2.getMessage());
            }
        }
    }

    private void m(String msg) {
    }

    private void n(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8645, new Class[]{String.class}, Void.TYPE).isSupported) {
            LoggerService loggerService = this.a2;
            if (loggerService != null) {
                loggerService.reportELK(this, msg, "info", "AppSecurityAlarm");
            }
            m(msg);
        }
    }

    public com.leedarson.serviceimpl.security.http.b g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8646, new Class[0], com.leedarson.serviceimpl.security.http.b.class);
        if (proxy.isSupported) {
            return (com.leedarson.serviceimpl.security.http.b) proxy.result;
        }
        if (this.q == null) {
            this.q = new com.leedarson.serviceimpl.security.http.b(this.y.getApplicationContext());
        }
        return this.q;
    }

    /* compiled from: AlarmWindowHelper */
    public class d implements io.reactivex.functions.e<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        d(int i) {
            this.c = i;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8661, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((String) obj);
            }
        }

        public void a(String s) {
            if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 8660, new Class[]{String.class}, Void.TYPE).isSupported) {
                int i = this.c;
                if (i == 1) {
                    a aVar = a.this;
                    a.d(aVar, "skip once close response:" + s);
                } else if (i == 2) {
                    a aVar2 = a.this;
                    a.d(aVar2, "ignore response:" + s);
                }
            }
        }
    }

    public void o(int operateType) {
        Object[] objArr = {new Integer(operateType)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8647, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            g().c(operateType, SharePreferenceUtils.getPrefString(this.y, "houseId", "")).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.android.schedulers.a.a()).X(new d(operateType));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void j() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 8648(0x21c8, float:1.2118E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.app.Dialog r1 = r0.a1
            if (r1 == 0) goto L_0x0029
            boolean r1 = r1.isShowing()
            if (r1 == 0) goto L_0x0029
            android.app.Dialog r1 = r0.a1
            r1.dismiss()
            r1 = 0
            r0.a1 = r1
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.security.a.j():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 8649(0x21c9, float:1.212E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.base.views.common.dialogs.a r1 = r0.p0
            if (r1 == 0) goto L_0x0029
            boolean r1 = r1.isShowing()
            if (r1 == 0) goto L_0x0029
            com.leedarson.base.views.common.dialogs.a r1 = r0.p0
            r1.dismiss()
            r1 = 0
            r0.p0 = r1
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.security.a.k():void");
    }

    /* compiled from: AlarmWindowHelper */
    public class e implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8662, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View v = view;
            a.e(a.this);
            if (v.getId() == R$id.btn_tv0) {
                a.this.o(1);
            } else if (v.getId() == R$id.btn_tv1) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) "HomeModes.AutoAlarm");
                    JSONObject param = new JSONObject();
                    param.put("animation", 0);
                    jsonObject.put("params", (Object) param);
                    a.b(a.this, "push跳转调用发起");
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                    org.greenrobot.eventbus.c.c().l(new SwitchToLastMainWebTabEvent());
                    if (!com.leedarson.base.utils.c.h().l()) {
                        int delayTime = BaseApplication.b().p0 ? 600 : BaseResp.ERR_MSG_SEND_FAIL;
                        a aVar = a.this;
                        a.b(aVar, "不在mainActivity，延迟:" + delayTime + "ms,关闭除了main以外的所有activity");
                        a.this.p1.postDelayed(new C0160a(), (long) delayTime);
                    }
                } catch (Exception e) {
                }
            } else if (v.getId() == R$id.btn_tv2) {
                a.this.o(2);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        /* renamed from: com.leedarson.serviceimpl.security.a$e$a  reason: collision with other inner class name */
        /* compiled from: AlarmWindowHelper */
        public class C0160a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0160a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8663, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.base.utils.c.h().g();
                }
            }
        }
    }

    @l
    public void onScreenConfigurationChange(ScreenConfigurationChangeEvent screenConfigurationChangeEvent) {
        if (!PatchProxy.proxy(new Object[]{screenConfigurationChangeEvent}, this, changeQuickRedirect, false, 8650, new Class[]{ScreenConfigurationChangeEvent.class}, Void.TYPE).isSupported) {
            if (!this.z) {
                return;
            }
            if (screenConfigurationChangeEvent.isPortrait()) {
                com.petterp.floatingx.listener.control.b a = com.petterp.floatingx.a.c().a();
                int i = this.d;
                a.b(0.0f, (float) i, (float) i, (float) i);
                return;
            }
            com.petterp.floatingx.listener.control.b a3 = com.petterp.floatingx.a.c().a();
            int i2 = this.d;
            a3.b(0.0f, (float) ((i2 * 2) + (i2 - 10)), (float) i2, (float) ((i2 * 2) + (i2 - 10)));
        }
    }
}
