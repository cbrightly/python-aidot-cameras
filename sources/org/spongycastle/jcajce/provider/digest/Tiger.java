package org.spongycastle.jcajce.provider.digest;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.TigerDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class Tiger {
    private Tiger() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new TigerDigest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new TigerDigest((TigerDigest) this.c);
            return d;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new TigerDigest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACTIGER", Opcodes.CHECKCAST, new CipherKeyGenerator());
        }
    }

    public static class TigerHmac extends BaseMac {
        public TigerHmac() {
            super(new HMac(new TigerDigest()));
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacTiger", (ASN1ObjectIdentifier) null, false, 2, 3, Opcodes.CHECKCAST, 0);
        }
    }

    public static class PBEWithHashMac extends BaseMac {
        public PBEWithHashMac() {
            super(new HMac(new TigerDigest()), 2, 3, Opcodes.CHECKCAST);
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = Tiger.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.TIGER", sb.toString());
            provider.addAlgorithm("MessageDigest.Tiger", str + "$Digest");
            b(provider, "TIGER", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "TIGER", IANAObjectIdentifiers.p);
            provider.addAlgorithm("SecretKeyFactory.PBEWITHHMACTIGER", str + "$PBEWithMacKeyFactory");
        }
    }
}
