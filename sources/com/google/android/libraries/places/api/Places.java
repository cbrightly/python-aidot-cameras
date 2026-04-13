package com.google.android.libraries.places.api;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.internal.zzgg;
import com.google.android.libraries.places.internal.zzgh;
import com.google.android.libraries.places.internal.zzgi;
import com.google.android.libraries.places.internal.zzgk;
import com.google.android.libraries.places.internal.zzgr;
import com.google.android.libraries.places.internal.zzgt;
import com.google.android.libraries.places.internal.zziy;
import java.util.Locale;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class Places {
    private static final zzgk zza = new zzgk();
    @Nullable
    private static volatile zzgi zzb;

    private Places() {
    }

    @RecentlyNonNull
    public static synchronized PlacesClient createClient(@RecentlyNonNull Context context) {
        PlacesClient zza2;
        synchronized (Places.class) {
            try {
                zziy.zzc(context, "Context must not be null.");
                zza2 = zza(context, zzgr.zzd(context).zze());
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
        return zza2;
    }

    public static synchronized void deinitialize() {
        synchronized (Places.class) {
            zza.zzc();
        }
    }

    public static synchronized boolean isInitialized() {
        boolean zzf;
        synchronized (Places.class) {
            try {
                zzf = zza.zzf();
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
        return zzf;
    }

    public static synchronized PlacesClient zza(Context context, zzgr zzgr) {
        PlacesClient zza2;
        synchronized (Places.class) {
            try {
                zziy.zzc(context, "Context must not be null.");
                zziy.zzk(isInitialized(), "Places must be initialized first.");
                zzgh zza3 = zzgg.zza();
                zza3.zzc(context);
                zza3.zza(zza);
                zza3.zzb(zzgr);
                zza2 = zza3.zzd().zza();
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
        return zza2;
    }

    public static synchronized void zzb(@RecentlyNonNull Context context, @RecentlyNonNull String str, @Nullable Locale locale, boolean z) {
        synchronized (Places.class) {
            try {
                zziy.zzc(context, "Application context must not be null.");
                zziy.zzc(str, "API Key must not be null.");
                zziy.zze(!str.isEmpty(), "API Key must not be empty.");
                zzgt.zza(context.getApplicationContext(), false);
                zza.zzd(str, locale, false);
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
    }

    public static synchronized zzgk zzc() {
        zzgk zzgk;
        synchronized (Places.class) {
            zzgk = zza;
        }
        return zzgk;
    }

    public static void initialize(@RecentlyNonNull Context applicationContext, @RecentlyNonNull String apiKey) {
        try {
            zzb(applicationContext, apiKey, (Locale) null, false);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public static synchronized void initialize(@RecentlyNonNull Context applicationContext, @RecentlyNonNull String apiKey, @Nullable Locale locale) {
        synchronized (Places.class) {
            try {
                zzb(applicationContext, apiKey, locale, false);
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }
    }
}
