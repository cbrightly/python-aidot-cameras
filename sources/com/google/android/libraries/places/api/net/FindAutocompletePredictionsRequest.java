package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.internal.zzgl;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class FindAutocompletePredictionsRequest implements zzgl {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public FindAutocompletePredictionsRequest build() {
            setCountries((List<String>) zzjq.zzj(getCountries()));
            setTypesFilter(zzjq.zzj(getTypesFilter()));
            return zza();
        }

        @RecentlyNullable
        public abstract CancellationToken getCancellationToken();

        @RecentlyNonNull
        public abstract List<String> getCountries();

        @RecentlyNullable
        public abstract LocationBias getLocationBias();

        @RecentlyNullable
        public abstract LocationRestriction getLocationRestriction();

        @RecentlyNullable
        public abstract LatLng getOrigin();

        @RecentlyNullable
        public abstract String getQuery();

        @RecentlyNullable
        public abstract AutocompleteSessionToken getSessionToken();

        @Deprecated
        @RecentlyNullable
        public abstract TypeFilter getTypeFilter();

        @RecentlyNonNull
        public abstract List<String> getTypesFilter();

        @RecentlyNonNull
        public abstract Builder setCancellationToken(@Nullable CancellationToken cancellationToken);

        @RecentlyNonNull
        public abstract Builder setCountries(@RecentlyNonNull List<String> list);

        @RecentlyNonNull
        public Builder setCountries(@RecentlyNonNull String... countries) {
            return setCountries((List<String>) zzjq.zzk(countries));
        }

        @RecentlyNonNull
        @Deprecated
        public Builder setCountry(@Nullable String countryCode) {
            setCountries((List<String>) countryCode == null ? zzjq.zzl() : zzjq.zzm(countryCode));
            return this;
        }

        @RecentlyNonNull
        public abstract Builder setLocationBias(@Nullable LocationBias locationBias);

        @RecentlyNonNull
        public abstract Builder setLocationRestriction(@Nullable LocationRestriction locationRestriction);

        @RecentlyNonNull
        public abstract Builder setOrigin(@Nullable LatLng latLng);

        @RecentlyNonNull
        public abstract Builder setQuery(@Nullable String str);

        @RecentlyNonNull
        public abstract Builder setSessionToken(@Nullable AutocompleteSessionToken autocompleteSessionToken);

        @RecentlyNonNull
        @Deprecated
        public abstract Builder setTypeFilter(@Nullable TypeFilter typeFilter);

        @RecentlyNonNull
        public abstract Builder setTypesFilter(@RecentlyNonNull List<String> list);

        /* access modifiers changed from: package-private */
        public abstract FindAutocompletePredictionsRequest zza();
    }

    @RecentlyNonNull
    public static Builder builder() {
        zzi zzi = new zzi();
        zzi.setCountries(new ArrayList());
        zzi.setTypesFilter(new ArrayList());
        return zzi;
    }

    @RecentlyNonNull
    public static FindAutocompletePredictionsRequest newInstance(@Nullable String query) {
        Builder builder = builder();
        builder.setQuery(query);
        return builder.build();
    }

    @RecentlyNullable
    public abstract CancellationToken getCancellationToken();

    @RecentlyNonNull
    public abstract List<String> getCountries();

    @Deprecated
    @RecentlyNullable
    public String getCountry() {
        T t;
        if (getCountries().size() <= 1) {
            Iterator<T> it = getCountries().iterator();
            if (it.hasNext()) {
                t = it.next();
            } else {
                t = null;
            }
            return (String) t;
        }
        throw new UnsupportedOperationException("Multiple countries found in this request - use getCountries() instead of getCountry().");
    }

    @RecentlyNullable
    public abstract LocationBias getLocationBias();

    @RecentlyNullable
    public abstract LocationRestriction getLocationRestriction();

    @RecentlyNullable
    public abstract LatLng getOrigin();

    @RecentlyNullable
    public abstract String getQuery();

    @RecentlyNullable
    public abstract AutocompleteSessionToken getSessionToken();

    @Deprecated
    @RecentlyNullable
    public abstract TypeFilter getTypeFilter();

    @RecentlyNonNull
    public abstract List<String> getTypesFilter();
}
