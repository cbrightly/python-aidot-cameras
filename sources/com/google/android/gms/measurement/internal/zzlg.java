package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.work.WorkRequest;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzcl;
import com.google.android.gms.internal.measurement.zzff;
import com.google.android.gms.internal.measurement.zzfs;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzfw;
import com.google.android.gms.internal.measurement.zzfx;
import com.google.android.gms.internal.measurement.zzgc;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzgm;
import com.google.android.gms.internal.measurement.zzop;
import com.google.android.gms.internal.measurement.zzpw;
import com.google.android.gms.internal.measurement.zzqr;
import com.google.android.gms.internal.measurement.zzra;
import com.google.firebase.messaging.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzlg implements zzgz {
    private static volatile zzlg zzb;
    private long zzA;
    private final Map zzB;
    private final Map zzC;
    private zziq zzD;
    private String zzE;
    private final zzln zzF = new zzlb(this);
    @VisibleForTesting
    long zza;
    private final zzfv zzc;
    private final zzfa zzd;
    private zzam zze;
    private zzfc zzf;
    private zzkr zzg;
    private zzaa zzh;
    private final zzli zzi;
    private zzio zzj;
    private zzka zzk;
    private final zzkv zzl;
    private zzfm zzm;
    /* access modifiers changed from: private */
    public final zzge zzn;
    private boolean zzo = false;
    private boolean zzp;
    private List zzq;
    private int zzr;
    private int zzs;
    private boolean zzt;
    private boolean zzu;
    private boolean zzv;
    private FileLock zzw;
    private FileChannel zzx;
    private List zzy;
    private List zzz;

    zzlg(zzlh zzlh, zzge zzge) {
        Preconditions.checkNotNull(zzlh);
        this.zzn = zzge.zzp(zzlh.zza, (zzcl) null, (Long) null);
        this.zzA = -1;
        this.zzl = new zzkv(this);
        zzli zzli = new zzli(this);
        zzli.zzX();
        this.zzi = zzli;
        zzfa zzfa = new zzfa(this);
        zzfa.zzX();
        this.zzd = zzfa;
        zzfv zzfv = new zzfv(this);
        zzfv.zzX();
        this.zzc = zzfv;
        this.zzB = new HashMap();
        this.zzC = new HashMap();
        zzaB().zzp(new zzkw(this, zzlh));
    }

    @VisibleForTesting
    static final void zzaa(zzfs zzfs, int i, String str) {
        List zzp2 = zzfs.zzp();
        int i2 = 0;
        while (i2 < zzp2.size()) {
            if (!"_err".equals(((zzfx) zzp2.get(i2)).zzg())) {
                i2++;
            } else {
                return;
            }
        }
        zzfw zze2 = zzfx.zze();
        zze2.zzj("_err");
        zze2.zzi(Long.valueOf((long) i).longValue());
        zzfw zze3 = zzfx.zze();
        zze3.zzj("_ev");
        zze3.zzk(str);
        zzfs.zzf((zzfx) zze2.zzaD());
        zzfs.zzf((zzfx) zze3.zzaD());
    }

    @VisibleForTesting
    static final void zzab(zzfs zzfs, @NonNull String str) {
        List zzp2 = zzfs.zzp();
        for (int i = 0; i < zzp2.size(); i++) {
            if (str.equals(((zzfx) zzp2.get(i)).zzg())) {
                zzfs.zzh(i);
                return;
            }
        }
    }

    @WorkerThread
    private final zzq zzac(String str) {
        String str2 = str;
        zzam zzam = this.zze;
        zzal(zzam);
        zzh zzj2 = zzam.zzj(str2);
        if (zzj2 == null || TextUtils.isEmpty(zzj2.zzx())) {
            zzaA().zzc().zzb("No app data available; dropping", str2);
            return null;
        }
        Boolean zzad = zzad(zzj2);
        if (zzad == null || zzad.booleanValue()) {
            zzh zzh2 = zzj2;
            zzh2.zza();
            return new zzq(str, zzj2.zzz(), zzj2.zzx(), zzj2.zzb(), zzj2.zzw(), zzj2.zzm(), zzj2.zzj(), (String) null, zzj2.zzal(), false, zzj2.zzy(), 0, 0, 0, zzh2.zzak(), false, zzh2.zzs(), zzh2.zzr(), zzh2.zzk(), zzh2.zzD(), (String) null, zzh(str).zzh(), "", (String) null, zzh2.zzan(), zzh2.zzq());
        }
        zzaA().zzd().zzb("App version does not match; dropping. appId", zzeu.zzn(str));
        return null;
    }

    @WorkerThread
    private final Boolean zzad(zzh zzh2) {
        try {
            if (zzh2.zzb() != -2147483648L) {
                if (zzh2.zzb() == ((long) Wrappers.packageManager(this.zzn.zzaw()).getPackageInfo(zzh2.zzu(), 0).versionCode)) {
                    return true;
                }
            } else {
                String str = Wrappers.packageManager(this.zzn.zzaw()).getPackageInfo(zzh2.zzu(), 0).versionName;
                String zzx2 = zzh2.zzx();
                if (zzx2 != null && zzx2.equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @WorkerThread
    private final void zzae() {
        zzaB().zzg();
        if (this.zzt || this.zzu || this.zzv) {
            zzaA().zzj().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzt), Boolean.valueOf(this.zzu), Boolean.valueOf(this.zzv));
            return;
        }
        zzaA().zzj().zza("Stopping uploading service(s)");
        List<Runnable> list = this.zzq;
        if (list != null) {
            for (Runnable run : list) {
                run.run();
            }
            ((List) Preconditions.checkNotNull(this.zzq)).clear();
        }
    }

    @VisibleForTesting
    private final void zzaf(zzgc zzgc, long j, boolean z) {
        String str;
        zzll zzll;
        String str2;
        zzam zzam = this.zze;
        zzal(zzam);
        if (true != z) {
            str = "_lte";
        } else {
            str = "_se";
        }
        zzll zzp2 = zzam.zzp(zzgc.zzaq(), str);
        if (zzp2 == null || zzp2.zze == null) {
            zzll = new zzll(zzgc.zzaq(), "auto", str, zzax().currentTimeMillis(), Long.valueOf(j));
        } else {
            zzll = new zzll(zzgc.zzaq(), "auto", str, zzax().currentTimeMillis(), Long.valueOf(((Long) zzp2.zze).longValue() + j));
        }
        zzgl zzd2 = zzgm.zzd();
        zzd2.zzf(str);
        zzd2.zzg(zzax().currentTimeMillis());
        zzd2.zze(((Long) zzll.zze).longValue());
        zzgm zzgm = (zzgm) zzd2.zzaD();
        int zza2 = zzli.zza(zzgc, str);
        if (zza2 >= 0) {
            zzgc.zzan(zza2, zzgm);
        } else {
            zzgc.zzm(zzgm);
        }
        if (j > 0) {
            zzam zzam2 = this.zze;
            zzal(zzam2);
            zzam2.zzL(zzll);
            if (true != z) {
                str2 = "lifetime";
            } else {
                str2 = "session-scoped";
            }
            zzaA().zzj().zzc("Updated engagement user property. scope, value", str2, zzll.zze);
        }
    }

    @WorkerThread
    private final void zzag() {
        long j;
        long j2;
        zzaB().zzg();
        zzB();
        if (this.zza > 0) {
            long abs = CostTimeUtil.HOUR - Math.abs(zzax().elapsedRealtime() - this.zza);
            if (abs > 0) {
                zzaA().zzj().zzb("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                zzm().zzc();
                zzkr zzkr = this.zzg;
                zzal(zzkr);
                zzkr.zza();
                return;
            }
            this.zza = 0;
        }
        if (!this.zzn.zzM() || !zzai()) {
            zzaA().zzj().zza("Nothing to upload or uploading impossible");
            zzm().zzc();
            zzkr zzkr2 = this.zzg;
            zzal(zzkr2);
            zzkr2.zza();
            return;
        }
        long currentTimeMillis = zzax().currentTimeMillis();
        zzg();
        long max = Math.max(0, ((Long) zzeh.zzA.zza((Object) null)).longValue());
        zzam zzam = this.zze;
        zzal(zzam);
        boolean z = true;
        if (!zzam.zzH()) {
            zzam zzam2 = this.zze;
            zzal(zzam2);
            if (!zzam2.zzG()) {
                z = false;
            }
        }
        if (z) {
            String zzl2 = zzg().zzl();
            if (TextUtils.isEmpty(zzl2) || ".none.".equals(zzl2)) {
                zzg();
                j = Math.max(0, ((Long) zzeh.zzu.zza((Object) null)).longValue());
            } else {
                zzg();
                j = Math.max(0, ((Long) zzeh.zzv.zza((Object) null)).longValue());
            }
        } else {
            zzg();
            j = Math.max(0, ((Long) zzeh.zzt.zza((Object) null)).longValue());
        }
        long zza2 = this.zzk.zzc.zza();
        long zza3 = this.zzk.zzd.zza();
        zzam zzam3 = this.zze;
        zzal(zzam3);
        boolean z2 = z;
        long zzd2 = zzam3.zzd();
        zzam zzam4 = this.zze;
        zzal(zzam4);
        long j3 = max;
        long max2 = Math.max(zzd2, zzam4.zze());
        if (max2 == 0) {
            j2 = 0;
        } else {
            long abs2 = currentTimeMillis - Math.abs(max2 - currentTimeMillis);
            long abs3 = currentTimeMillis - Math.abs(zza2 - currentTimeMillis);
            long abs4 = currentTimeMillis - Math.abs(zza3 - currentTimeMillis);
            j2 = abs2 + j3;
            long max3 = Math.max(abs3, abs4);
            if (z2 && max3 > 0) {
                j2 = Math.min(abs2, max3) + j;
            }
            zzli zzli = this.zzi;
            zzal(zzli);
            if (!zzli.zzw(max3, j)) {
                j2 = max3 + j;
            }
            if (abs4 != 0 && abs4 >= abs2) {
                int i = 0;
                while (true) {
                    zzg();
                    if (i >= Math.min(20, Math.max(0, ((Integer) zzeh.zzC.zza((Object) null)).intValue()))) {
                        j2 = 0;
                        break;
                    }
                    zzg();
                    j2 += Math.max(0, ((Long) zzeh.zzB.zza((Object) null)).longValue()) * (1 << i);
                    if (j2 > abs4) {
                        break;
                    }
                    i++;
                }
            }
        }
        if (j2 != 0) {
            zzfa zzfa = this.zzd;
            zzal(zzfa);
            if (zzfa.zza()) {
                long zza4 = this.zzk.zzb.zza();
                zzg();
                long max4 = Math.max(0, ((Long) zzeh.zzr.zza((Object) null)).longValue());
                zzli zzli2 = this.zzi;
                zzal(zzli2);
                if (!zzli2.zzw(zza4, max4)) {
                    j2 = Math.max(j2, zza4 + max4);
                }
                zzm().zzc();
                long currentTimeMillis2 = j2 - zzax().currentTimeMillis();
                if (currentTimeMillis2 <= 0) {
                    zzg();
                    currentTimeMillis2 = Math.max(0, ((Long) zzeh.zzw.zza((Object) null)).longValue());
                    this.zzk.zzc.zzb(zzax().currentTimeMillis());
                }
                zzaA().zzj().zzb("Upload scheduled in approximately ms", Long.valueOf(currentTimeMillis2));
                zzkr zzkr3 = this.zzg;
                zzal(zzkr3);
                zzkr3.zzd(currentTimeMillis2);
                return;
            }
            zzaA().zzj().zza("No network");
            zzm().zzb();
            zzkr zzkr4 = this.zzg;
            zzal(zzkr4);
            zzkr4.zza();
            return;
        }
        zzaA().zzj().zza("Next upload time is 0");
        zzm().zzc();
        zzkr zzkr5 = this.zzg;
        zzal(zzkr5);
        zzkr5.zza();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:367:0x0b7a, code lost:
        if (r10 > (com.google.android.gms.measurement.internal.zzag.zzA() + r8)) goto L_0x0b7c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01ad, code lost:
        r9 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01ae, code lost:
        switch(r9) {
            case 0: goto L_0x01bd;
            default: goto L_0x01b1;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01b1, code lost:
        r26 = r6;
        r23 = r8;
        r24 = r11;
        r25 = r15;
        r2 = false;
        r15 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01bd, code lost:
        r9 = 0;
        r14 = false;
        r22 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x085d A[Catch:{ NumberFormatException -> 0x07d6, all -> 0x0d13 }] */
    /* JADX WARNING: Removed duplicated region for block: B:278:0x0886 A[Catch:{ NumberFormatException -> 0x07d6, all -> 0x0d13 }] */
    /* JADX WARNING: Removed duplicated region for block: B:366:0x0b6a A[Catch:{ NumberFormatException -> 0x07d6, all -> 0x0d13 }] */
    /* JADX WARNING: Removed duplicated region for block: B:375:0x0bf1 A[Catch:{ NumberFormatException -> 0x07d6, all -> 0x0d13 }] */
    /* JADX WARNING: Removed duplicated region for block: B:379:0x0c0d A[Catch:{ SQLiteException -> 0x0c25 }] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzah(java.lang.String r41, long r42) {
        /*
            r40 = this;
            r1 = r40
            java.lang.String r2 = "_npa"
            java.lang.String r3 = "_ai"
            com.google.android.gms.measurement.internal.zzam r4 = r1.zze
            zzal(r4)
            r4.zzw()
            com.google.android.gms.measurement.internal.zzld r4 = new com.google.android.gms.measurement.internal.zzld     // Catch:{ all -> 0x0d13 }
            r12 = 0
            r4.<init>(r1, r12)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzam r5 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r5)     // Catch:{ all -> 0x0d13 }
            r6 = 0
            long r9 = r1.zzA     // Catch:{ all -> 0x0d13 }
            r7 = r42
            r11 = r4
            r5.zzU(r6, r7, r9, r11)     // Catch:{ all -> 0x0d13 }
            java.util.List r5 = r4.zzc     // Catch:{ all -> 0x0d13 }
            if (r5 == 0) goto L_0x0d01
            boolean r5 = r5.isEmpty()     // Catch:{ all -> 0x0d13 }
            if (r5 == 0) goto L_0x002e
            goto L_0x0d01
        L_0x002e:
            com.google.android.gms.internal.measurement.zzgd r5 = r4.zza     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzkx r5 = r5.zzbB()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgc r5 = (com.google.android.gms.internal.measurement.zzgc) r5     // Catch:{ all -> 0x0d13 }
            r5.zzr()     // Catch:{ all -> 0x0d13 }
            r11 = r12
            r14 = r11
            r8 = 0
            r9 = 0
            r10 = 0
            r13 = -1
            r15 = -1
        L_0x0040:
            java.util.List r12 = r4.zzc     // Catch:{ all -> 0x0d13 }
            int r12 = r12.size()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "_fr"
            java.lang.String r7 = "_et"
            r16 = r10
            java.lang.String r10 = "_e"
            r17 = r13
            r18 = r14
            if (r8 >= r12) goto L_0x052f
            java.util.List r12 = r4.zzc     // Catch:{ all -> 0x0d13 }
            java.lang.Object r12 = r12.get(r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r12 = (com.google.android.gms.internal.measurement.zzft) r12     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzkx r12 = r12.zzbB()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfs r12 = (com.google.android.gms.internal.measurement.zzfs) r12     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzfv r14 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r13 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r13 = r13.zzy()     // Catch:{ all -> 0x0d13 }
            r19 = r2
            java.lang.String r2 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r14.zzr(r13, r2)     // Catch:{ all -> 0x0d13 }
            java.lang.String r13 = "_err"
            if (r2 == 0) goto L_0x00f5
            com.google.android.gms.measurement.internal.zzeu r2 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Dropping blocked raw event. appId"
            com.google.android.gms.internal.measurement.zzgd r7 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r7 = r7.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r7)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzge r10 = r1.zzn     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzep r10 = r10.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.String r10 = r10.zzd(r14)     // Catch:{ all -> 0x0d13 }
            r2.zzc(r6, r7, r10)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzfv r2 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r6 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = r6.zzy()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r2.zzp(r6)     // Catch:{ all -> 0x0d13 }
            if (r2 != 0) goto L_0x00e8
            com.google.android.gms.measurement.internal.zzfv r2 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r6 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = r6.zzy()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r2.zzs(r6)     // Catch:{ all -> 0x0d13 }
            if (r2 == 0) goto L_0x00c3
            goto L_0x00e8
        L_0x00c3:
            java.lang.String r2 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r13.equals(r2)     // Catch:{ all -> 0x0d13 }
            if (r2 != 0) goto L_0x00e8
            com.google.android.gms.measurement.internal.zzlo r20 = r40.zzv()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzln r2 = r1.zzF     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r6 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r22 = r6.zzy()     // Catch:{ all -> 0x0d13 }
            r23 = 11
            java.lang.String r24 = "_ev"
            java.lang.String r25 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            r26 = 0
            r21 = r2
            r20.zzO(r21, r22, r23, r24, r25, r26)     // Catch:{ all -> 0x0d13 }
        L_0x00e8:
            r21 = r3
            r6 = r5
            r7 = r8
            r10 = r16
            r13 = r17
            r14 = r18
            r5 = -1
            goto L_0x0525
        L_0x00f5:
            java.lang.String r2 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = com.google.android.gms.measurement.internal.zzhb.zza(r3)     // Catch:{ all -> 0x0d13 }
            boolean r2 = r2.equals(r14)     // Catch:{ all -> 0x0d13 }
            if (r2 == 0) goto L_0x0171
            r12.zzi(r3)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r2 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = "Renaming ad_impression to _ai"
            r2.zza(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r2 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            java.lang.String r2 = r2.zzr()     // Catch:{ all -> 0x0d13 }
            r14 = 5
            boolean r2 = android.util.Log.isLoggable(r2, r14)     // Catch:{ all -> 0x0d13 }
            if (r2 == 0) goto L_0x016e
            r2 = 0
        L_0x0123:
            int r14 = r12.zza()     // Catch:{ all -> 0x0d13 }
            if (r2 >= r14) goto L_0x016b
            java.lang.String r14 = "ad_platform"
            com.google.android.gms.internal.measurement.zzfx r20 = r12.zzn(r2)     // Catch:{ all -> 0x0d13 }
            r21 = r3
            java.lang.String r3 = r20.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r14.equals(r3)     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0166
            com.google.android.gms.internal.measurement.zzfx r3 = r12.zzn(r2)     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = r3.zzh()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0d13 }
            if (r3 != 0) goto L_0x0166
            java.lang.String r3 = "admob"
            com.google.android.gms.internal.measurement.zzfx r14 = r12.zzn(r2)     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = r14.zzh()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r3.equalsIgnoreCase(r14)     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0166
            com.google.android.gms.measurement.internal.zzeu r3 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzl()     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = "AdMob ad impression logged from app. Potentially duplicative."
            r3.zza(r14)     // Catch:{ all -> 0x0d13 }
        L_0x0166:
            int r2 = r2 + 1
            r3 = r21
            goto L_0x0123
        L_0x016b:
            r21 = r3
            goto L_0x0173
        L_0x016e:
            r21 = r3
            goto L_0x0173
        L_0x0171:
            r21 = r3
        L_0x0173:
            com.google.android.gms.measurement.internal.zzfv r2 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r3 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = r3.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r2.zzq(r3, r14)     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = "_c"
            if (r2 != 0) goto L_0x01c2
            com.google.android.gms.measurement.internal.zzli r14 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r14)     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14)     // Catch:{ all -> 0x0d13 }
            int r20 = r14.hashCode()     // Catch:{ all -> 0x0d13 }
            switch(r20) {
                case 95027: goto L_0x01a0;
                default: goto L_0x019d;
            }
        L_0x019d:
            r20 = r9
            goto L_0x01ad
        L_0x01a0:
            r20 = r9
            java.lang.String r9 = "_ui"
            boolean r9 = r14.equals(r9)
            if (r9 == 0) goto L_0x01ad
            r9 = 0
            goto L_0x01ae
        L_0x01ad:
            r9 = -1
        L_0x01ae:
            switch(r9) {
                case 0: goto L_0x01bd;
                default: goto L_0x01b1;
            }
        L_0x01b1:
            r26 = r6
            r23 = r8
            r24 = r11
            r25 = r15
            r2 = 0
            r15 = r5
            goto L_0x039c
        L_0x01bd:
            r9 = 0
            r14 = 0
            r22 = 0
            goto L_0x01c8
        L_0x01c2:
            r20 = r9
            r9 = 0
            r14 = 0
            r22 = 0
        L_0x01c8:
            r23 = r8
            int r8 = r12.zza()     // Catch:{ all -> 0x0d13 }
            r24 = r11
            java.lang.String r11 = "_r"
            if (r9 >= r8) goto L_0x023e
            com.google.android.gms.internal.measurement.zzfx r8 = r12.zzn(r9)     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r8.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r8 = r3.equals(r8)     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x0201
            com.google.android.gms.internal.measurement.zzfx r8 = r12.zzn(r9)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzkx r8 = r8.zzbB()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfw r8 = (com.google.android.gms.internal.measurement.zzfw) r8     // Catch:{ all -> 0x0d13 }
            r25 = r15
            r14 = 1
            r8.zzi(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r8 = r8.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r8 = (com.google.android.gms.internal.measurement.zzfx) r8     // Catch:{ all -> 0x0d13 }
            r12.zzk(r9, r8)     // Catch:{ all -> 0x0d13 }
            r15 = r5
            r26 = r6
            r14 = 1
            goto L_0x0232
        L_0x0201:
            r25 = r15
            com.google.android.gms.internal.measurement.zzfx r8 = r12.zzn(r9)     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r8.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r8 = r11.equals(r8)     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x022f
            com.google.android.gms.internal.measurement.zzfx r8 = r12.zzn(r9)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzkx r8 = r8.zzbB()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfw r8 = (com.google.android.gms.internal.measurement.zzfw) r8     // Catch:{ all -> 0x0d13 }
            r15 = r5
            r26 = r6
            r5 = 1
            r8.zzi(r5)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r5 = r8.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r5 = (com.google.android.gms.internal.measurement.zzfx) r5     // Catch:{ all -> 0x0d13 }
            r12.zzk(r9, r5)     // Catch:{ all -> 0x0d13 }
            r22 = 1
            goto L_0x0232
        L_0x022f:
            r15 = r5
            r26 = r6
        L_0x0232:
            int r9 = r9 + 1
            r5 = r15
            r8 = r23
            r11 = r24
            r15 = r25
            r6 = r26
            goto L_0x01c8
        L_0x023e:
            r26 = r6
            r25 = r15
            r15 = r5
            if (r14 != 0) goto L_0x0271
            if (r2 == 0) goto L_0x0271
            com.google.android.gms.measurement.internal.zzeu r5 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Marking event as conversion"
            com.google.android.gms.measurement.internal.zzge r8 = r1.zzn     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzep r8 = r8.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r8.zzd(r9)     // Catch:{ all -> 0x0d13 }
            r5.zzb(r6, r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfw r5 = com.google.android.gms.internal.measurement.zzfx.zze()     // Catch:{ all -> 0x0d13 }
            r5.zzj(r3)     // Catch:{ all -> 0x0d13 }
            r8 = 1
            r5.zzi(r8)     // Catch:{ all -> 0x0d13 }
            r12.zze(r5)     // Catch:{ all -> 0x0d13 }
        L_0x0271:
            if (r22 != 0) goto L_0x029d
            com.google.android.gms.measurement.internal.zzeu r5 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Marking event as real-time"
            com.google.android.gms.measurement.internal.zzge r8 = r1.zzn     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzep r8 = r8.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r8.zzd(r9)     // Catch:{ all -> 0x0d13 }
            r5.zzb(r6, r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfw r5 = com.google.android.gms.internal.measurement.zzfx.zze()     // Catch:{ all -> 0x0d13 }
            r5.zzj(r11)     // Catch:{ all -> 0x0d13 }
            r8 = 1
            r5.zzi(r8)     // Catch:{ all -> 0x0d13 }
            r12.zze(r5)     // Catch:{ all -> 0x0d13 }
        L_0x029d:
            com.google.android.gms.measurement.internal.zzam r5 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r5)     // Catch:{ all -> 0x0d13 }
            long r28 = r40.zza()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r6 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r30 = r6.zzy()     // Catch:{ all -> 0x0d13 }
            r31 = 0
            r32 = 0
            r33 = 0
            r34 = 0
            r35 = 1
            r27 = r5
            com.google.android.gms.measurement.internal.zzak r5 = r27.zzl(r28, r30, r31, r32, r33, r34, r35)     // Catch:{ all -> 0x0d13 }
            long r5 = r5.zze     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzag r8 = r40.zzg()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r9 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzy()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeg r14 = com.google.android.gms.measurement.internal.zzeh.zzo     // Catch:{ all -> 0x0d13 }
            int r8 = r8.zze(r9, r14)     // Catch:{ all -> 0x0d13 }
            long r8 = (long) r8     // Catch:{ all -> 0x0d13 }
            int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r5 <= 0) goto L_0x02d7
            zzab(r12, r11)     // Catch:{ all -> 0x0d13 }
            goto L_0x02d9
        L_0x02d7:
            r16 = 1
        L_0x02d9:
            java.lang.String r5 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            boolean r5 = com.google.android.gms.measurement.internal.zzlo.zzak(r5)     // Catch:{ all -> 0x0d13 }
            if (r5 == 0) goto L_0x039b
            if (r2 == 0) goto L_0x039b
            com.google.android.gms.measurement.internal.zzam r5 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r5)     // Catch:{ all -> 0x0d13 }
            long r28 = r40.zza()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r6 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r30 = r6.zzy()     // Catch:{ all -> 0x0d13 }
            r31 = 0
            r32 = 0
            r33 = 1
            r34 = 0
            r35 = 0
            r27 = r5
            com.google.android.gms.measurement.internal.zzak r5 = r27.zzl(r28, r30, r31, r32, r33, r34, r35)     // Catch:{ all -> 0x0d13 }
            long r5 = r5.zzc     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzag r8 = r40.zzg()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r9 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzy()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeg r11 = com.google.android.gms.measurement.internal.zzeh.zzn     // Catch:{ all -> 0x0d13 }
            int r8 = r8.zze(r9, r11)     // Catch:{ all -> 0x0d13 }
            long r8 = (long) r8     // Catch:{ all -> 0x0d13 }
            int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r5 <= 0) goto L_0x039b
            com.google.android.gms.measurement.internal.zzeu r5 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Too many conversions. Not logging as conversion. appId"
            com.google.android.gms.internal.measurement.zzgd r8 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r8.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r8)     // Catch:{ all -> 0x0d13 }
            r5.zzb(r6, r8)     // Catch:{ all -> 0x0d13 }
            r5 = 0
            r6 = 0
            r8 = 0
            r9 = -1
        L_0x0336:
            int r11 = r12.zza()     // Catch:{ all -> 0x0d13 }
            if (r6 >= r11) goto L_0x0361
            com.google.android.gms.internal.measurement.zzfx r11 = r12.zzn(r6)     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = r11.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r14 = r3.equals(r14)     // Catch:{ all -> 0x0d13 }
            if (r14 == 0) goto L_0x0352
            com.google.android.gms.internal.measurement.zzkx r5 = r11.zzbB()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfw r5 = (com.google.android.gms.internal.measurement.zzfw) r5     // Catch:{ all -> 0x0d13 }
            r9 = r6
            goto L_0x035e
        L_0x0352:
            java.lang.String r11 = r11.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r11 = r13.equals(r11)     // Catch:{ all -> 0x0d13 }
            if (r11 == 0) goto L_0x035e
            r8 = 1
        L_0x035e:
            int r6 = r6 + 1
            goto L_0x0336
        L_0x0361:
            if (r8 == 0) goto L_0x036a
            if (r5 == 0) goto L_0x0369
            r12.zzh(r9)     // Catch:{ all -> 0x0d13 }
            goto L_0x039c
        L_0x0369:
            r5 = 0
        L_0x036a:
            if (r5 == 0) goto L_0x0384
            com.google.android.gms.internal.measurement.zzkx r5 = r5.zzav()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfw r5 = (com.google.android.gms.internal.measurement.zzfw) r5     // Catch:{ all -> 0x0d13 }
            r5.zzj(r13)     // Catch:{ all -> 0x0d13 }
            r13 = 10
            r5.zzi(r13)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r5 = r5.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r5 = (com.google.android.gms.internal.measurement.zzfx) r5     // Catch:{ all -> 0x0d13 }
            r12.zzk(r9, r5)     // Catch:{ all -> 0x0d13 }
            goto L_0x039c
        L_0x0384:
            com.google.android.gms.measurement.internal.zzeu r5 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Did not find conversion parameter. appId"
            com.google.android.gms.internal.measurement.zzgd r8 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r8.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r8)     // Catch:{ all -> 0x0d13 }
            r5.zzb(r6, r8)     // Catch:{ all -> 0x0d13 }
        L_0x039b:
        L_0x039c:
            if (r2 == 0) goto L_0x0458
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0d13 }
            java.util.List r5 = r12.zzp()     // Catch:{ all -> 0x0d13 }
            r2.<init>(r5)     // Catch:{ all -> 0x0d13 }
            r5 = 0
            r6 = -1
            r8 = -1
        L_0x03aa:
            int r9 = r2.size()     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = "currency"
            java.lang.String r13 = "value"
            if (r5 >= r9) goto L_0x03db
            java.lang.Object r9 = r2.get(r5)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r9 = (com.google.android.gms.internal.measurement.zzfx) r9     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r9 = r13.equals(r9)     // Catch:{ all -> 0x0d13 }
            if (r9 == 0) goto L_0x03c7
            r6 = r5
            goto L_0x03d8
        L_0x03c7:
            java.lang.Object r9 = r2.get(r5)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r9 = (com.google.android.gms.internal.measurement.zzfx) r9     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r9 = r11.equals(r9)     // Catch:{ all -> 0x0d13 }
            if (r9 == 0) goto L_0x03d8
            r8 = r5
        L_0x03d8:
            int r5 = r5 + 1
            goto L_0x03aa
        L_0x03db:
            r5 = -1
            if (r6 != r5) goto L_0x03e1
            r5 = -1
            goto L_0x0459
        L_0x03e1:
            java.lang.Object r5 = r2.get(r6)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r5 = (com.google.android.gms.internal.measurement.zzfx) r5     // Catch:{ all -> 0x0d13 }
            boolean r5 = r5.zzw()     // Catch:{ all -> 0x0d13 }
            if (r5 != 0) goto L_0x0413
            java.lang.Object r5 = r2.get(r6)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r5 = (com.google.android.gms.internal.measurement.zzfx) r5     // Catch:{ all -> 0x0d13 }
            boolean r5 = r5.zzu()     // Catch:{ all -> 0x0d13 }
            if (r5 != 0) goto L_0x0413
            com.google.android.gms.measurement.internal.zzeu r2 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzl()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "Value must be specified with a numeric type."
            r2.zza(r5)     // Catch:{ all -> 0x0d13 }
            r12.zzh(r6)     // Catch:{ all -> 0x0d13 }
            zzab(r12, r3)     // Catch:{ all -> 0x0d13 }
            r2 = 18
            zzaa(r12, r2, r13)     // Catch:{ all -> 0x0d13 }
            r5 = -1
            goto L_0x0459
        L_0x0413:
            r5 = -1
            if (r8 != r5) goto L_0x0417
            goto L_0x043f
        L_0x0417:
            java.lang.Object r2 = r2.get(r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r2 = (com.google.android.gms.internal.measurement.zzfx) r2     // Catch:{ all -> 0x0d13 }
            java.lang.String r2 = r2.zzh()     // Catch:{ all -> 0x0d13 }
            int r8 = r2.length()     // Catch:{ all -> 0x0d13 }
            r9 = 3
            if (r8 != r9) goto L_0x043f
            r8 = 0
        L_0x0429:
            int r9 = r2.length()     // Catch:{ all -> 0x0d13 }
            if (r8 >= r9) goto L_0x0459
            int r9 = r2.codePointAt(r8)     // Catch:{ all -> 0x0d13 }
            boolean r13 = java.lang.Character.isLetter(r9)     // Catch:{ all -> 0x0d13 }
            if (r13 == 0) goto L_0x043f
            int r9 = java.lang.Character.charCount(r9)     // Catch:{ all -> 0x0d13 }
            int r8 = r8 + r9
            goto L_0x0429
        L_0x043f:
            com.google.android.gms.measurement.internal.zzeu r2 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzl()     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = "Value parameter discarded. You must also supply a 3-letter ISO_4217 currency code in the currency parameter."
            r2.zza(r8)     // Catch:{ all -> 0x0d13 }
            r12.zzh(r6)     // Catch:{ all -> 0x0d13 }
            zzab(r12, r3)     // Catch:{ all -> 0x0d13 }
            r2 = 19
            zzaa(r12, r2, r11)     // Catch:{ all -> 0x0d13 }
            goto L_0x0459
        L_0x0458:
            r5 = -1
        L_0x0459:
            java.lang.String r2 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r10.equals(r2)     // Catch:{ all -> 0x0d13 }
            r8 = 1000(0x3e8, double:4.94E-321)
            if (r2 == 0) goto L_0x04b6
            com.google.android.gms.measurement.internal.zzli r2 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r2 = r12.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r2 = (com.google.android.gms.internal.measurement.zzft) r2     // Catch:{ all -> 0x0d13 }
            r3 = r26
            com.google.android.gms.internal.measurement.zzfx r2 = com.google.android.gms.measurement.internal.zzli.zzB(r2, r3)     // Catch:{ all -> 0x0d13 }
            if (r2 != 0) goto L_0x04b0
            if (r18 == 0) goto L_0x04a5
            long r2 = r18.zzc()     // Catch:{ all -> 0x0d13 }
            long r6 = r12.zzc()     // Catch:{ all -> 0x0d13 }
            long r2 = r2 - r6
            long r2 = java.lang.Math.abs(r2)     // Catch:{ all -> 0x0d13 }
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 > 0) goto L_0x04a5
            com.google.android.gms.internal.measurement.zzkx r2 = r18.zzav()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfs r2 = (com.google.android.gms.internal.measurement.zzfs) r2     // Catch:{ all -> 0x0d13 }
            boolean r3 = r1.zzaj(r12, r2)     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x04a5
            r6 = r15
            r3 = r25
            r6.zzS(r3, r2)     // Catch:{ all -> 0x0d13 }
            r15 = r3
            r13 = r17
            r11 = 0
            r14 = 0
            goto L_0x0511
        L_0x04a5:
            r6 = r15
            r3 = r25
            r15 = r3
            r11 = r12
            r14 = r18
            r13 = r20
            goto L_0x0511
        L_0x04b0:
            r6 = r15
            r3 = r25
            r7 = r17
            goto L_0x050b
        L_0x04b6:
            r6 = r15
            r3 = r25
            java.lang.String r2 = "_vs"
            java.lang.String r10 = r12.zzo()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r2.equals(r10)     // Catch:{ all -> 0x0d13 }
            if (r2 == 0) goto L_0x0509
            com.google.android.gms.measurement.internal.zzli r2 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r2 = r12.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r2 = (com.google.android.gms.internal.measurement.zzft) r2     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r2 = com.google.android.gms.measurement.internal.zzli.zzB(r2, r7)     // Catch:{ all -> 0x0d13 }
            if (r2 != 0) goto L_0x0506
            if (r24 == 0) goto L_0x04ff
            long r10 = r24.zzc()     // Catch:{ all -> 0x0d13 }
            long r13 = r12.zzc()     // Catch:{ all -> 0x0d13 }
            long r10 = r10 - r13
            long r10 = java.lang.Math.abs(r10)     // Catch:{ all -> 0x0d13 }
            int r2 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r2 > 0) goto L_0x04ff
            com.google.android.gms.internal.measurement.zzkx r2 = r24.zzav()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfs r2 = (com.google.android.gms.internal.measurement.zzfs) r2     // Catch:{ all -> 0x0d13 }
            boolean r7 = r1.zzaj(r2, r12)     // Catch:{ all -> 0x0d13 }
            if (r7 == 0) goto L_0x04ff
            r7 = r17
            r6.zzS(r7, r2)     // Catch:{ all -> 0x0d13 }
            r15 = r3
            r13 = r7
            r11 = 0
            r14 = 0
            goto L_0x0511
        L_0x04ff:
            r7 = r17
            r13 = r7
            r14 = r12
            r15 = r20
            goto L_0x050f
        L_0x0506:
            r7 = r17
            goto L_0x050b
        L_0x0509:
            r7 = r17
        L_0x050b:
            r15 = r3
            r13 = r7
            r14 = r18
        L_0x050f:
            r11 = r24
        L_0x0511:
            java.util.List r2 = r4.zzc     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r3 = r12.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r3 = (com.google.android.gms.internal.measurement.zzft) r3     // Catch:{ all -> 0x0d13 }
            r7 = r23
            r2.set(r7, r3)     // Catch:{ all -> 0x0d13 }
            int r9 = r20 + 1
            r6.zzk(r12)     // Catch:{ all -> 0x0d13 }
            r10 = r16
        L_0x0525:
            int r8 = r7 + 1
            r5 = r6
            r2 = r19
            r3 = r21
            r12 = 0
            goto L_0x0040
        L_0x052f:
            r19 = r2
            r3 = r6
            r20 = r9
            r6 = r5
            r8 = 0
            r11 = r8
            r5 = r20
            r2 = 0
        L_0x053b:
            if (r2 >= r5) goto L_0x058d
            com.google.android.gms.internal.measurement.zzft r13 = r6.zze(r2)     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = r13.zzh()     // Catch:{ all -> 0x0d13 }
            boolean r14 = r10.equals(r14)     // Catch:{ all -> 0x0d13 }
            if (r14 == 0) goto L_0x055e
            com.google.android.gms.measurement.internal.zzli r14 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r14 = com.google.android.gms.measurement.internal.zzli.zzB(r13, r3)     // Catch:{ all -> 0x0d13 }
            if (r14 == 0) goto L_0x055e
            r6.zzA(r2)     // Catch:{ all -> 0x0d13 }
            int r5 = r5 + -1
            int r2 = r2 + -1
            goto L_0x058a
        L_0x055e:
            com.google.android.gms.measurement.internal.zzli r14 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r13 = com.google.android.gms.measurement.internal.zzli.zzB(r13, r7)     // Catch:{ all -> 0x0d13 }
            if (r13 == 0) goto L_0x0589
            boolean r14 = r13.zzw()     // Catch:{ all -> 0x0d13 }
            if (r14 == 0) goto L_0x0578
            long r13 = r13.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.Long r13 = java.lang.Long.valueOf(r13)     // Catch:{ all -> 0x0d13 }
            goto L_0x0579
        L_0x0578:
            r13 = 0
        L_0x0579:
            if (r13 == 0) goto L_0x0589
            long r14 = r13.longValue()     // Catch:{ all -> 0x0d13 }
            int r14 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r14 <= 0) goto L_0x0589
            long r13 = r13.longValue()     // Catch:{ all -> 0x0d13 }
            long r11 = r11 + r13
            goto L_0x058a
        L_0x0589:
        L_0x058a:
            r13 = 1
            int r2 = r2 + r13
            goto L_0x053b
        L_0x058d:
            r2 = 0
            r1.zzaf(r6, r11, r2)     // Catch:{ all -> 0x0d13 }
            java.util.List r2 = r6.zzat()     // Catch:{ all -> 0x0d13 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0d13 }
        L_0x059a:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "_se"
            if (r3 == 0) goto L_0x05c0
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r3 = (com.google.android.gms.internal.measurement.zzft) r3     // Catch:{ all -> 0x0d13 }
            java.lang.String r7 = "_s"
            java.lang.String r3 = r3.zzh()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r7.equals(r3)     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x059a
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = r6.zzaq()     // Catch:{ all -> 0x0d13 }
            r2.zzA(r3, r5)     // Catch:{ all -> 0x0d13 }
        L_0x05c0:
            java.lang.String r2 = "_sid"
            int r2 = com.google.android.gms.measurement.internal.zzli.zza(r6, r2)     // Catch:{ all -> 0x0d13 }
            if (r2 < 0) goto L_0x05cd
            r2 = 1
            r1.zzaf(r6, r11, r2)     // Catch:{ all -> 0x0d13 }
            goto L_0x05ee
        L_0x05cd:
            int r2 = com.google.android.gms.measurement.internal.zzli.zza(r6, r5)     // Catch:{ all -> 0x0d13 }
            if (r2 < 0) goto L_0x05ee
            r6.zzB(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r2 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = "Session engagement user property is in the bundle without session ID. appId"
            com.google.android.gms.internal.measurement.zzgd r5 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = r5.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r5)     // Catch:{ all -> 0x0d13 }
            r2.zzb(r3, r5)     // Catch:{ all -> 0x0d13 }
        L_0x05ee:
            com.google.android.gms.measurement.internal.zzli r2 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzge r3 = r2.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "Checking account type status for ad personalization signals"
            r3.zza(r5)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzlg r3 = r2.zzf     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzfv r3 = r3.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = r6.zzaq()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r3.zzn(r5)     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0685
            com.google.android.gms.measurement.internal.zzlg r3 = r2.zzf     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzam r3 = r3.zze     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = r6.zzaq()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzh r3 = r3.zzj(r5)     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0685
            boolean r3 = r3.zzak()     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0685
            com.google.android.gms.measurement.internal.zzge r3 = r2.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzaq r3 = r3.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r3.zze()     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0685
            com.google.android.gms.measurement.internal.zzge r3 = r2.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzc()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "Turning off ad personalization due to account type"
            r3.zza(r5)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgl r3 = com.google.android.gms.internal.measurement.zzgm.zzd()     // Catch:{ all -> 0x0d13 }
            r5 = r19
            r3.zzf(r5)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzaq r2 = r2.zzg()     // Catch:{ all -> 0x0d13 }
            long r10 = r2.zza()     // Catch:{ all -> 0x0d13 }
            r3.zzg(r10)     // Catch:{ all -> 0x0d13 }
            r10 = 1
            r3.zze(r10)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r2 = r3.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgm r2 = (com.google.android.gms.internal.measurement.zzgm) r2     // Catch:{ all -> 0x0d13 }
            r3 = 0
        L_0x0667:
            int r7 = r6.zzb()     // Catch:{ all -> 0x0d13 }
            if (r3 >= r7) goto L_0x0682
            com.google.android.gms.internal.measurement.zzgm r7 = r6.zzap(r3)     // Catch:{ all -> 0x0d13 }
            java.lang.String r7 = r7.zzf()     // Catch:{ all -> 0x0d13 }
            boolean r7 = r5.equals(r7)     // Catch:{ all -> 0x0d13 }
            if (r7 == 0) goto L_0x067f
            r6.zzan(r3, r2)     // Catch:{ all -> 0x0d13 }
            goto L_0x0685
        L_0x067f:
            int r3 = r3 + 1
            goto L_0x0667
        L_0x0682:
            r6.zzm(r2)     // Catch:{ all -> 0x0d13 }
        L_0x0685:
            r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r6.zzai(r2)     // Catch:{ all -> 0x0d13 }
            r2 = -9223372036854775808
            r6.zzQ(r2)     // Catch:{ all -> 0x0d13 }
            r2 = 0
        L_0x0693:
            int r3 = r6.zza()     // Catch:{ all -> 0x0d13 }
            if (r2 >= r3) goto L_0x06c6
            com.google.android.gms.internal.measurement.zzft r3 = r6.zze(r2)     // Catch:{ all -> 0x0d13 }
            long r10 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            long r12 = r6.zzd()     // Catch:{ all -> 0x0d13 }
            int r5 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r5 >= 0) goto L_0x06b0
            long r10 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            r6.zzai(r10)     // Catch:{ all -> 0x0d13 }
        L_0x06b0:
            long r10 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            long r12 = r6.zzc()     // Catch:{ all -> 0x0d13 }
            int r5 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r5 <= 0) goto L_0x06c3
            long r10 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            r6.zzQ(r10)     // Catch:{ all -> 0x0d13 }
        L_0x06c3:
            int r2 = r2 + 1
            goto L_0x0693
        L_0x06c6:
            r6.zzz()     // Catch:{ all -> 0x0d13 }
            r6.zzo()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzaa r10 = r1.zzh     // Catch:{ all -> 0x0d13 }
            zzal(r10)     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = r6.zzaq()     // Catch:{ all -> 0x0d13 }
            java.util.List r12 = r6.zzat()     // Catch:{ all -> 0x0d13 }
            java.util.List r13 = r6.zzau()     // Catch:{ all -> 0x0d13 }
            long r2 = r6.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.Long r14 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x0d13 }
            long r2 = r6.zzc()     // Catch:{ all -> 0x0d13 }
            java.lang.Long r15 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x0d13 }
            java.util.List r2 = r10.zza(r11, r12, r13, r14, r15)     // Catch:{ all -> 0x0d13 }
            r6.zzf(r2)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzag r2 = r40.zzg()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r3 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = r3.zzy()     // Catch:{ all -> 0x0d13 }
            boolean r2 = r2.zzw(r3)     // Catch:{ all -> 0x0d13 }
            if (r2 == 0) goto L_0x0a4e
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x0d13 }
            r2.<init>()     // Catch:{ all -> 0x0d13 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0d13 }
            r3.<init>()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzlo r5 = r40.zzv()     // Catch:{ all -> 0x0d13 }
            java.security.SecureRandom r5 = r5.zzG()     // Catch:{ all -> 0x0d13 }
            r7 = 0
        L_0x0717:
            int r10 = r6.zza()     // Catch:{ all -> 0x0d13 }
            if (r7 >= r10) goto L_0x0a1a
            com.google.android.gms.internal.measurement.zzft r10 = r6.zze(r7)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzkx r10 = r10.zzbB()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfs r10 = (com.google.android.gms.internal.measurement.zzfs) r10     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.String r12 = "_ep"
            boolean r11 = r11.equals(r12)     // Catch:{ all -> 0x0d13 }
            java.lang.String r12 = "_efs"
            java.lang.String r13 = "_sr"
            if (r11 == 0) goto L_0x07ba
            com.google.android.gms.measurement.internal.zzli r11 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r11)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r11 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r11 = (com.google.android.gms.internal.measurement.zzft) r11     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = "_en"
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzli.zzC(r11, r14)     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x0d13 }
            java.lang.Object r14 = r2.get(r11)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r14 = (com.google.android.gms.measurement.internal.zzas) r14     // Catch:{ all -> 0x0d13 }
            if (r14 != 0) goto L_0x076f
            com.google.android.gms.measurement.internal.zzam r14 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r15 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r15 = r15.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r17 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r11)     // Catch:{ all -> 0x0d13 }
            r8 = r17
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r14 = r14.zzn(r15, r8)     // Catch:{ all -> 0x0d13 }
            if (r14 == 0) goto L_0x076f
            r2.put(r11, r14)     // Catch:{ all -> 0x0d13 }
            goto L_0x0770
        L_0x076f:
        L_0x0770:
            if (r14 == 0) goto L_0x07af
            java.lang.Long r8 = r14.zzi     // Catch:{ all -> 0x0d13 }
            if (r8 != 0) goto L_0x07af
            java.lang.Long r8 = r14.zzj     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x078e
            long r8 = r8.longValue()     // Catch:{ all -> 0x0d13 }
            r17 = 1
            int r8 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1))
            if (r8 <= 0) goto L_0x078e
            com.google.android.gms.measurement.internal.zzli r8 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r8)     // Catch:{ all -> 0x0d13 }
            java.lang.Long r8 = r14.zzj     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzli.zzz(r10, r13, r8)     // Catch:{ all -> 0x0d13 }
        L_0x078e:
            java.lang.Boolean r8 = r14.zzk     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x07a6
            boolean r8 = r8.booleanValue()     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x07a6
            com.google.android.gms.measurement.internal.zzli r8 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r8)     // Catch:{ all -> 0x0d13 }
            r8 = 1
            java.lang.Long r11 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzli.zzz(r10, r12, r11)     // Catch:{ all -> 0x0d13 }
        L_0x07a6:
            com.google.android.gms.internal.measurement.zzlb r8 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r8 = (com.google.android.gms.internal.measurement.zzft) r8     // Catch:{ all -> 0x0d13 }
            r3.add(r8)     // Catch:{ all -> 0x0d13 }
        L_0x07af:
            r6.zzS(r7, r10)     // Catch:{ all -> 0x0d13 }
            r21 = r5
            r8 = r7
            r7 = r6
            r5 = 1
            goto L_0x0a10
        L_0x07ba:
            com.google.android.gms.measurement.internal.zzfv r8 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r9 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = "measurement.account.time_zone_offset_minutes"
            java.lang.String r11 = r8.zza(r9, r11)     // Catch:{ all -> 0x0d13 }
            boolean r14 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x0d13 }
            if (r14 != 0) goto L_0x07ee
            long r8 = java.lang.Long.parseLong(r11)     // Catch:{ NumberFormatException -> 0x07d6 }
            goto L_0x07f0
        L_0x07d6:
            r0 = move-exception
            r11 = r0
            com.google.android.gms.measurement.internal.zzge r8 = r8.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r14 = "Unable to parse timezone offset. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r9)     // Catch:{ all -> 0x0d13 }
            r8.zzc(r14, r9, r11)     // Catch:{ all -> 0x0d13 }
            r8 = 0
            goto L_0x07f0
        L_0x07ee:
            r8 = 0
        L_0x07f0:
            com.google.android.gms.measurement.internal.zzlo r11 = r40.zzv()     // Catch:{ all -> 0x0d13 }
            long r14 = r10.zzc()     // Catch:{ all -> 0x0d13 }
            long r14 = r11.zzr(r14, r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r11 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r11 = (com.google.android.gms.internal.measurement.zzft) r11     // Catch:{ all -> 0x0d13 }
            r43 = r12
            r17 = 1
            java.lang.Long r12 = java.lang.Long.valueOf(r17)     // Catch:{ all -> 0x0d13 }
            r17 = r8
            java.lang.String r8 = "_dbg"
            boolean r9 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x0d13 }
            if (r9 != 0) goto L_0x0848
            java.util.List r9 = r11.zzi()     // Catch:{ all -> 0x0d13 }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x0d13 }
        L_0x081c:
            boolean r11 = r9.hasNext()     // Catch:{ all -> 0x0d13 }
            if (r11 == 0) goto L_0x0848
            java.lang.Object r11 = r9.next()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzfx r11 = (com.google.android.gms.internal.measurement.zzfx) r11     // Catch:{ all -> 0x0d13 }
            r21 = r9
            java.lang.String r9 = r11.zzg()     // Catch:{ all -> 0x0d13 }
            boolean r9 = r8.equals(r9)     // Catch:{ all -> 0x0d13 }
            if (r9 == 0) goto L_0x0845
            long r8 = r11.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0d13 }
            boolean r8 = r12.equals(r8)     // Catch:{ all -> 0x0d13 }
            if (r8 != 0) goto L_0x0843
            goto L_0x0848
        L_0x0843:
            r8 = 1
            goto L_0x085b
        L_0x0845:
            r9 = r21
            goto L_0x081c
        L_0x0848:
            com.google.android.gms.measurement.internal.zzfv r8 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r9 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            int r8 = r8.zzc(r9, r11)     // Catch:{ all -> 0x0d13 }
        L_0x085b:
            if (r8 > 0) goto L_0x0886
            com.google.android.gms.measurement.internal.zzeu r9 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = "Sample rate must be positive. event, rate"
            java.lang.String r12 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0d13 }
            r9.zzc(r11, r12, r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r8 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r8 = (com.google.android.gms.internal.measurement.zzft) r8     // Catch:{ all -> 0x0d13 }
            r3.add(r8)     // Catch:{ all -> 0x0d13 }
            r6.zzS(r7, r10)     // Catch:{ all -> 0x0d13 }
            r21 = r5
            r8 = r7
            r7 = r6
            r5 = 1
            goto L_0x0a10
        L_0x0886:
            java.lang.String r9 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r9 = r2.get(r9)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r9 = (com.google.android.gms.measurement.internal.zzas) r9     // Catch:{ all -> 0x0d13 }
            if (r9 != 0) goto L_0x08e9
            com.google.android.gms.measurement.internal.zzam r9 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r9)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r11 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = r11.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.String r12 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r9 = r9.zzn(r11, r12)     // Catch:{ all -> 0x0d13 }
            if (r9 != 0) goto L_0x08e6
            com.google.android.gms.measurement.internal.zzeu r9 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = "Event being bundled has no eventAggregate. appId, eventName"
            com.google.android.gms.internal.measurement.zzgd r12 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r12 = r12.zzy()     // Catch:{ all -> 0x0d13 }
            r21 = r14
            java.lang.String r14 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            r9.zzc(r11, r12, r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r9 = new com.google.android.gms.measurement.internal.zzas     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r11 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r24 = r11.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.String r25 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            r26 = 1
            r28 = 1
            r30 = 1
            long r32 = r10.zzc()     // Catch:{ all -> 0x0d13 }
            r34 = 0
            r36 = 0
            r37 = 0
            r38 = 0
            r39 = 0
            r23 = r9
            r23.<init>(r24, r25, r26, r28, r30, r32, r34, r36, r37, r38, r39)     // Catch:{ all -> 0x0d13 }
            goto L_0x08eb
        L_0x08e6:
            r21 = r14
            goto L_0x08eb
        L_0x08e9:
            r21 = r14
        L_0x08eb:
            com.google.android.gms.measurement.internal.zzli r11 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r11)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r11 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r11 = (com.google.android.gms.internal.measurement.zzft) r11     // Catch:{ all -> 0x0d13 }
            java.lang.String r12 = "_eid"
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzli.zzC(r11, r12)     // Catch:{ all -> 0x0d13 }
            java.lang.Long r11 = (java.lang.Long) r11     // Catch:{ all -> 0x0d13 }
            if (r11 == 0) goto L_0x0902
            r12 = 1
            goto L_0x0903
        L_0x0902:
            r12 = 0
        L_0x0903:
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x0d13 }
            r14 = 1
            if (r8 != r14) goto L_0x093c
            com.google.android.gms.internal.measurement.zzlb r8 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r8 = (com.google.android.gms.internal.measurement.zzft) r8     // Catch:{ all -> 0x0d13 }
            r3.add(r8)     // Catch:{ all -> 0x0d13 }
            boolean r8 = r12.booleanValue()     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x0931
            java.lang.Long r8 = r9.zzi     // Catch:{ all -> 0x0d13 }
            if (r8 != 0) goto L_0x0925
            java.lang.Long r8 = r9.zzj     // Catch:{ all -> 0x0d13 }
            if (r8 != 0) goto L_0x0925
            java.lang.Boolean r8 = r9.zzk     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x0931
        L_0x0925:
            r8 = 0
            com.google.android.gms.measurement.internal.zzas r9 = r9.zza(r8, r8, r8)     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            r2.put(r8, r9)     // Catch:{ all -> 0x0d13 }
        L_0x0931:
            r6.zzS(r7, r10)     // Catch:{ all -> 0x0d13 }
            r21 = r5
            r8 = r7
            r7 = r6
            r5 = 1
            goto L_0x0a10
        L_0x093c:
            int r14 = r5.nextInt(r8)     // Catch:{ all -> 0x0d13 }
            if (r14 != 0) goto L_0x097e
            com.google.android.gms.measurement.internal.zzli r11 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r11)     // Catch:{ all -> 0x0d13 }
            long r14 = (long) r8     // Catch:{ all -> 0x0d13 }
            java.lang.Long r8 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzli.zzz(r10, r13, r8)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r11 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r11 = (com.google.android.gms.internal.measurement.zzft) r11     // Catch:{ all -> 0x0d13 }
            r3.add(r11)     // Catch:{ all -> 0x0d13 }
            boolean r11 = r12.booleanValue()     // Catch:{ all -> 0x0d13 }
            if (r11 == 0) goto L_0x0963
            r11 = 0
            com.google.android.gms.measurement.internal.zzas r9 = r9.zza(r11, r8, r11)     // Catch:{ all -> 0x0d13 }
        L_0x0963:
            java.lang.String r8 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            long r11 = r10.zzc()     // Catch:{ all -> 0x0d13 }
            r14 = r21
            com.google.android.gms.measurement.internal.zzas r9 = r9.zzb(r11, r14)     // Catch:{ all -> 0x0d13 }
            r2.put(r8, r9)     // Catch:{ all -> 0x0d13 }
            r21 = r5
            r22 = r6
            r23 = r7
            r5 = 1
            goto L_0x0a09
        L_0x097e:
            r14 = r21
            r21 = r5
            java.lang.Long r5 = r9.zzh     // Catch:{ all -> 0x0d13 }
            if (r5 == 0) goto L_0x0993
            long r17 = r5.longValue()     // Catch:{ all -> 0x0d13 }
            r22 = r6
            r23 = r7
            r24 = r11
            r25 = r12
            goto L_0x09a9
        L_0x0993:
            com.google.android.gms.measurement.internal.zzlo r5 = r40.zzv()     // Catch:{ all -> 0x0d13 }
            r22 = r6
            r23 = r7
            long r6 = r10.zzb()     // Catch:{ all -> 0x0d13 }
            r24 = r11
            r25 = r12
            r11 = r17
            long r17 = r5.zzr(r6, r11)     // Catch:{ all -> 0x0d13 }
        L_0x09a9:
            int r5 = (r17 > r14 ? 1 : (r17 == r14 ? 0 : -1))
            if (r5 == 0) goto L_0x09f3
            com.google.android.gms.measurement.internal.zzli r5 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r5)     // Catch:{ all -> 0x0d13 }
            r5 = 1
            java.lang.Long r7 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0d13 }
            r11 = r43
            com.google.android.gms.measurement.internal.zzli.zzz(r10, r11, r7)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzli r7 = r1.zzi     // Catch:{ all -> 0x0d13 }
            zzal(r7)     // Catch:{ all -> 0x0d13 }
            long r7 = (long) r8     // Catch:{ all -> 0x0d13 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzli.zzz(r10, r13, r7)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r8 = r10.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzft r8 = (com.google.android.gms.internal.measurement.zzft) r8     // Catch:{ all -> 0x0d13 }
            r3.add(r8)     // Catch:{ all -> 0x0d13 }
            boolean r8 = r25.booleanValue()     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x09e3
            r8 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r8)     // Catch:{ all -> 0x0d13 }
            r8 = 0
            com.google.android.gms.measurement.internal.zzas r9 = r9.zza(r8, r7, r11)     // Catch:{ all -> 0x0d13 }
        L_0x09e3:
            java.lang.String r7 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            long r11 = r10.zzc()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r8 = r9.zzb(r11, r14)     // Catch:{ all -> 0x0d13 }
            r2.put(r7, r8)     // Catch:{ all -> 0x0d13 }
            goto L_0x0a09
        L_0x09f3:
            r5 = 1
            boolean r7 = r25.booleanValue()     // Catch:{ all -> 0x0d13 }
            if (r7 == 0) goto L_0x0a09
            java.lang.String r7 = r10.zzo()     // Catch:{ all -> 0x0d13 }
            r11 = r24
            r8 = 0
            com.google.android.gms.measurement.internal.zzas r9 = r9.zza(r11, r8, r8)     // Catch:{ all -> 0x0d13 }
            r2.put(r7, r9)     // Catch:{ all -> 0x0d13 }
        L_0x0a09:
            r7 = r22
            r8 = r23
            r7.zzS(r8, r10)     // Catch:{ all -> 0x0d13 }
        L_0x0a10:
            int r8 = r8 + 1
            r6 = r7
            r7 = r8
            r5 = r21
            r8 = 0
            goto L_0x0717
        L_0x0a1a:
            r7 = r6
            int r5 = r3.size()     // Catch:{ all -> 0x0d13 }
            int r6 = r7.zza()     // Catch:{ all -> 0x0d13 }
            if (r5 >= r6) goto L_0x0a2b
            r7.zzr()     // Catch:{ all -> 0x0d13 }
            r7.zzg(r3)     // Catch:{ all -> 0x0d13 }
        L_0x0a2b:
            java.util.Set r2 = r2.entrySet()     // Catch:{ all -> 0x0d13 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0d13 }
        L_0x0a33:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0a4f
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0d13 }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzam r5 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r5)     // Catch:{ all -> 0x0d13 }
            java.lang.Object r3 = r3.getValue()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzas r3 = (com.google.android.gms.measurement.internal.zzas) r3     // Catch:{ all -> 0x0d13 }
            r5.zzE(r3)     // Catch:{ all -> 0x0d13 }
            goto L_0x0a33
        L_0x0a4e:
            r7 = r6
        L_0x0a4f:
            com.google.android.gms.internal.measurement.zzgd r2 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r2 = r2.zzy()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzh r3 = r3.zzj(r2)     // Catch:{ all -> 0x0d13 }
            if (r3 != 0) goto L_0x0a78
            com.google.android.gms.measurement.internal.zzeu r3 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "Bundling raw events w/o app info. appId"
            com.google.android.gms.internal.measurement.zzgd r6 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = r6.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)     // Catch:{ all -> 0x0d13 }
            r3.zzb(r5, r6)     // Catch:{ all -> 0x0d13 }
            goto L_0x0ad4
        L_0x0a78:
            int r5 = r7.zza()     // Catch:{ all -> 0x0d13 }
            if (r5 <= 0) goto L_0x0ad4
            long r5 = r3.zzn()     // Catch:{ all -> 0x0d13 }
            r8 = 0
            int r10 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r10 == 0) goto L_0x0a8c
            r7.zzab(r5)     // Catch:{ all -> 0x0d13 }
            goto L_0x0a8f
        L_0x0a8c:
            r7.zzv()     // Catch:{ all -> 0x0d13 }
        L_0x0a8f:
            long r8 = r3.zzp()     // Catch:{ all -> 0x0d13 }
            r10 = 0
            int r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x0a9a
            goto L_0x0a9b
        L_0x0a9a:
            r5 = r8
        L_0x0a9b:
            int r8 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x0aa3
            r7.zzac(r5)     // Catch:{ all -> 0x0d13 }
            goto L_0x0aa6
        L_0x0aa3:
            r7.zzw()     // Catch:{ all -> 0x0d13 }
        L_0x0aa6:
            r3.zzF()     // Catch:{ all -> 0x0d13 }
            long r5 = r3.zzo()     // Catch:{ all -> 0x0d13 }
            int r5 = (int) r5     // Catch:{ all -> 0x0d13 }
            r7.zzI(r5)     // Catch:{ all -> 0x0d13 }
            long r5 = r7.zzd()     // Catch:{ all -> 0x0d13 }
            r3.zzac(r5)     // Catch:{ all -> 0x0d13 }
            long r5 = r7.zzc()     // Catch:{ all -> 0x0d13 }
            r3.zzaa(r5)     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = r3.zzt()     // Catch:{ all -> 0x0d13 }
            if (r5 == 0) goto L_0x0ac9
            r7.zzW(r5)     // Catch:{ all -> 0x0d13 }
            goto L_0x0acc
        L_0x0ac9:
            r7.zzs()     // Catch:{ all -> 0x0d13 }
        L_0x0acc:
            com.google.android.gms.measurement.internal.zzam r5 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r5)     // Catch:{ all -> 0x0d13 }
            r5.zzD(r3)     // Catch:{ all -> 0x0d13 }
        L_0x0ad4:
            int r3 = r7.zza()     // Catch:{ all -> 0x0d13 }
            if (r3 <= 0) goto L_0x0c58
            com.google.android.gms.measurement.internal.zzge r3 = r1.zzn     // Catch:{ all -> 0x0d13 }
            r3.zzay()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzfv r3 = r1.zzc     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r5 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = r5.zzy()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzff r3 = r3.zze(r5)     // Catch:{ all -> 0x0d13 }
            r5 = -1
            if (r3 == 0) goto L_0x0b01
            boolean r8 = r3.zzu()     // Catch:{ all -> 0x0d13 }
            if (r8 != 0) goto L_0x0af9
            goto L_0x0b01
        L_0x0af9:
            long r8 = r3.zzc()     // Catch:{ all -> 0x0d13 }
            r7.zzK(r8)     // Catch:{ all -> 0x0d13 }
            goto L_0x0b28
        L_0x0b01:
            com.google.android.gms.internal.measurement.zzgd r3 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r3 = r3.zzG()     // Catch:{ all -> 0x0d13 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0d13 }
            if (r3 == 0) goto L_0x0b11
            r7.zzK(r5)     // Catch:{ all -> 0x0d13 }
            goto L_0x0b28
        L_0x0b11:
            com.google.android.gms.measurement.internal.zzeu r3 = r40.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = "Did not find measurement config or missing version info. appId"
            com.google.android.gms.internal.measurement.zzgd r9 = r4.zza     // Catch:{ all -> 0x0d13 }
            java.lang.String r9 = r9.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r9)     // Catch:{ all -> 0x0d13 }
            r3.zzb(r8, r9)     // Catch:{ all -> 0x0d13 }
        L_0x0b28:
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzlb r7 = r7.zzaD()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.internal.measurement.zzgd r7 = (com.google.android.gms.internal.measurement.zzgd) r7     // Catch:{ all -> 0x0d13 }
            r3.zzg()     // Catch:{ all -> 0x0d13 }
            r3.zzW()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = r7.zzy()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)     // Catch:{ all -> 0x0d13 }
            boolean r8 = r7.zzbg()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.common.internal.Preconditions.checkState(r8)     // Catch:{ all -> 0x0d13 }
            r3.zzz()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzge r8 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.common.util.Clock r8 = r8.zzax()     // Catch:{ all -> 0x0d13 }
            long r8 = r8.currentTimeMillis()     // Catch:{ all -> 0x0d13 }
            long r10 = r7.zzk()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzge r12 = r3.zzt     // Catch:{ all -> 0x0d13 }
            r12.zzf()     // Catch:{ all -> 0x0d13 }
            long r12 = com.google.android.gms.measurement.internal.zzag.zzA()     // Catch:{ all -> 0x0d13 }
            long r12 = r8 - r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 < 0) goto L_0x0b7c
            long r10 = r7.zzk()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzge r12 = r3.zzt     // Catch:{ all -> 0x0d13 }
            r12.zzf()     // Catch:{ all -> 0x0d13 }
            long r12 = com.google.android.gms.measurement.internal.zzag.zzA()     // Catch:{ all -> 0x0d13 }
            long r12 = r12 + r8
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0b9f
        L_0x0b7c:
            com.google.android.gms.measurement.internal.zzge r10 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r10 = r10.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r10 = r10.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.String r11 = "Storing bundle outside of the max uploading time span. appId, now, timestamp"
            java.lang.String r12 = r7.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzeu.zzn(r12)     // Catch:{ all -> 0x0d13 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0d13 }
            long r13 = r7.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.Long r9 = java.lang.Long.valueOf(r13)     // Catch:{ all -> 0x0d13 }
            r10.zzd(r11, r12, r8, r9)     // Catch:{ all -> 0x0d13 }
        L_0x0b9f:
            byte[] r8 = r7.zzbx()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzlg r9 = r3.zzf     // Catch:{ IOException -> 0x0c3f }
            com.google.android.gms.measurement.internal.zzli r9 = r9.zzi     // Catch:{ IOException -> 0x0c3f }
            zzal(r9)     // Catch:{ IOException -> 0x0c3f }
            byte[] r8 = r9.zzy(r8)     // Catch:{ IOException -> 0x0c3f }
            com.google.android.gms.measurement.internal.zzge r9 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r9 = r9.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzj()     // Catch:{ all -> 0x0d13 }
            java.lang.String r10 = "Saving bundle, size"
            int r11 = r8.length     // Catch:{ all -> 0x0d13 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0d13 }
            r9.zzb(r10, r11)     // Catch:{ all -> 0x0d13 }
            android.content.ContentValues r9 = new android.content.ContentValues     // Catch:{ all -> 0x0d13 }
            r9.<init>()     // Catch:{ all -> 0x0d13 }
            java.lang.String r10 = "app_id"
            java.lang.String r11 = r7.zzy()     // Catch:{ all -> 0x0d13 }
            r9.put(r10, r11)     // Catch:{ all -> 0x0d13 }
            java.lang.String r10 = "bundle_end_timestamp"
            long r11 = r7.zzk()     // Catch:{ all -> 0x0d13 }
            java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0d13 }
            r9.put(r10, r11)     // Catch:{ all -> 0x0d13 }
            java.lang.String r10 = "data"
            r9.put(r10, r8)     // Catch:{ all -> 0x0d13 }
            java.lang.String r8 = "has_realtime"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r16)     // Catch:{ all -> 0x0d13 }
            r9.put(r8, r10)     // Catch:{ all -> 0x0d13 }
            boolean r8 = r7.zzbm()     // Catch:{ all -> 0x0d13 }
            if (r8 == 0) goto L_0x0bfe
            java.lang.String r8 = "retry_count"
            int r10 = r7.zze()     // Catch:{ all -> 0x0d13 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x0d13 }
            r9.put(r8, r10)     // Catch:{ all -> 0x0d13 }
        L_0x0bfe:
            android.database.sqlite.SQLiteDatabase r8 = r3.zzh()     // Catch:{ SQLiteException -> 0x0c25 }
            java.lang.String r10 = "queue"
            r11 = 0
            long r8 = r8.insert(r10, r11, r9)     // Catch:{ SQLiteException -> 0x0c25 }
            int r5 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x0c58
            com.google.android.gms.measurement.internal.zzge r5 = r3.zzt     // Catch:{ SQLiteException -> 0x0c25 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ SQLiteException -> 0x0c25 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ SQLiteException -> 0x0c25 }
            java.lang.String r6 = "Failed to insert bundle (got -1). appId"
            java.lang.String r8 = r7.zzy()     // Catch:{ SQLiteException -> 0x0c25 }
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r8)     // Catch:{ SQLiteException -> 0x0c25 }
            r5.zzb(r6, r8)     // Catch:{ SQLiteException -> 0x0c25 }
            goto L_0x0c58
        L_0x0c25:
            r0 = move-exception
            r5 = r0
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Error storing bundle. appId"
            java.lang.String r7 = r7.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r7)     // Catch:{ all -> 0x0d13 }
            r3.zzc(r6, r7, r5)     // Catch:{ all -> 0x0d13 }
            goto L_0x0c58
        L_0x0c3f:
            r0 = move-exception
            r5 = r0
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Data loss. Failed to serialize bundle. appId"
            java.lang.String r7 = r7.zzy()     // Catch:{ all -> 0x0d13 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r7)     // Catch:{ all -> 0x0d13 }
            r3.zzc(r6, r7, r5)     // Catch:{ all -> 0x0d13 }
        L_0x0c58:
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            java.util.List r4 = r4.zzb     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)     // Catch:{ all -> 0x0d13 }
            r3.zzg()     // Catch:{ all -> 0x0d13 }
            r3.zzW()     // Catch:{ all -> 0x0d13 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "rowid in ("
            r5.<init>(r6)     // Catch:{ all -> 0x0d13 }
            r6 = 0
        L_0x0c70:
            int r7 = r4.size()     // Catch:{ all -> 0x0d13 }
            if (r6 >= r7) goto L_0x0c8d
            if (r6 == 0) goto L_0x0c7d
            java.lang.String r7 = ","
            r5.append(r7)     // Catch:{ all -> 0x0d13 }
        L_0x0c7d:
            java.lang.Object r7 = r4.get(r6)     // Catch:{ all -> 0x0d13 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x0d13 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0d13 }
            r5.append(r7)     // Catch:{ all -> 0x0d13 }
            int r6 = r6 + 1
            goto L_0x0c70
        L_0x0c8d:
            java.lang.String r6 = ")"
            r5.append(r6)     // Catch:{ all -> 0x0d13 }
            android.database.sqlite.SQLiteDatabase r6 = r3.zzh()     // Catch:{ all -> 0x0d13 }
            java.lang.String r7 = "raw_events"
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0d13 }
            r8 = 0
            int r5 = r6.delete(r7, r5, r8)     // Catch:{ all -> 0x0d13 }
            int r6 = r4.size()     // Catch:{ all -> 0x0d13 }
            if (r5 == r6) goto L_0x0cc2
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r6 = "Deleted fewer rows from raw events table than expected"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0d13 }
            int r4 = r4.size()     // Catch:{ all -> 0x0d13 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0d13 }
            r3.zzc(r6, r5, r4)     // Catch:{ all -> 0x0d13 }
        L_0x0cc2:
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r3)     // Catch:{ all -> 0x0d13 }
            android.database.sqlite.SQLiteDatabase r4 = r3.zzh()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x0cda }
            r7 = 0
            r6[r7] = r2     // Catch:{ SQLiteException -> 0x0cda }
            r7 = 1
            r6[r7] = r2     // Catch:{ SQLiteException -> 0x0cda }
            r4.execSQL(r5, r6)     // Catch:{ SQLiteException -> 0x0cda }
            goto L_0x0cef
        L_0x0cda:
            r0 = move-exception
            r4 = r0
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0d13 }
            java.lang.String r5 = "Failed to remove unused event metadata. appId"
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzeu.zzn(r2)     // Catch:{ all -> 0x0d13 }
            r3.zzc(r5, r2, r4)     // Catch:{ all -> 0x0d13 }
        L_0x0cef:
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            r2.zzC()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            zzal(r2)
            r2.zzx()
            r2 = 1
            return r2
        L_0x0d01:
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0d13 }
            zzal(r2)     // Catch:{ all -> 0x0d13 }
            r2.zzC()     // Catch:{ all -> 0x0d13 }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            zzal(r2)
            r2.zzx()
            r2 = 0
            return r2
        L_0x0d13:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze
            zzal(r3)
            r3.zzx()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzlg.zzah(java.lang.String, long):boolean");
    }

    private final boolean zzai() {
        zzaB().zzg();
        zzB();
        zzam zzam = this.zze;
        zzal(zzam);
        if (zzam.zzF()) {
            return true;
        }
        zzam zzam2 = this.zze;
        zzal(zzam2);
        return !TextUtils.isEmpty(zzam2.zzr());
    }

    private final boolean zzaj(zzfs zzfs, zzfs zzfs2) {
        Preconditions.checkArgument("_e".equals(zzfs.zzo()));
        zzal(this.zzi);
        zzfx zzB2 = zzli.zzB((zzft) zzfs.zzaD(), "_sc");
        String str = null;
        String zzh2 = zzB2 == null ? null : zzB2.zzh();
        zzal(this.zzi);
        zzfx zzB3 = zzli.zzB((zzft) zzfs2.zzaD(), "_pc");
        if (zzB3 != null) {
            str = zzB3.zzh();
        }
        if (str == null || !str.equals(zzh2)) {
            return false;
        }
        Preconditions.checkArgument("_e".equals(zzfs.zzo()));
        zzal(this.zzi);
        zzfx zzB4 = zzli.zzB((zzft) zzfs.zzaD(), "_et");
        if (zzB4 == null || !zzB4.zzw() || zzB4.zzd() <= 0) {
            return true;
        }
        long zzd2 = zzB4.zzd();
        zzal(this.zzi);
        zzfx zzB5 = zzli.zzB((zzft) zzfs2.zzaD(), "_et");
        if (zzB5 != null && zzB5.zzd() > 0) {
            zzd2 += zzB5.zzd();
        }
        zzal(this.zzi);
        zzli.zzz(zzfs2, "_et", Long.valueOf(zzd2));
        zzal(this.zzi);
        zzli.zzz(zzfs, "_fr", 1L);
        return true;
    }

    private static final boolean zzak(zzq zzq2) {
        return !TextUtils.isEmpty(zzq2.zzb) || !TextUtils.isEmpty(zzq2.zzq);
    }

    private static final zzkt zzal(zzkt zzkt) {
        if (zzkt == null) {
            throw new IllegalStateException("Upload Component not created");
        } else if (zzkt.zzY()) {
            return zzkt;
        } else {
            throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(String.valueOf(zzkt.getClass()))));
        }
    }

    public static zzlg zzt(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (zzlg.class) {
                if (zzb == null) {
                    zzb = new zzlg((zzlh) Preconditions.checkNotNull(new zzlh(context)), (zzge) null);
                }
            }
        }
        return zzb;
    }

    static /* bridge */ /* synthetic */ void zzy(zzlg zzlg, zzlh zzlh) {
        zzlg.zzaB().zzg();
        zzlg.zzm = new zzfm(zzlg);
        zzam zzam = new zzam(zzlg);
        zzam.zzX();
        zzlg.zze = zzam;
        zzlg.zzg().zzq((zzaf) Preconditions.checkNotNull(zzlg.zzc));
        zzka zzka = new zzka(zzlg);
        zzka.zzX();
        zzlg.zzk = zzka;
        zzaa zzaa = new zzaa(zzlg);
        zzaa.zzX();
        zzlg.zzh = zzaa;
        zzio zzio = new zzio(zzlg);
        zzio.zzX();
        zzlg.zzj = zzio;
        zzkr zzkr = new zzkr(zzlg);
        zzkr.zzX();
        zzlg.zzg = zzkr;
        zzlg.zzf = new zzfc(zzlg);
        if (zzlg.zzr != zzlg.zzs) {
            zzlg.zzaA().zzd().zzc("Not all upload components initialized", Integer.valueOf(zzlg.zzr), Integer.valueOf(zzlg.zzs));
        }
        zzlg.zzo = true;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final void zzA() {
        zzaB().zzg();
        zzB();
        if (!this.zzp) {
            this.zzp = true;
            if (zzZ()) {
                FileChannel fileChannel = this.zzx;
                zzaB().zzg();
                int i = 0;
                if (fileChannel == null || !fileChannel.isOpen()) {
                    zzaA().zzd().zza("Bad channel to read from");
                } else {
                    ByteBuffer allocate = ByteBuffer.allocate(4);
                    try {
                        fileChannel.position(0);
                        int read = fileChannel.read(allocate);
                        if (read == 4) {
                            allocate.flip();
                            i = allocate.getInt();
                        } else if (read != -1) {
                            zzaA().zzk().zzb("Unexpected data length. Bytes read", Integer.valueOf(read));
                        }
                    } catch (IOException e) {
                        zzaA().zzd().zzb("Failed to read from channel", e);
                    }
                }
                int zzi2 = this.zzn.zzh().zzi();
                zzaB().zzg();
                if (i > zzi2) {
                    zzaA().zzd().zzc("Panic: can't downgrade version. Previous, current version", Integer.valueOf(i), Integer.valueOf(zzi2));
                } else if (i < zzi2) {
                    FileChannel fileChannel2 = this.zzx;
                    zzaB().zzg();
                    if (fileChannel2 == null || !fileChannel2.isOpen()) {
                        zzaA().zzd().zza("Bad channel to read from");
                    } else {
                        ByteBuffer allocate2 = ByteBuffer.allocate(4);
                        allocate2.putInt(zzi2);
                        allocate2.flip();
                        try {
                            fileChannel2.truncate(0);
                            fileChannel2.write(allocate2);
                            fileChannel2.force(true);
                            if (fileChannel2.size() != 4) {
                                zzaA().zzd().zzb("Error writing to channel. Bytes written", Long.valueOf(fileChannel2.size()));
                            }
                            zzaA().zzj().zzc("Storage version upgraded. Previous, current version", Integer.valueOf(i), Integer.valueOf(zzi2));
                            return;
                        } catch (IOException e2) {
                            zzaA().zzd().zzb("Failed to write to channel", e2);
                        }
                    }
                    zzaA().zzd().zzc("Storage version upgrade failed. Previous, current version", Integer.valueOf(i), Integer.valueOf(zzi2));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzB() {
        if (!this.zzo) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzC(String str, zzgc zzgc) {
        int zza2;
        int indexOf;
        zzfv zzfv = this.zzc;
        zzal(zzfv);
        Set zzk2 = zzfv.zzk(str);
        if (zzk2 != null) {
            zzgc.zzi(zzk2);
        }
        zzfv zzfv2 = this.zzc;
        zzal(zzfv2);
        if (zzfv2.zzv(str)) {
            zzgc.zzp();
        }
        zzfv zzfv3 = this.zzc;
        zzal(zzfv3);
        if (zzfv3.zzy(str)) {
            if (zzg().zzs(str, zzeh.zzar)) {
                String zzas = zzgc.zzas();
                if (!TextUtils.isEmpty(zzas) && (indexOf = zzas.indexOf(".")) != -1) {
                    zzgc.zzY(zzas.substring(0, indexOf));
                }
            } else {
                zzgc.zzu();
            }
        }
        zzfv zzfv4 = this.zzc;
        zzal(zzfv4);
        if (zzfv4.zzz(str) && (zza2 = zzli.zza(zzgc, "_id")) != -1) {
            zzgc.zzB(zza2);
        }
        zzfv zzfv5 = this.zzc;
        zzal(zzfv5);
        if (zzfv5.zzx(str)) {
            zzgc.zzq();
        }
        zzfv zzfv6 = this.zzc;
        zzal(zzfv6);
        if (zzfv6.zzu(str)) {
            zzgc.zzn();
            zzlf zzlf = (zzlf) this.zzC.get(str);
            if (zzlf == null || zzlf.zzb + zzg().zzi(str, zzeh.zzT) < zzax().elapsedRealtime()) {
                zzlf = new zzlf(this);
                this.zzC.put(str, zzlf);
            }
            zzgc.zzR(zzlf.zza);
        }
        zzfv zzfv7 = this.zzc;
        zzal(zzfv7);
        if (zzfv7.zzw(str)) {
            zzgc.zzy();
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzD(zzh zzh2) {
        ArrayMap arrayMap;
        zzaB().zzg();
        if (!TextUtils.isEmpty(zzh2.zzz()) || !TextUtils.isEmpty(zzh2.zzs())) {
            zzkv zzkv = this.zzl;
            Uri.Builder builder = new Uri.Builder();
            String zzz2 = zzh2.zzz();
            if (TextUtils.isEmpty(zzz2)) {
                zzz2 = zzh2.zzs();
            }
            ArrayMap arrayMap2 = null;
            Uri.Builder appendQueryParameter = builder.scheme((String) zzeh.zze.zza((Object) null)).encodedAuthority((String) zzeh.zzf.zza((Object) null)).path("config/app/".concat(String.valueOf(zzz2))).appendQueryParameter("platform", "android");
            zzkv.zzt.zzf().zzh();
            appendQueryParameter.appendQueryParameter("gmp_version", String.valueOf(77000)).appendQueryParameter("runtime_version", "0");
            String uri = builder.build().toString();
            try {
                String str = (String) Preconditions.checkNotNull(zzh2.zzu());
                URL url = new URL(uri);
                zzaA().zzj().zzb("Fetching remote configuration", str);
                zzfv zzfv = this.zzc;
                zzal(zzfv);
                zzff zze2 = zzfv.zze(str);
                zzfv zzfv2 = this.zzc;
                zzal(zzfv2);
                String zzh3 = zzfv2.zzh(str);
                if (zze2 != null) {
                    if (!TextUtils.isEmpty(zzh3)) {
                        ArrayMap arrayMap3 = new ArrayMap();
                        arrayMap3.put(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE, zzh3);
                        arrayMap2 = arrayMap3;
                    }
                    zzfv zzfv3 = this.zzc;
                    zzal(zzfv3);
                    String zzf2 = zzfv3.zzf(str);
                    if (!TextUtils.isEmpty(zzf2)) {
                        if (arrayMap2 == null) {
                            arrayMap2 = new ArrayMap();
                        }
                        arrayMap2.put(HttpHeaders.HEAD_KEY_IF_NONE_MATCH, zzf2);
                        arrayMap = arrayMap2;
                    } else {
                        arrayMap = arrayMap2;
                    }
                } else {
                    arrayMap = null;
                }
                this.zzt = true;
                zzfa zzfa = this.zzd;
                zzal(zzfa);
                zzky zzky = new zzky(this);
                zzfa.zzg();
                zzfa.zzW();
                Preconditions.checkNotNull(url);
                Preconditions.checkNotNull(zzky);
                zzfa.zzt.zzaB().zzo(new zzez(zzfa, str, url, (byte[]) null, arrayMap, zzky));
            } catch (MalformedURLException e) {
                zzaA().zzd().zzc("Failed to parse config URL. Not fetching. appId", zzeu.zzn(zzh2.zzu()), uri);
            }
        } else {
            zzI((String) Preconditions.checkNotNull(zzh2.zzu()), KitWrapItem.TYPE_VERSION, (Throwable) null, (byte[]) null, (Map) null);
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzE(zzaw zzaw, zzq zzq2) {
        zzaw zzaw2;
        List<zzac> list;
        List<zzac> list2;
        List<zzac> list3;
        String str;
        zzq zzq3 = zzq2;
        Preconditions.checkNotNull(zzq2);
        Preconditions.checkNotEmpty(zzq3.zza);
        zzaB().zzg();
        zzB();
        String str2 = zzq3.zza;
        long j = zzaw.zzd;
        zzev zzb2 = zzev.zzb(zzaw);
        zzaB().zzg();
        zziq zziq = null;
        if (!(this.zzD == null || (str = this.zzE) == null || !str.equals(str2))) {
            zziq = this.zzD;
        }
        zzlo.zzK(zziq, zzb2.zzd, false);
        zzaw zza2 = zzb2.zza();
        zzal(this.zzi);
        if (zzli.zzA(zza2, zzq3)) {
            if (!zzq3.zzh) {
                zzd(zzq3);
                return;
            }
            List list4 = zzq3.zzt;
            if (list4 == null) {
                zzaw2 = zza2;
            } else if (list4.contains(zza2.zza)) {
                Bundle zzc2 = zza2.zzb.zzc();
                zzc2.putLong("ga_safelisted", 1);
                zzaw2 = new zzaw(zza2.zza, new zzau(zzc2), zza2.zzc, zza2.zzd);
            } else {
                zzaA().zzc().zzd("Dropping non-safelisted event. appId, event name, origin", str2, zza2.zza, zza2.zzc);
                return;
            }
            zzam zzam = this.zze;
            zzal(zzam);
            zzam.zzw();
            try {
                zzam zzam2 = this.zze;
                zzal(zzam2);
                Preconditions.checkNotEmpty(str2);
                zzam2.zzg();
                zzam2.zzW();
                int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
                if (i < 0) {
                    zzam2.zzt.zzaA().zzk().zzc("Invalid time querying timed out conditional properties", zzeu.zzn(str2), Long.valueOf(j));
                    list = Collections.emptyList();
                } else {
                    list = zzam2.zzt("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str2, String.valueOf(j)});
                }
                for (zzac zzac : list) {
                    if (zzac != null) {
                        zzaA().zzj().zzd("User property timed out", zzac.zza, this.zzn.zzj().zzf(zzac.zzc.zzb), zzac.zzc.zza());
                        zzaw zzaw3 = zzac.zzg;
                        if (zzaw3 != null) {
                            zzY(new zzaw(zzaw3, j), zzq3);
                        }
                        zzam zzam3 = this.zze;
                        zzal(zzam3);
                        zzam3.zza(str2, zzac.zzc.zzb);
                    }
                }
                zzam zzam4 = this.zze;
                zzal(zzam4);
                Preconditions.checkNotEmpty(str2);
                zzam4.zzg();
                zzam4.zzW();
                if (i < 0) {
                    zzam4.zzt.zzaA().zzk().zzc("Invalid time querying expired conditional properties", zzeu.zzn(str2), Long.valueOf(j));
                    list2 = Collections.emptyList();
                } else {
                    list2 = zzam4.zzt("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str2, String.valueOf(j)});
                }
                ArrayList<zzaw> arrayList = new ArrayList<>(list2.size());
                for (zzac zzac2 : list2) {
                    if (zzac2 != null) {
                        zzaA().zzj().zzd("User property expired", zzac2.zza, this.zzn.zzj().zzf(zzac2.zzc.zzb), zzac2.zzc.zza());
                        zzam zzam5 = this.zze;
                        zzal(zzam5);
                        zzam5.zzA(str2, zzac2.zzc.zzb);
                        zzaw zzaw4 = zzac2.zzk;
                        if (zzaw4 != null) {
                            arrayList.add(zzaw4);
                        }
                        zzam zzam6 = this.zze;
                        zzal(zzam6);
                        zzam6.zza(str2, zzac2.zzc.zzb);
                    }
                }
                for (zzaw zzaw5 : arrayList) {
                    zzY(new zzaw(zzaw5, j), zzq3);
                }
                zzam zzam7 = this.zze;
                zzal(zzam7);
                String str3 = zzaw2.zza;
                Preconditions.checkNotEmpty(str2);
                Preconditions.checkNotEmpty(str3);
                zzam7.zzg();
                zzam7.zzW();
                if (i < 0) {
                    zzam7.zzt.zzaA().zzk().zzd("Invalid time querying triggered conditional properties", zzeu.zzn(str2), zzam7.zzt.zzj().zzd(str3), Long.valueOf(j));
                    list3 = Collections.emptyList();
                } else {
                    list3 = zzam7.zzt("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str2, str3, String.valueOf(j)});
                }
                ArrayList<zzaw> arrayList2 = new ArrayList<>(list3.size());
                for (zzac zzac3 : list3) {
                    if (zzac3 != null) {
                        zzlj zzlj = zzac3.zzc;
                        zzll zzll = new zzll((String) Preconditions.checkNotNull(zzac3.zza), zzac3.zzb, zzlj.zzb, j, Preconditions.checkNotNull(zzlj.zza()));
                        zzam zzam8 = this.zze;
                        zzal(zzam8);
                        if (zzam8.zzL(zzll)) {
                            zzaA().zzj().zzd("User property triggered", zzac3.zza, this.zzn.zzj().zzf(zzll.zzc), zzll.zze);
                        } else {
                            zzaA().zzd().zzd("Too many active user properties, ignoring", zzeu.zzn(zzac3.zza), this.zzn.zzj().zzf(zzll.zzc), zzll.zze);
                        }
                        zzaw zzaw6 = zzac3.zzi;
                        if (zzaw6 != null) {
                            arrayList2.add(zzaw6);
                        }
                        zzac3.zzc = new zzlj(zzll);
                        zzac3.zze = true;
                        zzam zzam9 = this.zze;
                        zzal(zzam9);
                        zzam9.zzK(zzac3);
                    }
                }
                zzY(zzaw2, zzq3);
                for (zzaw zzaw7 : arrayList2) {
                    zzY(new zzaw(zzaw7, j), zzq3);
                }
                zzam zzam10 = this.zze;
                zzal(zzam10);
                zzam10.zzC();
            } finally {
                zzam zzam11 = this.zze;
                zzal(zzam11);
                zzam11.zzx();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzF(zzaw zzaw, String str) {
        zzaw zzaw2 = zzaw;
        String str2 = str;
        zzam zzam = this.zze;
        zzal(zzam);
        zzh zzj2 = zzam.zzj(str2);
        if (zzj2 == null || TextUtils.isEmpty(zzj2.zzx())) {
            zzaA().zzc().zzb("No app data available; dropping event", str2);
            return;
        }
        Boolean zzad = zzad(zzj2);
        if (zzad == null) {
            if (!"_ui".equals(zzaw2.zza)) {
                zzaA().zzk().zzb("Could not find package. appId", zzeu.zzn(str));
            }
        } else if (!zzad.booleanValue()) {
            zzaA().zzd().zzb("App version does not match; dropping event. appId", zzeu.zzn(str));
            return;
        }
        String zzz2 = zzj2.zzz();
        String zzx2 = zzj2.zzx();
        long zzb2 = zzj2.zzb();
        String zzw2 = zzj2.zzw();
        long zzm2 = zzj2.zzm();
        long zzj3 = zzj2.zzj();
        zzq zzq2 = r2;
        boolean zzal = zzj2.zzal();
        zzh zzh2 = zzj2;
        String zzy2 = zzh2.zzy();
        zzh2.zza();
        zzq zzq3 = new zzq(str, zzz2, zzx2, zzb2, zzw2, zzm2, zzj3, (String) null, zzal, false, zzy2, 0, 0, 0, zzh2.zzak(), false, zzh2.zzs(), zzh2.zzr(), zzh2.zzk(), zzh2.zzD(), (String) null, zzh(str2).zzh(), "", (String) null, zzh2.zzan(), zzh2.zzq());
        zzG(zzaw2, zzq2);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzG(zzaw zzaw, zzq zzq2) {
        Preconditions.checkNotEmpty(zzq2.zza);
        zzev zzb2 = zzev.zzb(zzaw);
        zzlo zzv2 = zzv();
        Bundle bundle = zzb2.zzd;
        zzam zzam = this.zze;
        zzal(zzam);
        zzv2.zzL(bundle, zzam.zzi(zzq2.zza));
        zzv().zzN(zzb2, zzg().zzd(zzq2.zza));
        zzaw zza2 = zzb2.zza();
        if (Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zza2.zza) && "referrer API v2".equals(zza2.zzb.zzg("_cis"))) {
            String zzg2 = zza2.zzb.zzg("gclid");
            if (!TextUtils.isEmpty(zzg2)) {
                zzW(new zzlj("_lgclid", zza2.zzd, zzg2, "auto"), zzq2);
            }
        }
        zzE(zza2, zzq2);
    }

    /* access modifiers changed from: package-private */
    public final void zzH() {
        this.zzs++;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0049 A[Catch:{ all -> 0x0176, all -> 0x0180 }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005c A[Catch:{ all -> 0x0176, all -> 0x0180 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0129 A[Catch:{ all -> 0x0176, all -> 0x0180 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0137 A[Catch:{ all -> 0x0176, all -> 0x0180 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0159 A[Catch:{ all -> 0x0176, all -> 0x0180 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x015d A[Catch:{ all -> 0x0176, all -> 0x0180 }] */
    @androidx.annotation.WorkerThread
    @com.google.android.gms.common.util.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzI(java.lang.String r8, int r9, java.lang.Throwable r10, byte[] r11, java.util.Map r12) {
        /*
            r7 = this;
            com.google.android.gms.measurement.internal.zzgb r0 = r7.zzaB()
            r0.zzg()
            r7.zzB()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)
            r0 = 0
            if (r11 != 0) goto L_0x0012
            byte[] r11 = new byte[r0]     // Catch:{ all -> 0x0180 }
        L_0x0012:
            com.google.android.gms.measurement.internal.zzeu r1 = r7.zzaA()     // Catch:{ all -> 0x0180 }
            com.google.android.gms.measurement.internal.zzes r1 = r1.zzj()     // Catch:{ all -> 0x0180 }
            java.lang.String r2 = "onConfigFetched. Response size"
            int r3 = r11.length     // Catch:{ all -> 0x0180 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0180 }
            r1.zzb(r2, r3)     // Catch:{ all -> 0x0180 }
            com.google.android.gms.measurement.internal.zzam r1 = r7.zze     // Catch:{ all -> 0x0180 }
            zzal(r1)     // Catch:{ all -> 0x0180 }
            r1.zzw()     // Catch:{ all -> 0x0180 }
            com.google.android.gms.measurement.internal.zzam r1 = r7.zze     // Catch:{ all -> 0x0176 }
            zzal(r1)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzh r1 = r1.zzj(r8)     // Catch:{ all -> 0x0176 }
            r2 = 200(0xc8, float:2.8E-43)
            r4 = 304(0x130, float:4.26E-43)
            if (r9 == r2) goto L_0x0042
            r2 = 204(0xcc, float:2.86E-43)
            if (r9 == r2) goto L_0x0042
            if (r9 != r4) goto L_0x0046
            r9 = r4
        L_0x0042:
            if (r10 != 0) goto L_0x0046
            r2 = 1
            goto L_0x0047
        L_0x0046:
            r2 = r0
        L_0x0047:
            if (r1 != 0) goto L_0x005c
            com.google.android.gms.measurement.internal.zzeu r9 = r7.zzaA()     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzk()     // Catch:{ all -> 0x0176 }
            java.lang.String r10 = "App does not exist in onConfigFetched. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r8)     // Catch:{ all -> 0x0176 }
            r9.zzb(r10, r8)     // Catch:{ all -> 0x0176 }
            goto L_0x0160
        L_0x005c:
            r5 = 404(0x194, float:5.66E-43)
            if (r2 != 0) goto L_0x00ba
            if (r9 != r5) goto L_0x0063
            goto L_0x00ba
        L_0x0063:
            com.google.android.gms.common.util.Clock r11 = r7.zzax()     // Catch:{ all -> 0x0176 }
            long r11 = r11.currentTimeMillis()     // Catch:{ all -> 0x0176 }
            r1.zzV(r11)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzam r11 = r7.zze     // Catch:{ all -> 0x0176 }
            zzal(r11)     // Catch:{ all -> 0x0176 }
            r11.zzD(r1)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzeu r11 = r7.zzaA()     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzes r11 = r11.zzj()     // Catch:{ all -> 0x0176 }
            java.lang.String r12 = "Fetching config failed. code, error"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0176 }
            r11.zzc(r12, r1, r10)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzfv r10 = r7.zzc     // Catch:{ all -> 0x0176 }
            zzal(r10)     // Catch:{ all -> 0x0176 }
            r10.zzl(r8)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzka r8 = r7.zzk     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzff r8 = r8.zzd     // Catch:{ all -> 0x0176 }
            com.google.android.gms.common.util.Clock r10 = r7.zzax()     // Catch:{ all -> 0x0176 }
            long r10 = r10.currentTimeMillis()     // Catch:{ all -> 0x0176 }
            r8.zzb(r10)     // Catch:{ all -> 0x0176 }
            r8 = 503(0x1f7, float:7.05E-43)
            if (r9 == r8) goto L_0x00a6
            r8 = 429(0x1ad, float:6.01E-43)
            if (r9 != r8) goto L_0x00b5
        L_0x00a6:
            com.google.android.gms.measurement.internal.zzka r8 = r7.zzk     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzff r8 = r8.zzb     // Catch:{ all -> 0x0176 }
            com.google.android.gms.common.util.Clock r9 = r7.zzax()     // Catch:{ all -> 0x0176 }
            long r9 = r9.currentTimeMillis()     // Catch:{ all -> 0x0176 }
            r8.zzb(r9)     // Catch:{ all -> 0x0176 }
        L_0x00b5:
            r7.zzag()     // Catch:{ all -> 0x0176 }
            goto L_0x0160
        L_0x00ba:
            r10 = 0
            if (r12 == 0) goto L_0x00c6
            java.lang.String r2 = "Last-Modified"
            java.lang.Object r2 = r12.get(r2)     // Catch:{ all -> 0x0176 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ all -> 0x0176 }
            goto L_0x00c7
        L_0x00c6:
            r2 = r10
        L_0x00c7:
            if (r2 == 0) goto L_0x00d6
            boolean r6 = r2.isEmpty()     // Catch:{ all -> 0x0176 }
            if (r6 != 0) goto L_0x00d6
            java.lang.Object r2 = r2.get(r0)     // Catch:{ all -> 0x0176 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0176 }
            goto L_0x00d7
        L_0x00d6:
            r2 = r10
        L_0x00d7:
            if (r12 == 0) goto L_0x00e2
            java.lang.String r6 = "ETag"
            java.lang.Object r12 = r12.get(r6)     // Catch:{ all -> 0x0176 }
            java.util.List r12 = (java.util.List) r12     // Catch:{ all -> 0x0176 }
            goto L_0x00e3
        L_0x00e2:
            r12 = r10
        L_0x00e3:
            if (r12 == 0) goto L_0x00f2
            boolean r6 = r12.isEmpty()     // Catch:{ all -> 0x0176 }
            if (r6 != 0) goto L_0x00f2
            java.lang.Object r12 = r12.get(r0)     // Catch:{ all -> 0x0176 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ all -> 0x0176 }
            goto L_0x00f3
        L_0x00f2:
            r12 = r10
        L_0x00f3:
            if (r9 == r5) goto L_0x0101
            if (r9 != r4) goto L_0x00f8
            goto L_0x0101
        L_0x00f8:
            com.google.android.gms.measurement.internal.zzfv r10 = r7.zzc     // Catch:{ all -> 0x0176 }
            zzal(r10)     // Catch:{ all -> 0x0176 }
            r10.zzt(r8, r11, r2, r12)     // Catch:{ all -> 0x0176 }
            goto L_0x0114
        L_0x0101:
            com.google.android.gms.measurement.internal.zzfv r11 = r7.zzc     // Catch:{ all -> 0x0176 }
            zzal(r11)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.internal.measurement.zzff r11 = r11.zze(r8)     // Catch:{ all -> 0x0176 }
            if (r11 != 0) goto L_0x0114
            com.google.android.gms.measurement.internal.zzfv r11 = r7.zzc     // Catch:{ all -> 0x0176 }
            zzal(r11)     // Catch:{ all -> 0x0176 }
            r11.zzt(r8, r10, r10, r10)     // Catch:{ all -> 0x0176 }
        L_0x0114:
            com.google.android.gms.common.util.Clock r10 = r7.zzax()     // Catch:{ all -> 0x0176 }
            long r10 = r10.currentTimeMillis()     // Catch:{ all -> 0x0176 }
            r1.zzM(r10)     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzam r10 = r7.zze     // Catch:{ all -> 0x0176 }
            zzal(r10)     // Catch:{ all -> 0x0176 }
            r10.zzD(r1)     // Catch:{ all -> 0x0176 }
            if (r9 != r5) goto L_0x0137
            com.google.android.gms.measurement.internal.zzeu r9 = r7.zzaA()     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzl()     // Catch:{ all -> 0x0176 }
            java.lang.String r10 = "Config not found. Using empty config. appId"
            r9.zzb(r10, r8)     // Catch:{ all -> 0x0176 }
            goto L_0x0148
        L_0x0137:
            com.google.android.gms.measurement.internal.zzeu r8 = r7.zzaA()     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzj()     // Catch:{ all -> 0x0176 }
            java.lang.String r10 = "Successfully fetched config. Got network response. code, size"
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0176 }
            r8.zzc(r10, r9, r3)     // Catch:{ all -> 0x0176 }
        L_0x0148:
            com.google.android.gms.measurement.internal.zzfa r8 = r7.zzd     // Catch:{ all -> 0x0176 }
            zzal(r8)     // Catch:{ all -> 0x0176 }
            boolean r8 = r8.zza()     // Catch:{ all -> 0x0176 }
            if (r8 == 0) goto L_0x015d
            boolean r8 = r7.zzai()     // Catch:{ all -> 0x0176 }
            if (r8 == 0) goto L_0x015d
            r7.zzX()     // Catch:{ all -> 0x0176 }
            goto L_0x0160
        L_0x015d:
            r7.zzag()     // Catch:{ all -> 0x0176 }
        L_0x0160:
            com.google.android.gms.measurement.internal.zzam r8 = r7.zze     // Catch:{ all -> 0x0176 }
            zzal(r8)     // Catch:{ all -> 0x0176 }
            r8.zzC()     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzam r8 = r7.zze     // Catch:{ all -> 0x0180 }
            zzal(r8)     // Catch:{ all -> 0x0180 }
            r8.zzx()     // Catch:{ all -> 0x0180 }
            r7.zzt = r0
            r7.zzae()
            return
        L_0x0176:
            r8 = move-exception
            com.google.android.gms.measurement.internal.zzam r9 = r7.zze     // Catch:{ all -> 0x0180 }
            zzal(r9)     // Catch:{ all -> 0x0180 }
            r9.zzx()     // Catch:{ all -> 0x0180 }
            throw r8     // Catch:{ all -> 0x0180 }
        L_0x0180:
            r8 = move-exception
            r7.zzt = r0
            r7.zzae()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzlg.zzI(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    /* access modifiers changed from: package-private */
    public final void zzJ(boolean z) {
        zzag();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final void zzK(int i, Throwable th, byte[] bArr, String str) {
        zzam zzam;
        zzaB().zzg();
        zzB();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzu = false;
                zzae();
                throw th2;
            }
        }
        List<Long> list = (List) Preconditions.checkNotNull(this.zzy);
        this.zzy = null;
        if (i != 200) {
            if (i == 204) {
                i = 204;
            }
            zzaA().zzj().zzc("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzk.zzd.zzb(zzax().currentTimeMillis());
            if (i == 503 || i == 429) {
                this.zzk.zzb.zzb(zzax().currentTimeMillis());
            }
            zzam zzam2 = this.zze;
            zzal(zzam2);
            zzam2.zzy(list);
            zzag();
            this.zzu = false;
            zzae();
        }
        if (th == null) {
            try {
                this.zzk.zzc.zzb(zzax().currentTimeMillis());
                this.zzk.zzd.zzb(0);
                zzag();
                zzaA().zzj().zzc("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzam zzam3 = this.zze;
                zzal(zzam3);
                zzam3.zzw();
                try {
                    for (Long l : list) {
                        try {
                            zzam = this.zze;
                            zzal(zzam);
                            long longValue = l.longValue();
                            zzam.zzg();
                            zzam.zzW();
                            if (zzam.zzh().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (SQLiteException e) {
                            zzam.zzt.zzaA().zzd().zzb("Failed to delete a bundle in a queue table", e);
                            throw e;
                        } catch (SQLiteException e2) {
                            List list2 = this.zzz;
                            if (list2 == null || !list2.contains(l)) {
                                throw e2;
                            }
                        }
                    }
                    zzam zzam4 = this.zze;
                    zzal(zzam4);
                    zzam4.zzC();
                    zzam zzam5 = this.zze;
                    zzal(zzam5);
                    zzam5.zzx();
                    this.zzz = null;
                    zzfa zzfa = this.zzd;
                    zzal(zzfa);
                    if (!zzfa.zza() || !zzai()) {
                        this.zzA = -1;
                        zzag();
                    } else {
                        zzX();
                    }
                    this.zza = 0;
                } catch (Throwable th3) {
                    zzam zzam6 = this.zze;
                    zzal(zzam6);
                    zzam6.zzx();
                    throw th3;
                }
            } catch (SQLiteException e3) {
                zzaA().zzd().zzb("Database error while trying to delete uploaded bundles", e3);
                this.zza = zzax().elapsedRealtime();
                zzaA().zzj().zzb("Disable upload, time", Long.valueOf(this.zza));
            }
            this.zzu = false;
            zzae();
        }
        zzaA().zzj().zzc("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
        this.zzk.zzd.zzb(zzax().currentTimeMillis());
        this.zzk.zzb.zzb(zzax().currentTimeMillis());
        zzam zzam22 = this.zze;
        zzal(zzam22);
        zzam22.zzy(list);
        zzag();
        this.zzu = false;
        zzae();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x03e6 A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0414 A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0427 A[SYNTHETIC, Splitter:B:138:0x0427] */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x04cd A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x04ee A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x0555 A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0112 A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01fc A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0202 A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x025b A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x026a A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x027a A[Catch:{ RuntimeException -> 0x036e, all -> 0x0585 }] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzL(com.google.android.gms.measurement.internal.zzq r24) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            java.lang.String r3 = "_sysu"
            java.lang.String r4 = "_sys"
            java.lang.String r5 = "com.android.vending"
            java.lang.String r6 = "_pfo"
            java.lang.String r0 = "_npa"
            java.lang.String r7 = "_uwa"
            java.lang.String r8 = "app_id=?"
            com.google.android.gms.measurement.internal.zzgb r9 = r23.zzaB()
            r9.zzg()
            r23.zzB()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r24)
            java.lang.String r9 = r2.zza
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9)
            boolean r9 = zzak(r24)
            if (r9 == 0) goto L_0x058f
            com.google.android.gms.measurement.internal.zzam r9 = r1.zze
            zzal(r9)
            java.lang.String r10 = r2.zza
            com.google.android.gms.measurement.internal.zzh r9 = r9.zzj(r10)
            r10 = 0
            if (r9 == 0) goto L_0x0060
            java.lang.String r12 = r9.zzz()
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 == 0) goto L_0x0060
            java.lang.String r12 = r2.zzb
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 != 0) goto L_0x0060
            r9.zzM(r10)
            com.google.android.gms.measurement.internal.zzam r12 = r1.zze
            zzal(r12)
            r12.zzD(r9)
            com.google.android.gms.measurement.internal.zzfv r9 = r1.zzc
            zzal(r9)
            java.lang.String r12 = r2.zza
            r9.zzm(r12)
        L_0x0060:
            boolean r9 = r2.zzh
            if (r9 != 0) goto L_0x0068
            r23.zzd(r24)
            return
        L_0x0068:
            long r12 = r2.zzm
            int r9 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r9 != 0) goto L_0x0077
            com.google.android.gms.common.util.Clock r9 = r23.zzax()
            long r12 = r9.currentTimeMillis()
            goto L_0x0078
        L_0x0077:
        L_0x0078:
            com.google.android.gms.measurement.internal.zzge r9 = r1.zzn
            com.google.android.gms.measurement.internal.zzaq r9 = r9.zzg()
            r9.zzd()
            int r9 = r2.zzn
            r15 = 1
            if (r9 == 0) goto L_0x00a0
            if (r9 == r15) goto L_0x00a0
            com.google.android.gms.measurement.internal.zzeu r16 = r23.zzaA()
            com.google.android.gms.measurement.internal.zzes r14 = r16.zzk()
            java.lang.String r10 = r2.zza
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            java.lang.String r11 = "Incorrect app type, assuming installed app. appId, appType"
            r14.zzc(r11, r10, r9)
            r9 = 0
        L_0x00a0:
            com.google.android.gms.measurement.internal.zzam r10 = r1.zze
            zzal(r10)
            r10.zzw()
            com.google.android.gms.measurement.internal.zzam r10 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r10)     // Catch:{ all -> 0x0585 }
            java.lang.String r11 = r2.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzll r10 = r10.zzp(r11, r0)     // Catch:{ all -> 0x0585 }
            r11 = r3
            r20 = r4
            if (r10 == 0) goto L_0x00c5
            java.lang.String r14 = "auto"
            java.lang.String r3 = r10.zzb     // Catch:{ all -> 0x0585 }
            boolean r3 = r14.equals(r3)     // Catch:{ all -> 0x0585 }
            if (r3 == 0) goto L_0x00c3
            goto L_0x00c5
        L_0x00c3:
            r4 = r15
            goto L_0x00ff
        L_0x00c5:
            java.lang.Boolean r3 = r2.zzr     // Catch:{ all -> 0x0585 }
            if (r3 == 0) goto L_0x00f9
            com.google.android.gms.measurement.internal.zzlj r0 = new com.google.android.gms.measurement.internal.zzlj     // Catch:{ all -> 0x0585 }
            java.lang.String r3 = "_npa"
            java.lang.Boolean r4 = r2.zzr     // Catch:{ all -> 0x0585 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x0585 }
            if (r15 == r4) goto L_0x00d8
            r18 = 0
            goto L_0x00da
        L_0x00d8:
            r18 = 1
        L_0x00da:
            java.lang.Long r18 = java.lang.Long.valueOf(r18)     // Catch:{ all -> 0x0585 }
            java.lang.String r19 = "auto"
            r4 = 0
            r14 = r0
            r4 = r15
            r15 = r3
            r16 = r12
            r14.<init>(r15, r16, r18, r19)     // Catch:{ all -> 0x0585 }
            if (r10 == 0) goto L_0x00f5
            java.lang.Object r3 = r10.zze     // Catch:{ all -> 0x0585 }
            java.lang.Long r10 = r0.zzd     // Catch:{ all -> 0x0585 }
            boolean r3 = r3.equals(r10)     // Catch:{ all -> 0x0585 }
            if (r3 != 0) goto L_0x00ff
        L_0x00f5:
            r1.zzW(r0, r2)     // Catch:{ all -> 0x0585 }
            goto L_0x00ff
        L_0x00f9:
            r4 = r15
            if (r10 == 0) goto L_0x00ff
            r1.zzP(r0, r2)     // Catch:{ all -> 0x0585 }
        L_0x00ff:
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r0)     // Catch:{ all -> 0x0585 }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x0585 }
            java.lang.Object r3 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)     // Catch:{ all -> 0x0585 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzh r0 = r0.zzj(r3)     // Catch:{ all -> 0x0585 }
            if (r0 == 0) goto L_0x01fc
            com.google.android.gms.measurement.internal.zzlo r10 = r23.zzv()     // Catch:{ all -> 0x0585 }
            java.lang.String r14 = r2.zzb     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = r0.zzz()     // Catch:{ all -> 0x0585 }
            java.lang.String r3 = r2.zzq     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = r0.zzs()     // Catch:{ all -> 0x0585 }
            boolean r3 = r10.zzao(r14, r15, r3, r4)     // Catch:{ all -> 0x0585 }
            if (r3 == 0) goto L_0x01f7
            com.google.android.gms.measurement.internal.zzeu r3 = r23.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzk()     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "New GMP App Id passed in. Removing cached database data. appId"
            java.lang.String r10 = r0.zzu()     // Catch:{ all -> 0x0585 }
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0585 }
            r3.zzb(r4, r10)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r3)     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = r0.zzu()     // Catch:{ all -> 0x0585 }
            r3.zzW()     // Catch:{ all -> 0x0585 }
            r3.zzg()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4)     // Catch:{ all -> 0x0585 }
            android.database.sqlite.SQLiteDatabase r0 = r3.zzh()     // Catch:{ SQLiteException -> 0x01dd }
            r10 = 1
            java.lang.String[] r14 = new java.lang.String[r10]     // Catch:{ SQLiteException -> 0x01dd }
            r10 = 0
            r14[r10] = r4     // Catch:{ SQLiteException -> 0x01dd }
            java.lang.String r10 = "events"
            int r10 = r0.delete(r10, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            java.lang.String r15 = "user_attributes"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "conditional_properties"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "apps"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "raw_events"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "raw_events_metadata"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "event_filters"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "property_filters"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "audience_filter_values"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            java.lang.String r15 = "consent_settings"
            int r15 = r0.delete(r15, r8, r14)     // Catch:{ SQLiteException -> 0x01dd }
            int r10 = r10 + r15
            com.google.android.gms.internal.measurement.zzph.zzc()     // Catch:{ SQLiteException -> 0x01dd }
            com.google.android.gms.measurement.internal.zzge r15 = r3.zzt     // Catch:{ SQLiteException -> 0x01dd }
            com.google.android.gms.measurement.internal.zzag r15 = r15.zzf()     // Catch:{ SQLiteException -> 0x01dd }
            r21 = r11
            com.google.android.gms.measurement.internal.zzeg r11 = com.google.android.gms.measurement.internal.zzeh.zzat     // Catch:{ SQLiteException -> 0x01d9 }
            r22 = r6
            r6 = 0
            boolean r11 = r15.zzs(r6, r11)     // Catch:{ SQLiteException -> 0x01d7 }
            if (r11 == 0) goto L_0x01bd
            java.lang.String r6 = "default_event_params"
            int r0 = r0.delete(r6, r8, r14)     // Catch:{ SQLiteException -> 0x01d7 }
            int r10 = r10 + r0
            goto L_0x01be
        L_0x01bd:
        L_0x01be:
            if (r10 <= 0) goto L_0x01d5
            com.google.android.gms.measurement.internal.zzge r0 = r3.zzt     // Catch:{ SQLiteException -> 0x01d7 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x01d7 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()     // Catch:{ SQLiteException -> 0x01d7 }
            java.lang.String r6 = "Deleted application data. app, records"
            java.lang.Integer r8 = java.lang.Integer.valueOf(r10)     // Catch:{ SQLiteException -> 0x01d7 }
            r0.zzc(r6, r4, r8)     // Catch:{ SQLiteException -> 0x01d7 }
            r0 = 0
            goto L_0x0200
        L_0x01d5:
            r0 = 0
            goto L_0x0200
        L_0x01d7:
            r0 = move-exception
            goto L_0x01e2
        L_0x01d9:
            r0 = move-exception
            r22 = r6
            goto L_0x01e2
        L_0x01dd:
            r0 = move-exception
            r22 = r6
            r21 = r11
        L_0x01e2:
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0585 }
            java.lang.String r6 = "Error deleting application data. appId, error"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r4)     // Catch:{ all -> 0x0585 }
            r3.zzc(r6, r4, r0)     // Catch:{ all -> 0x0585 }
            r0 = 0
            goto L_0x0200
        L_0x01f7:
            r22 = r6
            r21 = r11
            goto L_0x0200
        L_0x01fc:
            r22 = r6
            r21 = r11
        L_0x0200:
            if (r0 == 0) goto L_0x0256
            long r3 = r0.zzb()     // Catch:{ all -> 0x0585 }
            r10 = -2147483648(0xffffffff80000000, double:NaN)
            int r3 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r3 == 0) goto L_0x0219
            long r3 = r0.zzb()     // Catch:{ all -> 0x0585 }
            long r14 = r2.zzj     // Catch:{ all -> 0x0585 }
            int r3 = (r3 > r14 ? 1 : (r3 == r14 ? 0 : -1))
            if (r3 == 0) goto L_0x0219
            r15 = 1
            goto L_0x021a
        L_0x0219:
            r15 = 0
        L_0x021a:
            java.lang.String r3 = r0.zzx()     // Catch:{ all -> 0x0585 }
            long r16 = r0.zzb()     // Catch:{ all -> 0x0585 }
            int r0 = (r16 > r10 ? 1 : (r16 == r10 ? 0 : -1))
            if (r0 != 0) goto L_0x0232
            if (r3 == 0) goto L_0x0232
            java.lang.String r0 = r2.zzc     // Catch:{ all -> 0x0585 }
            boolean r0 = r3.equals(r0)     // Catch:{ all -> 0x0585 }
            if (r0 != 0) goto L_0x0232
            r0 = 1
            goto L_0x0233
        L_0x0232:
            r0 = 0
        L_0x0233:
            r0 = r0 | r15
            if (r0 == 0) goto L_0x0256
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x0585 }
            r0.<init>()     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "_pv"
            r0.putString(r4, r3)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzaw r3 = new com.google.android.gms.measurement.internal.zzaw     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = "_au"
            com.google.android.gms.measurement.internal.zzau r4 = new com.google.android.gms.measurement.internal.zzau     // Catch:{ all -> 0x0585 }
            r4.<init>(r0)     // Catch:{ all -> 0x0585 }
            java.lang.String r17 = "auto"
            r14 = r3
            r16 = r4
            r18 = r12
            r14.<init>(r15, r16, r17, r18)     // Catch:{ all -> 0x0585 }
            r1.zzE(r3, r2)     // Catch:{ all -> 0x0585 }
        L_0x0256:
            r23.zzd(r24)     // Catch:{ all -> 0x0585 }
            if (r9 != 0) goto L_0x026a
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r0)     // Catch:{ all -> 0x0585 }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "_f"
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzn(r3, r4)     // Catch:{ all -> 0x0585 }
            r15 = 0
            goto L_0x0278
        L_0x026a:
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r0)     // Catch:{ all -> 0x0585 }
            java.lang.String r3 = r2.zza     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "_v"
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzn(r3, r4)     // Catch:{ all -> 0x0585 }
            r15 = 1
        L_0x0278:
            if (r0 != 0) goto L_0x0555
            r3 = 3600000(0x36ee80, double:1.7786363E-317)
            long r8 = r12 / r3
            r10 = 1
            long r8 = r8 + r10
            long r8 = r8 * r3
            java.lang.String r3 = "_dac"
            java.lang.String r4 = "_et"
            java.lang.String r6 = "_r"
            java.lang.String r10 = "_c"
            if (r15 != 0) goto L_0x0508
            com.google.android.gms.measurement.internal.zzlj r0 = new com.google.android.gms.measurement.internal.zzlj     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = "_fot"
            java.lang.Long r18 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0585 }
            java.lang.String r19 = "auto"
            r14 = r0
            r16 = r12
            r14.<init>(r15, r16, r18, r19)     // Catch:{ all -> 0x0585 }
            r1.zzW(r0, r2)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzgb r0 = r23.zzaB()     // Catch:{ all -> 0x0585 }
            r0.zzg()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzfm r0 = r1.zzm     // Catch:{ all -> 0x0585 }
            java.lang.Object r0 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ all -> 0x0585 }
            r8 = r0
            com.google.android.gms.measurement.internal.zzfm r8 = (com.google.android.gms.measurement.internal.zzfm) r8     // Catch:{ all -> 0x0585 }
            java.lang.String r0 = r2.zza     // Catch:{ all -> 0x0585 }
            if (r0 == 0) goto L_0x03a3
            boolean r9 = r0.isEmpty()     // Catch:{ all -> 0x0585 }
            if (r9 == 0) goto L_0x02bc
            goto L_0x03a3
        L_0x02bc:
            com.google.android.gms.measurement.internal.zzge r9 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzgb r9 = r9.zzaB()     // Catch:{ all -> 0x0585 }
            r9.zzg()     // Catch:{ all -> 0x0585 }
            boolean r9 = r8.zza()     // Catch:{ all -> 0x0585 }
            if (r9 != 0) goto L_0x02dc
            com.google.android.gms.measurement.internal.zzge r0 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzi()     // Catch:{ all -> 0x0585 }
            java.lang.String r5 = "Install Referrer Reporter is not available"
            r0.zza(r5)     // Catch:{ all -> 0x0585 }
            goto L_0x03b2
        L_0x02dc:
            com.google.android.gms.measurement.internal.zzfl r9 = new com.google.android.gms.measurement.internal.zzfl     // Catch:{ all -> 0x0585 }
            r9.<init>(r8, r0)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzge r0 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzgb r0 = r0.zzaB()     // Catch:{ all -> 0x0585 }
            r0.zzg()     // Catch:{ all -> 0x0585 }
            android.content.Intent r0 = new android.content.Intent     // Catch:{ all -> 0x0585 }
            java.lang.String r11 = "com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE"
            r0.<init>(r11)     // Catch:{ all -> 0x0585 }
            android.content.ComponentName r11 = new android.content.ComponentName     // Catch:{ all -> 0x0585 }
            java.lang.String r14 = "com.google.android.finsky.externalreferrer.GetInstallReferrerService"
            r11.<init>(r5, r14)     // Catch:{ all -> 0x0585 }
            r0.setComponent(r11)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzge r11 = r8.zza     // Catch:{ all -> 0x0585 }
            android.content.Context r11 = r11.zzaw()     // Catch:{ all -> 0x0585 }
            android.content.pm.PackageManager r11 = r11.getPackageManager()     // Catch:{ all -> 0x0585 }
            if (r11 != 0) goto L_0x0318
            com.google.android.gms.measurement.internal.zzge r0 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzm()     // Catch:{ all -> 0x0585 }
            java.lang.String r5 = "Failed to obtain Package Manager to verify binding conditions for Install Referrer"
            r0.zza(r5)     // Catch:{ all -> 0x0585 }
            goto L_0x03b2
        L_0x0318:
            r14 = 0
            java.util.List r11 = r11.queryIntentServices(r0, r14)     // Catch:{ all -> 0x0585 }
            if (r11 == 0) goto L_0x0393
            boolean r14 = r11.isEmpty()     // Catch:{ all -> 0x0585 }
            if (r14 != 0) goto L_0x0393
            r14 = 0
            java.lang.Object r11 = r11.get(r14)     // Catch:{ all -> 0x0585 }
            android.content.pm.ResolveInfo r11 = (android.content.pm.ResolveInfo) r11     // Catch:{ all -> 0x0585 }
            android.content.pm.ServiceInfo r11 = r11.serviceInfo     // Catch:{ all -> 0x0585 }
            if (r11 == 0) goto L_0x03b2
            java.lang.String r14 = r11.packageName     // Catch:{ all -> 0x0585 }
            java.lang.String r11 = r11.name     // Catch:{ all -> 0x0585 }
            if (r11 == 0) goto L_0x0383
            boolean r5 = r5.equals(r14)     // Catch:{ all -> 0x0585 }
            if (r5 == 0) goto L_0x0383
            boolean r5 = r8.zza()     // Catch:{ all -> 0x0585 }
            if (r5 == 0) goto L_0x0383
            android.content.Intent r5 = new android.content.Intent     // Catch:{ all -> 0x0585 }
            r5.<init>(r0)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.common.stats.ConnectionTracker r0 = com.google.android.gms.common.stats.ConnectionTracker.getInstance()     // Catch:{ RuntimeException -> 0x036e }
            com.google.android.gms.measurement.internal.zzge r11 = r8.zza     // Catch:{ RuntimeException -> 0x036e }
            android.content.Context r11 = r11.zzaw()     // Catch:{ RuntimeException -> 0x036e }
            r14 = 1
            boolean r0 = r0.bindService(r11, r5, r9, r14)     // Catch:{ RuntimeException -> 0x036e }
            com.google.android.gms.measurement.internal.zzge r5 = r8.zza     // Catch:{ RuntimeException -> 0x036e }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ RuntimeException -> 0x036e }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzj()     // Catch:{ RuntimeException -> 0x036e }
            java.lang.String r9 = "Install Referrer Service is"
            if (r0 == 0) goto L_0x0368
            java.lang.String r0 = "available"
            goto L_0x036a
        L_0x0368:
            java.lang.String r0 = "not available"
        L_0x036a:
            r5.zzb(r9, r0)     // Catch:{ RuntimeException -> 0x036e }
            goto L_0x03b2
        L_0x036e:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r5 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r5 = r5.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r5 = r5.zzd()     // Catch:{ all -> 0x0585 }
            java.lang.String r8 = "Exception occurred while binding to Install Referrer Service"
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0585 }
            r5.zzb(r8, r0)     // Catch:{ all -> 0x0585 }
            goto L_0x03b2
        L_0x0383:
            com.google.android.gms.measurement.internal.zzge r0 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x0585 }
            java.lang.String r5 = "Play Store version 8.3.73 or higher required for Install Referrer"
            r0.zza(r5)     // Catch:{ all -> 0x0585 }
            goto L_0x03b2
        L_0x0393:
            com.google.android.gms.measurement.internal.zzge r0 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzi()     // Catch:{ all -> 0x0585 }
            java.lang.String r5 = "Play Service for fetching Install Referrer is unavailable on device"
            r0.zza(r5)     // Catch:{ all -> 0x0585 }
            goto L_0x03b2
        L_0x03a3:
            com.google.android.gms.measurement.internal.zzge r0 = r8.zza     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzm()     // Catch:{ all -> 0x0585 }
            java.lang.String r5 = "Install Referrer Reporter was called with invalid app package name"
            r0.zza(r5)     // Catch:{ all -> 0x0585 }
        L_0x03b2:
            com.google.android.gms.measurement.internal.zzgb r0 = r23.zzaB()     // Catch:{ all -> 0x0585 }
            r0.zzg()     // Catch:{ all -> 0x0585 }
            r23.zzB()     // Catch:{ all -> 0x0585 }
            android.os.Bundle r5 = new android.os.Bundle     // Catch:{ all -> 0x0585 }
            r5.<init>()     // Catch:{ all -> 0x0585 }
            r8 = 1
            r5.putLong(r10, r8)     // Catch:{ all -> 0x0585 }
            r5.putLong(r6, r8)     // Catch:{ all -> 0x0585 }
            r8 = 0
            r5.putLong(r7, r8)     // Catch:{ all -> 0x0585 }
            r6 = r22
            r5.putLong(r6, r8)     // Catch:{ all -> 0x0585 }
            r10 = r20
            r5.putLong(r10, r8)     // Catch:{ all -> 0x0585 }
            r11 = r21
            r5.putLong(r11, r8)     // Catch:{ all -> 0x0585 }
            r8 = 1
            r5.putLong(r4, r8)     // Catch:{ all -> 0x0585 }
            boolean r0 = r2.zzp     // Catch:{ all -> 0x0585 }
            if (r0 == 0) goto L_0x03eb
            r8 = 1
            r5.putLong(r3, r8)     // Catch:{ all -> 0x0585 }
        L_0x03eb:
            java.lang.String r0 = r2.zza     // Catch:{ all -> 0x0585 }
            java.lang.Object r0 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ all -> 0x0585 }
            r3 = r0
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r0)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)     // Catch:{ all -> 0x0585 }
            r0.zzg()     // Catch:{ all -> 0x0585 }
            r0.zzW()     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "first_open_count"
            long r8 = r0.zzc(r3, r4)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzn     // Catch:{ all -> 0x0585 }
            android.content.Context r0 = r0.zzaw()     // Catch:{ all -> 0x0585 }
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch:{ all -> 0x0585 }
            if (r0 != 0) goto L_0x0427
            com.google.android.gms.measurement.internal.zzeu r0 = r23.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "PackageManager is null, first open report might be inaccurate. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x0585 }
            r0.zzb(r4, r3)     // Catch:{ all -> 0x0585 }
            goto L_0x04e8
        L_0x0427:
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzn     // Catch:{ NameNotFoundException -> 0x0437 }
            android.content.Context r0 = r0.zzaw()     // Catch:{ NameNotFoundException -> 0x0437 }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch:{ NameNotFoundException -> 0x0437 }
            r4 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r3, r4)     // Catch:{ NameNotFoundException -> 0x0437 }
            goto L_0x044a
        L_0x0437:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzeu r4 = r23.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzd()     // Catch:{ all -> 0x0585 }
            java.lang.String r14 = "Package info is null, first open report might be inaccurate. appId"
            java.lang.Object r15 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x0585 }
            r4.zzc(r14, r15, r0)     // Catch:{ all -> 0x0585 }
            r0 = 0
        L_0x044a:
            if (r0 == 0) goto L_0x04a3
            long r14 = r0.firstInstallTime     // Catch:{ all -> 0x0585 }
            r16 = 0
            int r4 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r4 == 0) goto L_0x04a3
            r20 = r10
            r21 = r11
            long r10 = r0.lastUpdateTime     // Catch:{ all -> 0x0585 }
            int r0 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r0 == 0) goto L_0x0484
            com.google.android.gms.measurement.internal.zzag r0 = r23.zzg()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzeg r4 = com.google.android.gms.measurement.internal.zzeh.zzad     // Catch:{ all -> 0x0585 }
            r10 = 0
            boolean r0 = r0.zzs(r10, r4)     // Catch:{ all -> 0x0585 }
            if (r0 == 0) goto L_0x047c
            r14 = 0
            int r0 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r0 != 0) goto L_0x047a
            r8 = 1
            r5.putLong(r7, r8)     // Catch:{ all -> 0x0585 }
            r8 = 0
            r15 = 0
            goto L_0x0486
        L_0x047a:
            r15 = 0
            goto L_0x0486
        L_0x047c:
            r14 = 1
            r5.putLong(r7, r14)     // Catch:{ all -> 0x0585 }
            r15 = 0
            goto L_0x0486
        L_0x0484:
            r10 = 0
            r15 = 1
        L_0x0486:
            com.google.android.gms.measurement.internal.zzlj r0 = new com.google.android.gms.measurement.internal.zzlj     // Catch:{ all -> 0x0585 }
            java.lang.String r4 = "_fi"
            r7 = 1
            if (r7 == r15) goto L_0x0490
            r14 = 0
            goto L_0x0492
        L_0x0490:
            r14 = 1
        L_0x0492:
            java.lang.Long r18 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x0585 }
            java.lang.String r19 = "auto"
            r14 = r0
            r15 = r4
            r16 = r12
            r14.<init>(r15, r16, r18, r19)     // Catch:{ all -> 0x0585 }
            r1.zzW(r0, r2)     // Catch:{ all -> 0x0585 }
            goto L_0x04a8
        L_0x04a3:
            r20 = r10
            r21 = r11
            r10 = 0
        L_0x04a8:
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzn     // Catch:{ NameNotFoundException -> 0x04b8 }
            android.content.Context r0 = r0.zzaw()     // Catch:{ NameNotFoundException -> 0x04b8 }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch:{ NameNotFoundException -> 0x04b8 }
            r4 = 0
            android.content.pm.ApplicationInfo r3 = r0.getApplicationInfo(r3, r4)     // Catch:{ NameNotFoundException -> 0x04b8 }
            goto L_0x04cb
        L_0x04b8:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzeu r4 = r23.zzaA()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzes r4 = r4.zzd()     // Catch:{ all -> 0x0585 }
            java.lang.String r7 = "Application info is null, first open report might be inaccurate. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x0585 }
            r4.zzc(r7, r3, r0)     // Catch:{ all -> 0x0585 }
            r3 = r10
        L_0x04cb:
            if (r3 == 0) goto L_0x04e7
            int r0 = r3.flags     // Catch:{ all -> 0x0585 }
            r4 = 1
            r0 = r0 & r4
            if (r0 == 0) goto L_0x04da
            r4 = r20
            r10 = 1
            r5.putLong(r4, r10)     // Catch:{ all -> 0x0585 }
        L_0x04da:
            int r0 = r3.flags     // Catch:{ all -> 0x0585 }
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x04e7
            r3 = r21
            r10 = 1
            r5.putLong(r3, r10)     // Catch:{ all -> 0x0585 }
        L_0x04e7:
        L_0x04e8:
            r3 = 0
            int r0 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r0 < 0) goto L_0x04f1
            r5.putLong(r6, r8)     // Catch:{ all -> 0x0585 }
        L_0x04f1:
            com.google.android.gms.measurement.internal.zzaw r0 = new com.google.android.gms.measurement.internal.zzaw     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = "_f"
            com.google.android.gms.measurement.internal.zzau r3 = new com.google.android.gms.measurement.internal.zzau     // Catch:{ all -> 0x0585 }
            r3.<init>(r5)     // Catch:{ all -> 0x0585 }
            java.lang.String r17 = "auto"
            r14 = r0
            r16 = r3
            r18 = r12
            r14.<init>(r15, r16, r17, r18)     // Catch:{ all -> 0x0585 }
            r1.zzG(r0, r2)     // Catch:{ all -> 0x0585 }
            goto L_0x0574
        L_0x0508:
            com.google.android.gms.measurement.internal.zzlj r0 = new com.google.android.gms.measurement.internal.zzlj     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = "_fvt"
            java.lang.Long r18 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0585 }
            java.lang.String r19 = "auto"
            r14 = r0
            r16 = r12
            r14.<init>(r15, r16, r18, r19)     // Catch:{ all -> 0x0585 }
            r1.zzW(r0, r2)     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzgb r0 = r23.zzaB()     // Catch:{ all -> 0x0585 }
            r0.zzg()     // Catch:{ all -> 0x0585 }
            r23.zzB()     // Catch:{ all -> 0x0585 }
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x0585 }
            r0.<init>()     // Catch:{ all -> 0x0585 }
            r7 = 1
            r0.putLong(r10, r7)     // Catch:{ all -> 0x0585 }
            r0.putLong(r6, r7)     // Catch:{ all -> 0x0585 }
            r0.putLong(r4, r7)     // Catch:{ all -> 0x0585 }
            boolean r4 = r2.zzp     // Catch:{ all -> 0x0585 }
            if (r4 == 0) goto L_0x053e
            r4 = 1
            r0.putLong(r3, r4)     // Catch:{ all -> 0x0585 }
        L_0x053e:
            com.google.android.gms.measurement.internal.zzaw r3 = new com.google.android.gms.measurement.internal.zzaw     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = "_v"
            com.google.android.gms.measurement.internal.zzau r4 = new com.google.android.gms.measurement.internal.zzau     // Catch:{ all -> 0x0585 }
            r4.<init>(r0)     // Catch:{ all -> 0x0585 }
            java.lang.String r17 = "auto"
            r14 = r3
            r16 = r4
            r18 = r12
            r14.<init>(r15, r16, r17, r18)     // Catch:{ all -> 0x0585 }
            r1.zzG(r3, r2)     // Catch:{ all -> 0x0585 }
            goto L_0x0574
        L_0x0555:
            boolean r0 = r2.zzi     // Catch:{ all -> 0x0585 }
            if (r0 == 0) goto L_0x0574
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x0585 }
            r0.<init>()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzaw r3 = new com.google.android.gms.measurement.internal.zzaw     // Catch:{ all -> 0x0585 }
            java.lang.String r15 = "_cd"
            com.google.android.gms.measurement.internal.zzau r4 = new com.google.android.gms.measurement.internal.zzau     // Catch:{ all -> 0x0585 }
            r4.<init>(r0)     // Catch:{ all -> 0x0585 }
            java.lang.String r17 = "auto"
            r14 = r3
            r16 = r4
            r18 = r12
            r14.<init>(r15, r16, r17, r18)     // Catch:{ all -> 0x0585 }
            r1.zzG(r3, r2)     // Catch:{ all -> 0x0585 }
        L_0x0574:
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x0585 }
            zzal(r0)     // Catch:{ all -> 0x0585 }
            r0.zzC()     // Catch:{ all -> 0x0585 }
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze
            zzal(r0)
            r0.zzx()
            return
        L_0x0585:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            zzal(r2)
            r2.zzx()
            throw r0
        L_0x058f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzlg.zzL(com.google.android.gms.measurement.internal.zzq):void");
    }

    /* access modifiers changed from: package-private */
    public final void zzM() {
        this.zzr++;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzN(zzac zzac) {
        zzq zzac2 = zzac((String) Preconditions.checkNotNull(zzac.zza));
        if (zzac2 != null) {
            zzO(zzac, zzac2);
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzO(zzac zzac, zzq zzq2) {
        Bundle bundle;
        Preconditions.checkNotNull(zzac);
        Preconditions.checkNotEmpty(zzac.zza);
        Preconditions.checkNotNull(zzac.zzc);
        Preconditions.checkNotEmpty(zzac.zzc.zzb);
        zzaB().zzg();
        zzB();
        if (zzak(zzq2)) {
            if (zzq2.zzh) {
                zzam zzam = this.zze;
                zzal(zzam);
                zzam.zzw();
                try {
                    zzd(zzq2);
                    String str = (String) Preconditions.checkNotNull(zzac.zza);
                    zzam zzam2 = this.zze;
                    zzal(zzam2);
                    zzac zzk2 = zzam2.zzk(str, zzac.zzc.zzb);
                    if (zzk2 != null) {
                        zzaA().zzc().zzc("Removing conditional user property", zzac.zza, this.zzn.zzj().zzf(zzac.zzc.zzb));
                        zzam zzam3 = this.zze;
                        zzal(zzam3);
                        zzam3.zza(str, zzac.zzc.zzb);
                        if (zzk2.zze) {
                            zzam zzam4 = this.zze;
                            zzal(zzam4);
                            zzam4.zzA(str, zzac.zzc.zzb);
                        }
                        zzaw zzaw = zzac.zzk;
                        if (zzaw != null) {
                            zzau zzau = zzaw.zzb;
                            if (zzau != null) {
                                bundle = zzau.zzc();
                            } else {
                                bundle = null;
                            }
                            zzY((zzaw) Preconditions.checkNotNull(zzv().zzz(str, ((zzaw) Preconditions.checkNotNull(zzac.zzk)).zza, bundle, zzk2.zzb, zzac.zzk.zzd, true, true)), zzq2);
                        }
                    } else {
                        zzaA().zzk().zzc("Conditional user property doesn't exist", zzeu.zzn(zzac.zza), this.zzn.zzj().zzf(zzac.zzc.zzb));
                    }
                    zzam zzam5 = this.zze;
                    zzal(zzam5);
                    zzam5.zzC();
                } finally {
                    zzam zzam6 = this.zze;
                    zzal(zzam6);
                    zzam6.zzx();
                }
            } else {
                zzd(zzq2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzP(String str, zzq zzq2) {
        long j;
        zzaB().zzg();
        zzB();
        if (zzak(zzq2)) {
            if (!zzq2.zzh) {
                zzd(zzq2);
            } else if (!"_npa".equals(str) || zzq2.zzr == null) {
                zzaA().zzc().zzb("Removing user property", this.zzn.zzj().zzf(str));
                zzam zzam = this.zze;
                zzal(zzam);
                zzam.zzw();
                try {
                    zzd(zzq2);
                    if ("_id".equals(str)) {
                        zzam zzam2 = this.zze;
                        zzal(zzam2);
                        zzam2.zzA((String) Preconditions.checkNotNull(zzq2.zza), "_lair");
                    }
                    zzam zzam3 = this.zze;
                    zzal(zzam3);
                    zzam3.zzA((String) Preconditions.checkNotNull(zzq2.zza), str);
                    zzam zzam4 = this.zze;
                    zzal(zzam4);
                    zzam4.zzC();
                    zzaA().zzc().zzb("User property removed", this.zzn.zzj().zzf(str));
                } finally {
                    zzam zzam5 = this.zze;
                    zzal(zzam5);
                    zzam5.zzx();
                }
            } else {
                zzaA().zzc().zza("Falling back to manifest metadata value for ad personalization");
                long currentTimeMillis = zzax().currentTimeMillis();
                if (true != zzq2.zzr.booleanValue()) {
                    j = 0;
                } else {
                    j = 1;
                }
                zzW(new zzlj("_npa", currentTimeMillis, Long.valueOf(j), "auto"), zzq2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final void zzQ(zzq zzq2) {
        if (this.zzy != null) {
            ArrayList arrayList = new ArrayList();
            this.zzz = arrayList;
            arrayList.addAll(this.zzy);
        }
        zzam zzam = this.zze;
        zzal(zzam);
        String str = (String) Preconditions.checkNotNull(zzq2.zza);
        Preconditions.checkNotEmpty(str);
        zzam.zzg();
        zzam.zzW();
        try {
            SQLiteDatabase zzh2 = zzam.zzh();
            String[] strArr = {str};
            int delete = zzh2.delete("apps", "app_id=?", strArr) + zzh2.delete("events", "app_id=?", strArr) + zzh2.delete("user_attributes", "app_id=?", strArr) + zzh2.delete("conditional_properties", "app_id=?", strArr) + zzh2.delete("raw_events", "app_id=?", strArr) + zzh2.delete("raw_events_metadata", "app_id=?", strArr) + zzh2.delete("queue", "app_id=?", strArr) + zzh2.delete("audience_filter_values", "app_id=?", strArr) + zzh2.delete("main_event_params", "app_id=?", strArr) + zzh2.delete("default_event_params", "app_id=?", strArr);
            if (delete > 0) {
                zzam.zzt.zzaA().zzj().zzc("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzam.zzt.zzaA().zzd().zzc("Error resetting analytics data. appId, error", zzeu.zzn(str), e);
        }
        if (zzq2.zzh) {
            zzL(zzq2);
        }
    }

    @WorkerThread
    public final void zzR(String str, zziq zziq) {
        zzaB().zzg();
        String str2 = this.zzE;
        if (str2 == null || str2.equals(str) || zziq != null) {
            this.zzE = str;
            this.zzD = zziq;
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzS() {
        zzaB().zzg();
        zzam zzam = this.zze;
        zzal(zzam);
        zzam.zzz();
        if (this.zzk.zzc.zza() == 0) {
            this.zzk.zzc.zzb(zzax().currentTimeMillis());
        }
        zzag();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzT(zzac zzac) {
        zzq zzac2 = zzac((String) Preconditions.checkNotNull(zzac.zza));
        if (zzac2 != null) {
            zzU(zzac, zzac2);
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzU(zzac zzac, zzq zzq2) {
        Preconditions.checkNotNull(zzac);
        Preconditions.checkNotEmpty(zzac.zza);
        Preconditions.checkNotNull(zzac.zzb);
        Preconditions.checkNotNull(zzac.zzc);
        Preconditions.checkNotEmpty(zzac.zzc.zzb);
        zzaB().zzg();
        zzB();
        if (zzak(zzq2)) {
            if (!zzq2.zzh) {
                zzd(zzq2);
                return;
            }
            zzac zzac2 = new zzac(zzac);
            boolean z = false;
            zzac2.zze = false;
            zzam zzam = this.zze;
            zzal(zzam);
            zzam.zzw();
            try {
                zzam zzam2 = this.zze;
                zzal(zzam2);
                zzac zzk2 = zzam2.zzk((String) Preconditions.checkNotNull(zzac2.zza), zzac2.zzc.zzb);
                if (zzk2 != null && !zzk2.zzb.equals(zzac2.zzb)) {
                    zzaA().zzk().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzn.zzj().zzf(zzac2.zzc.zzb), zzac2.zzb, zzk2.zzb);
                }
                if (zzk2 != null && zzk2.zze) {
                    zzac2.zzb = zzk2.zzb;
                    zzac2.zzd = zzk2.zzd;
                    zzac2.zzh = zzk2.zzh;
                    zzac2.zzf = zzk2.zzf;
                    zzac2.zzi = zzk2.zzi;
                    zzac2.zze = true;
                    zzlj zzlj = zzac2.zzc;
                    zzac2.zzc = new zzlj(zzlj.zzb, zzk2.zzc.zzc, zzlj.zza(), zzk2.zzc.zzf);
                } else if (TextUtils.isEmpty(zzac2.zzf)) {
                    zzlj zzlj2 = zzac2.zzc;
                    zzac2.zzc = new zzlj(zzlj2.zzb, zzac2.zzd, zzlj2.zza(), zzac2.zzc.zzf);
                    zzac2.zze = true;
                    z = true;
                }
                if (zzac2.zze) {
                    zzlj zzlj3 = zzac2.zzc;
                    zzll zzll = new zzll((String) Preconditions.checkNotNull(zzac2.zza), zzac2.zzb, zzlj3.zzb, zzlj3.zzc, Preconditions.checkNotNull(zzlj3.zza()));
                    zzam zzam3 = this.zze;
                    zzal(zzam3);
                    if (zzam3.zzL(zzll)) {
                        zzaA().zzc().zzd("User property updated immediately", zzac2.zza, this.zzn.zzj().zzf(zzll.zzc), zzll.zze);
                    } else {
                        zzaA().zzd().zzd("(2)Too many active user properties, ignoring", zzeu.zzn(zzac2.zza), this.zzn.zzj().zzf(zzll.zzc), zzll.zze);
                    }
                    if (z && zzac2.zzi != null) {
                        zzY(new zzaw(zzac2.zzi, zzac2.zzd), zzq2);
                    }
                }
                zzam zzam4 = this.zze;
                zzal(zzam4);
                if (zzam4.zzK(zzac2)) {
                    zzaA().zzc().zzd("Conditional property added", zzac2.zza, this.zzn.zzj().zzf(zzac2.zzc.zzb), zzac2.zzc.zza());
                } else {
                    zzaA().zzd().zzd("Too many conditional properties, ignoring", zzeu.zzn(zzac2.zza), this.zzn.zzj().zzf(zzac2.zzc.zzb), zzac2.zzc.zza());
                }
                zzam zzam5 = this.zze;
                zzal(zzam5);
                zzam5.zzC();
            } finally {
                zzam zzam6 = this.zze;
                zzal(zzam6);
                zzam6.zzx();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzV(String str, zzai zzai) {
        zzaB().zzg();
        zzB();
        this.zzB.put(str, zzai);
        zzam zzam = this.zze;
        zzal(zzam);
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(zzai);
        zzam.zzg();
        zzam.zzW();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("consent_state", zzai.zzh());
        try {
            if (zzam.zzh().insertWithOnConflict("consent_settings", (String) null, contentValues, 5) == -1) {
                zzam.zzt.zzaA().zzd().zzb("Failed to insert/update consent setting (got -1). appId", zzeu.zzn(str));
            }
        } catch (SQLiteException e) {
            zzam.zzt.zzaA().zzd().zzc("Error storing consent setting. appId, error", zzeu.zzn(str), e);
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzW(zzlj zzlj, zzq zzq2) {
        long j;
        int i;
        int i2;
        zzlj zzlj2 = zzlj;
        zzq zzq3 = zzq2;
        zzaB().zzg();
        zzB();
        if (zzak(zzq2)) {
            if (!zzq3.zzh) {
                zzd(zzq3);
                return;
            }
            int zzl2 = zzv().zzl(zzlj2.zzb);
            if (zzl2 != 0) {
                zzlo zzv2 = zzv();
                String str = zzlj2.zzb;
                zzg();
                String zzD2 = zzv2.zzD(str, 24, true);
                String str2 = zzlj2.zzb;
                if (str2 != null) {
                    i2 = str2.length();
                } else {
                    i2 = 0;
                }
                zzv().zzO(this.zzF, zzq3.zza, zzl2, "_ev", zzD2, i2);
                return;
            }
            int zzd2 = zzv().zzd(zzlj2.zzb, zzlj.zza());
            if (zzd2 != 0) {
                zzlo zzv3 = zzv();
                String str3 = zzlj2.zzb;
                zzg();
                String zzD3 = zzv3.zzD(str3, 24, true);
                Object zza2 = zzlj.zza();
                if (zza2 == null || (!(zza2 instanceof String) && !(zza2 instanceof CharSequence))) {
                    i = 0;
                } else {
                    i = zza2.toString().length();
                }
                zzv().zzO(this.zzF, zzq3.zza, zzd2, "_ev", zzD3, i);
                return;
            }
            Object zzB2 = zzv().zzB(zzlj2.zzb, zzlj.zza());
            if (zzB2 != null) {
                if ("_sid".equals(zzlj2.zzb)) {
                    long j2 = zzlj2.zzc;
                    String str4 = zzlj2.zzf;
                    String str5 = (String) Preconditions.checkNotNull(zzq3.zza);
                    zzam zzam = this.zze;
                    zzal(zzam);
                    zzll zzp2 = zzam.zzp(str5, "_sno");
                    if (zzp2 != null) {
                        Object obj = zzp2.zze;
                        if (obj instanceof Long) {
                            j = ((Long) obj).longValue();
                            zzW(new zzlj("_sno", j2, Long.valueOf(j + 1), str4), zzq3);
                        }
                    }
                    if (zzp2 != null) {
                        zzaA().zzk().zzb("Retrieved last session number from database does not contain a valid (long) value", zzp2.zze);
                    }
                    zzam zzam2 = this.zze;
                    zzal(zzam2);
                    zzas zzn2 = zzam2.zzn(str5, "_s");
                    if (zzn2 != null) {
                        j = zzn2.zzc;
                        zzaA().zzj().zzb("Backfill the session number. Last used session number", Long.valueOf(j));
                    } else {
                        j = 0;
                    }
                    zzW(new zzlj("_sno", j2, Long.valueOf(j + 1), str4), zzq3);
                }
                zzll zzll = new zzll((String) Preconditions.checkNotNull(zzq3.zza), (String) Preconditions.checkNotNull(zzlj2.zzf), zzlj2.zzb, zzlj2.zzc, zzB2);
                zzaA().zzj().zzc("Setting user property", this.zzn.zzj().zzf(zzll.zzc), zzB2);
                zzam zzam3 = this.zze;
                zzal(zzam3);
                zzam3.zzw();
                try {
                    if ("_id".equals(zzll.zzc)) {
                        zzam zzam4 = this.zze;
                        zzal(zzam4);
                        zzll zzp3 = zzam4.zzp(zzq3.zza, "_id");
                        if (zzp3 != null && !zzll.zze.equals(zzp3.zze)) {
                            zzam zzam5 = this.zze;
                            zzal(zzam5);
                            zzam5.zzA(zzq3.zza, "_lair");
                        }
                    }
                    zzd(zzq3);
                    zzam zzam6 = this.zze;
                    zzal(zzam6);
                    boolean zzL = zzam6.zzL(zzll);
                    zzam zzam7 = this.zze;
                    zzal(zzam7);
                    zzam7.zzC();
                    if (!zzL) {
                        zzaA().zzd().zzc("Too many unique user properties are set. Ignoring user property", this.zzn.zzj().zzf(zzll.zzc), zzll.zze);
                        zzv().zzO(this.zzF, zzq3.zza, 9, (String) null, (String) null, 0);
                    }
                } finally {
                    zzam zzam8 = this.zze;
                    zzal(zzam8);
                    zzam8.zzx();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:133:?, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0275, code lost:
        r0 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0279, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x027a, code lost:
        r9 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x027d, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x027e, code lost:
        r20 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x012a, code lost:
        if (r11 != null) goto L_0x0108;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0279 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:78:0x0193] */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x02a2 A[SYNTHETIC, Splitter:B:147:0x02a2] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x02ab A[Catch:{ all -> 0x0131, all -> 0x053c }] */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x04ae A[Catch:{ all -> 0x0131, all -> 0x053c }] */
    /* JADX WARNING: Removed duplicated region for block: B:235:0x051a A[Catch:{ all -> 0x0131, all -> 0x053c }] */
    /* JADX WARNING: Removed duplicated region for block: B:238:0x0521 A[Catch:{ all -> 0x0131, all -> 0x053c }] */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x0538 A[SYNTHETIC, Splitter:B:245:0x0538] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0135 A[Catch:{ all -> 0x0131, all -> 0x053c }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:132:0x0272=Splitter:B:132:0x0272, B:233:0x0515=Splitter:B:233:0x0515, B:47:0x0108=Splitter:B:47:0x0108, B:144:0x0289=Splitter:B:144:0x0289, B:57:0x011b=Splitter:B:57:0x011b, B:60:0x012e=Splitter:B:60:0x012e} */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzX() {
        /*
            r22 = this;
            r1 = r22
            com.google.android.gms.measurement.internal.zzgb r0 = r22.zzaB()
            r0.zzg()
            r22.zzB()
            r2 = 1
            r1.zzv = r2
            r3 = 0
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzn     // Catch:{ all -> 0x053c }
            r0.zzay()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzn     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzjy r0 = r0.zzt()     // Catch:{ all -> 0x053c }
            java.lang.Boolean r0 = r0.zzj()     // Catch:{ all -> 0x053c }
            if (r0 != 0) goto L_0x0034
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ all -> 0x053c }
            java.lang.String r2 = "Upload data called on the client side before use of service was decided"
            r0.zza(r2)     // Catch:{ all -> 0x053c }
            r1.zzv = r3
        L_0x0030:
            r22.zzae()
            return
        L_0x0034:
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x053c }
            if (r0 == 0) goto L_0x004a
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x053c }
            java.lang.String r2 = "Upload called in the client side when service should be used"
            r0.zza(r2)     // Catch:{ all -> 0x053c }
            r1.zzv = r3
            goto L_0x0030
        L_0x004a:
            long r4 = r1.zza     // Catch:{ all -> 0x053c }
            r6 = 0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x0058
            r22.zzag()     // Catch:{ all -> 0x053c }
            r1.zzv = r3
            goto L_0x0030
        L_0x0058:
            com.google.android.gms.measurement.internal.zzgb r0 = r22.zzaB()     // Catch:{ all -> 0x053c }
            r0.zzg()     // Catch:{ all -> 0x053c }
            java.util.List r0 = r1.zzy     // Catch:{ all -> 0x053c }
            if (r0 == 0) goto L_0x0073
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()     // Catch:{ all -> 0x053c }
            java.lang.String r2 = "Uploading requested multiple times"
            r0.zza(r2)     // Catch:{ all -> 0x053c }
            r1.zzv = r3
            goto L_0x0030
        L_0x0073:
            com.google.android.gms.measurement.internal.zzfa r0 = r1.zzd     // Catch:{ all -> 0x053c }
            zzal(r0)     // Catch:{ all -> 0x053c }
            boolean r0 = r0.zza()     // Catch:{ all -> 0x053c }
            if (r0 != 0) goto L_0x0091
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()     // Catch:{ all -> 0x053c }
            java.lang.String r2 = "Network not connected, ignoring upload request"
            r0.zza(r2)     // Catch:{ all -> 0x053c }
            r22.zzag()     // Catch:{ all -> 0x053c }
            r1.zzv = r3
            goto L_0x0030
        L_0x0091:
            com.google.android.gms.common.util.Clock r0 = r22.zzax()     // Catch:{ all -> 0x053c }
            long r4 = r0.currentTimeMillis()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzag r0 = r22.zzg()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzeg r8 = com.google.android.gms.measurement.internal.zzeh.zzR     // Catch:{ all -> 0x053c }
            r9 = 0
            int r0 = r0.zze(r9, r8)     // Catch:{ all -> 0x053c }
            r22.zzg()     // Catch:{ all -> 0x053c }
            long r10 = com.google.android.gms.measurement.internal.zzag.zzz()     // Catch:{ all -> 0x053c }
            long r10 = r4 - r10
            r8 = r3
        L_0x00ae:
            if (r8 >= r0) goto L_0x00b9
            boolean r12 = r1.zzah(r9, r10)     // Catch:{ all -> 0x053c }
            if (r12 == 0) goto L_0x00b9
            int r8 = r8 + 1
            goto L_0x00ae
        L_0x00b9:
            com.google.android.gms.measurement.internal.zzka r0 = r1.zzk     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzff r0 = r0.zzc     // Catch:{ all -> 0x053c }
            long r10 = r0.zza()     // Catch:{ all -> 0x053c }
            int r0 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r0 == 0) goto L_0x00dc
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzc()     // Catch:{ all -> 0x053c }
            java.lang.String r6 = "Uploading events. Elapsed time since last upload attempt (ms)"
            long r7 = r4 - r10
            long r7 = java.lang.Math.abs(r7)     // Catch:{ all -> 0x053c }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x053c }
            r0.zzb(r6, r7)     // Catch:{ all -> 0x053c }
        L_0x00dc:
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x053c }
            zzal(r0)     // Catch:{ all -> 0x053c }
            java.lang.String r6 = r0.zzr()     // Catch:{ all -> 0x053c }
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x053c }
            r7 = -1
            if (r0 != 0) goto L_0x04b2
            long r10 = r1.zzA     // Catch:{ all -> 0x053c }
            int r0 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r0 != 0) goto L_0x0139
            com.google.android.gms.measurement.internal.zzam r10 = r1.zze     // Catch:{ all -> 0x053c }
            zzal(r10)     // Catch:{ all -> 0x053c }
            android.database.sqlite.SQLiteDatabase r0 = r10.zzh()     // Catch:{ SQLiteException -> 0x0119, all -> 0x0117 }
            java.lang.String r11 = "select rowid from raw_events order by rowid desc limit 1;"
            android.database.Cursor r11 = r0.rawQuery(r11, r9)     // Catch:{ SQLiteException -> 0x0119, all -> 0x0117 }
            boolean r0 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x0115, all -> 0x0112 }
            if (r0 != 0) goto L_0x010c
        L_0x0108:
            r11.close()     // Catch:{ all -> 0x053c }
            goto L_0x012e
        L_0x010c:
            long r7 = r11.getLong(r3)     // Catch:{ SQLiteException -> 0x0115, all -> 0x0112 }
            goto L_0x0108
        L_0x0112:
            r0 = move-exception
            r9 = r11
            goto L_0x0133
        L_0x0115:
            r0 = move-exception
            goto L_0x011b
        L_0x0117:
            r0 = move-exception
            goto L_0x0133
        L_0x0119:
            r0 = move-exception
            r11 = r9
        L_0x011b:
            com.google.android.gms.measurement.internal.zzge r10 = r10.zzt     // Catch:{ all -> 0x0131 }
            com.google.android.gms.measurement.internal.zzeu r10 = r10.zzaA()     // Catch:{ all -> 0x0131 }
            com.google.android.gms.measurement.internal.zzes r10 = r10.zzd()     // Catch:{ all -> 0x0131 }
            java.lang.String r12 = "Error querying raw events"
            r10.zzb(r12, r0)     // Catch:{ all -> 0x0131 }
            if (r11 == 0) goto L_0x012d
            goto L_0x0108
        L_0x012d:
        L_0x012e:
            r1.zzA = r7     // Catch:{ all -> 0x053c }
            goto L_0x0139
        L_0x0131:
            r0 = move-exception
            r9 = r11
        L_0x0133:
            if (r9 == 0) goto L_0x0138
            r9.close()     // Catch:{ all -> 0x053c }
        L_0x0138:
            throw r0     // Catch:{ all -> 0x053c }
        L_0x0139:
            com.google.android.gms.measurement.internal.zzag r0 = r22.zzg()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzeg r7 = com.google.android.gms.measurement.internal.zzeh.zzg     // Catch:{ all -> 0x053c }
            int r0 = r0.zze(r6, r7)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzag r7 = r22.zzg()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzeg r8 = com.google.android.gms.measurement.internal.zzeh.zzh     // Catch:{ all -> 0x053c }
            int r7 = r7.zze(r6, r8)     // Catch:{ all -> 0x053c }
            int r7 = java.lang.Math.max(r3, r7)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzam r8 = r1.zze     // Catch:{ all -> 0x053c }
            zzal(r8)     // Catch:{ all -> 0x053c }
            r8.zzg()     // Catch:{ all -> 0x053c }
            r8.zzW()     // Catch:{ all -> 0x053c }
            if (r0 <= 0) goto L_0x0160
            r10 = r2
            goto L_0x0161
        L_0x0160:
            r10 = r3
        L_0x0161:
            com.google.android.gms.common.internal.Preconditions.checkArgument(r10)     // Catch:{ all -> 0x053c }
            if (r7 <= 0) goto L_0x0168
            r10 = r2
            goto L_0x0169
        L_0x0168:
            r10 = r3
        L_0x0169:
            com.google.android.gms.common.internal.Preconditions.checkArgument(r10)     // Catch:{ all -> 0x053c }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r6)     // Catch:{ all -> 0x053c }
            android.database.sqlite.SQLiteDatabase r11 = r8.zzh()     // Catch:{ SQLiteException -> 0x0285, all -> 0x0281 }
            java.lang.String r12 = "queue"
            java.lang.String r13 = "rowid"
            java.lang.String r14 = "data"
            java.lang.String r15 = "retry_count"
            java.lang.String[] r13 = new java.lang.String[]{r13, r14, r15}     // Catch:{ SQLiteException -> 0x0285, all -> 0x0281 }
            java.lang.String r14 = "app_id=?"
            java.lang.String[] r15 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0285, all -> 0x0281 }
            r15[r3] = r6     // Catch:{ SQLiteException -> 0x0285, all -> 0x0281 }
            r16 = 0
            r17 = 0
            java.lang.String r18 = "rowid"
            java.lang.String r19 = java.lang.String.valueOf(r0)     // Catch:{ SQLiteException -> 0x0285, all -> 0x0281 }
            android.database.Cursor r11 = r11.query(r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ SQLiteException -> 0x0285, all -> 0x0281 }
            boolean r0 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x027d, all -> 0x0279 }
            if (r0 != 0) goto L_0x01a4
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ SQLiteException -> 0x027d, all -> 0x0279 }
            r11.close()     // Catch:{ all -> 0x053c }
            r20 = r4
            goto L_0x02a5
        L_0x01a4:
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x027d, all -> 0x0279 }
            r12.<init>()     // Catch:{ SQLiteException -> 0x027d, all -> 0x0279 }
            r13 = r3
        L_0x01aa:
            long r14 = r11.getLong(r3)     // Catch:{ SQLiteException -> 0x027d, all -> 0x0279 }
            byte[] r0 = r11.getBlob(r2)     // Catch:{ IOException -> 0x024b }
            com.google.android.gms.measurement.internal.zzlg r9 = r8.zzf     // Catch:{ IOException -> 0x024b }
            com.google.android.gms.measurement.internal.zzli r9 = r9.zzi     // Catch:{ IOException -> 0x024b }
            zzal(r9)     // Catch:{ IOException -> 0x024b }
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0236 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0236 }
            java.util.zip.GZIPInputStream r0 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x0236 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x0236 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0236 }
            r3.<init>()     // Catch:{ IOException -> 0x0236 }
            r10 = 1024(0x400, float:1.435E-42)
            byte[] r10 = new byte[r10]     // Catch:{ IOException -> 0x0236 }
        L_0x01cd:
            r20 = r4
            int r4 = r0.read(r10)     // Catch:{ IOException -> 0x0234 }
            if (r4 > 0) goto L_0x022c
            r0.close()     // Catch:{ IOException -> 0x0234 }
            r2.close()     // Catch:{ IOException -> 0x0234 }
            byte[] r0 = r3.toByteArray()     // Catch:{ IOException -> 0x0234 }
            boolean r2 = r12.isEmpty()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            if (r2 != 0) goto L_0x01eb
            int r2 = r0.length     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            int r2 = r2 + r13
            if (r2 <= r7) goto L_0x01eb
            goto L_0x0271
        L_0x01eb:
            com.google.android.gms.internal.measurement.zzgc r2 = com.google.android.gms.internal.measurement.zzgd.zzu()     // Catch:{ IOException -> 0x0217 }
            com.google.android.gms.internal.measurement.zzmh r2 = com.google.android.gms.measurement.internal.zzli.zzl(r2, r0)     // Catch:{ IOException -> 0x0217 }
            com.google.android.gms.internal.measurement.zzgc r2 = (com.google.android.gms.internal.measurement.zzgc) r2     // Catch:{ IOException -> 0x0217 }
            r3 = 2
            boolean r4 = r11.isNull(r3)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            if (r4 != 0) goto L_0x0203
            int r4 = r11.getInt(r3)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            r2.zzaf(r4)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
        L_0x0203:
            int r0 = r0.length     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            int r13 = r13 + r0
            com.google.android.gms.internal.measurement.zzlb r0 = r2.zzaD()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            com.google.android.gms.internal.measurement.zzgd r0 = (com.google.android.gms.internal.measurement.zzgd) r0     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            java.lang.Long r2 = java.lang.Long.valueOf(r14)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            android.util.Pair r0 = android.util.Pair.create(r0, r2)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            r12.add(r0)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            goto L_0x0261
        L_0x0217:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r2 = r8.zzt     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            java.lang.String r3 = "Failed to merge queued bundle. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            r2.zzc(r3, r4, r0)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            goto L_0x0261
        L_0x022c:
            r5 = 0
            r3.write(r10, r5, r4)     // Catch:{ IOException -> 0x0234 }
            r4 = r20
            goto L_0x01cd
        L_0x0234:
            r0 = move-exception
            goto L_0x0239
        L_0x0236:
            r0 = move-exception
            r20 = r4
        L_0x0239:
            com.google.android.gms.measurement.internal.zzge r2 = r9.zzt     // Catch:{ IOException -> 0x0249 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ IOException -> 0x0249 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ IOException -> 0x0249 }
            java.lang.String r3 = "Failed to ungzip content"
            r2.zzb(r3, r0)     // Catch:{ IOException -> 0x0249 }
            throw r0     // Catch:{ IOException -> 0x0249 }
        L_0x0249:
            r0 = move-exception
            goto L_0x024e
        L_0x024b:
            r0 = move-exception
            r20 = r4
        L_0x024e:
            com.google.android.gms.measurement.internal.zzge r2 = r8.zzt     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            java.lang.String r3 = "Failed to unzip queued bundle. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            r2.zzc(r3, r4, r0)     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
        L_0x0261:
            boolean r0 = r11.moveToNext()     // Catch:{ SQLiteException -> 0x0277, all -> 0x0279 }
            if (r0 == 0) goto L_0x0271
            if (r13 <= r7) goto L_0x026a
            goto L_0x0271
        L_0x026a:
            r4 = r20
            r2 = 1
            r3 = 0
            r9 = 0
            goto L_0x01aa
        L_0x0271:
            r11.close()     // Catch:{ all -> 0x053c }
            r0 = r12
            goto L_0x02a5
        L_0x0277:
            r0 = move-exception
            goto L_0x0289
        L_0x0279:
            r0 = move-exception
            r9 = r11
            goto L_0x04ac
        L_0x027d:
            r0 = move-exception
            r20 = r4
            goto L_0x0289
        L_0x0281:
            r0 = move-exception
            r9 = 0
            goto L_0x04ac
        L_0x0285:
            r0 = move-exception
            r20 = r4
            r11 = 0
        L_0x0289:
            com.google.android.gms.measurement.internal.zzge r2 = r8.zzt     // Catch:{ all -> 0x04aa }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x04aa }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x04aa }
            java.lang.String r3 = "Error querying bundles. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)     // Catch:{ all -> 0x04aa }
            r2.zzc(r3, r4, r0)     // Catch:{ all -> 0x04aa }
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ all -> 0x04aa }
            if (r11 == 0) goto L_0x02a5
            r11.close()     // Catch:{ all -> 0x053c }
        L_0x02a5:
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x053c }
            if (r2 != 0) goto L_0x052f
            com.google.android.gms.measurement.internal.zzai r2 = r1.zzh(r6)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzah r3 = com.google.android.gms.measurement.internal.zzah.AD_STORAGE     // Catch:{ all -> 0x053c }
            boolean r2 = r2.zzi(r3)     // Catch:{ all -> 0x053c }
            if (r2 == 0) goto L_0x030c
            java.util.Iterator r2 = r0.iterator()     // Catch:{ all -> 0x053c }
        L_0x02bb:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x053c }
            if (r3 == 0) goto L_0x02da
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x053c }
            android.util.Pair r3 = (android.util.Pair) r3     // Catch:{ all -> 0x053c }
            java.lang.Object r3 = r3.first     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgd r3 = (com.google.android.gms.internal.measurement.zzgd) r3     // Catch:{ all -> 0x053c }
            java.lang.String r4 = r3.zzK()     // Catch:{ all -> 0x053c }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x053c }
            if (r4 != 0) goto L_0x02bb
            java.lang.String r2 = r3.zzK()     // Catch:{ all -> 0x053c }
            goto L_0x02db
        L_0x02da:
            r2 = 0
        L_0x02db:
            if (r2 == 0) goto L_0x030c
            r3 = 0
        L_0x02de:
            int r4 = r0.size()     // Catch:{ all -> 0x053c }
            if (r3 >= r4) goto L_0x030c
            java.lang.Object r4 = r0.get(r3)     // Catch:{ all -> 0x053c }
            android.util.Pair r4 = (android.util.Pair) r4     // Catch:{ all -> 0x053c }
            java.lang.Object r4 = r4.first     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgd r4 = (com.google.android.gms.internal.measurement.zzgd) r4     // Catch:{ all -> 0x053c }
            java.lang.String r5 = r4.zzK()     // Catch:{ all -> 0x053c }
            boolean r5 = r5.isEmpty()     // Catch:{ all -> 0x053c }
            if (r5 == 0) goto L_0x02f9
            goto L_0x0309
        L_0x02f9:
            java.lang.String r4 = r4.zzK()     // Catch:{ all -> 0x053c }
            boolean r4 = r4.equals(r2)     // Catch:{ all -> 0x053c }
            if (r4 != 0) goto L_0x0309
            r2 = 0
            java.util.List r0 = r0.subList(r2, r3)     // Catch:{ all -> 0x053c }
            goto L_0x030c
        L_0x0309:
            int r3 = r3 + 1
            goto L_0x02de
        L_0x030c:
            com.google.android.gms.internal.measurement.zzga r2 = com.google.android.gms.internal.measurement.zzgb.zza()     // Catch:{ all -> 0x053c }
            int r3 = r0.size()     // Catch:{ all -> 0x053c }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x053c }
            int r5 = r0.size()     // Catch:{ all -> 0x053c }
            r4.<init>(r5)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzag r5 = r22.zzg()     // Catch:{ all -> 0x053c }
            boolean r5 = r5.zzt(r6)     // Catch:{ all -> 0x053c }
            if (r5 == 0) goto L_0x0335
            com.google.android.gms.measurement.internal.zzai r5 = r1.zzh(r6)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzah r7 = com.google.android.gms.measurement.internal.zzah.AD_STORAGE     // Catch:{ all -> 0x053c }
            boolean r5 = r5.zzi(r7)     // Catch:{ all -> 0x053c }
            if (r5 == 0) goto L_0x0335
            r5 = 1
            goto L_0x0336
        L_0x0335:
            r5 = 0
        L_0x0336:
            com.google.android.gms.measurement.internal.zzai r7 = r1.zzh(r6)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzah r8 = com.google.android.gms.measurement.internal.zzah.AD_STORAGE     // Catch:{ all -> 0x053c }
            boolean r7 = r7.zzi(r8)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzai r8 = r1.zzh(r6)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzah r9 = com.google.android.gms.measurement.internal.zzah.ANALYTICS_STORAGE     // Catch:{ all -> 0x053c }
            boolean r8 = r8.zzi(r9)     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzqr.zzc()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzag r9 = r22.zzg()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzeg r10 = com.google.android.gms.measurement.internal.zzeh.zzao     // Catch:{ all -> 0x053c }
            boolean r9 = r9.zzs(r6, r10)     // Catch:{ all -> 0x053c }
            r10 = 0
        L_0x0358:
            if (r10 >= r3) goto L_0x03d6
            java.lang.Object r11 = r0.get(r10)     // Catch:{ all -> 0x053c }
            android.util.Pair r11 = (android.util.Pair) r11     // Catch:{ all -> 0x053c }
            java.lang.Object r11 = r11.first     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgd r11 = (com.google.android.gms.internal.measurement.zzgd) r11     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzkx r11 = r11.zzbB()     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgc r11 = (com.google.android.gms.internal.measurement.zzgc) r11     // Catch:{ all -> 0x053c }
            java.lang.Object r12 = r0.get(r10)     // Catch:{ all -> 0x053c }
            android.util.Pair r12 = (android.util.Pair) r12     // Catch:{ all -> 0x053c }
            java.lang.Object r12 = r12.second     // Catch:{ all -> 0x053c }
            java.lang.Long r12 = (java.lang.Long) r12     // Catch:{ all -> 0x053c }
            r4.add(r12)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzag r12 = r22.zzg()     // Catch:{ all -> 0x053c }
            r12.zzh()     // Catch:{ all -> 0x053c }
            r12 = 77000(0x12cc8, double:3.8043E-319)
            r11.zzam(r12)     // Catch:{ all -> 0x053c }
            r12 = r20
            r11.zzal(r12)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzge r14 = r1.zzn     // Catch:{ all -> 0x053c }
            r14.zzay()     // Catch:{ all -> 0x053c }
            r14 = 0
            r11.zzag(r14)     // Catch:{ all -> 0x053c }
            if (r5 != 0) goto L_0x0397
            r11.zzq()     // Catch:{ all -> 0x053c }
        L_0x0397:
            if (r7 != 0) goto L_0x039f
            r11.zzx()     // Catch:{ all -> 0x053c }
            r11.zzt()     // Catch:{ all -> 0x053c }
        L_0x039f:
            if (r8 != 0) goto L_0x03a4
            r11.zzn()     // Catch:{ all -> 0x053c }
        L_0x03a4:
            r1.zzC(r6, r11)     // Catch:{ all -> 0x053c }
            if (r9 != 0) goto L_0x03ac
            r11.zzy()     // Catch:{ all -> 0x053c }
        L_0x03ac:
            com.google.android.gms.measurement.internal.zzag r14 = r22.zzg()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzeg r15 = com.google.android.gms.measurement.internal.zzeh.zzV     // Catch:{ all -> 0x053c }
            boolean r14 = r14.zzs(r6, r15)     // Catch:{ all -> 0x053c }
            if (r14 == 0) goto L_0x03ce
            com.google.android.gms.internal.measurement.zzlb r14 = r11.zzaD()     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgd r14 = (com.google.android.gms.internal.measurement.zzgd) r14     // Catch:{ all -> 0x053c }
            byte[] r14 = r14.zzbx()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzli r15 = r1.zzi     // Catch:{ all -> 0x053c }
            zzal(r15)     // Catch:{ all -> 0x053c }
            long r14 = r15.zzd(r14)     // Catch:{ all -> 0x053c }
            r11.zzJ(r14)     // Catch:{ all -> 0x053c }
        L_0x03ce:
            r2.zza(r11)     // Catch:{ all -> 0x053c }
            int r10 = r10 + 1
            r20 = r12
            goto L_0x0358
        L_0x03d6:
            r12 = r20
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            java.lang.String r0 = r0.zzr()     // Catch:{ all -> 0x053c }
            r5 = 2
            boolean r0 = android.util.Log.isLoggable(r0, r5)     // Catch:{ all -> 0x053c }
            if (r0 == 0) goto L_0x03f7
            com.google.android.gms.measurement.internal.zzli r0 = r1.zzi     // Catch:{ all -> 0x053c }
            zzal(r0)     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzlb r5 = r2.zzaD()     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgb r5 = (com.google.android.gms.internal.measurement.zzgb) r5     // Catch:{ all -> 0x053c }
            java.lang.String r9 = r0.zzm(r5)     // Catch:{ all -> 0x053c }
            goto L_0x03f8
        L_0x03f7:
            r9 = 0
        L_0x03f8:
            com.google.android.gms.measurement.internal.zzli r0 = r1.zzi     // Catch:{ all -> 0x053c }
            zzal(r0)     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzlb r0 = r2.zzaD()     // Catch:{ all -> 0x053c }
            com.google.android.gms.internal.measurement.zzgb r0 = (com.google.android.gms.internal.measurement.zzgb) r0     // Catch:{ all -> 0x053c }
            byte[] r14 = r0.zzbx()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzkv r0 = r1.zzl     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzku r5 = r0.zza(r6)     // Catch:{ all -> 0x053c }
            boolean r0 = r4.isEmpty()     // Catch:{ MalformedURLException -> 0x0492 }
            r7 = 1
            r0 = r0 ^ r7
            com.google.android.gms.common.internal.Preconditions.checkArgument(r0)     // Catch:{ MalformedURLException -> 0x0492 }
            java.util.List r0 = r1.zzy     // Catch:{ MalformedURLException -> 0x0492 }
            if (r0 == 0) goto L_0x0428
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ MalformedURLException -> 0x0492 }
            java.lang.String r4 = "Set uploading progress before finishing the previous upload"
            r0.zza(r4)     // Catch:{ MalformedURLException -> 0x0492 }
            goto L_0x042f
        L_0x0428:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ MalformedURLException -> 0x0492 }
            r0.<init>(r4)     // Catch:{ MalformedURLException -> 0x0492 }
            r1.zzy = r0     // Catch:{ MalformedURLException -> 0x0492 }
        L_0x042f:
            com.google.android.gms.measurement.internal.zzka r0 = r1.zzk     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzff r0 = r0.zzd     // Catch:{ MalformedURLException -> 0x0492 }
            r0.zzb(r12)     // Catch:{ MalformedURLException -> 0x0492 }
            java.lang.String r0 = "?"
            if (r3 <= 0) goto L_0x0443
            r3 = 0
            com.google.android.gms.internal.measurement.zzgd r0 = r2.zzb(r3)     // Catch:{ MalformedURLException -> 0x0492 }
            java.lang.String r0 = r0.zzy()     // Catch:{ MalformedURLException -> 0x0492 }
        L_0x0443:
            com.google.android.gms.measurement.internal.zzeu r2 = r22.zzaA()     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzj()     // Catch:{ MalformedURLException -> 0x0492 }
            java.lang.String r3 = "Uploading data. app, uncompressed size, data"
            int r4 = r14.length     // Catch:{ MalformedURLException -> 0x0492 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ MalformedURLException -> 0x0492 }
            r2.zzd(r3, r0, r4, r9)     // Catch:{ MalformedURLException -> 0x0492 }
            r2 = 1
            r1.zzu = r2     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzfa r11 = r1.zzd     // Catch:{ MalformedURLException -> 0x0492 }
            zzal(r11)     // Catch:{ MalformedURLException -> 0x0492 }
            java.net.URL r13 = new java.net.URL     // Catch:{ MalformedURLException -> 0x0492 }
            java.lang.String r0 = r5.zza()     // Catch:{ MalformedURLException -> 0x0492 }
            r13.<init>(r0)     // Catch:{ MalformedURLException -> 0x0492 }
            java.util.Map r15 = r5.zzb()     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzkx r0 = new com.google.android.gms.measurement.internal.zzkx     // Catch:{ MalformedURLException -> 0x0492 }
            r0.<init>(r1, r6)     // Catch:{ MalformedURLException -> 0x0492 }
            r11.zzg()     // Catch:{ MalformedURLException -> 0x0492 }
            r11.zzW()     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r13)     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r14)     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzgb r2 = r2.zzaB()     // Catch:{ MalformedURLException -> 0x0492 }
            com.google.android.gms.measurement.internal.zzez r3 = new com.google.android.gms.measurement.internal.zzez     // Catch:{ MalformedURLException -> 0x0492 }
            r10 = r3
            r12 = r6
            r16 = r0
            r10.<init>(r11, r12, r13, r14, r15, r16)     // Catch:{ MalformedURLException -> 0x0492 }
            r2.zzo(r3)     // Catch:{ MalformedURLException -> 0x0492 }
            goto L_0x052f
        L_0x0492:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzeu r0 = r22.zzaA()     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x053c }
            java.lang.String r2 = "Failed to parse upload URL. Not uploading. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)     // Catch:{ all -> 0x053c }
            java.lang.String r4 = r5.zza()     // Catch:{ all -> 0x053c }
            r0.zzc(r2, r3, r4)     // Catch:{ all -> 0x053c }
            goto L_0x052f
        L_0x04aa:
            r0 = move-exception
            r9 = r11
        L_0x04ac:
            if (r9 == 0) goto L_0x04b1
            r9.close()     // Catch:{ all -> 0x053c }
        L_0x04b1:
            throw r0     // Catch:{ all -> 0x053c }
        L_0x04b2:
            r12 = r4
            r1.zzA = r7     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x053c }
            zzal(r2)     // Catch:{ all -> 0x053c }
            r22.zzg()     // Catch:{ all -> 0x053c }
            long r3 = com.google.android.gms.measurement.internal.zzag.zzz()     // Catch:{ all -> 0x053c }
            long r4 = r12 - r3
            r2.zzg()     // Catch:{ all -> 0x053c }
            r2.zzW()     // Catch:{ all -> 0x053c }
            android.database.sqlite.SQLiteDatabase r0 = r2.zzh()     // Catch:{ SQLiteException -> 0x0502, all -> 0x04ff }
            java.lang.String r3 = "select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;"
            r6 = 1
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x0502, all -> 0x04ff }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ SQLiteException -> 0x0502, all -> 0x04ff }
            r5 = 0
            r6[r5] = r4     // Catch:{ SQLiteException -> 0x0502, all -> 0x04ff }
            android.database.Cursor r3 = r0.rawQuery(r3, r6)     // Catch:{ SQLiteException -> 0x0502, all -> 0x04ff }
            boolean r0 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x04fd }
            if (r0 != 0) goto L_0x04f3
            com.google.android.gms.measurement.internal.zzge r0 = r2.zzt     // Catch:{ SQLiteException -> 0x04fd }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x04fd }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()     // Catch:{ SQLiteException -> 0x04fd }
            java.lang.String r4 = "No expired configs for apps with pending events"
            r0.zza(r4)     // Catch:{ SQLiteException -> 0x04fd }
            goto L_0x0515
        L_0x04f3:
            r4 = 0
            java.lang.String r9 = r3.getString(r4)     // Catch:{ SQLiteException -> 0x04fd }
            r3.close()     // Catch:{ all -> 0x053c }
            goto L_0x051b
        L_0x04fd:
            r0 = move-exception
            goto L_0x0504
        L_0x04ff:
            r0 = move-exception
            r9 = 0
            goto L_0x0536
        L_0x0502:
            r0 = move-exception
            r3 = 0
        L_0x0504:
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt     // Catch:{ all -> 0x0534 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0534 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0534 }
            java.lang.String r4 = "Error selecting expired configs"
            r2.zzb(r4, r0)     // Catch:{ all -> 0x0534 }
            if (r3 == 0) goto L_0x051a
        L_0x0515:
            r3.close()     // Catch:{ all -> 0x053c }
            r9 = 0
            goto L_0x051b
        L_0x051a:
            r9 = 0
        L_0x051b:
            boolean r0 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x053c }
            if (r0 != 0) goto L_0x052f
            com.google.android.gms.measurement.internal.zzam r0 = r1.zze     // Catch:{ all -> 0x053c }
            zzal(r0)     // Catch:{ all -> 0x053c }
            com.google.android.gms.measurement.internal.zzh r0 = r0.zzj(r9)     // Catch:{ all -> 0x053c }
            if (r0 == 0) goto L_0x052f
            r1.zzD(r0)     // Catch:{ all -> 0x053c }
        L_0x052f:
            r2 = 0
            r1.zzv = r2
            goto L_0x0030
        L_0x0534:
            r0 = move-exception
            r9 = r3
        L_0x0536:
            if (r9 == 0) goto L_0x053b
            r9.close()     // Catch:{ all -> 0x053c }
        L_0x053b:
            throw r0     // Catch:{ all -> 0x053c }
        L_0x053c:
            r0 = move-exception
            r2 = 0
            r1.zzv = r2
            r22.zzae()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzlg.zzX():void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x07e3, code lost:
        if (r14.isEmpty() != false) goto L_0x07e5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x034f A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0388 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x03a2 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x03a5 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0407 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0430  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x058d A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x05cb A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x063f A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x068a A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0697 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x06a4 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x06dc A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x06ed A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x072e A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:227:0x0755 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:228:0x075a A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x0760 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x07e8 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:263:0x082e A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x087d A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x088a A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x08a3 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:285:0x0930 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:289:0x0950 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:299:0x09e2 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x0a94 A[Catch:{ SQLiteException -> 0x0aaf }] */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x0aaa  */
    /* JADX WARNING: Removed duplicated region for block: B:350:0x09f4 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01d6 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01f0 A[SYNTHETIC, Splitter:B:56:0x01f0] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0254 A[SYNTHETIC, Splitter:B:71:0x0254] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0264 A[Catch:{ NumberFormatException -> 0x07cd, all -> 0x0b2b }] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzY(com.google.android.gms.measurement.internal.zzaw r36, com.google.android.gms.measurement.internal.zzq r37) {
        /*
            r35 = this;
            r1 = r35
            r2 = r36
            r3 = r37
            java.lang.String r4 = "metadata_fingerprint"
            java.lang.String r5 = "app_id"
            java.lang.String r6 = "raw_events"
            java.lang.String r7 = "_sno"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r37)
            java.lang.String r8 = r3.zza
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)
            long r8 = java.lang.System.nanoTime()
            com.google.android.gms.measurement.internal.zzgb r10 = r35.zzaB()
            r10.zzg()
            r35.zzB()
            java.lang.String r10 = r3.zza
            com.google.android.gms.measurement.internal.zzli r11 = r1.zzi
            zzal(r11)
            boolean r11 = com.google.android.gms.measurement.internal.zzli.zzA(r36, r37)
            if (r11 != 0) goto L_0x0032
            return
        L_0x0032:
            boolean r11 = r3.zzh
            if (r11 == 0) goto L_0x0b36
            com.google.android.gms.measurement.internal.zzfv r11 = r1.zzc
            zzal(r11)
            java.lang.String r12 = r2.zza
            boolean r11 = r11.zzr(r10, r12)
            java.lang.String r15 = "_err"
            r14 = 0
            if (r11 == 0) goto L_0x00df
            com.google.android.gms.measurement.internal.zzeu r3 = r35.zzaA()
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzk()
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzn
            com.google.android.gms.measurement.internal.zzep r5 = r5.zzj()
            java.lang.String r6 = r2.zza
            java.lang.String r5 = r5.zzd(r6)
            java.lang.String r6 = "Dropping blocked event. appId"
            r3.zzc(r6, r4, r5)
            com.google.android.gms.measurement.internal.zzfv r3 = r1.zzc
            zzal(r3)
            boolean r3 = r3.zzp(r10)
            if (r3 != 0) goto L_0x0097
            com.google.android.gms.measurement.internal.zzfv r3 = r1.zzc
            zzal(r3)
            boolean r3 = r3.zzs(r10)
            if (r3 == 0) goto L_0x007a
            goto L_0x0097
        L_0x007a:
            java.lang.String r3 = r2.zza
            boolean r3 = r15.equals(r3)
            if (r3 != 0) goto L_0x00de
            com.google.android.gms.measurement.internal.zzlo r11 = r35.zzv()
            com.google.android.gms.measurement.internal.zzln r12 = r1.zzF
            java.lang.String r2 = r2.zza
            r14 = 11
            r17 = 0
            java.lang.String r15 = "_ev"
            r13 = r10
            r16 = r2
            r11.zzO(r12, r13, r14, r15, r16, r17)
            return
        L_0x0097:
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            zzal(r2)
            com.google.android.gms.measurement.internal.zzh r2 = r2.zzj(r10)
            if (r2 == 0) goto L_0x00de
            long r3 = r2.zzl()
            long r5 = r2.zzc()
            long r3 = java.lang.Math.max(r3, r5)
            com.google.android.gms.common.util.Clock r5 = r35.zzax()
            long r5 = r5.currentTimeMillis()
            long r5 = r5 - r3
            long r3 = java.lang.Math.abs(r5)
            r35.zzg()
            com.google.android.gms.measurement.internal.zzeg r5 = com.google.android.gms.measurement.internal.zzeh.zzz
            java.lang.Object r5 = r5.zza(r14)
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x00de
            com.google.android.gms.measurement.internal.zzeu r3 = r35.zzaA()
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzc()
            java.lang.String r4 = "Fetching config for blocked app"
            r3.zza(r4)
            r1.zzD(r2)
        L_0x00de:
            return
        L_0x00df:
            com.google.android.gms.measurement.internal.zzev r2 = com.google.android.gms.measurement.internal.zzev.zzb(r36)
            com.google.android.gms.measurement.internal.zzlo r11 = r35.zzv()
            com.google.android.gms.measurement.internal.zzag r12 = r35.zzg()
            int r12 = r12.zzd(r10)
            r11.zzN(r2, r12)
            com.google.android.gms.internal.measurement.zzpn.zzc()
            com.google.android.gms.measurement.internal.zzag r11 = r35.zzg()
            com.google.android.gms.measurement.internal.zzeg r12 = com.google.android.gms.measurement.internal.zzeh.zzaA
            boolean r11 = r11.zzs(r14, r12)
            if (r11 == 0) goto L_0x0110
            com.google.android.gms.measurement.internal.zzag r11 = r35.zzg()
            com.google.android.gms.measurement.internal.zzeg r12 = com.google.android.gms.measurement.internal.zzeh.zzQ
            r13 = 10
            r14 = 35
            int r11 = r11.zzf(r10, r12, r13, r14)
            goto L_0x0111
        L_0x0110:
            r11 = 0
        L_0x0111:
            java.util.TreeSet r12 = new java.util.TreeSet
            android.os.Bundle r13 = r2.zzd
            java.util.Set r13 = r13.keySet()
            r12.<init>(r13)
            java.util.Iterator r12 = r12.iterator()
        L_0x0120:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0163
            java.lang.Object r13 = r12.next()
            java.lang.String r13 = (java.lang.String) r13
            java.lang.String r14 = "items"
            boolean r14 = r14.equals(r13)
            if (r14 == 0) goto L_0x015c
            com.google.android.gms.measurement.internal.zzlo r14 = r35.zzv()
            r17 = r12
            android.os.Bundle r12 = r2.zzd
            android.os.Parcelable[] r12 = r12.getParcelableArray(r13)
            com.google.android.gms.internal.measurement.zzpn.zzc()
            com.google.android.gms.measurement.internal.zzag r13 = r35.zzg()
            r18 = r15
            com.google.android.gms.measurement.internal.zzeg r15 = com.google.android.gms.measurement.internal.zzeh.zzaA
            r28 = r8
            r8 = 0
            boolean r9 = r13.zzs(r8, r15)
            r14.zzM(r12, r11, r9)
            r12 = r17
            r15 = r18
            r8 = r28
            goto L_0x0120
        L_0x015c:
            r28 = r8
            r17 = r12
            r18 = r15
            goto L_0x0120
        L_0x0163:
            r28 = r8
            r18 = r15
            com.google.android.gms.measurement.internal.zzaw r2 = r2.zza()
            com.google.android.gms.measurement.internal.zzeu r8 = r35.zzaA()
            java.lang.String r8 = r8.zzr()
            r9 = 2
            boolean r8 = android.util.Log.isLoggable(r8, r9)
            if (r8 == 0) goto L_0x0191
            com.google.android.gms.measurement.internal.zzeu r8 = r35.zzaA()
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzj()
            com.google.android.gms.measurement.internal.zzge r11 = r1.zzn
            com.google.android.gms.measurement.internal.zzep r11 = r11.zzj()
            java.lang.String r11 = r11.zzc(r2)
            java.lang.String r12 = "Logging event"
            r8.zzb(r12, r11)
        L_0x0191:
            com.google.android.gms.internal.measurement.zzpk.zzc()
            com.google.android.gms.measurement.internal.zzag r8 = r35.zzg()
            com.google.android.gms.measurement.internal.zzeg r11 = com.google.android.gms.measurement.internal.zzeh.zzax
            r14 = 0
            r8.zzs(r14, r11)
            com.google.android.gms.measurement.internal.zzam r8 = r1.zze
            zzal(r8)
            r8.zzw()
            r1.zzd(r3)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = "ecommerce_purchase"
            java.lang.String r11 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r8 = r8.equals(r11)     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = "refund"
            if (r8 != 0) goto L_0x01cb
            java.lang.String r8 = "purchase"
            java.lang.String r12 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r8 = r8.equals(r12)     // Catch:{ all -> 0x0b2b }
            if (r8 != 0) goto L_0x01cb
            java.lang.String r8 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r8 = r11.equals(r8)     // Catch:{ all -> 0x0b2b }
            if (r8 == 0) goto L_0x01c9
            r8 = 1
            goto L_0x01cc
        L_0x01c9:
            r8 = 0
            goto L_0x01cc
        L_0x01cb:
            r8 = 1
        L_0x01cc:
            java.lang.String r12 = "_iap"
            java.lang.String r13 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r12 = r12.equals(r13)     // Catch:{ all -> 0x0b2b }
            if (r12 != 0) goto L_0x01e3
            if (r8 == 0) goto L_0x01da
            r8 = 1
            goto L_0x01e3
        L_0x01da:
            r33 = r4
            r34 = r5
            r4 = r18
            r5 = 1
            goto L_0x038f
        L_0x01e3:
            com.google.android.gms.measurement.internal.zzau r12 = r2.zzb     // Catch:{ all -> 0x0b2b }
            java.lang.String r13 = "currency"
            java.lang.String r12 = r12.zzg(r13)     // Catch:{ all -> 0x0b2b }
            java.lang.String r13 = "value"
            if (r8 == 0) goto L_0x0254
            com.google.android.gms.measurement.internal.zzau r8 = r2.zzb     // Catch:{ all -> 0x0b2b }
            java.lang.Double r8 = r8.zzd(r13)     // Catch:{ all -> 0x0b2b }
            double r16 = r8.doubleValue()     // Catch:{ all -> 0x0b2b }
            r19 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r16 = r16 * r19
            r21 = 0
            int r8 = (r16 > r21 ? 1 : (r16 == r21 ? 0 : -1))
            if (r8 != 0) goto L_0x0214
            com.google.android.gms.measurement.internal.zzau r8 = r2.zzb     // Catch:{ all -> 0x0b2b }
            java.lang.Long r8 = r8.zze(r13)     // Catch:{ all -> 0x0b2b }
            long r14 = r8.longValue()     // Catch:{ all -> 0x0b2b }
            double r13 = (double) r14     // Catch:{ all -> 0x0b2b }
            double r16 = r13 * r19
        L_0x0214:
            r13 = 4890909195324358656(0x43e0000000000000, double:9.223372036854776E18)
            int r8 = (r16 > r13 ? 1 : (r16 == r13 ? 0 : -1))
            if (r8 > 0) goto L_0x022e
            r13 = -4332462841530417152(0xc3e0000000000000, double:-9.223372036854776E18)
            int r8 = (r16 > r13 ? 1 : (r16 == r13 ? 0 : -1))
            if (r8 < 0) goto L_0x022e
            long r13 = java.lang.Math.round(r16)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r8 = r11.equals(r8)     // Catch:{ all -> 0x0b2b }
            if (r8 == 0) goto L_0x025e
            long r13 = -r13
            goto L_0x025e
        L_0x022e:
            com.google.android.gms.measurement.internal.zzeu r2 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzk()     // Catch:{ all -> 0x0b2b }
            java.lang.String r3 = "Data lost. Currency value is too big. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.Double r5 = java.lang.Double.valueOf(r16)     // Catch:{ all -> 0x0b2b }
            r2.zzc(r3, r4, r5)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r2)     // Catch:{ all -> 0x0b2b }
            r2.zzC()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
        L_0x024d:
            zzal(r2)
            r2.zzx()
            return
        L_0x0254:
            com.google.android.gms.measurement.internal.zzau r8 = r2.zzb     // Catch:{ all -> 0x0b2b }
            java.lang.Long r8 = r8.zze(r13)     // Catch:{ all -> 0x0b2b }
            long r13 = r8.longValue()     // Catch:{ all -> 0x0b2b }
        L_0x025e:
            boolean r8 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x0b2b }
            if (r8 != 0) goto L_0x0388
            java.util.Locale r8 = java.util.Locale.US     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r12.toUpperCase(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = "[A-Z]{3}"
            boolean r11 = r8.matches(r11)     // Catch:{ all -> 0x0b2b }
            if (r11 == 0) goto L_0x0380
            java.lang.String r11 = "_ltv_"
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r11.concat(r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r11 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r11)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzll r11 = r11.zzp(r10, r8)     // Catch:{ all -> 0x0b2b }
            if (r11 == 0) goto L_0x02c3
            java.lang.Object r11 = r11.zze     // Catch:{ all -> 0x0b2b }
            boolean r12 = r11 instanceof java.lang.Long     // Catch:{ all -> 0x0b2b }
            if (r12 != 0) goto L_0x0291
            r15 = r18
            r9 = 0
            goto L_0x02c6
        L_0x0291:
            java.lang.Long r11 = (java.lang.Long) r11     // Catch:{ all -> 0x0b2b }
            long r11 = r11.longValue()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzll r19 = new com.google.android.gms.measurement.internal.zzll     // Catch:{ all -> 0x0b2b }
            java.lang.String r15 = r2.zzc     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.util.Clock r16 = r35.zzax()     // Catch:{ all -> 0x0b2b }
            long r16 = r16.currentTimeMillis()     // Catch:{ all -> 0x0b2b }
            long r11 = r11 + r13
            java.lang.Long r20 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0b2b }
            r11 = r19
            r12 = r10
            r14 = 0
            r13 = r15
            r9 = r14
            r15 = 0
            r14 = r8
            r8 = r18
            r15 = r16
            r17 = r20
            r11.<init>(r12, r13, r14, r15, r17)     // Catch:{ all -> 0x0b2b }
            r33 = r4
            r34 = r5
            r4 = r8
            r8 = r19
            r5 = 1
            goto L_0x0344
        L_0x02c3:
            r15 = r18
            r9 = 0
        L_0x02c6:
            com.google.android.gms.measurement.internal.zzam r11 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r11)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzag r12 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r9 = com.google.android.gms.measurement.internal.zzeh.zzE     // Catch:{ all -> 0x0b2b }
            int r9 = r12.zze(r10, r9)     // Catch:{ all -> 0x0b2b }
            int r9 = r9 + -1
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)     // Catch:{ all -> 0x0b2b }
            r11.zzg()     // Catch:{ all -> 0x0b2b }
            r11.zzW()     // Catch:{ all -> 0x0b2b }
            android.database.sqlite.SQLiteDatabase r12 = r11.zzh()     // Catch:{ SQLiteException -> 0x030c }
            r18 = r15
            java.lang.String r15 = "delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);"
            r33 = r4
            r4 = 3
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0304 }
            r16 = 0
            r4[r16] = r10     // Catch:{ SQLiteException -> 0x0304 }
            r34 = r5
            r5 = 1
            r4[r5] = r10     // Catch:{ SQLiteException -> 0x0302 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ SQLiteException -> 0x0302 }
            r16 = 2
            r4[r16] = r9     // Catch:{ SQLiteException -> 0x0302 }
            r12.execSQL(r15, r4)     // Catch:{ SQLiteException -> 0x0302 }
            goto L_0x0328
        L_0x0302:
            r0 = move-exception
            goto L_0x0314
        L_0x0304:
            r0 = move-exception
            goto L_0x0309
        L_0x0306:
            r0 = move-exception
            r33 = r4
        L_0x0309:
            r34 = r5
            goto L_0x0313
        L_0x030c:
            r0 = move-exception
            r33 = r4
            r34 = r5
            r18 = r15
        L_0x0313:
            r5 = 1
        L_0x0314:
            r4 = r0
            com.google.android.gms.measurement.internal.zzge r9 = r11.zzt     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeu r9 = r9.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = "Error pruning currencies. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            r9.zzc(r11, r12, r4)     // Catch:{ all -> 0x0b2b }
        L_0x0328:
            com.google.android.gms.measurement.internal.zzll r19 = new com.google.android.gms.measurement.internal.zzll     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = r2.zzc     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.util.Clock r9 = r35.zzax()     // Catch:{ all -> 0x0b2b }
            long r15 = r9.currentTimeMillis()     // Catch:{ all -> 0x0b2b }
            java.lang.Long r17 = java.lang.Long.valueOf(r13)     // Catch:{ all -> 0x0b2b }
            r11 = r19
            r12 = r10
            r13 = r4
            r14 = r8
            r4 = r18
            r11.<init>(r12, r13, r14, r15, r17)     // Catch:{ all -> 0x0b2b }
            r8 = r19
        L_0x0344:
            com.google.android.gms.measurement.internal.zzam r9 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r9)     // Catch:{ all -> 0x0b2b }
            boolean r9 = r9.zzL(r8)     // Catch:{ all -> 0x0b2b }
            if (r9 != 0) goto L_0x038f
            com.google.android.gms.measurement.internal.zzeu r9 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r9 = r9.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = "Too many unique user properties are set. Ignoring user property. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r13 = r1.zzn     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzep r13 = r13.zzj()     // Catch:{ all -> 0x0b2b }
            java.lang.String r14 = r8.zzc     // Catch:{ all -> 0x0b2b }
            java.lang.String r13 = r13.zzf(r14)     // Catch:{ all -> 0x0b2b }
            java.lang.Object r8 = r8.zze     // Catch:{ all -> 0x0b2b }
            r9.zzd(r11, r12, r13, r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlo r11 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzln r12 = r1.zzF     // Catch:{ all -> 0x0b2b }
            r14 = 9
            r15 = 0
            r16 = 0
            r17 = 0
            r13 = r10
            r11.zzO(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x0b2b }
            goto L_0x038f
        L_0x0380:
            r33 = r4
            r34 = r5
            r4 = r18
            r5 = 1
            goto L_0x038f
        L_0x0388:
            r33 = r4
            r34 = r5
            r4 = r18
            r5 = 1
        L_0x038f:
            java.lang.String r8 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r8 = com.google.android.gms.measurement.internal.zzlo.zzak(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r4 = r4.equals(r9)     // Catch:{ all -> 0x0b2b }
            r35.zzv()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzau r9 = r2.zzb     // Catch:{ all -> 0x0b2b }
            if (r9 != 0) goto L_0x03a5
            r12 = 0
            goto L_0x03c7
        L_0x03a5:
            com.google.android.gms.measurement.internal.zzat r11 = new com.google.android.gms.measurement.internal.zzat     // Catch:{ all -> 0x0b2b }
            r11.<init>(r9)     // Catch:{ all -> 0x0b2b }
            r12 = 0
        L_0x03ac:
            boolean r16 = r11.hasNext()     // Catch:{ all -> 0x0b2b }
            if (r16 == 0) goto L_0x03c6
            java.lang.String r14 = r11.next()     // Catch:{ all -> 0x0b2b }
            java.lang.Object r14 = r9.zzf(r14)     // Catch:{ all -> 0x0b2b }
            boolean r15 = r14 instanceof android.os.Parcelable[]     // Catch:{ all -> 0x0b2b }
            if (r15 == 0) goto L_0x03c4
            android.os.Parcelable[] r14 = (android.os.Parcelable[]) r14     // Catch:{ all -> 0x0b2b }
            int r14 = r14.length     // Catch:{ all -> 0x0b2b }
            long r14 = (long) r14     // Catch:{ all -> 0x0b2b }
            long r12 = r12 + r14
            goto L_0x03ac
        L_0x03c4:
            goto L_0x03ac
        L_0x03c6:
        L_0x03c7:
            com.google.android.gms.measurement.internal.zzam r11 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r11)     // Catch:{ all -> 0x0b2b }
            long r14 = r35.zza()     // Catch:{ all -> 0x0b2b }
            r22 = 1
            long r18 = r12 + r22
            r9 = 1
            r20 = 0
            r21 = 0
            r12 = r14
            r30 = r6
            r5 = 0
            r14 = r10
            r15 = r18
            r17 = r9
            r18 = r8
            r19 = r20
            r20 = r4
            com.google.android.gms.measurement.internal.zzak r9 = r11.zzm(r12, r14, r15, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x0b2b }
            long r11 = r9.zzb     // Catch:{ all -> 0x0b2b }
            r35.zzg()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r13 = com.google.android.gms.measurement.internal.zzeh.zzk     // Catch:{ all -> 0x0b2b }
            r15 = 0
            java.lang.Object r13 = r13.zza(r15)     // Catch:{ all -> 0x0b2b }
            java.lang.Integer r13 = (java.lang.Integer) r13     // Catch:{ all -> 0x0b2b }
            int r13 = r13.intValue()     // Catch:{ all -> 0x0b2b }
            long r13 = (long) r13     // Catch:{ all -> 0x0b2b }
            long r11 = r11 - r13
            int r13 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            r16 = 1000(0x3e8, double:4.94E-321)
            if (r13 <= 0) goto L_0x0430
            long r11 = r11 % r16
            int r2 = (r11 > r22 ? 1 : (r11 == r22 ? 0 : -1))
            if (r2 != 0) goto L_0x0424
            com.google.android.gms.measurement.internal.zzeu r2 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r3 = "Data loss. Too many events logged. appId, count"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            long r5 = r9.zzb     // Catch:{ all -> 0x0b2b }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0b2b }
            r2.zzc(r3, r4, r5)     // Catch:{ all -> 0x0b2b }
        L_0x0424:
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r2)     // Catch:{ all -> 0x0b2b }
            r2.zzC()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            goto L_0x024d
        L_0x0430:
            if (r8 == 0) goto L_0x0486
            long r11 = r9.zza     // Catch:{ all -> 0x0b2b }
            r35.zzg()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r13 = com.google.android.gms.measurement.internal.zzeh.zzm     // Catch:{ all -> 0x0b2b }
            java.lang.Object r13 = r13.zza(r15)     // Catch:{ all -> 0x0b2b }
            java.lang.Integer r13 = (java.lang.Integer) r13     // Catch:{ all -> 0x0b2b }
            int r13 = r13.intValue()     // Catch:{ all -> 0x0b2b }
            long r13 = (long) r13     // Catch:{ all -> 0x0b2b }
            long r11 = r11 - r13
            int r13 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r13 <= 0) goto L_0x0486
            long r11 = r11 % r16
            int r3 = (r11 > r22 ? 1 : (r11 == r22 ? 0 : -1))
            if (r3 != 0) goto L_0x0466
            com.google.android.gms.measurement.internal.zzeu r3 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = "Data loss. Too many public events logged. appId, count"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            long r6 = r9.zza     // Catch:{ all -> 0x0b2b }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0b2b }
            r3.zzc(r4, r5, r6)     // Catch:{ all -> 0x0b2b }
        L_0x0466:
            com.google.android.gms.measurement.internal.zzlo r11 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzln r12 = r1.zzF     // Catch:{ all -> 0x0b2b }
            r14 = 16
            java.lang.String r15 = "_ev"
            java.lang.String r2 = r2.zza     // Catch:{ all -> 0x0b2b }
            r17 = 0
            r13 = r10
            r16 = r2
            r11.zzO(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r2)     // Catch:{ all -> 0x0b2b }
            r2.zzC()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            goto L_0x024d
        L_0x0486:
            r11 = 1000000(0xf4240, float:1.401298E-39)
            if (r4 == 0) goto L_0x04cf
            long r12 = r9.zzd     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzag r4 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            java.lang.String r14 = r3.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r15 = com.google.android.gms.measurement.internal.zzeh.zzl     // Catch:{ all -> 0x0b2b }
            int r4 = r4.zze(r14, r15)     // Catch:{ all -> 0x0b2b }
            int r4 = java.lang.Math.min(r11, r4)     // Catch:{ all -> 0x0b2b }
            r14 = 0
            int r4 = java.lang.Math.max(r14, r4)     // Catch:{ all -> 0x0b2b }
            long r14 = (long) r4     // Catch:{ all -> 0x0b2b }
            long r12 = r12 - r14
            int r4 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x04cf
            int r2 = (r12 > r22 ? 1 : (r12 == r22 ? 0 : -1))
            if (r2 != 0) goto L_0x04c3
            com.google.android.gms.measurement.internal.zzeu r2 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r3 = "Too many error events logged. appId, count"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            long r5 = r9.zzd     // Catch:{ all -> 0x0b2b }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0b2b }
            r2.zzc(r3, r4, r5)     // Catch:{ all -> 0x0b2b }
        L_0x04c3:
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r2)     // Catch:{ all -> 0x0b2b }
            r2.zzC()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            goto L_0x024d
        L_0x04cf:
            com.google.android.gms.measurement.internal.zzau r4 = r2.zzb     // Catch:{ all -> 0x0b2b }
            android.os.Bundle r4 = r4.zzc()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlo r9 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            java.lang.String r12 = "_o"
            java.lang.String r13 = r2.zzc     // Catch:{ all -> 0x0b2b }
            r9.zzP(r4, r12, r13)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlo r9 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            boolean r9 = r9.zzaf(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.String r15 = "_r"
            if (r9 == 0) goto L_0x0500
            com.google.android.gms.measurement.internal.zzlo r9 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            java.lang.String r12 = "_dbg"
            java.lang.Long r13 = java.lang.Long.valueOf(r22)     // Catch:{ all -> 0x0b2b }
            r9.zzP(r4, r12, r13)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlo r9 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            r9.zzP(r4, r15, r13)     // Catch:{ all -> 0x0b2b }
        L_0x0500:
            java.lang.String r9 = "_s"
            java.lang.String r12 = r2.zza     // Catch:{ all -> 0x0b2b }
            boolean r9 = r9.equals(r12)     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0526
            com.google.android.gms.measurement.internal.zzam r9 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r9)     // Catch:{ all -> 0x0b2b }
            java.lang.String r12 = r3.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzll r9 = r9.zzp(r12, r7)     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0526
            java.lang.Object r12 = r9.zze     // Catch:{ all -> 0x0b2b }
            boolean r12 = r12 instanceof java.lang.Long     // Catch:{ all -> 0x0b2b }
            if (r12 == 0) goto L_0x0526
            com.google.android.gms.measurement.internal.zzlo r12 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            java.lang.Object r9 = r9.zze     // Catch:{ all -> 0x0b2b }
            r12.zzP(r4, r7, r9)     // Catch:{ all -> 0x0b2b }
        L_0x0526:
            com.google.android.gms.measurement.internal.zzam r7 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r7)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)     // Catch:{ all -> 0x0b2b }
            r7.zzg()     // Catch:{ all -> 0x0b2b }
            r7.zzW()     // Catch:{ all -> 0x0b2b }
            android.database.sqlite.SQLiteDatabase r9 = r7.zzh()     // Catch:{ SQLiteException -> 0x056f }
            com.google.android.gms.measurement.internal.zzge r12 = r7.zzt     // Catch:{ SQLiteException -> 0x056f }
            com.google.android.gms.measurement.internal.zzag r12 = r12.zzf()     // Catch:{ SQLiteException -> 0x056f }
            com.google.android.gms.measurement.internal.zzeg r13 = com.google.android.gms.measurement.internal.zzeh.zzp     // Catch:{ SQLiteException -> 0x056f }
            int r12 = r12.zze(r10, r13)     // Catch:{ SQLiteException -> 0x056f }
            int r11 = java.lang.Math.min(r11, r12)     // Catch:{ SQLiteException -> 0x056f }
            r12 = 0
            int r11 = java.lang.Math.max(r12, r11)     // Catch:{ SQLiteException -> 0x0569 }
            java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ SQLiteException -> 0x056f }
            java.lang.String r12 = "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)"
            r13 = 2
            java.lang.String[] r13 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x056f }
            r31 = 0
            r13[r31] = r10     // Catch:{ SQLiteException -> 0x0567 }
            r14 = 1
            r13[r14] = r11     // Catch:{ SQLiteException -> 0x0567 }
            r14 = r30
            int r7 = r9.delete(r14, r12, r13)     // Catch:{ SQLiteException -> 0x0565 }
            long r11 = (long) r7
            goto L_0x0589
        L_0x0565:
            r0 = move-exception
            goto L_0x0574
        L_0x0567:
            r0 = move-exception
            goto L_0x056c
        L_0x0569:
            r0 = move-exception
            r31 = r12
        L_0x056c:
            r14 = r30
            goto L_0x0574
        L_0x056f:
            r0 = move-exception
            r14 = r30
            r31 = 0
        L_0x0574:
            r9 = r0
            com.google.android.gms.measurement.internal.zzge r7 = r7.zzt     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeu r7 = r7.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r7 = r7.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = "Error deleting over the limit events. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            r7.zzc(r11, r12, r9)     // Catch:{ all -> 0x0b2b }
            r11 = r5
        L_0x0589:
            int r7 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x05a2
            com.google.android.gms.measurement.internal.zzeu r7 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r7 = r7.zzk()     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = "Data lost. Too many events stored on disk, deleted. appId"
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0b2b }
            r7.zzc(r9, r13, r11)     // Catch:{ all -> 0x0b2b }
        L_0x05a2:
            com.google.android.gms.measurement.internal.zzar r7 = new com.google.android.gms.measurement.internal.zzar     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r12 = r1.zzn     // Catch:{ all -> 0x0b2b }
            java.lang.String r13 = r2.zzc     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = r2.zza     // Catch:{ all -> 0x0b2b }
            long r5 = r2.zzd     // Catch:{ all -> 0x0b2b }
            r18 = 0
            r11 = r7
            r2 = r14
            r14 = r10
            r30 = r2
            r32 = r15
            r2 = 0
            r15 = r9
            r16 = r5
            r20 = r4
            r11.<init>((com.google.android.gms.measurement.internal.zzge) r12, (java.lang.String) r13, (java.lang.String) r14, (java.lang.String) r15, (long) r16, (long) r18, (android.os.Bundle) r20)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r4 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r4)     // Catch:{ all -> 0x0b2b }
            java.lang.String r5 = r7.zzb     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzn(r10, r5)     // Catch:{ all -> 0x0b2b }
            if (r4 != 0) goto L_0x063f
            com.google.android.gms.measurement.internal.zzam r4 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r4)     // Catch:{ all -> 0x0b2b }
            long r4 = r4.zzf(r10)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzag r6 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            int r6 = r6.zzb(r10)     // Catch:{ all -> 0x0b2b }
            long r11 = (long) r6     // Catch:{ all -> 0x0b2b }
            int r4 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r4 < 0) goto L_0x0621
            if (r8 == 0) goto L_0x0621
            com.google.android.gms.measurement.internal.zzeu r2 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r3 = "Too many event names used, ignoring event. appId, name, supported count"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r10)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzn     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzep r5 = r5.zzj()     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r7.zzb     // Catch:{ all -> 0x0b2b }
            java.lang.String r5 = r5.zzd(r6)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzag r6 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            int r6 = r6.zzb(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0b2b }
            r2.zzd(r3, r4, r5, r6)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlo r11 = r35.zzv()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzln r12 = r1.zzF     // Catch:{ all -> 0x0b2b }
            r14 = 8
            r15 = 0
            r16 = 0
            r17 = 0
            r13 = r10
            r11.zzO(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            goto L_0x024d
        L_0x0621:
            com.google.android.gms.measurement.internal.zzas r4 = new com.google.android.gms.measurement.internal.zzas     // Catch:{ all -> 0x0b2b }
            java.lang.String r13 = r7.zzb     // Catch:{ all -> 0x0b2b }
            long r5 = r7.zzd     // Catch:{ all -> 0x0b2b }
            r14 = 0
            r16 = 0
            r18 = 0
            r22 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r11 = r4
            r12 = r10
            r20 = r5
            r11.<init>(r12, r13, r14, r16, r18, r20, r22, r24, r25, r26, r27)     // Catch:{ all -> 0x0b2b }
            goto L_0x064d
        L_0x063f:
            com.google.android.gms.measurement.internal.zzge r5 = r1.zzn     // Catch:{ all -> 0x0b2b }
            long r8 = r4.zzf     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzar r7 = r7.zza(r5, r8)     // Catch:{ all -> 0x0b2b }
            long r5 = r7.zzd     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzc(r5)     // Catch:{ all -> 0x0b2b }
        L_0x064d:
            com.google.android.gms.measurement.internal.zzam r5 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r5)     // Catch:{ all -> 0x0b2b }
            r5.zzE(r4)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzgb r4 = r35.zzaB()     // Catch:{ all -> 0x0b2b }
            r4.zzg()     // Catch:{ all -> 0x0b2b }
            r35.zzB()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r37)     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = r7.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4)     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = r7.zza     // Catch:{ all -> 0x0b2b }
            java.lang.String r5 = r3.zza     // Catch:{ all -> 0x0b2b }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkArgument(r4)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.internal.measurement.zzgc r4 = com.google.android.gms.internal.measurement.zzgd.zzu()     // Catch:{ all -> 0x0b2b }
            r5 = 1
            r4.zzad(r5)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = "android"
            r4.zzZ(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r3.zza     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x068f
            java.lang.String r6 = r3.zza     // Catch:{ all -> 0x0b2b }
            r4.zzD(r6)     // Catch:{ all -> 0x0b2b }
        L_0x068f:
            java.lang.String r6 = r3.zzd     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x069c
            java.lang.String r6 = r3.zzd     // Catch:{ all -> 0x0b2b }
            r4.zzF(r6)     // Catch:{ all -> 0x0b2b }
        L_0x069c:
            java.lang.String r6 = r3.zzc     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x06a9
            java.lang.String r6 = r3.zzc     // Catch:{ all -> 0x0b2b }
            r4.zzG(r6)     // Catch:{ all -> 0x0b2b }
        L_0x06a9:
            com.google.android.gms.internal.measurement.zzqr.zzc()     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r3.zzx     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x06d3
            com.google.android.gms.measurement.internal.zzag r6 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r8 = com.google.android.gms.measurement.internal.zzeh.zzam     // Catch:{ all -> 0x0b2b }
            boolean r6 = r6.zzs(r2, r8)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x06ce
            com.google.android.gms.measurement.internal.zzag r6 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r3.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r9 = com.google.android.gms.measurement.internal.zzeh.zzao     // Catch:{ all -> 0x0b2b }
            boolean r6 = r6.zzs(r8, r9)     // Catch:{ all -> 0x0b2b }
            if (r6 == 0) goto L_0x06d3
        L_0x06ce:
            java.lang.String r6 = r3.zzx     // Catch:{ all -> 0x0b2b }
            r4.zzah(r6)     // Catch:{ all -> 0x0b2b }
        L_0x06d3:
            long r8 = r3.zzj     // Catch:{ all -> 0x0b2b }
            r10 = -2147483648(0xffffffff80000000, double:NaN)
            int r6 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r6 == 0) goto L_0x06e0
            int r6 = (int) r8     // Catch:{ all -> 0x0b2b }
            r4.zzH(r6)     // Catch:{ all -> 0x0b2b }
        L_0x06e0:
            long r8 = r3.zze     // Catch:{ all -> 0x0b2b }
            r4.zzV(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r3.zzb     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x06f2
            java.lang.String r6 = r3.zzb     // Catch:{ all -> 0x0b2b }
            r4.zzU(r6)     // Catch:{ all -> 0x0b2b }
        L_0x06f2:
            java.lang.String r6 = r3.zza     // Catch:{ all -> 0x0b2b }
            java.lang.Object r6 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzai r6 = r1.zzh(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r3.zzv     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzai r8 = com.google.android.gms.measurement.internal.zzai.zzb(r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzai r6 = r6.zzc(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r6.zzh()     // Catch:{ all -> 0x0b2b }
            r4.zzL(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r4.zzar()     // Catch:{ all -> 0x0b2b }
            boolean r6 = r6.isEmpty()     // Catch:{ all -> 0x0b2b }
            if (r6 == 0) goto L_0x0726
            java.lang.String r6 = r3.zzq     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x0726
            java.lang.String r6 = r3.zzq     // Catch:{ all -> 0x0b2b }
            r4.zzC(r6)     // Catch:{ all -> 0x0b2b }
        L_0x0726:
            long r8 = r3.zzf     // Catch:{ all -> 0x0b2b }
            r10 = 0
            int r6 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r6 == 0) goto L_0x0731
            r4.zzM(r8)     // Catch:{ all -> 0x0b2b }
        L_0x0731:
            long r8 = r3.zzs     // Catch:{ all -> 0x0b2b }
            r4.zzP(r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzli r6 = r1.zzi     // Catch:{ all -> 0x0b2b }
            zzal(r6)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlg r8 = r6.zzf     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r8 = r8.zzn     // Catch:{ all -> 0x0b2b }
            android.content.Context r8 = r8.zzaw()     // Catch:{ all -> 0x0b2b }
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = "com.google.android.gms.measurement"
            android.net.Uri r9 = com.google.android.gms.internal.measurement.zzhq.zza(r9)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzay r10 = com.google.android.gms.measurement.internal.zzay.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.internal.measurement.zzhf r8 = com.google.android.gms.internal.measurement.zzhf.zza(r8, r9, r10)     // Catch:{ all -> 0x0b2b }
            if (r8 != 0) goto L_0x075a
            java.util.Map r8 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x0b2b }
            goto L_0x075e
        L_0x075a:
            java.util.Map r8 = r8.zzc()     // Catch:{ all -> 0x0b2b }
        L_0x075e:
            if (r8 == 0) goto L_0x07e5
            boolean r9 = r8.isEmpty()     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0769
            r14 = r2
            goto L_0x07e6
        L_0x0769:
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ all -> 0x0b2b }
            r14.<init>()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r9 = com.google.android.gms.measurement.internal.zzeh.zzP     // Catch:{ all -> 0x0b2b }
            java.lang.Object r9 = r9.zza(r2)     // Catch:{ all -> 0x0b2b }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ all -> 0x0b2b }
            int r9 = r9.intValue()     // Catch:{ all -> 0x0b2b }
            java.util.Set r8 = r8.entrySet()     // Catch:{ all -> 0x0b2b }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ all -> 0x0b2b }
        L_0x0782:
            boolean r10 = r8.hasNext()     // Catch:{ all -> 0x0b2b }
            if (r10 == 0) goto L_0x07df
            java.lang.Object r10 = r8.next()     // Catch:{ all -> 0x0b2b }
            java.util.Map$Entry r10 = (java.util.Map.Entry) r10     // Catch:{ all -> 0x0b2b }
            java.lang.Object r11 = r10.getKey()     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x0b2b }
            java.lang.String r12 = "measurement.id."
            boolean r11 = r11.startsWith(r12)     // Catch:{ all -> 0x0b2b }
            if (r11 == 0) goto L_0x0782
            java.lang.Object r10 = r10.getValue()     // Catch:{ NumberFormatException -> 0x07cd }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ NumberFormatException -> 0x07cd }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ NumberFormatException -> 0x07cd }
            if (r10 == 0) goto L_0x0782
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ NumberFormatException -> 0x07cd }
            r14.add(r10)     // Catch:{ NumberFormatException -> 0x07cd }
            int r10 = r14.size()     // Catch:{ NumberFormatException -> 0x07cd }
            if (r10 < r9) goto L_0x0782
            com.google.android.gms.measurement.internal.zzge r10 = r6.zzt     // Catch:{ NumberFormatException -> 0x07cd }
            com.google.android.gms.measurement.internal.zzeu r10 = r10.zzaA()     // Catch:{ NumberFormatException -> 0x07cd }
            com.google.android.gms.measurement.internal.zzes r10 = r10.zzk()     // Catch:{ NumberFormatException -> 0x07cd }
            java.lang.String r11 = "Too many experiment IDs. Number of IDs"
            int r12 = r14.size()     // Catch:{ NumberFormatException -> 0x07cd }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ NumberFormatException -> 0x07cd }
            r10.zzb(r11, r12)     // Catch:{ NumberFormatException -> 0x07cd }
            goto L_0x07df
        L_0x07cd:
            r0 = move-exception
            r10 = r0
            com.google.android.gms.measurement.internal.zzge r11 = r6.zzt     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeu r11 = r11.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r11 = r11.zzk()     // Catch:{ all -> 0x0b2b }
            java.lang.String r12 = "Experiment ID NumberFormatException"
            r11.zzb(r12, r10)     // Catch:{ all -> 0x0b2b }
            goto L_0x0782
        L_0x07df:
            boolean r6 = r14.isEmpty()     // Catch:{ all -> 0x0b2b }
            if (r6 == 0) goto L_0x07e6
        L_0x07e5:
            r14 = r2
        L_0x07e6:
            if (r14 == 0) goto L_0x07eb
            r4.zzh(r14)     // Catch:{ all -> 0x0b2b }
        L_0x07eb:
            java.lang.String r6 = r3.zza     // Catch:{ all -> 0x0b2b }
            java.lang.Object r6 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzai r6 = r1.zzh(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r3.zzv     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzai r8 = com.google.android.gms.measurement.internal.zzai.zzb(r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzai r6 = r6.zzc(r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzah r8 = com.google.android.gms.measurement.internal.zzah.AD_STORAGE     // Catch:{ all -> 0x0b2b }
            boolean r9 = r6.zzi(r8)     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0837
            boolean r9 = r3.zzo     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0837
            com.google.android.gms.measurement.internal.zzka r9 = r1.zzk     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = r3.zza     // Catch:{ all -> 0x0b2b }
            android.util.Pair r9 = r9.zzd(r10, r6)     // Catch:{ all -> 0x0b2b }
            java.lang.Object r10 = r9.first     // Catch:{ all -> 0x0b2b }
            java.lang.CharSequence r10 = (java.lang.CharSequence) r10     // Catch:{ all -> 0x0b2b }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ all -> 0x0b2b }
            if (r10 != 0) goto L_0x0837
            boolean r10 = r3.zzo     // Catch:{ all -> 0x0b2b }
            if (r10 == 0) goto L_0x0837
            java.lang.Object r10 = r9.first     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x0b2b }
            r4.zzae(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.Object r9 = r9.second     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0837
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ all -> 0x0b2b }
            boolean r9 = r9.booleanValue()     // Catch:{ all -> 0x0b2b }
            r4.zzX(r9)     // Catch:{ all -> 0x0b2b }
        L_0x0837:
            com.google.android.gms.measurement.internal.zzge r9 = r1.zzn     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzaq r9 = r9.zzg()     // Catch:{ all -> 0x0b2b }
            r9.zzv()     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = android.os.Build.MODEL     // Catch:{ all -> 0x0b2b }
            r4.zzN(r9)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r9 = r1.zzn     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzaq r9 = r9.zzg()     // Catch:{ all -> 0x0b2b }
            r9.zzv()     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = android.os.Build.VERSION.RELEASE     // Catch:{ all -> 0x0b2b }
            r4.zzY(r9)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r9 = r1.zzn     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzaq r9 = r9.zzg()     // Catch:{ all -> 0x0b2b }
            long r9 = r9.zzb()     // Catch:{ all -> 0x0b2b }
            int r9 = (int) r9     // Catch:{ all -> 0x0b2b }
            r4.zzak(r9)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r9 = r1.zzn     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzaq r9 = r9.zzg()     // Catch:{ all -> 0x0b2b }
            java.lang.String r9 = r9.zzc()     // Catch:{ all -> 0x0b2b }
            r4.zzao(r9)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.internal.measurement.zzpw.zzc()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzag r9 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r10 = com.google.android.gms.measurement.internal.zzeh.zzaE     // Catch:{ all -> 0x0b2b }
            boolean r9 = r9.zzs(r2, r10)     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0882
            long r9 = r3.zzz     // Catch:{ all -> 0x0b2b }
            r4.zzaj(r9)     // Catch:{ all -> 0x0b2b }
        L_0x0882:
            com.google.android.gms.measurement.internal.zzge r9 = r1.zzn     // Catch:{ all -> 0x0b2b }
            boolean r9 = r9.zzJ()     // Catch:{ all -> 0x0b2b }
            if (r9 == 0) goto L_0x0896
            r4.zzaq()     // Catch:{ all -> 0x0b2b }
            boolean r9 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0b2b }
            if (r9 != 0) goto L_0x0896
            r4.zzO(r2)     // Catch:{ all -> 0x0b2b }
        L_0x0896:
            com.google.android.gms.measurement.internal.zzam r9 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r9)     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = r3.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzh r9 = r9.zzj(r10)     // Catch:{ all -> 0x0b2b }
            if (r9 != 0) goto L_0x0907
            com.google.android.gms.measurement.internal.zzh r9 = new com.google.android.gms.measurement.internal.zzh     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzge r10 = r1.zzn     // Catch:{ all -> 0x0b2b }
            java.lang.String r11 = r3.zza     // Catch:{ all -> 0x0b2b }
            r9.<init>(r10, r11)     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = r1.zzw(r6)     // Catch:{ all -> 0x0b2b }
            r9.zzI(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = r3.zzk     // Catch:{ all -> 0x0b2b }
            r9.zzW(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = r3.zzb     // Catch:{ all -> 0x0b2b }
            r9.zzX(r10)     // Catch:{ all -> 0x0b2b }
            boolean r8 = r6.zzi(r8)     // Catch:{ all -> 0x0b2b }
            if (r8 == 0) goto L_0x08d0
            com.google.android.gms.measurement.internal.zzka r8 = r1.zzk     // Catch:{ all -> 0x0b2b }
            java.lang.String r10 = r3.zza     // Catch:{ all -> 0x0b2b }
            boolean r11 = r3.zzo     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r8.zzf(r10, r11)     // Catch:{ all -> 0x0b2b }
            r9.zzaf(r8)     // Catch:{ all -> 0x0b2b }
        L_0x08d0:
            r10 = 0
            r9.zzab(r10)     // Catch:{ all -> 0x0b2b }
            r9.zzac(r10)     // Catch:{ all -> 0x0b2b }
            r9.zzaa(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r3.zzc     // Catch:{ all -> 0x0b2b }
            r9.zzK(r8)     // Catch:{ all -> 0x0b2b }
            long r10 = r3.zzj     // Catch:{ all -> 0x0b2b }
            r9.zzL(r10)     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r3.zzd     // Catch:{ all -> 0x0b2b }
            r9.zzJ(r8)     // Catch:{ all -> 0x0b2b }
            long r10 = r3.zze     // Catch:{ all -> 0x0b2b }
            r9.zzY(r10)     // Catch:{ all -> 0x0b2b }
            long r10 = r3.zzf     // Catch:{ all -> 0x0b2b }
            r9.zzT(r10)     // Catch:{ all -> 0x0b2b }
            boolean r8 = r3.zzh     // Catch:{ all -> 0x0b2b }
            r9.zzad(r8)     // Catch:{ all -> 0x0b2b }
            long r10 = r3.zzs     // Catch:{ all -> 0x0b2b }
            r9.zzU(r10)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r8 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r8)     // Catch:{ all -> 0x0b2b }
            r8.zzD(r9)     // Catch:{ all -> 0x0b2b }
        L_0x0907:
            com.google.android.gms.measurement.internal.zzah r8 = com.google.android.gms.measurement.internal.zzah.ANALYTICS_STORAGE     // Catch:{ all -> 0x0b2b }
            boolean r6 = r6.zzi(r8)     // Catch:{ all -> 0x0b2b }
            if (r6 == 0) goto L_0x0926
            java.lang.String r6 = r9.zzv()     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x0926
            java.lang.String r6 = r9.zzv()     // Catch:{ all -> 0x0b2b }
            java.lang.Object r6 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x0b2b }
            r4.zzE(r6)     // Catch:{ all -> 0x0b2b }
        L_0x0926:
            java.lang.String r6 = r9.zzy()     // Catch:{ all -> 0x0b2b }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0b2b }
            if (r6 != 0) goto L_0x093d
            java.lang.String r6 = r9.zzy()     // Catch:{ all -> 0x0b2b }
            java.lang.Object r6 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x0b2b }
            r4.zzT(r6)     // Catch:{ all -> 0x0b2b }
        L_0x093d:
            com.google.android.gms.measurement.internal.zzam r6 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r3 = r3.zza     // Catch:{ all -> 0x0b2b }
            java.util.List r3 = r6.zzu(r3)     // Catch:{ all -> 0x0b2b }
            r13 = r31
        L_0x094a:
            int r6 = r3.size()     // Catch:{ all -> 0x0b2b }
            if (r13 >= r6) goto L_0x0980
            com.google.android.gms.internal.measurement.zzgl r6 = com.google.android.gms.internal.measurement.zzgm.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.Object r8 = r3.get(r13)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzll r8 = (com.google.android.gms.measurement.internal.zzll) r8     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r8.zzc     // Catch:{ all -> 0x0b2b }
            r6.zzf(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.Object r8 = r3.get(r13)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzll r8 = (com.google.android.gms.measurement.internal.zzll) r8     // Catch:{ all -> 0x0b2b }
            long r8 = r8.zzd     // Catch:{ all -> 0x0b2b }
            r6.zzg(r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzli r8 = r1.zzi     // Catch:{ all -> 0x0b2b }
            zzal(r8)     // Catch:{ all -> 0x0b2b }
            java.lang.Object r9 = r3.get(r13)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzll r9 = (com.google.android.gms.measurement.internal.zzll) r9     // Catch:{ all -> 0x0b2b }
            java.lang.Object r9 = r9.zze     // Catch:{ all -> 0x0b2b }
            r8.zzu(r6, r9)     // Catch:{ all -> 0x0b2b }
            r4.zzl(r6)     // Catch:{ all -> 0x0b2b }
            int r13 = r13 + 1
            goto L_0x094a
        L_0x0980:
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ IOException -> 0x0ae1 }
            zzal(r3)     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.internal.measurement.zzlb r6 = r4.zzaD()     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.internal.measurement.zzgd r6 = (com.google.android.gms.internal.measurement.zzgd) r6     // Catch:{ IOException -> 0x0ae1 }
            r3.zzg()     // Catch:{ IOException -> 0x0ae1 }
            r3.zzW()     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r6)     // Catch:{ IOException -> 0x0ae1 }
            java.lang.String r8 = r6.zzy()     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)     // Catch:{ IOException -> 0x0ae1 }
            byte[] r8 = r6.zzbx()     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.measurement.internal.zzlg r9 = r3.zzf     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.measurement.internal.zzli r9 = r9.zzi     // Catch:{ IOException -> 0x0ae1 }
            zzal(r9)     // Catch:{ IOException -> 0x0ae1 }
            long r9 = r9.zzd(r8)     // Catch:{ IOException -> 0x0ae1 }
            android.content.ContentValues r11 = new android.content.ContentValues     // Catch:{ IOException -> 0x0ae1 }
            r11.<init>()     // Catch:{ IOException -> 0x0ae1 }
            java.lang.String r12 = r6.zzy()     // Catch:{ IOException -> 0x0ae1 }
            r13 = r34
            r11.put(r13, r12)     // Catch:{ IOException -> 0x0ae1 }
            java.lang.Long r12 = java.lang.Long.valueOf(r9)     // Catch:{ IOException -> 0x0ae1 }
            r14 = r33
            r11.put(r14, r12)     // Catch:{ IOException -> 0x0ae1 }
            java.lang.String r12 = "metadata"
            r11.put(r12, r8)     // Catch:{ IOException -> 0x0ae1 }
            android.database.sqlite.SQLiteDatabase r8 = r3.zzh()     // Catch:{ SQLiteException -> 0x0ac7 }
            java.lang.String r12 = "raw_events_metadata"
            r15 = 4
            r8.insertWithOnConflict(r12, r2, r11, r15)     // Catch:{ SQLiteException -> 0x0ac7 }
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r3)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzau r4 = r7.zzf     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzat r6 = new com.google.android.gms.measurement.internal.zzat     // Catch:{ all -> 0x0b2b }
            r6.<init>(r4)     // Catch:{ all -> 0x0b2b }
        L_0x09dc:
            boolean r4 = r6.hasNext()     // Catch:{ all -> 0x0b2b }
            if (r4 == 0) goto L_0x09f4
            java.lang.String r4 = r6.next()     // Catch:{ all -> 0x0b2b }
            r8 = r32
            boolean r4 = r8.equals(r4)     // Catch:{ all -> 0x0b2b }
            if (r4 == 0) goto L_0x09f1
            r31 = r5
            goto L_0x0a35
        L_0x09f1:
            r32 = r8
            goto L_0x09dc
        L_0x09f4:
            com.google.android.gms.measurement.internal.zzfv r4 = r1.zzc     // Catch:{ all -> 0x0b2b }
            zzal(r4)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r7.zza     // Catch:{ all -> 0x0b2b }
            java.lang.String r8 = r7.zzb     // Catch:{ all -> 0x0b2b }
            boolean r4 = r4.zzq(r6, r8)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r15 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r15)     // Catch:{ all -> 0x0b2b }
            long r16 = r35.zza()     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r7.zza     // Catch:{ all -> 0x0b2b }
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r18 = r6
            com.google.android.gms.measurement.internal.zzak r6 = r15.zzl(r16, r18, r19, r20, r21, r22, r23)     // Catch:{ all -> 0x0b2b }
            if (r4 == 0) goto L_0x0a34
            long r11 = r6.zze     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzag r4 = r35.zzg()     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r7.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeg r8 = com.google.android.gms.measurement.internal.zzeh.zzo     // Catch:{ all -> 0x0b2b }
            int r4 = r4.zze(r6, r8)     // Catch:{ all -> 0x0b2b }
            long r5 = (long) r4     // Catch:{ all -> 0x0b2b }
            int r4 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r4 >= 0) goto L_0x0a34
            r31 = 1
            goto L_0x0a35
        L_0x0a34:
        L_0x0a35:
            r3.zzg()     // Catch:{ all -> 0x0b2b }
            r3.zzW()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = r7.zza     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzlg r4 = r3.zzf     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzli r4 = r4.zzi     // Catch:{ all -> 0x0b2b }
            zzal(r4)     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.internal.measurement.zzft r4 = r4.zzj(r7)     // Catch:{ all -> 0x0b2b }
            byte[] r4 = r4.zzbx()     // Catch:{ all -> 0x0b2b }
            android.content.ContentValues r5 = new android.content.ContentValues     // Catch:{ all -> 0x0b2b }
            r5.<init>()     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = r7.zza     // Catch:{ all -> 0x0b2b }
            r5.put(r13, r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = "name"
            java.lang.String r8 = r7.zzb     // Catch:{ all -> 0x0b2b }
            r5.put(r6, r8)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = "timestamp"
            long r11 = r7.zzd     // Catch:{ all -> 0x0b2b }
            java.lang.Long r8 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0b2b }
            r5.put(r6, r8)     // Catch:{ all -> 0x0b2b }
            java.lang.Long r6 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x0b2b }
            r5.put(r14, r6)     // Catch:{ all -> 0x0b2b }
            java.lang.String r6 = "data"
            r5.put(r6, r4)     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = "realtime"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r31)     // Catch:{ all -> 0x0b2b }
            r5.put(r4, r6)     // Catch:{ all -> 0x0b2b }
            android.database.sqlite.SQLiteDatabase r4 = r3.zzh()     // Catch:{ SQLiteException -> 0x0aaf }
            r6 = r30
            long r4 = r4.insert(r6, r2, r5)     // Catch:{ SQLiteException -> 0x0aaf }
            r8 = -1
            int r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x0aaa
            com.google.android.gms.measurement.internal.zzge r2 = r3.zzt     // Catch:{ SQLiteException -> 0x0aaf }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ SQLiteException -> 0x0aaf }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ SQLiteException -> 0x0aaf }
            java.lang.String r4 = "Failed to insert raw event (got -1). appId"
            java.lang.String r5 = r7.zza     // Catch:{ SQLiteException -> 0x0aaf }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r5)     // Catch:{ SQLiteException -> 0x0aaf }
            r2.zzb(r4, r5)     // Catch:{ SQLiteException -> 0x0aaf }
            goto L_0x0af8
        L_0x0aaa:
            r2 = 0
            r1.zza = r2     // Catch:{ all -> 0x0b2b }
            goto L_0x0af8
        L_0x0aaf:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r4 = "Error storing raw event. appId"
            java.lang.String r5 = r7.zza     // Catch:{ all -> 0x0b2b }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r5)     // Catch:{ all -> 0x0b2b }
            r3.zzc(r4, r5, r2)     // Catch:{ all -> 0x0b2b }
            goto L_0x0af8
        L_0x0ac7:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ IOException -> 0x0ae1 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ IOException -> 0x0ae1 }
            java.lang.String r5 = "Error storing raw event metadata. appId"
            java.lang.String r6 = r6.zzy()     // Catch:{ IOException -> 0x0ae1 }
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)     // Catch:{ IOException -> 0x0ae1 }
            r3.zzc(r5, r6, r2)     // Catch:{ IOException -> 0x0ae1 }
            throw r2     // Catch:{ IOException -> 0x0ae1 }
        L_0x0ae1:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzeu r3 = r35.zzaA()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0b2b }
            java.lang.String r5 = "Data loss. Failed to insert raw event metadata. appId"
            java.lang.String r4 = r4.zzaq()     // Catch:{ all -> 0x0b2b }
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r4)     // Catch:{ all -> 0x0b2b }
            r3.zzc(r5, r4, r2)     // Catch:{ all -> 0x0b2b }
        L_0x0af8:
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze     // Catch:{ all -> 0x0b2b }
            zzal(r2)     // Catch:{ all -> 0x0b2b }
            r2.zzC()     // Catch:{ all -> 0x0b2b }
            com.google.android.gms.measurement.internal.zzam r2 = r1.zze
            zzal(r2)
            r2.zzx()
            r35.zzag()
            com.google.android.gms.measurement.internal.zzeu r2 = r35.zzaA()
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzj()
            long r3 = java.lang.System.nanoTime()
            long r3 = r3 - r28
            r5 = 500000(0x7a120, double:2.47033E-318)
            long r3 = r3 + r5
            r5 = 1000000(0xf4240, double:4.940656E-318)
            long r3 = r3 / r5
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            java.lang.String r4 = "Background event processing time, ms"
            r2.zzb(r4, r3)
            return
        L_0x0b2b:
            r0 = move-exception
            r2 = r0
            com.google.android.gms.measurement.internal.zzam r3 = r1.zze
            zzal(r3)
            r3.zzx()
            throw r2
        L_0x0b36:
            r1.zzd(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzlg.zzY(com.google.android.gms.measurement.internal.zzaw, com.google.android.gms.measurement.internal.zzq):void");
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final boolean zzZ() {
        zzaB().zzg();
        FileLock fileLock = this.zzw;
        if (fileLock == null || !fileLock.isValid()) {
            this.zze.zzt.zzf();
            try {
                FileChannel channel = new RandomAccessFile(new File(this.zzn.zzaw().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
                this.zzx = channel;
                FileLock tryLock = channel.tryLock();
                this.zzw = tryLock;
                if (tryLock != null) {
                    zzaA().zzj().zza("Storage concurrent access okay");
                    return true;
                }
                zzaA().zzd().zza("Storage concurrent data access panic");
                return false;
            } catch (FileNotFoundException e) {
                zzaA().zzd().zzb("Failed to acquire storage lock", e);
                return false;
            } catch (IOException e2) {
                zzaA().zzd().zzb("Failed to access storage lock file", e2);
                return false;
            } catch (OverlappingFileLockException e3) {
                zzaA().zzk().zzb("Storage lock already acquired", e3);
                return false;
            }
        } else {
            zzaA().zzj().zza("Storage concurrent access okay");
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public final long zza() {
        long currentTimeMillis = zzax().currentTimeMillis();
        zzka zzka = this.zzk;
        zzka.zzW();
        zzka.zzg();
        long zza2 = zzka.zze.zza();
        if (zza2 == 0) {
            zza2 = ((long) zzka.zzt.zzv().zzG().nextInt(86400000)) + 1;
            zzka.zze.zzb(zza2);
        }
        return ((((currentTimeMillis + zza2) / 1000) / 60) / 60) / 24;
    }

    public final zzeu zzaA() {
        return ((zzge) Preconditions.checkNotNull(this.zzn)).zzaA();
    }

    public final zzgb zzaB() {
        return ((zzge) Preconditions.checkNotNull(this.zzn)).zzaB();
    }

    public final Context zzaw() {
        return this.zzn.zzaw();
    }

    public final Clock zzax() {
        return ((zzge) Preconditions.checkNotNull(this.zzn)).zzax();
    }

    public final zzab zzay() {
        throw null;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final zzh zzd(zzq zzq2) {
        String str;
        zzaB().zzg();
        zzB();
        Preconditions.checkNotNull(zzq2);
        Preconditions.checkNotEmpty(zzq2.zza);
        if (!zzq2.zzw.isEmpty()) {
            this.zzC.put(zzq2.zza, new zzlf(this, zzq2.zzw));
        }
        zzam zzam = this.zze;
        zzal(zzam);
        zzh zzj2 = zzam.zzj(zzq2.zza);
        zzai zzc2 = zzh(zzq2.zza).zzc(zzai.zzb(zzq2.zzv));
        zzah zzah = zzah.AD_STORAGE;
        if (zzc2.zzi(zzah)) {
            str = this.zzk.zzf(zzq2.zza, zzq2.zzo);
        } else {
            str = "";
        }
        if (zzj2 == null) {
            zzj2 = new zzh(this.zzn, zzq2.zza);
            if (zzc2.zzi(zzah.ANALYTICS_STORAGE)) {
                zzj2.zzI(zzw(zzc2));
            }
            if (zzc2.zzi(zzah)) {
                zzj2.zzaf(str);
            }
        } else if (zzc2.zzi(zzah) && str != null && !str.equals(zzj2.zzB())) {
            zzj2.zzaf(str);
            if (zzq2.zzo && !"00000000-0000-0000-0000-000000000000".equals(this.zzk.zzd(zzq2.zza, zzc2).first)) {
                zzj2.zzI(zzw(zzc2));
                zzam zzam2 = this.zze;
                zzal(zzam2);
                if (zzam2.zzp(zzq2.zza, "_id") != null) {
                    zzam zzam3 = this.zze;
                    zzal(zzam3);
                    if (zzam3.zzp(zzq2.zza, "_lair") == null) {
                        zzll zzll = new zzll(zzq2.zza, "auto", "_lair", zzax().currentTimeMillis(), 1L);
                        zzam zzam4 = this.zze;
                        zzal(zzam4);
                        zzam4.zzL(zzll);
                    }
                }
            }
        } else if (TextUtils.isEmpty(zzj2.zzv()) && zzc2.zzi(zzah.ANALYTICS_STORAGE)) {
            zzj2.zzI(zzw(zzc2));
        }
        zzj2.zzX(zzq2.zzb);
        zzj2.zzG(zzq2.zzq);
        if (!TextUtils.isEmpty(zzq2.zzk)) {
            zzj2.zzW(zzq2.zzk);
        }
        long j = zzq2.zze;
        if (j != 0) {
            zzj2.zzY(j);
        }
        if (!TextUtils.isEmpty(zzq2.zzc)) {
            zzj2.zzK(zzq2.zzc);
        }
        zzj2.zzL(zzq2.zzj);
        String str2 = zzq2.zzd;
        if (str2 != null) {
            zzj2.zzJ(str2);
        }
        zzj2.zzT(zzq2.zzf);
        zzj2.zzad(zzq2.zzh);
        if (!TextUtils.isEmpty(zzq2.zzg)) {
            zzj2.zzZ(zzq2.zzg);
        }
        zzj2.zzH(zzq2.zzo);
        zzj2.zzae(zzq2.zzr);
        zzj2.zzU(zzq2.zzs);
        zzqr.zzc();
        if (zzg().zzs((String) null, zzeh.zzam) || zzg().zzs(zzq2.zza, zzeh.zzao)) {
            zzj2.zzah(zzq2.zzx);
        }
        zzop.zzc();
        if (zzg().zzs((String) null, zzeh.zzal)) {
            zzj2.zzag(zzq2.zzt);
        } else {
            zzop.zzc();
            if (zzg().zzs((String) null, zzeh.zzak)) {
                zzj2.zzag((List) null);
            }
        }
        zzra.zzc();
        if (zzg().zzs((String) null, zzeh.zzaq)) {
            zzj2.zzai(zzq2.zzy);
        }
        zzpw.zzc();
        if (zzg().zzs((String) null, zzeh.zzaE)) {
            zzj2.zzaj(zzq2.zzz);
        }
        if (zzj2.zzam()) {
            zzam zzam5 = this.zze;
            zzal(zzam5);
            zzam5.zzD(zzj2);
        }
        return zzj2;
    }

    public final zzaa zzf() {
        zzaa zzaa = this.zzh;
        zzal(zzaa);
        return zzaa;
    }

    public final zzag zzg() {
        return ((zzge) Preconditions.checkNotNull(this.zzn)).zzf();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006a  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzai zzh(java.lang.String r7) {
        /*
            r6 = this;
            com.google.android.gms.measurement.internal.zzai r0 = com.google.android.gms.measurement.internal.zzai.zza
            com.google.android.gms.measurement.internal.zzgb r0 = r6.zzaB()
            r0.zzg()
            r6.zzB()
            java.util.Map r0 = r6.zzB
            java.lang.Object r0 = r0.get(r7)
            com.google.android.gms.measurement.internal.zzai r0 = (com.google.android.gms.measurement.internal.zzai) r0
            if (r0 != 0) goto L_0x006e
            com.google.android.gms.measurement.internal.zzam r0 = r6.zze
            zzal(r0)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)
            r0.zzg()
            r0.zzW()
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]
            r2 = 0
            r1[r2] = r7
            android.database.sqlite.SQLiteDatabase r3 = r0.zzh()
            java.lang.String r4 = "select consent_state from consent_settings where app_id=? limit 1;"
            r5 = 0
            android.database.Cursor r5 = r3.rawQuery(r4, r1)     // Catch:{ SQLiteException -> 0x0057, all -> 0x0055 }
            boolean r1 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x0053 }
            if (r1 == 0) goto L_0x0043
            java.lang.String r0 = r5.getString(r2)     // Catch:{ SQLiteException -> 0x0053 }
            r5.close()
            goto L_0x0049
        L_0x0043:
            r5.close()
            java.lang.String r0 = "G1"
        L_0x0049:
            com.google.android.gms.measurement.internal.zzai r0 = com.google.android.gms.measurement.internal.zzai.zzb(r0)
            r6.zzV(r7, r0)
            goto L_0x006f
        L_0x0051:
            r7 = move-exception
            goto L_0x0068
        L_0x0053:
            r7 = move-exception
            goto L_0x0058
        L_0x0055:
            r7 = move-exception
            goto L_0x0068
        L_0x0057:
            r7 = move-exception
        L_0x0058:
            com.google.android.gms.measurement.internal.zzge r0 = r0.zzt     // Catch:{ all -> 0x0051 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x0051 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x0051 }
            java.lang.String r1 = "Database error"
            r0.zzc(r1, r4, r7)     // Catch:{ all -> 0x0051 }
            throw r7     // Catch:{ all -> 0x0051 }
        L_0x0068:
            if (r5 == 0) goto L_0x006d
            r5.close()
        L_0x006d:
            throw r7
        L_0x006e:
        L_0x006f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzlg.zzh(java.lang.String):com.google.android.gms.measurement.internal.zzai");
    }

    public final zzam zzi() {
        zzam zzam = this.zze;
        zzal(zzam);
        return zzam;
    }

    public final zzep zzj() {
        return this.zzn.zzj();
    }

    public final zzfa zzl() {
        zzfa zzfa = this.zzd;
        zzal(zzfa);
        return zzfa;
    }

    public final zzfc zzm() {
        zzfc zzfc = this.zzf;
        if (zzfc != null) {
            return zzfc;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    public final zzfv zzo() {
        zzfv zzfv = this.zzc;
        zzal(zzfv);
        return zzfv;
    }

    /* access modifiers changed from: package-private */
    public final zzge zzq() {
        return this.zzn;
    }

    public final zzio zzr() {
        zzio zzio = this.zzj;
        zzal(zzio);
        return zzio;
    }

    public final zzka zzs() {
        return this.zzk;
    }

    public final zzli zzu() {
        zzli zzli = this.zzi;
        zzal(zzli);
        return zzli;
    }

    public final zzlo zzv() {
        return ((zzge) Preconditions.checkNotNull(this.zzn)).zzv();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzw(zzai zzai) {
        if (!zzai.zzi(zzah.ANALYTICS_STORAGE)) {
            return null;
        }
        byte[] bArr = new byte[16];
        zzv().zzG().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, bArr)});
    }

    /* access modifiers changed from: package-private */
    public final String zzx(zzq zzq2) {
        try {
            return (String) zzaB().zzh(new zzkz(this, zzq2)).get(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzaA().zzd().zzc("Failed to get app instance id. appId", zzeu.zzn(zzq2.zza), e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzz(Runnable runnable) {
        zzaB().zzg();
        if (this.zzq == null) {
            this.zzq = new ArrayList();
        }
        this.zzq.add(runnable);
    }
}
