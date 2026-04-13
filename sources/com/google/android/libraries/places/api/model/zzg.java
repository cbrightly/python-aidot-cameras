package com.google.android.libraries.places.api.model;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzg extends zzbk {
    private final int zza;
    private final int zzb;

    zzg(int i, int i2) {
        this.zza = i;
        this.zzb = i2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzbk) {
            zzbk zzbk = (zzbk) obj;
            return this.zza == zzbk.zzb() && this.zzb == zzbk.zza();
        }
    }

    public final int hashCode() {
        return ((this.zza ^ 1000003) * 1000003) ^ this.zzb;
    }

    public final String toString() {
        int i = this.zza;
        int i2 = this.zzb;
        return "SubstringMatch{offset=" + i + ", length=" + i2 + "}";
    }

    /* access modifiers changed from: package-private */
    public final int zza() {
        return this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final int zzb() {
        return this.zza;
    }
}
