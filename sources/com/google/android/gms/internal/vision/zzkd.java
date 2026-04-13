package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzkd implements zzkl {
    private zzkl[] zza;

    zzkd(zzkl... zzklArr) {
        this.zza = zzklArr;
    }

    public final boolean zza(Class<?> cls) {
        for (zzkl zza2 : this.zza) {
            if (zza2.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzki zzb(Class<?> cls) {
        for (zzkl zzkl : this.zza) {
            if (zzkl.zza(cls)) {
                return zzkl.zzb(cls);
            }
        }
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? "No factory is available for message type: ".concat(valueOf) : new String("No factory is available for message type: "));
    }
}
