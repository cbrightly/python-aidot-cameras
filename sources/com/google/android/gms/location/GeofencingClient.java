package com.google.android.gms.location;

import android.app.PendingIntent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.Task;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface GeofencingClient extends HasApiKey<Api.ApiOptions.NoOptions> {
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @NonNull
    Task<Void> addGeofences(@NonNull GeofencingRequest geofencingRequest, @NonNull PendingIntent pendingIntent);

    @NonNull
    Task<Void> removeGeofences(@NonNull PendingIntent pendingIntent);

    @NonNull
    Task<Void> removeGeofences(@NonNull List<String> list);
}
