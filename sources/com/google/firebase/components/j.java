package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* compiled from: lambda */
public final /* synthetic */ class j implements Runnable {
    public final /* synthetic */ LazySet c;
    public final /* synthetic */ Provider d;

    public /* synthetic */ j(LazySet lazySet, Provider provider) {
        this.c = lazySet;
        this.d = provider;
    }

    public final void run() {
        this.c.add(this.d);
    }
}
