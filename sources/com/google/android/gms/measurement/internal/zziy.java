package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.GuardedBy;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zziy extends zzf {
    @VisibleForTesting
    protected zziq zza;
    private volatile zziq zzb;
    private volatile zziq zzc;
    private final Map zzd = new ConcurrentHashMap();
    @GuardedBy("activityLock")
    private Activity zze;
    @GuardedBy("activityLock")
    private volatile boolean zzf;
    private volatile zziq zzg;
    /* access modifiers changed from: private */
    public zziq zzh;
    @GuardedBy("activityLock")
    private boolean zzi;
    private final Object zzj = new Object();

    public zziy(zzge zzge) {
        super(zzge);
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzA(zziq zziq, zziq zziq2, long j, boolean z, Bundle bundle) {
        boolean z2;
        String str;
        long j2;
        long j3;
        zziq zziq3 = zziq;
        zziq zziq4 = zziq2;
        long j4 = j;
        Bundle bundle2 = bundle;
        zzg();
        boolean z3 = false;
        if (zziq4 == null || zziq4.zzc != zziq3.zzc || !zzir.zza(zziq4.zzb, zziq3.zzb)) {
            z2 = true;
        } else {
            z2 = !zzir.zza(zziq4.zza, zziq3.zza);
        }
        if (z && this.zza != null) {
            z3 = true;
        }
        if (z2) {
            Bundle bundle3 = bundle2 != null ? new Bundle(bundle2) : new Bundle();
            zzlo.zzK(zziq3, bundle3, true);
            if (zziq4 != null) {
                String str2 = zziq4.zza;
                if (str2 != null) {
                    bundle3.putString("_pn", str2);
                }
                String str3 = zziq4.zzb;
                if (str3 != null) {
                    bundle3.putString("_pc", str3);
                }
                bundle3.putLong("_pi", zziq4.zzc);
            }
            if (z3) {
                zzkm zzkm = this.zzt.zzu().zzb;
                long j5 = j4 - zzkm.zzb;
                zzkm.zzb = j4;
                if (j5 > 0) {
                    this.zzt.zzv().zzI(bundle3, j5);
                }
            }
            if (!this.zzt.zzf().zzu()) {
                bundle3.putLong("_mst", 1);
            }
            if (true != zziq3.zze) {
                str = "auto";
            } else {
                str = "app";
            }
            long currentTimeMillis = this.zzt.zzax().currentTimeMillis();
            if (zziq3.zze) {
                j3 = currentTimeMillis;
                long j6 = zziq3.zzf;
                if (j6 != 0) {
                    j2 = j6;
                    this.zzt.zzq().zzH(str, "_vs", j2, bundle3);
                }
            } else {
                j3 = currentTimeMillis;
            }
            j2 = j3;
            this.zzt.zzq().zzH(str, "_vs", j2, bundle3);
        }
        if (z3) {
            zzB(this.zza, true, j4);
        }
        this.zza = zziq3;
        if (zziq3.zze) {
            this.zzh = zziq3;
        }
        this.zzt.zzt().zzG(zziq3);
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzB(zziq zziq, boolean z, long j) {
        boolean z2;
        this.zzt.zzd().zzf(this.zzt.zzax().elapsedRealtime());
        if (zziq == null || !zziq.zzd) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (this.zzt.zzu().zzb.zzd(z2, z, j) && zziq != null) {
            zziq.zzd = false;
        }
    }

    static /* bridge */ /* synthetic */ void zzp(zziy zziy, Bundle bundle, zziq zziq, zziq zziq2, long j) {
        bundle.remove(FirebaseAnalytics.Param.SCREEN_NAME);
        bundle.remove(FirebaseAnalytics.Param.SCREEN_CLASS);
        zziy.zzA(zziq, zziq2, j, true, zziy.zzt.zzv().zzu((String) null, FirebaseAnalytics.Event.SCREEN_VIEW, bundle, (List) null, false));
    }

    @MainThread
    private final zziq zzy(@NonNull Activity activity) {
        Preconditions.checkNotNull(activity);
        zziq zziq = (zziq) this.zzd.get(activity);
        if (zziq == null) {
            zziq zziq2 = new zziq((String) null, zzl(activity.getClass(), "Activity"), this.zzt.zzv().zzq());
            this.zzd.put(activity, zziq2);
            zziq = zziq2;
        }
        return this.zzg != null ? this.zzg : zziq;
    }

    @MainThread
    private final void zzz(Activity activity, zziq zziq, boolean z) {
        zziq zziq2;
        zziq zziq3;
        zziq zziq4 = zziq;
        if (this.zzb == null) {
            zziq2 = this.zzc;
        } else {
            zziq2 = this.zzb;
        }
        if (zziq4.zzb == null) {
            zziq3 = new zziq(zziq4.zza, activity != null ? zzl(activity.getClass(), "Activity") : null, zziq4.zzc, zziq4.zze, zziq4.zzf);
        } else {
            zziq3 = zziq4;
        }
        this.zzc = this.zzb;
        this.zzb = zziq3;
        this.zzt.zzaB().zzp(new zzit(this, zziq3, zziq2, this.zzt.zzax().elapsedRealtime(), z));
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return false;
    }

    public final zziq zzi() {
        return this.zzb;
    }

    @WorkerThread
    public final zziq zzj(boolean z) {
        zza();
        zzg();
        if (!z) {
            return this.zza;
        }
        zziq zziq = this.zza;
        return zziq != null ? zziq : this.zzh;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final String zzl(Class cls, String str) {
        String str2;
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            return "Activity";
        }
        String[] split = canonicalName.split("\\.");
        int length = split.length;
        if (length > 0) {
            str2 = split[length - 1];
        } else {
            str2 = "";
        }
        int length2 = str2.length();
        this.zzt.zzf();
        if (length2 <= 100) {
            return str2;
        }
        this.zzt.zzf();
        return str2.substring(0, 100);
    }

    @MainThread
    public final void zzr(Activity activity, Bundle bundle) {
        Bundle bundle2;
        if (this.zzt.zzf().zzu() && bundle != null && (bundle2 = bundle.getBundle("com.google.app_measurement.screen_service")) != null) {
            this.zzd.put(activity, new zziq(bundle2.getString("name"), bundle2.getString("referrer_name"), bundle2.getLong("id")));
        }
    }

    @MainThread
    public final void zzs(Activity activity) {
        synchronized (this.zzj) {
            if (activity == this.zze) {
                this.zze = null;
            }
        }
        if (this.zzt.zzf().zzu()) {
            this.zzd.remove(activity);
        }
    }

    @MainThread
    public final void zzt(Activity activity) {
        synchronized (this.zzj) {
            this.zzi = false;
            this.zzf = true;
        }
        long elapsedRealtime = this.zzt.zzax().elapsedRealtime();
        if (!this.zzt.zzf().zzu()) {
            this.zzb = null;
            this.zzt.zzaB().zzp(new zziv(this, elapsedRealtime));
            return;
        }
        zziq zzy = zzy(activity);
        this.zzc = this.zzb;
        this.zzb = null;
        this.zzt.zzaB().zzp(new zziw(this, zzy, elapsedRealtime));
    }

    @MainThread
    public final void zzu(Activity activity) {
        synchronized (this.zzj) {
            this.zzi = true;
            if (activity != this.zze) {
                synchronized (this.zzj) {
                    this.zze = activity;
                    this.zzf = false;
                }
                if (this.zzt.zzf().zzu()) {
                    this.zzg = null;
                    this.zzt.zzaB().zzp(new zzix(this));
                }
            }
        }
        if (!this.zzt.zzf().zzu()) {
            this.zzb = this.zzg;
            this.zzt.zzaB().zzp(new zziu(this));
            return;
        }
        zzz(activity, zzy(activity), false);
        zzd zzd2 = this.zzt.zzd();
        zzd2.zzt.zzaB().zzp(new zzc(zzd2, zzd2.zzt.zzax().elapsedRealtime()));
    }

    @MainThread
    public final void zzv(Activity activity, Bundle bundle) {
        zziq zziq;
        if (this.zzt.zzf().zzu() && bundle != null && (zziq = (zziq) this.zzd.get(activity)) != null) {
            Bundle bundle2 = new Bundle();
            bundle2.putLong("id", zziq.zzc);
            bundle2.putString("name", zziq.zza);
            bundle2.putString("referrer_name", zziq.zzb);
            bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0088, code lost:
        if (r1 <= 100) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b4, code lost:
        if (r1 <= 100) goto L_0x00cf;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzw(@androidx.annotation.NonNull android.app.Activity r4, @androidx.annotation.Size(max = 36, min = 1) java.lang.String r5, @androidx.annotation.Size(max = 36, min = 1) java.lang.String r6) {
        /*
            r3 = this;
            com.google.android.gms.measurement.internal.zzge r0 = r3.zzt
            com.google.android.gms.measurement.internal.zzag r0 = r0.zzf()
            boolean r0 = r0.zzu()
            if (r0 != 0) goto L_0x001c
            com.google.android.gms.measurement.internal.zzge r4 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzl()
            java.lang.String r5 = "setCurrentScreen cannot be called while screen reporting is disabled."
            r4.zza(r5)
            return
        L_0x001c:
            com.google.android.gms.measurement.internal.zziq r0 = r3.zzb
            if (r0 != 0) goto L_0x0030
            com.google.android.gms.measurement.internal.zzge r4 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzl()
            java.lang.String r5 = "setCurrentScreen cannot be called while no activity active"
            r4.zza(r5)
            return
        L_0x0030:
            java.util.Map r1 = r3.zzd
            java.lang.Object r1 = r1.get(r4)
            if (r1 != 0) goto L_0x0048
            com.google.android.gms.measurement.internal.zzge r4 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzl()
            java.lang.String r5 = "setCurrentScreen must be called with an activity in the activity lifecycle"
            r4.zza(r5)
            return
        L_0x0048:
            if (r6 != 0) goto L_0x0054
            java.lang.Class r6 = r4.getClass()
            java.lang.String r1 = "Activity"
            java.lang.String r6 = r3.zzl(r6, r1)
        L_0x0054:
            java.lang.String r1 = r0.zzb
            boolean r1 = com.google.android.gms.measurement.internal.zzir.zza(r1, r6)
            java.lang.String r0 = r0.zza
            boolean r0 = com.google.android.gms.measurement.internal.zzir.zza(r0, r5)
            if (r1 == 0) goto L_0x0075
            if (r0 != 0) goto L_0x0065
            goto L_0x0075
        L_0x0065:
            com.google.android.gms.measurement.internal.zzge r4 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzl()
            java.lang.String r5 = "setCurrentScreen cannot be called with the same class and name"
            r4.zza(r5)
            return
        L_0x0075:
            r0 = 100
            if (r5 == 0) goto L_0x00a3
            int r1 = r5.length()
            if (r1 <= 0) goto L_0x008b
            int r1 = r5.length()
            com.google.android.gms.measurement.internal.zzge r2 = r3.zzt
            r2.zzf()
            if (r1 > r0) goto L_0x008b
            goto L_0x00a3
        L_0x008b:
            com.google.android.gms.measurement.internal.zzge r4 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzl()
            int r5 = r5.length()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r6 = "Invalid screen name length in setCurrentScreen. Length"
            r4.zzb(r6, r5)
            return
        L_0x00a3:
            if (r6 == 0) goto L_0x00cf
            int r1 = r6.length()
            if (r1 <= 0) goto L_0x00b7
            int r1 = r6.length()
            com.google.android.gms.measurement.internal.zzge r2 = r3.zzt
            r2.zzf()
            if (r1 > r0) goto L_0x00b7
            goto L_0x00cf
        L_0x00b7:
            com.google.android.gms.measurement.internal.zzge r4 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r4 = r4.zzaA()
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzl()
            int r5 = r6.length()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r6 = "Invalid class name length in setCurrentScreen. Length"
            r4.zzb(r6, r5)
            return
        L_0x00cf:
            com.google.android.gms.measurement.internal.zzge r0 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()
            if (r5 != 0) goto L_0x00de
            java.lang.String r1 = "null"
            goto L_0x00df
        L_0x00de:
            r1 = r5
        L_0x00df:
            java.lang.String r2 = "Setting current screen to name, class"
            r0.zzc(r2, r1, r6)
            com.google.android.gms.measurement.internal.zziq r0 = new com.google.android.gms.measurement.internal.zziq
            com.google.android.gms.measurement.internal.zzge r1 = r3.zzt
            com.google.android.gms.measurement.internal.zzlo r1 = r1.zzv()
            long r1 = r1.zzq()
            r0.<init>(r5, r6, r1)
            java.util.Map r5 = r3.zzd
            r5.put(r4, r0)
            r5 = 1
            r3.zzz(r4, r0, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zziy.zzw(android.app.Activity, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0031, code lost:
        if (r2 > 100) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0063, code lost:
        if (r4 > 100) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c2, code lost:
        r0 = r12.zzt.zzaA().zzj();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cc, code lost:
        if (r3 != null) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ce, code lost:
        r1 = com.google.maps.android.BuildConfig.TRAVIS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d1, code lost:
        r1 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d2, code lost:
        if (r4 != null) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d4, code lost:
        r2 = com.google.maps.android.BuildConfig.TRAVIS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d7, code lost:
        r2 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00d8, code lost:
        r0.zzc("Logging screen view with name, class", r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00df, code lost:
        if (r12.zzb != null) goto L_0x00e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e1, code lost:
        r0 = r12.zzc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e4, code lost:
        r0 = r12.zzb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e6, code lost:
        r2 = new com.google.android.gms.measurement.internal.zziq(r3, r4, r12.zzt.zzv().zzq(), true, r14);
        r12.zzb = r2;
        r12.zzc = r0;
        r12.zzg = r2;
        r12.zzt.zzaB().zzp(new com.google.android.gms.measurement.internal.zzis(r12, r13, r2, r0, r12.zzt.zzax().elapsedRealtime()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x011b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzx(android.os.Bundle r13, long r14) {
        /*
            r12 = this;
            java.lang.Object r0 = r12.zzj
            monitor-enter(r0)
            boolean r1 = r12.zzi     // Catch:{ all -> 0x011c }
            if (r1 != 0) goto L_0x0018
            com.google.android.gms.measurement.internal.zzge r13 = r12.zzt     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzeu r13 = r13.zzaA()     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzes r13 = r13.zzl()     // Catch:{ all -> 0x011c }
            java.lang.String r14 = "Cannot log screen view event when the app is in the background."
            r13.zza(r14)     // Catch:{ all -> 0x011c }
            monitor-exit(r0)     // Catch:{ all -> 0x011c }
            return
        L_0x0018:
            java.lang.String r1 = "screen_name"
            java.lang.String r3 = r13.getString(r1)     // Catch:{ all -> 0x011c }
            r1 = 100
            if (r3 == 0) goto L_0x004c
            int r2 = r3.length()     // Catch:{ all -> 0x011c }
            if (r2 <= 0) goto L_0x0033
            int r2 = r3.length()     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzge r4 = r12.zzt     // Catch:{ all -> 0x011c }
            r4.zzf()     // Catch:{ all -> 0x011c }
            if (r2 <= r1) goto L_0x004c
        L_0x0033:
            com.google.android.gms.measurement.internal.zzge r13 = r12.zzt     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzeu r13 = r13.zzaA()     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzes r13 = r13.zzl()     // Catch:{ all -> 0x011c }
            java.lang.String r14 = "Invalid screen name length for screen view. Length"
            int r15 = r3.length()     // Catch:{ all -> 0x011c }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x011c }
            r13.zzb(r14, r15)     // Catch:{ all -> 0x011c }
            monitor-exit(r0)     // Catch:{ all -> 0x011c }
            return
        L_0x004c:
            java.lang.String r2 = "screen_class"
            java.lang.String r2 = r13.getString(r2)     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x007e
            int r4 = r2.length()     // Catch:{ all -> 0x011c }
            if (r4 <= 0) goto L_0x0065
            int r4 = r2.length()     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzge r5 = r12.zzt     // Catch:{ all -> 0x011c }
            r5.zzf()     // Catch:{ all -> 0x011c }
            if (r4 <= r1) goto L_0x007e
        L_0x0065:
            com.google.android.gms.measurement.internal.zzge r13 = r12.zzt     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzeu r13 = r13.zzaA()     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzes r13 = r13.zzl()     // Catch:{ all -> 0x011c }
            java.lang.String r14 = "Invalid screen class length for screen view. Length"
            int r15 = r2.length()     // Catch:{ all -> 0x011c }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x011c }
            r13.zzb(r14, r15)     // Catch:{ all -> 0x011c }
            monitor-exit(r0)     // Catch:{ all -> 0x011c }
            return
        L_0x007e:
            if (r2 != 0) goto L_0x0094
            android.app.Activity r1 = r12.zze     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x0090
            java.lang.Class r1 = r1.getClass()     // Catch:{ all -> 0x011c }
            java.lang.String r2 = "Activity"
            java.lang.String r1 = r12.zzl(r1, r2)     // Catch:{ all -> 0x011c }
            r4 = r1
            goto L_0x0095
        L_0x0090:
            java.lang.String r1 = "Activity"
            r4 = r1
            goto L_0x0095
        L_0x0094:
            r4 = r2
        L_0x0095:
            com.google.android.gms.measurement.internal.zziq r1 = r12.zzb     // Catch:{ all -> 0x011c }
            boolean r2 = r12.zzf     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x00c1
            if (r1 == 0) goto L_0x00c1
            r2 = 0
            r12.zzf = r2     // Catch:{ all -> 0x011c }
            java.lang.String r2 = r1.zzb     // Catch:{ all -> 0x011c }
            boolean r2 = com.google.android.gms.measurement.internal.zzir.zza(r2, r4)     // Catch:{ all -> 0x011c }
            java.lang.String r1 = r1.zza     // Catch:{ all -> 0x011c }
            boolean r1 = com.google.android.gms.measurement.internal.zzir.zza(r1, r3)     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x00c1
            if (r1 == 0) goto L_0x00c1
            com.google.android.gms.measurement.internal.zzge r13 = r12.zzt     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzeu r13 = r13.zzaA()     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzes r13 = r13.zzl()     // Catch:{ all -> 0x011c }
            java.lang.String r14 = "Ignoring call to log screen view event with duplicate parameters."
            r13.zza(r14)     // Catch:{ all -> 0x011c }
            monitor-exit(r0)     // Catch:{ all -> 0x011c }
            return
        L_0x00c1:
            monitor-exit(r0)     // Catch:{ all -> 0x011c }
            com.google.android.gms.measurement.internal.zzge r0 = r12.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()
            if (r3 != 0) goto L_0x00d1
            java.lang.String r1 = "null"
            goto L_0x00d2
        L_0x00d1:
            r1 = r3
        L_0x00d2:
            if (r4 != 0) goto L_0x00d7
            java.lang.String r2 = "null"
            goto L_0x00d8
        L_0x00d7:
            r2 = r4
        L_0x00d8:
            java.lang.String r5 = "Logging screen view with name, class"
            r0.zzc(r5, r1, r2)
            com.google.android.gms.measurement.internal.zziq r0 = r12.zzb
            if (r0 != 0) goto L_0x00e4
            com.google.android.gms.measurement.internal.zziq r0 = r12.zzc
            goto L_0x00e6
        L_0x00e4:
            com.google.android.gms.measurement.internal.zziq r0 = r12.zzb
        L_0x00e6:
            com.google.android.gms.measurement.internal.zziq r1 = new com.google.android.gms.measurement.internal.zziq
            com.google.android.gms.measurement.internal.zzge r2 = r12.zzt
            com.google.android.gms.measurement.internal.zzlo r2 = r2.zzv()
            long r5 = r2.zzq()
            r7 = 1
            r2 = r1
            r8 = r14
            r2.<init>(r3, r4, r5, r7, r8)
            r12.zzb = r1
            r12.zzc = r0
            r12.zzg = r1
            com.google.android.gms.measurement.internal.zzge r14 = r12.zzt
            com.google.android.gms.common.util.Clock r14 = r14.zzax()
            long r10 = r14.elapsedRealtime()
            com.google.android.gms.measurement.internal.zzge r14 = r12.zzt
            com.google.android.gms.measurement.internal.zzgb r14 = r14.zzaB()
            com.google.android.gms.measurement.internal.zzis r15 = new com.google.android.gms.measurement.internal.zzis
            r5 = r15
            r6 = r12
            r7 = r13
            r8 = r1
            r9 = r0
            r5.<init>(r6, r7, r8, r9, r10)
            r14.zzp(r15)
            return
        L_0x011c:
            r13 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x011c }
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zziy.zzx(android.os.Bundle, long):void");
    }
}
