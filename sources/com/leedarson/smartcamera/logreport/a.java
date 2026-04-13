package com.leedarson.smartcamera.logreport;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ c c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;
    public final /* synthetic */ String x;

    public /* synthetic */ a(c cVar, String str, String str2, String str3, String str4) {
        this.c = cVar;
        this.d = str;
        this.f = str2;
        this.q = str3;
        this.x = str4;
    }

    public final void run() {
        this.c.d(this.d, this.f, this.q, this.x);
    }
}
