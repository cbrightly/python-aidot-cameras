package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbk;
import com.google.android.gms.maps.internal.zzbm;
import com.google.android.gms.maps.internal.zzbo;
import com.google.android.gms.maps.internal.zzbq;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public class StreetViewPanorama {
    private final IStreetViewPanoramaDelegate zza;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnStreetViewPanoramaCameraChangeListener {
        void onStreetViewPanoramaCameraChange(@NonNull StreetViewPanoramaCamera streetViewPanoramaCamera);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnStreetViewPanoramaChangeListener {
        void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation streetViewPanoramaLocation);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnStreetViewPanoramaClickListener {
        void onStreetViewPanoramaClick(@NonNull StreetViewPanoramaOrientation streetViewPanoramaOrientation);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public interface OnStreetViewPanoramaLongClickListener {
        void onStreetViewPanoramaLongClick(@NonNull StreetViewPanoramaOrientation streetViewPanoramaOrientation);
    }

    public StreetViewPanorama(@NonNull IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) {
        this.zza = (IStreetViewPanoramaDelegate) Preconditions.checkNotNull(iStreetViewPanoramaDelegate, "delegate");
    }

    public void animateTo(@NonNull StreetViewPanoramaCamera camera, long duration) {
        Preconditions.checkNotNull(camera);
        try {
            this.zza.animateTo(camera, duration);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public StreetViewPanoramaLocation getLocation() {
        try {
            return this.zza.getStreetViewPanoramaLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public StreetViewPanoramaCamera getPanoramaCamera() {
        try {
            return this.zza.getPanoramaCamera();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isPanningGesturesEnabled() {
        try {
            return this.zza.isPanningGesturesEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isStreetNamesEnabled() {
        try {
            return this.zza.isStreetNamesEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isUserNavigationEnabled() {
        try {
            return this.zza.isUserNavigationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isZoomGesturesEnabled() {
        try {
            return this.zza.isZoomGesturesEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public Point orientationToPoint(@NonNull StreetViewPanoramaOrientation orientation) {
        try {
            IObjectWrapper orientationToPoint = this.zza.orientationToPoint(orientation);
            if (orientationToPoint == null) {
                return null;
            }
            return (Point) ObjectWrapper.unwrap(orientationToPoint);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public StreetViewPanoramaOrientation pointToOrientation(@NonNull Point point) {
        try {
            return this.zza.pointToOrientation(ObjectWrapper.wrap(point));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnStreetViewPanoramaCameraChangeListener(@Nullable OnStreetViewPanoramaCameraChangeListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnStreetViewPanoramaCameraChangeListener((zzbk) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnStreetViewPanoramaCameraChangeListener(new zzaj(this, listener));
        }
    }

    public final void setOnStreetViewPanoramaChangeListener(@Nullable OnStreetViewPanoramaChangeListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnStreetViewPanoramaChangeListener((zzbm) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnStreetViewPanoramaChangeListener(new zzai(this, listener));
        }
    }

    public final void setOnStreetViewPanoramaClickListener(@Nullable OnStreetViewPanoramaClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnStreetViewPanoramaClickListener((zzbo) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnStreetViewPanoramaClickListener(new zzak(this, listener));
        }
    }

    public final void setOnStreetViewPanoramaLongClickListener(@Nullable OnStreetViewPanoramaLongClickListener listener) {
        if (listener == null) {
            try {
                this.zza.setOnStreetViewPanoramaLongClickListener((zzbq) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.setOnStreetViewPanoramaLongClickListener(new zzal(this, listener));
        }
    }

    public void setPanningGesturesEnabled(boolean enablePanning) {
        try {
            this.zza.enablePanning(enablePanning);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(@NonNull LatLng position) {
        try {
            this.zza.setPosition(position);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setStreetNamesEnabled(boolean enableStreetNames) {
        try {
            this.zza.enableStreetNames(enableStreetNames);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setUserNavigationEnabled(boolean enableUserNavigation) {
        try {
            this.zza.enableUserNavigation(enableUserNavigation);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setZoomGesturesEnabled(boolean enableZoom) {
        try {
            this.zza.enableZoom(enableZoom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(@NonNull LatLng position, int radius) {
        try {
            this.zza.setPositionWithRadius(position, radius);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(@NonNull LatLng position, int radius, @Nullable StreetViewSource source) {
        try {
            this.zza.setPositionWithRadiusAndSource(position, radius, source);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(@NonNull LatLng position, @Nullable StreetViewSource source) {
        try {
            this.zza.setPositionWithSource(position, source);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(@NonNull String panoId) {
        try {
            this.zza.setPositionWithID(panoId);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
