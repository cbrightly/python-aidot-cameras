package com.google.android.gms.common.util;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;

@ShowFirstParty
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface Clock {

    /* renamed from: com.google.android.gms.common.util.Clock$-CC  reason: invalid class name */
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public final /* synthetic */ class CC {
        @KeepForSdk
        public static long $default$currentThreadTimeMillis(@NonNull Clock clock) {
            return SystemClock.currentThreadTimeMillis();
        }
    }

    @KeepForSdk
    long currentThreadTimeMillis();

    @KeepForSdk
    long currentTimeMillis();

    @KeepForSdk
    long elapsedRealtime();

    @KeepForSdk
    long nanoTime();
}
