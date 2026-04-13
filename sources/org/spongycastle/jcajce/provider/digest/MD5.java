package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class MD5 {
    private MD5() {
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new MD5Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACMD5", 128, new CipherKeyGenerator());
        }
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new MD5Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new MD5Digest((MD5Digest) this.c);
            return d;
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = MD5.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.MD5", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest." + PKCSObjectIdentifiers.t0, "MD5");
            b(provider, "MD5", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "MD5", IANAObjectIdentifiers.n);
        }
    }
}
