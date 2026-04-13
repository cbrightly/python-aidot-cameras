package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final /* synthetic */ class zzhi implements Runnable {
    public final /* synthetic */ zzij zza;
    public final /* synthetic */ Bundle zzb;
    public final /* synthetic */ long zzc;

    public /* synthetic */ zzhi(zzij zzij, Bundle bundle, long j) {
        this.zza = zzij;
        this.zzb = bundle;
        this.zzc = j;
    }

    public final void run() {
        zzij zzij = this.zza;
        Bundle bundle = this.zzb;
        long j = this.zzc;
        if (TextUtils.isEmpty(zzij.zzt.zzh().zzm())) {
            zzij.zzR(bundle, 0, j);
        } else {
            zzij.zzt.zzaA().zzl().zza("Using developer consent only; google app id found");
        }
    }
}
