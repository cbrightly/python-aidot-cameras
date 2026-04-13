package com.google.android.libraries.places.api.model;

import android.os.Parcelable;
import androidx.annotation.FloatRange;
import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.internal.zziy;
import com.google.android.libraries.places.internal.zzkc;
import com.google.auto.value.AutoValue;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class PlaceLikelihood implements Parcelable {
    public static final double LIKELIHOOD_MAX_VALUE = 1.0d;
    public static final double LIKELIHOOD_MIN_VALUE = 0.0d;

    @RecentlyNonNull
    public static PlaceLikelihood newInstance(@RecentlyNonNull Place place, @FloatRange(from = 0.0d, to = 1.0d) double likelihood) {
        Double valueOf = Double.valueOf(0.0d);
        Double valueOf2 = Double.valueOf(1.0d);
        zzkc zzb = zzkc.zzb(valueOf, valueOf2);
        Double valueOf3 = Double.valueOf(likelihood);
        zziy.zzh(zzb.zzd(valueOf3), "Likelihood must not be out-of-range: %s to %s, but was: %s.", valueOf, valueOf2, valueOf3);
        return new zzba(place, likelihood);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public abstract double getLikelihood();

    @RecentlyNonNull
    public abstract Place getPlace();
}
