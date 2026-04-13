package com.google.android.gms.internal.base;

import android.os.Build;
import androidx.annotation.ChecksSdkIntAtLeast;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class zan {
    @ChecksSdkIntAtLeast(api = 33, codename = "Tiramisu")
    static boolean zaa() {
        return Build.VERSION.SDK_INT >= 33 || Build.VERSION.CODENAME.charAt(0) == 'T';
    }
}
