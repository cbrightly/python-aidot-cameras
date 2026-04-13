package com.google.android.gms.stats;

import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.util.WorkSourceUtil;
import com.google.android.gms.internal.stats.zzb;
import com.google.android.gms.internal.stats.zzc;
import com.google.android.gms.internal.stats.zzh;
import com.google.android.gms.internal.stats.zzi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.ThreadSafe;

@ShowFirstParty
@KeepForSdk
@ThreadSafe
/* compiled from: com.google.android.gms:play-services-stats@@17.0.1 */
public class WakeLock {
    private static final long zzb = TimeUnit.DAYS.toMillis(366);
    private static volatile ScheduledExecutorService zzc = null;
    private static final Object zzd = new Object();
    private static volatile zzd zze = new zzb();
    @GuardedBy("acquireReleaseLock")
    zzb zza;
    private final Object zzf = new Object();
    @GuardedBy("acquireReleaseLock")
    private final PowerManager.WakeLock zzg;
    @GuardedBy("acquireReleaseLock")
    private int zzh = 0;
    @GuardedBy("acquireReleaseLock")
    private Future<?> zzi;
    @GuardedBy("acquireReleaseLock")
    private long zzj;
    @GuardedBy("acquireReleaseLock")
    private final Set<zze> zzk = new HashSet();
    @GuardedBy("acquireReleaseLock")
    private boolean zzl = true;
    @GuardedBy("acquireReleaseLock")
    private int zzm;
    private Clock zzn = DefaultClock.getInstance();
    private WorkSource zzo;
    private final String zzp;
    private final String zzq;
    private final Context zzr;
    @GuardedBy("acquireReleaseLock")
    private final Map<String, zzc> zzs = new HashMap();
    private AtomicInteger zzt = new AtomicInteger(0);
    private final ScheduledExecutorService zzu;

    @KeepForSdk
    public WakeLock(@NonNull Context context, int levelAndFlags, @NonNull String wakeLockName) {
        String str;
        String packageName = context.getPackageName();
        Preconditions.checkNotNull(context, "WakeLock: context must not be null");
        Preconditions.checkNotEmpty(wakeLockName, "WakeLock: wakeLockName must not be empty");
        this.zzr = context.getApplicationContext();
        this.zzq = wakeLockName;
        this.zza = null;
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            String valueOf = String.valueOf(wakeLockName);
            if (valueOf.length() != 0) {
                str = "*gcore*:".concat(valueOf);
            } else {
                str = new String("*gcore*:");
            }
            this.zzp = str;
        } else {
            this.zzp = wakeLockName;
        }
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            StringBuilder sb = new StringBuilder(29);
            sb.append("expected a non-null reference", 0, 29);
            throw new zzi(sb.toString());
        }
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(levelAndFlags, wakeLockName);
        this.zzg = newWakeLock;
        if (WorkSourceUtil.hasWorkSourcePermission(context)) {
            WorkSource fromPackage = WorkSourceUtil.fromPackage(context, Strings.isEmptyOrWhitespace(packageName) ? context.getPackageName() : packageName);
            this.zzo = fromPackage;
            if (fromPackage != null) {
                zze(newWakeLock, fromPackage);
            }
        }
        ScheduledExecutorService scheduledExecutorService = zzc;
        if (scheduledExecutorService == null) {
            synchronized (zzd) {
                scheduledExecutorService = zzc;
                if (scheduledExecutorService == null) {
                    zzh.zza();
                    scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(Executors.newScheduledThreadPool(1));
                    zzc = scheduledExecutorService;
                }
            }
        }
        this.zzu = scheduledExecutorService;
    }

    public static /* synthetic */ void zza(@NonNull WakeLock wakeLock) {
        synchronized (wakeLock.zzf) {
            if (wakeLock.isHeld()) {
                Log.e("WakeLock", String.valueOf(wakeLock.zzp).concat(" ** IS FORCE-RELEASED ON TIMEOUT **"));
                wakeLock.zzc();
                if (wakeLock.isHeld()) {
                    wakeLock.zzh = 1;
                    wakeLock.zzd(0);
                }
            }
        }
    }

    @GuardedBy("acquireReleaseLock")
    private final String zzb(String str) {
        if (!this.zzl || !TextUtils.isEmpty((CharSequence) null)) {
        }
        return null;
    }

    @GuardedBy("acquireReleaseLock")
    private final void zzc() {
        if (!this.zzk.isEmpty()) {
            ArrayList arrayList = new ArrayList(this.zzk);
            this.zzk.clear();
            if (arrayList.size() > 0) {
                zze zze2 = (zze) arrayList.get(0);
                throw null;
            }
        }
    }

    private final void zzd(int i) {
        synchronized (this.zzf) {
            if (isHeld()) {
                if (this.zzl) {
                    int i2 = this.zzh - 1;
                    this.zzh = i2;
                    if (i2 > 0) {
                        return;
                    }
                } else {
                    this.zzh = 0;
                }
                zzc();
                for (zzc zzc2 : this.zzs.values()) {
                    zzc2.zza = 0;
                }
                this.zzs.clear();
                Future<?> future = this.zzi;
                if (future != null) {
                    future.cancel(false);
                    this.zzi = null;
                    this.zzj = 0;
                }
                this.zzm = 0;
                if (this.zzg.isHeld()) {
                    try {
                        this.zzg.release();
                        if (this.zza != null) {
                            this.zza = null;
                        }
                    } catch (RuntimeException e) {
                        if (e.getClass().equals(RuntimeException.class)) {
                            Log.e("WakeLock", String.valueOf(this.zzp).concat(" failed to release!"), e);
                            if (this.zza != null) {
                                this.zza = null;
                            }
                            return;
                        }
                        throw e;
                    } catch (Throwable th) {
                        if (this.zza != null) {
                            this.zza = null;
                        }
                        throw th;
                    }
                } else {
                    Log.e("WakeLock", String.valueOf(this.zzp).concat(" should be held!"));
                }
            }
        }
    }

    private static void zze(PowerManager.WakeLock wakeLock, WorkSource workSource) {
        try {
            wakeLock.setWorkSource(workSource);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            Log.wtf("WakeLock", e.toString());
        }
    }

    @KeepForSdk
    public void acquire(long timeout) {
        this.zzt.incrementAndGet();
        long j = zzb;
        long j2 = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        long max = Math.max(Math.min(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, j), 1);
        if (timeout > 0) {
            max = Math.min(timeout, max);
        }
        synchronized (this.zzf) {
            if (!isHeld()) {
                this.zza = zzb.zza(false, (zzc) null);
                this.zzg.acquire();
                this.zzn.elapsedRealtime();
            }
            this.zzh++;
            this.zzm++;
            zzb((String) null);
            zzc zzc2 = this.zzs.get((Object) null);
            if (zzc2 == null) {
                zzc2 = new zzc((zzb) null);
                this.zzs.put((Object) null, zzc2);
            }
            zzc2.zza++;
            long elapsedRealtime = this.zzn.elapsedRealtime();
            if (DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE - elapsedRealtime > max) {
                j2 = elapsedRealtime + max;
            }
            if (j2 > this.zzj) {
                this.zzj = j2;
                Future<?> future = this.zzi;
                if (future != null) {
                    future.cancel(false);
                }
                this.zzi = this.zzu.schedule(new zza(this), max, TimeUnit.MILLISECONDS);
            }
        }
    }

    @KeepForSdk
    public boolean isHeld() {
        boolean z;
        synchronized (this.zzf) {
            z = this.zzh > 0;
        }
        return z;
    }

    @KeepForSdk
    public void release() {
        if (this.zzt.decrementAndGet() < 0) {
            Log.e("WakeLock", String.valueOf(this.zzp).concat(" release without a matched acquire!"));
        }
        synchronized (this.zzf) {
            zzb((String) null);
            if (this.zzs.containsKey((Object) null)) {
                zzc zzc2 = this.zzs.get((Object) null);
                if (zzc2 != null) {
                    int i = zzc2.zza - 1;
                    zzc2.zza = i;
                    if (i == 0) {
                        this.zzs.remove((Object) null);
                    }
                }
            } else {
                Log.w("WakeLock", String.valueOf(this.zzp).concat(" counter does not exist"));
            }
            zzd(0);
        }
    }

    @KeepForSdk
    public void setReferenceCounted(boolean value) {
        synchronized (this.zzf) {
            this.zzl = value;
        }
    }
}
