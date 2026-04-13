package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzlz implements zzmg {
    private final zzmg[] zza;

    zzlz(zzmg... zzmgArr) {
        this.zza = zzmgArr;
    }

    public final zzmf zzb(Class cls) {
        zzmg[] zzmgArr = this.zza;
        for (int i = 0; i < 2; i++) {
            zzmg zzmg = zzmgArr[i];
            if (zzmg.zzc(cls)) {
                return zzmg.zzb(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(String.valueOf(cls.getName())));
    }

    public final boolean zzc(Class cls) {
        zzmg[] zzmgArr = this.zza;
        for (int i = 0; i < 2; i++) {
            if (zzmgArr[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
