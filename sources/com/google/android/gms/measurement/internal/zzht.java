package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzht implements Runnable {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zzij zzb;

    zzht(zzij zzij, Bundle bundle) {
        this.zzb = zzij;
        this.zza = bundle;
    }

    public final void run() {
        zzij zzij = this.zzb;
        Bundle bundle = this.zza;
        zzij.zzg();
        zzij.zza();
        Preconditions.checkNotNull(bundle);
        String checkNotEmpty = Preconditions.checkNotEmpty(bundle.getString("name"));
        if (!zzij.zzt.zzJ()) {
            zzij.zzt.zzaA().zzj().zza("Conditional property not cleared since app measurement is disabled");
            return;
        }
        zzlj zzlj = new zzlj(checkNotEmpty, 0, (Object) null, "");
        try {
            zzac zzac = r4;
            zzac zzac2 = new zzac(bundle.getString("app_id"), "", zzlj, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), bundle.getBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME), (zzaw) null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT), (zzaw) null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE), zzij.zzt.zzv().zzz(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS), "", bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), true, true));
            zzij.zzt.zzt().zzE(zzac);
        } catch (IllegalArgumentException e) {
        }
    }
}
