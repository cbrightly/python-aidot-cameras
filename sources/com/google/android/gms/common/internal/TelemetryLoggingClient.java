package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.DoNotMock;
import com.google.errorprone.annotations.RestrictedInheritance;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@RestrictedInheritance(allowedOnPath = ".*java.*/com/google/android/gms.*", explanation = "Use canonical fakes instead.", link = "go/gmscore-restrictedinheritance")
@KeepForSdk
@DoNotMock("Use canonical fakes instead. go/cheezhead-testing-methodology")
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public interface TelemetryLoggingClient extends HasApiKey<TelemetryLoggingOptions> {
    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    Task<Void> log(@NonNull TelemetryData telemetryData);
}
