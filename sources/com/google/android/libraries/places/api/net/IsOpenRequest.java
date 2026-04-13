package com.google.android.libraries.places.api.net;

import androidx.annotation.Nullable;
import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RecentlyNullable;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.internal.zzgl;
import com.google.android.libraries.places.internal.zziy;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class IsOpenRequest implements zzgl {

    @AutoValue.Builder
    /* compiled from: com.google.android.libraries.places:places@@3.1.0 */
    public static abstract class Builder {
        @RecentlyNonNull
        public IsOpenRequest build() {
            IsOpenRequest zza = zza();
            Place place = zza.getPlace();
            if (place != null) {
                zziy.zze(place.getId() != null, "Place must have a valid place id.");
            }
            return zza;
        }

        @RecentlyNullable
        public abstract CancellationToken getCancellationToken();

        @RecentlyNonNull
        public abstract Place getPlace();

        @RecentlyNonNull
        public abstract String getPlaceId();

        public abstract long getUtcTimeMillis();

        @RecentlyNonNull
        public abstract Builder setCancellationToken(@Nullable CancellationToken cancellationToken);

        @RecentlyNonNull
        public abstract Builder setPlace(@RecentlyNonNull Place place);

        @RecentlyNonNull
        public abstract Builder setPlaceId(@RecentlyNonNull String str);

        @RecentlyNonNull
        public abstract Builder setUtcTimeMillis(long j);

        /* access modifiers changed from: package-private */
        public abstract IsOpenRequest zza();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull Place place) {
        zzq zzq = new zzq();
        zzq.setPlace(place);
        zzq.setUtcTimeMillis(System.currentTimeMillis());
        return zzq;
    }

    @RecentlyNonNull
    public static IsOpenRequest newInstance(@RecentlyNonNull Place place) {
        return builder(place).build();
    }

    @RecentlyNullable
    public abstract CancellationToken getCancellationToken();

    @RecentlyNullable
    public abstract Place getPlace();

    @RecentlyNullable
    public abstract String getPlaceId();

    public abstract long getUtcTimeMillis();

    @RecentlyNonNull
    public static IsOpenRequest newInstance(@RecentlyNonNull Place place, long utcTimeMillis) {
        return builder(place, utcTimeMillis).build();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull Place place, long utcTimeMillis) {
        zzq zzq = new zzq();
        zzq.setPlace(place);
        zzq.setUtcTimeMillis(utcTimeMillis);
        return zzq;
    }

    @RecentlyNonNull
    public static IsOpenRequest newInstance(@RecentlyNonNull String placeId) {
        return builder(placeId).build();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull String placeId) {
        zzq zzq = new zzq();
        zzq.setPlaceId(placeId);
        zzq.setUtcTimeMillis(System.currentTimeMillis());
        return zzq;
    }

    @RecentlyNonNull
    public static IsOpenRequest newInstance(@RecentlyNonNull String placeId, long utcTimeMillis) {
        return builder(placeId, utcTimeMillis).build();
    }

    @RecentlyNonNull
    public static Builder builder(@RecentlyNonNull String placeId, long utcTimeMillis) {
        zzq zzq = new zzq();
        zzq.setPlaceId(placeId);
        zzq.setUtcTimeMillis(utcTimeMillis);
        return zzq;
    }
}
