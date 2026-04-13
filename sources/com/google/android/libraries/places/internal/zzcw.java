package com.google.android.libraries.places.internal;

import android.os.SystemClock;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzcw {
    private static final zzcn zza = new zzcq();
    private static final zzcw zzb = new zzcw(SystemClock.elapsedRealtime());

    private zzcw() {
        SystemClock.elapsedRealtime();
    }

    private zzcw(long j) {
    }

    public static zzcw zza() {
        return new zzcw(SystemClock.elapsedRealtime());
    }
}
