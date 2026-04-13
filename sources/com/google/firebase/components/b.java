package com.google.firebase.components;

/* compiled from: lambda */
public final /* synthetic */ class b implements ComponentFactory {
    public final /* synthetic */ Object a;

    public /* synthetic */ b(Object obj) {
        this.a = obj;
    }

    public final Object create(ComponentContainer componentContainer) {
        Object obj = this.a;
        Component.lambda$intoSet$4(obj, componentContainer);
        return obj;
    }
}
