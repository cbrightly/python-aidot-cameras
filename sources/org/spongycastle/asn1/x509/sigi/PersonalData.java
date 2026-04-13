package org.spongycastle.asn1.x509.sigi;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;

public class PersonalData extends ASN1Object {
    private NameOrPseudonym c;
    private BigInteger d;
    private ASN1GeneralizedTime f;
    private DirectoryString q;
    private String x;
    private DirectoryString y;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        vec.a(this.c);
        if (this.d != null) {
            vec.a(new DERTaggedObject(false, 0, new ASN1Integer(this.d)));
        }
        if (this.f != null) {
            vec.a(new DERTaggedObject(false, 1, this.f));
        }
        if (this.q != null) {
            vec.a(new DERTaggedObject(true, 2, this.q));
        }
        if (this.x != null) {
            vec.a(new DERTaggedObject(false, 3, new DERPrintableString(this.x, true)));
        }
        if (this.y != null) {
            vec.a(new DERTaggedObject(true, 4, this.y));
        }
        return new DERSequence(vec);
    }
}
