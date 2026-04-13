package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.manager.c;
import com.bumptech.glide.util.i;

public final class DefaultConnectivityMonitor implements c {
    private final Context c;
    final c.a d;
    boolean f;
    private boolean q;
    private final BroadcastReceiver x = new BroadcastReceiver() {
        public void onReceive(@NonNull Context context, Intent intent) {
            DefaultConnectivityMonitor defaultConnectivityMonitor = DefaultConnectivityMonitor.this;
            boolean wasConnected = defaultConnectivityMonitor.f;
            defaultConnectivityMonitor.f = defaultConnectivityMonitor.h(context);
            if (wasConnected != DefaultConnectivityMonitor.this.f) {
                if (Log.isLoggable("ConnectivityMonitor", 3)) {
                    Log.d("ConnectivityMonitor", "connectivity changed, isConnected: " + DefaultConnectivityMonitor.this.f);
                }
                DefaultConnectivityMonitor defaultConnectivityMonitor2 = DefaultConnectivityMonitor.this;
                defaultConnectivityMonitor2.d.a(defaultConnectivityMonitor2.f);
            }
        }
    };

    DefaultConnectivityMonitor(@NonNull Context context, @NonNull c.a listener) {
        this.c = context.getApplicationContext();
        this.d = listener;
    }

    private void i() {
        if (!this.q) {
            this.f = h(this.c);
            try {
                this.c.registerReceiver(this.x, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.q = true;
            } catch (SecurityException e) {
                if (Log.isLoggable("ConnectivityMonitor", 5)) {
                    Log.w("ConnectivityMonitor", "Failed to register", e);
                }
            }
        }
    }

    private void j() {
        if (this.q) {
            this.c.unregisterReceiver(this.x);
            this.q = false;
        }
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"MissingPermission"})
    public boolean h(@NonNull Context context) {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) i.d((ConnectivityManager) context.getSystemService("connectivity"))).getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
            }
            return true;
        } catch (RuntimeException e) {
            if (Log.isLoggable("ConnectivityMonitor", 5)) {
                Log.w("ConnectivityMonitor", "Failed to determine connectivity status when connectivity changed", e);
            }
            return true;
        }
    }

    public void onStart() {
        i();
    }

    public void onStop() {
        j();
    }

    public void onDestroy() {
    }
}
