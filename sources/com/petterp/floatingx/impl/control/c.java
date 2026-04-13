package com.petterp.floatingx.impl.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.petterp.floatingx.view.FxManagerView;
import com.petterp.floatingx.view.FxViewHolder;
import java.lang.ref.WeakReference;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxBasisControlImpl.kt */
public class c implements com.petterp.floatingx.listener.control.c, com.petterp.floatingx.listener.control.b {
    @NotNull
    private final com.petterp.floatingx.assist.helper.b c;
    @Nullable
    private FxManagerView d;
    @Nullable
    private FxViewHolder f;
    @Nullable
    private WeakReference<ViewGroup> q;
    @NotNull
    private final g x;
    @NotNull
    private final g y;

    public c(@NotNull com.petterp.floatingx.assist.helper.b bVar) {
        k.e(bVar, "helper");
        this.c = bVar;
        kotlin.k kVar = kotlin.k.NONE;
        this.x = i.a(kVar, new C0200c(this));
        this.y = i.a(kVar, new d(this));
    }

    /* compiled from: FxBasisControlImpl.kt */
    public static final class a implements Runnable {
        final /* synthetic */ c c;

        a(c cVar) {
            this.c = cVar;
        }

        public final void run() {
            this.c.u();
        }
    }

    /* compiled from: FxBasisControlImpl.kt */
    public static final class b implements Runnable {
        final /* synthetic */ c c;

        b(c cVar) {
            this.c = cVar;
        }

        public final void run() {
            this.c.l();
        }
    }

    private final Runnable n() {
        return (Runnable) this.x.getValue();
    }

    private final Runnable p() {
        return (Runnable) this.y.getValue();
    }

    @NotNull
    public com.petterp.floatingx.listener.control.b a() {
        return this;
    }

    public void i() {
        if (this.d != null || this.f != null) {
            if (f()) {
                com.petterp.floatingx.assist.helper.b bVar = this.c;
                if (bVar.p && bVar.f != null) {
                    FxManagerView fxManagerView = this.d;
                    if (fxManagerView != null) {
                        fxManagerView.removeCallbacks(n());
                    }
                    com.petterp.floatingx.assist.a aVar = this.c.f;
                    k.c(aVar);
                    h(aVar.h(this.d), n());
                    return;
                }
            }
            u();
        }
    }

    /* renamed from: com.petterp.floatingx.impl.control.c$c  reason: collision with other inner class name */
    /* compiled from: FxExt.kt */
    public static final class C0200c extends l implements kotlin.jvm.functions.a<Runnable> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0200c(c cVar) {
            super(0);
            this.this$0 = cVar;
        }

        @NotNull
        public final Runnable invoke() {
            return new a(this.this$0);
        }
    }

    /* compiled from: FxExt.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<Runnable> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(c cVar) {
            super(0);
            this.this$0 = cVar;
        }

        @NotNull
        public final Runnable invoke() {
            return new b(this.this$0);
        }
    }

    public void hide() {
        com.petterp.floatingx.assist.a aVar;
        if (f()) {
            y(false);
            com.petterp.floatingx.assist.helper.b bVar = this.c;
            if (!bVar.p || (aVar = bVar.f) == null) {
                l();
                return;
            }
            k.c(aVar);
            if (aVar.b()) {
                com.petterp.floatingx.util.c cVar = this.c.y;
                if (cVar != null) {
                    cVar.b("fxView->Animation ,endAnimation Executing, cancel this operation!");
                    return;
                }
                return;
            }
            com.petterp.floatingx.util.c cVar2 = this.c.y;
            if (cVar2 != null) {
                cVar2.b("fxView->Animation ,endAnimation Running");
            }
            FxManagerView fxManagerView = this.d;
            if (fxManagerView != null) {
                fxManagerView.removeCallbacks(p());
            }
            com.petterp.floatingx.assist.a aVar2 = this.c.f;
            k.c(aVar2);
            h(aVar2.h(this.d), p());
        }
    }

    public boolean f() {
        FxManagerView fxManagerView = this.d;
        if (fxManagerView != null) {
            k.c(fxManagerView);
            if (ViewCompat.isAttachedToWindow(fxManagerView)) {
                FxManagerView fxManagerView2 = this.d;
                k.c(fxManagerView2);
                if (fxManagerView2.getVisibility() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    public FxManagerView c() {
        return this.d;
    }

    public void v(long time, @NotNull View.OnClickListener clickListener) {
        k.e(clickListener, "clickListener");
        com.petterp.floatingx.assist.helper.b bVar = this.c;
        bVar.d = time;
        bVar.s = true;
        bVar.x = clickListener;
    }

    public void setClickListener(@NotNull View.OnClickListener clickListener) {
        k.e(clickListener, "clickListener");
        v(0, clickListener);
    }

    public void e(float x2, float y2) {
        t(x2, y2, true);
    }

    public void t(float x2, float y2, boolean useAnimation) {
        FxManagerView fxManagerView = this.d;
        if (fxManagerView != null) {
            fxManagerView.moveLocationByVector$floatingx_release(x2, y2, useAnimation);
        }
    }

    public void b(float t, float l, float b2, float r) {
        com.petterp.floatingx.assist.b $this$setBorderMargin_u24lambda_u2d2 = this.c.j;
        $this$setBorderMargin_u24lambda_u2d2.h(t);
        $this$setBorderMargin_u24lambda_u2d2.f(l);
        $this$setBorderMargin_u24lambda_u2d2.e(b2);
        $this$setBorderMargin_u24lambda_u2d2.g(r);
        FxManagerView fxManagerView = this.d;
        if (fxManagerView != null) {
            fxManagerView.moveToEdge$floatingx_release();
        }
    }

    public void g(@NotNull com.petterp.floatingx.assist.c mode) {
        k.e(mode, "mode");
        this.c.k = mode;
        FxManagerView fxManagerView = this.d;
        if (fxManagerView != null) {
            fxManagerView.updateDisplayMode$floatingx_release();
        }
    }

    /* access modifiers changed from: protected */
    public final void s() {
        com.petterp.floatingx.assist.helper.b bVar = this.c;
        if (bVar.a == 0 && bVar.b == null) {
            throw new RuntimeException("The layout id cannot be 0 ,and layoutView==null");
        }
        ViewGroup o = o();
        if (o != null) {
            o.removeView(this.d);
        }
        r();
    }

    /* access modifiers changed from: protected */
    public void r() {
        Context context = k();
        if (context != null) {
            View view = null;
            FxManagerView init$floatingx_release = new FxManagerView(context, (AttributeSet) null, 2, (DefaultConstructorMarker) null).init$floatingx_release(this.c);
            this.d = init$floatingx_release;
            if (init$floatingx_release != null) {
                view = init$floatingx_release.getChildFxView();
            }
            if (view != null) {
                View fxContentView = view;
                this.f = new FxViewHolder(fxContentView);
                com.petterp.floatingx.listener.d fxViewLifecycle = this.c.v;
                if (fxViewLifecycle != null) {
                    fxViewLifecycle.d(fxContentView);
                    FxViewHolder fxViewHolder = this.f;
                    k.c(fxViewHolder);
                    fxViewLifecycle.b(fxViewHolder);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final FxManagerView q() {
        if (this.d == null) {
            s();
        }
        return this.d;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final ViewGroup o() {
        WeakReference<ViewGroup> weakReference = this.q;
        if (weakReference == null) {
            return null;
        }
        return (ViewGroup) weakReference.get();
    }

    /* access modifiers changed from: protected */
    public void m(@Nullable ViewGroup container) {
        if (this.d != null && container != null) {
            com.petterp.floatingx.util.c cVar = this.c.y;
            if (cVar != null) {
                cVar.b("fxView-lifecycle-> code->removeView");
            }
            com.petterp.floatingx.listener.d dVar = this.c.v;
            if (dVar != null) {
                dVar.e();
            }
            container.removeView(this.d);
        }
    }

    /* access modifiers changed from: protected */
    public final void l() {
        ViewGroup containerGroup = o();
        if (containerGroup != null) {
            m(containerGroup);
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Context k() {
        com.petterp.floatingx.util.c cVar;
        ViewGroup viewGroup;
        WeakReference<ViewGroup> weakReference = this.q;
        Context context = null;
        if (!(weakReference == null || (viewGroup = (ViewGroup) weakReference.get()) == null)) {
            context = viewGroup.getContext();
        }
        Context context2 = context;
        if (context2 == null && (cVar = this.c.y) != null) {
            cVar.c("context = null,check your rule!");
        }
        return context2;
    }

    /* access modifiers changed from: protected */
    public final void j() {
        WeakReference<ViewGroup> weakReference = this.q;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.q = null;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void x(FxManagerView $this$show) {
        k.e($this$show, "<this>");
        this.c.m = true;
        $this$show.setVisibility(0);
        com.petterp.floatingx.assist.helper.b bVar = this.c;
        com.petterp.floatingx.assist.a fxAnimation = bVar.f;
        if (fxAnimation == null || !bVar.p) {
            return;
        }
        if (fxAnimation.d()) {
            com.petterp.floatingx.util.c cVar = this.c.y;
            if (cVar != null) {
                cVar.b("fxView->Animation ,startAnimation Executing, cancel this operation!");
                return;
            }
            return;
        }
        com.petterp.floatingx.util.c cVar2 = this.c.y;
        if (cVar2 != null) {
            cVar2.b("fxView->Animation ,startAnimation Executing, cancel this operation.");
        }
        fxAnimation.e($this$show);
    }

    /* access modifiers changed from: protected */
    public /* synthetic */ void u() {
        FxManagerView fxManagerView = this.d;
        if (fxManagerView != null) {
            fxManagerView.removeCallbacks(p());
        }
        FxManagerView fxManagerView2 = this.d;
        if (fxManagerView2 != null) {
            fxManagerView2.removeCallbacks(n());
        }
        WeakReference<ViewGroup> weakReference = this.q;
        m(weakReference == null ? null : (ViewGroup) weakReference.get());
        this.d = null;
        this.f = null;
        this.c.a();
        j();
        com.petterp.floatingx.util.c cVar = this.c.y;
        if (cVar != null) {
            cVar.b("fxView-lifecycle-> code->cancelFx");
        }
    }

    /* access modifiers changed from: protected */
    public final void y(boolean newStatus) {
        com.petterp.floatingx.assist.helper.b bVar = this.c;
        if (bVar.m != newStatus) {
            bVar.m = newStatus;
        }
    }

    public final void w(@NotNull ViewGroup viewGroup) {
        k.e(viewGroup, "viewGroup");
        this.q = new WeakReference<>(viewGroup);
    }

    private final void h(long j, Runnable runnable) {
        FxManagerView magnetView = this.d;
        if (magnetView != null) {
            magnetView.removeCallbacks(runnable);
            magnetView.postDelayed(runnable, j);
        }
    }
}
