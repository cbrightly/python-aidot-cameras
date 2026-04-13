package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.spongycastle.jcajce.spec.GOST28147WrapParameterSpec;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Arrays;

public abstract class BaseWrapCipher extends CipherSpi implements PBE {
    private Class[] c;
    protected int d;
    protected int f;
    private final JcaJceHelper p0;
    protected AlgorithmParameters q;
    protected Wrapper x;
    private int y;
    private byte[] z;

    protected BaseWrapCipher() {
        this.c = new Class[]{GOST28147WrapParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class};
        this.d = 2;
        this.f = 1;
        this.q = null;
        this.x = null;
        this.p0 = new BCJcaJceHelper();
    }

    protected BaseWrapCipher(Wrapper wrapEngine) {
        this(wrapEngine, 0);
    }

    protected BaseWrapCipher(Wrapper wrapEngine, int ivSize) {
        this.c = new Class[]{GOST28147WrapParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class};
        this.d = 2;
        this.f = 1;
        this.q = null;
        this.x = null;
        this.p0 = new BCJcaJceHelper();
        this.x = wrapEngine;
        this.y = ivSize;
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        return Arrays.h(this.z);
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int inputLen) {
        return -1;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    /* access modifiers changed from: protected */
    public final AlgorithmParameters a(String algorithm) {
        return this.p0.g(algorithm);
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String mode) {
        throw new NoSuchAlgorithmException("can't support mode " + mode);
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String padding) {
        throw new NoSuchPaddingException("Padding " + padding + " unknown.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random) {
        CipherParameters param;
        int i;
        if (key instanceof BCPBEKey) {
            BCPBEKey k = (BCPBEKey) key;
            if (params instanceof PBEParameterSpec) {
                param = PBE.Util.g(k, params, this.x.b());
            } else if (k.getParam() != null) {
                param = k.getParam();
            } else {
                throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
        } else {
            param = new KeyParameter(key.getEncoded());
        }
        if (params instanceof IvParameterSpec) {
            param = new ParametersWithIV(param, ((IvParameterSpec) params).getIV());
        }
        if (params instanceof GOST28147WrapParameterSpec) {
            GOST28147WrapParameterSpec spec = (GOST28147WrapParameterSpec) params;
            byte[] sBox = spec.a();
            if (sBox != null) {
                param = new ParametersWithSBox(param, sBox);
            }
            param = new ParametersWithUKM(param, spec.b());
        }
        if ((param instanceof KeyParameter) && (i = this.y) != 0) {
            byte[] bArr = new byte[i];
            this.z = bArr;
            random.nextBytes(bArr);
            param = new ParametersWithIV(param, this.z);
        }
        if (random != null) {
            param = new ParametersWithRandom(param, random);
        }
        switch (opmode) {
            case 1:
            case 2:
                throw new IllegalArgumentException("engine only valid for wrapping");
            case 3:
                this.x.a(true, param);
                return;
            case 4:
                this.x.a(false, param);
                return;
            default:
                System.out.println("eeek!");
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) {
        AlgorithmParameterSpec paramSpec = null;
        if (params != null) {
            int i = 0;
            while (true) {
                Class[] clsArr = this.c;
                if (i == clsArr.length) {
                    break;
                }
                try {
                    paramSpec = params.getParameterSpec(clsArr[i]);
                    break;
                } catch (Exception e) {
                    i++;
                }
            }
            if (paramSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + params.toString());
            }
        }
        this.q = params;
        engineInit(opmode, key, paramSpec, random);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, SecureRandom random) {
        try {
            engineInit(opmode, key, (AlgorithmParameterSpec) null, random);
        } catch (InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        throw new RuntimeException("not supported for wrapping");
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        throw new RuntimeException("not supported for wrapping");
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineWrap(Key key) {
        byte[] encoded = key.getEncoded();
        if (encoded != null) {
            try {
                Wrapper wrapper = this.x;
                if (wrapper == null) {
                    return engineDoFinal(encoded, 0, encoded.length);
                }
                return wrapper.wrap(encoded, 0, encoded.length);
            } catch (BadPaddingException e) {
                throw new IllegalBlockSizeException(e.getMessage());
            }
        } else {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public Key engineUnwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType) {
        byte[] encoded;
        try {
            Wrapper wrapper = this.x;
            if (wrapper == null) {
                encoded = engineDoFinal(wrappedKey, 0, wrappedKey.length);
            } else {
                encoded = wrapper.c(wrappedKey, 0, wrappedKey.length);
            }
            if (wrappedKeyType == 3) {
                return new SecretKeySpec(encoded, wrappedKeyAlgorithm);
            }
            if (!wrappedKeyAlgorithm.equals("") || wrappedKeyType != 2) {
                try {
                    KeyFactory kf = this.p0.e(wrappedKeyAlgorithm);
                    if (wrappedKeyType == 1) {
                        return kf.generatePublic(new X509EncodedKeySpec(encoded));
                    }
                    if (wrappedKeyType == 2) {
                        return kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
                    }
                    throw new InvalidKeyException("Unknown key type " + wrappedKeyType);
                } catch (NoSuchProviderException e) {
                    throw new InvalidKeyException("Unknown key type " + e.getMessage());
                } catch (InvalidKeySpecException e2) {
                    throw new InvalidKeyException("Unknown key type " + e2.getMessage());
                }
            } else {
                try {
                    PrivateKeyInfo in = PrivateKeyInfo.f(encoded);
                    PrivateKey privKey = BouncyCastleProvider.getPrivateKey(in);
                    if (privKey != null) {
                        return privKey;
                    }
                    throw new InvalidKeyException("algorithm " + in.g().e() + " not supported");
                } catch (Exception e3) {
                    throw new InvalidKeyException("Invalid key encoding.");
                }
            }
        } catch (InvalidCipherTextException e4) {
            throw new InvalidKeyException(e4.getMessage());
        } catch (BadPaddingException e5) {
            throw new InvalidKeyException(e5.getMessage());
        } catch (IllegalBlockSizeException e22) {
            throw new InvalidKeyException(e22.getMessage());
        }
    }
}
