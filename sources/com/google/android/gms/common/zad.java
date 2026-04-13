package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.internal.base.zau;

@SuppressLint({"HandlerLeak"})
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zad extends zau {
    final /* synthetic */ GoogleApiAvailability zaa;
    private final Context zab;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zad(GoogleApiAvailability googleApiAvailability, Context context) {
        super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
        this.zaa = googleApiAvailability;
        this.zab = context.getApplicationContext();
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        switch (i) {
            case 1:
                int isGooglePlayServicesAvailable = this.zaa.isGooglePlayServicesAvailable(this.zab);
                if (this.zaa.isUserResolvableError(isGooglePlayServicesAvailable)) {
                    this.zaa.showErrorNotification(this.zab, isGooglePlayServicesAvailable);
                    return;
                }
                return;
            default:
                Log.w("GoogleApiAvailability", "Don't know how to handle this message: " + i);
                return;
        }
    }
}
