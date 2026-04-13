package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.base.R;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zabw;
import com.google.android.gms.common.api.internal.zabx;
import com.google.android.gms.common.api.internal.zacc;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.zac;
import com.google.android.gms.common.internal.zag;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.android.gms.internal.base.zad;
import com.google.android.gms.internal.base.zae;
import com.google.android.gms.internal.base.zao;
import com.google.android.gms.internal.base.zap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.errorprone.annotations.RestrictedInheritance;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.Arrays;

@RestrictedInheritance(allowedOnPath = ".*java.*/com/google/android/gms.*", allowlistAnnotations = {zad.class, zae.class}, explanation = "Sub classing of GMS Core's APIs are restricted to GMS Core client libs and testing fakes.", link = "go/gmscore-restrictedinheritance")
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class GoogleApiAvailability extends GoogleApiAvailabilityLight {
    @NonNull
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final Object zaa = new Object();
    private static final GoogleApiAvailability zab = new GoogleApiAvailability();
    @GuardedBy("mLock")
    private String zac;

    @NonNull
    public static GoogleApiAvailability getInstance() {
        return zab;
    }

    @NonNull
    public static final Task zai(@NonNull HasApiKey hasApiKey, @NonNull HasApiKey... hasApiKeyArr) {
        Preconditions.checkNotNull(hasApiKey, "Requested API must not be null.");
        for (HasApiKey checkNotNull : hasApiKeyArr) {
            Preconditions.checkNotNull(checkNotNull, "Requested API must not be null.");
        }
        ArrayList arrayList = new ArrayList(hasApiKeyArr.length + 1);
        arrayList.add(hasApiKey);
        arrayList.addAll(Arrays.asList(hasApiKeyArr));
        return GoogleApiManager.zaj().zam(arrayList);
    }

    @NonNull
    public Task<Void> checkApiAvailability(@NonNull GoogleApi<?> api, @NonNull GoogleApi<?>... apis) {
        return zai(api, apis).onSuccessTask(zab.zaa);
    }

    @ShowFirstParty
    @KeepForSdk
    public int getClientVersion(@NonNull Context context) {
        return super.getClientVersion(context);
    }

    @Nullable
    public Dialog getErrorDialog(@NonNull Activity activity, int errorCode, int requestCode) {
        return getErrorDialog(activity, errorCode, requestCode, (DialogInterface.OnCancelListener) null);
    }

    @ShowFirstParty
    @KeepForSdk
    @Nullable
    public Intent getErrorResolutionIntent(@Nullable Context context, int errorCode, @Nullable String trackingSource) {
        return super.getErrorResolutionIntent(context, errorCode, trackingSource);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(@NonNull Context context, int errorCode, int requestCode) {
        return super.getErrorResolutionPendingIntent(context, errorCode, requestCode);
    }

    @NonNull
    public final String getErrorString(int errorCode) {
        return super.getErrorString(errorCode);
    }

    @ResultIgnorabilityUnspecified
    @HideFirstParty
    public int isGooglePlayServicesAvailable(@NonNull Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    public final boolean isUserResolvableError(int errorCode) {
        return super.isUserResolvableError(errorCode);
    }

    @MainThread
    @NonNull
    public Task<Void> makeGooglePlayServicesAvailable(@NonNull Activity activity) {
        int i = GOOGLE_PLAY_SERVICES_VERSION_CODE;
        Preconditions.checkMainThread("makeGooglePlayServicesAvailable must be called from the main thread");
        int isGooglePlayServicesAvailable = isGooglePlayServicesAvailable(activity, i);
        if (isGooglePlayServicesAvailable == 0) {
            return Tasks.forResult(null);
        }
        zacc zaa2 = zacc.zaa(activity);
        zaa2.zah(new ConnectionResult(isGooglePlayServicesAvailable, (PendingIntent) null), 0);
        return zaa2.zad();
    }

    @TargetApi(26)
    public void setDefaultNotificationChannelId(@NonNull Context context, @NonNull String notificationChannelId) {
        if (PlatformVersion.isAtLeastO()) {
            Preconditions.checkNotNull(((NotificationManager) Preconditions.checkNotNull(context.getSystemService("notification"))).getNotificationChannel(notificationChannelId));
        }
        synchronized (zaa) {
            this.zac = notificationChannelId;
        }
    }

    @ResultIgnorabilityUnspecified
    public boolean showErrorDialogFragment(@NonNull Activity activity, int errorCode, int requestCode) {
        return showErrorDialogFragment(activity, errorCode, requestCode, (DialogInterface.OnCancelListener) null);
    }

    public void showErrorNotification(@NonNull Context context, int errorCode) {
        zae(context, errorCode, (String) null, getErrorResolutionPendingIntent(context, errorCode, 0, "n"));
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [android.content.DialogInterface$OnClickListener] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.app.Dialog zaa(@androidx.annotation.NonNull android.content.Context r6, int r7, @androidx.annotation.Nullable com.google.android.gms.common.internal.zag r8, @androidx.annotation.Nullable android.content.DialogInterface.OnCancelListener r9, @androidx.annotation.Nullable android.content.DialogInterface.OnClickListener r10) {
        /*
            r5 = this;
            r0 = 0
            if (r7 != 0) goto L_0x0004
            return r0
        L_0x0004:
            android.util.TypedValue r1 = new android.util.TypedValue
            r1.<init>()
            android.content.res.Resources$Theme r2 = r6.getTheme()
            r3 = 16843529(0x1010309, float:2.3695736E-38)
            r4 = 1
            r2.resolveAttribute(r3, r1, r4)
            android.content.res.Resources r2 = r6.getResources()
            int r1 = r1.resourceId
            java.lang.String r1 = r2.getResourceEntryName(r1)
            java.lang.String r2 = "Theme.Dialog.Alert"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x002d
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            r1 = 5
            r0.<init>(r6, r1)
            goto L_0x002e
        L_0x002d:
        L_0x002e:
            if (r0 != 0) goto L_0x0035
            android.app.AlertDialog$Builder r0 = new android.app.AlertDialog$Builder
            r0.<init>(r6)
        L_0x0035:
            java.lang.String r1 = com.google.android.gms.common.internal.zac.zad(r6, r7)
            r0.setMessage(r1)
            if (r9 == 0) goto L_0x0041
            r0.setOnCancelListener(r9)
        L_0x0041:
            java.lang.String r9 = com.google.android.gms.common.internal.zac.zac(r6, r7)
            if (r9 == 0) goto L_0x004d
            if (r8 != 0) goto L_0x004a
            r8 = r10
        L_0x004a:
            r0.setPositiveButton(r9, r8)
        L_0x004d:
            java.lang.String r6 = com.google.android.gms.common.internal.zac.zag(r6, r7)
            if (r6 == 0) goto L_0x0056
            r0.setTitle(r6)
        L_0x0056:
            java.lang.Object[] r6 = new java.lang.Object[r4]
            r8 = 0
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6[r8] = r7
            java.lang.String r7 = "Creating dialog for Google Play services availability issue. ConnectionResult=%s"
            java.lang.String r6 = java.lang.String.format(r7, r6)
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            r7.<init>()
            java.lang.String r8 = "GoogleApiAvailability"
            android.util.Log.w(r8, r6, r7)
            android.app.AlertDialog r6 = r0.create()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GoogleApiAvailability.zaa(android.content.Context, int, com.google.android.gms.common.internal.zag, android.content.DialogInterface$OnCancelListener, android.content.DialogInterface$OnClickListener):android.app.Dialog");
    }

    @NonNull
    public final Dialog zab(@NonNull Activity activity, @NonNull DialogInterface.OnCancelListener onCancelListener) {
        ProgressBar progressBar = new ProgressBar(activity, (AttributeSet) null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(progressBar);
        builder.setMessage(zac.zad(activity, 18));
        builder.setPositiveButton("", (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        zad(activity, create, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return create;
    }

    @ResultIgnorabilityUnspecified
    @Nullable
    public final zabx zac(Context context, zabw zabw) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        zabx zabx = new zabx(zabw);
        zao.zaa(context, zabx, intentFilter);
        zabx.zaa(context);
        if (isUninstalledAppPossiblyUpdating(context, "com.google.android.gms")) {
            return zabx;
        }
        zabw.zaa();
        zabx.zab();
        return null;
    }

    /* access modifiers changed from: package-private */
    public final void zad(Activity activity, Dialog dialog, String str, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        try {
            if (activity instanceof FragmentActivity) {
                SupportErrorDialogFragment.newInstance(dialog, onCancelListener).show(((FragmentActivity) activity).getSupportFragmentManager(), str);
                return;
            }
        } catch (NoClassDefFoundError e) {
        }
        ErrorDialogFragment.newInstance(dialog, onCancelListener).show(activity.getFragmentManager(), str);
    }

    /* access modifiers changed from: package-private */
    @TargetApi(20)
    public final void zae(Context context, int i, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        int i2;
        String str2;
        Log.w("GoogleApiAvailability", String.format("GMS core API Availability. ConnectionResult=%s, tag=%s", new Object[]{Integer.valueOf(i), null}), new IllegalArgumentException());
        if (i == 18) {
            zaf(context);
        } else if (pendingIntent != null) {
            String zaf = zac.zaf(context, i);
            String zae = zac.zae(context, i);
            Resources resources = context.getResources();
            NotificationManager notificationManager = (NotificationManager) Preconditions.checkNotNull(context.getSystemService("notification"));
            NotificationCompat.Builder style = new NotificationCompat.Builder(context).setLocalOnly(true).setAutoCancel(true).setContentTitle(zaf).setStyle(new NotificationCompat.BigTextStyle().bigText(zae));
            if (DeviceProperties.isWearable(context)) {
                Preconditions.checkState(PlatformVersion.isAtLeastKitKatWatch());
                style.setSmallIcon(context.getApplicationInfo().icon).setPriority(2);
                if (DeviceProperties.isWearableWithoutPlayStore(context)) {
                    style.addAction(R.drawable.common_full_open_on_phone, resources.getString(R.string.common_open_on_phone), pendingIntent);
                } else {
                    style.setContentIntent(pendingIntent);
                }
            } else {
                style.setSmallIcon(17301642).setTicker(resources.getString(R.string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setContentIntent(pendingIntent).setContentText(zae);
            }
            if (PlatformVersion.isAtLeastO()) {
                Preconditions.checkState(PlatformVersion.isAtLeastO());
                synchronized (zaa) {
                    str2 = this.zac;
                }
                if (str2 == null) {
                    str2 = "com.google.android.gms.availability";
                    NotificationChannel notificationChannel = notificationManager.getNotificationChannel(str2);
                    String zab2 = zac.zab(context);
                    if (notificationChannel == null) {
                        notificationManager.createNotificationChannel(new NotificationChannel(str2, zab2, 4));
                    } else if (!zab2.contentEquals(notificationChannel.getName())) {
                        notificationChannel.setName(zab2);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                }
                style.setChannelId(str2);
            }
            Notification build = style.build();
            switch (i) {
                case 1:
                case 2:
                case 3:
                    GooglePlayServicesUtilLight.sCanceledAvailabilityNotification.set(false);
                    i2 = 10436;
                    break;
                default:
                    i2 = 39789;
                    break;
            }
            notificationManager.notify(i2, build);
        } else if (i == 6) {
            Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
        }
    }

    /* access modifiers changed from: package-private */
    public final void zaf(Context context) {
        new zad(this, context).sendEmptyMessageDelayed(1, 120000);
    }

    @ResultIgnorabilityUnspecified
    public final boolean zag(@NonNull Activity activity, @NonNull LifecycleFragment lifecycleFragment, int i, int i2, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        Dialog zaa2 = zaa(activity, i, zag.zad(lifecycleFragment, getErrorResolutionIntent(activity, i, "d"), 2), onCancelListener, (DialogInterface.OnClickListener) null);
        if (zaa2 == null) {
            return false;
        }
        zad(activity, zaa2, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public final boolean zah(@NonNull Context context, @NonNull ConnectionResult connectionResult, int i) {
        PendingIntent errorResolutionPendingIntent;
        if (InstantApps.isInstantApp(context) || (errorResolutionPendingIntent = getErrorResolutionPendingIntent(context, connectionResult)) == null) {
            return false;
        }
        zae(context, connectionResult.getErrorCode(), (String) null, PendingIntent.getActivity(context, 0, GoogleApiActivity.zaa(context, errorResolutionPendingIntent, i, true), zap.zaa | 134217728));
        return true;
    }

    @Nullable
    public Dialog getErrorDialog(@NonNull Activity activity, int errorCode, int requestCode, @Nullable DialogInterface.OnCancelListener cancelListener) {
        return zaa(activity, errorCode, zag.zab(activity, getErrorResolutionIntent(activity, errorCode, "d"), requestCode), cancelListener, (DialogInterface.OnClickListener) null);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(@NonNull Context context, @NonNull ConnectionResult result) {
        if (result.hasResolution()) {
            return result.getResolution();
        }
        return getErrorResolutionPendingIntent(context, result.getErrorCode(), 0);
    }

    @ShowFirstParty
    @KeepForSdk
    public int isGooglePlayServicesAvailable(@NonNull Context context, int minApkVersion) {
        return super.isGooglePlayServicesAvailable(context, minApkVersion);
    }

    @ResultIgnorabilityUnspecified
    public boolean showErrorDialogFragment(@NonNull Activity activity, int errorCode, int requestCode, @Nullable DialogInterface.OnCancelListener cancelListener) {
        Dialog errorDialog = getErrorDialog(activity, errorCode, requestCode, cancelListener);
        if (errorDialog == null) {
            return false;
        }
        zad(activity, errorDialog, GooglePlayServicesUtil.GMS_ERROR_DIALOG, cancelListener);
        return true;
    }

    @NonNull
    public Task<Void> checkApiAvailability(@NonNull HasApiKey<?> api, @NonNull HasApiKey<?>... apis) {
        return zai(api, apis).onSuccessTask(zaa.zaa);
    }

    public void showErrorNotification(@NonNull Context context, @NonNull ConnectionResult result) {
        zae(context, result.getErrorCode(), (String) null, getErrorResolutionPendingIntent(context, result));
    }

    @Nullable
    public Dialog getErrorDialog(@NonNull Fragment fragment, int errorCode, int requestCode) {
        return getErrorDialog(fragment, errorCode, requestCode, (DialogInterface.OnCancelListener) null);
    }

    public boolean showErrorDialogFragment(@NonNull Activity activity, int errorCode, @NonNull ActivityResultLauncher<IntentSenderRequest> activityResultLauncher, @Nullable DialogInterface.OnCancelListener cancelListener) {
        Dialog zaa2 = zaa(activity, errorCode, (zag) null, cancelListener, new zac(this, activity, errorCode, activityResultLauncher));
        if (zaa2 == null) {
            return false;
        }
        zad(activity, zaa2, GooglePlayServicesUtil.GMS_ERROR_DIALOG, cancelListener);
        return true;
    }

    @Nullable
    public Dialog getErrorDialog(@NonNull Fragment fragment, int errorCode, int requestCode, @Nullable DialogInterface.OnCancelListener cancelListener) {
        return zaa(fragment.requireContext(), errorCode, zag.zac(fragment, getErrorResolutionIntent(fragment.requireContext(), errorCode, "d"), requestCode), cancelListener, (DialogInterface.OnClickListener) null);
    }
}
