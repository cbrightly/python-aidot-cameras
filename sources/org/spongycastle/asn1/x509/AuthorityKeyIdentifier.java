package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class AuthorityKeyIdentifier extends ASN1Object {
    ASN1OctetString c = null;
    GeneralNames d = null;
    ASN1Integer f = null;

    public static AuthorityKeyIdentifier e(Object obj) {
        if (obj instanceof AuthorityKeyIdentifier) {
            return (AuthorityKeyIdentifier) obj;
        }
        if (obj != null) {
            return new AuthorityKeyIdentifier(ASN1Sequence.o(obj));
        }
        return null;
    }

    protected AuthorityKeyIdentifier(ASN1Sequence seq) {
        Enumeration e = seq.s();
        while (e.hasMoreElements()) {
            ASN1TaggedObject o = ASN1TaggedObject.o(e.nextElement());
            switch (o.r()) {
                case 0:
                    this.c = ASN1OctetString.p(o, false);
                    break;
                case 1:
                    this.d = GeneralNames.f(o, false);
                    break;
                case 2:
                    this.f = ASN1Integer.p(o, false);
                    break;
                default:
                    throw new IllegalArgumentException("illegal tag");
            }
        }
    }

    public byte[] f() {
        ASN1OctetString aSN1OctetString = this.c;
        if (aSN1OctetString != null) {
            return aSN1OctetString.q();
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(false, 0, this.c));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 1, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 2, this.f));
        }
        return new DERSequence(v);
    }

    public String toString() {
        return "AuthorityKeyIdentifier: KeyID(" + this.c.q() + ")";
    }
}
