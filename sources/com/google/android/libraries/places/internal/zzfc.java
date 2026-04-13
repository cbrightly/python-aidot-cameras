package com.google.android.libraries.places.internal;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzfc extends zzdv {
    @Nullable
    private final Locale zza;
    private final String zzb;
    private final zzgx zzc;

    protected zzfc(zzgl zzgl, @Nullable Locale locale, String str, boolean z, zzgx zzgx) {
        super(zzgl);
        this.zza = locale;
        this.zzb = str;
        this.zzc = zzgx;
    }

    protected static void zzg(Map map, String str, @Nullable Object obj, @Nullable Object obj2) {
        String str2;
        if (obj != null) {
            str2 = obj.toString();
        } else {
            str2 = null;
        }
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    /* access modifiers changed from: protected */
    public final String zzc() {
        zzfo zzfo = new zzfo(zze(), this.zzb);
        zzfo.zza(this.zza);
        zzfo.zzb(zzf());
        return zzfo.zzc();
    }

    /* access modifiers changed from: protected */
    public final Map zzd() {
        HashMap hashMap = new HashMap();
        hashMap.putAll(this.zzc.zza());
        hashMap.put("X-Places-Android-Sdk", "3.1.0");
        return hashMap;
    }

    /* access modifiers changed from: protected */
    public abstract String zze();

    /* access modifiers changed from: protected */
    public abstract Map zzf();
}
