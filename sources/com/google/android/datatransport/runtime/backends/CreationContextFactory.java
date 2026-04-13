package com.google.android.datatransport.runtime.backends;

import android.content.Context;
import com.google.android.datatransport.runtime.time.Clock;
import com.google.android.datatransport.runtime.time.Monotonic;
import com.google.android.datatransport.runtime.time.WallTime;

public class CreationContextFactory {
    private final Context applicationContext;
    private final Clock monotonicClock;
    private final Clock wallClock;

    CreationContextFactory(Context applicationContext2, @WallTime Clock wallClock2, @Monotonic Clock monotonicClock2) {
        this.applicationContext = applicationContext2;
        this.wallClock = wallClock2;
        this.monotonicClock = monotonicClock2;
    }

    /* access modifiers changed from: package-private */
    public CreationContext create(String backendName) {
        return CreationContext.create(this.applicationContext, this.wallClock, this.monotonicClock, backendName);
    }
}
