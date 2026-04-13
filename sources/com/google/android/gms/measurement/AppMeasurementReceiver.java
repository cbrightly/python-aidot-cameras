package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.measurement.internal.zzfn;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class AppMeasurementReceiver extends WakefulBroadcastReceiver implements zzfn.zza {
    private zzfn zza;

    @NonNull
    public BroadcastReceiver.PendingResult doGoAsync() {
        return goAsync();
    }

    @MainThread
    public void doStartService(@NonNull Context context, @NonNull Intent service) {
        WakefulBroadcastReceiver.startWakefulService(context, service);
    }

    @MainThread
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        if (this.zza == null) {
            this.zza = new zzfn(this);
        }
        this.zza.zza(context, intent);
    }
}
