package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class Period implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public abstract Period build();

        @RecentlyNullable
        public abstract TimeOfWeek getClose();

        @RecentlyNullable
        public abstract TimeOfWeek getOpen();

        @RecentlyNonNull
        public abstract Builder setClose(@Nullable TimeOfWeek timeOfWeek);

        @RecentlyNonNull
        public abstract Builder setOpen(@Nullable TimeOfWeek timeOfWeek);
    }

    @RecentlyNonNull
    public static Builder builder() {
        return new zzo();
    }

    @RecentlyNullable
    public abstract TimeOfWeek getClose();

    @RecentlyNullable
    public abstract TimeOfWeek getOpen();
}
