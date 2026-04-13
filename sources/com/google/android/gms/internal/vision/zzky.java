package com.google.android.gms.internal.vision;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzky {
    private static final zzky zza = new zzky();
    private final zzlf zzb = new zzkb();
    private final ConcurrentMap<Class<?>, zzlc<?>> zzc = new ConcurrentHashMap();

    public static zzky zza() {
        return zza;
    }

    public final <T> zzlc<T> zza(Class<T> cls) {
        zzjf.zza(cls, "messageType");
        zzlc<T> zzlc = (zzlc) this.zzc.get(cls);
        if (zzlc != null) {
            return zzlc;
        }
        zzlc<T> zza2 = this.zzb.zza(cls);
        zzjf.zza(cls, "messageType");
        zzjf.zza(zza2, "schema");
        zzlc<T> putIfAbsent = this.zzc.putIfAbsent(cls, zza2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        return zza2;
    }

    public final <T> zzlc<T> zza(T t) {
        return zza(t.getClass());
    }

    private zzky() {
    }
}
