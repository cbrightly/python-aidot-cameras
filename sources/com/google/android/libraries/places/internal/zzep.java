package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzep extends zzfc {
    zzep(FetchPlaceRequest fetchPlaceRequest, Locale locale, String str, boolean z, zzgx zzgx) {
        super(fetchPlaceRequest, locale, str, false, zzgx);
    }

    /* access modifiers changed from: protected */
    public final String zze() {
        return "details/json";
    }

    public final Map zzf() {
        FetchPlaceRequest fetchPlaceRequest = (FetchPlaceRequest) zzb();
        HashMap hashMap = new HashMap();
        zzfc.zzg(hashMap, "placeid", fetchPlaceRequest.getPlaceId(), (Object) null);
        zzfc.zzg(hashMap, "sessiontoken", fetchPlaceRequest.getSessionToken(), (Object) null);
        zzfc.zzg(hashMap, "fields", zzfv.zza(fetchPlaceRequest.getPlaceFields()), (Object) null);
        return hashMap;
    }
}
