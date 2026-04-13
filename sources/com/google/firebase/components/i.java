package com.google.firebase.components;

import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class i implements Provider {
    public final /* synthetic */ ComponentRegistrar a;

    public /* synthetic */ i(ComponentRegistrar componentRegistrar) {
        this.a = componentRegistrar;
    }

    public final Object get() {
        ComponentRegistrar componentRegistrar = this.a;
        ComponentRuntime.Builder.lambda$addComponentRegistrar$0(componentRegistrar);
        return componentRegistrar;
    }
}
