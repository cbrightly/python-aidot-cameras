package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.rainbow.RainbowSigner;

public class SignatureSpi extends java.security.SignatureSpi {
    private Digest a;
    private RainbowSigner b;
    private SecureRandom c;

    protected SignatureSpi(Digest digest, RainbowSigner signer) {
        this.a = digest;
        this.b = signer;
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        CipherParameters param = RainbowKeysToParams.generatePublicKeyParameter(publicKey);
        this.a.reset();
        this.b.a(false, param);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey, SecureRandom random) {
        this.c = random;
        engineInitSign(privateKey);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        CipherParameters param = RainbowKeysToParams.generatePrivateKeyParameter(privateKey);
        SecureRandom secureRandom = this.c;
        if (secureRandom != null) {
            param = new ParametersWithRandom(param, secureRandom);
        }
        this.a.reset();
        this.b.a(true, param);
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

    public static class withSha224 extends SignatureSpi {
        public withSha224() {
            super(new SHA224Digest(), new RainbowSigner());
        }
    }

    public static class withSha256 extends SignatureSpi {
        public withSha256() {
            super(new SHA256Digest(), new RainbowSigner());
        }
    }

    public static class withSha384 extends SignatureSpi {
        public withSha384() {
            super(new SHA384Digest(), new RainbowSigner());
        }
    }

    public static class withSha512 extends SignatureSpi {
        public withSha512() {
            super(new SHA512Digest(), new RainbowSigner());
        }
    }
}
