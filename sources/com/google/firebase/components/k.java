package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ OptionalProvider c;
    public final /* synthetic */ Provider d;

    public /* synthetic */ k(OptionalProvider optionalProvider, Provider provider) {
        this.c = optionalProvider;
        this.d = provider;
    }

    public final void run() {
        this.c.set(this.d);
    }
}
