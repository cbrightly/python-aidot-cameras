package com.petterp.floatingx.impl.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.petterp.floatingx.impl.control.b;
import java.lang.ref.WeakReference;
import java.util.Collection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxLifecycleCallbackImpl.kt */
public final class a implements Application.ActivityLifecycleCallbacks {
    @NotNull
    public static final C0201a c = new C0201a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @Nullable
    public static WeakReference<Activity> d;

    private final Collection<b> c() {
        return com.petterp.floatingx.a.a.g().values();
    }

    public void onActivityCreated(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
        k.e(activity, "activity");
        if (!d()) {
            for (b fx : c()) {
                fx.onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    public void onActivityStarted(@NotNull Activity activity) {
        k.e(activity, "activity");
        if (!d()) {
            for (b fx : c()) {
                fx.onActivityStarted(activity);
            }
        }
    }

    public void onActivityResumed(@NotNull Activity activity) {
        k.e(activity, "activity");
        e(activity);
        if (!d()) {
            for (b fx : c()) {
                fx.onActivityResumed(activity);
            }
        }
    }

    public void onActivityPaused(@NotNull Activity activity) {
        k.e(activity, "activity");
        if (!d()) {
            for (b fx : c()) {
                fx.onActivityPaused(activity);
            }
        }
    }

    public void onActivityStopped(@NotNull Activity activity) {
        k.e(activity, "activity");
        if (!d()) {
            for (b fx : c()) {
                fx.onActivityStopped(activity);
            }
        }
    }

    public void onActivityDestroyed(@NotNull Activity activity) {
        k.e(activity, "activity");
        if (!d()) {
            for (b fx : c()) {
                fx.onActivityDestroyed(activity);
            }
            WeakReference<Activity> weakReference = d;
            if ((weakReference == null ? null : (Activity) weakReference.get()) == activity) {
                WeakReference<Activity> weakReference2 = d;
                if (weakReference2 != null) {
                    weakReference2.clear();
                }
                d = null;
            }
        }
    }

    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {
        k.e(activity, "activity");
        k.e(outState, "outState");
        if (!d()) {
            for (b fx : c()) {
                fx.onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    private final boolean d() {
        return c().isEmpty();
    }

    private final void e(Activity activity) {
        WeakReference<Activity> weakReference = d;
        if (!k.a(weakReference == null ? null : (Activity) weakReference.get(), activity)) {
            d = new WeakReference<>(activity);
        }
    }

    /* renamed from: com.petterp.floatingx.impl.lifecycle.a$a  reason: collision with other inner class name */
    /* compiled from: FxLifecycleCallbackImpl.kt */
    public static final class C0201a {
        public /* synthetic */ C0201a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0201a() {
        }

        @Nullable
        public final WeakReference<Activity> a() {
            return a.d;
        }

        public final void c(@Nullable WeakReference<Activity> weakReference) {
            a.d = weakReference;
        }

        public final /* synthetic */ void d(Activity activity) {
            if (activity != null) {
                WeakReference<Activity> a = a();
                if ((a == null ? null : (Activity) a.get()) != activity) {
                    c(new WeakReference(activity));
                }
            }
        }

        public final /* synthetic */ void b() {
            WeakReference<Activity> a = a();
            if (a != null) {
                a.clear();
            }
            c((WeakReference<Activity>) null);
        }
    }
}
