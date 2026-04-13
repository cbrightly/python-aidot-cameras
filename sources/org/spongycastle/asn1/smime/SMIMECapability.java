package org.spongycastle.asn1.smime;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapability extends ASN1Object {
    public static final ASN1ObjectIdentifier a1 = NISTObjectIdentifiers.K;
    public static final ASN1ObjectIdentifier c = PKCSObjectIdentifiers.e1;
    public static final ASN1ObjectIdentifier d = PKCSObjectIdentifiers.f1;
    public static final ASN1ObjectIdentifier f = PKCSObjectIdentifiers.g1;
    public static final ASN1ObjectIdentifier p0 = NISTObjectIdentifiers.C;
    public static final ASN1ObjectIdentifier q = new ASN1ObjectIdentifier("1.3.14.3.2.7");
    public static final ASN1ObjectIdentifier x = PKCSObjectIdentifiers.m0;
    public static final ASN1ObjectIdentifier y = PKCSObjectIdentifiers.n0;
    public static final ASN1ObjectIdentifier z = NISTObjectIdentifiers.u;
    private ASN1Encodable a2;
    private ASN1ObjectIdentifier p1;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.p1);
        ASN1Encodable aSN1Encodable = this.a2;
        if (aSN1Encodable != null) {
            v.a(aSN1Encodable);
        }
        return new DERSequence(v);
    }
}
