package com.google.android.libraries.places.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.internal.zzgt;
import com.google.android.libraries.places.internal.zzhh;
import com.google.android.libraries.places.internal.zzhi;
import com.google.android.libraries.places.internal.zzhj;
import com.google.android.libraries.places.internal.zzhm;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class Autocomplete {

    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static class IntentBuilder {
        private final zzhi zza;

        public IntentBuilder(zzhj zzhj) {
            this.zza = zzhj.zzg();
        }

        public IntentBuilder(@RecentlyNonNull AutocompleteActivityMode mode, @RecentlyNonNull List<Place.Field> placeFields) {
            this.zza = zzhj.zzn(mode, placeFields, zzhh.INTENT);
        }

        @RecentlyNonNull
        public Intent build(@RecentlyNonNull Context context) {
            try {
                Intent intent = new Intent(context, AutocompleteActivity.class);
                zzhi zzhi = this.zza;
                Resources.Theme theme = context.getTheme();
                TypedValue typedValue = new TypedValue();
                if (theme.resolveAttribute(16843827, typedValue, true)) {
                    zzhi.zzi(typedValue.data);
                }
                TypedValue typedValue2 = new TypedValue();
                if (theme.resolveAttribute(16843828, typedValue2, true)) {
                    zzhi.zzj(typedValue2.data);
                }
                intent.putExtra("places/AutocompleteOptions", this.zza.zzm());
                return intent;
            } catch (Error | RuntimeException e) {
                zzgt.zzb(e);
                throw e;
            }
        }

        @RecentlyNonNull
        public IntentBuilder setCountries(@RecentlyNonNull List<String> countries) {
            this.zza.zza(countries);
            return this;
        }

        @RecentlyNonNull
        @Deprecated
        public IntentBuilder setCountry(@Nullable String country) {
            this.zza.zzn(country);
            return this;
        }

        @RecentlyNonNull
        public IntentBuilder setHint(@Nullable String hint) {
            this.zza.zzb(hint);
            return this;
        }

        @RecentlyNonNull
        public IntentBuilder setInitialQuery(@Nullable String initialQuery) {
            this.zza.zzc(initialQuery);
            return this;
        }

        @RecentlyNonNull
        public IntentBuilder setLocationBias(@Nullable LocationBias locationBias) {
            this.zza.zzd(locationBias);
            return this;
        }

        @RecentlyNonNull
        public IntentBuilder setLocationRestriction(@Nullable LocationRestriction locationRestriction) {
            this.zza.zze(locationRestriction);
            return this;
        }

        @RecentlyNonNull
        @Deprecated
        public IntentBuilder setTypeFilter(@Nullable TypeFilter typeFilter) {
            this.zza.zzk(typeFilter);
            return this;
        }

        @RecentlyNonNull
        public IntentBuilder setTypesFilter(@RecentlyNonNull List<String> typesFilter) {
            this.zza.zzl(typesFilter);
            return this;
        }

        public final IntentBuilder zza(zzhh zzhh) {
            this.zza.zzg(zzhh);
            return this;
        }
    }

    private Autocomplete() {
    }

    @RecentlyNonNull
    public static Place getPlaceFromIntent(@RecentlyNonNull Intent intent) {
        return zzhm.zzb(intent);
    }

    @RecentlyNonNull
    public static Status getStatusFromIntent(@RecentlyNonNull Intent intent) {
        return zzhm.zza(intent);
    }
}
