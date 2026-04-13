package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.WhirlpoolDigest;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.signers.ISO9796d2Signer;
import org.spongycastle.crypto.util.DigestFactory;

public class ISOSignatureSpi extends SignatureSpi {
    private ISO9796d2Signer a;

    protected ISOSignatureSpi(Digest digest, AsymmetricBlockCipher cipher) {
        this.a = new ISO9796d2Signer(cipher, digest, true);
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        this.a.a(false, RSAUtil.c((RSAPublicKey) publicKey));
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        this.a.a(true, RSAUtil.b((RSAPrivateKey) privateKey));
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b) {
        this.a.d(b);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b, int off, int len) {
        this.a.update(b, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        try {
            return this.a.c();
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        return this.a.b(sigBytes);
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

    public static class SHA1WithRSAEncryption extends ISOSignatureSpi {
        public SHA1WithRSAEncryption() {
            super(DigestFactory.b(), new RSABlindedEngine());
        }
    }

    public static class MD5WithRSAEncryption extends ISOSignatureSpi {
        public MD5WithRSAEncryption() {
            super(DigestFactory.a(), new RSABlindedEngine());
        }
    }

    public static class RIPEMD160WithRSAEncryption extends ISOSignatureSpi {
        public RIPEMD160WithRSAEncryption() {
            super(new RIPEMD160Digest(), new RSABlindedEngine());
        }
    }

    public static class SHA224WithRSAEncryption extends ISOSignatureSpi {
        public SHA224WithRSAEncryption() {
            super(DigestFactory.c(), new RSABlindedEngine());
        }
    }

    public static class SHA256WithRSAEncryption extends ISOSignatureSpi {
        public SHA256WithRSAEncryption() {
            super(DigestFactory.d(), new RSABlindedEngine());
        }
    }

    public static class SHA384WithRSAEncryption extends ISOSignatureSpi {
        public SHA384WithRSAEncryption() {
            super(DigestFactory.e(), new RSABlindedEngine());
        }
    }

    public static class SHA512WithRSAEncryption extends ISOSignatureSpi {
        public SHA512WithRSAEncryption() {
            super(DigestFactory.j(), new RSABlindedEngine());
        }
    }

    public static class SHA512_224WithRSAEncryption extends ISOSignatureSpi {
        public SHA512_224WithRSAEncryption() {
            super(DigestFactory.k(), new RSABlindedEngine());
        }
    }

    public static class SHA512_256WithRSAEncryption extends ISOSignatureSpi {
        public SHA512_256WithRSAEncryption() {
            super(DigestFactory.l(), new RSABlindedEngine());
        }
    }

    public static class WhirlpoolWithRSAEncryption extends ISOSignatureSpi {
        public WhirlpoolWithRSAEncryption() {
            super(new WhirlpoolDigest(), new RSABlindedEngine());
        }
    }
}
