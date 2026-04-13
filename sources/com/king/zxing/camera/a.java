package com.king.zxing.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import com.king.zxing.util.b;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: AutoFocusManager */
public final class a implements Camera.AutoFocusCallback {
    private static final Collection<String> a;
    private boolean b;
    private boolean c;
    private final boolean d;
    private final Camera e;
    private AsyncTask<?, ?, ?> f;

    static {
        ArrayList arrayList = new ArrayList(2);
        a = arrayList;
        arrayList.add("auto");
        arrayList.add("macro");
    }

    a(Context context, Camera camera) {
        this.e = camera;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String currentFocusMode = camera.getParameters().getFocusMode();
        boolean z = true;
        z = (!sharedPrefs.getBoolean("preferences_auto_focus", true) || !a.contains(currentFocusMode)) ? false : z;
        this.d = z;
        b.f("Current focus mode '" + currentFocusMode + "'; use auto focus? " + z);
        c();
    }

    public synchronized void onAutoFocus(boolean success, Camera theCamera) {
        this.c = false;
        a();
        if (success) {
            b.h("AutoFocusManager --lite 自动聚焦成功");
        } else {
            b.h("AutoFocusManager --lite 自动聚焦失败");
        }
    }

    private synchronized void a() {
        if (!this.b && this.f == null) {
            C0076a newTask = new C0076a(this);
            try {
                newTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                this.f = newTask;
            } catch (RejectedExecutionException ree) {
                b.j("Could not request auto focus", ree);
            }
        }
        return;
    }

    /* access modifiers changed from: package-private */
    public synchronized void c() {
        if (this.d) {
            this.f = null;
            if (!this.b && !this.c) {
                try {
                    b.h("AutoFocusManager 聚焦");
                    this.e.autoFocus(this);
                    this.c = true;
                } catch (RuntimeException re) {
                    b.j("Unexpected exception while focusing", re);
                    a();
                }
            }
        }
        return;
    }

    private synchronized void b() {
        AsyncTask<?, ?, ?> asyncTask = this.f;
        if (asyncTask != null) {
            if (asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                this.f.cancel(true);
            }
            this.f = null;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void d() {
        this.b = true;
        if (this.d) {
            b();
            try {
                this.e.cancelAutoFocus();
            } catch (RuntimeException re) {
                b.j("Unexpected exception while cancelling focusing", re);
            }
        }
        return;
    }

    /* renamed from: com.king.zxing.camera.a$a  reason: collision with other inner class name */
    /* compiled from: AutoFocusManager */
    public static class C0076a extends AsyncTask<Object, Object, Object> {
        private WeakReference<a> a;

        public C0076a(a manager) {
            this.a = new WeakReference<>(manager);
        }

        /* access modifiers changed from: protected */
        public Object doInBackground(Object... voids) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            a manager = (a) this.a.get();
            if (manager == null) {
                return null;
            }
            manager.c();
            return null;
        }
    }
}
