package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.RC4Engine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class ARC4 {
    private ARC4() {
    }

    public static class Base extends BaseStreamCipher {
        public Base() {
            super(new RC4Engine(), 0);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("RC4", 128, new CipherKeyGenerator());
        }
    }

    public static class PBEWithSHAAnd128BitKeyFactory extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitKeyFactory() {
            super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.r2, true, 2, 1, 128, 0);
        }
    }

    public static class PBEWithSHAAnd40BitKeyFactory extends PBESecretKeyFactory {
        public PBEWithSHAAnd40BitKeyFactory() {
            super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.r2, true, 2, 1, 40, 0);
        }
    }

    public static class PBEWithSHAAnd128Bit extends BaseStreamCipher {
        public PBEWithSHAAnd128Bit() {
            super(new RC4Engine(), 0, 128, 1);
        }
    }

    public static class PBEWithSHAAnd40Bit extends BaseStreamCipher {
        public PBEWithSHAAnd40Bit() {
            super(new RC4Engine(), 0, 40, 1);
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = ARC4.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Base");
            provider.addAlgorithm("Cipher.ARC4", sb.toString());
            provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.o0, "ARC4");
            provider.addAlgorithm("Alg.Alias.Cipher.ARCFOUR", "ARC4");
            provider.addAlgorithm("Alg.Alias.Cipher.RC4", "ARC4");
            provider.addAlgorithm("KeyGenerator.ARC4", str + "$KeyGen");
            provider.addAlgorithm("Alg.Alias.KeyGenerator.RC4", "ARC4");
            provider.addAlgorithm("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITRC4", str + "$PBEWithSHAAnd128BitKeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND40BITRC4", str + "$PBEWithSHAAnd40BitKeyFactory");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.r2;
            sb2.append(aSN1ObjectIdentifier);
            provider.addAlgorithm(sb2.toString(), "PKCS12PBE");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Alg.Alias.AlgorithmParameters.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.s2;
            sb3.append(aSN1ObjectIdentifier2);
            provider.addAlgorithm(sb3.toString(), "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC4", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC4", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC4", "PKCS12PBE");
            provider.addAlgorithm("Cipher.PBEWITHSHAAND128BITRC4", str + "$PBEWithSHAAnd128Bit");
            provider.addAlgorithm("Cipher.PBEWITHSHAAND40BITRC4", str + "$PBEWithSHAAnd40Bit");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier, "PBEWITHSHAAND128BITRC4");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier2, "PBEWITHSHAAND40BITRC4");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC4", "PBEWITHSHAAND128BITRC4");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC4", "PBEWITHSHAAND40BITRC4");
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier, "PBEWITHSHAAND128BITRC4");
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier2, "PBEWITHSHAAND40BITRC4");
        }
    }
}
