package com.google.android.gms.location;

import android.app.PendingIntent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

@Deprecated
/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface ActivityRecognitionApi {
    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    PendingResult<Status> removeActivityUpdates(@NonNull GoogleApiClient googleApiClient, @NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    PendingResult<Status> requestActivityUpdates(@NonNull GoogleApiClient googleApiClient, long j, @NonNull PendingIntent pendingIntent);
}
