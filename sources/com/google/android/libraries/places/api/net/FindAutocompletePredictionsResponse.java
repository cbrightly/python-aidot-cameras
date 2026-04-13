package com.google.android.libraries.places.api.net;

import androidx.annotation.RecentlyNonNull;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.internal.zzjq;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class FindAutocompletePredictionsResponse {
    @RecentlyNonNull
    public static FindAutocompletePredictionsResponse newInstance(@RecentlyNonNull List<AutocompletePrediction> autocompletePredictions) {
        return new zzl(zzjq.zzj(autocompletePredictions));
    }

    @RecentlyNonNull
    public abstract List<AutocompletePrediction> getAutocompletePredictions();
}
