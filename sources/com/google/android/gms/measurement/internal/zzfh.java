package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzfh {
    final String zza;
    final /* synthetic */ zzfj zzb;
    private final String zzc;
    private final String zzd;
    private final long zze;

    /* synthetic */ zzfh(zzfj zzfj, String str, long j, zzfg zzfg) {
        boolean z;
        this.zzb = zzfj;
        Preconditions.checkNotEmpty("health_monitor");
        if (j > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.zza = "health_monitor:start";
        this.zzc = "health_monitor:count";
        this.zzd = "health_monitor:value";
        this.zze = j;
    }

    @WorkerThread
    private final long zzc() {
        return this.zzb.zza().getLong(this.zza, 0);
    }

    @WorkerThread
    private final void zzd() {
        this.zzb.zzg();
        long currentTimeMillis = this.zzb.zzt.zzax().currentTimeMillis();
        SharedPreferences.Editor edit = this.zzb.zza().edit();
        edit.remove(this.zzc);
        edit.remove(this.zzd);
        edit.putLong(this.zza, currentTimeMillis);
        edit.apply();
    }

    @WorkerThread
    public final Pair zza() {
        long j;
        this.zzb.zzg();
        this.zzb.zzg();
        long zzc2 = zzc();
        if (zzc2 == 0) {
            zzd();
            j = 0;
        } else {
            j = Math.abs(zzc2 - this.zzb.zzt.zzax().currentTimeMillis());
        }
        long j2 = this.zze;
        if (j < j2) {
            return null;
        }
        if (j > j2 + j2) {
            zzd();
            return null;
        }
        String string = this.zzb.zza().getString(this.zzd, (String) null);
        long j3 = this.zzb.zza().getLong(this.zzc, 0);
        zzd();
        if (string == null || j3 <= 0) {
            return zzfj.zza;
        }
        return new Pair(string, Long.valueOf(j3));
    }

    @WorkerThread
    public final void zzb(String str, long j) {
        this.zzb.zzg();
        if (zzc() == 0) {
            zzd();
        }
        if (str == null) {
            str = "";
        }
        long j2 = this.zzb.zza().getLong(this.zzc, 0);
        if (j2 <= 0) {
            SharedPreferences.Editor edit = this.zzb.zza().edit();
            edit.putString(this.zzd, str);
            edit.putLong(this.zzc, 1);
            edit.apply();
            return;
        }
        long nextLong = this.zzb.zzt.zzv().zzG().nextLong() & DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        long j3 = j2 + 1;
        long j4 = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE / j3;
        SharedPreferences.Editor edit2 = this.zzb.zza().edit();
        if (nextLong < j4) {
            edit2.putString(this.zzd, str);
        }
        edit2.putLong(this.zzc, j3);
        edit2.apply();
    }
}
