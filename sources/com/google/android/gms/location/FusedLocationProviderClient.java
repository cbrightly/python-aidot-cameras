package com.google.android.gms.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface FusedLocationProviderClient extends HasApiKey<Api.ApiOptions.NoOptions> {
    @NonNull
    @Deprecated
    public static final String KEY_MOCK_LOCATION = "mockLocation";
    @NonNull
    @Deprecated
    public static final String KEY_VERTICAL_ACCURACY = "verticalAccuracy";

    @NonNull
    Task<Void> flushLocations();

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Location> getCurrentLocation(int i, @Nullable CancellationToken cancellationToken);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Location> getCurrentLocation(@NonNull CurrentLocationRequest currentLocationRequest, @Nullable CancellationToken cancellationToken);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Location> getLastLocation();

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Location> getLastLocation(@NonNull LastLocationRequest lastLocationRequest);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<LocationAvailability> getLocationAvailability();

    @NonNull
    Task<Void> removeLocationUpdates(@NonNull PendingIntent pendingIntent);

    @NonNull
    Task<Void> removeLocationUpdates(@NonNull LocationCallback locationCallback);

    @NonNull
    Task<Void> removeLocationUpdates(@NonNull LocationListener locationListener);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> requestLocationUpdates(@NonNull LocationRequest locationRequest, @NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> requestLocationUpdates(@NonNull LocationRequest locationRequest, @NonNull LocationCallback locationCallback, @Nullable Looper looper);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> requestLocationUpdates(@NonNull LocationRequest locationRequest, @NonNull LocationListener locationListener, @Nullable Looper looper);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> requestLocationUpdates(@NonNull LocationRequest locationRequest, @NonNull Executor executor, @NonNull LocationCallback locationCallback);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> requestLocationUpdates(@NonNull LocationRequest locationRequest, @NonNull Executor executor, @NonNull LocationListener locationListener);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> setMockLocation(@NonNull Location location);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    @NonNull
    Task<Void> setMockMode(boolean z);
}
