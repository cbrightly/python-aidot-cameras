package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.common.zzd;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@ShowFirstParty
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class GoogleApiAvailabilityLight {
    @NonNull
    @KeepForSdk
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @KeepForSdk
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    @NonNull
    @KeepForSdk
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    @KeepForSdk
    static final String TRACKING_SOURCE_DIALOG = "d";
    @KeepForSdk
    static final String TRACKING_SOURCE_NOTIFICATION = "n";
    private static final GoogleApiAvailabilityLight zza = new GoogleApiAvailabilityLight();

    @KeepForSdk
    GoogleApiAvailabilityLight() {
    }

    @NonNull
    @KeepForSdk
    public static GoogleApiAvailabilityLight getInstance() {
        return zza;
    }

    @KeepForSdk
    public void cancelAvailabilityErrorNotifications(@NonNull Context context) {
        GooglePlayServicesUtilLight.cancelAvailabilityErrorNotifications(context);
    }

    @ShowFirstParty
    @KeepForSdk
    public int getApkVersion(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getApkVersion(context);
    }

    @ShowFirstParty
    @KeepForSdk
    public int getClientVersion(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getClientVersion(context);
    }

    @ShowFirstParty
    @Nullable
    @KeepForSdk
    @Deprecated
    public Intent getErrorResolutionIntent(int errorCode) {
        return getErrorResolutionIntent((Context) null, errorCode, (String) null);
    }

    @KeepForSdk
    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(@NonNull Context context, int errorCode, int requestCode) {
        return getErrorResolutionPendingIntent(context, errorCode, requestCode, (String) null);
    }

    @NonNull
    @KeepForSdk
    public String getErrorString(int errorCode) {
        return GooglePlayServicesUtilLight.getErrorString(errorCode);
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    @HideFirstParty
    public int isGooglePlayServicesAvailable(@NonNull Context context) {
        return isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPlayServicesPossiblyUpdating(@NonNull Context context, int errorCode) {
        return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, errorCode);
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPlayStorePossiblyUpdating(@NonNull Context context, int errorCode) {
        return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(context, errorCode);
    }

    @KeepForSdk
    public boolean isUninstalledAppPossiblyUpdating(@NonNull Context context, @NonNull String packageName) {
        return GooglePlayServicesUtilLight.zza(context, packageName);
    }

    @KeepForSdk
    public boolean isUserResolvableError(int errorCode) {
        return GooglePlayServicesUtilLight.isUserRecoverableError(errorCode);
    }

    @KeepForSdk
    public void verifyGooglePlayServicesIsAvailable(@NonNull Context context, int minApkVersion) {
        GooglePlayServicesUtilLight.ensurePlayServicesAvailable(context, minApkVersion);
    }

    @ShowFirstParty
    @KeepForSdk
    @Nullable
    public Intent getErrorResolutionIntent(@Nullable Context context, int errorCode, @Nullable String trackingSource) {
        switch (errorCode) {
            case 1:
            case 2:
                if (context == null || !DeviceProperties.isWearableWithoutPlayStore(context)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("gcore_");
                    sb.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
                    sb.append("-");
                    if (!TextUtils.isEmpty(trackingSource)) {
                        sb.append(trackingSource);
                    }
                    sb.append("-");
                    if (context != null) {
                        sb.append(context.getPackageName());
                    }
                    sb.append("-");
                    if (context != null) {
                        try {
                            sb.append(Wrappers.packageManager(context).getPackageInfo(context.getPackageName(), 0).versionCode);
                        } catch (PackageManager.NameNotFoundException e) {
                        }
                    }
                    String sb2 = sb.toString();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    Uri.Builder appendQueryParameter = Uri.parse("market://details").buildUpon().appendQueryParameter("id", "com.google.android.gms");
                    if (!TextUtils.isEmpty(sb2)) {
                        appendQueryParameter.appendQueryParameter("pcampaignid", sb2);
                    }
                    intent.setData(appendQueryParameter.build());
                    intent.setPackage("com.android.vending");
                    intent.addFlags(524288);
                    return intent;
                }
                Intent intent2 = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
                intent2.setPackage("com.google.android.wearable.app");
                return intent2;
            case 3:
                Uri fromParts = Uri.fromParts("package", "com.google.android.gms", (String) null);
                Intent intent3 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent3.setData(fromParts);
                return intent3;
            default:
                return null;
        }
    }

    @ShowFirstParty
    @KeepForSdk
    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(@NonNull Context context, int errorCode, int requestCode, @Nullable String trackingSource) {
        Intent errorResolutionIntent = getErrorResolutionIntent(context, errorCode, trackingSource);
        if (errorResolutionIntent == null) {
            return null;
        }
        return PendingIntent.getActivity(context, requestCode, errorResolutionIntent, zzd.zza | 134217728);
    }

    @KeepForSdk
    public int isGooglePlayServicesAvailable(@NonNull Context context, int minApkVersion) {
        int minApkVersion2 = GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context, minApkVersion);
        if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, minApkVersion2)) {
            return 18;
        }
        return minApkVersion2;
    }
}
