package com.leedarson.serviceimpl.blec075;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ BleC075ServiceImpl c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;

    public /* synthetic */ a(BleC075ServiceImpl bleC075ServiceImpl, String str, String str2) {
        this.c = bleC075ServiceImpl;
        this.d = str;
        this.f = str2;
    }

    public final void run() {
        this.c.p(this.d, this.f);
    }
}
