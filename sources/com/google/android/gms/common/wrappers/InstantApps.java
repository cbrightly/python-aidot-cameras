package com.google.android.gms.common.wrappers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class InstantApps {
    private static Context zza;
    @Nullable
    private static Boolean zzb;

    @KeepForSdk
    public static synchronized boolean isInstantApp(@NonNull Context context) {
        Boolean bool;
        synchronized (InstantApps.class) {
            Context applicationContext = context.getApplicationContext();
            Context context2 = zza;
            if (!(context2 == null || (bool = zzb) == null)) {
                if (context2 == applicationContext) {
                    boolean booleanValue = bool.booleanValue();
                    return booleanValue;
                }
            }
            zzb = null;
            if (PlatformVersion.isAtLeastO()) {
                zzb = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
            } else {
                try {
                    context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                    zzb = true;
                } catch (ClassNotFoundException e) {
                    zzb = false;
                }
            }
            zza = applicationContext;
            boolean booleanValue2 = zzb.booleanValue();
            return booleanValue2;
        }
    }
}
