package com.google.firebase.heartbeatinfo;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.Qualified;

/* compiled from: lambda */
public final /* synthetic */ class d implements ComponentFactory {
    public final /* synthetic */ Qualified a;

    public /* synthetic */ d(Qualified qualified) {
        this.a = qualified;
    }

    public final Object create(ComponentContainer componentContainer) {
        return DefaultHeartBeatController.lambda$component$3(this.a, componentContainer);
    }
}
