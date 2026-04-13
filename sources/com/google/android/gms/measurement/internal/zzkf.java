package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzcl;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzkf {
    private final Context zza;

    public zzkf(Context context) {
        Preconditions.checkNotNull(context);
        this.zza = context;
    }

    private final zzeu zzk() {
        return zzge.zzp(this.zza, (zzcl) null, (Long) null).zzaA();
    }

    @MainThread
    public final int zza(Intent intent, int i, int i2) {
        zzge zzp = zzge.zzp(this.zza, (zzcl) null, (Long) null);
        zzeu zzaA = zzp.zzaA();
        if (intent == null) {
            zzaA.zzk().zza("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzp.zzay();
        zzaA.zzj().zzc("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zzh(new zzkc(this, i2, zzaA, intent));
        }
        return 2;
    }

    @MainThread
    public final IBinder zzb(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzgw(zzlg.zzt(this.zza), (String) null);
        }
        zzk().zzk().zzb("onBind received unknown action", action);
        return null;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzc(int i, zzeu zzeu, Intent intent) {
        if (((zzke) this.zza).zzc(i)) {
            zzeu.zzj().zzb("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzk().zzj().zza("Completed wakeful intent.");
            ((zzke) this.zza).zza(intent);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzd(zzeu zzeu, JobParameters jobParameters) {
        zzeu.zzj().zza("AppMeasurementJobService processed last upload request.");
        ((zzke) this.zza).zzb(jobParameters, false);
    }

    @MainThread
    public final void zze() {
        zzge zzp = zzge.zzp(this.zza, (zzcl) null, (Long) null);
        zzeu zzaA = zzp.zzaA();
        zzp.zzay();
        zzaA.zzj().zza("Local AppMeasurementService is starting up");
    }

    @MainThread
    public final void zzf() {
        zzge zzp = zzge.zzp(this.zza, (zzcl) null, (Long) null);
        zzeu zzaA = zzp.zzaA();
        zzp.zzay();
        zzaA.zzj().zza("Local AppMeasurementService is shutting down");
    }

    @MainThread
    public final void zzg(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onRebind called with null intent");
            return;
        }
        zzk().zzj().zzb("onRebind called. action", intent.getAction());
    }

    public final void zzh(Runnable runnable) {
        zzlg zzt = zzlg.zzt(this.zza);
        zzt.zzaB().zzp(new zzkd(this, zzt, runnable));
    }

    @TargetApi(24)
    @MainThread
    public final boolean zzi(JobParameters jobParameters) {
        zzge zzp = zzge.zzp(this.zza, (zzcl) null, (Long) null);
        zzeu zzaA = zzp.zzaA();
        String string = jobParameters.getExtras().getString("action");
        zzp.zzay();
        zzaA.zzj().zzb("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zzh(new zzkb(this, zzaA, jobParameters));
        return true;
    }

    @MainThread
    public final boolean zzj(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onUnbind called with null intent");
            return true;
        }
        zzk().zzj().zzb("onUnbind called for intent. action", intent.getAction());
        return true;
    }
}
