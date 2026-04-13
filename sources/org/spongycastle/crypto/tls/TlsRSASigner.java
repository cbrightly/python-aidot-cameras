package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.signers.GenericSigner;
import org.spongycastle.crypto.signers.RSADigestSigner;

public class TlsRSASigner extends AbstractTlsSigner {
    public byte[] f(SignatureAndHashAlgorithm algorithm, AsymmetricKeyParameter privateKey, byte[] hash) {
        Signer signer = h(algorithm, true, true, new ParametersWithRandom(privateKey, this.a.d()));
        signer.update(hash, 0, hash.length);
        return signer.c();
    }

    public boolean b(SignatureAndHashAlgorithm algorithm, byte[] sigBytes, AsymmetricKeyParameter publicKey, byte[] hash) {
        Signer signer = h(algorithm, true, false, publicKey);
        signer.update(hash, 0, hash.length);
        return signer.b(sigBytes);
    }

    public Signer d(SignatureAndHashAlgorithm algorithm, AsymmetricKeyParameter publicKey) {
        return h(algorithm, false, false, publicKey);
    }

    public boolean e(AsymmetricKeyParameter publicKey) {
        return (publicKey instanceof RSAKeyParameters) && !publicKey.a();
    }

    /* access modifiers changed from: protected */
    public Signer h(SignatureAndHashAlgorithm algorithm, boolean raw, boolean forSigning, CipherParameters cp) {
        Digest d;
        Signer s;
        if ((algorithm != null) != TlsUtils.U(this.a)) {
            throw new IllegalStateException();
        } else if (algorithm == null || algorithm.c() == 1) {
            if (raw) {
                d = new NullDigest();
            } else if (algorithm == null) {
                d = new CombinedHash();
            } else {
                d = TlsUtils.o(algorithm.b());
            }
            if (algorithm != null) {
                s = new RSADigestSigner(d, TlsUtils.H(algorithm.b()));
            } else {
                s = new GenericSigner(g(), d);
            }
            s.a(forSigning, cp);
            return s;
        } else {
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: protected */
    public AsymmetricBlockCipher g() {
        return new PKCS1Encoding(new RSABlindedEngine());
    }
}
