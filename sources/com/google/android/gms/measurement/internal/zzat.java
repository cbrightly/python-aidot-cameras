package com.google.android.gms.measurement.internal;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzat implements Iterator {
    final Iterator zza;
    final /* synthetic */ zzau zzb;

    zzat(zzau zzau) {
        this.zzb = zzau;
        this.zza = zzau.zza.keySet().iterator();
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }

    /* renamed from: zza */
    public final String next() {
        return (String) this.zza.next();
    }
}
