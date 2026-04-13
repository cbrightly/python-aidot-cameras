package com.leedarson.log.mgr;

import io.reactivex.functions.f;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class m implements f {
    public final /* synthetic */ r c;

    public /* synthetic */ m(r rVar) {
        this.c = rVar;
    }

    public final Object apply(Object obj) {
        return this.c.h((File) obj);
    }
}
