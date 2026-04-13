package com.google.android.gms.common;

import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final /* synthetic */ class zze implements Callable {
    public final /* synthetic */ boolean zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ zzj zzc;

    public /* synthetic */ zze(boolean z, String str, zzj zzj) {
        this.zza = z;
        this.zzb = str;
        this.zzc = zzj;
    }

    public final Object call() {
        return zzn.zzd(this.zza, this.zzb, this.zzc);
    }
}
