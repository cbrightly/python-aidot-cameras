package com.google.android.gms.maps.model;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.maps.zzaa;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class Marker {
    private final zzaa zza;

    public Marker(zzaa zzaa) {
        this.zza = (zzaa) Preconditions.checkNotNull(zzaa);
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof Marker)) {
            return false;
        }
        try {
            return this.zza.zzC(((Marker) other).zza);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getAlpha() {
        try {
            return this.zza.zzd();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public String getId() {
        try {
            return this.zza.zzj();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public LatLng getPosition() {
        try {
            return this.zza.zzi();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getRotation() {
        try {
            return this.zza.zze();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public String getSnippet() {
        try {
            return this.zza.zzk();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public Object getTag() {
        try {
            return ObjectWrapper.unwrap(this.zza.zzh());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public String getTitle() {
        try {
            return this.zza.zzl();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getZIndex() {
        try {
            return this.zza.zzf();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        try {
            return this.zza.zzg();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isDraggable() {
        try {
            return this.zza.zzD();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isFlat() {
        try {
            return this.zza.zzE();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isInfoWindowShown() {
        try {
            return this.zza.zzF();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isVisible() {
        try {
            return this.zza.zzG();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setIcon(@Nullable BitmapDescriptor iconDescriptor) {
        if (iconDescriptor == null) {
            try {
                this.zza.zzs((IObjectWrapper) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zza.zzs(iconDescriptor.zza());
        }
    }

    public void setPosition(@NonNull LatLng latlng) {
        if (latlng != null) {
            try {
                this.zza.zzu(latlng);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        }
    }

    public void hideInfoWindow() {
        try {
            this.zza.zzm();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void remove() {
        try {
            this.zza.zzn();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setAlpha(float alpha) {
        try {
            this.zza.zzo(alpha);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setAnchor(float anchorU, float anchorV) {
        try {
            this.zza.zzp(anchorU, anchorV);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setDraggable(boolean draggable) {
        try {
            this.zza.zzq(draggable);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setFlat(boolean flat) {
        try {
            this.zza.zzr(flat);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setInfoWindowAnchor(float anchorU, float anchorV) {
        try {
            this.zza.zzt(anchorU, anchorV);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setRotation(float rotation) {
        try {
            this.zza.zzv(rotation);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setSnippet(@Nullable String snippet) {
        try {
            this.zza.zzw(snippet);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTag(@Nullable Object tag) {
        try {
            this.zza.zzx(ObjectWrapper.wrap(tag));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTitle(@Nullable String title) {
        try {
            this.zza.zzy(title);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setVisible(boolean visible) {
        try {
            this.zza.zzz(visible);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setZIndex(float zIndex) {
        try {
            this.zza.zzA(zIndex);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void showInfoWindow() {
        try {
            this.zza.zzB();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
