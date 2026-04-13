package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.RecentlyNonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class SpecialDay implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public abstract SpecialDay build();

        @RecentlyNonNull
        public abstract LocalDate getDate();

        public abstract boolean isExceptional();

        @RecentlyNonNull
        public abstract Builder setDate(@RecentlyNonNull LocalDate localDate);

        @RecentlyNonNull
        public abstract Builder setExceptional(boolean z);
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull LocalDate date) {
        zzz zzz = new zzz();
        zzz.setDate(date);
        zzz.setExceptional(false);
        return zzz;
    }

    @RecentlyNonNull
    public abstract LocalDate getDate();

    public abstract boolean isExceptional();
}
