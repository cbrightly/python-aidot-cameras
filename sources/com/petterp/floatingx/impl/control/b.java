package com.petterp.floatingx.impl.control;

import android.annotation.NonNull;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.petterp.floatingx.listener.control.a;
import com.petterp.floatingx.util.c;
import com.petterp.floatingx.util.d;
import com.petterp.floatingx.view.FxManagerView;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxAppControlImpl.kt */
public final class b extends c implements a, Application.ActivityLifecycleCallbacks {
    @NotNull
    private final OnApplyWindowInsetsListener a1 = new a(this);
    @NotNull
    private final com.petterp.floatingx.impl.lifecycle.b p0;
    @NotNull
    private final com.petterp.floatingx.assist.helper.a z;

    public void onActivityCreated(@NonNull @NotNull Activity activity, @Nullable @android.annotation.Nullable Bundle bundle) {
        k.e(activity, "p0");
        this.p0.onActivityCreated(activity, bundle);
    }

    public void onActivityDestroyed(@NonNull @NotNull Activity activity) {
        k.e(activity, "p0");
        this.p0.onActivityDestroyed(activity);
    }

    public void onActivityPaused(@NonNull @NotNull Activity activity) {
        k.e(activity, "p0");
        this.p0.onActivityPaused(activity);
    }

    public void onActivityResumed(@NonNull @NotNull Activity activity) {
        k.e(activity, "p0");
        this.p0.onActivityResumed(activity);
    }

    public void onActivitySaveInstanceState(@NonNull @NotNull Activity activity, @NonNull @NotNull Bundle bundle) {
        k.e(activity, "p0");
        k.e(bundle, "p1");
        this.p0.onActivitySaveInstanceState(activity, bundle);
    }

    public void onActivityStarted(@NonNull @NotNull Activity activity) {
        k.e(activity, "p0");
        this.p0.onActivityStarted(activity);
    }

    public void onActivityStopped(@NonNull @NotNull Activity activity) {
        k.e(activity, "p0");
        this.p0.onActivityStopped(activity);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull com.petterp.floatingx.assist.helper.a helper, @NotNull com.petterp.floatingx.impl.lifecycle.b proxyLifecycleImpl) {
        super(helper);
        k.e(helper, "helper");
        k.e(proxyLifecycleImpl, "proxyLifecycleImpl");
        this.z = helper;
        this.p0 = proxyLifecycleImpl;
        proxyLifecycleImpl.f(helper, this);
    }

    /* access modifiers changed from: private */
    public static final WindowInsetsCompat E(b this$0, View $noName_0, WindowInsetsCompat insets) {
        k.e(this$0, "this$0");
        int statusBar = insets.getStableInsetTop();
        com.petterp.floatingx.assist.helper.a aVar = this$0.z;
        if (aVar.B != statusBar) {
            c cVar = aVar.y;
            if (cVar != null) {
                cVar.d("System--StatusBar---old-(" + this$0.z.B + "),new-(" + statusBar + "))");
            }
            this$0.z.B = statusBar;
        }
        return insets;
    }

    public void d(@NotNull Activity activity) {
        k.e(activity, "activity");
        if (this.z.f(activity) && !f() && z(activity)) {
            FxManagerView c = c();
            if (c != null) {
                x(c);
            }
            y(true);
            com.petterp.floatingx.a.b(com.petterp.floatingx.a.a, (Activity) null, 1, (Object) null);
        }
    }

    public void B(@NotNull Activity activity) {
        k.e(activity, "activity");
        FrameLayout it = d.a(activity);
        if (it != null) {
            m(it);
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Context k() {
        Application f = com.petterp.floatingx.a.a.f();
        k.c(f);
        return f;
    }

    private final void C() {
        FxManagerView it = c();
        if (it != null) {
            ViewCompat.setOnApplyWindowInsetsListener(it, this.a1);
            it.requestApplyInsets();
        }
    }

    public final boolean z(@NotNull Activity activity) {
        x xVar;
        c cVar;
        FxManagerView c;
        k.e(activity, "activity");
        FrameLayout it = d.a(activity);
        if (it == null) {
            xVar = null;
        } else if (o() == it) {
            return false;
        } else {
            boolean isAnimation = false;
            if (c() == null) {
                this.z.h(activity);
                this.z.i(activity);
                s();
                isAnimation = true;
            } else {
                FxManagerView c2 = c();
                if (!(c2 != null && c2.getVisibility() == 0) && (c = c()) != null) {
                    c.setVisibility(0);
                }
                l();
            }
            w(it);
            c cVar2 = this.z.y;
            if (cVar2 != null) {
                cVar2.b("fxView-lifecycle-> code->addView");
            }
            com.petterp.floatingx.listener.d dVar = this.z.v;
            if (dVar != null) {
                dVar.f();
            }
            ViewGroup o = o();
            if (o != null) {
                o.addView(c());
            }
            if (isAnimation) {
                com.petterp.floatingx.assist.helper.a aVar = this.z;
                if (aVar.p && aVar.f != null) {
                    c cVar3 = aVar.y;
                    if (cVar3 != null) {
                        cVar3.b("fxView->Animation -----start");
                    }
                    com.petterp.floatingx.assist.a aVar2 = this.z.f;
                    if (aVar2 != null) {
                        aVar2.e(c());
                    }
                }
            }
            xVar = x.a;
        }
        if (xVar == null && (cVar = this.z.y) != null) {
            cVar.c("system -> fxParentView==null");
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void m(@Nullable ViewGroup container) {
        super.m(container);
        j();
    }

    /* access modifiers changed from: protected */
    public void r() {
        A();
        super.r();
        C();
    }

    /* access modifiers changed from: protected */
    public void u() {
        A();
        super.u();
        com.petterp.floatingx.a.a.l(this.z.e(), this);
    }

    private final void A() {
        FxManagerView managerView = c();
        if (managerView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(managerView, (OnApplyWindowInsetsListener) null);
        }
    }
}
