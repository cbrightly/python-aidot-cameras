package com.google.android.gms.common.internal;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzv {
    @Nullable
    private final String zza;
    private final String zzb;
    private final boolean zzc;

    public zzv(String str, @Nullable String str2, boolean z, int i, boolean z2) {
        this.zzb = str;
        this.zza = str2;
        this.zzc = z2;
    }

    /* access modifiers changed from: package-private */
    public final String zza() {
        return this.zzb;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzb() {
        return this.zza;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzc() {
        return this.zzc;
    }
}
