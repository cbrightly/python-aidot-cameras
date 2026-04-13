package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.google.firebase.messaging.Constants;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.concurrent.atomic.AtomicInteger;
import net.sqlcipher.database.SQLiteDatabase;

public final class CommonNotificationBuilder {
    private static final String ACTION_MESSAGING_EVENT = "com.google.firebase.MESSAGING_EVENT";
    public static final String FCM_FALLBACK_NOTIFICATION_CHANNEL = "fcm_fallback_notification_channel";
    public static final String FCM_FALLBACK_NOTIFICATION_CHANNEL_LABEL = "fcm_fallback_notification_channel_label";
    private static final String FCM_FALLBACK_NOTIFICATION_CHANNEL_NAME_NO_RESOURCE = "Misc";
    private static final int ILLEGAL_RESOURCE_ID = 0;
    public static final String METADATA_DEFAULT_CHANNEL_ID = "com.google.firebase.messaging.default_notification_channel_id";
    public static final String METADATA_DEFAULT_COLOR = "com.google.firebase.messaging.default_notification_color";
    public static final String METADATA_DEFAULT_ICON = "com.google.firebase.messaging.default_notification_icon";
    private static final AtomicInteger requestCodeProvider = new AtomicInteger((int) SystemClock.elapsedRealtime());

    private CommonNotificationBuilder() {
    }

    static DisplayNotificationInfo createNotificationInfo(Context context, NotificationParams params) {
        Bundle manifestMetadata = getManifestMetadata(context.getPackageManager(), context.getPackageName());
        return createNotificationInfo(context, context, params, getOrCreateChannel(context, params.getNotificationChannelId(), manifestMetadata), manifestMetadata);
    }

    public static DisplayNotificationInfo createNotificationInfo(Context callingContext, Context appContext, NotificationParams params, String channelId, Bundle manifestMetadata) {
        Context context = appContext;
        NotificationParams notificationParams = params;
        Bundle bundle = manifestMetadata;
        String pkgName = appContext.getPackageName();
        Resources appResources = appContext.getResources();
        PackageManager appPackageManager = appContext.getPackageManager();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        String title = notificationParams.getPossiblyLocalizedString(appResources, pkgName, Constants.MessageNotificationKeys.TITLE);
        if (!TextUtils.isEmpty(title)) {
            builder.setContentTitle(title);
        }
        String body = notificationParams.getPossiblyLocalizedString(appResources, pkgName, Constants.MessageNotificationKeys.BODY);
        if (!TextUtils.isEmpty(body)) {
            builder.setContentText(body);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        }
        builder.setSmallIcon(getSmallIcon(appPackageManager, appResources, pkgName, notificationParams.getString(Constants.MessageNotificationKeys.ICON), bundle));
        Uri sound = getSound(pkgName, notificationParams, appResources);
        if (sound != null) {
            builder.setSound(sound);
        }
        builder.setContentIntent(createContentIntent(callingContext, notificationParams, pkgName, appPackageManager));
        PendingIntent deleteIntent = createDeleteIntent(callingContext, appContext, params);
        if (deleteIntent != null) {
            builder.setDeleteIntent(deleteIntent);
        }
        Integer color = getColor(context, notificationParams.getString(Constants.MessageNotificationKeys.COLOR), bundle);
        if (color != null) {
            builder.setColor(color.intValue());
        }
        builder.setAutoCancel(!notificationParams.getBoolean(Constants.MessageNotificationKeys.STICKY));
        boolean localOnly = notificationParams.getBoolean(Constants.MessageNotificationKeys.LOCAL_ONLY);
        builder.setLocalOnly(localOnly);
        boolean z = localOnly;
        String ticker = notificationParams.getString(Constants.MessageNotificationKeys.TICKER);
        if (ticker != null) {
            builder.setTicker(ticker);
        }
        Integer notificationPriority = params.getNotificationPriority();
        if (notificationPriority != null) {
            String str = ticker;
            builder.setPriority(notificationPriority.intValue());
        }
        Integer visibility = params.getVisibility();
        if (visibility != null) {
            builder.setVisibility(visibility.intValue());
        }
        Integer notificationCount = params.getNotificationCount();
        if (notificationCount != null) {
            Integer num = visibility;
            builder.setNumber(notificationCount.intValue());
        }
        Long eventTime = notificationParams.getLong(Constants.MessageNotificationKeys.EVENT_TIME);
        if (eventTime != null) {
            builder.setShowWhen(true);
            Integer num2 = notificationCount;
            builder.setWhen(eventTime.longValue());
        }
        long[] vibrateTimings = params.getVibrateTimings();
        if (vibrateTimings != null) {
            builder.setVibrate(vibrateTimings);
        }
        int[] lightSettings = params.getLightSettings();
        Long l = eventTime;
        if (lightSettings != null) {
            long[] jArr = vibrateTimings;
            String str2 = pkgName;
            builder.setLights(lightSettings[0], lightSettings[1], lightSettings[2]);
        } else {
            String str3 = pkgName;
        }
        builder.setDefaults(getConsolidatedDefaults(params));
        return new DisplayNotificationInfo(builder, getTag(params), 0);
    }

    private static int getConsolidatedDefaults(NotificationParams params) {
        int result = 0;
        if (params.getBoolean(Constants.MessageNotificationKeys.DEFAULT_SOUND)) {
            result = 0 | 1;
        }
        if (params.getBoolean(Constants.MessageNotificationKeys.DEFAULT_VIBRATE_TIMINGS)) {
            result |= 2;
        }
        if (params.getBoolean(Constants.MessageNotificationKeys.DEFAULT_LIGHT_SETTINGS)) {
            return result | 4;
        }
        return result;
    }

    @TargetApi(26)
    private static boolean isValidIcon(Resources resources, int resId) {
        if (Build.VERSION.SDK_INT != 26) {
            return true;
        }
        try {
            if (!(resources.getDrawable(resId, (Resources.Theme) null) instanceof AdaptiveIconDrawable)) {
                return true;
            }
            Log.e(Constants.TAG, "Adaptive icons cannot be used in notifications. Ignoring icon id: " + resId);
            return false;
        } catch (Resources.NotFoundException e) {
            Log.e(Constants.TAG, "Couldn't find resource " + resId + ", treating it as an invalid icon");
            return false;
        }
    }

    private static int getSmallIcon(PackageManager packageManager, Resources resources, String pkgName, String resourceKey, Bundle manifestMetadata) {
        if (!TextUtils.isEmpty(resourceKey)) {
            int iconId = resources.getIdentifier(resourceKey, "drawable", pkgName);
            if (iconId != 0 && isValidIcon(resources, iconId)) {
                return iconId;
            }
            int iconId2 = resources.getIdentifier(resourceKey, "mipmap", pkgName);
            if (iconId2 != 0 && isValidIcon(resources, iconId2)) {
                return iconId2;
            }
            Log.w(Constants.TAG, "Icon resource " + resourceKey + " not found. Notification will use default icon.");
        }
        int iconId3 = manifestMetadata.getInt(METADATA_DEFAULT_ICON, 0);
        if (iconId3 == 0 || !isValidIcon(resources, iconId3)) {
            try {
                iconId3 = packageManager.getApplicationInfo(pkgName, 0).icon;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w(Constants.TAG, "Couldn't get own application info: " + e);
            }
        }
        if (iconId3 == 0 || !isValidIcon(resources, iconId3)) {
            return 17301651;
        }
        return iconId3;
    }

    private static Integer getColor(Context context, String color, Bundle manifestMetadata) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        if (!TextUtils.isEmpty(color)) {
            try {
                return Integer.valueOf(Color.parseColor(color));
            } catch (IllegalArgumentException e) {
                Log.w(Constants.TAG, "Color is invalid: " + color + ". Notification will use default color.");
            }
        }
        int colorResourceId = manifestMetadata.getInt(METADATA_DEFAULT_COLOR, 0);
        if (colorResourceId != 0) {
            try {
                return Integer.valueOf(ContextCompat.getColor(context, colorResourceId));
            } catch (Resources.NotFoundException e2) {
                Log.w(Constants.TAG, "Cannot find the color resource referenced in AndroidManifest.");
            }
        }
        return null;
    }

    private static Uri getSound(String pkgName, NotificationParams params, Resources resources) {
        String soundName = params.getSoundResourceName();
        if (TextUtils.isEmpty(soundName)) {
            return null;
        }
        if ("default".equals(soundName) || resources.getIdentifier(soundName, "raw", pkgName) == 0) {
            return RingtoneManager.getDefaultUri(2);
        }
        return Uri.parse("android.resource://" + pkgName + "/raw/" + soundName);
    }

    @Nullable
    private static PendingIntent createContentIntent(Context context, NotificationParams params, String pkgName, PackageManager pm) {
        Intent intent = createTargetIntent(pkgName, params, pm);
        if (intent == null) {
            return null;
        }
        intent.addFlags(67108864);
        intent.putExtras(params.paramsWithReservedKeysRemoved());
        if (shouldUploadMetrics(params)) {
            intent.putExtra(Constants.MessageNotificationKeys.ANALYTICS_DATA, params.paramsForAnalyticsIntent());
        }
        int generatePendingIntentRequestCode = generatePendingIntentRequestCode();
        int pendingIntentFlags = getPendingIntentFlags(1073741824);
        PushAutoTrackHelper.hookIntentGetActivity(context, generatePendingIntentRequestCode, intent, pendingIntentFlags);
        PendingIntent activity = PendingIntent.getActivity(context, generatePendingIntentRequestCode, intent, pendingIntentFlags);
        PushAutoTrackHelper.hookPendingIntentGetActivity(activity, context, generatePendingIntentRequestCode, intent, pendingIntentFlags);
        return activity;
    }

    private static Intent createTargetIntent(String pkgName, NotificationParams params, PackageManager pm) {
        String action = params.getString(Constants.MessageNotificationKeys.CLICK_ACTION);
        if (!TextUtils.isEmpty(action)) {
            Intent intent = new Intent(action);
            intent.setPackage(pkgName);
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            return intent;
        }
        Uri link = params.getLink();
        if (link != null) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setPackage(pkgName);
            intent2.setData(link);
            return intent2;
        }
        Intent intent3 = pm.getLaunchIntentForPackage(pkgName);
        if (intent3 == null) {
            Log.w(Constants.TAG, "No activity found to launch app");
        }
        return intent3;
    }

    private static Bundle getManifestMetadata(PackageManager pm, String packageName) {
        Bundle bundle;
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 128);
            if (!(info == null || (bundle = info.metaData) == null)) {
                return bundle;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(Constants.TAG, "Couldn't get own application info: " + e);
        }
        return Bundle.EMPTY;
    }

    @VisibleForTesting
    @TargetApi(26)
    public static String getOrCreateChannel(Context context, String msgChannel, Bundle manifestMetadata) {
        String defaultChannelName;
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).targetSdkVersion < 26) {
                return null;
            }
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
            if (!TextUtils.isEmpty(msgChannel)) {
                if (notificationManager.getNotificationChannel(msgChannel) != null) {
                    return msgChannel;
                }
                Log.w(Constants.TAG, "Notification Channel requested (" + msgChannel + ") has not been created by the app. Manifest configuration, or default, value will be used.");
            }
            String manifestChannel = manifestMetadata.getString(METADATA_DEFAULT_CHANNEL_ID);
            if (TextUtils.isEmpty(manifestChannel)) {
                Log.w(Constants.TAG, "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
            } else if (notificationManager.getNotificationChannel(manifestChannel) != null) {
                return manifestChannel;
            } else {
                Log.w(Constants.TAG, "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
            }
            if (notificationManager.getNotificationChannel(FCM_FALLBACK_NOTIFICATION_CHANNEL) == null) {
                int channelLabelResourceId = context.getResources().getIdentifier(FCM_FALLBACK_NOTIFICATION_CHANNEL_LABEL, TypedValues.Custom.S_STRING, context.getPackageName());
                if (channelLabelResourceId == 0) {
                    Log.e(Constants.TAG, "String resource \"fcm_fallback_notification_channel_label\" is not found. Using default string channel name.");
                    defaultChannelName = FCM_FALLBACK_NOTIFICATION_CHANNEL_NAME_NO_RESOURCE;
                } else {
                    defaultChannelName = context.getString(channelLabelResourceId);
                }
                notificationManager.createNotificationChannel(new NotificationChannel(FCM_FALLBACK_NOTIFICATION_CHANNEL, defaultChannelName, 3));
            }
            return FCM_FALLBACK_NOTIFICATION_CHANNEL;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static int generatePendingIntentRequestCode() {
        return requestCodeProvider.incrementAndGet();
    }

    private static int getPendingIntentFlags(int baseFlags) {
        if (Build.VERSION.SDK_INT >= 23) {
            return 67108864 | baseFlags;
        }
        return baseFlags;
    }

    @Nullable
    private static PendingIntent createDeleteIntent(Context callingContext, Context appContext, NotificationParams params) {
        if (!shouldUploadMetrics(params)) {
            return null;
        }
        return createMessagingPendingIntent(callingContext, appContext, new Intent(CloudMessagingReceiver.IntentActionKeys.NOTIFICATION_DISMISS).putExtras(params.paramsForAnalyticsIntent()));
    }

    private static PendingIntent createMessagingPendingIntent(Context callingContext, Context appContext, Intent intent) {
        int generatePendingIntentRequestCode = generatePendingIntentRequestCode();
        Intent putExtra = new Intent(ACTION_MESSAGING_EVENT).setComponent(new ComponentName(appContext, "com.google.firebase.iid.FirebaseInstanceIdReceiver")).putExtra(CloudMessagingReceiver.IntentKeys.WRAPPED_INTENT, intent);
        int pendingIntentFlags = getPendingIntentFlags(1073741824);
        PushAutoTrackHelper.hookIntentGetBroadcast(callingContext, generatePendingIntentRequestCode, putExtra, pendingIntentFlags);
        PendingIntent broadcast = PendingIntent.getBroadcast(callingContext, generatePendingIntentRequestCode, putExtra, pendingIntentFlags);
        PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, callingContext, generatePendingIntentRequestCode, putExtra, pendingIntentFlags);
        return broadcast;
    }

    static boolean shouldUploadMetrics(@NonNull NotificationParams params) {
        return params.getBoolean(Constants.AnalyticsKeys.ENABLED);
    }

    private static String getTag(NotificationParams params) {
        String tag = params.getString(Constants.MessageNotificationKeys.TAG);
        if (!TextUtils.isEmpty(tag)) {
            return tag;
        }
        return "FCM-Notification:" + SystemClock.uptimeMillis();
    }

    public static class DisplayNotificationInfo {
        public final int id;
        public final NotificationCompat.Builder notificationBuilder;
        public final String tag;

        DisplayNotificationInfo(NotificationCompat.Builder notificationBuilder2, String tag2, int id2) {
            this.notificationBuilder = notificationBuilder2;
            this.tag = tag2;
            this.id = id2;
        }
    }
}
