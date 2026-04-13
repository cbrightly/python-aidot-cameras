package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AuthorityInformationAccess;

public class ServiceLocator extends ASN1Object {
    private final X500Name c;
    private final AuthorityInformationAccess d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        AuthorityInformationAccess authorityInformationAccess = this.d;
        if (authorityInformationAccess != null) {
            v.a(authorityInformationAccess);
        }
        return new DERSequence(v);
    }
}
