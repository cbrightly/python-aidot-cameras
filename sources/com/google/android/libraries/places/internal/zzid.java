package com.google.android.libraries.places.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzid extends ViewModel {
    private final zzhq zza;
    private final zzig zzb;
    private final zzih zzc;
    private final Handler zzd = new Handler(Looper.getMainLooper());
    private Runnable zze;
    private final MutableLiveData zzf = new MutableLiveData();

    /* synthetic */ zzid(zzhq zzhq, zzig zzig, zzih zzih, zzic zzic) {
        this.zza = zzhq;
        this.zzb = zzig;
        this.zzc = zzih;
    }

    private static Status zzn(Exception exc) {
        if (exc instanceof ApiException) {
            return ((ApiException) exc).getStatus();
        }
        return new Status(13, exc.getMessage());
    }

    private final void zzo(zzhl zzhl) {
        if (!zzhl.equals(this.zzf.getValue())) {
            this.zzf.setValue(zzhl);
        }
    }

    private static boolean zzp(Status status) {
        return status.isCanceled() || status.getStatusCode() == 9012 || status.getStatusCode() == 9011;
    }

    public final LiveData zza() {
        return this.zzf;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(String str, Task task) {
        if (!task.isCanceled()) {
            Exception exception = task.getException();
            if (exception == null) {
                this.zzb.zzp();
                List<AutocompletePrediction> autocompletePredictions = ((FindAutocompletePredictionsResponse) task.getResult()).getAutocompletePredictions();
                if (autocompletePredictions.isEmpty()) {
                    zzo(zzhl.zzh(str));
                } else {
                    zzo(zzhl.zzj(autocompletePredictions));
                }
            } else {
                this.zzb.zzr();
                Status zzn = zzn(exception);
                if (zzp(zzn)) {
                    zzo(zzhl.zzq(zzn));
                } else {
                    zzo(zzhl.zzi(str, zzn));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzc(AutocompletePrediction autocompletePrediction, Task task) {
        if (!task.isCanceled()) {
            Exception exception = task.getException();
            if (exception == null) {
                this.zzb.zzq();
                zzo(zzhl.zzn(((FetchPlaceResponse) task.getResult()).getPlace()));
                return;
            }
            this.zzb.zzs();
            Status zzn = zzn(exception);
            if (zzp(zzn)) {
                zzo(zzhl.zzq(zzn));
            } else {
                zzo(zzhl.zzm(autocompletePrediction, zzn));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzd(String str) {
        this.zza.zzb(str).addOnCompleteListener(new zzhz(this, str));
    }

    public final void zze(@Nullable Bundle bundle) {
        if (bundle == null) {
            this.zzf.setValue(zzhl.zzo());
        }
    }

    public final void zzf(AutocompletePrediction autocompletePrediction, int i) {
        this.zzb.zzu(i);
        Task zza2 = this.zza.zza(autocompletePrediction);
        if (!zza2.isComplete()) {
            zzo(zzhl.zzg());
        }
        zza2.addOnCompleteListener(new zzhy(this, autocompletePrediction));
    }

    public final void zzg() {
        this.zzb.zzv();
    }

    public final void zzh() {
        this.zzb.zzl();
    }

    public final void zzi() {
        this.zzb.zzm();
    }

    public final void zzj() {
        this.zzb.zzn();
        zzo(zzhl.zzl());
    }

    public final void zzk() {
        this.zzb.zzw();
        zzm("");
    }

    public final void zzl(String str) {
        this.zza.zzc();
        zzm(str);
        zzo(zzhl.zzp());
    }

    public final void zzm(String str) {
        this.zzb.zzt(str);
        this.zzd.removeCallbacks(this.zze);
        if (str.isEmpty()) {
            this.zza.zzc();
            zzo(zzhl.zzk());
            return;
        }
        zzia zzia = new zzia(this, str);
        this.zze = zzia;
        this.zzd.postDelayed(zzia, 100);
        zzo(zzhl.zzg());
    }

    /* access modifiers changed from: protected */
    public final void onCleared() {
        try {
            this.zza.zzc();
            this.zzd.removeCallbacks(this.zze);
            this.zzb.zzo();
            this.zzc.zza(this.zzb);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
