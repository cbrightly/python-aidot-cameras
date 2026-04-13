package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.kdf.DHKEKGenerator;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.jcajce.spec.UserKeyingMaterialSpec;

public class KeyAgreementSpi extends BaseAgreementSpi {
    private static final BigInteger i = BigInteger.valueOf(1);
    private static final BigInteger j = BigInteger.valueOf(2);
    private BigInteger k;
    private BigInteger l;
    private BigInteger m;
    private BigInteger n;

    public KeyAgreementSpi() {
        super("Diffie-Hellman", (DerivationFunction) null);
    }

    public KeyAgreementSpi(String kaAlgorithm, DerivationFunction kdf) {
        super(kaAlgorithm, kdf);
    }

    /* access modifiers changed from: protected */
    public byte[] e(BigInteger r) {
        int expectedLength = (this.l.bitLength() + 7) / 8;
        byte[] tmp = r.toByteArray();
        if (tmp.length == expectedLength) {
            return tmp;
        }
        if (tmp[0] == 0 && tmp.length == expectedLength + 1) {
            byte[] rv = new byte[(tmp.length - 1)];
            System.arraycopy(tmp, 1, rv, 0, rv.length);
            return rv;
        }
        byte[] rv2 = new byte[expectedLength];
        System.arraycopy(tmp, 0, rv2, rv2.length - tmp.length, tmp.length);
        return rv2;
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean lastPhase) {
        if (this.k == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        } else if (key instanceof DHPublicKey) {
            DHPublicKey pubKey = (DHPublicKey) key;
            if (!pubKey.getParams().getG().equals(this.m) || !pubKey.getParams().getP().equals(this.l)) {
                throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
            }
            BigInteger peerY = ((DHPublicKey) key).getY();
            if (peerY != null && peerY.compareTo(j) >= 0) {
                BigInteger bigInteger = this.l;
                BigInteger bigInteger2 = i;
                if (peerY.compareTo(bigInteger.subtract(bigInteger2)) < 0) {
                    BigInteger modPow = peerY.modPow(this.k, this.l);
                    this.n = modPow;
                    if (modPow.compareTo(bigInteger2) == 0) {
                        throw new InvalidKeyException("Shared key can't be 1");
                    } else if (lastPhase) {
                        return null;
                    } else {
                        return new BCDHPublicKey(this.n, pubKey.getParams());
                    }
                }
            }
            throw new InvalidKeyException("Invalid DH PublicKey");
        } else {
            throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() {
        if (this.k != null) {
            return super.engineGenerateSecret();
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] sharedSecret, int offset) {
        if (this.k != null) {
            return super.engineGenerateSecret(sharedSecret, offset);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(String algorithm) {
        if (this.k != null) {
            byte[] res = e(this.n);
            if (algorithm.equals("TlsPremasterSecret")) {
                return new SecretKeySpec(BaseAgreementSpi.d(res), algorithm);
            }
            return super.engineGenerateSecret(algorithm);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec params, SecureRandom random) {
        if (key instanceof DHPrivateKey) {
            DHPrivateKey privKey = (DHPrivateKey) key;
            if (params == null) {
                this.l = privKey.getParams().getP();
                this.m = privKey.getParams().getG();
            } else if (params instanceof DHParameterSpec) {
                DHParameterSpec p = (DHParameterSpec) params;
                this.l = p.getP();
                this.m = p.getG();
            } else if (params instanceof UserKeyingMaterialSpec) {
                this.l = privKey.getParams().getP();
                this.m = privKey.getParams().getG();
                this.h = ((UserKeyingMaterialSpec) params).a();
            } else {
                throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
            }
            BigInteger x = privKey.getX();
            this.n = x;
            this.k = x;
            return;
        }
        throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom random) {
        if (key instanceof DHPrivateKey) {
            DHPrivateKey privKey = (DHPrivateKey) key;
            this.l = privKey.getParams().getP();
            this.m = privKey.getParams().getG();
            BigInteger x = privKey.getX();
            this.n = x;
            this.k = x;
            return;
        }
        throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
    }

    /* access modifiers changed from: protected */
    public byte[] a() {
        return e(this.n);
    }

    public static class DHwithRFC2631KDF extends KeyAgreementSpi {
        public DHwithRFC2631KDF() {
            super("DHwithRFC2631KDF", new DHKEKGenerator(DigestFactory.b()));
        }
    }
}
