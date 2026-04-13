package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.internal.zzgl;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class FetchPlaceRequest implements zzgl {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public FetchPlaceRequest build() {
            zza(zzjq.zzj(zzc().getPlaceFields()));
            return zzc();
        }

        @RecentlyNullable
        public abstract CancellationToken getCancellationToken();

        @RecentlyNullable
        public abstract AutocompleteSessionToken getSessionToken();

        @RecentlyNonNull
        public abstract Builder setCancellationToken(@Nullable CancellationToken cancellationToken);

        @RecentlyNonNull
        public abstract Builder setSessionToken(@Nullable AutocompleteSessionToken autocompleteSessionToken);

        /* access modifiers changed from: package-private */
        public abstract Builder zza(List list);

        /* access modifiers changed from: package-private */
        public abstract FetchPlaceRequest zzc();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull String placeId, @RecentlyNonNull List<Place.Field> placeFields) {
        zze zze = new zze();
        zze.zzb(placeId);
        zze.zza(placeFields);
        return zze;
    }

    @RecentlyNonNull
    public static FetchPlaceRequest newInstance(@RecentlyNonNull String placeId, @RecentlyNonNull List<Place.Field> placeFields) {
        return builder(placeId, placeFields).build();
    }

    @RecentlyNullable
    public abstract CancellationToken getCancellationToken();

    @RecentlyNonNull
    public abstract List<Place.Field> getPlaceFields();

    @RecentlyNonNull
    public abstract String getPlaceId();

    @RecentlyNullable
    public abstract AutocompleteSessionToken getSessionToken();
}
