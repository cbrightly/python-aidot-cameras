package com.telink.ble.mesh.core.networking;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.ble.mesh.core.message.Priority;

public class NetWorkingBytes {
    public static ChangeQuickRedirect changeQuickRedirect;
    private byte[] a;
    private String b;
    private int c;
    private Priority d;

    public NetWorkingBytes(byte[] data, int dest, Priority priority, String extra) {
        this.a = data;
        this.c = dest;
        this.d = priority;
        this.b = extra;
    }

    public int a() {
        return this.c;
    }

    public Priority d() {
        return this.d;
    }

    public byte[] c() {
        return this.a;
    }

    public String b() {
        return this.b;
    }
}
