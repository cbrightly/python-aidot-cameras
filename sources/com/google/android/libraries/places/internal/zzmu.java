package com.google.android.libraries.places.internal;

import java.io.Closeable;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmu implements Closeable {
    private static final ThreadLocal zza = new zzmt();
    private int zzb = 0;

    public static int zza() {
        return ((zzmu) zza.get()).zzb;
    }

    public final void close() {
        int i = this.zzb;
        if (i > 0) {
            this.zzb = i - 1;
            return;
        }
        throw new AssertionError("Mismatched calls to RecursionDepth (possible error in core library)");
    }
}
