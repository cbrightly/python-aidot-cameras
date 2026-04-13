package com.google.android.libraries.places.api.model;

import com.google.android.libraries.places.api.model.PhotoMetadata;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzq extends PhotoMetadata.Builder {
    private String zza;
    private int zzb;
    private int zzc;
    private String zzd;
    private byte zze;

    zzq() {
    }

    public final String getAttributions() {
        String str = this.zza;
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Property \"attributions\" has not been set");
    }

    public final int getHeight() {
        if ((this.zze & 1) != 0) {
            return this.zzb;
        }
        throw new IllegalStateException("Property \"height\" has not been set");
    }

    public final int getWidth() {
        if ((this.zze & 2) != 0) {
            return this.zzc;
        }
        throw new IllegalStateException("Property \"width\" has not been set");
    }

    public final PhotoMetadata.Builder setAttributions(String str) {
        if (str != null) {
            this.zza = str;
            return this;
        }
        throw new NullPointerException("Null attributions");
    }

    public final PhotoMetadata.Builder setHeight(int i) {
        this.zzb = i;
        this.zze = (byte) (this.zze | 1);
        return this;
    }

    public final PhotoMetadata.Builder setWidth(int i) {
        this.zzc = i;
        this.zze = (byte) (this.zze | 2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public final PhotoMetadata.Builder zza(String str) {
        if (str != null) {
            this.zzd = str;
            return this;
        }
        throw new NullPointerException("Null photoReference");
    }

    /* access modifiers changed from: package-private */
    public final PhotoMetadata zzb() {
        String str;
        String str2;
        if (this.zze == 3 && (str = this.zza) != null && (str2 = this.zzd) != null) {
            return new zzaw(str, this.zzb, this.zzc, str2);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" attributions");
        }
        if ((this.zze & 1) == 0) {
            sb.append(" height");
        }
        if ((this.zze & 2) == 0) {
            sb.append(" width");
        }
        if (this.zzd == null) {
            sb.append(" photoReference");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
