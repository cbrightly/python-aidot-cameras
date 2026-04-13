package org.spongycastle.jcajce.provider.asymmetric.rsa;

import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.ISO9796d1Encoding;
import org.spongycastle.crypto.encodings.OAEPEncoding;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.spongycastle.jcajce.provider.util.BadBlockException;
import org.spongycastle.jcajce.provider.util.DigestFactory;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.util.Strings;

public class CipherSpi extends BaseCipherSpi {
    private final JcaJceHelper e = new BCJcaJceHelper();
    private AsymmetricBlockCipher f;
    private AlgorithmParameterSpec g;
    private AlgorithmParameters h;
    private boolean i = false;
    private boolean j = false;
    private ByteArrayOutputStream k = new ByteArrayOutputStream();

    public CipherSpi(AsymmetricBlockCipher engine) {
        this.f = engine;
    }

    public CipherSpi(OAEPParameterSpec pSpec) {
        try {
            c(pSpec);
        } catch (NoSuchPaddingException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }

    public CipherSpi(boolean publicKeyOnly, boolean privateKeyOnly, AsymmetricBlockCipher engine) {
        this.i = publicKeyOnly;
        this.j = privateKeyOnly;
        this.f = engine;
    }

    private void c(OAEPParameterSpec pSpec) {
        MGF1ParameterSpec mgfParams = (MGF1ParameterSpec) pSpec.getMGFParameters();
        Digest digest = DigestFactory.a(mgfParams.getDigestAlgorithm());
        if (digest != null) {
            this.f = new OAEPEncoding(new RSABlindedEngine(), digest, ((PSource.PSpecified) pSpec.getPSource()).getValue());
            this.g = pSpec;
            return;
        }
        throw new NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + mgfParams.getDigestAlgorithm());
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        try {
            return this.f.c();
        } catch (NullPointerException e2) {
            throw new IllegalStateException("RSA Cipher not initialised");
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        if (key instanceof RSAPrivateKey) {
            return ((RSAPrivateKey) key).getModulus().bitLength();
        }
        if (key instanceof RSAPublicKey) {
            return ((RSAPublicKey) key).getModulus().bitLength();
        }
        throw new IllegalArgumentException("not an RSA key!");
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int inputLen) {
        try {
            return this.f.b();
        } catch (NullPointerException e2) {
            throw new IllegalStateException("RSA Cipher not initialised");
        }
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.h == null && this.g != null) {
            try {
                AlgorithmParameters g2 = this.e.g("OAEP");
                this.h = g2;
                g2.init(this.g);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.h;
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String mode) {
        String md = Strings.l(mode);
        if (!md.equals("NONE") && !md.equals("ECB")) {
            if (md.equals("1")) {
                this.j = true;
                this.i = false;
            } else if (md.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                this.j = false;
                this.i = true;
            } else {
                throw new NoSuchAlgorithmException("can't support mode " + mode);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String padding) {
        String pad = Strings.l(padding);
        if (pad.equals("NOPADDING")) {
            this.f = new RSABlindedEngine();
        } else if (pad.equals("PKCS1PADDING")) {
            this.f = new PKCS1Encoding(new RSABlindedEngine());
        } else if (pad.equals("ISO9796-1PADDING")) {
            this.f = new ISO9796d1Encoding(new RSABlindedEngine());
        } else if (pad.equals("OAEPWITHMD5ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPPADDING")) {
            c(OAEPParameterSpec.DEFAULT);
        } else if (pad.equals("OAEPWITHSHA1ANDMGF1PADDING") || pad.equals("OAEPWITHSHA-1ANDMGF1PADDING")) {
            c(OAEPParameterSpec.DEFAULT);
        } else if (pad.equals("OAEPWITHSHA224ANDMGF1PADDING") || pad.equals("OAEPWITHSHA-224ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA256ANDMGF1PADDING") || pad.equals("OAEPWITHSHA-256ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA384ANDMGF1PADDING") || pad.equals("OAEPWITHSHA-384ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA512ANDMGF1PADDING") || pad.equals("OAEPWITHSHA-512ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA3-224ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA3-224", "MGF1", new MGF1ParameterSpec("SHA3-224"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA3-256ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA3-256", "MGF1", new MGF1ParameterSpec("SHA3-256"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA3-384ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA3-384", "MGF1", new MGF1ParameterSpec("SHA3-384"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA3-512ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA3-512", "MGF1", new MGF1ParameterSpec("SHA3-512"), PSource.PSpecified.DEFAULT));
        } else {
            throw new NoSuchPaddingException(padding + " unavailable with RSA.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random) {
        CipherParameters param;
        if (params == null || (params instanceof OAEPParameterSpec)) {
            if (key instanceof RSAPublicKey) {
                if (!this.j || opmode != 1) {
                    param = RSAUtil.c((RSAPublicKey) key);
                } else {
                    throw new InvalidKeyException("mode 1 requires RSAPrivateKey");
                }
            } else if (!(key instanceof RSAPrivateKey)) {
                throw new InvalidKeyException("unknown key type passed to RSA");
            } else if (!this.i || opmode != 1) {
                param = RSAUtil.b((RSAPrivateKey) key);
            } else {
                throw new InvalidKeyException("mode 2 requires RSAPublicKey");
            }
            if (params != null) {
                OAEPParameterSpec spec = (OAEPParameterSpec) params;
                this.g = params;
                if (!spec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !spec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.R.s())) {
                    throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
                } else if (spec.getMGFParameters() instanceof MGF1ParameterSpec) {
                    Digest digest = DigestFactory.a(spec.getDigestAlgorithm());
                    if (digest != null) {
                        MGF1ParameterSpec mgfParams = (MGF1ParameterSpec) spec.getMGFParameters();
                        Digest mgfDigest = DigestFactory.a(mgfParams.getDigestAlgorithm());
                        if (mgfDigest != null) {
                            this.f = new OAEPEncoding(new RSABlindedEngine(), digest, mgfDigest, ((PSource.PSpecified) spec.getPSource()).getValue());
                        } else {
                            throw new InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + mgfParams.getDigestAlgorithm());
                        }
                    } else {
                        throw new InvalidAlgorithmParameterException("no match on digest algorithm: " + spec.getDigestAlgorithm());
                    }
                } else {
                    throw new InvalidAlgorithmParameterException("unkown MGF parameters");
                }
            }
            if (!(this.f instanceof RSABlindedEngine)) {
                if (random != null) {
                    param = new ParametersWithRandom(param, random);
                } else {
                    param = new ParametersWithRandom(param, new SecureRandom());
                }
            }
            this.k.reset();
            switch (opmode) {
                case 1:
                case 3:
                    this.f.a(true, param);
                    return;
                case 2:
                case 4:
                    this.f.a(false, param);
                    return;
                default:
                    throw new InvalidParameterException("unknown opmode " + opmode + " passed to RSA");
            }
        } else {
            throw new InvalidAlgorithmParameterException("unknown parameter type: " + params.getClass().getName());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) {
        AlgorithmParameterSpec paramSpec = null;
        if (params != null) {
            try {
                paramSpec = params.getParameterSpec(OAEPParameterSpec.class);
            } catch (InvalidParameterSpecException e2) {
                throw new InvalidAlgorithmParameterException("cannot recognise parameters: " + e2.toString(), e2);
            }
        }
        this.h = params;
        engineInit(opmode, key, paramSpec, random);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, SecureRandom random) {
        try {
            engineInit(opmode, key, (AlgorithmParameterSpec) null, random);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException("Eeeek! " + e2.toString(), e2);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        this.k.write(input, inputOffset, inputLen);
        if (this.f instanceof RSABlindedEngine) {
            if (this.k.size() <= this.f.c() + 1) {
                return null;
            }
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        } else if (this.k.size() <= this.f.c()) {
            return null;
        } else {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        this.k.write(input, inputOffset, inputLen);
        if (this.f instanceof RSABlindedEngine) {
            if (this.k.size() <= this.f.c() + 1) {
                return 0;
            }
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        } else if (this.k.size() <= this.f.c()) {
            return 0;
        } else {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
        if (input != null) {
            this.k.write(input, inputOffset, inputLen);
        }
        if (this.f instanceof RSABlindedEngine) {
            if (this.k.size() > this.f.c() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.k.size() > this.f.c()) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        return b();
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        if (input != null) {
            this.k.write(input, inputOffset, inputLen);
        }
        if (this.f instanceof RSABlindedEngine) {
            if (this.k.size() > this.f.c() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.k.size() > this.f.c()) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        byte[] out = b();
        for (int i2 = 0; i2 != out.length; i2++) {
            output[outputOffset + i2] = out[i2];
        }
        return out.length;
    }

    private byte[] b() {
        try {
            byte[] bytes = this.k.toByteArray();
            byte[] d = this.f.d(bytes, 0, bytes.length);
            this.k.reset();
            return d;
        } catch (InvalidCipherTextException e2) {
            throw new BadBlockException("unable to decrypt block", e2);
        } catch (ArrayIndexOutOfBoundsException e3) {
            throw new BadBlockException("unable to decrypt block", e3);
        } catch (Throwable th) {
            this.k.reset();
            throw th;
        }
    }

    public static class NoPadding extends CipherSpi {
        public NoPadding() {
            super((AsymmetricBlockCipher) new RSABlindedEngine());
        }
    }

    public static class PKCS1v1_5Padding extends CipherSpi {
        public PKCS1v1_5Padding() {
            super((AsymmetricBlockCipher) new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class PKCS1v1_5Padding_PrivateOnly extends CipherSpi {
        public PKCS1v1_5Padding_PrivateOnly() {
            super(false, true, new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class PKCS1v1_5Padding_PublicOnly extends CipherSpi {
        public PKCS1v1_5Padding_PublicOnly() {
            super(true, false, new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class OAEPPadding extends CipherSpi {
        public OAEPPadding() {
            super(OAEPParameterSpec.DEFAULT);
        }
    }

    public static class ISO9796d1Padding extends CipherSpi {
        public ISO9796d1Padding() {
            super((AsymmetricBlockCipher) new ISO9796d1Encoding(new RSABlindedEngine()));
        }
    }
}
