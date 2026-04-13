package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.concurrent.Executor;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class GmsClientSupervisor {
    @Nullable
    @VisibleForTesting
    static HandlerThread zza;
    private static final Object zzb = new Object();
    @Nullable
    private static zzs zzc;
    @Nullable
    private static Executor zzd;
    private static boolean zze = false;

    @KeepForSdk
    public static int getDefaultBindFlags() {
        return 4225;
    }

    @NonNull
    @KeepForSdk
    public static GmsClientSupervisor getInstance(@NonNull Context context) {
        Looper looper;
        synchronized (zzb) {
            if (zzc == null) {
                Context applicationContext = context.getApplicationContext();
                if (zze) {
                    looper = getOrStartHandlerThread().getLooper();
                } else {
                    looper = context.getMainLooper();
                }
                zzc = new zzs(applicationContext, looper, zzd);
            }
        }
        return zzc;
    }

    @NonNull
    @KeepForSdk
    public static HandlerThread getOrStartHandlerThread() {
        synchronized (zzb) {
            HandlerThread handlerThread = zza;
            if (handlerThread != null) {
                return handlerThread;
            }
            HandlerThread handlerThread2 = new HandlerThread("GoogleApiHandler", 9);
            zza = handlerThread2;
            handlerThread2.start();
            HandlerThread handlerThread3 = zza;
            return handlerThread3;
        }
    }

    @KeepForSdk
    public static void setDefaultBindExecutor(@Nullable Executor executor) {
        synchronized (zzb) {
            zzs zzs = zzc;
            if (zzs != null) {
                zzs.zzi(executor);
            }
            zzd = executor;
        }
    }

    @KeepForSdk
    public static void setUseHandlerThreadForCallbacks() {
        synchronized (zzb) {
            zzs zzs = zzc;
            if (zzs != null && !zze) {
                zzs.zzj(getOrStartHandlerThread().getLooper());
            }
            zze = true;
        }
    }

    @KeepForSdk
    public boolean bindService(@NonNull ComponentName componentName, @NonNull ServiceConnection connection, @NonNull String realClientName) {
        return zzc(new zzo(componentName, 4225), connection, realClientName, (Executor) null);
    }

    @KeepForSdk
    public void unbindService(@NonNull ComponentName componentName, @NonNull ServiceConnection connection, @NonNull String realClientName) {
        zza(new zzo(componentName, 4225), connection, realClientName);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(zzo zzo, ServiceConnection serviceConnection, String str);

    public final void zzb(@NonNull String str, @NonNull String str2, int i, @NonNull ServiceConnection serviceConnection, @NonNull String str3, boolean z) {
        zza(new zzo(str, str2, 4225, z), serviceConnection, str3);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zzc(zzo zzo, ServiceConnection serviceConnection, String str, @Nullable Executor executor);

    @KeepForSdk
    public boolean bindService(@NonNull ComponentName componentName, @NonNull ServiceConnection connection, @NonNull String realClientName, @Nullable Executor executor) {
        return zzc(new zzo(componentName, 4225), connection, realClientName, executor);
    }

    @KeepForSdk
    public void unbindService(@NonNull String startServiceAction, @NonNull ServiceConnection connection, @NonNull String realClientName) {
        zza(new zzo(startServiceAction, 4225, false), connection, realClientName);
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public boolean bindService(@NonNull String startServiceAction, @NonNull ServiceConnection connection, @NonNull String realClientName) {
        return zzc(new zzo(startServiceAction, 4225, false), connection, realClientName, (Executor) null);
    }
}
