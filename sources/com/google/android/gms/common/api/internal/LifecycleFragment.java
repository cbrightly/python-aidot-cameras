package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface LifecycleFragment {
    @KeepForSdk
    void addCallback(@NonNull String str, @NonNull LifecycleCallback lifecycleCallback);

    @KeepForSdk
    @Nullable
    <T extends LifecycleCallback> T getCallbackOrNull(@NonNull String str, @NonNull Class<T> cls);

    @KeepForSdk
    @Nullable
    Activity getLifecycleActivity();

    @KeepForSdk
    boolean isCreated();

    @KeepForSdk
    boolean isStarted();

    @KeepForSdk
    void startActivityForResult(@NonNull Intent intent, int i);
}
