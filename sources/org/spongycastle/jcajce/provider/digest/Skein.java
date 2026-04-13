package org.spongycastle.jcajce.provider.digest;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.digests.SkeinDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.macs.SkeinMac;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;

public class Skein {
    private Skein() {
    }

    public static class DigestSkein256 extends BCMessageDigest implements Cloneable {
        public DigestSkein256(int outputSize) {
            super(new SkeinDigest(256, outputSize));
        }

        public Object clone() {
            BCMessageDigest d = (BCMessageDigest) super.clone();
            d.c = new SkeinDigest((SkeinDigest) this.c);
            return d;
        }
    }

    public static class Digest_256_128 extends DigestSkein256 {
        public Digest_256_128() {
            super(128);
        }
    }

    public static class Digest_256_160 extends DigestSkein256 {
        public Digest_256_160() {
            super(Opcodes.IF_ICMPNE);
        }
    }

    public static class Digest_256_224 extends DigestSkein256 {
        public Digest_256_224() {
            super(224);
        }
    }

    public static class Digest_256_256 extends DigestSkein256 {
        public Digest_256_256() {
            super(256);
        }
    }

    public static class DigestSkein512 extends BCMessageDigest implements Cloneable {
        public DigestSkein512(int outputSize) {
            super(new SkeinDigest(512, outputSize));
        }

        public Object clone() {
            BCMessageDigest d = (BCMessageDigest) super.clone();
            d.c = new SkeinDigest((SkeinDigest) this.c);
            return d;
        }
    }

    public static class Digest_512_128 extends DigestSkein512 {
        public Digest_512_128() {
            super(128);
        }
    }

    public static class Digest_512_160 extends DigestSkein512 {
        public Digest_512_160() {
            super(Opcodes.IF_ICMPNE);
        }
    }

    public static class Digest_512_224 extends DigestSkein512 {
        public Digest_512_224() {
            super(224);
        }
    }

    public static class Digest_512_256 extends DigestSkein512 {
        public Digest_512_256() {
            super(256);
        }
    }

    public static class Digest_512_384 extends DigestSkein512 {
        public Digest_512_384() {
            super(384);
        }
    }

    public static class Digest_512_512 extends DigestSkein512 {
        public Digest_512_512() {
            super(512);
        }
    }

    public static class DigestSkein1024 extends BCMessageDigest implements Cloneable {
        public DigestSkein1024(int outputSize) {
            super(new SkeinDigest(1024, outputSize));
        }

        public Object clone() {
            BCMessageDigest d = (BCMessageDigest) super.clone();
            d.c = new SkeinDigest((SkeinDigest) this.c);
            return d;
        }
    }

    public static class Digest_1024_384 extends DigestSkein1024 {
        public Digest_1024_384() {
            super(384);
        }
    }

    public static class Digest_1024_512 extends DigestSkein1024 {
        public Digest_1024_512() {
            super(512);
        }
    }

    public static class Digest_1024_1024 extends DigestSkein1024 {
        public Digest_1024_1024() {
            super(1024);
        }
    }

    public static class HashMac_256_128 extends BaseMac {
        public HashMac_256_128() {
            super(new HMac(new SkeinDigest(256, 128)));
        }
    }

    public static class HashMac_256_160 extends BaseMac {
        public HashMac_256_160() {
            super(new HMac(new SkeinDigest(256, Opcodes.IF_ICMPNE)));
        }
    }

    public static class HashMac_256_224 extends BaseMac {
        public HashMac_256_224() {
            super(new HMac(new SkeinDigest(256, 224)));
        }
    }

    public static class HashMac_256_256 extends BaseMac {
        public HashMac_256_256() {
            super(new HMac(new SkeinDigest(256, 256)));
        }
    }

    public static class HashMac_512_128 extends BaseMac {
        public HashMac_512_128() {
            super(new HMac(new SkeinDigest(512, 128)));
        }
    }

    public static class HashMac_512_160 extends BaseMac {
        public HashMac_512_160() {
            super(new HMac(new SkeinDigest(512, Opcodes.IF_ICMPNE)));
        }
    }

    public static class HashMac_512_224 extends BaseMac {
        public HashMac_512_224() {
            super(new HMac(new SkeinDigest(512, 224)));
        }
    }

    public static class HashMac_512_256 extends BaseMac {
        public HashMac_512_256() {
            super(new HMac(new SkeinDigest(512, 256)));
        }
    }

    public static class HashMac_512_384 extends BaseMac {
        public HashMac_512_384() {
            super(new HMac(new SkeinDigest(512, 384)));
        }
    }

    public static class HashMac_512_512 extends BaseMac {
        public HashMac_512_512() {
            super(new HMac(new SkeinDigest(512, 512)));
        }
    }

    public static class HashMac_1024_384 extends BaseMac {
        public HashMac_1024_384() {
            super(new HMac(new SkeinDigest(1024, 384)));
        }
    }

    public static class HashMac_1024_512 extends BaseMac {
        public HashMac_1024_512() {
            super(new HMac(new SkeinDigest(1024, 512)));
        }
    }

    public static class HashMac_1024_1024 extends BaseMac {
        public HashMac_1024_1024() {
            super(new HMac(new SkeinDigest(1024, 1024)));
        }
    }

    public static class HMacKeyGenerator_256_128 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_128() {
            super("HMACSkein-256-128", 128, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_160 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_160() {
            super("HMACSkein-256-160", Opcodes.IF_ICMPNE, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_224 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_224() {
            super("HMACSkein-256-224", 224, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_256 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_256() {
            super("HMACSkein-256-256", 256, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_128 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_128() {
            super("HMACSkein-512-128", 128, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_160 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_160() {
            super("HMACSkein-512-160", Opcodes.IF_ICMPNE, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_224 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_224() {
            super("HMACSkein-512-224", 224, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_256 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_256() {
            super("HMACSkein-512-256", 256, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_384 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_384() {
            super("HMACSkein-512-384", 384, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_512 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_512() {
            super("HMACSkein-512-512", 512, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_1024_384 extends BaseKeyGenerator {
        public HMacKeyGenerator_1024_384() {
            super("HMACSkein-1024-384", 384, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_1024_512 extends BaseKeyGenerator {
        public HMacKeyGenerator_1024_512() {
            super("HMACSkein-1024-512", 512, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_1024_1024 extends BaseKeyGenerator {
        public HMacKeyGenerator_1024_1024() {
            super("HMACSkein-1024-1024", 1024, new CipherKeyGenerator());
        }
    }

    public static class SkeinMac_256_128 extends BaseMac {
        public SkeinMac_256_128() {
            super(new SkeinMac(256, 128));
        }
    }

    public static class SkeinMac_256_160 extends BaseMac {
        public SkeinMac_256_160() {
            super(new SkeinMac(256, Opcodes.IF_ICMPNE));
        }
    }

    public static class SkeinMac_256_224 extends BaseMac {
        public SkeinMac_256_224() {
            super(new SkeinMac(256, 224));
        }
    }

    public static class SkeinMac_256_256 extends BaseMac {
        public SkeinMac_256_256() {
            super(new SkeinMac(256, 256));
        }
    }

    public static class SkeinMac_512_128 extends BaseMac {
        public SkeinMac_512_128() {
            super(new SkeinMac(512, 128));
        }
    }

    public static class SkeinMac_512_160 extends BaseMac {
        public SkeinMac_512_160() {
            super(new SkeinMac(512, Opcodes.IF_ICMPNE));
        }
    }

    public static class SkeinMac_512_224 extends BaseMac {
        public SkeinMac_512_224() {
            super(new SkeinMac(512, 224));
        }
    }

    public static class SkeinMac_512_256 extends BaseMac {
        public SkeinMac_512_256() {
            super(new SkeinMac(512, 256));
        }
    }

    public static class SkeinMac_512_384 extends BaseMac {
        public SkeinMac_512_384() {
            super(new SkeinMac(512, 384));
        }
    }

    public static class SkeinMac_512_512 extends BaseMac {
        public SkeinMac_512_512() {
            super(new SkeinMac(512, 512));
        }
    }

    public static class SkeinMac_1024_384 extends BaseMac {
        public SkeinMac_1024_384() {
            super(new SkeinMac(1024, 384));
        }
    }

    public static class SkeinMac_1024_512 extends BaseMac {
        public SkeinMac_1024_512() {
            super(new SkeinMac(1024, 512));
        }
    }

    public static class SkeinMac_1024_1024 extends BaseMac {
        public SkeinMac_1024_1024() {
            super(new SkeinMac(1024, 1024));
        }
    }

    public static class SkeinMacKeyGenerator_256_128 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_128() {
            super("Skein-MAC-256-128", 128, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_160 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_160() {
            super("Skein-MAC-256-160", Opcodes.IF_ICMPNE, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_224 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_224() {
            super("Skein-MAC-256-224", 224, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_256 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_256() {
            super("Skein-MAC-256-256", 256, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_128 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_128() {
            super("Skein-MAC-512-128", 128, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_160 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_160() {
            super("Skein-MAC-512-160", Opcodes.IF_ICMPNE, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_224 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_224() {
            super("Skein-MAC-512-224", 224, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_256 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_256() {
            super("Skein-MAC-512-256", 256, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_384 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_384() {
            super("Skein-MAC-512-384", 384, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_512 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_512() {
            super("Skein-MAC-512-512", 512, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_1024_384 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_1024_384() {
            super("Skein-MAC-1024-384", 384, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_1024_512 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_1024_512() {
            super("Skein-MAC-1024-512", 512, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_1024_1024 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_1024_1024() {
            super("Skein-MAC-1024-1024", 1024, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = Skein.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$Digest_256_128");
            provider.addAlgorithm("MessageDigest.Skein-256-128", sb.toString());
            provider.addAlgorithm("MessageDigest.Skein-256-160", str + "$Digest_256_160");
            provider.addAlgorithm("MessageDigest.Skein-256-224", str + "$Digest_256_224");
            provider.addAlgorithm("MessageDigest.Skein-256-256", str + "$Digest_256_256");
            provider.addAlgorithm("MessageDigest.Skein-512-128", str + "$Digest_512_128");
            provider.addAlgorithm("MessageDigest.Skein-512-160", str + "$Digest_512_160");
            provider.addAlgorithm("MessageDigest.Skein-512-224", str + "$Digest_512_224");
            provider.addAlgorithm("MessageDigest.Skein-512-256", str + "$Digest_512_256");
            provider.addAlgorithm("MessageDigest.Skein-512-384", str + "$Digest_512_384");
            provider.addAlgorithm("MessageDigest.Skein-512-512", str + "$Digest_512_512");
            provider.addAlgorithm("MessageDigest.Skein-1024-384", str + "$Digest_1024_384");
            provider.addAlgorithm("MessageDigest.Skein-1024-512", str + "$Digest_1024_512");
            provider.addAlgorithm("MessageDigest.Skein-1024-1024", str + "$Digest_1024_1024");
            b(provider, "Skein-256-128", str + "$HashMac_256_128", str + "$HMacKeyGenerator_256_128");
            b(provider, "Skein-256-160", str + "$HashMac_256_160", str + "$HMacKeyGenerator_256_160");
            b(provider, "Skein-256-224", str + "$HashMac_256_224", str + "$HMacKeyGenerator_256_224");
            b(provider, "Skein-256-256", str + "$HashMac_256_256", str + "$HMacKeyGenerator_256_256");
            b(provider, "Skein-512-128", str + "$HashMac_512_128", str + "$HMacKeyGenerator_512_128");
            b(provider, "Skein-512-160", str + "$HashMac_512_160", str + "$HMacKeyGenerator_512_160");
            b(provider, "Skein-512-224", str + "$HashMac_512_224", str + "$HMacKeyGenerator_512_224");
            b(provider, "Skein-512-256", str + "$HashMac_512_256", str + "$HMacKeyGenerator_512_256");
            b(provider, "Skein-512-384", str + "$HashMac_512_384", str + "$HMacKeyGenerator_512_384");
            b(provider, "Skein-512-512", str + "$HashMac_512_512", str + "$HMacKeyGenerator_512_512");
            b(provider, "Skein-1024-384", str + "$HashMac_1024_384", str + "$HMacKeyGenerator_1024_384");
            b(provider, "Skein-1024-512", str + "$HashMac_1024_512", str + "$HMacKeyGenerator_1024_512");
            b(provider, "Skein-1024-1024", str + "$HashMac_1024_1024", str + "$HMacKeyGenerator_1024_1024");
            d(provider, 256, 128);
            d(provider, 256, Opcodes.IF_ICMPNE);
            d(provider, 256, 224);
            d(provider, 256, 256);
            d(provider, 512, 128);
            d(provider, 512, Opcodes.IF_ICMPNE);
            d(provider, 512, 224);
            d(provider, 512, 256);
            d(provider, 512, 384);
            d(provider, 512, 512);
            d(provider, 1024, 384);
            d(provider, 1024, 512);
            d(provider, 1024, 1024);
        }

        private void d(ConfigurableProvider provider, int blockSize, int outputSize) {
            String mainName = "Skein-MAC-" + blockSize + "-" + outputSize;
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$SkeinMac_");
            sb.append(blockSize);
            sb.append("_");
            sb.append(outputSize);
            provider.addAlgorithm("Mac." + mainName, sb.toString());
            provider.addAlgorithm("Alg.Alias.Mac.Skein-MAC" + blockSize + "/" + outputSize, mainName);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("KeyGenerator.");
            sb2.append(mainName);
            provider.addAlgorithm(sb2.toString(), str + "$SkeinMacKeyGenerator_" + blockSize + "_" + outputSize);
            provider.addAlgorithm("Alg.Alias.KeyGenerator.Skein-MAC" + blockSize + "/" + outputSize, mainName);
        }
    }
}
