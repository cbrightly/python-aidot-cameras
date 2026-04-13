package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class g implements Provider {
    public final /* synthetic */ ComponentRuntime a;
    public final /* synthetic */ Component b;

    public /* synthetic */ g(ComponentRuntime componentRuntime, Component component) {
        this.a = componentRuntime;
        this.b = component;
    }

    public final Object get() {
        return this.a.a(this.b);
    }
}
