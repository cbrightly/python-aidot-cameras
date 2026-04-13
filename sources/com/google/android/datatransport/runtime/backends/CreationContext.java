package com.google.android.datatransport.runtime.backends;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.datatransport.runtime.time.Clock;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CreationContext {
    private static final String DEFAULT_BACKEND_NAME = "cct";

    public abstract Context getApplicationContext();

    @NonNull
    public abstract String getBackendName();

    public abstract Clock getMonotonicClock();

    public abstract Clock getWallClock();

    public static CreationContext create(Context applicationContext, Clock wallClock, Clock monotonicClock) {
        return new AutoValue_CreationContext(applicationContext, wallClock, monotonicClock, DEFAULT_BACKEND_NAME);
    }

    public static CreationContext create(Context applicationContext, Clock wallClock, Clock monotonicClock, String backendName) {
        return new AutoValue_CreationContext(applicationContext, wallClock, monotonicClock, backendName);
    }
}
