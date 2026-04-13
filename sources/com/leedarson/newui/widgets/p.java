package com.leedarson.newui.widgets;

import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.app.NotificationCompat;
import com.leedarson.R$anim;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.n;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import meshsdk.model.json.RoutineRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LDSLiveAnalyseDialog.kt */
public final class p extends Dialog implements o0 {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    public static ChangeQuickRedirect changeQuickRedirect;
    private final /* synthetic */ o0 d = p0.b();
    private ComponentCallbacks f;
    /* access modifiers changed from: private */
    @NotNull
    public final com.leedarson.newui.analyse.b q;
    private Animation x;
    private boolean y;
    /* access modifiers changed from: private */
    public Dialog z;

    @NotNull
    public g getCoroutineContext() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5600, new Class[0], g.class);
        return proxy.isSupported ? (g) proxy.result : this.d.getCoroutineContext();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public p(@NotNull Context context, @NotNull String device) {
        super(context, R$style.BottomDialog);
        k.e(context, "context");
        k.e(device, RoutineRule.THEN_TYPE_DEVICE);
        setContentView(LayoutInflater.from(context).inflate(R$layout.dialog_live_analyse, (ViewGroup) null));
        h();
        this.q = new com.leedarson.newui.analyse.b(device);
    }

    public static final /* synthetic */ void a(p $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 5617, new Class[]{p.class}, Void.TYPE).isSupported) {
            $this.f();
        }
    }

    public static final /* synthetic */ void d(p $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 5616, new Class[]{p.class}, Void.TYPE).isSupported) {
            $this.w();
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 5601, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            w();
            this.f = new b(this);
            Context context = getContext();
            ComponentCallbacks componentCallbacks = this.f;
            if (componentCallbacks != null) {
                context.registerComponentCallbacks(componentCallbacks);
            } else {
                k.t("mListener");
                throw null;
            }
        }
    }

    /* compiled from: LDSLiveAnalyseDialog.kt */
    public static final class b implements ComponentCallbacks {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ p c;

        b(p $receiver) {
            this.c = $receiver;
        }

        public void onConfigurationChanged(@NotNull Configuration newConfig) {
            if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 5618, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
                k.e(newConfig, "newConfig");
                p.d(this.c);
            }
        }

        public void onLowMemory() {
        }
    }

    private final void w() {
        Window $this$setWindowAttr_u24lambda_u2d1;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5602, new Class[0], Void.TYPE).isSupported && ($this$setWindowAttr_u24lambda_u2d1 = getWindow()) != null) {
            $this$setWindowAttr_u24lambda_u2d1.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = $this$setWindowAttr_u24lambda_u2d1.getAttributes();
            WindowManager.LayoutParams $this$setWindowAttr_u24lambda_u2d1_u24lambda_u2d0 = attributes;
            $this$setWindowAttr_u24lambda_u2d1_u24lambda_u2d0.width = -1;
            $this$setWindowAttr_u24lambda_u2d1_u24lambda_u2d0.height = (int) (((double) n.b($this$setWindowAttr_u24lambda_u2d1.getContext())) * 0.89d);
            $this$setWindowAttr_u24lambda_u2d1_u24lambda_u2d0.gravity = 80;
            $this$setWindowAttr_u24lambda_u2d1.setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            x xVar = x.a;
            $this$setWindowAttr_u24lambda_u2d1.setAttributes(attributes);
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5603, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            if (this.f != null) {
                Context context = getContext();
                ComponentCallbacks componentCallbacks = this.f;
                if (componentCallbacks != null) {
                    context.unregisterComponentCallbacks(componentCallbacks);
                } else {
                    k.t("mListener");
                    throw null;
                }
            } else {
                k.t("mListener");
                throw null;
            }
        }
    }

    public final void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5604, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            Dialog $this$initView_u24lambda_u2d4 = dialog;
            $this$initView_u24lambda_u2d4.setContentView(R$layout.del_dialog_layout);
            $this$initView_u24lambda_u2d4.setCanceledOnTouchOutside(false);
            ((LDSTextView) $this$initView_u24lambda_u2d4.findViewById(R$id.tip_content_tv)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_stop_testing));
            int i = R$id.left_btn_tv;
            ((LDSTextView) $this$initView_u24lambda_u2d4.findViewById(i)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_stop));
            int i2 = R$id.right_btn_tv;
            ((LDSTextView) $this$initView_u24lambda_u2d4.findViewById(i2)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_matter_continue_continue));
            ((LDSTextView) $this$initView_u24lambda_u2d4.findViewById(i)).setOnClickListener(new k(this, $this$initView_u24lambda_u2d4));
            ((LDSTextView) $this$initView_u24lambda_u2d4.findViewById(i2)).setOnClickListener(new i($this$initView_u24lambda_u2d4));
            x xVar = x.a;
            this.z = dialog;
            ((ImageView) findViewById(R$id.img_analyse_close)).setOnClickListener(new h(this));
            ((LDSTextView) findViewById(R$id.tv_confirm)).setOnClickListener(new j(this));
            ((LDSTextView) findViewById(R$id.tv_goto_live_view)).setOnClickListener(new l(this));
            com.bumptech.glide.b.t(getContext()).k().K0(Integer.valueOf(R$drawable.ipc_connection_test)).H0((ImageView) findViewById(R$id.img_test_loading));
            Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R$anim.rotate_loading_icon_live);
            k.d(loadAnimation, "loadAnimation(context,R.anim.rotate_loading_icon_live)");
            this.x = loadAnimation;
            ImageView imageView = (ImageView) findViewById(R$id.img_loading1);
            Animation animation = this.x;
            if (animation != null) {
                imageView.startAnimation(animation);
                ImageView imageView2 = (ImageView) findViewById(R$id.img_loading2);
                Animation animation2 = this.x;
                if (animation2 != null) {
                    imageView2.startAnimation(animation2);
                    ImageView imageView3 = (ImageView) findViewById(R$id.img_loading3);
                    Animation animation3 = this.x;
                    if (animation3 != null) {
                        imageView3.startAnimation(animation3);
                        setCanceledOnTouchOutside(false);
                        return;
                    }
                    k.t("animation");
                    throw null;
                }
                k.t("animation");
                throw null;
            }
            k.t("animation");
            throw null;
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void i(p this$0, Dialog $this_apply, View view) {
        Class[] clsArr = {p.class, Dialog.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, $this_apply, view}, (Object) null, changeQuickRedirect, true, 5611, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        k.e($this_apply, "$this_apply");
        this$0.dismiss();
        $this_apply.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void k(Dialog $this_apply, View view) {
        Class[] clsArr = {Dialog.class, View.class};
        if (PatchProxy.proxy(new Object[]{$this_apply, view}, (Object) null, changeQuickRedirect, true, 5612, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e($this_apply, "$this_apply");
        $this_apply.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void m(p this$0, View view) {
        Class[] clsArr = {p.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 5613, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.v();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void n(p this$0, View view) {
        Class[] clsArr = {p.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 5614, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void p(p this$0, View view) {
        Class[] clsArr = {p.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 5615, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.v();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    @f(c = "com.leedarson.newui.widgets.LDSLiveAnalyseDialog$startTest$1", f = "LDSLiveAnalyseDialog.kt", l = {192}, m = "invokeSuspend")
    /* compiled from: LDSLiveAnalyseDialog.kt */
    public static final class c extends l implements kotlin.jvm.functions.p<o0, d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ p this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(p pVar, d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = pVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 5620, new Class[]{Object.class, d.class}, d.class);
            return proxy.isSupported ? (d) proxy.result : new c(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 5622, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 5621, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @f(c = "com.leedarson.newui.widgets.LDSLiveAnalyseDialog$startTest$1$1", f = "LDSLiveAnalyseDialog.kt", l = {}, m = "invokeSuspend")
        /* compiled from: LDSLiveAnalyseDialog.kt */
        public static final class a extends l implements kotlin.jvm.functions.p<com.leedarson.newui.analyse.a, d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ p this$0;

            /* renamed from: com.leedarson.newui.widgets.p$c$a$a  reason: collision with other inner class name */
            /* compiled from: LDSLiveAnalyseDialog.kt */
            public final /* synthetic */ class C0123a {
                public static final /* synthetic */ int[] a;

                static {
                    int[] iArr = new int[com.leedarson.newui.analyse.a.values().length];
                    iArr[com.leedarson.newui.analyse.a.AppNetworkNoProblem.ordinal()] = 1;
                    iArr[com.leedarson.newui.analyse.a.DeviceNetworkNoProblem.ordinal()] = 2;
                    iArr[com.leedarson.newui.analyse.a.PingNoProblem.ordinal()] = 3;
                    iArr[com.leedarson.newui.analyse.a.AppNetworkUnconnected.ordinal()] = 4;
                    iArr[com.leedarson.newui.analyse.a.AppNetworkUnavailable.ordinal()] = 5;
                    iArr[com.leedarson.newui.analyse.a.AppNetworkNoReason.ordinal()] = 6;
                    iArr[com.leedarson.newui.analyse.a.PingError.ordinal()] = 7;
                    iArr[com.leedarson.newui.analyse.a.DeviceOffline.ordinal()] = 8;
                    iArr[com.leedarson.newui.analyse.a.DeviceRssiLow.ordinal()] = 9;
                    iArr[com.leedarson.newui.analyse.a.DeviceNetworkNoReason.ordinal()] = 10;
                    iArr[com.leedarson.newui.analyse.a.DeviceP2PNoReason.ordinal()] = 11;
                    a = iArr;
                }
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(p pVar, d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = pVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 5624, new Class[]{Object.class, d.class}, d.class);
                if (proxy.isSupported) {
                    return (d) proxy.result;
                }
                a aVar = new a(this.this$0, dVar);
                aVar.L$0 = obj;
                return aVar;
            }

            @Nullable
            public final Object invoke(@NotNull com.leedarson.newui.analyse.a aVar, @Nullable d<? super x> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{aVar, dVar}, this, changeQuickRedirect, false, 5625, new Class[]{com.leedarson.newui.analyse.a.class, d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((a) create(aVar, dVar)).invokeSuspend(x.a);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 5626, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((com.leedarson.newui.analyse.a) obj, (d<? super x>) (d) obj2);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5623, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b(obj);
                        com.leedarson.newui.analyse.a it = (com.leedarson.newui.analyse.a) this.L$0;
                        this.this$0.g(it.toString());
                        switch (C0123a.a[it.ordinal()]) {
                            case 1:
                                p pVar = this.this$0;
                                int i = R$id.img_loading1;
                                ((ImageView) pVar.findViewById(i)).clearAnimation();
                                ((ImageView) this.this$0.findViewById(i)).setImageResource(R$drawable.img_analyse_success);
                                break;
                            case 2:
                                p pVar2 = this.this$0;
                                int i2 = R$id.img_loading2;
                                ((ImageView) pVar2.findViewById(i2)).clearAnimation();
                                ((ImageView) this.this$0.findViewById(i2)).setImageResource(R$drawable.img_analyse_success);
                                break;
                            case 3:
                                Dialog b = this.this$0.z;
                                if (b != null) {
                                    if (kotlin.coroutines.jvm.internal.b.a(b.isShowing()).booleanValue()) {
                                        Dialog b2 = this.this$0.z;
                                        if (b2 != null) {
                                            b2.dismiss();
                                        } else {
                                            k.t("closeDialog");
                                            throw null;
                                        }
                                    }
                                    p pVar3 = this.this$0;
                                    int i3 = R$id.img_loading3;
                                    ((ImageView) pVar3.findViewById(i3)).clearAnimation();
                                    ((ImageView) this.this$0.findViewById(i3)).setImageResource(R$drawable.img_analyse_success);
                                    p.a(this.this$0);
                                    ((LinearLayout) this.this$0.findViewById(R$id.layout_test_result)).setVisibility(8);
                                    ((LinearLayout) this.this$0.findViewById(R$id.layout_test_no_problem)).setVisibility(0);
                                    break;
                                } else {
                                    k.t("closeDialog");
                                    throw null;
                                }
                            case 4:
                                p.a(this.this$0);
                                ((ImageView) this.this$0.findViewById(R$id.img_analyse_result)).setImageResource(R$drawable.bg_analyse_result_app_wifi);
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_title)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_app_network_unconnected_title));
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_content)).c(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_app_network_unconnected_content), 8, 2);
                                break;
                            case 5:
                            case 6:
                            case 7:
                                p.a(this.this$0);
                                ((ImageView) this.this$0.findViewById(R$id.img_analyse_result)).setImageResource(R$drawable.bg_analyse_result_app_wifi_cloud);
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_title)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_app_network_unavailable_title));
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_content)).c(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_app_network_unavailable_content), 8, 2);
                                break;
                            case 8:
                                p.a(this.this$0);
                                ((ImageView) this.this$0.findViewById(R$id.img_analyse_result)).setImageResource(R$drawable.bg_analyse_result_device_wifi);
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_title)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_device_network_unconnected_title));
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_content)).c(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_device_network_unconnected_content), 8, 2);
                                break;
                            case 9:
                                p.a(this.this$0);
                                ((ImageView) this.this$0.findViewById(R$id.img_analyse_result)).setImageResource(R$drawable.bg_analyse_result_device_wifi_weak);
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_title)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_device_network_unavailable_title));
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_content)).c(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_device_network_unavailable_content), 8, 2);
                                break;
                            case 10:
                            case 11:
                                p.a(this.this$0);
                                ((ImageView) this.this$0.findViewById(R$id.img_analyse_result)).setImageResource(R$drawable.bg_analyse_result_device_wifi_cloud);
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_title)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_device_network_unavailable_title));
                                ((LDSTextView) this.this$0.findViewById(R$id.tv_analyse_content)).c(PubUtils.getString(BaseApplication.b(), R$string.lds_live_analyse_device_network_unavailable_content), 8, 2);
                                break;
                        }
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 5619, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    kotlinx.coroutines.flow.c<com.leedarson.newui.analyse.a> f = e.f(e.o(this.this$0.q.t(), new a(this.this$0, (d<? super a>) null)), new b(this.this$0, (d<? super b>) null));
                    this.label = 1;
                    if (e.h(f, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }

        @f(c = "com.leedarson.newui.widgets.LDSLiveAnalyseDialog$startTest$1$2", f = "LDSLiveAnalyseDialog.kt", l = {}, m = "invokeSuspend")
        /* compiled from: LDSLiveAnalyseDialog.kt */
        public static final class b extends l implements q<kotlinx.coroutines.flow.d<? super com.leedarson.newui.analyse.a>, Throwable, d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ p this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(p pVar, d<? super b> dVar) {
                super(3, dVar);
                this.this$0 = pVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2, obj3}, this, changeQuickRedirect, false, 5629, new Class[]{cls, cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((kotlinx.coroutines.flow.d<? super com.leedarson.newui.analyse.a>) (kotlinx.coroutines.flow.d) obj, (Throwable) obj2, (d<? super x>) (d) obj3);
            }

            @Nullable
            public final Object invoke(@NotNull kotlinx.coroutines.flow.d<? super com.leedarson.newui.analyse.a> dVar, @NotNull Throwable th, @Nullable d<? super x> dVar2) {
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dVar, th, dVar2}, this, changeQuickRedirect2, false, 5628, new Class[]{kotlinx.coroutines.flow.d.class, Throwable.class, d.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                b bVar = new b(this.this$0, dVar2);
                bVar.L$0 = th;
                return bVar.invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5627, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b(obj);
                        this.this$0.g(k.l("error", ((Throwable) this.L$0).getMessage()));
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    public final void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5605, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(this, (g) null, (q0) null, new c(this, (d<? super c>) null), 3, (Object) null);
            this.q.C();
        }
    }

    private final void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5606, new Class[0], Void.TYPE).isSupported) {
            this.y = true;
            ((LinearLayout) findViewById(R$id.layout_test)).setVisibility(8);
            ((LinearLayout) findViewById(R$id.layout_test_result)).setVisibility(0);
            Dialog dialog = this.z;
            if (dialog == null) {
                k.t("closeDialog");
                throw null;
            } else if (dialog.isShowing()) {
                Dialog dialog2 = this.z;
                if (dialog2 != null) {
                    dialog2.dismiss();
                } else {
                    k.t("closeDialog");
                    throw null;
                }
            }
        }
    }

    private final void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5607, new Class[0], Void.TYPE).isSupported) {
            if (this.y) {
                e();
                dismiss();
                return;
            }
            Dialog dialog = this.z;
            if (dialog != null) {
                dialog.show();
            } else {
                k.t("closeDialog");
                throw null;
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5608, new Class[0], Void.TYPE).isSupported) {
            v();
        }
    }

    private final void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5609, new Class[0], Void.TYPE).isSupported) {
            g("cancelCoroutineScope");
            this.q.p();
            p0.c(this, (CancellationException) null);
        }
    }

    public final void g(@NotNull String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5610, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(msg, NotificationCompat.CATEGORY_MESSAGE);
            timber.log.a.g("LDSLiveAnalyseDialog").a(msg, new Object[0]);
        }
    }

    /* compiled from: LDSLiveAnalyseDialog.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
