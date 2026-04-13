package androidx.core.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

public class NotificationChannelCompat {
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;
    AudioAttributes mAudioAttributes;
    private boolean mBypassDnd;
    private boolean mCanBubble;
    String mConversationId;
    String mDescription;
    String mGroupId;
    @NonNull
    final String mId;
    int mImportance;
    private boolean mImportantConversation;
    int mLightColor;
    boolean mLights;
    private int mLockscreenVisibility;
    CharSequence mName;
    String mParentId;
    boolean mShowBadge;
    Uri mSound;
    boolean mVibrationEnabled;
    long[] mVibrationPattern;

    public static class Builder {
        private final NotificationChannelCompat mChannel;

        public Builder(@NonNull String id, int importance) {
            this.mChannel = new NotificationChannelCompat(id, importance);
        }

        @NonNull
        public Builder setName(@Nullable CharSequence name) {
            this.mChannel.mName = name;
            return this;
        }

        @NonNull
        public Builder setImportance(int importance) {
            this.mChannel.mImportance = importance;
            return this;
        }

        @NonNull
        public Builder setDescription(@Nullable String description) {
            this.mChannel.mDescription = description;
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String groupId) {
            this.mChannel.mGroupId = groupId;
            return this;
        }

        @NonNull
        public Builder setShowBadge(boolean showBadge) {
            this.mChannel.mShowBadge = showBadge;
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri sound, @Nullable AudioAttributes audioAttributes) {
            NotificationChannelCompat notificationChannelCompat = this.mChannel;
            notificationChannelCompat.mSound = sound;
            notificationChannelCompat.mAudioAttributes = audioAttributes;
            return this;
        }

        @NonNull
        public Builder setLightsEnabled(boolean lights) {
            this.mChannel.mLights = lights;
            return this;
        }

        @NonNull
        public Builder setLightColor(int argb) {
            this.mChannel.mLightColor = argb;
            return this;
        }

        @NonNull
        public Builder setVibrationEnabled(boolean vibration) {
            this.mChannel.mVibrationEnabled = vibration;
            return this;
        }

        @NonNull
        public Builder setVibrationPattern(@Nullable long[] vibrationPattern) {
            NotificationChannelCompat notificationChannelCompat = this.mChannel;
            notificationChannelCompat.mVibrationEnabled = vibrationPattern != null && vibrationPattern.length > 0;
            notificationChannelCompat.mVibrationPattern = vibrationPattern;
            return this;
        }

        @NonNull
        public Builder setConversationId(@NonNull String parentChannelId, @NonNull String conversationId) {
            if (Build.VERSION.SDK_INT >= 30) {
                NotificationChannelCompat notificationChannelCompat = this.mChannel;
                notificationChannelCompat.mParentId = parentChannelId;
                notificationChannelCompat.mConversationId = conversationId;
            }
            return this;
        }

        @NonNull
        public NotificationChannelCompat build() {
            return this.mChannel;
        }
    }

    NotificationChannelCompat(@NonNull String id, int importance) {
        this.mShowBadge = true;
        this.mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.mLightColor = 0;
        this.mId = (String) Preconditions.checkNotNull(id);
        this.mImportance = importance;
        if (Build.VERSION.SDK_INT >= 21) {
            this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        }
    }

    @RequiresApi(26)
    NotificationChannelCompat(@NonNull NotificationChannel channel) {
        this(channel.getId(), channel.getImportance());
        this.mName = channel.getName();
        this.mDescription = channel.getDescription();
        this.mGroupId = channel.getGroup();
        this.mShowBadge = channel.canShowBadge();
        this.mSound = channel.getSound();
        this.mAudioAttributes = channel.getAudioAttributes();
        this.mLights = channel.shouldShowLights();
        this.mLightColor = channel.getLightColor();
        this.mVibrationEnabled = channel.shouldVibrate();
        this.mVibrationPattern = channel.getVibrationPattern();
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            this.mParentId = channel.getParentChannelId();
            this.mConversationId = channel.getConversationId();
        }
        this.mBypassDnd = channel.canBypassDnd();
        this.mLockscreenVisibility = channel.getLockscreenVisibility();
        if (i >= 29) {
            this.mCanBubble = channel.canBubble();
        }
        if (i >= 30) {
            this.mImportantConversation = channel.isImportantConversation();
        }
    }

    /* access modifiers changed from: package-private */
    public NotificationChannel getNotificationChannel() {
        String str;
        String str2;
        int i = Build.VERSION.SDK_INT;
        if (i < 26) {
            return null;
        }
        NotificationChannel channel = new NotificationChannel(this.mId, this.mName, this.mImportance);
        channel.setDescription(this.mDescription);
        channel.setGroup(this.mGroupId);
        channel.setShowBadge(this.mShowBadge);
        channel.setSound(this.mSound, this.mAudioAttributes);
        channel.enableLights(this.mLights);
        channel.setLightColor(this.mLightColor);
        channel.setVibrationPattern(this.mVibrationPattern);
        channel.enableVibration(this.mVibrationEnabled);
        if (!(i < 30 || (str = this.mParentId) == null || (str2 = this.mConversationId) == null)) {
            channel.setConversationId(str, str2);
        }
        return channel;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.mId, this.mImportance).setName(this.mName).setDescription(this.mDescription).setGroup(this.mGroupId).setShowBadge(this.mShowBadge).setSound(this.mSound, this.mAudioAttributes).setLightsEnabled(this.mLights).setLightColor(this.mLightColor).setVibrationEnabled(this.mVibrationEnabled).setVibrationPattern(this.mVibrationPattern).setConversationId(this.mParentId, this.mConversationId);
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    @Nullable
    public CharSequence getName() {
        return this.mName;
    }

    @Nullable
    public String getDescription() {
        return this.mDescription;
    }

    public int getImportance() {
        return this.mImportance;
    }

    @Nullable
    public Uri getSound() {
        return this.mSound;
    }

    @Nullable
    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public boolean shouldShowLights() {
        return this.mLights;
    }

    public int getLightColor() {
        return this.mLightColor;
    }

    public boolean shouldVibrate() {
        return this.mVibrationEnabled;
    }

    @Nullable
    public long[] getVibrationPattern() {
        return this.mVibrationPattern;
    }

    public boolean canShowBadge() {
        return this.mShowBadge;
    }

    @Nullable
    public String getGroup() {
        return this.mGroupId;
    }

    @Nullable
    public String getParentChannelId() {
        return this.mParentId;
    }

    @Nullable
    public String getConversationId() {
        return this.mConversationId;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    public boolean canBubble() {
        return this.mCanBubble;
    }

    public boolean isImportantConversation() {
        return this.mImportantConversation;
    }
}
