package com.google.firebase.concurrent;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: lambda */
public final /* synthetic */ class r implements ComponentFactory {
    public static final /* synthetic */ r a = new r();

    private /* synthetic */ r() {
    }

    public final Object create(ComponentContainer componentContainer) {
        return UiExecutor.INSTANCE;
    }
}
