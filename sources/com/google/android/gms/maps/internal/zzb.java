package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zza;
import com.google.android.gms.internal.maps.zzc;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class zzb extends zza implements ICameraUpdateFactoryDelegate {
    zzb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
    }

    public final IObjectWrapper newCameraPosition(CameraPosition cameraPosition) {
        Parcel zza = zza();
        zzc.zze(zza, cameraPosition);
        Parcel zzH = zzH(7, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper newLatLng(LatLng latLng) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        Parcel zzH = zzH(8, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper newLatLngBounds(LatLngBounds latLngBounds, int i) {
        Parcel zza = zza();
        zzc.zze(zza, latLngBounds);
        zza.writeInt(i);
        Parcel zzH = zzH(10, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper newLatLngBoundsWithSize(LatLngBounds latLngBounds, int i, int i2, int i3) {
        Parcel zza = zza();
        zzc.zze(zza, latLngBounds);
        zza.writeInt(i);
        zza.writeInt(i2);
        zza.writeInt(i3);
        Parcel zzH = zzH(11, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper newLatLngZoom(LatLng latLng, float f) {
        Parcel zza = zza();
        zzc.zze(zza, latLng);
        zza.writeFloat(f);
        Parcel zzH = zzH(9, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper scrollBy(float f, float f2) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zza.writeFloat(f2);
        Parcel zzH = zzH(3, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper zoomBy(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        Parcel zzH = zzH(5, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper zoomByWithFocus(float f, int i, int i2) {
        Parcel zza = zza();
        zza.writeFloat(f);
        zza.writeInt(i);
        zza.writeInt(i2);
        Parcel zzH = zzH(6, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper zoomIn() {
        Parcel zzH = zzH(1, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper zoomOut() {
        Parcel zzH = zzH(2, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    public final IObjectWrapper zoomTo(float f) {
        Parcel zza = zza();
        zza.writeFloat(f);
        Parcel zzH = zzH(4, zza);
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }
}
