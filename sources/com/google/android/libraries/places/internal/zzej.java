package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzej extends zzfc {
    zzej(FetchPhotoRequest fetchPhotoRequest, String str, boolean z, zzgx zzgx) {
        super(fetchPhotoRequest, (Locale) null, str, false, zzgx);
    }

    /* access modifiers changed from: protected */
    public final String zze() {
        return "photo";
    }

    public final Map zzf() {
        FetchPhotoRequest fetchPhotoRequest = (FetchPhotoRequest) zzb();
        PhotoMetadata photoMetadata = fetchPhotoRequest.getPhotoMetadata();
        HashMap hashMap = new HashMap();
        zzfc.zzg(hashMap, "maxheight", fetchPhotoRequest.getMaxHeight(), (Object) null);
        zzfc.zzg(hashMap, "maxwidth", fetchPhotoRequest.getMaxWidth(), (Object) null);
        hashMap.put("photoreference", photoMetadata.zza());
        return hashMap;
    }
}
