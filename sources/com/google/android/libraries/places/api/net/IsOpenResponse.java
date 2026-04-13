package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class IsOpenResponse {
    @RecentlyNonNull
    public static IsOpenResponse newInstance(@Nullable Boolean bool) {
        return new zzt(bool);
    }

    @RecentlyNullable
    public abstract Boolean isOpen();
}
