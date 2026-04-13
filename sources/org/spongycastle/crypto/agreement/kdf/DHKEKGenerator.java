package org.spongycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.util.Pack;

public class DHKEKGenerator implements DerivationFunction {
    private final Digest a;
    private ASN1ObjectIdentifier b;
    private int c;
    private byte[] d;
    private byte[] e;

    public DHKEKGenerator(Digest digest) {
        this.a = digest;
    }

    public void b(DerivationParameters param) {
        DHKDFParameters params = (DHKDFParameters) param;
        this.b = params.a();
        this.c = params.c();
        this.d = params.d();
        this.e = params.b();
    }

    public int a(byte[] out, int outOff, int len) {
        int cThreshold;
        byte[] bArr = out;
        int i = len;
        int outOff2 = outOff;
        if (bArr.length - i >= outOff2) {
            long oBytes = (long) i;
            int outLen = this.a.e();
            if (oBytes <= 8589934591L) {
                int cThreshold2 = (int) (((((long) outLen) + oBytes) - 1) / ((long) outLen));
                byte[] dig = new byte[this.a.e()];
                int i2 = 0;
                int counter = 1;
                int len2 = i;
                while (i2 < cThreshold2) {
                    Digest digest = this.a;
                    byte[] bArr2 = this.d;
                    digest.update(bArr2, 0, bArr2.length);
                    ASN1EncodableVector v1 = new ASN1EncodableVector();
                    ASN1EncodableVector v2 = new ASN1EncodableVector();
                    v2.a(this.b);
                    v2.a(new DEROctetString(Pack.f(counter)));
                    v1.a(new DERSequence(v2));
                    if (this.e != null) {
                        cThreshold = cThreshold2;
                        v1.a(new DERTaggedObject(true, 0, new DEROctetString(this.e)));
                    } else {
                        cThreshold = cThreshold2;
                    }
                    v1.a(new DERTaggedObject(true, 2, new DEROctetString(Pack.f(this.c))));
                    try {
                        byte[] other = new DERSequence(v1).getEncoded("DER");
                        this.a.update(other, 0, other.length);
                        this.a.c(dig, 0);
                        if (len2 > outLen) {
                            System.arraycopy(dig, 0, bArr, outOff2, outLen);
                            outOff2 += outLen;
                            len2 -= outLen;
                        } else {
                            System.arraycopy(dig, 0, bArr, outOff2, len2);
                        }
                        counter++;
                        i2++;
                        cThreshold2 = cThreshold;
                    } catch (IOException e2) {
                        throw new IllegalArgumentException("unable to encode parameter info: " + e2.getMessage());
                    }
                }
                this.a.reset();
                return (int) oBytes;
            }
            throw new IllegalArgumentException("Output length too large");
        }
        throw new OutputLengthException("output buffer too small");
    }
}
