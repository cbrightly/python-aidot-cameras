package com.leedarson.log.mgr;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ q c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;

    public /* synthetic */ f(q qVar, String str, String str2, String str3) {
        this.c = qVar;
        this.d = str;
        this.f = str2;
        this.q = str3;
    }

    public final void run() {
        this.c.C(this.d, this.f, this.q);
    }
}
