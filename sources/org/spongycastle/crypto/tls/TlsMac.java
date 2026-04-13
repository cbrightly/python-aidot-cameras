package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.digests.LongDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class TlsMac {
    protected TlsContext a;
    protected byte[] b;
    protected Mac c;
    protected int d;
    protected int e;
    protected int f;

    public TlsMac(TlsContext context, Digest digest, byte[] key, int keyOff, int keyLen) {
        this.a = context;
        KeyParameter keyParameter = new KeyParameter(key, keyOff, keyLen);
        this.b = Arrays.h(keyParameter.a());
        if (digest instanceof LongDigest) {
            this.d = 128;
            this.e = 16;
        } else {
            this.d = 64;
            this.e = 8;
        }
        if (TlsUtils.P(context)) {
            this.c = new SSL3Mac(digest);
            if (digest.e() == 20) {
                this.e = 4;
            }
        } else {
            this.c = new HMac(digest);
        }
        this.c.a(keyParameter);
        this.f = this.c.e();
        if (context.f().m) {
            this.f = Math.min(this.f, 10);
        }
    }

    public int d() {
        return this.f;
    }

    public byte[] a(long seqNo, short type, byte[] message, int offset, int length) {
        ProtocolVersion serverVersion = this.a.b();
        boolean isSSL = serverVersion.j();
        byte[] macHeader = new byte[(isSSL ? 11 : 13)];
        TlsUtils.I0(seqNo, macHeader, 0);
        TlsUtils.M0(type, macHeader, 8);
        if (!isSSL) {
            TlsUtils.S0(serverVersion, macHeader, 9);
        }
        TlsUtils.E0(length, macHeader, macHeader.length - 2);
        this.c.update(macHeader, 0, macHeader.length);
        this.c.update(message, offset, length);
        byte[] result = new byte[this.c.e()];
        this.c.c(result, 0);
        return e(result);
    }

    public byte[] b(long seqNo, short type, byte[] message, int offset, int length, int fullLength, byte[] dummyData) {
        byte[] result = a(seqNo, type, message, offset, length);
        int headerLength = TlsUtils.P(this.a) ? 11 : 13;
        int extra = c(headerLength + fullLength) - c(headerLength + length);
        while (true) {
            extra--;
            if (extra >= 0) {
                this.c.update(dummyData, 0, this.d);
            } else {
                this.c.d(dummyData[0]);
                this.c.reset();
                return result;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int c(int inputLength) {
        return (this.e + inputLength) / this.d;
    }

    /* access modifiers changed from: protected */
    public byte[] e(byte[] bs) {
        int length = bs.length;
        int i = this.f;
        if (length <= i) {
            return bs;
        }
        return Arrays.x(bs, i);
    }
}
