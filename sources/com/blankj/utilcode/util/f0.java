package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

/* compiled from: Utils */
public final class f0 {
    @SuppressLint({"StaticFieldLeak"})
    private static Application a;

    /* compiled from: Utils */
    public interface b {
        void a(Activity activity);

        void b(Activity activity);
    }

    public static void b(Application app) {
        if (app == null) {
            Log.e("Utils", "app is null.");
            return;
        }
        Application application = a;
        if (application == null) {
            a = app;
            h0.y(app);
            h0.F();
        } else if (!application.equals(app)) {
            h0.K(a);
            a = app;
            h0.y(app);
        }
    }

    public static Application a() {
        Application application = a;
        if (application != null) {
            return application;
        }
        b(h0.m());
        if (a != null) {
            Log.i("Utils", h0.n() + " reflect app success.");
            return a;
        }
        throw new NullPointerException("reflect failed.");
    }

    /* compiled from: Utils */
    public static class a {
        public void a(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void e(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void d(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void c(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void f(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void b(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void g(@NonNull Activity activity, Lifecycle.Event event) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }
    }
}
