package org.spongycastle.asn1.x500.style;

import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameStyle;

public class BCStrictStyle extends BCStyle {
    public static final X500NameStyle P = new BCStrictStyle();

    public boolean d(X500Name name1, X500Name name2) {
        RDN[] rdns1 = name1.h();
        RDN[] rdns2 = name2.h();
        if (rdns1.length != rdns2.length) {
            return false;
        }
        for (int i = 0; i != rdns1.length; i++) {
            if (!k(rdns1[i], rdns2[i])) {
                return false;
            }
        }
        return true;
    }
}
