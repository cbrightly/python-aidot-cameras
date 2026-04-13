package com.google.firebase.tracing;

import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: lambda */
public final /* synthetic */ class a implements ComponentFactory {
    public final /* synthetic */ String a;
    public final /* synthetic */ Component b;

    public /* synthetic */ a(String str, Component component) {
        this.a = str;
        this.b = component;
    }

    public final Object create(ComponentContainer componentContainer) {
        return ComponentMonitor.lambda$processRegistrar$0(this.a, this.b, componentContainer);
    }
}
