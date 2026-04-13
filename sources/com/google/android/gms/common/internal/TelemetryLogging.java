package com.google.android.gms.common.internal;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.service.zao;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class TelemetryLogging {
    private TelemetryLogging() {
    }

    @NonNull
    @KeepForSdk
    public static TelemetryLoggingClient getClient(@NonNull Context context) {
        return getClient(context, TelemetryLoggingOptions.zaa);
    }

    @NonNull
    @KeepForSdk
    public static TelemetryLoggingClient getClient(@NonNull Context context, @NonNull TelemetryLoggingOptions options) {
        return new zao(context, options);
    }
}
