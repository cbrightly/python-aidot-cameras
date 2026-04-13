package com.google.android.libraries.places.api.model;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzf extends zzbj {
    private int zza;
    private int zzb;
    private byte zzc;

    zzf() {
    }

    public final zzbj zza(int i) {
        this.zzb = i;
        this.zzc = (byte) (this.zzc | 2);
        return this;
    }

    public final zzbj zzb(int i) {
        this.zza = i;
        this.zzc = (byte) (this.zzc | 1);
        return this;
    }

    public final zzbk zzc() {
        if (this.zzc == 3) {
            return new zzak(this.zza, this.zzb);
        }
        StringBuilder sb = new StringBuilder();
        if ((this.zzc & 1) == 0) {
            sb.append(" offset");
        }
        if ((this.zzc & 2) == 0) {
            sb.append(" length");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
