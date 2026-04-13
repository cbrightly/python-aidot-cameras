package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.internal.zzgl;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class FindCurrentPlaceRequest implements zzgl {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public FindCurrentPlaceRequest build() {
            zza(zzjq.zzj(zzb().getPlaceFields()));
            return zzb();
        }

        @RecentlyNullable
        public abstract CancellationToken getCancellationToken();

        @RecentlyNonNull
        public abstract Builder setCancellationToken(@Nullable CancellationToken cancellationToken);

        /* access modifiers changed from: package-private */
        public abstract Builder zza(List list);

        /* access modifiers changed from: package-private */
        public abstract FindCurrentPlaceRequest zzb();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull List<Place.Field> placeFields) {
        zzm zzm = new zzm();
        zzm.zza(placeFields);
        return zzm;
    }

    @RecentlyNonNull
    public static FindCurrentPlaceRequest newInstance(@RecentlyNonNull List<Place.Field> placeFields) {
        return builder(placeFields).build();
    }

    @RecentlyNullable
    public abstract CancellationToken getCancellationToken();

    @RecentlyNonNull
    public abstract List<Place.Field> getPlaceFields();
}
