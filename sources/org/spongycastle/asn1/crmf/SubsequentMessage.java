package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Integer;

public class SubsequentMessage extends ASN1Integer {
    public static final SubsequentMessage d = new SubsequentMessage(0);
    public static final SubsequentMessage f = new SubsequentMessage(1);

    private SubsequentMessage(int value) {
        super((long) value);
    }
}
