package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.GOST3411Digest;
import org.spongycastle.crypto.digests.GOST3411_2012_256Digest;
import org.spongycastle.crypto.digests.GOST3411_2012_512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class GOST3411 {
    private GOST3411() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new GOST3411Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new GOST3411Digest((GOST3411Digest) this.c);
            return d;
        }
    }

    public static class Digest2012_256 extends BCMessageDigest implements Cloneable {
        public Digest2012_256() {
            super(new GOST3411_2012_256Digest());
        }

        public Object clone() {
            Digest2012_256 d = (Digest2012_256) super.clone();
            d.c = new GOST3411_2012_256Digest((GOST3411_2012_256Digest) this.c);
            return d;
        }
    }

    public static class Digest2012_512 extends BCMessageDigest implements Cloneable {
        public Digest2012_512() {
            super(new GOST3411_2012_512Digest());
        }

        public Object clone() {
            Digest2012_512 d = (Digest2012_512) super.clone();
            d.c = new GOST3411_2012_512Digest((GOST3411_2012_512Digest) this.c);
            return d;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new GOST3411Digest()));
        }
    }

    public static class HashMac2012_256 extends BaseMac {
        public HashMac2012_256() {
            super(new HMac(new GOST3411_2012_256Digest()));
        }
    }

    public static class HashMac2012_512 extends BaseMac {
        public HashMac2012_512() {
            super(new HMac(new GOST3411_2012_512Digest()));
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacGOST3411", (ASN1ObjectIdentifier) null, false, 2, 6, 256, 0);
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACGOST3411", 256, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator2012_256 extends BaseKeyGenerator {
        public KeyGenerator2012_256() {
            super("HMACGOST3411", 256, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator2012_512 extends BaseKeyGenerator {
        public KeyGenerator2012_512() {
            super("HMACGOST3411", 512, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = GOST3411.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.GOST3411", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest.GOST", "GOST3411");
            provider.addAlgorithm("Alg.Alias.MessageDigest.GOST-3411", "GOST3411");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.MessageDigest.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = CryptoProObjectIdentifiers.b;
            sb2.append(aSN1ObjectIdentifier);
            provider.addAlgorithm(sb2.toString(), "GOST3411");
            b(provider, "GOST3411", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "GOST3411", aSN1ObjectIdentifier);
            provider.addAlgorithm("MessageDigest.GOST3411-2012-256", str + "$Digest2012_256");
            provider.addAlgorithm("Alg.Alias.MessageDigest.GOST-2012-256", "GOST3411-2012-256");
            provider.addAlgorithm("Alg.Alias.MessageDigest.GOST-3411-2012-256", "GOST3411-2012-256");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + RosstandartObjectIdentifiers.c, "GOST3411-2012-256");
            b(provider, "GOST3411-2012-256", str + "$HashMac2012_256", str + "$KeyGenerator2012_256");
            c(provider, "GOST3411-2012-256", RosstandartObjectIdentifiers.e);
            provider.addAlgorithm("MessageDigest.GOST3411-2012-512", str + "$Digest2012_512");
            provider.addAlgorithm("Alg.Alias.MessageDigest.GOST-2012-512", "GOST3411-2012-512");
            provider.addAlgorithm("Alg.Alias.MessageDigest.GOST-3411-2012-512", "GOST3411-2012-512");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + RosstandartObjectIdentifiers.d, "GOST3411-2012-512");
            b(provider, "GOST3411-2012-512", str + "$HashMac2012_512", str + "$KeyGenerator2012_512");
            c(provider, "GOST3411-2012-512", RosstandartObjectIdentifiers.f);
            provider.addAlgorithm("SecretKeyFactory.PBEWITHHMACGOST3411", str + "$PBEWithMacKeyFactory");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier, "PBEWITHHMACGOST3411");
        }
    }
}
