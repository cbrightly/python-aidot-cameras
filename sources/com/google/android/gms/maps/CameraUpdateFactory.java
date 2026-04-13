package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class CameraUpdateFactory {
    private static ICameraUpdateFactoryDelegate zza;

    private CameraUpdateFactory() {
    }

    @NonNull
    public static CameraUpdate newCameraPosition(@NonNull CameraPosition cameraPosition) {
        Preconditions.checkNotNull(cameraPosition, "cameraPosition must not be null");
        try {
            return new CameraUpdate(zzb().newCameraPosition(cameraPosition));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate newLatLng(@NonNull LatLng latLng) {
        Preconditions.checkNotNull(latLng, "latLng must not be null");
        try {
            return new CameraUpdate(zzb().newLatLng(latLng));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int padding) {
        Preconditions.checkNotNull(bounds, "bounds must not be null");
        try {
            return new CameraUpdate(zzb().newLatLngBounds(bounds, padding));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate newLatLngZoom(@NonNull LatLng latLng, float zoom) {
        Preconditions.checkNotNull(latLng, "latLng must not be null");
        try {
            return new CameraUpdate(zzb().newLatLngZoom(latLng, zoom));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate scrollBy(float xPixel, float yPixel) {
        try {
            return new CameraUpdate(zzb().scrollBy(xPixel, yPixel));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate zoomBy(float amount) {
        try {
            return new CameraUpdate(zzb().zoomBy(amount));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate zoomIn() {
        try {
            return new CameraUpdate(zzb().zoomIn());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate zoomOut() {
        try {
            return new CameraUpdate(zzb().zoomOut());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate zoomTo(float zoom) {
        try {
            return new CameraUpdate(zzb().zoomTo(zoom));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static void zza(@NonNull ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate) {
        zza = (ICameraUpdateFactoryDelegate) Preconditions.checkNotNull(iCameraUpdateFactoryDelegate);
    }

    private static ICameraUpdateFactoryDelegate zzb() {
        return (ICameraUpdateFactoryDelegate) Preconditions.checkNotNull(zza, "CameraUpdateFactory is not initialized");
    }

    @NonNull
    public static CameraUpdate zoomBy(float amount, @NonNull Point focus) {
        Preconditions.checkNotNull(focus, "focus must not be null");
        try {
            return new CameraUpdate(zzb().zoomByWithFocus(amount, focus.x, focus.y));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public static CameraUpdate newLatLngBounds(@NonNull LatLngBounds bounds, int width, int height, int padding) {
        Preconditions.checkNotNull(bounds, "bounds must not be null");
        try {
            return new CameraUpdate(zzb().newLatLngBoundsWithSize(bounds, width, height, padding));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
