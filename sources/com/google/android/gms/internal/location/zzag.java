package com.google.android.gms.internal.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.SleepSegmentRequest;
import com.google.android.gms.location.zza;
import com.google.android.gms.location.zzb;
import com.google.android.gms.location.zzm;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzag extends GoogleApi implements ActivityRecognitionClient {
    static final Api.ClientKey zza;
    public static final Api zzb;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zza = clientKey;
        zzb = new Api("ActivityRecognition.API", new zzad(), clientKey);
    }

    public zzag(Activity activity) {
        super(activity, zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public final Task<Void> removeActivityTransitionUpdates(PendingIntent pendingIntent) {
        return doWrite(TaskApiCall.builder().run(new zzx(pendingIntent)).setMethodKey(2406).build());
    }

    public final Task<Void> removeActivityUpdates(PendingIntent pendingIntent) {
        return doWrite(TaskApiCall.builder().run(new zzy(pendingIntent)).setMethodKey(2402).build());
    }

    public final Task<Void> removeSleepSegmentUpdates(PendingIntent pendingIntent) {
        return doWrite(TaskApiCall.builder().run(new zzab(pendingIntent)).setMethodKey(2411).build());
    }

    public final Task<Void> requestActivityTransitionUpdates(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent) {
        activityTransitionRequest.zza(getContextAttributionTag());
        return doWrite(TaskApiCall.builder().run(new zzaa(activityTransitionRequest, pendingIntent)).setMethodKey(2405).build());
    }

    public final Task<Void> requestActivityUpdates(long j, PendingIntent pendingIntent) {
        zza zza2 = new zza();
        zza2.zza(j);
        zzb zzb2 = zza2.zzb();
        zzb2.zza(getContextAttributionTag());
        return doWrite(TaskApiCall.builder().run(new zzz(zzb2, pendingIntent)).setMethodKey(2401).build());
    }

    public final Task<Void> requestSleepSegmentUpdates(PendingIntent pendingIntent, SleepSegmentRequest sleepSegmentRequest) {
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        return doRead(TaskApiCall.builder().run(new zzac(this, pendingIntent, sleepSegmentRequest)).setFeatures(zzm.zzb).setMethodKey(2410).build());
    }

    public zzag(Context context) {
        super(context, zzb, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
