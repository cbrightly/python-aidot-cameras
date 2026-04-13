package com.google.android.libraries.places.internal;

import android.location.Location;
import android.text.TextUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesStatusCodes;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfm implements zzdi {
    private final zzgx zza;
    private final zzdn zzb;
    private final zzdt zzc;
    private final zzgj zzd;
    private final zzcn zze;
    private final zzen zzf;
    private final zzer zzg;
    private final zzev zzh;
    private final zzez zzi;
    private final zzgk zzj;

    zzfm(zzgk zzgk, zzgx zzgx, zzdn zzdn, zzdt zzdt, zzgj zzgj, zzcn zzcn, zzen zzen, zzer zzer, zzev zzev, zzez zzez) {
        this.zzj = zzgk;
        this.zza = zzgx;
        this.zzb = zzdn;
        this.zzc = zzdt;
        this.zzd = zzgj;
        this.zze = zzcn;
        this.zzf = zzen;
        this.zzg = zzer;
        this.zzh = zzev;
        this.zzi = zzez;
    }

    static final /* synthetic */ FetchPlaceResponse zzi(Task task) {
        zzjq zzjq;
        zzeq zzeq = (zzeq) task.getResult();
        int zza2 = zzft.zza(zzeq.status);
        if (!PlacesStatusCodes.isError(zza2)) {
            zzfs zzfs = zzeq.result;
            String[] strArr = zzeq.htmlAttributions;
            if (strArr != null) {
                zzjq = zzjq.zzk(strArr);
            } else {
                zzjq = null;
            }
            return FetchPlaceResponse.newInstance(zzfp.zzf(zzfs, zzjq));
        }
        throw new ApiException(new Status(zza2, zzft.zzb(zzeq.status, zzeq.errorMessage)));
    }

    static final /* synthetic */ FindCurrentPlaceResponse zzj(Task task) {
        zzjq zzjq;
        zzey zzey = (zzey) task.getResult();
        int zza2 = zzft.zza(zzey.status);
        if (!PlacesStatusCodes.isError(zza2)) {
            ArrayList arrayList = new ArrayList();
            zzfr[] zzfrArr = zzey.predictions;
            if (zzfrArr != null) {
                int i = 0;
                while (i < zzfrArr.length) {
                    zzfr zzfr = zzfrArr[i];
                    if (zzfr.zza() != null) {
                        Double zzb2 = zzfr.zzb();
                        if (zzb2 != null) {
                            zzfs zza3 = zzfr.zza();
                            String[] strArr = zzey.htmlAttributions;
                            if (strArr != null) {
                                zzjq = zzjq.zzk(strArr);
                            } else {
                                zzjq = null;
                            }
                            arrayList.add(PlaceLikelihood.newInstance(zzfp.zzf(zza3, zzjq), zzb2.doubleValue()));
                            i++;
                        } else {
                            throw new ApiException(new Status(8, "Unexpected server error: PlaceLikelihood returned without a likelihood value"));
                        }
                    } else {
                        throw new ApiException(new Status(8, "Unexpected server error: PlaceLikelihood returned without a Place value"));
                    }
                }
            }
            return FindCurrentPlaceResponse.newInstance(arrayList);
        }
        throw new ApiException(new Status(zza2, zzft.zzb(zzey.status, zzey.errorMessage)));
    }

    public final Task zza(FetchPhotoRequest fetchPhotoRequest) {
        Integer maxWidth = fetchPhotoRequest.getMaxWidth();
        Integer maxHeight = fetchPhotoRequest.getMaxHeight();
        if (maxWidth == null && maxHeight == null) {
            return Tasks.forException(new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, "Must include max width or max height in request.")));
        }
        if (maxWidth != null && maxWidth.intValue() <= 0) {
            return Tasks.forException(new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, String.format("Max Width must not be < 1, but was: %d.", new Object[]{maxWidth}))));
        } else if (maxHeight == null || maxHeight.intValue() > 0) {
            String zza2 = this.zzj.zza();
            this.zzj.zze();
            return this.zzc.zzb(new zzej(fetchPhotoRequest, zza2, false, this.zza), new zzek()).continueWith(new zzfk(this)).continueWith(new zzfl(this, this.zze.zza()));
        } else {
            return Tasks.forException(new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, String.format("Max Height must not be < 1, but was: %d.", new Object[]{maxHeight}))));
        }
    }

    public final Task zzb(FetchPlaceRequest fetchPlaceRequest) {
        if (TextUtils.isEmpty(fetchPlaceRequest.getPlaceId())) {
            return Tasks.forException(new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, "Place ID must not be empty.")));
        }
        if (fetchPlaceRequest.getPlaceFields().isEmpty()) {
            return Tasks.forException(new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, "Place Fields must not be empty.")));
        }
        Locale zzb2 = this.zzj.zzb();
        String zza2 = this.zzj.zza();
        this.zzj.zze();
        return this.zzb.zza(new zzep(fetchPlaceRequest, zzb2, zza2, false, this.zza), zzeq.class).continueWith(new zzfg(this)).continueWith(new zzfh(this, this.zze.zza()));
    }

    public final Task zzc(FindAutocompletePredictionsRequest findAutocompletePredictionsRequest) {
        String query = findAutocompletePredictionsRequest.getQuery();
        if (query == null || TextUtils.isEmpty(query.trim())) {
            return Tasks.forResult(FindAutocompletePredictionsResponse.newInstance(zzjq.zzl()));
        }
        Locale zzb2 = this.zzj.zzb();
        String zza2 = this.zzj.zza();
        this.zzj.zze();
        return this.zzb.zza(new zzet(findAutocompletePredictionsRequest, zzb2, zza2, false, this.zza), zzeu.class).continueWith(new zzfe(this)).continueWith(new zzff(this, this.zze.zza()));
    }

    public final Task zzd(FindCurrentPlaceRequest findCurrentPlaceRequest, Location location, zzjq zzjq) {
        if (findCurrentPlaceRequest.getPlaceFields().isEmpty()) {
            return Tasks.forException(new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, "Place Fields must not be empty.")));
        }
        Locale zzb2 = this.zzj.zzb();
        String zza2 = this.zzj.zza();
        this.zzj.zze();
        return this.zzb.zza(new zzex(findCurrentPlaceRequest, location, zzjq, zzb2, zza2, false, this.zza), zzey.class).continueWith(new zzfi(this)).continueWith(new zzfj(this, this.zze.zza()));
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FetchPhotoResponse zze(long j, Task task) {
        this.zzd.zzb(task, j, this.zze.zza());
        return (FetchPhotoResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FetchPlaceResponse zzf(long j, Task task) {
        this.zzd.zzd(task, j, this.zze.zza());
        return (FetchPlaceResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FindAutocompletePredictionsResponse zzg(long j, Task task) {
        this.zzd.zzf(task, j, this.zze.zza());
        return (FindAutocompletePredictionsResponse) task.getResult();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ FindCurrentPlaceResponse zzh(long j, Task task) {
        this.zzd.zzh(task, j, this.zze.zza());
        return (FindCurrentPlaceResponse) task.getResult();
    }
}
