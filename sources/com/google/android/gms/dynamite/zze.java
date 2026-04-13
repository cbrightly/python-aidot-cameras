package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zze implements DynamiteModule.VersionPolicy.IVersions {
    zze() {
    }

    public final int zza(Context context, String str) {
        return DynamiteModule.getLocalVersion(context, str);
    }

    public final int zzb(Context context, String str, boolean z) {
        return DynamiteModule.zza(context, str, z);
    }
}
