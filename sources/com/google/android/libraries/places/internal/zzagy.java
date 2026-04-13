package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzagy implements zzahf {
    private final zzahf[] zza;

    zzagy(zzahf... zzahfArr) {
        this.zza = zzahfArr;
    }

    public final zzahe zzb(Class cls) {
        zzahf[] zzahfArr = this.zza;
        for (int i = 0; i < 2; i++) {
            zzahf zzahf = zzahfArr[i];
            if (zzahf.zzc(cls)) {
                return zzahf.zzb(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(String.valueOf(cls.getName())));
    }

    public final boolean zzc(Class cls) {
        zzahf[] zzahfArr = this.zza;
        for (int i = 0; i < 2; i++) {
            if (zzahfArr[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
