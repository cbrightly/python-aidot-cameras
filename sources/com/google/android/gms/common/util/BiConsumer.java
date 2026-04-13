package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface BiConsumer<T, U> {
    @KeepForSdk
    void accept(@NonNull T t, @NonNull U u);
}
