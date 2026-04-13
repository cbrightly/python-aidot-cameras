package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.mceliece.McElieceCipher;
import org.spongycastle.pqc.crypto.mceliece.McElieceKeyParameters;
import org.spongycastle.pqc.jcajce.provider.util.AsymmetricBlockCipher;

public class McEliecePKCSCipherSpi extends AsymmetricBlockCipher implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private McElieceCipher x;

    public McEliecePKCSCipherSpi(McElieceCipher cipher) {
        this.x = cipher;
    }

    /* access modifiers changed from: protected */
    public void o(Key key, AlgorithmParameterSpec params, SecureRandom sr) {
        this.x.d(true, new ParametersWithRandom(McElieceKeysToParams.generatePublicKeyParameter((PublicKey) key), sr));
        McElieceCipher mcElieceCipher = this.x;
        this.f = mcElieceCipher.e;
        this.q = mcElieceCipher.f;
    }

    /* access modifiers changed from: protected */
    public void n(Key key, AlgorithmParameterSpec params) {
        this.x.d(false, McElieceKeysToParams.generatePrivateKeyParameter((PrivateKey) key));
        McElieceCipher mcElieceCipher = this.x;
        this.f = mcElieceCipher.e;
        this.q = mcElieceCipher.f;
    }

    /* access modifiers changed from: protected */
    public byte[] q(byte[] input) {
        try {
            return this.x.h(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public byte[] p(byte[] input) {
        try {
            return this.x.g(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int e(Key key) {
        McElieceKeyParameters mcElieceKeyParameters;
        if (key instanceof PublicKey) {
            mcElieceKeyParameters = (McElieceKeyParameters) McElieceKeysToParams.generatePublicKeyParameter((PublicKey) key);
        } else {
            mcElieceKeyParameters = (McElieceKeyParameters) McElieceKeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        }
        return this.x.c(mcElieceKeyParameters);
    }

    public static class McEliecePKCS extends McEliecePKCSCipherSpi {
        public McEliecePKCS() {
            super(new McElieceCipher());
        }
    }
}
