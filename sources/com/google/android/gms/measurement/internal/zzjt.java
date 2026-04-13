package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzjt implements Runnable {
    final /* synthetic */ ComponentName zza;
    final /* synthetic */ zzjx zzb;

    zzjt(zzjx zzjx, ComponentName componentName) {
        this.zzb = zzjx;
        this.zza = componentName;
    }

    public final void run() {
        zzjy.zzo(this.zzb.zza, this.zza);
    }
}
