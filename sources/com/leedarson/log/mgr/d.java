package com.leedarson.log.mgr;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class d implements f {
    public final /* synthetic */ q c;

    public /* synthetic */ d(q qVar) {
        this.c = qVar;
    }

    public final Object apply(Object obj) {
        Boolean bool = (Boolean) obj;
        this.c.G(bool);
        return bool;
    }
}
