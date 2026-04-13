package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Xof;

public final class KeyedHashFunctions {
    private final Digest a;
    private final int b;

    protected KeyedHashFunctions(Digest digest, int digestSize) {
        if (digest != null) {
            this.a = digest;
            this.b = digestSize;
            return;
        }
        throw new NullPointerException("digest == null");
    }

    private byte[] e(int fixedValue, byte[] key, byte[] index) {
        byte[] in = XMSSUtil.q((long) fixedValue, this.b);
        this.a.update(in, 0, in.length);
        this.a.update(key, 0, key.length);
        this.a.update(index, 0, index.length);
        int i = this.b;
        byte[] out = new byte[i];
        Digest digest = this.a;
        if (digest instanceof Xof) {
            ((Xof) digest).j(out, 0, i);
        } else {
            digest.c(out, 0);
        }
        return out;
    }

    /* access modifiers changed from: protected */
    public byte[] a(byte[] key, byte[] in) {
        int length = key.length;
        int i = this.b;
        if (length != i) {
            throw new IllegalArgumentException("wrong key length");
        } else if (in.length == i) {
            return e(0, key, in);
        } else {
            throw new IllegalArgumentException("wrong in length");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] b(byte[] key, byte[] in) {
        int length = key.length;
        int i = this.b;
        if (length != i) {
            throw new IllegalArgumentException("wrong key length");
        } else if (in.length == i * 2) {
            return e(1, key, in);
        } else {
            throw new IllegalArgumentException("wrong in length");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] c(byte[] key, byte[] in) {
        if (key.length == this.b * 3) {
            return e(2, key, in);
        }
        throw new IllegalArgumentException("wrong key length");
    }

    /* access modifiers changed from: protected */
    public byte[] d(byte[] key, byte[] address) {
        if (key.length != this.b) {
            throw new IllegalArgumentException("wrong key length");
        } else if (address.length == 32) {
            return e(3, key, address);
        } else {
            throw new IllegalArgumentException("wrong address length");
        }
    }
}
