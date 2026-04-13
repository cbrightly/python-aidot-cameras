package com.google.firebase.messaging;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.messaging.Constants;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import org.json.JSONArray;
import org.json.JSONException;

public class NotificationParams {
    private static final int COLOR_TRANSPARENT_IN_HEX = -16777216;
    private static final int EMPTY_JSON_ARRAY_LENGTH = 1;
    private static final String TAG = "NotificationParams";
    private static final int VISIBILITY_MAX = 1;
    private static final int VISIBILITY_MIN = -1;
    @NonNull
    private final Bundle data;

    public NotificationParams(@NonNull Bundle data2) {
        if (data2 != null) {
            this.data = new Bundle(data2);
            return;
        }
        throw new NullPointerException("data");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Integer getNotificationCount() {
        Integer notificationCount = getInteger(Constants.MessageNotificationKeys.NOTIFICATION_COUNT);
        if (notificationCount == null) {
            return null;
        }
        if (notificationCount.intValue() >= 0) {
            return notificationCount;
        }
        Log.w(Constants.TAG, "notificationCount is invalid: " + notificationCount + ". Skipping setting notificationCount.");
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Integer getNotificationPriority() {
        Integer notificationPriority = getInteger(Constants.MessageNotificationKeys.NOTIFICATION_PRIORITY);
        if (notificationPriority == null) {
            return null;
        }
        if (notificationPriority.intValue() >= -2 && notificationPriority.intValue() <= 2) {
            return notificationPriority;
        }
        Log.w(Constants.TAG, "notificationPriority is invalid " + notificationPriority + ". Skipping setting notificationPriority.");
        return null;
    }

    /* access modifiers changed from: package-private */
    public Integer getVisibility() {
        Integer visibility = getInteger(Constants.MessageNotificationKeys.VISIBILITY);
        if (visibility == null) {
            return null;
        }
        if (visibility.intValue() >= -1 && visibility.intValue() <= 1) {
            return visibility;
        }
        Log.w(TAG, "visibility is invalid: " + visibility + ". Skipping setting visibility.");
        return null;
    }

    public String getString(String key) {
        return this.data.getString(normalizePrefix(key));
    }

    private String normalizePrefix(String key) {
        if (!this.data.containsKey(key) && key.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            String keyWithOldPrefix = keyWithOldPrefix(key);
            if (this.data.containsKey(keyWithOldPrefix)) {
                return keyWithOldPrefix;
            }
        }
        return key;
    }

    public boolean getBoolean(String key) {
        String value = getString(key);
        return "1".equals(value) || Boolean.parseBoolean(value);
    }

    public Integer getInteger(String key) {
        String value = getString(key);
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            Log.w(TAG, "Couldn't parse value of " + userFriendlyKey(key) + "(" + value + ") into an int");
            return null;
        }
    }

    public Long getLong(String key) {
        String value = getString(key);
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        try {
            return Long.valueOf(Long.parseLong(value));
        } catch (NumberFormatException e) {
            Log.w(TAG, "Couldn't parse value of " + userFriendlyKey(key) + "(" + value + ") into a long");
            return null;
        }
    }

    @Nullable
    public String getLocalizationResourceForKey(String key) {
        return getString(key + Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX);
    }

    @Nullable
    public Object[] getLocalizationArgsForKey(String key) {
        JSONArray jsonArray = getJSONArray(key + Constants.MessageNotificationKeys.TEXT_ARGS_SUFFIX);
        if (jsonArray == null) {
            return null;
        }
        String[] args = new String[jsonArray.length()];
        for (int i = 0; i < args.length; i++) {
            args[i] = jsonArray.optString(i);
        }
        return args;
    }

    @Nullable
    public JSONArray getJSONArray(String key) {
        String json = getString(key);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            Log.w(TAG, "Malformed JSON for key " + userFriendlyKey(key) + ": " + json + ", falling back to default");
            return null;
        }
    }

    private static String userFriendlyKey(String key) {
        if (key.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            return key.substring(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX.length());
        }
        return key;
    }

    @Nullable
    public Uri getLink() {
        String link = getString(Constants.MessageNotificationKeys.LINK_ANDROID);
        if (TextUtils.isEmpty(link)) {
            link = getString(Constants.MessageNotificationKeys.LINK);
        }
        if (!TextUtils.isEmpty(link)) {
            return Uri.parse(link);
        }
        return null;
    }

    @Nullable
    public String getSoundResourceName() {
        String sound = getString(Constants.MessageNotificationKeys.SOUND_2);
        if (TextUtils.isEmpty(sound)) {
            return getString(Constants.MessageNotificationKeys.SOUND);
        }
        return sound;
    }

    @Nullable
    public long[] getVibrateTimings() {
        JSONArray jsonArray = getJSONArray(Constants.MessageNotificationKeys.VIBRATE_TIMINGS);
        if (jsonArray == null) {
            return null;
        }
        try {
            if (jsonArray.length() > 1) {
                long[] result = new long[jsonArray.length()];
                for (int i = 0; i < result.length; i++) {
                    result[i] = jsonArray.optLong(i);
                }
                return result;
            }
            throw new JSONException("vibrateTimings have invalid length");
        } catch (NumberFormatException | JSONException e) {
            Log.w(TAG, "User defined vibrateTimings is invalid: " + jsonArray + ". Skipping setting vibrateTimings.");
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public int[] getLightSettings() {
        JSONArray lightSettings = getJSONArray(Constants.MessageNotificationKeys.LIGHT_SETTINGS);
        if (lightSettings == null) {
            return null;
        }
        int[] result = new int[3];
        try {
            if (lightSettings.length() == 3) {
                result[0] = getLightColor(lightSettings.optString(0));
                result[1] = lightSettings.optInt(1);
                result[2] = lightSettings.optInt(2);
                return result;
            }
            throw new JSONException("lightSettings don't have all three fields");
        } catch (JSONException e) {
            Log.w(TAG, "LightSettings is invalid: " + lightSettings + ". Skipping setting LightSettings");
            return null;
        } catch (IllegalArgumentException e2) {
            Log.w(TAG, "LightSettings is invalid: " + lightSettings + ". " + e2.getMessage() + ". Skipping setting LightSettings");
            return null;
        }
    }

    public Bundle paramsWithReservedKeysRemoved() {
        Bundle cleanedData = new Bundle(this.data);
        for (String key : this.data.keySet()) {
            if (isReservedKey(key)) {
                cleanedData.remove(key);
            }
        }
        return cleanedData;
    }

    public Bundle paramsForAnalyticsIntent() {
        Bundle analyticsBundle = new Bundle(this.data);
        for (String key : this.data.keySet()) {
            if (!isAnalyticsKey(key)) {
                analyticsBundle.remove(key);
            }
        }
        return analyticsBundle;
    }

    @Nullable
    public String getLocalizedString(Resources resources, String packageName, String key) {
        String resourceKey = getLocalizationResourceForKey(key);
        if (TextUtils.isEmpty(resourceKey)) {
            return null;
        }
        int id = resources.getIdentifier(resourceKey, TypedValues.Custom.S_STRING, packageName);
        if (id == 0) {
            Log.w(TAG, userFriendlyKey(key + Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX) + " resource not found: " + key + " Default value will be used.");
            return null;
        }
        Object[] args = getLocalizationArgsForKey(key);
        if (args == null) {
            return resources.getString(id);
        }
        try {
            return resources.getString(id, args);
        } catch (MissingFormatArgumentException e) {
            Log.w(TAG, "Missing format argument for " + userFriendlyKey(key) + ": " + Arrays.toString(args) + " Default value will be used.", e);
            return null;
        }
    }

    public String getPossiblyLocalizedString(Resources resources, String packageName, String key) {
        String unlocalized = getString(key);
        if (!TextUtils.isEmpty(unlocalized)) {
            return unlocalized;
        }
        return getLocalizedString(resources, packageName, key);
    }

    public boolean hasImage() {
        return !TextUtils.isEmpty(getString(Constants.MessageNotificationKeys.IMAGE_URL));
    }

    public String getNotificationChannelId() {
        return getString(Constants.MessageNotificationKeys.CHANNEL);
    }

    private static boolean isAnalyticsKey(String key) {
        return key.startsWith(Constants.AnalyticsKeys.PREFIX) || key.equals("from");
    }

    private static boolean isReservedKey(String key) {
        return key.startsWith(Constants.MessagePayloadKeys.RESERVED_CLIENT_LIB_PREFIX) || key.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX) || key.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX_OLD);
    }

    private static int getLightColor(String color) {
        int result = Color.parseColor(color);
        if (result != -16777216) {
            return result;
        }
        throw new IllegalArgumentException("Transparent color is invalid");
    }

    public boolean isNotification() {
        return getBoolean(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION);
    }

    public static boolean isNotification(Bundle data2) {
        return "1".equals(data2.getString(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)) || "1".equals(data2.getString(keyWithOldPrefix(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)));
    }

    private static String keyWithOldPrefix(String key) {
        if (!key.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            return key;
        }
        return key.replace(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX, Constants.MessageNotificationKeys.NOTIFICATION_PREFIX_OLD);
    }
}
