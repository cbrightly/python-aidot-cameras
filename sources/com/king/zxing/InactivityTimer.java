package com.king.zxing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import androidx.work.PeriodicWorkRequest;
import com.king.zxing.util.b;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.lang.ref.WeakReference;
import java.util.concurrent.RejectedExecutionException;

public final class InactivityTimer {
    private static final String a = InactivityTimer.class.getSimpleName();
    private final Activity b;
    private final BroadcastReceiver c = new PowerStatusReceiver(this);
    private boolean d = false;
    private AsyncTask<Object, Object, Object> e;

    InactivityTimer(Activity activity) {
        this.b = activity;
        c();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        b();
        a aVar = new a(this.b);
        this.e = aVar;
        try {
            aVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        } catch (RejectedExecutionException e2) {
            b.i("Couldn't schedule inactivity task; ignoring");
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        b();
        if (this.d) {
            this.b.unregisterReceiver(this.c);
            this.d = false;
            return;
        }
        b.i("PowerStatusReceiver was never registered?");
    }

    /* access modifiers changed from: package-private */
    public void e() {
        if (this.d) {
            b.i("PowerStatusReceiver was already registered?");
        } else {
            this.b.registerReceiver(this.c, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            this.d = true;
        }
        c();
    }

    /* access modifiers changed from: private */
    public void b() {
        AsyncTask<Object, Object, Object> asyncTask = this.e;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.e = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void f() {
        b();
    }

    public static class PowerStatusReceiver extends BroadcastReceiver {
        private WeakReference<InactivityTimer> a;

        public PowerStatusReceiver(InactivityTimer inactivityTimer) {
            this.a = new WeakReference<>(inactivityTimer);
        }

        public void onReceive(Context context, Intent intent) {
            InactivityTimer inactivityTimer;
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction()) && (inactivityTimer = (InactivityTimer) this.a.get()) != null) {
                if (intent.getIntExtra("plugged", -1) <= 0) {
                    inactivityTimer.c();
                } else {
                    inactivityTimer.b();
                }
            }
        }
    }

    public static class a extends AsyncTask<Object, Object, Object> {
        private WeakReference<Activity> a;

        public a(Activity activity) {
            this.a = new WeakReference<>(activity);
        }

        /* access modifiers changed from: protected */
        public Object doInBackground(Object... objects) {
            try {
                Thread.sleep(PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS);
                b.f("Finishing activity due to inactivity");
                Activity activity = (Activity) this.a.get();
                if (activity == null) {
                    return null;
                }
                activity.finish();
                return null;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }
}
