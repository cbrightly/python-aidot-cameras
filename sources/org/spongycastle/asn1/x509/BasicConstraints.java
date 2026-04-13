package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class BasicConstraints extends ASN1Object {
    ASN1Boolean c = ASN1Boolean.r(false);
    ASN1Integer d = null;

    public static BasicConstraints e(Object obj) {
        if (obj instanceof BasicConstraints) {
            return (BasicConstraints) obj;
        }
        if (obj instanceof X509Extension) {
            return e(X509Extension.a((X509Extension) obj));
        }
        if (obj != null) {
            return new BasicConstraints(ASN1Sequence.o(obj));
        }
        return null;
    }

    private BasicConstraints(ASN1Sequence seq) {
        if (seq.size() == 0) {
            this.c = null;
            this.d = null;
            return;
        }
        if (seq.r(0) instanceof ASN1Boolean) {
            this.c = ASN1Boolean.p(seq.r(0));
        } else {
            this.c = null;
            this.d = ASN1Integer.o(seq.r(0));
        }
        if (seq.size() <= 1) {
            return;
        }
        if (this.c != null) {
            this.d = ASN1Integer.o(seq.r(1));
            return;
        }
        throw new IllegalArgumentException("wrong sequence in constructor");
    }

    public boolean g() {
        ASN1Boolean aSN1Boolean = this.c;
        return aSN1Boolean != null && aSN1Boolean.s();
    }

    public BigInteger f() {
        ASN1Integer aSN1Integer = this.d;
        if (aSN1Integer != null) {
            return aSN1Integer.r();
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1Boolean aSN1Boolean = this.c;
        if (aSN1Boolean != null) {
            v.a(aSN1Boolean);
        }
        ASN1Integer aSN1Integer = this.d;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        return new DERSequence(v);
    }

    public String toString() {
        if (this.d != null) {
            return "BasicConstraints: isCa(" + g() + "), pathLenConstraint = " + this.d.r();
        } else if (this.c == null) {
            return "BasicConstraints: isCa(false)";
        } else {
            return "BasicConstraints: isCa(" + g() + ")";
        }
    }
}
