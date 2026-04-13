package com.google.android.gms.common;

import android.content.Intent;
import androidx.annotation.NonNull;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class GooglePlayServicesRepairableException extends UserRecoverableException {
    private final int zza;

    public GooglePlayServicesRepairableException(int connectionStatusCode, @NonNull String msg, @NonNull Intent intent) {
        super(msg, intent);
        this.zza = connectionStatusCode;
    }

    public int getConnectionStatusCode() {
        return this.zza;
    }
}
