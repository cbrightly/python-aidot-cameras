package com.blankj.utilcode.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.Utils;
import com.blankj.utilcode.util.f0;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: UtilsActivityLifecycleImpl */
public final class g0 implements Application.ActivityLifecycleCallbacks {
    static final g0 c = new g0();
    private final LinkedList<Activity> d = new LinkedList<>();
    private final List<f0.b> f = new ArrayList();
    private final Map<Activity, List<f0.a>> q = new ConcurrentHashMap();
    private int x = 0;
    private int y = 0;
    private boolean z = false;

    g0() {
    }

    /* access modifiers changed from: package-private */
    public void m(Application app) {
        app.registerActivityLifecycleCallbacks(this);
    }

    /* access modifiers changed from: package-private */
    public void r(Application app) {
        this.d.clear();
        app.unregisterActivityLifecycleCallbacks(this);
    }

    /* access modifiers changed from: package-private */
    public Activity l() {
        for (Activity activity : f()) {
            if (h0.z(activity)) {
                return activity;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public List<Activity> f() {
        if (!this.d.isEmpty()) {
            return this.d;
        }
        this.d.addAll(e());
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public void addOnAppStatusChangedListener(f0.b listener) {
        this.f.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeOnAppStatusChangedListener(f0.b listener) {
        this.f.remove(listener);
    }

    /* compiled from: UtilsActivityLifecycleImpl */
    public class a implements Runnable {
        final /* synthetic */ Activity c;
        final /* synthetic */ f0.a d;

        a(Activity activity, f0.a aVar) {
            this.c = activity;
            this.d = aVar;
        }

        public void run() {
            g0.this.c(this.c, this.d);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Activity activity, f0.a listener) {
        if (activity != null && listener != null) {
            h0.I(new a(activity, listener));
        }
    }

    /* access modifiers changed from: package-private */
    public Application k() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object app = activityThreadClass.getMethod("getApplication", new Class[0]).invoke(g(), new Object[0]);
            if (app == null) {
                return null;
            }
            return (Application) app;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (ClassNotFoundException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void c(Activity activity, f0.a lifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> callbacks = this.q.get(activity);
        if (callbacks == null) {
            callbacks = new ArrayList<>();
            this.q.put(activity, callbacks);
        } else if (callbacks.contains(lifecycleCallbacks)) {
            return;
        }
        callbacks.add(lifecycleCallbacks);
    }

    private void d(Activity activity, Lifecycle.Event event) {
        List<Utils.ActivityLifecycleCallbacks> listeners = this.q.get(activity);
        if (listeners != null) {
            Iterator<Utils.ActivityLifecycleCallbacks> it = listeners.iterator();
            while (it.hasNext()) {
                f0.a listener = (f0.a) it.next();
                listener.g(activity, event);
                if (event.equals(Lifecycle.Event.ON_CREATE)) {
                    listener.a(activity);
                } else if (event.equals(Lifecycle.Event.ON_START)) {
                    listener.e(activity);
                } else if (event.equals(Lifecycle.Event.ON_RESUME)) {
                    listener.d(activity);
                } else if (event.equals(Lifecycle.Event.ON_PAUSE)) {
                    listener.c(activity);
                } else if (event.equals(Lifecycle.Event.ON_STOP)) {
                    listener.f(activity);
                } else if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                    listener.b(activity);
                }
            }
            if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                this.q.remove(activity);
            }
        }
    }

    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        if (activity != null) {
            h0.b(activity);
            p();
            q(activity);
            d(activity, Lifecycle.Event.ON_CREATE);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void onActivityStarted(@NonNull Activity activity) {
        if (activity != null) {
            if (!this.z) {
                q(activity);
            }
            int i = this.y;
            if (i < 0) {
                this.y = i + 1;
            } else {
                this.x++;
            }
            d(activity, Lifecycle.Event.ON_START);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void onActivityResumed(@NonNull Activity activity) {
        if (activity != null) {
            q(activity);
            if (this.z) {
                this.z = false;
                n(activity, true);
            }
            o(activity, false);
            d(activity, Lifecycle.Event.ON_RESUME);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void onActivityPaused(@NonNull Activity activity) {
        if (activity != null) {
            d(activity, Lifecycle.Event.ON_PAUSE);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            this.y--;
        } else {
            int i = this.x - 1;
            this.x = i;
            if (i <= 0) {
                this.z = true;
                n(activity, false);
            }
        }
        o(activity, true);
        d(activity, Lifecycle.Event.ON_STOP);
    }

    public void onActivitySaveInstanceState(@NonNull Activity activity, Bundle outState) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public void onActivityDestroyed(@NonNull Activity activity) {
        if (activity != null) {
            this.d.remove(activity);
            h0.i(activity);
            d(activity, Lifecycle.Event.ON_DESTROY);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private void o(Activity activity, boolean isSave) {
        if (isSave) {
            activity.getWindow().getDecorView().setTag(-123, Integer.valueOf(activity.getWindow().getAttributes().softInputMode));
            activity.getWindow().setSoftInputMode(3);
            return;
        }
        Object tag = activity.getWindow().getDecorView().getTag(-123);
        if (tag instanceof Integer) {
            h0.J(new b(activity, tag), 100);
        }
    }

    /* compiled from: UtilsActivityLifecycleImpl */
    public class b implements Runnable {
        final /* synthetic */ Activity c;
        final /* synthetic */ Object d;

        b(Activity activity, Object obj) {
            this.c = activity;
            this.d = obj;
        }

        public void run() {
            Window window = this.c.getWindow();
            if (window != null) {
                window.setSoftInputMode(((Integer) this.d).intValue());
            }
        }
    }

    private void n(Activity activity, boolean isForeground) {
        if (!this.f.isEmpty()) {
            for (f0.b statusListener : this.f) {
                if (isForeground) {
                    statusListener.a(activity);
                } else {
                    statusListener.b(activity);
                }
            }
        }
    }

    private void q(Activity activity) {
        if (!this.d.contains(activity)) {
            this.d.addFirst(activity);
        } else if (!this.d.getFirst().equals(activity)) {
            this.d.remove(activity);
            this.d.addFirst(activity);
        }
    }

    private List<Activity> e() {
        LinkedList<Activity> list = new LinkedList<>();
        Activity topActivity = null;
        try {
            Object activityThread = g();
            Field mActivitiesField = activityThread.getClass().getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(activityThread);
            if (!(mActivities instanceof Map)) {
                return list;
            }
            for (Object activityRecord : ((Map) mActivities).values()) {
                Class activityClientRecordClass = activityRecord.getClass();
                Field activityField = activityClientRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                if (topActivity == null) {
                    Field pausedField = activityClientRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        topActivity = activity;
                    } else {
                        list.add(activity);
                    }
                } else {
                    list.add(activity);
                }
            }
            if (topActivity != null) {
                list.addFirst(topActivity);
            }
            return list;
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e.getMessage());
        }
    }

    private Object g() {
        Object activityThread = h();
        if (activityThread != null) {
            return activityThread;
        }
        Object activityThread2 = i();
        if (activityThread2 != null) {
            return activityThread2;
        }
        return j();
    }

    private Object h() {
        try {
            Field sCurrentActivityThreadField = Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            return sCurrentActivityThreadField.get((Object) null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e.getMessage());
            return null;
        }
    }

    private Object i() {
        try {
            return Class.forName("android.app.ActivityThread").getMethod("currentActivityThread", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.getMessage());
            return null;
        }
    }

    private Object j() {
        try {
            Field mLoadedApkField = Application.class.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(f0.a());
            Field mActivityThreadField = mLoadedApk.getClass().getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            return mActivityThreadField.get(mLoadedApk);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInLoadedApkField: " + e.getMessage());
            return null;
        }
    }

    private static void p() {
        if (Build.VERSION.SDK_INT < 26 || !ValueAnimator.areAnimatorsEnabled()) {
            try {
                Field sDurationScaleField = ValueAnimator.class.getDeclaredField("sDurationScale");
                sDurationScaleField.setAccessible(true);
                if (((Float) sDurationScaleField.get((Object) null)).floatValue() == 0.0f) {
                    sDurationScaleField.set((Object) null, Float.valueOf(1.0f));
                    Log.i("UtilsActivityLifecycle", "setAnimatorsEnabled: Animators are enabled now!");
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
    }
}
