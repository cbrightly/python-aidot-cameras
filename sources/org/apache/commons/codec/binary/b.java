package org.apache.commons.codec.binary;

import java.util.Arrays;

/* compiled from: BaseNCodec */
public abstract class b {
    protected final byte a = 61;
    private final int b;
    private final int c;
    protected final int d;
    private final int e;

    /* access modifiers changed from: package-private */
    public abstract void c(byte[] bArr, int i, int i2, a aVar);

    /* access modifiers changed from: package-private */
    public abstract void e(byte[] bArr, int i, int i2, a aVar);

    /* access modifiers changed from: protected */
    public abstract boolean j(byte b2);

    /* compiled from: BaseNCodec */
    public static class a {
        int a;
        long b;
        byte[] c;
        int d;
        int e;
        boolean f;
        int g;
        int h;

        a() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[]{getClass().getSimpleName(), Arrays.toString(this.c), Integer.valueOf(this.g), Boolean.valueOf(this.f), Integer.valueOf(this.a), Long.valueOf(this.b), Integer.valueOf(this.h), Integer.valueOf(this.d), Integer.valueOf(this.e)});
        }
    }

    protected b(int unencodedBlockSize, int encodedBlockSize, int lineLength, int chunkSeparatorLength) {
        this.b = unencodedBlockSize;
        this.c = encodedBlockSize;
        this.d = lineLength > 0 && chunkSeparatorLength > 0 ? (lineLength / encodedBlockSize) * encodedBlockSize : 0;
        this.e = chunkSeparatorLength;
    }

    /* access modifiers changed from: package-private */
    public int a(a context) {
        if (context.c != null) {
            return context.d - context.e;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int h() {
        return 8192;
    }

    private byte[] l(a context) {
        byte[] bArr = context.c;
        if (bArr == null) {
            context.c = new byte[h()];
            context.d = 0;
            context.e = 0;
        } else {
            byte[] b2 = new byte[(bArr.length * 2)];
            System.arraycopy(bArr, 0, b2, 0, bArr.length);
            context.c = b2;
        }
        return context.c;
    }

    /* access modifiers changed from: protected */
    public byte[] g(int size, a context) {
        byte[] bArr = context.c;
        if (bArr == null || bArr.length < context.d + size) {
            return l(context);
        }
        return bArr;
    }

    /* access modifiers changed from: package-private */
    public int k(byte[] b2, int bPos, int bAvail, a context) {
        if (context.c == null) {
            return context.f != 0 ? -1 : 0;
        }
        int len = Math.min(a(context), bAvail);
        System.arraycopy(context.c, context.e, b2, bPos, len);
        int i = context.e + len;
        context.e = i;
        if (i >= context.d) {
            context.c = null;
        }
        return len;
    }

    public byte[] d(byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        a context = new a();
        c(pArray, 0, pArray.length, context);
        c(pArray, 0, -1, context);
        byte[] result = new byte[context.d];
        k(result, 0, result.length, context);
        return result;
    }

    public byte[] f(byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        a context = new a();
        e(pArray, 0, pArray.length, context);
        e(pArray, 0, -1, context);
        byte[] buf = new byte[(context.d - context.e)];
        k(buf, 0, buf.length, context);
        return buf;
    }

    /* access modifiers changed from: protected */
    public boolean b(byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        }
        for (byte element : arrayOctet) {
            if (61 == element || j(element)) {
                return true;
            }
        }
        return false;
    }

    public long i(byte[] pArray) {
        int length = pArray.length;
        int i = this.b;
        long len = ((long) (((length + i) - 1) / i)) * ((long) this.c);
        int i2 = this.d;
        if (i2 > 0) {
            return len + ((((((long) i2) + len) - 1) / ((long) i2)) * ((long) this.e));
        }
        return len;
    }
}
