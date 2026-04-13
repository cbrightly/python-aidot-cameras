package com.google.firebase.datatransport;

import android.content.Context;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: lambda */
public final /* synthetic */ class a implements ComponentFactory {
    public static final /* synthetic */ a a = new a();

    private /* synthetic */ a() {
    }

    public final Object create(ComponentContainer componentContainer) {
        return TransportRuntime.initialize((Context) componentContainer.get(Context.class));
    }
}
