package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.GOST3411Digest;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.TigerDigest;
import org.spongycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.util.DigestFactory;

public interface PBE {

    public static class Util {
        private static PBEParametersGenerator b(int type, int hash) {
            if (type == 0 || type == 4) {
                switch (hash) {
                    case 0:
                        return new PKCS5S1ParametersGenerator(DigestFactory.a());
                    case 1:
                        return new PKCS5S1ParametersGenerator(DigestFactory.b());
                    case 5:
                        return new PKCS5S1ParametersGenerator(new MD2Digest());
                    default:
                        throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
                }
            } else if (type == 1 || type == 5) {
                switch (hash) {
                    case 0:
                        return new PKCS5S2ParametersGenerator(DigestFactory.a());
                    case 1:
                        return new PKCS5S2ParametersGenerator(DigestFactory.b());
                    case 2:
                        return new PKCS5S2ParametersGenerator(new RIPEMD160Digest());
                    case 3:
                        return new PKCS5S2ParametersGenerator(new TigerDigest());
                    case 4:
                        return new PKCS5S2ParametersGenerator(DigestFactory.d());
                    case 5:
                        return new PKCS5S2ParametersGenerator(new MD2Digest());
                    case 6:
                        return new PKCS5S2ParametersGenerator(new GOST3411Digest());
                    case 7:
                        return new PKCS5S2ParametersGenerator(DigestFactory.c());
                    case 8:
                        return new PKCS5S2ParametersGenerator(DigestFactory.e());
                    case 9:
                        return new PKCS5S2ParametersGenerator(DigestFactory.j());
                    case 10:
                        return new PKCS5S2ParametersGenerator(DigestFactory.f());
                    case 11:
                        return new PKCS5S2ParametersGenerator(DigestFactory.g());
                    case 12:
                        return new PKCS5S2ParametersGenerator(DigestFactory.h());
                    case 13:
                        return new PKCS5S2ParametersGenerator(DigestFactory.i());
                    default:
                        throw new IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
                }
            } else if (type != 2) {
                return new OpenSSLPBEParametersGenerator();
            } else {
                switch (hash) {
                    case 0:
                        return new PKCS12ParametersGenerator(DigestFactory.a());
                    case 1:
                        return new PKCS12ParametersGenerator(DigestFactory.b());
                    case 2:
                        return new PKCS12ParametersGenerator(new RIPEMD160Digest());
                    case 3:
                        return new PKCS12ParametersGenerator(new TigerDigest());
                    case 4:
                        return new PKCS12ParametersGenerator(DigestFactory.d());
                    case 5:
                        return new PKCS12ParametersGenerator(new MD2Digest());
                    case 6:
                        return new PKCS12ParametersGenerator(new GOST3411Digest());
                    case 7:
                        return new PKCS12ParametersGenerator(DigestFactory.c());
                    case 8:
                        return new PKCS12ParametersGenerator(DigestFactory.e());
                    case 9:
                        return new PKCS12ParametersGenerator(DigestFactory.j());
                    default:
                        throw new IllegalStateException("unknown digest scheme for PBE encryption.");
                }
            }
        }

        public static CipherParameters h(byte[] pbeKey, int scheme, int digest, int keySize, int ivSize, AlgorithmParameterSpec spec, String targetAlgorithm) {
            CipherParameters param;
            if (spec == null || !(spec instanceof PBEParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pbeParam = (PBEParameterSpec) spec;
            PBEParametersGenerator generator = b(scheme, digest);
            generator.g(pbeKey, pbeParam.getSalt(), pbeParam.getIterationCount());
            if (ivSize != 0) {
                param = generator.f(keySize, ivSize);
            } else {
                param = generator.e(keySize);
            }
            if (targetAlgorithm.startsWith("DES")) {
                if (param instanceof ParametersWithIV) {
                    DESParameters.c(((KeyParameter) ((ParametersWithIV) param).b()).a());
                } else {
                    DESParameters.c(((KeyParameter) param).a());
                }
            }
            return param;
        }

        public static CipherParameters g(BCPBEKey pbeKey, AlgorithmParameterSpec spec, String targetAlgorithm) {
            CipherParameters param;
            if (spec == null || !(spec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pbeParam = (PBEParameterSpec) spec;
            PBEParametersGenerator generator = b(pbeKey.getType(), pbeKey.getDigest());
            byte[] key = pbeKey.getEncoded();
            if (pbeKey.shouldTryWrongPKCS12()) {
                key = new byte[2];
            }
            generator.g(key, pbeParam.getSalt(), pbeParam.getIterationCount());
            if (pbeKey.getIvSize() != 0) {
                param = generator.f(pbeKey.getKeySize(), pbeKey.getIvSize());
            } else {
                param = generator.e(pbeKey.getKeySize());
            }
            if (targetAlgorithm.startsWith("DES")) {
                if (param instanceof ParametersWithIV) {
                    DESParameters.c(((KeyParameter) ((ParametersWithIV) param).b()).a());
                } else {
                    DESParameters.c(((KeyParameter) param).a());
                }
            }
            return param;
        }

        public static CipherParameters e(BCPBEKey pbeKey, AlgorithmParameterSpec spec) {
            if (spec == null || !(spec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pbeParam = (PBEParameterSpec) spec;
            PBEParametersGenerator generator = b(pbeKey.getType(), pbeKey.getDigest());
            generator.g(pbeKey.getEncoded(), pbeParam.getSalt(), pbeParam.getIterationCount());
            return generator.d(pbeKey.getKeySize());
        }

        public static CipherParameters d(PBEKeySpec keySpec, int type, int hash, int keySize) {
            PBEParametersGenerator generator = b(type, hash);
            byte[] key = a(type, keySpec);
            generator.g(key, keySpec.getSalt(), keySpec.getIterationCount());
            CipherParameters param = generator.d(keySize);
            for (int i = 0; i != key.length; i++) {
                key[i] = 0;
            }
            return param;
        }

        public static CipherParameters f(PBEKeySpec keySpec, int type, int hash, int keySize, int ivSize) {
            CipherParameters param;
            PBEParametersGenerator generator = b(type, hash);
            byte[] key = a(type, keySpec);
            generator.g(key, keySpec.getSalt(), keySpec.getIterationCount());
            if (ivSize != 0) {
                param = generator.f(keySize, ivSize);
            } else {
                param = generator.e(keySize);
            }
            for (int i = 0; i != key.length; i++) {
                key[i] = 0;
            }
            return param;
        }

        public static CipherParameters c(SecretKey key, int type, int hash, int keySize, PBEParameterSpec pbeSpec) {
            PBEParametersGenerator generator = b(type, hash);
            byte[] keyBytes = key.getEncoded();
            generator.g(key.getEncoded(), pbeSpec.getSalt(), pbeSpec.getIterationCount());
            CipherParameters param = generator.d(keySize);
            for (int i = 0; i != keyBytes.length; i++) {
                keyBytes[i] = 0;
            }
            return param;
        }

        private static byte[] a(int type, PBEKeySpec keySpec) {
            if (type == 2) {
                return PBEParametersGenerator.a(keySpec.getPassword());
            }
            if (type == 5 || type == 4) {
                return PBEParametersGenerator.c(keySpec.getPassword());
            }
            return PBEParametersGenerator.b(keySpec.getPassword());
        }
    }
}
