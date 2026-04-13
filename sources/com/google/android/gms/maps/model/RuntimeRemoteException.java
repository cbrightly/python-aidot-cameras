package com.google.android.gms.maps.model;

import android.os.RemoteException;
import androidx.annotation.NonNull;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class RuntimeRemoteException extends RuntimeException {
    public RuntimeRemoteException(@NonNull RemoteException e) {
        super(e);
    }
}
