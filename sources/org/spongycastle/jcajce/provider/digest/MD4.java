package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class MD4 {
    private MD4() {
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new MD4Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACMD4", 128, new CipherKeyGenerator());
        }
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new MD4Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new MD4Digest((MD4Digest) this.c);
            return d;
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = MD4.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.MD4", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.s0, "MD4");
            b(provider, "MD4", str + "$HashMac", str + "$KeyGenerator");
        }
    }
}
