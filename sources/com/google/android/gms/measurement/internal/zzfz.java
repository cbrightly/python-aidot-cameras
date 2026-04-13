package com.google.android.gms.measurement.internal;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzfz extends FutureTask implements Comparable {
    final boolean zza;
    final /* synthetic */ zzgb zzb;
    private final long zzc;
    private final String zzd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzfz(zzgb zzgb, Runnable runnable, boolean z, String str) {
        super(runnable, (Object) null);
        this.zzb = zzgb;
        Preconditions.checkNotNull(str);
        long andIncrement = zzgb.zza.getAndIncrement();
        this.zzc = andIncrement;
        this.zzd = str;
        this.zza = z;
        if (andIncrement == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            zzgb.zzt.zzaA().zzd().zza("Tasks index overflow");
        }
    }

    public final /* bridge */ /* synthetic */ int compareTo(@NonNull Object obj) {
        zzfz zzfz = (zzfz) obj;
        boolean z = this.zza;
        if (z != zzfz.zza) {
            return !z ? 1 : -1;
        }
        int i = (this.zzc > zzfz.zzc ? 1 : (this.zzc == zzfz.zzc ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        if (i > 0) {
            return 1;
        }
        this.zzb.zzt.zzaA().zzh().zzb("Two tasks share the same index. index", Long.valueOf(this.zzc));
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void setException(Throwable th) {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
        this.zzb.zzt.zzaA().zzd().zzb(this.zzd, th);
        if ((th instanceof zzfx) && (defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()) != null) {
            defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzfz(zzgb zzgb, Callable callable, boolean z, String str) {
        super(callable);
        this.zzb = zzgb;
        Preconditions.checkNotNull("Task exception on worker thread");
        long andIncrement = zzgb.zza.getAndIncrement();
        this.zzc = andIncrement;
        this.zzd = "Task exception on worker thread";
        this.zza = z;
        if (andIncrement == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
            zzgb.zzt.zzaA().zzd().zza("Tasks index overflow");
        }
    }
}
