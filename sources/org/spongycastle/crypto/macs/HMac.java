package org.spongycastle.crypto.macs;

import java.util.Hashtable;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Memoable;

public class HMac implements Mac {
    private static Hashtable a;
    private Digest b;
    private int c;
    private int d;
    private Memoable e;
    private Memoable f;
    private byte[] g;
    private byte[] h;

    static {
        Hashtable hashtable = new Hashtable();
        a = hashtable;
        hashtable.put("GOST3411", Integers.b(32));
        a.put("MD2", Integers.b(16));
        a.put("MD4", Integers.b(64));
        a.put("MD5", Integers.b(64));
        a.put("RIPEMD128", Integers.b(64));
        a.put("RIPEMD160", Integers.b(64));
        a.put("SHA-1", Integers.b(64));
        a.put("SHA-224", Integers.b(64));
        a.put("SHA-256", Integers.b(64));
        a.put("SHA-384", Integers.b(128));
        a.put("SHA-512", Integers.b(128));
        a.put("Tiger", Integers.b(64));
        a.put("Whirlpool", Integers.b(64));
    }

    private static int f(Digest digest) {
        if (digest instanceof ExtendedDigest) {
            return ((ExtendedDigest) digest).k();
        }
        Integer b2 = (Integer) a.get(digest.b());
        if (b2 != null) {
            return b2.intValue();
        }
        throw new IllegalArgumentException("unknown digest passed: " + digest.b());
    }

    public HMac(Digest digest) {
        this(digest, f(digest));
    }

    private HMac(Digest digest, int byteLength) {
        this.b = digest;
        int e2 = digest.e();
        this.c = e2;
        this.d = byteLength;
        this.g = new byte[byteLength];
        this.h = new byte[(e2 + byteLength)];
    }

    public String b() {
        return this.b.b() + "/HMAC";
    }

    public void a(CipherParameters params) {
        byte[] bArr;
        this.b.reset();
        byte[] key = ((KeyParameter) params).a();
        int keyLength = key.length;
        if (keyLength > this.d) {
            this.b.update(key, 0, keyLength);
            this.b.c(this.g, 0);
            keyLength = this.c;
        } else {
            System.arraycopy(key, 0, this.g, 0, keyLength);
        }
        int i = keyLength;
        while (true) {
            bArr = this.g;
            if (i >= bArr.length) {
                break;
            }
            bArr[i] = 0;
            i++;
        }
        System.arraycopy(bArr, 0, this.h, 0, this.d);
        g(this.g, this.d, (byte) 54);
        g(this.h, this.d, (byte) 92);
        Digest digest = this.b;
        if (digest instanceof Memoable) {
            Memoable copy = ((Memoable) digest).copy();
            this.f = copy;
            ((Digest) copy).update(this.h, 0, this.d);
        }
        Digest digest2 = this.b;
        byte[] bArr2 = this.g;
        digest2.update(bArr2, 0, bArr2.length);
        Digest digest3 = this.b;
        if (digest3 instanceof Memoable) {
            this.e = ((Memoable) digest3).copy();
        }
    }

    public int e() {
        return this.c;
    }

    public void d(byte in) {
        this.b.d(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.b.update(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        this.b.c(this.h, this.d);
        Memoable memoable = this.f;
        if (memoable != null) {
            ((Memoable) this.b).m(memoable);
            Digest digest = this.b;
            digest.update(this.h, this.d, digest.e());
        } else {
            Digest digest2 = this.b;
            byte[] bArr = this.h;
            digest2.update(bArr, 0, bArr.length);
        }
        int len = this.b.c(out, outOff);
        int i = this.d;
        while (true) {
            byte[] bArr2 = this.h;
            if (i >= bArr2.length) {
                break;
            }
            bArr2[i] = 0;
            i++;
        }
        Memoable memoable2 = this.e;
        if (memoable2 != null) {
            ((Memoable) this.b).m(memoable2);
        } else {
            Digest digest3 = this.b;
            byte[] bArr3 = this.g;
            digest3.update(bArr3, 0, bArr3.length);
        }
        return len;
    }

    public void reset() {
        this.b.reset();
        Digest digest = this.b;
        byte[] bArr = this.g;
        digest.update(bArr, 0, bArr.length);
    }

    private static void g(byte[] pad, int len, byte n) {
        for (int i = 0; i < len; i++) {
            pad[i] = (byte) (pad[i] ^ n);
        }
    }
}
