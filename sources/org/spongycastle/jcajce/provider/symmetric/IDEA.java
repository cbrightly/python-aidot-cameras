package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.misc.IDEACBCPar;
import org.spongycastle.asn1.misc.MiscObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.IDEAEngine;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class IDEA {
    private IDEA() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new IDEAEngine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new IDEAEngine()), 64);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("IDEA", 128, new CipherKeyGenerator());
        }
    }

    public static class PBEWithSHAAndIDEAKeyGen extends PBESecretKeyFactory {
        public PBEWithSHAAndIDEAKeyGen() {
            super("PBEwithSHAandIDEA-CBC", (ASN1ObjectIdentifier) null, true, 2, 1, 128, 64);
        }
    }

    public static class PBEWithSHAAndIDEA extends BaseBlockCipher {
        public PBEWithSHAAndIDEA() {
            super((BlockCipher) new CBCBlockCipher(new IDEAEngine()));
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for IDEA parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[8];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("IDEA");
                params.init(new IvParameterSpec(iv));
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        private byte[] iv;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            return engineGetEncoded("ASN.1");
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return new IDEACBCPar(engineGetEncoded("RAW")).getEncoded();
            }
            if (!format.equals("RAW")) {
                return null;
            }
            byte[] bArr = this.iv;
            byte[] tmp = new byte[bArr.length];
            System.arraycopy(bArr, 0, tmp, 0, bArr.length);
            return tmp;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == IvParameterSpec.class) {
                return new IvParameterSpec(this.iv);
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof IvParameterSpec) {
                this.iv = ((IvParameterSpec) paramSpec).getIV();
                return;
            }
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            byte[] bArr = new byte[params.length];
            this.iv = bArr;
            System.arraycopy(params, 0, bArr, 0, bArr.length);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (format.equals("RAW")) {
                engineInit(params);
            } else if (format.equals("ASN.1")) {
                engineInit(new IDEACBCPar((ASN1Sequence) new ASN1InputStream(params).r()).getIV());
            } else {
                throw new IOException("Unknown parameters format in IV parameters object");
            }
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "IDEA Parameters";
        }
    }

    public static class Mac extends BaseMac {
        public Mac() {
            super(new CBCBlockCipherMac(new IDEAEngine()));
        }
    }

    public static class CFB8Mac extends BaseMac {
        public CFB8Mac() {
            super(new CFBBlockCipherMac(new IDEAEngine()));
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = IDEA.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParamGen");
            provider.addAlgorithm("AlgorithmParameterGenerator.IDEA", sb.toString());
            provider.addAlgorithm("AlgorithmParameterGenerator.1.3.6.1.4.1.188.7.1.1.2", str + "$AlgParamGen");
            provider.addAlgorithm("AlgorithmParameters.IDEA", str + "$AlgParams");
            provider.addAlgorithm("AlgorithmParameters.1.3.6.1.4.1.188.7.1.1.2", str + "$AlgParams");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA-CBC", "PKCS12PBE");
            provider.addAlgorithm("Cipher.IDEA", str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = MiscObjectIdentifiers.v;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            provider.addAlgorithm("Cipher.PBEWITHSHAANDIDEA-CBC", str + "$PBEWithSHAAndIDEA");
            provider.addAlgorithm("KeyGenerator.IDEA", str + "$KeyGen");
            provider.addAlgorithm("KeyGenerator", aSN1ObjectIdentifier, str + "$KeyGen");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAANDIDEA-CBC", str + "$PBEWithSHAAndIDEAKeyGen");
            provider.addAlgorithm("Mac.IDEAMAC", str + "$Mac");
            provider.addAlgorithm("Alg.Alias.Mac.IDEA", "IDEAMAC");
            provider.addAlgorithm("Mac.IDEAMAC/CFB8", str + "$CFB8Mac");
            provider.addAlgorithm("Alg.Alias.Mac.IDEA/CFB8", "IDEAMAC/CFB8");
        }
    }
}
