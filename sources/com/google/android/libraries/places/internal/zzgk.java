package com.google.android.libraries.places.internal;

import androidx.annotation.Nullable;
import java.util.Locale;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgk {
    @Nullable
    private volatile String zza;
    @Nullable
    private volatile Locale zzb;
    private volatile boolean zzc;

    public final synchronized String zza() {
        zziy.zzk(zzf(), "ApiConfig must be initialized.");
        if (this.zza != null) {
        } else {
            throw null;
        }
        return this.zza;
    }

    public final synchronized Locale zzb() {
        zziy.zzk(zzf(), "ApiConfig must be initialized.");
        return this.zzb == null ? Locale.getDefault() : this.zzb;
    }

    public final synchronized void zzc() {
        this.zza = null;
        this.zzb = null;
    }

    public final synchronized void zzd(String str, @Nullable Locale locale, boolean z) {
        zziy.zzc(str, "API Key must not be null.");
        zziy.zze(!str.isEmpty(), "API Key must not be empty.");
        this.zza = str;
        this.zzb = locale;
        this.zzc = false;
    }

    public final boolean zze() {
        return false;
    }

    public final synchronized boolean zzf() {
        return this.zza != null;
    }
}
