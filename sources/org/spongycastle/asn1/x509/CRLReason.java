package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.util.Integers;

public class CRLReason extends ASN1Object {
    private static final String[] c = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
    private static final Hashtable d = new Hashtable();
    private ASN1Enumerated f;

    public static CRLReason e(Object o) {
        if (o instanceof CRLReason) {
            return (CRLReason) o;
        }
        if (o != null) {
            return g(ASN1Enumerated.p(o).q().intValue());
        }
        return null;
    }

    private CRLReason(int reason) {
        this.f = new ASN1Enumerated(reason);
    }

    public String toString() {
        String str;
        int reason = f().intValue();
        if (reason < 0 || reason > 10) {
            str = "invalid";
        } else {
            str = c[reason];
        }
        return "CRLReason: " + str;
    }

    public BigInteger f() {
        return this.f.q();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.f;
    }

    public static CRLReason g(int value) {
        Integer idx = Integers.b(value);
        Hashtable hashtable = d;
        if (!hashtable.containsKey(idx)) {
            hashtable.put(idx, new CRLReason(value));
        }
        return (CRLReason) hashtable.get(idx);
    }
}
