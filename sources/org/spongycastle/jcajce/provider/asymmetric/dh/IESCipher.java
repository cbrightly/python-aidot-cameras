package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.CipherSpi;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.KeyEncoder;
import org.spongycastle.crypto.agreement.DHBasicAgreement;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.IESEngine;
import org.spongycastle.crypto.generators.DHKeyPairGenerator;
import org.spongycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHKeyParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.IESWithCipherParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.parsers.DHIESPublicKeyParser;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.DHUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.IESUtil;
import org.spongycastle.jcajce.provider.util.BadBlockException;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.IESKey;
import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Strings;

public class IESCipher extends CipherSpi {
    private final JcaJceHelper a = new BCJcaJceHelper();
    private final int b;
    private IESEngine c;
    private int d = -1;
    private ByteArrayOutputStream e = new ByteArrayOutputStream();
    private AlgorithmParameters f = null;
    private IESParameterSpec g = null;
    private AsymmetricKeyParameter h;
    private SecureRandom i;
    private boolean j = false;
    private AsymmetricKeyParameter k = null;

    public IESCipher(IESEngine engine) {
        this.c = engine;
        this.b = 0;
    }

    public IESCipher(IESEngine engine, int ivLength) {
        this.c = engine;
        this.b = ivLength;
    }

    public int engineGetBlockSize() {
        if (this.c.d() != null) {
            return this.c.d().b();
        }
        return 0;
    }

    public int engineGetKeySize(Key key) {
        if (key instanceof DHKey) {
            return ((DHKey) key).getParams().getP().bitLength();
        }
        throw new IllegalArgumentException("not a DH key");
    }

    public byte[] engineGetIV() {
        IESParameterSpec iESParameterSpec = this.g;
        if (iESParameterSpec != null) {
            return iESParameterSpec.e();
        }
        return null;
    }

    public AlgorithmParameters engineGetParameters() {
        if (this.f == null && this.g != null) {
            try {
                AlgorithmParameters g2 = this.a.g("IES");
                this.f = g2;
                g2.init(this.g);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.f;
    }

    public void engineSetMode(String mode) {
        String modeName = Strings.l(mode);
        if (modeName.equals("NONE")) {
            this.j = false;
        } else if (modeName.equals("DHAES")) {
            this.j = true;
        } else {
            throw new IllegalArgumentException("can't support mode " + mode);
        }
    }

    public int engineGetOutputSize(int inputLen) {
        int len2;
        int len3;
        if (this.h != null) {
            int len1 = this.c.f().e();
            if (this.k == null) {
                len2 = (((((DHKeyParameters) this.h).b().e().bitLength() + 7) * 2) / 8) + 1;
            } else {
                len2 = 0;
            }
            if (this.c.d() == null) {
                len3 = inputLen;
            } else {
                int i2 = this.d;
                if (i2 == 1 || i2 == 3) {
                    len3 = this.c.d().c(inputLen);
                } else if (i2 == 2 || i2 == 4) {
                    len3 = this.c.d().c((inputLen - len1) - len2);
                } else {
                    throw new IllegalStateException("cipher not initialised");
                }
            }
            int i3 = this.d;
            if (i3 == 1 || i3 == 3) {
                return this.e.size() + len1 + len2 + len3;
            }
            if (i3 == 2 || i3 == 4) {
                return ((this.e.size() - len1) - len2) + len3;
            }
            throw new IllegalStateException("IESCipher not initialised");
        }
        throw new IllegalStateException("cipher not initialised");
    }

    public void engineSetPadding(String padding) {
        String paddingName = Strings.l(padding);
        if (!paddingName.equals("NOPADDING") && !paddingName.equals("PKCS5PADDING") && !paddingName.equals("PKCS7PADDING")) {
            throw new NoSuchPaddingException("padding not available with IESCipher");
        }
    }

    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) {
        AlgorithmParameterSpec paramSpec = null;
        if (params != null) {
            try {
                paramSpec = params.getParameterSpec(IESParameterSpec.class);
            } catch (Exception e2) {
                throw new InvalidAlgorithmParameterException("cannot recognise parameters: " + e2.toString());
            }
        }
        this.f = params;
        engineInit(opmode, key, paramSpec, random);
    }

    public void engineInit(int opmode, Key key, AlgorithmParameterSpec engineSpec, SecureRandom random) {
        if (engineSpec == null) {
            byte[] nonce = null;
            int i2 = this.b;
            if (i2 != 0 && opmode == 1) {
                nonce = new byte[i2];
                random.nextBytes(nonce);
            }
            this.g = IESUtil.a(this.c.d(), nonce);
        } else if (engineSpec instanceof IESParameterSpec) {
            this.g = (IESParameterSpec) engineSpec;
        } else {
            throw new InvalidAlgorithmParameterException("must be passed IES parameters");
        }
        byte[] nonce2 = this.g.e();
        int i3 = this.b;
        if (i3 == 0 || (nonce2 != null && nonce2.length == i3)) {
            if (opmode == 1 || opmode == 3) {
                if (key instanceof DHPublicKey) {
                    this.h = DHUtil.b((PublicKey) key);
                } else if (key instanceof IESKey) {
                    IESKey ieKey = (IESKey) key;
                    this.h = DHUtil.b(ieKey.getPublic());
                    this.k = DHUtil.a(ieKey.getPrivate());
                } else {
                    throw new InvalidKeyException("must be passed recipient's public DH key for encryption");
                }
            } else if (opmode != 2 && opmode != 4) {
                throw new InvalidKeyException("must be passed EC key");
            } else if (key instanceof DHPrivateKey) {
                this.h = DHUtil.a((PrivateKey) key);
            } else if (key instanceof IESKey) {
                IESKey ieKey2 = (IESKey) key;
                this.k = DHUtil.b(ieKey2.getPublic());
                this.h = DHUtil.a(ieKey2.getPrivate());
            } else {
                throw new InvalidKeyException("must be passed recipient's private DH key for decryption");
            }
            this.i = random;
            this.d = opmode;
            this.e.reset();
            return;
        }
        throw new InvalidAlgorithmParameterException("NONCE in IES Parameters needs to be " + this.b + " bytes long");
    }

    public void engineInit(int opmode, Key key, SecureRandom random) {
        try {
            engineInit(opmode, key, (AlgorithmParameterSpec) null, random);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new IllegalArgumentException("cannot handle supplied parameter spec: " + e2.getMessage());
        }
    }

    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        this.e.write(input, inputOffset, inputLen);
        return null;
    }

    public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        this.e.write(input, inputOffset, inputLen);
        return 0;
    }

    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
        if (inputLen != 0) {
            this.e.write(input, inputOffset, inputLen);
        }
        byte[] in = this.e.toByteArray();
        this.e.reset();
        CipherParameters params = new IESWithCipherParameters(this.g.b(), this.g.c(), this.g.d(), this.g.a());
        if (this.g.e() != null) {
            params = new ParametersWithIV(params, this.g.e());
        }
        DHParameters dhParams = ((DHKeyParameters) this.h).b();
        AsymmetricKeyParameter asymmetricKeyParameter = this.k;
        if (asymmetricKeyParameter != null) {
            try {
                int i2 = this.d;
                if (i2 != 1) {
                    if (i2 != 3) {
                        this.c.i(false, this.h, asymmetricKeyParameter, params);
                        return this.c.j(in, 0, in.length);
                    }
                }
                this.c.i(true, asymmetricKeyParameter, this.h, params);
                return this.c.j(in, 0, in.length);
            } catch (Exception e2) {
                throw new BadBlockException("unable to process block", e2);
            }
        } else {
            int i3 = this.d;
            if (i3 == 1 || i3 == 3) {
                DHKeyPairGenerator gen = new DHKeyPairGenerator();
                gen.b(new DHKeyGenerationParameters(this.i, dhParams));
                try {
                    this.c.h(this.h, params, new EphemeralKeyPairGenerator(gen, new KeyEncoder() {
                        public byte[] a(AsymmetricKeyParameter keyParameter) {
                            byte[] Vloc = new byte[((((DHKeyParameters) keyParameter).b().e().bitLength() + 7) / 8)];
                            byte[] Vtmp = BigIntegers.b(((DHPublicKeyParameters) keyParameter).c());
                            if (Vtmp.length <= Vloc.length) {
                                System.arraycopy(Vtmp, 0, Vloc, Vloc.length - Vtmp.length, Vtmp.length);
                                return Vloc;
                            }
                            throw new IllegalArgumentException("Senders's public key longer than expected.");
                        }
                    }));
                    return this.c.j(in, 0, in.length);
                } catch (Exception e3) {
                    throw new BadBlockException("unable to process block", e3);
                }
            } else if (i3 == 2 || i3 == 4) {
                try {
                    IESEngine iESEngine = this.c;
                    AsymmetricKeyParameter asymmetricKeyParameter2 = this.h;
                    iESEngine.g(asymmetricKeyParameter2, params, new DHIESPublicKeyParser(((DHKeyParameters) asymmetricKeyParameter2).b()));
                    return this.c.j(in, 0, in.length);
                } catch (InvalidCipherTextException e4) {
                    throw new BadBlockException("unable to process block", e4);
                }
            } else {
                throw new IllegalStateException("IESCipher not initialised");
            }
        }
    }

    public int engineDoFinal(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset) {
        byte[] buf = engineDoFinal(input, inputOffset, inputLength);
        System.arraycopy(buf, 0, output, outputOffset, buf.length);
        return buf.length;
    }

    public static class IES extends IESCipher {
        public IES() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()), new HMac(DigestFactory.b())));
        }
    }

    public static class IESwithDESedeCBC extends IESCipher {
        public IESwithDESedeCBC() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()), new HMac(DigestFactory.b()), new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()))), 8);
        }
    }

    public static class IESwithAESCBC extends IESCipher {
        public IESwithAESCBC() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.b()), new HMac(DigestFactory.b()), new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()))), 16);
        }
    }
}
