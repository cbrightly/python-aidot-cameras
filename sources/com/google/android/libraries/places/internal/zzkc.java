package com.google.android.libraries.places.internal;

import java.io.Serializable;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzkc extends zzkd implements Serializable {
    public static final /* synthetic */ int zzc = 0;
    private static final zzkc zzd = new zzkc(zzjj.zzb, zzjh.zzb);
    final zzjl zza;
    final zzjl zzb;

    private zzkc(zzjl zzjl, zzjl zzjl2) {
        this.zza = zzjl;
        this.zzb = zzjl2;
        if (zzjl.compareTo(zzjl2) > 0 || zzjl == zzjh.zzb || zzjl2 == zzjj.zzb) {
            throw new IllegalArgumentException("Invalid range: ".concat(zze(zzjl, zzjl2)));
        }
    }

    public static zzkc zza(Comparable comparable) {
        return new zzkc(new zzjk(comparable), zzjh.zzb);
    }

    public static zzkc zzb(Comparable comparable, Comparable comparable2) {
        return new zzkc(new zzjk(comparable), new zzji(comparable2));
    }

    public static zzkc zzc(Comparable comparable, Comparable comparable2) {
        return new zzkc(new zzjk(comparable), new zzjk(comparable2));
    }

    private static String zze(zzjl zzjl, zzjl zzjl2) {
        StringBuilder sb = new StringBuilder(16);
        zzjl.zzc(sb);
        sb.append("..");
        zzjl2.zzd(sb);
        return sb.toString();
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj instanceof zzkc) {
            zzkc zzkc = (zzkc) obj;
            if (!this.zza.equals(zzkc.zza) || !this.zzb.equals(zzkc.zzb)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (this.zza.hashCode() * 31) + this.zzb.hashCode();
    }

    public final String toString() {
        return zze(this.zza, this.zzb);
    }

    public final boolean zzd(Comparable comparable) {
        if (comparable == null) {
            throw null;
        } else if (!this.zza.zze(comparable) || this.zzb.zze(comparable)) {
            return false;
        } else {
            return true;
        }
    }
}
