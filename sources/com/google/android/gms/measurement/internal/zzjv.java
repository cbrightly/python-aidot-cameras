package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjv implements Runnable {
    final /* synthetic */ zzjx zza;

    zzjv(zzjx zzjx) {
        this.zza = zzjx;
    }

    public final void run() {
        zzjy zzjy = this.zza.zza;
        Context zzaw = zzjy.zzt.zzaw();
        this.zza.zza.zzt.zzay();
        zzjy.zzo(zzjy, new ComponentName(zzaw, "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
