package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.RIPEMD256Digest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

public class DigestSignatureSpi extends SignatureSpi {
    private Digest a;
    private AsymmetricBlockCipher b;
    private AlgorithmIdentifier c;

    protected DigestSignatureSpi(Digest digest, AsymmetricBlockCipher cipher) {
        this.a = digest;
        this.b = cipher;
        this.c = null;
    }

    protected DigestSignatureSpi(ASN1ObjectIdentifier objId, Digest digest, AsymmetricBlockCipher cipher) {
        this.a = digest;
        this.b = cipher;
        this.c = new AlgorithmIdentifier(objId, DERNull.c);
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        if (publicKey instanceof RSAPublicKey) {
            CipherParameters param = RSAUtil.c((RSAPublicKey) publicKey);
            this.a.reset();
            this.b.a(false, param);
            return;
        }
        throw new InvalidKeyException("Supplied key (" + b(publicKey) + ") is not a RSAPublicKey instance");
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        if (privateKey instanceof RSAPrivateKey) {
            CipherParameters param = RSAUtil.b((RSAPrivateKey) privateKey);
            this.a.reset();
            this.b.a(true, param);
            return;
        }
        throw new InvalidKeyException("Supplied key (" + b(privateKey) + ") is not a RSAPrivateKey instance");
    }

    private String b(Object o) {
        if (o == null) {
            return null;
        }
        return o.getClass().getName();
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
            byte[] bytes = a(hash);
            return this.b.d(bytes, 0, bytes.length);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SignatureException("key too small for signature type");
        } catch (Exception e2) {
            throw new SignatureException(e2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        try {
            byte[] sig = this.b.d(sigBytes, 0, sigBytes.length);
            byte[] expected = a(hash);
            if (sig.length == expected.length) {
                return Arrays.u(sig, expected);
            }
            if (sig.length == expected.length - 2) {
                expected[1] = (byte) (expected[1] - 2);
                expected[3] = (byte) (expected[3] - 2);
                int sigOffset = expected[3] + 4;
                int expectedOffset = sigOffset + 2;
                int nonEqual = 0;
                for (int i = 0; i < expected.length - expectedOffset; i++) {
                    nonEqual |= sig[sigOffset + i] ^ expected[expectedOffset + i];
                }
                for (int i2 = 0; i2 < sigOffset; i2++) {
                    nonEqual |= sig[i2] ^ expected[i2];
                }
                if (nonEqual == 0) {
                    return true;
                }
                return false;
            }
            Arrays.u(expected, expected);
            return false;
        } catch (Exception e) {
            return false;
        }
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
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    private byte[] a(byte[] hash) {
        AlgorithmIdentifier algorithmIdentifier = this.c;
        if (algorithmIdentifier == null) {
            return hash;
        }
        return new DigestInfo(algorithmIdentifier, hash).getEncoded("DER");
    }

    public static class SHA1 extends DigestSignatureSpi {
        public SHA1() {
            super(OIWObjectIdentifiers.i, DigestFactory.b(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA224 extends DigestSignatureSpi {
        public SHA224() {
            super(NISTObjectIdentifiers.f, DigestFactory.c(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA256 extends DigestSignatureSpi {
        public SHA256() {
            super(NISTObjectIdentifiers.c, DigestFactory.d(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA384 extends DigestSignatureSpi {
        public SHA384() {
            super(NISTObjectIdentifiers.d, DigestFactory.e(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA512 extends DigestSignatureSpi {
        public SHA512() {
            super(NISTObjectIdentifiers.e, DigestFactory.j(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA512_224 extends DigestSignatureSpi {
        public SHA512_224() {
            super(NISTObjectIdentifiers.g, DigestFactory.k(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA512_256 extends DigestSignatureSpi {
        public SHA512_256() {
            super(NISTObjectIdentifiers.h, DigestFactory.l(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA3_224 extends DigestSignatureSpi {
        public SHA3_224() {
            super(NISTObjectIdentifiers.i, DigestFactory.f(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA3_256 extends DigestSignatureSpi {
        public SHA3_256() {
            super(NISTObjectIdentifiers.j, DigestFactory.g(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA3_384 extends DigestSignatureSpi {
        public SHA3_384() {
            super(NISTObjectIdentifiers.k, DigestFactory.h(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA3_512 extends DigestSignatureSpi {
        public SHA3_512() {
            super(NISTObjectIdentifiers.l, DigestFactory.i(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class MD2 extends DigestSignatureSpi {
        public MD2() {
            super(PKCSObjectIdentifiers.r0, new MD2Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class MD4 extends DigestSignatureSpi {
        public MD4() {
            super(PKCSObjectIdentifiers.s0, new MD4Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class MD5 extends DigestSignatureSpi {
        public MD5() {
            super(PKCSObjectIdentifiers.t0, DigestFactory.a(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class RIPEMD160 extends DigestSignatureSpi {
        public RIPEMD160() {
            super(TeleTrusTObjectIdentifiers.b, new RIPEMD160Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class RIPEMD128 extends DigestSignatureSpi {
        public RIPEMD128() {
            super(TeleTrusTObjectIdentifiers.c, new RIPEMD128Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class RIPEMD256 extends DigestSignatureSpi {
        public RIPEMD256() {
            super(TeleTrusTObjectIdentifiers.d, new RIPEMD256Digest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class noneRSA extends DigestSignatureSpi {
        public noneRSA() {
            super(new NullDigest(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }
}
