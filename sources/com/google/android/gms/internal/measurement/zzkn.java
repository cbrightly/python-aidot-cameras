package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzkn {
    static final zzkn zza = new zzkn(true);
    public static final /* synthetic */ int zzb = 0;
    private static volatile boolean zzc = false;
    private static volatile zzkn zzd;
    private final Map zze;

    zzkn() {
        this.zze = new HashMap();
    }

    public final zzkz zzb(zzmi zzmi, int i) {
        return (zzkz) this.zze.get(new zzkm(zzmi, i));
    }

    zzkn(boolean z) {
        this.zze = Collections.emptyMap();
    }

    public static zzkn zza() {
        Class<zzkn> cls = zzkn.class;
        zzkn zzkn = zzd;
        if (zzkn != null) {
            return zzkn;
        }
        synchronized (cls) {
            zzkn zzkn2 = zzd;
            if (zzkn2 != null) {
                return zzkn2;
            }
            zzkn zzb2 = zzkv.zzb(cls);
            zzd = zzb2;
            return zzb2;
        }
    }
}
