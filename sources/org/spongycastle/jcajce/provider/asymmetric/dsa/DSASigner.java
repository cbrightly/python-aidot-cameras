package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;

public class DSASigner extends SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest c;
    private DSA d;
    private SecureRandom f;

    protected DSASigner(Digest digest, DSA signer) {
        this.c = digest;
        this.d = signer;
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        CipherParameters param = DSAUtil.c(publicKey);
        this.c.reset();
        this.d.a(false, param);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey, SecureRandom random) {
        this.f = random;
        engineInitSign(privateKey);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        CipherParameters param = DSAUtil.b(privateKey);
        SecureRandom secureRandom = this.f;
        if (secureRandom != null) {
            param = new ParametersWithRandom(param, secureRandom);
        }
        this.c.reset();
        this.d.a(true, param);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b) {
        this.c.d(b);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b, int off, int len) {
        this.c.update(b, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        byte[] hash = new byte[this.c.e()];
        this.c.c(hash, 0);
        try {
            BigInteger[] sig = this.d.b(hash);
            return b(sig[0], sig[1]);
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        byte[] hash = new byte[this.c.e()];
        this.c.c(hash, 0);
        try {
            BigInteger[] sig = a(sigBytes);
            return this.d.c(hash, sig[0], sig[1]);
        } catch (Exception e) {
            throw new SignatureException("error decoding signature bytes.");
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
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    private byte[] b(BigInteger r, BigInteger s) {
        return new DERSequence((ASN1Encodable[]) new ASN1Integer[]{new ASN1Integer(r), new ASN1Integer(s)}).getEncoded("DER");
    }

    private BigInteger[] a(byte[] encoding) {
        ASN1Sequence s = (ASN1Sequence) ASN1Primitive.h(encoding);
        if (s.size() != 2) {
            throw new IOException("malformed signature");
        } else if (Arrays.b(encoding, s.getEncoded("DER"))) {
            return new BigInteger[]{((ASN1Integer) s.r(0)).r(), ((ASN1Integer) s.r(1)).r()};
        } else {
            throw new IOException("malformed signature");
        }
    }

    public static class stdDSA extends DSASigner {
        public stdDSA() {
            super(DigestFactory.b(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSA extends DSASigner {
        public detDSA() {
            super(DigestFactory.b(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.b())));
        }
    }

    public static class dsa224 extends DSASigner {
        public dsa224() {
            super(DigestFactory.c(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSA224 extends DSASigner {
        public detDSA224() {
            super(DigestFactory.c(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.c())));
        }
    }

    public static class dsa256 extends DSASigner {
        public dsa256() {
            super(DigestFactory.d(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSA256 extends DSASigner {
        public detDSA256() {
            super(DigestFactory.d(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.d())));
        }
    }

    public static class dsa384 extends DSASigner {
        public dsa384() {
            super(DigestFactory.e(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSA384 extends DSASigner {
        public detDSA384() {
            super(DigestFactory.e(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.e())));
        }
    }

    public static class dsa512 extends DSASigner {
        public dsa512() {
            super(DigestFactory.j(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSA512 extends DSASigner {
        public detDSA512() {
            super(DigestFactory.j(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.j())));
        }
    }

    public static class dsaSha3_224 extends DSASigner {
        public dsaSha3_224() {
            super(DigestFactory.f(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSASha3_224 extends DSASigner {
        public detDSASha3_224() {
            super(DigestFactory.f(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.f())));
        }
    }

    public static class dsaSha3_256 extends DSASigner {
        public dsaSha3_256() {
            super(DigestFactory.g(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSASha3_256 extends DSASigner {
        public detDSASha3_256() {
            super(DigestFactory.g(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.g())));
        }
    }

    public static class dsaSha3_384 extends DSASigner {
        public dsaSha3_384() {
            super(DigestFactory.h(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSASha3_384 extends DSASigner {
        public detDSASha3_384() {
            super(DigestFactory.h(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.h())));
        }
    }

    public static class dsaSha3_512 extends DSASigner {
        public dsaSha3_512() {
            super(DigestFactory.i(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }

    public static class detDSASha3_512 extends DSASigner {
        public detDSASha3_512() {
            super(DigestFactory.i(), new org.spongycastle.crypto.signers.DSASigner(new HMacDSAKCalculator(DigestFactory.i())));
        }
    }

    public static class noneDSA extends DSASigner {
        public noneDSA() {
            super(new NullDigest(), new org.spongycastle.crypto.signers.DSASigner());
        }
    }
}
