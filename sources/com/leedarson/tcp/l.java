package com.leedarson.tcp;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: ProtocolMsg */
public class l {
    public static ChangeQuickRedirect changeQuickRedirect;
    private k a = new k();
    private byte[] b;

    public byte[] a() {
        return this.b;
    }

    public void c(byte[] bodyBytes) {
        this.b = bodyBytes;
    }

    public k b() {
        return this.a;
    }

    public void d(k protocolHeader) {
        this.a = protocolHeader;
    }
}
