package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class f implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ Map b;

    public /* synthetic */ f(Uploader uploader, Map map) {
        this.a = uploader;
        this.b = map;
    }

    public final Object execute() {
        this.a.f(this.b);
        return null;
    }
}
