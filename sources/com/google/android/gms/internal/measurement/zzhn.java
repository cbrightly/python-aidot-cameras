package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhn implements zzhk {
    @GuardedBy("GservicesLoader.class")
    private static zzhn zza;
    @Nullable
    private final Context zzb;
    @Nullable
    private final ContentObserver zzc;

    private zzhn() {
        this.zzb = null;
        this.zzc = null;
    }

    private zzhn(Context context) {
        this.zzb = context;
        zzhm zzhm = new zzhm(this, (Handler) null);
        this.zzc = zzhm;
        context.getContentResolver().registerContentObserver(zzha.zza, true, zzhm);
    }

    static zzhn zza(Context context) {
        zzhn zzhn;
        synchronized (zzhn.class) {
            if (zza == null) {
                zza = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzhn(context) : new zzhn();
            }
            zzhn = zza;
        }
        return zzhn;
    }

    static synchronized void zze() {
        Context context;
        synchronized (zzhn.class) {
            zzhn zzhn = zza;
            if (!(zzhn == null || (context = zzhn.zzb) == null || zzhn.zzc == null)) {
                context.getContentResolver().unregisterContentObserver(zza.zzc);
            }
            zza = null;
        }
    }

    @Nullable
    /* renamed from: zzc */
    public final String zzb(String str) {
        Context context = this.zzb;
        if (context == null || zzhb.zza(context)) {
            return null;
        }
        try {
            return (String) zzhi.zza(new zzhl(this, str));
        } catch (IllegalStateException | NullPointerException | SecurityException e) {
            Log.e("GservicesLoader", "Unable to read GServices for: ".concat(String.valueOf(str)), e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ String zzd(String str) {
        return zzha.zza(this.zzb.getContentResolver(), str, (String) null);
    }
}
