package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHA512tDigest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.sphincs.SPHINCS256Signer;

public class SignatureSpi extends java.security.SignatureSpi {
    private Digest a;
    private SPHINCS256Signer b;
    private SecureRandom c;

    protected SignatureSpi(Digest digest, SPHINCS256Signer signer) {
        this.a = digest;
        this.b = signer;
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        if (publicKey instanceof BCSphincs256PublicKey) {
            CipherParameters param = ((BCSphincs256PublicKey) publicKey).getKeyParams();
            this.a.reset();
            this.b.a(false, param);
            return;
        }
        throw new InvalidKeyException("unknown public key passed to SPHINCS-256");
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey, SecureRandom random) {
        this.c = random;
        engineInitSign(privateKey);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        if (privateKey instanceof BCSphincs256PrivateKey) {
            CipherParameters param = ((BCSphincs256PrivateKey) privateKey).getKeyParams();
            SecureRandom secureRandom = this.c;
            if (secureRandom != null) {
                param = new ParametersWithRandom(param, secureRandom);
            }
            this.a.reset();
            this.b.a(true, param);
            return;
        }
        throw new InvalidKeyException("unknown private key passed to SPHINCS-256");
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b2) {
        this.a.d(b2);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b2, int off, int len) {
        this.a.update(b2, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        try {
            return this.b.b(hash);
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        return this.b.c(hash, sigBytes);
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(AlgorithmParameterSpec params) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(String param, Object value) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public Object engineGetParameter(String param) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    public static class withSha512 extends SignatureSpi {
        public withSha512() {
            super(new SHA512Digest(), new SPHINCS256Signer(new SHA512tDigest(256), new SHA512Digest()));
        }
    }

    public static class withSha3_512 extends SignatureSpi {
        public withSha3_512() {
            super(new SHA3Digest(512), new SPHINCS256Signer(new SHA3Digest(256), new SHA3Digest(512)));
        }
    }
}
