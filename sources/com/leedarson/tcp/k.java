package com.leedarson.tcp;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: ProtocolHeader */
public class k {
    public static ChangeQuickRedirect changeQuickRedirect;
    private short a;
    private short b;
    private int c;

    public short a() {
        return this.a;
    }

    public void d(short magic) {
        this.a = magic;
    }

    public short b() {
        return this.b;
    }

    public void e(short msgType) {
        this.b = msgType;
    }

    public void c(int len) {
        this.c = len;
    }

    public k() {
    }

    public k(short magic, short msgType, int len) {
        this.a = magic;
        this.b = msgType;
        this.c = len;
    }
}
