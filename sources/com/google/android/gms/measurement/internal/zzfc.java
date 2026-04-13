package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfc extends BroadcastReceiver {
    @VisibleForTesting
    static final String zza = zzfc.class.getName();
    /* access modifiers changed from: private */
    public final zzlg zzb;
    private boolean zzc;
    private boolean zzd;

    zzfc(zzlg zzlg) {
        Preconditions.checkNotNull(zzlg);
        this.zzb = zzlg;
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        this.zzb.zzB();
        String action = intent.getAction();
        this.zzb.zzaA().zzj().zzb("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zza2 = this.zzb.zzl().zza();
            if (this.zzd != zza2) {
                this.zzd = zza2;
                this.zzb.zzaB().zzp(new zzfb(this, zza2));
                return;
            }
            return;
        }
        this.zzb.zzaA().zzk().zzb("NetworkBroadcastReceiver received unknown action", action);
    }

    @WorkerThread
    public final void zzb() {
        this.zzb.zzB();
        this.zzb.zzaB().zzg();
        if (!this.zzc) {
            this.zzb.zzaw().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.zzd = this.zzb.zzl().zza();
            this.zzb.zzaA().zzj().zzb("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzd));
            this.zzc = true;
        }
    }

    @WorkerThread
    public final void zzc() {
        this.zzb.zzB();
        this.zzb.zzaB().zzg();
        this.zzb.zzaB().zzg();
        if (this.zzc) {
            this.zzb.zzaA().zzj().zza("Unregistering connectivity change receiver");
            this.zzc = false;
            this.zzd = false;
            try {
                this.zzb.zzaw().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzb.zzaA().zzd().zzb("Failed to unregister the network broadcast receiver", e);
            }
        }
    }
}
