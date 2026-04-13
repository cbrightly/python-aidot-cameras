package com.google.android.libraries.places.internal;

import android.graphics.Bitmap;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzek {
    private Bitmap zza;

    public final zzem zza() {
        zziy.zzk(this.zza != null, "Photo must be set to non-null value.");
        return new zzem(this.zza, (zzel) null);
    }

    public final zzek zzb(Bitmap bitmap) {
        this.zza = bitmap;
        return this;
    }
}
