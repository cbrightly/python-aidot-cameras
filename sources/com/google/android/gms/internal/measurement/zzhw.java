package com.google.android.gms.internal.measurement;

import android.util.Log;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhw extends zzib {
    zzhw(zzhy zzhy, String str, Double d, boolean z) {
        super(zzhy, "measurement.test.double_flag", d, true, (zzia) null);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final /* synthetic */ Object zza(Object obj) {
        try {
            return Double.valueOf(Double.parseDouble((String) obj));
        } catch (NumberFormatException e) {
            String str = this.zzb;
            Log.e("PhenotypeFlag", "Invalid double value for " + str + ": " + ((String) obj));
            return null;
        }
    }
}
