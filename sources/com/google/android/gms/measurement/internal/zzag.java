package com.google.android.gms.measurement.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzag extends zzgx {
    private Boolean zza;
    private zzaf zzb = zzae.zza;
    private Boolean zzc;

    zzag(zzge zzge) {
        super(zzge);
    }

    public static final long zzA() {
        return ((Long) zzeh.zzD.zza((Object) null)).longValue();
    }

    private final String zzB(String str, String str2) {
        Class<String> cls = String.class;
        try {
            String str3 = (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{cls, cls}).invoke((Object) null, new Object[]{str, ""});
            Preconditions.checkNotNull(str3);
            return str3;
        } catch (ClassNotFoundException e) {
            this.zzt.zzaA().zzd().zzb("Could not find SystemProperties class", e);
            return "";
        } catch (NoSuchMethodException e2) {
            this.zzt.zzaA().zzd().zzb("Could not find SystemProperties.get() method", e2);
            return "";
        } catch (IllegalAccessException e3) {
            this.zzt.zzaA().zzd().zzb("Could not access SystemProperties.get()", e3);
            return "";
        } catch (InvocationTargetException e4) {
            this.zzt.zzaA().zzd().zzb("SystemProperties.get() threw an exception", e4);
            return "";
        }
    }

    public static final long zzz() {
        return ((Long) zzeh.zzd.zza((Object) null)).longValue();
    }

    @WorkerThread
    public final double zza(String str, zzeg zzeg) {
        if (str == null) {
            return ((Double) zzeg.zza((Object) null)).doubleValue();
        }
        String zza2 = this.zzb.zza(str, zzeg.zzb());
        if (TextUtils.isEmpty(zza2)) {
            return ((Double) zzeg.zza((Object) null)).doubleValue();
        }
        try {
            return ((Double) zzeg.zza(Double.valueOf(Double.parseDouble(zza2)))).doubleValue();
        } catch (NumberFormatException e) {
            return ((Double) zzeg.zza((Object) null)).doubleValue();
        }
    }

    /* access modifiers changed from: package-private */
    public final int zzb(@Size(min = 1) String str) {
        return zzf(str, zzeh.zzH, 500, WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
    }

    public final int zzc() {
        return this.zzt.zzv().zzai(201500000, true) ? 100 : 25;
    }

    public final int zzd(@Size(min = 1) String str) {
        return zzf(str, zzeh.zzI, 25, 100);
    }

    @WorkerThread
    public final int zze(String str, zzeg zzeg) {
        if (str == null) {
            return ((Integer) zzeg.zza((Object) null)).intValue();
        }
        String zza2 = this.zzb.zza(str, zzeg.zzb());
        if (TextUtils.isEmpty(zza2)) {
            return ((Integer) zzeg.zza((Object) null)).intValue();
        }
        try {
            return ((Integer) zzeg.zza(Integer.valueOf(Integer.parseInt(zza2)))).intValue();
        } catch (NumberFormatException e) {
            return ((Integer) zzeg.zza((Object) null)).intValue();
        }
    }

    @WorkerThread
    public final int zzf(String str, zzeg zzeg, int i, int i2) {
        return Math.max(Math.min(zze(str, zzeg), i2), i);
    }

    public final long zzh() {
        this.zzt.zzay();
        return 77000;
    }

    @WorkerThread
    public final long zzi(String str, zzeg zzeg) {
        if (str == null) {
            return ((Long) zzeg.zza((Object) null)).longValue();
        }
        String zza2 = this.zzb.zza(str, zzeg.zzb());
        if (TextUtils.isEmpty(zza2)) {
            return ((Long) zzeg.zza((Object) null)).longValue();
        }
        try {
            return ((Long) zzeg.zza(Long.valueOf(Long.parseLong(zza2)))).longValue();
        } catch (NumberFormatException e) {
            return ((Long) zzeg.zza((Object) null)).longValue();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final Bundle zzj() {
        try {
            if (this.zzt.zzaw().getPackageManager() == null) {
                this.zzt.zzaA().zzd().zza("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(this.zzt.zzaw()).getApplicationInfo(this.zzt.zzaw().getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            this.zzt.zzaA().zzd().zza("Failed to load metadata: ApplicationInfo is null");
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            this.zzt.zzaA().zzd().zzb("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final Boolean zzk(@Size(min = 1) String str) {
        Preconditions.checkNotEmpty(str);
        Bundle zzj = zzj();
        if (zzj == null) {
            this.zzt.zzaA().zzd().zza("Failed to load metadata: Metadata bundle is null");
            return null;
        } else if (!zzj.containsKey(str)) {
            return null;
        } else {
            return Boolean.valueOf(zzj.getBoolean(str));
        }
    }

    public final String zzl() {
        return zzB("debug.firebase.analytics.app", "");
    }

    public final String zzm() {
        return zzB("debug.deferred.deeplink", "");
    }

    /* access modifiers changed from: package-private */
    public final String zzn() {
        this.zzt.zzay();
        return "FA";
    }

    @WorkerThread
    public final String zzo(String str, zzeg zzeg) {
        if (str == null) {
            return (String) zzeg.zza((Object) null);
        }
        return (String) zzeg.zza(this.zzb.zza(str, zzeg.zzb()));
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final List zzp(@Size(min = 1) String str) {
        Integer num;
        Preconditions.checkNotEmpty("analytics.safelisted_events");
        Bundle zzj = zzj();
        if (zzj == null) {
            this.zzt.zzaA().zzd().zza("Failed to load metadata: Metadata bundle is null");
            num = null;
        } else if (!zzj.containsKey("analytics.safelisted_events")) {
            num = null;
        } else {
            num = Integer.valueOf(zzj.getInt("analytics.safelisted_events"));
        }
        if (num == null) {
            return null;
        }
        try {
            String[] stringArray = this.zzt.zzaw().getResources().getStringArray(num.intValue());
            if (stringArray == null) {
                return null;
            }
            return Arrays.asList(stringArray);
        } catch (Resources.NotFoundException e) {
            this.zzt.zzaA().zzd().zzb("Failed to load string array from metadata: resource not found", e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzq(zzaf zzaf) {
        this.zzb = zzaf;
    }

    public final boolean zzr() {
        Boolean zzk = zzk("google_analytics_adid_collection_enabled");
        return zzk == null || zzk.booleanValue();
    }

    @WorkerThread
    public final boolean zzs(String str, zzeg zzeg) {
        if (str == null) {
            return ((Boolean) zzeg.zza((Object) null)).booleanValue();
        }
        String zza2 = this.zzb.zza(str, zzeg.zzb());
        if (TextUtils.isEmpty(zza2)) {
            return ((Boolean) zzeg.zza((Object) null)).booleanValue();
        }
        return ((Boolean) zzeg.zza(Boolean.valueOf("1".equals(zza2)))).booleanValue();
    }

    public final boolean zzt(String str) {
        return "1".equals(this.zzb.zza(str, "gaia_collection_enabled"));
    }

    public final boolean zzu() {
        Boolean zzk = zzk("google_analytics_automatic_screen_reporting_enabled");
        return zzk == null || zzk.booleanValue();
    }

    public final boolean zzv() {
        this.zzt.zzay();
        Boolean zzk = zzk("firebase_analytics_collection_deactivated");
        return zzk != null && zzk.booleanValue();
    }

    public final boolean zzw(String str) {
        return "1".equals(this.zzb.zza(str, "measurement.event_sampling_enabled"));
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final boolean zzx() {
        if (this.zza == null) {
            Boolean zzk = zzk("app_measurement_lite");
            this.zza = zzk;
            if (zzk == null) {
                this.zza = false;
            }
        }
        if (this.zza.booleanValue() || !this.zzt.zzN()) {
            return true;
        }
        return false;
    }

    @EnsuresNonNull({"this.isMainProcess"})
    public final boolean zzy() {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    ApplicationInfo applicationInfo = this.zzt.zzaw().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = false;
                        if (str != null && str.equals(myProcessName)) {
                            z = true;
                        }
                        this.zzc = Boolean.valueOf(z);
                    }
                    if (this.zzc == null) {
                        this.zzc = Boolean.TRUE;
                        this.zzt.zzaA().zzd().zza("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzc.booleanValue();
    }
}
