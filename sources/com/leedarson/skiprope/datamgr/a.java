package com.leedarson.skiprope.datamgr;

import io.reactivex.m;
import io.reactivex.n;
import java.io.File;
import okhttp3.e0;

/* compiled from: lambda */
public final /* synthetic */ class a implements n {
    public final /* synthetic */ f a;
    public final /* synthetic */ e0 b;
    public final /* synthetic */ File c;

    public /* synthetic */ a(f fVar, e0 e0Var, File file) {
        this.a = fVar;
        this.b = e0Var;
        this.c = file;
    }

    public final void subscribe(m mVar) {
        this.a.j(this.b, this.c, mVar);
    }
}
