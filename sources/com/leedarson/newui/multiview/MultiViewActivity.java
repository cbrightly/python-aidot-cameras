package com.leedarson.newui.multiview;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.IpcDeviceComparableBean;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import kotlin.collections.u;
import kotlin.coroutines.d;
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
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MultiViewActivity.kt */
public final class MultiViewActivity extends BaseFragmentActivity {
    @NotNull
    public static final a a2 = new a((DefaultConstructorMarker) null);
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final g p2 = i.b(new c(this));
    @Nullable
    private MultiViewFragment p3;
    @Nullable
    private MultiViewEditFragment p4;

    public static final /* synthetic */ void b0(MultiViewActivity $this, int page) {
        if (!PatchProxy.proxy(new Object[]{$this, new Integer(page)}, (Object) null, changeQuickRedirect, true, 4171, new Class[]{MultiViewActivity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            $this.e0(page);
        }
    }

    public static final /* synthetic */ MultiViewViewModel c0(MultiViewActivity $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 4170, new Class[]{MultiViewActivity.class}, MultiViewViewModel.class);
        return proxy.isSupported ? (MultiViewViewModel) proxy.result : $this.f0();
    }

    /* compiled from: MultiViewActivity.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<MultiViewViewModel> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(MultiViewActivity multiViewActivity) {
            super(0);
            this.this$0 = multiViewActivity;
        }

        @NotNull
        public final MultiViewViewModel invoke() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4184, new Class[0], MultiViewViewModel.class);
            return proxy.isSupported ? (MultiViewViewModel) proxy.result : (MultiViewViewModel) new ViewModelProvider(this.this$0).get(MultiViewViewModel.class);
        }
    }

    private final MultiViewViewModel f0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4162, new Class[0], MultiViewViewModel.class);
        return proxy.isSupported ? (MultiViewViewModel) proxy.result : (MultiViewViewModel) this.p2.getValue();
    }

    public int S() {
        return R$layout.activity_multi_view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 4163, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
        }
    }

    public void T() {
        int i = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4164, new Class[0], Void.TYPE).isSupported) {
            ArrayList $this$initBundleData_u24lambda_u2d0 = getIntent().getParcelableArrayListExtra("devices");
            if ($this$initBundleData_u24lambda_u2d0 != null) {
                int indexPref = 0;
                int size = $this$initBundleData_u24lambda_u2d0.size() - 1;
                if (size >= 0) {
                    do {
                        int index = i;
                        i++;
                        f0().g().add(new IpcDeviceComparableBean((IpcDeviceBean) $this$initBundleData_u24lambda_u2d0.get(index), SharePreferenceUtils.getPrefInt(BaseApplication.b(), k.l(SharePreferenceUtils.KEY_MULTI_VIEW_DEVICE_INDEX, ((IpcDeviceBean) $this$initBundleData_u24lambda_u2d0.get(index)).id), indexPref)));
                        indexPref++;
                    } while (i <= size);
                }
                u.v(f0().g());
            }
            IpcDeviceBean $this$initBundleData_u24lambda_u2d1 = (IpcDeviceBean) getIntent().getParcelableExtra("current_device");
            if ($this$initBundleData_u24lambda_u2d1 != null) {
                f0().h($this$initBundleData_u24lambda_u2d1);
            }
        }
    }

    @f(c = "com.leedarson.newui.multiview.MultiViewActivity$init$1", f = "MultiViewActivity.kt", l = {55}, m = "invokeSuspend")
    /* compiled from: MultiViewActivity.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ MultiViewActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(MultiViewActivity multiViewActivity, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = multiViewActivity;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4173, new Class[]{Object.class, d.class}, d.class);
            return proxy.isSupported ? (d) proxy.result : new b(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4175, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4174, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @f(c = "com.leedarson.newui.multiview.MultiViewActivity$init$1$1", f = "MultiViewActivity.kt", l = {56}, m = "invokeSuspend")
        /* compiled from: MultiViewActivity.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ MultiViewActivity this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(MultiViewActivity multiViewActivity, d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = multiViewActivity;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4177, new Class[]{Object.class, d.class}, d.class);
                return proxy.isSupported ? (d) proxy.result : new a(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4179, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super x>) (d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4178, new Class[]{o0.class, d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @f(c = "com.leedarson.newui.multiview.MultiViewActivity$init$1$1$1", f = "MultiViewActivity.kt", l = {}, m = "invokeSuspend")
            /* renamed from: com.leedarson.newui.multiview.MultiViewActivity$b$a$a  reason: collision with other inner class name */
            /* compiled from: MultiViewActivity.kt */
            public static final class C0114a extends kotlin.coroutines.jvm.internal.l implements p<Integer, d<? super x>, Object> {
                public static ChangeQuickRedirect changeQuickRedirect;
                /* synthetic */ int I$0;
                int label;
                final /* synthetic */ MultiViewActivity this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0114a(MultiViewActivity multiViewActivity, d<? super C0114a> dVar) {
                    super(2, dVar);
                    this.this$0 = multiViewActivity;
                }

                @NotNull
                public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 4181, new Class[]{Object.class, d.class}, d.class);
                    if (proxy.isSupported) {
                        return (d) proxy.result;
                    }
                    C0114a aVar = new C0114a(this.this$0, dVar);
                    aVar.I$0 = ((Number) obj).intValue();
                    return aVar;
                }

                @Nullable
                public final Object invoke(int i, @Nullable d<? super x> dVar) {
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i), dVar}, this, changeQuickRedirect, false, 4182, new Class[]{Integer.TYPE, d.class}, Object.class);
                    return proxy.isSupported ? proxy.result : ((C0114a) create(Integer.valueOf(i), dVar)).invokeSuspend(x.a);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    Class<Object> cls = Object.class;
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4183, new Class[]{cls, cls}, Object.class);
                    return proxy.isSupported ? proxy.result : invoke(((Number) obj).intValue(), (d<? super x>) (d) obj2);
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4180, new Class[]{Object.class}, Object.class);
                    if (proxy.isSupported) {
                        return proxy.result;
                    }
                    kotlin.coroutines.intrinsics.c.d();
                    switch (this.label) {
                        case 0:
                            kotlin.p.b(obj);
                            MultiViewActivity.b0(this.this$0, this.I$0);
                            return x.a;
                        default:
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4176, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        kotlinx.coroutines.flow.x<Integer> d2 = MultiViewActivity.c0(this.this$0).d();
                        C0114a aVar = new C0114a(this.this$0, (d<? super C0114a>) null);
                        this.label = 1;
                        if (e.i(d2, aVar, this) != d) {
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
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4172, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    MultiViewActivity multiViewActivity = this.this$0;
                    Lifecycle.State state = Lifecycle.State.CREATED;
                    a aVar = new a(multiViewActivity, (d<? super a>) null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle((LifecycleOwner) multiViewActivity, state, (p<? super o0, ? super d<? super x>, ? extends Object>) aVar, (d<? super x>) this) != d) {
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

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4165, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(LifecycleOwnerKt.getLifecycleScope(this), (kotlin.coroutines.g) null, (q0) null, new b(this, (d<? super b>) null), 3, (Object) null);
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4166, new Class[0], Void.TYPE).isSupported) {
            if (f0().d().getValue().intValue() == 2) {
                f0().j(1);
            } else {
                f0().c();
            }
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4167, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    private final void g0(FragmentTransaction transaction) {
        if (!PatchProxy.proxy(new Object[]{transaction}, this, changeQuickRedirect, false, 4168, new Class[]{FragmentTransaction.class}, Void.TYPE).isSupported) {
            MultiViewFragment it = this.p3;
            if (it != null) {
                transaction.hide(it);
            }
            MultiViewEditFragment it2 = this.p4;
            if (it2 != null) {
                transaction.hide(it2);
            }
        }
    }

    private final void e0(int page) {
        Object[] objArr = {new Integer(page)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4169, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (page) {
                case 1:
                    int i = R$id.content;
                    ((FrameLayout) findViewById(i)).setBackgroundColor(-1);
                    FragmentTransaction $this$commitFragment_u24lambda_u2d4 = getSupportFragmentManager().beginTransaction();
                    k.d($this$commitFragment_u24lambda_u2d4, "this");
                    g0($this$commitFragment_u24lambda_u2d4);
                    MultiViewFragment multiViewFragment = this.p3;
                    if (multiViewFragment == null) {
                        MultiViewFragment a3 = MultiViewFragment.a1.a();
                        this.p3 = a3;
                        k.c(a3);
                        $this$commitFragment_u24lambda_u2d4.add(i, (Fragment) a3);
                    } else if (multiViewFragment != null) {
                        multiViewFragment.S1();
                    }
                    $this$commitFragment_u24lambda_u2d4.setCustomAnimations(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
                    MultiViewFragment multiViewFragment2 = this.p3;
                    k.c(multiViewFragment2);
                    $this$commitFragment_u24lambda_u2d4.show(multiViewFragment2);
                    $this$commitFragment_u24lambda_u2d4.commitAllowingStateLoss();
                    return;
                case 2:
                    int i2 = R$id.content;
                    ((FrameLayout) findViewById(i2)).setBackgroundColor(getResources().getColor(R$color.bg_primary_color));
                    FragmentTransaction $this$commitFragment_u24lambda_u2d5 = getSupportFragmentManager().beginTransaction();
                    k.d($this$commitFragment_u24lambda_u2d5, "this");
                    g0($this$commitFragment_u24lambda_u2d5);
                    if (this.p4 == null) {
                        MultiViewEditFragment a4 = MultiViewEditFragment.a1.a();
                        this.p4 = a4;
                        k.c(a4);
                        $this$commitFragment_u24lambda_u2d5.add(i2, (Fragment) a4);
                    }
                    $this$commitFragment_u24lambda_u2d5.setCustomAnimations(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                    MultiViewEditFragment multiViewEditFragment = this.p4;
                    k.c(multiViewEditFragment);
                    $this$commitFragment_u24lambda_u2d5.show(multiViewEditFragment);
                    $this$commitFragment_u24lambda_u2d5.commitAllowingStateLoss();
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: MultiViewActivity.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
