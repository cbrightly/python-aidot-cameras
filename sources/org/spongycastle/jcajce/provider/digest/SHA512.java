package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHA512tDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.macs.OldHMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA512 {
    private SHA512() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA512Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new SHA512Digest((SHA512Digest) this.c);
            return d;
        }
    }

    public static class DigestT extends BCMessageDigest implements Cloneable {
        public DigestT(int bitLength) {
            super(new SHA512tDigest(bitLength));
        }

        public Object clone() {
            DigestT d = (DigestT) super.clone();
            d.c = new SHA512tDigest((SHA512tDigest) this.c);
            return d;
        }
    }

    public static class DigestT224 extends DigestT {
        public DigestT224() {
            super(224);
        }
    }

    public static class DigestT256 extends DigestT {
        public DigestT256() {
            super(256);
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA512Digest()));
        }
    }

    public static class HashMacT224 extends BaseMac {
        public HashMacT224() {
            super(new HMac(new SHA512tDigest(224)));
        }
    }

    public static class HashMacT256 extends BaseMac {
        public HashMacT256() {
            super(new HMac(new SHA512tDigest(256)));
        }
    }

    public static class OldSHA512 extends BaseMac {
        public OldSHA512() {
            super(new OldHMac(new SHA512Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA512", 512, new CipherKeyGenerator());
        }
    }

    public static class KeyGeneratorT224 extends BaseKeyGenerator {
        public KeyGeneratorT224() {
            super("HMACSHA512/224", 224, new CipherKeyGenerator());
        }
    }

    public static class KeyGeneratorT256 extends BaseKeyGenerator {
        public KeyGeneratorT256() {
            super("HMACSHA512/256", 256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA512.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.SHA-512", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA512", "SHA-512");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.e, "SHA-512");
            provider.addAlgorithm("MessageDigest.SHA-512/224", str + "$DigestT224");
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA512/224", "SHA-512/224");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.g, "SHA-512/224");
            provider.addAlgorithm("MessageDigest.SHA-512/256", str + "$DigestT256");
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA512256", "SHA-512/256");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.h, "SHA-512/256");
            provider.addAlgorithm("Mac.OLDHMACSHA512", str + "$OldSHA512");
            provider.addAlgorithm("Mac.PBEWITHHMACSHA512", str + "$HashMac");
            b(provider, "SHA512", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "SHA512", PKCSObjectIdentifiers.y0);
            b(provider, "SHA512/224", str + "$HashMacT224", str + "$KeyGeneratorT224");
            b(provider, "SHA512/256", str + "$HashMacT256", str + "$KeyGeneratorT256");
        }
    }
}
