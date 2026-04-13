package com.google.firebase.concurrent;

import com.google.firebase.inject.Provider;
import java.util.concurrent.Executors;

/* compiled from: lambda */
public final /* synthetic */ class q implements Provider {
    public static final /* synthetic */ q a = new q();

    private /* synthetic */ q() {
    }

    public final Object get() {
        return ExecutorsRegistrar.scheduled(Executors.newFixedThreadPool(Math.max(2, Runtime.getRuntime().availableProcessors()), ExecutorsRegistrar.factory("Firebase Lite", 0, ExecutorsRegistrar.litePolicy())));
    }
}
