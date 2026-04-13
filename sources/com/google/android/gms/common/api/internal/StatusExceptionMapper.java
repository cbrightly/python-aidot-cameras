package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface StatusExceptionMapper {
    @NonNull
    @KeepForSdk
    Exception getException(@NonNull Status status);
}
