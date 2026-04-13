package com.google.android.libraries.places.internal;

import java.util.Collections;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzmq {
    /* access modifiers changed from: private */
    public static final Comparator zza = new zzmi();
    private static final Comparator zzb = new zzmj();
    private static final zzmq zzc = new zzmq(new zzmo(Collections.emptyList()));
    private final zzmo zzd;

    private zzmq(zzmo zzmo) {
        this.zzd = zzmo;
    }

    public static zzmq zza() {
        return zzc;
    }

    public final boolean equals(@NullableDecl Object obj) {
        return (obj instanceof zzmq) && ((zzmq) obj).zzd.equals(this.zzd);
    }

    public final int hashCode() {
        return ~this.zzd.hashCode();
    }

    public final String toString() {
        return this.zzd.toString();
    }
}
