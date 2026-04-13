package com.google.android.libraries.places.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzgr {
    public static zzgq zzd(Context context) {
        String packageName = context.getPackageName();
        int i = 0;
        try {
            i = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        zzgn zzgn = new zzgn();
        zzgn.zza(packageName);
        zzgn.zzb(i);
        zzgn.zzd(1);
        return zzgn;
    }

    public abstract int zza();

    public abstract String zzb();

    public abstract int zzc();
}
