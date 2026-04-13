package com.google.android.libraries.places.internal;

import com.google.android.gms.tasks.CancellationTokenSource;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzho extends zzht {
    private final CancellationTokenSource zza;
    private final String zzb;

    zzho(CancellationTokenSource cancellationTokenSource, String str) {
        this.zza = cancellationTokenSource;
        if (str != null) {
            this.zzb = str;
            return;
        }
        throw new NullPointerException("Null query");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzht) {
            zzht zzht = (zzht) obj;
            return this.zza.equals(zzht.zza()) && this.zzb.equals(zzht.zzb());
        }
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
    }

    public final String toString() {
        String obj = this.zza.toString();
        String str = this.zzb;
        return "AutocompleteRequest{source=" + obj + ", query=" + str + "}";
    }

    public final CancellationTokenSource zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }
}
