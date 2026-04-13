package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.CancellationTokenSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzhp extends zzhu {
    private final CancellationTokenSource zza;
    private final String zzb;

    zzhp(CancellationTokenSource cancellationTokenSource, String str) {
        this.zza = cancellationTokenSource;
        if (str != null) {
            this.zzb = str;
            return;
        }
        throw new NullPointerException("Null placeId");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzhu) {
            zzhu zzhu = (zzhu) obj;
            return this.zza.equals(zzhu.zza()) && this.zzb.equals(zzhu.zzb());
        }
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
    }

    public final String toString() {
        String obj = this.zza.toString();
        String str = this.zzb;
        return "PlaceRequest{source=" + obj + ", placeId=" + str + "}";
    }

    public final CancellationTokenSource zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }
}
