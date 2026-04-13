package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.IssuerSerial;

public class ProcurationSyntax extends ASN1Object {
    private String c;
    private DirectoryString d;
    private GeneralName f;
    private IssuerSerial q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        if (this.c != null) {
            vec.a(new DERTaggedObject(true, 1, new DERPrintableString(this.c, true)));
        }
        if (this.d != null) {
            vec.a(new DERTaggedObject(true, 2, this.d));
        }
        if (this.f != null) {
            vec.a(new DERTaggedObject(true, 3, this.f));
        } else {
            vec.a(new DERTaggedObject(true, 3, this.q));
        }
        return new DERSequence(vec);
    }
}
