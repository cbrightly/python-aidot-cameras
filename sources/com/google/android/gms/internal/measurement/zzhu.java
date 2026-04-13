package com.google.android.gms.internal.measurement;

import android.util.Log;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhu extends zzib {
    zzhu(zzhy zzhy, String str, Long l, boolean z) {
        super(zzhy, str, l, true, (zzia) null);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object zza(Object obj) {
        try {
            return Long.valueOf(Long.parseLong((String) obj));
        } catch (NumberFormatException e) {
            String str = this.zzb;
            Log.e("PhenotypeFlag", "Invalid long value for " + str + ": " + ((String) obj));
            return null;
        }
    }
}
