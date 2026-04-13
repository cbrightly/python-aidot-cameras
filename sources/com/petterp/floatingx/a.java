package com.petterp.floatingx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.leedarson.bean.H5ActionName;
import com.petterp.floatingx.impl.control.b;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressLint({"StaticFieldLeak"})
/* compiled from: FloatingX.kt */
public final class a {
    @NotNull
    public static final a a = new a();
    @NotNull
    private static HashMap<String, b> b = new HashMap<>(3);
    @Nullable
    private static com.petterp.floatingx.impl.lifecycle.a c;
    private static /* synthetic */ Application d;

    @NotNull
    public static final com.petterp.floatingx.listener.control.a c() {
        return e((String) null, 1, (Object) null);
    }

    private a() {
    }

    @Nullable
    public final Application f() {
        return d;
    }

    public final void k(@Nullable Application application) {
        d = application;
    }

    @NotNull
    public static final com.petterp.floatingx.listener.control.a i(@NotNull com.petterp.floatingx.assist.helper.a helper) {
        b bVar;
        k.e(helper, "helper");
        a aVar = a;
        if (d != null) {
            if ((!b.isEmpty()) && (bVar = b.get(helper.e())) != null) {
                bVar.i();
            }
            b fxAppControlImpl = new b(helper, new com.petterp.floatingx.impl.lifecycle.b());
            b.put(helper.e(), fxAppControlImpl);
            if (helper.m) {
                b(aVar, (Activity) null, 1, (Object) null);
            }
            return fxAppControlImpl;
        }
        throw new NullPointerException("context == null, please call AppHelper.setContext(context) to set context");
    }

    public static /* synthetic */ com.petterp.floatingx.listener.control.a e(String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "FX_DEFAULT_TAG";
        }
        return d(str);
    }

    @NotNull
    public static final com.petterp.floatingx.listener.control.a d(@NotNull String tag) {
        k.e(tag, Progress.TAG);
        return a.h(tag);
    }

    public final /* synthetic */ Map g() {
        return b;
    }

    public final /* synthetic */ void l(String tag, b control) {
        k.e(tag, Progress.TAG);
        k.e(control, H5ActionName.ACTION_DEVICE_CONTROL);
        if (b.values().contains(control)) {
            b.remove(tag);
        }
        if (b.isEmpty()) {
            j();
        }
    }

    public static /* synthetic */ void b(a aVar, Activity activity, int i, Object obj) {
        if ((i & 1) != 0) {
            activity = null;
        }
        aVar.a(activity);
    }

    public final /* synthetic */ void a(Activity activity) {
        if (c == null) {
            com.petterp.floatingx.impl.lifecycle.a.c.d(activity);
            com.petterp.floatingx.impl.lifecycle.a aVar = new com.petterp.floatingx.impl.lifecycle.a();
            c = aVar;
            Application application = d;
            if (application != null) {
                application.registerActivityLifecycleCallbacks(aVar);
            }
        }
    }

    private final b h(String tag) {
        String errorMessage = "fxs[" + tag + "]==null!,Please check if FloatingX.install() or AppHelper.setTag() is called.";
        b bVar = b.get(tag);
        if (bVar != null) {
            return bVar;
        }
        throw new NullPointerException(errorMessage);
    }

    private final void j() {
        if (c != null || com.petterp.floatingx.impl.lifecycle.a.c.a() != null) {
            Application application = d;
            if (application != null) {
                application.unregisterActivityLifecycleCallbacks(c);
            }
            com.petterp.floatingx.impl.lifecycle.a.c.b();
            c = null;
        }
    }
}
