package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: lambda */
public final /* synthetic */ class j implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ List a;
    public final /* synthetic */ ScheduledExecutorService b;
    public final /* synthetic */ Executor c;
    public final /* synthetic */ long d;
    public final /* synthetic */ boolean e;

    public /* synthetic */ j(List list, ScheduledExecutorService scheduledExecutorService, Executor executor, long j, boolean z) {
        this.a = list;
        this.b = scheduledExecutorService;
        this.c = executor;
        this.d = j;
        this.e = z;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return DeferrableSurfaces.lambda$surfaceListWithTimeout$3(this.a, this.b, this.c, this.d, this.e, completer);
    }
}
