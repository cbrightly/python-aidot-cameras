package com.google.android.gms.internal.measurement;

import java.io.Serializable;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzir {
    public static zzim zza(zzim zzim) {
        if ((zzim instanceof zzip) || (zzim instanceof zzin)) {
            return zzim;
        }
        if (zzim instanceof Serializable) {
            return new zzin(zzim);
        }
        return new zzip(zzim);
    }

    public static zzim zzb(Object obj) {
        return new zziq(obj);
    }
}
