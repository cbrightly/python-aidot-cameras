package com.leedarson.tcp.ipc;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: RecordVideoHeader */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private long j;
    private int k;

    public int j() {
        return this.k;
    }

    public void u(int version) {
        this.k = version;
    }

    public int g() {
        return this.h;
    }

    public void r(int sequence) {
        this.h = sequence;
    }

    public int a() {
        return this.a;
    }

    public void k(int cmd) {
        this.a = cmd;
    }

    public int h() {
        return this.i;
    }

    public void s(int subCmd) {
        this.i = subCmd;
    }

    public int b() {
        return this.b;
    }

    public void l(int cmdParam) {
        this.b = cmdParam;
    }

    public void o(int payloadlen) {
        this.e = payloadlen;
    }

    public long i() {
        return this.j;
    }

    public void t(long timeStamp) {
        this.j = timeStamp;
    }

    public int c() {
        return this.c;
    }

    public void m(int context) {
        this.c = context;
    }

    public int d() {
        return this.d;
    }

    public void n(int encodeType) {
        this.d = encodeType;
    }

    public int f() {
        return this.g;
    }

    public void q(int result) {
        this.g = result;
    }

    public int e() {
        return this.f;
    }

    public void p(int reserve) {
        this.f = reserve;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10791, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "RecordVideoHeader{version=" + this.k + ", sequence=" + this.h + ", cmd=" + this.a + ", subCmd=" + this.i + ", cmdParam=" + this.b + ", payloadlen=" + this.e + ", timeStamp=" + this.j + ", context=" + this.c + ", encodeType=" + this.d + ", result=" + this.g + ", reserve=" + this.f + '}';
    }
}
