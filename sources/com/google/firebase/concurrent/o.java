package com.google.firebase.concurrent;

import com.google.firebase.inject.Provider;
import java.util.concurrent.Executors;

/* compiled from: lambda */
public final /* synthetic */ class o implements Provider {
    public static final /* synthetic */ o a = new o();

    private /* synthetic */ o() {
    }

    public final Object get() {
        return Executors.newSingleThreadScheduledExecutor(ExecutorsRegistrar.factory("Firebase Scheduler", 0));
    }
}
