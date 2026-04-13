package com.google.android.libraries.places.internal;

import com.google.auto.value.AutoValue;

@AutoValue.Builder
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzgq {
    /* access modifiers changed from: package-private */
    public abstract zzgq zzb(int i);

    /* access modifiers changed from: package-private */
    public abstract zzgr zzc();

    public abstract zzgq zzd(int i);

    public final zzgr zze() {
        zzgr zzc = zzc();
        zziy.zzk(!zzc.zzb().isEmpty(), "Package name must not be empty.");
        return zzc;
    }
}
