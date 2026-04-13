package org.spongycastle.jcajce.provider.symmetric;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RC2CBCParameter;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.RC2Engine;
import org.spongycastle.crypto.engines.RC2WrapEngine;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.util.Arrays;

public final class RC2 {
    private RC2() {
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new RC2Engine());
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new RC2Engine()), 64);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new RC2WrapEngine());
        }
    }

    public static class CBCMAC extends BaseMac {
        public CBCMAC() {
            super(new CBCBlockCipherMac(new RC2Engine()));
        }
    }

    public static class CFB8MAC extends BaseMac {
        public CFB8MAC() {
            super(new CFBBlockCipherMac(new RC2Engine()));
        }
    }

    public static class PBEWithSHA1KeyFactory extends PBESecretKeyFactory {
        public PBEWithSHA1KeyFactory() {
            super("PBEwithSHA1andRC2", PKCSObjectIdentifiers.i0, true, 0, 1, 64, 64);
        }
    }

    public static class PBEWithSHAAnd128BitKeyFactory extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitKeyFactory() {
            super("PBEwithSHAand128BitRC2-CBC", PKCSObjectIdentifiers.v2, true, 2, 1, 128, 64);
        }
    }

    public static class PBEWithSHAAnd40BitKeyFactory extends PBESecretKeyFactory {
        public PBEWithSHAAnd40BitKeyFactory() {
            super("PBEwithSHAand40BitRC2-CBC", PKCSObjectIdentifiers.w2, true, 2, 1, 40, 64);
        }
    }

    public static class PBEWithMD5AndRC2 extends BaseBlockCipher {
        public PBEWithMD5AndRC2() {
            super(new CBCBlockCipher(new RC2Engine()), 0, 0, 64, 8);
        }
    }

    public static class PBEWithSHA1AndRC2 extends BaseBlockCipher {
        public PBEWithSHA1AndRC2() {
            super(new CBCBlockCipher(new RC2Engine()), 0, 1, 64, 8);
        }
    }

    public static class PBEWithSHAAnd128BitRC2 extends BaseBlockCipher {
        public PBEWithSHAAnd128BitRC2() {
            super(new CBCBlockCipher(new RC2Engine()), 2, 1, 128, 8);
        }
    }

    public static class PBEWithSHAAnd40BitRC2 extends BaseBlockCipher {
        public PBEWithSHAAnd40BitRC2() {
            super(new CBCBlockCipher(new RC2Engine()), 2, 1, 40, 8);
        }
    }

    public static class PBEWithMD2KeyFactory extends PBESecretKeyFactory {
        public PBEWithMD2KeyFactory() {
            super("PBEwithMD2andRC2", PKCSObjectIdentifiers.e0, true, 0, 5, 64, 64);
        }
    }

    public static class PBEWithMD5KeyFactory extends PBESecretKeyFactory {
        public PBEWithMD5KeyFactory() {
            super("PBEwithMD5andRC2", PKCSObjectIdentifiers.g0, true, 0, 0, 64, 64);
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        RC2ParameterSpec d = null;

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random) {
            if (genParamSpec instanceof RC2ParameterSpec) {
                this.d = (RC2ParameterSpec) genParamSpec;
                return;
            }
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC2 parameter generation.");
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            if (this.d == null) {
                byte[] iv = new byte[8];
                if (this.b == null) {
                    this.b = new SecureRandom();
                }
                this.b.nextBytes(iv);
                try {
                    AlgorithmParameters params = a("RC2");
                    params.init(new IvParameterSpec(iv));
                    return params;
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            } else {
                try {
                    AlgorithmParameters params2 = a("RC2");
                    params2.init(this.d);
                    return params2;
                } catch (Exception e2) {
                    throw new RuntimeException(e2.getMessage());
                }
            }
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("RC2", 128, new CipherKeyGenerator());
        }
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        private static final short[] ekb = {93, 190, 155, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_ADPCM, 17, 153, 110, 77, 89, 243, 133, 166, 63, 183, 131, 197, 228, 115, 107, 58, 104, 90, 192, 71, 160, 100, 52, 12, 241, 208, 82, 165, 185, 30, 150, 67, 65, 216, 212, 44, 219, 248, 7, 119, 42, 202, 235, 239, 16, 28, 22, 13, 56, 114, 47, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711U, 193, 249, MqttException.REASON_CODE_SUBSCRIBE_FAILED, 196, 109, 174, 48, 61, 206, 32, 99, 254, 230, 26, 199, 184, 80, 232, 36, 23, 252, 37, 111, 187, 106, 163, 68, 83, 217, 162, 1, 171, 188, 182, 31, 152, 238, 154, 167, 45, 79, 158, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_MP3, 172, 224, 198, 73, 70, 41, 244, 148, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711A, 175, 225, 91, 195, 179, 123, 87, 209, 124, 156, 237, 135, 64, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_PCM, 226, 203, 147, 20, 201, 97, 46, 229, 204, 246, 94, 168, 92, 214, 117, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_SPEEX, 98, 149, 88, 105, 118, 161, 74, 181, 85, 9, 120, 51, 130, 215, 221, 121, 245, 27, 11, 222, 38, 33, 40, 116, 4, 151, 86, 223, 60, 240, 55, 57, 220, 255, 6, 164, 234, 66, 8, 218, 180, 113, 176, 207, 18, 122, 78, 250, 108, 29, 132, 0, 200, 127, 145, 69, 170, 43, 194, 177, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G726, 213, 186, 242, 173, 25, 178, 103, 54, 247, 15, 10, 146, 125, 227, 157, 233, 144, 62, 35, 39, 102, 19, 236, 129, 21, 189, 34, 191, 159, 126, 169, 81, 75, 76, 251, 2, 211, 112, 134, 49, 231, 59, 5, 3, 84, 96, 72, 101, 24, 210, 205, 95, 50, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_AAC, 14, 53, 253};
        private static final short[] table = {189, 86, 234, 242, 162, 241, 172, 42, 176, 147, 209, 156, 27, 51, 253, 208, 48, 4, 182, 220, 125, 223, 50, 75, 247, 203, 69, 155, 49, 187, 33, 90, 65, 159, 225, 217, 74, 77, 158, 218, 160, 104, 44, 195, 39, 95, MqttException.REASON_CODE_SUBSCRIBE_FAILED, 54, 62, 238, 251, 149, 26, 254, 206, 168, 52, 169, 19, 240, 166, 63, 216, 12, 120, 36, 175, 35, 82, 193, 103, 23, 245, 102, 144, 231, 232, 7, 184, 96, 72, 230, 30, 83, 243, 146, 164, 114, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_PCM, 8, 21, 110, 134, 0, 132, 250, 244, 127, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711A, 66, 25, 246, 219, 205, 20, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_SPEEX, 80, 18, 186, 60, 6, 78, 236, 179, 53, 17, 161, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_AAC, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_MP3, 43, 148, 153, 183, 113, 116, 211, 228, 191, 58, 222, 150, 14, 188, 10, 237, 119, 252, 55, 107, 3, 121, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G711U, 98, 198, 215, 192, 210, 124, 106, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_ADPCM, 34, 163, 91, 5, 93, 2, 117, 213, 97, 227, 24, AVIOCTRLDEFs.MEDIA_CODEC_AUDIO_G726, 85, 81, 173, 31, 11, 94, 133, 229, 194, 87, 99, 202, 61, 108, 180, 197, 204, 112, 178, 145, 89, 13, 71, 32, 200, 79, 88, 224, 1, 226, 22, 56, 196, 111, 59, 15, 101, 70, 190, 126, 45, 123, 130, 249, 64, 181, 29, 115, 248, 235, 38, 199, 135, 151, 37, 84, 177, 40, 170, 152, 157, 165, 100, 109, 122, 212, 16, 129, 68, 239, 73, 214, 174, 46, 221, 118, 92, 47, 167, 28, 201, 9, 105, 154, 131, 207, 41, 57, 185, 233, 76, 255, 67, 171};
        private byte[] iv;
        private int parameterVersion = 58;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            return Arrays.h(this.iv);
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String format) {
            if (isASN1FormatString(format)) {
                int i = this.parameterVersion;
                if (i == -1) {
                    return new RC2CBCParameter(engineGetEncoded()).getEncoded();
                }
                return new RC2CBCParameter(i, engineGetEncoded()).getEncoded();
            } else if (format.equals("RAW")) {
                return engineGetEncoded();
            } else {
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
            int i;
            if ((paramSpec == RC2ParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) && (i = this.parameterVersion) != -1) {
                if (i < 256) {
                    return new RC2ParameterSpec(ekb[this.parameterVersion], this.iv);
                }
                return new RC2ParameterSpec(this.parameterVersion, this.iv);
            } else if (paramSpec == IvParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
                return new IvParameterSpec(this.iv);
            } else {
                throw new InvalidParameterSpecException("unknown parameter spec passed to RC2 parameters object.");
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec paramSpec) {
            if (paramSpec instanceof IvParameterSpec) {
                this.iv = ((IvParameterSpec) paramSpec).getIV();
            } else if (paramSpec instanceof RC2ParameterSpec) {
                int effKeyBits = ((RC2ParameterSpec) paramSpec).getEffectiveKeyBits();
                if (effKeyBits != -1) {
                    if (effKeyBits < 256) {
                        this.parameterVersion = table[effKeyBits];
                    } else {
                        this.parameterVersion = effKeyBits;
                    }
                }
                this.iv = ((RC2ParameterSpec) paramSpec).getIV();
            } else {
                throw new InvalidParameterSpecException("IvParameterSpec or RC2ParameterSpec required to initialise a RC2 parameters algorithm parameters object");
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params) {
            this.iv = Arrays.h(params);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] params, String format) {
            if (isASN1FormatString(format)) {
                RC2CBCParameter p = RC2CBCParameter.e(ASN1Primitive.h(params));
                if (p.f() != null) {
                    this.parameterVersion = p.f().intValue();
                }
                this.iv = p.getIV();
            } else if (format.equals("RAW")) {
                engineInit(params);
            } else {
                throw new IOException("Unknown parameters format in IV parameters object");
            }
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "RC2 Parameters";
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = RC2.class.getName();

        public void a(ConfigurableProvider provider) {
            StringBuilder sb = new StringBuilder();
            String str = a;
            sb.append(str);
            sb.append("$AlgParamGen");
            provider.addAlgorithm("AlgorithmParameterGenerator.RC2", sb.toString());
            provider.addAlgorithm("AlgorithmParameterGenerator.1.2.840.113549.3.2", str + "$AlgParamGen");
            provider.addAlgorithm("KeyGenerator.RC2", str + "$KeyGenerator");
            provider.addAlgorithm("KeyGenerator.1.2.840.113549.3.2", str + "$KeyGenerator");
            provider.addAlgorithm("AlgorithmParameters.RC2", str + "$AlgParams");
            provider.addAlgorithm("AlgorithmParameters.1.2.840.113549.3.2", str + "$AlgParams");
            provider.addAlgorithm("Cipher.RC2", str + "$ECB");
            provider.addAlgorithm("Cipher.RC2WRAP", str + "$Wrap");
            provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.z2, "RC2WRAP");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.n0;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, str + "$CBC");
            provider.addAlgorithm("Mac.RC2MAC", str + "$CBCMAC");
            provider.addAlgorithm("Alg.Alias.Mac.RC2", "RC2MAC");
            provider.addAlgorithm("Mac.RC2MAC/CFB8", str + "$CFB8MAC");
            provider.addAlgorithm("Alg.Alias.Mac.RC2/CFB8", "RC2MAC/CFB8");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD2ANDRC2-CBC", "PBEWITHMD2ANDRC2");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD5ANDRC2-CBC", "PBEWITHMD5ANDRC2");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDRC2-CBC", "PBEWITHSHA1ANDRC2");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.e0;
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier2, "PBEWITHMD2ANDRC2");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.g0;
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier3, "PBEWITHMD5ANDRC2");
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.i0;
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory", aSN1ObjectIdentifier4, "PBEWITHSHA1ANDRC2");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.5", "PBEWITHSHAAND128BITRC2-CBC");
            provider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.6", "PBEWITHSHAAND40BITRC2-CBC");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHMD2ANDRC2", str + "$PBEWithMD2KeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHMD5ANDRC2", str + "$PBEWithMD5KeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHA1ANDRC2", str + "$PBEWithSHA1KeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITRC2-CBC", str + "$PBEWithSHAAnd128BitKeyFactory");
            provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND40BITRC2-CBC", str + "$PBEWithSHAAnd40BitKeyFactory");
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier2, "PBEWITHMD2ANDRC2");
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier3, "PBEWITHMD5ANDRC2");
            provider.addAlgorithm("Alg.Alias.Cipher", aSN1ObjectIdentifier4, "PBEWITHSHA1ANDRC2");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.5", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.6", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWithSHAAnd3KeyTripleDES", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.v2, "PBEWITHSHAAND128BITRC2-CBC");
            provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.w2, "PBEWITHSHAAND40BITRC2-CBC");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC2-CBC", "PBEWITHSHAAND128BITRC2-CBC");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC2-CBC", "PBEWITHSHAAND40BITRC2-CBC");
            provider.addAlgorithm("Cipher.PBEWITHSHA1ANDRC2", str + "$PBEWithSHA1AndRC2");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAANDRC2-CBC", "PBEWITHSHA1ANDRC2");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDRC2-CBC", "PBEWITHSHA1ANDRC2");
            provider.addAlgorithm("Cipher.PBEWITHSHAAND128BITRC2-CBC", str + "$PBEWithSHAAnd128BitRC2");
            provider.addAlgorithm("Cipher.PBEWITHSHAAND40BITRC2-CBC", str + "$PBEWithSHAAnd40BitRC2");
            provider.addAlgorithm("Cipher.PBEWITHMD5ANDRC2", str + "$PBEWithMD5AndRC2");
            provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHMD5ANDRC2-CBC", "PBEWITHMD5ANDRC2");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1ANDRC2", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC2", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1ANDRC2-CBC", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC2-CBC", "PKCS12PBE");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC2-CBC", "PKCS12PBE");
        }
    }
}
