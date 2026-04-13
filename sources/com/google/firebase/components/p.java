package com.google.firebase.components;

import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class p implements Deferred.DeferredHandler {
    public final /* synthetic */ Deferred.DeferredHandler a;
    public final /* synthetic */ Deferred.DeferredHandler b;

    public /* synthetic */ p(Deferred.DeferredHandler deferredHandler, Deferred.DeferredHandler deferredHandler2) {
        this.a = deferredHandler;
        this.b = deferredHandler2;
    }

    public final void handle(Provider provider) {
        OptionalProvider.lambda$whenAvailable$2(this.a, this.b, provider);
    }
}
