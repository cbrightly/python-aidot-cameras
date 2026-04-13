package com.google.android.gms.common.stats;

import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
@Deprecated
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class StatsUtils {
    @NonNull
    @KeepForSdk
    public static String getEventKey(@NonNull PowerManager.WakeLock wakeLock, @NonNull String secondaryName) {
        String valueOf = String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(wakeLock)));
        if (true == TextUtils.isEmpty(secondaryName)) {
            secondaryName = "";
        }
        return String.valueOf(valueOf).concat(String.valueOf(secondaryName));
    }
}
