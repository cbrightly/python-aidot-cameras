package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SM3Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.SM2Signer;
import org.spongycastle.jcajce.provider.asymmetric.util.DSABase;
import org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.util.Arrays;

public class GMSignatureSpi extends DSABase {
    GMSignatureSpi(Digest digest, DSA signer, DSAEncoder encoder) {
        super(digest, signer, encoder);
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        CipherParameters param = ECUtils.a(publicKey);
        this.c.reset();
        this.d.a(false, param);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        CipherParameters param = ECUtil.d(privateKey);
        this.c.reset();
        SecureRandom secureRandom = this.appRandom;
        if (secureRandom != null) {
            this.d.a(true, new ParametersWithRandom(param, secureRandom));
        } else {
            this.d.a(true, param);
        }
    }

    public static class sm3WithSM2 extends GMSignatureSpi {
        public sm3WithSM2() {
            super(new SM3Digest(), new SM2Signer(), new StdDSAEncoder());
        }
    }

    public static class StdDSAEncoder implements DSAEncoder {
        private StdDSAEncoder() {
        }

        public byte[] a(BigInteger r, BigInteger s) {
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.a(new ASN1Integer(r));
            v.a(new ASN1Integer(s));
            return new DERSequence(v).getEncoded("DER");
        }

        public BigInteger[] b(byte[] encoding) {
            ASN1Sequence s = (ASN1Sequence) ASN1Primitive.h(encoding);
            if (s.size() != 2) {
                throw new IOException("malformed signature");
            } else if (Arrays.b(encoding, s.getEncoded("DER"))) {
                return new BigInteger[]{ASN1Integer.o(s.r(0)).r(), ASN1Integer.o(s.r(1)).r()};
            } else {
                throw new IOException("malformed signature");
            }
        }
    }
}
