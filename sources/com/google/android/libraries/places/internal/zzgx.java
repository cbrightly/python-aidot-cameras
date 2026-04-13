package com.google.android.libraries.places.internal;

import android.content.Context;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgx {
    private final Context zza;

    public zzgx(Context context) {
        zziy.zzc(context, "Context must not be null.");
        this.zza = context;
    }

    public final zzjt zza() {
        String packageName = this.zza.getPackageName();
        String zza2 = zzgm.zza(this.zza.getPackageManager(), packageName);
        zzjs zzjs = new zzjs();
        if (packageName != null) {
            zzjs.zza("X-Android-Package", packageName);
        }
        if (zza2 != null) {
            zzjs.zza("X-Android-Cert", zza2);
        }
        return zzjs.zzb();
    }
}
