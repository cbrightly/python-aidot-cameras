package com.google.android.libraries.places.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.AbstractMap;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzkf extends zzjq {
    final /* synthetic */ zzkg zza;

    zzkf(zzkg zzkg) {
        this.zza = zzkg;
    }

    public final /* bridge */ /* synthetic */ Object get(int i) {
        zziy.zza(i, this.zza.zzc, FirebaseAnalytics.Param.INDEX);
        zzkg zzkg = this.zza;
        int i2 = i + i;
        Object obj = zzkg.zzb[i2];
        obj.getClass();
        Object obj2 = zzkg.zzb[i2 + 1];
        obj2.getClass();
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    public final int size() {
        return this.zza.zzc;
    }

    public final boolean zzf() {
        return true;
    }
}
