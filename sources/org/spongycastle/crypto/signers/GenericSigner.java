package org.spongycastle.crypto.signers;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class GenericSigner implements Signer {
    private final AsymmetricBlockCipher a;
    private final Digest b;
    private boolean c;

    public GenericSigner(AsymmetricBlockCipher engine, Digest digest) {
        this.a = engine;
        this.b = digest;
    }

    public void a(boolean forSigning, CipherParameters parameters) {
        AsymmetricKeyParameter k;
        this.c = forSigning;
        if (parameters instanceof ParametersWithRandom) {
            k = (AsymmetricKeyParameter) ((ParametersWithRandom) parameters).a();
        } else {
            k = (AsymmetricKeyParameter) parameters;
        }
        if (forSigning && !k.a()) {
            throw new IllegalArgumentException("signing requires private key");
        } else if (forSigning || !k.a()) {
            e();
            this.a.a(forSigning, parameters);
        } else {
            throw new IllegalArgumentException("verification requires public key");
        }
    }

    public void d(byte input) {
        this.b.d(input);
    }

    public void update(byte[] input, int inOff, int length) {
        this.b.update(input, inOff, length);
    }

    public byte[] c() {
        if (this.c) {
            byte[] hash = new byte[this.b.e()];
            this.b.c(hash, 0);
            return this.a.d(hash, 0, hash.length);
        }
        throw new IllegalStateException("GenericSigner not initialised for signature generation.");
    }

    public boolean b(byte[] signature) {
        if (!this.c) {
            byte[] hash = new byte[this.b.e()];
            this.b.c(hash, 0);
            try {
                byte[] sig = this.a.d(signature, 0, signature.length);
                if (sig.length < hash.length) {
                    byte[] tmp = new byte[hash.length];
                    System.arraycopy(sig, 0, tmp, tmp.length - sig.length, sig.length);
                    sig = tmp;
                }
                return Arrays.u(sig, hash);
            } catch (Exception e) {
                return false;
            }
        } else {
            throw new IllegalStateException("GenericSigner not initialised for verification");
        }
    }

    public void e() {
        this.b.reset();
    }
}
