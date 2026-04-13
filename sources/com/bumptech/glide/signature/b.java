package com.bumptech.glide.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.f;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: ApplicationVersionSignature */
public final class b {
    private static final ConcurrentMap<String, f> a = new ConcurrentHashMap();

    @NonNull
    public static f c(@NonNull Context context) {
        String packageName = context.getPackageName();
        ConcurrentMap<String, f> concurrentMap = a;
        f result = (f) concurrentMap.get(packageName);
        if (result != null) {
            return result;
        }
        f toAdd = d(context);
        f result2 = concurrentMap.putIfAbsent(packageName, toAdd);
        if (result2 == null) {
            return toAdd;
        }
        return result2;
    }

    @NonNull
    private static f d(@NonNull Context context) {
        return new d(b(a(context)));
    }

    @NonNull
    private static String b(@Nullable PackageInfo packageInfo) {
        if (packageInfo != null) {
            return String.valueOf(packageInfo.versionCode);
        }
        return UUID.randomUUID().toString();
    }

    @Nullable
    private static PackageInfo a(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AppVersionSignature", "Cannot resolve info for" + context.getPackageName(), e);
            return null;
        }
    }
}
