package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class UserNotice extends ASN1Object {
    private final NoticeReference c;
    private final DisplayText d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector av = new ASN1EncodableVector();
        NoticeReference noticeReference = this.c;
        if (noticeReference != null) {
            av.a(noticeReference);
        }
        DisplayText displayText = this.d;
        if (displayText != null) {
            av.a(displayText);
        }
        return new DERSequence(av);
    }
}
