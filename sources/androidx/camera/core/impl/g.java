package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class g implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ DeferrableSurface a;

    public /* synthetic */ g(DeferrableSurface deferrableSurface) {
        this.a = deferrableSurface;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.a(completer);
    }
}
