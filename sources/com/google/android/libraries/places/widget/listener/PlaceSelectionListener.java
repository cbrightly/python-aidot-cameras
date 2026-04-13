package com.google.android.libraries.places.widget.listener;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public interface PlaceSelectionListener {
    void onError(@RecentlyNonNull Status status);

    void onPlaceSelected(@RecentlyNonNull Place place);
}
