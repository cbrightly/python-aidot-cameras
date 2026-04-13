package com.leedarson.log.mgr;

import io.reactivex.functions.f;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class k implements f {
    public final /* synthetic */ q c;

    public /* synthetic */ k(q qVar) {
        this.c = qVar;
    }

    public final Object apply(Object obj) {
        return this.c.L((File) obj);
    }
}
