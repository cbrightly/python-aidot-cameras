package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.x509.GeneralName;

public class DVCSErrorNotice extends ASN1Object {
    private PKIStatusInfo c;
    private GeneralName d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        GeneralName generalName = this.d;
        if (generalName != null) {
            v.a(generalName);
        }
        return new DERSequence(v);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("DVCSErrorNotice {\ntransactionStatus: ");
        sb.append(this.c);
        sb.append("\n");
        if (this.d != null) {
            str = "transactionIdentifier: " + this.d + "\n";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("}\n");
        return sb.toString();
    }
}
