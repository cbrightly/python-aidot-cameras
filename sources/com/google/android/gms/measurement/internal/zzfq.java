package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzu;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzfq implements Callable {
    public final /* synthetic */ zzfv zza;
    public final /* synthetic */ String zzb;

    public /* synthetic */ zzfq(zzfv zzfv, String str) {
        this.zza = zzfv;
        this.zzb = str;
    }

    public final Object call() {
        return new zzu("internal.appMetadata", new zzfo(this.zza, this.zzb));
    }
}
