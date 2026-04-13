package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.digests.SkeinEngine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.SkeinParameters;

public class SkeinMac implements Mac {
    private SkeinEngine a;

    public SkeinMac(int stateSizeBits, int digestSizeBits) {
        this.a = new SkeinEngine(stateSizeBits, digestSizeBits);
    }

    public String b() {
        return "Skein-MAC-" + (this.a.f() * 8) + "-" + (this.a.g() * 8);
    }

    public void a(CipherParameters params) {
        SkeinParameters skeinParameters;
        if (params instanceof SkeinParameters) {
            skeinParameters = (SkeinParameters) params;
        } else if (params instanceof KeyParameter) {
            skeinParameters = new SkeinParameters.Builder().c(((KeyParameter) params).a()).a();
        } else {
            throw new IllegalArgumentException("Invalid parameter passed to Skein MAC init - " + params.getClass().getName());
        }
        if (skeinParameters.a() != null) {
            this.a.h(skeinParameters);
            return;
        }
        throw new IllegalArgumentException("Skein MAC requires a key parameter.");
    }

    public int e() {
        return this.a.g();
    }

    public void reset() {
        this.a.l();
    }

    public void d(byte in) {
        this.a.r(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.s(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        return this.a.e(out, outOff);
    }
}
