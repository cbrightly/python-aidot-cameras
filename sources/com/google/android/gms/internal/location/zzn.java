package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SleepSegmentRequest;
import com.google.android.gms.location.zzb;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzn extends zza implements zzo {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    public final Location zzd() {
        Parcel zzb = zzb(7, zza());
        Location location = (Location) zzc.zza(zzb, Location.CREATOR);
        zzb.recycle();
        return location;
    }

    public final ICancelToken zze(CurrentLocationRequest currentLocationRequest, zzq zzq) {
        Parcel zza = zza();
        zzc.zzd(zza, currentLocationRequest);
        zzc.zze(zza, zzq);
        Parcel zzb = zzb(87, zza);
        ICancelToken asInterface = ICancelToken.Stub.asInterface(zzb.readStrongBinder());
        zzb.recycle();
        return asInterface;
    }

    public final LocationAvailability zzf(String str) {
        Parcel zza = zza();
        zza.writeString(str);
        Parcel zzb = zzb(34, zza);
        LocationAvailability locationAvailability = (LocationAvailability) zzc.zza(zzb, LocationAvailability.CREATOR);
        zzb.recycle();
        return locationAvailability;
    }

    public final void zzg(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzm zzm) {
        Parcel zza = zza();
        zzc.zzd(zza, geofencingRequest);
        zzc.zzd(zza, pendingIntent);
        zzc.zze(zza, zzm);
        zzc(57, zza);
    }

    public final void zzh(LocationSettingsRequest locationSettingsRequest, zzs zzs, String str) {
        Parcel zza = zza();
        zzc.zzd(zza, locationSettingsRequest);
        zzc.zze(zza, zzs);
        zza.writeString((String) null);
        zzc(63, zza);
    }

    public final void zzi(zzk zzk) {
        Parcel zza = zza();
        zzc.zze(zza, zzk);
        zzc(67, zza);
    }

    public final void zzj(LastLocationRequest lastLocationRequest, zzq zzq) {
        Parcel zza = zza();
        zzc.zzd(zza, lastLocationRequest);
        zzc.zze(zza, zzq);
        zzc(82, zza);
    }

    public final void zzk(zzdb zzdb, LocationRequest locationRequest, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, zzdb);
        zzc.zzd(zza, locationRequest);
        zzc.zze(zza, iStatusCallback);
        zzc(88, zza);
    }

    public final void zzl(PendingIntent pendingIntent, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, pendingIntent);
        zzc.zze(zza, iStatusCallback);
        zzc(73, zza);
    }

    public final void zzm(PendingIntent pendingIntent) {
        Parcel zza = zza();
        zzc.zzd(zza, pendingIntent);
        zzc(6, zza);
    }

    public final void zzn(PendingIntent pendingIntent, zzm zzm, String str) {
        Parcel zza = zza();
        zzc.zzd(zza, pendingIntent);
        zzc.zze(zza, zzm);
        zza.writeString(str);
        zzc(2, zza);
    }

    public final void zzo(String[] strArr, zzm zzm, String str) {
        Parcel zza = zza();
        zza.writeStringArray(strArr);
        zzc.zze(zza, zzm);
        zza.writeString(str);
        zzc(3, zza);
    }

    public final void zzp(PendingIntent pendingIntent, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, pendingIntent);
        zzc.zze(zza, iStatusCallback);
        zzc(69, zza);
    }

    public final void zzq(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, activityTransitionRequest);
        zzc.zzd(zza, pendingIntent);
        zzc.zze(zza, iStatusCallback);
        zzc(72, zza);
    }

    public final void zzr(long j, boolean z, PendingIntent pendingIntent) {
        Parcel zza = zza();
        zza.writeLong(j);
        zzc.zzc(zza, true);
        zzc.zzd(zza, pendingIntent);
        zzc(5, zza);
    }

    public final void zzs(zzb zzb, PendingIntent pendingIntent, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, zzb);
        zzc.zzd(zza, pendingIntent);
        zzc.zze(zza, iStatusCallback);
        zzc(70, zza);
    }

    public final void zzt(PendingIntent pendingIntent, SleepSegmentRequest sleepSegmentRequest, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, pendingIntent);
        zzc.zzd(zza, sleepSegmentRequest);
        zzc.zze(zza, iStatusCallback);
        zzc(79, zza);
    }

    public final void zzu(Location location) {
        Parcel zza = zza();
        zzc.zzd(zza, location);
        zzc(13, zza);
    }

    public final void zzv(Location location, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, location);
        zzc.zze(zza, iStatusCallback);
        zzc(85, zza);
    }

    public final void zzw(boolean z) {
        Parcel zza = zza();
        zzc.zzc(zza, z);
        zzc(12, zza);
    }

    public final void zzx(boolean z, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzc(zza, z);
        zzc.zze(zza, iStatusCallback);
        zzc(84, zza);
    }

    public final void zzy(zzdb zzdb, IStatusCallback iStatusCallback) {
        Parcel zza = zza();
        zzc.zzd(zza, zzdb);
        zzc.zze(zza, iStatusCallback);
        zzc(89, zza);
    }

    public final void zzz(zzdf zzdf) {
        Parcel zza = zza();
        zzc.zzd(zza, zzdf);
        zzc(59, zza);
    }
}
