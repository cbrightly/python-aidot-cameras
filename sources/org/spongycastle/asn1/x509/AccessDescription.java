package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class AccessDescription extends ASN1Object {
    public static final ASN1ObjectIdentifier c = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.2");
    public static final ASN1ObjectIdentifier d = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1");
    ASN1ObjectIdentifier f;
    GeneralName q;

    public ASN1ObjectIdentifier e() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector accessDescription = new ASN1EncodableVector();
        accessDescription.a(this.f);
        accessDescription.a(this.q);
        return new DERSequence(accessDescription);
    }

    public String toString() {
        return "AccessDescription: Oid(" + this.f.s() + ")";
    }
}
