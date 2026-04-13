package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.digests.SHA512tDigest;
import org.spongycastle.pqc.crypto.sphincs.SPHINCS256KeyGenerationParameters;
import org.spongycastle.pqc.crypto.sphincs.SPHINCS256KeyPairGenerator;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.SPHINCS256KeyGenParameterSpec;

public class Sphincs256KeyPairGeneratorSpi extends KeyPairGenerator {
    ASN1ObjectIdentifier a = NISTObjectIdentifiers.h;
    SPHINCS256KeyGenerationParameters b;
    SPHINCS256KeyPairGenerator c = new SPHINCS256KeyPairGenerator();
    SecureRandom d = new SecureRandom();
    boolean e = false;

    public Sphincs256KeyPairGeneratorSpi() {
        super("SPHINCS256");
    }

    public void initialize(int strength, SecureRandom random) {
        throw new IllegalArgumentException("use AlgorithmParameterSpec");
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof SPHINCS256KeyGenParameterSpec) {
            SPHINCS256KeyGenParameterSpec sphincsParams = (SPHINCS256KeyGenParameterSpec) params;
            if (sphincsParams.a().equals("SHA512-256")) {
                this.a = NISTObjectIdentifiers.h;
                this.b = new SPHINCS256KeyGenerationParameters(random, new SHA512tDigest(256));
            } else if (sphincsParams.a().equals("SHA3-256")) {
                this.a = NISTObjectIdentifiers.j;
                this.b = new SPHINCS256KeyGenerationParameters(random, new SHA3Digest(256));
            }
            this.c.b(this.b);
            this.e = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a SPHINCS256KeyGenParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.e) {
            SPHINCS256KeyGenerationParameters sPHINCS256KeyGenerationParameters = new SPHINCS256KeyGenerationParameters(this.d, new SHA512tDigest(256));
            this.b = sPHINCS256KeyGenerationParameters;
            this.c.b(sPHINCS256KeyGenerationParameters);
            this.e = true;
        }
        AsymmetricCipherKeyPair pair = this.c.a();
        return new KeyPair(new BCSphincs256PublicKey(this.a, (SPHINCSPublicKeyParameters) pair.b()), new BCSphincs256PrivateKey(this.a, (SPHINCSPrivateKeyParameters) pair.a()));
    }
}
