package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.macs.OldHMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA384 {
    private SHA384() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA384Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new SHA384Digest((SHA384Digest) this.c);
            return d;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA384Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA384", 384, new CipherKeyGenerator());
        }
    }

    public static class OldSHA384 extends BaseMac {
        public OldSHA384() {
            super(new OldHMac(new SHA384Digest()));
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA384.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.SHA-384", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA384", "SHA-384");
            provider.addAlgorithm("Alg.Alias.MessageDigest." + NISTObjectIdentifiers.d, "SHA-384");
            provider.addAlgorithm("Mac.OLDHMACSHA384", str + "$OldSHA384");
            provider.addAlgorithm("Mac.PBEWITHHMACSHA384", str + "$HashMac");
            b(provider, "SHA384", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "SHA384", PKCSObjectIdentifiers.x0);
        }
    }
}
