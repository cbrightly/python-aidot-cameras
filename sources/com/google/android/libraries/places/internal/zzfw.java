package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.api.model.TypeFilter;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfw {
    private static final zzjt zza;

    static {
        zzjs zzjs = new zzjs();
        zzjs.zza(TypeFilter.ADDRESS, PlaceTypes.ADDRESS);
        zzjs.zza(TypeFilter.CITIES, PlaceTypes.CITIES);
        zzjs.zza(TypeFilter.ESTABLISHMENT, PlaceTypes.ESTABLISHMENT);
        zzjs.zza(TypeFilter.GEOCODE, PlaceTypes.GEOCODE);
        zzjs.zza(TypeFilter.REGIONS, PlaceTypes.REGIONS);
        zza = zzjs.zzb();
    }

    public static String zza(TypeFilter typeFilter) {
        String str = (String) zza.get(typeFilter);
        return str == null ? "" : str;
    }
}
