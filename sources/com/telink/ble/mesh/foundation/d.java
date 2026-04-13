package com.telink.ble.mesh.foundation;

import io.reactivex.functions.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ MeshController c;
    public final /* synthetic */ AtomicBoolean d;
    public final /* synthetic */ String f;

    public /* synthetic */ d(MeshController meshController, AtomicBoolean atomicBoolean, String str) {
        this.c = meshController;
        this.d = atomicBoolean;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.Q0(this.d, this.f, (Throwable) obj);
    }
}
