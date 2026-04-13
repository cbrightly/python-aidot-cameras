package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.PSSSigner;
import org.spongycastle.jcajce.provider.util.DigestFactory;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;

public class PSSSignatureSpi extends SignatureSpi {
    private final JcaJceHelper a;
    private AlgorithmParameters b;
    private PSSParameterSpec c;
    private PSSParameterSpec d;
    private AsymmetricBlockCipher e;
    private Digest f;
    private Digest g;
    private int h;
    private byte i;
    private boolean j;
    private PSSSigner k;

    private byte a(int trailerField) {
        if (trailerField == 1) {
            return -68;
        }
        throw new IllegalArgumentException("unknown trailer field");
    }

    private void b() {
        if (this.j) {
            this.f = new NullPssDigest(this.g);
        } else {
            this.f = this.g;
        }
    }

    protected PSSSignatureSpi(AsymmetricBlockCipher signer, PSSParameterSpec paramSpecArg) {
        this(signer, paramSpecArg, false);
    }

    protected PSSSignatureSpi(AsymmetricBlockCipher signer, PSSParameterSpec baseParamSpec, boolean isRaw) {
        this.a = new BCJcaJceHelper();
        this.e = signer;
        this.d = baseParamSpec;
        if (baseParamSpec == null) {
            this.c = PSSParameterSpec.DEFAULT;
        } else {
            this.c = baseParamSpec;
        }
        this.g = DigestFactory.a(this.c.getDigestAlgorithm());
        this.h = this.c.getSaltLength();
        this.i = a(this.c.getTrailerField());
        this.j = isRaw;
        b();
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        if (publicKey instanceof RSAPublicKey) {
            PSSSigner pSSSigner = new PSSSigner(this.e, this.f, this.g, this.h, this.i);
            this.k = pSSSigner;
            pSSSigner.a(false, RSAUtil.c((RSAPublicKey) publicKey));
            return;
        }
        throw new InvalidKeyException("Supplied key is not a RSAPublicKey instance");
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey, SecureRandom random) {
        if (privateKey instanceof RSAPrivateKey) {
            PSSSigner pSSSigner = new PSSSigner(this.e, this.f, this.g, this.h, this.i);
            this.k = pSSSigner;
            pSSSigner.a(true, new ParametersWithRandom(RSAUtil.b((RSAPrivateKey) privateKey), random));
            return;
        }
        throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        if (privateKey instanceof RSAPrivateKey) {
            PSSSigner pSSSigner = new PSSSigner(this.e, this.f, this.g, this.h, this.i);
            this.k = pSSSigner;
            pSSSigner.a(true, RSAUtil.b((RSAPrivateKey) privateKey));
            return;
        }
        throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b2) {
        this.k.d(b2);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b2, int off, int len) {
        this.k.update(b2, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        try {
            return this.k.c();
        } catch (CryptoException e2) {
            throw new SignatureException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        return this.k.b(sigBytes);
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(AlgorithmParameterSpec params) {
        if (params instanceof PSSParameterSpec) {
            PSSParameterSpec newParamSpec = (PSSParameterSpec) params;
            PSSParameterSpec pSSParameterSpec = this.d;
            if (pSSParameterSpec != null && !DigestFactory.c(pSSParameterSpec.getDigestAlgorithm(), newParamSpec.getDigestAlgorithm())) {
                throw new InvalidAlgorithmParameterException("parameter must be using " + this.d.getDigestAlgorithm());
            } else if (!newParamSpec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !newParamSpec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.R.s())) {
                throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
            } else if (newParamSpec.getMGFParameters() instanceof MGF1ParameterSpec) {
                MGF1ParameterSpec mgfParams = (MGF1ParameterSpec) newParamSpec.getMGFParameters();
                if (DigestFactory.c(mgfParams.getDigestAlgorithm(), newParamSpec.getDigestAlgorithm())) {
                    Digest newDigest = DigestFactory.a(mgfParams.getDigestAlgorithm());
                    if (newDigest != null) {
                        this.b = null;
                        this.c = newParamSpec;
                        this.g = newDigest;
                        this.h = newParamSpec.getSaltLength();
                        this.i = a(this.c.getTrailerField());
                        b();
                        return;
                    }
                    throw new InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + mgfParams.getDigestAlgorithm());
                }
                throw new InvalidAlgorithmParameterException("digest algorithm for MGF should be the same as for PSS parameters.");
            } else {
                throw new InvalidAlgorithmParameterException("unknown MGF parameters");
            }
        } else {
            throw new InvalidAlgorithmParameterException("Only PSSParameterSpec supported");
        }
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.b == null && this.c != null) {
            try {
                AlgorithmParameters g2 = this.a.g("PSS");
                this.b = g2;
                g2.init(this.c);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(String param, Object value) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public Object engineGetParameter(String param) {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    public static class nonePSS extends PSSSignatureSpi {
        public nonePSS() {
            super(new RSABlindedEngine(), (PSSParameterSpec) null, true);
        }
    }

    public static class PSSwithRSA extends PSSSignatureSpi {
        public PSSwithRSA() {
            super(new RSABlindedEngine(), (PSSParameterSpec) null);
        }
    }

    public static class SHA1withRSA extends PSSSignatureSpi {
        public SHA1withRSA() {
            super(new RSABlindedEngine(), PSSParameterSpec.DEFAULT);
        }
    }

    public static class SHA224withRSA extends PSSSignatureSpi {
        public SHA224withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), 28, 1));
        }
    }

    public static class SHA256withRSA extends PSSSignatureSpi {
        public SHA256withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1));
        }
    }

    public static class SHA384withRSA extends PSSSignatureSpi {
        public SHA384withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-384", "MGF1", new MGF1ParameterSpec("SHA-384"), 48, 1));
        }
    }

    public static class SHA512withRSA extends PSSSignatureSpi {
        public SHA512withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-512"), 64, 1));
        }
    }

    public static class SHA512_224withRSA extends PSSSignatureSpi {
        public SHA512_224withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512(224)", "MGF1", new MGF1ParameterSpec("SHA-512(224)"), 28, 1));
        }
    }

    public static class SHA512_256withRSA extends PSSSignatureSpi {
        public SHA512_256withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512(256)", "MGF1", new MGF1ParameterSpec("SHA-512(256)"), 32, 1));
        }
    }

    public static class SHA3_224withRSA extends PSSSignatureSpi {
        public SHA3_224withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-224", "MGF1", new MGF1ParameterSpec("SHA3-224"), 28, 1));
        }
    }

    public static class SHA3_256withRSA extends PSSSignatureSpi {
        public SHA3_256withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-256", "MGF1", new MGF1ParameterSpec("SHA3-256"), 32, 1));
        }
    }

    public static class SHA3_384withRSA extends PSSSignatureSpi {
        public SHA3_384withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-384", "MGF1", new MGF1ParameterSpec("SHA3-384"), 48, 1));
        }
    }

    public static class SHA3_512withRSA extends PSSSignatureSpi {
        public SHA3_512withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-512", "MGF1", new MGF1ParameterSpec("SHA3-512"), 64, 1));
        }
    }

    public class NullPssDigest implements Digest {
        private ByteArrayOutputStream a = new ByteArrayOutputStream();
        private Digest b;
        private boolean c = true;

        public NullPssDigest(Digest mgfDigest) {
            this.b = mgfDigest;
        }

        public String b() {
            return "NULL";
        }

        public int e() {
            return this.b.e();
        }

        public void d(byte in) {
            this.a.write(in);
        }

        public void update(byte[] in, int inOff, int len) {
            this.a.write(in, inOff, len);
        }

        public int c(byte[] out, int outOff) {
            byte[] res = this.a.toByteArray();
            if (this.c) {
                System.arraycopy(res, 0, out, outOff, res.length);
            } else {
                this.b.update(res, 0, res.length);
                this.b.c(out, outOff);
            }
            reset();
            this.c = !this.c;
            return res.length;
        }

        public void reset() {
            this.a.reset();
            this.b.reset();
        }
    }
}
