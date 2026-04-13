package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.util.Arrays;

public class MacData extends ASN1Object {
    private static final BigInteger c = BigInteger.valueOf(1);
    DigestInfo d;
    byte[] f;
    BigInteger q;

    public static MacData e(Object obj) {
        if (obj instanceof MacData) {
            return (MacData) obj;
        }
        if (obj != null) {
            return new MacData(ASN1Sequence.o(obj));
        }
        return null;
    }

    private MacData(ASN1Sequence seq) {
        this.d = DigestInfo.g(seq.r(0));
        this.f = Arrays.h(((ASN1OctetString) seq.r(1)).q());
        if (seq.size() == 3) {
            this.q = ((ASN1Integer) seq.r(2)).r();
        } else {
            this.q = c;
        }
    }

    public MacData(DigestInfo digInfo, byte[] salt, int iterationCount) {
        this.d = digInfo;
        this.f = Arrays.h(salt);
        this.q = BigInteger.valueOf((long) iterationCount);
    }

    public DigestInfo f() {
        return this.d;
    }

    public byte[] getSalt() {
        return Arrays.h(this.f);
    }

    public BigInteger getIterationCount() {
        return this.q;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        v.a(new DEROctetString(this.f));
        if (!this.q.equals(c)) {
            v.a(new ASN1Integer(this.q));
        }
        return new DERSequence(v);
    }
}
