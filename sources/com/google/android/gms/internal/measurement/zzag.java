package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzag implements zzap {
    private final zzap zza;
    private final String zzb;

    public zzag() {
        throw null;
    }

    public zzag(String str) {
        this.zza = zzap.zzf;
        this.zzb = str;
    }

    public zzag(String str, zzap zzap) {
        this.zza = zzap;
        this.zzb = str;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzag)) {
            return false;
        }
        zzag zzag = (zzag) obj;
        return this.zzb.equals(zzag.zzb) && this.zza.equals(zzag.zza);
    }

    public final int hashCode() {
        return (this.zzb.hashCode() * 31) + this.zza.hashCode();
    }

    public final zzap zzb() {
        return this.zza;
    }

    public final zzap zzbU(String str, zzg zzg, List list) {
        throw new IllegalStateException("Control does not have functions");
    }

    public final String zzc() {
        return this.zzb;
    }

    public final zzap zzd() {
        return new zzag(this.zzb, this.zza.zzd());
    }

    public final Boolean zzg() {
        throw new IllegalStateException("Control is not a boolean");
    }

    public final Double zzh() {
        throw new IllegalStateException("Control is not a double");
    }

    public final String zzi() {
        throw new IllegalStateException("Control is not a String");
    }

    public final Iterator zzl() {
        return null;
    }
}
