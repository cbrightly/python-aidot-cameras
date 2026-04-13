package com.google.android.gms.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

@Deprecated
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface FusedLocationProviderApi {
    @NonNull
    @Deprecated
    public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
    @NonNull
    @Deprecated
    public static final String KEY_MOCK_LOCATION = "mockLocation";

    @NonNull
    PendingResult<Status> flushLocations(@NonNull GoogleApiClient googleApiClient);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @Nullable
    Location getLastLocation(@NonNull GoogleApiClient googleApiClient);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @Nullable
    LocationAvailability getLocationAvailability(@NonNull GoogleApiClient googleApiClient);

    @NonNull
    PendingResult<Status> removeLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull PendingIntent pendingIntent);

    @NonNull
    PendingResult<Status> removeLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull LocationCallback locationCallback);

    @NonNull
    PendingResult<Status> removeLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull LocationListener locationListener);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    PendingResult<Status> requestLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull LocationRequest locationRequest, @NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    PendingResult<Status> requestLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull LocationRequest locationRequest, @NonNull LocationCallback locationCallback, @NonNull Looper looper);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    PendingResult<Status> requestLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull LocationRequest locationRequest, @NonNull LocationListener locationListener);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    PendingResult<Status> requestLocationUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull LocationRequest locationRequest, @NonNull LocationListener locationListener, @NonNull Looper looper);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    PendingResult<Status> setMockLocation(@NonNull GoogleApiClient googleApiClient, @NonNull Location location);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    PendingResult<Status> setMockMode(@NonNull GoogleApiClient googleApiClient, boolean z);
}
