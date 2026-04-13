package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.wrappers.Wrappers;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class ClientLibraryUtils {
    private ClientLibraryUtils() {
    }

    @KeepForSdk
    public static int getClientVersion(@NonNull Context context, @NonNull String packageName) {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null || (bundle = applicationInfo.metaData) == null) {
            return -1;
        }
        return bundle.getInt("com.google.android.gms.version", -1);
    }

    @KeepForSdk
    @Nullable
    public static PackageInfo getPackageInfo(@NonNull Context context, @NonNull String packageName) {
        try {
            return Wrappers.packageManager(context).getPackageInfo(packageName, 128);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @KeepForSdk
    public static boolean isPackageSide() {
        return false;
    }
}
