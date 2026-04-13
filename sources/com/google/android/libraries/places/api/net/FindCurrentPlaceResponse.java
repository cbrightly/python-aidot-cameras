package com.google.android.libraries.places.api.net;

import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class FindCurrentPlaceResponse {
    @RecentlyNonNull
    public static FindCurrentPlaceResponse newInstance(@RecentlyNonNull List<PlaceLikelihood> placeLikelihoods) {
        return new zzp(zzjq.zzj(placeLikelihoods));
    }

    @RecentlyNonNull
    public abstract List<PlaceLikelihood> getPlaceLikelihoods();
}
