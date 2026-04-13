package com.leedarson.newui.signal_test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.newui.multiview.utils.BeanUtilsKt;
import com.leedarson.newui.view.LiveCameraView;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.coroutines.jvm.internal.f;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SignalTestActivity.kt */
public final class SignalTestActivity extends BaseActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    public static final a p0 = new a((DefaultConstructorMarker) null);
    @NotNull
    private final g a1 = i.b(new d(this));

    public static final /* synthetic */ SignalTestViewModel X(SignalTestActivity $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 4686, new Class[]{SignalTestActivity.class}, SignalTestViewModel.class);
        return proxy.isSupported ? (SignalTestViewModel) proxy.result : $this.Z();
    }

    /* compiled from: SignalTestActivity.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<SignalTestViewModel> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ SignalTestActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(SignalTestActivity signalTestActivity) {
            super(0);
            this.this$0 = signalTestActivity;
        }

        @NotNull
        public final SignalTestViewModel invoke() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4703, new Class[0], SignalTestViewModel.class);
            return proxy.isSupported ? (SignalTestViewModel) proxy.result : (SignalTestViewModel) new ViewModelProvider(this.this$0).get(SignalTestViewModel.class);
        }
    }

    private final SignalTestViewModel Z() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4674, new Class[0], SignalTestViewModel.class);
        return proxy.isSupported ? (SignalTestViewModel) proxy.result : (SignalTestViewModel) this.a1.getValue();
    }

    public int O() {
        return R$layout.activity_signal_test;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 4675, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
        }
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4676, new Class[0], Void.TYPE).isSupported) {
            Z().m((IpcDeviceBean) getIntent().getParcelableExtra("current_device"));
        }
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4677, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean it = Z().h();
            if (it != null) {
                LiveCameraView $this$init_u24lambda_u2d1_u24lambda_u2d0 = (LiveCameraView) findViewById(R$id.cameraview);
                $this$init_u24lambda_u2d1_u24lambda_u2d0.m0(it.getAspectRatio(), 1.7777778f);
                int itemType = BeanUtilsKt.getItemType(it);
                String str = it.id;
                String str2 = it.p2pId;
                String str3 = it.account;
                String str4 = it.password;
                String str5 = it.props.isDTLS;
                Boolean bool = it.share;
                k.d(bool, "it.share");
                $this$init_u24lambda_u2d1_u24lambda_u2d0.T(itemType, str, str2, str3, str4, str5, bool.booleanValue(), it.modelId);
                if (it.isCriticalBattery()) {
                    $this$init_u24lambda_u2d1_u24lambda_u2d0.K();
                } else {
                    k.d($this$init_u24lambda_u2d1_u24lambda_u2d0, "this");
                    Y($this$init_u24lambda_u2d1_u24lambda_u2d0, it.props.TurnOnOff);
                }
                $this$init_u24lambda_u2d1_u24lambda_u2d0.setRegularTitleVisibility(0);
            }
            ((LDSTextView) findViewById(R$id.tv_skip)).setOnClickListener(new a(this));
            ((ImageView) findViewById(R$id.iv_back)).setOnClickListener(new b(this));
            ((LDSTextView) findViewById(R$id.tv_next)).setOnClickListener(new c(this));
            e0();
            Z().i();
        }
    }

    @f(c = "com.leedarson.newui.signal_test.SignalTestActivity$init$2$1", f = "SignalTestActivity.kt", l = {71}, m = "invokeSuspend")
    /* compiled from: SignalTestActivity.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ SignalTestActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(SignalTestActivity signalTestActivity, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = signalTestActivity;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4688, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new b(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4690, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4689, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            b bVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4687, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    this.label = 1;
                    if (z0.a(200, this) != d) {
                        bVar = this;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    bVar = this;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            bVar.this$0.finish();
            return x.a;
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void a0(SignalTestActivity this$0, View view) {
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4683, new Class[]{SignalTestActivity.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        IpcDeviceBean h = this$0.Z().h();
        com.leedarson.utils.k.b(h == null ? null : h.id);
        z1 unused = j.d(LifecycleOwnerKt.getLifecycleScope(this$0), (kotlin.coroutines.g) null, (q0) null, new b(this$0, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void b0(SignalTestActivity this$0, View view) {
        Class[] clsArr = {SignalTestActivity.class, View.class};
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4684, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.finish();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void c0(SignalTestActivity this$0, View view) {
        if (PatchProxy.proxy(new Object[]{this$0, view}, (Object) null, changeQuickRedirect, true, 4685, new Class[]{SignalTestActivity.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e(this$0, "this$0");
        IpcDeviceBean h = this$0.Z().h();
        String str = null;
        String str2 = h == null ? null : h.id;
        IpcDeviceBean h2 = this$0.Z().h();
        if (h2 != null) {
            str = h2.productId;
        }
        com.leedarson.utils.k.f(str2, str);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4678, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            ((LiveCameraView) findViewById(R$id.cameraview)).t0();
        }
    }

    private final void Y(LiveCameraView cameraView, boolean turnOnOff) {
        if (!PatchProxy.proxy(new Object[]{cameraView, new Byte(turnOnOff ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4679, new Class[]{LiveCameraView.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (turnOnOff) {
                cameraView.L();
                return;
            }
            cameraView.v0();
            cameraView.M();
        }
    }

    @f(c = "com.leedarson.newui.signal_test.SignalTestActivity$initObserver$1", f = "SignalTestActivity.kt", l = {101}, m = "invokeSuspend")
    /* compiled from: SignalTestActivity.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ SignalTestActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(SignalTestActivity signalTestActivity, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = signalTestActivity;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4692, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new c(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4694, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4693, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @f(c = "com.leedarson.newui.signal_test.SignalTestActivity$initObserver$1$1", f = "SignalTestActivity.kt", l = {102}, m = "invokeSuspend")
        /* compiled from: SignalTestActivity.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ SignalTestActivity this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(SignalTestActivity signalTestActivity, kotlin.coroutines.d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = signalTestActivity;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4696, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new a(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4698, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4697, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @f(c = "com.leedarson.newui.signal_test.SignalTestActivity$initObserver$1$1$1", f = "SignalTestActivity.kt", l = {}, m = "invokeSuspend")
            /* renamed from: com.leedarson.newui.signal_test.SignalTestActivity$c$a$a  reason: collision with other inner class name */
            /* compiled from: SignalTestActivity.kt */
            public static final class C0118a extends kotlin.coroutines.jvm.internal.l implements p<d, kotlin.coroutines.d<? super x>, Object> {
                public static ChangeQuickRedirect changeQuickRedirect;
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ SignalTestActivity this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0118a(SignalTestActivity signalTestActivity, kotlin.coroutines.d<? super C0118a> dVar) {
                    super(2, dVar);
                    this.this$0 = signalTestActivity;
                }

                @NotNull
                public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 4700, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                    if (proxy.isSupported) {
                        return (kotlin.coroutines.d) proxy.result;
                    }
                    C0118a aVar = new C0118a(this.this$0, dVar);
                    aVar.L$0 = obj;
                    return aVar;
                }

                @Nullable
                public final Object invoke(@NotNull d dVar, @Nullable kotlin.coroutines.d<? super x> dVar2) {
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dVar, dVar2}, this, changeQuickRedirect, false, 4701, new Class[]{d.class, kotlin.coroutines.d.class}, Object.class);
                    return proxy.isSupported ? proxy.result : ((C0118a) create(dVar, dVar2)).invokeSuspend(x.a);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    Class<Object> cls = Object.class;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4702, new Class[]{cls, cls}, Object.class);
                    return proxy.isSupported ? proxy.result : invoke((d) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4699, new Class[]{Object.class}, Object.class);
                    if (proxy.isSupported) {
                        return proxy.result;
                    }
                    kotlin.coroutines.intrinsics.c.d();
                    switch (this.label) {
                        case 0:
                            kotlin.p.b(obj);
                            SignalTestActivity signalTestActivity = this.this$0;
                            d $this$invokeSuspend_u24lambda_u2d0 = (d) this.L$0;
                            if ($this$invokeSuspend_u24lambda_u2d0 == d.None) {
                                com.bumptech.glide.b.u(signalTestActivity).k().K0(kotlin.coroutines.jvm.internal.b.c($this$invokeSuspend_u24lambda_u2d0.getIcon())).H0((ImageView) signalTestActivity.findViewById(R$id.img_wifi_strength));
                            } else {
                                com.bumptech.glide.b.u(signalTestActivity).p(kotlin.coroutines.jvm.internal.b.c($this$invokeSuspend_u24lambda_u2d0.getIcon())).H0((ImageView) signalTestActivity.findViewById(R$id.img_wifi_strength));
                            }
                            if ($this$invokeSuspend_u24lambda_u2d0.getType() != -1) {
                                int i = R$id.tv_wifi_strength;
                                ((LDSTextView) signalTestActivity.findViewById(i)).setTextColor(signalTestActivity.getResources().getColor($this$invokeSuspend_u24lambda_u2d0.getTypeColor()));
                                ((LDSTextView) signalTestActivity.findViewById(i)).setText(signalTestActivity.getString($this$invokeSuspend_u24lambda_u2d0.getType()));
                            }
                            if ($this$invokeSuspend_u24lambda_u2d0.getTips() != -1) {
                                int i2 = R$id.tv_wifi_strength_tips;
                                ((LDSTextView) signalTestActivity.findViewById(i2)).setTextColor(signalTestActivity.getResources().getColor($this$invokeSuspend_u24lambda_u2d0.getTipsColor()));
                                ((LDSTextView) signalTestActivity.findViewById(i2)).setText(signalTestActivity.getString($this$invokeSuspend_u24lambda_u2d0.getTips()));
                            }
                            return x.a;
                        default:
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4695, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        kotlinx.coroutines.flow.x<d> k = SignalTestActivity.X(this.this$0).k();
                        C0118a aVar = new C0118a(this.this$0, (kotlin.coroutines.d<? super C0118a>) null);
                        this.label = 1;
                        if (e.i(k, aVar, this) != d) {
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
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4691, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    SignalTestActivity signalTestActivity = this.this$0;
                    Lifecycle.State state = Lifecycle.State.CREATED;
                    a aVar = new a(signalTestActivity, (kotlin.coroutines.d<? super a>) null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle((LifecycleOwner) signalTestActivity, state, (p<? super o0, ? super kotlin.coroutines.d<? super x>, ? extends Object>) aVar, (kotlin.coroutines.d<? super x>) this) != d) {
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
    }

    private final void e0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4680, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(LifecycleOwnerKt.getLifecycleScope(this), (kotlin.coroutines.g) null, (q0) null, new c(this, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4681, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4682, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            int i = R$id.cameraview;
            ((LiveCameraView) findViewById(i)).v0();
            ((LiveCameraView) findViewById(i)).g0();
        }
    }

    /* compiled from: SignalTestActivity.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
