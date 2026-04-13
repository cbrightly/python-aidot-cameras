package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.collection.LruCache;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzc;
import com.google.android.gms.internal.measurement.zzd;
import com.google.android.gms.internal.measurement.zzfb;
import com.google.android.gms.internal.measurement.zzfc;
import com.google.android.gms.internal.measurement.zzfe;
import com.google.android.gms.internal.measurement.zzff;
import com.google.android.gms.internal.measurement.zzfj;
import com.google.android.gms.internal.measurement.zzgr;
import com.google.android.gms.internal.measurement.zzgt;
import com.google.android.gms.internal.measurement.zzll;
import com.google.android.gms.internal.measurement.zzr;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfv extends zzkt implements zzaf {
    @VisibleForTesting
    final Map zza = new ArrayMap();
    @VisibleForTesting
    final Map zzb = new ArrayMap();
    @VisibleForTesting
    final Map zzc = new ArrayMap();
    @VisibleForTesting
    final LruCache zzd = new zzfs(this, 20);
    final zzr zze = new zzft(this);
    /* access modifiers changed from: private */
    public final Map zzg = new ArrayMap();
    private final Map zzh = new ArrayMap();
    private final Map zzi = new ArrayMap();
    private final Map zzj = new ArrayMap();
    private final Map zzk = new ArrayMap();
    private final Map zzl = new ArrayMap();

    zzfv(zzlg zzlg) {
        super(zzlg);
    }

    @WorkerThread
    private final zzff zzA(String str, byte[] bArr) {
        Long l;
        if (bArr == null) {
            return zzff.zzg();
        }
        try {
            zzff zzff = (zzff) ((zzfe) zzli.zzl(zzff.zze(), bArr)).zzaD();
            zzes zzj2 = this.zzt.zzaA().zzj();
            String str2 = null;
            if (zzff.zzu()) {
                l = Long.valueOf(zzff.zzc());
            } else {
                l = null;
            }
            if (zzff.zzt()) {
                str2 = zzff.zzh();
            }
            zzj2.zzc("Parsed config. version, gmp_app_id", l, str2);
            return zzff;
        } catch (zzll e) {
            this.zzt.zzaA().zzk().zzc("Unable to merge remote config. appId", zzeu.zzn(str), e);
            return zzff.zzg();
        } catch (RuntimeException e2) {
            this.zzt.zzaA().zzk().zzc("Unable to merge remote config. appId", zzeu.zzn(str), e2);
            return zzff.zzg();
        }
    }

    private final void zzB(String str, zzfe zzfe) {
        HashSet hashSet = new HashSet();
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        for (zzfb zzb2 : zzfe.zzg()) {
            hashSet.add(zzb2.zzb());
        }
        for (int i = 0; i < zzfe.zza(); i++) {
            zzfc zzfc = (zzfc) zzfe.zzb(i).zzbB();
            if (zzfc.zzc().isEmpty()) {
                this.zzt.zzaA().zzk().zza("EventConfig contained null event name");
            } else {
                String zzc2 = zzfc.zzc();
                String zzb3 = zzhb.zzb(zzfc.zzc());
                if (!TextUtils.isEmpty(zzb3)) {
                    zzfc.zzb(zzb3);
                    zzfe.zzd(i, zzfc);
                }
                if (zzfc.zzf() && zzfc.zzd()) {
                    arrayMap.put(zzc2, true);
                }
                if (zzfc.zzg() && zzfc.zze()) {
                    arrayMap2.put(zzfc.zzc(), true);
                }
                if (zzfc.zzh()) {
                    if (zzfc.zza() < 2 || zzfc.zza() > 65535) {
                        this.zzt.zzaA().zzk().zzc("Invalid sampling rate. Event name, sample rate", zzfc.zzc(), Integer.valueOf(zzfc.zza()));
                    } else {
                        arrayMap3.put(zzfc.zzc(), Integer.valueOf(zzfc.zza()));
                    }
                }
            }
        }
        this.zza.put(str, hashSet);
        this.zzb.put(str, arrayMap);
        this.zzc.put(str, arrayMap2);
        this.zzi.put(str, arrayMap3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x011b  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzC(java.lang.String r13) {
        /*
            r12 = this;
            r12.zzW()
            r12.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r13)
            java.util.Map r0 = r12.zzh
            java.lang.Object r0 = r0.get(r13)
            if (r0 != 0) goto L_0x011f
            com.google.android.gms.measurement.internal.zzlg r0 = r12.zzf
            com.google.android.gms.measurement.internal.zzam r0 = r0.zzi()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r13)
            r0.zzg()
            r0.zzW()
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r0.zzh()     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            java.lang.String r3 = "apps"
            java.lang.String r4 = "remote_config"
            java.lang.String r5 = "config_last_modified_time"
            java.lang.String r6 = "e_tag"
            java.lang.String[] r4 = new java.lang.String[]{r4, r5, r6}     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            java.lang.String r5 = "app_id=?"
            r10 = 1
            java.lang.String[] r6 = new java.lang.String[r10]     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            r11 = 0
            r6[r11] = r13     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            boolean r3 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x007a }
            if (r3 != 0) goto L_0x0047
            goto L_0x0097
        L_0x0047:
            byte[] r3 = r2.getBlob(r11)     // Catch:{ SQLiteException -> 0x007a }
            java.lang.String r4 = r2.getString(r10)     // Catch:{ SQLiteException -> 0x007a }
            r5 = 2
            java.lang.String r5 = r2.getString(r5)     // Catch:{ SQLiteException -> 0x007a }
            boolean r6 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x007a }
            if (r6 == 0) goto L_0x006e
            com.google.android.gms.measurement.internal.zzge r6 = r0.zzt     // Catch:{ SQLiteException -> 0x007a }
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()     // Catch:{ SQLiteException -> 0x007a }
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzd()     // Catch:{ SQLiteException -> 0x007a }
            java.lang.String r7 = "Got multiple records for app config, expected one. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r13)     // Catch:{ SQLiteException -> 0x007a }
            r6.zzb(r7, r8)     // Catch:{ SQLiteException -> 0x007a }
        L_0x006e:
            if (r3 != 0) goto L_0x0071
            goto L_0x0097
        L_0x0071:
            com.google.android.gms.measurement.internal.zzaj r6 = new com.google.android.gms.measurement.internal.zzaj     // Catch:{ SQLiteException -> 0x007a }
            r6.<init>(r3, r4, r5)     // Catch:{ SQLiteException -> 0x007a }
            r2.close()
            goto L_0x009d
        L_0x007a:
            r3 = move-exception
            goto L_0x0082
        L_0x007c:
            r13 = move-exception
            goto L_0x0119
        L_0x007f:
            r2 = move-exception
            r3 = r2
            r2 = r1
        L_0x0082:
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzt     // Catch:{ all -> 0x0117 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0117 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x0117 }
            java.lang.String r4 = "Error querying remote config. appId"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r13)     // Catch:{ all -> 0x0117 }
            r0.zzc(r4, r5, r3)     // Catch:{ all -> 0x0117 }
            if (r2 == 0) goto L_0x009c
        L_0x0097:
            r2.close()
            r6 = r1
            goto L_0x009d
        L_0x009c:
            r6 = r1
        L_0x009d:
            if (r6 != 0) goto L_0x00cd
            java.util.Map r0 = r12.zzg
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzb
            r0.put(r13, r1)
            java.util.Map r0 = r12.zza
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzc
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzh
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzj
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzk
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzl
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzi
            r0.put(r13, r1)
            return
        L_0x00cd:
            byte[] r0 = r6.zza
            com.google.android.gms.internal.measurement.zzff r0 = r12.zzA(r13, r0)
            com.google.android.gms.internal.measurement.zzkx r0 = r0.zzbB()
            com.google.android.gms.internal.measurement.zzfe r0 = (com.google.android.gms.internal.measurement.zzfe) r0
            r12.zzB(r13, r0)
            java.util.Map r1 = r12.zzg
            com.google.android.gms.internal.measurement.zzlb r2 = r0.zzaD()
            com.google.android.gms.internal.measurement.zzff r2 = (com.google.android.gms.internal.measurement.zzff) r2
            java.util.Map r2 = zzE(r2)
            r1.put(r13, r2)
            java.util.Map r1 = r12.zzh
            com.google.android.gms.internal.measurement.zzlb r2 = r0.zzaD()
            com.google.android.gms.internal.measurement.zzff r2 = (com.google.android.gms.internal.measurement.zzff) r2
            r1.put(r13, r2)
            com.google.android.gms.internal.measurement.zzlb r1 = r0.zzaD()
            com.google.android.gms.internal.measurement.zzff r1 = (com.google.android.gms.internal.measurement.zzff) r1
            r12.zzD(r13, r1)
            java.util.Map r1 = r12.zzj
            java.lang.String r0 = r0.zze()
            r1.put(r13, r0)
            java.util.Map r0 = r12.zzk
            java.lang.String r1 = r6.zzb
            r0.put(r13, r1)
            java.util.Map r0 = r12.zzl
            java.lang.String r1 = r6.zzc
            r0.put(r13, r1)
            return
        L_0x0117:
            r13 = move-exception
            r1 = r2
        L_0x0119:
            if (r1 == 0) goto L_0x011e
            r1.close()
        L_0x011e:
            throw r13
        L_0x011f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfv.zzC(java.lang.String):void");
    }

    @WorkerThread
    private final void zzD(String str, zzff zzff) {
        if (zzff.zza() != 0) {
            this.zzt.zzaA().zzj().zzb("EES programs found", Integer.valueOf(zzff.zza()));
            zzgt zzgt = (zzgt) zzff.zzo().get(0);
            try {
                zzc zzc2 = new zzc();
                zzc2.zzd("internal.remoteConfig", new zzfp(this, str));
                zzc2.zzd("internal.appMetadata", new zzfq(this, str));
                zzc2.zzd("internal.logger", new zzfr(this));
                zzc2.zzc(zzgt);
                this.zzd.put(str, zzc2);
                this.zzt.zzaA().zzj().zzc("EES program loaded for appId, activities", str, Integer.valueOf(zzgt.zza().zza()));
                for (zzgr zzb2 : zzgt.zza().zzd()) {
                    this.zzt.zzaA().zzj().zzb("EES program activity", zzb2.zzb());
                }
            } catch (zzd e) {
                this.zzt.zzaA().zzd().zzb("Failed to load EES program. appId", str);
            }
        } else {
            this.zzd.remove(str);
        }
    }

    private static final Map zzE(zzff zzff) {
        ArrayMap arrayMap = new ArrayMap();
        if (zzff != null) {
            for (zzfj zzfj : zzff.zzp()) {
                arrayMap.put(zzfj.zzb(), zzfj.zzc());
            }
        }
        return arrayMap;
    }

    static /* bridge */ /* synthetic */ zzc zzd(zzfv zzfv, String str) {
        zzfv.zzW();
        Preconditions.checkNotEmpty(str);
        if (!zzfv.zzo(str)) {
            return null;
        }
        if (!zzfv.zzh.containsKey(str) || zzfv.zzh.get(str) == null) {
            zzfv.zzC(str);
        } else {
            zzfv.zzD(str, (zzff) zzfv.zzh.get(str));
        }
        return (zzc) zzfv.zzd.snapshot().get(str);
    }

    @WorkerThread
    public final String zza(String str, String str2) {
        zzg();
        zzC(str);
        Map map = (Map) this.zzg.get(str);
        if (map != null) {
            return (String) map.get(str2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final boolean zzb() {
        return false;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final int zzc(String str, String str2) {
        Integer num;
        zzg();
        zzC(str);
        Map map = (Map) this.zzi.get(str);
        if (map == null || (num = (Integer) map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final zzff zze(String str) {
        zzW();
        zzg();
        Preconditions.checkNotEmpty(str);
        zzC(str);
        return (zzff) this.zzh.get(str);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final String zzf(String str) {
        zzg();
        return (String) this.zzl.get(str);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final String zzh(String str) {
        zzg();
        return (String) this.zzk.get(str);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzi(String str) {
        zzg();
        zzC(str);
        return (String) this.zzj.get(str);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final Set zzk(String str) {
        zzg();
        zzC(str);
        return (Set) this.zza.get(str);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzl(String str) {
        zzg();
        this.zzk.put(str, (Object) null);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzm(String str) {
        zzg();
        this.zzh.remove(str);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzn(String str) {
        zzg();
        zzff zze2 = zze(str);
        if (zze2 == null) {
            return false;
        }
        return zze2.zzs();
    }

    public final boolean zzo(String str) {
        zzff zzff;
        if (TextUtils.isEmpty(str) || (zzff = (zzff) this.zzh.get(str)) == null || zzff.zza() == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzp(String str) {
        return "1".equals(zza(str, "measurement.upload.blacklist_internal"));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzq(String str, String str2) {
        Boolean bool;
        zzg();
        zzC(str);
        if ("ecommerce_purchase".equals(str2) || FirebaseAnalytics.Event.PURCHASE.equals(str2) || FirebaseAnalytics.Event.REFUND.equals(str2)) {
            return true;
        }
        Map map = (Map) this.zzc.get(str);
        if (map == null || (bool = (Boolean) map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzr(String str, String str2) {
        Boolean bool;
        zzg();
        zzC(str);
        if (zzp(str) && zzlo.zzaj(str2)) {
            return true;
        }
        if (zzs(str) && zzlo.zzak(str2)) {
            return true;
        }
        Map map = (Map) this.zzb.get(str);
        if (map == null || (bool = (Boolean) map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* access modifiers changed from: package-private */
    public final boolean zzs(String str) {
        return "1".equals(zza(str, "measurement.upload.blacklist_public"));
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final boolean zzt(String str, byte[] bArr, String str2, String str3) {
        zzW();
        zzg();
        Preconditions.checkNotEmpty(str);
        zzfe zzfe = (zzfe) zzA(str, bArr).zzbB();
        zzB(str, zzfe);
        zzD(str, (zzff) zzfe.zzaD());
        this.zzh.put(str, (zzff) zzfe.zzaD());
        this.zzj.put(str, zzfe.zze());
        this.zzk.put(str, str2);
        this.zzl.put(str, str3);
        this.zzg.put(str, zzE((zzff) zzfe.zzaD()));
        this.zzf.zzi().zzB(str, new ArrayList(zzfe.zzf()));
        try {
            zzfe.zzc();
            bArr = ((zzff) zzfe.zzaD()).zzbx();
        } catch (RuntimeException e) {
            this.zzt.zzaA().zzk().zzc("Unable to serialize reduced-size config. Storing full config instead. appId", zzeu.zzn(str), e);
        }
        zzam zzi2 = this.zzf.zzi();
        Preconditions.checkNotEmpty(str);
        zzi2.zzg();
        zzi2.zzW();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr);
        contentValues.put("config_last_modified_time", str2);
        contentValues.put("e_tag", str3);
        try {
            if (((long) zzi2.zzh().update("apps", contentValues, "app_id = ?", new String[]{str})) == 0) {
                zzi2.zzt.zzaA().zzd().zzb("Failed to update remote config (got 0). appId", zzeu.zzn(str));
            }
        } catch (SQLiteException e2) {
            zzi2.zzt.zzaA().zzd().zzc("Error storing remote config. appId", zzeu.zzn(str), e2);
        }
        this.zzh.put(str, (zzff) zzfe.zzaD());
        return true;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzu(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("app_instance_id");
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzv(String str) {
        zzg();
        zzC(str);
        if (this.zza.get(str) == null || (!((Set) this.zza.get(str)).contains("device_model") && !((Set) this.zza.get(str)).contains("device_info"))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzw(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("enhanced_user_id");
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzx(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("google_signals");
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzy(String str) {
        zzg();
        zzC(str);
        if (this.zza.get(str) == null || (!((Set) this.zza.get(str)).contains("os_version") && !((Set) this.zza.get(str)).contains("device_info"))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzz(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("user_id");
    }
}
