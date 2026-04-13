package org.spongycastle.jcajce.provider.digest;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class RIPEMD160 {
    private RIPEMD160() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new RIPEMD160Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new RIPEMD160Digest((RIPEMD160Digest) this.c);
            return d;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new RIPEMD160Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACRIPEMD160", Opcodes.IF_ICMPNE, new CipherKeyGenerator());
        }
    }

    public static class PBEWithHmac extends BaseMac {
        public PBEWithHmac() {
            super(new HMac(new RIPEMD160Digest()), 2, 2, Opcodes.IF_ICMPNE);
        }
    }

    public static class PBEWithHmacKeyFactory extends PBESecretKeyFactory {
        public PBEWithHmacKeyFactory() {
            super("PBEwithHmacRIPEMD160", (ASN1ObjectIdentifier) null, false, 2, 2, Opcodes.IF_ICMPNE, 0);
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = RIPEMD160.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.RIPEMD160", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest." + TeleTrusTObjectIdentifiers.b, "RIPEMD160");
            b(provider, "RIPEMD160", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "RIPEMD160", IANAObjectIdentifiers.q);
            provider.addAlgorithm("SecretKeyFactory.PBEWITHHMACRIPEMD160", str + "$PBEWithHmacKeyFactory");
            provider.addAlgorithm("Mac.PBEWITHHMACRIPEMD160", str + "$PBEWithHmac");
        }
    }
}
