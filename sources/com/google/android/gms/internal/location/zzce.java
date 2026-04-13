package com.google.android.gms.internal.location;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzce extends GoogleApi implements SettingsClient {
    public static final /* synthetic */ int zza = 0;

    public zzce(@NonNull Activity activity) {
        super(activity, zzbp.zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public final Task<LocationSettingsResponse> checkLocationSettings(LocationSettingsRequest locationSettingsRequest) {
        return doRead(TaskApiCall.builder().run(new zzcd(locationSettingsRequest)).setMethodKey(2426).build());
    }

    public zzce(@NonNull Context context) {
        super(context, zzbp.zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
