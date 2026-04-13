package com.leedarson.serviceimpl;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ Runnable c;

    public /* synthetic */ b(Runnable runnable) {
        this.c = runnable;
    }

    public final void accept(Object obj) {
        BleMeshServiceImpl.z(this.c, (Integer) obj);
    }
}
