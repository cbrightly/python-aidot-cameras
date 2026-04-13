package com.google.android.gms.measurement.internal;

import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zziq {
    @Nullable
    public final String zza;
    @Nullable
    public final String zzb;
    public final long zzc;
    boolean zzd;
    public final boolean zze;
    public final long zzf;

    public zziq(@Nullable String str, @Nullable String str2, long j) {
        this(str, str2, j, false, 0);
    }

    public zziq(@Nullable String str, @Nullable String str2, long j, boolean z, long j2) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = j;
        this.zzd = false;
        this.zze = z;
        this.zzf = j2;
    }
}
