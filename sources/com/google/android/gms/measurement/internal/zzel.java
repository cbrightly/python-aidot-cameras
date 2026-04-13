package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import androidx.annotation.WorkerThread;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzpw;
import com.google.android.gms.internal.measurement.zzqr;
import com.google.android.gms.internal.measurement.zzrg;
import com.google.maps.android.BuildConfig;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzel extends zzf {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private final long zzg;
    private List zzh;
    private String zzi;
    private int zzj;
    private String zzk;
    private String zzl;
    private String zzm;
    private long zzn = 0;
    private String zzo = null;

    zzel(zzge zzge, long j) {
        super(zzge);
        this.zzg = j;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0137  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0157  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0191 A[Catch:{ IllegalStateException -> 0x01e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0192 A[Catch:{ IllegalStateException -> 0x01e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x019b A[Catch:{ IllegalStateException -> 0x01e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01c3 A[Catch:{ IllegalStateException -> 0x01e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x020d  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0246  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0253  */
    @androidx.annotation.WorkerThread
    @org.checkerframework.checker.nullness.qual.EnsuresNonNull({"appId", "appStore", "appName", "gmpAppId", "gaAppId"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzd() {
        /*
            r11 = this;
            com.google.android.gms.measurement.internal.zzge r0 = r11.zzt
            android.content.Context r0 = r0.zzaw()
            java.lang.String r0 = r0.getPackageName()
            com.google.android.gms.measurement.internal.zzge r1 = r11.zzt
            android.content.Context r1 = r1.zzaw()
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            java.lang.String r2 = "Unknown"
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            java.lang.String r4 = ""
            r5 = 0
            java.lang.String r6 = "unknown"
            if (r1 != 0) goto L_0x0036
            com.google.android.gms.measurement.internal.zzge r7 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r7 = r7.zzaA()
            com.google.android.gms.measurement.internal.zzes r7 = r7.zzd()
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r0)
            java.lang.String r9 = "PackageManager is null, app identity information might be inaccurate. appId"
            r7.zzb(r9, r8)
            r8 = r2
            goto L_0x00a2
        L_0x0036:
            java.lang.String r6 = r1.getInstallerPackageName(r0)     // Catch:{ IllegalArgumentException -> 0x003b }
            goto L_0x004f
        L_0x003b:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzge r7 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r7 = r7.zzaA()
            com.google.android.gms.measurement.internal.zzes r7 = r7.zzd()
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeu.zzn(r0)
            java.lang.String r9 = "Error retrieving app installer package name. appId"
            r7.zzb(r9, r8)
        L_0x004f:
            if (r6 != 0) goto L_0x0054
            java.lang.String r6 = "manual_install"
            goto L_0x005e
        L_0x0054:
            java.lang.String r7 = "com.android.vending"
            boolean r7 = r7.equals(r6)
            if (r7 == 0) goto L_0x005e
            r6 = r4
        L_0x005e:
            com.google.android.gms.measurement.internal.zzge r7 = r11.zzt     // Catch:{ NameNotFoundException -> 0x008b }
            android.content.Context r7 = r7.zzaw()     // Catch:{ NameNotFoundException -> 0x008b }
            java.lang.String r7 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x008b }
            android.content.pm.PackageInfo r7 = r1.getPackageInfo(r7, r5)     // Catch:{ NameNotFoundException -> 0x008b }
            if (r7 == 0) goto L_0x0089
            android.content.pm.ApplicationInfo r8 = r7.applicationInfo     // Catch:{ NameNotFoundException -> 0x008b }
            java.lang.CharSequence r8 = r1.getApplicationLabel(r8)     // Catch:{ NameNotFoundException -> 0x008b }
            boolean r9 = android.text.TextUtils.isEmpty(r8)     // Catch:{ NameNotFoundException -> 0x008b }
            if (r9 != 0) goto L_0x007f
            java.lang.String r8 = r8.toString()     // Catch:{ NameNotFoundException -> 0x008b }
            goto L_0x0080
        L_0x007f:
            r8 = r2
        L_0x0080:
            java.lang.String r2 = r7.versionName     // Catch:{ NameNotFoundException -> 0x0085 }
            int r3 = r7.versionCode     // Catch:{ NameNotFoundException -> 0x0085 }
            goto L_0x00a2
        L_0x0085:
            r7 = move-exception
            r7 = r2
            r2 = r8
            goto L_0x008d
        L_0x0089:
            r8 = r2
            goto L_0x00a2
        L_0x008b:
            r7 = move-exception
            r7 = r2
        L_0x008d:
            com.google.android.gms.measurement.internal.zzge r8 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzd()
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzeu.zzn(r0)
            java.lang.String r10 = "Error retrieving package info. appId, appName"
            r8.zzc(r10, r9, r2)
            r8 = r2
            r2 = r7
        L_0x00a2:
            r11.zza = r0
            r11.zzd = r6
            r11.zzb = r2
            r11.zzc = r3
            r11.zze = r8
            r2 = 0
            r11.zzf = r2
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt
            java.lang.String r2 = r2.zzw()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x00cc
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt
            java.lang.String r2 = r2.zzx()
            java.lang.String r3 = "am"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x00cc
            r2 = 1
            goto L_0x00cd
        L_0x00cc:
            r2 = r5
        L_0x00cd:
            com.google.android.gms.measurement.internal.zzge r3 = r11.zzt
            int r3 = r3.zza()
            switch(r3) {
                case 0: goto L_0x0157;
                case 1: goto L_0x0147;
                case 2: goto L_0x0137;
                case 3: goto L_0x0127;
                case 4: goto L_0x0117;
                case 5: goto L_0x0107;
                case 6: goto L_0x00f7;
                case 7: goto L_0x00e7;
                default: goto L_0x00d6;
            }
        L_0x00d6:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzi()
            java.lang.String r7 = "App measurement disabled due to denied storage consent"
            r6.zza(r7)
            goto L_0x0166
        L_0x00e7:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzi()
            java.lang.String r7 = "App measurement disabled via the global data collection setting"
            r6.zza(r7)
            goto L_0x0166
        L_0x00f7:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzl()
            java.lang.String r7 = "App measurement deactivated via resources. This method is being deprecated. Please refer to https://firebase.google.com/support/guides/disable-analytics"
            r6.zza(r7)
            goto L_0x0166
        L_0x0107:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzj()
            java.lang.String r7 = "App measurement disabled via the init parameters"
            r6.zza(r7)
            goto L_0x0166
        L_0x0117:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzi()
            java.lang.String r7 = "App measurement disabled via the manifest"
            r6.zza(r7)
            goto L_0x0166
        L_0x0127:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzi()
            java.lang.String r7 = "App measurement disabled by setAnalyticsCollectionEnabled(false)"
            r6.zza(r7)
            goto L_0x0166
        L_0x0137:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzj()
            java.lang.String r7 = "App measurement deactivated via the init parameters"
            r6.zza(r7)
            goto L_0x0166
        L_0x0147:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzi()
            java.lang.String r7 = "App measurement deactivated via the manifest"
            r6.zza(r7)
            goto L_0x0166
        L_0x0157:
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzj()
            java.lang.String r7 = "App measurement collection enabled"
            r6.zza(r7)
        L_0x0166:
            r11.zzk = r4
            r11.zzl = r4
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt
            r6.zzay()
            if (r2 == 0) goto L_0x0179
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt
            java.lang.String r2 = r2.zzw()
            r11.zzl = r2
        L_0x0179:
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt     // Catch:{ IllegalStateException -> 0x01e2 }
            android.content.Context r2 = r2.zzaw()     // Catch:{ IllegalStateException -> 0x01e2 }
            com.google.android.gms.measurement.internal.zzge r6 = r11.zzt     // Catch:{ IllegalStateException -> 0x01e2 }
            java.lang.String r6 = r6.zzz()     // Catch:{ IllegalStateException -> 0x01e2 }
            java.lang.String r7 = "google_app_id"
            java.lang.String r2 = com.google.android.gms.measurement.internal.zzip.zzc(r2, r7, r6)     // Catch:{ IllegalStateException -> 0x01e2 }
            boolean r6 = android.text.TextUtils.isEmpty(r2)     // Catch:{ IllegalStateException -> 0x01e2 }
            if (r6 == 0) goto L_0x0192
            goto L_0x0193
        L_0x0192:
            r4 = r2
        L_0x0193:
            r11.zzk = r4     // Catch:{ IllegalStateException -> 0x01e2 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ IllegalStateException -> 0x01e2 }
            if (r2 != 0) goto L_0x01c1
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt     // Catch:{ IllegalStateException -> 0x01e2 }
            android.content.Context r2 = r2.zzaw()     // Catch:{ IllegalStateException -> 0x01e2 }
            com.google.android.gms.measurement.internal.zzge r4 = r11.zzt     // Catch:{ IllegalStateException -> 0x01e2 }
            java.lang.String r4 = r4.zzz()     // Catch:{ IllegalStateException -> 0x01e2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r2)     // Catch:{ IllegalStateException -> 0x01e2 }
            android.content.res.Resources r6 = r2.getResources()     // Catch:{ IllegalStateException -> 0x01e2 }
            boolean r7 = android.text.TextUtils.isEmpty(r4)     // Catch:{ IllegalStateException -> 0x01e2 }
            if (r7 != 0) goto L_0x01b5
            goto L_0x01b9
        L_0x01b5:
            java.lang.String r4 = com.google.android.gms.measurement.internal.zzfw.zza(r2)     // Catch:{ IllegalStateException -> 0x01e2 }
        L_0x01b9:
            java.lang.String r2 = "admob_app_id"
            java.lang.String r2 = com.google.android.gms.measurement.internal.zzfw.zzb(r2, r6, r4)     // Catch:{ IllegalStateException -> 0x01e2 }
            r11.zzl = r2     // Catch:{ IllegalStateException -> 0x01e2 }
        L_0x01c1:
            if (r3 != 0) goto L_0x01f6
            com.google.android.gms.measurement.internal.zzge r2 = r11.zzt     // Catch:{ IllegalStateException -> 0x01e2 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ IllegalStateException -> 0x01e2 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzj()     // Catch:{ IllegalStateException -> 0x01e2 }
            java.lang.String r3 = "App measurement enabled for app package, google app id"
            java.lang.String r4 = r11.zza     // Catch:{ IllegalStateException -> 0x01e2 }
            java.lang.String r6 = r11.zzk     // Catch:{ IllegalStateException -> 0x01e2 }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ IllegalStateException -> 0x01e2 }
            if (r6 == 0) goto L_0x01dc
            java.lang.String r6 = r11.zzl     // Catch:{ IllegalStateException -> 0x01e2 }
            goto L_0x01de
        L_0x01dc:
            java.lang.String r6 = r11.zzk     // Catch:{ IllegalStateException -> 0x01e2 }
        L_0x01de:
            r2.zzc(r3, r4, r6)     // Catch:{ IllegalStateException -> 0x01e2 }
            goto L_0x01f6
        L_0x01e2:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzge r3 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()
            java.lang.Object r0 = com.google.android.gms.measurement.internal.zzeu.zzn(r0)
            java.lang.String r4 = "Fetching Google App Id failed with exception. appId"
            r3.zzc(r4, r0, r2)
        L_0x01f6:
            r0 = 0
            r11.zzh = r0
            com.google.android.gms.measurement.internal.zzge r0 = r11.zzt
            r0.zzay()
            com.google.android.gms.measurement.internal.zzge r0 = r11.zzt
            com.google.android.gms.measurement.internal.zzag r0 = r0.zzf()
            java.lang.String r2 = "analytics.safelisted_events"
            java.util.List r0 = r0.zzp(r2)
            if (r0 != 0) goto L_0x020d
            goto L_0x0242
        L_0x020d:
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x0223
            com.google.android.gms.measurement.internal.zzge r0 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzl()
            java.lang.String r2 = "Safelisted event list is empty. Ignoring"
            r0.zza(r2)
            goto L_0x0244
        L_0x0223:
            java.util.Iterator r2 = r0.iterator()
        L_0x0227:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0242
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            com.google.android.gms.measurement.internal.zzge r4 = r11.zzt
            com.google.android.gms.measurement.internal.zzlo r4 = r4.zzv()
            java.lang.String r6 = "safelisted event"
            boolean r3 = r4.zzac(r6, r3)
            if (r3 != 0) goto L_0x0227
            goto L_0x0244
        L_0x0242:
            r11.zzh = r0
        L_0x0244:
            if (r1 == 0) goto L_0x0253
            com.google.android.gms.measurement.internal.zzge r0 = r11.zzt
            android.content.Context r0 = r0.zzaw()
            boolean r0 = com.google.android.gms.common.wrappers.InstantApps.isInstantApp(r0)
            r11.zzj = r0
            return
        L_0x0253:
            r11.zzj = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzel.zzd():void");
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return true;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final int zzh() {
        zza();
        return this.zzj;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final int zzi() {
        zza();
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final zzq zzj(String str) {
        long j;
        String str2;
        long j2;
        String str3;
        Boolean bool;
        String str4;
        int i;
        long j3;
        boolean z;
        long j4;
        int i2;
        int i3;
        long j5;
        zzg();
        String zzl2 = zzl();
        String zzm2 = zzm();
        zza();
        String str5 = this.zzb;
        zza();
        long j6 = (long) this.zzc;
        zza();
        Preconditions.checkNotNull(this.zzd);
        String str6 = this.zzd;
        this.zzt.zzf().zzh();
        zza();
        zzg();
        long j7 = this.zzf;
        if (j7 == 0) {
            zzlo zzv = this.zzt.zzv();
            Context zzaw = this.zzt.zzaw();
            String packageName = this.zzt.zzaw().getPackageName();
            zzv.zzg();
            Preconditions.checkNotNull(zzaw);
            Preconditions.checkNotEmpty(packageName);
            PackageManager packageManager = zzaw.getPackageManager();
            MessageDigest zzF = zzlo.zzF();
            long j8 = -1;
            if (zzF == null) {
                zzv.zzt.zzaA().zzd().zza("Could not get MD5 instance");
                j5 = -1;
            } else if (packageManager != null) {
                try {
                    if (!zzv.zzah(zzaw, packageName)) {
                        Signature[] signatureArr = Wrappers.packageManager(zzaw).getPackageInfo(zzv.zzt.zzaw().getPackageName(), 64).signatures;
                        if (signatureArr == null || signatureArr.length <= 0) {
                            zzv.zzt.zzaA().zzk().zza("Could not get signatures");
                        } else {
                            j8 = zzlo.zzp(zzF.digest(signatureArr[0].toByteArray()));
                        }
                    } else {
                        j8 = 0;
                    }
                    j5 = j8;
                } catch (PackageManager.NameNotFoundException e) {
                    zzv.zzt.zzaA().zzd().zzb("Package name not found", e);
                    j5 = 0;
                }
            } else {
                j5 = 0;
            }
            this.zzf = j5;
            j = j5;
        } else {
            j = j7;
        }
        boolean zzJ = this.zzt.zzJ();
        boolean z2 = !this.zzt.zzm().zzl;
        zzg();
        if (!this.zzt.zzJ()) {
            str2 = null;
        } else {
            zzrg.zzc();
            if (this.zzt.zzf().zzs((String) null, zzeh.zzac)) {
                this.zzt.zzaA().zzj().zza("Disabled IID for tests.");
                str2 = null;
            } else {
                try {
                    Class<?> loadClass = this.zzt.zzaw().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
                    if (loadClass == null) {
                        str2 = null;
                    } else {
                        try {
                            Object invoke = loadClass.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke((Object) null, new Object[]{this.zzt.zzaw()});
                            if (invoke == null) {
                                str2 = null;
                            } else {
                                try {
                                    str2 = (String) loadClass.getDeclaredMethod("getFirebaseInstanceId", new Class[0]).invoke(invoke, new Object[0]);
                                } catch (Exception e2) {
                                    this.zzt.zzaA().zzl().zza("Failed to retrieve Firebase Instance Id");
                                    str2 = null;
                                }
                            }
                        } catch (Exception e3) {
                            this.zzt.zzaA().zzm().zza("Failed to obtain Firebase Analytics instance");
                            str2 = null;
                        }
                    }
                } catch (ClassNotFoundException e4) {
                    str2 = null;
                }
            }
        }
        zzge zzge = this.zzt;
        long zza2 = zzge.zzm().zzc.zza();
        if (zza2 == 0) {
            str3 = zzl2;
            j2 = zzge.zzc;
        } else {
            str3 = zzl2;
            j2 = Math.min(zzge.zzc, zza2);
        }
        zza();
        int i4 = this.zzj;
        boolean zzr = this.zzt.zzf().zzr();
        zzfj zzm3 = this.zzt.zzm();
        zzm3.zzg();
        boolean z3 = zzm3.zza().getBoolean("deferred_analytics_collection", false);
        zza();
        String str7 = this.zzl;
        Boolean zzk2 = this.zzt.zzf().zzk("google_analytics_default_allow_ad_personalization_signals");
        if (zzk2 == null) {
            bool = null;
        } else {
            bool = Boolean.valueOf(!zzk2.booleanValue());
        }
        long j9 = this.zzg;
        List list = this.zzh;
        String zzh2 = this.zzt.zzm().zzc().zzh();
        if (this.zzi == null) {
            this.zzi = this.zzt.zzv().zzC();
        }
        List list2 = list;
        String str8 = this.zzi;
        zzqr.zzc();
        long j10 = j9;
        if (this.zzt.zzf().zzs((String) null, zzeh.zzan)) {
            zzg();
            j3 = 0;
            if (this.zzn == 0) {
                i = i4;
            } else {
                i = i4;
                long currentTimeMillis = this.zzt.zzax().currentTimeMillis() - this.zzn;
                if (this.zzm != null && currentTimeMillis > CostTimeUtil.DAY && this.zzo == null) {
                    zzo();
                }
            }
            if (this.zzm == null) {
                zzo();
            }
            str4 = this.zzm;
        } else {
            i = i4;
            j3 = 0;
            str4 = null;
        }
        zzag zzf2 = this.zzt.zzf();
        zzge zzge2 = zzf2.zzt;
        Boolean zzk3 = zzf2.zzk("google_analytics_sgtm_upload_enabled");
        if (zzk3 == null) {
            z = false;
        } else {
            z = zzk3.booleanValue();
        }
        zzpw.zzc();
        if (this.zzt.zzf().zzs((String) null, zzeh.zzaD)) {
            zzlo zzv2 = this.zzt.zzv();
            String zzl3 = zzl();
            if (zzv2.zzt.zzaw().getPackageManager() != null) {
                try {
                    i3 = 0;
                    try {
                        ApplicationInfo applicationInfo = Wrappers.packageManager(zzv2.zzt.zzaw()).getApplicationInfo(zzl3, 0);
                        if (applicationInfo != null) {
                            i2 = applicationInfo.targetSdkVersion;
                        } else {
                            i2 = 0;
                        }
                    } catch (PackageManager.NameNotFoundException e5) {
                        zzv2.zzt.zzay();
                        zzv2.zzt.zzaA().zzi().zzb("PackageManager failed to find running app: app_id", zzl3);
                        i2 = i3;
                        j3 = (long) i2;
                        j4 = j3;
                        return new zzq(str3, zzm2, str5, j6, str6, 77000, j, str, zzJ, z2, str2, 0, j2, i, zzr, z3, str7, bool, j10, list2, (String) null, zzh2, str8, str4, z, j4);
                    }
                } catch (PackageManager.NameNotFoundException e6) {
                    i3 = 0;
                    zzv2.zzt.zzay();
                    zzv2.zzt.zzaA().zzi().zzb("PackageManager failed to find running app: app_id", zzl3);
                    i2 = i3;
                    j3 = (long) i2;
                    j4 = j3;
                    return new zzq(str3, zzm2, str5, j6, str6, 77000, j, str, zzJ, z2, str2, 0, j2, i, zzr, z3, str7, bool, j10, list2, (String) null, zzh2, str8, str4, z, j4);
                }
                j3 = (long) i2;
            }
            j4 = j3;
        } else {
            j4 = j3;
        }
        return new zzq(str3, zzm2, str5, j6, str6, 77000, j, str, zzJ, z2, str2, 0, j2, i, zzr, z3, str7, bool, j10, list2, (String) null, zzh2, str8, str4, z, j4);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzk() {
        zza();
        return this.zzl;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzl() {
        zza();
        Preconditions.checkNotNull(this.zza);
        return this.zza;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final String zzm() {
        zzg();
        zza();
        Preconditions.checkNotNull(this.zzk);
        return this.zzk;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final List zzn() {
        return this.zzh;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzo() {
        String str;
        String str2;
        zzg();
        if (!this.zzt.zzm().zzc().zzi(zzah.ANALYTICS_STORAGE)) {
            this.zzt.zzaA().zzc().zza("Analytics Storage consent is not granted");
            str = null;
        } else {
            byte[] bArr = new byte[16];
            this.zzt.zzv().zzG().nextBytes(bArr);
            str = String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, bArr)});
        }
        zzes zzc2 = this.zzt.zzaA().zzc();
        Object[] objArr = new Object[1];
        if (str == null) {
            str2 = BuildConfig.TRAVIS;
        } else {
            str2 = "not null";
        }
        objArr[0] = str2;
        zzc2.zza(String.format("Resetting session stitching token to %s", objArr));
        this.zzm = str;
        this.zzn = this.zzt.zzax().currentTimeMillis();
    }

    /* access modifiers changed from: package-private */
    public final boolean zzp(String str) {
        String str2 = this.zzo;
        boolean z = false;
        if (str2 != null && !str2.equals(str)) {
            z = true;
        }
        this.zzo = str;
        return z;
    }
}
