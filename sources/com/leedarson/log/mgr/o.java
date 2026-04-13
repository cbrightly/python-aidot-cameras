package com.leedarson.log.mgr;

import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    public final /* synthetic */ r c;
    public final /* synthetic */ File d;

    public /* synthetic */ o(r rVar, File file) {
        this.c = rVar;
        this.d = file;
    }

    public final void run() {
        this.c.n(this.d);
    }
}
