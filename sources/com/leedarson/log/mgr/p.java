package com.leedarson.log.mgr;

import io.reactivex.m;
import io.reactivex.n;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class p implements n {
    public final /* synthetic */ t a;
    public final /* synthetic */ File[] b;

    public /* synthetic */ p(t tVar, File[] fileArr) {
        this.a = tVar;
        this.b = fileArr;
    }

    public final void subscribe(m mVar) {
        this.a.i(this.b, mVar);
    }
}
