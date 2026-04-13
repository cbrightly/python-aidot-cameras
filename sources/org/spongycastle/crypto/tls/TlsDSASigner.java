package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.DSADigestSigner;

public abstract class TlsDSASigner extends AbstractTlsSigner {
    /* access modifiers changed from: protected */
    public abstract DSA g(short s);

    /* access modifiers changed from: protected */
    public abstract short h();

    public byte[] f(SignatureAndHashAlgorithm algorithm, AsymmetricKeyParameter privateKey, byte[] hash) {
        Signer signer = j(algorithm, true, true, new ParametersWithRandom(privateKey, this.a.d()));
        if (algorithm == null) {
            signer.update(hash, 16, 20);
        } else {
            signer.update(hash, 0, hash.length);
        }
        return signer.c();
    }

    public boolean b(SignatureAndHashAlgorithm algorithm, byte[] sigBytes, AsymmetricKeyParameter publicKey, byte[] hash) {
        Signer signer = j(algorithm, true, false, publicKey);
        if (algorithm == null) {
            signer.update(hash, 16, 20);
        } else {
            signer.update(hash, 0, hash.length);
        }
        return signer.b(sigBytes);
    }

    public Signer d(SignatureAndHashAlgorithm algorithm, AsymmetricKeyParameter publicKey) {
        return j(algorithm, false, false, publicKey);
    }

    /* access modifiers changed from: protected */
    public CipherParameters i(boolean forSigning, CipherParameters cp) {
        return cp;
    }

    /* access modifiers changed from: protected */
    public Signer j(SignatureAndHashAlgorithm algorithm, boolean raw, boolean forSigning, CipherParameters cp) {
        if ((algorithm != null) != TlsUtils.U(this.a)) {
            throw new IllegalStateException();
        } else if (algorithm == null || algorithm.c() == h()) {
            short hashAlgorithm = algorithm == null ? 2 : algorithm.b();
            Signer s = new DSADigestSigner(g(hashAlgorithm), raw ? new NullDigest() : TlsUtils.o(hashAlgorithm));
            s.a(forSigning, i(forSigning, cp));
            return s;
        } else {
            throw new IllegalStateException();
        }
    }
}
