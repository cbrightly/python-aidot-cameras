package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzhb;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.2.2 */
public final class zzd implements AppMeasurementSdk.OnEventListener {
    final /* synthetic */ zze zza;

    public zzd(zze zze) {
        this.zza = zze;
    }

    public final void onEvent(String str, String str2, Bundle bundle, long j) {
        if (this.zza.zza.contains(str2)) {
            Bundle bundle2 = new Bundle();
            int i = zzc.zza;
            String zza2 = zzhb.zza(str2);
            if (zza2 != null) {
                str2 = zza2;
            }
            bundle2.putString("events", str2);
            this.zza.zzb.onMessageTriggered(2, bundle2);
        }
    }
}
