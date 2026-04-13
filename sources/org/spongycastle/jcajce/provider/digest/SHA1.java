package org.spongycastle.jcajce.provider.digest;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class SHA1 {
    private SHA1() {
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA1Digest());
        }

        public Object clone() {
            Digest d = (Digest) super.clone();
            d.c = new SHA1Digest((SHA1Digest) this.c);
            return d;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA1", Opcodes.IF_ICMPNE, new CipherKeyGenerator());
        }
    }

    public static class SHA1Mac extends BaseMac {
        public SHA1Mac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacSHA", (ASN1ObjectIdentifier) null, false, 2, 1, Opcodes.IF_ICMPNE, 0);
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA1.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest");
            provider.addAlgorithm("MessageDigest.SHA-1", sb.toString());
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA1", "SHA-1");
            provider.addAlgorithm("Alg.Alias.MessageDigest.SHA", "SHA-1");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.MessageDigest.");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = OIWObjectIdentifiers.i;
            sb2.append(aSN1ObjectIdentifier);
            provider.addAlgorithm(sb2.toString(), "SHA-1");
            b(provider, "SHA1", str + "$HashMac", str + "$KeyGenerator");
            c(provider, "SHA1", PKCSObjectIdentifiers.u0);
            c(provider, "SHA1", IANAObjectIdentifiers.o);
            provider.addAlgorithm("Mac.PBEWITHHMACSHA", str + "$SHA1Mac");
            provider.addAlgorithm("Mac.PBEWITHHMACSHA1", str + "$SHA1Mac");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + aSN1ObjectIdentifier, "PBEWITHHMACSHA1");
            provider.addAlgorithm("Alg.Alias.Mac." + aSN1ObjectIdentifier, "PBEWITHHMACSHA");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA1", str + "$PBEWithMacKeyFactory");
        }
    }
}
