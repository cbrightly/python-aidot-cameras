package com.google.android.gms.location;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;

@Deprecated
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface SettingsApi {
    @NonNull
    PendingResult<LocationSettingsResult> checkLocationSettings(@NonNull GoogleApiClient googleApiClient, @NonNull LocationSettingsRequest locationSettingsRequest);
}
