package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.measurement.internal.zzke;
import com.google.android.gms.measurement.internal.zzkf;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class AppMeasurementService extends Service implements zzke {
    private zzkf zza;

    private final zzkf zzd() {
        if (this.zza == null) {
            this.zza = new zzkf(this);
        }
        return this.zza;
    }

    @MainThread
    @Nullable
    public IBinder onBind(@NonNull Intent intent) {
        return zzd().zzb(intent);
    }

    @MainThread
    public void onCreate() {
        super.onCreate();
        zzd().zze();
    }

    @MainThread
    public void onDestroy() {
        zzd().zzf();
        super.onDestroy();
    }

    @MainThread
    public void onRebind(@NonNull Intent intent) {
        zzd().zzg(intent);
    }

    @MainThread
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        zzd().zza(intent, flags, startId);
        return 2;
    }

    @MainThread
    public boolean onUnbind(@NonNull Intent intent) {
        zzd().zzj(intent);
        return true;
    }

    public final void zza(@NonNull Intent intent) {
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }

    public final void zzb(@NonNull JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }

    public final boolean zzc(int i) {
        return stopSelfResult(i);
    }
}
