package com.google.firebase.concurrent;

import com.google.firebase.inject.Provider;
import java.util.concurrent.Executors;

/* compiled from: lambda */
public final /* synthetic */ class p implements Provider {
    public static final /* synthetic */ p a = new p();

    private /* synthetic */ p() {
    }

    public final Object get() {
        return ExecutorsRegistrar.scheduled(Executors.newCachedThreadPool(ExecutorsRegistrar.factory("Firebase Blocking", 11)));
    }
}
