package com.google.android.gms.internal.vision;

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

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzau implements zzay {
    @GuardedBy("ConfigurationContentLoader.class")
    private static final Map<Uri, zzau> zza = new ArrayMap();
    private static final String[] zzh = {CacheEntity.KEY, "value"};
    private final ContentResolver zzb;
    private final Uri zzc;
    private final ContentObserver zzd;
    private final Object zze = new Object();
    private volatile Map<String, String> zzf;
    @GuardedBy("this")
    private final List<zzaz> zzg = new ArrayList();

    private zzau(ContentResolver contentResolver, Uri uri) {
        zzaw zzaw = new zzaw(this, (Handler) null);
        this.zzd = zzaw;
        zzde.zza(contentResolver);
        zzde.zza(uri);
        this.zzb = contentResolver;
        this.zzc = uri;
        contentResolver.registerContentObserver(uri, false, zzaw);
    }

    public static zzau zza(ContentResolver contentResolver, Uri uri) {
        zzau zzau;
        synchronized (zzau.class) {
            Map<Uri, zzau> map = zza;
            zzau = map.get(uri);
            if (zzau == null) {
                try {
                    zzau zzau2 = new zzau(contentResolver, uri);
                    try {
                        map.put(uri, zzau2);
                        zzau = zzau2;
                    } catch (SecurityException e) {
                        zzau = zzau2;
                    }
                } catch (SecurityException e2) {
                }
            }
        }
        return zzau;
    }

    private final Map<String, String> zzd() {
        Map<String, String> map = this.zzf;
        if (map == null) {
            synchronized (this.zze) {
                map = this.zzf;
                if (map == null) {
                    map = zze();
                    this.zzf = map;
                }
            }
        }
        return map != null ? map : Collections.emptyMap();
    }

    public final void zza() {
        synchronized (this.zze) {
            this.zzf = null;
            zzbi.zza();
        }
        synchronized (this) {
            for (zzaz zza2 : this.zzg) {
                zza2.zza();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    private final Map<String, String> zze() {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            Map<String, String> map = (Map) zzbb.zza(new zzax(this));
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return map;
        } catch (SQLiteException | IllegalStateException | SecurityException e) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return null;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }

    static synchronized void zzb() {
        synchronized (zzau.class) {
            for (zzau next : zza.values()) {
                next.zzb.unregisterContentObserver(next.zzd);
            }
            zza.clear();
        }
    }

    public final /* synthetic */ Object zza(String str) {
        return zzd().get(str);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Map zzc() {
        Map map;
        Cursor query = this.zzb.query(this.zzc, zzh, (String) null, (String[]) null, (String) null);
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
}
