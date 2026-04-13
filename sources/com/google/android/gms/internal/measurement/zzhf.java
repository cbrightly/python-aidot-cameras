package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhf implements zzhk {
    public static final String[] zza = {CacheEntity.KEY, "value"};
    @GuardedBy("ConfigurationContentLoader.class")
    private static final Map zzb = new ArrayMap();
    private final ContentResolver zzc;
    private final Uri zzd;
    private final Runnable zze;
    private final ContentObserver zzf;
    private final Object zzg = new Object();
    private volatile Map zzh;
    @GuardedBy("this")
    private final List zzi = new ArrayList();

    private zzhf(ContentResolver contentResolver, Uri uri, Runnable runnable) {
        zzhe zzhe = new zzhe(this, (Handler) null);
        this.zzf = zzhe;
        if (contentResolver == null) {
            throw null;
        } else if (uri != null) {
            this.zzc = contentResolver;
            this.zzd = uri;
            this.zze = runnable;
            contentResolver.registerContentObserver(uri, false, zzhe);
        } else {
            throw null;
        }
    }

    public static zzhf zza(ContentResolver contentResolver, Uri uri, Runnable runnable) {
        zzhf zzhf;
        synchronized (zzhf.class) {
            Map map = zzb;
            zzhf = (zzhf) map.get(uri);
            if (zzhf == null) {
                try {
                    zzhf zzhf2 = new zzhf(contentResolver, uri, runnable);
                    try {
                        map.put(uri, zzhf2);
                    } catch (SecurityException e) {
                    }
                    zzhf = zzhf2;
                } catch (SecurityException e2) {
                }
            }
        }
        return zzhf;
    }

    static synchronized void zze() {
        synchronized (zzhf.class) {
            for (zzhf zzhf : zzb.values()) {
                zzhf.zzc.unregisterContentObserver(zzhf.zzf);
            }
            zzb.clear();
        }
    }

    public final /* bridge */ /* synthetic */ Object zzb(String str) {
        return (String) zzc().get(str);
    }

    /* JADX INFO: finally extract failed */
    public final Map zzc() {
        Map map;
        Map map2 = this.zzh;
        if (map2 == null) {
            synchronized (this.zzg) {
                map2 = this.zzh;
                if (map2 == null) {
                    StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        map = (Map) zzhi.zza(new zzhd(this));
                        StrictMode.setThreadPolicy(allowThreadDiskReads);
                    } catch (SQLiteException | IllegalStateException | SecurityException e) {
                        try {
                            Log.e("ConfigurationContentLdr", "PhenotypeFlag unable to load ContentProvider, using default values");
                            StrictMode.setThreadPolicy(allowThreadDiskReads);
                            map = null;
                        } catch (Throwable th) {
                            StrictMode.setThreadPolicy(allowThreadDiskReads);
                            throw th;
                        }
                    }
                    this.zzh = map;
                    map2 = map;
                }
            }
        }
        if (map2 != null) {
            return map2;
        }
        return Collections.emptyMap();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Map zzd() {
        Map map;
        Cursor query = this.zzc.query(this.zzd, zza, (String) null, (String[]) null, (String) null);
        if (query == null) {
            return Collections.emptyMap();
        }
        try {
            int count = query.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            if (count <= 256) {
                map = new ArrayMap(count);
            } else {
                map = new HashMap(count, 1.0f);
            }
            while (query.moveToNext()) {
                map.put(query.getString(0), query.getString(1));
            }
            query.close();
            return map;
        } finally {
            query.close();
        }
    }

    public final void zzf() {
        synchronized (this.zzg) {
            this.zzh = null;
            this.zze.run();
        }
        synchronized (this) {
            for (zzhg zza2 : this.zzi) {
                zza2.zza();
            }
        }
    }
}
