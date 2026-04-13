package com.google.android.libraries.places.internal;

import android.location.Location;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzcy {
    private static final long zza = TimeUnit.SECONDS.toMillis(30);
    private final FusedLocationProviderClient zzb;
    private final zzgb zzc;

    zzcy(FusedLocationProviderClient fusedLocationProviderClient, zzgb zzgb) {
        this.zzb = fusedLocationProviderClient;
        this.zzc = zzgb;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final Task zza(CancellationToken cancellationToken) {
        Task<Location> task;
        TaskCompletionSource taskCompletionSource;
        Class<FusedLocationProviderClient> cls = FusedLocationProviderClient.class;
        CurrentLocationRequest.Builder priority = new CurrentLocationRequest.Builder().setPriority(100);
        long j = zza;
        CurrentLocationRequest build = priority.setDurationMillis(j).build();
        if (cls.isInterface()) {
            task = this.zzb.getCurrentLocation(build, cancellationToken);
        } else {
            try {
                task = (Task) cls.getMethod("getCurrentLocation", new Class[]{CurrentLocationRequest.class, CancellationToken.class}).invoke(this.zzb, new Object[]{build, cancellationToken});
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        }
        zzgb zzgb = this.zzc;
        if (cancellationToken == null) {
            taskCompletionSource = new TaskCompletionSource();
        } else {
            taskCompletionSource = new TaskCompletionSource(cancellationToken);
        }
        zzgb.zza(taskCompletionSource, j, "Location timeout.");
        task.continueWithTask(new zzfz(zzgb, taskCompletionSource));
        taskCompletionSource.getTask().addOnCompleteListener(new zzga(zzgb, taskCompletionSource));
        return taskCompletionSource.getTask().continueWithTask(new zzcx(this));
    }
}
