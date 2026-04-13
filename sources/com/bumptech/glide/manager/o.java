package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.c;
import com.bumptech.glide.e;
import com.bumptech.glide.i;
import com.bumptech.glide.load.resource.bitmap.r;
import com.bumptech.glide.util.j;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RequestManagerRetriever */
public class o implements Handler.Callback {
    private static final b c = new a();
    private final Bundle a1 = new Bundle();
    private volatile i d;
    @VisibleForTesting
    final Map<FragmentManager, n> f = new HashMap();
    private final ArrayMap<View, Fragment> p0 = new ArrayMap<>();
    private final j p1;
    @VisibleForTesting
    final Map<androidx.fragment.app.FragmentManager, SupportRequestManagerFragment> q = new HashMap();
    private final Handler x;
    private final b y;
    private final ArrayMap<View, androidx.fragment.app.Fragment> z = new ArrayMap<>();

    /* compiled from: RequestManagerRetriever */
    public interface b {
        @NonNull
        i a(@NonNull com.bumptech.glide.b bVar, @NonNull k kVar, @NonNull p pVar, @NonNull Context context);
    }

    public o(@Nullable b factory, e experiments) {
        this.y = factory != null ? factory : c;
        this.x = new Handler(Looper.getMainLooper(), this);
        this.p1 = b(experiments);
    }

    private static j b(e experiments) {
        if (!r.b || !r.a) {
            return new f();
        }
        if (experiments.a(c.d.class)) {
            return new h();
        }
        return new i();
    }

    @NonNull
    private i h(@NonNull Context context) {
        if (this.d == null) {
            synchronized (this) {
                if (this.d == null) {
                    this.d = this.y.a(com.bumptech.glide.b.c(context.getApplicationContext()), new b(), new g(), context.getApplicationContext());
                }
            }
        }
        return this.d;
    }

    @NonNull
    public i f(@NonNull Context context) {
        if (context != null) {
            if (j.r() && !(context instanceof Application)) {
                if (context instanceof FragmentActivity) {
                    return g((FragmentActivity) context);
                }
                if (context instanceof Activity) {
                    return e((Activity) context);
                }
                if ((context instanceof ContextWrapper) && ((ContextWrapper) context).getBaseContext().getApplicationContext() != null) {
                    return f(((ContextWrapper) context).getBaseContext());
                }
            }
            return h(context);
        }
        throw new IllegalArgumentException("You cannot start a load on a null Context");
    }

    @NonNull
    public i g(@NonNull FragmentActivity activity) {
        if (j.q()) {
            return f(activity.getApplicationContext());
        }
        a(activity);
        this.p1.a(activity);
        return n(activity, activity.getSupportFragmentManager(), (androidx.fragment.app.Fragment) null, m(activity));
    }

    @NonNull
    public i e(@NonNull Activity activity) {
        if (j.q()) {
            return f(activity.getApplicationContext());
        }
        if (activity instanceof FragmentActivity) {
            return g((FragmentActivity) activity);
        }
        a(activity);
        this.p1.a(activity);
        return d(activity, activity.getFragmentManager(), (Fragment) null, m(activity));
    }

    @Nullable
    private static Activity c(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return c(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @TargetApi(17)
    private static void a(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    @Deprecated
    public n i(Activity activity) {
        return j(activity.getFragmentManager(), (Fragment) null);
    }

    @NonNull
    private n j(@NonNull FragmentManager fm, @Nullable Fragment parentHint) {
        n current = (n) fm.findFragmentByTag("com.bumptech.glide.manager");
        if (current != null) {
            return current;
        }
        n current2 = this.f.get(fm);
        if (current2 != null) {
            return current2;
        }
        n current3 = new n();
        current3.j(parentHint);
        this.f.put(fm, current3);
        fm.beginTransaction().add(current3, "com.bumptech.glide.manager").commitAllowingStateLoss();
        this.x.obtainMessage(1, fm).sendToTarget();
        return current3;
    }

    @NonNull
    @Deprecated
    private i d(@NonNull Context context, @NonNull FragmentManager fm, @Nullable Fragment parentHint, boolean isParentVisible) {
        n current = j(fm, parentHint);
        i requestManager = current.e();
        if (requestManager == null) {
            requestManager = this.y.a(com.bumptech.glide.b.c(context), current.c(), current.f(), context);
            if (isParentVisible) {
                requestManager.onStart();
            }
            current.k(requestManager);
        }
        return requestManager;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SupportRequestManagerFragment k(androidx.fragment.app.FragmentManager fragmentManager) {
        return l(fragmentManager, (androidx.fragment.app.Fragment) null);
    }

    private static boolean m(Context context) {
        Activity activity = c(context);
        return activity == null || !activity.isFinishing();
    }

    @NonNull
    private SupportRequestManagerFragment l(@NonNull androidx.fragment.app.FragmentManager fm, @Nullable androidx.fragment.app.Fragment parentHint) {
        SupportRequestManagerFragment current = (SupportRequestManagerFragment) fm.findFragmentByTag("com.bumptech.glide.manager");
        if (current != null) {
            return current;
        }
        SupportRequestManagerFragment current2 = this.q.get(fm);
        if (current2 != null) {
            return current2;
        }
        SupportRequestManagerFragment current3 = new SupportRequestManagerFragment();
        current3.v1(parentHint);
        this.q.put(fm, current3);
        fm.beginTransaction().add((androidx.fragment.app.Fragment) current3, "com.bumptech.glide.manager").commitAllowingStateLoss();
        this.x.obtainMessage(2, fm).sendToTarget();
        return current3;
    }

    @NonNull
    private i n(@NonNull Context context, @NonNull androidx.fragment.app.FragmentManager fm, @Nullable androidx.fragment.app.Fragment parentHint, boolean isParentVisible) {
        SupportRequestManagerFragment current = l(fm, parentHint);
        i requestManager = current.p1();
        if (requestManager == null) {
            requestManager = this.y.a(com.bumptech.glide.b.c(context), current.n1(), current.q1(), context);
            if (isParentVisible) {
                requestManager.onStart();
            }
            current.w1(requestManager);
        }
        return requestManager;
    }

    public boolean handleMessage(Message message) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        switch (message.what) {
            case 1:
                Object fm = (FragmentManager) message.obj;
                key = fm;
                removed = this.f.remove(fm);
                break;
            case 2:
                Object supportFm = (androidx.fragment.app.FragmentManager) message.obj;
                key = supportFm;
                removed = this.q.remove(supportFm);
                break;
            default:
                handled = false;
                break;
        }
        if (handled && removed == null && Log.isLoggable("RMRetriever", 5)) {
            Log.w("RMRetriever", "Failed to remove expected request manager fragment, manager: " + key);
        }
        return handled;
    }

    /* compiled from: RequestManagerRetriever */
    public class a implements b {
        a() {
        }

        @NonNull
        public i a(@NonNull com.bumptech.glide.b glide, @NonNull k lifecycle, @NonNull p requestManagerTreeNode, @NonNull Context context) {
            return new i(glide, lifecycle, requestManagerTreeNode, context);
        }
    }
}
