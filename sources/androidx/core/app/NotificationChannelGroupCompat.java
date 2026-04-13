package androidx.core.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationChannelGroupCompat {
    private boolean mBlocked;
    private List<NotificationChannelCompat> mChannels;
    String mDescription;
    final String mId;
    CharSequence mName;

    public static class Builder {
        final NotificationChannelGroupCompat mGroup;

        public Builder(@NonNull String id) {
            this.mGroup = new NotificationChannelGroupCompat(id);
        }

        @NonNull
        public Builder setName(@Nullable CharSequence name) {
            this.mGroup.mName = name;
            return this;
        }

        @NonNull
        public Builder setDescription(@Nullable String description) {
            this.mGroup.mDescription = description;
            return this;
        }

        @NonNull
        public NotificationChannelGroupCompat build() {
            return this.mGroup;
        }
    }

    NotificationChannelGroupCompat(@NonNull String id) {
        this.mChannels = Collections.emptyList();
        this.mId = (String) Preconditions.checkNotNull(id);
    }

    @RequiresApi(28)
    NotificationChannelGroupCompat(@NonNull NotificationChannelGroup group) {
        this(group, Collections.emptyList());
    }

    @RequiresApi(26)
    NotificationChannelGroupCompat(@NonNull NotificationChannelGroup group, @NonNull List<NotificationChannel> allChannels) {
        this(group.getId());
        this.mName = group.getName();
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            this.mDescription = group.getDescription();
        }
        if (i >= 28) {
            this.mBlocked = group.isBlocked();
            this.mChannels = getChannelsCompat(group.getChannels());
            return;
        }
        this.mChannels = getChannelsCompat(allChannels);
    }

    @RequiresApi(26)
    private List<NotificationChannelCompat> getChannelsCompat(List<NotificationChannel> channels) {
        List<NotificationChannelCompat> channelsCompat = new ArrayList<>();
        for (NotificationChannel channel : channels) {
            if (this.mId.equals(channel.getGroup())) {
                channelsCompat.add(new NotificationChannelCompat(channel));
            }
        }
        return channelsCompat;
    }

    /* access modifiers changed from: package-private */
    public NotificationChannelGroup getNotificationChannelGroup() {
        int i = Build.VERSION.SDK_INT;
        if (i < 26) {
            return null;
        }
        NotificationChannelGroup group = new NotificationChannelGroup(this.mId, this.mName);
        if (i >= 28) {
            group.setDescription(this.mDescription);
        }
        return group;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this.mId).setName(this.mName).setDescription(this.mDescription);
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

    public boolean isBlocked() {
        return this.mBlocked;
    }

    @NonNull
    public List<NotificationChannelCompat> getChannels() {
        return this.mChannels;
    }
}
