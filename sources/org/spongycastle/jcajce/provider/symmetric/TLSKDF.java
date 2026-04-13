package org.spongycastle.jcajce.provider.symmetric;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.spec.TLSKeyMaterialSpec;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class TLSKDF {

    public static class TLSKeyMaterialFactory extends BaseSecretKeyFactory {
        protected TLSKeyMaterialFactory(String algName) {
            super(algName, (ASN1ObjectIdentifier) null);
        }
    }

    public static final class TLS10 extends TLSKeyMaterialFactory {
        public TLS10() {
            super("TLS10KDF");
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof TLSKeyMaterialSpec) {
                return new SecretKeySpec(TLSKDF.a((TLSKeyMaterialSpec) keySpec), this.c);
            }
            throw new InvalidKeySpecException("Invalid KeySpec");
        }
    }

    public static final class TLS11 extends TLSKeyMaterialFactory {
        public TLS11() {
            super("TLS11KDF");
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof TLSKeyMaterialSpec) {
                return new SecretKeySpec(TLSKDF.a((TLSKeyMaterialSpec) keySpec), this.c);
            }
            throw new InvalidKeySpecException("Invalid KeySpec");
        }
    }

    /* access modifiers changed from: private */
    public static byte[] a(TLSKeyMaterialSpec parameters) {
        Mac md5Hmac = new HMac(DigestFactory.a());
        Mac sha1HMac = new HMac(DigestFactory.b());
        byte[] labelSeed = Arrays.r(Strings.f(parameters.a()), parameters.d());
        byte[] secret = parameters.c();
        int s_half = (secret.length + 1) / 2;
        byte[] s1 = new byte[s_half];
        byte[] s2 = new byte[s_half];
        System.arraycopy(secret, 0, s1, 0, s_half);
        System.arraycopy(secret, secret.length - s_half, s2, 0, s_half);
        int size = parameters.b();
        byte[] b1 = new byte[size];
        byte[] b2 = new byte[size];
        d(md5Hmac, s1, labelSeed, b1);
        d(sha1HMac, s2, labelSeed, b2);
        for (int i = 0; i < size; i++) {
            b1[i] = (byte) (b1[i] ^ b2[i]);
        }
        return b1;
    }

    public static class TLS12 extends TLSKeyMaterialFactory {
        private final Mac f;

        protected TLS12(String algName, Mac prf) {
            super(algName);
            this.f = prf;
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof TLSKeyMaterialSpec) {
                return new SecretKeySpec(a((TLSKeyMaterialSpec) keySpec, this.f), this.c);
            }
            throw new InvalidKeySpecException("Invalid KeySpec");
        }

        private byte[] a(TLSKeyMaterialSpec parameters, Mac prf) {
            byte[] labelSeed = Arrays.r(Strings.f(parameters.a()), parameters.d());
            byte[] secret = parameters.c();
            byte[] buf = new byte[parameters.b()];
            TLSKDF.d(prf, secret, labelSeed, buf);
            return buf;
        }
    }

    public static final class TLS12withSHA256 extends TLS12 {
        public TLS12withSHA256() {
            super("TLS12withSHA256KDF", new HMac(new SHA256Digest()));
        }
    }

    public static final class TLS12withSHA384 extends TLS12 {
        public TLS12withSHA384() {
            super("TLS12withSHA384KDF", new HMac(new SHA384Digest()));
        }
    }

    public static final class TLS12withSHA512 extends TLS12 {
        public TLS12withSHA512() {
            super("TLS12withSHA512KDF", new HMac(new SHA512Digest()));
        }
    }

    /* access modifiers changed from: private */
    public static void d(Mac mac, byte[] secret, byte[] seed, byte[] out) {
        mac.a(new KeyParameter(secret));
        byte[] a = seed;
        int size = mac.e();
        int iterations = ((out.length + size) - 1) / size;
        byte[] buf = new byte[mac.e()];
        byte[] buf2 = new byte[mac.e()];
        for (int i = 0; i < iterations; i++) {
            mac.update(a, 0, a.length);
            mac.c(buf, 0);
            a = buf;
            mac.update(a, 0, a.length);
            mac.update(seed, 0, seed.length);
            mac.c(buf2, 0);
            System.arraycopy(buf2, 0, out, size * i, Math.min(size, out.length - (size * i)));
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = TLSKDF.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$TLS10");
            provider.addAlgorithm("SecretKeyFactory.TLS10KDF", sb.toString());
            provider.addAlgorithm("SecretKeyFactory.TLS11KDF", str + "$TLS11");
            provider.addAlgorithm("SecretKeyFactory.TLS12WITHSHA256KDF", str + "$TLS12withSHA256");
            provider.addAlgorithm("SecretKeyFactory.TLS12WITHSHA384KDF", str + "$TLS12withSHA384");
            provider.addAlgorithm("SecretKeyFactory.TLS12WITHSHA512KDF", str + "$TLS12withSHA512");
        }
    }
}
