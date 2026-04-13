package com.google.android.gms.maps.model;

import android.graphics.Bitmap;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.maps.zzi;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class BitmapDescriptorFactory {
    public static final float HUE_AZURE = 210.0f;
    public static final float HUE_BLUE = 240.0f;
    public static final float HUE_CYAN = 180.0f;
    public static final float HUE_GREEN = 120.0f;
    public static final float HUE_MAGENTA = 300.0f;
    public static final float HUE_ORANGE = 30.0f;
    public static final float HUE_RED = 0.0f;
    public static final float HUE_ROSE = 330.0f;
    public static final float HUE_VIOLET = 270.0f;
    public static final float HUE_YELLOW = 60.0f;
    private static zzi zza;

    private BitmapDescriptorFactory() {
    }

    @NonNull
    public static BitmapDescriptor defaultMarker() {
        try {
            return new BitmapDescriptor(zzb().zzd());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static BitmapDescriptor fromAsset(@NonNull String assetName) {
        Preconditions.checkNotNull(assetName, "assetName must not be null");
        try {
            return new BitmapDescriptor(zzb().zzf(assetName));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static BitmapDescriptor fromBitmap(@NonNull Bitmap image) {
        Preconditions.checkNotNull(image, "image must not be null");
        try {
            return new BitmapDescriptor(zzb().zzg(image));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static BitmapDescriptor fromFile(@NonNull String fileName) {
        Preconditions.checkNotNull(fileName, "fileName must not be null");
        try {
            return new BitmapDescriptor(zzb().zzh(fileName));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static BitmapDescriptor fromPath(@NonNull String absolutePath) {
        Preconditions.checkNotNull(absolutePath, "absolutePath must not be null");
        try {
            return new BitmapDescriptor(zzb().zzi(absolutePath));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static BitmapDescriptor fromResource(int resourceId) {
        try {
            return new BitmapDescriptor(zzb().zzj(resourceId));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static void zza(zzi zzi) {
        if (zza == null) {
            zza = (zzi) Preconditions.checkNotNull(zzi, "delegate must not be null");
        }
    }

    private static zzi zzb() {
        return (zzi) Preconditions.checkNotNull(zza, "IBitmapDescriptorFactory is not initialized");
    }

    @NonNull
    public static BitmapDescriptor defaultMarker(float hue) {
        try {
            return new BitmapDescriptor(zzb().zze(hue));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
