package com.leedarson.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: RoutineRule */
public class m {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = -1;
    private final int b = 2;
    private final int c = 3;
    private final int d = 5;
    private final int e = 7;
    private final int f = 4;
    private final int g = 6;
    private final int h = 0;
    private final int i = 1;
    private final int j = 255;
    public int k = -1;
    public int l;
    public int m = -1;
    public int n = -1;
    public float o = -1.0f;
    public float p = -1.0f;
    public float q = -1.0f;
    public int r;
    public String s;
    public int t;
    public int u;
    public int v;

    public int a() {
        boolean hasHsl = this.r == 1;
        if (this.k != -1) {
            return 2;
        }
        if (this.m != -1) {
            if (this.n != -1) {
                return 5;
            }
            if (hasHsl) {
                return 7;
            }
            return 3;
        } else if (this.n != -1) {
            return 4;
        } else {
            if (hasHsl) {
                return 6;
            }
            int i2 = this.l;
            if (i2 > 0) {
                return 1;
            }
            if (i2 == 0) {
                return 0;
            }
            return 255;
        }
    }

    public byte[] d() {
        return new byte[]{(byte) this.m, (byte) this.t, (byte) this.u, (byte) this.v};
    }

    public byte c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11529, new Class[0], Byte.TYPE);
        if (proxy.isSupported) {
            return ((Byte) proxy.result).byteValue();
        }
        return (byte) (a() | 128);
    }

    public byte b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11531, new Class[0], Byte.TYPE);
        if (proxy.isSupported) {
            return ((Byte) proxy.result).byteValue();
        }
        if ("Party".equals(this.s)) {
            return 17;
        }
        if ("Dynamic".equals(this.s)) {
            return 9;
        }
        if ("Calm".equals(this.s)) {
            return 1;
        }
        return 17;
    }
}
