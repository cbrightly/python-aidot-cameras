package com.google.android.gms.location;

import android.app.PendingIntent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public interface ActivityRecognitionClient extends HasApiKey<Api.ApiOptions.NoOptions> {
    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    Task<Void> removeActivityTransitionUpdates(@NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    Task<Void> removeActivityUpdates(@NonNull PendingIntent pendingIntent);

    @NonNull
    Task<Void> removeSleepSegmentUpdates(@NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    Task<Void> requestActivityTransitionUpdates(@NonNull ActivityTransitionRequest activityTransitionRequest, @NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    Task<Void> requestActivityUpdates(long j, @NonNull PendingIntent pendingIntent);

    @RequiresPermission(anyOf = {"android.permission.ACTIVITY_RECOGNITION", "com.google.android.gms.permission.ACTIVITY_RECOGNITION"})
    @NonNull
    Task<Void> requestSleepSegmentUpdates(@NonNull PendingIntent pendingIntent, @NonNull SleepSegmentRequest sleepSegmentRequest);
}
