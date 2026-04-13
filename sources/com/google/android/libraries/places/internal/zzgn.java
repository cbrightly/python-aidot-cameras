package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgn extends zzgq {
    private String zza;
    private int zzb;
    private byte zzc;
    private int zzd;

    zzgn() {
    }

    /* access modifiers changed from: package-private */
    public final zzgq zza(String str) {
        if (str != null) {
            this.zza = str;
            return this;
        }
        throw new NullPointerException("Null packageName");
    }

    /* access modifiers changed from: package-private */
    public final zzgq zzb(int i) {
        this.zzb = i;
        this.zzc = 1;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final zzgr zzc() {
        String str;
        int i;
        if (this.zzc == 1 && (str = this.zza) != null && (i = this.zzd) != 0) {
            return new zzgp(str, this.zzb, i, (zzgo) null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" packageName");
        }
        if (this.zzc == 0) {
            sb.append(" versionCode");
        }
        if (this.zzd == 0) {
            sb.append(" requestSource");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }

    public final zzgq zzd(int i) {
        this.zzd = i;
        return this;
    }
}
