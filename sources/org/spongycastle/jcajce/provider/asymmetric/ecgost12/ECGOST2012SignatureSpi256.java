package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.GOST3411_2012_256Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.ECGOST3410_2012Signer;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.interfaces.ECKey;
import org.spongycastle.jce.interfaces.ECPublicKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class ECGOST2012SignatureSpi256 extends SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest c = new GOST3411_2012_256Digest();
    private DSA d = new ECGOST3410_2012Signer();
    private int f = 64;
    private int q = (64 / 2);

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        CipherParameters param;
        if (publicKey instanceof ECPublicKey) {
            param = a(publicKey);
        } else {
            try {
                param = ECUtil.e(BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.g(publicKey.getEncoded())));
            } catch (Exception e) {
                throw new InvalidKeyException("cannot recognise key type in ECGOST-2012-256 signer");
            }
        }
        this.c.reset();
        this.d.a(false, param);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        if (privateKey instanceof ECKey) {
            CipherParameters param = ECUtil.d(privateKey);
            this.c.reset();
            SecureRandom secureRandom = this.appRandom;
            if (secureRandom != null) {
                this.d.a(true, new ParametersWithRandom(param, secureRandom));
            } else {
                this.d.a(true, param);
            }
        } else {
            throw new InvalidKeyException("cannot recognise key type in ECGOST-2012-256 signer");
        }
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b) {
        this.c.d(b);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b, int off, int len) {
        this.c.update(b, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        byte[] hash = new byte[this.c.e()];
        this.c.c(hash, 0);
        try {
            byte[] sigBytes = new byte[this.f];
            BigInteger[] sig = this.d.b(hash);
            byte[] r = sig[0].toByteArray();
            byte[] s = sig[1].toByteArray();
            if (s[0] != 0) {
                System.arraycopy(s, 0, sigBytes, this.q - s.length, s.length);
            } else {
                System.arraycopy(s, 1, sigBytes, this.q - (s.length - 1), s.length - 1);
            }
            if (r[0] != 0) {
                System.arraycopy(r, 0, sigBytes, this.f - r.length, r.length);
            } else {
                System.arraycopy(r, 1, sigBytes, this.f - (r.length - 1), r.length - 1);
            }
            return sigBytes;
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        byte[] hash = new byte[this.c.e()];
        this.c.c(hash, 0);
        try {
            int i = this.q;
            byte[] r = new byte[i];
            byte[] s = new byte[i];
            System.arraycopy(sigBytes, 0, s, 0, i);
            int i2 = this.q;
            System.arraycopy(sigBytes, i2, r, 0, i2);
            BigInteger[] sig = {new BigInteger(1, r), new BigInteger(1, s)};
            return this.d.c(hash, sig[0], sig[1]);
        } catch (Exception e) {
            throw new SignatureException("error decoding signature bytes.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(AlgorithmParameterSpec params) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(String param, Object value) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public Object engineGetParameter(String param) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    static AsymmetricKeyParameter a(PublicKey key) {
        return key instanceof BCECGOST3410_2012PublicKey ? ((BCECGOST3410_2012PublicKey) key).engineGetKeyParameters() : ECUtil.e(key);
    }
}
