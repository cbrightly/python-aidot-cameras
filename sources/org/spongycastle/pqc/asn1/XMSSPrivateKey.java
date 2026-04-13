package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;

public class XMSSPrivateKey extends ASN1Object {
    private final int c;
    private final byte[] d;
    private final byte[] f;
    private final byte[] q;
    private final byte[] x;
    private final byte[] y;

    public XMSSPrivateKey(int index, byte[] secretKeySeed, byte[] secretKeyPRF, byte[] publicSeed, byte[] root, byte[] bdsState) {
        this.c = index;
        this.d = Arrays.h(secretKeySeed);
        this.f = Arrays.h(secretKeyPRF);
        this.q = Arrays.h(publicSeed);
        this.x = Arrays.h(root);
        this.y = Arrays.h(bdsState);
    }

    private XMSSPrivateKey(ASN1Sequence seq) {
        if (!ASN1Integer.o(seq.r(0)).r().equals(BigInteger.valueOf(0))) {
            throw new IllegalArgumentException("unknown version of sequence");
        } else if (seq.size() == 2 || seq.size() == 3) {
            ASN1Sequence keySeq = ASN1Sequence.o(seq.r(1));
            this.c = ASN1Integer.o(keySeq.r(0)).r().intValue();
            this.d = Arrays.h(ASN1OctetString.o(keySeq.r(1)).q());
            this.f = Arrays.h(ASN1OctetString.o(keySeq.r(2)).q());
            this.q = Arrays.h(ASN1OctetString.o(keySeq.r(3)).q());
            this.x = Arrays.h(ASN1OctetString.o(keySeq.r(4)).q());
            if (seq.size() == 3) {
                this.y = Arrays.h(ASN1OctetString.p(ASN1TaggedObject.o(seq.r(2)), true).q());
            } else {
                this.y = null;
            }
        } else {
            throw new IllegalArgumentException("key sequence wrong size");
        }
    }

    public static XMSSPrivateKey g(Object o) {
        if (o instanceof XMSSPrivateKey) {
            return (XMSSPrivateKey) o;
        }
        if (o != null) {
            return new XMSSPrivateKey(ASN1Sequence.o(o));
        }
        return null;
    }

    public int f() {
        return this.c;
    }

    public byte[] n() {
        return Arrays.h(this.d);
    }

    public byte[] k() {
        return Arrays.h(this.f);
    }

    public byte[] h() {
        return Arrays.h(this.q);
    }

    public byte[] i() {
        return Arrays.h(this.x);
    }

    public byte[] e() {
        return Arrays.h(this.y);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(0));
        ASN1EncodableVector vK = new ASN1EncodableVector();
        vK.a(new ASN1Integer((long) this.c));
        vK.a(new DEROctetString(this.d));
        vK.a(new DEROctetString(this.f));
        vK.a(new DEROctetString(this.q));
        vK.a(new DEROctetString(this.x));
        v.a(new DERSequence(vK));
        v.a(new DERTaggedObject(true, 0, new DEROctetString(this.y)));
        return new DERSequence(v);
    }
}
