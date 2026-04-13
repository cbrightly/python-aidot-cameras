package org.spongycastle.util;

import org.glassfish.grizzly.http.server.util.MappingData;

public abstract class Pack {
    public static int a(byte[] bs, int off) {
        int off2 = off + 1;
        int off3 = off2 + 1;
        return (bs[off] << 24) | ((bs[off2] & 255) << MappingData.PATH) | ((bs[off3] & 255) << 8) | (bs[off3 + 1] & 255);
    }

    public static void b(byte[] bs, int off, int[] ns) {
        for (int i = 0; i < ns.length; i++) {
            ns[i] = a(bs, off);
            off += 4;
        }
    }

    public static byte[] f(int n) {
        byte[] bs = new byte[4];
        d(n, bs, 0);
        return bs;
    }

    public static void d(int n, byte[] bs, int off) {
        bs[off] = (byte) (n >>> 24);
        int off2 = off + 1;
        bs[off2] = (byte) (n >>> 16);
        int off3 = off2 + 1;
        bs[off3] = (byte) (n >>> 8);
        bs[off3 + 1] = (byte) n;
    }

    public static byte[] g(int[] ns) {
        byte[] bs = new byte[(ns.length * 4)];
        e(ns, bs, 0);
        return bs;
    }

    public static void e(int[] ns, byte[] bs, int off) {
        for (int d : ns) {
            d(d, bs, off);
            off += 4;
        }
    }

    public static long c(byte[] bs, int off) {
        return ((((long) a(bs, off)) & 4294967295L) << 32) | (4294967295L & ((long) a(bs, off + 4)));
    }

    public static byte[] q(long n) {
        byte[] bs = new byte[8];
        p(n, bs, 0);
        return bs;
    }

    public static void p(long n, byte[] bs, int off) {
        d((int) (n >>> 32), bs, off);
        d((int) (4294967295L & n), bs, off + 4);
    }

    public static short o(byte[] bs, int off) {
        return (short) ((bs[off] & 255) | ((bs[off + 1] & 255) << 8));
    }

    public static int j(byte[] bs, int off) {
        int off2 = off + 1;
        int off3 = off2 + 1;
        return (bs[off] & 255) | ((bs[off2] & 255) << 8) | ((bs[off3] & 255) << MappingData.PATH) | (bs[off3 + 1] << 24);
    }

    public static void k(byte[] bs, int bOff, int[] ns, int nOff, int count) {
        for (int i = 0; i < count; i++) {
            ns[nOff + i] = j(bs, bOff);
            bOff += 4;
        }
    }

    public static int[] l(byte[] bs, int off, int count) {
        int[] ns = new int[count];
        for (int i = 0; i < ns.length; i++) {
            ns[i] = j(bs, off);
            off += 4;
        }
        return ns;
    }

    public static void v(short n, byte[] bs, int off) {
        bs[off] = (byte) n;
        bs[off + 1] = (byte) (n >>> 8);
    }

    public static void h(int n, byte[] bs, int off) {
        bs[off] = (byte) n;
        int off2 = off + 1;
        bs[off2] = (byte) (n >>> 8);
        int off3 = off2 + 1;
        bs[off3] = (byte) (n >>> 16);
        bs[off3 + 1] = (byte) (n >>> 24);
    }

    public static void i(int[] ns, byte[] bs, int off) {
        for (int h : ns) {
            h(h, bs, off);
            off += 4;
        }
    }

    public static long m(byte[] bs, int off) {
        return ((((long) j(bs, off + 4)) & 4294967295L) << 32) | (4294967295L & ((long) j(bs, off)));
    }

    public static void n(byte[] bs, int off, long[] ns) {
        for (int i = 0; i < ns.length; i++) {
            ns[i] = m(bs, off);
            off += 8;
        }
    }

    public static byte[] u(long n) {
        byte[] bs = new byte[8];
        r(n, bs, 0);
        return bs;
    }

    public static void r(long n, byte[] bs, int off) {
        h((int) (4294967295L & n), bs, off);
        h((int) (n >>> 32), bs, off + 4);
    }

    public static void t(long[] ns, byte[] bs, int off) {
        for (long r : ns) {
            r(r, bs, off);
            off += 8;
        }
    }

    public static void s(long[] ns, int nsOff, int nsLen, byte[] bs, int bsOff) {
        for (int i = 0; i < nsLen; i++) {
            r(ns[nsOff + i], bs, bsOff);
            bsOff += 8;
        }
    }
}
