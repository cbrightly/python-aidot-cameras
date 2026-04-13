package com.leedarson.log.mgr;

import io.reactivex.functions.f;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class b implements f {
    public final /* synthetic */ File c;

    public /* synthetic */ b(File file) {
        this.c = file;
    }

    public final Object apply(Object obj) {
        return q.H(this.c, (Boolean) obj);
    }
}
