package com.google.android.libraries.places.api.net;

import androidx.annotation.RecentlyNonNull;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public interface PlacesClient {
    @RecentlyNonNull
    Task<FetchPhotoResponse> fetchPhoto(@RecentlyNonNull FetchPhotoRequest fetchPhotoRequest);

    @RecentlyNonNull
    Task<FetchPlaceResponse> fetchPlace(@RecentlyNonNull FetchPlaceRequest fetchPlaceRequest);

    @RecentlyNonNull
    Task<FindAutocompletePredictionsResponse> findAutocompletePredictions(@RecentlyNonNull FindAutocompletePredictionsRequest findAutocompletePredictionsRequest);

    @RecentlyNonNull
    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    Task<FindCurrentPlaceResponse> findCurrentPlace(@RecentlyNonNull FindCurrentPlaceRequest findCurrentPlaceRequest);

    @RecentlyNonNull
    Task<IsOpenResponse> isOpen(@RecentlyNonNull IsOpenRequest isOpenRequest);
}
