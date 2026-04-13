package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzt;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final /* synthetic */ class zzfr implements Callable {
    public final /* synthetic */ zzfv zza;

    public /* synthetic */ zzfr(zzfv zzfv) {
        this.zza = zzfv;
    }

    public final Object call() {
        return new zzt(this.zza.zze);
    }
}
