package com.google.android.libraries.places.internal;

import com.google.firebase.analytics.FirebaseAnalytics;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzki extends zzjq {
    private final transient Object[] zza;
    private final transient int zzb;
    private final transient int zzc;

    zzki(Object[] objArr, int i, int i2) {
        this.zza = objArr;
        this.zzb = i;
        this.zzc = i2;
    }

    public final Object get(int i) {
        zziy.zza(i, this.zzc, FirebaseAnalytics.Param.INDEX);
        Object obj = this.zza[i + i + this.zzb];
        obj.getClass();
        return obj;
    }

    public final int size() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzf() {
        return true;
    }
}
