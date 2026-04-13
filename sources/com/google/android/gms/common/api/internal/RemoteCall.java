package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public interface RemoteCall<T, U> {
    @KeepForSdk
    void accept(@NonNull T t, @NonNull U u);
}
