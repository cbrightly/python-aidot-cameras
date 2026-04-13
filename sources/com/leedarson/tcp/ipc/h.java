package com.leedarson.tcp.ipc;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: RecordVideoMessage */
public class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    private g a;
    private byte[] b;

    public h(g header) {
        this.a = header;
    }

    public h(g header, byte[] payload) {
        header.o(payload.length);
        this.a = header;
        this.b = payload;
    }

    public g a() {
        return this.a;
    }

    public byte[] b() {
        return this.b;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10792, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "RecordVideoMessage [header=" + this.a + "]";
    }
}
