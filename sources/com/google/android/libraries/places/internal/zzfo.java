package com.google.android.libraries.places.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfo {
    private final String zza;
    private final String zzb;
    private Locale zzc = null;
    private Map zzd = new HashMap();

    zzfo(String str, String str2) {
        this.zza = str;
        zziy.zze(!TextUtils.isEmpty(str2), "API key cannot be empty.");
        this.zzb = str2;
    }

    /* access modifiers changed from: package-private */
    public final zzfo zza(Locale locale) {
        this.zzc = locale;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final zzfo zzb(Map map) {
        this.zzd = new HashMap(map);
        return this;
    }

    public final String zzc() {
        Uri.Builder buildUpon = Uri.parse("https://maps.googleapis.com/").buildUpon();
        buildUpon.appendEncodedPath("maps/api/place/");
        buildUpon.appendEncodedPath(this.zza);
        buildUpon.appendQueryParameter(CacheEntity.KEY, this.zzb);
        Locale locale = this.zzc;
        if (locale != null) {
            String languageTag = locale.toLanguageTag();
            if (!TextUtils.isEmpty(languageTag)) {
                buildUpon.appendQueryParameter(IjkMediaMeta.IJKM_KEY_LANGUAGE, languageTag);
            }
        }
        Map map = this.zzd;
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                buildUpon.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return buildUpon.build().toString();
    }
}
