package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzi extends FindAutocompletePredictionsRequest.Builder {
    private String zza;
    private LocationBias zzb;
    private LocationRestriction zzc;
    private LatLng zzd;
    private List zze;
    private AutocompleteSessionToken zzf;
    private TypeFilter zzg;
    private List zzh;
    private CancellationToken zzi;

    zzi() {
    }

    @Nullable
    public final CancellationToken getCancellationToken() {
        return this.zzi;
    }

    public final List<String> getCountries() {
        List<String> list = this.zze;
        if (list != null) {
            return list;
        }
        throw new IllegalStateException("Property \"countries\" has not been set");
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

    @Nullable
    public final TypeFilter getTypeFilter() {
        return this.zzg;
    }

    public final List<String> getTypesFilter() {
        List<String> list = this.zzh;
        if (list != null) {
            return list;
        }
        throw new IllegalStateException("Property \"typesFilter\" has not been set");
    }

    public final FindAutocompletePredictionsRequest.Builder setCancellationToken(@Nullable CancellationToken cancellationToken) {
        this.zzi = cancellationToken;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setCountries(List<String> list) {
        if (list != null) {
            this.zze = list;
            return this;
        }
        throw new NullPointerException("Null countries");
    }

    public final FindAutocompletePredictionsRequest.Builder setLocationBias(@Nullable LocationBias locationBias) {
        this.zzb = locationBias;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setLocationRestriction(@Nullable LocationRestriction locationRestriction) {
        this.zzc = locationRestriction;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setOrigin(@Nullable LatLng latLng) {
        this.zzd = latLng;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setQuery(@Nullable String str) {
        this.zza = str;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setSessionToken(@Nullable AutocompleteSessionToken autocompleteSessionToken) {
        this.zzf = autocompleteSessionToken;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setTypeFilter(@Nullable TypeFilter typeFilter) {
        this.zzg = typeFilter;
        return this;
    }

    public final FindAutocompletePredictionsRequest.Builder setTypesFilter(List<String> list) {
        if (list != null) {
            this.zzh = list;
            return this;
        }
        throw new NullPointerException("Null typesFilter");
    }

    /* access modifiers changed from: package-private */
    public final FindAutocompletePredictionsRequest zza() {
        List list;
        List list2 = this.zze;
        if (list2 != null && (list = this.zzh) != null) {
            return new zzk(this.zza, this.zzb, this.zzc, this.zzd, list2, this.zzf, this.zzg, list, this.zzi, (zzj) null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zze == null) {
            sb.append(" countries");
        }
        if (this.zzh == null) {
            sb.append(" typesFilter");
        }
        throw new IllegalStateException("Missing required properties:".concat(sb.toString()));
    }
}
