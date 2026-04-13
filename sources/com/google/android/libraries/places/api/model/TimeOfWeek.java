package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class TimeOfWeek implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public abstract TimeOfWeek build();

        @RecentlyNullable
        public abstract LocalDate getDate();

        @RecentlyNonNull
        public abstract DayOfWeek getDay();

        @RecentlyNonNull
        public abstract LocalTime getTime();

        public abstract boolean isTruncated();

        @RecentlyNonNull
        public abstract Builder setDate(@Nullable LocalDate localDate);

        @RecentlyNonNull
        public abstract Builder setDay(@RecentlyNonNull DayOfWeek dayOfWeek);

        @RecentlyNonNull
        public abstract Builder setTime(@RecentlyNonNull LocalTime localTime);

        @RecentlyNonNull
        public abstract Builder setTruncated(boolean z);
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull DayOfWeek day, @RecentlyNonNull LocalTime localTime) {
        zzab zzab = new zzab();
        zzab.setDay(day);
        zzab.setTime(localTime);
        zzab.setTruncated(false);
        return zzab;
    }

    @RecentlyNonNull
    public static TimeOfWeek newInstance(@RecentlyNonNull DayOfWeek day, @RecentlyNonNull LocalTime localTime) {
        Builder builder = builder(day, localTime);
        builder.setTruncated(false);
        return builder.build();
    }

    @RecentlyNullable
    public abstract LocalDate getDate();

    @RecentlyNonNull
    public abstract DayOfWeek getDay();

    @RecentlyNonNull
    public abstract LocalTime getTime();

    public abstract boolean isTruncated();
}
