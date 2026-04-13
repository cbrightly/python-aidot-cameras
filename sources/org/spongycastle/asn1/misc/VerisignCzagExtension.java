package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.DERIA5String;

public class VerisignCzagExtension extends DERIA5String {
    public VerisignCzagExtension(DERIA5String str) {
        super(str.a());
    }

    public String toString() {
        return "VerisignCzagExtension: " + a();
    }
}
