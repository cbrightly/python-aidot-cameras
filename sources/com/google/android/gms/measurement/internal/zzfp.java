package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzn;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzfp implements Callable {
    public final /* synthetic */ zzfv zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzfp(zzfv zzfv, String str) {
        this.zza = zzfv;
        this.zzb = str;
    }

    public final Object call() {
        return new zzn("internal.remoteConfig", new zzfu(this.zza, this.zzb));
    }
}
