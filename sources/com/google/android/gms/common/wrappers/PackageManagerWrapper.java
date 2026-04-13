package com.google.android.gms.common.wrappers;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Process;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class PackageManagerWrapper {
    @NonNull
    protected final Context zza;

    public PackageManagerWrapper(@NonNull Context context) {
        this.zza = context;
    }

    @KeepForSdk
    public int checkCallingOrSelfPermission(@NonNull String permission) {
        return this.zza.checkCallingOrSelfPermission(permission);
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public int checkPermission(@NonNull String permission, @NonNull String packageName) {
        return this.zza.getPackageManager().checkPermission(permission, packageName);
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public ApplicationInfo getApplicationInfo(@NonNull String packageName, int flags) {
        return this.zza.getPackageManager().getApplicationInfo(packageName, flags);
    }

    @NonNull
    @KeepForSdk
    public CharSequence getApplicationLabel(@NonNull String packageName) {
        return this.zza.getPackageManager().getApplicationLabel(this.zza.getPackageManager().getApplicationInfo(packageName, 0));
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public Pair<CharSequence, Drawable> getApplicationLabelAndIcon(@NonNull String packageName) {
        ApplicationInfo applicationInfo = this.zza.getPackageManager().getApplicationInfo(packageName, 0);
        return Pair.create(this.zza.getPackageManager().getApplicationLabel(applicationInfo), this.zza.getPackageManager().getApplicationIcon(applicationInfo));
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public PackageInfo getPackageInfo(@NonNull String packageName, int flags) {
        return this.zza.getPackageManager().getPackageInfo(packageName, flags);
    }

    @KeepForSdk
    public boolean isCallerInstantApp() {
        String nameForUid;
        if (Binder.getCallingUid() == Process.myUid()) {
            return InstantApps.isInstantApp(this.zza);
        }
        if (!PlatformVersion.isAtLeastO() || (nameForUid = this.zza.getPackageManager().getNameForUid(Binder.getCallingUid())) == null) {
            return false;
        }
        return this.zza.getPackageManager().isInstantApp(nameForUid);
    }

    @TargetApi(19)
    public final boolean zza(int i, @NonNull String str) {
        if (PlatformVersion.isAtLeastKitKat()) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) this.zza.getSystemService("appops");
                if (appOpsManager != null) {
                    appOpsManager.checkPackage(i, str);
                    return true;
                }
                throw new NullPointerException("context.getSystemService(Context.APP_OPS_SERVICE) is null");
            } catch (SecurityException e) {
                return false;
            }
        } else {
            String[] packagesForUid = this.zza.getPackageManager().getPackagesForUid(i);
            if (!(str == null || packagesForUid == null)) {
                for (String equals : packagesForUid) {
                    if (str.equals(equals)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
