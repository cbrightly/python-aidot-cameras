package com.leedarson.newui.multiview;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.IpcDeviceComparableBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.flow.p;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.t;
import kotlinx.coroutines.flow.v;
import kotlinx.coroutines.flow.x;
import kotlinx.coroutines.flow.z;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MultiViewViewModel.kt */
public final class MultiViewViewModel extends ViewModel {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private List<IpcDeviceComparableBean> a = new ArrayList();
    @Nullable
    private IpcDeviceBean b;
    /* access modifiers changed from: private */
    @NotNull
    public final q<Integer> c;
    @NotNull
    private final x<Integer> d;
    /* access modifiers changed from: private */
    @NotNull
    public final p<Boolean> e;
    @NotNull
    private final t<Boolean> f;

    public MultiViewViewModel() {
        q<Integer> a2 = z.a(1);
        this.c = a2;
        this.d = e.b(a2);
        p<Boolean> b2 = v.b(0, 0, (h) null, 7, (Object) null);
        this.e = b2;
        this.f = e.a(b2);
    }

    @NotNull
    public final List<IpcDeviceComparableBean> g() {
        return this.a;
    }

    public final void i(@NotNull List<IpcDeviceComparableBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 4251, new Class[]{List.class}, Void.TYPE).isSupported) {
            k.e(list, "<set-?>");
            this.a = list;
        }
    }

    @Nullable
    public final IpcDeviceBean e() {
        return this.b;
    }

    public final void h(@Nullable IpcDeviceBean ipcDeviceBean) {
        this.b = ipcDeviceBean;
    }

    @NotNull
    public final x<Integer> d() {
        return this.d;
    }

    @NotNull
    public final t<Boolean> f() {
        return this.f;
    }

    @f(c = "com.leedarson.newui.multiview.MultiViewViewModel$switchPage$1", f = "MultiViewViewModel.kt", l = {30}, m = "invokeSuspend")
    /* compiled from: MultiViewViewModel.kt */
    public static final class b extends l implements kotlin.jvm.functions.p<o0, d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int $page;
        int label;
        final /* synthetic */ MultiViewViewModel this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(MultiViewViewModel multiViewViewModel, int i, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = multiViewViewModel;
            this.$page = i;
        }

        @NotNull
        public final d<kotlin.x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 4259, new Class[]{Object.class, d.class}, d.class);
            if (proxy.isSupported) {
                return (d) proxy.result;
            }
            return new b(this.this$0, this.$page, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4261, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super kotlin.x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4260, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((b) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4258, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    q a = this.this$0.c;
                    Integer c = kotlin.coroutines.jvm.internal.b.c(this.$page);
                    this.label = 1;
                    if (a.emit(c, this) != d) {
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
            return kotlin.x.a;
        }
    }

    public final void j(int page) {
        Object[] objArr = {new Integer(page)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4252, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            z1 unused = j.d(ViewModelKt.getViewModelScope(this), (g) null, (q0) null, new b(this, page, (d<? super b>) null), 3, (Object) null);
        }
    }

    @f(c = "com.leedarson.newui.multiview.MultiViewViewModel$finishPage$1", f = "MultiViewViewModel.kt", l = {36}, m = "invokeSuspend")
    /* compiled from: MultiViewViewModel.kt */
    public static final class a extends l implements kotlin.jvm.functions.p<o0, d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ MultiViewViewModel this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(MultiViewViewModel multiViewViewModel, d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = multiViewViewModel;
        }

        @NotNull
        public final d<kotlin.x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4255, new Class[]{Object.class, d.class}, d.class);
            return proxy.isSupported ? (d) proxy.result : new a(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4257, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super kotlin.x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4256, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4254, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    p b = this.this$0.e;
                    Boolean a = kotlin.coroutines.jvm.internal.b.a(true);
                    this.label = 1;
                    if (b.emit(a, this) != d) {
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
            return kotlin.x.a;
        }
    }

    public final void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4253, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(ViewModelKt.getViewModelScope(this), (g) null, (q0) null, new a(this, (d<? super a>) null), 3, (Object) null);
        }
    }
}
