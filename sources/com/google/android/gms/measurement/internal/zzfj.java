package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.internal.Preconditions;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzfj extends zzgy {
    static final Pair zza = new Pair("", 0L);
    public zzfh zzb;
    public final zzff zzc = new zzff(this, "first_open_time", 0);
    public final zzff zzd = new zzff(this, "app_install_time", 0);
    public final zzfi zze = new zzfi(this, "app_instance_id", (String) null);
    public final zzff zzf = new zzff(this, "session_timeout", 1800000);
    public final zzfd zzg = new zzfd(this, "start_new_session", true);
    public final zzfi zzh = new zzfi(this, "non_personalized_ads", (String) null);
    public final zzfd zzi = new zzfd(this, "allow_remote_dynamite", false);
    public final zzff zzj = new zzff(this, "last_pause_time", 0);
    public final zzff zzk = new zzff(this, "session_id", 0);
    public boolean zzl;
    public final zzfd zzm = new zzfd(this, "app_backgrounded", false);
    public final zzfd zzn = new zzfd(this, "deep_link_retrieval_complete", false);
    public final zzff zzo = new zzff(this, "deep_link_retrieval_attempts", 0);
    public final zzfi zzp = new zzfi(this, "firebase_feature_rollouts", (String) null);
    public final zzfi zzq = new zzfi(this, "deferred_attribution_cache", (String) null);
    public final zzff zzr = new zzff(this, "deferred_attribution_cache_timestamp", 0);
    public final zzfe zzs = new zzfe(this, "default_event_parameters", (Bundle) null);
    private SharedPreferences zzu;
    private String zzv;
    private boolean zzw;
    private long zzx;

    zzfj(zzge zzge) {
        super(zzge);
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public final SharedPreferences zza() {
        zzg();
        zzv();
        Preconditions.checkNotNull(this.zzu);
        return this.zzu;
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    @EnsuresNonNull.List({@EnsuresNonNull({"this.preferences"}), @EnsuresNonNull({"this.monitoringSample"})})
    public final void zzaC() {
        SharedPreferences sharedPreferences = this.zzt.zzaw().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzu = sharedPreferences;
        boolean z = sharedPreferences.getBoolean("has_been_opened", false);
        this.zzl = z;
        if (!z) {
            SharedPreferences.Editor edit = this.zzu.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
        this.zzt.zzf();
        this.zzb = new zzfh(this, "health_monitor", Math.max(0, ((Long) zzeh.zzc.zza((Object) null)).longValue()), (zzfg) null);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final Pair zzb(String str) {
        zzg();
        long elapsedRealtime = this.zzt.zzax().elapsedRealtime();
        String str2 = this.zzv;
        if (str2 != null && elapsedRealtime < this.zzx) {
            return new Pair(str2, Boolean.valueOf(this.zzw));
        }
        this.zzx = elapsedRealtime + this.zzt.zzf().zzi(str, zzeh.zza);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzaw());
            this.zzv = "";
            String id = advertisingIdInfo.getId();
            if (id != null) {
                this.zzv = id;
            }
            this.zzw = advertisingIdInfo.isLimitAdTrackingEnabled();
        } catch (Exception e) {
            this.zzt.zzaA().zzc().zzb("Unable to get advertising id", e);
            this.zzv = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(this.zzv, Boolean.valueOf(this.zzw));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final zzai zzc() {
        zzg();
        return zzai.zzb(zza().getString("consent_settings", "G1"));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final Boolean zzd() {
        zzg();
        if (zza().contains("measurement_enabled")) {
            return Boolean.valueOf(zza().getBoolean("measurement_enabled", true));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return true;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzh(Boolean bool) {
        zzg();
        SharedPreferences.Editor edit = zza().edit();
        if (bool != null) {
            edit.putBoolean("measurement_enabled", bool.booleanValue());
        } else {
            edit.remove("measurement_enabled");
        }
        edit.apply();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final void zzi(boolean z) {
        zzg();
        this.zzt.zzaA().zzj().zzb("App measurement setting deferred collection", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zza().edit();
        edit.putBoolean("deferred_analytics_collection", z);
        edit.apply();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzj() {
        SharedPreferences sharedPreferences = this.zzu;
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.contains("deferred_analytics_collection");
    }

    /* access modifiers changed from: package-private */
    public final boolean zzk(long j) {
        return j - this.zzf.zza() > this.zzj.zza();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzl(int i) {
        return zzai.zzj(i, zza().getInt("consent_source", 100));
    }
}
