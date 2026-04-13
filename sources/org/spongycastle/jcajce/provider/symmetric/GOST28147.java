package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST28147Parameters;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.CryptoProWrapEngine;
import org.spongycastle.crypto.engines.GOST28147Engine;
import org.spongycastle.crypto.engines.GOST28147WrapEngine;
import org.spongycastle.crypto.macs.GOST28147Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.GCFBBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.spec.GOST28147ParameterSpec;

public final class GOST28147 {
    private static Map<ASN1ObjectIdentifier, String> a = new HashMap();
    /* access modifiers changed from: private */
    public static Map<String, ASN1ObjectIdentifier> b = new HashMap();

    static {
        a.put(CryptoProObjectIdentifiers.g, "E-TEST");
        Map<ASN1ObjectIdentifier, String> map = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = CryptoProObjectIdentifiers.h;
        map.put(aSN1ObjectIdentifier, "E-A");
        Map<ASN1ObjectIdentifier, String> map2 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = CryptoProObjectIdentifiers.i;
        map2.put(aSN1ObjectIdentifier2, "E-B");
        Map<ASN1ObjectIdentifier, String> map3 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = CryptoProObjectIdentifiers.j;
        map3.put(aSN1ObjectIdentifier3, "E-C");
        Map<ASN1ObjectIdentifier, String> map4 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = CryptoProObjectIdentifiers.k;
        map4.put(aSN1ObjectIdentifier4, "E-D");
        b.put("E-A", aSN1ObjectIdentifier);
        b.put("E-B", aSN1ObjectIdentifier2);
        b.put("E-C", aSN1ObjectIdentifier3);
        b.put("E-D", aSN1ObjectIdentifier4);
    }

    private GOST28147() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new GOST28147Engine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new GOST28147Engine()), 64);
        }
    }

    public static class GCFB extends BaseBlockCipher {
        public GCFB() {
            super(new BufferedBlockCipher(new GCFBBlockCipher(new GOST28147Engine())), 64);
        }
    }

    public static class GostWrap extends BaseWrapCipher {
        public GostWrap() {
            super(new GOST28147WrapEngine());
        }
    }

    public static class CryptoProWrap extends BaseWrapCipher {
        public CryptoProWrap() {
            super(new CryptoProWrapEngine());
        }
    }

    public static class Mac extends BaseMac {
        public Mac() {
            super(new GOST28147Mac());
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int keySize) {
            super("GOST28147", keySize, new CipherKeyGenerator());
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        byte[] d = new byte[8];
        byte[] e = GOST28147Engine.j("E-A");

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            if (genParamSpec instanceof GOST28147ParameterSpec) {
                this.e = ((GOST28147ParameterSpec) genParamSpec).c();
                return;
            }
            throw new InvalidAlgorithmParameterException("parameter spec not supported");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(this.d);
            try {
                AlgorithmParameters params = a("GOST28147");
                params.init(new GOST28147ParameterSpec(this.e, this.d));
                return params;
            } catch (Exception e2) {
                throw new RuntimeException(e2.getMessage());
            }
        }
    }

    public static abstract class BaseAlgParams extends BaseAlgorithmParameters {
        private byte[] iv;
        private ASN1ObjectIdentifier sBox = CryptoProObjectIdentifiers.h;

        /* access modifiers changed from: package-private */
        public abstract void localInit(byte[] bArr);

        /* access modifiers changed from: protected */
        public final void engineInit(byte[] encoding) {
            engineInit(encoding, "ASN.1");
        }

        /* access modifiers changed from: protected */
        public final byte[] engineGetEncoded() {
            return engineGetEncoded("ASN.1");
        }

        /* access modifiers changed from: protected */
        public final byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return localGetEncoded();
            }
            throw new IOException("Unknown parameter format: " + format);
        }

        /* access modifiers changed from: protected */
        public final void engineInit(byte[] params, String format) {
            if (params == null) {
                throw new NullPointerException("Encoded parameters cannot be null");
            } else if (isASN1FormatString(format)) {
                try {
                    localInit(params);
                } catch (IOException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new IOException("Parameter parsing failed: " + e2.getMessage());
                }
            } else {
                throw new IOException("Unknown parameter format: " + format);
            }
        }

        /* access modifiers changed from: protected */
        public byte[] localGetEncoded() {
            return new GOST28147Parameters(this.iv, this.sBox).getEncoded();
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == IvParameterSpec.class) {
                return new IvParameterSpec(this.iv);
            }
            if (paramSpec == GOST28147ParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
                return new GOST28147ParameterSpec(this.sBox, this.iv);
            }
            throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + paramSpec.getName());
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof IvParameterSpec) {
                this.iv = ((IvParameterSpec) paramSpec).getIV();
            } else if (paramSpec instanceof GOST28147ParameterSpec) {
                this.iv = ((GOST28147ParameterSpec) paramSpec).a();
                try {
                    this.sBox = getSBoxOID(((GOST28147ParameterSpec) paramSpec).c());
                } catch (IllegalArgumentException e) {
                    throw new InvalidParameterSpecException(e.getMessage());
                }
            } else {
                throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
            }
        }

        protected static ASN1ObjectIdentifier getSBoxOID(String name) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) GOST28147.b.get(name);
            if (oid != null) {
                return oid;
            }
            throw new IllegalArgumentException("Unknown SBOX name: " + name);
        }

        protected static ASN1ObjectIdentifier getSBoxOID(byte[] sBox2) {
            return getSBoxOID(GOST28147Engine.k(sBox2));
        }
    }

    public static class AlgParams extends BaseAlgParams {
        private byte[] iv;
        private ASN1ObjectIdentifier sBox = CryptoProObjectIdentifiers.h;

        /* access modifiers changed from: protected */
        public byte[] localGetEncoded() {
            return new GOST28147Parameters(this.iv, this.sBox).getEncoded();
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == IvParameterSpec.class) {
                return new IvParameterSpec(this.iv);
            }
            if (paramSpec == GOST28147ParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
                return new GOST28147ParameterSpec(this.sBox, this.iv);
            }
            throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + paramSpec.getName());
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof IvParameterSpec) {
                this.iv = ((IvParameterSpec) paramSpec).getIV();
            } else if (paramSpec instanceof GOST28147ParameterSpec) {
                this.iv = ((GOST28147ParameterSpec) paramSpec).a();
                try {
                    this.sBox = BaseAlgParams.getSBoxOID(((GOST28147ParameterSpec) paramSpec).c());
                } catch (IllegalArgumentException e) {
                    throw new InvalidParameterSpecException(e.getMessage());
                }
            } else {
                throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
            }
        }

        /* access modifiers changed from: protected */
        public void localInit(byte[] params) {
            ASN1Primitive asn1Params = ASN1Primitive.h(params);
            if (asn1Params instanceof ASN1OctetString) {
                this.iv = ASN1OctetString.o(asn1Params).q();
            } else if (asn1Params instanceof ASN1Sequence) {
                GOST28147Parameters gParams = GOST28147Parameters.f(asn1Params);
                this.sBox = gParams.e();
                this.iv = gParams.getIV();
            } else {
                throw new IOException("Unable to recognize parameters");
            }
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "GOST 28147 IV Parameters";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = GOST28147.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$ECB");
            provider.addAlgorithm("Cipher.GOST28147", sb.toString());
            provider.addAlgorithm("Alg.Alias.Cipher.GOST", "GOST28147");
            provider.addAlgorithm("Alg.Alias.Cipher.GOST-28147", "GOST28147");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Cipher.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = CryptoProObjectIdentifiers.f;
            sb2.append(aSN1ObjectIdentifier);
            provider.addAlgorithm(sb2.toString(), str + "$GCFB");
            provider.addAlgorithm("KeyGenerator.GOST28147", str + "$KeyGen");
            provider.addAlgorithm("Alg.Alias.KeyGenerator.GOST", "GOST28147");
            provider.addAlgorithm("Alg.Alias.KeyGenerator.GOST-28147", "GOST28147");
            provider.addAlgorithm("Alg.Alias.KeyGenerator." + aSN1ObjectIdentifier, "GOST28147");
            provider.addAlgorithm("AlgorithmParameters.GOST28147", str + "$AlgParams");
            provider.addAlgorithm("AlgorithmParameterGenerator.GOST28147", str + "$AlgParamGen");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + aSN1ObjectIdentifier, "GOST28147");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + aSN1ObjectIdentifier, "GOST28147");
            provider.addAlgorithm("Cipher." + CryptoProObjectIdentifiers.e, str + "$CryptoProWrap");
            provider.addAlgorithm("Cipher." + CryptoProObjectIdentifiers.d, str + "$GostWrap");
            provider.addAlgorithm("Mac.GOST28147MAC", str + "$Mac");
            provider.addAlgorithm("Alg.Alias.Mac.GOST28147", "GOST28147MAC");
        }
    }
}
