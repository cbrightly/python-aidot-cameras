package com.petterp.floatingx.impl.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.ViewParent;
import com.leedarson.bean.H5ActionName;
import com.petterp.floatingx.util.c;
import com.petterp.floatingx.util.d;
import com.petterp.floatingx.view.FxManagerView;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxProxyLifecycleCallBackImpl.kt */
public final class b implements Application.ActivityLifecycleCallbacks {
    @Nullable
    private com.petterp.floatingx.assist.helper.a c;
    @Nullable
    private com.petterp.floatingx.impl.control.b d;
    @NotNull
    private final g f = i.a(k.NONE, new a());

    private final c c() {
        com.petterp.floatingx.assist.helper.a aVar = this.c;
        if (aVar == null) {
            return null;
        }
        return aVar.y;
    }

    private final boolean b() {
        com.petterp.floatingx.assist.helper.a aVar = this.c;
        if (aVar == null) {
            return false;
        }
        return aVar.m;
    }

    private final com.petterp.floatingx.listener.b a() {
        com.petterp.floatingx.assist.helper.a aVar = this.c;
        if (aVar == null) {
            return null;
        }
        return aVar.d();
    }

    private final Map<Class<?>, Boolean> d() {
        return (Map) this.f.getValue();
    }

    private final String e(Activity $this$name) {
        String name = $this$name.getClass().getName();
        kotlin.jvm.internal.k.d(name, "javaClass.name");
        return (String) y.d0(x.F0(name, new String[]{"."}, false, 0, 6, (Object) null));
    }

    private final boolean i(Activity $this$isParent) {
        FxManagerView c2;
        com.petterp.floatingx.impl.control.b bVar = this.d;
        ViewParent viewParent = null;
        if (!(bVar == null || (c2 = bVar.c()) == null)) {
            viewParent = c2.getParent();
        }
        return viewParent == d.a($this$isParent);
    }

    private final boolean g(Activity $this$isActivityInValid) {
        Class cls = $this$isActivityInValid.getClass();
        Boolean bool = d().get(cls);
        return bool == null ? h(cls) : bool.booleanValue();
    }

    /* compiled from: FxExt.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<Class<?>, Boolean>> {
        public a() {
            super(0);
        }

        @NotNull
        public final Map<Class<?>, Boolean> invoke() {
            return new LinkedHashMap();
        }
    }

    public final void f(@NotNull com.petterp.floatingx.assist.helper.a helper, @NotNull com.petterp.floatingx.impl.control.b control) {
        kotlin.jvm.internal.k.e(helper, "helper");
        kotlin.jvm.internal.k.e(control, H5ActionName.ACTION_DEVICE_CONTROL);
        this.c = helper;
        this.d = control;
    }

    public void onActivityCreated(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
        com.petterp.floatingx.listener.b a2;
        kotlin.jvm.internal.k.e(activity, "activity");
        if (b() && a() != null && g(activity) && (a2 = a()) != null) {
            a2.e(activity, savedInstanceState);
        }
    }

    public void onActivityStarted(@NotNull Activity activity) {
        com.petterp.floatingx.assist.helper.a aVar;
        com.petterp.floatingx.listener.b d2;
        kotlin.jvm.internal.k.e(activity, "activity");
        if (b() && (aVar = this.c) != null && (d2 = aVar.d()) != null) {
            d2.g(activity);
        }
    }

    public void onActivityResumed(@NotNull Activity activity) {
        c c2;
        kotlin.jvm.internal.k.e(activity, "activity");
        if (b()) {
            String activityName = e(activity);
            c c3 = c();
            if (c3 != null) {
                c3.b("fxApp->insert, insert [" + activityName + "] Start ---------->");
            }
            if (g(activity)) {
                com.petterp.floatingx.listener.b a2 = a();
                if (a2 != null) {
                    a2.a(activity);
                }
                if (i(activity)) {
                    c c4 = c();
                    if (c4 != null) {
                        c4.b("fxApp->insert, insert [" + activityName + "] Fail ,The current Activity has been inserted.");
                        return;
                    }
                    return;
                }
                com.petterp.floatingx.impl.control.b it = this.d;
                kotlin.x xVar = null;
                if (it != null) {
                    it.z(activity);
                    c c5 = c();
                    if (c5 != null) {
                        c5.b("fxApp->insert, insert [" + activityName + "] Success--------------->");
                        xVar = kotlin.x.a;
                    }
                }
                if (xVar == null && (c2 = c()) != null) {
                    c2.b("fxApp->insert, insert [" + activityName + "] Fail ,appControl = null.");
                    return;
                }
                return;
            }
            c c6 = c();
            if (c6 != null) {
                c6.b("fxApp->insert, insert [" + activityName + "] Fail ,This activity is not in the list of allowed inserts.");
            }
        }
    }

    public void onActivityPaused(@NotNull Activity activity) {
        com.petterp.floatingx.listener.b it;
        kotlin.jvm.internal.k.e(activity, "activity");
        if (b() && (it = a()) != null && g(activity)) {
            it.c(activity);
        }
    }

    public void onActivityStopped(@NotNull Activity activity) {
        com.petterp.floatingx.listener.b it;
        kotlin.jvm.internal.k.e(activity, "activity");
        if (b() && (it = a()) != null && g(activity)) {
            it.d(activity);
        }
    }

    public void onActivityDestroyed(@NotNull Activity activity) {
        com.petterp.floatingx.impl.control.b bVar;
        kotlin.jvm.internal.k.e(activity, "activity");
        if (b()) {
            com.petterp.floatingx.listener.b it = a();
            if (it != null && g(activity)) {
                it.f(activity);
            }
            boolean isParent = i(activity);
            c c2 = c();
            if (c2 != null) {
                c2.b("fxApp->detach? isContainActivity-" + g(activity) + "--enableFx-" + b() + "---isParent-" + isParent);
            }
            if (isParent && (bVar = this.d) != null) {
                bVar.B(activity);
            }
        }
    }

    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {
        com.petterp.floatingx.listener.b it;
        kotlin.jvm.internal.k.e(activity, "activity");
        kotlin.jvm.internal.k.e(outState, "outState");
        if (b() && (it = a()) != null && g(activity)) {
            it.b(activity, outState);
        }
    }

    private final boolean h(Class<?> cls) {
        com.petterp.floatingx.assist.helper.a it = this.c;
        if (it == null) {
            return false;
        }
        boolean isInsert = it.g(cls);
        d().put(cls, Boolean.valueOf(isInsert));
        return isInsert;
    }
}
