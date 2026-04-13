package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.BufferedAsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.ISO9796d1Encoding;
import org.spongycastle.crypto.encodings.OAEPEncoding;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.ElGamalEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.spongycastle.jcajce.provider.util.BadBlockException;
import org.spongycastle.jcajce.provider.util.DigestFactory;
import org.spongycastle.jce.interfaces.ElGamalKey;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;
import org.spongycastle.util.Strings;

public class CipherSpi extends BaseCipherSpi {
    private BufferedAsymmetricBlockCipher e;
    private AlgorithmParameterSpec f;
    private AlgorithmParameters g;

    public CipherSpi(AsymmetricBlockCipher engine) {
        this.e = new BufferedAsymmetricBlockCipher(engine);
    }

    private void c(OAEPParameterSpec pSpec) {
        MGF1ParameterSpec mgfParams = (MGF1ParameterSpec) pSpec.getMGFParameters();
        Digest digest = DigestFactory.a(mgfParams.getDigestAlgorithm());
        if (digest != null) {
            this.e = new BufferedAsymmetricBlockCipher(new OAEPEncoding(new ElGamalEngine(), digest, ((PSource.PSpecified) pSpec.getPSource()).getValue()));
            this.f = pSpec;
            return;
        }
        throw new NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + mgfParams.getDigestAlgorithm());
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return this.e.b();
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        if (key instanceof ElGamalKey) {
            return ((ElGamalKey) key).getParameters().b().bitLength();
        }
        if (key instanceof DHKey) {
            return ((DHKey) key).getParams().getP().bitLength();
        }
        throw new IllegalArgumentException("not an ElGamal key!");
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int inputLen) {
        return this.e.c();
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.g == null && this.f != null) {
            try {
                AlgorithmParameters a = a("OAEP");
                this.g = a;
                a.init(this.f);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.g;
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String mode) {
        String md = Strings.l(mode);
        if (!md.equals("NONE") && !md.equals("ECB")) {
            throw new NoSuchAlgorithmException("can't support mode " + mode);
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String padding) {
        String pad = Strings.l(padding);
        if (pad.equals("NOPADDING")) {
            this.e = new BufferedAsymmetricBlockCipher(new ElGamalEngine());
        } else if (pad.equals("PKCS1PADDING")) {
            this.e = new BufferedAsymmetricBlockCipher(new PKCS1Encoding(new ElGamalEngine()));
        } else if (pad.equals("ISO9796-1PADDING")) {
            this.e = new BufferedAsymmetricBlockCipher(new ISO9796d1Encoding(new ElGamalEngine()));
        } else if (pad.equals("OAEPPADDING")) {
            c(OAEPParameterSpec.DEFAULT);
        } else if (pad.equals("OAEPWITHMD5ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA1ANDMGF1PADDING")) {
            c(OAEPParameterSpec.DEFAULT);
        } else if (pad.equals("OAEPWITHSHA224ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA256ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA384ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSource.PSpecified.DEFAULT));
        } else if (pad.equals("OAEPWITHSHA512ANDMGF1PADDING")) {
            c(new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSource.PSpecified.DEFAULT));
        } else {
            throw new NoSuchPaddingException(padding + " unavailable with ElGamal.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random) {
        CipherParameters param;
        if (params == null) {
            if (key instanceof ElGamalPublicKey) {
                param = ElGamalUtil.b((PublicKey) key);
            } else if (key instanceof ElGamalPrivateKey) {
                param = ElGamalUtil.a((PrivateKey) key);
            } else {
                throw new InvalidKeyException("unknown key type passed to ElGamal");
            }
            if (random != null) {
                param = new ParametersWithRandom(param, random);
            }
            switch (opmode) {
                case 1:
                case 3:
                    this.e.d(true, param);
                    return;
                case 2:
                case 4:
                    this.e.d(false, param);
                    return;
                default:
                    throw new InvalidParameterException("unknown opmode " + opmode + " passed to ElGamal");
            }
        } else {
            throw new IllegalArgumentException("unknown parameter type.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) {
        throw new InvalidAlgorithmParameterException("can't handle parameters in ElGamal");
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, SecureRandom random) {
        engineInit(opmode, key, (AlgorithmParameterSpec) null, random);
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        this.e.e(input, inputOffset, inputLen);
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        this.e.e(input, inputOffset, inputLen);
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
        this.e.e(input, inputOffset, inputLen);
        return b();
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        this.e.e(input, inputOffset, inputLen);
        byte[] out = b();
        for (int i = 0; i != out.length; i++) {
            output[outputOffset + i] = out[i];
        }
        return out.length;
    }

    private byte[] b() {
        try {
            return this.e.a();
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException("unable to decrypt block") {
                public synchronized Throwable getCause() {
                    return e2;
                }
            };
        } catch (ArrayIndexOutOfBoundsException e3) {
            throw new BadBlockException("unable to decrypt block", e3);
        }
    }

    public static class NoPadding extends CipherSpi {
        public NoPadding() {
            super(new ElGamalEngine());
        }
    }

    public static class PKCS1v1_5Padding extends CipherSpi {
        public PKCS1v1_5Padding() {
            super(new PKCS1Encoding(new ElGamalEngine()));
        }
    }
}
