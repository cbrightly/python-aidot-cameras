package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IInterface;
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
public interface zzo extends IInterface {
    @Deprecated
    Location zzd();

    @Deprecated
    ICancelToken zze(CurrentLocationRequest currentLocationRequest, zzq zzq);

    @Deprecated
    LocationAvailability zzf(String str);

    void zzg(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzm zzm);

    void zzh(LocationSettingsRequest locationSettingsRequest, zzs zzs, String str);

    void zzi(zzk zzk);

    @Deprecated
    void zzj(LastLocationRequest lastLocationRequest, zzq zzq);

    void zzk(zzdb zzdb, LocationRequest locationRequest, IStatusCallback iStatusCallback);

    void zzl(PendingIntent pendingIntent, IStatusCallback iStatusCallback);

    void zzm(PendingIntent pendingIntent);

    void zzn(PendingIntent pendingIntent, zzm zzm, String str);

    void zzo(String[] strArr, zzm zzm, String str);

    void zzp(PendingIntent pendingIntent, IStatusCallback iStatusCallback);

    void zzq(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, IStatusCallback iStatusCallback);

    void zzr(long j, boolean z, PendingIntent pendingIntent);

    void zzs(zzb zzb, PendingIntent pendingIntent, IStatusCallback iStatusCallback);

    void zzt(PendingIntent pendingIntent, SleepSegmentRequest sleepSegmentRequest, IStatusCallback iStatusCallback);

    @Deprecated
    void zzu(Location location);

    void zzv(Location location, IStatusCallback iStatusCallback);

    @Deprecated
    void zzw(boolean z);

    void zzx(boolean z, IStatusCallback iStatusCallback);

    void zzy(zzdb zzdb, IStatusCallback iStatusCallback);

    @Deprecated
    void zzz(zzdf zzdf);
}
