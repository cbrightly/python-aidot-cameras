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
import org.spongycastle.asn1.misc.CAST5CBCParameters;
import org.spongycastle.asn1.misc.MiscObjectIdentifiers;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.CAST5Engine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class CAST5 {
    private CAST5() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new CAST5Engine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new CAST5Engine()), 64);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("CAST5", 128, new CipherKeyGenerator());
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for CAST5 parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] iv = new byte[8];
            if (this.b == null) {
                this.b = new SecureRandom();
            }
            this.b.nextBytes(iv);
            try {
                AlgorithmParameters params = a("CAST5");
                params.init(new IvParameterSpec(iv));
                return params;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        private byte[] iv;
        private int keyLength = 128;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            byte[] bArr = this.iv;
            byte[] tmp = new byte[bArr.length];
            System.arraycopy(bArr, 0, tmp, 0, bArr.length);
            return tmp;
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                return new CAST5CBCParameters(engineGetEncoded(), this.keyLength).getEncoded();
            }
            if (format.equals("RAW")) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            if (paramSpec == IvParameterSpec.class) {
                return new IvParameterSpec(this.iv);
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to CAST5 parameters object.");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof IvParameterSpec) {
                this.iv = ((IvParameterSpec) paramSpec).getIV();
                return;
            }
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a CAST5 parameters algorithm parameters object");
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            byte[] bArr = new byte[params.length];
            this.iv = bArr;
            System.arraycopy(params, 0, bArr, 0, bArr.length);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (isASN1FormatString(format)) {
                CAST5CBCParameters p = CAST5CBCParameters.e(new ASN1InputStream(params).r());
                this.keyLength = p.f();
                this.iv = p.getIV();
            } else if (format.equals("RAW")) {
                engineInit(params);
            } else {
                throw new IOException("Unknown parameters format in IV parameters object");
            }
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "CAST5 Parameters";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = CAST5.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParams");
            provider.addAlgorithm("AlgorithmParameters.CAST5", sb.toString());
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113533.7.66.10", "CAST5");
            provider.addAlgorithm("AlgorithmParameterGenerator.CAST5", str + "$AlgParamGen");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.1.2.840.113533.7.66.10", "CAST5");
            provider.addAlgorithm("Cipher.CAST5", str + "$ECB");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = MiscObjectIdentifiers.u;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            provider.addAlgorithm("KeyGenerator.CAST5", str + "$KeyGen");
            provider.addAlgorithm("Alg.Alias.KeyGenerator", aSN1ObjectIdentifier, "CAST5");
        }
    }
}
