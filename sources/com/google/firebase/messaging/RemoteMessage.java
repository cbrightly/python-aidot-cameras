package com.google.firebase.messaging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.messaging.Constants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import meshsdk.model.GroupInfo;

@SafeParcelable.Class(creator = "RemoteMessageCreator")
@SafeParcelable.Reserved({1})
public final class RemoteMessage extends AbstractSafeParcelable {
    public static final Parcelable.Creator<RemoteMessage> CREATOR = new RemoteMessageCreator();
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;
    @SafeParcelable.Field(id = 2)
    Bundle bundle;
    private Map<String, String> data;
    private Notification notification;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    @SafeParcelable.Constructor
    public RemoteMessage(@SafeParcelable.Param(id = 2) Bundle bundle2) {
        this.bundle = bundle2;
    }

    /* access modifiers changed from: package-private */
    public void populateSendMessageIntent(Intent intent) {
        intent.putExtras(this.bundle);
    }

    public void writeToParcel(@NonNull Parcel out, int flags) {
        RemoteMessageCreator.writeToParcel(this, out, flags);
    }

    @Nullable
    public String getSenderId() {
        return this.bundle.getString(Constants.MessagePayloadKeys.SENDER_ID);
    }

    @Nullable
    public String getFrom() {
        return this.bundle.getString("from");
    }

    @Nullable
    public String getTo() {
        return this.bundle.getString(Constants.MessagePayloadKeys.TO);
    }

    @NonNull
    public Map<String, String> getData() {
        if (this.data == null) {
            this.data = Constants.MessagePayloadKeys.extractDeveloperDefinedPayload(this.bundle);
        }
        return this.data;
    }

    @ShowFirstParty
    @Nullable
    public byte[] getRawData() {
        return this.bundle.getByteArray(Constants.MessagePayloadKeys.RAW_DATA);
    }

    @Nullable
    public String getCollapseKey() {
        return this.bundle.getString(Constants.MessagePayloadKeys.COLLAPSE_KEY);
    }

    @Nullable
    public String getMessageId() {
        String messageId = this.bundle.getString(Constants.MessagePayloadKeys.MSGID);
        if (messageId == null) {
            return this.bundle.getString(Constants.MessagePayloadKeys.MSGID_SERVER);
        }
        return messageId;
    }

    @Nullable
    public String getMessageType() {
        return this.bundle.getString(Constants.MessagePayloadKeys.MESSAGE_TYPE);
    }

    public long getSentTime() {
        Object sentTime = this.bundle.get(Constants.MessagePayloadKeys.SENT_TIME);
        if (sentTime instanceof Long) {
            return ((Long) sentTime).longValue();
        }
        if (!(sentTime instanceof String)) {
            return 0;
        }
        try {
            return Long.parseLong((String) sentTime);
        } catch (NumberFormatException e) {
            Log.w(Constants.TAG, "Invalid sent time: " + sentTime);
            return 0;
        }
    }

    public int getTtl() {
        Object ttl = this.bundle.get(Constants.MessagePayloadKeys.TTL);
        if (ttl instanceof Integer) {
            return ((Integer) ttl).intValue();
        }
        if (!(ttl instanceof String)) {
            return 0;
        }
        try {
            return Integer.parseInt((String) ttl);
        } catch (NumberFormatException e) {
            Log.w(Constants.TAG, "Invalid TTL: " + ttl);
            return 0;
        }
    }

    public int getOriginalPriority() {
        String originalPriority = this.bundle.getString(Constants.MessagePayloadKeys.ORIGINAL_PRIORITY);
        if (originalPriority == null) {
            originalPriority = this.bundle.getString(Constants.MessagePayloadKeys.PRIORITY_V19);
        }
        return getMessagePriority(originalPriority);
    }

    public int getPriority() {
        String priority = this.bundle.getString(Constants.MessagePayloadKeys.DELIVERED_PRIORITY);
        if (priority == null) {
            if ("1".equals(this.bundle.getString(Constants.MessagePayloadKeys.PRIORITY_REDUCED_V19))) {
                return 2;
            }
            priority = this.bundle.getString(Constants.MessagePayloadKeys.PRIORITY_V19);
        }
        return getMessagePriority(priority);
    }

    private int getMessagePriority(String priority) {
        if ("high".equals(priority)) {
            return 1;
        }
        if (GroupInfo.TYPE_NORMAL.equals(priority)) {
            return 2;
        }
        return 0;
    }

    @Nullable
    public Notification getNotification() {
        if (this.notification == null && NotificationParams.isNotification(this.bundle)) {
            this.notification = new Notification(new NotificationParams(this.bundle));
        }
        return this.notification;
    }

    @KeepForSdk
    public Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtras(this.bundle);
        return intent;
    }

    public static class Builder {
        private final Bundle bundle;
        private final Map<String, String> data = new ArrayMap();

        public Builder(@NonNull String to) {
            Bundle bundle2 = new Bundle();
            this.bundle = bundle2;
            if (!TextUtils.isEmpty(to)) {
                bundle2.putString(Constants.MessagePayloadKeys.TO, to);
                return;
            }
            throw new IllegalArgumentException("Invalid to: " + to);
        }

        @NonNull
        public RemoteMessage build() {
            Bundle bundle2 = new Bundle();
            for (Map.Entry<String, String> entry : this.data.entrySet()) {
                bundle2.putString(entry.getKey(), entry.getValue());
            }
            bundle2.putAll(this.bundle);
            this.bundle.remove("from");
            return new RemoteMessage(bundle2);
        }

        @NonNull
        public Builder addData(@NonNull String key, @Nullable String value) {
            this.data.put(key, value);
            return this;
        }

        @NonNull
        public Builder setData(@NonNull Map<String, String> data2) {
            this.data.clear();
            this.data.putAll(data2);
            return this;
        }

        @NonNull
        public Map<String, String> getData() {
            return this.data;
        }

        @NonNull
        public Builder clearData() {
            this.data.clear();
            return this;
        }

        @NonNull
        public String getMessageId() {
            return this.bundle.getString(Constants.MessagePayloadKeys.MSGID, "");
        }

        @Nullable
        public String getMessageType() {
            return this.bundle.getString(Constants.MessagePayloadKeys.MESSAGE_TYPE);
        }

        @Nullable
        public String getCollapseKey() {
            return this.bundle.getString(Constants.MessagePayloadKeys.MESSAGE_TYPE);
        }

        @IntRange(from = 0, to = 86400)
        public int getTtl() {
            return Integer.parseInt(this.bundle.getString(Constants.MessagePayloadKeys.MESSAGE_TYPE, "0"));
        }

        @ShowFirstParty
        @NonNull
        public Builder setRawData(byte[] data2) {
            this.bundle.putByteArray(Constants.MessagePayloadKeys.RAW_DATA, data2);
            return this;
        }

        @NonNull
        public Builder setMessageId(@NonNull String messageId) {
            this.bundle.putString(Constants.MessagePayloadKeys.MSGID, messageId);
            return this;
        }

        @NonNull
        public Builder setMessageType(@Nullable String messageType) {
            this.bundle.putString(Constants.MessagePayloadKeys.MESSAGE_TYPE, messageType);
            return this;
        }

        @NonNull
        public Builder setTtl(@IntRange(from = 0, to = 86400) int ttl) {
            this.bundle.putString(Constants.MessagePayloadKeys.TTL, String.valueOf(ttl));
            return this;
        }

        @NonNull
        public Builder setCollapseKey(@Nullable String collapseKey) {
            this.bundle.putString(Constants.MessagePayloadKeys.COLLAPSE_KEY, collapseKey);
            return this;
        }
    }

    public static class Notification {
        private final String body;
        private final String[] bodyLocArgs;
        private final String bodyLocKey;
        private final String channelId;
        private final String clickAction;
        private final String color;
        private final boolean defaultLightSettings;
        private final boolean defaultSound;
        private final boolean defaultVibrateTimings;
        private final Long eventTime;
        private final String icon;
        private final String imageUrl;
        private final int[] lightSettings;
        private final Uri link;
        private final boolean localOnly;
        private final Integer notificationCount;
        private final Integer notificationPriority;
        private final String sound;
        private final boolean sticky;
        private final String tag;
        private final String ticker;
        private final String title;
        private final String[] titleLocArgs;
        private final String titleLocKey;
        private final long[] vibrateTimings;
        private final Integer visibility;

        private Notification(NotificationParams params) {
            this.title = params.getString(Constants.MessageNotificationKeys.TITLE);
            this.titleLocKey = params.getLocalizationResourceForKey(Constants.MessageNotificationKeys.TITLE);
            this.titleLocArgs = getLocalizationArgs(params, Constants.MessageNotificationKeys.TITLE);
            this.body = params.getString(Constants.MessageNotificationKeys.BODY);
            this.bodyLocKey = params.getLocalizationResourceForKey(Constants.MessageNotificationKeys.BODY);
            this.bodyLocArgs = getLocalizationArgs(params, Constants.MessageNotificationKeys.BODY);
            this.icon = params.getString(Constants.MessageNotificationKeys.ICON);
            this.sound = params.getSoundResourceName();
            this.tag = params.getString(Constants.MessageNotificationKeys.TAG);
            this.color = params.getString(Constants.MessageNotificationKeys.COLOR);
            this.clickAction = params.getString(Constants.MessageNotificationKeys.CLICK_ACTION);
            this.channelId = params.getString(Constants.MessageNotificationKeys.CHANNEL);
            this.link = params.getLink();
            this.imageUrl = params.getString(Constants.MessageNotificationKeys.IMAGE_URL);
            this.ticker = params.getString(Constants.MessageNotificationKeys.TICKER);
            this.notificationPriority = params.getInteger(Constants.MessageNotificationKeys.NOTIFICATION_PRIORITY);
            this.visibility = params.getInteger(Constants.MessageNotificationKeys.VISIBILITY);
            this.notificationCount = params.getInteger(Constants.MessageNotificationKeys.NOTIFICATION_COUNT);
            this.sticky = params.getBoolean(Constants.MessageNotificationKeys.STICKY);
            this.localOnly = params.getBoolean(Constants.MessageNotificationKeys.LOCAL_ONLY);
            this.defaultSound = params.getBoolean(Constants.MessageNotificationKeys.DEFAULT_SOUND);
            this.defaultVibrateTimings = params.getBoolean(Constants.MessageNotificationKeys.DEFAULT_VIBRATE_TIMINGS);
            this.defaultLightSettings = params.getBoolean(Constants.MessageNotificationKeys.DEFAULT_LIGHT_SETTINGS);
            this.eventTime = params.getLong(Constants.MessageNotificationKeys.EVENT_TIME);
            this.lightSettings = params.getLightSettings();
            this.vibrateTimings = params.getVibrateTimings();
        }

        private static String[] getLocalizationArgs(NotificationParams params, String key) {
            Object[] args = params.getLocalizationArgsForKey(key);
            if (args == null) {
                return null;
            }
            String[] stringArgs = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                stringArgs[i] = String.valueOf(args[i]);
            }
            return stringArgs;
        }

        @Nullable
        public String getTitle() {
            return this.title;
        }

        @Nullable
        public String getTitleLocalizationKey() {
            return this.titleLocKey;
        }

        @Nullable
        public String[] getTitleLocalizationArgs() {
            return this.titleLocArgs;
        }

        @Nullable
        public String getBody() {
            return this.body;
        }

        @Nullable
        public String getBodyLocalizationKey() {
            return this.bodyLocKey;
        }

        @Nullable
        public String[] getBodyLocalizationArgs() {
            return this.bodyLocArgs;
        }

        @Nullable
        public String getIcon() {
            return this.icon;
        }

        @Nullable
        public Uri getImageUrl() {
            String str = this.imageUrl;
            if (str != null) {
                return Uri.parse(str);
            }
            return null;
        }

        @Nullable
        public String getSound() {
            return this.sound;
        }

        @Nullable
        public String getTag() {
            return this.tag;
        }

        @Nullable
        public String getColor() {
            return this.color;
        }

        @Nullable
        public String getClickAction() {
            return this.clickAction;
        }

        @Nullable
        public String getChannelId() {
            return this.channelId;
        }

        @Nullable
        public Uri getLink() {
            return this.link;
        }

        @Nullable
        public String getTicker() {
            return this.ticker;
        }

        public boolean getSticky() {
            return this.sticky;
        }

        public boolean getLocalOnly() {
            return this.localOnly;
        }

        public boolean getDefaultSound() {
            return this.defaultSound;
        }

        public boolean getDefaultVibrateSettings() {
            return this.defaultVibrateTimings;
        }

        public boolean getDefaultLightSettings() {
            return this.defaultLightSettings;
        }

        @Nullable
        public Integer getNotificationPriority() {
            return this.notificationPriority;
        }

        @Nullable
        public Integer getVisibility() {
            return this.visibility;
        }

        @Nullable
        public Integer getNotificationCount() {
            return this.notificationCount;
        }

        @Nullable
        public Long getEventTime() {
            return this.eventTime;
        }

        @Nullable
        public int[] getLightSettings() {
            return this.lightSettings;
        }

        @Nullable
        public long[] getVibrateTimings() {
            return this.vibrateTimings;
        }
    }
}
