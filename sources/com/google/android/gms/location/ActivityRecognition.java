package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.location.zzag;
import com.google.android.gms.internal.location.zzw;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public class ActivityRecognition {
    @NonNull
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> API = zzag.zzb;
    @NonNull
    @Deprecated
    public static final ActivityRecognitionApi ActivityRecognitionApi = new zzw();

    private ActivityRecognition() {
    }

    @NonNull
    public static ActivityRecognitionClient getClient(@NonNull Activity activity) {
        return new zzag(activity);
    }

    @NonNull
    public static ActivityRecognitionClient getClient(@NonNull Context context) {
        return new zzag(context);
    }
}
