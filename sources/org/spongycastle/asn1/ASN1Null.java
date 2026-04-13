package org.spongycastle.asn1;

public abstract class ASN1Null extends ASN1Primitive {
    public int hashCode() {
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1Null)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "NULL";
    }
}
