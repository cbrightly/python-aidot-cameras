package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA3 {
    private SHA3() {
    }

    public static class DigestSHA3 extends BCMessageDigest implements Cloneable {
        public DigestSHA3(int size) {
            super(new SHA3Digest(size));
        }

        public Object clone() {
            BCMessageDigest d = (BCMessageDigest) super.clone();
            d.c = new SHA3Digest((SHA3Digest) this.c);
            return d;
        }
    }

    public static class HashMacSHA3 extends BaseMac {
        public HashMacSHA3(int size) {
            super(new HMac(new SHA3Digest(size)));
        }
    }

    public static class KeyGeneratorSHA3 extends BaseKeyGenerator {
        public KeyGeneratorSHA3(int size) {
            super("HMACSHA3-" + size, size, new CipherKeyGenerator());
        }
    }

    public static class Digest224 extends DigestSHA3 {
        public Digest224() {
            super(224);
        }
    }

    public static class Digest256 extends DigestSHA3 {
        public Digest256() {
            super(256);
        }
    }

    public static class Digest384 extends DigestSHA3 {
        public Digest384() {
            super(384);
        }
    }

    public static class Digest512 extends DigestSHA3 {
        public Digest512() {
            super(512);
        }
    }

    public static class HashMac224 extends HashMacSHA3 {
        public HashMac224() {
            super(224);
        }
    }

    public static class HashMac256 extends HashMacSHA3 {
        public HashMac256() {
            super(256);
        }
    }

    public static class HashMac384 extends HashMacSHA3 {
        public HashMac384() {
            super(384);
        }
    }

    public static class HashMac512 extends HashMacSHA3 {
        public HashMac512() {
            super(512);
        }
    }

    public static class KeyGenerator224 extends KeyGeneratorSHA3 {
        public KeyGenerator224() {
            super(224);
        }
    }

    public static class KeyGenerator256 extends KeyGeneratorSHA3 {
        public KeyGenerator256() {
            super(256);
        }
    }

    public static class KeyGenerator384 extends KeyGeneratorSHA3 {
        public KeyGenerator384() {
            super(384);
        }
    }

    public static class KeyGenerator512 extends KeyGeneratorSHA3 {
        public KeyGenerator512() {
            super(512);
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA3.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest224");
            provider.addAlgorithm("MessageDigest.SHA3-224", sb.toString());
            provider.addAlgorithm("MessageDigest.SHA3-256", str + "$Digest256");
            provider.addAlgorithm("MessageDigest.SHA3-384", str + "$Digest384");
            provider.addAlgorithm("MessageDigest.SHA3-512", str + "$Digest512");
            provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.i, str + "$Digest224");
            provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.j, str + "$Digest256");
            provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.k, str + "$Digest384");
            provider.addAlgorithm("MessageDigest", NISTObjectIdentifiers.l, str + "$Digest512");
            b(provider, "SHA3-224", str + "$HashMac224", str + "$KeyGenerator224");
            c(provider, "SHA3-224", NISTObjectIdentifiers.o);
            b(provider, "SHA3-256", str + "$HashMac256", str + "$KeyGenerator256");
            c(provider, "SHA3-256", NISTObjectIdentifiers.p);
            b(provider, "SHA3-384", str + "$HashMac384", str + "$KeyGenerator384");
            c(provider, "SHA3-384", NISTObjectIdentifiers.q);
            b(provider, "SHA3-512", str + "$HashMac512", str + "$KeyGenerator512");
            c(provider, "SHA3-512", NISTObjectIdentifiers.r);
        }
    }
}
