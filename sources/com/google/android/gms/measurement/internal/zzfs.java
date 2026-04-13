package com.google.android.gms.measurement.internal;

import androidx.collection.LruCache;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfs extends LruCache {
    final /* synthetic */ zzfv zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzfs(zzfv zzfv, int i) {
        super(20);
        this.zza = zzfv;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ Object create(Object obj) {
        String str = (String) obj;
        Preconditions.checkNotEmpty(str);
        return zzfv.zzd(this.zza, str);
    }
}
