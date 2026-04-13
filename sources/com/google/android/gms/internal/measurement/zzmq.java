package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzmq {
    private static final zzmq zza = new zzmq();
    private final zzmu zzb = new zzma();
    private final ConcurrentMap zzc = new ConcurrentHashMap();

    private zzmq() {
    }

    public static zzmq zza() {
        return zza;
    }

    public final zzmt zzb(Class cls) {
        zzlj.zzc(cls, "messageType");
        zzmt zzmt = (zzmt) this.zzc.get(cls);
        if (zzmt == null) {
            zzmt = this.zzb.zza(cls);
            zzlj.zzc(cls, "messageType");
            zzlj.zzc(zzmt, "schema");
            zzmt zzmt2 = (zzmt) this.zzc.putIfAbsent(cls, zzmt);
            return zzmt2 == null ? zzmt : zzmt2;
        }
    }
}
