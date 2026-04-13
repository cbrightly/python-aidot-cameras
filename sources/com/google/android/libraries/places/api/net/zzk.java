package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzk extends FindAutocompletePredictionsRequest {
    private final String zza;
    private final LocationBias zzb;
    private final LocationRestriction zzc;
    private final LatLng zzd;
    private final List zze;
    private final AutocompleteSessionToken zzf;
    private final TypeFilter zzg;
    private final List zzh;
    private final CancellationToken zzi;

    /* synthetic */ zzk(String str, LocationBias locationBias, LocationRestriction locationRestriction, LatLng latLng, List list, AutocompleteSessionToken autocompleteSessionToken, TypeFilter typeFilter, List list2, CancellationToken cancellationToken, zzj zzj) {
        this.zza = str;
        this.zzb = locationBias;
        this.zzc = locationRestriction;
        this.zzd = latLng;
        this.zze = list;
        this.zzf = autocompleteSessionToken;
        this.zzg = typeFilter;
        this.zzh = list2;
        this.zzi = cancellationToken;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006f, code lost:
        r1 = r4.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0085, code lost:
        r1 = r4.zzg;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a7, code lost:
        r1 = r4.zzi;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
            r2 = 0
            if (r1 == 0) goto L_0x00bf
            com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest r5 = (com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest) r5
            java.lang.String r1 = r4.zza
            if (r1 != 0) goto L_0x0016
            java.lang.String r1 = r5.getQuery()
            if (r1 != 0) goto L_0x00be
        L_0x0015:
            goto L_0x0021
        L_0x0016:
            java.lang.String r3 = r5.getQuery()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            goto L_0x0015
        L_0x0021:
            com.google.android.libraries.places.api.model.LocationBias r1 = r4.zzb
            if (r1 != 0) goto L_0x002c
            com.google.android.libraries.places.api.model.LocationBias r1 = r5.getLocationBias()
            if (r1 != 0) goto L_0x00be
        L_0x002b:
            goto L_0x0037
        L_0x002c:
            com.google.android.libraries.places.api.model.LocationBias r3 = r5.getLocationBias()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            goto L_0x002b
        L_0x0037:
            com.google.android.libraries.places.api.model.LocationRestriction r1 = r4.zzc
            if (r1 != 0) goto L_0x0042
            com.google.android.libraries.places.api.model.LocationRestriction r1 = r5.getLocationRestriction()
            if (r1 != 0) goto L_0x00be
        L_0x0041:
            goto L_0x004d
        L_0x0042:
            com.google.android.libraries.places.api.model.LocationRestriction r3 = r5.getLocationRestriction()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            goto L_0x0041
        L_0x004d:
            com.google.android.gms.maps.model.LatLng r1 = r4.zzd
            if (r1 != 0) goto L_0x0058
            com.google.android.gms.maps.model.LatLng r1 = r5.getOrigin()
            if (r1 != 0) goto L_0x00be
        L_0x0057:
            goto L_0x0063
        L_0x0058:
            com.google.android.gms.maps.model.LatLng r3 = r5.getOrigin()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            goto L_0x0057
        L_0x0063:
            java.util.List r1 = r4.zze
            java.util.List r3 = r5.getCountries()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            com.google.android.libraries.places.api.model.AutocompleteSessionToken r1 = r4.zzf
            if (r1 != 0) goto L_0x007a
            com.google.android.libraries.places.api.model.AutocompleteSessionToken r1 = r5.getSessionToken()
            if (r1 != 0) goto L_0x00be
        L_0x0079:
            goto L_0x0085
        L_0x007a:
            com.google.android.libraries.places.api.model.AutocompleteSessionToken r3 = r5.getSessionToken()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            goto L_0x0079
        L_0x0085:
            com.google.android.libraries.places.api.model.TypeFilter r1 = r4.zzg
            if (r1 != 0) goto L_0x0090
            com.google.android.libraries.places.api.model.TypeFilter r1 = r5.getTypeFilter()
            if (r1 != 0) goto L_0x00be
        L_0x008f:
            goto L_0x009b
        L_0x0090:
            com.google.android.libraries.places.api.model.TypeFilter r3 = r5.getTypeFilter()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            goto L_0x008f
        L_0x009b:
            java.util.List r1 = r4.zzh
            java.util.List r3 = r5.getTypesFilter()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00be
            com.google.android.gms.tasks.CancellationToken r1 = r4.zzi
            if (r1 != 0) goto L_0x00b2
            com.google.android.gms.tasks.CancellationToken r5 = r5.getCancellationToken()
            if (r5 != 0) goto L_0x00bc
            goto L_0x00bd
        L_0x00b2:
            com.google.android.gms.tasks.CancellationToken r5 = r5.getCancellationToken()
            boolean r5 = r1.equals(r5)
            if (r5 != 0) goto L_0x00bd
        L_0x00bc:
            goto L_0x00be
        L_0x00bd:
            return r0
        L_0x00be:
            return r2
        L_0x00bf:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.net.zzk.equals(java.lang.Object):boolean");
    }

    @Nullable
    public final CancellationToken getCancellationToken() {
        return this.zzi;
    }

    public final List<String> getCountries() {
        return this.zze;
    }

    @Nullable
    public final LocationBias getLocationBias() {
        return this.zzb;
    }

    @Nullable
    public final LocationRestriction getLocationRestriction() {
        return this.zzc;
    }

    @Nullable
    public final LatLng getOrigin() {
        return this.zzd;
    }

    @Nullable
    public final String getQuery() {
        return this.zza;
    }

    @Nullable
    public final AutocompleteSessionToken getSessionToken() {
        return this.zzf;
    }

    @Deprecated
    @Nullable
    public final TypeFilter getTypeFilter() {
        return this.zzg;
    }

    public final List<String> getTypesFilter() {
        return this.zzh;
    }

    public final String toString() {
        String str = this.zza;
        String valueOf = String.valueOf(this.zzb);
        String valueOf2 = String.valueOf(this.zzc);
        String valueOf3 = String.valueOf(this.zzd);
        String obj = this.zze.toString();
        String valueOf4 = String.valueOf(this.zzf);
        String valueOf5 = String.valueOf(this.zzg);
        String obj2 = this.zzh.toString();
        String valueOf6 = String.valueOf(this.zzi);
        return "FindAutocompletePredictionsRequest{query=" + str + ", locationBias=" + valueOf + ", locationRestriction=" + valueOf2 + ", origin=" + valueOf3 + ", countries=" + obj + ", sessionToken=" + valueOf4 + ", typeFilter=" + valueOf5 + ", typesFilter=" + obj2 + ", cancellationToken=" + valueOf6 + "}";
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        String str = this.zza;
        int i7 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        LocationBias locationBias = this.zzb;
        if (locationBias == null) {
            i2 = 0;
        } else {
            i2 = locationBias.hashCode();
        }
        int i8 = i ^ 1000003;
        LocationRestriction locationRestriction = this.zzc;
        if (locationRestriction == null) {
            i3 = 0;
        } else {
            i3 = locationRestriction.hashCode();
        }
        int i9 = ((((i8 * 1000003) ^ i2) * 1000003) ^ i3) * 1000003;
        LatLng latLng = this.zzd;
        if (latLng == null) {
            i4 = 0;
        } else {
            i4 = latLng.hashCode();
        }
        int hashCode = (((i9 ^ i4) * 1000003) ^ this.zze.hashCode()) * 1000003;
        AutocompleteSessionToken autocompleteSessionToken = this.zzf;
        if (autocompleteSessionToken == null) {
            i5 = 0;
        } else {
            i5 = autocompleteSessionToken.hashCode();
        }
        int i10 = (hashCode ^ i5) * 1000003;
        TypeFilter typeFilter = this.zzg;
        if (typeFilter == null) {
            i6 = 0;
        } else {
            i6 = typeFilter.hashCode();
        }
        int hashCode2 = (((i10 ^ i6) * 1000003) ^ this.zzh.hashCode()) * 1000003;
        CancellationToken cancellationToken = this.zzi;
        if (cancellationToken != null) {
            i7 = cancellationToken.hashCode();
        }
        return hashCode2 ^ i7;
    }
}
