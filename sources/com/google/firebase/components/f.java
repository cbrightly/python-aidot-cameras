package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class f implements Provider {
    public final /* synthetic */ String a;

    public /* synthetic */ f(String str) {
        this.a = str;
    }

    public final Object get() {
        return ComponentDiscovery.instantiate(this.a);
    }
}
