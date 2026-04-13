package com.google.firebase.components;

import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class q implements ComponentRegistrarProcessor {
    public static final /* synthetic */ q a = new q();

    private /* synthetic */ q() {
    }

    public final List processRegistrar(ComponentRegistrar componentRegistrar) {
        return componentRegistrar.getComponents();
    }
}
