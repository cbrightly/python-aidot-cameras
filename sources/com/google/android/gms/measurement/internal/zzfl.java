package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.MainThread;
import com.google.android.gms.internal.measurement.zzbq;
import com.google.android.gms.internal.measurement.zzbr;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfl implements ServiceConnection {
    final /* synthetic */ zzfm zza;
    /* access modifiers changed from: private */
    public final String zzb;

    zzfl(zzfm zzfm, String str) {
        this.zza = zzfm;
        this.zzb = str;
    }

    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder != null) {
            try {
                zzbr zzb2 = zzbq.zzb(iBinder);
                if (zzb2 == null) {
                    this.zza.zza.zzaA().zzk().zza("Install Referrer Service implementation was not found");
                    return;
                }
                this.zza.zza.zzaA().zzj().zza("Install Referrer Service connected");
                this.zza.zza.zzaB().zzp(new zzfk(this, zzb2, this));
            } catch (RuntimeException e) {
                this.zza.zza.zzaA().zzk().zzb("Exception occurred while calling Install Referrer API", e);
            }
        } else {
            this.zza.zza.zzaA().zzk().zza("Install Referrer connection returned with null binder");
        }
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        this.zza.zza.zzaA().zzj().zza("Install Referrer Service disconnected");
    }
}
