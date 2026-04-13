package com.telink.ble.mesh.foundation;

import com.leedarson.base.beans.a;
import io.reactivex.functions.e;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: lambda */
public final /* synthetic */ class f implements e {
    public final /* synthetic */ MeshController c;
    public final /* synthetic */ String d;
    public final /* synthetic */ AtomicBoolean f;

    public /* synthetic */ f(MeshController meshController, String str, AtomicBoolean atomicBoolean) {
        this.c = meshController;
        this.d = str;
        this.f = atomicBoolean;
    }

    public final void accept(Object obj) {
        this.c.O0(this.d, this.f, (a) obj);
    }
}
