package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class h implements Provider {
    public final /* synthetic */ ComponentRegistrar a;

    public /* synthetic */ h(ComponentRegistrar componentRegistrar) {
        this.a = componentRegistrar;
    }

    public final Object get() {
        ComponentRegistrar componentRegistrar = this.a;
        ComponentRuntime.lambda$toProviders$1(componentRegistrar);
        return componentRegistrar;
    }
}
