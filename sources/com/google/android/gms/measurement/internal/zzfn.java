package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcl;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfn {
    private final zza zza;

    /* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
    public interface zza {
        void doStartService(Context context, Intent intent);
    }

    public zzfn(zza zza2) {
        Preconditions.checkNotNull(zza2);
        this.zza = zza2;
    }

    @MainThread
    public final void zza(Context context, Intent intent) {
        zzge zzp = zzge.zzp(context, (zzcl) null, (Long) null);
        zzeu zzaA = zzp.zzaA();
        if (intent == null) {
            zzaA.zzk().zza("Receiver called with null intent");
            return;
        }
        zzp.zzay();
        String action = intent.getAction();
        zzaA.zzj().zzb("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzaA.zzj().zza("Starting wakeful intent.");
            this.zza.doStartService(context, className);
        } else if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            zzaA.zzk().zza("Install Referrer Broadcasts are deprecated");
        }
    }
}
