package org.spongycastle.util;

import java.math.BigInteger;
import java.util.NoSuchElementException;

public final class Arrays {
    private Arrays() {
    }

    public static boolean c(char[] a, char[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        for (int i = 0; i != a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean b(byte[] a, byte[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        for (int i = 0; i != a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean g(short[] a, short[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        for (int i = 0; i != a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean u(byte[] expected, byte[] supplied) {
        if (expected == supplied) {
            return true;
        }
        if (expected == null || supplied == null) {
            return false;
        }
        if (expected.length != supplied.length) {
            return true ^ u(expected, expected);
        }
        int nonEqual = 0;
        for (int i = 0; i != expected.length; i++) {
            nonEqual |= expected[i] ^ supplied[i];
        }
        if (nonEqual == 0) {
            return true;
        }
        return false;
    }

    public static boolean d(int[] a, int[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        for (int i = 0; i != a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean e(long[] a, long[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        for (int i = 0; i != a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean f(Object[] a, Object[] b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        for (int i = 0; i != a.length; i++) {
            Object objA = a[i];
            Object objB = b[i];
            if (objA == null) {
                if (objB != null) {
                    return false;
                }
            } else if (!objA.equals(objB)) {
                return false;
            }
        }
        return true;
    }

    public static boolean w(short[] a, short n) {
        for (short s : a) {
            if (s == n) {
                return true;
            }
        }
        return false;
    }

    public static boolean v(int[] a, int n) {
        for (int i : a) {
            if (i == n) {
                return true;
            }
        }
        return false;
    }

    public static void F(byte[] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    public static void G(long[] array, long value) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    public static void H(short[] array, short value) {
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    public static int J(byte[] data) {
        if (data == null) {
            return 0;
        }
        int i = data.length;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            hc = (hc * 257) ^ data[i];
        }
    }

    public static int K(char[] data) {
        if (data == null) {
            return 0;
        }
        int i = data.length;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            hc = (hc * 257) ^ data[i];
        }
    }

    public static int L(int[] data) {
        if (data == null) {
            return 0;
        }
        int i = data.length;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            hc = (hc * 257) ^ data[i];
        }
    }

    public static int M(int[] data, int off, int len) {
        if (data == null) {
            return 0;
        }
        int i = len;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            hc = (hc * 257) ^ data[off + i];
        }
    }

    public static int N(long[] data, int off, int len) {
        if (data == null) {
            return 0;
        }
        int i = len;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            long di = data[off + i];
            hc = (((hc * 257) ^ ((int) di)) * 257) ^ ((int) (di >>> 32));
        }
    }

    public static int R(short[][][] shorts) {
        int hc = 0;
        for (int i = 0; i != shorts.length; i++) {
            hc = (hc * 257) + Q(shorts[i]);
        }
        return hc;
    }

    public static int Q(short[][] shorts) {
        int hc = 0;
        for (int i = 0; i != shorts.length; i++) {
            hc = (hc * 257) + P(shorts[i]);
        }
        return hc;
    }

    public static int P(short[] data) {
        if (data == null) {
            return 0;
        }
        int i = data.length;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            hc = (hc * 257) ^ (data[i] & 255);
        }
    }

    public static int O(Object[] data) {
        if (data == null) {
            return 0;
        }
        int i = data.length;
        int hc = i + 1;
        while (true) {
            i--;
            if (i < 0) {
                return hc;
            }
            hc = (hc * 257) ^ data[i].hashCode();
        }
    }

    public static byte[] h(byte[] data) {
        if (data == null) {
            return null;
        }
        byte[] copy = new byte[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static char[] j(char[] data) {
        if (data == null) {
            return null;
        }
        char[] copy = new char[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static byte[] i(byte[] data, byte[] existing) {
        if (data == null) {
            return null;
        }
        if (existing == null || existing.length != data.length) {
            return h(data);
        }
        System.arraycopy(data, 0, existing, 0, existing.length);
        return existing;
    }

    public static byte[][] p(byte[][] data) {
        if (data == null) {
            return null;
        }
        byte[][] copy = new byte[data.length][];
        for (int i = 0; i != copy.length; i++) {
            copy[i] = h(data[i]);
        }
        return copy;
    }

    public static byte[][][] q(byte[][][] data) {
        if (data == null) {
            return null;
        }
        byte[][][] copy = new byte[data.length][][];
        for (int i = 0; i != copy.length; i++) {
            copy[i] = p(data[i]);
        }
        return copy;
    }

    public static int[] k(int[] data) {
        if (data == null) {
            return null;
        }
        int[] copy = new int[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static long[] l(long[] data) {
        if (data == null) {
            return null;
        }
        long[] copy = new long[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static long[] m(long[] data, long[] existing) {
        if (data == null) {
            return null;
        }
        if (existing == null || existing.length != data.length) {
            return l(data);
        }
        System.arraycopy(data, 0, existing, 0, existing.length);
        return existing;
    }

    public static short[] o(short[] data) {
        if (data == null) {
            return null;
        }
        short[] copy = new short[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static BigInteger[] n(BigInteger[] data) {
        if (data == null) {
            return null;
        }
        BigInteger[] copy = new BigInteger[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static byte[] x(byte[] data, int newLength) {
        byte[] tmp = new byte[newLength];
        if (newLength < data.length) {
            System.arraycopy(data, 0, tmp, 0, newLength);
        } else {
            System.arraycopy(data, 0, tmp, 0, data.length);
        }
        return tmp;
    }

    public static int[] y(int[] data, int newLength) {
        int[] tmp = new int[newLength];
        if (newLength < data.length) {
            System.arraycopy(data, 0, tmp, 0, newLength);
        } else {
            System.arraycopy(data, 0, tmp, 0, data.length);
        }
        return tmp;
    }

    public static long[] z(long[] data, int newLength) {
        long[] tmp = new long[newLength];
        if (newLength < data.length) {
            System.arraycopy(data, 0, tmp, 0, newLength);
        } else {
            System.arraycopy(data, 0, tmp, 0, data.length);
        }
        return tmp;
    }

    public static BigInteger[] A(BigInteger[] data, int newLength) {
        BigInteger[] tmp = new BigInteger[newLength];
        if (newLength < data.length) {
            System.arraycopy(data, 0, tmp, 0, newLength);
        } else {
            System.arraycopy(data, 0, tmp, 0, data.length);
        }
        return tmp;
    }

    public static byte[] B(byte[] data, int from, int to) {
        int newLength = I(from, to);
        byte[] tmp = new byte[newLength];
        if (data.length - from < newLength) {
            System.arraycopy(data, from, tmp, 0, data.length - from);
        } else {
            System.arraycopy(data, from, tmp, 0, newLength);
        }
        return tmp;
    }

    public static int[] C(int[] data, int from, int to) {
        int newLength = I(from, to);
        int[] tmp = new int[newLength];
        if (data.length - from < newLength) {
            System.arraycopy(data, from, tmp, 0, data.length - from);
        } else {
            System.arraycopy(data, from, tmp, 0, newLength);
        }
        return tmp;
    }

    public static long[] D(long[] data, int from, int to) {
        int newLength = I(from, to);
        long[] tmp = new long[newLength];
        if (data.length - from < newLength) {
            System.arraycopy(data, from, tmp, 0, data.length - from);
        } else {
            System.arraycopy(data, from, tmp, 0, newLength);
        }
        return tmp;
    }

    public static BigInteger[] E(BigInteger[] data, int from, int to) {
        int newLength = I(from, to);
        BigInteger[] tmp = new BigInteger[newLength];
        if (data.length - from < newLength) {
            System.arraycopy(data, from, tmp, 0, data.length - from);
        } else {
            System.arraycopy(data, from, tmp, 0, newLength);
        }
        return tmp;
    }

    private static int I(int from, int to) {
        int newLength = to - from;
        if (newLength >= 0) {
            return newLength;
        }
        StringBuffer sb = new StringBuffer(from);
        sb.append(" > ");
        sb.append(to);
        throw new IllegalArgumentException(sb.toString());
    }

    public static short[] a(short[] a, short b) {
        if (a == null) {
            return new short[]{b};
        }
        int length = a.length;
        short[] result = new short[(length + 1)];
        System.arraycopy(a, 0, result, 0, length);
        result[length] = b;
        return result;
    }

    public static byte[] r(byte[] a, byte[] b) {
        if (a != null && b != null) {
            byte[] rv = new byte[(a.length + b.length)];
            System.arraycopy(a, 0, rv, 0, a.length);
            System.arraycopy(b, 0, rv, a.length, b.length);
            return rv;
        } else if (b != null) {
            return h(b);
        } else {
            return h(a);
        }
    }

    public static byte[] s(byte[] a, byte[] b, byte[] c) {
        if (a != null && b != null && c != null) {
            byte[] rv = new byte[(a.length + b.length + c.length)];
            System.arraycopy(a, 0, rv, 0, a.length);
            System.arraycopy(b, 0, rv, a.length, b.length);
            System.arraycopy(c, 0, rv, a.length + b.length, c.length);
            return rv;
        } else if (a == null) {
            return r(b, c);
        } else {
            if (b == null) {
                return r(a, c);
            }
            return r(a, b);
        }
    }

    public static byte[] t(byte[] a, byte[] b, byte[] c, byte[] d) {
        if (a != null && b != null && c != null && d != null) {
            byte[] rv = new byte[(a.length + b.length + c.length + d.length)];
            System.arraycopy(a, 0, rv, 0, a.length);
            System.arraycopy(b, 0, rv, a.length, b.length);
            System.arraycopy(c, 0, rv, a.length + b.length, c.length);
            System.arraycopy(d, 0, rv, a.length + b.length + c.length, d.length);
            return rv;
        } else if (d == null) {
            return s(a, b, c);
        } else {
            if (c == null) {
                return s(a, b, d);
            }
            if (b == null) {
                return s(a, c, d);
            }
            return s(b, c, d);
        }
    }

    public static byte[] S(byte[] a, byte b) {
        if (a == null) {
            return new byte[]{b};
        }
        int length = a.length;
        byte[] result = new byte[(length + 1)];
        System.arraycopy(a, 0, result, 1, length);
        result[0] = b;
        return result;
    }

    public static byte[] T(byte[] a) {
        if (a == null) {
            return null;
        }
        int p1 = 0;
        int p2 = a.length;
        byte[] result = new byte[p2];
        while (true) {
            p2--;
            if (p2 < 0) {
                return result;
            }
            result[p2] = a[p1];
            p1++;
        }
    }

    public static int[] U(int[] a) {
        if (a == null) {
            return null;
        }
        int p1 = 0;
        int p2 = a.length;
        int[] result = new int[p2];
        while (true) {
            p2--;
            if (p2 < 0) {
                return result;
            }
            result[p2] = a[p1];
            p1++;
        }
    }

    public static class Iterator<T> implements java.util.Iterator<T> {
        private final T[] c;
        private int d = 0;

        public Iterator(T[] dataArray) {
            this.c = dataArray;
        }

        public boolean hasNext() {
            return this.d < this.c.length;
        }

        public T next() {
            int i = this.d;
            T[] tArr = this.c;
            if (i != tArr.length) {
                this.d = i + 1;
                return tArr[i];
            }
            throw new NoSuchElementException("Out of elements: " + this.d);
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from an Array.");
        }
    }
}
