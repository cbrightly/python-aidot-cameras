package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import androidx.annotation.NonNull;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class ResolvableApiException extends ApiException {
    public ResolvableApiException(@NonNull Status status) {
        super(status);
    }

    @NonNull
    public PendingIntent getResolution() {
        return getStatus().getResolution();
    }

    public void startResolutionForResult(@NonNull Activity activity, int requestCode) {
        getStatus().startResolutionForResult(activity, requestCode);
    }
}
