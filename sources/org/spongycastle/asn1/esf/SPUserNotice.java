package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.DisplayText;
import org.spongycastle.asn1.x509.NoticeReference;

public class SPUserNotice extends ASN1Object {
    private NoticeReference c;
    private DisplayText d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        NoticeReference noticeReference = this.c;
        if (noticeReference != null) {
            v.a(noticeReference);
        }
        DisplayText displayText = this.d;
        if (displayText != null) {
            v.a(displayText);
        }
        return new DERSequence(v);
    }
}
