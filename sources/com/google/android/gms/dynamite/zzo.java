package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzo implements DynamiteModule.VersionPolicy.IVersions {
    private final int zza;

    public zzo(int i, int i2) {
        this.zza = i;
    }

    public final int zza(Context context, String str) {
        return this.zza;
    }

    public final int zzb(Context context, String str, boolean z) {
        return 0;
    }
}
