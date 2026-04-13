package com.google.android.libraries.places.internal;

import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import java.util.Locale;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdf implements zzgj {
    private final zzgr zza;
    private final zzgv zzb;
    private final zzgk zzc;

    zzdf(zzgv zzgv, zzgr zzgr, zzgk zzgk) {
        this.zzb = zzgv;
        this.zza = zzgr;
        this.zzc = zzgk;
    }

    @VisibleForTesting
    static final int zzi(Task task) {
        ApiException apiException;
        if (task.isSuccessful()) {
            return 2;
        }
        Exception exception = task.getException();
        if (exception != null) {
            if (exception instanceof ApiException) {
                apiException = (ApiException) exception;
            } else {
                apiException = new ApiException(new Status(13, exception.getMessage()));
            }
            switch (apiException.getStatusCode()) {
                case 7:
                    return 4;
                case 15:
                    return 3;
                default:
                    return 1;
            }
        } else {
            throw null;
        }
    }

    private final zzabw zzj() {
        Locale zzb2 = this.zzc.zzb();
        Locale locale = Locale.getDefault();
        zzabw zza2 = zzaby.zza();
        zza2.zzd(zzb2.toString());
        if (!zzb2.equals(locale)) {
            zza2.zzb(locale.toString());
        }
        return zza2;
    }

    private final void zzk(zzzf zzzf) {
        zzaah zzb2 = zzgw.zzb(this.zza);
        zzb2.zzl(16);
        zzb2.zze(zzzf);
        zzb2.zza(this.zzc.zza());
        zzl((zzaam) zzb2.zzq());
    }

    private final void zzl(zzaam zzaam) {
        this.zzb.zza(zzgw.zza(zzaam));
    }

    public final void zza(FetchPhotoRequest fetchPhotoRequest) {
        zzabo zza2 = zzabq.zza();
        zza2.zza(2);
        zzaah zzb2 = zzgw.zzb(this.zza);
        zzb2.zzl(5);
        zzb2.zzg((zzabq) zza2.zzq());
        zzb2.zza(this.zzc.zza());
        zzl((zzaam) zzb2.zzq());
    }

    public final void zzb(Task task, long j, long j2) {
        zzza zza2 = zzzf.zza();
        zza2.zzf(15);
        zza2.zze(zzi(task));
        zza2.zzd((int) (j2 - j));
        zzk((zzzf) zza2.zzq());
    }

    public final void zzc(FetchPlaceRequest fetchPlaceRequest) {
        zzaas zza2 = zzaat.zza();
        zza2.zza(1);
        zzabr zza3 = zzabs.zza();
        zza3.zza(zzfv.zzb(fetchPlaceRequest.getPlaceFields()));
        zza2.zzb((zzabs) zza3.zzq());
        zzabw zzj = zzj();
        zzj.zze(5);
        zzj.zzc((zzaat) zza2.zzq());
        zzaah zzb2 = zzgw.zzb(this.zza);
        zzb2.zzl(1);
        zzb2.zzh((zzaby) zzj.zzq());
        zzb2.zza(this.zzc.zza());
        AutocompleteSessionToken sessionToken = fetchPlaceRequest.getSessionToken();
        if (sessionToken != null) {
            zzb2.zzj(sessionToken.toString());
        }
        zzl((zzaam) zzb2.zzq());
    }

    public final void zzd(Task task, long j, long j2) {
        boolean isSuccessful = task.isSuccessful();
        zzys zza2 = zzyt.zza();
        zza2.zza(1);
        zza2.zzb(isSuccessful ? 1 : 0);
        zzza zza3 = zzzf.zza();
        zza3.zzf(8);
        zza3.zzc((zzyt) zza2.zzq());
        zza3.zze(zzi(task));
        zza3.zzd((int) (j2 - j));
        zzk((zzzf) zza3.zzq());
    }

    public final void zze(FindAutocompletePredictionsRequest findAutocompletePredictionsRequest) {
        zzzj zza2 = zzzk.zza();
        TypeFilter typeFilter = findAutocompletePredictionsRequest.getTypeFilter();
        if (typeFilter != null) {
            zza2.zza(zzfw.zza(typeFilter));
        }
        zzzk zzzk = (zzzk) zza2.zzq();
        zzzv zza3 = zzzw.zza();
        if (zzzk != null) {
            zza3.zza(zzzk);
        }
        zzabw zzj = zzj();
        zzj.zze(6);
        zzj.zza((zzzw) zza3.zzq());
        zzaah zzb2 = zzgw.zzb(this.zza);
        zzb2.zzl(1);
        zzb2.zzh((zzaby) zzj.zzq());
        zzb2.zza(this.zzc.zza());
        AutocompleteSessionToken sessionToken = findAutocompletePredictionsRequest.getSessionToken();
        if (sessionToken != null) {
            zzb2.zzj(sessionToken.toString());
        }
        zzl((zzaam) zzb2.zzq());
    }

    public final void zzf(Task task, long j, long j2) {
        int i;
        if (task.isSuccessful()) {
            i = ((FindAutocompletePredictionsResponse) task.getResult()).getAutocompletePredictions().size();
        } else {
            i = 0;
        }
        zzyn zza2 = zzyo.zza();
        zza2.zza(i);
        zzza zza3 = zzzf.zza();
        zza3.zzf(6);
        zza3.zzb((zzyo) zza2.zzq());
        zza3.zze(zzi(task));
        zza3.zzd((int) (j2 - j));
        zzk((zzzf) zza3.zzq());
    }

    public final void zzg(FindCurrentPlaceRequest findCurrentPlaceRequest, Task task, long j, long j2) {
        boolean isSuccessful = task.isSuccessful();
        zzaay zza2 = zzaba.zza();
        zzabr zza3 = zzabs.zza();
        zza3.zza(zzfv.zzb(findCurrentPlaceRequest.getPlaceFields()));
        zza2.zzb((zzabs) zza3.zzq());
        zza2.zza((int) (j2 - j));
        int i = 1;
        if (true == isSuccessful) {
            i = 2;
        }
        zza2.zzc(i);
        zzaah zzb2 = zzgw.zzb(this.zza);
        zzb2.zzl(6);
        zzb2.zzd((zzaba) zza2.zzq());
        zzb2.zza(this.zzc.zza());
        zzl((zzaam) zzb2.zzq());
    }

    public final void zzh(Task task, long j, long j2) {
        int i;
        if (task.isSuccessful()) {
            i = ((FindCurrentPlaceResponse) task.getResult()).getPlaceLikelihoods().size();
        } else {
            i = 0;
        }
        zzyg zza2 = zzyh.zza();
        zza2.zza(i);
        zzza zza3 = zzzf.zza();
        zza3.zzf(4);
        zza3.zza((zzyh) zza2.zzq());
        zza3.zze(zzi(task));
        zza3.zzd((int) (j2 - j));
        zzk((zzzf) zza3.zzq());
    }
}
