package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.crypto.digests.SM3Digest;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;

public class SM3 {
    private SM3() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SM3Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new SM3Digest((SM3Digest) this.c);
            return d;
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SM3.class.getName();

        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("MessageDigest.SM3", a + "$Digest");
            provider.addAlgorithm("Alg.Alias.MessageDigest.SM3", "SM3");
            provider.addAlgorithm("Alg.Alias.MessageDigest.1.2.156.197.1.401", "SM3");
        }
    }
}
