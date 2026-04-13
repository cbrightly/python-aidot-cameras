package com.google.firebase.concurrent;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: lambda */
public final /* synthetic */ class u implements ComponentFactory {
    public static final /* synthetic */ u a = new u();

    private /* synthetic */ u() {
    }

    public final Object create(ComponentContainer componentContainer) {
        return ExecutorsRegistrar.BG_EXECUTOR.get();
    }
}
