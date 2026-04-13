package com.bumptech.glide.manager;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.manager.c;

/* compiled from: DefaultConnectivityMonitorFactory */
public class e implements d {
    @NonNull
    public c a(@NonNull Context context, @NonNull c.a listener) {
        String str;
        boolean hasPermission = ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") == 0;
        if (Log.isLoggable("ConnectivityMonitor", 3)) {
            if (hasPermission) {
                str = "ACCESS_NETWORK_STATE permission granted, registering connectivity monitor";
            } else {
                str = "ACCESS_NETWORK_STATE permission missing, cannot register connectivity monitor";
            }
            Log.d("ConnectivityMonitor", str);
        }
        if (hasPermission) {
            return new DefaultConnectivityMonitor(context, listener);
        }
        return new m();
    }
}
