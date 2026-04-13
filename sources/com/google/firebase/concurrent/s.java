package com.google.firebase.concurrent;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: lambda */
public final /* synthetic */ class s implements ComponentFactory {
    public static final /* synthetic */ s a = new s();

    private /* synthetic */ s() {
    }

    public final Object create(ComponentContainer componentContainer) {
        return ExecutorsRegistrar.LITE_EXECUTOR.get();
    }
}
