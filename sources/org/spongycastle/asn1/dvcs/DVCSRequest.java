package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class DVCSRequest extends ASN1Object {
    private DVCSRequestInformation c;
    private Data d;
    private GeneralName f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        GeneralName generalName = this.f;
        if (generalName != null) {
            v.a(generalName);
        }
        return new DERSequence(v);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("DVCSRequest {\nrequestInformation: ");
        sb.append(this.c);
        sb.append("\ndata: ");
        sb.append(this.d);
        sb.append("\n");
        if (this.f != null) {
            str = "transactionIdentifier: " + this.f + "\n";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("}\n");
        return sb.toString();
    }
}
