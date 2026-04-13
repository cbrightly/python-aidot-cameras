package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzgf implements Runnable {
    public final /* synthetic */ zzgw zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ Bundle zzc;

    public /* synthetic */ zzgf(zzgw zzgw, String str, Bundle bundle) {
        this.zza = zzgw;
        this.zzb = str;
        this.zzc = bundle;
    }

    public final void run() {
        this.zza.zzw(this.zzb, this.zzc);
    }
}
