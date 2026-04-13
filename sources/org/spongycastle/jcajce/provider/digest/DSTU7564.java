package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.ua.UAObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.DSTU7564Digest;
import org.spongycastle.crypto.macs.DSTU7564Mac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class DSTU7564 {
    private DSTU7564() {
    }

    public static class DigestDSTU7564 extends BCMessageDigest implements Cloneable {
        public DigestDSTU7564(int size) {
            super(new DSTU7564Digest(size));
        }

        public Object clone() {
            BCMessageDigest d = (BCMessageDigest) super.clone();
            d.c = new DSTU7564Digest((DSTU7564Digest) this.c);
            return d;
        }
    }

    public static class Digest256 extends DigestDSTU7564 {
        public Digest256() {
            super(256);
        }
    }

    public static class Digest384 extends DigestDSTU7564 {
        public Digest384() {
            super(384);
        }
    }

    public static class Digest512 extends DigestDSTU7564 {
        public Digest512() {
            super(512);
        }
    }

    public static class HashMac256 extends BaseMac {
        public HashMac256() {
            super(new DSTU7564Mac(256));
        }
    }

    public static class HashMac384 extends BaseMac {
        public HashMac384() {
            super(new DSTU7564Mac(384));
        }
    }

    public static class HashMac512 extends BaseMac {
        public HashMac512() {
            super(new DSTU7564Mac(512));
        }
    }

    public static class KeyGenerator256 extends BaseKeyGenerator {
        public KeyGenerator256() {
            super("HMACDSTU7564-256", 256, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator384 extends BaseKeyGenerator {
        public KeyGenerator384() {
            super("HMACDSTU7564-384", 384, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator512 extends BaseKeyGenerator {
        public KeyGenerator512() {
            super("HMACDSTU7564-512", 512, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = DSTU7564.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest256");
            provider.addAlgorithm("MessageDigest.DSTU7564-256", sb.toString());
            provider.addAlgorithm("MessageDigest.DSTU7564-384", str + "$Digest384");
            provider.addAlgorithm("MessageDigest.DSTU7564-512", str + "$Digest512");
            provider.addAlgorithm("MessageDigest", UAObjectIdentifiers.d, str + "$Digest256");
            provider.addAlgorithm("MessageDigest", UAObjectIdentifiers.e, str + "$Digest384");
            provider.addAlgorithm("MessageDigest", UAObjectIdentifiers.f, str + "$Digest512");
            b(provider, "DSTU7564-256", str + "$HashMac256", str + "$KeyGenerator256");
            b(provider, "DSTU7564-384", str + "$HashMac384", str + "$KeyGenerator384");
            b(provider, "DSTU7564-512", str + "$HashMac512", str + "$KeyGenerator512");
            c(provider, "DSTU7564-256", UAObjectIdentifiers.g);
            c(provider, "DSTU7564-384", UAObjectIdentifiers.h);
            c(provider, "DSTU7564-512", UAObjectIdentifiers.i);
        }
    }
}
