package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzbz extends zza implements IUiSettingsDelegate {
    zzbz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IUiSettingsDelegate");
    }

    public final boolean isCompassEnabled() {
        Parcel zzH = zzH(10, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isIndoorLevelPickerEnabled() {
        Parcel zzH = zzH(17, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isMapToolbarEnabled() {
        Parcel zzH = zzH(19, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isMyLocationButtonEnabled() {
        Parcel zzH = zzH(11, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isRotateGesturesEnabled() {
        Parcel zzH = zzH(15, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isScrollGesturesEnabled() {
        Parcel zzH = zzH(12, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isScrollGesturesEnabledDuringRotateOrZoom() {
        Parcel zzH = zzH(21, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isTiltGesturesEnabled() {
        Parcel zzH = zzH(14, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isZoomControlsEnabled() {
        Parcel zzH = zzH(9, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final boolean isZoomGesturesEnabled() {
        Parcel zzH = zzH(13, zza());
        boolean zzh = zzc.zzh(zzH);
        zzH.recycle();
        return zzh;
    }

    public final void setAllGesturesEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(8, zza);
    }

    public final void setCompassEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(2, zza);
    }

    public final void setIndoorLevelPickerEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(16, zza);
    }

    public final void setMapToolbarEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(18, zza);
    }

    public final void setMyLocationButtonEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(3, zza);
    }

    public final void setRotateGesturesEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(7, zza);
    }

    public final void setScrollGesturesEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(4, zza);
    }

    public final void setScrollGesturesEnabledDuringRotateOrZoom(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(20, zza);
    }

    public final void setTiltGesturesEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(6, zza);
    }

    public final void setZoomControlsEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(1, zza);
    }

    public final void setZoomGesturesEnabled(boolean z) {
        Parcel zza = zza();
        zzc.zzd(zza, z);
        zzc(5, zza);
    }
}
