package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class PlusCode implements Parcelable {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public abstract PlusCode build();

        @RecentlyNullable
        public abstract String getCompoundCode();

        @RecentlyNullable
        public abstract String getGlobalCode();

        @RecentlyNonNull
        public abstract Builder setCompoundCode(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setGlobalCode(@Nullable String str);
    }

    @RecentlyNonNull
    public static Builder builder() {
        return new zzv();
    }

    @RecentlyNullable
    public abstract String getCompoundCode();

    @RecentlyNullable
    public abstract String getGlobalCode();
}
