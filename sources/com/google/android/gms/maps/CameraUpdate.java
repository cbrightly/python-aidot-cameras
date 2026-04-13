package com.google.android.gms.maps;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class CameraUpdate {
    private final IObjectWrapper zza;

    CameraUpdate(IObjectWrapper iObjectWrapper) {
        this.zza = (IObjectWrapper) Preconditions.checkNotNull(iObjectWrapper);
    }

    @NonNull
    public final IObjectWrapper zza() {
        return this.zza;
    }
}
