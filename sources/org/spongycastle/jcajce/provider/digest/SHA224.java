package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA224 {
    private SHA224() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA224Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new SHA224Digest((SHA224Digest) this.c);
            return d;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA224Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA224", 224, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA224.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.SHA-224", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA224", "SHA-224");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.f, "SHA-224");
            provider.addAlgorithm("Mac.PBEWITHHMACSHA224", str + "$HashMac");
            b(provider, "SHA224", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "SHA224", PKCSObjectIdentifiers.v0);
        }
    }
}
