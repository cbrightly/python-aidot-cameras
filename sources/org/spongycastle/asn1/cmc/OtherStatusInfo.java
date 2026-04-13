package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class OtherStatusInfo extends ASN1Object implements ASN1Choice {
    private final CMCFailInfo c;
    private final PendInfo d;
    private final ExtendedFailInfo f;

    public ASN1Primitive toASN1Primitive() {
        PendInfo pendInfo = this.d;
        if (pendInfo != null) {
            return pendInfo.toASN1Primitive();
        }
        CMCFailInfo cMCFailInfo = this.c;
        if (cMCFailInfo != null) {
            return cMCFailInfo.toASN1Primitive();
        }
        return this.f.toASN1Primitive();
    }
}
