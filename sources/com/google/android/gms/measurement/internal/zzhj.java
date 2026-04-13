package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final /* synthetic */ class zzhj implements Runnable {
    public final /* synthetic */ zzij zza;
    public final /* synthetic */ Bundle zzb;

    public /* synthetic */ zzhj(zzij zzij, Bundle bundle) {
        this.zza = zzij;
        this.zzb = bundle;
    }

    public final void run() {
        this.zza.zzC(this.zzb);
    }
}
