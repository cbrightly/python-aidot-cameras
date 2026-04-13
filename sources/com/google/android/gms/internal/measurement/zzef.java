package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.measurement.dynamite.ModuleDescriptor;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzfw;
import com.google.android.gms.measurement.internal.zzhe;
import com.google.android.gms.measurement.internal.zzip;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.gms:play-services-measurement-sdk-api@@21.2.2 */
public final class zzef {
    private static volatile zzef zzc;
    protected final Clock zza;
    protected final ExecutorService zzb;
    /* access modifiers changed from: private */
    public final String zzd;
    private final AppMeasurementSdk zze;
    @GuardedBy("listenerList")
    private final List zzf;
    private int zzg;
    /* access modifiers changed from: private */
    public boolean zzh;
    private final String zzi;
    /* access modifiers changed from: private */
    public volatile zzcc zzj;

    protected zzef(Context context, String str, String str2, String str3, Bundle bundle) {
        if (str == null || !zzW(str2, str3)) {
            this.zzd = "FA";
        } else {
            this.zzd = str;
        }
        this.zza = DefaultClock.getInstance();
        zzbx.zza();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new zzdi(this));
        boolean z = true;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        this.zzb = Executors.unconfigurableExecutorService(threadPoolExecutor);
        this.zze = new AppMeasurementSdk(this);
        this.zzf = new ArrayList();
        try {
            if (zzip.zzc(context, "google_app_id", zzfw.zza(context)) != null && !zzS()) {
                this.zzi = null;
                this.zzh = true;
                Log.w(this.zzd, "Disabling data collection. Found google_app_id in strings.xml but Google Analytics for Firebase is missing. Remove this value or add Google Analytics for Firebase to resume data collection.");
                return;
            }
        } catch (IllegalStateException e) {
        }
        if (!zzW(str2, str3)) {
            this.zzi = "fa";
            if (str2 == null || str3 == null) {
                if ((str2 == null) ^ (str3 != null ? false : z)) {
                    Log.w(this.zzd, "Specified origin or custom app id is null. Both parameters will be ignored.");
                }
            } else {
                Log.v(this.zzd, "Deferring to Google Analytics for Firebase for event data collection. https://firebase.google.com/docs/analytics");
            }
        } else {
            this.zzi = str2;
        }
        zzV(new zzcx(this, str2, str3, context, bundle));
        Application application = (Application) context.getApplicationContext();
        if (application == null) {
            Log.w(this.zzd, "Unable to register lifecycle notifications. Application null.");
        } else {
            application.registerActivityLifecycleCallbacks(new zzee(this));
        }
    }

    /* access modifiers changed from: private */
    public final void zzT(Exception exc, boolean z, boolean z2) {
        this.zzh |= z;
        if (z) {
            Log.w(this.zzd, "Data collection startup failed. No data will be collected.", exc);
            return;
        }
        if (z2) {
            zzB(5, "Error with data collection. Data lost.", exc, (Object) null, (Object) null);
        }
        Log.w(this.zzd, "Error with data collection. Data lost.", exc);
    }

    private final void zzU(String str, String str2, Bundle bundle, boolean z, boolean z2, Long l) {
        zzV(new zzds(this, l, str, str2, bundle, z, z2));
    }

    /* access modifiers changed from: private */
    public final void zzV(zzdu zzdu) {
        this.zzb.execute(zzdu);
    }

    /* access modifiers changed from: private */
    public final boolean zzW(String str, String str2) {
        return (str2 == null || str == null || zzS()) ? false : true;
    }

    public static zzef zzg(Context context, String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotNull(context);
        if (zzc == null) {
            synchronized (zzef.class) {
                if (zzc == null) {
                    zzc = new zzef(context, str, str2, str3, bundle);
                }
            }
        }
        return zzc;
    }

    public final void zzA(String str, String str2, Bundle bundle, long j) {
        zzU(str, str2, bundle, true, false, Long.valueOf(j));
    }

    public final void zzB(int i, String str, Object obj, Object obj2, Object obj3) {
        zzV(new zzdg(this, false, 5, str, obj, (Object) null, (Object) null));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003d, code lost:
        if (r4.zzj == null) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r4.zzj.registerOnMeasurementEventListener(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0054, code lost:
        android.util.Log.w(r4.zzd, "Failed to register event listener on calling thread. Trying again on the dynamite thread.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005b, code lost:
        zzV(new com.google.android.gms.internal.measurement.zzdq(r4, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0063, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzC(com.google.android.gms.measurement.internal.zzhf r5) {
        /*
            r4 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            java.util.List r0 = r4.zzf
            monitor-enter(r0)
            r1 = 0
        L_0x0007:
            java.util.List r2 = r4.zzf     // Catch:{ all -> 0x0064 }
            int r2 = r2.size()     // Catch:{ all -> 0x0064 }
            if (r1 >= r2) goto L_0x002b
            java.util.List r2 = r4.zzf     // Catch:{ all -> 0x0064 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0064 }
            android.util.Pair r2 = (android.util.Pair) r2     // Catch:{ all -> 0x0064 }
            java.lang.Object r2 = r2.first     // Catch:{ all -> 0x0064 }
            boolean r2 = r5.equals(r2)     // Catch:{ all -> 0x0064 }
            if (r2 == 0) goto L_0x0028
            java.lang.String r5 = r4.zzd     // Catch:{ all -> 0x0064 }
            java.lang.String r1 = "OnEventListener already registered."
            android.util.Log.w(r5, r1)     // Catch:{ all -> 0x0064 }
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            return
        L_0x0028:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x002b:
            com.google.android.gms.internal.measurement.zzdw r1 = new com.google.android.gms.internal.measurement.zzdw     // Catch:{ all -> 0x0064 }
            r1.<init>(r5)     // Catch:{ all -> 0x0064 }
            java.util.List r2 = r4.zzf     // Catch:{ all -> 0x0064 }
            android.util.Pair r3 = new android.util.Pair     // Catch:{ all -> 0x0064 }
            r3.<init>(r5, r1)     // Catch:{ all -> 0x0064 }
            r2.add(r3)     // Catch:{ all -> 0x0064 }
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            com.google.android.gms.internal.measurement.zzcc r5 = r4.zzj
            if (r5 == 0) goto L_0x005b
            com.google.android.gms.internal.measurement.zzcc r5 = r4.zzj     // Catch:{ RemoteException -> 0x0053, BadParcelableException -> 0x0051, IllegalArgumentException -> 0x004f, IllegalStateException -> 0x004d, NetworkOnMainThreadException -> 0x004b, NullPointerException -> 0x0049, SecurityException -> 0x0047, UnsupportedOperationException -> 0x0045 }
            r5.registerOnMeasurementEventListener(r1)     // Catch:{ RemoteException -> 0x0053, BadParcelableException -> 0x0051, IllegalArgumentException -> 0x004f, IllegalStateException -> 0x004d, NetworkOnMainThreadException -> 0x004b, NullPointerException -> 0x0049, SecurityException -> 0x0047, UnsupportedOperationException -> 0x0045 }
            return
        L_0x0045:
            r5 = move-exception
            goto L_0x0054
        L_0x0047:
            r5 = move-exception
            goto L_0x0054
        L_0x0049:
            r5 = move-exception
            goto L_0x0054
        L_0x004b:
            r5 = move-exception
            goto L_0x0054
        L_0x004d:
            r5 = move-exception
            goto L_0x0054
        L_0x004f:
            r5 = move-exception
            goto L_0x0054
        L_0x0051:
            r5 = move-exception
            goto L_0x0054
        L_0x0053:
            r5 = move-exception
        L_0x0054:
            java.lang.String r5 = r4.zzd
            java.lang.String r0 = "Failed to register event listener on calling thread. Trying again on the dynamite thread."
            android.util.Log.w(r5, r0)
        L_0x005b:
            com.google.android.gms.internal.measurement.zzdq r5 = new com.google.android.gms.internal.measurement.zzdq
            r5.<init>(r4, r1)
            r4.zzV(r5)
            return
        L_0x0064:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzef.zzC(com.google.android.gms.measurement.internal.zzhf):void");
    }

    public final void zzD() {
        zzV(new zzcv(this));
    }

    public final void zzE(Bundle bundle) {
        zzV(new zzcn(this, bundle));
    }

    public final void zzF(Bundle bundle) {
        zzV(new zzct(this, bundle));
    }

    public final void zzG(Bundle bundle) {
        zzV(new zzcu(this, bundle));
    }

    public final void zzH(Activity activity, String str, String str2) {
        zzV(new zzcr(this, activity, str, str2));
    }

    public final void zzI(boolean z) {
        zzV(new zzdn(this, z));
    }

    public final void zzJ(Bundle bundle) {
        zzV(new zzdo(this, bundle));
    }

    public final void zzK(zzhe zzhe) {
        zzdv zzdv = new zzdv(zzhe);
        if (this.zzj != null) {
            try {
                this.zzj.setEventInterceptor(zzdv);
                return;
            } catch (BadParcelableException | NetworkOnMainThreadException | RemoteException | IllegalArgumentException | IllegalStateException | NullPointerException | SecurityException | UnsupportedOperationException e) {
                Log.w(this.zzd, "Failed to set event interceptor on calling thread. Trying again on the dynamite thread.");
            }
        }
        zzV(new zzdp(this, zzdv));
    }

    public final void zzL(Boolean bool) {
        zzV(new zzcs(this, bool));
    }

    public final void zzM(long j) {
        zzV(new zzcw(this, j));
    }

    public final void zzN(String str) {
        zzV(new zzcq(this, str));
    }

    public final void zzO(String str, String str2, Object obj, boolean z) {
        zzV(new zzdt(this, str, str2, obj, z));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r3.zzj == null) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3.zzj.unregisterOnMeasurementEventListener(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        android.util.Log.w(r3.zzd, "Failed to unregister event listener on calling thread. Trying again on the dynamite thread.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0061, code lost:
        zzV(new com.google.android.gms.internal.measurement.zzdr(r3, r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0069, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzP(com.google.android.gms.measurement.internal.zzhf r4) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)
            java.util.List r0 = r3.zzf
            monitor-enter(r0)
            r1 = 0
        L_0x0007:
            java.util.List r2 = r3.zzf     // Catch:{ all -> 0x006a }
            int r2 = r2.size()     // Catch:{ all -> 0x006a }
            if (r1 >= r2) goto L_0x002b
            java.util.List r2 = r3.zzf     // Catch:{ all -> 0x006a }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x006a }
            android.util.Pair r2 = (android.util.Pair) r2     // Catch:{ all -> 0x006a }
            java.lang.Object r2 = r2.first     // Catch:{ all -> 0x006a }
            boolean r2 = r4.equals(r2)     // Catch:{ all -> 0x006a }
            if (r2 == 0) goto L_0x0028
            java.util.List r4 = r3.zzf     // Catch:{ all -> 0x006a }
            java.lang.Object r4 = r4.get(r1)     // Catch:{ all -> 0x006a }
            android.util.Pair r4 = (android.util.Pair) r4     // Catch:{ all -> 0x006a }
            goto L_0x002c
        L_0x0028:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x002b:
            r4 = 0
        L_0x002c:
            if (r4 != 0) goto L_0x0037
            java.lang.String r4 = r3.zzd     // Catch:{ all -> 0x006a }
            java.lang.String r1 = "OnEventListener had not been registered."
            android.util.Log.w(r4, r1)     // Catch:{ all -> 0x006a }
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            return
        L_0x0037:
            java.util.List r1 = r3.zzf     // Catch:{ all -> 0x006a }
            r1.remove(r4)     // Catch:{ all -> 0x006a }
            java.lang.Object r4 = r4.second     // Catch:{ all -> 0x006a }
            com.google.android.gms.internal.measurement.zzdw r4 = (com.google.android.gms.internal.measurement.zzdw) r4     // Catch:{ all -> 0x006a }
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            com.google.android.gms.internal.measurement.zzcc r0 = r3.zzj
            if (r0 == 0) goto L_0x0061
            com.google.android.gms.internal.measurement.zzcc r0 = r3.zzj     // Catch:{ RemoteException -> 0x0059, BadParcelableException -> 0x0057, IllegalArgumentException -> 0x0055, IllegalStateException -> 0x0053, NetworkOnMainThreadException -> 0x0051, NullPointerException -> 0x004f, SecurityException -> 0x004d, UnsupportedOperationException -> 0x004b }
            r0.unregisterOnMeasurementEventListener(r4)     // Catch:{ RemoteException -> 0x0059, BadParcelableException -> 0x0057, IllegalArgumentException -> 0x0055, IllegalStateException -> 0x0053, NetworkOnMainThreadException -> 0x0051, NullPointerException -> 0x004f, SecurityException -> 0x004d, UnsupportedOperationException -> 0x004b }
            return
        L_0x004b:
            r0 = move-exception
            goto L_0x005a
        L_0x004d:
            r0 = move-exception
            goto L_0x005a
        L_0x004f:
            r0 = move-exception
            goto L_0x005a
        L_0x0051:
            r0 = move-exception
            goto L_0x005a
        L_0x0053:
            r0 = move-exception
            goto L_0x005a
        L_0x0055:
            r0 = move-exception
            goto L_0x005a
        L_0x0057:
            r0 = move-exception
            goto L_0x005a
        L_0x0059:
            r0 = move-exception
        L_0x005a:
            java.lang.String r0 = r3.zzd
            java.lang.String r1 = "Failed to unregister event listener on calling thread. Trying again on the dynamite thread."
            android.util.Log.w(r0, r1)
        L_0x0061:
            com.google.android.gms.internal.measurement.zzdr r0 = new com.google.android.gms.internal.measurement.zzdr
            r0.<init>(r3, r4)
            r3.zzV(r0)
            return
        L_0x006a:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzef.zzP(com.google.android.gms.measurement.internal.zzhf):void");
    }

    /* access modifiers changed from: protected */
    public final boolean zzS() {
        try {
            Class.forName("com.google.firebase.analytics.FirebaseAnalytics", false, getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public final int zza(String str) {
        zzbz zzbz = new zzbz();
        zzV(new zzdj(this, str, zzbz));
        Integer num = (Integer) zzbz.zzf(zzbz.zzb(10000), Integer.class);
        if (num == null) {
            return 25;
        }
        return num.intValue();
    }

    public final long zzb() {
        zzbz zzbz = new zzbz();
        zzV(new zzdc(this, zzbz));
        Long zzc2 = zzbz.zzc(500);
        if (zzc2 != null) {
            return zzc2.longValue();
        }
        long nextLong = new Random(System.nanoTime() ^ this.zza.currentTimeMillis()).nextLong();
        int i = this.zzg + 1;
        this.zzg = i;
        return nextLong + ((long) i);
    }

    public final Bundle zzc(Bundle bundle, boolean z) {
        zzbz zzbz = new zzbz();
        zzV(new zzdh(this, bundle, zzbz));
        if (z) {
            return zzbz.zzb(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        }
        return null;
    }

    public final AppMeasurementSdk zzd() {
        return this.zze;
    }

    /* access modifiers changed from: protected */
    public final zzcc zzf(Context context, boolean z) {
        try {
            return zzcb.asInterface(DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION, ModuleDescriptor.MODULE_ID).instantiate("com.google.android.gms.measurement.internal.AppMeasurementDynamiteService"));
        } catch (DynamiteModule.LoadingException e) {
            zzT(e, true, false);
            return null;
        }
    }

    @WorkerThread
    public final Long zzh() {
        zzbz zzbz = new zzbz();
        zzV(new zzdl(this, zzbz));
        return zzbz.zzc(120000);
    }

    public final Object zzi(int i) {
        zzbz zzbz = new zzbz();
        zzV(new zzdm(this, zzbz, i));
        return zzbz.zzf(zzbz.zzb(15000), Object.class);
    }

    public final String zzk() {
        return this.zzi;
    }

    @WorkerThread
    public final String zzl() {
        zzbz zzbz = new zzbz();
        zzV(new zzdk(this, zzbz));
        return zzbz.zzd(120000);
    }

    public final String zzm() {
        zzbz zzbz = new zzbz();
        zzV(new zzdb(this, zzbz));
        return zzbz.zzd(50);
    }

    public final String zzn() {
        zzbz zzbz = new zzbz();
        zzV(new zzde(this, zzbz));
        return zzbz.zzd(500);
    }

    public final String zzo() {
        zzbz zzbz = new zzbz();
        zzV(new zzdd(this, zzbz));
        return zzbz.zzd(500);
    }

    public final String zzp() {
        zzbz zzbz = new zzbz();
        zzV(new zzda(this, zzbz));
        return zzbz.zzd(500);
    }

    public final List zzq(String str, String str2) {
        zzbz zzbz = new zzbz();
        zzV(new zzcp(this, str, str2, zzbz));
        List list = (List) zzbz.zzf(zzbz.zzb(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS), List.class);
        return list == null ? Collections.emptyList() : list;
    }

    public final Map zzr(String str, String str2, boolean z) {
        zzbz zzbz = new zzbz();
        zzV(new zzdf(this, str, str2, z, zzbz));
        Bundle zzb2 = zzbz.zzb(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        if (zzb2 == null || zzb2.size() == 0) {
            return Collections.emptyMap();
        }
        HashMap hashMap = new HashMap(zzb2.size());
        for (String str3 : zzb2.keySet()) {
            Object obj = zzb2.get(str3);
            if ((obj instanceof Double) || (obj instanceof Long) || (obj instanceof String)) {
                hashMap.put(str3, obj);
            }
        }
        return hashMap;
    }

    public final void zzv(String str) {
        zzV(new zzcy(this, str));
    }

    public final void zzw(String str, String str2, Bundle bundle) {
        zzV(new zzco(this, str, str2, bundle));
    }

    public final void zzx(String str) {
        zzV(new zzcz(this, str));
    }

    public final void zzy(@NonNull String str, Bundle bundle) {
        zzU((String) null, str, bundle, false, true, (Long) null);
    }

    public final void zzz(String str, String str2, Bundle bundle) {
        zzU(str, str2, bundle, true, true, (Long) null);
    }
}
