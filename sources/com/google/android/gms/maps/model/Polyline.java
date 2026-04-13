package com.google.android.gms.maps.model;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.maps.zzag;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class Polyline {
    private final zzag zza;

    public Polyline(zzag zzag) {
        this.zza = (zzag) Preconditions.checkNotNull(zzag);
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof Polyline)) {
            return false;
        }
        try {
            return this.zza.zzD(((Polyline) other).zza);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getColor() {
        try {
            return this.zza.zzf();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public Cap getEndCap() {
        try {
            return this.zza.zzj().zza();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public String getId() {
        try {
            return this.zza.zzl();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getJointType() {
        try {
            return this.zza.zzg();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public List<PatternItem> getPattern() {
        try {
            return PatternItem.zza(this.zza.zzm());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public List<LatLng> getPoints() {
        try {
            return this.zza.zzn();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public List<StyleSpan> getSpans() {
        try {
            return this.zza.zzo();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @NonNull
    public Cap getStartCap() {
        try {
            return this.zza.zzk().zza();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Nullable
    public Object getTag() {
        try {
            return ObjectWrapper.unwrap(this.zza.zzi());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getWidth() {
        try {
            return this.zza.zzd();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getZIndex() {
        try {
            return this.zza.zze();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        try {
            return this.zza.zzh();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isClickable() {
        try {
            return this.zza.zzE();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isGeodesic() {
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

    public void setEndCap(@NonNull Cap endCap) {
        Preconditions.checkNotNull(endCap, "endCap must not be null");
        try {
            this.zza.zzs(endCap);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPoints(@NonNull List<LatLng> points) {
        Preconditions.checkNotNull(points, "points must not be null");
        try {
            this.zza.zzw(points);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setStartCap(@NonNull Cap startCap) {
        Preconditions.checkNotNull(startCap, "startCap must not be null");
        try {
            this.zza.zzy(startCap);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void remove() {
        try {
            this.zza.zzp();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setClickable(boolean clickable) {
        try {
            this.zza.zzq(clickable);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setColor(int color) {
        try {
            this.zza.zzr(color);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setGeodesic(boolean geodesic) {
        try {
            this.zza.zzt(geodesic);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setJointType(int jointType) {
        try {
            this.zza.zzu(jointType);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPattern(@Nullable List<PatternItem> pattern) {
        try {
            this.zza.zzv(pattern);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setSpans(@NonNull List<StyleSpan> spans) {
        try {
            this.zza.zzx(spans);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTag(@Nullable Object tag) {
        try {
            this.zza.zzz(ObjectWrapper.wrap(tag));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setVisible(boolean visible) {
        try {
            this.zza.zzA(visible);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setWidth(float width) {
        try {
            this.zza.zzB(width);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setZIndex(float zIndex) {
        try {
            this.zza.zzC(zIndex);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
