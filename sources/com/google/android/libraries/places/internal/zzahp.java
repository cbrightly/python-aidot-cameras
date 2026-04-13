package com.google.android.libraries.places.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzahp {
    private static final zzahp zza = new zzahp();
    private final zzaht zzb = new zzagz();
    private final ConcurrentMap zzc = new ConcurrentHashMap();

    private zzahp() {
    }

    public static zzahp zza() {
        return zza;
    }

    public final zzahs zzb(Class cls) {
        zzagi.zzc(cls, "messageType");
        zzahs zzahs = (zzahs) this.zzc.get(cls);
        if (zzahs == null) {
            zzahs = this.zzb.zza(cls);
            zzagi.zzc(cls, "messageType");
            zzagi.zzc(zzahs, "schema");
            zzahs zzahs2 = (zzahs) this.zzc.putIfAbsent(cls, zzahs);
            return zzahs2 == null ? zzahs : zzahs2;
        }
    }
}
