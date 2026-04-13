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
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.ECNRSigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.DSABase;
import org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.util.Arrays;

public class SignatureSpi extends DSABase {
    SignatureSpi(Digest digest, DSA signer, DSAEncoder encoder) {
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

    public static class ecDSA extends SignatureSpi {
        public ecDSA() {
            super(DigestFactory.b(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA extends SignatureSpi {
        public ecDetDSA() {
            super(DigestFactory.b(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.b())), new StdDSAEncoder());
        }
    }

    public static class ecDSAnone extends SignatureSpi {
        public ecDSAnone() {
            super(new NullDigest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA224 extends SignatureSpi {
        public ecDSA224() {
            super(DigestFactory.c(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA224 extends SignatureSpi {
        public ecDetDSA224() {
            super(DigestFactory.c(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.c())), new StdDSAEncoder());
        }
    }

    public static class ecDSA256 extends SignatureSpi {
        public ecDSA256() {
            super(DigestFactory.d(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA256 extends SignatureSpi {
        public ecDetDSA256() {
            super(DigestFactory.d(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.d())), new StdDSAEncoder());
        }
    }

    public static class ecDSA384 extends SignatureSpi {
        public ecDSA384() {
            super(DigestFactory.e(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA384 extends SignatureSpi {
        public ecDetDSA384() {
            super(DigestFactory.e(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.e())), new StdDSAEncoder());
        }
    }

    public static class ecDSA512 extends SignatureSpi {
        public ecDSA512() {
            super(DigestFactory.j(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA512 extends SignatureSpi {
        public ecDetDSA512() {
            super(DigestFactory.j(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.j())), new StdDSAEncoder());
        }
    }

    public static class ecDSASha3_224 extends SignatureSpi {
        public ecDSASha3_224() {
            super(DigestFactory.f(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSASha3_224 extends SignatureSpi {
        public ecDetDSASha3_224() {
            super(DigestFactory.f(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.f())), new StdDSAEncoder());
        }
    }

    public static class ecDSASha3_256 extends SignatureSpi {
        public ecDSASha3_256() {
            super(DigestFactory.g(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSASha3_256 extends SignatureSpi {
        public ecDetDSASha3_256() {
            super(DigestFactory.g(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.g())), new StdDSAEncoder());
        }
    }

    public static class ecDSASha3_384 extends SignatureSpi {
        public ecDSASha3_384() {
            super(DigestFactory.h(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSASha3_384 extends SignatureSpi {
        public ecDetDSASha3_384() {
            super(DigestFactory.h(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.h())), new StdDSAEncoder());
        }
    }

    public static class ecDSASha3_512 extends SignatureSpi {
        public ecDSASha3_512() {
            super(DigestFactory.i(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSASha3_512 extends SignatureSpi {
        public ecDetDSASha3_512() {
            super(DigestFactory.i(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.i())), new StdDSAEncoder());
        }
    }

    public static class ecDSARipeMD160 extends SignatureSpi {
        public ecDSARipeMD160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR extends SignatureSpi {
        public ecNR() {
            super(DigestFactory.b(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR224 extends SignatureSpi {
        public ecNR224() {
            super(DigestFactory.c(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR256 extends SignatureSpi {
        public ecNR256() {
            super(DigestFactory.d(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR384 extends SignatureSpi {
        public ecNR384() {
            super(DigestFactory.e(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR512 extends SignatureSpi {
        public ecNR512() {
            super(DigestFactory.j(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecCVCDSA extends SignatureSpi {
        public ecCVCDSA() {
            super(DigestFactory.b(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA224 extends SignatureSpi {
        public ecCVCDSA224() {
            super(DigestFactory.c(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA256 extends SignatureSpi {
        public ecCVCDSA256() {
            super(DigestFactory.d(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA384 extends SignatureSpi {
        public ecCVCDSA384() {
            super(DigestFactory.e(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA512 extends SignatureSpi {
        public ecCVCDSA512() {
            super(DigestFactory.j(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecPlainDSARP160 extends SignatureSpi {
        public ecPlainDSARP160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), new PlainDSAEncoder());
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

    public static class PlainDSAEncoder implements DSAEncoder {
        private PlainDSAEncoder() {
        }

        public byte[] a(BigInteger r, BigInteger s) {
            byte[] res;
            byte[] first = c(r);
            byte[] second = c(s);
            if (first.length > second.length) {
                res = new byte[(first.length * 2)];
            } else {
                res = new byte[(second.length * 2)];
            }
            System.arraycopy(first, 0, res, (res.length / 2) - first.length, first.length);
            System.arraycopy(second, 0, res, res.length - second.length, second.length);
            return res;
        }

        private byte[] c(BigInteger val) {
            byte[] res = val.toByteArray();
            if (res[0] != 0) {
                return res;
            }
            byte[] tmp = new byte[(res.length - 1)];
            System.arraycopy(res, 1, tmp, 0, tmp.length);
            return tmp;
        }

        public BigInteger[] b(byte[] encoding) {
            byte[] first = new byte[(encoding.length / 2)];
            byte[] second = new byte[(encoding.length / 2)];
            System.arraycopy(encoding, 0, first, 0, first.length);
            System.arraycopy(encoding, first.length, second, 0, second.length);
            return new BigInteger[]{new BigInteger(1, first), new BigInteger(1, second)};
        }
    }
}
