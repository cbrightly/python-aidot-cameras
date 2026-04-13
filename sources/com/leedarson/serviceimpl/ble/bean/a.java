package com.leedarson.serviceimpl.ble.bean;

import com.leedarson.serviceimpl.ble.manager.d;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;

/* compiled from: ResponseHeader */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a;
    private int[] b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;

    public String d() {
        return this.h;
    }

    public a(byte[] data) {
        if (data != null && data.length >= 7) {
            this.a = data[0] & 255;
            this.b = d.b(data[1]);
            this.c = d.a(data[2]);
            this.d = d.a(data[3]);
            this.g = d.a(data[4]);
            this.e = d.a(data[5]);
            this.f = d.a(data[6]);
            this.h = d.d(data);
        }
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6252, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ResponseHeader{sequenceNum=" + this.a + ", packetFlag=" + Arrays.toString(this.b) + ", sourceId='" + this.c + '\'' + ", destinationId='" + this.d + '\'' + ", commandId='" + this.e + '\'' + ", status='" + this.f + '\'' + ", categoryId='" + this.g + '\'' + ", payload='" + this.h + '\'' + '}';
    }

    public String a() {
        return this.g;
    }

    public int e() {
        return this.a;
    }

    public String f() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String b() {
        return this.e;
    }

    public String g() {
        return this.f;
    }
}
