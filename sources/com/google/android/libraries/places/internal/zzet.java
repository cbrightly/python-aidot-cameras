package com.google.android.libraries.places.internal;

import android.text.TextUtils;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzet extends zzfc {
    zzet(FindAutocompletePredictionsRequest findAutocompletePredictionsRequest, Locale locale, String str, boolean z, zzgx zzgx) {
        super(findAutocompletePredictionsRequest, locale, str, false, zzgx);
    }

    /* access modifiers changed from: protected */
    public final String zze() {
        return "autocomplete/json";
    }

    public final Map zzf() {
        String str;
        String str2;
        String str3;
        String str4;
        HashMap hashMap = new HashMap();
        FindAutocompletePredictionsRequest findAutocompletePredictionsRequest = (FindAutocompletePredictionsRequest) zzb();
        TypeFilter typeFilter = findAutocompletePredictionsRequest.getTypeFilter();
        List<String> typesFilter = findAutocompletePredictionsRequest.getTypesFilter();
        String query = findAutocompletePredictionsRequest.getQuery();
        if (query == null) {
            str = null;
        } else {
            str = query.replaceFirst("^\\s+", "").replaceFirst("\\s+$", " ");
        }
        zzfc.zzg(hashMap, "input", str, (Object) null);
        if (!typesFilter.isEmpty()) {
            zzfc.zzg(hashMap, "types", TextUtils.join("|", typesFilter), (Object) null);
        } else {
            if (typeFilter != null) {
                str4 = zzfw.zza(typeFilter);
            } else {
                str4 = null;
            }
            zzfc.zzg(hashMap, "types", str4, (Object) null);
        }
        zzfc.zzg(hashMap, "sessiontoken", findAutocompletePredictionsRequest.getSessionToken(), (Object) null);
        zzfc.zzg(hashMap, "origin", zzfu.zzb(findAutocompletePredictionsRequest.getOrigin()), (Object) null);
        zzfc.zzg(hashMap, "locationbias", zzfu.zzc(findAutocompletePredictionsRequest.getLocationBias()), (Object) null);
        zzfc.zzg(hashMap, "locationrestriction", zzfu.zzd(findAutocompletePredictionsRequest.getLocationRestriction()), (Object) null);
        List<String> countries = findAutocompletePredictionsRequest.getCountries();
        StringBuilder sb = new StringBuilder();
        for (String next : countries) {
            if (TextUtils.isEmpty(next)) {
                str3 = null;
            } else {
                str3 = "country:".concat(String.valueOf(next.toLowerCase(Locale.US)));
            }
            if (str3 != null) {
                if (sb.length() != 0) {
                    sb.append('|');
                }
                sb.append(str3);
            }
        }
        if (sb.length() == 0) {
            str2 = null;
        } else {
            str2 = sb.toString();
        }
        zzfc.zzg(hashMap, "components", str2, (Object) null);
        return hashMap;
    }
}
