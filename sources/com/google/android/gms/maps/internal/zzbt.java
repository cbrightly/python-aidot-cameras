package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzbt extends zza implements IProjectionDelegate {
    zzbt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IProjectionDelegate");
    }

    public final LatLng fromScreenLocation(IObjectWrapper iObjectWrapper) {
        Parcel zza = zza();
        zzc.zzg(zza, iObjectWrapper);
        Parcel zzH = zzH(1, zza);
        LatLng latLng = (LatLng) zzc.zza(zzH, LatLng.CREATOR);
        zzH.recycle();
        return latLng;
    }

    public final VisibleRegion getVisibleRegion() {
        Parcel zzH = zzH(3, zza());
        VisibleRegion visibleRegion = (VisibleRegion) zzc.zza(zzH, VisibleRegion.CREATOR);
        zzH.recycle();
        return visibleRegion;
    }

    public final IObjectWrapper toScreenLocation(LatLng latLng) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        Parcel zzH = zzH(2, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }
}
