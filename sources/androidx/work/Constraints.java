package androidx.work;

import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.room.ColumnInfo;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class Constraints {
    public static final Constraints NONE = new Builder().build();
    @ColumnInfo(name = "content_uri_triggers")
    private ContentUriTriggers mContentUriTriggers = new ContentUriTriggers();
    @ColumnInfo(name = "required_network_type")
    private NetworkType mRequiredNetworkType = NetworkType.NOT_REQUIRED;
    @ColumnInfo(name = "requires_battery_not_low")
    private boolean mRequiresBatteryNotLow;
    @ColumnInfo(name = "requires_charging")
    private boolean mRequiresCharging;
    @ColumnInfo(name = "requires_device_idle")
    private boolean mRequiresDeviceIdle;
    @ColumnInfo(name = "requires_storage_not_low")
    private boolean mRequiresStorageNotLow;
    @ColumnInfo(name = "trigger_content_update_delay")
    private long mTriggerContentUpdateDelay = -1;
    @ColumnInfo(name = "trigger_max_content_delay")
    private long mTriggerMaxContentDelay = -1;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Constraints() {
    }

    Constraints(Builder builder) {
        this.mRequiresCharging = builder.mRequiresCharging;
        int i = Build.VERSION.SDK_INT;
        this.mRequiresDeviceIdle = i >= 23 && builder.mRequiresDeviceIdle;
        this.mRequiredNetworkType = builder.mRequiredNetworkType;
        this.mRequiresBatteryNotLow = builder.mRequiresBatteryNotLow;
        this.mRequiresStorageNotLow = builder.mRequiresStorageNotLow;
        if (i >= 24) {
            this.mContentUriTriggers = builder.mContentUriTriggers;
            this.mTriggerContentUpdateDelay = builder.mTriggerContentUpdateDelay;
            this.mTriggerMaxContentDelay = builder.mTriggerContentMaxDelay;
        }
    }

    public Constraints(@NonNull Constraints other) {
        this.mRequiresCharging = other.mRequiresCharging;
        this.mRequiresDeviceIdle = other.mRequiresDeviceIdle;
        this.mRequiredNetworkType = other.mRequiredNetworkType;
        this.mRequiresBatteryNotLow = other.mRequiresBatteryNotLow;
        this.mRequiresStorageNotLow = other.mRequiresStorageNotLow;
        this.mContentUriTriggers = other.mContentUriTriggers;
    }

    @NonNull
    public NetworkType getRequiredNetworkType() {
        return this.mRequiredNetworkType;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiredNetworkType(@NonNull NetworkType requiredNetworkType) {
        this.mRequiredNetworkType = requiredNetworkType;
    }

    public boolean requiresCharging() {
        return this.mRequiresCharging;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresCharging(boolean requiresCharging) {
        this.mRequiresCharging = requiresCharging;
    }

    @RequiresApi(23)
    public boolean requiresDeviceIdle() {
        return this.mRequiresDeviceIdle;
    }

    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresDeviceIdle(boolean requiresDeviceIdle) {
        this.mRequiresDeviceIdle = requiresDeviceIdle;
    }

    public boolean requiresBatteryNotLow() {
        return this.mRequiresBatteryNotLow;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresBatteryNotLow(boolean requiresBatteryNotLow) {
        this.mRequiresBatteryNotLow = requiresBatteryNotLow;
    }

    public boolean requiresStorageNotLow() {
        return this.mRequiresStorageNotLow;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRequiresStorageNotLow(boolean requiresStorageNotLow) {
        this.mRequiresStorageNotLow = requiresStorageNotLow;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public long getTriggerContentUpdateDelay() {
        return this.mTriggerContentUpdateDelay;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setTriggerContentUpdateDelay(long triggerContentUpdateDelay) {
        this.mTriggerContentUpdateDelay = triggerContentUpdateDelay;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public long getTriggerMaxContentDelay() {
        return this.mTriggerMaxContentDelay;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setTriggerMaxContentDelay(long triggerMaxContentDelay) {
        this.mTriggerMaxContentDelay = triggerMaxContentDelay;
    }

    @RequiresApi(24)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setContentUriTriggers(@Nullable ContentUriTriggers mContentUriTriggers2) {
        this.mContentUriTriggers = mContentUriTriggers2;
    }

    @RequiresApi(24)
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ContentUriTriggers getContentUriTriggers() {
        return this.mContentUriTriggers;
    }

    @RequiresApi(24)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean hasContentUriTriggers() {
        return this.mContentUriTriggers.size() > 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Constraints that = (Constraints) o;
        if (this.mRequiresCharging == that.mRequiresCharging && this.mRequiresDeviceIdle == that.mRequiresDeviceIdle && this.mRequiresBatteryNotLow == that.mRequiresBatteryNotLow && this.mRequiresStorageNotLow == that.mRequiresStorageNotLow && this.mTriggerContentUpdateDelay == that.mTriggerContentUpdateDelay && this.mTriggerMaxContentDelay == that.mTriggerMaxContentDelay && this.mRequiredNetworkType == that.mRequiredNetworkType) {
            return this.mContentUriTriggers.equals(that.mContentUriTriggers);
        }
        return false;
    }

    public int hashCode() {
        long j = this.mTriggerContentUpdateDelay;
        long j2 = this.mTriggerMaxContentDelay;
        return (((((((((((((this.mRequiredNetworkType.hashCode() * 31) + (this.mRequiresCharging ? 1 : 0)) * 31) + (this.mRequiresDeviceIdle ? 1 : 0)) * 31) + (this.mRequiresBatteryNotLow ? 1 : 0)) * 31) + (this.mRequiresStorageNotLow ? 1 : 0)) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + this.mContentUriTriggers.hashCode();
    }

    public static final class Builder {
        ContentUriTriggers mContentUriTriggers = new ContentUriTriggers();
        NetworkType mRequiredNetworkType = NetworkType.NOT_REQUIRED;
        boolean mRequiresBatteryNotLow = false;
        boolean mRequiresCharging = false;
        boolean mRequiresDeviceIdle = false;
        boolean mRequiresStorageNotLow = false;
        long mTriggerContentMaxDelay = -1;
        long mTriggerContentUpdateDelay = -1;

        public Builder() {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder(@NonNull Constraints constraints) {
            boolean z = false;
            this.mRequiresCharging = constraints.requiresCharging();
            int i = Build.VERSION.SDK_INT;
            if (i >= 23 && constraints.requiresDeviceIdle()) {
                z = true;
            }
            this.mRequiresDeviceIdle = z;
            this.mRequiredNetworkType = constraints.getRequiredNetworkType();
            this.mRequiresBatteryNotLow = constraints.requiresBatteryNotLow();
            this.mRequiresStorageNotLow = constraints.requiresStorageNotLow();
            if (i >= 24) {
                this.mTriggerContentUpdateDelay = constraints.getTriggerContentUpdateDelay();
                this.mTriggerContentMaxDelay = constraints.getTriggerMaxContentDelay();
                this.mContentUriTriggers = constraints.getContentUriTriggers();
            }
        }

        @NonNull
        public Builder setRequiresCharging(boolean requiresCharging) {
            this.mRequiresCharging = requiresCharging;
            return this;
        }

        @RequiresApi(23)
        @NonNull
        public Builder setRequiresDeviceIdle(boolean requiresDeviceIdle) {
            this.mRequiresDeviceIdle = requiresDeviceIdle;
            return this;
        }

        @NonNull
        public Builder setRequiredNetworkType(@NonNull NetworkType networkType) {
            this.mRequiredNetworkType = networkType;
            return this;
        }

        @NonNull
        public Builder setRequiresBatteryNotLow(boolean requiresBatteryNotLow) {
            this.mRequiresBatteryNotLow = requiresBatteryNotLow;
            return this;
        }

        @NonNull
        public Builder setRequiresStorageNotLow(boolean requiresStorageNotLow) {
            this.mRequiresStorageNotLow = requiresStorageNotLow;
            return this;
        }

        @RequiresApi(24)
        @NonNull
        public Builder addContentUriTrigger(@NonNull Uri uri, boolean triggerForDescendants) {
            this.mContentUriTriggers.add(uri, triggerForDescendants);
            return this;
        }

        @RequiresApi(24)
        @NonNull
        public Builder setTriggerContentUpdateDelay(long duration, @NonNull TimeUnit timeUnit) {
            this.mTriggerContentUpdateDelay = timeUnit.toMillis(duration);
            return this;
        }

        @RequiresApi(26)
        @NonNull
        public Builder setTriggerContentUpdateDelay(Duration duration) {
            this.mTriggerContentUpdateDelay = duration.toMillis();
            return this;
        }

        @RequiresApi(24)
        @NonNull
        public Builder setTriggerContentMaxDelay(long duration, @NonNull TimeUnit timeUnit) {
            this.mTriggerContentMaxDelay = timeUnit.toMillis(duration);
            return this;
        }

        @RequiresApi(26)
        @NonNull
        public Builder setTriggerContentMaxDelay(Duration duration) {
            this.mTriggerContentMaxDelay = duration.toMillis();
            return this;
        }

        @NonNull
        public Constraints build() {
            return new Constraints(this);
        }
    }
}
