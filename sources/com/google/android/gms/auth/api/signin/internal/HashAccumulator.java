package com.google.android.gms.auth.api.signin.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class HashAccumulator {
    private int zaa = 1;

    @NonNull
    @KeepForSdk
    @CanIgnoreReturnValue
    public HashAccumulator addObject(@Nullable Object object) {
        this.zaa = (this.zaa * 31) + (object == null ? 0 : object.hashCode());
        return this;
    }

    @KeepForSdk
    public int hash() {
        return this.zaa;
    }

    @NonNull
    @CanIgnoreReturnValue
    public final HashAccumulator zaa(boolean z) {
        this.zaa = (this.zaa * 31) + (z ? 1 : 0);
        return this;
    }
}
