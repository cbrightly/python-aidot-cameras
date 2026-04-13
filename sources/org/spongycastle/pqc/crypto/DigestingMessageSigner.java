package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DigestingMessageSigner implements Signer {
    private final Digest a;
    private final MessageSigner b;
    private boolean c;

    public void a(boolean forSigning, CipherParameters param) {
        AsymmetricKeyParameter k;
        this.c = forSigning;
        if (param instanceof ParametersWithRandom) {
            k = (AsymmetricKeyParameter) ((ParametersWithRandom) param).a();
        } else {
            k = (AsymmetricKeyParameter) param;
        }
        if (forSigning && !k.a()) {
            throw new IllegalArgumentException("Signing Requires Private Key.");
        } else if (forSigning || !k.a()) {
            e();
            this.b.a(forSigning, param);
        } else {
            throw new IllegalArgumentException("Verification Requires Public Key.");
        }
    }

    public byte[] c() {
        if (this.c) {
            byte[] hash = new byte[this.a.e()];
            this.a.c(hash, 0);
            return this.b.b(hash);
        }
        throw new IllegalStateException("DigestingMessageSigner not initialised for signature generation.");
    }

    public void d(byte b2) {
        this.a.d(b2);
    }

    public void update(byte[] in, int off, int len) {
        this.a.update(in, off, len);
    }

    public void e() {
        this.a.reset();
    }

    public boolean b(byte[] signature) {
        if (!this.c) {
            byte[] hash = new byte[this.a.e()];
            this.a.c(hash, 0);
            return this.b.c(hash, signature);
        }
        throw new IllegalStateException("DigestingMessageSigner not initialised for verification");
    }
}
