package com.google.android.libraries.places.internal;

import android.location.Location;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.zzbq;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.IsOpenRequest;
import com.google.android.libraries.places.api.net.IsOpenResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzeg implements PlacesClient {
    private final zzdi zza;
    private final zzcy zzb;
    private final zzdd zzc;
    private final zzgj zzd;
    private final zzcn zze;

    zzeg(zzdi zzdi, zzcy zzcy, zzdd zzdd, zzgj zzgj, zzcn zzcn) {
        this.zza = zzdi;
        this.zzb = zzcy;
        this.zzc = zzdd;
        this.zzd = zzgj;
        this.zze = zzcn;
    }

    private static void zzh(zzcv zzcv, @Nullable zzcw zzcw) {
        zzcv.zza(zzcv, zzcv.zzb("Duration"));
        zzcs.zza();
        zzcs.zza();
        zzcv.zza(zzcv, zzcv.zzb("Battery"));
        zzcs.zza();
    }

    public final Task<FetchPhotoResponse> fetchPhoto(FetchPhotoRequest fetchPhotoRequest) {
        try {
            zziy.zzc(fetchPhotoRequest, "Request must not be null.");
            zzcs.zza();
            return this.zza.zza(fetchPhotoRequest).continueWith(new zzdy(this, fetchPhotoRequest, zzcw.zza())).continueWithTask(zzdz.zza);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final Task<FetchPlaceResponse> fetchPlace(FetchPlaceRequest fetchPlaceRequest) {
        try {
            zziy.zzc(fetchPlaceRequest, "Request must not be null.");
            zzcs.zza();
            return this.zza.zzb(fetchPlaceRequest).continueWith(new zzee(this, fetchPlaceRequest, zzcw.zza())).continueWithTask(zzdz.zza);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final Task<FindAutocompletePredictionsResponse> findAutocompletePredictions(FindAutocompletePredictionsRequest findAutocompletePredictionsRequest) {
        try {
            zziy.zzc(findAutocompletePredictionsRequest, "Request must not be null.");
            zzcs.zza();
            return this.zza.zzc(findAutocompletePredictionsRequest).continueWith(new zzef(this, findAutocompletePredictionsRequest, zzcw.zza())).continueWithTask(zzdz.zza);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final Task<FindCurrentPlaceResponse> findCurrentPlace(FindCurrentPlaceRequest findCurrentPlaceRequest) {
        return zza(findCurrentPlaceRequest, (String) null);
    }

    public final Task<IsOpenResponse> isOpen(IsOpenRequest isOpenRequest) {
        List list;
        try {
            zziy.zzc(isOpenRequest, "Request must not be null.");
            Place place = isOpenRequest.getPlace();
            String placeId = isOpenRequest.getPlaceId();
            long utcTimeMillis = isOpenRequest.getUtcTimeMillis();
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            if (place == null) {
                int i = zzbq.zza;
                list = Arrays.asList(new Place.Field[]{Place.Field.BUSINESS_STATUS, Place.Field.CURRENT_OPENING_HOURS, Place.Field.OPENING_HOURS, Place.Field.UTC_OFFSET});
            } else {
                list = new ArrayList();
                Place.BusinessStatus businessStatus = place.getBusinessStatus();
                if (businessStatus == null || businessStatus == Place.BusinessStatus.OPERATIONAL) {
                    if (businessStatus == null) {
                        list.add(Place.Field.BUSINESS_STATUS);
                    }
                    if (place.getCurrentOpeningHours() == null) {
                        list.add(Place.Field.CURRENT_OPENING_HOURS);
                    }
                    if (place.getOpeningHours() == null) {
                        list.add(Place.Field.OPENING_HOURS);
                    }
                    if (place.getUtcOffsetMinutes() == null) {
                        list.add(Place.Field.UTC_OFFSET);
                    }
                }
            }
            if (!list.isEmpty()) {
                if (place != null) {
                    placeId = place.getId();
                }
                if (placeId != null) {
                    FetchPlaceRequest.Builder builder = FetchPlaceRequest.builder(placeId, list);
                    builder.setCancellationToken(isOpenRequest.getCancellationToken());
                    FetchPlaceRequest build = builder.build();
                    zzcs.zza();
                    return this.zza.zzb(build).continueWith(new zzea(this, build, zzcw.zza())).onSuccessTask(new zzeb(place, utcTimeMillis, taskCompletionSource)).continueWithTask(zzdz.zza);
                }
                throw null;
            } else if (place != null) {
                taskCompletionSource.setResult(IsOpenResponse.newInstance(zzbq.zza(place, utcTimeMillis)));
                return taskCompletionSource.getTask();
            } else {
                throw null;
            }
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final Task zza(FindCurrentPlaceRequest findCurrentPlaceRequest, @Nullable String str) {
        try {
            zziy.zzc(findCurrentPlaceRequest, "Request must not be null.");
            long zza2 = this.zze.zza();
            zzcs.zza();
            return this.zzb.zza(findCurrentPlaceRequest.getCancellationToken()).onSuccessTask(new zzec(this, findCurrentPlaceRequest, (String) null)).continueWith(new zzed(this, findCurrentPlaceRequest, zza2, zzcw.zza())).continueWithTask(zzdz.zza);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Task zzb(FindCurrentPlaceRequest findCurrentPlaceRequest, String str, Location location) {
        zziy.zzc(location, "Location must not be null.");
        return this.zza.zzd(findCurrentPlaceRequest, location, this.zzc.zza((String) null));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FetchPhotoResponse zzc(FetchPhotoRequest fetchPhotoRequest, zzcw zzcw, Task task) {
        this.zzd.zza(fetchPhotoRequest);
        zzh(zzcv.zzb("FetchPhoto"), zzcw);
        return (FetchPhotoResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FetchPlaceResponse zzd(FetchPlaceRequest fetchPlaceRequest, zzcw zzcw, Task task) {
        this.zzd.zzc(fetchPlaceRequest);
        zzh(zzcv.zzb("FetchPlace"), zzcw);
        return (FetchPlaceResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FetchPlaceResponse zze(FetchPlaceRequest fetchPlaceRequest, zzcw zzcw, Task task) {
        this.zzd.zzc(fetchPlaceRequest);
        zzh(zzcv.zzb("IsOpenFetchPlace"), zzcw);
        return (FetchPlaceResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FindAutocompletePredictionsResponse zzf(FindAutocompletePredictionsRequest findAutocompletePredictionsRequest, zzcw zzcw, Task task) {
        this.zzd.zze(findAutocompletePredictionsRequest);
        zzh(zzcv.zzb("FindAutocompletePredictions"), zzcw);
        return (FindAutocompletePredictionsResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FindCurrentPlaceResponse zzg(FindCurrentPlaceRequest findCurrentPlaceRequest, long j, zzcw zzcw, Task task) {
        this.zzd.zzg(findCurrentPlaceRequest, task, j, this.zze.zza());
        zzh(zzcv.zzb("FindCurrentPlace"), zzcw);
        return (FindCurrentPlaceResponse) task.getResult();
    }
}
