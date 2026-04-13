package com.google.android.gms.internal.vision;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.vision.L;
import javax.annotation.concurrent.GuardedBy;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class zzt<T> {
    private final Context zza;
    private final Object zzb = new Object();
    private final String zzc;
    private final String zzd;
    private final String zze;
    private boolean zzf = false;
    private boolean zzg = false;
    @GuardedBy("lock")
    @Nullable
    private T zzh;

    public zzt(Context context, String str, String str2) {
        this.zza = context;
        this.zzc = str;
        String valueOf = String.valueOf("com.google.android.gms.vision.dynamite.");
        String valueOf2 = String.valueOf(str2);
        this.zzd = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        this.zze = str2;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract T zza(DynamiteModule dynamiteModule, Context context);

    /* access modifiers changed from: protected */
    public abstract void zza();

    public final boolean zzb() {
        return zzd() != null;
    }

    public final void zzc() {
        synchronized (this.zzb) {
            if (this.zzh != null) {
                try {
                    zza();
                } catch (RemoteException e) {
                    Log.e(this.zzc, "Could not finalize native handle", e);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @RequiresNonNull({"context", "thickFeatureName", "featureName"})
    @Nullable
    public final T zzd() {
        synchronized (this.zzb) {
            T t = this.zzh;
            if (t != null) {
                return t;
            }
            DynamiteModule dynamiteModule = null;
            try {
                dynamiteModule = DynamiteModule.load(this.zza, DynamiteModule.PREFER_HIGHEST_OR_REMOTE_VERSION, this.zzd);
            } catch (DynamiteModule.LoadingException e) {
                String format = String.format("%s.%s", new Object[]{"com.google.android.gms.vision", this.zze});
                L.d("Cannot load thick client module, fall back to load optional module %s", format);
                try {
                    dynamiteModule = DynamiteModule.load(this.zza, DynamiteModule.PREFER_REMOTE, format);
                } catch (DynamiteModule.LoadingException e2) {
                    L.e(e2, "Error loading optional module %s", format);
                    if (!this.zzf) {
                        L.d("Broadcasting download intent for dependency %s", this.zze);
                        String str = this.zze;
                        Intent intent = new Intent();
                        intent.setClassName("com.google.android.gms", "com.google.android.gms.vision.DependencyBroadcastReceiverProxy");
                        intent.putExtra("com.google.android.gms.vision.DEPENDENCIES", str);
                        intent.setAction("com.google.android.gms.vision.DEPENDENCY");
                        this.zza.sendBroadcast(intent);
                        this.zzf = true;
                    }
                }
            }
            if (dynamiteModule != null) {
                try {
                    this.zzh = zza(dynamiteModule, this.zza);
                } catch (RemoteException | DynamiteModule.LoadingException e3) {
                    Log.e(this.zzc, "Error creating remote native handle", e3);
                }
            }
            boolean z = this.zzg;
            if (!z && this.zzh == null) {
                Log.w(this.zzc, "Native handle not yet available. Reverting to no-op handle.");
                this.zzg = true;
            } else if (z && this.zzh != null) {
                Log.w(this.zzc, "Native handle is now available.");
            }
            T t2 = this.zzh;
            return t2;
        }
    }
}
