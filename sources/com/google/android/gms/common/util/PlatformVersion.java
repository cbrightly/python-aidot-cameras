package com.google.android.gms.common.util;

import android.os.Build;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.core.os.BuildCompat;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class PlatformVersion {
    private PlatformVersion() {
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 11)
    public static boolean isAtLeastHoneycomb() {
        return true;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 12)
    public static boolean isAtLeastHoneycombMR1() {
        return true;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 14)
    public static boolean isAtLeastIceCreamSandwich() {
        return true;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 15)
    public static boolean isAtLeastIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= 15;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 16)
    public static boolean isAtLeastJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 17)
    public static boolean isAtLeastJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= 17;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 18)
    public static boolean isAtLeastJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= 18;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 19)
    public static boolean isAtLeastKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 20)
    public static boolean isAtLeastKitKatWatch() {
        return Build.VERSION.SDK_INT >= 20;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 21)
    public static boolean isAtLeastLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 22)
    public static boolean isAtLeastLollipopMR1() {
        return Build.VERSION.SDK_INT >= 22;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 23)
    public static boolean isAtLeastM() {
        return Build.VERSION.SDK_INT >= 23;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 24)
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 26)
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 28)
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 29)
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 30)
    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 31)
    public static boolean isAtLeastS() {
        return Build.VERSION.SDK_INT >= 31;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 32)
    public static boolean isAtLeastSv2() {
        return Build.VERSION.SDK_INT >= 32;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 33)
    public static boolean isAtLeastT() {
        return Build.VERSION.SDK_INT >= 33;
    }

    @KeepForSdk
    @ChecksSdkIntAtLeast(api = 33, codename = "UpsideDownCake")
    public static boolean isAtLeastU() {
        if (!isAtLeastT()) {
            return false;
        }
        return BuildCompat.isAtLeastU();
    }
}
