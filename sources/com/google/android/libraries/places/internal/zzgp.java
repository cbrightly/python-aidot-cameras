package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgp extends zzgr {
    private final String zza;
    private final int zzb;
    private final int zzc;

    /* synthetic */ zzgp(String str, int i, int i2, zzgo zzgo) {
        this.zza = str;
        this.zzb = i;
        this.zzc = i2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzgr) {
            zzgr zzgr = (zzgr) obj;
            return this.zza.equals(zzgr.zzb()) && this.zzb == zzgr.zza() && this.zzc == zzgr.zzc();
        }
    }

    public final int hashCode() {
        return ((((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb) * 1000003) ^ this.zzc;
    }

    public final String toString() {
        String str;
        String str2 = this.zza;
        int i = this.zzb;
        switch (this.zzc) {
            case 1:
                str = "PROGRAMMATIC_API";
                break;
            default:
                str = "AUTOCOMPLETE_WIDGET";
                break;
        }
        return "ClientProfile{packageName=" + str2 + ", versionCode=" + i + ", requestSource=" + str + "}";
    }

    public final int zza() {
        return this.zzb;
    }

    public final String zzb() {
        return this.zza;
    }

    public final int zzc() {
        return this.zzc;
    }
}
