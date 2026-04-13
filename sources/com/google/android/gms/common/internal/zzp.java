package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.StrictMode;
import androidx.annotation.Nullable;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class zzp implements ServiceConnection, zzt {
    final /* synthetic */ zzs zza;
    private final Map zzb = new HashMap();
    private int zzc = 2;
    private boolean zzd;
    @Nullable
    private IBinder zze;
    private final zzo zzf;
    private ComponentName zzg;

    public zzp(zzs zzs, zzo zzo) {
        this.zza = zzs;
        this.zzf = zzo;
    }

    public final void onBindingDied(ComponentName componentName) {
        onServiceDisconnected(componentName);
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zza.zzb) {
            this.zza.zzd.removeMessages(1, this.zzf);
            this.zze = iBinder;
            this.zzg = componentName;
            for (ServiceConnection onServiceConnected : this.zzb.values()) {
                onServiceConnected.onServiceConnected(componentName, iBinder);
            }
            this.zzc = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zza.zzb) {
            this.zza.zzd.removeMessages(1, this.zzf);
            this.zze = null;
            this.zzg = componentName;
            for (ServiceConnection onServiceDisconnected : this.zzb.values()) {
                onServiceDisconnected.onServiceDisconnected(componentName);
            }
            this.zzc = 2;
        }
    }

    public final int zza() {
        return this.zzc;
    }

    public final ComponentName zzb() {
        return this.zzg;
    }

    @Nullable
    public final IBinder zzc() {
        return this.zze;
    }

    public final void zzd(ServiceConnection serviceConnection, ServiceConnection serviceConnection2, String str) {
        this.zzb.put(serviceConnection, serviceConnection2);
    }

    public final void zze(String str, @Nullable Executor executor) {
        this.zzc = 3;
        StrictMode.VmPolicy vmPolicy = StrictMode.getVmPolicy();
        if (PlatformVersion.isAtLeastS()) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(vmPolicy).permitUnsafeIntentLaunch().build());
        }
        try {
            zzs zzs = this.zza;
            boolean zza2 = zzs.zzf.zza(zzs.zzc, str, this.zzf.zzb(zzs.zzc), this, 4225, executor);
            this.zzd = zza2;
            if (zza2) {
                this.zza.zzd.sendMessageDelayed(this.zza.zzd.obtainMessage(1, this.zzf), this.zza.zzh);
            } else {
                this.zzc = 2;
                try {
                    zzs zzs2 = this.zza;
                    zzs2.zzf.unbindService(zzs2.zzc, this);
                } catch (IllegalArgumentException e) {
                }
            }
        } finally {
            StrictMode.setVmPolicy(vmPolicy);
        }
    }

    public final void zzf(ServiceConnection serviceConnection, String str) {
        this.zzb.remove(serviceConnection);
    }

    public final void zzg(String str) {
        this.zza.zzd.removeMessages(1, this.zzf);
        zzs zzs = this.zza;
        zzs.zzf.unbindService(zzs.zzc, this);
        this.zzd = false;
        this.zzc = 2;
    }

    public final boolean zzh(ServiceConnection serviceConnection) {
        return this.zzb.containsKey(serviceConnection);
    }

    public final boolean zzi() {
        return this.zzb.isEmpty();
    }

    public final boolean zzj() {
        return this.zzd;
    }
}
