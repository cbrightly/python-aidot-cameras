package org.spongycastle.jcajce.provider.asymmetric.dstu;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.GOST3411Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.DSTU4145Signer;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.interfaces.ECKey;

public class SignatureSpi extends java.security.SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private static byte[] c = {10, 9, 13, 6, 14, 11, 4, 5, 15, 1, 3, 12, 7, 0, 8, 2, 8, 0, 12, 4, 9, 6, 7, 11, 2, 3, 1, 15, 5, 14, 10, 13, 15, 6, 5, 8, 14, 11, 10, 4, 12, 0, 3, 7, 2, 9, 1, 13, 3, 8, 13, 9, 6, 11, 15, 0, 2, 5, 12, 10, 4, 14, 1, 7, 15, 8, 14, 9, 7, 2, 0, 13, 12, 6, 1, 5, 11, 4, 3, 10, 2, 8, 9, 7, 5, 15, 0, 11, 12, 1, 13, 14, 10, 3, 6, 4, 3, 8, 11, 5, 6, 4, 14, 10, 2, 12, 1, 7, 9, 15, 13, 0, 1, 2, 3, 14, 6, 13, 11, 8, 15, 10, 12, 5, 7, 9, 0, 4};
    private Digest d;
    private DSA f = new DSTU4145Signer();

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        CipherParameters param;
        if (publicKey instanceof BCDSTU4145PublicKey) {
            param = ((BCDSTU4145PublicKey) publicKey).engineGetKeyParameters();
        } else {
            param = ECUtil.e(publicKey);
        }
        this.d = new GOST3411Digest(a(((BCDSTU4145PublicKey) publicKey).getSbox()));
        this.f.a(false, param);
    }

    /* access modifiers changed from: package-private */
    public byte[] a(byte[] compressed) {
        byte[] expanded = new byte[128];
        for (int i = 0; i < compressed.length; i++) {
            expanded[i * 2] = (byte) ((compressed[i] >> 4) & 15);
            expanded[(i * 2) + 1] = (byte) (compressed[i] & 15);
        }
        return expanded;
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        CipherParameters param = null;
        if (privateKey instanceof ECKey) {
            param = ECUtil.d(privateKey);
        }
        this.d = new GOST3411Digest(c);
        SecureRandom secureRandom = this.appRandom;
        if (secureRandom != null) {
            this.f.a(true, new ParametersWithRandom(param, secureRandom));
        } else {
            this.f.a(true, param);
        }
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b) {
        this.d.d(b);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b, int off, int len) {
        this.d.update(b, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        byte[] hash = new byte[this.d.e()];
        this.d.c(hash, 0);
        try {
            BigInteger[] sig = this.f.b(hash);
            byte[] r = sig[0].toByteArray();
            byte[] s = sig[1].toByteArray();
            byte[] sigBytes = new byte[(r.length > s.length ? r.length * 2 : s.length * 2)];
            System.arraycopy(s, 0, sigBytes, (sigBytes.length / 2) - s.length, s.length);
            System.arraycopy(r, 0, sigBytes, sigBytes.length - r.length, r.length);
            return new DEROctetString(sigBytes).getEncoded();
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        byte[] hash = new byte[this.d.e()];
        this.d.c(hash, 0);
        try {
            byte[] bytes = ((ASN1OctetString) ASN1Primitive.h(sigBytes)).q();
            byte[] r = new byte[(bytes.length / 2)];
            byte[] s = new byte[(bytes.length / 2)];
            System.arraycopy(bytes, 0, s, 0, bytes.length / 2);
            System.arraycopy(bytes, bytes.length / 2, r, 0, bytes.length / 2);
            BigInteger[] sig = {new BigInteger(1, r), new BigInteger(1, s)};
            return this.f.c(hash, sig[0], sig[1]);
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
}
