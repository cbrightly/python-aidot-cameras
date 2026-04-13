package com.google.android.gms.common.internal;

import android.util.Log;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class zzc {
    @Nullable
    private Object zza;
    private boolean zzb = false;
    final /* synthetic */ BaseGmsClient zzd;

    public zzc(BaseGmsClient baseGmsClient, Object obj) {
        this.zzd = baseGmsClient;
        this.zza = obj;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Object obj);

    /* access modifiers changed from: protected */
    public abstract void zzc();

    public final void zze() {
        Object obj;
        synchronized (this) {
            obj = this.zza;
            if (this.zzb) {
                String obj2 = toString();
                Log.w("GmsClient", "Callback proxy " + obj2 + " being reused. This is not safe.");
            }
        }
        if (obj != null) {
            try {
                zza(obj);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        synchronized (this) {
            this.zzb = true;
        }
        zzg();
    }

    public final void zzf() {
        synchronized (this) {
            this.zza = null;
        }
    }

    public final void zzg() {
        zzf();
        synchronized (this.zzd.zzt) {
            this.zzd.zzt.remove(this);
        }
    }
}
