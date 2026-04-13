package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.zag;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class GooglePlayServicesUtil extends GooglePlayServicesUtilLight {
    @NonNull
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @NonNull
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    @NonNull
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    @Nullable
    public static Dialog getErrorDialog(int errorCode, @NonNull Activity activity, int requestCode) {
        return getErrorDialog(errorCode, activity, requestCode, (DialogInterface.OnCancelListener) null);
    }

    @NonNull
    @Deprecated
    public static PendingIntent getErrorPendingIntent(int errorCode, @NonNull Context context, int requestCode) {
        return GooglePlayServicesUtilLight.getErrorPendingIntent(errorCode, context, requestCode);
    }

    @NonNull
    @Deprecated
    @VisibleForTesting
    public static String getErrorString(int errorCode) {
        return GooglePlayServicesUtilLight.getErrorString(errorCode);
    }

    @NonNull
    public static Context getRemoteContext(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getRemoteContext(context);
    }

    @NonNull
    public static Resources getRemoteResource(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getRemoteResource(context);
    }

    @ResultIgnorabilityUnspecified
    @Deprecated
    @HideFirstParty
    public static int isGooglePlayServicesAvailable(@NonNull Context context) {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int errorCode) {
        return GooglePlayServicesUtilLight.isUserRecoverableError(errorCode);
    }

    @ResultIgnorabilityUnspecified
    @Deprecated
    public static boolean showErrorDialogFragment(int errorCode, @NonNull Activity activity, int requestCode) {
        return showErrorDialogFragment(errorCode, activity, requestCode, (DialogInterface.OnCancelListener) null);
    }

    @Deprecated
    public static void showErrorNotification(int errorCode, @NonNull Context context) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, errorCode) || GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(context, errorCode)) {
            instance.zaf(context);
        } else {
            instance.showErrorNotification(context, errorCode);
        }
    }

    @Deprecated
    @Nullable
    public static Dialog getErrorDialog(int errorCode, @NonNull Activity activity, int requestCode, @Nullable DialogInterface.OnCancelListener cancelListener) {
        if (true == GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(activity, errorCode)) {
            errorCode = 18;
        }
        return GoogleApiAvailability.getInstance().getErrorDialog(activity, errorCode, requestCode, cancelListener);
    }

    @KeepForSdk
    @Deprecated
    public static int isGooglePlayServicesAvailable(@NonNull Context context, int minApkVersion) {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context, minApkVersion);
    }

    @ResultIgnorabilityUnspecified
    @Deprecated
    public static boolean showErrorDialogFragment(int errorCode, @NonNull Activity activity, int requestCode, @Nullable DialogInterface.OnCancelListener cancelListener) {
        return showErrorDialogFragment(errorCode, activity, (Fragment) null, requestCode, cancelListener);
    }

    @ResultIgnorabilityUnspecified
    public static boolean showErrorDialogFragment(int errorCode, @NonNull Activity activity, @Nullable Fragment fragment, int requestCode, @Nullable DialogInterface.OnCancelListener cancelListener) {
        if (true == GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(activity, errorCode)) {
            errorCode = 18;
        }
        int i = errorCode;
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        if (fragment == null) {
            return instance.showErrorDialogFragment(activity, i, requestCode, cancelListener);
        }
        Dialog zaa = instance.zaa(activity, i, zag.zac(fragment, GoogleApiAvailability.getInstance().getErrorResolutionIntent(activity, i, "d"), requestCode), cancelListener, (DialogInterface.OnClickListener) null);
        if (zaa == null) {
            return false;
        }
        instance.zad(activity, zaa, GMS_ERROR_DIALOG, cancelListener);
        return true;
    }
}
