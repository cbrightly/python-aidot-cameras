package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;

public class GCFBBlockCipher extends StreamBlockCipher {
    private static final byte[] b = {105, 0, 114, 34, 100, -55, 4, 35, -115, 58, -37, -106, 70, -23, 42, -60, 24, -2, -84, -108, 0, -19, 7, 18, -64, -122, -36, -62, -17, 76, -87, 43};
    private final CFBBlockCipher c;
    private KeyParameter d;
    private long e = 0;
    private boolean f;

    public GCFBBlockCipher(BlockCipher engine) {
        super(engine);
        this.c = new CFBBlockCipher(engine, engine.c() * 8);
    }

    public void a(boolean forEncryption, CipherParameters params) {
        this.e = 0;
        this.c.a(forEncryption, params);
        this.f = forEncryption;
        if (params instanceof ParametersWithIV) {
            params = ((ParametersWithIV) params).b();
        }
        if (params instanceof ParametersWithRandom) {
            params = ((ParametersWithRandom) params).a();
        }
        if (params instanceof ParametersWithSBox) {
            params = ((ParametersWithSBox) params).a();
        }
        this.d = (KeyParameter) params;
    }

    public String b() {
        String name = this.c.b();
        return name.substring(0, name.indexOf(47)) + "/G" + name.substring(name.indexOf(47) + 1);
    }

    public int c() {
        return this.c.c();
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        d(in, inOff, this.c.c(), out, outOff);
        return this.c.c();
    }

    /* access modifiers changed from: protected */
    public byte g(byte b2) {
        long j = this.e;
        if (j > 0 && j % 1024 == 0) {
            BlockCipher base = this.c.h();
            base.a(false, this.d);
            byte[] nextKey = new byte[32];
            byte[] bArr = b;
            base.f(bArr, 0, nextKey, 0);
            base.f(bArr, 8, nextKey, 8);
            base.f(bArr, 16, nextKey, 16);
            base.f(bArr, 24, nextKey, 24);
            KeyParameter keyParameter = new KeyParameter(nextKey);
            this.d = keyParameter;
            base.a(true, keyParameter);
            byte[] iv = this.c.k();
            base.f(iv, 0, iv, 0);
            this.c.a(this.f, new ParametersWithIV(this.d, iv));
        }
        this.e++;
        return this.c.g(b2);
    }

    public void reset() {
        this.e = 0;
        this.c.reset();
    }
}
