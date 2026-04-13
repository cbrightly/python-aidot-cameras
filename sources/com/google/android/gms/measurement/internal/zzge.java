package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzcl;
import com.google.android.gms.internal.measurement.zzib;
import com.google.android.gms.internal.measurement.zzos;
import com.google.firebase.messaging.Constants;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzge implements zzgz {
    private static volatile zzge zzd;
    private zzel zzA;
    private boolean zzB = false;
    private Boolean zzC;
    private long zzD;
    private volatile Boolean zzE;
    private volatile boolean zzF;
    private int zzG;
    private final AtomicInteger zzH = new AtomicInteger(0);
    @VisibleForTesting
    protected Boolean zza;
    @VisibleForTesting
    protected Boolean zzb;
    @VisibleForTesting
    final long zzc;
    private final Context zze;
    private final String zzf;
    private final String zzg;
    private final String zzh;
    private final boolean zzi;
    private final zzab zzj;
    private final zzag zzk;
    private final zzfj zzl;
    private final zzeu zzm;
    private final zzgb zzn;
    private final zzko zzo;
    private final zzlo zzp;
    private final zzep zzq;
    private final Clock zzr;
    private final zziy zzs;
    private final zzij zzt;
    private final zzd zzu;
    private final zzin zzv;
    private final String zzw;
    private zzen zzx;
    private zzjy zzy;
    private zzaq zzz;

    zzge(zzhh zzhh) {
        long j;
        Bundle bundle;
        boolean z = false;
        Preconditions.checkNotNull(zzhh);
        Context context = zzhh.zza;
        zzab zzab = new zzab(context);
        this.zzj = zzab;
        zzee.zza = zzab;
        this.zze = context;
        this.zzf = zzhh.zzb;
        this.zzg = zzhh.zzc;
        this.zzh = zzhh.zzd;
        this.zzi = zzhh.zzh;
        this.zzE = zzhh.zze;
        this.zzw = zzhh.zzj;
        this.zzF = true;
        zzcl zzcl = zzhh.zzg;
        if (!(zzcl == null || (bundle = zzcl.zzg) == null)) {
            Object obj = bundle.get("measurementEnabled");
            if (obj instanceof Boolean) {
                this.zza = (Boolean) obj;
            }
            Object obj2 = zzcl.zzg.get("measurementDeactivated");
            if (obj2 instanceof Boolean) {
                this.zzb = (Boolean) obj2;
            }
        }
        zzib.zzd(context);
        Clock instance = DefaultClock.getInstance();
        this.zzr = instance;
        Long l = zzhh.zzi;
        if (l != null) {
            j = l.longValue();
        } else {
            j = instance.currentTimeMillis();
        }
        this.zzc = j;
        this.zzk = new zzag(this);
        zzfj zzfj = new zzfj(this);
        zzfj.zzw();
        this.zzl = zzfj;
        zzeu zzeu = new zzeu(this);
        zzeu.zzw();
        this.zzm = zzeu;
        zzlo zzlo = new zzlo(this);
        zzlo.zzw();
        this.zzp = zzlo;
        this.zzq = new zzep(new zzhg(zzhh, this));
        this.zzu = new zzd(this);
        zziy zziy = new zziy(this);
        zziy.zzb();
        this.zzs = zziy;
        zzij zzij = new zzij(this);
        zzij.zzb();
        this.zzt = zzij;
        zzko zzko = new zzko(this);
        zzko.zzb();
        this.zzo = zzko;
        zzin zzin = new zzin(this);
        zzin.zzw();
        this.zzv = zzin;
        zzgb zzgb = new zzgb(this);
        zzgb.zzw();
        this.zzn = zzgb;
        zzcl zzcl2 = zzhh.zzg;
        z = (zzcl2 == null || zzcl2.zzb == 0) ? true : z;
        if (context.getApplicationContext() instanceof Application) {
            zzij zzq2 = zzq();
            if (zzq2.zzt.zze.getApplicationContext() instanceof Application) {
                Application application = (Application) zzq2.zzt.zze.getApplicationContext();
                if (zzq2.zza == null) {
                    zzq2.zza = new zzii(zzq2);
                }
                if (z) {
                    application.unregisterActivityLifecycleCallbacks(zzq2.zza);
                    application.registerActivityLifecycleCallbacks(zzq2.zza);
                    zzq2.zzt.zzaA().zzj().zza("Registered activity lifecycle callback");
                }
            }
        } else {
            zzaA().zzk().zza("Application context is not an Application");
        }
        zzgb.zzp(new zzgd(this, zzhh));
    }

    static /* bridge */ /* synthetic */ void zzA(zzge zzge, zzhh zzhh) {
        zzge.zzaB().zzg();
        zzge.zzk.zzn();
        zzaq zzaq = new zzaq(zzge);
        zzaq.zzw();
        zzge.zzz = zzaq;
        zzel zzel = new zzel(zzge, zzhh.zzf);
        zzel.zzb();
        zzge.zzA = zzel;
        zzen zzen = new zzen(zzge);
        zzen.zzb();
        zzge.zzx = zzen;
        zzjy zzjy = new zzjy(zzge);
        zzjy.zzb();
        zzge.zzy = zzjy;
        zzge.zzp.zzx();
        zzge.zzl.zzx();
        zzge.zzA.zzc();
        zzes zzi2 = zzge.zzaA().zzi();
        zzge.zzk.zzh();
        zzi2.zzb("App measurement initialized, version", 77000L);
        zzge.zzaA().zzi().zza("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String zzl2 = zzel.zzl();
        if (TextUtils.isEmpty(zzge.zzf)) {
            if (zzge.zzv().zzaf(zzl2)) {
                zzge.zzaA().zzi().zza("Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.");
            } else {
                zzge.zzaA().zzi().zza("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(String.valueOf(zzl2)));
            }
        }
        zzge.zzaA().zzc().zza("Debug-level message logging enabled");
        if (zzge.zzG != zzge.zzH.get()) {
            zzge.zzaA().zzd().zzc("Not all components initialized", Integer.valueOf(zzge.zzG), Integer.valueOf(zzge.zzH.get()));
        }
        zzge.zzB = true;
    }

    static final void zzO() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    private static final void zzP(zzgx zzgx) {
        if (zzgx == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static final void zzQ(zzf zzf2) {
        if (zzf2 == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzf2.zze()) {
            throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(String.valueOf(zzf2.getClass()))));
        }
    }

    private static final void zzR(zzgy zzgy) {
        if (zzgy == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzgy.zzy()) {
            throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(String.valueOf(zzgy.getClass()))));
        }
    }

    public static zzge zzp(Context context, zzcl zzcl, Long l) {
        Bundle bundle;
        if (zzcl != null && (zzcl.zze == null || zzcl.zzf == null)) {
            zzcl = new zzcl(zzcl.zza, zzcl.zzb, zzcl.zzc, zzcl.zzd, (String) null, (String) null, zzcl.zzg, (String) null);
        }
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzd == null) {
            synchronized (zzge.class) {
                if (zzd == null) {
                    zzd = new zzge(new zzhh(context, zzcl, l));
                }
            }
        } else if (!(zzcl == null || (bundle = zzcl.zzg) == null || !bundle.containsKey("dataCollectionDefaultEnabled"))) {
            Preconditions.checkNotNull(zzd);
            zzd.zzE = Boolean.valueOf(zzcl.zzg.getBoolean("dataCollectionDefaultEnabled"));
        }
        Preconditions.checkNotNull(zzd);
        return zzd;
    }

    /* access modifiers changed from: package-private */
    public final void zzB() {
        this.zzH.incrementAndGet();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzC(String str, int i, Throwable th, byte[] bArr, Map map) {
        if (!(i == 200 || i == 204)) {
            if (i == 304) {
                i = 304;
            }
            zzaA().zzk().zzc("Network Request for Deferred Deep Link failed. response, exception", Integer.valueOf(i), th);
        }
        if (th == null) {
            zzm().zzn.zza(true);
            if (bArr == null || bArr.length == 0) {
                zzaA().zzc().zza("Deferred Deep Link response empty.");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr));
                String optString = jSONObject.optString("deeplink", "");
                String optString2 = jSONObject.optString("gclid", "");
                double optDouble = jSONObject.optDouble("timestamp", 0.0d);
                if (TextUtils.isEmpty(optString)) {
                    zzaA().zzc().zza("Deferred Deep Link is empty.");
                    return;
                }
                zzlo zzv2 = zzv();
                zzge zzge = zzv2.zzt;
                if (!TextUtils.isEmpty(optString)) {
                    List<ResolveInfo> queryIntentActivities = zzv2.zzt.zze.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(optString)), 0);
                    if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("gclid", optString2);
                        bundle.putString("_cis", "ddp");
                        this.zzt.zzG("auto", Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundle);
                        zzlo zzv3 = zzv();
                        if (!TextUtils.isEmpty(optString)) {
                            try {
                                SharedPreferences.Editor edit = zzv3.zzt.zze.getSharedPreferences("google.analytics.deferred.deeplink.prefs", 0).edit();
                                edit.putString("deeplink", optString);
                                edit.putLong("timestamp", Double.doubleToRawLongBits(optDouble));
                                if (edit.commit()) {
                                    zzv3.zzt.zze.sendBroadcast(new Intent("android.google.analytics.action.DEEPLINK_ACTION"));
                                    return;
                                }
                                return;
                            } catch (RuntimeException e) {
                                zzv3.zzt.zzaA().zzd().zzb("Failed to persist Deferred Deep Link. exception", e);
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
                zzaA().zzk().zzc("Deferred Deep Link validation failed. gclid, deep link", optString2, optString);
                return;
            } catch (JSONException e2) {
                zzaA().zzd().zzb("Failed to parse the Deferred Deep Link response. exception", e2);
                return;
            }
        }
        zzaA().zzk().zzc("Network Request for Deferred Deep Link failed. response, exception", Integer.valueOf(i), th);
    }

    /* access modifiers changed from: package-private */
    public final void zzD() {
        this.zzG++;
    }

    @WorkerThread
    public final void zzE() {
        zzaB().zzg();
        zzR(zzr());
        String zzl2 = zzh().zzl();
        Pair zzb2 = zzm().zzb(zzl2);
        if (!this.zzk.zzr() || ((Boolean) zzb2.second).booleanValue() || TextUtils.isEmpty((CharSequence) zzb2.first)) {
            zzaA().zzc().zza("ADID unavailable to retrieve Deferred Deep Link. Skipping");
            return;
        }
        zzin zzr2 = zzr();
        zzr2.zzv();
        ConnectivityManager connectivityManager = (ConnectivityManager) zzr2.zzt.zze.getSystemService("connectivity");
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            try {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (SecurityException e) {
            }
        }
        if (networkInfo == null || !networkInfo.isConnected()) {
            zzaA().zzk().zza("Network is not available for Deferred Deep Link request. Skipping");
            return;
        }
        zzlo zzv2 = zzv();
        zzh().zzt.zzk.zzh();
        URL zzE2 = zzv2.zzE(77000, zzl2, (String) zzb2.first, zzm().zzo.zza() - 1);
        if (zzE2 != null) {
            zzin zzr3 = zzr();
            zzgc zzgc = new zzgc(this);
            zzr3.zzg();
            zzr3.zzv();
            Preconditions.checkNotNull(zzE2);
            Preconditions.checkNotNull(zzgc);
            zzr3.zzt.zzaB().zzo(new zzim(zzr3, zzl2, zzE2, (byte[]) null, (Map) null, zzgc));
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzF(boolean z) {
        this.zzE = Boolean.valueOf(z);
    }

    @WorkerThread
    public final void zzG(boolean z) {
        zzaB().zzg();
        this.zzF = z;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final void zzH(zzcl zzcl) {
        zzai zzai;
        zzaB().zzg();
        zzai zzc2 = zzm().zzc();
        zzfj zzm2 = zzm();
        zzge zzge = zzm2.zzt;
        zzm2.zzg();
        int i = 100;
        int i2 = zzm2.zza().getInt("consent_source", 100);
        zzag zzag = this.zzk;
        zzge zzge2 = zzag.zzt;
        Boolean zzk2 = zzag.zzk("google_analytics_default_allow_ad_storage");
        zzag zzag2 = this.zzk;
        zzge zzge3 = zzag2.zzt;
        Boolean zzk3 = zzag2.zzk("google_analytics_default_allow_analytics_storage");
        if (!(zzk2 == null && zzk3 == null) && zzm().zzl(-10)) {
            zzai = new zzai(zzk2, zzk3);
            i = -10;
        } else if (TextUtils.isEmpty(zzh().zzm()) || !(i2 == 0 || i2 == 30 || i2 == 10 || i2 == 30 || i2 == 30 || i2 == 40)) {
            if (TextUtils.isEmpty(zzh().zzm()) && zzcl != null && zzcl.zzg != null && zzm().zzl(30)) {
                zzai = zzai.zza(zzcl.zzg);
                if (!zzai.equals(zzai.zza)) {
                    i = 30;
                }
            }
            zzai = null;
        } else {
            zzq().zzS(zzai.zza, -10, this.zzc);
            zzai = null;
        }
        if (zzai != null) {
            zzq().zzS(zzai, i, this.zzc);
            zzc2 = zzai;
        }
        zzq().zzV(zzc2);
        if (zzm().zzc.zza() == 0) {
            zzaA().zzj().zzb("Persisting first open", Long.valueOf(this.zzc));
            zzm().zzc.zzb(this.zzc);
        }
        zzq().zzb.zzc();
        if (zzM()) {
            if (!TextUtils.isEmpty(zzh().zzm()) || !TextUtils.isEmpty(zzh().zzk())) {
                zzlo zzv2 = zzv();
                String zzm3 = zzh().zzm();
                zzfj zzm4 = zzm();
                zzm4.zzg();
                String string = zzm4.zza().getString("gmp_app_id", (String) null);
                String zzk4 = zzh().zzk();
                zzfj zzm5 = zzm();
                zzm5.zzg();
                if (zzv2.zzao(zzm3, string, zzk4, zzm5.zza().getString("admob_app_id", (String) null))) {
                    zzaA().zzi().zza("Rechecking which service to use due to a GMP App Id change");
                    zzfj zzm6 = zzm();
                    zzm6.zzg();
                    Boolean zzd2 = zzm6.zzd();
                    SharedPreferences.Editor edit = zzm6.zza().edit();
                    edit.clear();
                    edit.apply();
                    if (zzd2 != null) {
                        zzm6.zzh(zzd2);
                    }
                    zzi().zzj();
                    this.zzy.zzs();
                    this.zzy.zzr();
                    zzm().zzc.zzb(this.zzc);
                    zzm().zze.zzb((String) null);
                }
                zzfj zzm7 = zzm();
                String zzm8 = zzh().zzm();
                zzm7.zzg();
                SharedPreferences.Editor edit2 = zzm7.zza().edit();
                edit2.putString("gmp_app_id", zzm8);
                edit2.apply();
                zzfj zzm9 = zzm();
                String zzk5 = zzh().zzk();
                zzm9.zzg();
                SharedPreferences.Editor edit3 = zzm9.zza().edit();
                edit3.putString("admob_app_id", zzk5);
                edit3.apply();
            }
            if (!zzm().zzc().zzi(zzah.ANALYTICS_STORAGE)) {
                zzm().zze.zzb((String) null);
            }
            zzq().zzO(zzm().zze.zza());
            zzos.zzc();
            if (this.zzk.zzs((String) null, zzeh.zzae)) {
                try {
                    zzv().zzt.zze.getClassLoader().loadClass("com.google.firebase.remoteconfig.FirebaseRemoteConfig");
                } catch (ClassNotFoundException e) {
                    if (!TextUtils.isEmpty(zzm().zzp.zza())) {
                        zzaA().zzk().zza("Remote config removed with active feature rollouts");
                        zzm().zzp.zzb((String) null);
                    }
                }
            }
            if (!TextUtils.isEmpty(zzh().zzm()) || !TextUtils.isEmpty(zzh().zzk())) {
                boolean zzJ = zzJ();
                if (!zzm().zzj() && !this.zzk.zzv()) {
                    zzm().zzi(!zzJ);
                }
                if (zzJ) {
                    zzq().zzz();
                }
                zzu().zza.zza();
                zzt().zzu(new AtomicReference());
                zzt().zzH(zzm().zzs.zza());
            }
        } else if (zzJ()) {
            if (!zzv().zzae("android.permission.INTERNET")) {
                zzaA().zzd().zza("App is missing INTERNET permission");
            }
            if (!zzv().zzae("android.permission.ACCESS_NETWORK_STATE")) {
                zzaA().zzd().zza("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!Wrappers.packageManager(this.zze).isCallerInstantApp() && !this.zzk.zzx()) {
                if (!zzlo.zzal(this.zze)) {
                    zzaA().zzd().zza("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzlo.zzam(this.zze, false)) {
                    zzaA().zzd().zza("AppMeasurementService not registered/enabled");
                }
            }
            zzaA().zzd().zza("Uploading is not possible. App measurement disabled");
        }
        zzm().zzi.zza(true);
    }

    @WorkerThread
    public final boolean zzI() {
        return this.zzE != null && this.zzE.booleanValue();
    }

    @WorkerThread
    public final boolean zzJ() {
        return zza() == 0;
    }

    @WorkerThread
    public final boolean zzK() {
        zzaB().zzg();
        return this.zzF;
    }

    @Pure
    public final boolean zzL() {
        return TextUtils.isEmpty(this.zzf);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final boolean zzM() {
        boolean z;
        if (this.zzB) {
            zzaB().zzg();
            Boolean bool = this.zzC;
            if (bool == null || this.zzD == 0 || (!bool.booleanValue() && Math.abs(this.zzr.elapsedRealtime() - this.zzD) > 1000)) {
                this.zzD = this.zzr.elapsedRealtime();
                boolean z2 = true;
                if (zzv().zzae("android.permission.INTERNET")) {
                    if (zzv().zzae("android.permission.ACCESS_NETWORK_STATE")) {
                        if (Wrappers.packageManager(this.zze).isCallerInstantApp() || this.zzk.zzx()) {
                            z = true;
                        } else if (zzlo.zzal(this.zze) && zzlo.zzam(this.zze, false)) {
                            z = true;
                        }
                    }
                    z = false;
                } else {
                    z = false;
                }
                Boolean valueOf = Boolean.valueOf(z);
                this.zzC = valueOf;
                if (valueOf.booleanValue()) {
                    if (!zzv().zzY(zzh().zzm(), zzh().zzk()) && TextUtils.isEmpty(zzh().zzk())) {
                        z2 = false;
                    }
                    this.zzC = Boolean.valueOf(z2);
                }
            }
            return this.zzC.booleanValue();
        }
        throw new IllegalStateException("AppMeasurement is not initialized");
    }

    @Pure
    public final boolean zzN() {
        return this.zzi;
    }

    @WorkerThread
    public final int zza() {
        zzaB().zzg();
        if (this.zzk.zzv()) {
            return 1;
        }
        Boolean bool = this.zzb;
        if (bool != null && bool.booleanValue()) {
            return 2;
        }
        zzaB().zzg();
        if (!this.zzF) {
            return 8;
        }
        Boolean zzd2 = zzm().zzd();
        if (zzd2 == null) {
            zzag zzag = this.zzk;
            zzab zzab = zzag.zzt.zzj;
            Boolean zzk2 = zzag.zzk("firebase_analytics_collection_enabled");
            if (zzk2 == null) {
                Boolean bool2 = this.zza;
                if (bool2 != null) {
                    if (bool2.booleanValue()) {
                        return 0;
                    }
                    return 5;
                } else if (this.zzE == null || this.zzE.booleanValue()) {
                    return 0;
                } else {
                    return 7;
                }
            } else if (zzk2.booleanValue()) {
                return 0;
            } else {
                return 4;
            }
        } else if (zzd2.booleanValue()) {
            return 0;
        } else {
            return 3;
        }
    }

    @Pure
    public final zzeu zzaA() {
        zzR(this.zzm);
        return this.zzm;
    }

    @Pure
    public final zzgb zzaB() {
        zzR(this.zzn);
        return this.zzn;
    }

    @Pure
    public final Context zzaw() {
        return this.zze;
    }

    @Pure
    public final Clock zzax() {
        return this.zzr;
    }

    @Pure
    public final zzab zzay() {
        return this.zzj;
    }

    @Pure
    public final zzd zzd() {
        zzd zzd2 = this.zzu;
        if (zzd2 != null) {
            return zzd2;
        }
        throw new IllegalStateException("Component not created");
    }

    @Pure
    public final zzag zzf() {
        return this.zzk;
    }

    @Pure
    public final zzaq zzg() {
        zzR(this.zzz);
        return this.zzz;
    }

    @Pure
    public final zzel zzh() {
        zzQ(this.zzA);
        return this.zzA;
    }

    @Pure
    public final zzen zzi() {
        zzQ(this.zzx);
        return this.zzx;
    }

    @Pure
    public final zzep zzj() {
        return this.zzq;
    }

    public final zzeu zzl() {
        zzeu zzeu = this.zzm;
        if (zzeu == null || !zzeu.zzy()) {
            return null;
        }
        return zzeu;
    }

    @Pure
    public final zzfj zzm() {
        zzP(this.zzl);
        return this.zzl;
    }

    /* access modifiers changed from: package-private */
    @SideEffectFree
    public final zzgb zzo() {
        return this.zzn;
    }

    @Pure
    public final zzij zzq() {
        zzQ(this.zzt);
        return this.zzt;
    }

    @Pure
    public final zzin zzr() {
        zzR(this.zzv);
        return this.zzv;
    }

    @Pure
    public final zziy zzs() {
        zzQ(this.zzs);
        return this.zzs;
    }

    @Pure
    public final zzjy zzt() {
        zzQ(this.zzy);
        return this.zzy;
    }

    @Pure
    public final zzko zzu() {
        zzQ(this.zzo);
        return this.zzo;
    }

    @Pure
    public final zzlo zzv() {
        zzP(this.zzp);
        return this.zzp;
    }

    @Pure
    public final String zzw() {
        return this.zzf;
    }

    @Pure
    public final String zzx() {
        return this.zzg;
    }

    @Pure
    public final String zzy() {
        return this.zzh;
    }

    @Pure
    public final String zzz() {
        return this.zzw;
    }
}
