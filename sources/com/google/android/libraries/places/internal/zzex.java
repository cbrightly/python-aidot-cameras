package com.google.android.libraries.places.internal;

import android.location.Location;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzex extends zzfc {
    private final Location zza;
    private final zzjq zzb;

    zzex(FindCurrentPlaceRequest findCurrentPlaceRequest, Location location, zzjq zzjq, Locale locale, String str, boolean z, zzgx zzgx) {
        super(findCurrentPlaceRequest, locale, str, false, zzgx);
        this.zza = location;
        this.zzb = zzjq;
    }

    /* access modifiers changed from: protected */
    public final String zze() {
        return "findplacefromuserlocation/json";
    }

    public final Map zzf() {
        Integer num;
        FindCurrentPlaceRequest findCurrentPlaceRequest = (FindCurrentPlaceRequest) zzb();
        HashMap hashMap = new HashMap();
        zzfc.zzg(hashMap, FirebaseAnalytics.Param.LOCATION, zzfu.zza(this.zza), (Object) null);
        zzfc.zzg(hashMap, "wifiaccesspoints", zzfu.zze(this.zzb, WearableStatusCodes.TARGET_NODE_NOT_CONNECTED), (Object) null);
        Location location = this.zza;
        if (location == null) {
            num = null;
        } else {
            float accuracy = location.getAccuracy();
            num = (!location.hasAccuracy() || accuracy <= 0.0f) ? null : Integer.valueOf(Math.round(accuracy * 100.0f));
        }
        zzfc.zzg(hashMap, "precision", num, (Object) null);
        zzfc.zzg(hashMap, "timestamp", Long.valueOf(this.zza.getTime()), (Object) null);
        zzfc.zzg(hashMap, "fields", zzfv.zza(findCurrentPlaceRequest.getPlaceFields()), (Object) null);
        return hashMap;
    }
}
