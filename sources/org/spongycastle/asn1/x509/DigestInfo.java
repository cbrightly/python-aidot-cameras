package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class DigestInfo extends ASN1Object {
    private byte[] c;
    private AlgorithmIdentifier d;

    public static DigestInfo g(Object obj) {
        if (obj instanceof DigestInfo) {
            return (DigestInfo) obj;
        }
        if (obj != null) {
            return new DigestInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    public DigestInfo(AlgorithmIdentifier algId, byte[] digest) {
        this.c = digest;
        this.d = algId;
    }

    public DigestInfo(ASN1Sequence obj) {
        Enumeration e = obj.s();
        this.d = AlgorithmIdentifier.f(e.nextElement());
        this.c = ASN1OctetString.o(e.nextElement()).q();
    }

    public AlgorithmIdentifier e() {
        return this.d;
    }

    public byte[] f() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        v.a(new DEROctetString(this.c));
        return new DERSequence(v);
    }
}
