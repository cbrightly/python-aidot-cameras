package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzbw extends zza implements IStreetViewPanoramaDelegate {
    zzbw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
    }

    public final void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long j) {
        Parcel zza = zza();
        zzc.zze(zza, streetViewPanoramaCamera);
        zza.writeLong(j);
        zzc(9, zza);
    }

    public final void enablePanning(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(2, zza);
    }

    public final void enableStreetNames(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(4, zza);
    }

    public final void enableUserNavigation(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(3, zza);
    }

    public final void enableZoom(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(1, zza);
    }

    public final StreetViewPanoramaCamera getPanoramaCamera() {
        Parcel zzH = zzH(10, zza());
        StreetViewPanoramaCamera streetViewPanoramaCamera = (StreetViewPanoramaCamera) zzc.zza(zzH, StreetViewPanoramaCamera.CREATOR);
        zzH.recycle();
        return streetViewPanoramaCamera;
    }

    public final StreetViewPanoramaLocation getStreetViewPanoramaLocation() {
        Parcel zzH = zzH(14, zza());
        StreetViewPanoramaLocation streetViewPanoramaLocation = (StreetViewPanoramaLocation) zzc.zza(zzH, StreetViewPanoramaLocation.CREATOR);
        zzH.recycle();
        return streetViewPanoramaLocation;
    }

    public final boolean isPanningGesturesEnabled() {
        Parcel zzH = zzH(6, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isStreetNamesEnabled() {
        Parcel zzH = zzH(8, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isUserNavigationEnabled() {
        Parcel zzH = zzH(7, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isZoomGesturesEnabled() {
        Parcel zzH = zzH(5, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final IObjectWrapper orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        Parcel zza = zza();
        zzc.zze(zza, streetViewPanoramaOrientation);
        Parcel zzH = zzH(19, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final StreetViewPanoramaOrientation pointToOrientation(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        Parcel zzH = zzH(18, zza);
        StreetViewPanoramaOrientation streetViewPanoramaOrientation = (StreetViewPanoramaOrientation) zzc.zza(zzH, StreetViewPanoramaOrientation.CREATOR);
        zzH.recycle();
        return streetViewPanoramaOrientation;
    }

    public final void setOnStreetViewPanoramaCameraChangeListener(zzbk zzbk) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbk);
        zzc(16, zza);
    }

    public final void setOnStreetViewPanoramaChangeListener(zzbm zzbm) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbm);
        zzc(15, zza);
    }

    public final void setOnStreetViewPanoramaClickListener(zzbo zzbo) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbo);
        zzc(17, zza);
    }

    public final void setOnStreetViewPanoramaLongClickListener(zzbq zzbq) {
        Parcel zza = zza();
        zzc.zzg(zza, zzbq);
        zzc(20, zza);
    }

    public final void setPosition(LatLng latLng) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zzc(12, zza);
    }

    public final void setPositionWithID(String str) {
        Parcel zza = zza();
        zza.writeString(str);
        zzc(11, zza);
    }

    public final void setPositionWithRadius(LatLng latLng, int i) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zza.writeInt(i);
        zzc(13, zza);
    }

    public final void setPositionWithRadiusAndSource(LatLng latLng, int i, StreetViewSource streetViewSource) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zza.writeInt(i);
        zzc.zze(zza, streetViewSource);
        zzc(22, zza);
    }

    public final void setPositionWithSource(LatLng latLng, StreetViewSource streetViewSource) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zzc.zze(zza, streetViewSource);
        zzc(21, zza);
    }
}
