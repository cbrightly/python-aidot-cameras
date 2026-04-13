package com.google.android.gms.measurement.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzgb extends zzgy {
    /* access modifiers changed from: private */
    public static final AtomicLong zza = new AtomicLong(Long.MIN_VALUE);
    /* access modifiers changed from: private */
    @Nullable
    public zzga zzb;
    /* access modifiers changed from: private */
    @Nullable
    public zzga zzc;
    private final PriorityBlockingQueue zzd = new PriorityBlockingQueue();
    private final BlockingQueue zze = new LinkedBlockingQueue();
    private final Thread.UncaughtExceptionHandler zzf = new zzfy(this, "Thread death: Uncaught exception on worker thread");
    private final Thread.UncaughtExceptionHandler zzg = new zzfy(this, "Thread death: Uncaught exception on network thread");
    /* access modifiers changed from: private */
    public final Object zzh = new Object();
    /* access modifiers changed from: private */
    public final Semaphore zzi = new Semaphore(2);
    /* access modifiers changed from: private */
    public volatile boolean zzj;

    zzgb(zzge zzge) {
        super(zzge);
    }

    private final void zzt(zzfz zzfz) {
        synchronized (this.zzh) {
            this.zzd.add(zzfz);
            zzga zzga = this.zzb;
            if (zzga == null) {
                zzga zzga2 = new zzga(this, "Measurement Worker", this.zzd);
                this.zzb = zzga2;
                zzga2.setUncaughtExceptionHandler(this.zzf);
                this.zzb.start();
            } else {
                zzga.zza();
            }
        }
    }

    public final void zzaz() {
        if (Thread.currentThread() != this.zzc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final Object zzd(AtomicReference atomicReference, long j, String str, Runnable runnable) {
        synchronized (atomicReference) {
            this.zzt.zzaB().zzp(runnable);
            try {
                atomicReference.wait(j);
            } catch (InterruptedException e) {
                zzes zzk = this.zzt.zzaA().zzk();
                zzk.zza("Interrupted waiting for " + str);
                return null;
            }
        }
        Object obj = atomicReference.get();
        if (obj == null) {
            this.zzt.zzaA().zzk().zza("Timed out waiting for ".concat(str));
        }
        return obj;
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return false;
    }

    public final void zzg() {
        if (Thread.currentThread() != this.zzb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final Future zzh(Callable callable) {
        zzv();
        Preconditions.checkNotNull(callable);
        zzfz zzfz = new zzfz(this, callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            if (!this.zzd.isEmpty()) {
                this.zzt.zzaA().zzk().zza("Callable skipped the worker queue.");
            }
            zzfz.run();
        } else {
            zzt(zzfz);
        }
        return zzfz;
    }

    public final Future zzi(Callable callable) {
        zzv();
        Preconditions.checkNotNull(callable);
        zzfz zzfz = new zzfz(this, callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            zzfz.run();
        } else {
            zzt(zzfz);
        }
        return zzfz;
    }

    public final void zzo(Runnable runnable) {
        zzv();
        Preconditions.checkNotNull(runnable);
        zzfz zzfz = new zzfz(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzh) {
            this.zze.add(zzfz);
            zzga zzga = this.zzc;
            if (zzga == null) {
                zzga zzga2 = new zzga(this, "Measurement Network", this.zze);
                this.zzc = zzga2;
                zzga2.setUncaughtExceptionHandler(this.zzg);
                this.zzc.start();
            } else {
                zzga.zza();
            }
        }
    }

    public final void zzp(Runnable runnable) {
        zzv();
        Preconditions.checkNotNull(runnable);
        zzt(new zzfz(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzq(Runnable runnable) {
        zzv();
        Preconditions.checkNotNull(runnable);
        zzt(new zzfz(this, runnable, true, "Task exception on worker thread"));
    }

    public final boolean zzs() {
        return Thread.currentThread() == this.zzb;
    }
}
